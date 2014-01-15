 package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.bpm.integracao.ObjetoInstanciaFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.centralit.citcorpore.bean.ExecucaoProblemaDTO;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoProblema;
import br.com.centralit.citcorpore.integracao.CategoriaProblemaDAO;
import br.com.centralit.citcorpore.integracao.ExecucaoProblemaDao;
import br.com.centralit.citcorpore.integracao.FluxoServicoDao;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

import com.google.gson.Gson;

@SuppressWarnings({ "unchecked", "rawtypes","unused" })
public class ExecucaoProblemaServiceEjb extends CrudServicePojoImpl implements ExecucaoProblemaService {

	private static final long serialVersionUID = -2298938916934313221L;

	protected CrudDAO getDao() throws ServiceException {
		return new ExecucaoProblemaDao();
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
		return new ExecucaoProblema().recuperaTarefas(loginUsuario);
	}

	@Override
	public TarefaFluxoDTO recuperaTarefa(String loginUsuario, Integer idTarefa) throws Exception {
		TarefaFluxoDTO result = null;
		List<TarefaFluxoDTO> lstTarefas = recuperaTarefas(loginUsuario);
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
		ExecucaoProblemaDTO execucaoProblemaDTO = (ExecucaoProblemaDTO) new ExecucaoProblemaDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());

		ProblemaDTO problemaDto = new ProblemaServiceEjb().restoreAll(execucaoProblemaDTO.getIdProblema());
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		tc.start();
		try {

			ExecucaoProblema execucaoProblema = getExecucaoProblema(problemaDto, tc);
			execucaoProblema.delega(loginUsuario, problemaDto, idTarefa, usuarioDestino, grupoDestino);
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

	
	public void trataCamposTarefa(Map<String, String> params, Collection<CamposObjetoNegocioDTO> colCampos, Map<String, Object> map, String principal) throws Exception {
		if (colCampos == null)
			return;

		Gson gson = new Gson();

		ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
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
			ExecucaoProblemaDTO execucaoProblemaDto = new ExecucaoProblemaDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
			if (execucaoProblemaDto != null) {
				ObjetoNegocioDTO objetoNegocioDto = new ObjetoNegocioDao().findByNomeObjetoNegocio("Problema");
				if (objetoNegocioDto == null)
					return null;

				GrupoVisaoCamposNegocioDTO grupoCampoDto = new GrupoVisaoCamposNegocioDTO();
				CamposObjetoNegocioDTO campoDto = new CamposObjetoNegocioDTO();
				campoDto.setNome("IDPROBLEMA");
				campoDto.setNomeDB("IDPROBLEMA");
				campoDto.setIdObjetoNegocio(objetoNegocioDto.getIdObjetoNegocio());
				campoDto.setPk("S");
				campoDto.setValue(execucaoProblemaDto.getIdProblema());
				grupoCampoDto.setCamposObjetoNegocioDto(campoDto);
				result.add(grupoCampoDto);
			}
		}
		return result;
	}

