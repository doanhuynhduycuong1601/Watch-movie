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

public class FrmAddVideo {
	public static boolean addVideo(HttpServletRequest req, HttpServletResponse resp) {
		String name = req.getParameter("title");
		String times = req.getParameter("times");
		
		return checkFrm(req, name, times);
	}
	
	
	
	

	private static boolean checkFrm(HttpServletRequest req, String name, String times) {
		Valid vl = new Valid();
		frmTitle(req, vl, name);
		frmTimes(req, vl, times);
		return vl.getLoi().isEmpty();
	}
	
	private static void frmTitle(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "Title";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
        } catch (Exception ex) {
        	req.setAttribute("addVideoErrorTitle", ex.getMessage());
        	req.setAttribute("addVideoErrorTitleInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void frmTimes(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "Times";
			vl.isEmpty(input, name);
			String time = vl.reH + vl.reM + vl.reS;
			vl.reMatch(input, name, time, " Định dạng là 00h00m00s");
        } catch (Exception ex) {
        	req.setAttribute("addVideoErrorTimes", ex.getMessage());
        	req.setAttribute("addVideoErrorTimesInput", "style=\"background-color: yellow;\"");
        }
	}
	
	
	
	

}
