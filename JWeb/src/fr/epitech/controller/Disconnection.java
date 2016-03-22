package fr.epitech.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.Product;
import fr.epitech.modele.ProductDao;

/**
 * Servlet handling the disconnection
 * @author acca_b
 *
 */
public class Disconnection extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("/JWeb/Products");
	}
}
