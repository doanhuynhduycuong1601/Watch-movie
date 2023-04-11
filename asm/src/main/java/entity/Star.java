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
@Table(name="star")
public class Star {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idStar")
	private Integer idStar;
	
	@Column(name="nameStar")
	private String nameStar;
	
	@Column(name="imgStar")
	private String imgStar;
	
	@Column(name="countryID")
	private Integer countryID;
	
	@Temporal(TemporalType.DATE)
	@Column(name="birth")
	private Date birth;
	
	@Column(name="gender")
	private Boolean gender;
	
	@Column(name="introduce")
	private String introduce;
	

	public Star(String nameStar, int countryID) {
		super();
		this.nameStar = nameStar;
		this.countryID = countryID;
	}


	public Star(Integer idStar) {
		super();
		this.idStar = idStar;
	}
	
	
	
	
}
