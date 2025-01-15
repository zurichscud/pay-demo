package com.example.paydemo.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.paydemo.entity.LoginUser;
import com.example.paydemo.entity.User;
import com.example.paydemo.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: zurichscud
 * @Date: 2025/1/15 下午11:27
 * @Description: TODO
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO:数据库查询
        //TODO:权限验证
        User user = new User();
        if (ObjectUtil.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new LoginUser(user);
    }
}
