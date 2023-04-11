package MyFunction;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import DAO.DAO;
import DAO.GenreDAO;
import DAO.StarDAO;
import DAO.VideoDAO;
import Utils.FileUtils;
import entity.Country;
import entity.Director;
import entity.Genre;
import entity.Star;
import entity.Video;
import entity.VideoLinks;
import jakarta.servlet.http.HttpServletRequest;
import lombok.val;

public class ManagerVideo {
	public void createVideo(HttpServletRequest req, VideoDAO daoVideo, GenreDAO daoGenre, StarDAO daoStar) {
		Video v = new Video();
		try {
			BeanUtils.populate(v, req.getParameterMap());
			v.setPoster(FileUtils.writeFile(req, "poster", "/img/imgposter"));
			
			if(req.getParameter("countryID") != null) {
				v.setCountry(new Country(Integer.parseInt(req.getParameter("countryID"))));
			}

			
			listLink(req, v);
			
			List<Genre> listGenre = listGenre(req,daoGenre);
			v.getGenres().addAll(listGenre);
			v.getStars().addAll(listStar(req,daoStar));
			
			String idVideo = req.getParameter("idVideo");
			if(idVideo != null && !idVideo.equals("") && v.getIdVideo() > 0) {
				Video vAo = daoVideo.findById(Integer.parseInt(idVideo));
				v.setViewVideo(vAo.getViewVideo());
				v.setDateUpload(vAo.getDateUpload());
			}
			
			//trường hợp của update
			if(v.getIdVideo() > 0) {
				Video vAo = daoVideo.videoDetail(v.getIdVideo());
				v.setDateUpload(vAo.getDateUpload());
				if(v.getPoster().equals("")) {
					v.setPoster(vAo.getPoster());
				}
			}
			
			daoVideo.update(v);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void removeVideo(HttpServletRequest req, VideoDAO daoVideo) {
		String idVideo = req.getParameter("idVideo");
		if(idVideo == null || idVideo.equals("")) {
			return;
		}
		
		Video v = daoVideo.findById(Integer.parseInt(idVideo));
		v.getGenres().clear();
		v.getLinks().clear();
		v.getStars().clear();
		daoVideo.remove(v);
	}
	
	
	
	
	private List<Genre> listGenre(HttpServletRequest req, GenreDAO daoGenre){
		List<Genre> listGenre = new ArrayList<>();
		String[] genres = req.getParameterValues("genreAddData");
		if(genres == null) {
			return listGenre;
		}
		for (String string : genres) {
			listGenre.add(daoGenre.getReference(Integer.parseInt(string)));
		}
		return listGenre;
	}
	
	
	private List<Star> listStar(HttpServletRequest req, StarDAO daoStar){
		List<Star> listStar = new ArrayList<>();
		String[] stars = req.getParameterValues("star");
		if(stars == null) {
			return listStar;
		}
		
		for (String string : stars) {
			listStar.add(daoStar.getReference(Integer.parseInt(string)));
		}
		return listStar;
	}
	
	private void listLink(HttpServletRequest req, Video v){
		String[] links = req.getParameterValues("link");
		if(links == null) {
			v.getLinks().clear();
			return;
		}
		for (String string : links) {
			VideoLinks link = new VideoLinks(string, v);
			v.getLinks().add(link);
		}
	}
}
