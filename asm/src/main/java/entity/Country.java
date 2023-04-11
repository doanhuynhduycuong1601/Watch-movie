package entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="country")
public class Country {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idCountry")
	private int idCountry;
	
	@Column(name="nameCountry")
	private String nameCountry;

	public Country(String nameCountry) {
		this.nameCountry = nameCountry;
	}
	
	// Một đạo diễn có thể chỉ đạo nhiều video
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos = new ArrayList<>();

	public Country(int idCountry) {
		super();
		this.idCountry = idCountry;
	}
    
    
}
