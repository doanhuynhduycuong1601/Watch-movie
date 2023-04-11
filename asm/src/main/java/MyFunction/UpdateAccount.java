package MyFunction;


import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

import DAO.UserDAO;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;

public class UpdateAccount {
	public static User updateAccount(HttpServletRequest req, UserDAO daoUser) {
		User s = daoUser.findById(req.getParameter("id"));
		if(s == null) {
			return null;
		}
		try {
			
			DateTimeConverter dtc = new DateConverter(new Date());
			dtc.setPattern("yyyy-MM-dd");
			ConvertUtils.register(dtc, Date.class);
			
			BeanUtils.populate(s, req.getParameterMap());

			daoUser.update(s);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}
