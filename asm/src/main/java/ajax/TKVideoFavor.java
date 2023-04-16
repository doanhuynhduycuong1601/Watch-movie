package ajax;

import java.io.IOException;
import java.util.List;

import DAO.UserDAO;
import DAO.VideoDAO;
import entity.Video;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import view.AllDAO;

public class TKVideoFavor {
	public static void videoFavor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String result = "";
		
		List<Video> v = AllDAO.daoVideo.videoHaveFavor(req.getParameter("favor").equals("true"));
		result = table(v);
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(result);
	}

	private static String table(List<Video> video) {
		return "<table class=\"row\">\r\n"
				+ "					<tr class=\"row mb-3\">\r\n"
				+ "						<th class=\"col-2\">ID</th>\r\n"
				+ "						<th class=\"col-4\">Title</th>\r\n"
				+ "						<th class=\"col-4\">View</th>\r\n"
				+ "						<th class=\"col-2\">Active</th>\r\n"
				+ "					</tr>\r\n"
				+list(video)
				+ "				</table>";
	}

	private static String list(List<Video> video) {
		String value = "";

		for (Video v : video) {
			value +=  "	<tr class=\"row mb-3\">\r\n"
					+ "		<td class=\"col-2\">"+v.getIdVideo()+"</td>\r\n"
					+ "		<td class=\"col-4\">"+v.getTitle()+"</td>\r\n"
					+ "		<td class=\"col-4\">"+v.getViewVideo()+"</td>\r\n"
					+ "		<td class=\"col-2\">"+v.getActive()+"</td>\r\n"
					+ "	</tr>\r\n";
		}
		return value;
	}
}
