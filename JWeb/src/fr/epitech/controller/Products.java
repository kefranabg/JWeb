package fr.epitech.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epitech.modele.CreateProductForm;
import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.Product;
import fr.epitech.modele.ProductDao;

/**
 * Servlet handling the products web page
 * @author acca_b
 *
 */
public class Products extends HttpServlet {
	private ProductDao     productDao;

    public void init() throws ServletException {
        this.productDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getProductDao();
    }

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ArrayList<Product> products = productDao.getAllProducts();
	request.setAttribute("products", products);	
	this.getServletContext().getRequestDispatcher( "/WEB-INF/Products.jsp" ).forward( request, response );
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ArrayList<Product> products = productDao.getAllProducts();
	request.setAttribute("products", products);	

	this.getServletContext().getRequestDispatcher( "/WEB-INF/Products.jsp" ).forward( request, response );
}

}
