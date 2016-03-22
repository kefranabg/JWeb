package fr.epitech.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epitech.modele.Comment;
import fr.epitech.modele.CommentDao;
import fr.epitech.modele.DAOFactory;
 
/**
 * Servlet handling the Comment adding
 * @author acca_b
 *
 */
public class AddComment extends HttpServlet {

	private CommentDao     commentDao;
	
    public void init() throws ServletException {
        this.commentDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getCommentDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
    	Comment comment = new Comment(request.getParameter("comment"), Long.parseLong(request.getParameter("id_user")), Long.parseLong(request.getParameter("id_product")), request.getParameter("title"));
    	if (comment.isValid(commentDao) == true)
    		commentDao.create(comment);
    	System.out.println(request.getParameter("product"));
    	response.sendRedirect("/JWeb/ProductView?product=" + request.getParameter("product"));
   }
}
