package MyFunction;


import java.util.ArrayList;
import java.util.List;

import DAO.FavoriteDAO;
import DAO.VideoDAO;
import Utils.CookieUtils;
import entity.Advertising;
import entity.CustomerLikeVideo;
import entity.Favorite;
import entity.Video;
import entity.VideoLinks;
import entity.VideoLinksDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import view.Account;
import view.AllDAO;

public class VideoDetailFunction {
	public static void videoDetail(HttpServletRequest req) {
		String uri = req.getRequestURI();
		int id = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));
		String idUser = Account.id();
		req.setAttribute("vDetail", AllDAO.daoVideo.videoDetail(id));
		Favorite f;
		try {
			f = AllDAO.daoFavorite.findIdUserIdVideo(idUser, id);
		} catch (Exception e) {
			f = null;
		}
		
		
		List<Advertising> a = AllDAO.daoAdver.adver();
		if(a.size() > 0 ){
			req.setAttribute("adver", a.get(0));
		}
		
		req.setAttribute("videoListDetail", listVideo(req, id));
		req.setAttribute("like", f);
	}
	
	public static void videDetailView(HttpServletRequest req, HttpServletResponse resp) {
		String uri = req.getRequestURI();
		int idVideo = Integer.valueOf(uri.substring(uri.lastIndexOf("/") + 1));
		Video video = AllDAO.daoVideo.videoDetail(idVideo);
		
		//kiểm tra video còn hoạt động hay không
		int time = video.getActive() ? 60*60*60 : 0;
		CookieUtils.add("video"+idVideo, String.valueOf(idVideo), time, resp);
		
		//thực hiện like icon time
		Favorite f;
		try {
			String idUser = Account.id();
			f = AllDAO.daoFavorite.findIdUserIdVideo(idUser, idVideo);
		} catch (Exception e) {
			f = null;
		}
		req.setAttribute("vDetail", video);
		
		//lấy ra link video 
		List<VideoLinksDetail> link = list(video.getLinks());
		req.setAttribute("links", link);
		
		//nếu link mà = 0 thì cho đại mọt đường link để thông báo lỗi
		if(link.size()> 0) {
			req.setAttribute("linkOne", link.get(0).getUrlVideo());
		}else {
			req.setAttribute("linkOne", "asdfdsafsdafdsafdsafdsafdsafdsafasdf");
		}
		
//		AllDAO.daoVideo.updateViewVideo(idVideo);
		//tang view
		int times = video.alltime();
		System.out.println(times);
		req.setAttribute("tangluotxem", "setTimeout(myFunction, "+times*1000+");");
		
		List<Advertising> a = AllDAO.daoAdver.adver();
		if(a.size() > 0 ){
			req.setAttribute("adver", a.get(0));
		}
		
		req.setAttribute("videoListDetail", listVideo(req, idVideo));
		req.setAttribute("like", f);
	}
	
	private static List<VideoLinksDetail> list(List<VideoLinks> list){
		List<VideoLinksDetail> ao = new ArrayList<>();
		int num = 1;
		for (VideoLinks l : list) {
			ao.add(new VideoLinksDetail(num, l.getUrlVideo()));
			num++;
		}
		return ao;
	}
	
	
	private static List<CustomerLikeVideo> listVideo(HttpServletRequest req, int id){
		//lấy ra danh sách video từ cookie
		List<CustomerLikeVideo> listCookie = CookieUtils.list(req, id);
		//số lượng video thể hiện ra
		int maxVideoShow = 5;
		//lấy ra danh sách id video để xuất ra các video còn lại. nếu List video từ cookie bé hơn max
		List<Integer> listId = listVideoConLai(listCookie);
		//add video đang xem
		listId.add(id);
		
		//số lượng video còn lại
		int quantityVideoConLai = maxVideoShow - listId.size();
		if(quantityVideoConLai < 0) {
			quantityVideoConLai = 0;
		}
		//lấy ra video còn lại
		List<CustomerLikeVideo> listConLai = AllDAO.daoVideo.videoDetailLikeListLast(Account.id(), listId, quantityVideoConLai);
		listCookie.addAll(listConLai);
		return listCookie;
	}
	
	private static List<Integer> listVideoConLai(List<CustomerLikeVideo> listCookie){
		List<Integer> list = new ArrayList<>();
		for (CustomerLikeVideo c : listCookie) {
			list.add(c.getIdVideo());
		}
		return list;
	}
}
