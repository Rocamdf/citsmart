package br.com.centralit.citcorpore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

/**
 * Precisei criar esta classe, para utilizar este código tanto na indexação Lucene como na tela Base de Conhecimento...
 * @author euler.ramos
 *
 */
public class Arquivo {
	private String link;
	private String nome;
	private String extensao;
	private String conteudo;
	
	
	public Arquivo(ControleGEDDTO controleGEDDTO) {
		super();
		this.nome = controleGEDDTO.getNomeArquivo();
		this.extensao = controleGEDDTO.getExtensaoArquivo();
		try {
			this.link = this.getFromGed(controleGEDDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.conteudo = this.buscaConteudo();
	}
	
	public Arquivo() {
		
	}

	public String getLink() {
		return link;
	}

	public String getNome() {
		return nome;
	}

	public String getExtensao() {
		return extensao;
	}

	public String getConteudo() {
		return conteudo;
	}

	private String buscaConteudo(){
		if (this.link!=null){
			StringBuilder textoDocumento = new StringBuilder();

			String[] arrayMicrosoftWord;

			File arquivo = new File(this.link);
			if (arquivo.exists()) {
				if (this.getExtensao().equals("pdf")) {
					try {
						textoDocumento.append(this.extrairFormatoPDF(this.link));
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (this.getExtensao().equals("doc")) {
					arrayMicrosoftWord = this
							.extrairFormatoMicrosoftWord(this.link);
					for (int i = 0; i < arrayMicrosoftWord.length; i++) {
						textoDocumento.append(arrayMicrosoftWord[i]);
					}

				} else if (this.getExtensao().equals("docx")) {
					textoDocumento.append(this.extrairFormatoDOCX(this.link));

				} else if (this.getExtensao().equals("xls")) {
					textoDocumento.append(this.extrairFormatoXLS(this.link));

				} else if (this.getExtensao().equals("xlsx")) {
					textoDocumento.append(this.extrairFormatoXLSX(this.link));
				} else if (this.getExtensao().equals("txt")) {
					textoDocumento.append(extrairFormatoTxt(this.link));
				}
			}		
			
			return textoDocumento.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * Extrai o texto nos formatos suportados pelo Microsoft Word. Na necessidade presente sÃ³ serÃ¡ necessÃ¡rios os formatos DOC e DOCX.
	 * 
	 * @param caminhoDocumento
	 * @param nomeArquivo
	 * @return String
	 */
	private String[] extrairFormatoMicrosoftWord(String caminhoDocumento) {

		File file = null;
		WordExtractor extractor = null;
		String[] fileData = null;

		try {

			file = new File(caminhoDocumento);

			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			HWPFDocument document = new HWPFDocument(fis);

			extractor = new WordExtractor(document);

			fileData = extractor.getParagraphText();

			fis.close();

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return fileData;
	}

	/**
	 * Extrai texto dos documentos do formato .docx
	 * 
	 * @param caminhoDocumento
	 * @return StringBuffer
	 * @author Vadoilo Damasceno
	 */
	private StringBuilder extrairFormatoDOCX(String caminhoDocumento) {

		File file = null;
		XWPFWordExtractor extractor = null;
		StringBuilder texto = new StringBuilder();

		try {

			file = new File(caminhoDocumento);

			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument document = new XWPFDocument(fis);

			extractor = new XWPFWordExtractor(document);

			texto.append(extractor.getText());

			fis.close();

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		return texto;
	}

	/**
	 * Extrai texto de documentos no formato .xlsx
	 * 
	 * @param caminhoDocumento
	 * @return StringBuffer
	 * @author Vadoilo Damasceno
	 */
	private StringBuilder extrairFormatoXLSX(String caminhoDocumento) {

		StringBuilder texto = new StringBuilder();

		try {

			File file = new File(caminhoDocumento);

			FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());

			XSSFWorkbook document = new XSSFWorkbook(fileInputStream);

			XSSFExcelExtractor extractor = new XSSFExcelExtractor(document);

			extractor.setFormulasNotResults(true);
			extractor.setIncludeSheetNames(true);

			texto.append(extractor.getText());

			fileInputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return texto;
	}

	/**
	 * Extrai texto de documentos no formato .xls
	 * 
	 * @param caminhoDocumento
	 * @return StringBuffer
	 * @author Vadoilo Damasceno
	 */
	private StringBuilder extrairFormatoXLS(String caminhoDocumento) {

		StringBuilder texto = new StringBuilder();

		try {

			File file = new File(caminhoDocumento);

			FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());

			HSSFWorkbook document = new HSSFWorkbook(fileInputStream);

			ExcelExtractor extractor = new ExcelExtractor(document);

			extractor.setFormulasNotResults(true);
			extractor.setIncludeSheetNames(true);

			texto.append(extractor.getText());

			fileInputStream.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return texto;
	}

	/**
	 * Extrai o texto de um documento no formato PDF.
	 * 
	 * @param caminhoDocumento
	 * @param nomeArquivo
	 * @return StringBuffer
	 * @throws IOException
	 */
	private StringBuilder extrairFormatoPDF(String caminhoDocumento) throws IOException {

		StringBuilder texto = new StringBuilder();

		PDFParser parser;

		FileInputStream fi;

		try {

			fi = new FileInputStream(new File(caminhoDocumento));

			parser = new PDFParser(fi);

			parser.parse();

			COSDocument cd = parser.getDocument();

			PDFTextStripper stripper = new PDFTextStripper();

			texto.append(stripper.getText(new PDDocument(cd)));

			cd.close();

			fi.close();

		} catch (FileNotFoundException e1) {

			e1.printStackTrace();

		} catch (IOException e1) {

			e1.printStackTrace();
		}

		return texto;
	}

	/**
	 * Extrai o texto de um arquivo no formato txt.
	 * 
	 * @param caminhoDocumento
	 * @param nomeArquivo
	 * @return StringBuffer
	 */
	private StringBuilder extrairFormatoTxt(String caminhoDocumento) {

		File file = null;

		StringBuilder texto = new StringBuilder("");

		try {

			file = new File(caminhoDocumento);

			FileInputStream fis = new FileInputStream(file);

			int ln;

			while ((ln = fis.read()) != -1) {

				texto.append((char) ln);

			}

			fis.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return texto;
	}
	
	/**
	 * Retorna Link do arquivo Decriptografado do GED.
	 * 
	 * @param idControleGed
	 * @return
	 * @throws Exception
	 */
	public String getFromGed(ControleGEDDTO controleGEDDTO) throws Exception {
		Integer idEmpresa = 1;

		String pasta = "";
		if (controleGEDDTO != null) {
			pasta = controleGEDDTO.getPasta();
		}

		String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "/usr/local/gedCitsmart/");
		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "";
		}

		if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
		}

		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
			PRONTUARIO_GED_DIRETORIO = "/ged";
		}
		String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
		if (PRONTUARIO_GED_INTERNO == null) {
			PRONTUARIO_GED_INTERNO = "S";
		}
		String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
		if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados))
			prontuarioGedInternoBancoDados = "N";

		if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {

			String fileRec = CITCorporeUtil.caminho_real_app + "tempUpload/REC_FROM_GED_" + controleGEDDTO.getIdControleGED() + "." + controleGEDDTO.getExtensaoArquivo();
			
			File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
			if (arquivo.exists()){
				CriptoUtils.decryptFile(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", fileRec,
										System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PRIVADA"));
			} else {
		    	System.out.println("Arquivo : "+arquivo.getPath()+" Não Encontrado!");
		    }

			return fileRec;
		}
		return null;
	}
	
	public static boolean verificaSeArquivoExiste(String caminhoDocumento){
		File arquivo = null;
		try {
			arquivo = new File(caminhoDocumento);
			return arquivo.exists();
		} catch (Exception e) {}
		return false;
	}
	
	public static String getDirUploadImagem(UploadDTO uploadDTO){
		return "../../tempUpload/" + uploadDTO.getNameFile();
	}
	
}