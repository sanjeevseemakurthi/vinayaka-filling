package com.example.demo.jwtauth;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	public userdetailsRepository userdetailsRepository;
	
	@Autowired
	public userdata userdata;
	
	@Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		userdata = this.userdetailsRepository.findByUsername(s);
		String username = userdata.getUsername();
		String password = userdata.getPassword();
        return new  User(username, password, new ArrayList<>());
    }
}
