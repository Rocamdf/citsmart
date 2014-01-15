package br.com.centralit.citcorpore.negocio;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.AuditoriaItemConfigDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.CaracteristicaTipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoValorDTO;
import br.com.centralit.citcorpore.bean.ItemCfgSolicitacaoServDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoListaNegraEncontradosDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.SoftwaresListaNegraDTO;
import br.com.centralit.citcorpore.bean.SoftwaresListaNegraEncontradosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.AuditoriaItemConfigDao;
import br.com.centralit.citcorpore.integracao.CaracteristicaDao;
import br.com.centralit.citcorpore.integracao.CaracteristicaTipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.GrupoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.HistoricoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.HistoricoValorDAO;
import br.com.centralit.citcorpore.integracao.ItemCfgSolicitacaoServDAO;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.ProblemaDAO;
import br.com.centralit.citcorpore.integracao.ProblemaItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.SoftwaresListaNegraDao;
import br.com.centralit.citcorpore.integracao.SoftwaresListaNegraEncontradosDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.TipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.ValorDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;

/**
 * @author Maycon.Fernandes
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class ItemConfiguracaoServiceEjb extends CrudServicePojoImpl implements ItemConfiguracaoService {
	//public static Semaphore semaphoro_ItemConfiguracaoServiceEjb = new Semaphore(1);
	
	private static final long serialVersionUID = -4101073129183404628L;

	/** DAO de Valor. */
	private ValorDao valorDao = new ValorDao();

	private boolean situacao = false;

	protected CrudDAO getDao() throws ServiceException {
		return new ItemConfiguracaoDao();
	}

	protected CrudDAO getHistoricoItemDao() throws ServiceException {
		return new HistoricoItemConfiguracaoDAO();
	}

	protected CrudDAO getAuditoriaICDao() throws ServiceException {
		return new AuditoriaItemConfigDao();
	}

	protected CrudDAO getHistoricoVlrDao() throws ServiceException {
		return new HistoricoValorDAO();
	}

	protected CrudDAO getRequisicaoMudancaItemDao() throws ServiceException {
		return new RequisicaoMudancaItemConfiguracaoDao();
	}

	protected CrudDAO getRequisicaoLiberacaoItemDao() throws ServiceException {
		return new RequisicaoLiberacaoItemConfiguracaoDao();
	}

	protected CrudDAO getProblemaItemDao() throws ServiceException {
		return new ProblemaItemConfiguracaoDAO();
	}

	protected CrudDAO getIncidenteItemDao() throws ServiceException {
		return new ItemCfgSolicitacaoServDAO();
	}

	public HistoricoValorDAO getHistoricoValorDao() throws ServiceException {
		return (HistoricoValorDAO) getHistoricoVlrDao();
	}

	public ItemCfgSolicitacaoServDAO getIncidenteItemConfiguracaoDAO() throws ServiceException {
		return (ItemCfgSolicitacaoServDAO) getIncidenteItemDao();
	}

	public ProblemaItemConfiguracaoDAO getProblemaItemConfiguracaoDAO() throws ServiceException {
		return (ProblemaItemConfiguracaoDAO) getProblemaItemDao();
	}

	public RequisicaoMudancaItemConfiguracaoDao getRequisicaoMudancaItemConfiguracaoDao() throws ServiceException {
		return (RequisicaoMudancaItemConfiguracaoDao) getRequisicaoMudancaItemDao();
	}

	public ItemConfiguracaoDao getItemConfiguracaoDao() throws ServiceException {
		return (ItemConfiguracaoDao) getDao();
	}

	public RequisicaoLiberacaoItemConfiguracaoDao getRequisicaoLiberacaoItemConfiguracaoDao() throws ServiceException {
		return (RequisicaoLiberacaoItemConfiguracaoDao) getRequisicaoLiberacaoItemDao();
	}

	public HistoricoItemConfiguracaoDAO getHistoricoItemConfiguracaoDao() throws ServiceException {
		return (HistoricoItemConfiguracaoDAO) getHistoricoItemDao();
	}

	public AuditoriaItemConfigDao getAuditoriaItemConfigDao() throws ServiceException {
		return (AuditoriaItemConfigDao) getAuditoriaICDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}

	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	public Collection list(String ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	public ItemConfiguracaoDTO createItemConfiguracao(ItemConfiguracaoDTO itemConfiguracao, UsuarioDTO user) throws Exception {
		ItemConfiguracaoDTO itemConfiguracaoBean = (ItemConfiguracaoDTO) itemConfiguracao;
		HistoricoItemConfiguracaoDTO historioIcDto = null;

		CaracteristicaTipoItemConfiguracaoDAO caracteristicaTipoItemConfiguracaoDao = new CaracteristicaTipoItemConfiguracaoDAO();
		CaracteristicaDao caracteristicaDao = new CaracteristicaDao();
		ValorDao valorDao = new ValorDao();
		TipoItemConfiguracaoDAO tipoItemConfiguracaoDao = new TipoItemConfiguracaoDAO();
		HistoricoItemConfiguracaoDAO histItemDAO = new HistoricoItemConfiguracaoDAO();
		ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
		AuditoriaItemConfigDao auditoriaItemConfigDao = new AuditoriaItemConfigDao();
		SoftwaresListaNegraDao softwaresListaNegraDao = new SoftwaresListaNegraDao();
		SoftwaresListaNegraEncontradosDao softwaresListaNegraEncontradosDao = new SoftwaresListaNegraEncontradosDao();

		Collection<SoftwaresListaNegraDTO> colsoftwareslistanegra = softwaresListaNegraDao.recuperaCollectionSoftwaresListaNegra();
		List<SoftwaresListaNegraEncontradosDTO> colsoftwareslistanegraencontrados = new ArrayList<SoftwaresListaNegraEncontradosDTO>();

		String softwareslistanegra = softwaresListaNegraDao.recuperaStringSoftwaresListaNegra();
		Pattern p = Pattern.compile(softwareslistanegra.toLowerCase());

		NotificacaoListaNegraEncontradosDTO notificacaoListaNegraEncontradosDTO = new NotificacaoListaNegraEncontradosDTO();
		List<NotificacaoListaNegraEncontradosDTO> colListaNegraEncontrados = new ArrayList<NotificacaoListaNegraEncontradosDTO>();

		/*
		 * Collection<SoftwaresListaNegraDTO> colsoftSoftwaresListaNegra = softwaresListaNegraDao.recuperaSoftwaresListaNegra();
		 * 
		 * for(SoftwaresListaNegraDTO dados : colsoftSoftwaresListaNegra){ //atribui ao Hashmap listaNegra os softwares lista negra listaNegra.put(dados.getIdSoftwaresListaNegra(),
		 * dados.getNomeSoftwaresListaNegra().toLowerCase()); }
		 */

		/*
		 * Inserido por Emauri - 23/11/2013
		 */		
		TransactionControler transactionControler = null;
		if (CITCorporeUtil.JDBC_ALIAS_INVENTORY != null && !CITCorporeUtil.JDBC_ALIAS_INVENTORY.trim().equalsIgnoreCase("")){
			transactionControler = new TransactionControlerImpl(CITCorporeUtil.JDBC_ALIAS_INVENTORY);
		}else{
			transactionControler = new TransactionControlerImpl(itemConfiguracaoDao.getAliasDB());
		}
		/*
		 * Fim - Inserido por Emauri - 23/11/2013
		 */
		try {
			validaCreate(itemConfiguracaoBean);

			caracteristicaTipoItemConfiguracaoDao.setTransactionControler(transactionControler);
			caracteristicaDao.setTransactionControler(transactionControler);
			valorDao.setTransactionControler(transactionControler);
			tipoItemConfiguracaoDao.setTransactionControler(transactionControler);
			histItemDAO.setTransactionControler(transactionControler);
			itemConfiguracaoDao.setTransactionControler(transactionControler);
			softwaresListaNegraDao.setTransactionControler(transactionControler);
			auditoriaItemConfigDao.setTransactionControler(transactionControler);
			transactionControler.start();

			itemConfiguracaoBean.setDataInicio(UtilDatas.getDataAtual());
			itemConfiguracaoBean.setDtUltimaCaptura(UtilDatas.getDataHoraAtual());

			List<TipoItemConfiguracaoDTO> listTipo = itemConfiguracaoBean.getTipoItemConfiguracao();

			List<ItemConfiguracaoDTO> lstItemCongConfiguracaoDTOs = (List) itemConfiguracaoDao.findByIdItemConfiguracaoPai(itemConfiguracaoBean);

			List condicao = new ArrayList();
			List ordenacao = new ArrayList();
			condicao.add(new Condition("identificacao", "like", itemConfiguracaoBean.getIdentificacaoPadrao()));
			condicao.add(new Condition("idItemConfiguracaoPai", "IS", null));

			ordenacao.add(new Order("idItemConfiguracaoPai"));
			List<ItemConfiguracaoDTO> lstItemConfiguracaoFilhos = null;
			List<ItemConfiguracaoDTO> lstItemCongConfiguracaoPai = (List) itemConfiguracaoDao.findByCondition(condicao, ordenacao);

			boolean verificaGravacaoIc = false;
			ItemConfiguracaoDTO itemConfiguracaoGravado = new ItemConfiguracaoDTO();
			// Caso Exista Item Configuracao Vinculado ao Pai será setado data
			// fim para todos e Cadastrado todos novamente.
			if (lstItemCongConfiguracaoDTOs == null || lstItemCongConfiguracaoDTOs.size() <= 0) {
				if (lstItemCongConfiguracaoPai != null && lstItemCongConfiguracaoPai.size() > 0) {
					Timestamp dataHoraDesitalacao = UtilDatas.getDataHoraAtual();
					itemConfiguracaoGravado = lstItemCongConfiguracaoPai.get(0);
					itemConfiguracaoDao.updateItemConfiguracaoFilho(itemConfiguracaoGravado.getIdItemConfiguracao(), UtilDatas.getDataAtual(), dataHoraDesitalacao);

					lstItemConfiguracaoFilhos = (List) itemConfiguracaoDao.listItemConfiguracaoFilho(itemConfiguracaoGravado);

					if (lstItemConfiguracaoFilhos != null && lstItemConfiguracaoFilhos.size() > 0) {
						verificaGravacaoIc = true;
					}

					itemConfiguracaoGravado.setDatahoradesinstalacao(dataHoraDesitalacao);
					if (itemConfiguracaoBean.getIdItemConfiguracao() != null) {
						itemConfiguracaoBean = (ItemConfiguracaoDTO) this.getItemConfiguracaoDao().restore(itemConfiguracaoBean);
						histItemDAO.create(this.createHistoricoItem(itemConfiguracaoGravado, user));
					}
				} else {
					itemConfiguracaoGravado = (ItemConfiguracaoDTO) itemConfiguracaoDao.create(itemConfiguracaoBean);
					// if(itemConfiguracaoBean.getIdItemConfiguracao()!=null) {
					// itemConfiguracaoBean = (ItemConfiguracaoDTO) this.getItemConfiguracaoDao().restore(itemConfiguracaoBean);
					// histItemDAO.create(this.createHistoricoItem(itemConfiguracaoBean, user));
					// }
				}
			} else {

				Timestamp dataHoraDesitalacao = UtilDatas.getDataHoraAtual();
				itemConfiguracaoGravado = lstItemCongConfiguracaoDTOs.get(0);
				itemConfiguracaoGravado.setDtUltimaCaptura(UtilDatas.getDataHoraAtual());
				itemConfiguracaoDao.updateNotNull(itemConfiguracaoGravado);
				itemConfiguracaoDao.updateItemConfiguracaoFilho(itemConfiguracaoGravado.getIdItemConfiguracao(), UtilDatas.getDataAtual(), dataHoraDesitalacao);

				lstItemConfiguracaoFilhos = (List) itemConfiguracaoDao.listItemConfiguracaoFilho(itemConfiguracaoGravado);
				if (lstItemConfiguracaoFilhos != null && lstItemConfiguracaoFilhos.size() > 0) {
					verificaGravacaoIc = true;
				}

				if (itemConfiguracaoGravado.getIdItemConfiguracao() != null) {
					itemConfiguracaoBean = (ItemConfiguracaoDTO) this.getItemConfiguracaoDao().restore(itemConfiguracaoGravado);
					historioIcDto = (HistoricoItemConfiguracaoDTO) histItemDAO.create(this.createHistoricoItem(itemConfiguracaoGravado, user));
				}
			}

			for (TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO : listTipo) {
				try {

					// SoftwaresListaNegraDTO softwaresListaNegraDTO = (SoftwaresListaNegraDTO) document.getBean();

					// Gravar todos Tipo Item Configuracao
					TipoItemConfiguracaoDTO tipoItemConfiguracaoGravar = this.gravarTipoItemConfiguracao(tipoItemConfiguracaoDTO, tipoItemConfiguracaoDao);

					List<CaracteristicaDTO> lstCaracteristica = tipoItemConfiguracaoDTO.getCaracteristicas();

					// Gravar Item Configuracao Pai Com ID Tipo Item
					// Configuracao
					ItemConfiguracaoDTO itemCongFilhoGravar = this.gravarItemConfiguracaoFilho(itemConfiguracaoGravado, tipoItemConfiguracaoGravar, itemConfiguracaoDao, lstCaracteristica,
							transactionControler);

					historioIcDto = null;
					int i = 0;

					this.situacao = false;

					// For para Identificar Todas caracteristica do Tipo Item
					// Configuracao
					for (CaracteristicaDTO caracteristicaBean : lstCaracteristica) {
						try {
							// Grava Caracteristica Tipo Item Configuracao
							CaracteristicaDTO caracteristicaGravar = this
									.gravarCaracteristica(caracteristicaBean, tipoItemConfiguracaoGravar, caracteristicaDao, caracteristicaTipoItemConfiguracaoDao);

							ValorDTO valorDTO = caracteristicaBean.getValor();
							ValorDTO valorGravar = this.gravarValor(valorDTO, caracteristicaGravar, itemCongFilhoGravar, itemCongFilhoGravar.getHistoricoItemConfiguracaoDTO(), verificaGravacaoIc,
									transactionControler);

							// Verifica se há Software Lista Negra Instalado
							if (colsoftwareslistanegra != null && colsoftwareslistanegra.size() > 0) {
								if (valorGravar.getValorStr() != null && !StringUtils.isBlank(valorGravar.getValorStr())) {
									if (tipoItemConfiguracaoDTO.getNome() != null && !StringUtils.isBlank(tipoItemConfiguracaoDTO.getNome())) {
										if (tipoItemConfiguracaoDTO.getNome().equalsIgnoreCase("SOFTWARES")) {
											System.out.println("Software instalado: " + valorGravar.getValorStr());

											Matcher m = p.matcher(valorGravar.getValorStr().toLowerCase());

											while (m.find()) {
												for (SoftwaresListaNegraDTO s : colsoftwareslistanegra) {
													if (s.getNomeSoftwaresListaNegra().equalsIgnoreCase(m.group())) {
														SoftwaresListaNegraEncontradosDTO softwaresListaNegraEncontradosDTO = new SoftwaresListaNegraEncontradosDTO();
														softwaresListaNegraEncontradosDTO.setIdsoftwareslistanegra(s.getIdSoftwaresListaNegra());
														softwaresListaNegraEncontradosDTO.setIditemconfiguracao(itemConfiguracaoGravado.getIdItemConfiguracao());
														softwaresListaNegraEncontradosDTO.setSoftwarelistanegraencontrado(valorGravar.getValorStr());
														softwaresListaNegraEncontradosDTO.setData(UtilDatas.getDataHoraAtual());
														softwaresListaNegraEncontradosDao.create(softwaresListaNegraEncontradosDTO);

														notificacaoListaNegraEncontradosDTO.setComputador(itemConfiguracaoGravado.getIdentificacao());
														notificacaoListaNegraEncontradosDTO.setSoftwarelistanegra(s.getNomeSoftwaresListaNegra());
														notificacaoListaNegraEncontradosDTO.setSoftwareencontrado(valorGravar.getValorStr());
														notificacaoListaNegraEncontradosDTO.setDataHora(UtilDatas.formatTimestamp(UtilDatas.getDataHoraAtual()));
														colListaNegraEncontrados.add(notificacaoListaNegraEncontradosDTO);
														notificacaoListaNegraEncontradosDTO = new NotificacaoListaNegraEncontradosDTO();

														break;
													}
												}
											}
										}
									}
								}
							}

							// // Recupera nome dos Softwares Lista Negra
							// SoftwaresListaNegraDTO softwaresListaNegraDTO = new SoftwaresListaNegraDTO();
							// softwaresListaNegraDTO = (SoftwaresListaNegraDTO) softwaresListaNegraDao.recuperaSoftwaresListaNegra(softwaresListaNegraDTO);
							//
							// // Tratamento Softwares Lista Negra
							// if(tipoItemConfiguracaoDTO.getNome().equalsIgnoreCase("SOFTWARES")) {
							//
							//
							// }

						} catch (Exception e) {
							System.out.println("PROBLEMA AO GRAVAR CARACTERISTICA!");
							continue;
						}
					}

				} catch (Exception e) {
					System.out.println("Problema ao gravar tipoItemConfiguracao ao criar itemConfiguracao: " + e);
					e.printStackTrace();
					continue;
				}
			}

			itemConfiguracaoGravado.setDataFim(UtilDatas.getDataAtual());
			List<ItemConfiguracaoDTO> listItemConfiguracaoDto = this.retornaItemConfiguracaoFinalizadoByIdItemConfiguracao(itemConfiguracaoGravado, itemConfiguracaoDao);

			// List<ItemConfiguracaoDTO> listItemConfiguracaoDto = this.retornaItemConfiguracaoFinalizadoByIdItemConfiguracao(itemConfiguracaoGravado, itemConfiguracaoDao);
			if (listItemConfiguracaoDto != null && listItemConfiguracaoDto.size() > 0) {
				for (ItemConfiguracaoDTO itemConfiguracaoDTO : listItemConfiguracaoDto) {
					auditoriaItemConfigDao.create(this.gravarAuditoriaItemConfig(itemConfiguracaoDTO, null, null, null, "Desativado"));
				}
			}

			if (colListaNegraEncontrados != null && colListaNegraEncontrados.size() > 0) {
				notificacaoListaNegraEncontradosDTO.setTabelaListaNegra(formatarColNotifListaNegraEmail(colListaNegraEncontrados));
			}

			if (notificacaoListaNegraEncontradosDTO.getTabelaListaNegra() != null && !StringUtils.isBlank(notificacaoListaNegraEncontradosDTO.getTabelaListaNegra())) {
				try {
					enviaEmailGrupo(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_SOFTWARE_LISTA_NEGRA, "").trim()),
							Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_RESPONSAVEL_SOFTWARE_LISTA_NEGRA, "").trim()),
							notificacaoListaNegraEncontradosDTO);
				} catch (NumberFormatException e) {
					System.out.println(i18n_Message("requisicaoLiberacao.emailNaoDefinido"));
				}
			}

			transactionControler.commit();
			transactionControler.close();
		} catch (Exception e) {
			System.out.println("Erro ao gravar item de configuracao: " + e.getMessage());
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		}
		return itemConfiguracaoBean;
	}

	/**
	 * Formata em string a coleção de softwares lista negra, transformando em uma tabela HTML para enviar por email
	 * 
	 * @author ronnie.lopes
	 * 
	 */
	public String formatarColNotifListaNegraEmail(List<NotificacaoListaNegraEncontradosDTO> lista) {
		StringBuilder texto = new StringBuilder();

		if (lista != null && lista.size() > 0) {

			texto.append("<table border='1' align='left' width=100%>" + "	  <tbody>" + "			<tr>" + "				<td><b>Computador</b></td>" + "         	<td><b>Software Lista Negra</b></td>"
					+ "				<td><b>Software Encontrado</b></td>" + "				<td><b>Data Hora</b></td>" + "			</tr>");
			for (NotificacaoListaNegraEncontradosDTO n : lista) {
				texto.append("	<tr>" + "				<td>" + n.getComputador() + "</td>" + "				<td>" + n.getSoftwarelistanegra() + "</td>" + "				<td>" + n.getSoftwareencontrado() + "</td>" + "				<td>"
						+ n.getDataHora() + "</td>" + "			</tr>");
			}
			texto.append("</tbody>" + "</table>");
			return texto.toString();
		} else {
			return null;
		}
	}

	/**
	 * Notifica com relatório Softwares Lista Negra todos os responsáveis de um grupo
	 * 
	 * @author ronnie.lopes
	 * @param idModeloEmail
	 *            , idGrupoDestino, notListaNegraEncontradosDTO
	 * @throws Exception
	 */
	public void enviaEmailGrupo(Integer idModeloEmail, Integer idGrupoDestino, NotificacaoListaNegraEncontradosDTO notListaNegraEncontradosDTO) throws Exception {
		MensagemEmail mensagem = null;

		if (idGrupoDestino == null) {
			return;
		}

		if (idModeloEmail == null) {
			return;
		}

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		List<String> emails = null;

		try {
			emails = (List<String>) grupoService.listarPessoasEmailPorGrupo(idGrupoDestino);

		} catch (Exception e) {
			return;
		}

		if (emails == null || emails.isEmpty()) {
			return;
		}

		String remetente = ParametroUtil.getValor(ParametroSistema.RemetenteNotificacoesSolicitacao, null, null);
		if (remetente == null)
			throw new LogicException(i18n_Message("requisicaoLiberacao.remetenteNaoDefinido"));

		Object[] emailsArray = new Object[(emails.size() / 2)];
		int j = 0;
		for (int i = 0; i < emails.size(); i++) {
			if (emails.get(i).contains("@")) {
				continue;
			} else {
				emailsArray[j] = emails.get(i);
				j++;
			}
		}

		try {
			int i = 0;
			for (String email : emails) {
				int posArroba = email.indexOf("@");
				if (posArroba > 0 && email.substring(posArroba).contains(".")) {
					try {
						if (StringUtils.isNotBlank(emailsArray[i].toString())) {
							String nomeContato = emailsArray[i].toString();
							notListaNegraEncontradosDTO.setNomeContato(nomeContato);
						}
						mensagem = new MensagemEmail(idModeloEmail, new IDto[] { notListaNegraEncontradosDTO });
						mensagem.envia(email, remetente, remetente);
						i++;
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void create(List<ItemConfiguracaoDTO> parm, UsuarioDTO user) throws Exception {

		// System.out.println("Iniciado");

		for (ItemConfiguracaoDTO itemConfiguracaoDTO : parm) {

			createItemConfiguracao(itemConfiguracaoDTO, user);

		}

		// System.out.println("Finalizado");
	}

	/**
	 * Grava Tipo Item configuracao
	 * 
	 * @param tipoItemConfiguracaoDTO
	 * @return tipoItemConfiguracaoDTO
	 * @throws Exception
	 */
	public TipoItemConfiguracaoDTO gravarTipoItemConfiguracao(TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO, TipoItemConfiguracaoDAO tipoItemConfiguracaoDao) throws Exception {

		try {

			List listTipoItemConf = tipoItemConfiguracaoDao.findByNomeTipoItemConfiguracao(tipoItemConfiguracaoDTO.getNome());

			if (listTipoItemConf == null || listTipoItemConf.size() == 0) {

				tipoItemConfiguracaoDTO.setDataInicio(UtilDatas.getDataAtual());
				tipoItemConfiguracaoDTO.setIdEmpresa(1);

				tipoItemConfiguracaoDTO = (TipoItemConfiguracaoDTO) tipoItemConfiguracaoDao.create(tipoItemConfiguracaoDTO);

			} else {

				tipoItemConfiguracaoDTO = (TipoItemConfiguracaoDTO) listTipoItemConf.get(0);

			}

			tipoItemConfiguracaoDTO.setSistema("S");

		} catch (Exception e) {

			System.out.println("Problema ao gravar tipoItemConfiguracao: " + e);
			e.printStackTrace();

		}

		return tipoItemConfiguracaoDTO;
	}

	/**
	 * Gravar Caracteristica do Tipo Item Configuracao
	 * 
	 * @param caracteristicaBean
	 * @param tipoitemconfiguracao
	 * @param caracteristicaDao
	 * @param caracteristicaTipoItemConfiguracaoDAO
	 * @return caracteristicaBean
	 * @throws Exception
	 */
	private CaracteristicaDTO gravarCaracteristica(CaracteristicaDTO caracteristicaBean, TipoItemConfiguracaoDTO tipoitemconfiguracao, CaracteristicaDao caracteristicaDao,
			CaracteristicaTipoItemConfiguracaoDAO caracteristicaTipoItemConfiguracaoDao) throws Exception {
		//semaphoro_ItemConfiguracaoServiceEjb.acquireUninterruptibly();
		try {

			List lstExisteCaract = caracteristicaDao.consultarCaracteristicas("", caracteristicaBean.getTag(), false);

			if (lstExisteCaract == null || lstExisteCaract.size() == 0) {

				caracteristicaBean.setIdEmpresa(1);
				caracteristicaBean.setTipo("A");
				caracteristicaBean.setDataInicio(UtilDatas.getDataAtual());
				caracteristicaBean.setSistema("S");
				// caracteristicaBean.setSistema((char) 1);
				caracteristicaBean = (CaracteristicaDTO) caracteristicaDao.create(caracteristicaBean);

			} else {

				caracteristicaBean = (CaracteristicaDTO) lstExisteCaract.get(0);
			}

			List lstExisteCaractTipoItem = caracteristicaDao.consultarCaracteristicas(tipoitemconfiguracao.getTag(), caracteristicaBean.getTag(), false);

			if (lstExisteCaractTipoItem == null || lstExisteCaractTipoItem.size() == 0) {

				CaracteristicaTipoItemConfiguracaoDTO caracteristicaTipoItemConfiguracaoBean = this.gravarCaracteristicaTipoItemConfiguracaoDTO(tipoitemconfiguracao, caracteristicaBean,
						caracteristicaTipoItemConfiguracaoDao);

			}

		} catch (Exception e) {

			System.out.println("PROBLEMA AO GRAVAR CARACTERISTICA!");

		}
		//semaphoro_ItemConfiguracaoServiceEjb.release();

		return caracteristicaBean;
	}

	/**
	 * Grava Valor Relacionado a caracteristica de Item configuracao do tipo item Configuracao
	 * 
	 * @param valorDTO
	 * @param valorDao
	 * @param caracteristicaBean
	 * @param itemConfiguracaoDTO
	 * @return valorDTO
	 * @throws Exception
	 */
	private ValorDTO gravarValor(ValorDTO valorDTO, CaracteristicaDTO caracteristicaBean, ItemConfiguracaoDTO itemConfiguracaoDTO, HistoricoItemConfiguracaoDTO historicoIc,
			boolean verificaGravacaoIc, TransactionControler tc) throws Exception {
		//semaphoro_ItemConfiguracaoServiceEjb.acquireUninterruptibly();
		try {

			ValorDao valorDao = new ValorDao();
			HistoricoValorDAO historicoValorDAO = new HistoricoValorDAO();
			AuditoriaItemConfigDao auditoriaItemConfigDao = new AuditoriaItemConfigDao();
			valorDao.setTransactionControler(tc);
			historicoValorDAO.setTransactionControler(tc);
			auditoriaItemConfigDao.setTransactionControler(tc);

			HistoricoValorDTO historico = null;

			ValorDTO valorBean = valorDao.restore(true, itemConfiguracaoDTO.getIdItemConfiguracao(), caracteristicaBean.getIdCaracteristica());
			// Verificar se já existe valor para item configuracao
			// caracteristica
			if (valorBean == null) {
				valorDTO.setIdItemConfiguracao(itemConfiguracaoDTO.getIdItemConfiguracao());
				valorDTO.setIdCaracteristica(caracteristicaBean.getIdCaracteristica());
				valorDTO = (ValorDTO) valorDao.create(valorDTO);
				if (verificaGravacaoIc) {
					// Verifica situacao para gravar log somente uma vez, como pode ser alterado somente a uma valor de uma caracteristica
					if (!this.situacao) {
						auditoriaItemConfigDao.create(this.gravarAuditoriaItemConfig(itemConfiguracaoDTO, null, null, null, "Criação"));
						this.situacao = true;
					}
				}
				return valorDTO;
			} else {
				ValorDTO valorByIdIC = valorDao.restoreValorByIdItemConfiguracao(itemConfiguracaoDTO.getIdItemConfiguracao(), caracteristicaBean.getIdCaracteristica(), valorDTO.getValorStr());
				valorBean.setValorStr(valorDTO.getValorStr());
				if (historicoIc != null && historicoIc.getIdHistoricoIC() != null){
					// Grava Historico valor item configuracao e grava auditoria
					historico = (HistoricoValorDTO) historicoValorDAO.create(this.createHistoricoValor(valorBean, null, historicoIc.getIdHistoricoIC().intValue()));
				}
				if (valorByIdIC == null) {
					if (verificaGravacaoIc) {
						if (!this.situacao) {
							auditoriaItemConfigDao.create(this.gravarAuditoriaItemConfig(itemConfiguracaoDTO, null, null, null, "Alteração"));
							this.situacao = true;
						}
					}
				}
				valorDao.update(valorBean);
			}
		} catch (Exception e) {
			System.out.println("Problema ao gravar valor: " + e.getMessage());
			e.printStackTrace();
		}
		//semaphoro_ItemConfiguracaoServiceEjb.release();
		return valorDTO;
	}

	/**
	 * Grava Item Configuracao Filho
	 * 
	 * @param itemConfiguracaoBean
	 * @param tipoItemConfiguracaoDTO
	 * @return itemConfiguracaoDTO
	 * @throws Exception
	 */
	private ItemConfiguracaoDTO gravarItemConfiguracaoFilho(ItemConfiguracaoDTO itemConfiguracaoBean, TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO, ItemConfiguracaoDao itemConfiguracaoDao,
			List<CaracteristicaDTO> listCaracteristica, TransactionControler transactionControler) throws Exception {
		ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
		HistoricoItemConfiguracaoDAO historicoItemConfiguracaoDAO = new HistoricoItemConfiguracaoDAO();
		HistoricoItemConfiguracaoDTO historioIcDto = new HistoricoItemConfiguracaoDTO();
		//semaphoro_ItemConfiguracaoServiceEjb.acquireUninterruptibly();
		try {

			itemConfiguracaoDTO.setIdItemConfiguracaoPai(itemConfiguracaoBean.getIdItemConfiguracao());
			itemConfiguracaoDTO.setIdTipoItemConfiguracao(tipoItemConfiguracaoDTO.getId());
			itemConfiguracaoDTO.setDataInicio(UtilDatas.getDataAtual());

			String identificacao = "";

			// procura qual a identificação do item configuracao filho
			int cont = 0;
			for (CaracteristicaDTO caracteristicaDTO : listCaracteristica) {

				ValorDTO valor = (ValorDTO) caracteristicaDTO.getValor();

				if (!valor.getValorStr().equalsIgnoreCase("")) {

					if (caracteristicaDTO.getTag().equalsIgnoreCase("GUID")) {

						identificacao += " - " + valor.getValorStr();

						if (cont > 1)
							break;

					} else {
						if (cont <= 1) {

							if (identificacao.length() > 0) {

								identificacao += " - " + valor.getValorStr();

							} else {

								identificacao = valor.getValorStr();

							}
						}
					}
					cont++;
				}
			}

			if (identificacao.length() > 400)
				identificacao = identificacao.substring(0, 399);

			itemConfiguracaoDTO.setIdentificacao(identificacao);

			List<ItemConfiguracaoDTO> lstItemCongConfiguracaoDTOs = (List) itemConfiguracaoDao.findByIdItemConfiguracaoPai(itemConfiguracaoDTO);

			if (lstItemCongConfiguracaoDTOs == null || lstItemCongConfiguracaoDTOs.size() <= 0) {

				itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoDao.create(itemConfiguracaoDTO);

			} else {

				itemConfiguracaoDTO = lstItemCongConfiguracaoDTOs.get(0);
				itemConfiguracaoDTO.setDataFim(null);

				itemConfiguracaoDao.updateItemConfiguracaoFilho(itemConfiguracaoDTO.getIdItemConfiguracao(), null, null);

				itemConfiguracaoDao.update(itemConfiguracaoDTO);

				if (itemConfiguracaoDTO.getIdItemConfiguracao() != null) {
					itemConfiguracaoDTO = (ItemConfiguracaoDTO) this.getItemConfiguracaoDao().restore(itemConfiguracaoDTO);
					historioIcDto = (HistoricoItemConfiguracaoDTO) historicoItemConfiguracaoDAO.create(this.createHistoricoItem(itemConfiguracaoDTO, null));
				}

				if (historioIcDto != null) {
					itemConfiguracaoDTO.setHistoricoItemConfiguracaoDTO(historioIcDto);
				}
			}

		} catch (Exception e) {

			System.out.println("PROBLEMA AO GRAVAR ITEM CONFIGURACAO FILHO!");

		}
		//semaphoro_ItemConfiguracaoServiceEjb.release();

		return itemConfiguracaoDTO;
	}

	/**
	 * Grava Vinculo Caracteristica e Tipo Item congiguracao
	 * 
	 * @param tipoItemConfiguracaoDTO
	 * @param caracteristicaBean
	 * @return caracteristicaTipoItemConfiguracaoBean
	 */
	private CaracteristicaTipoItemConfiguracaoDTO gravarCaracteristicaTipoItemConfiguracaoDTO(TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO, CaracteristicaDTO caracteristicaBean,
			CaracteristicaTipoItemConfiguracaoDAO caracteristicaTipoItemConfiguracaoDao) {

		CaracteristicaTipoItemConfiguracaoDTO caracteristicaTipoItemConfiguracaoBean = new CaracteristicaTipoItemConfiguracaoDTO();

		try {

			caracteristicaTipoItemConfiguracaoBean.setIdTipoItemConfiguracao(tipoItemConfiguracaoDTO.getId());
			caracteristicaTipoItemConfiguracaoBean.setIdCaracteristica(caracteristicaBean.getIdCaracteristica());
			caracteristicaTipoItemConfiguracaoBean.setDataInicio(UtilDatas.getDataAtual());
			caracteristicaTipoItemConfiguracaoBean.setNameInfoAgente(caracteristicaBean.getNome());

			caracteristicaTipoItemConfiguracaoDao.create(caracteristicaTipoItemConfiguracaoBean);

		} catch (Exception e) {
			System.out.println("PROBLEMA AO GRAVAR CARACTERISTICA TIPO ITEM CONFIGURACAO!");
		}

		return caracteristicaTipoItemConfiguracaoBean;
	}

	@Override
	public ItemConfiguracaoDTO restoreByIdItemConfiguracao(Integer idItemConfiguracao) throws Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idItemConfiguracao", "=", idItemConfiguracao));

		try {

			ArrayList<ItemConfiguracaoDTO> retorno = (ArrayList<ItemConfiguracaoDTO>) ((ItemConfiguracaoDao) getDao()).findByCondition(condicoes, null);

			if (retorno != null) {

				return retorno.get(0);

			}

		} catch (ServiceException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;
	}

	@Override
	public Collection findByIdTipoItemConfiguracao(Integer parm) throws Exception {
		return null;
	}

	public ItemConfiguracaoDTO createItemConfiguracaoAplicacao(ItemConfiguracaoDTO itemConfiguracao, UsuarioDTO user) throws ServiceException, LogicException {

		TransactionControler transactionControler = new TransactionControlerImpl(this.getItemConfiguracaoDao().getAliasDB());

		try {
			HistoricoItemConfiguracaoDTO ic = new HistoricoItemConfiguracaoDTO();

			this.validaCreate(itemConfiguracao);
			this.getItemConfiguracaoDao().setTransactionControler(transactionControler);
			this.getHistoricoItemConfiguracaoDao().setTransactionControler(transactionControler);
			this.getValorDao().setTransactionControler(transactionControler);
			this.getAuditoriaItemConfigDao().setTransactionControler(transactionControler);

			transactionControler.start();

			if (itemConfiguracao != null && itemConfiguracao.getIdItemConfiguracaoPai() != null) {
				ItemConfiguracaoDTO itemConfiguracaoPai = new ItemConfiguracaoDTO();
				itemConfiguracaoPai.setIdItemConfiguracao(itemConfiguracao.getIdItemConfiguracaoPai());
				itemConfiguracaoPai = (ItemConfiguracaoDTO) this.restore(itemConfiguracaoPai);
			}

			if(itemConfiguracao != null){
				itemConfiguracao.setDataInicio(UtilDatas.getDataAtual());
			}
			
			if (itemConfiguracao != null && itemConfiguracao.getIdGrupoItemConfiguracao() != null) {
				if (itemConfiguracao.getIdGrupoItemConfiguracao() == 1000) {
					throw new LogicException("Você deve criar um novo Grupo e fazer a vinculação com ele.");
				}
			}
			itemConfiguracao = (ItemConfiguracaoDTO) this.getItemConfiguracaoDao().create(itemConfiguracao);
			if (itemConfiguracao.getIdItemConfiguracao() != null) {
				ic = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().create(this.createHistoricoItem(itemConfiguracao, user));
			}
			relacaoMudanca(itemConfiguracao);
			relacaoProblema(itemConfiguracao);
			relacaoIncidente(itemConfiguracao);
			relacaoLiberacao(itemConfiguracao);

			enviarEmailNotificacao(itemConfiguracao, transactionControler, "CRIA_IC");

			this.criarEAssociarValorDaCaracteristicaAoItemConfiguracao(itemConfiguracao, user, ic.getIdHistoricoIC());

			transactionControler.commit();
			transactionControler.close();

		} catch (Exception e) {

			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);

		}

		return itemConfiguracao;
	}

	/**
	 * Retorna DAO de Valor.
	 * 
	 * @return valor do atributo valorDao.
	 * @author valdoilo.damasceno
	 */
	public ValorDao getValorDao() {
		return valorDao;
	}

	/**
	 * Retorna Service de Valor.
	 * 
	 * @return ValorService
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public ValorService getValorService() throws ServiceException, Exception {
		return (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
	}

	/**
	 * Retorna Service de Valor.
	 * 
	 * @return ValorService
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public UsuarioService getUsuarioService() throws ServiceException, Exception {
		return (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
	}

	/**
	 * Cria ou Atualiza associação do Valor da Característica ao ítem de Configuração.
	 * 
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void criarEAssociarValorDaCaracteristicaAoItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDto, UsuarioDTO user, Integer idHistoricoIC) throws Exception {

		if (itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas() != null && itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas() != null
				&& !itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().isEmpty() && idHistoricoIC != null) {
			boolean situacao = false;

			for (int i = 0; i < itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().size(); i++) {

				ValorDTO valor = this.getValorDao().restoreItemConfiguracao(itemConfiguracaoDto.getIdItemConfiguracao(),
						((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getIdCaracteristica());

				if (valor != null) {

					valor.setValorStr(((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getValorString());
					valor.setValorLongo(((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getValorString());

					ValorDTO valorDto = this.getValorDao().restoreValorByIdItemConfiguracao(itemConfiguracaoDto.getIdItemConfiguracao(),
							((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getIdCaracteristica(), valor.getValorStr());
					HistoricoValorDTO historicoValorbean = this.getHistoricoValorDao().restoreValorByIdValor(valor.getIdValor());
					this.getValorDao().update(valor);
					HistoricoValorDTO historioValorDto = (HistoricoValorDTO) this.getHistoricoValorDao().create(this.createHistoricoValor(valor, user, idHistoricoIC));

					if (valorDto == null) {
						if (!situacao) {
							this.getAuditoriaItemConfigDao().create(this.gravarAuditoriaItemConfig(itemConfiguracaoDto, null, null, user, "Alteração"));
							situacao = true;
						}

					} else if (historicoValorbean == null) {
						if (!situacao) {
							this.getAuditoriaItemConfigDao().create(this.gravarAuditoriaItemConfig(itemConfiguracaoDto, null, null, user, "Alteração"));
							situacao = true;
						}
					}

				} else {
					HistoricoItemConfiguracaoDTO historioConfiguracaoDTO = new HistoricoItemConfiguracaoDTO();
					valor = new ValorDTO();
					valor.setIdItemConfiguracao(itemConfiguracaoDto.getIdItemConfiguracao());
					valor.setIdCaracteristica(((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getIdCaracteristica());
					valor.setValorStr(((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getValorString());
					valor.setValorLongo(((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getValorString());
					this.getValorDao().create(valor);

					HistoricoValorDTO historioValorDto = (HistoricoValorDTO) this.getHistoricoValorDao().create(this.createHistoricoValor(valor, user, idHistoricoIC));
					historioConfiguracaoDTO.setIdHistoricoIC(idHistoricoIC);
					itemConfiguracaoDto.setHistoricoItemConfiguracaoDTO(historioConfiguracaoDTO);
					if (!situacao) {
						this.getAuditoriaItemConfigDao().create(this.gravarAuditoriaItemConfig(itemConfiguracaoDto, null, null, user, "Criação"));
						situacao = true;
					}

				}
			}
		}
	}

	@Override
	public ItemConfiguracaoDTO listIdUsuario(String obj) throws Exception {

		try {

			return getItemConfiguracaoDao().listIdUsuario(obj);

		} catch (Exception e) {

			throw new ServiceException(e);

		}
	}

	@Override
	public void updateItemConfiguracao(IDto ItemConfiguracao, UsuarioDTO user) throws ServiceException, LogicException {

		ItemConfiguracaoDTO itemConfiguracaoDto = new ItemConfiguracaoDTO();
		TransactionControler transactionControler = new TransactionControlerImpl(this.getItemConfiguracaoDao().getAliasDB());

		HistoricoItemConfiguracaoDTO ic = new HistoricoItemConfiguracaoDTO();
		itemConfiguracaoDto = (ItemConfiguracaoDTO) ItemConfiguracao;
		try {

			this.getHistoricoItemConfiguracaoDao().setTransactionControler(transactionControler);
			this.getRequisicaoMudancaItemConfiguracaoDao().setTransactionControler(transactionControler);
			this.getProblemaItemConfiguracaoDAO().setTransactionControler(transactionControler);
			this.getIncidenteItemConfiguracaoDAO().setTransactionControler(transactionControler);
			this.getRequisicaoLiberacaoItemConfiguracaoDao().setTransactionControler(transactionControler);
			this.getValorDao().setTransactionControler(transactionControler);
			this.getHistoricoValorDao().setTransactionControler(transactionControler);
			transactionControler.start();
			/* Gravando o historico */
			if (itemConfiguracaoDto.getIdItemConfiguracao() != null) {
				ic = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().create(this.createHistoricoItem(itemConfiguracaoDto, user));
			}

			relacaoMudanca(itemConfiguracaoDto);
			relacaoProblema(itemConfiguracaoDto);
			relacaoIncidente(itemConfiguracaoDto);
			relacaoLiberacao(itemConfiguracaoDto);

			enviarEmailNotificacao(itemConfiguracaoDto, transactionControler, "ALT_IC");

			this.criarEAssociarValorDaCaracteristicaAoItemConfiguracao(itemConfiguracaoDto, user, ic.getIdHistoricoIC());
			this.getItemConfiguracaoDao().update(itemConfiguracaoDto);

			transactionControler.commit();
			transactionControler.close();

		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		}

	}

	public void relacaoMudanca(ItemConfiguracaoDTO item) throws ServiceException, Exception {
		/* Gravando o relacionamento com mudanca */
		if (item.getIdMudanca() != null) {
			RequisicaoMudancaItemConfiguracaoDTO m = new RequisicaoMudancaItemConfiguracaoDTO();
			m.setIdItemConfiguracao(item.getIdItemConfiguracao());
			m.setIdRequisicaoMudanca(item.getIdMudanca());
			if (!this.getRequisicaoMudancaItemConfiguracaoDao().verificaSeCadastrado(m))
				this.getRequisicaoMudancaItemConfiguracaoDao().create(m);
		}
	}

	public void relacaoProblema(ItemConfiguracaoDTO item) throws ServiceException, Exception {
		/* Gravando o relacionamento com problema */
		if (item.getIdProblema() != null) {
			ProblemaItemConfiguracaoDTO m = new ProblemaItemConfiguracaoDTO();
			m.setIdItemConfiguracao(item.getIdItemConfiguracao());
			m.setIdProblema(item.getIdProblema());
			if (!this.getProblemaItemConfiguracaoDAO().verificaSeCadastrado(m))
				this.getProblemaItemConfiguracaoDAO().create(m);
		}
	}

	public void relacaoIncidente(ItemConfiguracaoDTO item) throws ServiceException, Exception {
		/* Gravando o relacionamento com incidente */
		if (item.getIdIncidente() != null) {
			ItemCfgSolicitacaoServDTO m = new ItemCfgSolicitacaoServDTO();
			m.setIdItemConfiguracao(item.getIdItemConfiguracao());
			m.setIdSolicitacaoServico(item.getIdIncidente());
			m.setDataInicio(UtilDatas.getDataAtual());
			if (!this.getIncidenteItemConfiguracaoDAO().verificaSeCadastrado(m))
				this.getIncidenteItemConfiguracaoDAO().create(m);
		}
	}

	public void restaurarBaseline(ItemConfiguracaoDTO item, UsuarioDTO user) throws Exception {
		ItemConfiguracaoDTO itemConfiguracaoDto = new ItemConfiguracaoDTO();
		TransactionControler transactionControler = new TransactionControlerImpl(this.getItemConfiguracaoDao().getAliasDB());
		this.getValorDao().setTransactionControler(transactionControler);

		this.getHistoricoItemConfiguracaoDao().setTransactionControler(transactionControler);

		HistoricoItemConfiguracaoDTO ic = new HistoricoItemConfiguracaoDTO();
		itemConfiguracaoDto = (ItemConfiguracaoDTO) item;
		try {

			/* Gravando o historico */
			if (itemConfiguracaoDto.getIdItemConfiguracao() != null) {
				ic = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().create(this.createHistoricoItem(itemConfiguracaoDto, user));
			}
			transactionControler.start();
			itemConfiguracaoDto.setDataInicio(UtilDatas.getDataAtual());
			this.getItemConfiguracaoDao().update(itemConfiguracaoDto);
			this.getValorDao().deleteByIdItemConfiguracao(itemConfiguracaoDto.getIdItemConfiguracao());
			if (itemConfiguracaoDto.getValores() != null) {
				for (ValorDTO valorDto : itemConfiguracaoDto.getValores()) {
					this.getValorDao().create(valorDto);
					this.getHistoricoValorDao().create(this.createHistoricoValor(valorDto, user, ic.getIdHistoricoIC()));
				}
			}
			transactionControler.commit();
			transactionControler.close();

		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		}
	}

	/* Historico de item configuração */
	public HistoricoItemConfiguracaoDTO createHistoricoItem(ItemConfiguracaoDTO itemConfiguracao, UsuarioDTO user) throws Exception {
		HistoricoItemConfiguracaoDTO historico = new HistoricoItemConfiguracaoDTO();
		Reflexao.copyPropertyValues(itemConfiguracao, historico);

		HistoricoItemConfiguracaoDTO ultVersao = new HistoricoItemConfiguracaoDTO();
		ultVersao = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().maxIdHistorico(itemConfiguracao);
		if (ultVersao.getIdHistoricoIC() != null) {
			ultVersao = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().restore(ultVersao);
			historico.setHistoricoVersao((ultVersao.getHistoricoVersao() == null ? 1d : +new BigDecimal(ultVersao.getHistoricoVersao() + 0.1f).setScale(1, BigDecimal.ROUND_DOWN).floatValue()));
		} else {
			historico.setHistoricoVersao(1d);
		}

		historico.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());
		if (user != null) {
			if (user.getIdEmpregado() == null) {
				historico.setIdAutorAlteracao(1);
			} else {
				historico.setIdAutorAlteracao(user.getIdEmpregado());
			}

		} else {
			historico.setIdAutorAlteracao(1);
		}

		return historico;
	}

	/* Historico de valor item configuração */
	public HistoricoValorDTO createHistoricoValor(ValorDTO valor, UsuarioDTO user, Integer idHistoricoIC) throws Exception {
		HistoricoValorDTO historico = new HistoricoValorDTO();
		Reflexao.copyPropertyValues(valor, historico);
		historico.setBaseLine("");
		historico.setIdHistoricoIC(idHistoricoIC);
		historico.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

		if (user == null) {
			historico.setIdAutorAlteracao(1);
		} else {
			historico.setIdAutorAlteracao(user.getIdEmpregado());
		}

		return historico;
	}

	@Override
	public void delete(IDto itemConfiguracao) throws ServiceException, LogicException {

		ItemConfiguracaoDTO itemConfiguracaoDto = new ItemConfiguracaoDTO();

		itemConfiguracaoDto = (ItemConfiguracaoDTO) itemConfiguracao;

		try {
			if (itemConfiguracaoDto.getIdItemConfiguracaoPai() == null) {

				TransactionControler transactionControler = new TransactionControlerImpl(this.getItemConfiguracaoDao().getAliasDB());

				try {

					transactionControler.start();
					Collection<ItemConfiguracaoDTO> listItemConfiguracaoFilho = getItemConfiguracaoDao().listItemConfiguracaoFilho(itemConfiguracaoDto);

					for (ItemConfiguracaoDTO itemConfiguracaoFilho : listItemConfiguracaoFilho) {

						itemConfiguracaoFilho = (ItemConfiguracaoDTO) this.restore(itemConfiguracaoFilho);
						itemConfiguracaoFilho.setDataFim(UtilDatas.getDataAtual());

						this.getItemConfiguracaoDao().update(itemConfiguracaoFilho);
					}

					transactionControler.commit();

					transactionControler.close();

				} catch (Exception e) {

					e.printStackTrace();
					this.rollbackTransaction(transactionControler, e);

				}

				itemConfiguracaoDto.setDataFim(UtilDatas.getDataAtual());
				this.getItemConfiguracaoDao().update(itemConfiguracaoDto);

			} else {

				itemConfiguracaoDto.setDataFim(UtilDatas.getDataAtual());
				this.getItemConfiguracaoDao().update(itemConfiguracaoDto);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean validaDuplicidadeItemConfiguracao(ItemConfiguracaoDTO bean) throws Exception {
		ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();

		return itemConfiguracaoDao.validaDuplicidadeItemConfiguracao(bean);
	}

	/**
	 * Retorna o Id da Empresa.
	 * 
	 * @param request
	 * @return
	 */
	public static Integer getIdEmpresa(HttpServletRequest req) {
		UsuarioDTO usuario = WebUtil.getUsuario(req);
		return usuario.getIdEmpresa();
	}

	public Collection<ItemConfiguracaoDTO> listByGrupo(GrupoItemConfiguracaoDTO grupoICDto, String criticidade, String status) throws Exception {
		return this.getItemConfiguracaoDao().listByGrupo(grupoICDto, criticidade, status);
	}

	public Collection<ItemConfiguracaoDTO> listByGrupo(GrupoItemConfiguracaoDTO grupoICDto, ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
		return this.getItemConfiguracaoDao().listByGrupo(grupoICDto, itemConfiguracaoDTO);
	}

	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(String criticidade, String status) throws Exception {
		return this.getItemConfiguracaoDao().listItensSemGrupo(criticidade, status);
	}

	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(String criticidade, String status, String sistemaOperacional, String grupoTrabalho, String tipoMembroDominio, String usuario,
			String processador, List softwares) throws Exception {
		return this.getItemConfiguracaoDao().listItensSemGrupo(criticidade, status, sistemaOperacional, grupoTrabalho, tipoMembroDominio, usuario, processador, softwares);
	}

	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
		return this.getItemConfiguracaoDao().listItensSemGrupo(itemConfiguracaoDTO);
	}

	@Override
	public void atualizaGrupo(ItemConfiguracaoDTO itemConfiguracaoDTO, UsuarioDTO user) throws Exception {

		TransactionControler transactionControler = new TransactionControlerImpl(this.getItemConfiguracaoDao().getAliasDB());
		try {
			this.getHistoricoItemConfiguracaoDao().setTransactionControler(transactionControler);

			transactionControler.start();
			this.getItemConfiguracaoDao().atualizaGrupo(itemConfiguracaoDTO);
			Collection<ItemConfiguracaoDTO> listItemConfiguracaoFilho = getItemConfiguracaoDao().listItemConfiguracaoFilho(itemConfiguracaoDTO);

			for (ItemConfiguracaoDTO itemConfiguracaoFilho : listItemConfiguracaoFilho) {

				itemConfiguracaoFilho = (ItemConfiguracaoDTO) this.restore(itemConfiguracaoFilho);
				itemConfiguracaoFilho.setIdGrupoItemConfiguracao(itemConfiguracaoDTO.getIdGrupoItemConfiguracao());
				this.getItemConfiguracaoDao().atualizaGrupo(itemConfiguracaoFilho);

			}
			itemConfiguracaoDTO = (ItemConfiguracaoDTO) this.restore(itemConfiguracaoDTO);

			enviarEmailNotificacao(itemConfiguracaoDTO, transactionControler, "ALT_IC_GRUPO");

			transactionControler.commit();
			transactionControler.close();

		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		}

	}

	@Override
	public Collection<ItemConfiguracaoDTO> listByEvento(Integer idEvento) throws Exception {
		try {
			ItemConfiguracaoDao dao = (ItemConfiguracaoDao) getDao();
			return dao.listByEvento(idEvento);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public boolean VerificaSeCadastrado(ItemConfiguracaoDTO itemDTO) throws Exception {
		return this.getItemConfiguracaoDao().VerificaSeCadastrado(itemDTO);
	}

	public void updateNotNull(IDto dto) {
		try {
			validaUpdate(dto);
			((ItemConfiguracaoDao) getDao()).updateNotNull(dto);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean verificaItemCriticos(Integer idItemConfiguracao) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		ProblemaDAO problemaDao = new ProblemaDAO();
		SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();

		List<ItemConfiguracaoDTO> listItemConfiguracao = new ArrayList<ItemConfiguracaoDTO>();

		List<RequisicaoMudancaDTO> listMudanca = requisicaoMudancaDao.listMudancaByIdItemConfiguracao(idItemConfiguracao);
		List<ProblemaDTO> listProblema = problemaDao.listProblemaByIdItemConfiguracao(idItemConfiguracao);
		List<SolicitacaoServicoDTO> listSolicitacao = solicitacaoServicoDao.listSolicitacaoServicoByItemConfiguracao(idItemConfiguracao);

		if (listMudanca != null && listMudanca.size() > 0) {
			return true;
		}

		if (listSolicitacao != null && listSolicitacao.size() > 0) {
			return true;
		}

		if (listProblema != null && listProblema.size() > 0) {
			return true;
		}

		return false;
	}

	public Collection<ItemConfiguracaoDTO> listByIdItemConfiguracaoPai(Integer idItemPai) throws Exception {
		try {
			ItemConfiguracaoDao dao = (ItemConfiguracaoDao) getDao();
			return dao.findByIdItemConfiguracaoPai(idItemPai);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<ItemConfiguracaoDTO> pesquisaDataExpiracao(Date data) throws Exception {
		ItemConfiguracaoDao dao = new ItemConfiguracaoDao();
		return dao.pesquisaDataExpiracao(data);
	}

	public boolean verificaMidiaSoftware(Integer idMidiaSoftware) throws Exception {
		return this.getItemConfiguracaoDao().verificaMidiaSoftware(idMidiaSoftware);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdMudanca(Integer idMudanca) throws Exception {
		return this.getItemConfiguracaoDao().listItemConfiguracaoByIdMudanca(idMudanca);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdProblema(Integer problema) throws Exception {
		return this.getItemConfiguracaoDao().listItemConfiguracaoByIdProblema(problema);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdIncidente(Integer idIncidente) throws Exception {
		return this.getItemConfiguracaoDao().listItemConfiguracaoByIdIncidente(idIncidente);
	}

	public void enviarEmailNotificacao(ItemConfiguracaoDTO itemConfiguracaoDTO, TransactionControler transactionControler, String notificacao) throws Exception {

		EmpregadoDao empregadoDao = new EmpregadoDao();
		GrupoItemConfiguracaoDAO grupoItemConfiguracaoDAO = new GrupoItemConfiguracaoDAO();
		EmpregadoDTO emp = new EmpregadoDTO();
		GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = new GrupoItemConfiguracaoDTO();

		if (transactionControler != null) {
			empregadoDao.setTransactionControler(transactionControler);
		}

		String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
		String ID_MODELO_EMAIL_AVISAR_CRIACAO_IC = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_CRIACAO_IC, "16");
		String ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_ALTERACAO_IC, "14");
		String ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC_GRUPO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_ALTERACAO_IC_GRUPO, "15");
		String ID_MODELO_EMAIL = "";

		if (ID_MODELO_EMAIL_AVISAR_CRIACAO_IC == null || ID_MODELO_EMAIL_AVISAR_CRIACAO_IC.isEmpty()) {
			ID_MODELO_EMAIL_AVISAR_CRIACAO_IC = "16";
		}

		if (ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC == null || ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC.isEmpty()) {
			ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC = "14";
		}

		if (ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC_GRUPO == null || ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC_GRUPO.isEmpty()) {
			ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC_GRUPO = "15";
		}

		if (notificacao.equals("CRIA_IC")) {
			ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_CRIACAO_IC;
		} else if (notificacao.equals("ALT_IC")) {
			ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC;
		} else if (notificacao.equals("ALT_IC_GRUPO")) {
			ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC_GRUPO;
		}

		if (!ID_MODELO_EMAIL.isEmpty()) {

			String PADRAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ENVIO_PADRAO_EMAIL_IC, "1");
			if (PADRAO == null || PADRAO.isEmpty()) {
				PADRAO = "1";
			}
			if (PADRAO.equals("1")) {
				// Enviando email para o grupo do IC
				if (itemConfiguracaoDTO.getIdGrupoItemConfiguracao() != null) {
					grupoItemConfiguracaoDTO.setIdGrupoItemConfiguracao(itemConfiguracaoDTO.getIdGrupoItemConfiguracao());
					if (grupoItemConfiguracaoDAO.verificaSeExiste(grupoItemConfiguracaoDTO)) {
						grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) grupoItemConfiguracaoDAO.restore(grupoItemConfiguracaoDTO);
						itemConfiguracaoDTO.setNomeGrupoItemConfiguracao(grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao());
					} else
						grupoItemConfiguracaoDTO = null;
				}
				if (grupoItemConfiguracaoDTO != null) {
					MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL.trim()), new IDto[] { itemConfiguracaoDTO });
					if (grupoItemConfiguracaoDTO.getEmailGrupoItemConfiguracao() != null) {
						mensagem.envia(grupoItemConfiguracaoDTO.getEmailGrupoItemConfiguracao(), "", remetente);
					}
				}
			} else if (PADRAO.equals("2")) {
				// Enviando email para o proprietário do IC
				if (itemConfiguracaoDTO.getIdProprietario() != null) {
					emp.setIdEmpregado(itemConfiguracaoDTO.getIdProprietario());
					emp = (EmpregadoDTO) empregadoDao.restore(emp);
				}
				if (emp != null) {
					MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL), new IDto[] { itemConfiguracaoDTO });
					if (emp.getEmail() != null) {
						mensagem.envia(emp.getEmail(), "", remetente);
					}
				}
			} else if (PADRAO.equals("3")) {
				// Enviando email para o grupo do IC
				if (itemConfiguracaoDTO.getIdGrupoItemConfiguracao() != null) {
					grupoItemConfiguracaoDTO.setIdGrupoItemConfiguracao(itemConfiguracaoDTO.getIdGrupoItemConfiguracao());
					if (grupoItemConfiguracaoDAO.verificaSeExiste(grupoItemConfiguracaoDTO)) {
						grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) grupoItemConfiguracaoDAO.restore(grupoItemConfiguracaoDTO);
						itemConfiguracaoDTO.setNomeGrupoItemConfiguracao(grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao());
					} else
						grupoItemConfiguracaoDTO = null;
				}
				if (grupoItemConfiguracaoDTO != null) {
					MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL), new IDto[] { itemConfiguracaoDTO });
					if (grupoItemConfiguracaoDTO.getEmailGrupoItemConfiguracao() != null) {
						mensagem.envia(grupoItemConfiguracaoDTO.getEmailGrupoItemConfiguracao(), "", remetente);
					}
				}

				// Enviando email para o proprietário do IC
				if (itemConfiguracaoDTO.getIdProprietario() != null) {
					emp.setIdEmpregado(itemConfiguracaoDTO.getIdProprietario());
					emp = (EmpregadoDTO) empregadoDao.restore(emp);
				}
				if (emp != null) {
					MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL), new IDto[] { itemConfiguracaoDTO });
					if (emp.getEmail() != null) {
						mensagem.envia(emp.getEmail(), "", remetente);
					}
				}
			}
		}

	}

	@Override
	public Collection<ItemConfiguracaoDTO> listaItemConfiguracaoPorBaseConhecimento(ItemConfiguracaoDTO itemConfiguracao) throws Exception {
		return this.getItemConfiguracaoDao().listaItemConfiguracaoPorBaseConhecimento(itemConfiguracao);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> quantidadeItemConfiguracaoPorBaseConhecimento(ItemConfiguracaoDTO itemConfiguracao) throws Exception {
		return this.getItemConfiguracaoDao().quantidadeItemConfiguracaoPorBaseConhecimento(itemConfiguracao);
	}

	@Override
	public Collection findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException {
		ItemConfiguracaoDao itenConfiguracaoDao = new ItemConfiguracaoDao();

		try {
			return itenConfiguracaoDao.findByConhecimento(baseConhecimentoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Collection findByIdItemConfiguracaoPai(Integer parm) throws Exception {

		return this.getItemConfiguracaoDao().findByIdItemConfiguracaoPai(parm);

	}

	public Integer quantidadeMidiaSoftware(ItemConfiguracaoDTO itemDTO) throws Exception {
		return this.getItemConfiguracaoDao().quantidadeMidiaSoftware(itemDTO);
	}

	@Override
	public List<ItemConfiguracaoDTO> listaItemConfiguracaoOfficePak(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
		return this.getItemConfiguracaoDao().listaItemConfiguracaoOfficePak(itemConfiguracaoDTO);
	}

	@Override
	public List<ItemConfiguracaoDTO> listaItemConfiguracaoOfficePak(ItemConfiguracaoDTO itemConfiguracaoDTO, String chave) throws Exception {
		return this.getItemConfiguracaoDao().listaItemConfiguracaoOfficePak(itemConfiguracaoDTO, chave);
	}

	public void atualizaParaGrupoProducao(int idItem) throws ServiceException, Exception {

		Integer ID_CICLO_PRODUCAO_PADRAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO_PADRAO, "1003").isEmpty() ? "1003"
				: ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO_PADRAO, "1003")));
		Integer ID_CICLO_PRODUCAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO, "998").isEmpty() ? "998" : ParametroUtil
				.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO, "998")));

		ItemConfiguracaoDao dao = new ItemConfiguracaoDao();
		ItemConfiguracaoDTO itemDto = new ItemConfiguracaoDTO();
		itemDto.setIdItemConfiguracao(idItem);
		itemDto = (ItemConfiguracaoDTO) dao.restore(itemDto);

		GrupoItemConfiguracaoDAO grupoDao = new GrupoItemConfiguracaoDAO();
		GrupoItemConfiguracaoDTO grupo = new GrupoItemConfiguracaoDTO();
		if(grupo != null && itemDto != null){
			grupo.setIdGrupoItemConfiguracao(itemDto.getIdGrupoItemConfiguracao());
		}
		
		if (grupo != null && grupo.getIdGrupoItemConfiguracao() != null) {
			grupo = (GrupoItemConfiguracaoDTO) grupoDao.restore(grupo);
		}

		if (grupo != null) {
			if (grupo.getIdGrupoItemConfiguracaoPai() != null && grupo.getIdGrupoItemConfiguracaoPai().intValue() > 0)
				if (grupo.getIdGrupoItemConfiguracaoPai() == ID_CICLO_PRODUCAO || grupo.getIdGrupoItemConfiguracaoPai() == ID_CICLO_PRODUCAO_PADRAO)
					return;
		}

		if (itemDto != null) {
			if (itemDto.getIdGrupoItemConfiguracao() != ID_CICLO_PRODUCAO_PADRAO) {
				dao.atualizaIdGrupoPadrao(itemDto, ID_CICLO_PRODUCAO_PADRAO);
			}
		}
	}

	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdLiberacao(Integer idLiberacao) throws Exception {
		ItemConfiguracaoDao dao = new ItemConfiguracaoDao();
		return dao.listItemConfiguracaoByIdLiberacao(idLiberacao);
	}

	public void relacaoLiberacao(ItemConfiguracaoDTO item) throws ServiceException, Exception {
		/* Gravando o relacionamento com liberação */
		if (item.getIdLiberacao() != null) {
			RequisicaoLiberacaoItemConfiguracaoDTO liberacao = new RequisicaoLiberacaoItemConfiguracaoDTO();
			liberacao.setIdItemConfiguracao(item.getIdItemConfiguracao());
			liberacao.setIdRequisicaoLiberacao(item.getIdLiberacao());
			if (!this.getRequisicaoLiberacaoItemConfiguracaoDao().verificaSeCadastrado(liberacao))
				this.getRequisicaoLiberacaoItemConfiguracaoDao().create(liberacao);
		}
	}

	/* Historico de item configuração para ser chamado pelos modulo de liberacao, mudanca e problema para setar a origem da modificação */
	public void createHistoricoItemComOrigem(ItemConfiguracaoDTO itemConfiguracao, RequisicaoLiberacaoDTO liberacao, String origem) throws Exception {
		HistoricoItemConfiguracaoDTO historico = new HistoricoItemConfiguracaoDTO();
		UsuarioDTO user = new UsuarioDTO();
		Reflexao.copyPropertyValues(itemConfiguracao, historico);
		HistoricoItemConfiguracaoDAO dao = new HistoricoItemConfiguracaoDAO();

		user = liberacao.getUsuarioDto();

		historico.setOrigem(origem);
		historico.setIdOrigemModificacao(liberacao.getIdRequisicaoLiberacao());

		HistoricoItemConfiguracaoDTO ultVersao = new HistoricoItemConfiguracaoDTO();
		ultVersao = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().maxIdHistorico(itemConfiguracao);
		if (ultVersao.getIdHistoricoIC() != null) {
			ultVersao = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().restore(ultVersao);
			historico.setHistoricoVersao((ultVersao.getHistoricoVersao() == null ? 1d : +new BigDecimal(ultVersao.getHistoricoVersao() + 0.1f).setScale(1, BigDecimal.ROUND_DOWN).floatValue()));
		} else {
			historico.setHistoricoVersao(1d);
		}

		historico.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());
		if (user != null) {
			if (user.getIdEmpregado() == null) {
				historico.setIdAutorAlteracao(1);
			} else {
				historico.setIdAutorAlteracao(user.getIdEmpregado());
			}

		} else {
			historico.setIdAutorAlteracao(1);
		}
		dao.create(historico);
	}

	public AuditoriaItemConfigDTO gravarAuditoriaItemConfig(ItemConfiguracaoDTO itemConfiguracaoDTO, HistoricoValorDTO historicoValorDTO, ValorDTO valorDto, UsuarioDTO usr, String tipoAlteracao) {
		AuditoriaItemConfigDTO auditoriaItemConfigDTO = new AuditoriaItemConfigDTO();

		try {

			if (itemConfiguracaoDTO != null && itemConfiguracaoDTO.getIdItemConfiguracao() != null) {

				if (itemConfiguracaoDTO.getHistoricoItemConfiguracaoDTO() != null) {
					auditoriaItemConfigDTO.setIdHistoricoIC(itemConfiguracaoDTO.getHistoricoItemConfiguracaoDTO().getIdHistoricoIC());
				}

				auditoriaItemConfigDTO.setIdItemConfiguracao(itemConfiguracaoDTO.getIdItemConfiguracao());
				auditoriaItemConfigDTO.setIdItemConfiguracaoPai(itemConfiguracaoDTO.getIdItemConfiguracaoPai());
				if (auditoriaItemConfigDTO.getIdHistoricoValor() != null) {
					auditoriaItemConfigDTO.setIdHistoricoValor(auditoriaItemConfigDTO.getIdHistoricoValor());
				}
			}

			if (usr != null && usr.getIdUsuario() != null) {
				auditoriaItemConfigDTO.setIdUsuario(usr.getIdUsuario());
			}

			if (tipoAlteracao != null) {
				auditoriaItemConfigDTO.setTipoAlteracao(tipoAlteracao);
			}

			if (historicoValorDTO != null && historicoValorDTO.getIdHistoricoValor() != null) {
				auditoriaItemConfigDTO.setIdHistoricoValor(historicoValorDTO.getIdHistoricoValor());
				auditoriaItemConfigDTO.setIdHistoricoIC(historicoValorDTO.getIdHistoricoIC());
			} else if (valorDto != null && valorDto.getIdValor() != null) {
				auditoriaItemConfigDTO.setIdHistoricoValor(valorDto.getIdValor());
			}

			auditoriaItemConfigDTO.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return auditoriaItemConfigDTO;
	}

	public void finalizarItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDto, TransactionControler tc) {
		ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
		try {
			if (itemConfiguracaoDto != null && itemConfiguracaoDto.getIdItemConfiguracao() != null) {
				itemConfiguracaoDao.setTransactionControler(tc);
				itemConfiguracaoDto.setDataExpiracao(UtilDatas.getDataAtual());
				itemConfiguracaoDto.setDataFim(UtilDatas.getDataAtual());
				itemConfiguracaoDao.finalizarItemConfiguracao(itemConfiguracaoDto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<AuditoriaItemConfigDTO> historicoAlteracaoItemConfiguracaoByIdItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
		ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
		ItemConfiguracaoDTO itemConfiguracaoRetornoDTO = (ItemConfiguracaoDTO) restore(itemConfiguracaoDTO);
		AuditoriaItemConfigDao auditoriaItemConfigDao = new AuditoriaItemConfigDao();

		return auditoriaItemConfigDao.historicoAlteracaoItemConfiguracaoByIdItemConfiguracao(itemConfiguracaoDTO);

	}

	/**
	 * 
	 * necessario passa data de finalização
	 * 
	 * @param itemConfiguracaoDTO
	 * @return lista de item configuração finalizados "Desistalando"
	 * @throws Exception
	 */
	public List<ItemConfiguracaoDTO> retornaItemConfiguracaoFinalizadoByIdItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDTO, ItemConfiguracaoDao itemConfiguracaoDao) throws Exception {

		return itemConfiguracaoDao.listItemConfiguracaoFinalizadosByIdItemconfiguracao(itemConfiguracaoDTO);
	}

	public boolean atualizaStatus(Integer item, Integer status) {
		ItemConfiguracaoDao dao = new ItemConfiguracaoDao();
		return dao.atualizaStatus(item, status);
	}

	@Override
	public Collection findByIdItemConfiguracaoPai(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
		try {
			ItemConfiguracaoDao dao = (ItemConfiguracaoDao) getDao();
			return dao.findByIdItemConfiguracaoPai(itemConfiguracaoDTO);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ItemConfiguracaoDTO findByIdentificacaoItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
		try {
			ItemConfiguracaoDao dao = (ItemConfiguracaoDao) getDao();
			return dao.findByIdentificacaoItemConfiguracao(itemConfiguracaoDTO);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection<ItemConfiguracaoDTO> listByIdGrupoAndTipoItemAndIdItemPaiAtivos(Integer idGrupo, Integer idTipo, Integer idPai) throws Exception {
		try {
			ItemConfiguracaoDao dao = (ItemConfiguracaoDao) getDao();
			return dao.listByIdGrupoAndTipoItemAndIdItemPaiAtivos(idGrupo, idTipo, idPai);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection<ItemConfiguracaoDTO> listByIdItemPaiAndTagTipoItemCfg(Integer idItemConfiguracaoPai, String tagTipoCfg) throws Exception {
		return this.getItemConfiguracaoDao().listByIdItemPaiAndTagTipoItemCfg(idItemConfiguracaoPai, tagTipoCfg);
	}

	public Collection<ItemConfiguracaoDTO> listAtivos() throws Exception {
		return this.getItemConfiguracaoDao().listAtivos();
	}

	public Collection<ItemConfiguracaoDTO> listByIdentificacao(String identif) throws Exception {
		ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("identificacao", "like", identif));
		condicao.add(new Condition("idItemConfiguracaoPai", "IS", null));

		ordenacao.add(new Order("idItemConfiguracaoPai"));
		List<ItemConfiguracaoDTO> lstItemCongConfiguracaoPai = (List) itemConfiguracaoDao.findByCondition(condicao, ordenacao);

		return lstItemCongConfiguracaoPai;
	}

}
