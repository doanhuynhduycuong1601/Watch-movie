package ajax;

import java.io.IOException;
import java.util.List;

import DAO.FavoriteDAO;
import DAO.GenreDAO;
import DAO.UserDAO;
import entity.Favorite;
import entity.Genre;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import view.AllDAO;

public class ManagerGenreAjax {
	public static void genre(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String result = "";
		if(req.getParameter("edit") != null) {
			Genre genre = AllDAO.daoGenre.getReference(Integer.parseInt(req.getParameter("value")));
			result += "<input name=\"id\" hidden value=\""+genre.getId()+"\">";
			result += form(genre, buttonEdit());
		}else {
			Genre genre = new Genre("","chonanh.png","");
			result += form(genre, buttonReset());
		}
		
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(result);
	}
	
	
	private static String form(Genre genre, String button) {
		return "<label class=\"col-4\" for=\"managerGenreInput\"> <img src=\"\\lab6\\img\\imggenre\\"+genre.getImg()+"\"\r\n"
				+ "					class=\"row\" style=\"height: 270px;width: 100%\" id=\"managerGenreImg\">\r\n"
				+ "				</label> <input name=\"img\" style=\"display: none\" id=\"managerGenreInput\" type=\"file\"\r\n"
				+ "					name=\"poster\">\r\n"
				+ "				<div class=\"col-7 offset-1\">\r\n"
				+ "					<div class=\"row mb-4 text-white\">\r\n"
				+ "						<label class=\"col-3\">Name :</label> <input name=\"names\" value=\""+genre.getNames()+"\" class=\"col-7\"\r\n"
				+ "							placeholder=\"Name\">\r\n"
				+ "					</div>\r\n"
				+ "					<div class=\"row mb-4\">\r\n"
				+ "						<label class=\"col-3 text-white\">Description :</label>\r\n"
				+ "						<textarea name=\"descriptions\" class=\"col-7\" rows=\"\" cols=\"\">"+genre.getDescriptions()+"</textarea>\r\n"
				+ "					</div>\r\n"
				+ "\r\n"
				+ "					<div class=\"row\">\r\n"
				+ "<div class=\"col-12\">\r\n"
				+ 	button							
				+ "							<label onclick=\"editGenre('0','reset')\" class=\"btn btn-primary\">Reset</label>\r\n"
				+ "						</div>"
				+ "					</div>\r\n"
				+ "				</div>";
	}
	
	private static String buttonReset() {
		return "<button onclick=\"managerClick("+"'createGenre'"+")\" type=\"button\" class=\"btn btn-primary\">Create</button>\r\n";
	}
	
	private static String buttonEdit() {
		return "<button onclick=\"managerClick("+"'updateGenre'"+")\" type=\"button\" class=\"btn btn-primary\">Update</button>\r\n "
				+"<button onclick=\"managerClick("+"'deleteGenre'"+")\" type=\"button\" class=\"btn btn-primary\">Delete</button>\r\n";
	}
}
