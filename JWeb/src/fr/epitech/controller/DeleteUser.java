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
import fr.epitech.modele.User;
import fr.epitech.modele.UserDao;

/**
 * Servlet handling deletion of a user
 * @author acca_b
 *
 */
public class DeleteUser extends HttpServlet {
	private UserDao     userDao;
	private CommentDao     commentDao;

    public void init() throws ServletException {
        this.userDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getUserDao();
        this.commentDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getCommentDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = userDao.findById(Long.parseLong(request.getParameter("delete")));
    	
   		if (user.getImagePath() != null && !user.getImagePath().equals("images/unknown.jpg"))
   		{
   			File file = new File("/Users/acca_b/Documents/bitbucket/jweb/JWeb/WebContent/" + user.getImagePath());
   			file.delete();
   		}
    	
   		userDao.delete(Long.parseLong(request.getParameter("delete")));
   		commentDao.delete(Long.parseLong(request.getParameter("delete")));
   		response.sendRedirect("/JWeb/UserAccount");
    }
}