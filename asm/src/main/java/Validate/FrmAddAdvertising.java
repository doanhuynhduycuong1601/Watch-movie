package Validate;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import DAO.DAO;
import DAO.UserDAO;
import Utils.CookieUtils;
import Utils.Valid;
import entity.Advertising;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import view.Account;
import view.AllDAO;

public class FrmAddAdvertising {
	public static boolean addVideo(HttpServletRequest req, Advertising link) {		
		return checkFrm(req, link);
	}
	
	
	
	

	private static boolean checkFrm(HttpServletRequest req, Advertising link) {
		Valid vl = new Valid();
		frmTitle(req, vl, link.getNames());
		frmTimes(req, vl, link.getTimes());
		frmLink(req, vl, link.getLinkURL());
		frmDate(req, vl, link);
		return vl.getLoi().isEmpty();
	}
	
	private static void frmTitle(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "Names";
			if(input == null) {
				input = "";
			}
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
        } catch (Exception ex) {
        	req.setAttribute("addAdverErrorNames", ex.getMessage());
        	req.setAttribute("addAdverErrorNamesInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void frmTimes(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "Times";
			vl.isEmpty(input, name);
			String time = vl.reH + vl.reM + vl.reS;
			vl.reMatch(input, name, time, " Định dạng là 00h00m00s");
        } catch (Exception ex) {
        	req.setAttribute("addAdverErrorTimes", ex.getMessage());
        	req.setAttribute("addAdverErrorTimesInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void frmLink(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "Link";
			vl.isEmpty(input, name);
        } catch (Exception ex) {
        	req.setAttribute("addAdverErrorLinks", ex.getMessage());
        	req.setAttribute("addAdverErrorLinksInput", "style=\"background-color: yellow;\"");
        }
	}
	
	
	private static void frmDate(HttpServletRequest req, Valid vl, Advertising a) {
		try {
			String name = "Date";
			vl.isEmpty(req.getParameter("dates"), name);
			vl.reMatch(req.getParameter("dates"), name, vl.reDate(), "");
        } catch (Exception ex) {
        	req.setAttribute("addAdverErrorDate", ex.getMessage());
        	req.setAttribute("addAdverErrorDateInput", "style=\"background-color: yellow;\"");
        	a.setDates(null);
        }
	}
	
	

}
