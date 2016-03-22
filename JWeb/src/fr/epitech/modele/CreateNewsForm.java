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

/**
 * Bean used for news creation form
 * @author acca_b
 *
 */
public class CreateNewsForm {
	private static final String NEWS_TITLE_FIELD = "title";
	private static final String NEWS_DESCRIPTION_FIELD = "description";
	private static final String NEWS_IMAGE_FIELD = "image";
	private static final int IMAGE_SIZE = 10240; 
	private static final String NEWS_DEFAULT_IMAGE = "images/unknown.jpg";

	public String errors = null;
    private NewsDao newsDao;

    public CreateNewsForm(NewsDao _news)
    {
    	this.newsDao = _news;
    }

    public String getErrors()
    {
    	return errors;
    }
    
    public void isTitleValid(String title, News news)
    {
    	if (title != null)
    	{
    		if (title.length() < 5)
    			throw new NewsFormException("Le titre de la news doit contenir au moins 5 caracteres.");
    		if (title.length() > 60)
    			throw new NewsFormException("Le titre de la news doit contenir moins de 60 caracteres.");
    	}
   		else
   			throw new NewsFormException("Merci d'entrer un titre pour la news");
    	news.setTitle(title);
    }
    
    public void isDescriptionValid(String description, News news)
    {
    	if (description != null)
    	{
    		if (description.length() < 10)
    			throw new NewsFormException("La description de la news doit contenir au moins 10 caracteres.");
    		if (description.length() > 1000)
    			throw new NewsFormException("La description de la news doit contenir moins de 1000 caracteres.");
    	}
    	else
    		throw new NewsFormException("Merci d'entrer une description pour la news");
    	news.setDescription(description);
    }
    
    public String splitImageFileName(String imageName)
    {
  		return imageName.substring(imageName.lastIndexOf('.')).trim().replace("\"", "");
    }
    
    public void writeImage(InputStream fileContent, String fileName, String imagePath) throws NewsFormException
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
    		throw new NewsFormException( "Erreur lors de l'écriture du fichier." );
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
    
    public String processAndStoreImage(String completePath, String serverImagePath, String imageName, InputStream fileContent, News news) throws NewsFormException
    {
    	String fileName = null;
    	
    	try {
    		
    		fileName = news.getTitle() + splitImageFileName(imageName);
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
    		throw new NewsFormException( "Le fichier envoyé ne doit pas dépasser 1Mo." );
    	}
    	return serverImagePath + fileName;
    }
    
    public void isImageValid(String imagePath, String imageName, InputStream fileContent, News news)
    {
    	String finalImage = null;
    	try {
    		finalImage = processAndStoreImage("/Users/acca_b/Documents/bitbucket/jweb/JWeb/WebContent/", imagePath, imageName, fileContent, news);
    	}
    	catch (NewsFormException e) {
    		errors = e.getMessage();
    	}
    	news.setImagePath(finalImage);
    }
    
    public News createNews(Map<String, String> data, InputStream fileContent)
    {
    	String title = data.get(NEWS_TITLE_FIELD);
    	String description = data.get(NEWS_DESCRIPTION_FIELD);
    	String image = data.get(NEWS_IMAGE_FIELD);

    	News news = new News();
    	   	
    	
    	try {
       		isTitleValid(title, news);
        	isDescriptionValid(description, news);
    	}
    	catch (NewsFormException e)
    	{
    		errors = e.getMessage();
    	}
    	try {
    		if (errors == null)
    		{
    			News alreadyExist = newsDao.findByName(news.getTitle());
    			if (alreadyExist != null)
    				errors = "Nom de news déjà utilisé";
    			else
    			{
    	        	if (image != null && image.length() > 0)
    	        		isImageValid("images/news/", image, fileContent, news);
    	        	else
    	        		news.setImagePath(NEWS_DEFAULT_IMAGE);
    	        	if (errors == null)
    	        		newsDao.create(news);
    			}
    		}
    	}
    	catch (DAOException e)
    	{
    		errors = "Erreur imprévue lors de la création de la news";
    		e.printStackTrace();
    	}
    	return news;
    }

    
}
