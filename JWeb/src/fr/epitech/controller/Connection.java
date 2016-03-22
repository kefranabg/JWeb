package fr.epitech.controller;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.User;
import fr.epitech.modele.UserDao;

/**
 * Servlet handling the connexion web page
 * @author acca_b
 *
 */
public class Connection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private UserDao     userDao;

	    public void init() throws ServletException {
	        this.userDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getUserDao();
	    }
	    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/Connection.jsp" ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		User user = userDao.findByEmail(request.getParameter("userEmail"));
			 HttpSession session = request.getSession();
			if (user == null || user.getPassword().equals(request.getParameter("userPassword")) == false)
			{
				session.setAttribute( "usersession", null );
				request.setAttribute("error", "La connexion a échouée.");
				this.getServletContext().getRequestDispatcher("/WEB-INF/Connection.jsp").forward( request, response );
			}
			else
			{
				session.setAttribute( "usersession", user );
				response.sendRedirect("/JWeb/Acceuil");
			}
				
	}
}
