package entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="users")
public class User {
	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="email")
	private String email;
	
	@Column(name="pass")
	private String pass;
	
	@Column(name="img")
	private String img;
	
	@Column(name="fullname")
	private String fullname;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="admins")
	private Boolean admins = false;
	
	@Column(name="gender")
	private Boolean gender = true;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateCreate")
	private Date dateCreate = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name="birth")
	private Date birth;
	
	@Column(name="existss")
	private Boolean existss = true;
	
	@OneToMany(mappedBy = "user")
	private List<Favorite> favorites;

	public User(String id) {
		super();
		this.id = id;
	}
	
	
	public String getBirth() {
		if(birth == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		return sdf.format(birth);
	}
	
	
}
