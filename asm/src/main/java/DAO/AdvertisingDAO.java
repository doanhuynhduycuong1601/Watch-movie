package DAO;

import java.util.List;
import java.util.Map;

import Utils.Jpa;
import entity.Advertising;
import entity.CustomerLikeVideo;
import entity.Favorite;
import entity.Genre;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.metamodel.Type;

public class AdvertisingDAO extends DAO<Advertising, Integer> {
	private EntityManager em = Jpa.getEntityManager();
	
	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}

	@Override
	public void create(Advertising entity) {
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
	public void update(Advertising entity) {
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
	public void remove(Advertising entity) {
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
	public Advertising findById(Integer id) {
		return em.find(Advertising.class, id);
	}
	
	
	public List<Advertising> selectAll(String names){
		String jpql = "SELECT l FROM Advertising l where l.names LIKE :names";
		TypedQuery<Advertising> query = em.createQuery(jpql, Advertising.class);
		query.setParameter("names", "%"+names+"%");
		return query.getResultList();
	}
	
	
	public List<Advertising> adver(){		
		String jpql = "SELECT l FROM Advertising l where l.dates > getdate() order by newid()";
		
		TypedQuery<Advertising> query = em.createQuery(jpql, Advertising.class);
		query.setMaxResults(1);
		return query.getResultList();
	}

	
	


	
}
