package fr.epitech.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import fr.epitech.modele.CreateUserForm;
import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.User;
import fr.epitech.modele.UserDao;

/**
 * Servlet handling the account view
 * @author acca_b
 *
 */
public class UserAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	 private UserDao     userDao;

	    public void init() throws ServletException {
	        this.userDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getUserDao();
	    }
	    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null)
		{
			response.sendRedirect(request.getContextPath() + "/Connection");
			return;
		}
		User oldUser = (User) session.getAttribute("usersession");
		if (oldUser == null)
		{
			response.sendRedirect(request.getContextPath() + "/Connection");
			return;
		}
		if (oldUser.getIsAdmin() == true)
			request.setAttribute("users", userDao.list());
		this.getServletContext().getRequestDispatcher( "/WEB-INF/UserAccount.jsp" ).forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		InputStream fileContent = null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload( factory );
			List<FileItem> uploadItems = upload.parseRequest( request );
			for( FileItem uploadItem : uploadItems )
			{
				if( uploadItem.isFormField() )
				{
					String fieldName = uploadItem.getFieldName();
					String value = uploadItem.getString("UTF-8");
					data.put(fieldName, value);
				}
				else
				{
					String fieldName = uploadItem.getFieldName();
					String fileName = FilenameUtils.getName(uploadItem.getName());
					fileContent = uploadItem.getInputStream();
					data.put(fieldName, fileName);
				}
			}
		}
		catch (FileUploadException e)
		{
			
		}

		CreateUserForm userForm = new CreateUserForm(userDao);
		HttpSession session = request.getSession(false);
		if (session == null)
		{
			response.sendRedirect("/JWeb/Connection");
			return;
		}
		User oldUser = (User) session.getAttribute("usersession");
		if (oldUser == null)
		{
			response.sendRedirect("/JWeb/Connection");
			return;
		}
		User user = userForm.updateUser(data, oldUser, fileContent);
		if (oldUser.getIsAdmin() == true)
			request.setAttribute("users", userDao.list());
		session.setAttribute( "usersession", user );
		request.setAttribute("userForm", userForm);
		this.getServletContext().getRequestDispatcher("/WEB-INF/UserAccount.jsp").forward( request, response );
	}


}
