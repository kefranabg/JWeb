package fr.epitech.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epitech.modele.CommentDao;
import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.News;
import fr.epitech.modele.NewsDao;
import fr.epitech.modele.Product;

/**
 * Servlet handling the deletion of a news
 * @author acca_b
 *
 */
public class DeleteNews extends HttpServlet {
	private NewsDao     newsDao;

    public void init() throws ServletException {
        this.newsDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getNewsDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
    	News news = newsDao.findById(Long.parseLong(request.getParameter("delete")));
    	
    	if (!news.getImagePath().equals("images/unknown.jpg"))
    	{
    		File file = new File("/Users/acca_b/Documents/bitbucket/jweb/JWeb/WebContent/" + news.getImagePath());
    		file.delete();
    	}
    	
    	newsDao.delete(Long.parseLong(request.getParameter("delete")));
    	response.sendRedirect("/JWeb/Acceuil");
	}
}
