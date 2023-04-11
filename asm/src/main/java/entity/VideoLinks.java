package entity;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "linkVideo")
public class VideoLinks {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String urlVideo;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Video")
    private Video video;

	public VideoLinks(String urlVideo) {
		super();
		this.urlVideo = urlVideo;
	}

	public VideoLinks(String urlVideo, Video video) {
		super();
		this.urlVideo = urlVideo;
		this.video = video;
	}
	
	
	
	
}
