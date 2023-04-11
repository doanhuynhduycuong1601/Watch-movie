package Validate;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import DAO.DAO;
import DAO.UserDAO;
import Utils.CookieUtils;
import Utils.Valid;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrmLogin {
	public static User login(HttpServletRequest req, HttpServletResponse resp,UserDAO userdao) {
		User s = new User();
		try {
			BeanUtils.populate(s, req.getParameterMap());
			if(!checkFrmRe(req, s)) {
				req.setAttribute("btnLogin", "document.getElementById(\"btnLogin\").click();");
				req.setAttribute("formLg", s);
			}else {
				User account = userdao.findById(s.getId());
				if(account != null) {
					if(account.getPass().equals(s.getPass())) {
						String remember = req.getParameter("remember");
						int mls = (remember == null) ? 0 : 60 * 60 * 60; // 0 = xóa
						CookieUtils.add("user", account.getId(), mls, resp);
						CookieUtils.add("pass", account.getPass(), mls, resp);
						CookieUtils.add("rememberLogin", "checked", mls, resp);
						req.setAttribute("btnLogin", "alert('login thành công')");
						return account;
					}else{
						userPassIncorrect(req, s);
					}
				}else {
					userPassIncorrect(req, s);
				}
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void userPassIncorrect(HttpServletRequest req, User s) {
		req.setAttribute("btnLogin", "document.getElementById(\"btnLogin\").click();");
		req.setAttribute("formLg", s);
		req.setAttribute("lgErrorUser", "User or Pass incorrect.");
    	req.setAttribute("lgErrorUserInput", "style=\"background-color: yellow;\"");
    	
    	req.setAttribute("lgErrorPass", "User or Pass incorrect.");
    	req.setAttribute("lgErrorPassInput", "style=\"background-color: yellow;\"");
	}
	private static boolean checkFrmRe(HttpServletRequest req, User s) {
		Valid vl = new Valid();
		frmLgUser(req, vl, s.getId());
		frmLgPass(req, vl, s.getPass());
		return vl.getLoi().isEmpty();
	}
	
	private static void frmLgUser(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "User";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
        } catch (Exception ex) {
        	req.setAttribute("lgErrorUser", ex.getMessage());
        	req.setAttribute("lgErrorUserInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void frmLgPass(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "Pass";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
        } catch (Exception ex) {
        	req.setAttribute("lgErrorPass", ex.getMessage());
        	req.setAttribute("lgErrorPassInput", "style=\"background-color: yellow;\"");
        }
	}

}
