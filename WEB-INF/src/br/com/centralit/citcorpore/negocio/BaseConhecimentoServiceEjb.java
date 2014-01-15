/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoRelacionadoDTO;
import br.com.centralit.citcorpore.bean.ComentariosDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoICDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoMudancaDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoProblemaDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.HistoricoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoUsuarioDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AnexoBaseConhecimentoDAO;
import br.com.centralit.citcorpore.integracao.BaseConhecimentoDAO;
import br.com.centralit.citcorpore.integracao.ComentariosDAO;
import br.com.centralit.citcorpore.integracao.ConhecimentoICDao;
import br.com.centralit.citcorpore.integracao.ConhecimentoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.ConhecimentoMudancaDao;
import br.com.centralit.citcorpore.integracao.ConhecimentoProblemaDao;
import br.com.centralit.citcorpore.integracao.ConhecimentoSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.HistoricoBaseConhecimentoDAO;
import br.com.centralit.citcorpore.integracao.NotificacaoDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.util.Arquivo;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.centralit.lucene.Lucene;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

/**
 * ServiceEJB de BaseConhecimento.
 * 
 * @author valdoilo.damasceno
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseConhecimentoServiceEjb extends CrudServicePojoImpl implements BaseConhecimentoService {

	private static final long serialVersionUID = 1366543033831132918L;

	private File pastaDaBaseConhecimento;

	private Integer idBaseConhecimento;

	public Integer getIdBaseConhecimento() {
		return this.idBaseConhecimento;
	}

	@Override
	public BaseConhecimentoDTO create(BaseConhecimentoDTO baseConhecimentoDto, Collection<UploadDTO> arquivosUpados, Integer idEmpresa, UsuarioDTO usuarioDto) throws Exception {

		NotificacaoDTO notificacaoDto = new NotificacaoDTO();
		ConhecimentoProblemaDTO conhecimentoProblemaDTO = new ConhecimentoProblemaDTO();

		ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");

		if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
			prontuarioGedInternoBancoDados = "N";
		}

		String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "/usr/local/gedCitsmart/");
		String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");

		TransactionControler transactionControler = new TransactionControlerImpl(this.getBaseConhecimentoDao().getAliasDB());

		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		AnexoBaseConhecimentoDAO anexoBaseConhecimentoDao = new AnexoBaseConhecimentoDAO();
		ControleGEDDao controleGEDDao = new ControleGEDDao();
		HistoricoBaseConhecimentoDAO historicoBaseConhecimentoDao = new HistoricoBaseConhecimentoDAO();
		ConhecimentoProblemaDao conhecimentoProblemaDao =  new ConhecimentoProblemaDao();

		try {

			baseConhecimentoDao.setTransactionControler(transactionControler);
			anexoBaseConhecimentoDao.setTransactionControler(transactionControler);
			controleGEDDao.setTransactionControler(transactionControler);
			historicoBaseConhecimentoDao.setTransactionControler(transactionControler);
			conhecimentoProblemaDao.setTransactionControler(transactionControler);

			transactionControler.start();

			this.validaCreate(baseConhecimentoDto);

			baseConhecimentoDto.setDataInicio(UtilDatas.getDataAtual());
			baseConhecimentoDto.setIdUsuarioAutor(usuarioDto.getIdUsuario());
			baseConhecimentoDto.setArquivado("N");
			
			if(baseConhecimentoDto.getErroConhecido()==null || baseConhecimentoDto.getErroConhecido().equalsIgnoreCase("")){
				baseConhecimentoDto.setErroConhecido("N");
			}

			boolean isAprovaBaseConhecimento = this.usuarioAprovaBaseConhecimento(usuarioDto, baseConhecimentoDto.getIdPasta());

			if (!isAprovaBaseConhecimento) {
				baseConhecimentoDto.setStatus("N");
			} else {
				if (baseConhecimentoDto.getStatus().equalsIgnoreCase("S")) {
					baseConhecimentoDto.setVersao("1.0");
					baseConhecimentoDto.setIdUsuarioAprovador(usuarioDto.getIdUsuario());
					baseConhecimentoDto.setDataPublicacao(UtilDatas.getDataAtual());
				}

			}

			notificacaoDto = this.criarNotificacao(baseConhecimentoDto, transactionControler);

			if (notificacaoDto.getIdNotificacao() != null) {
				baseConhecimentoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
			}

			// TODO CREATE
			baseConhecimentoDto = (BaseConhecimentoDTO) baseConhecimentoDao.create(baseConhecimentoDto);

			HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDto = new HistoricoBaseConhecimentoDTO();

			Reflexao.copyPropertyValues(baseConhecimentoDto, historicoBaseConhecimentoDto);

			historicoBaseConhecimentoDto.setIdUsuarioAlteracao(usuarioDto.getIdUsuario());

			historicoBaseConhecimentoDto.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

			historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.create(historicoBaseConhecimentoDto);

			baseConhecimentoDto.setIdHistoricoBaseConhecimento(historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento());

			baseConhecimentoDao.updateNotNull(baseConhecimentoDto);

			this.enviarEmailNotificacaoConhecimento(baseConhecimentoDto, transactionControler, "C");

			// TODO ENVIAR NOTIFICAÇÃO PARA O RESPONSÁVEL PELO GERENCIAMENTO DE CONHECIMENTO.

			this.criarImportanciaConhecimentoUsuario(baseConhecimentoDto, transactionControler);
			this.criarImportanciaConhecimentoGrupo(baseConhecimentoDto, transactionControler);
			this.criarRelacionamentoEntreConhecimentos(baseConhecimentoDto, transactionControler);
			this.criarComentarios(baseConhecimentoDto, transactionControler);
			this.criarRelacionamentoEntreEventoMonitConhecimento(baseConhecimentoDto, transactionControler);
			// TODO  CRIAR RELACIONAMENTO ENTRE UMA BASE DE CONHECIMENTO COM UM PROBLEMA.
			if(baseConhecimentoDto.getIdProblema()!=null && baseConhecimentoDto.getIdBaseConhecimento()!=null){
				
					conhecimentoProblemaDao.deleteByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
					
					conhecimentoProblemaDTO.setIdProblema(baseConhecimentoDto.getIdProblema());
					conhecimentoProblemaDTO.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
					conhecimentoProblemaDao.create(conhecimentoProblemaDTO);
			}

			String pasta = "";
			if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
				pasta = controleGEDService.getProximaPastaArmazenar();

				File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}

				fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}

				fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
			}

			ArrayList<AnexoBaseConhecimentoDTO> listaAnexoBaseConhecimento = new ArrayList<AnexoBaseConhecimentoDTO>();
			if (arquivosUpados != null && !arquivosUpados.isEmpty()) {

				for (UploadDTO uploadDto : arquivosUpados) {

					ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

					AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

					controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
					controleGEDDTO.setId(baseConhecimentoDto.getIdBaseConhecimento());
					controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
					controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
					controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
					controleGEDDTO.setPasta(pasta);
					controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

					if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
						controleGEDDTO.setPathArquivo(uploadDto.getPath());
					} else {
						controleGEDDTO.setPathArquivo(null);
					}
					
					controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO, "");

					if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
						if (controleGEDDTO != null) {
							File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDto.getNameFile()));
							CriptoUtils.encryptFile(uploadDto.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", System.getProperties()
									.get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
							anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
							arquivo.delete();
						}
					}

					anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
					if(controleGEDDTO != null){
						anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
						anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
					}
					anexoBaseConhecimento.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
					anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
					// Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
					Arquivo arquivo = new Arquivo(controleGEDDTO);
					anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
					
					anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

					listaAnexoBaseConhecimento.add(anexoBaseConhecimento);
				}
			} else {
				System.out.println("BaseConhecimento - A arquivosUpados esta vazia!");
			}

			//@Author euler.ramos
			//Poderia tratar a exclusão quando a base se tona arquivada ou não publicada, mas por enquanto prefiro deixá-la indexada!
			//INDEXAÇÃO LUCENE
			//Indexando Base de Conhecimento
			Lucene lucene = new Lucene();
			//Publicada, Não arquivada e Não excluída!
			lucene.indexarBaseConhecimento(baseConhecimentoDto,listaAnexoBaseConhecimento);
			
			transactionControler.commit();
			transactionControler.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("GED BaseConhecimento - Erro ao gravar Arquivo ged e gravar o ged no banco de dados: " + e);
			this.rollbackTransaction(transactionControler, e);
		}

		return baseConhecimentoDto;
	}

	@Override
	public void update(BaseConhecimentoDTO baseConhecimentoDto, Collection<UploadDTO> arquivosUpados, Integer idEmpresa, UsuarioDTO usuarioDto) throws ServiceException, Exception {
		//@Author euler.ramos
		Lucene lucene = new Lucene();
		ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		ImportanciaConhecimentoUsuarioService importanciaConhecimentoUsuarioService = (ImportanciaConhecimentoUsuarioService) ServiceLocator.getInstance().getService(ImportanciaConhecimentoUsuarioService.class, null);
		ImportanciaConhecimentoGrupoService importanciaConhecimentoGrupoService = (ImportanciaConhecimentoGrupoService) ServiceLocator.getInstance().getService(ImportanciaConhecimentoGrupoService.class, null);
		BaseConhecimentoRelacionadoService baseConhecimentoRelacionadoService = (BaseConhecimentoRelacionadoService) ServiceLocator.getInstance().getService(BaseConhecimentoRelacionadoService.class, null);

		NotificacaoDTO notificacaoDto = new NotificacaoDTO();
		HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDto = new HistoricoBaseConhecimentoDTO();
		ConhecimentoProblemaDTO conhecimentoProblemaDTO =  new ConhecimentoProblemaDTO();

		String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");

		if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
			prontuarioGedInternoBancoDados = "N";
		}

		String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "/usr/local/gedCitsmart/");
		String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");

		TransactionControler transactionControler = new TransactionControlerImpl(this.getBaseConhecimentoDao().getAliasDB());

		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		AnexoBaseConhecimentoDAO anexoBaseConhecimentoDao = new AnexoBaseConhecimentoDAO();
		ControleGEDDao controleGEDDao = new ControleGEDDao();
		HistoricoBaseConhecimentoDAO historicoBaseConhecimentoDao = new HistoricoBaseConhecimentoDAO();
		ConhecimentoProblemaDao conhecimentoProblemaDao =  new ConhecimentoProblemaDao();

		baseConhecimentoDao.setTransactionControler(transactionControler);
		anexoBaseConhecimentoDao.setTransactionControler(transactionControler);
		controleGEDDao.setTransactionControler(transactionControler);
		historicoBaseConhecimentoDao.setTransactionControler(transactionControler);
		conhecimentoProblemaDao.setTransactionControler(transactionControler);
		
		BaseConhecimentoDTO novaBaseConhecimento = this.atribuirValoresNovaBaseConhecimento(baseConhecimentoDto);

		String status = ((BaseConhecimentoDTO) this.getBaseConhecimentoDao().restore(baseConhecimentoDto)).getStatus();

		String pasta = "";
		if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
			pasta = controleGEDService.getProximaPastaArmazenar();

			File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

			fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
		}

		boolean isAprovaBaseConhecimento = this.usuarioAprovaBaseConhecimento(usuarioDto, baseConhecimentoDto.getIdPasta());

		// A BASE IRÁ SER APROVADA
		if (novaBaseConhecimento.getStatus() != null && isAprovaBaseConhecimento && novaBaseConhecimento.getStatus().equalsIgnoreCase("S")) {
			if (novaBaseConhecimento.getVersao() != null && !novaBaseConhecimento.getVersao().equals("")) {
				Double novaVersao = Double.parseDouble(novaBaseConhecimento.getVersao()) + 0.1;

				novaBaseConhecimento.setVersao(novaVersao.toString().substring(0, 3));
				novaBaseConhecimento.setTitulo(baseConhecimentoDto.getTitulo().split(" - v")[0] + " - v" + (novaBaseConhecimento.getVersao()));
			} else {
				novaBaseConhecimento.setVersao("1.0");
			}

			// BASE NÃO APROVADA - VAI SER APROVADA
			if (baseConhecimentoDto.getIdBaseConhecimentoPai() != null || status.equalsIgnoreCase("N")) {

				try {
					transactionControler.start();

					novaBaseConhecimento.setStatus("S");
					novaBaseConhecimento.setIdBaseConhecimentoPai(null);
					novaBaseConhecimento.setIdUsuarioAprovador(usuarioDto.getIdUsuario());
					novaBaseConhecimento.setDataPublicacao(UtilDatas.getDataAtual());
					
					if(novaBaseConhecimento.getErroConhecido()==null || novaBaseConhecimento.getErroConhecido().equalsIgnoreCase("")){
						novaBaseConhecimento.setErroConhecido("N");
					}

					notificacaoDto = this.criarNotificacao(novaBaseConhecimento, transactionControler);

					if (notificacaoDto.getIdNotificacao() != null) {
						novaBaseConhecimento.setIdNotificacao(notificacaoDto.getIdNotificacao());
					}

					// TODO CREATE
					novaBaseConhecimento = (BaseConhecimentoDTO) baseConhecimentoDao.create(novaBaseConhecimento);

					if (baseConhecimentoDto.getIdBaseConhecimentoPai() != null) {

						BaseConhecimentoDTO baseConhecimentoPai = new BaseConhecimentoDTO();

						baseConhecimentoPai.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimentoPai());
						baseConhecimentoPai.setStatus("S");

						baseConhecimentoDao.updateNotNull(baseConhecimentoPai);

					}

					Reflexao.copyPropertyValues(novaBaseConhecimento, historicoBaseConhecimentoDto);

					historicoBaseConhecimentoDto.setIdUsuarioAlteracao(usuarioDto.getIdUsuario());

					historicoBaseConhecimentoDto.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

					if (historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento() == null) {
						historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.create(historicoBaseConhecimentoDto);
					} else {
						historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.createWithID(historicoBaseConhecimentoDto);
					}

					if (novaBaseConhecimento.getIdHistoricoBaseConhecimento() == null) {

						novaBaseConhecimento.setIdHistoricoBaseConhecimento(historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento());

						baseConhecimentoDao.updateNotNull(novaBaseConhecimento);
					}

					// TODO ENVIAR NOTIFICAÇÃO PARA O RESPONSÁVEL PELO GERENCIAMENTO DE CONHECIMENTO.
					this.enviarEmailNotificacaoConhecimento(baseConhecimentoDto, transactionControler, "U");

					this.idBaseConhecimento = novaBaseConhecimento.getIdBaseConhecimento();

					this.criarImportanciaConhecimentoUsuario(novaBaseConhecimento, transactionControler);
					this.criarImportanciaConhecimentoGrupo(novaBaseConhecimento, transactionControler);
					this.criarRelacionamentoEntreConhecimentos(novaBaseConhecimento, transactionControler);
					this.criarComentarios(novaBaseConhecimento, transactionControler);
					this.criarRelacionamentoEntreEventoMonitConhecimento(novaBaseConhecimento, transactionControler);
					
					// TODO  CRIAR RELACIONAMENTO ENTRE UMA BASE DE CONHECIMENTO COM UM PROBLEMA.
					if(novaBaseConhecimento.getIdProblema()!=null && novaBaseConhecimento.getIdBaseConhecimento()!=null){
						
							conhecimentoProblemaDao.deleteByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
							
							conhecimentoProblemaDTO.setIdProblema(novaBaseConhecimento.getIdProblema());
							conhecimentoProblemaDTO.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
							conhecimentoProblemaDao.create(conhecimentoProblemaDTO);
					}

					ArrayList<AnexoBaseConhecimentoDTO> listaAnexoBaseConhecimento = new ArrayList<AnexoBaseConhecimentoDTO>();
					if (arquivosUpados != null && !arquivosUpados.isEmpty()) {

						for (UploadDTO uploadDto : arquivosUpados) {

							if (uploadDto.getTemporario().equalsIgnoreCase("S")) {

								ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
								AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

								controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
								controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
								controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
								controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
								controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
								controleGEDDTO.setPasta(pasta);
								controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

								if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
									controleGEDDTO.setPathArquivo(uploadDto.getPath());
								} else {
									controleGEDDTO.setPathArquivo(null);
								}

								controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO, "");

								if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {

									if (controleGEDDTO != null) {

										File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDto.getNameFile()));

										CriptoUtils.encryptFile(uploadDto.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged",
												System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));

										anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");

										arquivo.delete();
									}
								}

								anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
								if(controleGEDDTO != null){
									anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
									anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
								}
								anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
								anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
								// Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
								Arquivo arquivo = new Arquivo(controleGEDDTO);
								anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
								anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

								listaAnexoBaseConhecimento.add(anexoBaseConhecimento);

							} else {

								ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

								AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

								controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
								controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
								controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
								controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
								controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
								controleGEDDTO.setPasta(pasta);
								controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());
								controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO, "");

								File arquivoAntigo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + uploadDto.getPath().substring(3) + ".ged");

								copiarArquivo(arquivoAntigo, PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");

								anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
								anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
								anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
								anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
								anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
								anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
								// Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
								Arquivo arquivo = new Arquivo(controleGEDDTO);
								anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
								anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

								listaAnexoBaseConhecimento.add(anexoBaseConhecimento);
							}
						}
					}

					//@Author euler.ramos
					lucene.indexarBaseConhecimento(novaBaseConhecimento,listaAnexoBaseConhecimento);
					
					baseConhecimentoRelacionadoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
					importanciaConhecimentoUsuarioService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
					importanciaConhecimentoGrupoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
					conhecimentoProblemaDao.deleteByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
					baseConhecimentoDao.delete(baseConhecimentoDto);
					
					//Excluir Base de Conhecimento Antiga do índice, atendendo solicitação: 111236 nessa exclusão os anexos são excluidos juntamente.
					lucene.excluirBaseConhecimento(baseConhecimentoDto,true);

					// TODO EXCLUIR COMENTÁRIOS

					// TODO EXCLUIR NOTIFICAÇÕES

					// TODO EXCLUIR IMPORTÂNCIA CONHECIMENTO

					// TODO EXCLUIR COMENTÁRIOS
					
					// TODO EXCLUIR CONHECIMENTOPROBLEMA

					// EXCLUO OS ANEXOS DA BASE DE CONHECIMENTO ANTIGA.

					Collection<AnexoBaseConhecimentoDTO> anexosBaseConhecimento = anexoBaseConhecimentoDao.consultarAnexosDaBaseConhecimento(baseConhecimentoDto);
					if (anexosBaseConhecimento != null && !anexosBaseConhecimento.isEmpty()) {
						for (AnexoBaseConhecimentoDTO anexo : anexosBaseConhecimento) {
							anexoBaseConhecimentoDao.delete(anexo);
							deleteDir(new File(anexo.getLink()));
						}
					}

					// EXCLUO OS GEDS DA BASE DE CONHECIMENTO ANTIGA.

					Collection<ControleGEDDTO> gedsBaseConhecimento = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_BASECONHECIMENTO, baseConhecimentoDto.getIdBaseConhecimento());
					if (gedsBaseConhecimento != null && !gedsBaseConhecimento.isEmpty()) {
						for (ControleGEDDTO ged : gedsBaseConhecimento) {
							controleGEDDao.delete(ged);
						}
					}

					transactionControler.commit();
					transactionControler.close();
				} catch (Exception e) {
					this.rollbackTransaction(transactionControler, e);
				}

			} else {

				// BASE PUBLICADA, ALTERADA VAI SER PUBLICADA NOVAMENTE COM OUTRA VERSÃO
				try {

					transactionControler.start();

					novaBaseConhecimento.setIdUsuarioAprovador(usuarioDto.getIdUsuario());
					novaBaseConhecimento.setDataPublicacao(UtilDatas.getDataAtual());

					notificacaoDto = this.criarNotificacao(novaBaseConhecimento, transactionControler);

					if (notificacaoDto.getIdNotificacao() != null) {
						novaBaseConhecimento.setIdNotificacao(notificacaoDto.getIdNotificacao());
					}

					// TODO CREATE
					novaBaseConhecimento = (BaseConhecimentoDTO) baseConhecimentoDao.create(novaBaseConhecimento);

					Reflexao.copyPropertyValues(novaBaseConhecimento, historicoBaseConhecimentoDto);

					historicoBaseConhecimentoDto.setIdUsuarioAlteracao(usuarioDto.getIdUsuario());

					historicoBaseConhecimentoDto.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

					if (historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento() == null) {
						historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.create(historicoBaseConhecimentoDto);
					} else {
						historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.createWithID(historicoBaseConhecimentoDto);
					}

					if (novaBaseConhecimento.getIdHistoricoBaseConhecimento() == null) {

						novaBaseConhecimento.setIdHistoricoBaseConhecimento(historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento());

						baseConhecimentoDao.updateNotNull(novaBaseConhecimento);
					}

					baseConhecimentoDao.restore(baseConhecimentoDto);
					
					baseConhecimentoDto.setArquivado("S");

					baseConhecimentoDao.updateNotNull(baseConhecimentoDto);
					
					lucene.excluirBaseConhecimento(baseConhecimentoDto,true);

					// TODO ENVIAR NOTIFICAÇÃO PARA O RESPONSÁVEL PELO GERENCIAMENTO DE CONHECIMENTO.
					this.enviarEmailNotificacaoConhecimento(baseConhecimentoDto, transactionControler, "U");

					this.idBaseConhecimento = novaBaseConhecimento.getIdBaseConhecimento();

					this.criarImportanciaConhecimentoUsuario(novaBaseConhecimento, transactionControler);
					this.criarImportanciaConhecimentoGrupo(novaBaseConhecimento, transactionControler);
					this.criarRelacionamentoEntreConhecimentos(novaBaseConhecimento, transactionControler);
					this.criarComentarios(novaBaseConhecimento, transactionControler);
					this.criarRelacionamentoEntreEventoMonitConhecimento(novaBaseConhecimento, transactionControler);
					
					// TODO  CRIAR RELACIONAMENTO ENTRE UMA BASE DE CONHECIMENTO COM UM PROBLEMA.
					if(novaBaseConhecimento.getIdProblema()!=null && novaBaseConhecimento.getIdBaseConhecimento()!=null){
						
							conhecimentoProblemaDao.deleteByIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
							
							conhecimentoProblemaDTO.setIdProblema(novaBaseConhecimento.getIdProblema());
							conhecimentoProblemaDTO.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
							conhecimentoProblemaDao.create(conhecimentoProblemaDTO);
					}

					ArrayList<AnexoBaseConhecimentoDTO> listaAnexoBaseConhecimento = new ArrayList<AnexoBaseConhecimentoDTO>();
					if (arquivosUpados != null && !arquivosUpados.isEmpty()) {

						for (UploadDTO uploadDto : arquivosUpados) {

							if (uploadDto.getTemporario().equalsIgnoreCase("S")) {

								ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
								AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

								controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
								controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
								controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
								controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
								controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
								controleGEDDTO.setPasta(pasta);
								controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

								if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
									controleGEDDTO.setPathArquivo(uploadDto.getPath());
								} else {
									controleGEDDTO.setPathArquivo(null);
								}

								controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO, "");

								if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
									if (controleGEDDTO != null) {
										File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDto.getNameFile()));
										CriptoUtils.encryptFile(uploadDto.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged",
												System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
										anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
										arquivo.delete();
									}
								}

								anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
								if(controleGEDDTO != null){
									anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
									anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
								}
								anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
								anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
								// Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
								Arquivo arquivo = new Arquivo(controleGEDDTO);
								anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
								anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

								listaAnexoBaseConhecimento.add(anexoBaseConhecimento);
								
							} else {

								ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

								AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

								controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
								controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
								controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
								controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
								controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
								controleGEDDTO.setPasta(pasta);
								controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());
								controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO, "");

								File arquivoAntigo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + uploadDto.getPath().substring(3) + ".ged");

								copiarArquivo(arquivoAntigo, PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");

								anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
								anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
								anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
								anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
								anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
								anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
								// Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
								Arquivo arquivo = new Arquivo(controleGEDDTO);
								anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
								anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

								listaAnexoBaseConhecimento.add(anexoBaseConhecimento);
							}
						}
					}
					
					//Publicada, Não arquivada e Não excluída!
					lucene.indexarBaseConhecimento(novaBaseConhecimento,listaAnexoBaseConhecimento);
					
					transactionControler.commit();
					transactionControler.close();

				} catch (Exception e) {
					this.rollbackTransaction(transactionControler, e);
				}

			}

			// A BASE NÃO SERÁ PUBLICADA
		} else {

			// BASE NÃO APROVADA E QUE CONTINUARÁ NÃO APROVADA
			if (status.equalsIgnoreCase("N")) {

				try {

					transactionControler.start();

					notificacaoDto = this.criarNotificacao(novaBaseConhecimento, transactionControler);

					if (notificacaoDto.getIdNotificacao() != null) {
						novaBaseConhecimento.setIdNotificacao(notificacaoDto.getIdNotificacao());
					}

					// TODO CREATE
					novaBaseConhecimento = (BaseConhecimentoDTO) baseConhecimentoDao.create(novaBaseConhecimento);

					Reflexao.copyPropertyValues(novaBaseConhecimento, historicoBaseConhecimentoDto);

					historicoBaseConhecimentoDto.setIdUsuarioAlteracao(usuarioDto.getIdUsuario());

					historicoBaseConhecimentoDto.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

					if (historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento() == null) {
						historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.create(historicoBaseConhecimentoDto);
					} else {
						historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.createWithID(historicoBaseConhecimentoDto);
					}

					if (novaBaseConhecimento.getIdHistoricoBaseConhecimento() == null) {
						novaBaseConhecimento.setIdHistoricoBaseConhecimento(historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento());
						baseConhecimentoDao.updateNotNull(novaBaseConhecimento);
					}

					// TODO ENVIAR NOTIFICAÇÃO PARA O RESPONSÁVEL PELO GERENCIAMENTO DE CONHECIMENTO.
					this.enviarEmailNotificacaoConhecimento(baseConhecimentoDto, transactionControler, "U");
					
					// TODO  CRIAR RELACIONAMENTO ENTRE UMA BASE DE CONHECIMENTO COM UM PROBLEMA.
					if(novaBaseConhecimento.getIdProblema()!=null && novaBaseConhecimento.getIdBaseConhecimento()!=null){
						
							conhecimentoProblemaDao.deleteByIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
							
							conhecimentoProblemaDTO.setIdProblema(novaBaseConhecimento.getIdProblema());
							conhecimentoProblemaDTO.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
							conhecimentoProblemaDao.create(conhecimentoProblemaDTO);
					}

					if (arquivosUpados != null && !arquivosUpados.isEmpty()) {
						for (UploadDTO uploadDto : arquivosUpados) {
							if (uploadDto.getTemporario().equalsIgnoreCase("S")) {

								ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
								AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

								controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
								controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
								controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
								controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
								controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
								controleGEDDTO.setPasta(pasta);
								controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

								if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
									controleGEDDTO.setPathArquivo(uploadDto.getPath());
								} else {
									controleGEDDTO.setPathArquivo(null);
								}

								controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO, "");

								if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {

									if (controleGEDDTO != null) {
										File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDto.getNameFile()));
										CriptoUtils.encryptFile(uploadDto.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged",
												System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
										anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
										arquivo.delete();
									}
								}

								anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
								if(controleGEDDTO != null){
									anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
									anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
								}
								anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
								anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
								// Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
								Arquivo arquivo = new Arquivo(controleGEDDTO);
								anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
								anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

							} else {

								ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
								AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();
								controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
								controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
								controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
								controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
								controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
								controleGEDDTO.setPasta(pasta);
								controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());
								controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO, "");

								File arquivoAntigo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + uploadDto.getPath().substring(3) + ".ged");

								copiarArquivo(arquivoAntigo, PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");

								anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
								anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
								anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
								anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
								anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
								anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
								// Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
								Arquivo arquivo = new Arquivo(controleGEDDTO);
								anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
								anexoBaseConhecimento = (AnexoBaseConhecimentoDTO) anexoBaseConhecimentoDao.create(anexoBaseConhecimento);
							}
						}
					}

					// EXCLUO BASE DE CONHECIMENTO ANTIGA

					baseConhecimentoRelacionadoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
					importanciaConhecimentoUsuarioService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
					importanciaConhecimentoGrupoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);
					conhecimentoProblemaDao.deleteByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
					

					baseConhecimentoDao.delete(baseConhecimentoDto);
					lucene.excluirBaseConhecimento(baseConhecimentoDto,true);

					// EXCLUO OS ANEXOS DA BASE DE CONHECIMENTO ANTIGA.

					Collection<AnexoBaseConhecimentoDTO> anexosBaseConhecimento = anexoBaseConhecimentoDao.consultarAnexosDaBaseConhecimento(baseConhecimentoDto);

					if (anexosBaseConhecimento != null && !anexosBaseConhecimento.isEmpty()) {

						for (AnexoBaseConhecimentoDTO anexo : anexosBaseConhecimento) {
							anexoBaseConhecimentoDao.delete(anexo);
							deleteDir(new File(anexo.getLink()));
						}
					}

					// EXCLUO OS GEDS DA BASE DE CONHECIMENTO ANTIGA.

					Collection<ControleGEDDTO> gedsBaseConhecimento = controleGEDDao.listByIdTabelaAndIdBaseConhecimento(ControleGEDDTO.TABELA_BASECONHECIMENTO, baseConhecimentoDto.getIdBaseConhecimento());

					if (gedsBaseConhecimento != null && !gedsBaseConhecimento.isEmpty()) {
						for (ControleGEDDTO ged : gedsBaseConhecimento) {
							controleGEDDao.delete(ged);
						}
					}

					this.idBaseConhecimento = novaBaseConhecimento.getIdBaseConhecimento();

					transactionControler.commit();
					transactionControler.close();

				} catch (Exception e) {
					this.rollbackTransaction(transactionControler, e);
				}

			} else {

				// BASE APROVADA, ALTERADA NÃO SERÁ PUBLICADA
				novaBaseConhecimento.setStatus("N");

				novaBaseConhecimento.setIdBaseConhecimento(null);
				novaBaseConhecimento.setIdBaseConhecimentoPai(baseConhecimentoDto.getIdBaseConhecimento());

				try {

					// TODO CREATE
					novaBaseConhecimento = (BaseConhecimentoDTO) baseConhecimentoDao.create(novaBaseConhecimento);

					notificacaoDto = this.criarNotificacao(novaBaseConhecimento, transactionControler);

					if (notificacaoDto.getIdNotificacao() != null) {
						novaBaseConhecimento.setIdNotificacao(notificacaoDto.getIdNotificacao());
					}

					Reflexao.copyPropertyValues(novaBaseConhecimento, historicoBaseConhecimentoDto);

					historicoBaseConhecimentoDto.setIdUsuarioAlteracao(usuarioDto.getIdUsuario());

					historicoBaseConhecimentoDto.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

					if (historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento() == null) {
						historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.create(historicoBaseConhecimentoDto);
					} else {
						historicoBaseConhecimentoDto = (HistoricoBaseConhecimentoDTO) historicoBaseConhecimentoDao.createWithID(historicoBaseConhecimentoDto);
					}

					if (novaBaseConhecimento.getIdHistoricoBaseConhecimento() == null) {
						novaBaseConhecimento.setIdHistoricoBaseConhecimento(historicoBaseConhecimentoDto.getIdHistoricoBaseConhecimento());
					}

					baseConhecimentoDao.updateNotNull(novaBaseConhecimento);

					baseConhecimentoDao.restore(baseConhecimentoDto);
					
					baseConhecimentoDto.setArquivado("S");

					baseConhecimentoDao.updateNotNull(baseConhecimentoDto);
					lucene.excluirBaseConhecimento(baseConhecimentoDto,true);

					this.enviarEmailNotificacaoConhecimento(baseConhecimentoDto, transactionControler, "U");
					
					// TODO  CRIAR RELACIONAMENTO ENTRE UMA BASE DE CONHECIMENTO COM UM PROBLEMA.
					if(novaBaseConhecimento.getIdProblema()!=null && novaBaseConhecimento.getIdBaseConhecimento()!=null){
						
							conhecimentoProblemaDao.deleteByIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
							
							conhecimentoProblemaDTO.setIdProblema(novaBaseConhecimento.getIdProblema());
							conhecimentoProblemaDTO.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
							conhecimentoProblemaDao.create(conhecimentoProblemaDTO);
					}

					this.idBaseConhecimento = novaBaseConhecimento.getIdBaseConhecimento();

					if (arquivosUpados != null && !arquivosUpados.isEmpty()) {

						for (UploadDTO uploadDto : arquivosUpados) {

							if (uploadDto.getTemporario().equalsIgnoreCase("S")) {

								ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

								AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

								controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
								controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
								controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
								controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
								controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
								controleGEDDTO.setPasta(pasta);
								controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

								if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {
									controleGEDDTO.setPathArquivo(uploadDto.getPath());
								} else {
									controleGEDDTO.setPathArquivo(null);
								}

								controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO, "");

								if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) {

									if (controleGEDDTO != null) {

										File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDto.getNameFile()));

										CriptoUtils.encryptFile(uploadDto.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + 1 + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged",
												System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));

										anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");

										arquivo.delete();

									}
								}

								anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
								if(controleGEDDTO != null){
									anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
									anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
								}
								anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
								anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
								anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

							} else {

								ControleGEDDTO controleGEDDTO = new ControleGEDDTO();

								AnexoBaseConhecimentoDTO anexoBaseConhecimento = new AnexoBaseConhecimentoDTO();

								controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_BASECONHECIMENTO);
								controleGEDDTO.setId(novaBaseConhecimento.getIdBaseConhecimento());
								controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
								controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
								controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
								controleGEDDTO.setPasta(pasta);
								controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());

								controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO, "");

								File arquivoAntigo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + uploadDto.getPath().substring(3) + ".ged");

								copiarArquivo(arquivoAntigo, PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");

								anexoBaseConhecimento.setLink(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged");
								anexoBaseConhecimento.setDataInicio(UtilDatas.getDataAtual());
								anexoBaseConhecimento.setNomeAnexo(controleGEDDTO.getNomeArquivo());
								anexoBaseConhecimento.setExtensao(controleGEDDTO.getExtensaoArquivo());
								anexoBaseConhecimento.setIdBaseConhecimento(novaBaseConhecimento.getIdBaseConhecimento());
								anexoBaseConhecimento.setDescricao(uploadDto.getDescricao());
								anexoBaseConhecimentoDao.create(anexoBaseConhecimento);

							}
						}
					}
				} catch (Exception e) {
					this.rollbackTransaction(transactionControler, e);
				}
			}
		}
	}

	@Override
	public void excluir(BaseConhecimentoDTO baseConhecimentoBean, boolean isAprovaBaseConhecimento) throws Exception {

		if (isAprovaBaseConhecimento) {

			Lucene lucene = new Lucene(); 
			lucene.excluirBaseConhecimento(baseConhecimentoBean,true);

			this.excluirBaseConhecimentoComentarios(baseConhecimentoBean);

			this.excluirBaseConhecimento(baseConhecimentoBean);

			this.enviarEmailNotificacaoConhecimento(baseConhecimentoBean, null, "D");

		}
	}

	/**
	 * Atribui os valores dos atributos da nova Base de Conhecimento a ser cadastrada.
	 * 
	 * @return BaseConhecimentoDTO
	 * @author breno.guimaraes
	 * @throws Exception
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	private BaseConhecimentoDTO atribuirValoresNovaBaseConhecimento(BaseConhecimentoDTO baseConhecimento) throws ServiceException, Exception {
		BaseConhecimentoDTO novaBaseConhecimentoBean = new BaseConhecimentoDTO();

		novaBaseConhecimentoBean.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());
		novaBaseConhecimentoBean.setTitulo(baseConhecimento.getTitulo());
		novaBaseConhecimentoBean.setConteudo(baseConhecimento.getConteudo());
		novaBaseConhecimentoBean.setDataExpiracao(baseConhecimento.getDataExpiracao());
		novaBaseConhecimentoBean.setDataInicio(UtilDatas.getDataAtual());
		novaBaseConhecimentoBean.setIdPasta(baseConhecimento.getIdPasta());
		novaBaseConhecimentoBean.setStatus(baseConhecimento.getStatus());
		novaBaseConhecimentoBean.setIdUsuarioAutor(baseConhecimento.getIdUsuarioAutor());
		novaBaseConhecimentoBean.setComentarios(baseConhecimento.getComentarios());
		novaBaseConhecimentoBean.setListImportanciaConhecimentoUsuario(baseConhecimento.getListImportanciaConhecimentoUsuario());
		novaBaseConhecimentoBean.setListImportanciaConhecimentoGrupo(baseConhecimento.getListImportanciaConhecimentoGrupo());
		novaBaseConhecimentoBean.setJustificativaObservacao(baseConhecimento.getJustificativaObservacao());
		novaBaseConhecimentoBean.setFonteReferencia(baseConhecimento.getFonteReferencia());
		novaBaseConhecimentoBean.setIdNotificacao(baseConhecimento.getIdNotificacao());
		novaBaseConhecimentoBean.setListaDeUsuarioNotificacao(baseConhecimento.getListaDeUsuarioNotificacao());
		novaBaseConhecimentoBean.setListaDeGrupoNotificacao(baseConhecimento.getListaDeGrupoNotificacao());
		novaBaseConhecimentoBean.setTituloNotificacao(baseConhecimento.getTituloNotificacao());
		novaBaseConhecimentoBean.setTipoNotificacao(baseConhecimento.getTipoNotificacao());
		novaBaseConhecimentoBean.setListBaseConhecimentoRelacionado(baseConhecimento.getListBaseConhecimentoRelacionado());
		novaBaseConhecimentoBean.setFaq(baseConhecimento.getFaq());
		novaBaseConhecimentoBean.setOrigem(baseConhecimento.getOrigem());
		novaBaseConhecimentoBean.setIdHistoricoBaseConhecimento(baseConhecimento.getIdHistoricoBaseConhecimento());
		novaBaseConhecimentoBean.setArquivado(baseConhecimento.getArquivado());
		novaBaseConhecimentoBean.setIdBaseConhecimentoPai(baseConhecimento.getIdBaseConhecimentoPai());
		novaBaseConhecimentoBean.setPrivacidade(baseConhecimento.getPrivacidade());
		novaBaseConhecimentoBean.setSituacao(baseConhecimento.getSituacao());
		novaBaseConhecimentoBean.setListEventoMonitoramento(baseConhecimento.getListEventoMonitoramento());
		novaBaseConhecimentoBean.setGerenciamentoDisponibilidade(baseConhecimento.getGerenciamentoDisponibilidade());
		novaBaseConhecimentoBean.setDireitoAutoral(baseConhecimento.getDireitoAutoral());
		novaBaseConhecimentoBean.setLegislacao(baseConhecimento.getLegislacao());
		novaBaseConhecimentoBean.setErroConhecido(baseConhecimento.getErroConhecido());
		novaBaseConhecimentoBean.setIdProblema(baseConhecimento.getIdProblema());
		novaBaseConhecimentoBean.setIdHistoricoBaseConhecimento(null);

		String versao = ((BaseConhecimentoDTO) getBaseConhecimentoDao().restore(baseConhecimento)).getVersao();
		novaBaseConhecimentoBean.setVersao(versao);

		return novaBaseConhecimentoBean;
	}

	/**
	 * Exclui diretório e arquivos.
	 * 
	 * @param dir
	 * @author valdoilo.damasceno
	 */
	public boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	/**
	 * Copia Arquivo do diretÃ³rio temporÃ¡rio para DiretÃ³rio definitivo.
	 * 
	 * @param fonte
	 * @param destino
	 * @throws IOException
	 * @author valdoilo.damasceno
	 */
	public void copiarArquivo(File fonte, String destino) throws IOException {
		InputStream in;
		try {
			in = new FileInputStream(fonte);
			OutputStream out = new FileOutputStream(new File(destino));

			byte[] buf = new byte[1024];
			int len;
			try {
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Seta dataFim à Base de Conhecimento.
	 * 
	 * @param base
	 * @throws Exception
	 */
	private void excluirBaseConhecimento(BaseConhecimentoDTO base) throws Exception {
		this.excluirAnexosBaseConhecimento(base);
		base.setDataFim(UtilDatas.getDataAtual());
		Lucene lucene = new Lucene();
		lucene.excluirBaseConhecimento(base,true);
		this.getBaseConhecimentoDao().update(base);
	}

	/**
	 * Seta dataFim à Comentarios.
	 * 
	 * @param base
	 * @throws Exception
	 */
	private void excluirBaseConhecimentoComentarios(BaseConhecimentoDTO base) throws Exception {
		ComentariosDAO comentariosDao = new ComentariosDAO();

		Collection<ComentariosDTO> comentarios = comentariosDao.consultarComentarios(base);
		if (comentarios != null) {
			for (ComentariosDTO comentarioDto : comentarios) {
				comentarioDto.setDataFim(UtilDatas.getDataAtual());
				comentariosDao.update(comentarioDto);
			}
		}

	}

	/**
	 * Excluir Anexos da Base Conhecimento atribuindo sua dataFim.
	 * 
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	private void excluirAnexosBaseConhecimento(BaseConhecimentoDTO base) throws Exception {

		AnexoBaseConhecimentoDAO anexoBaseConhecimentoDao = new AnexoBaseConhecimentoDAO();

		Collection<AnexoBaseConhecimentoDTO> anexosBaseConhecimento = anexoBaseConhecimentoDao.consultarAnexosDaBaseConhecimento(base);

		if (anexosBaseConhecimento != null && !anexosBaseConhecimento.isEmpty()) {

			for (AnexoBaseConhecimentoDTO anexo : anexosBaseConhecimento) {

				anexoBaseConhecimentoDao.delete(anexo);

				deleteDir(new File(anexo.getLink()));
			}
		}
		
		Lucene lucene = new Lucene();
		lucene.excluiAnexosDaBaseConhecimento(Long.parseLong(String.valueOf(base.getIdBaseConhecimento())));
	}

	/**
	 * Retorna DAO de BaseConhecimento.
	 * 
	 * @return BaseConhecimentoDAO
	 * @throws ServiceException
	 */
	public BaseConhecimentoDAO getBaseConhecimentoDao() throws ServiceException {
		return (BaseConhecimentoDAO) this.getDao();
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new BaseConhecimentoDAO();
	}

	@Override
	protected void validaCreate(Object arg0) throws Exception {

	}

	@Override
	protected void validaDelete(Object arg0) throws Exception {

	}

	@Override
	protected void validaFind(Object arg0) throws Exception {

	}

	@Override
	protected void validaUpdate(Object arg0) throws Exception {

	}

	/**
	 * @return valor do atributo subDiretorio.
	 */
	public File getPastaDaBaseConhecimento() {
		return pastaDaBaseConhecimento;
	}

	/**
	 * Define valor do atributo subDiretorio.
	 * 
	 * @param pastaDaBaseConhecimento
	 */
	public void setPastaDaBaseConhecimento(File pastaDaBaseConhecimento) {
		this.pastaDaBaseConhecimento = pastaDaBaseConhecimento;
	}

	@Override
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByPasta(PastaDTO pasta) throws Exception {
		try {
			return this.getBaseConhecimentoDao().listarBaseConhecimentoByPasta(pasta);
		} catch (LogicException e) {
			throw e;
		}
	}

	@Override
	public Double calcularNota(Integer idBaseConhecimento) throws Exception {
		ComentariosDAO comentarioDao = new ComentariosDAO();
		try {
			return comentarioDao.calcularNota(idBaseConhecimento);
		} catch (LogicException e) {
			throw e;
		}
	}

	@Override
	public Long contarVotos(Integer idBaseConhecimento) throws Exception {
		ComentariosDAO comentarioDao = new ComentariosDAO();
		try {
			return comentarioDao.contarVotos(idBaseConhecimento);
		} catch (LogicException e) {
			throw e;
		}
	}

	@Override
	public boolean verificarSeBaseConhecimentoJaPossuiNovaVersao(BaseConhecimentoDTO baseConhecimento) throws Exception {
		BaseConhecimentoDAO baseDao = new BaseConhecimentoDAO();

		return baseDao.verificarSeBaseConhecimentoJaPossuiNovaVersao(baseConhecimento);
	}

	public boolean verificarSeBaseConhecimentoPossuiVersaoAnterior(BaseConhecimentoDTO baseConhecimento) throws Exception {
		String versao = StringUtils.remove(baseConhecimento.getTitulo(), baseConhecimento.getVersao());
		if (StringUtils.isBlank(versao)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean verificaBaseConhecimentoExistente(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		BaseConhecimentoDAO baseDao = new BaseConhecimentoDAO();
		return baseDao.verificaSeBaseConhecimentoExiste(baseConhecimentoDTO);
	}

	@Override
	public List<BaseConhecimentoDTO> validaNota(BaseConhecimentoDTO baseconhecimento) throws Exception {
		return this.getBaseConhecimentoDao().validaNota(baseconhecimento);
	}

	@Override
	public Collection<BaseConhecimentoDTO> listaBaseConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception {
		
		return  getBaseConhecimentoDao().listaBaseConhecimento(baseConhecimento);

	}

	@Override
	public Collection<BaseConhecimentoDTO> listaBaseConhecimentoUltimasVersoes(BaseConhecimentoDTO baseConhecimento) throws Exception {
		return getBaseConhecimentoDao().listaBaseConhecimentoUltimasVersoes(baseConhecimento);

	}

	/**
	 * Verifica se usuário aprova Base Conhecimento na pasta selecionada.
	 * 
	 * @param usuarioDto
	 * @param idPasta
	 * @return true = aprova; false = não aprova.
	 * @throws ServiceException
	 * @throws Exception
	 */
	private boolean usuarioAprovaBaseConhecimento(UsuarioDTO usuarioDto, Integer idPasta) throws ServiceException, Exception {

		boolean aprovaBaseConhecimento = false;

		PerfilAcessoPastaService perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);

		aprovaBaseConhecimento = perfilAcessoPastaService.verificarSeUsuarioAprovaBaseConhecimentoParaPastaSelecionada(usuarioDto, idPasta);

		return aprovaBaseConhecimento;
	}

	/**
	 * Cria comentários da Base de Conhecimento.
	 * 
	 * @param baseConhecimentoDto
	 * @param transactionControler
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	private void criarComentarios(BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws Exception {

		Collection<ComentariosDTO> comentarios = (Collection<ComentariosDTO>) baseConhecimentoDto.getComentarios();

		ComentariosDAO comentariosDao = new ComentariosDAO();

		comentariosDao.setTransactionControler(transactionControler);

		if (comentarios != null && !comentarios.isEmpty()) {

			for (ComentariosDTO comentario : comentarios) {

				comentario.setDataInicio(UtilDatas.getDataAtual());

				comentariosDao.create(comentario);

			}
		}

	}

	public void criarImportanciaConhecimentoUsuario(BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws ServiceException, Exception {

		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {

			ImportanciaConhecimentoUsuarioService importanciaConhecimentoUsuarioService = (ImportanciaConhecimentoUsuarioService) ServiceLocator.getInstance()
					.getService(ImportanciaConhecimentoUsuarioService.class, null);

			if (transactionControler == null) {
				transactionControler = new TransactionControlerImpl(this.getBaseConhecimentoDao().getAliasDB());
			}

			importanciaConhecimentoUsuarioService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);

			Collection<ImportanciaConhecimentoUsuarioDTO> listImportanciaConhecimentoUsuario = baseConhecimentoDto.getListImportanciaConhecimentoUsuario();
			if (listImportanciaConhecimentoUsuario != null && !listImportanciaConhecimentoUsuario.isEmpty()) {
				for (ImportanciaConhecimentoUsuarioDTO importanciaConhecimentoUsuario : listImportanciaConhecimentoUsuario) {
					if (importanciaConhecimentoUsuario.getIdBaseConhecimento() == null) {
						importanciaConhecimentoUsuario.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
						importanciaConhecimentoUsuarioService.create(importanciaConhecimentoUsuario, transactionControler);
					} else {
						importanciaConhecimentoUsuario.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
						importanciaConhecimentoUsuarioService.create(importanciaConhecimentoUsuario, transactionControler);
					}
				}
			}
		}
	}

	public void criarImportanciaConhecimentoGrupo(BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws Exception {

		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
			ImportanciaConhecimentoGrupoService importanciaConhecimentoGrupoService = (ImportanciaConhecimentoGrupoService) ServiceLocator.getInstance().getService(ImportanciaConhecimentoGrupoService.class, null);

			if (transactionControler == null) {
				transactionControler = new TransactionControlerImpl(this.getBaseConhecimentoDao().getAliasDB());
			}

			importanciaConhecimentoGrupoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);

			Collection<ImportanciaConhecimentoGrupoDTO> listImportanciaConhecimentoGrupo = baseConhecimentoDto.getListImportanciaConhecimentoGrupo();
			if (listImportanciaConhecimentoGrupo != null && !listImportanciaConhecimentoGrupo.isEmpty()) {
				for (ImportanciaConhecimentoGrupoDTO importanciaConhecimentoGrupo : listImportanciaConhecimentoGrupo) {
					if (importanciaConhecimentoGrupo.getIdBaseConhecimento() == null) {
						importanciaConhecimentoGrupo.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
						importanciaConhecimentoGrupoService.create(importanciaConhecimentoGrupo, transactionControler);
					} else {
						importanciaConhecimentoGrupo.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
						importanciaConhecimentoGrupoService.create(importanciaConhecimentoGrupo, transactionControler);
					}
				}
			}
		}
	}

	public void criarRelacionamentoEntreConhecimentos(BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws Exception {
		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
			BaseConhecimentoRelacionadoService baseConhecimentoRelacionadoService = (BaseConhecimentoRelacionadoService) ServiceLocator.getInstance().getService(BaseConhecimentoRelacionadoService.class, null);

			if (transactionControler == null) {
				transactionControler = new TransactionControlerImpl(this.getBaseConhecimentoDao().getAliasDB());
			}

			baseConhecimentoRelacionadoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);

			Collection<BaseConhecimentoRelacionadoDTO> listBaseConhecimentoRelacionado = baseConhecimentoDto.getListBaseConhecimentoRelacionado();
			if (listBaseConhecimentoRelacionado != null && !listBaseConhecimentoRelacionado.isEmpty()) {
				for (BaseConhecimentoRelacionadoDTO baseConhecimentoRelacionado : listBaseConhecimentoRelacionado) {
					if (baseConhecimentoRelacionado.getIdBaseConhecimento() == null) {
						baseConhecimentoRelacionado.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
						baseConhecimentoRelacionadoService.create(baseConhecimentoRelacionado, transactionControler);
					} else {
						baseConhecimentoRelacionado.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
						baseConhecimentoRelacionadoService.create(baseConhecimentoRelacionado, transactionControler);
					}
				}
			}
		}
	}

	/**
	 * Cria relacionamento entre Evento Monitoramento e Conhecimento.
	 * 
	 * @param baseConhecimentoDto
	 * @param transactionControler
	 * @throws ServiceException
	 * @throws Exception
	 * @author Thays
	 */
	public void criarRelacionamentoEntreEventoMonitConhecimento(BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws ServiceException, Exception {

		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
			EventoMonitConhecimentoService eventoMonitConhecimentoService = (EventoMonitConhecimentoService) ServiceLocator.getInstance().getService(EventoMonitConhecimentoService.class, null);

			if (transactionControler == null) {
				transactionControler = new TransactionControlerImpl(this.getBaseConhecimentoDao().getAliasDB());
			}

			eventoMonitConhecimentoService.deleteByIdConhecimento(baseConhecimentoDto.getIdBaseConhecimento(), transactionControler);

			Collection<EventoMonitConhecimentoDTO> listEventoMonitConhecimento = baseConhecimentoDto.getListEventoMonitoramento();
			if (listEventoMonitConhecimento != null && !listEventoMonitConhecimento.isEmpty()) {
				for (EventoMonitConhecimentoDTO eventoMonitConhecimento : listEventoMonitConhecimento) {
					eventoMonitConhecimento.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
					eventoMonitConhecimentoService.create(eventoMonitConhecimento, transactionControler);
				}
			}
		}
	}

	public NotificacaoDTO criarNotificacao(BaseConhecimentoDTO baseConhecimentoDto, TransactionControler transactionControler) throws ServiceException, Exception {

		NotificacaoDTO notificacaoDto = new NotificacaoDTO();

		NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);

		if (baseConhecimentoDto.getTituloNotificacao() != null && !StringUtils.isEmpty(baseConhecimentoDto.getTituloNotificacao().trim()) && baseConhecimentoDto.getTipoNotificacao() != null
				&& !StringUtils.isEmpty(baseConhecimentoDto.getTipoNotificacao().trim())) {

			if (transactionControler == null) {
				transactionControler = new TransactionControlerImpl(this.getBaseConhecimentoDao().getAliasDB());
			}

			if (baseConhecimentoDto.getIdNotificacao() != null) {
				notificacaoDto.setIdNotificacao(baseConhecimentoDto.getIdNotificacao());
				notificacaoDto.setListaDeUsuario(baseConhecimentoDto.getListaDeUsuarioNotificacao());
				notificacaoDto.setListaDeGrupo(baseConhecimentoDto.getListaDeGrupoNotificacao());
				notificacaoDto.setTitulo(baseConhecimentoDto.getTituloNotificacao());
				notificacaoDto.setTipoNotificacao(baseConhecimentoDto.getTipoNotificacao());
				notificacaoDto.setOrigemNotificacao(Enumerados.OrigemNotificacao.B.name());

				notificacaoService.update(notificacaoDto, transactionControler);

				return notificacaoDto;

			} else {
				notificacaoDto.setListaDeUsuario(baseConhecimentoDto.getListaDeUsuarioNotificacao());
				notificacaoDto.setListaDeGrupo(baseConhecimentoDto.getListaDeGrupoNotificacao());
				notificacaoDto.setTitulo(baseConhecimentoDto.getTituloNotificacao());
				notificacaoDto.setTipoNotificacao(baseConhecimentoDto.getTipoNotificacao());
				notificacaoDto.setOrigemNotificacao(Enumerados.OrigemNotificacao.B.name());

				return (NotificacaoDTO) notificacaoService.create(notificacaoDto, transactionControler);
			}
		} else {
			return notificacaoDto;
		}
	}

	@Override
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoFAQByPasta(PastaDTO pasta) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();

		return baseConhecimentoDao.listarBaseConhecimentoFAQByPasta(pasta);
	}

	private void enviarEmailNotificacaoConhecimento(BaseConhecimentoDTO baseConhecimentoDTO, TransactionControler transactionControler, String crud) throws Exception {

		EmpregadoDao empregadoDao = new EmpregadoDao();
		NotificacaoDao notificacaoDao = new NotificacaoDao();

		Collection<EmpregadoDTO> colEmpregados = new ArrayList();

		if (transactionControler != null) {
			empregadoDao.setTransactionControler(transactionControler);
			notificacaoDao.setTransactionControler(transactionControler);
		}

		if (baseConhecimentoDTO.getIdNotificacao() != null && baseConhecimentoDTO.getIdNotificacao() != 0) {

			String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
			String ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO, "8");
			String ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO, "9");
			String ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO, "10");
			String ID_MODELO_EMAIL = "";

			if (ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO == null || ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO.isEmpty()) {
				ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO = "11";
			}

			if (ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO == null || ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO.isEmpty()) {
				ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO = "12";
			}

			if (ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO == null || ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO.isEmpty()) {
				ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO = "13";
			}

			if (crud.equals("C")) {
				if (baseConhecimentoDTO.getTipoNotificacao().equals("T") || baseConhecimentoDTO.getTipoNotificacao().equals("C")) {
					ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO;
				}
			} else if (crud.equals("U")) {
				if (baseConhecimentoDTO.getTipoNotificacao().equals("T") || baseConhecimentoDTO.getTipoNotificacao().equals("A")) {
					ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO;
				}
			} else {
				if (baseConhecimentoDTO.getTipoNotificacao().equals("T") || baseConhecimentoDTO.getTipoNotificacao().equals("E")) {
					ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO;
				}
			}

			if (!ID_MODELO_EMAIL.isEmpty()) {

				colEmpregados = empregadoDao.listarEmailsNotificacoesConhecimento(baseConhecimentoDTO.getIdBaseConhecimento());

				if (colEmpregados != null) {

					for (EmpregadoDTO empregados : colEmpregados) {

						MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL.trim()), new IDto[] { baseConhecimentoDTO });

						if (empregados.getEmail() != null) {
							mensagem.envia(empregados.getEmail(), "", remetente);
						}

					}
				}

			}
		}

	}

	@Override
	public BaseConhecimentoDTO getBaseConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		return baseConhecimentoDao.getBaseConhecimento(baseConhecimento);
	}

	@Override
	public Collection<BaseConhecimentoDTO> listPesquisaBaseConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		return baseConhecimentoDao.listPesquisaBaseConhecimento(baseConhecimento);
	}

	@Override
	public void arquivarConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		baseConhecimentoDto.setArquivado("S");
		baseConhecimentoDao.arquivarConhecimento(baseConhecimentoDto);
	}

	public Integer obterGrauDeImportanciaParaUsuario(BaseConhecimentoDTO baseConhecimentoDto, UsuarioDTO usuarioDto) throws Exception {

		ImportanciaConhecimentoGrupoService importanciaConhecimentoGrupoService = (ImportanciaConhecimentoGrupoService) ServiceLocator.getInstance().getService(ImportanciaConhecimentoGrupoService.class, null);
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);

		Collection<GrupoEmpregadoDTO> listGrupoEmpregadoDto = grupoEmpregadoService.findByIdEmpregado(usuarioDto.getIdEmpregado());

		ImportanciaConhecimentoGrupoDTO importanciaConhecimento = importanciaConhecimentoGrupoService.obterGrauDeImportancia(baseConhecimentoDto, listGrupoEmpregadoDto, usuarioDto);

		if (importanciaConhecimento != null) {
			return Integer.parseInt(importanciaConhecimento.getGrauImportancia());
		} else {
			return 0;
		}
	}

	@Override
	public void restaurarConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		baseConhecimentoDto.setArquivado("N");
		baseConhecimentoDao.restaurarConhecimento(baseConhecimentoDto);
	}

	@Override
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByPastaRelatorio(PastaDTO pasta) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		return baseConhecimentoDao.listarBaseConhecimentoByPastaRelatorio(pasta);
	}

	@Override
	public Collection<BaseConhecimentoDTO> obterHistoricoDeVersoes(BaseConhecimentoDTO baseConhecimento) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		return baseConhecimentoDao.obterHistoricoDeVersoes(baseConhecimento);
	}

	@Override
	public void gravarSolicitacoesConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception {

		if (baseConhecimento.getIdBaseConhecimento() != null) {
			ConhecimentoSolicitacaoDao conhecimentoSolicitacaoDao = new ConhecimentoSolicitacaoDao();

			conhecimentoSolicitacaoDao.deleteByIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

			try {
				if (baseConhecimento.getColItensIncidentes() != null) {

					for (Iterator it = baseConhecimento.getColItensIncidentes().iterator(); it.hasNext();) {

						SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) it.next();

						ConhecimentoSolicitacaoDTO conhecimentoSolicitacaoDto = new ConhecimentoSolicitacaoDTO();

						conhecimentoSolicitacaoDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
						conhecimentoSolicitacaoDto.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

						conhecimentoSolicitacaoDao.create(conhecimentoSolicitacaoDto);
					}
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	@Override
	public void gravarProblemasConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception {

		if (baseConhecimento.getIdBaseConhecimento() != null) {
			ConhecimentoProblemaDao conhecimentoProblemaDAO = new ConhecimentoProblemaDao();

			conhecimentoProblemaDAO.deleteByIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

			try {
				if (baseConhecimento.getColItensProblema() != null) {

					for (Iterator it = baseConhecimento.getColItensProblema().iterator(); it.hasNext();) {

						ProblemaDTO problemaDTO = (ProblemaDTO) it.next();

						ConhecimentoProblemaDTO conhecimentoProblemaDTO = new ConhecimentoProblemaDTO();

						conhecimentoProblemaDTO.setIdProblema(problemaDTO.getIdProblema());
						conhecimentoProblemaDTO.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

						conhecimentoProblemaDAO.create(conhecimentoProblemaDTO);
					}
				}
				
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	@Override
	public void gravarMudancaConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception {

		if (baseConhecimento.getIdBaseConhecimento() != null) {

			ConhecimentoMudancaDao conhecimentoMudancaDao = new ConhecimentoMudancaDao();
			conhecimentoMudancaDao.deleteByIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

			try {
				if (baseConhecimento.getColItensMudanca() != null) {

					for (Iterator it = baseConhecimento.getColItensMudanca().iterator(); it.hasNext();) {

						RequisicaoMudancaDTO requisicaoMudancaDTO = (RequisicaoMudancaDTO) it.next();

						ConhecimentoMudancaDTO conhecimentoMudancaDTO = new ConhecimentoMudancaDTO();

						conhecimentoMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());
						conhecimentoMudancaDTO.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

						conhecimentoMudancaDao.create(conhecimentoMudancaDTO);
					}
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
	
	@Override
	public void gravarLiberacaoConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception {
		
		if (baseConhecimento.getIdBaseConhecimento() != null) {
			
			ConhecimentoLiberacaoDao conhecimentoLiberacaoDao = new ConhecimentoLiberacaoDao();
			
			conhecimentoLiberacaoDao.deleteByIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());
			
			try {
				if (baseConhecimento.getColItensLiberacao() != null) {
					
					for (Iterator it = baseConhecimento.getColItensLiberacao().iterator(); it.hasNext();) {
						
						RequisicaoLiberacaoDTO requisicaoLiberacaoDTO = (RequisicaoLiberacaoDTO) it.next();
						
						ConhecimentoLiberacaoDTO conhecimentoLiberacaoDTO = new ConhecimentoLiberacaoDTO();
						
						conhecimentoLiberacaoDTO.setIdRequisicaoLiberacao(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
						conhecimentoLiberacaoDTO.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());
						
						conhecimentoLiberacaoDao.create(conhecimentoLiberacaoDTO);
					}
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	@Override
	public void gravarICConhecimento(BaseConhecimentoDTO baseConhecimento) throws Exception {

		if (baseConhecimento.getIdBaseConhecimento() != null) {
			ConhecimentoICDao conhecimentoICDao = new ConhecimentoICDao();

			conhecimentoICDao.deleteByIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

			try {
				if (baseConhecimento.getColItensICSerialize() != null) {

					for (Iterator it = baseConhecimento.getColItensICSerialize().iterator(); it.hasNext();) {

						ItemConfiguracaoDTO itemConfiguracaoDto = (ItemConfiguracaoDTO) it.next();

						ConhecimentoICDTO conhecimentoICDTO = new ConhecimentoICDTO();

						conhecimentoICDTO.setIdItemConfiguracao(itemConfiguracaoDto.getIdItemConfiguracao());
						conhecimentoICDTO.setIdBaseConhecimento(baseConhecimento.getIdBaseConhecimento());

						conhecimentoICDao.create(conhecimentoICDTO);
					}
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
	
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByIds(Integer[] ids) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		return baseConhecimentoDao.listarBaseConhecimentoByIds(ids);
	}
	
	@Override
	public void updateNotNull(IDto obj) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		baseConhecimentoDao.updateNotNull(obj);
	}
	
	@Override
	public Collection<BaseConhecimentoDTO> quantidadeBaseConhecimentoPorPeriodo(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		
		Collection<BaseConhecimentoDTO> listaBaseConhecimento = null;
		Collection<BaseConhecimentoDTO> listaQuantitativoBaseConhecimentoDTOs = new ArrayList<BaseConhecimentoDTO>();
		
		BaseConhecimentoDTO relatorioBaseConhecimentoDTO = new BaseConhecimentoDTO();
		
		Integer qtdPublicados = 0, qtdNaoPublicados = 0, qtdAcessados = 0, qtdAvaliados = 0, qtdExcluidos = 0, qtdArquivados = 0, qtdAtualizados = 0, tipoFaq = 0, qtdErroConhecido = 0,
		qtdDocumentos = 0;
		
		try {
			listaBaseConhecimento = ((BaseConhecimentoDAO) getDao()).listaBaseConhecimentoTotal(baseConhecimentoDTO);
			if (listaBaseConhecimento != null) {
				for (BaseConhecimentoDTO baseConhecimentoDTO2 : listaBaseConhecimento) {
					//Conta publicados e criados (não publicados) no período
					if(baseConhecimentoDTO2.getStatus().trim().equalsIgnoreCase("S")){
						boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataPublicacao(), baseConhecimentoDTO.getDataInicio(), baseConhecimentoDTO.getDataFim());
						if(resp)
							qtdPublicados++;
					}else{
						boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataInicio(), baseConhecimentoDTO.getDataInicio(), baseConhecimentoDTO.getDataFim());
						if(resp)
							qtdNaoPublicados++;
					}
					
					//Conta excluídos
					if (baseConhecimentoDTO2.getDataFim() != null) {
						boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataFim(), baseConhecimentoDTO.getDataInicio(), baseConhecimentoDTO.getDataFim());
						if(resp)
							qtdExcluidos++;
					}
					
					//Conta arquivados
					if(baseConhecimentoDTO2.getArquivado() != null && baseConhecimentoDTO2.getArquivado().trim().equalsIgnoreCase("S")){
						boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataInicio(), baseConhecimentoDTO.getDataInicio(), baseConhecimentoDTO.getDataFim());
						if(resp)
							qtdArquivados++;
					}
					
					//Conta tipo FAQ
					if(baseConhecimentoDTO2.getFaq() != null && baseConhecimentoDTO2.getFaq().trim().equalsIgnoreCase("S")){
						boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataInicio(), baseConhecimentoDTO.getDataInicio(), baseConhecimentoDTO.getDataFim());
						if(resp)
							tipoFaq++;
					}
					
					//Conta tipo erro conhecidos
					if(baseConhecimentoDTO2.getErroConhecido() != null && baseConhecimentoDTO2.getErroConhecido().trim().equalsIgnoreCase("S")){
						boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataInicio(), baseConhecimentoDTO.getDataInicio(), baseConhecimentoDTO.getDataFim());
						if(resp)
							qtdErroConhecido++;
					}
					
					//Conta Bases de Conhecimento que são qualificadas como Documento
					if((baseConhecimentoDTO2.getFaq() == null || !baseConhecimentoDTO2.getFaq().trim().equalsIgnoreCase("S")) &&
					   (baseConhecimentoDTO2.getErroConhecido() == null || !baseConhecimentoDTO2.getErroConhecido().trim().equalsIgnoreCase("S"))){
						boolean resp = UtilDatas.dataEntreIntervalo(baseConhecimentoDTO2.getDataInicio(), baseConhecimentoDTO.getDataInicio(), baseConhecimentoDTO.getDataFim());
						if(resp)
							qtdDocumentos++;
					}					
					
				}
			}
			
			//Conta acessados no período
			ContadorAcessoService contadorAcessoService = (ContadorAcessoService) ServiceLocator.getInstance().getService(ContadorAcessoService.class, null);
			Integer contadorAcesso = contadorAcessoService.quantidadesDeAcessoPorPeriodo(baseConhecimentoDTO);
			if(contadorAcesso != null){
				qtdAcessados = contadorAcesso;
			}
			
			//Conta avaliados no período
			ComentariosService comentariosService = (ComentariosService) ServiceLocator.getInstance().getService(ComentariosService.class, null);
			Integer contadorComentarios = comentariosService.consultarComentariosPorPeriodo(baseConhecimentoDTO);
			if(contadorComentarios != null){
				qtdAvaliados++;
			}
			
			//Conta atualizados no período
			HistoricoBaseConhecimentoService historicoBaseConhecimentoService = (HistoricoBaseConhecimentoService) ServiceLocator.getInstance().getService(HistoricoBaseConhecimentoService.class, null);
			Collection<HistoricoBaseConhecimentoDTO> listHistoricoAlteracao = historicoBaseConhecimentoService.list();
			if(listHistoricoAlteracao != null){
				for (HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDTO : listHistoricoAlteracao) {
					Timestamp alteracao = historicoBaseConhecimentoDTO.getDataHoraAlteracao();
					if(alteracao != null){
						Date dataAlteracao = new Date(alteracao.getTime());
						boolean resp = UtilDatas.dataEntreIntervalo(dataAlteracao, baseConhecimentoDTO.getDataInicio(), baseConhecimentoDTO.getDataFim());
						if(resp){
							qtdAtualizados++;
						}
					}
				}
			}
			
			relatorioBaseConhecimentoDTO.setQtdPublicados(qtdPublicados);
			relatorioBaseConhecimentoDTO.setQtdNaoPublicados(qtdNaoPublicados);
			relatorioBaseConhecimentoDTO.setQtdAcessados(qtdAcessados);
			relatorioBaseConhecimentoDTO.setQtdAvaliados(qtdAvaliados);
			relatorioBaseConhecimentoDTO.setQtdExcluidos(qtdExcluidos);
			relatorioBaseConhecimentoDTO.setQtdArquivados(qtdArquivados);
			relatorioBaseConhecimentoDTO.setQtdAtualizados(qtdAtualizados);
			relatorioBaseConhecimentoDTO.setTipoFaq(tipoFaq);
			relatorioBaseConhecimentoDTO.setQtdErroConhecido(qtdErroConhecido); 
			relatorioBaseConhecimentoDTO.setQtdDocumentos(qtdDocumentos);
			
			listaQuantitativoBaseConhecimentoDTOs.add(relatorioBaseConhecimentoDTO);
			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaQuantitativoBaseConhecimentoDTOs;
	}
	
	@Override
	public Collection<ComentariosDTO> consultaConhecimentosAvaliados(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		ComentariosDAO dao = new ComentariosDAO();
		Collection<ComentariosDTO> listaComentarios = new ArrayList<ComentariosDTO>();
		try {
			listaComentarios = dao.consultarComentariosPorPeriodo(baseConhecimentoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return listaComentarios;
	}

	@Override
	public Collection<BaseConhecimentoDTO> consultaConhecimentosPorAutores(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		List<BaseConhecimentoDTO> listaConhecimentoPorAutor = new ArrayList<BaseConhecimentoDTO>();
		try {
			listaConhecimentoPorAutor = (List<BaseConhecimentoDTO>) baseConhecimentoDao.consultaConhecimentoPorAutor(baseConhecimentoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaConhecimentoPorAutor;
	}

	@Override
	public Collection<BaseConhecimentoDTO> consultaConhecimentosPorAprovadores(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		List<BaseConhecimentoDTO> listaConhecimentoPorAprovador = new ArrayList<BaseConhecimentoDTO>();
		try {
			listaConhecimentoPorAprovador = (List<BaseConhecimentoDTO>) baseConhecimentoDao.consultaConhecimentoPorAprovador(baseConhecimentoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaConhecimentoPorAprovador;
	}
			
	@Override
	public Collection<BaseConhecimentoDTO> consultaConhecimentosPublicadosPorOrigem(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		List<BaseConhecimentoDTO> listaConsultaConhecimentoPublicadosPorOrigem = new ArrayList<BaseConhecimentoDTO>();
		try {
			listaConsultaConhecimentoPublicadosPorOrigem = (List<BaseConhecimentoDTO>) baseConhecimentoDao.consultaConhecimentosPublicadosPorOrigem(baseConhecimentoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaConsultaConhecimentoPublicadosPorOrigem;
	}
	
	@Override
	public Collection<BaseConhecimentoDTO> consultaConhecimentosNaoPublicadosPorOrigem(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		List<BaseConhecimentoDTO> listaConsultaConhecimentoNaoPublicadosPorOrigem = new ArrayList<BaseConhecimentoDTO>();
		try {
			listaConsultaConhecimentoNaoPublicadosPorOrigem = (List<BaseConhecimentoDTO>) baseConhecimentoDao.consultaConhecimentosNaoPublicadosPorOrigem(baseConhecimentoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaConsultaConhecimentoNaoPublicadosPorOrigem;
	}
	
	@Override
	public Collection<BaseConhecimentoDTO> consultaConhecimentoQuantitativoEmLista(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		List<BaseConhecimentoDTO> listaConsultaConhecimentoQuantitativoEmLista = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaIncidente = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaRequisitos = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaProblema = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaMudanca = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaIC = new ArrayList<BaseConhecimentoDTO>();
		List<BaseConhecimentoDTO> listaServico = new ArrayList<BaseConhecimentoDTO>();
		
		try {
			listaConsultaConhecimentoQuantitativoEmLista = (List<BaseConhecimentoDTO>) baseConhecimentoDao.consultaConhecimentoQuantitativoEmLista(baseConhecimentoDTO);
			for (BaseConhecimentoDTO baseConhecimentoDTO2 : listaConsultaConhecimentoQuantitativoEmLista) {
				listaIncidente = (List<BaseConhecimentoDTO>) baseConhecimentoDao.consultaIncidenteLista(baseConhecimentoDTO2);
				baseConhecimentoDTO2.setListaIncidente(listaIncidente);
				listaRequisitos = (List<BaseConhecimentoDTO>) baseConhecimentoDao.consultaRequisicaoLista(baseConhecimentoDTO2);
				baseConhecimentoDTO2.setListaRequisitos(listaRequisitos);
				listaProblema = (List<BaseConhecimentoDTO>) baseConhecimentoDao.consultaProblemaLista(baseConhecimentoDTO2);
				baseConhecimentoDTO2.setListaProblema(listaProblema);
				listaMudanca = (List<BaseConhecimentoDTO>) baseConhecimentoDao.consultaMudancaLista(baseConhecimentoDTO2);
				baseConhecimentoDTO2.setListaMudanca(listaMudanca);
				listaIC = (List<BaseConhecimentoDTO>) baseConhecimentoDao.consultaItemConfiguracaoLista(baseConhecimentoDTO2);
				baseConhecimentoDTO2.setListaIC(listaIC);
				listaServico = (List<BaseConhecimentoDTO>) baseConhecimentoDao.consultaServicoLista(baseConhecimentoDTO2);
				baseConhecimentoDTO2.setListaServico(listaServico);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaConsultaConhecimentoQuantitativoEmLista;
	}
	
	@Override
	public Collection findByServico(SolicitacaoServicoDTO solicitacaoServicoDto) throws ServiceException, LogicException {
		BaseConhecimentoDAO baseConhecimentoDAO = new BaseConhecimentoDAO();

		try {
			return baseConhecimentoDAO.findByServico(solicitacaoServicoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoErroConhecidoByPasta(PastaDTO pasta) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		return baseConhecimentoDao.listarBaseConhecimentoErroConhecidoByPasta(pasta);
	}	
	
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoFAQ() throws Exception {
		BaseConhecimentoDAO dao = new BaseConhecimentoDAO();
		return dao.listarBaseConhecimentoFAQ();
	}
	
	@Override
	public Collection<BaseConhecimentoDTO> listarBasesConhecimentoPublicadas() throws Exception {
		return  getBaseConhecimentoDao().listarBasesConhecimentoPublicadas();
	}
	
	@Override
	public String verificaIdScriptOrientacao(HashMap mapFields) throws Exception {
		BaseConhecimentoDAO baseConhecimentoDao = new BaseConhecimentoDAO();
		List<BaseConhecimentoDTO> listaBaseConhecimento = null;
		String id = mapFields.get("IDBASECONHECIMENTO").toString().trim();
		if ((id==null)||(id.equals(""))){
			//Campo SCRIPT de Orientação (Base de Conhecimento) não é obrigatorio, por isso passei um valor qualquer para não validar mais.
			id="campoVazio";
			return id;
		}
		if (UtilStrings.soContemNumeros(id)) {
			Integer idBaseConhecimento = (Integer) Integer.parseInt(id);
			listaBaseConhecimento = baseConhecimentoDao.findByIdBaseConhecimento(idBaseConhecimento);
		} else {
			listaBaseConhecimento = baseConhecimentoDao.findByBaseConhecimento(id);
		}
		if((listaBaseConhecimento != null)&&(listaBaseConhecimento.size()>0)){
			return String.valueOf(listaBaseConhecimento.get(0).getIdBaseConhecimento());
		}else{
			return "0";
		}
	}	

}