package MyFunction;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import DAO.DAO;
import DAO.GenreDAO;
import DAO.StarDAO;
import DAO.VideoDAO;
import Utils.FileUtils;
import Validate.FrmAddVideo;
import entity.Country;
import entity.Director;
import entity.Genre;
import entity.Star;
import entity.Video;
import entity.VideoLinks;
import jakarta.servlet.http.HttpServletRequest;
import lombok.val;
import view.AllDAO;

public class ManagerVideo {

	private Video value(HttpServletRequest req) throws IllegalAccessException, InvocationTargetException {
		Video v = new Video();
		BeanUtils.populate(v, req.getParameterMap());
		v.setPoster(FileUtils.writeFile(req, "poster", "/img/imgposter"));
		if (v.getIdVideo() > 0) {
			Video vAo = AllDAO.daoVideo.findById(v.getIdVideo());
			v.setViewVideo(vAo.getViewVideo());
			v.setDateUpload(vAo.getDateUpload());
			if (v.getPoster().equals("")) {
				v.setPoster(vAo.getPoster());
			}
		}

		if (req.getParameter("countryID") != null) {
			v.setCountry(new Country(Integer.parseInt(req.getParameter("countryID"))));
		}

		listLink(req, v);

		List<Genre> listGenre = listGenre(req);
		v.getGenres().addAll(listGenre);
		v.getStars().addAll(listStar(req));
		return v;

	}

	public void createVideo(HttpServletRequest req) {
		try {
			Video v = value(req);
			if (!FrmAddVideo.addVideo(req, null)) {
				req.setAttribute("editVideo", v);
				req.setAttribute("clickEdit", "document.getElementById('editVideo').click()");
				return;
			}

			AllDAO.daoVideo.update(v);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeVideo(HttpServletRequest req) {
		Video video = null;
		try {
			video = value(req);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (video.getIdVideo() <= 0) {
			return;
		}

		Video v = AllDAO.daoVideo.findById(video.getIdVideo());
		v.getGenres().clear();
		v.getLinks().clear();
		v.getStars().clear();
		try {
		AllDAO.daoVideo.remove(v);
		}catch(RuntimeException run) {
			listLink(req, v);

			List<Genre> listGenre = listGenre(req);
			v.getGenres().addAll(listGenre);
			req.setAttribute("editVideo", v);
			req.setAttribute("clickEdit", "document.getElementById('editVideo').click()");
			req.setAttribute("addVideoErrorTitle", "Video đang được ưa thích không thể xóa");
        	req.setAttribute("addVideoErrorTitleInput", "style=\"background-color: yellow;\"");
		}
	}

	private List<Genre> listGenre(HttpServletRequest req) {
		List<Genre> listGenre = new ArrayList<>();
		String[] genres = req.getParameterValues("genreAddData");
		if (genres == null) {
			return listGenre;
		}
		for (String string : genres) {
			listGenre.add(AllDAO.daoGenre.getReference(Integer.parseInt(string)));
		}
		return listGenre;
	}

	private List<Star> listStar(HttpServletRequest req) {
		List<Star> listStar = new ArrayList<>();
		String[] stars = req.getParameterValues("star");
		if (stars == null) {
			return listStar;
		}

		for (String string : stars) {
			listStar.add(AllDAO.daoStar.getReference(Integer.parseInt(string)));
		}
		return listStar;
	}

	private void listLink(HttpServletRequest req, Video v) {
		String[] links = req.getParameterValues("link");
		if (links == null) {
			v.getLinks().clear();
			return;
		}
		for (String string : links) {
			VideoLinks link = new VideoLinks(string, v);
			v.getLinks().add(link);
		}
	}

	public String edit(List<Genre> list) {
		String result = "";
		for (Genre g : list) {
			result += "listGenre.push('" + g.getId() + "')\n";
		}
		return result;
	}
}
