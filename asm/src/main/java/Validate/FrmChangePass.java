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
import view.Account;
import view.AllDAO;

public class FrmChangePass {
	public static void changepass(HttpServletRequest req, HttpServletResponse resp) {
		String current = req.getParameter("current");
		String newPass = req.getParameter("newPass");
		String confirm = req.getParameter("confirm");
		if(checkFrmC(req,current,newPass,confirm)) {
			if(checkCompare(req, current, newPass, confirm)) {	
				Account.account.setPass(newPass);
				AllDAO.daoUser.update(Account.account);
				return;
			}
		}
		
		req.setAttribute("aChangePass", "document.getElementById(\"aChangePass\").click();");
		req.setAttribute("current", current);
		req.setAttribute("newPass", newPass);
		req.setAttribute("confirm", confirm);
	}
	
	
	private static boolean checkCompare(HttpServletRequest req, String passCurrent, String passNew, String confirm) {
		Valid vl = new Valid();
		comparePassCurrent(req, vl, passCurrent);
		comparePassNew(req, vl, passNew, confirm);
		return vl.getLoi().isEmpty();
	}
	
	private static void comparePassCurrent(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "PassCurrent";
			vl.compare(input, name, Account.account.getPass());
        } catch (Exception ex) {
        	req.setAttribute("cErrorCurrent", ex.getMessage());
        	req.setAttribute("cErrorCurrentInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void comparePassNew(HttpServletRequest req, Valid vl, String input1, String input2) {
		try {
			vl.compare(input1, "NewPassword and ConfirmPassword are not the same", input2);
        } catch (Exception ex) {
        	req.setAttribute("cErrorNew", ex.getMessage());
        	req.setAttribute("cErrorNewInput", "style=\"background-color: yellow;\"");
        	req.setAttribute("cErrorConfirm", ex.getMessage());
        	req.setAttribute("cErrorConfirmInput", "style=\"background-color: yellow;\"");
        }
	}
	

	private static boolean checkFrmC(HttpServletRequest req, String passCurrent, String passNew, String confirm) {
		Valid vl = new Valid();
		frmCurrent(req, vl, passCurrent);
		frmNew(req, vl, passNew);
		frmConfirm(req, vl, confirm);
		return vl.getLoi().isEmpty();
	}
	
	private static void frmCurrent(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "PassCurrent";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
        } catch (Exception ex) {
        	req.setAttribute("cErrorCurrent", ex.getMessage());
        	req.setAttribute("cErrorCurrentInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void frmNew(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "PassNew";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
        } catch (Exception ex) {
        	req.setAttribute("cErrorNew", ex.getMessage());
        	req.setAttribute("cErrorNewInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void frmConfirm(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "Confirm";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
        } catch (Exception ex) {
        	req.setAttribute("cErrorConfirm", ex.getMessage());
        	req.setAttribute("cErrorConfirmInput", "style=\"background-color: yellow;\"");
        }
	}
	
	

}
