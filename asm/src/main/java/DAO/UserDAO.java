package DAO;

import java.util.List;
import java.util.Map;

import Utils.Jpa;
import entity.CustomerLikeVideo;
import entity.Favorite;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.metamodel.Type;

public class UserDAO extends DAO<User, String> {
	private EntityManager em = Jpa.getEntityManager();
	
	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}

	@Override
	public void create(User entity) {
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
	public void update(User entity) {
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
	public void remove(User entity) {
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
	public User findById(String id) {
		return em.find(User.class, id);
	}
	
	
	public List<User> selectAll(){
		String jpql = "select us from User us left join us.favorites f on us.id = f.user.id";
		TypedQuery<User> query = em.createQuery(jpql, User.class);
		return query.getResultList();
	}

	
	public List<User> selectAllUser(){
		String jpql = "select us from User us";
		TypedQuery<User> query = em.createQuery(jpql, User.class);
		return query.getResultList();
	}
	
	public List<User> selectByFavorVideo(String title){
		String jpql = "select f.user from Favorite f where f.video.title LIKE :vTitle";
		TypedQuery<User> query = em.createQuery(jpql, User.class);
		query.setParameter("vTitle", "%"+title+"%");
		return query.getResultList();
	}
	
	
	//quên mật khẩu
	public User forgotPass(String id, String email) {
		String jpql = "SELECT us FROM User us where us.id = :idU and us.email = :email";
		TypedQuery<User> query = em.createQuery(jpql, User.class);
		query.setParameter("idU", id);
		query.setParameter("email", email);
		return query.getSingleResult();
	}
	


	
}
