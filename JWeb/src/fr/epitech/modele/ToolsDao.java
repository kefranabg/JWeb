package fr.epitech.modele;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * Tool bean for the DAO
 * @author acca_b
 *
 */
public class ToolsDao {


    public static PreparedStatement initializationPreparedRequest( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        PreparedStatement preparedStatement = (PreparedStatement) connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
    }
    
    

    public static void close( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture du ResultSet : " + e.getMessage() );
            }
        }
    }


    public static void close( Statement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture du Statement : " + e.getMessage() );
            }
        }
    }


    public static void close( Connection connexion ) {
        if ( connexion != null ) {
            try {
                connexion.close();
            } catch ( SQLException e ) {
                System.out.println( "Échec de la fermeture de la connexion : " + e.getMessage() );
            }
        }
    }


    public static void closeConnection( Statement statement, Connection connexion ) {
        close( statement );
        close( connexion );
    }


    public static void closeConnection( ResultSet resultSet, Statement statement, Connection connexion ) {
    	close(resultSet);
    	close(statement);
    	close(connexion);
    }
}
