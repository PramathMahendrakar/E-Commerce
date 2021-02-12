package com.pra.spring.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.pra.spring.dto.LoginDTO;
import com.pra.spring.dto.RegisterDTO;
import com.pra.spring.dto.ResetPasswordDTO;
import com.pra.spring.exception.RepositoryException;
import com.pra.spring.service.RegisterServiceImpl;

@Repository
public class RegisterDAOImpl implements RegisterDAO {

	private final static Logger logger = LoggerFactory.getLogger(RegisterDAOImpl.class);

	private SessionFactory factory;

	public RegisterDAOImpl(SessionFactory factory) {
		logger.info("Created" + this.getClass().getSimpleName());
		this.factory = factory;
	}

	public long save(RegisterDTO dto) {
		Session session = null;
		Transaction transaction = null;
		Serializable serializable = null;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			serializable = session.save(dto);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null)
				transaction.rollback();
		} finally {
			if (session != null)
				session.close();

		}
		return (long) serializable;

	}

	public long fetchEmailCount(RegisterDTO dto) {
		Session session = null;
		long n = 0;

		String email = dto.getEmail();
		try {

			session = factory.openSession();
			Query query = session.createQuery("select count(*) from RegisterDTO where email=:e ");
			query.setParameter("e", email);
			n = (Long) query.uniqueResult();
			System.out.println(n);

		} catch (Exception e) {
			System.err.println(e);
		} finally {
			if (session != null)
				session.close();
		}
		return n;

	}

	@Override
	public List<RegisterDTO> fetch(RegisterDTO dto) {

		List<RegisterDTO> list = null;
		Session session = null;
		try {

			session = factory.openSession();
			Query query = session.createQuery("from RegisterDTO where email=:e");
			query.setParameter("e", dto.getEmail());
			list = (List<RegisterDTO>) query.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	@Override
	public String fetchPassword(RegisterDTO dto) {
		Session session = null;
		String password = null;

		try {
			session = factory.openSession();
			Query query = session.createQuery("select password from RegisterDTO where email=:e");
			query.setParameter("e", dto.getEmail());
			password = (String) query.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return password;
	}

	@Override
	public String updatePaswword(RegisterDTO dto) {

		Session session = null;
		Transaction transaction = null;

		String randomString = null;

		randomString = RandomStringUtils.random(5, true, false);

		System.out.println(randomString);

		try {

			session = factory.openSession();
			transaction = session.beginTransaction();

			Query query = session.createQuery("update RegisterDTO set password=:p where email=:e");

			query.setParameter("e", dto.getEmail());
			query.setParameter("p", randomString);

			int status = query.executeUpdate();

			System.out.println(status);
			transaction.commit();
			return randomString;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return randomString;
	}

	@Override
	public boolean isValidOtp(ResetPasswordDTO dto) throws RepositoryException {
		Session session = null;
		long count = 0;
		try {
			session = factory.openSession();
			Query query = session.createQuery("select count(*) from RegisterDTO where password=:p");

			query.setParameter("p", dto.getPassword());

			count = (long) query.uniqueResult();
			if (count > 0)
				return true;
		} catch (HibernateException e) {

			throw new RepositoryException(e.getMessage());

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		} finally {
			session.close();
		}
		return false;
	}

	@Override
	public boolean resetPassword(ResetPasswordDTO resetDTO) {
		Session session = null;

		Transaction transaction = null;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("update RegisterDTO set password=:p where password=:q");
			query.setParameter("p", resetDTO.getNewPassword());
			query.setParameter("q", resetDTO.getPassword());

			System.out.println(resetDTO.getNewPassword());
			int status = query.executeUpdate();
			System.out.println(status);
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}

	@Override
	public Integer updateLoginFailCount(String email, int count) throws RepositoryException {
		Session session = null;
		int status = 0;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("update RegisterDTO set invalidLoginCount=:c where email=:e");
			query.setParameter("c", count);
			query.setParameter("e", email);

			status = query.executeUpdate();
			System.out.println(status);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			throw new RepositoryException(e.getMessage());

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		} finally {
			session.close();
		}

		return null;
	}

	@Override
	public boolean updateAccountLocked(String email, boolean locked) throws RepositoryException {
		Session session = null;
		int status = 0;
		Transaction transaction = null;
		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("update RegisterDTO set accountStatusLocked=:b where email=:e");
			query.setParameter("b", locked);
			query.setParameter("e", email);

			status = query.executeUpdate();
			System.out.println(status);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			throw new RepositoryException(e.getMessage());

		} catch (Exception e) {
			throw new RepositoryException(e.getMessage());
		} finally {
			session.close();
		}
		return false;
	};

}
