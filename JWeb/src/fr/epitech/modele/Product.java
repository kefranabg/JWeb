package fr.epitech.modele;

import java.sql.Timestamp;

/**
 * Bean used for the products
 * @author acca_b
 *
 */
public class Product {
	private Long      id;
    private int		  price;
    private String    description;
    private String    name;
    private String	  imagePath;
    private boolean   isInStock;
    private Timestamp creationDate;
    
    public Product()
    {
    	
    }
    
    public Product(int _price, String _description, String _name
    		, String _imagePath, boolean _isInStock) {
    	price = _price;
    	description = _description;
    	name = _name;
    	imagePath = _imagePath;
    	isInStock = _isInStock;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public int getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setIsInStock(boolean isInStock) {
        this.isInStock = isInStock;
    }
    
    public boolean getIsInStock() {
        return isInStock;
    }
    
    public Timestamp getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate( Timestamp creationDate ) {
        this.creationDate = creationDate;
    }
    
    public boolean isValid(ProductDao productDao)
    {
    	return true;
    }
}
