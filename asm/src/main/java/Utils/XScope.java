package Utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class XScope {
	
	public static HttpServletRequest request() {
		return RRSharer.request();
	}
	
	public static HttpSession session() {
		return request().getSession();
	}
	
	public static ServletContext application() {
		return request().getServletContext();
	}
	
	/* Tạo attribute trong request scope 
	 * @param name ten attribute
	 * @param value giá trị của attribute 
	 * */
	public static void setRequest(String name, Object value) {
		request().setAttribute(name, value);
	}
	
	/* Đọc attribute trong request scope
	 * @param name tên attribute
	 * @return giá trị của attribute hoặc null nều không tồn tại
	 * */
	
	@SuppressWarnings("unchecked")
	public static <T> T getRequest(String name)
	{
		return (T) request().getAttribute(name);
	}
	
	/*Xóa attribute trong request scope
	 * @param name ten attribute cần xóa
	 * */
	public static void removeRequest(String name) {
		request().removeAttribute(name);
	}
	
	/*Tạo attribute trong session scope
	 * @param name tên attribute 
	 * @param value giá trị của attribute
	 * */
	public static void setSession(String name, Object value) {
		session().setAttribute(name, value);
	}
	
	/*Doc attribute trong session scope
	 * @param name tên attribute 
	 * @param value giá trị của attribute
	 * */
	@SuppressWarnings("unchecked")
	public static <T> T getSession(String name) {
		return (T) session().getAttribute(name);
	}
	
	/*Xóa attribute trong session scope
	 * @param name ten attribute cần xóa
	 * */
	public static void removeSession(String name) {
		session().removeAttribute(name);
	}
	
	/* Tạo attribute trong application scope 
	 * @param name ten attribute
	 * @param value gia tri của attribute
	 * */
	public static void setApplication(String name, Object value) {
		application().setAttribute(name, value);
	}
	
	/* Đoc attribute trong application scope
	 * @param name ten attribute
	 * @return Gia tri cua attribute hoặc null nếu không tồn tại
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public static <T> T getApplication(String name) {
		return (T) application().getAttribute(name);
	}
	
	/*Xóa attribute trong application scope
	 * @param name ten attribute cần xóa
	 * */
	public static void removeApplication(String name) {
		application().removeAttribute(name);
	}
}
