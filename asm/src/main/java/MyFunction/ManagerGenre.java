package MyFunction;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import DAO.GenreDAO;
import Utils.FileUtils;
import entity.Genre;
import jakarta.servlet.http.HttpServletRequest;

public class ManagerGenre {
	public static void updateGenre(HttpServletRequest req, GenreDAO daoGenre) {
		Genre g;
		String idGenre = req.getParameter("id");
		if(idGenre == null) {
			g = new Genre();
		}else {
			g = daoGenre.findById(Integer.parseInt(idGenre));
		}
		try {
			BeanUtils.populate(g, req.getParameterMap());
			g.setImg(FileUtils.writeFile(req, "img", "/img/imggenre"));
			daoGenre.update(g);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void removeGenre(HttpServletRequest req, GenreDAO daoGenre) {
		int idGenre = Integer.parseInt(req.getParameter("id"));
		Genre g = daoGenre.findById(idGenre);
		g.getVideos().clear();
		daoGenre.remove(g);

	}

}
