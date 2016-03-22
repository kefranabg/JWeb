package fr.epitech.controller;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import fr.epitech.modele.CreateNewsForm;
import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.News;
import fr.epitech.modele.NewsDao;
import fr.epitech.modele.User;
import fr.epitech.modele.UserDao;

/**
 * Servlet handling news adding
 * @author acca_b
 *
 */
public class AddNews extends HttpServlet {
	private NewsDao     newsDao;
	private UserDao     userDao;

    public void init() throws ServletException {
        this.newsDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getNewsDao();
        this.userDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getUserDao();
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
		catch (FileUploadException e) {	
		}
    	CreateNewsForm newsForm = new CreateNewsForm(newsDao);
		News news = newsForm.createNews(data, fileContent);

		request.setAttribute("newsForm", newsForm);

		try {
			Field f = newsForm.getClass().getField("errors");
			System.out.println(f.get(newsForm));
			if ((String) f.get(newsForm) != null)
			{
				List<User> users = userDao.list();
				 for (int i = 0; i < users.size(); i++) 
					 if (users.get(i).getNewsletter() == true)
						 SendMail.send(users.get(i).getEmail(), news.getTitle(), news.getDescription());
			}
			this.getServletContext().getRequestDispatcher("/Acceuil").forward( request, response );			
		}
		catch (NoSuchFieldException e)
		{
			System.out.println("No such field exception");
		}
		catch (IllegalAccessException e)
		{
			System.out.println("Illegal access exception");			
		}
   }
}
