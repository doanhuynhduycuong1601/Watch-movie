package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor()
@NoArgsConstructor
@Data
@NamedQueries({
	@NamedQuery(name="Video.videoLikeDate",
			query = "SELECT DISTINCT f.video FROM Favorite f where f.likeDate BETWEEN :min AND :max"),//lab 6 cau 3 c
	@NamedQuery(name="Video.videoLikeByMonths",
	query = "SELECT DISTINCT f.video FROM Favorite f where MONTH(f.likeDate) IN (:months)"),//lab 6 cau 3 D
})
@NamedNativeQueries({
	@NamedNativeQuery(name = "Video.random10",
			query = "SELECT TOP 10 * FROM Videos ORDER BY newid()", resultClass = Video.class)
})

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "Video.updateViewVideo", //update view video
			procedureName = "updateViewVideo",
			parameters = {
			@StoredProcedureParameter(name="idVideo", type = Integer.class)},resultClasses = {Video.class})
})

@Entity
@Table(name = "videos")
public class Video {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idVideo")
	private int idVideo;
	@Column(name = "title")
	private String title;
	@Column(name = "poster")
	private String poster;
	@Column(name = "viewVideo")
	private int viewVideo;

	@OneToMany(mappedBy = "video")
	List<Favorite> favorites;

	@Temporal(TemporalType.DATE)
	@Column(name="dateUpload")
	private Date dateUpload = new Date();
	@Column(name="times")
	private String times;
	@Column(name="descriptions")
	private String descriptions;
	@Column(name="active")
	private Boolean active = null;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "genresInVideo", joinColumns = { @JoinColumn(name = "id_Video") }, inverseJoinColumns = {
			@JoinColumn(name = "id_Genre") })
	private List<Genre> genres = new ArrayList<>();

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "starsInVideo", joinColumns = { @JoinColumn(name = "id_Video") }, inverseJoinColumns = {
			@JoinColumn(name = "id_Star") })
	private List<Star> stars = new ArrayList<>();

	// Một đạo diễn có thể chỉ đạo nhiều video
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "directorID")
	private Director director;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "countryID")
	private Country country;
	
	
	 @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<VideoLinks> links = new ArrayList<>();

	public Video(String title, String poster, int viewVideo) {
		super();
		this.title = title;
		this.poster = poster;
		this.viewVideo = viewVideo;
	}

	public Video(int idVideo) {
		super();
		this.idVideo = idVideo;
	}
	

	public void addGenre(Genre genre) {
		genres.add(genre);
	}

	public void removeGenre(Genre genre) {
		genres.remove(genre);
	}

	public void addStar(Star star) {
		stars.add(star);
	}

	public void removeStar(Star star) {
		stars.remove(star);
	}
	
	public int h() {
		return Integer.valueOf(times.substring(0, times.indexOf("h"))) * 3600;
	}
	
	public int m() {
		int m = 0;
		try {
			m = Integer.valueOf(times.substring(times.lastIndexOf("m")-2, times.lastIndexOf("m"))) * 60; 
		}catch(Exception e) {
			m = Integer.valueOf(times.substring(times.lastIndexOf("m")-1, times.lastIndexOf("m"))) * 60;
		}
		return m;
	}
	
	public int s() {
		int s = 0;
		try {
			s = Integer.valueOf(times.substring(times.lastIndexOf("s")-2, times.lastIndexOf("s")));
		}catch(Exception e) {
			s = Integer.valueOf(times.substring(times.lastIndexOf("s")-1, times.lastIndexOf("s")));
		}
		return s;
	}
	
	public int alltime() {
		int ao = 0;
		if(times.contains("h")) {
			ao += h();
			System.out.println("h : "+h());
		}
		if(times.contains("m")) {
			ao += m();
			System.out.println("m : "+m());
		}
		if(times.contains("s")) {
			ao += s();
			System.out.println("s :" +s());
		}
		return ao > 0 ? (int)Math.ceil(ao*80/100) : ao;
	}

	public Video(int idVideo, String poster) {
		super();
		this.idVideo = idVideo;
		this.poster = poster;
	}

}
