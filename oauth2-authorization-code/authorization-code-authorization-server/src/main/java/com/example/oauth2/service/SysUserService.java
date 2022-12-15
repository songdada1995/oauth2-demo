package com.example.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author songbo
 * @version 1.0
 * @date 2022/12/14 10:01
 */
@Service
public class SysUserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 可改为查询数据库
        return new User("test", passwordEncoder.encode("123456"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("READ_ONLY_CLIENT"));
    }
}
