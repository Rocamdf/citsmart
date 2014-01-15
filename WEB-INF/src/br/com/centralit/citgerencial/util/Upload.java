/*
 * Created on 15/07/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citgerencial.util;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import br.com.centralit.citgerencial.util.ParametrosConfig;
import br.com.citframework.util.Constantes;

/**
 * @author CentralIT
 */
public class Upload {
	public void doUpload(HttpServletRequest request, Collection colFilesUpload) throws Exception {
		DiskFileUpload fu = new DiskFileUpload(); 
		fu.setSizeMax(-1);
		fu.setSizeThreshold(4096);
		fu.setRepositoryPath("");

		List fileItems = fu.parseRequest(request);
		Iterator i = fileItems.iterator();
		FileItem fi;
		UploadItem upIt;
		File arquivo;
		Iterator itAux = colFilesUpload.iterator();
		while(itAux.hasNext()){
			upIt = (UploadItem)itAux.next();
			while(i.hasNext()){
				fi = (FileItem)i.next();
				if (upIt.getNomeArquivo().toUpperCase().trim().equals(fi.getName().toUpperCase().trim())){
					arquivo = new File(upIt.getPathArquivo() + "\\" + upIt.getNomeArquivo());
					fi.write(arquivo);
				}
			}
		}
	}
	
	public HashMap[] doUploadAll(HttpServletRequest request) throws Exception {
		HashMap[] hshRetorno =  new HashMap[2];
		DiskFileUpload fu = new DiskFileUpload(); 
		fu.setSizeMax(-1);
		fu.setSizeThreshold(4096);
		
		String DIRETORIO_TEMP_UPLOAD_ARQUIVOS = ParametrosConfig.getInstance().getValueStr(request, "GERAL", "DIRETORIO_TEMP_UPLOAD_ARQUIVOS");
		if (DIRETORIO_TEMP_UPLOAD_ARQUIVOS == null){
			DIRETORIO_TEMP_UPLOAD_ARQUIVOS = "";
		}
		
		String str = Constantes.getValue("DISKFILEUPLOAD_REPOSITORYPATH");
		if (str == null){
			str = "";
		}
		if (str == null || str.equalsIgnoreCase("")){
			str = DIRETORIO_TEMP_UPLOAD_ARQUIVOS;
		}
			
		fu.setRepositoryPath(str);
		
		hshRetorno[0] = new HashMap();  //Retorna os campos de formulário
		hshRetorno[1] = new HashMap();  //Retorna os nomes de arquivos
		
		List fileItems = fu.parseRequest(request);
		Iterator i = fileItems.iterator();
		FileItem fi;
		while(i.hasNext()){
			fi = (FileItem)i.next();
			if (!fi.isFormField()){
				hshRetorno[1].put(Util.getNameFile(fi.getName()), fi);
				hshRetorno[0].put(fi.getFieldName().toUpperCase(), Util.getNameFile(fi.getName()));
				request.setAttribute(fi.getFieldName(), Util.getNameFile(fi.getName()));
			} else {
				hshRetorno[0].put(fi.getFieldName().toUpperCase(), fi.getString());	
				request.setAttribute(fi.getFieldName(), fi.getString());
			}
		}
		return hshRetorno;
	}

    public HashMap[] uploadW(HttpServletRequest request)throws Exception {    	
    	boolean isMultipart = ServletFileUpload.isMultipartContent(request);     	
    	HashMap[] hshRetorno =  new HashMap[2];
    	   if (isMultipart) {
    			DiskFileUpload fu = new DiskFileUpload(); 
    			fu.setSizeMax(50*1024*1024);
    			fu.setSizeThreshold(4096);
    			
    			String DIRETORIO_TEMP_UPLOAD_ARQUIVOS = ParametrosConfig.getInstance().getValueStr(request, "GERAL", "DIRETORIO_TEMP_UPLOAD_ARQUIVOS");
    			if (DIRETORIO_TEMP_UPLOAD_ARQUIVOS == null){
    				DIRETORIO_TEMP_UPLOAD_ARQUIVOS = "";
    			}
    			
    			String str = Constantes.getValue("DISKFILEUPLOAD_REPOSITORYPATH");
    			if (str == null){
    				str = "";
    			}
    			if (str == null || str.equalsIgnoreCase("")){
    				str = DIRETORIO_TEMP_UPLOAD_ARQUIVOS;
    			}
    				
    			fu.setRepositoryPath(str);
    			
    			hshRetorno[0] = new HashMap();  //Retorna os campos de formulário teste para subir
    			hshRetorno[1] = new HashMap();  //Retorna os nomes de arquivos
    			
    			List fileItems = fu.parseRequest(request);
    			Iterator i = fileItems.iterator();
    			FileItem fi;
    			while(i.hasNext()){
    				fi = (FileItem)i.next();
    				if (!fi.isFormField()){
    					hshRetorno[1].put(Util.getNameFile(fi.getName()), fi);
    					hshRetorno[0].put(fi.getFieldName().toUpperCase(), Util.getNameFile(fi.getName()));
    					request.setAttribute(fi.getFieldName(), Util.getNameFile(fi.getName()));
    				} else {
    					hshRetorno[0].put(fi.getFieldName().toUpperCase(), fi.getString());	
    					request.setAttribute(fi.getFieldName(), fi.getString());
    				}
    			}
    	   }      	   
    	   
    	       return hshRetorno;
    }

    
    public String uploadTeste(HttpServletRequest request)throws Exception {    	
    	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
    	String saida = "N";
    	   if (isMultipart) {    		
    			saida = "S";
    	   }   	     	   
      return saida;
    }

}
