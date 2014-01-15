package br.com.citframework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Classe utilizada para Zipar (compactar) arquivos.
 * @author emauri
 */
public class UtilZip {
    static final int BUFFER = 2048;
    public static void zipFile(String fileName, String fileNameOut) {
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(fileNameOut);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            //out.setMethod(ZipOutputStream.DEFLATED);
            byte data[] = new byte[BUFFER];
            // get a list of files from current directory

            FileInputStream fi = new FileInputStream(fileName);
            origin = new BufferedInputStream(fi, BUFFER);
            ZipEntry entry = new ZipEntry(UtilTratamentoArquivos.getFileName(fileName));
            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            origin.close();
            out.close();
            fi.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    /**
     * Classe utilizada para compactar uma arquivos e/ou pastas
     * @author rodrigo.oliveira
     * @throws IOException 
     */
    public static void zipFileOrDirectory(String fileNameOut, String fileNameIn) throws IOException{
    	
    	int cont;
    	byte[] dados = new byte[BUFFER];
	         
    	BufferedInputStream origem = null;
		FileInputStream streamDeEntrada = null;
		FileOutputStream destino = null;
		ZipOutputStream saida = null;
		ZipEntry entry = null;
		
		try {
	        destino = new FileOutputStream(new File(fileNameOut));
	        saida = new ZipOutputStream(new BufferedOutputStream(destino));
	        File file = new File(fileNameIn);
	        if(file.isDirectory()){
	            for (File arquivos : file.listFiles()) {
	                streamDeEntrada = new FileInputStream(arquivos);
	                origem = new BufferedInputStream(streamDeEntrada, BUFFER);
	                entry = new ZipEntry(arquivos.getName());
	                saida.putNextEntry(entry);
	                while((cont = origem.read(dados, 0, BUFFER)) != -1) {
	                    saida.write(dados, 0, cont);
	                }
	            }
	        }else{
	            streamDeEntrada = new FileInputStream(file);
	            origem = new BufferedInputStream(streamDeEntrada, BUFFER);
	            entry = new ZipEntry(file.getName());
	            saida.putNextEntry(entry);
	            while((cont = origem.read(dados, 0, BUFFER)) != -1) {
	                saida.write(dados, 0, cont);
	            }
	        }
	        
	        origem.close();
	        saida.close();
		} catch(IOException e) {
		    throw new IOException(e.getMessage());
		}

    }
   
}