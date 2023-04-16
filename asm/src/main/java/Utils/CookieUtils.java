package Utils;

import java.util.ArrayList;
import java.util.List;

import DAO.VideoDAO;
import entity.CustomerLikeVideo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import view.Account;
import view.AllDAO;

public class CookieUtils {
	// Tạo và gửi cookie về client để lưu
	public static Cookie add(String name, String value, int mls, HttpServletResponse resp) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(mls);
		cookie.setPath("/");
		resp.addCookie(cookie);
		return cookie;
	}

	// Tạo và gửi cookie về client để lưu
	public static String get(String name, HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(name)) {
					return cookie.getValue();
				}
			}
		}
		return "";
	}
	
	
	public static List<CustomerLikeVideo> list(HttpServletRequest req, int id){
		Cookie[] cookies = req.getCookies();
		List<Integer> list = new ArrayList<>();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().contains("video")) {
					list.add(Integer.valueOf(cookie.getValue()));
				}
			}
		}
		return AllDAO.daoVideo.videoDetailLikeList(Account.id(), list, id);
	}
}
