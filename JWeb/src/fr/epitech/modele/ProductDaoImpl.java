package fr.epitech.modele;

import static fr.epitech.modele.ToolsDao.closeConnection;
import static fr.epitech.modele.ToolsDao.initializationPreparedRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * DAO implementation bean for the product
 * @author acca_b
 *
 */
public class ProductDaoImpl implements ProductDao {
	 private DAOFactory          daoFactory;
	 private static final String SQL_SELECT_BY_NAME = "SELECT * FROM Product WHERE name = ?";
	 private static final String SQL_SELECT_BY_ID = "SELECT * FROM Product WHERE id = ?";
	 private static final String SQL_DELETE_BY_ID = "DELETE FROM Product WHERE id = ?";
	 private static final String SQL_INSERT = "INSERT INTO Product (name, description, price, image_path, is_in_stock, creation_date) VALUES (?, ?, ?, ?, ?, NOW())";
	 private static final String SQL_SELECT_ALL = "SELECT * FROM Product";
	 private static final String SQL_UPDATE = "UPDATE Product SET name=?, description=?, price=?, image_path=?, is_in_stock=? WHERE id=?";

	 ProductDaoImpl( DAOFactory daoFactory ) {
	        this.daoFactory = daoFactory;
	    }
	
   @Override
   public Product findByName(String name) throws DAOException {
   	Connection connection = null;
       PreparedStatement preparedStatement = null;
       ResultSet resultSet = null;
       Product product = null;

       try {
           /* Récupération d'une connexion depuis la Factory */
           connection = (Connection) daoFactory.getConnection();
           preparedStatement = initializationPreparedRequest( connection, SQL_SELECT_BY_NAME, false, name);
           resultSet = preparedStatement.executeQuery();
           /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
           if ( resultSet.next() ) {
               product = map(resultSet);
           }
       } catch ( SQLException e ) {
           throw new DAOException( e );
       } finally {
       	closeConnection( resultSet, preparedStatement, connection );
       }

       return product;
   }

   @Override
   public Product findById(Long id) throws DAOException {
   	Connection connection = null;
       PreparedStatement preparedStatement = null;
       ResultSet resultSet = null;
       Product product = null;

       try {
           /* Récupération d'une connexion depuis la Factory */
           connection = (Connection) daoFactory.getConnection();
           preparedStatement = initializationPreparedRequest( connection, SQL_SELECT_BY_ID, false, id);
           resultSet = preparedStatement.executeQuery();
           /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
           if ( resultSet.next() ) {
               product = map(resultSet);
           }
       } catch ( SQLException e ) {
           throw new DAOException( e );
       } finally {
       	closeConnection( resultSet, preparedStatement, connection );
       }

       return product;
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
   
   /* Implémentation de la méthode creer() définie dans l'interface UtilisateurDao */
   @Override
   public void create(Product product) throws IllegalArgumentException, DAOException {
   	Connection connexion = null;
       PreparedStatement preparedStatement = null;
       ResultSet valeursAutoGenerees = null;
       
       try {
           /* Récupération d'une connexion depuis la Factory */
           connexion = (Connection) daoFactory.getConnection();
           preparedStatement = initializationPreparedRequest( connexion, SQL_INSERT, true, product.getName(),
        		   	product.getDescription(), product.getPrice(), product.getImagePath(), product.getIsInStock());
           int statut = preparedStatement.executeUpdate();
           /* Analyse du statut retourné par la requête d'insertion */
           if ( statut == 0 ) {
               throw new DAOException( "Product add failed" );
           }
           /* Récupération de l'id auto-généré par la requête d'insertion */
           valeursAutoGenerees = preparedStatement.getGeneratedKeys();
           if ( valeursAutoGenerees.next() ) {
               /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
               product.setId( valeursAutoGenerees.getLong( 1 ) );
           } else {
               throw new DAOException( "Product add failed" );
           }
       } catch ( SQLException e ) {
    	   System.out.println("Exception thrown  :" + e);
       } finally {
           closeConnection( valeursAutoGenerees, preparedStatement, connexion );
       }
   }
   
   @Override
   public void update(Product product) throws IllegalArgumentException, DAOException {
   	Connection connexion = null;
       PreparedStatement preparedStatement = null;
       ResultSet valeursAutoGenerees = null;
       
       try {
           connexion = (Connection) daoFactory.getConnection();
           
           preparedStatement = initializationPreparedRequest(connexion, SQL_UPDATE, true, product.getName(), product.getDescription(), product.getPrice(),
        		   product.getImagePath(), product.getIsInStock(), product.getId());
           int statut = preparedStatement.executeUpdate();
           if ( statut != 1 ) {
               throw new DAOException( "Product update failed" );
           }
       } catch ( SQLException e ) {
           throw new DAOException( e );
       } finally {
           closeConnection( valeursAutoGenerees, preparedStatement, connexion );
       }  
   }
   
   private static Product map(ResultSet resultSet) throws SQLException {
       Product  product = new Product(resultSet.getInt("price"), resultSet.getString("description"), resultSet.getString("name")
    		   , resultSet.getString("image_path"), resultSet.getBoolean("is_in_stock"));
       product.setId( resultSet.getLong("id") );
       product.setCreationDate( resultSet.getTimestamp("creation_date") );
       return product;
   }

@Override
public ArrayList<Product> getAllProducts() throws DAOException {
	Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ArrayList<Product> products = new ArrayList<Product>();
    
    try {
        connection = (Connection) daoFactory.getConnection();
        preparedStatement = initializationPreparedRequest( connection, SQL_SELECT_ALL, false);
        resultSet = preparedStatement.executeQuery();
       while (resultSet.next()) {
            products.add(map(resultSet));
        }
    } catch ( SQLException e ) {
        throw new DAOException( e );
    } finally {
    	closeConnection( resultSet, preparedStatement, connection );
    }
    
	return products;
}
}
