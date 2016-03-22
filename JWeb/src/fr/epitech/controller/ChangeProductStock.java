package fr.epitech.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.Product;
import fr.epitech.modele.ProductDao;

/**
 * Servlet handling the stock changing of a product
 * @author acca_b
 *
 */
public class ChangeProductStock extends HttpServlet {

	private ProductDao     productDao;
	
    public void init() throws ServletException {
        this.productDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getProductDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	Product product = productDao.findById(Long.parseLong(request.getParameter("productId")));
    	product.setIsInStock(!product.getIsInStock());
    	productDao.update(product);
    	response.sendRedirect(request.getContextPath() + "/" + request.getParameter("callfrom"));
   }
}
