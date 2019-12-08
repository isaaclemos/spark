/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelDB;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import util.JPAUtil;

/**
 *
 * @author isaac
 */
public abstract class ActiveRecord<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public ActiveRecord() {

	}

	public void creatIt() throws Exception {

		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = JPAUtil.getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(this);
			em.getTransaction().commit();
		} finally {
			if (em != null && emf != null) {
				em.close();
				emf.close();
			}
		}
	}

	public void savIt() throws Exception {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = JPAUtil.getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.merge(this);
			em.getTransaction().commit();
		} finally {
			if (em != null && emf != null) {
				em.close();
				emf.close();
			}
		}
	}

	public void delete() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		ActiveRecord<T> obj = this;

		try {
			emf = JPAUtil.getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getTransaction().begin();
			if (!em.contains(obj)) {
				obj = em.merge(obj);
			}
			em.remove(obj);
			em.getTransaction().commit();
		} finally {
			if (em != null && emf != null) {
				em.close();
				emf.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> find() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = JPAUtil.getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getEntityManagerFactory().getCache().evictAll();
			return em.createQuery("select t from " + getClass().getTypeName() + " t").getResultList();
		} finally {
			if (em != null && emf != null) {
				em.close();
				emf.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public T findById(Integer id) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = JPAUtil.getEntityManagerFactory();
			em = emf.createEntityManager();
			em.getEntityManagerFactory().getCache().evictAll();
			return (T) em.find(getClass(), id);
		} finally {
			if (em != null && emf != null) {
				em.close();
				emf.close();
			}

		}
	}
}
