package view;

import DAO.AdvertisingDAO;
import DAO.CountryDAO;
import DAO.DirectorDAO;
import DAO.FavoriteDAO;
import DAO.GenreDAO;
import DAO.StarDAO;
import DAO.UserDAO;
import DAO.VideoDAO;

public class AllDAO {
	public static CountryDAO daoCountry = new CountryDAO();
	public static FavoriteDAO daoFavorite = new FavoriteDAO();
	public static StarDAO daoStar = new StarDAO();
	public static UserDAO daoUser = new UserDAO();
	public static VideoDAO daoVideo = new VideoDAO();
	public static DirectorDAO daoDirector = new DirectorDAO();
	public static GenreDAO daoGenre = new GenreDAO();
	public static AdvertisingDAO daoAdver = new AdvertisingDAO();
}
