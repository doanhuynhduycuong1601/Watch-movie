package entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "link")
public class Advertising {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String names;
	private String img;
	private String linkURL;
	@Temporal(TemporalType.DATE)
	private Date dates;
	private String times;

	public String getDate() {
		if (dates == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(dates);
	}

	
	public Advertising(String img) {
		super();
		this.img = img;
	}


	public Advertising(int id, String img) {
		super();
		this.id = id;
		this.img = img;
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
		return ao > 0 ? (int)Math.ceil(ao*80/100)*1000 : ao*1000;
	}
	

}
