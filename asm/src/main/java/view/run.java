
package view;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import DAO.CountryDAO;
import DAO.DAO;
import DAO.DirectorDAO;
import DAO.FavoriteDAO;
import DAO.GenreDAO;
import DAO.StarDAO;
import DAO.UserDAO;
import DAO.VideoDAO;
import MyFunction.ManagerGenre;
import MyFunction.ManagerVideo;
import MyFunction.UpdateAccount;
import MyFunction.VideoDetailFunction;
import MyFunction.VideoPage;
import Utils.CookieUtils;
import Validate.FrmAdUpdate;
import Validate.FrmChangePass;
import Validate.FrmForgot;
import Validate.FrmLogin;
import Validate.FrmRegister;
import ajax.ManagerCountry;
import ajax.ManagerGenreAjax;
import ajax.TKUserFavor;
import ajax.TKVideoFavor;
import ajax.UserLike;
import entity.Country;
import entity.CustomerLikeVideo;
import entity.Favorite;
import entity.Star;
import entity.User;
import entity.Video;
import entity.VideoLinks;
import entity.VideoLinksDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig()
@WebServlet({"/home", "/manager/account/*", "/manager/video/*", "/manager/genre/*", "/manager/thong_ke/*",
		"/account/*", "/detailVideo/*", "/search/*", "/videoByDate/page/*", "/videoByView/page/*",
		"/detailVideoView/*", "/myfavorities/*"})
public class run extends HttpServlet {
	CountryDAO daoCountry = new CountryDAO();
	FavoriteDAO daoFavorite = new FavoriteDAO();
	StarDAO daoStar = new StarDAO();
	UserDAO daoUser = new UserDAO();
	VideoDAO daoVideo = new VideoDAO();
	DirectorDAO daoDirector = new DirectorDAO();
	GenreDAO daoGenre = new GenreDAO();


	ManagerVideo managerVideo = new ManagerVideo();
	private String page = "/index.jsp";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ajax
		if (req.getParameter("ajax") != null) {
			if (req.getParameter("heart") != null) {
				UserLike.like(req, Account.account.getId(), daoFavorite);
			} else if (req.getParameter("tkSearchFavor") != null) {
				// lab6 cau 3 and cau 2
				TKUserFavor.search(req, resp, daoUser, daoFavorite);
			} else if (req.getParameter("managerGenre") != null) {
				ManagerGenreAjax.genre(req, resp, daoGenre);
			} else if (req.getParameter("tkSearchVideoFavor") != null) {
				// lab6 cau 3 and cau 2
				TKUserFavor.searchVideoFavor(req, resp, daoFavorite);
			} else if (req.getParameter("managerCountryUpdate") != null) {
				ManagerCountry.countryUpdate(req, resp, daoCountry);
			} else if (req.getParameter("managerCountryCreate") != null) {
				ManagerCountry.countryCreate(req, resp, daoCountry);
			} else if (req.getParameter("tkVideoFavor") != null) {
				TKVideoFavor.videoFavor(req, resp, daoVideo);
			}
			return;
		}

		String uri = req.getRequestURI();
		if (uri.contains("/home")) {
			if(req.getParameter("logout") != null) {
				Account.account = null;
			}
			page = "/index.jsp";
		}

		// search
		if (uri.contains("/search/")) {
			if (uri.contains("genre")) {
				VideoPage.searchVideoByGenre(req, daoVideo, daoGenre);
				page = "/html/searchByGenrePage.jsp";
			}else if(uri.contains("country")) {
				VideoPage.searchVideoByCountry(req, daoVideo, daoCountry);
				page = "/html/searchByCountryPage.jsp";
			}else if(uri.contains("name")) {
				VideoPage.searchVideoByName(req, daoVideo);
				page = "/html/searchByNamePage.jsp";
			}
		}

		// video by date
		if (uri.contains("videoByDate")) {
			VideoPage.orderbyDateVideo(req, daoVideo);
			page = "/html/searchByDatePage.jsp";
		}

		
		
		// video by view
		if (uri.contains("videoByView")) {
			VideoPage.orderbyViewVideo(req, daoVideo);
			page = "/html/searchByDatePage.jsp";
		}
		
		//video by my favorite
		if(uri.contains("myfavorities")) {
			VideoPage.videoByMyFavor(req, daoVideo);
			page = "/html/videoMyFavorPage.jsp";
		}

		// don't login
		if (Account.account == null) {
			User uAo = new User();
			uAo.setId(CookieUtils.get("user", req));
			uAo.setPass(CookieUtils.get("pass", req));
			req.setAttribute("formLg", uAo);
			req.setAttribute("rememberLogin", CookieUtils.get("rememberLogin", req));
		}

