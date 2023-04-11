package entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="genre")
public class Genre {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	String names;
	
	String img;
	
	String descriptions;
	
//	@ManyToMany(mappedBy = "genres")
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "genresInVideo", joinColumns = { @JoinColumn(name = "id_Genre") }, inverseJoinColumns = {
			@JoinColumn(name = "id_Video") })
	private List<Video> videos = new ArrayList<>();
	

	public Genre(String names, String img, String descriptions) {
		super();
		this.names = names;
		this.img = img;
		this.descriptions = descriptions;
	}


	public Genre(Integer id) {
		super();
		this.id = id;
	}
	
	
	
}
