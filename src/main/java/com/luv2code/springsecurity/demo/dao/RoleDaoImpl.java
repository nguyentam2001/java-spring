package com.luv2code.springsecurity.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao{

	//need inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Role findRoleByName(String theRoleName) {
		//get current hibernate session
		Session currentSession= sessionFactory.getCurrentSession();
		//now retrieve/read role from database by name
		Query<Role> theQuery= currentSession.createQuery("from Role where name=:roleName",Role.class);
		//set parameter for query
		theQuery.setParameter("roleName", theRoleName);
		
		Role theRole=null;
		try {
			theRole= theQuery.getSingleResult();
		}catch(Exception ex) {
			theRole=null;
		}
		return theRole;
	}

}
