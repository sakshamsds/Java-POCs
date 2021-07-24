package io.javabrain.springsecurityjwt.services;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		// get user from where ever it is stored, database etc
		// if no specific values then user existing user model from spring security
		// Security flaw: don't keep the password here, instead take it from config
		return new User("foo", "foo", new ArrayList<>());
	}

}
