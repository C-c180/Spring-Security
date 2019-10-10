package com.dfsx.standby.webapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Configuration
@EnableWebMvc
@ComponentScan({"com.dfsx.standby.webapi"})
@ImportResource("classpath:webapi/servlet-context.xml")
public class ServletConfig extends WebMvcConfigurerAdapter {
    private static final String DEFAULT_FORMAT = "snake";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MediaTypeInterceptor());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //add default(snake) json-http-message converter
        Json2HttpMessageConverter defaultJsonConverter = new Json2HttpMessageConverter();
        defaultJsonConverter.setObjectMapper(createObjectMapper((PropertyNamingStrategy.SNAKE_CASE)));
        defaultJsonConverter.setSupportedMediaTypes(createJsonMediaTypes(null));
        converters.add(defaultJsonConverter);

        //add camel json-http-message converter
        Json2HttpMessageConverter camelJsonConverter = new Json2HttpMessageConverter();
        camelJsonConverter.setObjectMapper(createObjectMapper(PropertyNamingStrategy.LOWER_CAMEL_CASE));
        camelJsonConverter.setSupportedMediaTypes(createJsonMediaTypes("format=camel"));
        converters.add(camelJsonConverter);
    }

    private ObjectMapper createObjectMapper(PropertyNamingStrategy namingStrategy) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setPropertyNamingStrategy(namingStrategy);
        SimpleModule simpleModule = new SimpleModule();
        //convert between date and unix timestamp(in seconds)
        simpleModule.addDeserializer(Date.class, new UnixTimestampDeserializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    private List<MediaType> createJsonMediaTypes(String parameters) {
        Map<String, String> mediaTypeParameters = parseParameters(parameters);
        List<MediaType> mediaTypes = new ArrayList<>();
        //mediaTypes.add(new JsonMediaType(mediaTypeParameters));
        mediaTypes.add(new MediaType("application", "json", mediaTypeParameters));
        return mediaTypes;
    }

    private static Map<String, String> parseParameters(String parameters) {
        Map<String, String> map = new HashMap<>();
        if(StringUtils.isEmpty(parameters)) return map;
        String[] keyvalues = parameters.split(";");
        for(String keyvalue : keyvalues) {
            int semicolon = keyvalue.indexOf('=');
            if(semicolon > 0) {
                map.put(
                    keyvalue.substring(0, semicolon).trim(),
                    keyvalue.substring(semicolon + 1).trim());
            }
        }
        return map;
    }

    private static class Json2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public Json2HttpMessageConverter() {
            super();
        }

        @Override
        protected boolean canRead(MediaType mediaType) {
            if(!super.canRead(mediaType)) return false;
            return validateJsonFormat(mediaType);
        }

        @Override
        protected boolean canWrite(MediaType mediaType) {
            if(!super.canWrite(mediaType)) return false;
            return validateJsonFormat(mediaType);
        }

        private boolean validateJsonFormat(MediaType mediaType) {
            if(mediaType == null) return true;

            ObjectMapper objectMapper = getObjectMapper();
            String format = mediaType.getParameters().getOrDefault("format", DEFAULT_FORMAT);
            switch (format) {
                case "snake":
                    return (objectMapper.getPropertyNamingStrategy() == PropertyNamingStrategy.SNAKE_CASE);
                case "camel":
                    return (objectMapper.getPropertyNamingStrategy() == PropertyNamingStrategy.LOWER_CAMEL_CASE);
                default:
                    return false;
            }
        }
    }

    private static class MediaTypeInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Enumeration<String> headerValues = request.getHeaders("Accept");
            if(headerValues != null) {
                try {
                    List<MediaType> mediaTypes = MediaType.parseMediaTypes(Collections.list(headerValues));
                    for(MediaType mediaType : mediaTypes) {
                        if(mediaType.getType().equals("application") &&
                            mediaType.getSubtype().equals("json")) {
                            request.setAttribute(
                                HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE,
                                Collections.singleton(mediaType));
                            return true;
                        }
                    }
                } catch (InvalidMediaTypeException ex) {
                }
            }
            //send default application/json in all other case
            request.setAttribute(
                HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE,
                Collections.singleton(MediaType.APPLICATION_JSON));
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {
        }
    }

    private static class UnixTimestampDeserializer extends DateDeserializers.DateDeserializer {
        @Override
        public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            if (_customFormat == null) {
                JsonToken t = jp.getCurrentToken();
                if (t == JsonToken.VALUE_NUMBER_INT) {
                    return new Date(jp.getLongValue() * 1000);
                }
            }
            return super.deserialize(jp, ctxt);
        }
    }
}
