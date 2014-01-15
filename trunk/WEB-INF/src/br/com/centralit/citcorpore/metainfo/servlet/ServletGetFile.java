package br.com.centralit.citcorpore.metainfo.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.citframework.util.UtilTratamentoArquivos;

public class ServletGetFile extends HttpServlet {
	/**
	 * Metodo doGet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
											throws ServletException, IOException{
  		processRequest(request, response);
	}

	/**
	 * Metodo doPost
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
											throws ServletException, IOException {
		processRequest(request, response);
	}
	/**
	 * Processa as requisicoes.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
													 throws ServletException, IOException{
		String file = request.getParameter("file");
		if (file == null || file.trim().equalsIgnoreCase("")){
			return;
		}
		String fileName = request.getParameter("fileName");
		if (fileName == null || fileName.trim().equalsIgnoreCase("")){
			fileName = "export.txt";
		}
		String fileType = request.getParameter("type");
		if (fileType == null || fileType.trim().equalsIgnoreCase("")){
		    fileType = "txt";
		}		
		try {
			byte[] bytes = UtilTratamentoArquivos.getBytesFromFile(new File(file));
			ServletOutputStream outputStream = response.getOutputStream();
			if (fileType.equalsIgnoreCase("txt")){
			    response.setContentType("text/plain");
			}
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);					
			response.setContentLength(bytes.length);
			outputStream.write(bytes);
			outputStream.flush();
			outputStream.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
