package fr.epitech.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epitech.modele.Comment;
import fr.epitech.modele.CommentDao;
import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.ProductDao;
import fr.epitech.modele.Product;
import fr.epitech.modele.User;

/**
 * Servlet handling the detailed view of a product
 * @author acca_b
 *
 */
public class ProductView extends HttpServlet {
	private ProductDao     productDao;
	private CommentDao     commentDao;

    public void init() throws ServletException {
        this.productDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getProductDao();
        this.commentDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getCommentDao();
    }

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
	Product product = productDao.findById(Long.parseLong(request.getParameter("product")));
	Map<Comment, User> list = commentDao.getComments(product.getId());
	
	Iterator it = list.entrySet().iterator();

	Comment[] comments = new Comment[list.size()];
	User[] users  = new User[list.size()];
	int i = 0;
   while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            comments[i] = (Comment) pairs.getKey();
            users[i] = (User) pairs.getValue();
            i++;        
}
	request.setAttribute("product", product);
	request.setAttribute("users", users);
	request.setAttribute("comments", comments);
	this.getServletContext().getRequestDispatcher( "/WEB-INF/Product.jsp" ).forward( request, response );
}
}
