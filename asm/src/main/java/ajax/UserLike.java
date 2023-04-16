package ajax;

import DAO.FavoriteDAO;
import entity.Favorite;
import entity.User;
import entity.Video;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import view.AllDAO;

public class UserLike {
	public static void like(HttpServletRequest req, String user) {
			int id = Integer.parseInt(req.getParameter("value"));
			boolean like = req.getParameter("like").equals("true");
			try {
				Favorite f = AllDAO.daoFavorite.findIdUserIdVideo(user, id);
				f.setLikes(like);
				AllDAO.daoFavorite.update(f);
			}catch(NoResultException n) {
				Favorite f = new Favorite(new User(user), new Video(id), like);
				AllDAO.daoFavorite.create(f);
			}
	}
}
