package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.ObjetoInstanciaFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.ExecucaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoLiberacao;
import br.com.centralit.citcorpore.integracao.ExecucaoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoDao;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

import com.google.gson.Gson;


@SuppressWarnings({"unchecked","rawtypes","unused","serial"})
public class ExecucaoLiberacaoServiceEjb extends CrudServicePojoImpl implements ExecucaoLiberacaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new ExecucaoLiberacaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario) throws Exception {
		return new ExecucaoLiberacao().recuperaTarefas(loginUsuario);
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
		ExecucaoLiberacaoDTO execucaoLiberacaoDto = (ExecucaoLiberacaoDTO) new ExecucaoLiberacaoDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
		
		RequisicaoLiberacaoDTO solicitacaoDto = new RequisicaoLiberacaoServiceEjb().restoreAll(execucaoLiberacaoDto.getIdRequisicaoLiberacao());
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		tc.start();
		try{

			ExecucaoLiberacao execucaoLiberacao = new ExecucaoLiberacao(tc);
			execucaoLiberacao.delega(loginUsuario, solicitacaoDto, idTarefa, usuarioDestino, grupoDestino);
			tc.commit();
		
		}finally{
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
	
	public void trataCamposTarefa(Map<String, String> params, Collection<CamposObjetoNegocioDTO> colCampos, Map<String, Object> map, String principal) throws Exception{
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
			}else if (campoDto.getIdObjetoNegocio() != null) {
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
		}else{
			ExecucaoLiberacaoDTO execucaoLiberacaoDto = new ExecucaoLiberacaoDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
			if (execucaoLiberacaoDto != null) {
				ObjetoNegocioDTO objetoNegocioDto = new ObjetoNegocioDao().findByNomeObjetoNegocio("RequisicaoLiberacao");
				if (objetoNegocioDto == null)
					return null;
				
				GrupoVisaoCamposNegocioDTO grupoCampoDto = new GrupoVisaoCamposNegocioDTO();
				CamposObjetoNegocioDTO campoDto = new CamposObjetoNegocioDTO();
				campoDto.setNome("IDREQUISICAOLIBERACAO");
				campoDto.setNomeDB("IDLIBERACAO");
				campoDto.setIdObjetoNegocio(objetoNegocioDto.getIdObjetoNegocio());
				campoDto.setPk("S");
				campoDto.setValue(execucaoLiberacaoDto.getIdRequisicaoLiberacao());
				grupoCampoDto.setCamposObjetoNegocioDto(campoDto);
				result.add(grupoCampoDto);
			}
		}
   		return result;
	}

	public void registraLiberacao(RequisicaoLiberacaoDTO requisicaoLiberacaoDto, TransactionControler tc, Usuario usuario) throws Exception {			
		ExecucaoLiberacao execucaoLiberacao = new ExecucaoLiberacao(requisicaoLiberacaoDto, tc, usuario);
		execucaoLiberacao.inicia();
	}	

	public void executa(UsuarioDTO usuarioDto, TransactionControler tc, Integer idFluxo, Integer idTarefa, String acaoFluxo, HashMap<String, String> params, Collection<CamposObjetoNegocioDTO> colCamposTodosPrincipal, Collection<CamposObjetoNegocioDTO> colCamposTodosVinc) throws Exception{
		if(usuario == null){
			usuario = new Usuario();
		}
		usuario.setLocale(usuarioDto.getLocale());
		
		HashMap<String, Object> map = new HashMap();
		trataCamposTarefa(params, colCamposTodosPrincipal, map, "S");
		trataCamposTarefa(params, colCamposTodosVinc, map, "N");
		Integer idSolicitacao = new Integer((String) map.get("IDREQUISICAOLIBERACAO"));
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = new RequisicaoLiberacaoServiceEjb().restoreAll(idSolicitacao, tc);

		new ExecucaoLiberacao(tc).executa(usuarioDto.getLogin(), requisicaoLiberacaoDto, idTarefa, acaoFluxo, map);
	}

	public void executa(RequisicaoLiberacaoDTO requisicaoLiberacaoDto, Integer idTarefa, String acaoFluxo, TransactionControler tc) throws Exception {
		String acao = Enumerados.ACAO_EXECUTAR;
		if (requisicaoLiberacaoDto.getAcaoFluxo() != null)
			acao = requisicaoLiberacaoDto.getAcaoFluxo();
		HashMap<String, Object> objetos = new HashMap();
		RequisicaoLiberacaoDTO liberacaoAuxDto = new RequisicaoLiberacaoServiceEjb().restoreAll(requisicaoLiberacaoDto.getIdRequisicaoLiberacao(), tc);
		new ExecucaoLiberacao(tc).executa(requisicaoLiberacaoDto.getUsuarioDto().getLogin(), liberacaoAuxDto, idTarefa, acaoFluxo, objetos);
	}

