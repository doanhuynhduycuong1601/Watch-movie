package MyFunction;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

import Utils.FileUtils;
import Validate.FrmAddAdvertising;
import entity.Advertising;
import jakarta.servlet.http.HttpServletRequest;
import view.AllDAO;

public class ManagerAdvertising {
	public static void add(HttpServletRequest req) {
		Advertising a = new Advertising();

		try {
			DateTimeConverter dtc = new DateConverter(new Date());
			dtc.setPattern("yyyy-MM-dd");
			ConvertUtils.register(dtc, Date.class);
			BeanUtils.populate(a, req.getParameterMap());
			a.setImg(FileUtils.writeFile(req, "img", "/img/imgAdver"));
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		
		if(a.getId() > 0) {
			Advertising ao = AllDAO.daoAdver.findById(a.getId());
			if(a.getImg().equals("")) {
				a.setImg(ao.getImg());
			}
		}
		
		if (!FrmAddAdvertising.addVideo(req, a)) {
			req.setAttribute("editLink", a);
			return;
		}

		AllDAO.daoAdver.update(a);
	}

	public static void remove(HttpServletRequest req) {
		Advertising a = new Advertising();

		try {
			DateTimeConverter dtc = new DateConverter(new Date());
			dtc.setPattern("yyyy-MM-dd");
			ConvertUtils.register(dtc, Date.class);
			BeanUtils.populate(a, req.getParameterMap());
			a.setImg(FileUtils.writeFile(req, "img", "/img/imgAdver"));
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		AllDAO.daoAdver.remove(a);
	}
}
