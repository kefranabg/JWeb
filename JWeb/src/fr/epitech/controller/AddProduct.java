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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import fr.epitech.modele.CreateProductForm;
import fr.epitech.modele.DAOFactory;
import fr.epitech.modele.Product;
import fr.epitech.modele.ProductDao;

/**
 * Servlet handling the product adding
 * @author acca_b
 *
 */
public class AddProduct extends HttpServlet {
	private ProductDao     productDao;

    public void init() throws ServletException {
        this.productDao = ( (DAOFactory) getServletContext().getAttribute("daofactory") ).getProductDao();
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

		CreateProductForm productForm = new CreateProductForm(productDao);

		Product product = productForm.createProduct(data, fileContent);

		request.setAttribute("productForm", productForm);
				
		this.getServletContext().getRequestDispatcher("/Products").forward( request, response );
   }
}