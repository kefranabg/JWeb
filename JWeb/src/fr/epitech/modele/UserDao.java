package fr.epitech.modele;

import java.util.List;

/**
 * DAO interface bean for the user
 * @author acca_b
 *
 */
public interface UserDao {

    void create(User utilisateur) throws DAOException;

    User findByEmail( String email ) throws DAOException;
    
    User findByName( String name ) throws DAOException;
    
    User findById(Long id) throws DAOException;
    
    List<User> list() throws DAOException;
    
    void update(User user) throws DAOException;
    
	void delete(Long id) throws DAOException;

}