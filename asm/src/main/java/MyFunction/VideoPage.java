package MyFunction;

import DAO.CountryDAO;
import DAO.FavoriteDAO;
import DAO.GenreDAO;
import DAO.VideoDAO;
import entity.Favorite;
import jakarta.servlet.http.HttpServletRequest;
import view.Account;
import view.AllDAO;

public class VideoPage {
	
	//video sắp xếp theo lượt xem
	public static void orderbyViewVideo(HttpServletRequest req) {

		int quantityVideo = AllDAO.daoVideo.listVieoAllActive().size();
		Integer[] value = setAttributePage(req, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("listVideo", AllDAO.daoVideo.listVieoViewDecrease(Account.id(), max, idPage - 1));
	}

	//video sắp xếp theo ngày
	public static void orderbyDateVideo(HttpServletRequest req) {
		int quantityVideo = AllDAO.daoVideo.listVieoAllActive().size();
		Integer[] value = setAttributePage(req, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("listVideo", AllDAO.daoVideo.listVieoDateDecrease(Account.id(), max, idPage - 1));
	}
	
	
	//video theo thể loại
	public static void searchVideoByGenre(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String stringGenre = "/asm/search/genre/";
		String idGenre = uri.substring(uri.indexOf(stringGenre)  + stringGenre.length(), uri.lastIndexOf("/"));
		int quantityVideo = AllDAO.daoVideo.listVieoByGenreAll(Integer.parseInt(idGenre)).size();
		
		
		Integer[] value = setAttributePage(req, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("id", idGenre);
		req.setAttribute("genreDetail", AllDAO.daoGenre.searchGenre(Integer.parseInt(idGenre)));
		req.setAttribute("listVideo", AllDAO.daoVideo.listVieoByGenre(Account.id(), Integer.parseInt(idGenre), max, idPage-1));
	}
	
	
	//video theo country
	public static void searchVideoByCountry(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String stringCountry = "/asm/search/country/";
		String idCountry = uri.substring(uri.indexOf(stringCountry)  + stringCountry.length(), uri.lastIndexOf("/"));
		
		int quantityVideo = AllDAO.daoVideo.listVieoByCountryAll(Integer.parseInt(idCountry)).size();
		
		
		Integer[] value = setAttributePage(req, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("id", idCountry);
		req.setAttribute("nameCountry", AllDAO.daoCountry.findById(Integer.parseInt(idCountry)).getNameCountry());
		req.setAttribute("listVideo", AllDAO.daoVideo.listVieoByGenre(Account.id(), Integer.parseInt(idCountry), max, idPage-1));
	}
	
	//tìm video theo tên phim
	public static void searchVideoByName(HttpServletRequest req) {
		String uri = req.getRequestURI();
		String stringName = "/asm/search/name/";
		String nameVideo = uri.substring(uri.indexOf(stringName)  + stringName.length(), uri.lastIndexOf("/"));
		
		int quantityVideo = AllDAO.daoVideo.listVieoByNameAll(nameVideo).size();
		
		
		Integer[] value = setAttributePage(req, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("nameVideo", nameVideo);
		req.setAttribute("listVideo", AllDAO.daoVideo.listVieoByName(Account.id(), nameVideo, max, idPage-1));
	}
	
	
	//video theo tài khoản yêu thích
	public static void videoByMyFavor(HttpServletRequest req) {
		String uri = req.getRequestURI();
		
		int quantityVideo = AllDAO.daoVideo.listVieoByMyFavorAll(Account.id()).size();
		
		
		Integer[] value = setAttributePage(req, quantityVideo);
		int max = value[0]; // vị trí thứ 0 là số lượng hiện tối đa
		int idPage = value[1]; // vị trí 1 là số trang hiện tại

		req.setAttribute("listVideo", AllDAO.daoVideo.listVieoByMyFavor(Account.id(), max, idPage-1));
	}
	
	


	private static Integer[] setAttributePage(HttpServletRequest req, int quantityVideo) {
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
