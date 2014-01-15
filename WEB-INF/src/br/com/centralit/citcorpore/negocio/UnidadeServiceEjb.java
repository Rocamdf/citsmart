package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ContratosUnidadesDTO;
import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.centralit.citcorpore.integracao.ContratosUnidadesDAO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.EnderecoDao;
import br.com.centralit.citcorpore.integracao.LocalidadeUnidadeDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.UnidadeDao;
import br.com.centralit.citcorpore.integracao.UnidadesAccServicosDao;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class UnidadeServiceEjb extends CrudServicePojoImpl implements UnidadeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	/** DAO de UnidadesAccServicosDAO. */
	private UnidadesAccServicosDao unidadesAccServicosDao = new UnidadesAccServicosDao();

	public UnidadesAccServicosDao getUnidadesAccServicosDao() {
		return unidadesAccServicosDao;
	}

	public void setUnidadesAccServicosDao(UnidadesAccServicosDao unidadesAccServicosDao) {
		this.unidadesAccServicosDao = unidadesAccServicosDao;
	}

	/** Bean de UnidadeDTO. */
	private UnidadeDTO unidadeDTO;

	public UnidadeDTO getUnidadeDTO() {
		return unidadeDTO;
	}

	public void setUnidadeDTO(UnidadeDTO unidadeDTO) {
		this.unidadeDTO = unidadeDTO;
	}

	protected CrudDAO getDao() throws ServiceException {
		return new UnidadeDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}

	public Class getBean() {
		return UnidadeDTO.class;
	}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {

		UnidadeDao unidadeDao = (UnidadeDao) getDao();
		ContratosUnidadesDAO contratosUnidadesDAO = new ContratosUnidadesDAO();
		LocalidadeUnidadeDAO localidadeUnidadeDao = new LocalidadeUnidadeDAO();
		EnderecoDao enderecoDao = new EnderecoDao();

		UnidadeDTO unidade = (UnidadeDTO) model;
		ContratosUnidadesDTO contratosUnidadesDTO = new ContratosUnidadesDTO();
		EnderecoDTO enderecoDto = new EnderecoDTO();
		unidade.setDataInicio(UtilDatas.getDataAtual());

		UnidadesAccServicosService unidadeAccService = new UnidadesAccServicosServiceEjb();

		TransactionControler transactionControler = new TransactionControlerImpl(getDao().getAliasDB());

		try {

			unidadeDao.setTransactionControler(transactionControler);
			contratosUnidadesDAO.setTransactionControler(transactionControler);
			localidadeUnidadeDao.setTransactionControler(transactionControler);
			enderecoDao.setTransactionControler(transactionControler);
			transactionControler.start();

			enderecoDto.setLogradouro(unidade.getLogradouro());
			enderecoDto.setComplemento(unidade.getComplemento());
			enderecoDto.setNumero(unidade.getNumero());
			enderecoDto.setBairro(unidade.getBairro());
			enderecoDto.setCep(unidade.getCep());
			enderecoDto.setIdCidade(unidade.getIdCidade());
			enderecoDto.setIdPais(unidade.getIdPais());
			enderecoDto.setIdUf(unidade.getIdUf());

			enderecoDto = (EnderecoDTO) enderecoDao.create(enderecoDto);

			if (enderecoDto.getIdEndereco() != null) {
				unidade.setIdEndereco(enderecoDto.getIdEndereco());
			}

			if(unidade.getAceitaEntregaProduto()==null){
				unidade.setAceitaEntregaProduto("N");
			}
			
			unidade = ((UnidadeDTO) unidadeDao.create(unidade));

			this.criarEAssociarServicoAUnidade(unidade);

			if (unidade.getIdContrato() != null) {
				for (int i = 0; i < unidade.getIdContrato().length; i++) {

					contratosUnidadesDTO.setIdUnidade(unidade.getIdUnidade());
					contratosUnidadesDTO.setIdContrato(unidade.getIdContrato()[i]);
					if (contratosUnidadesDTO.getIdContrato() != null) {
						contratosUnidadesDAO.create(contratosUnidadesDTO);
					}
				}
			}
			if (unidade.getListaDeLocalidade() != null) {
				if (unidade.getIdUnidade() != null && unidade.getIdUnidade() != 0) {
					for (LocalidadeUnidadeDTO localidadeUnidadeDto : unidade.getListaDeLocalidade()) {
						LocalidadeUnidadeDTO localidade = new LocalidadeUnidadeDTO();
						localidade.setIdUnidade(unidade.getIdUnidade());
						localidade.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
						localidade.setDataInicio(UtilDatas.getDataAtual());
						localidadeUnidadeDao.create(localidade);
					}
				}

			}

			transactionControler.commit();
			transactionControler.close();

		} catch (Exception e) {
			e.printStackTrace();
			// Retorna valores se caso algo de errado;
			this.rollbackTransaction(transactionControler, e);
		}

		return getUnidadeDTO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.citframework.service.CrudServicePojoImpl#update(br.com.citframework .dto.IDto)
	 */
	@Override
	public void update(IDto model) throws ServiceException, LogicException {

		UnidadeDao unidadeDao = (UnidadeDao) getDao();

		UnidadeDTO unidade = (UnidadeDTO) model;
		ContratosUnidadesDAO contratosUnidadesDAO = new ContratosUnidadesDAO();
		LocalidadeUnidadeDAO localidadeUnidadeDao = new LocalidadeUnidadeDAO();
		ContratosUnidadesDTO contratosUnidadesDTO = new ContratosUnidadesDTO();
		
		EnderecoDao enderecoDao = new EnderecoDao();
		
		EnderecoDTO enderecoDto = new EnderecoDTO();

		TransactionControler transactionControler = new TransactionControlerImpl(unidadeDao.getAliasDB());

		try {
			unidadeDao.setTransactionControler(transactionControler);
			contratosUnidadesDAO.setTransactionControler(transactionControler);
			localidadeUnidadeDao.setTransactionControler(transactionControler);
			enderecoDao.setTransactionControler(transactionControler);
			transactionControler.start();
			
			if(unidade.getIdEndereco()!=null){
				enderecoDto.setIdEndereco(unidade.getIdEndereco());
				enderecoDto.setLogradouro(unidade.getLogradouro());
				enderecoDto.setComplemento(unidade.getComplemento());
				enderecoDto.setNumero(unidade.getNumero());
				enderecoDto.setBairro(unidade.getBairro());
				enderecoDto.setCep(unidade.getCep());
				enderecoDto.setIdPais(unidade.getIdPais());
				enderecoDto.setIdUf(unidade.getIdUf());
				enderecoDto.setIdCidade(unidade.getIdCidade());
				enderecoDao.update(enderecoDto);
			}else{
				enderecoDto.setLogradouro(unidade.getLogradouro());
				enderecoDto.setComplemento(unidade.getComplemento());
				enderecoDto.setNumero(unidade.getNumero());
				enderecoDto.setBairro(unidade.getBairro());
				enderecoDto.setCep(unidade.getCep());
				enderecoDto.setIdPais(unidade.getIdPais());
				enderecoDto.setIdUf(unidade.getIdUf());
				enderecoDto.setIdCidade(unidade.getIdCidade());
				enderecoDto = (EnderecoDTO) enderecoDao.create(enderecoDto);
				
				if(enderecoDto.getIdEndereco()!=null){
					unidade.setIdEndereco(enderecoDto.getIdEndereco());
				}
			}
			
			
			
			
			if(unidade.getAceitaEntregaProduto()==null){
				unidade.setAceitaEntregaProduto("N");
			}
			
			String UNIDADE_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
			if (UNIDADE_VINC_CONTRATOS == null) {
				UNIDADE_VINC_CONTRATOS = "N";
			}
			if (UNIDADE_VINC_CONTRATOS.equalsIgnoreCase("S")) {
				contratosUnidadesDAO.deleteByIdUnidade(unidade.getIdUnidade());
				if (unidade.getIdContrato() != null) {
					for (int i = 0; i < unidade.getIdContrato().length; i++) {
						contratosUnidadesDTO.setIdUnidade(unidade.getIdUnidade());
						contratosUnidadesDTO.setIdContrato(unidade.getIdContrato()[i]);
						if (contratosUnidadesDTO.getIdContrato() != null) {
							contratosUnidadesDAO.create(contratosUnidadesDTO);
						}
					}
				}
			}			
 
			// Obtendo o serviço.
			LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);
			
			// Verificando se o serviço existe.
			if (localidadeUnidadeService != null) {
				
				// Excluindo todas as associacoes entre a unidade e localidades.
				localidadeUnidadeService.deleteByIdUnidade(unidade.getIdUnidade() );
				
				// Recuperando as localidades informadas pelo usuário.
				if (unidade.getListaDeLocalidade() != null) {
					
					// Percorrendo cada localidade informada.
					for (LocalidadeUnidadeDTO localidadeUnidadeDto : unidade.getListaDeLocalidade() ) {
						
						LocalidadeUnidadeDTO localidade = new LocalidadeUnidadeDTO();
						
						localidade.setIdUnidade(unidade.getIdUnidade() );
						
						localidade.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade() );
						
						localidade.setDataInicio(UtilDatas.getDataAtual() );
						
						// Criando a associacao entre a unidade e a localidade no BD.
						localidadeUnidadeDao.create(localidade);
					}
				}
			}			
			
			this.criarEAssociarServicoAUnidade(unidade);

			transactionControler.commit();
			transactionControler.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		super.update(unidade);
	}

	/**
	 * Associa serviços a unidade em questão.
	 * 
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	private void criarEAssociarServicoAUnidade(UnidadeDTO unidade) throws Exception {

		UnidadesAccServicosDTO unidadesAccServicosDTO = new UnidadesAccServicosDTO();

		if (unidade.getServicos() != null && !unidade.getServicos().isEmpty()) {
			for (int i = 0; i < unidade.getServicos().size(); i++) {
				Integer idServico = ((UnidadesAccServicosDTO) unidade.getServicos().get(i)).getIdServico();

				if (!getUnidadesAccServicosDao().existeAssociacaoComUnidade(idServico, unidade.getIdUnidade())) {
					unidadesAccServicosDTO.setIdServico(idServico);
					unidadesAccServicosDTO.setIdUnidade(unidade.getIdUnidade());

					getUnidadesAccServicosDao().create(unidadesAccServicosDTO);
				}
			}
		}
	}

	@Override
	public Collection findByIdUnidade() throws Exception {
		try {
			UnidadeDao unidadeDao = (UnidadeDao) getDao();
			return unidadeDao.findByIdUnidade();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection findByIdEmpregado() throws Exception {
		try {
			UnidadeDao unidadeDao = (UnidadeDao) getDao();
			return unidadeDao.findByIdEmpregado();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection listHierarquia() throws Exception {
		UnidadeDao dao = new UnidadeDao();
		Collection colFinal = new ArrayList();
		try {
			Collection col = dao.findSemPai();
			if (col != null) {
				for (Iterator it = col.iterator(); it.hasNext();) {
					UnidadeDTO unidadeDTO = (UnidadeDTO) it.next();
					unidadeDTO.setNivel(0);
					colFinal.add(unidadeDTO);
					Collection colAux = getCollectionHierarquia(unidadeDTO.getIdUnidade(), 0);
					if (colAux != null && colAux.size() > 0) {
						colFinal.addAll(colAux);
					}
				}
			}
			return colFinal;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection getCollectionHierarquia(Integer idUnidade, Integer nivel) throws Exception {
		UnidadeDao dao = new UnidadeDao();
		Collection col = dao.findByIdPai(idUnidade);
		Collection colFinal = new ArrayList();
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				UnidadeDTO unidadeDTO = (UnidadeDTO) it.next();
				unidadeDTO.setNivel(nivel + 1);
				colFinal.add(unidadeDTO);
				Collection colAux = getCollectionHierarquia(unidadeDTO.getIdUnidade(), unidadeDTO.getNivel());
				if (colAux != null && colAux.size() > 0) {
					colFinal.addAll(colAux);
				}
			}
		}
		return colFinal;
	}

	public boolean jaExisteUnidadeComMesmoNome(UnidadeDTO unidade) {

		String nome = unidade.getNome();
		Integer idunidade = unidade.getIdUnidade();

		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("nome", "=", nome));
		condicoes.add(new Condition("dataFim", "is", null));
		condicoes.add(new Condition("idUnidade", "<>", idunidade == null ? 0 : idunidade.intValue()));
		Collection retorno = null;

		try {
			retorno = ((UnidadeDao) getDao()).findByCondition(condicoes, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno == null ? false : true;

	}

	public void deletarUnidade(IDto model, DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception {
		UnidadeDTO unidadeDto = (UnidadeDTO) model;
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		ContratosUnidadesDAO contratosUnidadesDAO = new ContratosUnidadesDAO();
		EmpregadoDao empregadoDao = new EmpregadoDao();
		UnidadeDao unidadeDao = new UnidadeDao();
		LocalidadeUnidadeDAO localidadeUnidadeDao = new LocalidadeUnidadeDAO();
		SolicitacaoServicoDao solicitacaoDao = new SolicitacaoServicoDao();

		try {
			validaUpdate(model);
			empregadoDao.setTransactionControler(tc);
			unidadeDao.setTransactionControler(tc);
			contratosUnidadesDAO.setTransactionControler(tc);
			localidadeUnidadeDao.setTransactionControler(tc);
			solicitacaoDao.setTransactionControler(tc);
			if (empregadoDao.verificarSeUnidadePossuiEmpregado(unidadeDto.getIdUnidade()) || unidadeDao.verificarSeUnidadePossuiFilho(unidadeDto.getIdUnidade())) {
				document.alert(UtilI18N.internacionaliza(request,"citcorpore.comum.registroNaoPodeSerExcluido"));
				return;
			}
			if(solicitacaoDao.verificarExistenciaDeUnidade(unidadeDto.getIdUnidade())){
				document.alert(UtilI18N.internacionaliza(request,"citcorpore.comum.registroNaoPodeSerExcluido"));
				return;
			}
			tc.start();
			unidadeDto.setDataFim(UtilDatas.getDataAtual());
			unidadeDao.update(model);
			contratosUnidadesDAO.deleteByIdUnidade(unidadeDto.getIdUnidade());
			if (unidadeDto.getListaDeLocalidade() != null) {
				for (LocalidadeUnidadeDTO localidadeUnidadeDto : unidadeDto.getListaDeLocalidade()) {
					localidadeUnidadeDao.updateDataFim(localidadeUnidadeDto);
				}
			}
			document.alert(UtilI18N.internacionaliza(request,"MSG07"));
			tc.commit();
			tc.close();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}

	}

	@Override
	public void restaurarGridServicos(DocumentHTML document, Collection<UnidadesAccServicosDTO> servicos) {

		if (servicos != null && !servicos.isEmpty()) {
			int count = 0;
			document.executeScript("countServico = 0");

			for (UnidadesAccServicosDTO servicosBean : servicos) {
				count++;

				document.executeScript("restoreRow()");
				document.executeScript("seqSelecionada = " + count);

				String nomeServico = (servicosBean.getNomeServico() != null ? servicosBean.getNomeServico() : "");
				String descricaoServico = (servicosBean.getDetalheServico() != null ? servicosBean.getDetalheServico() : "");

				if (nomeServico != null) {
					nomeServico = nomeServico.replaceAll("'", "");
				}

				if (descricaoServico != null) {
					descricaoServico = descricaoServico.replaceAll("'", "");
				}

				document.executeScript("setRestoreServico('" + servicosBean.getIdServico() + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(nomeServico) + "'," + "'"
						+ br.com.citframework.util.WebUtil.codificaEnter(descricaoServico) + "')");
			}
			document.executeScript("exibeGrid()");
		} else {
			document.executeScript("ocultaGrid()");
		}

	}

	@Override
	public Collection findById(Integer idUnidade) throws Exception {
		try {
			UnidadeDao unidadeDao = (UnidadeDao) getDao();
			return unidadeDao.findById(idUnidade);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection listHierarquiaMultiContratos(Integer idContrato) throws Exception {
		UnidadeDao dao = new UnidadeDao();
		Collection colFinal = new ArrayList();
		try {
			Collection col = dao.findSemPaiMultContrato(idContrato);
			if (col != null) {
				for (Iterator it = col.iterator(); it.hasNext();) {
					UnidadeDTO unidadeDTO = (UnidadeDTO) it.next();
					unidadeDTO.setNivel(0);
					colFinal.add(unidadeDTO);
					Collection colAux = getCollectionHierarquia(unidadeDTO.getIdUnidade(), 0);
					if (colAux != null && colAux.size() > 0) {
						colFinal.addAll(colAux);
					}
				}
			}
			return colFinal;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
