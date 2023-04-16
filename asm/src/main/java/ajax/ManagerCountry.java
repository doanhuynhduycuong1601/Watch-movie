package ajax;

import java.io.IOException;

import DAO.CountryDAO;
import DAO.GenreDAO;
import entity.Country;
import entity.Genre;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import view.AllDAO;

public class ManagerCountry {
	public static void countryUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String idCountry = req.getParameter("id");
		Country c = AllDAO.daoCountry.findById(Integer.parseInt(idCountry));
		
		c.setNameCountry(req.getParameter("name"));
		
		AllDAO.daoCountry.update(c);

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write("Update thành công");
	}
	
	public static void countryCreate(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Country c = new Country(req.getParameter("name"));
		
		AllDAO.daoCountry.create(c);

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write("Create thành công");
	}
}
