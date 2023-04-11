package DAO;

import java.util.List;

import Utils.Jpa;
import entity.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class CountryDAO extends DAO<Country, Integer> {
	
	private EntityManager em = Jpa.getEntityManager();

	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}
	
	public List<Country> findAll() {
		// Câu lệnh truy vấn JPQL
		String jpql = "SELECT c FROM Country c";
		// Tạo đối tượng truy vấn
		TypedQuery<Country> query = em.createQuery(jpql, Country.class);
		// Truy vấn
		return query.getResultList();
	}

	@Override
	public void create(Country entity) {
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void update(Country entity) {
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void remove(Country entity) {
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public Country findById(Integer id) {
		return em.find(Country.class, id);
	}


}
