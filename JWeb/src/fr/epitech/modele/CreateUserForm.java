package fr.epitech.modele;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

/**
 * Bean used for the user creation form
 * @author acca_b
 *
 */
public class CreateUserForm {
	private static final String USER_NAME_FIELD = "userName";
	private static final String USER_EMAIL_FIELD = "userEmail";
	private static final String USER_PASSWORD_FIELD = "userPassword";
	private static final String USER_PASSWORD_CONFIRMATION_FIELD = "userPasswordConfirmation";
	private static final String USER_IMAGE_FIELD = "userImage";
	private static final String USER_NEWSLETTER_FIELD = "userNewsletter";
	private static final int IMAGE_SIZE = 10240; 
	private static final String USER_DEFAULT_IMAGE = "images/unknown.jpg";
	
	private String errors = null;
    private UserDao userDao;

    public CreateUserForm(UserDao _userDao) {
    	userDao = _userDao;
    }
    
    public String getErrors() {
    	return errors;
    }
        
    public static String getField(HttpServletRequest request, String fieldName) {
    	String field = request.getParameter(fieldName);
    	if (field == null || field.trim().length() == 0)
    		return null;
    	return field;
    }

    public void isNameValid(String name, User user)
    {
    	if (name != null)
    	{
    		if (name.length() < 2)
    			throw new UserFormException("Le nom d'utilisateur doit contenir au moins 2 caractères.");
    		if (name.length() > 20)
    			throw new UserFormException("Le nom d'utilisateur doit contenir moins de 20 caractères.");    			
    	}
   		else
   			throw new UserFormException("Merci d'entrer un nom d'utilisateur");
    	user.setName(name);
    }

    public void isEmailValid(String email, User user)
    {
    	if (email != null && !email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)"))
    		throw new UserFormException("Merci de saisir une adresse e-mail valide.");
    	user.setEmail(email);
    }

    public void isPasswordValid(String password, String passwordConfirmation, User user)
    {
    	if (password != null && passwordConfirmation != null)
    	{
    		if (!passwordConfirmation.equals(password))
    			throw new UserFormException("La confirmation du mot de passe doit être identique au mot de passe.");
    	}
    	else
    		throw new UserFormException("Merci d'entrer un mot de passe.");
    	user.setPassword(password);
    }
    
    public static String getFileName(Part part)
    {
    	for (String contentDisposition : part.getHeader("content-disposition").split(";"))
    		if (contentDisposition.trim().startsWith("filename"))
    			return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim(); 
    	return null;
    }
        
    public User createUser(HttpServletRequest request) {
    	String name = getField(request, USER_NAME_FIELD);
    	String email = getField(request, USER_EMAIL_FIELD);
    	String password = getField(request, USER_PASSWORD_FIELD);
    	String passwordConfirmation = getField(request, USER_PASSWORD_CONFIRMATION_FIELD);
    	
    	
    	User user = new User();
    	
    	try {
       		isNameValid(name, user);
        	isEmailValid(email, user);
        	isPasswordValid(password, passwordConfirmation, user);
        	user.setImagePath(USER_DEFAULT_IMAGE);
        	user.setNewsletter(false);
    	}
    	catch (UserFormException e)
    	{
    		errors = e.getMessage();
    	}
    	
    	try {
    		if (errors == null)
    		{
    			User alreadyExist = userDao.findByEmail(user.getEmail());
    			if (alreadyExist != null)
    				errors = "Adresse e-mail déjà utilisée";
    			else
    			{
        			alreadyExist = null;
        			alreadyExist = userDao.findByName(user.getName());
        			if (alreadyExist != null)
        				errors = "Nom d'utilisateur déjà utilisée";
        			else
        			{
            			userDao.create(user);
        			}
    			}
    		}
    	}
    	catch (DAOException e)
    	{
    		errors = "Erreur imprévue lors de la creation";
    		e.printStackTrace();
    	}
    	return user;
    }

    public String splitImageFileName(String imageName)
    {
  		return imageName.substring(imageName.lastIndexOf('.')).trim().replace("\"", "");
    }
    
    public void writeImage(InputStream fileContent, String fileName, String imagePath) throws UserFormException
    {
    	BufferedInputStream input = null;
    	BufferedOutputStream output = null;

    	File file = new File(imagePath + fileName);
    	file.delete();
    	    	
    	try {
            input = new BufferedInputStream(fileContent, IMAGE_SIZE);
            output = new BufferedOutputStream(new FileOutputStream(new File(imagePath + fileName)), IMAGE_SIZE );
            
            byte[] buffer = new byte[IMAGE_SIZE];
            int length = 0;
            while ((length = input.read(buffer)) > 0)
            {
                output.write(buffer, 0, length);
            }
        }
    	catch (Exception e)
    	{	
    		throw new UserFormException( "Erreur lors de l'écriture du fichier." );
        }
    	finally
    	{
            try {
            	if (output != null)
            		output.close();
            }
            catch ( IOException ignore ) {}
            try {
            	if (input != null)
            		input.close();
            }
            catch ( IOException ignore ) {}
        }
    }
    
    public String processAndStoreImage(String completePath, String serverImagePath, String imageName, InputStream fileContent, User user) throws UserFormException
    {
    	String fileName = null;
    	
    	try {

      	    fileName = user.getName() + splitImageFileName(imageName);    		
    		fileName = fileName.replace(" ", "");
      	    
      	    
    		if (fileName != null && !fileName.isEmpty())
    		{
    			fileName = fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
   				writeImage(fileContent, fileName, completePath + serverImagePath);
	    	}
    	}
    	catch (IllegalStateException e)
    	{
    		e.printStackTrace();
    		throw new UserFormException( "Le fichier envoyé ne doit pas dépasser 1Mo." );
    	}
    	return serverImagePath + fileName;
    }
    
    public void isImageValid(String imagePath, String imageName, InputStream fileContent, User user)
    {
    	String finalImage = null;
    	try {
    		finalImage = processAndStoreImage("/Users/acca_b/Documents/bitbucket/jweb/JWeb/WebContent/", imagePath, imageName, fileContent, user);
    	}
    	catch (UserFormException e) {
    		errors =  e.getMessage();
    	}
    	user.setImagePath(finalImage);
    }
            
    public User updateUser(Map<String, String> data, User oldUser, InputStream fileContent) {
    	String name = data.get(USER_NAME_FIELD);
    	String email = data.get(USER_EMAIL_FIELD);
    	String password = data.get(USER_PASSWORD_FIELD);
    	String passwordConfirmation = data.get(USER_PASSWORD_CONFIRMATION_FIELD);
    	String newsletter = data.get(USER_NEWSLETTER_FIELD);
    	String image = data.get(USER_IMAGE_FIELD);

    	User user = userDao.findById(oldUser.getId());
    	
    	try {
       		isNameValid(name, user);
        	isEmailValid(email, user);
        	isPasswordValid(password, passwordConfirmation, user);
        	if (image != null && image.length() > 0)
        		isImageValid("images/users/", image, fileContent, user);
        	user.setNewsletter(newsletter);
    	}
    	catch (UserFormException e)
    	{
    		errors = e.getMessage();
    	}
    	
    	try {
    		if (errors == null)
    		{
       			userDao.update(user);
    		}
    	}
    	catch (DAOException e)
    	{
    		errors = "Erreur imprévue lors de la mise à jour des informations";
    		e.printStackTrace();
    	}
    	return user;
    }
    
}
