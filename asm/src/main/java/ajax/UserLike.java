package ajax;

import DAO.FavoriteDAO;
import entity.Favorite;
import entity.User;
import entity.Video;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;

public class UserLike {
	public static void like(HttpServletRequest req, String user, FavoriteDAO daoFavor) {
			int id = Integer.parseInt(req.getParameter("value"));
			boolean like = req.getParameter("like").equals("true");
			try {
				Favorite f = daoFavor.findIdUserIdVideo(user, id);
				f.setLikes(like);
				daoFavor.update(f);
			}catch(NoResultException n) {
				Favorite f = new Favorite(new User(user), new Video(id), like);
				daoFavor.create(f);
			}
	}
}
