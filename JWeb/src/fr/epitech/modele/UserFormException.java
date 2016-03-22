package fr.epitech.modele;

/**
 * Bean for user creation or modification exception
 * @author acca_b
 *
 */
public class UserFormException extends RuntimeException {
		
	   public UserFormException( String message ) {
	       super( message );
	   }

	   public UserFormException( String message, Throwable cause ) {
	       super( message, cause );
	   }

	   public UserFormException( Throwable cause ) {
	       super( cause );
	   }
}
