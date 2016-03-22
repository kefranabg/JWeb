package fr.epitech.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.News;
import fr.epitech.modele.NewsDao;
import fr.epitech.modele.Product;
import fr.epitech.modele.ProductDao;

/**
 * Servlet handling the Acceuil web page
 * @author acca_b
 *
 */
public class Acceuil extends HttpServlet {
	
	private NewsDao     newsDao;

    public void init() throws ServletException {
        this.newsDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getNewsDao();
    }

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ArrayList<News> news = newsDao.getAllNews();
	request.setAttribute("news", news);
	this.getServletContext().getRequestDispatcher( "/WEB-INF/Acceuil.jsp" ).forward( request, response );
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ArrayList<News> news = newsDao.getAllNews();
	request.setAttribute("news", news);
	this.getServletContext().getRequestDispatcher( "/WEB-INF/Acceuil.jsp" ).forward( request, response );
}

}
