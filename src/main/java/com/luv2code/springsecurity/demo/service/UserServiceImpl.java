package com.luv2code.springsecurity.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luv2code.springsecurity.demo.dao.RoleDao;
import com.luv2code.springsecurity.demo.dao.UserDao;
import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.user.CrmUser;

@Service
public class UserServiceImpl implements UserService {
	//need inject user DAO
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findUserByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public User findByUserName(String userName) {
		//check the database if user exist
		return userDao.findUserByName(userName);
	}

	@Override
	@Transactional
	public void save(CrmUser crmUser) {
		User user= new User();
		user.setUserName(crmUser.getUserName());
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setEmail(crmUser.getEmail());
		//give role default is "employee"
		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
		//save user in database
		userDao.save(user);
	}
	
	

}
