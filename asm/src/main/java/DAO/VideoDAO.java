package DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Utils.Jpa;
import entity.CustomerLikeVideo;
import entity.Favorite;
import entity.Video;
import entity.VideoLinksDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;

public class VideoDAO extends DAO<Video, Integer> {
private EntityManager em = Jpa.getEntityManager();
	
	@Override
	protected void finalize() throws Throwable {
		em.close();
		super.finalize();
	}

	@Override
	public void create(Video entity) {
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
	public void update(Video entity) {
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
	public void remove(Video entity) {
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
	public Video findById(Integer id) {
		return em.find(Video.class, id);
	}
	
	public List<Video> listVideo(){
		String jpql = "SELECT v FROM Video v";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		query.setMaxResults(8);
		return query.getResultList();
	}
	
//	public List<Video> listVideo(String idUser){
////		String jpql = "select v "
////				+ "from Video v left join Favorite f on v.idVideo = f.video.idVideo "
////				+ "and f.user.id = :idU";
//		
////		String jpql = "SELECT v FROM Video v LEFT JOIN v.favorites f ON f.user.id = :idU";
//		
//		String jpql = "SELECT v FROM Video v LEFT JOIN v.favorites f WHERE f.user.id = :idU";
//		
//		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
//		query.setParameter("idU", idUser);
//		return query.getResultList();
//	}
	
//	public VideoDetail videoDetail(int idVideo){
//		String jpql = "SELECT v FROM VideoDetail v where v.id = :idV";
//		TypedQuery<VideoDetail> query = em.createQuery(jpql, VideoDetail.class);
//		query.setParameter("idV", idVideo);
//		return query.getSingleResult();
//	}
	
	
	//detail Video icon like
	public CustomerLikeVideo videoDetailLike(String idUser, int idVideo){		
		String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,f.likes) "
				+ "FROM Video v left join Favorite f on v.idVideo = f.video.idVideo "
				+ "and f.user.id = :idU where v.idVideo = :idV";
		
		TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
		query.setParameter("idU", idUser);
		query.setParameter("idV", idVideo);
		return query.getSingleResult();
	}
	
	//list video lấy từ cookie để chạy trang video detail
	public List<CustomerLikeVideo> videoDetailLikeList(String idUser, List<Integer> idVideoList, int idVideo){		
		String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,f.likes) "
				+ "FROM Video v left join Favorite f on v.idVideo = f.video.idVideo "
				+ "and f.user.id = :idU where v.idVideo in (:idVList) and v.active = 1 and "
				+ "v.idVideo not in (:idV)";
		
		TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
		query.setParameter("idU", idUser);
		query.setParameter("idVList", idVideoList);
		query.setParameter("idV", idVideo);
		return query.getResultList();
	}
	
	
	//list video lấy từ những video còn lại sau khi đã lấy từ cookie để chạy trang video detail
	public List<CustomerLikeVideo> videoDetailLikeListLast(String idUser, List<Integer> idVideoList, int max){		
		String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,f.likes) "
				+ "FROM Video v left join Favorite f on v.idVideo = f.video.idVideo "
				+ "and f.user.id = :idU where v.idVideo not in (:idVList) and v.active = 1 "
				+ "order by newid()";
		
		TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
		query.setMaxResults(max);
		query.setParameter("idU", idUser);
		query.setParameter("idVList", idVideoList);
		return query.getResultList();
	}
	
	
	//list video sắp xếp theo ngày giảm dần
	public List<CustomerLikeVideo> listVieoDateDecrease(String idUser, int max, int start){		
		String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,f.likes) "
				+ "FROM Video v left join Favorite f on v.idVideo = f.video.idVideo and f.user.id = :idU "
				+ "where v.active = 1 "
				+ "ORDER BY v.dateUpload desc";
		
		TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
		query.setFirstResult(start * max);
		query.setMaxResults(max);
		query.setParameter("idU", idUser);
		return query.getResultList();
	}
	
	
	//list video sắp xếp theo lượt xem giảm dần
	public List<CustomerLikeVideo> listVieoViewDecrease(String idUser, int max, int start){		
		String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,f.likes) "
				+ "FROM Video v left join Favorite f on v.idVideo = f.video.idVideo and f.user.id = :idU "
				+ "where v.active = 1 "
				+ "ORDER BY v.viewVideo DESC";
		
		TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
		query.setParameter("idU", idUser);
		query.setFirstResult(start * max);
		query.setMaxResults(max);
		return query.getResultList();
	}
	
	//list video tất cả video đang hoạt động (lấy ra số lượng video)
		public List<CustomerLikeVideo> listVieoAllActive(){		
			String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,false) "
					+ "FROM Video v where v.active = 1 ";
			TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
			return query.getResultList();
		}
	
	//list video theo thể loại
	public List<CustomerLikeVideo> listVieoByGenre(String idUser, int idGenre, int max, int start){		
		String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,f.likes) "
				+ "FROM Video v left join Favorite f on v.idVideo = f.video.idVideo and f.user.id = :idU "
				+ "join v.genres g where g.id = :idG "
				+ "and v.active = 1";
		
		TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
		query.setParameter("idU", idUser);
		query.setParameter("idG", idGenre);
		query.setFirstResult(start * max);
		query.setMaxResults(max);
		return query.getResultList();
	}
	
	//list video theo thể loại All
		public List<CustomerLikeVideo> listVieoByGenreAll(int idGenre){		
			String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,false) "
					+ "FROM Video v join v.genres g where g.id = :idG "
					+ "and v.active = 1";
			TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
			query.setParameter("idG", idGenre);
			return query.getResultList();
		}
		
		// list video theo quốc gia
		public List<CustomerLikeVideo> listVieoByCountry(String idUser, int idCountry, int max, int start) {
			String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,f.likes) "
					+ "FROM Video v left join Favorite f on v.idVideo = f.video.idVideo and f.user.id = :idU "
					+ "join v.country c where c.idCountry = :idC " + "and v.active = 1";

			TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
			query.setParameter("idU", idUser);
			query.setParameter("idC", idCountry);
			query.setFirstResult(start * max);
			query.setMaxResults(max);
			return query.getResultList();
		}

		// list video theo quốc gia All
		public List<CustomerLikeVideo> listVieoByCountryAll(int idCountry) {
			String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,false) "
					+ "FROM Video v join v.country c where c.idCountry = :idC " + "and v.active = 1";
			TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
			query.setParameter("idC", idCountry);
			return query.getResultList();
		}
		
		
		// list video theo tên phim
		public List<CustomerLikeVideo> listVieoByName(String idUser, String nameVideo, int max, int start) {
			String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,f.likes) "
						+ "FROM Video v left join Favorite f on v.idVideo = f.video.idVideo and f.user.id = :idU "
						+ "where v.title LIKE :vName and v.active = 1";
			TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
			query.setParameter("idU", idUser);
			query.setParameter("vName", "%"+nameVideo+"%");
			query.setFirstResult(start * max);
			query.setMaxResults(max);
			return query.getResultList();
		}

				// list video theo Name video All
		public List<CustomerLikeVideo> listVieoByNameAll(String nameVideo) {
			String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,false) "
						+ "FROM Video v where v.title LIKE :vName and v.active = 1";
			TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
			query.setParameter("vName", "%"+nameVideo+"%");
			return query.getResultList();
		}
		
		
		// list video theo tài khoản yêu thích
		public List<CustomerLikeVideo> listVieoByMyFavor(String idUser, int max, int start) {
			String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,f.likes) "
					+ "FROM Favorite f join f.video v on f.video.idVideo = v.idVideo "
					+ "join f.user us on f.user.id = us.id "
					+ "where us.id = :idU and v.active = 1 and f.likes = 1";
			TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
			query.setParameter("idU", idUser);
			query.setFirstResult(start * max);
			query.setMaxResults(max);
		return query.getResultList();
				}

		// list video theo Name video All
		public List<CustomerLikeVideo> listVieoByMyFavorAll(String idUser) {
			String jpql = "SELECT new CustomerLikeVideo(v.idVideo,v.title,v.poster,v.viewVideo,false) "
						+ "FROM Favorite f join f.video v on f.video.idVideo = v.idVideo "
						+ "join f.user us on f.user.id = us.id "
						+ "where us.id = :idU and v.active = 1 and f.likes = 1";
			TypedQuery<CustomerLikeVideo> query = em.createQuery(jpql, CustomerLikeVideo.class);
			query.setParameter("idU", idUser);
			return query.getResultList();
		}
	
//	public List<Video> listVideoByGenre(int idGenre){
//		String jpql = "SELECT v FROM Video v join v.genres g  where g.id = :idG";
//		
//		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
//		query.setParameter("idG", idGenre);
//		return query.getResultList();
//	}
	
	
	public List<Video> selectAll(){
//		String jpql = "SELECT DISTINCT v FROM Video v LEFT JOIN v.genres "
//				+ "LEFT JOIN v.stars";
		String jpql = "SELECT  v FROM Video v ";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		return query.getResultList();
	}
	
	
	//lấy ra video detail
	public Video videoDetail(int idVideo) {
		String jpql = "SELECT v FROM Video v where v.id = :idV";
		
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		query.setParameter("idV", idVideo);
		return query.getSingleResult();
	}
	
	public List<Video> videoHaveFavor(boolean favor){
		String jpql = "SELECT  v FROM Video v where v.favorites IS EMPTY";
		if(favor) {
			jpql = "SELECT  v FROM Video v where v.favorites IS NOT EMPTY";
		}
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		return query.getResultList();
	}
	
	
	
	/*@Name Query*/ //lab6 cau 3 _ c
	public List<Video> nameQueryVideoLikeDate(String min, String max) throws ParseException{
		TypedQuery<Video> query = em.createNamedQuery("Video.videoLikeDate", Video.class);
		query.setParameter("min", new SimpleDateFormat("yyyy-MM-dd").parse(min));
		query.setParameter("max", new SimpleDateFormat("yyyy-MM-dd").parse(max));
		return query.getResultList();
	}
	
	public List<Video> nameQueryVideoLikeByMonths(List<Integer> list){
		TypedQuery<Video> query = em.createNamedQuery("Video.videoLikeByMonths", Video.class);
		query.setParameter("months", list);
		return query.getResultList();
	}
	
	public List<Video> cau4A(){
		TypedQuery<Video> query = em.createNamedQuery("Video.random10", Video.class);
		return query.getResultList();
	}
	
	
	
	//update view video
	public void updateViewVideo(int id) {
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("Video.updateViewVideo");
		query.setParameter("idVideo", id);
		query.execute();
	}
}
