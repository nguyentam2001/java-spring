package com.luv2code.springsecurity.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	//need inject the session factory 
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public User findUserByName(String theUserName) {
		//get current session from session factory
		Session currentSession = sessionFactory.getCurrentSession();
		System.out.println("==> user name "+theUserName);
		//retrieve/ read user by username
		Query<User> theQueryUser= currentSession.createQuery("from User where userName='john'",User.class);
//		theQueryUser.setParameter("uName", theUserName);
		User theUser=null;
		try {
			theUser= theQueryUser.getSingleResult();
			System.out.println("user result: "+theUser);
		} catch (Exception e) {
			theUser= null;
		}
		return theUser;
	}

	@Override
	public void save(User user) {
		//get current session
		Session currentSession= sessionFactory.getCurrentSession();
		//create user finally.. LOL
		currentSession.saveOrUpdate(user);
	}

}
