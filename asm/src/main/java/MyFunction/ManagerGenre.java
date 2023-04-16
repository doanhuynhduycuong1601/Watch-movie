package MyFunction;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import DAO.GenreDAO;
import Utils.FileUtils;
import entity.Genre;
import jakarta.servlet.http.HttpServletRequest;
import view.AllDAO;

public class ManagerGenre {
	public static void updateGenre(HttpServletRequest req) {
		Genre g = new Genre();
		try {
			BeanUtils.populate(g, req.getParameterMap());
			g.setImg(FileUtils.writeFile(req, "img", "/img/imggenre"));
			
			if(g.getId() != null && g.getId()>0) {
				Genre ao = AllDAO.daoGenre.getReference(g.getId());
				if(g.getImg().equals("")) {
					g.setImg(ao.getImg());
				}
			}
			AllDAO.daoGenre.update(g);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void removeGenre(HttpServletRequest req) {
		int idGenre = Integer.parseInt(req.getParameter("id"));
		Genre g = AllDAO.daoGenre.findById(idGenre);
		g.getVideos().clear();
		AllDAO.daoGenre.remove(g);

	}

}
