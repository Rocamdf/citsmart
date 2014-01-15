package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.ObjetoInstanciaFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoSolicitacao;
import br.com.centralit.citcorpore.integracao.ExecucaoSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.FluxoServicoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

import com.google.gson.Gson;

@SuppressWarnings("unused")
public class ExecucaoSolicitacaoServiceEjb extends CrudServicePojoImpl implements ExecucaoSolicitacaoService {

	private static final long serialVersionUID = -2298938916934313221L;

	private static List<TarefaFluxoDTO> listTarefas;

	protected CrudDAO getDao() throws ServiceException {
		return new ExecucaoSolicitacaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario) throws Exception {
		return new ExecucaoSolicitacao().recuperaTarefas(loginUsuario);
	}

	@Override
	public TarefaFluxoDTO recuperaTarefa(String loginUsuario, Integer idTarefa) throws Exception {
		TarefaFluxoDTO result = null;
		List<TarefaFluxoDTO> lstTarefas = new ExecucaoSolicitacao().recuperaTarefas(loginUsuario, idTarefa);
		if (!lstTarefas.isEmpty()) {
			for (TarefaFluxoDTO tarefaDto : lstTarefas) {
				if (tarefaDto.getIdItemTrabalho().intValue() == idTarefa.intValue()) {
					result = tarefaDto;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public void delegaTarefa(String loginUsuario, Integer idTarefa, String usuarioDestino, String grupoDestino) throws Exception {
		if (idTarefa == null)
			return;

		TarefaFluxoDTO tarefaDto = recuperaTarefa(idTarefa);
		ExecucaoSolicitacaoDTO execucaoSolicitacaoDto = (ExecucaoSolicitacaoDTO) new ExecucaoSolicitacaoDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());

		SolicitacaoServicoDTO solicitacaoDto = new SolicitacaoServicoServiceEjb().restoreAll(execucaoSolicitacaoDto.getIdSolicitacaoServico());
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		tc.start();
		try {

			ExecucaoSolicitacao execucaoSolicitacao = getExecucaoSolicitacao(solicitacaoDto, tc);
			execucaoSolicitacao.delega(loginUsuario, solicitacaoDto, idTarefa, usuarioDestino, grupoDestino);
			tc.commit();

		} finally {
			tc.close();
		}
	}

	public TarefaFluxoDTO recuperaTarefa(Integer idTarefa) throws Exception {
		TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
		TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
		tarefaFluxoDto.setIdItemTrabalho(idTarefa);
		tarefaFluxoDto = (TarefaFluxoDTO) tarefaFluxoDao.restore(tarefaFluxoDto);
		ElementoFluxoDTO elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
		tarefaFluxoDto.setElementoFluxoDto(elementoDto);
		return tarefaFluxoDto;
	}

	public void trataCamposTarefa(Map<String, String> params, Collection<CamposObjetoNegocioDTO> colCampos, Map<String, Object> map, String principal, TransactionControler tc) throws Exception {
		if (colCampos == null)
			return;

		Gson gson = new Gson();

		ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
		objetoNegocioDao.setTransactionControler(tc);
		HashMap<Integer, ObjetoNegocioDTO> mapObjetos = new HashMap();

		for (CamposObjetoNegocioDTO campoDto : colCampos) {
			String value = params.get(campoDto.getNome());
			if (value == null)
				return;

			String nomeTabelaBD = "";

			String nomeObjeto = "";
			if (campoDto.getNomeTabelaDB() != null) {
				nomeTabelaBD = campoDto.getNomeTabelaDB();
				nomeTabelaBD = campoDto.getNomeTabelaDB().toLowerCase();
			} else if (campoDto.getIdObjetoNegocio() != null) {
				ObjetoNegocioDTO objetoNegocioDto = mapObjetos.get(campoDto.getIdObjetoNegocio());
				if (objetoNegocioDto == null) {
					objetoNegocioDto = new ObjetoNegocioDTO();
					objetoNegocioDto.setIdObjetoNegocio(campoDto.getIdObjetoNegocio());
					objetoNegocioDto = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDto);
					if (objetoNegocioDto != null)
						mapObjetos.put(campoDto.getIdObjetoNegocio(), objetoNegocioDto);
				}
				if(objetoNegocioDto != null){
					nomeTabelaBD = objetoNegocioDto.getNomeTabelaDB();
					nomeObjeto = objetoNegocioDto.getNomeObjetoNegocio().toLowerCase();
				}
			}

			ObjetoInstanciaFluxoDTO objetoInstanciaDto = new ObjetoInstanciaFluxoDTO();
			objetoInstanciaDto.setIdObjetoNegocio(campoDto.getIdObjetoNegocio());
			objetoInstanciaDto.setObjetoPrincipal(principal);
			objetoInstanciaDto.setCampoChave(campoDto.getPk());
			objetoInstanciaDto.setNomeObjeto(campoDto.getNome().toLowerCase());
			objetoInstanciaDto.setNomeTabelaBD(nomeTabelaBD);
			objetoInstanciaDto.setNomeCampoBD(campoDto.getNomeDB());
			objetoInstanciaDto.setTipoCampoBD(campoDto.getTipoDB());
			objetoInstanciaDto.setNomeClasse(String.class.getName());
			objetoInstanciaDto.setValor(gson.toJson(value));

			map.put(objetoInstanciaDto.getNomeObjeto(), objetoInstanciaDto);
		}
	}

	public Collection<GrupoVisaoCamposNegocioDTO> findCamposTarefa(Integer idTarefa) throws Exception {
		TarefaFluxoDTO tarefaDto = recuperaTarefa(idTarefa);
		if (tarefaDto == null)
			return null;

		Collection<GrupoVisaoCamposNegocioDTO> result = null;
		result = new ArrayList();
		Collection<ObjetoInstanciaFluxoDTO> colCampos = new ObjetoInstanciaFluxoDao().findByIdTarefa(idTarefa);
		if (colCampos != null) {
			Gson gson = new Gson();
			for (ObjetoInstanciaFluxoDTO campoTarefaDto : colCampos) {
				if (campoTarefaDto.getObjetoPrincipal().equalsIgnoreCase("S") && campoTarefaDto.getCampoChave().equalsIgnoreCase("S")) {
					GrupoVisaoCamposNegocioDTO grupoCampoDto = new GrupoVisaoCamposNegocioDTO();
					CamposObjetoNegocioDTO campoDto = new CamposObjetoNegocioDTO();
					campoDto.setIdObjetoNegocio(campoTarefaDto.getIdObjetoNegocio());
					campoDto.setNome(campoTarefaDto.getNomeObjeto());
					campoDto.setNomeDB(campoTarefaDto.getNomeCampoBD());
					campoDto.setPk("S");
					campoDto.setValue(gson.fromJson(campoTarefaDto.getValor(), String.class));
					campoDto.setTipoDB(campoTarefaDto.getTipoCampoBD());
					grupoCampoDto.setCamposObjetoNegocioDto(campoDto);
					result.add(grupoCampoDto);
				}
			}
		} else {
			ExecucaoSolicitacaoDTO execucaoSolicitacaoDto = new ExecucaoSolicitacaoDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
			if (execucaoSolicitacaoDto != null) {
				ObjetoNegocioDTO objetoNegocioDto = new ObjetoNegocioDao().findByNomeObjetoNegocio("SolicitacaoServico");
				if (objetoNegocioDto == null)
					return null;

				GrupoVisaoCamposNegocioDTO grupoCampoDto = new GrupoVisaoCamposNegocioDTO();
				CamposObjetoNegocioDTO campoDto = new CamposObjetoNegocioDTO();
				campoDto.setNome("IDSOLICITACAOSERVICO");
				campoDto.setNomeDB("IDSOLICITACAOSERVICO");
				campoDto.setIdObjetoNegocio(objetoNegocioDto.getIdObjetoNegocio());
				campoDto.setPk("S");
				campoDto.setValue(execucaoSolicitacaoDto.getIdSolicitacaoServico());
				grupoCampoDto.setCamposObjetoNegocioDto(campoDto);
				result.add(grupoCampoDto);
			}
		}
		return result;
	}

	public TipoFluxoDTO recuperaFluxoServico(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		Integer idServicoContrato = solicitacaoServicoDto.getIdServicoContrato();
		if (solicitacaoServicoDto.getIdServicoContrato() == null || solicitacaoServicoDto.getIdServicoContrato().intValue() <= 0) {
			SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoDTO();
			solicitacaoAuxDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			SolicitacaoServicoDao solicitacaoDao = new SolicitacaoServicoDao();
			// if(tc == null){
			// tc = new TransactionControlerImpl(getDao().getAliasDB());
			// tc.start();
			// }
			if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("sqlserver")) {
				if (tc != null) {
					solicitacaoDao.setTransactionControler(tc);
				}
			}
			solicitacaoAuxDto = (SolicitacaoServicoDTO) solicitacaoDao.restore(solicitacaoAuxDto);
			idServicoContrato = solicitacaoAuxDto.getIdServicoContrato();
		}
		TipoFluxoDTO tipoFluxoDto = null;
		TipoFluxoDao tipoDao = new TipoFluxoDao();
		if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("sqlserver")) {
			if (tc != null) {
				tipoDao.setTransactionControler(tc);
			}
		}
		FluxoServicoDTO fluxoServicoDto = new FluxoServicoDao().findPrincipalByIdServicoContrato(idServicoContrato);
		if (fluxoServicoDto != null) {
			tipoFluxoDto = new TipoFluxoDTO();
			tipoFluxoDto.setIdTipoFluxo(fluxoServicoDto.getIdTipoFluxo());
			tipoFluxoDto = (TipoFluxoDTO) tipoDao.restore(tipoFluxoDto);
		} else {
			String fluxoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NomeFluxoPadraoServicos, "SolicitacaoServico");
			if (fluxoPadrao != null)
				tipoFluxoDto = (TipoFluxoDTO) tipoDao.findByNome(fluxoPadrao);
		}
		if (tipoFluxoDto == null)
			throw new Exception("O fluxo associado ao serviço não foi parametrizado");
		return tipoFluxoDto;
	}

	public ExecucaoSolicitacao getExecucaoSolicitacao(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		int i = 0;
		TipoFluxoDTO tipoFluxoDto = recuperaFluxoServico(solicitacaoServicoDto, tc);
		if (tipoFluxoDto.getNomeClasseFluxo() != null && !tipoFluxoDto.getNomeClasseFluxo().trim().equals("")) {
			try {
				ExecucaoSolicitacao execucaoSolicitacao = (ExecucaoSolicitacao) Class.forName(tipoFluxoDto.getNomeClasseFluxo()).newInstance();
				execucaoSolicitacao.setTransacao(tc);
				execucaoSolicitacao.setObjetoNegocioDto(solicitacaoServicoDto);
				return execucaoSolicitacao;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return new ExecucaoSolicitacao(solicitacaoServicoDto, tc);
		}
	}

	public void registraSolicitacao(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		ExecucaoSolicitacao execucaoSolicitacao = getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.inicia();
	}

	public void executa(UsuarioDTO usuarioDto, TransactionControler tc, Integer idFluxo, Integer idTarefa, String acaoFluxo, HashMap<String, String> params,
			Collection<CamposObjetoNegocioDTO> colCamposTodosPrincipal, Collection<CamposObjetoNegocioDTO> colCamposTodosVinc) throws Exception {
		HashMap<String, Object> map = new HashMap();
		trataCamposTarefa(params, colCamposTodosPrincipal, map, "S", tc);
		trataCamposTarefa(params, colCamposTodosVinc, map, "N", tc);
		Integer idSolicitacao = new Integer((String) map.get("IDSOLICITACAOSERVICO"));
		SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoServiceEjb().restoreAll(idSolicitacao, tc);

		ExecucaoSolicitacao execucaoSolicitacao = getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.executa(usuarioDto.getLogin(), solicitacaoServicoDto, idTarefa, acaoFluxo, map);
	}

	public void executa(UsuarioDTO usuarioDto, Integer idTarefa, String acaoFluxo) throws Exception {
		TarefaFluxoDTO tarefaDto = recuperaTarefa(idTarefa);
		if (tarefaDto == null)
			return;

		ExecucaoSolicitacaoDTO execucaoSolicitacaoDto = (ExecucaoSolicitacaoDTO) new ExecucaoSolicitacaoDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
		if (execucaoSolicitacaoDto == null)
			return;

		SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
		solicitacaoServicoDto.setIdSolicitacaoServico(execucaoSolicitacaoDto.getIdSolicitacaoServico());
		solicitacaoServicoDto.setUsuarioDto(usuarioDto);
		TransactionControlerImpl tc = new TransactionControlerImpl(getDao().getAliasDB());
		executa(solicitacaoServicoDto, idTarefa, acaoFluxo, tc);
		try {
			if (tc != null) {
				tc.close();
				tc = null;
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void cancelaTarefa(String loginUsuario, Integer idTarefa, String motivo, TransactionControler tc) throws Exception {
		TarefaFluxoDTO tarefaDto = recuperaTarefa(idTarefa);
		if (tarefaDto == null)
			return;

		ExecucaoSolicitacaoDTO execucaoSolicitacaoDto = (ExecucaoSolicitacaoDTO) new ExecucaoSolicitacaoDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
		if (execucaoSolicitacaoDto == null)
			return;

		SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoServiceEjb().restoreAll(execucaoSolicitacaoDto.getIdSolicitacaoServico(), null);
		ExecucaoSolicitacao execucaoSolicitacao = getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.cancelaTarefa(loginUsuario, solicitacaoServicoDto, tarefaDto, motivo);
	}

	public void executa(SolicitacaoServicoDTO solicitacaoServicoDto, Integer idTarefa, String acaoFluxo, TransactionControler tc) throws Exception {
		String acao = Enumerados.ACAO_EXECUTAR;
		if (solicitacaoServicoDto.getAcaoFluxo() != null)
			acao = solicitacaoServicoDto.getAcaoFluxo();
		HashMap<String, Object> objetos = new HashMap();
		SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), tc);
		ExecucaoSolicitacao execucaoSolicitacao = getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.executa(solicitacaoServicoDto.getUsuarioDto().getLogin(), solicitacaoAuxDto, idTarefa, acaoFluxo, objetos);
	}

	public void direcionaAtendimento(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		if (solicitacaoServicoDto.getIdGrupoAtual() == null)
			return;

		SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), tc);

		if (solicitacaoAuxDto == null)
			throw new Exception("Problemas na recuperação da solicitação");

		if (solicitacaoAuxDto.getGrupoAtual() == null || solicitacaoAuxDto.getGrupoAtual().length() == 0)
			throw new Exception("Grupo executor não encontrado");

		getExecucaoSolicitacao(solicitacaoAuxDto, tc).direcionaAtendimento(solicitacaoServicoDto.getUsuarioDto().getLogin(), solicitacaoAuxDto, solicitacaoAuxDto.getGrupoAtual());
	}

	public void direcionaAtendimentoAutomatico(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		if (solicitacaoServicoDto.getIdGrupoAtual() == null)
			return;

		SolicitacaoServicoDTO solicitacaoAuxDto = new SolicitacaoServicoServiceEjb().restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), tc);

		if (solicitacaoAuxDto != null && solicitacaoAuxDto.getIdResponsavel() == null) {
			solicitacaoAuxDto.setIdResponsavel(1);
		}

		if (solicitacaoAuxDto == null)
			throw new Exception("Problemas na recuperação da solicitação");

		if (solicitacaoAuxDto.getIdGrupoAtual() == null || solicitacaoAuxDto.getIdGrupoAtual() == 0)
			throw new Exception("Grupo executor não encontrado");

		getExecucaoSolicitacao(solicitacaoAuxDto, tc).direcionaAtendimento("admin", solicitacaoAuxDto, solicitacaoAuxDto.getGrupoAtual());
	}

	public void encerra(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		ExecucaoSolicitacao execucaoSolicitacao = getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.encerra();
	}

	public void reabre(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		ExecucaoSolicitacao execucaoSolicitacao = getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.reabre(usuarioDto.getLogin());
	}

	public void suspende(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		ExecucaoSolicitacao execucaoSolicitacao = getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.suspende(usuarioDto.getLogin());
	}

	public void reativa(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception {
		ExecucaoSolicitacao execucaoSolicitacao = getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		execucaoSolicitacao.reativa(usuarioDto.getLogin());
	}

	public void determinaPrazoLimite(SolicitacaoServicoDTO solicitacaoServicoDto, Integer idCalendario, TransactionControler tc) throws Exception {
		ExecucaoSolicitacao execucaoSolicitacao = getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		if (execucaoSolicitacao != null) {
			execucaoSolicitacao.determinaPrazoLimite(solicitacaoServicoDto, idCalendario);
		}
	}
	
	public void determinaPrazoLimiteSolicitacaoACombinarReclassificada(SolicitacaoServicoDTO solicitacaoServicoDto, Integer idCalendario, TransactionControler tc) throws Exception {
		ExecucaoSolicitacao execucaoSolicitacao = getExecucaoSolicitacao(solicitacaoServicoDto, tc);
		if (execucaoSolicitacao != null) {
			execucaoSolicitacao.determinaPrazoLimiteSolicitacaoACombinarReclassificada(solicitacaoServicoDto, idCalendario);
		}
	}

	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(Integer pgAtual, Integer qtdAPaginacao, String login) throws Exception {
		return new ExecucaoSolicitacao().recuperaTarefas(pgAtual, qtdAPaginacao, login);
	}

	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario, TipoSolicitacaoServico[] tiposSolicitacao, String somenteEmAprovacao) throws Exception {
		return new ExecucaoSolicitacao().recuperaTarefas(loginUsuario, tiposSolicitacao, somenteEmAprovacao);
	}

	@Override
	public Integer totalPaginas(Integer itensPorPagina, String loginUsuario) throws Exception {
		return new ExecucaoSolicitacao().totalPaginas(itensPorPagina, loginUsuario);
	}

	public List<TarefaFluxoDTO> getListTarefas() {
		return listTarefas;
	}

	@Override
	public Integer obterTotalDePaginas(Integer itensPorPagina, String loginUsuario, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception {
		Integer total = 0;

		SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();

		// ESSA LISTA DE TAREFAS JÁ ESTÁ VINDO COM A SOLICITACAOSERVICODTO E NÃO DEVERIA VIR. CRIAR MÉTODO PARA TRAZER APENAS AS TAREFAS COM O IDINSTANCIA, QUE É A ÚNICA INFORMAÇÃO UTILIZADA NA
		// CONSULTA ABAIXO.
//		List<TarefaFluxoDTO> listTarefasComSolicitacaoServico = recuperaTarefas(loginUsuario);
//
//		listTarefas = listTarefasComSolicitacaoServico;
		//Comentado para centalizar o método abaixo

		if (listTarefas != null) {
			total = solicitacaoServicoDao.totalDePaginas(itensPorPagina, this.getListTarefas(), gerenciamentoBean, listContratoUsuarioLogado);
		}
		
		return total;
	}
	
	/**
	 * @param login
	 * @throws Exception
	 * @author mario.haysaki
	 * Centraliza a lista de tarefas Dentro da variável estatica
	 */
	public void atualizaListaTarefas (String login) throws Exception{
		List<TarefaFluxoDTO> listTarefasComSolicitacaoServico = recuperaTarefas(login);

		listTarefas = listTarefasComSolicitacaoServico;
	}

	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(Integer pgAtual, Integer qtdAPaginacao, String login, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado)
			throws Exception {

		List<TarefaFluxoDTO> listTarefaFluxo = new ExecucaoSolicitacao().recuperaTarefas(pgAtual, qtdAPaginacao, login, gerenciamentoBean, listContratoUsuarioLogado, this.getListTarefas());

		listTarefas = null;

		return listTarefaFluxo;
	}

	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception {

		List<TarefaFluxoDTO> listTarefaFluxo = new ExecucaoSolicitacao().recuperaTarefas(loginUsuario, gerenciamentoBean, listContratoUsuarioLogado, this.getListTarefas());

		listTarefas = null;

		return listTarefaFluxo;
	}

}
