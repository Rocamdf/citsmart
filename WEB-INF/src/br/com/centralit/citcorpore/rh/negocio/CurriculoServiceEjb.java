package br.com.centralit.citcorpore.rh.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
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

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoPesquisaDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoPesquisaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CompetenciaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EmailCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EnderecoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.TelefoneCurriculoDTO;
import br.com.centralit.citcorpore.rh.integracao.CertificacaoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.CompetenciaCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.CurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.EmailCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.EnderecoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.ExperienciaProfissionalCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.FormacaoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.IdiomaCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.TelefoneCurriculoDao;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.OrdenadorDocumentosPesquisa;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class CurriculoServiceEjb extends CrudServicePojoImpl implements
		CurriculoService {

	protected CrudDAO getDao() throws ServiceException {

		return new CurriculoDao();

	}

	protected void validaCreate(Object arg0) throws ServiceException,
			LogicException {

		validaColecoes((CurriculoDTO) arg0);

	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	private void validaColecoes(CurriculoDTO curriculoDTO)
			throws ServiceException, LogicException {

		if (curriculoDTO.getColTelefones() == null
				|| curriculoDTO.getColTelefones().isEmpty()) {

			throw new LogicException(
					"É necessário registrar ao menos um telefone");

		}

		if (curriculoDTO.getColEnderecos() == null
				|| curriculoDTO.getColEnderecos().isEmpty()) {

			throw new LogicException("É necessário ao menos um endereço");

		}

		if (curriculoDTO.getColEmail() == null
				|| curriculoDTO.getColEmail().isEmpty()) {

			throw new LogicException("É necessário ao menos um email");

		}

		if (curriculoDTO.getColFormacao() == null
				|| curriculoDTO.getColFormacao().isEmpty()) {

			throw new LogicException("É necessário ao menos uma formação");

		}

	}

	private void validaAnexos(CurriculoDTO curriculoDto) throws LogicException {
		if(curriculoDto.getAnexos() != null)
			if (curriculoDto.getAnexos().size() > 1)

			throw new LogicException("Só é possível anexar um arquivo");

	}

	private void atualizaAnexos(CurriculoDTO curriculoDto,
			TransactionControler tc) throws Exception {

		validaAnexos(curriculoDto);
		
		ControleGEDServiceBean controleGedService = new ControleGEDServiceBean();

		controleGedService.atualizaAnexos(curriculoDto.getAnexos(),
				ControleGEDDTO.TABELA_CURRICULO, curriculoDto.getIdCurriculo(),
				tc);

		if (curriculoDto.getFoto() != null) {
			UploadDTO foto = curriculoDto.getFoto();
			foto.setDescricao("Foto currículo "+curriculoDto.getNome());
			Collection<UploadDTO> anexos = new ArrayList();
			anexos.add(foto);
			controleGedService.atualizaAnexos(anexos, ControleGEDDTO.FOTO_CURRICULO, curriculoDto.getIdCurriculo(), tc);
		}
	}

	public IDto create(IDto model) throws ServiceException, LogicException

	{

		// Instancia Objeto controlador de transacao

		CrudDAO crudDao = getDao();
		TelefoneCurriculoDao telefoneCurriculoDao = new TelefoneCurriculoDao();
		EnderecoCurriculoDao enderecoCurriculoDao = new EnderecoCurriculoDao();
		FormacaoCurriculoDao formacaoCurriculoDao = new FormacaoCurriculoDao();
		EmailCurriculoDao emailCurriculoDao = new EmailCurriculoDao();
		CertificacaoCurriculoDao certificacaoCurriculoDao = new CertificacaoCurriculoDao();
		IdiomaCurriculoDao idiomaCurriculoDao = new IdiomaCurriculoDao();
		
		ExperienciaProfissionalCurriculoDao experienciaProfissionalCurriculoDao = new ExperienciaProfissionalCurriculoDao();

		CompetenciaCurriculoDao competenciaCurriculoDao =  new CompetenciaCurriculoDao();
		
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

		CurriculoDTO curriculoDTO = (CurriculoDTO) model;
		try {

			// Faz validacao, caso exista.

			validaCreate(model);

			// Seta o TransactionController para os DAOs

			crudDao.setTransactionControler(tc);
			telefoneCurriculoDao.setTransactionControler(tc);
			enderecoCurriculoDao.setTransactionControler(tc);
			emailCurriculoDao.setTransactionControler(tc);
			formacaoCurriculoDao.setTransactionControler(tc);
			certificacaoCurriculoDao.setTransactionControler(tc);
			idiomaCurriculoDao.setTransactionControler(tc);
			
			experienciaProfissionalCurriculoDao.setTransactionControler(tc);
			
			competenciaCurriculoDao.setTransactionControler(tc);

			// Inicia transacao
			tc.start();
			// Executa operacoes pertinentes ao negocio.

			model = crudDao.create(curriculoDTO);
			if (curriculoDTO.getColTelefones() != null) {
				for (java.util.Iterator it = curriculoDTO.getColTelefones()
						.iterator();
				it.hasNext();) {
					TelefoneCurriculoDTO telefoneCurriculoDto = (TelefoneCurriculoDTO) it
							.next();
					telefoneCurriculoDto.setIdCurriculo(curriculoDTO
							.getIdCurriculo());
					telefoneCurriculoDao.create(telefoneCurriculoDto);
				}

			}

			if (curriculoDTO.getColEnderecos() != null) {
				for (Iterator it = curriculoDTO.getColEnderecos().iterator();
				(it).hasNext();) {
					EnderecoCurriculoDTO enderecoCurriculoDto = (EnderecoCurriculoDTO) it
							.next();
					enderecoCurriculoDto.setIdCurriculo(curriculoDTO
							.getIdCurriculo());
					enderecoCurriculoDao.create(enderecoCurriculoDto);
				}
			}

			if (curriculoDTO.getColFormacao() != null) {
				for (Iterator it = curriculoDTO.getColFormacao().iterator();
				(it).hasNext();) {
					FormacaoCurriculoDTO formacaoCurriculoDto = (FormacaoCurriculoDTO) it
							.next();
					formacaoCurriculoDto.setIdCurriculo(curriculoDTO
							.getIdCurriculo());
					formacaoCurriculoDao.create(formacaoCurriculoDto);
				}
			}

			if (curriculoDTO.getColEmail() != null) {
				for (Iterator it = curriculoDTO.getColEmail().iterator();
				(it).hasNext();) {
					EmailCurriculoDTO emCurriculoDto = (EmailCurriculoDTO) it
							.next();
				;
					emCurriculoDto
							.setIdCurriculo(curriculoDTO.getIdCurriculo());
					emailCurriculoDao.create(emCurriculoDto);
				}
			}

			if (curriculoDTO.getColCertificacao() != null) {
				for (Iterator it = curriculoDTO.getColCertificacao().iterator();
				(it).hasNext();) {
					CertificacaoCurriculoDTO cerCurriculoDto = (CertificacaoCurriculoDTO) it
							.next();
					;
					cerCurriculoDto.setIdCurriculo(curriculoDTO
							.getIdCurriculo());
					certificacaoCurriculoDao.create(cerCurriculoDto);
				}
		}

			if (curriculoDTO.getColIdioma() != null) {

				for (Iterator it = curriculoDTO.getColIdioma().iterator();

				(it).hasNext();) {

					IdiomaCurriculoDTO idiomaCurriculoDto = (IdiomaCurriculoDTO) it
							.next();
					;

					idiomaCurriculoDto.setIdCurriculo(curriculoDTO
							.getIdCurriculo());

					idiomaCurriculoDao.create(idiomaCurriculoDto);

				}

			}
			
			if (curriculoDTO.getColExperienciaProfissional() != null) {
				
				for (Object obj : curriculoDTO.getColExperienciaProfissional()){
					ExperienciaProfissionalCurriculoDTO experienciaDTO = (ExperienciaProfissionalCurriculoDTO) obj;
				
					
					experienciaDTO.setIdCurriculo(curriculoDTO
							.getIdCurriculo());
					
					experienciaProfissionalCurriculoDao.create(experienciaDTO);
					
				}
				
			}
			
			if (curriculoDTO.getColCompetencias() != null) {
				
				for (Object obj : curriculoDTO.getColCompetencias()){
					CompetenciaCurriculoDTO competenciaDto = (CompetenciaCurriculoDTO) obj;
					
					
					competenciaDto.setIdCurriculo(curriculoDTO
							.getIdCurriculo());
					
					competenciaCurriculoDao.create(competenciaDto);
					
				}
				
			}
			
			

			atualizaAnexos(curriculoDTO, tc);
			//indexarAnexoDocumentoLucene(curriculoDTO);

			// Faz commit e fecha a transacao.

			tc.commit();

			tc.close();

			return model;

		} catch (Exception e) {

			this.rollbackTransaction(tc, e);

		}

		return model;

	}
	
	public void update(IDto model) throws ServiceException, LogicException {

		// Instancia Objeto controlador de transacao

		CrudDAO crudDao = getDao();

		TelefoneCurriculoDao telefoneCurriculoDao = new TelefoneCurriculoDao();

		EnderecoCurriculoDao enderecoCurriculoDao = new EnderecoCurriculoDao();

		FormacaoCurriculoDao formacaoCurriculoDao = new FormacaoCurriculoDao();

		EmailCurriculoDao emailCurriculoDao = new EmailCurriculoDao();

		CertificacaoCurriculoDao certificacaoCurriculoDao = new CertificacaoCurriculoDao();

		IdiomaCurriculoDao idiomaCurriculoDao = new IdiomaCurriculoDao();
		
		CompetenciaCurriculoDao competenciaCurriculoDao = new CompetenciaCurriculoDao();
		
		ExperienciaProfissionalCurriculoDao experienciaProfissionalCurriculoDao = new ExperienciaProfissionalCurriculoDao();

		TransactionControler tc = new TransactionControlerImpl(crudDao.

		getAliasDB());

		CurriculoDTO curriculoDTO = (CurriculoDTO) model;

		try {

			// Faz validacao, caso exista.

			validaCreate(model);

			// Instancia ou obtem os DAOs necessarios.

			// Seta o TransactionController para os DAOs

			crudDao.setTransactionControler(tc);

			telefoneCurriculoDao.setTransactionControler(tc);

			enderecoCurriculoDao.setTransactionControler(tc);

			emailCurriculoDao.setTransactionControler(tc);

			formacaoCurriculoDao.setTransactionControler(tc);

			certificacaoCurriculoDao.setTransactionControler(tc);

			idiomaCurriculoDao.setTransactionControler(tc);
			
			competenciaCurriculoDao.setTransactionControler(tc);
			
			experienciaProfissionalCurriculoDao.setTransactionControler(tc);

			// Inicia transacao

			tc.start();

			// Executa operacoes pertinentes ao negocio.

			crudDao.update(curriculoDTO);

			telefoneCurriculoDao.deleteByIdCurriculo(curriculoDTO
					.getIdCurriculo());

			if (curriculoDTO.getColTelefones() != null) {

				for (java.util.Iterator it = curriculoDTO.getColTelefones()
						.iterator();

				it.hasNext();) {

					TelefoneCurriculoDTO telefoneCurriculoDto = (TelefoneCurriculoDTO) it
							.next();

					telefoneCurriculoDto.setIdCurriculo(curriculoDTO
							.getIdCurriculo());

					telefoneCurriculoDao.create(telefoneCurriculoDto);

				}

			}

			enderecoCurriculoDao.deleteByIdCurriculo(curriculoDTO
					.getIdCurriculo());

			if (curriculoDTO.getColEnderecos() != null) {

				for (Iterator it = curriculoDTO.getColEnderecos().iterator();

				(it).hasNext();) {

					EnderecoCurriculoDTO enderecoCurriculoDto = (EnderecoCurriculoDTO) it
							.next();

					enderecoCurriculoDto.setIdCurriculo(curriculoDTO
							.getIdCurriculo());

					enderecoCurriculoDao.create(enderecoCurriculoDto);

				}

			}

			formacaoCurriculoDao.deleteByIdCurriculo(curriculoDTO
					.getIdCurriculo());

			if (curriculoDTO.getColFormacao() != null) {

				for (Iterator it = curriculoDTO.getColFormacao().iterator();

				(it).hasNext();) {

					FormacaoCurriculoDTO formacaoCurriculoDto = (FormacaoCurriculoDTO) it
							.next();

					formacaoCurriculoDto.setIdCurriculo(curriculoDTO
							.getIdCurriculo());

					formacaoCurriculoDao.create(formacaoCurriculoDto);

				}

			}

			emailCurriculoDao
					.deleteByIdCurriculo(curriculoDTO.getIdCurriculo());

			if (curriculoDTO.getColEmail() != null) {

				for (Iterator it = curriculoDTO.getColEmail().iterator();

				(it).hasNext();) {

					EmailCurriculoDTO emCurriculoDto = (EmailCurriculoDTO) it
							.next();
					;

					emCurriculoDto
							.setIdCurriculo(curriculoDTO.getIdCurriculo());

					emailCurriculoDao.create(emCurriculoDto);

				}

			}

			certificacaoCurriculoDao.deleteByIdCurriculo(curriculoDTO
					.getIdCurriculo());

			if (curriculoDTO.getColCertificacao() != null) {

				for (Iterator it = curriculoDTO.getColCertificacao().iterator();

				(it).hasNext();) {

					CertificacaoCurriculoDTO cerCurriculoDto = (CertificacaoCurriculoDTO) it
							.next();
					;

					cerCurriculoDto.setIdCurriculo(curriculoDTO
							.getIdCurriculo());

					certificacaoCurriculoDao.create(cerCurriculoDto);

				}

			}

			idiomaCurriculoDao.deleteByIdCurriculo(curriculoDTO
					.getIdCurriculo());

			if (curriculoDTO.getColIdioma() != null) {

				for (Iterator it = curriculoDTO.getColIdioma().iterator();

				(it).hasNext();) {

					IdiomaCurriculoDTO idiomaCurriculoDto = (IdiomaCurriculoDTO) it
							.next();
					;

					idiomaCurriculoDto.setIdCurriculo(curriculoDTO
							.getIdCurriculo());

					idiomaCurriculoDao.create(idiomaCurriculoDto);

				}

			}
			
			competenciaCurriculoDao.deleteByIdCurriculo(curriculoDTO
					.getIdCurriculo());
			
			if (curriculoDTO.getColCompetencias() != null) {
				
				for (Object obj : curriculoDTO.getColCompetencias()) {
					
					CompetenciaCurriculoDTO competencia = (CompetenciaCurriculoDTO) obj;
					
					competencia.setIdCurriculo(curriculoDTO
							.getIdCurriculo());
					
					competenciaCurriculoDao.create(competencia);
					
				}
				
			}
			
			experienciaProfissionalCurriculoDao.deleteByIdCurriculo(curriculoDTO.getIdCurriculo());
			
			if (curriculoDTO.getColExperienciaProfissional() != null) {
				
				for (Object obj : curriculoDTO.getColExperienciaProfissional()) {
					
					ExperienciaProfissionalCurriculoDTO experienciaProfissionalCurriculoDTO = (ExperienciaProfissionalCurriculoDTO) obj;
					
					experienciaProfissionalCurriculoDTO.setIdCurriculo(curriculoDTO
							.getIdCurriculo());
					
					experienciaProfissionalCurriculoDao.create(experienciaProfissionalCurriculoDTO);
					
				}
				
			}

			atualizaAnexos(curriculoDTO, tc);
			

			// Faz commit e fecha a transacao.

			tc.commit();

			tc.close();

		} catch (Exception e) {

			this.rollbackTransaction(tc, e);

		}

	}

	public IDto restore(IDto model) throws ServiceException, LogicException

	{

		try {

			CurriculoDTO curriculoDTO = (CurriculoDTO) model;

			TelefoneCurriculoDao telefoneCurriculoDao = new TelefoneCurriculoDao();

			Collection colTelefones = telefoneCurriculoDao
					.findByIdCurriculo(curriculoDTO.getIdCurriculo());

			EnderecoCurriculoDao enderecoCurriculoDao = new EnderecoCurriculoDao();

			Collection colEnderecos = enderecoCurriculoDao
					.findByIdCurriculo(curriculoDTO.getIdCurriculo());

			FormacaoCurriculoDao formacaoCurriculoDao = new FormacaoCurriculoDao();

			Collection colFormacao = formacaoCurriculoDao
					.findByIdCurriculo(curriculoDTO.getIdCurriculo());

			EmailCurriculoDao emailCurriculoDao = new EmailCurriculoDao();

			Collection colEmail = emailCurriculoDao
					.findByIdCurriculo(curriculoDTO.getIdCurriculo());

			CertificacaoCurriculoDao certificacaoCurriculoDao = new CertificacaoCurriculoDao();

			Collection colCertificacao = certificacaoCurriculoDao
					.findByIdCurriculo(curriculoDTO.getIdCurriculo());
			
			ExperienciaProfissionalCurriculoDao experienciaDao = new ExperienciaProfissionalCurriculoDao();
			Collection colExperiencias =  experienciaDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());
			
			CompetenciaCurriculoDao competenciaCurriculoDao = new CompetenciaCurriculoDao();
			Collection colcompetencias = competenciaCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());

			IdiomaCurriculoDao idiomaCurriculoDao = new IdiomaCurriculoDao();

			Collection<IdiomaCurriculoDTO> colIdioma = (Collection<IdiomaCurriculoDTO>) idiomaCurriculoDao
					.findByIdCurriculo(curriculoDTO.getIdCurriculo());

			curriculoDTO = (CurriculoDTO) new CurriculoDao()
					.restore(curriculoDTO);

			curriculoDTO.setColTelefones(colTelefones);

			curriculoDTO.setColEnderecos(colEnderecos);

			curriculoDTO.setColFormacao(colFormacao);

			curriculoDTO.setColEmail(colEmail);

			curriculoDTO.setColCertificacao(colCertificacao);

			curriculoDTO.setColIdioma(colIdioma);
			
			curriculoDTO.setColExperienciaProfissional(colExperiencias);
			
			curriculoDTO.setColCompetencias(colcompetencias);

			return curriculoDTO;

		} catch (Exception e) {

			throw new ServiceException(e);

		}

	}

	public String getFromGed(ControleGEDDTO controleGEDDTO) throws Exception {

		Integer idEmpresa = 1;

		String pasta = "";

		if (controleGEDDTO != null) {

			pasta = controleGEDDTO.getPasta();

		}

		String PRONTUARIO_GED_DIRETORIO = ParametroUtil
				.getValorParametroCitSmartHashMap(
						Enumerados.ParametroSistema.GedDiretorio,
						"/usr/local/gedCitsmart/");

		if (PRONTUARIO_GED_DIRETORIO == null
				|| PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {

			PRONTUARIO_GED_DIRETORIO = "";

		}

		if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {

			PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");

		}

		if (PRONTUARIO_GED_DIRETORIO == null
				|| PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {

			PRONTUARIO_GED_DIRETORIO = "/ged";

		}

		String PRONTUARIO_GED_INTERNO = ParametroUtil
				.getValorParametroCitSmartHashMap(
						Enumerados.ParametroSistema.GedInterno, "S");

		if (PRONTUARIO_GED_INTERNO == null) {

			PRONTUARIO_GED_INTERNO = "S";

		}

		String prontuarioGedInternoBancoDados = ParametroUtil
				.getValorParametroCitSmartHashMap(
						Enumerados.ParametroSistema.GedInternoBD, "N");

		if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados))

			prontuarioGedInternoBancoDados = "N";

		if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {

			String fileRec = CITCorporeUtil.caminho_real_app
					+ "tempUpload/REC_FROM_GED_"
					+ controleGEDDTO.getIdControleGED() + "."
					+ controleGEDDTO.getExtensaoArquivo();

			CriptoUtils.decryptFile(
					PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta
							+ "/" + controleGEDDTO.getIdControleGED() + ".ged",
					fileRec,

					System.getProperties().get("user.dir")
							+ Constantes.getValue("CAMINHO_CHAVE_PRIVADA"));

			return fileRec;

		}

		return null;

	}

	public String[] extrairFormatoMicrosoftWord(String caminhoDocumento) {

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

	public StringBuffer extrairFormatoDOCX(String caminhoDocumento) {

		File file = null;

		XWPFWordExtractor extractor = null;

		StringBuffer texto = new StringBuffer();

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

	public StringBuffer extrairFormatoXLSX(String caminhoDocumento) {

		StringBuffer texto = new StringBuffer();

		try {

			File file = new File(caminhoDocumento);

			FileInputStream fileInputStream = new FileInputStream(
					file.getAbsolutePath());

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

	public StringBuffer extrairFormatoXLS(String caminhoDocumento) {

		StringBuffer texto = new StringBuffer();

		try {

			File file = new File(caminhoDocumento);

			FileInputStream fileInputStream = new FileInputStream(
					file.getAbsolutePath());

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

	public StringBuffer extrairFormatoPDF(String caminhoDocumento)
			throws IOException {

		StringBuffer texto = new StringBuffer();

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

	public StringBuffer extrairFormatoTxt(String caminhoDocumento) {

		File file = null;

		StringBuffer texto = new StringBuffer("");

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
	
	public String retornarCaminhoFoto(Integer idCurriculo) throws Exception{
		ControleGEDDao controleGEDDao = new ControleGEDDao();
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		String caminhoFoto = "";
		try {
			Collection colAnexosFotos = controleGEDDao.listByIdTabelaAndID(ControleGEDDTO.FOTO_CURRICULO, idCurriculo);
			colAnexosFotos = controleGedService.convertListControleGEDToUploadDTO(colAnexosFotos);
			if (colAnexosFotos != null && colAnexosFotos.size() > 0){
				Iterator it = colAnexosFotos.iterator();
	//			UploadFileDTO uploadItem = (UploadFileDTO)it.next();
				UploadDTO uploadItem = (UploadDTO)it.next();
				
				caminhoFoto = uploadItem.getCaminhoRelativo();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return caminhoFoto;
		
	}

}
