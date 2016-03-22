package fr.epitech.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epitech.modele.CommentDao;
import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.Product;
import fr.epitech.modele.ProductDao;

/**
 * Servlet handling the deletion of a product
 * @author acca_b
 *
 */
public class DeleteProduct extends HttpServlet {
	private ProductDao     productDao;
	private CommentDao     commentDao;

    public void init() throws ServletException {
        this.commentDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getCommentDao();
        this.productDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getProductDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	Product product = productDao.findById(Long.parseLong(request.getParameter("delete")));
    	
    	if (!product.getImagePath().equals("images/unknown.jpg"))
    	{
    		File file = new File("/Users/acca_b/Documents/bitbucket/jweb/JWeb/WebContent/" + product.getImagePath());
        	file.delete();
    	}
    	
    	productDao.delete(Long.parseLong(request.getParameter("delete")));
    	commentDao.deleteByProductId(Long.parseLong(request.getParameter("delete")));
    	response.sendRedirect("/JWeb/Products");
	}
}