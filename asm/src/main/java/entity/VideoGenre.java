package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="genresInVideo")
public class VideoGenre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id = null;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Video")
	private Video video;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Genre")
	private Genre genre;
}
