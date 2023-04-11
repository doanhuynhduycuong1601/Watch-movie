package DAO;

import java.util.List;

import Utils.Jpa;
import entity.Favorite;
import entity.Report;
import entity.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class FavoriteDAO extends DAO<Favorite, Integer> {
	private EntityManager em = Jpa.getEntityManager();

	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}

	@Override
	public void create(Favorite entity) {
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
	public void update(Favorite entity) {
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
	public void remove(Favorite entity) {
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
	public Favorite findById(Integer id) {
		return em.find(Favorite.class, id);
	}
	
	public Favorite findIdUserIdVideo(String idUser, int idVideo) {
		String jpql = "SELECT f FROM Favorite f where f.user.id = :idU and f.video.idVideo = :idV";
		TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
		query.setParameter("idU", idUser);
		query.setParameter("idV", idVideo);
		return query.getSingleResult();
	}
	
	//nó đang sai sai 2b
	public List<Favorite> searchVideoFavorByName(String titleVideo){
		String jpql = "SELECT DISTINCT f FROM Favorite f where f.video.title LIKE :titleV";
		TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
		query.setParameter("titleV", "%"+titleVideo+"%");
		return query.getResultList();
	}
	
	//nó đang sai sai 2b
		public List<Video> searchVideoFavorByName2(String titleVideo){
			String jpql = "SELECT DISTINCT f.video FROM Favorite f where f.video.title LIKE :titleV";
			TypedQuery<Video> query = em.createQuery(jpql, Video.class);
			query.setParameter("titleV", "%"+titleVideo+"%");
			return query.getResultList();
		}
	
	
	public List<Report> listReport(){
		String jpql = "SELECT new Report(f.video.title,count(f),max(f.likeDate),min(f.likeDate)) FROM Favorite f "
				+ "where f.likes = 1 "
				+ "group by f.video.idVideo,f.video.title";
		TypedQuery<Report> query = em.createQuery(jpql, Report.class);
		return query.getResultList();
	}
	
	
	//list user like video have date like (chuc nang asm)
	public List<Favorite> listUserLikeVideoByNameVideo(String nameVideo){
		String jpql = "SELECT f FROM Favorite f join f.video v on f.video.idVideo = v.idVideo "
				+ "join f.user us on f.user.id = us.id "
				+ "where v.title like :vTitle";
		TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
		query.setParameter("vTitle", "%"+nameVideo+"%");
		return query.getResultList();
	}
	
	
	
	
	
	/*@Name Query*/
	public List<Favorite> nameQuerySelectVideoFavorByVideoTitle(String keyword){
		TypedQuery<Favorite> query = em.createNamedQuery("Favor.findByKeyword", Favorite.class);
		query.setParameter("titleV", "%"+keyword+"%");
		return query.getResultList();
	}
	
	public List<Favorite> nameQuerySelectVideoFavorByIdUser(String idUser){
		TypedQuery<Favorite> query = em.createNamedQuery("Favor.findByUser", Favorite.class);
		query.setParameter("idU", idUser);
		return query.getResultList();
	}
	
}
