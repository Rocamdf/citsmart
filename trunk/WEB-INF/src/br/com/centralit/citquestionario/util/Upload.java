/*
 * Created on 15/07/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citquestionario.util;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

import br.com.centralit.citcorpore.util.UploadItem;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilTratamentoArquivos;

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
		String str = Constantes.getValue("DISKFILEUPLOAD_REPOSITORYPATH");
		if (str == null){
			str = "";
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
				hshRetorno[1].put(UtilTratamentoArquivos.getFileName(fi.getName()), fi);
				hshRetorno[0].put(fi.getFieldName().toUpperCase(), UtilTratamentoArquivos.getFileName(fi.getName()));
				request.setAttribute(fi.getFieldName(), UtilTratamentoArquivos.getFileName(fi.getName()));
			} else {
				hshRetorno[0].put(fi.getFieldName().toUpperCase(), fi.getString());	
				request.setAttribute(fi.getFieldName(), fi.getString());
			}
		}
		return hshRetorno;
	}
}
