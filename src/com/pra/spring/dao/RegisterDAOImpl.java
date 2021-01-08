package com.pra.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.pra.spring.dto.RegisterDTO;

@Repository
public class RegisterDAOImpl implements RegisterDAO {

	private SessionFactory factory;

	public RegisterDAOImpl(SessionFactory factory) {
		System.out.println("Created..." + this.getClass().getSimpleName());
		this.factory = factory;
	}

	public long save(RegisterDTO dto) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(dto);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				transaction.rollback();
		} finally {
			if (session != null)
				session.close();

		}
		return 0;

	}

}