	public TipoFluxoDTO recuperaFluxoServico(ProblemaDTO problemaDto) throws Exception {
		//Integer idServicoContrato = problemaDto.getIdServicoContrato();
		/*if (problemaDto.getIdServicoContrato() == null || problemaDto.getIdServicoContrato().intValue() <= 0) {
			ProblemaDTO problemaAuxDto = new ProblemaDTO();
			problemaAuxDto.setIdProblema(problemaDto.getIdProblema());
			problemaAuxDto = (ProblemaDTO) new ProblemaDAO().restore(problemaAuxDto);
			idServicoContrato = problemaAuxDto.getIdServicoContrato();
		}*/
		TipoFluxoDTO tipoFluxoDto = null;
		ExecucaoProblema execucaoProblema =  new ExecucaoProblema();
		CategoriaProblemaDAO categoriaProblemaDao =  new CategoriaProblemaDAO();
		if(problemaDto.getIdCategoriaProblema()!=null){
			CategoriaProblemaDTO categoriaProblemaDto = new CategoriaProblemaDTO();
			categoriaProblemaDto.setIdCategoriaProblema(problemaDto.getIdCategoriaProblema());
			categoriaProblemaDto = (CategoriaProblemaDTO) categoriaProblemaDao.restore(categoriaProblemaDto);
			
			if(categoriaProblemaDto!=null){
				
				FluxoDTO fluxoDto = new FluxoDao().findByTipoFluxo(categoriaProblemaDto.getIdTipoFluxo());
				if (fluxoDto != null) {
					tipoFluxoDto = new TipoFluxoDTO();
					tipoFluxoDto.setIdTipoFluxo(fluxoDto.getIdTipoFluxo());
					tipoFluxoDto = (TipoFluxoDTO) new TipoFluxoDao().restore(tipoFluxoDto);
				} else {
					String fluxoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NomeFluxoPadraoProblema, null);
					if (fluxoPadrao != null){
						tipoFluxoDto = (TipoFluxoDTO) new TipoFluxoDao().findByNome(fluxoPadrao);
					}
						
				}
			}
		}
		
	
		
		
		if (tipoFluxoDto == null)
			throw new Exception("O fluxo associado ao serviço não foi parametrizado");
		return tipoFluxoDto;
	}

	public ExecucaoProblema getExecucaoProblema(ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		int i = 0;
		TipoFluxoDTO tipoFluxoDto = recuperaFluxoServico(problemaDto);
		if (tipoFluxoDto.getNomeClasseFluxo() != null) {
			ExecucaoProblema execucaoProblema = (ExecucaoProblema) Class.forName(tipoFluxoDto.getNomeClasseFluxo()).newInstance();
			execucaoProblema.setTransacao(tc);
			execucaoProblema.setObjetoNegocioDto(problemaDto);
			return execucaoProblema;
		} else {
			return new ExecucaoProblema(problemaDto, tc);
		}
	}

	public void registraProblema(ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		ExecucaoProblema execucaoProblema = new ExecucaoProblema(problemaDto, tc);
		execucaoProblema.inicia();
	}

	public void executa(UsuarioDTO usuarioDto, TransactionControler tc, Integer idFluxo, Integer idTarefa, String acaoFluxo, HashMap<String, String> params, Collection<CamposObjetoNegocioDTO> colCamposTodosPrincipal,
			Collection<CamposObjetoNegocioDTO> colCamposTodosVinc) throws Exception {
		HashMap<String, Object> map = new HashMap();
		trataCamposTarefa(params, colCamposTodosPrincipal, map, "S");
		trataCamposTarefa(params, colCamposTodosVinc, map, "N");
		Integer idProblema = new Integer((String) map.get("IDPROBLEMA"));
		ProblemaDTO problemaDto = new ProblemaServiceEjb().restoreAll(idProblema, tc);

		ExecucaoProblema execucaoProblema = getExecucaoProblema(problemaDto, tc);
		execucaoProblema.executa(usuarioDto.getLogin(), problemaDto, idTarefa, acaoFluxo, map);
	}

	public void executa(UsuarioDTO usuarioDto, Integer idTarefa, String acaoFluxo) throws Exception {
		TarefaFluxoDTO tarefaDto = recuperaTarefa(idTarefa);
		if (tarefaDto == null)
			return;

		ExecucaoProblemaDTO execucaoProblemaDto = (ExecucaoProblemaDTO) new ExecucaoProblemaDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
		if (execucaoProblemaDto == null)
			return;

		ProblemaDTO problemaDto = new ProblemaDTO();
		problemaDto.setIdProblema(execucaoProblemaDto.getIdProblema());
		problemaDto.setIdProprietario(usuarioDto.getIdUsuario());
		problemaDto.setUsuarioDto(usuarioDto);
		TransactionControlerImpl tc = new TransactionControlerImpl(getDao().getAliasDB());
		executa(problemaDto, idTarefa, acaoFluxo, tc);
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

		ExecucaoProblemaDTO execucaoProblemaDto = (ExecucaoProblemaDTO) new ExecucaoProblemaDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
		if (execucaoProblemaDto == null)
			return;

		ProblemaDTO problemaDto = new ProblemaServiceEjb().restoreAll(execucaoProblemaDto.getIdProblema(), null);
		ExecucaoProblema execucaoProblema = getExecucaoProblema(problemaDto, tc);
		execucaoProblema.cancelaTarefa(loginUsuario, problemaDto, tarefaDto, motivo);
	}

	public void executa(ProblemaDTO problemaDto, Integer idTarefa, String acaoFluxo, TransactionControler tc) throws Exception {
		String acao = Enumerados.ACAO_EXECUTAR;
		if (problemaDto.getAcaoFluxo() != null)
			acao = problemaDto.getAcaoFluxo();
		HashMap<String, Object> objetos = new HashMap();
		
		ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(problemaDto.getIdProblema(), tc);
		if(problemaAuxDto.getEmailContato() == null || problemaAuxDto.getEmailContato().equalsIgnoreCase("")){
			problemaAuxDto.setEmailContato(problemaDto.getEmailContato());
			problemaAuxDto.setNomeContato(problemaDto.getNomeContato());
		}
		new ExecucaoProblema(tc).executa(problemaDto.getUsuarioDto().getLogin(), problemaAuxDto, idTarefa, acaoFluxo, objetos);
	}

	public void direcionaAtendimento(ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		if (problemaDto.getIdGrupoAtual() == null)
			return;

		ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(problemaDto.getIdProblema(), tc);

		if (problemaAuxDto == null)
			throw new Exception("Problemas na recuperação da solicitação");

		if (problemaAuxDto.getNomeGrupoAtual() == null || problemaAuxDto.getNomeGrupoAtual().length() == 0)
			throw new Exception("Grupo executor não encontrado");

		getExecucaoProblema(problemaAuxDto, tc).direcionaAtendimento(problemaDto.getUsuarioDto().getLogin(), problemaAuxDto, problemaAuxDto.getNomeGrupoAtual());
	}
	
	public void direcionaAtendimentoAutomatico(ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		if (problemaDto.getIdGrupoAtual() == null)
			return;

		ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(problemaDto.getIdProblema(), tc);

		if(problemaAuxDto != null && problemaAuxDto.getIdResponsavel() == null){
			problemaAuxDto.setIdResponsavel(problemaAuxDto.getIdProprietario());
		}
		
		if (problemaAuxDto == null)
			throw new Exception("Problemas na recuperação da solicitação");

		if (problemaAuxDto.getNomeGrupoAtual() == null || problemaAuxDto.getNomeGrupoAtual().length() == 0)
			throw new Exception("Grupo executor não encontrado");

		getExecucaoProblema(problemaAuxDto, tc).direcionaAtendimento("admin", problemaAuxDto, problemaAuxDto.getNomeGrupoAtual());
	}

	public void encerra(UsuarioDTO usuarioDto,ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		ExecucaoProblema execucaoProblema = getExecucaoProblema(problemaDto, tc);
		execucaoProblema.encerra();
	}
	
	public void encerra(ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		ExecucaoProblema execucaoProblema = getExecucaoProblema(problemaDto, tc);
		execucaoProblema.encerra();
	}

	public void reabre(UsuarioDTO usuarioDto, ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		ExecucaoProblema execucaoProblema = getExecucaoProblema(problemaDto, tc);
		execucaoProblema.reabre(usuarioDto.getLogin());
	}

	public void suspende(UsuarioDTO usuarioDto, ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		ExecucaoProblema execucaoProblema = getExecucaoProblema(problemaDto, tc);
		execucaoProblema.suspende(usuarioDto.getLogin());
	}

	public void reativa(UsuarioDTO usuarioDto, ProblemaDTO problemaDto, TransactionControler tc) throws Exception {
		ExecucaoProblema execucaoProblema = getExecucaoProblema(problemaDto, tc);
		execucaoProblema.reativa(usuarioDto.getLogin());
	}

    public void determinaPrazoLimite(ProblemaDTO problemaDto, Integer idCalendario, TransactionControler tc) throws Exception {
        ExecucaoProblema execucaoProblema = getExecucaoProblema(problemaDto, tc);
        execucaoProblema.determinaPrazoLimite(problemaDto, idCalendario);
    }
    
    public TarefaFluxoDTO recuperaTarefa(String loginUsuario, ProblemaDTO problemaDto) throws Exception {
		TarefaFluxoDTO result = null;
		ProblemaDTO problemaAux = null;
		List<TarefaFluxoDTO> lstTarefas = recuperaTarefas(loginUsuario);
		if (!lstTarefas.isEmpty()) {
			for (TarefaFluxoDTO tarefaDto : lstTarefas) {
				problemaAux = (ProblemaDTO) tarefaDto.getProblemaDto();
				if (problemaAux.getIdProblema().intValue() == problemaDto.getIdProblema().intValue()) {
					result = tarefaDto;
					break;
				}
			}
		}
		return result;
	}
    
}
