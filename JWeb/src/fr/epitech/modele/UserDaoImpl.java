package fr.epitech.modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import static fr.epitech.modele.ToolsDao.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * DAO implementation bean for the user
 * @author acca_b
 *
 */
public class UserDaoImpl implements UserDao {
	 private DAOFactory          daoFactory;
	 private static final String SQL_SELECT_BY_EMAIL = "SELECT id, email, name, password, registration_date, newsletter, imagePath, isAdmin FROM User WHERE email = ?";
	 private static final String SQL_SELECT_BY_NAME = "SELECT id, email, name, password, registration_date, newsletter, imagePath, isAdmin FROM User WHERE name = ?";
	 private static final String SQL_SELECT_BY_ID = "SELECT id, email, name, password, registration_date, newsletter, imagePath, isAdmin FROM User WHERE id = ?";
	 private static final String SQL_INSERT = "INSERT INTO User (email, name, password, registration_date, newsletter, imagePath, isAdmin) VALUES (?, ?, ?, NOW(), ?, ?, ?)";
	 private static final String SQL_DELETE_BY_ID = "DELETE FROM User WHERE id = ?";
	 private static final String SQL_SELECT = "SELECT * FROM User ORDER BY id";
	 private static final String SQL_UPDATE_USER = "UPDATE User SET email=?, name=?, password=?, newsletter=?, imagePath=? WHERE id = ?";
	 
	    UserDaoImpl( DAOFactory daoFactory ) {
	        this.daoFactory = daoFactory;
	    }
	
    @Override
    public User findByEmail( String email ) throws DAOException {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = (Connection) daoFactory.getConnection();
            preparedStatement = initializationPreparedRequest( connection, SQL_SELECT_BY_EMAIL, false, email );
            resultSet = preparedStatement.executeQuery();
            if ( resultSet.next() ) {
                user = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	closeConnection( resultSet, preparedStatement, connection );
        }

        return user;
    }

    @Override
    public User findByName( String name ) throws DAOException {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = (Connection) daoFactory.getConnection();
            preparedStatement = initializationPreparedRequest( connection, SQL_SELECT_BY_NAME, false, name);
            resultSet = preparedStatement.executeQuery();

            if ( resultSet.next() ) {
                user = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	closeConnection( resultSet, preparedStatement, connection );
        }

        return user;
    }

    @Override
    public User findById( Long id) throws DAOException {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = (Connection) daoFactory.getConnection();
            preparedStatement = initializationPreparedRequest( connection, SQL_SELECT_BY_ID, false, id);
            resultSet = preparedStatement.executeQuery();

            if ( resultSet.next() ) {
                user = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	closeConnection( resultSet, preparedStatement, connection );
        }

        return user;
    }

    
    @Override
    public void delete(Long id) throws DAOException {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = null;

        try {
     	   connection = (Connection) daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQL_DELETE_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	closeConnection( resultSet, preparedStatement, connection );
        }
    }
    
    @Override
    public List<User> list() throws DAOException {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> listUser = new ArrayList<User>();

        try {
            connection = (Connection) daoFactory.getConnection();
            preparedStatement = (PreparedStatement) connection.prepareStatement(SQL_SELECT);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	listUser.add(map(resultSet));
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
        	closeConnection( resultSet, preparedStatement, connection );
        }

        return listUser;
    }
    
    @Override
    public void create( User user ) throws IllegalArgumentException, DAOException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = (Connection) daoFactory.getConnection();
            preparedStatement = initializationPreparedRequest( connexion, SQL_INSERT, true, user.getEmail(), user.getName(), user.getPassword() , user.getNewsletter(), user.getImagePath(), user.getIsAdmin());
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "User add failed" );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                user.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "User add failed" );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            closeConnection( valeursAutoGenerees, preparedStatement, connexion );
        }
    }
    
    public void update(User user) throws IllegalArgumentException, DAOException {
    	Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = (Connection) daoFactory.getConnection();
            preparedStatement = initializationPreparedRequest( connexion, SQL_UPDATE_USER, true, user.getEmail(), user.getName(), user.getPassword() , user.getNewsletter(), user.getImagePath(), user.getId());
            int statut = preparedStatement.executeUpdate();

            if ( statut != 1 ) {
                throw new DAOException( "User update failed" );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            closeConnection( valeursAutoGenerees, preparedStatement, connexion );
        }    	
    }
    
    public static User map( ResultSet resultSet ) throws SQLException {
        User user = new User(resultSet.getString("password"), resultSet.getString("email"), resultSet.getString("name"), resultSet.getBoolean("newsletter"));
        user.setId( resultSet.getLong("id") );
        user.setRegistrationDate( resultSet.getTimestamp("registration_date") );
        user.setIsAdmin(resultSet.getBoolean("isAdmin"));
        user.setImagePath(resultSet.getString("imagePath"));
        return user;
    }
}