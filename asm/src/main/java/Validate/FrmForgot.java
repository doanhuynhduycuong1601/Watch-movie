package Validate;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import DAO.DAO;
import DAO.UserDAO;
import Utils.CookieUtils;
import Utils.SendMail;
import Utils.Valid;
import entity.User;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import view.AllDAO;

public class FrmForgot {
	public static void forgot(HttpServletRequest req, HttpServletResponse resp) {
		User s = new User();
		try {
			BeanUtils.populate(s, req.getParameterMap());
			
			//check xem đúng định dạng chưa
			checkFrmF(req, s);
			System.out.println("Check xong");
			//check xem đúng tài khoản email trong database chưa
			User us = AllDAO.daoUser.forgotPass(s.getId(), s.getEmail());

			//gửi email
			SendMail.sendCodeRegister(us.getEmail(), "Forgot pass", us.getPass());
			req.setAttribute("aForgot", "alert('Đã gửi về gmail của bạn hãy check')");
			return;
			
		}catch(NoResultException e) {
			req.setAttribute("FErrorUser", "User or email incorrect");
        	req.setAttribute("FErrorUserInput", "style=\"background-color: yellow;\"");
        	req.setAttribute("FErrorEmail", "User or email incorrect");
        	req.setAttribute("FErrorEmailInput", "style=\"background-color: yellow;\"");
		}catch (Exception e) {
//			e.printStackTrace();
		}
		
		error(req, s);
	}
	
	private static void error(HttpServletRequest req, User s) {
		req.setAttribute("aForgot", "document.getElementById(\"aForgot\").click();");
		req.setAttribute("formF", s);
	}
	private static void checkFrmF(HttpServletRequest req, User s) throws Exception {
		Valid vl = new Valid();
		frmForgotUser(req, vl, s.getId());
		frmForgotEmail(req, vl, s.getEmail());
		if(!vl.getLoi().isEmpty()) {
			throw new Exception("Valid incorrect");
		}
	}
	
	private static void frmForgotUser(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "User";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
        } catch (Exception ex) {
        	req.setAttribute("FErrorUser", ex.getMessage());
        	req.setAttribute("FErrorUserInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void frmForgotEmail(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "Email";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
			vl.reMatch(input, name, vl.reEmail(), "Phải đúng định dạng");
        } catch (Exception ex) {
        	req.setAttribute("FErrorEmail", ex.getMessage());
        	req.setAttribute("FErrorEmailInput", "style=\"background-color: yellow;\"");
        }
	}
	

}