	public void direcionaAtendimento( RequisicaoLiberacaoDTO requisicaoLiberacaoDto, TransactionControler tc) throws Exception {
		if (requisicaoLiberacaoDto.getIdGrupoAtual() == null) 
			return;
			
		RequisicaoLiberacaoDTO liberacaoAuxDto = new RequisicaoLiberacaoServiceEjb().restoreAll(requisicaoLiberacaoDto.getIdRequisicaoLiberacao(), tc);
		
		if (liberacaoAuxDto == null)
			throw new Exception("Problemas na recuperação da solicitação");

		if (liberacaoAuxDto.getNomeGrupoAtual() == null || liberacaoAuxDto.getNomeGrupoAtual().length() == 0)
			throw new Exception("Grupo executor não encontrado");
		
		new ExecucaoLiberacao(liberacaoAuxDto,tc).direcionaAtendimento(requisicaoLiberacaoDto.getUsuarioDto().getLogin(), liberacaoAuxDto, liberacaoAuxDto.getNomeGrupoAtual());
	}
	
	public void direcionaAtendimentoSolicitante( RequisicaoLiberacaoDTO requisicaoLiberacaoDto, TransactionControler tc) throws Exception {
		if (requisicaoLiberacaoDto.getIdGrupoAprovador() == null) {
			return;
		}else{
			requisicaoLiberacaoDto.setIdGrupoAtual(requisicaoLiberacaoDto.getIdGrupoAprovador());
			RequisicaoLiberacaoDao requisicaoLiberacaoDao = new RequisicaoLiberacaoDao();
			requisicaoLiberacaoDao.setTransactionControler(tc);
			requisicaoLiberacaoDao.updateNotNull(requisicaoLiberacaoDto);
		}
		RequisicaoLiberacaoDTO liberacaoAuxDto = new RequisicaoLiberacaoServiceEjb().restoreAll(requisicaoLiberacaoDto.getIdRequisicaoLiberacao(), tc);
		
		if (liberacaoAuxDto == null)
			throw new Exception("Problemas na recuperação da solicitação");

		if (liberacaoAuxDto.getNomeGrupoAprovador() == null || liberacaoAuxDto.getNomeGrupoAprovador().length() == 0)
			throw new Exception("Grupo Aprovador não encontrado");
		
		new ExecucaoLiberacao(liberacaoAuxDto,tc).direcionaAtendimento(requisicaoLiberacaoDto.getUsuarioDto().getLogin(), liberacaoAuxDto, liberacaoAuxDto.getNomeGrupoAprovador());
	}
	
	public void executa(UsuarioDTO usuarioDto, Integer idTarefa, String acaoFluxo) throws Exception {
		if(usuario == null){
			usuario = new Usuario();
		}
		usuario.setLocale(usuarioDto.getLocale());
		
		TarefaFluxoDTO tarefaDto = recuperaTarefa(idTarefa);
		if (tarefaDto == null)
			return;

		ExecucaoLiberacaoDTO execucaoLiberacaoDto = (ExecucaoLiberacaoDTO) new ExecucaoLiberacaoDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
		if (execucaoLiberacaoDto == null)
			return;

		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = new RequisicaoLiberacaoDTO();
		requisicaoLiberacaoDto.setIdRequisicaoLiberacao(execucaoLiberacaoDto.getIdRequisicaoLiberacao());
		requisicaoLiberacaoDto.setUsuarioDto(usuarioDto);
		TransactionControlerImpl tc = new TransactionControlerImpl(getDao().getAliasDB());
		executa(requisicaoLiberacaoDto, idTarefa, acaoFluxo, tc);
		try {
			if (tc != null) {
				tc.close();
				tc = null;
			}
		} catch (Exception e) {
		}
	}

	public void suspende(UsuarioDTO usuarioDto, RequisicaoLiberacaoDTO requisicaoLiberacaoDto, TransactionControler tc) throws Exception {
		if(usuario == null){
			usuario = new Usuario();
		}
		usuario.setLocale(usuarioDto.getLocale());
		
		ExecucaoLiberacao execucaoLiberacao = new ExecucaoLiberacao(requisicaoLiberacaoDto, tc, usuario);
		execucaoLiberacao.suspende(usuarioDto.getLogin());
	}
	
	public void encerra(UsuarioDTO usuarioDto, RequisicaoLiberacaoDTO requisicaoLiberacaoDto, TransactionControler tc) throws Exception {
		if(usuario == null){
			usuario = new Usuario();
		}
		usuario.setLocale(usuarioDto.getLocale());
		ExecucaoLiberacao execucaoLiberacao = new ExecucaoLiberacao(requisicaoLiberacaoDto, tc);
		execucaoLiberacao.encerra();
	}
	
	public void reativa(UsuarioDTO usuarioDto, RequisicaoLiberacaoDTO requisicaoLiberacaoDto, TransactionControler tc) throws Exception {
		if(usuario == null){
			usuario = new Usuario();
		}
		usuario.setLocale(usuarioDto.getLocale());
		ExecucaoLiberacao execucaoLiberacao = new ExecucaoLiberacao(requisicaoLiberacaoDto, tc);
		execucaoLiberacao.reativa(usuarioDto.getLogin());
	}
	public void reabre(UsuarioDTO usuarioDto, RequisicaoLiberacaoDTO requisicaoLiberacaoDto, TransactionControler tc) throws Exception {
		if(usuario == null){
			usuario = new Usuario();
		}
		usuario.setLocale(usuarioDto.getLocale());
		ExecucaoLiberacao execucaoRequisicaoLiberacao = new ExecucaoLiberacao(requisicaoLiberacaoDto,null);
		execucaoRequisicaoLiberacao.reabre(usuarioDto.getLogin());
	}
}
