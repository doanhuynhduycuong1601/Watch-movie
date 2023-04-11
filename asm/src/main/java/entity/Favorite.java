package entity;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NamedQueries({
	@NamedQuery(name="Favor.findByKeyword",
			query = "SELECT DISTINCT f FROM Favorite f where f.video.title LIKE :titleV"),//lab 6 cau 3
	@NamedQuery(name="Favor.findByUser", 
			query = "SELECT f FROM Favorite f where f.user.id = :idU")//lab6 cau3
})
@Entity
@Table(name="favorities", uniqueConstraints={
@UniqueConstraint(columnNames = {"idUser","idVideo"})
})
public class Favorite {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idFavor")
	private Long idFavor;
	
	@ManyToOne @JoinColumn(name="idUser")
	User user;
	
	@ManyToOne @JoinColumn(name="idVideo")
	Video video;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="likeDate")
	private Date likeDate = new Date();
	
	@Column(name="likes")
	private Boolean likes;

	public Favorite(Boolean likes) {
		this.likes = likes;
	}

	public Favorite(User user, Video video, Boolean likes) {
		super();
		this.user = user;
		this.video = video;
		this.likes = likes;
	}

	public Favorite(Video video) {
		super();
		this.video = video;
	}
	
	
	
	
	
	
}
