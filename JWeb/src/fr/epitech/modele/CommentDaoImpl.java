package fr.epitech.modele;

import static fr.epitech.modele.ToolsDao.closeConnection;
import static fr.epitech.modele.ToolsDao.initializationPreparedRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * DAO implementation bean for the comments
 * @author acca_b
 *
 */
public class CommentDaoImpl implements CommentDao {
	 private DAOFactory          daoFactory;
	 private static final String SQL_DELETE_BY_USER_ID = "DELETE FROM Comment WHERE id_user = ?";
	 private static final String SQL_DELETE_BY_PRODUCT_ID = "DELETE FROM Comment WHERE id_product = ?";
	 private static final String SQL_INSERT = "INSERT INTO Comment (comment, id_user, id_product, title, creation_date) VALUES (?, ?, ?, ?, NOW())";
	 private static final String SQL_DELETE_BY_ID = "DELETE FROM Comment WHERE id = ?";
	 private static final String SQL_SELECT_COMMENTS_BY_PRODUCT = "SELECT * FROM Comment LEFT JOIN User ON Comment.id_user = User.id WHERE Comment.id_product = ? ORDER BY Comment.creation_date DESC";
	 
	 CommentDaoImpl( DAOFactory daoFactory ) {
	        this.daoFactory = daoFactory;
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
  public void deleteByProductId(Long idProduct) throws DAOException {
  	Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;

      try {
   	   connection = (Connection) daoFactory.getConnection();
          preparedStatement = (PreparedStatement) connection.prepareStatement(SQL_DELETE_BY_PRODUCT_ID);
          preparedStatement.setLong(1, idProduct);
          preparedStatement.executeUpdate();

      } catch ( SQLException e ) {
          throw new DAOException( e );
      } finally {
      	closeConnection( resultSet, preparedStatement, connection );
      }
  }
  
  @Override
  public void deleteByUserId(Long idUser) throws DAOException {
  	Connection connection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;

      try {
   	   connection = (Connection) daoFactory.getConnection();
          preparedStatement = (PreparedStatement) connection.prepareStatement(SQL_DELETE_BY_USER_ID);
          preparedStatement.setLong(1, idUser);
          preparedStatement.executeUpdate();

      } catch ( SQLException e ) {
          throw new DAOException( e );
      } finally {
      	closeConnection( resultSet, preparedStatement, connection );
      }
  }
  
  @Override
  public void create(Comment comment) throws IllegalArgumentException, DAOException {
  	Connection connexion = null;
      PreparedStatement preparedStatement = null;
      ResultSet valeursAutoGenerees = null;
      
      try {
          connexion = (Connection) daoFactory.getConnection();
          preparedStatement = initializationPreparedRequest( connexion, SQL_INSERT, true, comment.getComment(), comment.getIdUser(), comment.getIdProduct(), comment.getTitle());
          int statut = preparedStatement.executeUpdate();
           if ( statut == 0 ) {
              throw new DAOException( "Comment add failed" );
          }
          valeursAutoGenerees = preparedStatement.getGeneratedKeys();
          if ( valeursAutoGenerees.next() ) {
             comment.setId( valeursAutoGenerees.getLong(1));
          } else {
              throw new DAOException( "Comment add failed" );
          }
      } catch ( SQLException e ) {
   	   System.out.println("Exception thrown  :" + e);
      } finally {
          closeConnection( valeursAutoGenerees, preparedStatement, connexion );
      }
  }
  
  private static Comment map(ResultSet resultSet) throws SQLException {
	  Comment comment = new Comment(resultSet.getString("comment"), resultSet.getLong("id_user"), resultSet.getLong("id_product"), resultSet.getString("title"));
	  comment.setId( resultSet.getLong("id") );
	  comment.setCreationDate( resultSet.getTimestamp("creation_date") );
      return comment;
  }

@Override
public Map<Comment, User> getComments(Long idProduct) throws DAOException {
	Connection connection = null;
   PreparedStatement preparedStatement = null;
   ResultSet resultSet = null;
   Map<Comment, User> list = new HashMap<Comment, User>();
   
   try {
       connection = (Connection) daoFactory.getConnection();
       preparedStatement = initializationPreparedRequest( connection, SQL_SELECT_COMMENTS_BY_PRODUCT, false, idProduct);
       resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
           list.put(CommentDaoImpl.map(resultSet), UserDaoImpl.map(resultSet));
       }
   } catch ( SQLException e ) {
       throw new DAOException( e );
   } finally {
   	closeConnection( resultSet, preparedStatement, connection );
   }
   
	return list;
}
}

