package fr.epitech.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import fr.epitech.modele.CreateUserForm;
import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.User;
import fr.epitech.modele.UserDao;

/**
 * Servlet handling the registration
 * @author acca_b
 *
 */
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 private UserDao     userDao;

	    public void init() throws ServletException {
	        this.userDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getUserDao();
	    }
	    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/Inscription.jsp" ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		CreateUserForm userForm = new CreateUserForm(userDao);
		User user = userForm.createUser(request);

        request.setAttribute("user", user );
		request.setAttribute("userForm", userForm);
		
		
		if (userForm.getErrors() == null)
			this.getServletContext().getRequestDispatcher("/Connection").forward(request, response);
		else
			this.getServletContext().getRequestDispatcher("/WEB-INF/Inscription.jsp").forward( request, response );
	}
}
