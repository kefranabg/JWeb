package fr.epitech.modele;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Bean used for the comments
 * @author acca_b
 *
 */
public class Comment {
	private String 	comment;
	private String	title;
	private Long      id;
	private Timestamp creationDate;
	private Long	idUser;
	private Long	idProduct;
	
	public Comment(String comment, Long idUser, Long idProduct, String title) {
		this.comment = comment;
		this.idUser = idUser;
		this.idProduct = idProduct;
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

	 public String getComment() {
	        return comment;
	    }
	    
	 public void setComment(String comment) {
	        this.comment = comment;
	    }
	 
	 public String getTitle() {
	        return title;
	    }
	    
	 public void setTitle(String title) {
	        this.title = title;
	    }
	 
	 public Long getIdUser() {
	        return idUser;
	    }
	    
	 public void setIdUser(Long idUser) {
	        this.idUser = idUser;
	    }
	 
	 public Long getIdProduct() {
	        return idProduct;
	    }
	    
	 public void setIdProduct(Long idProduct) {
	        this.idProduct = idProduct;
	    }
	 
	 public boolean isValid(CommentDao commentdao) {
		return true; 
	 }
	
}
