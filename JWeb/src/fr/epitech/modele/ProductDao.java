package fr.epitech.modele;

import java.util.ArrayList;

/**
 * DAO interface bean for the product
 * @author acca_b
 *
 */
public interface ProductDao {

    void create(Product product) throws DAOException;

    Product findById(Long id) throws DAOException;
    
    void update(Product product) throws DAOException;
    
    Product findByName(String name) throws DAOException;
    
    void delete(Long id) throws DAOException;

    ArrayList<Product> getAllProducts() throws DAOException;
}