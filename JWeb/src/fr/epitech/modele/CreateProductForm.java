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
 * Bean used for the product creation form
 * @author acca_b
 *
 */
public class CreateProductForm {
	private static final String PRODUCT_NAME_FIELD = "name";
	private static final String PRODUCT_DESCRIPTION_FIELD = "description";
	private static final String PRODUCT_PRICE_FIELD = "price";
	private static final String PRODUCT_IMAGE_FIELD = "image";
	private static final int IMAGE_SIZE = 10240; 
	private static final String PRODUCT_DEFAULT_IMAGE = "images/unknown.jpg";

	private String errors = null;
    private ProductDao productDao;

    public CreateProductForm(ProductDao _productDao)
    {
    	this.productDao = _productDao;
    }
    
    public String getErrors() {
    	return errors;
    }

    public void isNameValid(String name, Product product)
    {
    	if (name != null)
    	{
    		if (name.length() < 5)
    			throw new ProductFormException("Le nom de produit doit contenir au moins 5 caracteres.");
    		if (name.length() > 20)
    			throw new ProductFormException("Le nom de produit doit contenir moins de 20 caracteres.");
    	}
   		else
   			throw new ProductFormException("Merci d'entrer un nom de produit");
    	product.setName(name);
    }
    
    public void isDescriptionValid(String description, Product product)
    {
    	if (description != null)
    	{
    		if (description.length() < 10)
    			throw new ProductFormException("La description doit comporter au moins 10 caracteres.");
    		if (description.length() > 1000)
    			throw new ProductFormException("La description doit comporter moins de 1000 caracteres.");
    	}
    	else
    		throw new ProductFormException("Merci d'entrer une description");
    	product.setDescription(description);
    }

    public void isPriceValid(String price, Product product)
    {
    	if (price != null)
    	{
    		if (price.length() > 8)
    			throw new ProductFormException("Prix trop élevé.");
        	if (Integer.parseInt(price) <= 0)
        		throw new ProductFormException("Le prix doit être superieur à 0.");
    	}
    	else
    		throw new ProductFormException("Merci d'entrer un prix pour le produit");
    	product.setPrice(Integer.parseInt(price));
    }

    public String splitImageFileName(String imageName)
    {
  		return  imageName.substring(imageName.lastIndexOf('.')).trim().replace("\"", "");
    }
    
    public void writeImage(InputStream fileContent, String fileName, String imagePath) throws ProductFormException
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
    		throw new ProductFormException( "Erreur lors de l'écriture du fichier." );
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
    
    public String processAndStoreImage(String completePath, String serverImagePath, String imageName, InputStream fileContent, Product product) throws ProductFormException
    {
    	String fileName = null;
    	
    	try {

    		fileName = product.getName() + splitImageFileName(imageName);		
    		fileName = fileName.replace(" ", "_");

    		if (fileName != null && !fileName.isEmpty())
    		{
    			fileName = fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
   				writeImage(fileContent, fileName, completePath + serverImagePath);
	    	}
    	}
    	catch (IllegalStateException e)
    	{
    		e.printStackTrace();
    		throw new ProductFormException( "Le fichier envoyé ne doit pas dépasser 1Mo." );
    	}
    	return serverImagePath + fileName;
    }
    
    public void isImageValid(String imagePath, String imageName, InputStream fileContent, Product product)
    {
    	String finalImage = null;
    	try {
    		finalImage = processAndStoreImage("/Users/acca_b/Documents/bitbucket/jweb/JWeb/WebContent/", imagePath, imageName, fileContent, product);
    	}
    	catch (ProductFormException e) {
    		errors = e.getMessage();
    	}
    	product.setImagePath(finalImage);
    }

    public Product createProduct(Map<String, String> data, InputStream fileContent)
    {
    	String name = data.get(PRODUCT_NAME_FIELD);
    	String description = data.get(PRODUCT_DESCRIPTION_FIELD);
    	String price = data.get(PRODUCT_PRICE_FIELD);
    	String image = data.get(PRODUCT_IMAGE_FIELD);

    	Product product = new Product();
    	
    	try {
       		isNameValid(name, product);
        	isDescriptionValid(description, product);
        	isPriceValid(price, product);
        	product.setIsInStock(true);
    	}
    	catch (ProductFormException e)
    	{
    		errors = e.getMessage();
    	}
    	try {
    		if (errors == null)
    		{
    			Product alreadyExist = productDao.findByName(product.getName());
    			if (alreadyExist != null)
    				errors = "Nom de produit déjà utilisé";
    			else    			
    			{
    	        	if (image != null && image.length() > 0)
    	        		isImageValid("images/products/", image, fileContent, product);
    	        	else
    	        		product.setImagePath(PRODUCT_DEFAULT_IMAGE);
    	        	if (errors == null)
    	        		productDao.create(product);
    			}
    		}
    	}
    	catch (DAOException e)
    	{
    		errors = "Erreur imprévue lors de la création du produit";
    		e.printStackTrace();
    	}
    	return product;
    }
}
