package DAO;

import java.util.List;

import Utils.Jpa;
import entity.Director;
import entity.Favorite;
import entity.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class DirectorDAO extends DAO<Director, Integer> {
	private EntityManager em = Jpa.getEntityManager();

	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}

	@Override
	public void create(Director entity) {
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
	public void update(Director entity) {
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
	public void remove(Director entity) {
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
	public Director findById(Integer id) {
		return em.find(Director.class, id);
	}

	public List<Director> selectAll() {
		String jpql = "SELECT d FROM Director d";
		TypedQuery<Director> query = em.createQuery(jpql, Director.class);

		return query.getResultList();
	}

}
