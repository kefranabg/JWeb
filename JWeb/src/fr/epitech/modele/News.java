package fr.epitech.modele;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Bean used for the News
 * @author acca_b
 *
 */
public class News {
	private String 	description;
	private String	title;
	private Long      id;
	private Timestamp creationDate;
	private String 	imagePath;
	
	public News()
	{
		
	}
	
	public News(String description, String title, String imagePath) {
		this.description = description;
		this.imagePath = imagePath;
		this.title = title;
	}
	
	 public Long getId() {
	        return id;
	    }
	    
	 public void setId( Long id ) {
	        this.id = id;
	    }

	 public String getDateHumanReadable()
	 {
		 DateFormat formatter;
		 
		 formatter = new SimpleDateFormat("dd/MM/yyyy"); 
		 return formatter.format(this.creationDate); 
	 }
	 
	 public Timestamp getCreationDate() {
	        return creationDate;
	    }
	 
	    
	 public void setCreationDate( Timestamp registrationDate ) {
	        this.creationDate = registrationDate;
	    }

	 public String getDescription() {
	        return description;
	    }
	    
	 public void setDescription(String description) {
	        this.description = description;
	    }
	 
	 public String getTitle() {
	        return title;
	    }
	    
	 public void setTitle(String title) {
	        this.title = title;
	    }
	 
	 public String getImagePath() {
	        return imagePath;
	    }
	    
	 public void setImagePath(String imagePath) {
	        this.imagePath = imagePath;
	    }
	 
	 public boolean isValid(NewsDao newsdao) {
		return true; 
	 }
	
}
