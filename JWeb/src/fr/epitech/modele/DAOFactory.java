package fr.epitech.modele;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DAO Factory bean
 * @author acca_b
 *
 */
public class DAOFactory {
	private static final String FILE_PROPERTIES       = "fr/epitech/modele/dao.properties";
    private static final String PROPERTY_URL             = "url";
    private static final String PROPERTY_DRIVER          = "driver";
    private static final String PROPERTY_USER_NAME = "username";
    private static final String PROPERTY_PASSWORD    = "password";

    private String              url;
    private String              username;
    private String              password;

    DAOFactory( String url, String username, String password ) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String nomUtilisateur;
        String motDePasse;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( FILE_PROPERTIES );

        if ( fichierProperties == null ) {
            throw new DAOConfigurationException( "Properties file " + FILE_PROPERTIES + " cannot be found." );
        }

        try {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            nomUtilisateur = properties.getProperty( PROPERTY_USER_NAME );
            motDePasse = properties.getProperty( PROPERTY_PASSWORD );
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Unable to load the properties file " + FILE_PROPERTIES, e );
        }

        try {
            Class.forName( driver );
        } catch ( ClassNotFoundException e ) {
            throw new DAOConfigurationException( "Unable to find the Drive in classpath.", e );
        }

        DAOFactory instance = new DAOFactory( url, nomUtilisateur, motDePasse );
        return instance;
    }


    Connection getConnection() throws SQLException {
        return DriverManager.getConnection( url, username, password );
    }


    public UserDao getUserDao() {
        return new UserDaoImpl( this );
    }
    
    public ProductDao getProductDao() {
        return new ProductDaoImpl( this );
    }
    
    public CommentDao getCommentDao() {
        return new CommentDaoImpl( this );
    }
    
    public NewsDao getNewsDao() {
        return new NewsDaoImpl(this);
    }
}
