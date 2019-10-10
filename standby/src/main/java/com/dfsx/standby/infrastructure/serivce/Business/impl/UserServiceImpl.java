package com.dfsx.standby.infrastructure.serivce.Business.impl;

import com.dfsx.standby.infrastructure.entities.UserEntity;
import com.dfsx.standby.infrastructure.repository.UserRepository;
import com.dfsx.standby.infrastructure.serivce.Business.UserService;
import com.dfsx.standby.infrastructure.utils.JWTUtil;
import com.dfsx.standby.webapi.dto.UserParam;
import com.dfsx.standby.webapi.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by YC on 2019/9/29.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository _userRepository;
    @Autowired
    private UserDetailsService _userDetailsService;
    @Autowired
    private PasswordEncoder _passwordEncoder;
    @Autowired
    private JWTUtil _jwtUtil;

    @Override
    public UserModel register(UserParam userParam) throws Exception {
        if (_userRepository.findUserByUsername(userParam.getUsername()) != null) {
            return null;
        }
        userParam.setPassword(_passwordEncoder.encode(userParam.getPassword()));
        _userRepository.addUser(new UserEntity(userParam));
        return new UserModel(userParam);
    }

    @Override
    public String login(String username, String password) throws Exception {
        String token;
        UserDetails userDetails = _userDetailsService.loadUserByUsername(username);
        if (_passwordEncoder.matches(_passwordEncoder.encode(password), userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        token = _jwtUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public UserModel getUserByUsername(String username) {
        UserEntity entity = _userRepository.findUserByUsername(username);
        if (entity == null) {
            return null;
        }
        return new UserModel(entity);
    }

    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring(JWTUtil.TOKENHEAD.length());
        if (_jwtUtil.canRefresh(token)) {
            return _jwtUtil.refreshToken(token);
        }
        return null;
    }
}
