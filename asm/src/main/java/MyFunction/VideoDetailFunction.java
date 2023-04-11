package MyFunction;


import java.util.ArrayList;
import java.util.List;

import DAO.FavoriteDAO;
import DAO.VideoDAO;
import Utils.CookieUtils;
import entity.CustomerLikeVideo;
import entity.Favorite;
import entity.Video;
import entity.VideoLinks;
import entity.VideoLinksDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import view.Account;

public class VideoDetailFunction {
	public static void videoDetail(HttpServletRequest req, VideoDAO daoVideo, FavoriteDAO daoFavorite) {
		String uri = req.getRequestURI();
		int id = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));
		String idUser = Account.id();
		req.setAttribute("vDetail", daoVideo.videoDetail(id));
		Favorite f;
		try {
			f = daoFavorite.findIdUserIdVideo(idUser, id);
		} catch (Exception e) {
			f = null;
		}
		
		req.setAttribute("videoListDetail", listVideo(req, daoVideo,id));
		req.setAttribute("like", f);
	}
	
	public static void videDetailView(HttpServletRequest req, HttpServletResponse resp, VideoDAO daoVideo, FavoriteDAO daoFavorite) {
		String uri = req.getRequestURI();
		int idVideo = Integer.valueOf(uri.substring(uri.lastIndexOf("/") + 1));
		Video video = daoVideo.videoDetail(idVideo);
		
		//kiểm tra video còn hoạt động hay không
		int time = video.getActive() ? 60*60*60 : 0;
		CookieUtils.add("video"+idVideo, String.valueOf(idVideo), time, resp);
		
		//thực hiện like icon time
		Favorite f;
		try {
			String idUser = Account.id();
			f = daoFavorite.findIdUserIdVideo(idUser, idVideo);
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
		
		daoVideo.updateViewVideo(idVideo);
		
		req.setAttribute("videoListDetail", listVideo(req, daoVideo,idVideo));
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
	
	
	private static List<CustomerLikeVideo> listVideo(HttpServletRequest req, VideoDAO daoVideo, int id){
		//lấy ra danh sách video từ cookie
		List<CustomerLikeVideo> listCookie = CookieUtils.list(req, daoVideo,id);
		//số lượng video thể hiện ra
		int maxVideoShow = 8;
		//lấy ra danh sách id video để xuất ra các video còn lại. nếu List video từ cookie bé hơn max
		List<Integer> listId = listVideoConLai(listCookie);
		//add video đang xem
		listId.add(id);
		
		//số lượng video còn lại
		int quantityVideoConLai = 8 - listId.size();
		
		//lấy ra video còn lại
		List<CustomerLikeVideo> listConLai = daoVideo.videoDetailLikeListLast(Account.id(), listId, quantityVideoConLai);
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
