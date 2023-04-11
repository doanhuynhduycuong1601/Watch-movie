package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class GenreShow {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	String names;
	
	String img;
	
	String descriptions;
    private long viewGenre;
	

    
}
