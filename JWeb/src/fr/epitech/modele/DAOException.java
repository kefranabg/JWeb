package fr.epitech.modele;

/**
 * DAO Exception bean
 * @author acca_b
 *
 */
public class DAOException extends RuntimeException {
		
	   public DAOException( String message ) {
	       super( message );
	   }

	   public DAOException( String message, Throwable cause ) {
	       super( message, cause );
	   }

	   public DAOException( Throwable cause ) {
	       super( cause );
	   }
}
