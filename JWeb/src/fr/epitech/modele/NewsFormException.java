package fr.epitech.modele;

/**
 * Bean for the news creation exceptions
 * @author acca_b
 *
 */
public class NewsFormException extends RuntimeException {
	   public NewsFormException( String message ) {
	       super( message );
	   }

	   public NewsFormException( String message, Throwable cause ) {
	       super( message, cause );
	   }

	   public NewsFormException( Throwable cause ) {
	       super( cause );
	   }
}
