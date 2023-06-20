package com.project.www.services;

import com.project.www.entity.auth.Role;
import com.project.www.entity.auth.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SecUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());

        return buildUserForAuth(user,authorities);
    }

    private UserDetails buildUserForAuth(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getActive(),true,true,true,authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for(Role role : userRoles) roles.add(new SimpleGrantedAuthority(role.getRole()));
        List<GrantedAuthority> list = new ArrayList<>(roles);
        return list;
    }
}
