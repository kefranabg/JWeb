package fr.epitech.modele;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean used for the User
 * @author acca_b
 *
 */
public class User {
	private Long      id;
    private String    email;
    private String    password;
    private String    name;
    private boolean	  newsletter;
    private Timestamp registrationDate;
    private String imagePath;
    private boolean isAdmin;

    public User()
    {
    	isAdmin = false;
    }
    
    public User(String _password, String _email, String _name, String _newsletter)
    {
    	password = _password;
    	email = _email;
    	name = _name;
    	if (_newsletter == null)
    		newsletter = false;
    	else
    		newsletter = true;
    	isAdmin = false;
    }

    public User(String _password, String _email, String _name, boolean _newsletter)
    {
    	password = _password;
    	email = _email;
    	name = _name;
   		newsletter = _newsletter;
    	isAdmin = false;
    }

    public Long getId() {
        return id;
    }
    
    public void setId( Long id ) {
        this.id = id;
    }

    public void setEmail( String email ) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }

    public void setPassword( String password ) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }

    public void setName( String name ) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

	 public String getDateHumanReadable()
	 {
		 DateFormat formatter;
		 
		 formatter = new SimpleDateFormat("dd/MM/yyyy"); 
		 return formatter.format(this.registrationDate); 
	 }
    
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate( Timestamp registrationDate ) {
        this.registrationDate = registrationDate;
    }

    public boolean getIsAdmin() {
    	return isAdmin;
    }
    
    public void setIsAdmin(boolean _isAdmin)
    {
    	this.isAdmin = _isAdmin;
    }

    public boolean getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}

	public void setNewsletter(String _newsletter) {
    	if (_newsletter == null)
    		this.newsletter = false;
    	else
    		this.newsletter = true;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
    
}
