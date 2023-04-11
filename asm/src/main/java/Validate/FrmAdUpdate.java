package Validate;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

import DAO.DAO;
import DAO.UserDAO;
import Utils.Valid;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrmAdUpdate {
	public static User update(HttpServletRequest req, HttpServletResponse resp, UserDAO userdao) {
		User s = userdao.findById(req.getParameter("id"));
		if(s == null) {
			return null;
		}
		try {
			if(!checkFrmRe(req, "pass", "fullname")) {
				s = new User();
				BeanUtils.populate(s, req.getParameterMap());
				req.setAttribute("adUpdate", "document.getElementById(\"adUpdate\").click();");
				req.setAttribute("adUpAc", s);
			}else {
				DateTimeConverter dtc = new DateConverter(new Date());
				dtc.setPattern("yyyy-MM-dd");
				ConvertUtils.register(dtc, Date.class);
				BeanUtils.populate(s, req.getParameterMap());
				userdao.update(s);
				return s;
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static boolean checkFrmRe(HttpServletRequest req, String pass, String fullName) {
		Valid vl = new Valid();
		frmPass(req, vl, req.getParameter(pass));
		frmName(req, vl, req.getParameter(fullName));
		return vl.getLoi().isEmpty();
	}
	
	
	private static void frmPass(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "Pass";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
        } catch (Exception ex) {
        	req.setAttribute("adUpErrorPass", ex.getMessage());
        	req.setAttribute("adUpErrorPassInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void frmName(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "FullName";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 10);
        } catch (Exception ex) {
        	req.setAttribute("adUpErrorName", ex.getMessage());
        	req.setAttribute("adUpErrorNameInput", "style=\"background-color: yellow;\"");
        }
	}

}
