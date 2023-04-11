package entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CustomerLikeVideo {
	@Id
	Integer idVideo;
	String title;
	String poster;
	Integer views;
	Boolean like;
	public CustomerLikeVideo(Integer idVideo, String title, String poster, Integer views, Boolean like) {
		super();
		this.idVideo = idVideo;
		this.title = title;
		this.poster = poster;
		this.views = views;
		this.like = like;
	}
	
	
}
