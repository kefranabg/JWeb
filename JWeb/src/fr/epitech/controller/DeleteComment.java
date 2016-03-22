package fr.epitech.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epitech.modele.CommentDao;
import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.ProductDao;

/**
 * Servlet handling the deletion of a comment
 * @author acca_b
 *
 */
public class DeleteComment extends HttpServlet {
	private CommentDao     commentDao;

    public void init() throws ServletException {
        this.commentDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getCommentDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	commentDao.delete(Long.parseLong(request.getParameter("delete")));
    	response.sendRedirect("/JWeb/ProductView?product=" + request.getParameter("product"));
	}
}