package Validate;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;

import DAO.DAO;
import DAO.UserDAO;
import Utils.SendMail;
import Utils.Valid;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrmRegister {
	public static void register(HttpServletRequest req, HttpServletResponse resp,UserDAO userdao) {
		User s = new User();
		try {
			BeanUtils.populate(s, req.getParameterMap());
			if(!checkFrmRe(req, s)) {
				req.setAttribute("aRegister", "document.getElementById(\"aRegister\").click();");
				req.setAttribute("formRe", s);
				code = "";
			}else {
				if(userdao.findById(s.getId()) == null) {
					if(code.isEmpty()) {
						code = getranDomCode();
						SendMail.sendCodeRegister(s.getEmail(), "Code register", code);
						req.setAttribute("formRe", s);
						req.setAttribute("aRegister", "document.getElementById(\"aRegister\").click();");
						req.setAttribute("reErrorMaCode", "Đã gửi code đến mail bạn.Bạn hãy check.");
					}else {
						String inputCode = req.getParameter("macode");
						if(inputCode != null && inputCode.equals(code)) {
							req.setAttribute("aRegister", "alert('Register thành công')");
							userdao.create(s);
						}else {
							req.setAttribute("MaCode", inputCode);
							req.setAttribute("formRe", s);
							req.setAttribute("reErrorMaCodeInput", "style=\"background-color: yellow;\"");
							req.setAttribute("reErrorMaCode", "Code nhập không đúng.");
							req.setAttribute("aRegister", "document.getElementById(\"aRegister\").click();");
						}
					}
				}else {
					req.setAttribute("aRegister", "document.getElementById(\"aRegister\").click();");
					req.setAttribute("formRe", s);
					req.setAttribute("reErrorUser", "User đã tồn tại.");
		        	req.setAttribute("reErrorUserInput", "style=\"background-color: yellow;\"");
		        	code = "";
				}
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String code = "";
	
	public static String getranDomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int ranCode = random.nextInt(9);
            str.append(ranCode);
        }
        return str.toString().trim();
    }
	
	private static boolean checkFrmRe(HttpServletRequest req, User s) {
		Valid vl = new Valid();
		frmReUser(req, vl, s.getId());
		frmRePass(req, vl, s.getPass());
		frmReName(req, vl, s.getFullname());
		frmReEmail(req, vl, s.getEmail());
		return vl.getLoi().isEmpty();
	}
	
	private static void frmReUser(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "User";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
        } catch (Exception ex) {
        	req.setAttribute("reErrorUser", ex.getMessage());
        	req.setAttribute("reErrorUserInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void frmRePass(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "Pass";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
        } catch (Exception ex) {
        	req.setAttribute("reErrorPass", ex.getMessage());
        	req.setAttribute("reErrorPassInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void frmReName(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "FullName";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 10);
        } catch (Exception ex) {
        	req.setAttribute("reErrorName", ex.getMessage());
        	req.setAttribute("reErrorNameInput", "style=\"background-color: yellow;\"");
        }
	}
	
	private static void frmReEmail(HttpServletRequest req, Valid vl, String input) {
		try {
			String name = "Email";
			vl.isEmpty(input, name);
			vl.minLength(input, name, 5);
			vl.reMatch(input, name, vl.reEmail(), "Phải có @ phía sau");
        } catch (Exception ex) {
        	req.setAttribute("reErrorEmail", ex.getMessage());
        	req.setAttribute("reErrorEmailInput", "style=\"background-color: yellow;\"");
        }
	}
}
