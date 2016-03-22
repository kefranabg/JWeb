package fr.epitech.modele;

import java.util.ArrayList;
import java.util.Map;

/**
 * DAO interface bean for the comments
 * @author acca_b
 *
 */
public interface CommentDao {
	void create(Comment comment) throws DAOException;
    
    void delete(Long id) throws DAOException;

    void deleteByProductId(Long id) throws DAOException;
    
    void deleteByUserId(Long id) throws DAOException;
    
    Map<Comment, User> getComments(Long idProduct) throws DAOException;

}
