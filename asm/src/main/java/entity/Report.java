package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.StoredProcedureParameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "spFavoriteByYear",
			procedureName = "spFavoriteByYear",
			parameters = {
			@StoredProcedureParameter(name="year", type = Integer.class)},resultClasses = {Report.class})
})

@Entity
public class Report {
	@Id
	String group;
	Long likes;
	Date newest;
	Date oldest;
	
	
	public String getNew() {
		if(newest == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(newest);
	}
	
	public String getOld() {
		if(oldest == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(oldest);
	}
}
