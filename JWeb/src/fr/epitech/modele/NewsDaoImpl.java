package fr.epitech.modele;

import static fr.epitech.modele.ToolsDao.closeConnection;
import static fr.epitech.modele.ToolsDao.initializationPreparedRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * DAO implementation bean for the news
 * @author acca_b
 *
 */
public class NewsDaoImpl implements NewsDao {
	private static final String SQL_SELECT_ALL = "SELECT * FROM News ORDER BY creation_date DESC";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM News WHERE id = ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM News WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO News (description, title, image_path, creation_date) VALUES (?, ?, ?, NOW())";
	private static final String SQL_SELECT_BY_TITLE = "SELECT * FROM News WHERE title = ?";
	private DAOFactory          daoFactory;
	
	NewsDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
	
	@Override
	public void create(News news) throws DAOException {
		Connection connexion = null;
	       PreparedStatement preparedStatement = null;
	       ResultSet valeursAutoGenerees = null;
	       try {
	           connexion = (Connection) daoFactory.getConnection();
	           preparedStatement = initializationPreparedRequest( connexion, SQL_INSERT, true, news.getDescription(), news.getTitle(), news.getImagePath());
	           int statut = preparedStatement.executeUpdate();
	           if ( statut == 0 ) {
	               throw new DAOException( "News add failed" );
	           }
	           valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	           if ( valeursAutoGenerees.next() ) {
	               news.setId( valeursAutoGenerees.getLong( 1 ) );
	           } else {
	               throw new DAOException( "News add failed" );
	           }
	       } catch ( SQLException e ) {
	    	   System.out.println("Exception thrown  :" + e);
	       } finally {
	           closeConnection( valeursAutoGenerees, preparedStatement, connexion );
	       }
	}

	@Override
	public void delete(Long id) throws DAOException {
		Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      ResultSet resultSet = null;

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
	public ArrayList<News> getAllNews() throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ArrayList<News> news = new ArrayList<News>();
	    
	    try {
	        connection = (Connection) daoFactory.getConnection();
	        preparedStatement = initializationPreparedRequest( connection, SQL_SELECT_ALL, false);
	        resultSet = preparedStatement.executeQuery();
	       while (resultSet.next()) {
	            news.add(map(resultSet));
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	closeConnection( resultSet, preparedStatement, connection );
	    }
	    
		return news;
	}
	
	@Override
	public News findByName(String title) throws DAOException {
	  	Connection connection = null;
	       PreparedStatement preparedStatement = null;
	       ResultSet resultSet = null;
	       News news = null;

	       try {
	           connection = (Connection) daoFactory.getConnection();
	           preparedStatement = initializationPreparedRequest( connection, SQL_SELECT_BY_TITLE, false, title);
	           resultSet = preparedStatement.executeQuery();
	           if ( resultSet.next() ) {
	               news = map(resultSet);
	           }
	       } catch ( SQLException e ) {
	           throw new DAOException( e );
	       } finally {
	       	closeConnection( resultSet, preparedStatement, connection );
	       }

	       return news;
	}
	
	@Override
	public News findById(Long id) throws DAOException {
	  	Connection connection = null;
	       PreparedStatement preparedStatement = null;
	       ResultSet resultSet = null;
	       News news = null;

	       try {
	           connection = (Connection) daoFactory.getConnection();
	           preparedStatement = initializationPreparedRequest( connection, SQL_SELECT_BY_ID, false, id);
	           resultSet = preparedStatement.executeQuery();
	           if ( resultSet.next() ) {
	               news = map(resultSet);
	           }
	       } catch ( SQLException e ) {
	           throw new DAOException( e );
	       } finally {
	       	closeConnection( resultSet, preparedStatement, connection );
	       }

	       return news;
	}

	private static News map(ResultSet resultSet) throws SQLException {
	       News  news = new News(resultSet.getString("description"), resultSet.getString("title"), resultSet.getString("image_path"));
	       news.setId( resultSet.getLong("id") );
	       news.setCreationDate( resultSet.getTimestamp("creation_date") );
	       return news;
	   }

}
