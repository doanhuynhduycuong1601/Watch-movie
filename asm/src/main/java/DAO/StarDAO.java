package DAO;

import java.util.List;

import Utils.Jpa;
import entity.Country;
import entity.Director;
import entity.Genre;
import entity.Star;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class StarDAO extends DAO<Star, Integer> {
	
	private EntityManager em = Jpa.getEntityManager();

	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}
	
	public List<Country> findAll() {
		// Câu lệnh truy vấn JPQL
		String jpql = "SELECT s FROM Star s";
		// Tạo đối tượng truy vấn
		TypedQuery<Country> query = em.createQuery(jpql, Country.class);
		// Truy vấn
		return query.getResultList();
	}
	

	@Override
	public void create(Star entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Star entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Star entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Star findById(Integer id) {
		return em.find(Star.class, id);
	}
	
	public List<Star> selectAll() {
		String jpql = "SELECT s FROM Star s";
		TypedQuery<Star> query = em.createQuery(jpql, Star.class);

		return query.getResultList();
	}
	
	public Star getReference(int id) {
		return em.getReference(Star.class, id);
	}


}
