package filter;

import java.io.IOException;

import Utils.RRSharer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class I18nFilter implements HttpFilter {
	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		String lang = req.getParameter("lang");
		if(lang != null) {
		req.getSession().setAttribute("lang", lang);
		}
		chain.doFilter(req, resp);
	
	}
}
