package MyFunction;

import DAO.CountryDAO;
import DAO.FavoriteDAO;
import DAO.GenreDAO;
import DAO.VideoDAO;
import entity.Favorite;
import jakarta.servlet.http.HttpServletRequest;
import view.Account;

public class VideoPage {
	
	//video sắp xếp theo lượt xem
	public static void orderbyViewVideo(HttpServletRequest req, VideoDAO daoVideo) {

		int quantityVideo = daoVideo.listVieoAllActive().size();
		Integer[] value = setAttributePage(req, daoVideo, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("listVideo", daoVideo.listVieoViewDecrease(Account.id(), max, idPage - 1));
	}

	//video sắp xếp theo ngày
	public static void orderbyDateVideo(HttpServletRequest req, VideoDAO daoVideo) {
		int quantityVideo = daoVideo.listVieoAllActive().size();
		Integer[] value = setAttributePage(req, daoVideo, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("listVideo", daoVideo.listVieoDateDecrease(Account.id(), max, idPage - 1));
	}
	
	
	//video theo thể loại
	public static void searchVideoByGenre(HttpServletRequest req, VideoDAO daoVideo, GenreDAO daoGenre) {
		String uri = req.getRequestURI();
		String stringGenre = "/asm/search/genre/";
		String idGenre = uri.substring(uri.indexOf(stringGenre)  + stringGenre.length(), uri.lastIndexOf("/"));
		int quantityVideo = daoVideo.listVieoByGenreAll(Integer.parseInt(idGenre)).size();
		
		
		Integer[] value = setAttributePage(req, daoVideo, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("id", idGenre);
		req.setAttribute("genreDetail", daoGenre.searchGenre(Integer.parseInt(idGenre)));
		req.setAttribute("listVideo", daoVideo.listVieoByGenre(Account.id(), Integer.parseInt(idGenre), max, idPage-1));
	}
	
	
	//video theo country
	public static void searchVideoByCountry(HttpServletRequest req, VideoDAO daoVideo, CountryDAO daoCountry) {
		String uri = req.getRequestURI();
		String stringCountry = "/asm/search/country/";
		String idCountry = uri.substring(uri.indexOf(stringCountry)  + stringCountry.length(), uri.lastIndexOf("/"));
		
		int quantityVideo = daoVideo.listVieoByCountryAll(Integer.parseInt(idCountry)).size();
		
		
		Integer[] value = setAttributePage(req, daoVideo, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("id", idCountry);
		req.setAttribute("nameCountry", daoCountry.findById(Integer.parseInt(idCountry)).getNameCountry());
		req.setAttribute("listVideo", daoVideo.listVieoByGenre(Account.id(), Integer.parseInt(idCountry), max, idPage-1));
	}
	
	//tìm video theo tên phim
	public static void searchVideoByName(HttpServletRequest req, VideoDAO daoVideo) {
		String uri = req.getRequestURI();
		String stringName = "/asm/search/name/";
		String nameVideo = uri.substring(uri.indexOf(stringName)  + stringName.length(), uri.lastIndexOf("/"));
		
		int quantityVideo = daoVideo.listVieoByNameAll(nameVideo).size();
		
		
		Integer[] value = setAttributePage(req, daoVideo, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("nameVideo", nameVideo);
		req.setAttribute("listVideo", daoVideo.listVieoByName(Account.id(), nameVideo, max, idPage-1));
	}
	
	
	//video theo tài khoản yêu thích
	public static void videoByMyFavor(HttpServletRequest req, VideoDAO daoVideo) {
		String uri = req.getRequestURI();
		
		int quantityVideo = daoVideo.listVieoByMyFavorAll(Account.id()).size();
		
		
		Integer[] value = setAttributePage(req, daoVideo, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("listVideo", daoVideo.listVieoByMyFavor(Account.id(), max, idPage-1));
	}
	
	


	private static Integer[] setAttributePage(HttpServletRequest req, VideoDAO daoVideo, int quantityVideo) {
		String uri = req.getRequestURI();
		int idPage = Integer.valueOf(uri.substring(uri.lastIndexOf("/") + 1));
		if (idPage < 1) {
			idPage = 1;
		}
		int max = 8;
		int lastPage = quantityVideo / max;
		if (quantityVideo % max != 0) {
			lastPage++;
		}
		req.setAttribute("pageCurrent", idPage);
		req.setAttribute("pageLast", lastPage);
		return new Integer[] { max, idPage };
	}
}