		// Account
		if (uri.contains("/account/register")) {
			FrmRegister.register(req, resp, daoUser);
			page = "/index.jsp";
		} else if (uri.contains("/account/login")) {
			Account.account = FrmLogin.login(req, resp, daoUser);
			page = "/index.jsp";
		} else if (uri.contains("/account/update")) {
			if (uri.contains("/account/update/update")) {
				Account.account = UpdateAccount.updateAccount(req, daoUser);
			}
			req.setAttribute("adUpAc", Account.account);
			page = "/html/updateInforPage.jsp";
		}else if(uri.contains("/account/changepass")) {
			FrmChangePass.changepass(req, resp, daoUser);
			page = "/index.jsp";
		}else if(uri.contains("account/forgot")) {
			FrmForgot.forgot(req, resp, daoUser);
			page = "/index.jsp";
		}

		// Xem video detail
		if (uri.contains("/detailVideo/")) {
			VideoDetailFunction.videoDetail(req, daoVideo, daoFavorite);
			page = "/html/detailVideoPage.jsp";
		} else if (uri.contains("detailVideoView")) {
			VideoDetailFunction.videDetailView(req, resp, daoVideo, daoFavorite);
			page = "/html/detailVideoViewPage.jsp";
		}

		// Admin Authorize
		if (uri.contains("/manager/account")) {
			if (uri.contains("update")) {
				page = "/html/adminAccountPage.jsp";
				FrmAdUpdate.update(req, resp, daoUser);
			} else if (uri.contains("edit")) {
				String user = uri.substring(uri.lastIndexOf("/") + 1);
				req.setAttribute("adUpAc", daoUser.findById(user));
				req.setAttribute("adUpdate", "document.getElementById(\"adUpdate\").click();");
			}
			req.setAttribute("listUser", daoUser.selectAllUser());
			page = "/html/adminAccountPage.jsp";

		} else if (uri.contains("/manager/video")) {
			if (uri.contains("update")) {
				managerVideo.createVideo(req, daoVideo, daoGenre, daoStar);
			} else if (uri.contains("create")) {
				managerVideo.createVideo(req, daoVideo, daoGenre, daoStar);
			} else if (uri.contains("edit")) {
				int id = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));
				req.setAttribute("editVideo", daoVideo.videoDetail(id));
				req.setAttribute("clickEdit", "document.getElementById('editVideo').click()");
			} else if (uri.contains("reset")) {
				req.setAttribute("editVideo", new Video(0));
				req.setAttribute("clickEdit", "document.getElementById('editVideo').click()");
			} else if (uri.contains("remove")) {
				managerVideo.removeVideo(req, daoVideo);
			}
			page = "/html/adminVideoPage.jsp";
			req.setAttribute("listVideo", daoVideo.selectAll());

		} else if (uri.contains("/manager/thong_ke")) {
			page = "/html/tkPage.jsp";
			req.setAttribute("favorCount", daoFavorite.listReport());
			req.setAttribute("videoHaveFavor", daoVideo.videoHaveFavor(true));
		} else if (uri.contains("/manager/genre")) {
			if (uri.contains("updateGenre") || uri.contains("createGenre")) {
				ManagerGenre.updateGenre(req, daoGenre);
			} else if (uri.contains("deleteGenre")) {
				ManagerGenre.removeGenre(req, daoGenre);
			}
			req.setAttribute("genreDontVideo", daoGenre.selectGenreDontVideo());
			req.setAttribute("genreAdmin", daoGenre.selectAmin());
			page = "/html/adminGenrePage.jsp";
		}

		// Page
		if (page.equals("/index.jsp")) {
			String idUser = Account.id();
			List<CustomerLikeVideo> list = daoVideo.listVieoDateDecrease(idUser, 8, 0);
			List<CustomerLikeVideo> listVideoDecrease = daoVideo.listVieoViewDecrease(idUser, 8, 0);
			req.setAttribute("listVideo", list);
			req.setAttribute("listVideoViewDecrease", listVideoDecrease);
		}

		req.setAttribute("urlAjax", req.getRequestURL());
		req.setAttribute("director", daoDirector.selectAll());
		req.setAttribute("country", daoCountry.findAll());
		req.setAttribute("account", Account.account);
		req.setAttribute("star", daoStar.selectAll());
		req.setAttribute("genre", daoGenre.selectAll());
		req.getRequestDispatcher(page).forward(req, resp);
	}
	
	
//	private List<VideoLinksDetail> list(List<VideoLinks> list){
//		List<VideoLinksDetail> ao = new ArrayList<>();
//		int num = 1;
//		for (VideoLinks l : list) {
//			ao.add(new VideoLinksDetail(num, l.getUrlVideo()));
//			num++;
//		}
//		return ao;
//	}
	
	
}
