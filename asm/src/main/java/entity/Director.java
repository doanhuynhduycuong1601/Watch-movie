package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="director")
public class Director {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idDirector")
	private Integer idDirector;
	
	@Column(name="nameDirector")
	private String nameDirector;
	
	@Column(name="imgDirector")
	private String imgDirector;
	
	@Column(name="countryID")
	private Integer countryID;
	
	@Temporal(TemporalType.DATE)
	@Column(name="birth")
	private Date birth;
	
	@Column(name="gender")
	private Boolean gender;
	
	@Column(name="introduce")
	private String introduce;

	
	
	// Một đạo diễn có thể chỉ đạo nhiều video
    @OneToMany(mappedBy = "director")
    private List<Video> videos = new ArrayList<>();



	public Director(Integer idDirector) {
		super();
		this.idDirector = idDirector;
	}
    
    
	
}
