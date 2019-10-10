package com.dfsx.standby;

import com.dfsx.standby.webapi.WebServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by YC on 2019/9/29.
 */
public class Application {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(GlobalConfig.class);
        applicationContext.refresh();

        WebServer webServer = new WebServer(applicationContext);
        webServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                webServer.stop();
            }
        });
    }
}
