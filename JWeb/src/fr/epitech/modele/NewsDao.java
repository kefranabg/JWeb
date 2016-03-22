package fr.epitech.modele;

import java.util.ArrayList;

/**
 * DAO interface bean for the news
 * @author acca_b
 *
 */
public interface NewsDao {

    void create(News news) throws DAOException;
    
    News findByName(String title) throws DAOException;
    News findById(Long id) throws DAOException;
    
    void delete(Long id) throws DAOException;

    ArrayList<News> getAllNews() throws DAOException;
}
