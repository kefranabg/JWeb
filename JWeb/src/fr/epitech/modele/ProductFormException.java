package fr.epitech.modele;

/**
 * Bean for product creation exception
 * @author acca_b
 *
 */
public class ProductFormException extends RuntimeException {
	
	   public ProductFormException( String message ) {
	       super( message );
	   }

	   public ProductFormException( String message, Throwable cause ) {
	       super( message, cause );
	   }

	   public ProductFormException( Throwable cause ) {
	       super( cause );
	   }
}
