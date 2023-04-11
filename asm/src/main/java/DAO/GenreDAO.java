package DAO;

import java.util.List;

import Utils.Jpa;
import entity.Director;
import entity.Genre;
import entity.GenreShow;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class GenreDAO extends DAO<Genre, Integer> {
	private EntityManager em = Jpa.getEntityManager();

	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}
	@Override
	public void create(Genre entity) {
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
	public void update(Genre entity) {
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
	public void remove(Genre entity) {
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
	public Genre findById(Integer id) {
		return em.find(Genre.class, id);
	}
	
	public Genre getReference(int id) {
		return em.getReference(Genre.class, id);
	}
	
	public List<Genre> selectAll() {
		String jpql = "SELECT g FROM Genre g";
		TypedQuery<Genre> query = em.createQuery(jpql, Genre.class);

		return query.getResultList();
	}
	
	public List<Genre> selectGenreDontVideo(){
		String jpql = "SELECT g FROM Genre g where g.videos IS EMPTY";
		TypedQuery<Genre> query = em.createQuery(jpql, Genre.class);
		return query.getResultList();
	}
	
	public List<GenreShow> selectAmin(){
		String jpql = "SELECT new GenreShow(g.id,g.names,g.img,g.descriptions,sum(v.viewVideo)) FROM Genre g join g.videos v "
				+ "group by g.id,g.names,g.img,g.descriptions";
		TypedQuery<GenreShow> query = em.createQuery(jpql, GenreShow.class);
		return query.getResultList();
	}
	
	public Genre searchGenre(int idGenre) {
		String jpql = "SELECT g FROM Genre g where g.id = :idG";
		TypedQuery<Genre> query = em.createQuery(jpql, Genre.class);
		query.setParameter("idG", idGenre);
		return query.getSingleResult();
	}

}
