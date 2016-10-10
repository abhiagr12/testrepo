package com.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;


public class CoreUserDetailService implements UserDetailsService{
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		if(username == null) return null;
		//Get USER Details from DB
		String password = "password";
		//End
		User user = new User(username, password, getAuthorities(username));
		return user;
	}
	
	private Collection<SimpleGrantedAuthority> getAuthorities(String username) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
}