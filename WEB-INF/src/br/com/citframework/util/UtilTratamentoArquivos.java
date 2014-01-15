package br.com.citframework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class UtilTratamentoArquivos {
	/**
	 * Obtem o nome de um arquivo
	 * @param path
	 * @param separador
	 * @return
	 */
	public static final String getFileName(String path, String separador) {
		StringTokenizer st = new StringTokenizer(path, separador);
		String result = "";
		while (st.hasMoreElements()) {
			result = st.nextElement().toString();
		}
		return result;
	}
	/**
	 * Pega o nome do arquivo
	 * @return
	 */
	public static final String getFileName(String fullPathFile){
		int tam = fullPathFile.length() - 1;
		String nomeFile = "";
		for (int i = tam; i >= 0; i--){
			if (fullPathFile.charAt(i) == '\\' || fullPathFile.charAt(i) == '/')
				break;
			else
			nomeFile = fullPathFile.charAt(i) + nomeFile;
		}
		return nomeFile;
	}	
	/**
	 * Faz a leitura de um arqivo texto e retorna a String que contem no arquivo.
	 * @param arquivo
	 * @return
	 */
	public static String getStringTextFromFileTxt(String arquivo) {
		String retorno = "";
		try {
			FileInputStream arq = new FileInputStream(arquivo);

			BufferedReader br = new BufferedReader(new InputStreamReader(arq, "ISO-8859-1"));
			while (br.ready()) {
				retorno += br.readLine() + "\n";
			}
			br.close();
			arq.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retorno;
	}
	
	public static String getStringTextFromFileTxtTemplate(String arquivo) {
		String retorno = "";
		try {
			FileInputStream arq = new FileInputStream(arquivo);

			BufferedReader br = null;
			if(System.getProperty("os.name").contains("Windows")){
				br = new BufferedReader(new InputStreamReader(arq, "ISO-8859-1"));
			} else {
				br = new BufferedReader(new InputStreamReader(arq, "UTF-8"));
			}
			while (br.ready()) {
				retorno += br.readLine() + "\n";
			}
			br.close();
			arq.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retorno;
	}
	
	public static String getStringTextFromFileTxt(String arquivo, String encode) {
		String retorno = "";
		try {
			FileInputStream arq = new FileInputStream(arquivo);
			
			BufferedReader br = null;
			if (encode == null || encode.trim().equalsIgnoreCase("")){
				br = new BufferedReader(new InputStreamReader(arq));
			}else{
				br = new BufferedReader(new InputStreamReader(arq, encode));
			}
			while (br.ready()) {
				retorno += br.readLine() + "\n";
			}
			br.close();
			arq.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
	/**
	 * Faz a leitura de um arqivo texto e retorna a String que contem no arquivo, mas sem quebras de linha.
	 * @param arquivo
	 * @return
	 */
	public static String getStringTextFromFileTxtSemQuebra(String arquivo) {
		String retorno = "";
		try {
			FileInputStream arq = new FileInputStream(arquivo);

			BufferedReader br = new BufferedReader(new InputStreamReader(arq));
			while (br.ready()) {
				retorno += br.readLine() + " ";
			}
			br.close();
			arq.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retorno;
	}	
	/**
	 * Faz a leitura de um arqivo texto e retorna a String que contem no arquivo, mas tudo em uma mesma linha.
	 * @param arquivo
	 * @return
	 */
	public static String getStringTextFromFileTxtInline(String arquivo) {
		String retorno = "";
		try {
			FileInputStream arq = new FileInputStream(arquivo);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(arq));
			while (br.ready()) {
				retorno += br.readLine();
			}
			br.close();
			arq.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
	
	
	public static String fromInputStreamToString(InputStream arq) throws Exception {
		String resposta ="";
		

		BufferedReader br = new BufferedReader(new InputStreamReader(arq));
		while (br.ready()) {
			resposta = br.readLine();
		}
		br.close();

		return resposta;
	}

	public static List fromInputStreamToList(InputStream arq) throws Exception {
		List lista = new ArrayList();
		BufferedReader br = new BufferedReader(new InputStreamReader(arq));
		while (br.ready()) {
			lista.add(br.readLine());
		}
		br.close();

		return lista;
	}
	
	
	/**
	 * Faz a leitura de um arquivo Texto e gera um List com o conteudo.
	 * @param arquivo
	 * @return
	 * @throws Exception
	 */
	public static List lerFileTXTGenerateList(String arquivo) throws Exception {
		List lista = new ArrayList();
		FileInputStream arq = new FileInputStream(arquivo);

		BufferedReader br = new BufferedReader(new InputStreamReader(arq));
		while (br.ready()) {
			lista.add(br.readLine());
		}
		br.close();
		arq.close();

		return lista;
	}
	/**
	 * Faz a leitura de um arquivo Texto e gera um List com o conteudo.
	 * @param arquivo
	 * @return
	 * @throws Exception
	 */
	public static StringBuffer lerFileTXTGenerateStringBuffer(String arquivo) throws Exception {
		StringBuffer strBuffer = new StringBuffer();
		FileInputStream arq = new FileInputStream(arquivo);

		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(arq));
			if(br != null){
				while (br.ready()) {
					strBuffer.append(br.readLine());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null){
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(arq != null){
					arq.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		arq = null;
		br = null;
		
		return strBuffer;
	}	
	/**
	 * Gera um lista de beans atraves de um arquivo texto. 
	 * @param arquivo
	 * @param separador
	 * @param campos (Lista de campos)
	 * @param classe (Classe a converter)
	 * @return
	 * @throws Exception
	 */
	public static List carregaBeansFromFileTxt(String arquivo, String separador, List campos, Class classe) throws Exception {

		List lstArq = lerFileTXTGenerateList(arquivo);
		List result = new ArrayList();

		Iterator itArq = lstArq.iterator();
		while (itArq.hasNext()) {
			String linha = itArq.next().toString();
			int i = 0;
			StringTokenizer stok = new StringTokenizer(linha, separador);
			Object obj = classe.newInstance();
			while (stok.hasMoreTokens()) {
				String valor = stok.nextToken();
				String campo = campos.get(i).toString();
				Reflexao.setPropertyValueAsString(obj, campo, valor);
				i++;
			}
			result.add(obj);
		}
		return result;

	}
	/**
	 * gera um arquivo TXT atraves de uma lista.
	 * @param arquivo
	 * @param lista
	 * @throws IOException
	 */
	public static final void geraFileTXT(String arquivo, List lista, FileOutputStream fos) throws IOException {
		PrintStream out = new PrintStream(fos);
		Iterator it = lista.iterator();
		while (it.hasNext()) {
			out.println(it.next().toString());
		}
	}	
	public static final void geraFileTXT(String arquivo, List lista, PrintStream out) throws IOException {
		Iterator it = lista.iterator();
		while (it.hasNext()) {
			out.println(it.next().toString());
		}
	}	
	public static final void geraFileTXT(String arquivo, List lista) throws IOException {
		FileOutputStream fos = new FileOutputStream(arquivo); //Novo Arquivo
		try{
			geraFileTXT(arquivo, lista, fos);
		}catch(Exception e){
		}			
		try{
			fos.close();
		}catch(Exception e){
			//throw new IOException("Erro ao fechar arquivo "+arquivo); 
		}	
		fos = null;
	}
	public static final void geraFileTXT(String arquivo, List lista, boolean append) throws IOException {
		File f = new File(arquivo);
		f.createNewFile();
		FileOutputStream fos = new FileOutputStream(arquivo, append); //Faz append de acordo com o parametro passado.
		geraFileTXT(arquivo, lista, fos);	
		try{
			fos.close();
		}catch(Exception e){
			throw new IOException("Erro ao fechar arquivo "+arquivo); 
		}		
	}
	/**
	 * Gera um arquivo atraves de uma string.
	 * @param arquivo
	 * @param texto
	 * @throws IOException
	 */
	public static final void geraFileTxtFromString(String arquivo, String texto) throws IOException {
		FileOutputStream fos = new FileOutputStream(arquivo);
		PrintStream out = null;
		try{
			out = new PrintStream(fos);
			out.print(texto);
		}catch(Exception e){
		}
		try{
			out.close();
		}catch(Exception e){
		}	
		try{
			fos.close();
		}catch(Exception e){
			//throw new IOException("Erro ao fechar arquivo "+arquivo); 
		}
		out = null;
		fos = null;
	}
	/**
	 * Copia um arquivo.
	 * @param inFile
	 * @param outFile
	 * @return
	 */
	public static boolean copyFile(String inFile, String outFile) {
		InputStream is = null;
		OutputStream os = null;
		byte[] buffer;
		boolean success = true;
		try {
			is = new FileInputStream(inFile);
			os = new FileOutputStream(outFile);
			buffer = new byte[is.available()];
			is.read(buffer);
			os.write(buffer);
			
		} catch (IOException e) {
			success = false;
		} catch (OutOfMemoryError e) {
			success = false;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
			}			
		}
		is = null;
		os = null;
		return success;
	}
	/*
	 * Faz a leitura de um arquivo e retorna o Array de Butes
	 */
	public static byte[] getBytesFromFile(File file) throws Exception 
	{
		long length = 0;
		InputStream is = null;
		try { 
			is = new FileInputStream(file);
			length = file.length(); 
		}
		catch (IOException e){
			System.out.println("Não foi possível ler o arquivo.");
			return null;
		}			
		// You cannot create an array using a long type. 
		// It needs to be an int type. 
		// Before converting to an int type, check 
		// to ensure that file is not larger than Integer.MAX_VALUE. 
		if (length > Integer.MAX_VALUE) 
		{
			throw new Exception("Arquivo muito grande >>> " + file.getName());
		} 
		try {
			byte[] bytes = new byte[(int) length]; 
			// Read in the bytes 
			int offset = 0; 
			int numRead = 0; 
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
			{ 
				offset += numRead;
			} 
			// Ensure all the bytes have been read in 
			if (offset < bytes.length) 
			{ 
				throw new Exception("Não foi possível ler o arquivo completamente >>> " + file.getName());
			} 
			// Close the input stream and return bytes 
			is.close(); 
			return bytes;
		}
		catch (IOException e){
			throw e;
		}
	}	
}
