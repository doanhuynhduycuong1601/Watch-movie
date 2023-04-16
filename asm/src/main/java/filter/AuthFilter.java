package filter;

import java.io.IOException;

import Utils.XScope;
import entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import view.Account;



@WebFilter(filterName = "auth", urlPatterns = {"/manager/*"})
public class AuthFilter implements HttpFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException  {
		String uri = request.getRequestURI();
		
		User user = Account.account;
		String error = "";
		if (user==null) {
			error = response.encodeURL("Vui lòng đăng nhập");	
		}
		else if (!user.getAdmins()&&uri.contains("/admin/")) {
			error = response.encodeURL("Vui lòng đăng nhập với vai trò admin");
		}
		if(!error.isEmpty()) {
			XScope.setSession("securi", uri);
			response.sendRedirect("/asm/error/manager?error="+response.encodeURL(error));
		}
		else {
			chain.doFilter(request, response);
		}
	}

}
