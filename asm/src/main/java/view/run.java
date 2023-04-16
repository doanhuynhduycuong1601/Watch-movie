
package view;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import MyFunction.ManagerAdvertising;
import MyFunction.ManagerGenre;
import MyFunction.ManagerVideo;
import MyFunction.UpdateAccount;
import MyFunction.VideoDetailFunction;
import MyFunction.VideoPage;
import Utils.RRSharer;
import Utils.XCookie;
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
import entity.Advertising;
import entity.Country;
import entity.CustomerLikeVideo;
import entity.Genre;
import entity.User;
import entity.Video;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig()
@WebServlet({ "/home", "/manager/account/*", "/manager/video/*", "/manager/genre/*", "/manager/thong_ke/*",
		"/account/*", "/detailVideo/*", "/search/*", "/videoByDate/page/*", "/videoByView/page/*", "/detailVideoView/*",
		"/myfavorities/*","/manager/advertising/*"})
public class run extends HttpServlet {

	ManagerVideo managerVideo = new ManagerVideo();
	private String page = "/html/include/video.jsp";
	List<Country> listCountry = AllDAO.daoCountry.findAll();
	List<Genre> listGenre = AllDAO.daoGenre.selectAll();

	@Override
	public void init() throws ServletException {
		reStart();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ajax
		RRSharer.add(req, resp);

		if (req.getParameter("ajax") != null) {
			if (req.getParameter("heart") != null) {
				UserLike.like(req, Account.account.getId());
			} else if (req.getParameter("tkSearchFavor") != null) {
				// lab6 cau 3 and cau 2
				TKUserFavor.search(req, resp);
			} else if (req.getParameter("managerGenre") != null) {
				ManagerGenreAjax.genre(req, resp);
			} else if (req.getParameter("tkSearchVideoFavor") != null) {
				// lab6 cau 3 and cau 2
				TKUserFavor.searchVideoFavor(req, resp);
			} else if (req.getParameter("managerCountryUpdate") != null) {
				ManagerCountry.countryUpdate(req, resp);
			} else if (req.getParameter("managerCountryCreate") != null) {
				ManagerCountry.countryCreate(req, resp);
			} else if (req.getParameter("tkVideoFavor") != null) {
				TKVideoFavor.videoFavor(req, resp);
			} else if (req.getParameter("tangtang") != null) {
				String id = req.getParameter("id");
				AllDAO.daoVideo.updateViewVideo(Integer.valueOf(id));
			}
			return;
		}

		String uri = req.getRequestURI();
		if (uri.contains("/home")) {
			if (req.getParameter("logout") != null) {
				Account.account = null;
			}
			page = "/html/include/video.jsp";
		}

		// search
		if (uri.contains("/search/")) {
			if (uri.contains("genre")) {
				VideoPage.searchVideoByGenre(req);
				page = "/html/include/searchByGenre.jsp";
			} else if (uri.contains("country")) {
				VideoPage.searchVideoByCountry(req);
				page = "/html/include/searchByCountry.jsp";
			} else if (uri.contains("name")) {
				VideoPage.searchVideoByName(req);
				page = "/html/include/searchByName.jsp";
			}
		}

		// video by date
		if (uri.contains("videoByDate")) {
			VideoPage.orderbyDateVideo(req);
			page = "/html/include/searchByDate.jsp";
		}

		// video by view
		if (uri.contains("videoByView")) {
			VideoPage.orderbyViewVideo(req);
			page = "/html/include/searchByDate.jsp";
		}

		// video by my favorite
		if (uri.contains("myfavorities")) {
			VideoPage.videoByMyFavor(req);
			page = "/html/include/videoMyFavor.jsp";
		}

		// don't login
		if (Account.account == null) {
			User uAo = new User();
			uAo.setId(XCookie.get("user"));
			uAo.setPass(XCookie.get("pass"));
			req.setAttribute("formLg", uAo);
			req.setAttribute("rememberLogin", XCookie.get("rememberLogin"));
		}

		// Account
		if (uri.contains("/account/register")) {
			FrmRegister.register(req, resp);
			page = "/html/include/video.jsp";
		} else if (uri.contains("/account/login")) {
			if (!req.getMethod().equals("GET")) {
				Account.account = FrmLogin.login(req, resp);
				page = "/html/include/video.jsp";
			}
		} else if (uri.contains("/account/update")) {
			if (uri.contains("/account/update/update")) {
				Account.account = UpdateAccount.updateAccount(req);
			}
			req.setAttribute("adUpAc", Account.account);
			page = "/html/include/updateInfor.jsp";
		} else if (uri.contains("/account/changepass")) {
			FrmChangePass.changepass(req, resp);
			page = "/html/include/video.jsp";
		} else if (uri.contains("account/forgot")) {
			FrmForgot.forgot(req, resp);
			page = "/html/include/video.jsp";
		}

		// Xem video detail
		if (uri.contains("/detailVideo/")) {
			VideoDetailFunction.videoDetail(req);
			page = "/html/include/detailVideo.jsp";
		} else if (uri.contains("detailVideoView")) {
			VideoDetailFunction.videDetailView(req, resp);

			page = "/html/include/detailVideoView.jsp";
		}

		// Admin Authorize
		if (uri.contains("/manager/account")) {
			if (uri.contains("update")) {
				page = "/html/include/adminAccount.jsp";
				FrmAdUpdate.update(req, resp);
			} else if (uri.contains("edit")) {
				String user = uri.substring(uri.lastIndexOf("/") + 1);
				req.setAttribute("adUpAc", AllDAO.daoUser.findById(user));
				req.setAttribute("adUpdate", "document.getElementById(\"adUpdate\").click();");
			}
			req.setAttribute("listUser", AllDAO.daoUser.selectAllUser());
			page = "/html/include/adminAccount.jsp";

		} else if (uri.contains("/manager/video")) {
			if (uri.contains("update")) {
				managerVideo.createVideo(req);
			} else if (uri.contains("create")) {
				managerVideo.createVideo(req);
			} else if (uri.contains("edit")) {
				int id = Integer.parseInt(uri.substring(uri.lastIndexOf("/") + 1));
				req.getSession().setAttribute("managerPageVideo", 1);
				Video v = AllDAO.daoVideo.videoDetail(id);
				req.setAttribute("editVideo", v);
				req.setAttribute("editVideoGenre", managerVideo.edit(v.getGenres()));
				;
				req.setAttribute("clickEdit", "document.getElementById('editVideo').click()");
			} else if (uri.contains("reset")) {
				req.setAttribute("editVideo", new Video(0,"chonanh.png"));
				req.setAttribute("clickEdit", "document.getElementById('editVideo').click()");
			} else if (uri.contains("remove")) {
				managerVideo.removeVideo(req);
			} else if (uri.contains("page")) {
				int numPage = Integer.valueOf(uri.substring(uri.lastIndexOf("/")+1));
				req.getSession().setAttribute("managerPageVideo", numPage);
			} else if(uri.contains("view")) {
				req.getSession().setAttribute("managerPageVideo", 1);
				req.setAttribute("editVideo", new Video(0,"chonanh.png"));
			}
			page = "/html/include/adminVideo.jsp";
			int num = numSession("managerPageVideo"); 
			int max = 8;
			req.setAttribute("listVideo", AllDAO.daoVideo.selectAll(num - 1, max));
			
			//
			int quantityVideo = AllDAO.daoVideo.selectAllPage().size();
			int pageLast = quantityVideo / 8;
			if(quantityVideo % max != 0) {
				pageLast++;
			}
			req.setAttribute("pageLast", pageLast);
			req.setAttribute("pageCurrent", num);

		} else if (uri.contains("/manager/thong_ke")) {
			page = "/html/include/tk.jsp";
			req.setAttribute("favorCount", AllDAO.daoFavorite.listReport());
			req.setAttribute("videoHaveFavor", AllDAO.daoVideo.videoHaveFavor(true));
		} else if (uri.contains("/manager/genre")) {
			if (uri.contains("updateGenre") || uri.contains("createGenre")) {
				ManagerGenre.updateGenre(req);
			} else if (uri.contains("deleteGenre")) {
				ManagerGenre.removeGenre(req);
			}
			req.setAttribute("genreDontVideo", AllDAO.daoGenre.selectGenreDontVideo());
			req.setAttribute("genreAdmin", AllDAO.daoGenre.selectAmin());
			page = "/html/include/adminGenre.jsp";
		}else if(uri.contains("/manager/advertising")) {
			if(uri.contains("create") || uri.contains("update")) {
				ManagerAdvertising.add(req);
				req.getSession().setAttribute("listAdver", AllDAO.daoAdver.selectAll(""));
			}else if(uri.contains("view")) {
				req.getSession().setAttribute("listAdver", AllDAO.daoAdver.selectAll(""));
				req.setAttribute("editLink", new Advertising(0,"chonanh.png"));
			}else if(uri.contains("edit")) {
				int id = Integer.valueOf(uri.substring(uri.lastIndexOf("/")+1));
				req.setAttribute("editLink", AllDAO.daoAdver.findById(id));
			}else if(uri.contains("reset")) {
				req.setAttribute("editLink", new Advertising(0,"chonanh.png"));
			}else if(uri.contains("remove")) {
				ManagerAdvertising.remove(req);
				req.getSession().setAttribute("listAdver", AllDAO.daoAdver.selectAll(""));
			}
			page = "/html/include/adminAdvertising.jsp";
		}

		// Page
		if (page.equals("/html/include/video.jsp")) {
			String idUser = Account.id();
			List<CustomerLikeVideo> list = AllDAO.daoVideo.listVieoDateDecrease(idUser, 8, 0);
			List<CustomerLikeVideo> listVideoDecrease = AllDAO.daoVideo.listVieoViewDecrease(idUser, 8, 0);
			req.setAttribute("listVideo", list);
			req.setAttribute("listVideoViewDecrease", listVideoDecrease);
		}

		req.setAttribute("urlAjax", req.getRequestURL());
		req.setAttribute("country", listCountry);
		req.setAttribute("account", Account.account);
		req.setAttribute("genre", listGenre);

		RRSharer.remove();

		req.setAttribute("view", page);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	public void reStart() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				listCountry = AllDAO.daoCountry.findAll();
				listGenre = AllDAO.daoGenre.selectAll();
				
			}
		};
		// Lên lịch cho tác vụ được thực hiện định kỳ
		timer.scheduleAtFixedRate(task, 0, 50000); // Lặp lại sau mỗi 5 giây
	}

	private int numSession(String name) {
		return (int)RRSharer.request().getSession().getAttribute(name);
	}


}
