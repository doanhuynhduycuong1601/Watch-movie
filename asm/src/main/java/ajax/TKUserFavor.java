package ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import DAO.FavoriteDAO;
import DAO.UserDAO;
import entity.Favorite;
import entity.User;
import entity.Video;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import view.AllDAO;

public class TKUserFavor {
	public static void search(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String idUser = req.getParameter("value");

		String result = "";
		if (idUser.isEmpty()) {
			return;
		}

//		lab6 cau 2
		User user = AllDAO.daoUser.findById(idUser);

		if (user == null) {
			result = "<h2>Không tìm thấy</h2>";
		} else {
			List<Favorite> f = user.getFavorites();
			result += setValueEmailName(user.getFullname(), idUser);
			if (f == null) {
				result += "<h2>Không có video yêu thích</h2>";
			} else {
				result += setTable(f);
			}
		}
		
		//lab 6 cau 3
//		List<Favorite> f = daoFavorite.nameQuerySelectVideoFavorByIdUser(idUser);
//		result += setTable(f);

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(result);
	}

	public static void searchVideoFavor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String title = req.getParameter("value");

		String result = "";
		if (title.isEmpty()) {
			return;
		}
		
		//cái dòng dưới đây đang sai sai
//		List<Favorite> f = daoFavor.searchVideoFavorByName(title);
		List<Video> v = AllDAO.daoFavorite.searchVideoFavorByName2(title);
		List<Favorite> f = listFavorAo(v);
		System.out.println(f.size());
		
		
//		List<Favorite> f = daoFavor.nameQuerySelectVideoFavorByVideoTitle(title);
		
		result += setTable(f);

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(result);
	}
	
	private static List<Favorite> listFavorAo(List<Video> list) {
		List<Favorite> listFavor = new ArrayList<>();
		for (Video v : list) {
			listFavor.add(new Favorite(v));
		}
		return listFavor;
	}

	private static String setTable(List<Favorite> favor) {
		return "<table class=\"row\"> " + "<tr class=\"row\"> " + "<th class=\"col-2\">ID Video</th> "
				+ "<th class=\"col-3\">Title</th> " + "<th class=\"col-3\">Views</th> "
				+ "<th class=\"col-2\">Active</th> " + "<th class=\"col-1\">Edit</th>"
				+ "<th class=\"col-1\">View</th> " + "</tr>" + addValueTd(favor) + "</table>";
	}

	private static String addValueTd(List<Favorite> favor) {
		String string = "";
		for (Favorite f : favor) {
//			if (!f.getLikes()) {
//				continue;
//			}
			string += "<tr class=\"row\"> " + "<th class=\"col-2\">" + f.getVideo().getIdVideo() + "</th> "
					+ "<th class=\"col-3\">" + f.getVideo().getTitle() + "</th> " + "<th class=\"col-3\">"
					+ f.getVideo().getViewVideo() + "</th> " + "<th class=\"col-2\">" + f.getVideo().getActive()
					+ "</th> " + "<th class=\"col-1\"><a href=\"/asm/manager/video/edit/"+f.getVideo().getIdVideo()+"\">Edit</a></th> "
					+ "<th class=\"col-1\"><a href=\"/asm/detailVideo/"+f.getVideo().getIdVideo()+"\">View</a></th> " + "</tr>";
		}
		return string;
	}

	private static String setValueEmailName(String fullname, String email) {
		return "<h2>Full name : " + fullname + "</h2> " + "<h2>Email : " + email + "</h2>";
	}
}
