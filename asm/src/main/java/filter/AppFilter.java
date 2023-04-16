package filter;

import java.io.IOException;

import Utils.RRSharer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebFilter(filterName = "app", urlPatterns = "/*")
public class AppFilter implements HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		RRSharer.add(request, response);
		chain.doFilter(request, response);
		RRSharer.remove();
	}
	
}
