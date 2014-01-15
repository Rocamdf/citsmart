package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.jce.provider.test.ECEncodingTest;

import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.integracao.ObjetoInstanciaFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.ExecucaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ExecucaoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoMudanca;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoSolicitacao;
import br.com.centralit.citcorpore.integracao.ExecucaoMudancaDao;
import br.com.centralit.citcorpore.integracao.ExecucaoSolicitacaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaDao;
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
import br.com.citframework.util.Reflexao;

import com.google.gson.Gson;


@SuppressWarnings({"unchecked","rawtypes","unused","serial"})
public class ExecucaoMudancaServiceEjb extends CrudServicePojoImpl implements ExecucaoMudancaService {
	protected CrudDAO getDao() throws ServiceException {
		return new ExecucaoMudancaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	@Override
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario) throws Exception {
		return new ExecucaoMudanca().recuperaTarefas(loginUsuario);
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
		ExecucaoMudancaDTO execucaoMudancaDto = (ExecucaoMudancaDTO) new ExecucaoMudancaDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
		
		RequisicaoMudancaDTO solicitacaoDto = new RequisicaoMudancaServiceEjb().restoreAll(execucaoMudancaDto.getIdRequisicaoMudanca());
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		tc.start();
		try{

			ExecucaoMudanca execucaoSolicitacao = new ExecucaoMudanca(tc);
			execucaoSolicitacao.delega(loginUsuario, solicitacaoDto, idTarefa, usuarioDestino, grupoDestino);
			tc.commit();
		
		}finally{
			tc.close();
		}
	}
	
	public TarefaFluxoDTO recuperaTarefa(Integer idTarefa) throws Exception {
		TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
		TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
		tarefaFluxoDto.setIdItemTrabalho(idTarefa);
		return (TarefaFluxoDTO) tarefaFluxoDao.restore(tarefaFluxoDto);
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
			ExecucaoMudancaDTO execucaoMudancaDto = new ExecucaoMudancaDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
			if (execucaoMudancaDto != null) {
				ObjetoNegocioDTO objetoNegocioDto = new ObjetoNegocioDao().findByNomeObjetoNegocio("RequisicaoMudanca");
				if (objetoNegocioDto == null)
					return null;
				
				GrupoVisaoCamposNegocioDTO grupoCampoDto = new GrupoVisaoCamposNegocioDTO();
				CamposObjetoNegocioDTO campoDto = new CamposObjetoNegocioDTO();
				campoDto.setNome("IDREQUISICAOMUDANCA");
				campoDto.setNomeDB("IDREQUISICAOMUDANCA");
				campoDto.setIdObjetoNegocio(objetoNegocioDto.getIdObjetoNegocio());
				campoDto.setPk("S");
				campoDto.setValue(execucaoMudancaDto.getIdRequisicaoMudanca());
				grupoCampoDto.setCamposObjetoNegocioDto(campoDto);
				result.add(grupoCampoDto);
			}
		}
   		return result;
	}

	@Override
	public void registraMudanca(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc, Usuario usuario) throws Exception {	
		ExecucaoMudanca execucaoMudanca = new ExecucaoMudanca(requisicaoMudancaDto, tc, usuario);
		execucaoMudanca.inicia();
	}
	
	public void registraMudanca(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc, UsuarioDTO usuarioDto) throws Exception {	
		if(usuario == null){
			usuario = new Usuario();
		}
		usuario.setLocale(usuarioDto.getLocale());
		ExecucaoMudanca execucaoMudanca = new ExecucaoMudanca(requisicaoMudancaDto, tc, usuario);
		execucaoMudanca.inicia();
	}	

	public void executa(UsuarioDTO usuarioDto, TransactionControler tc, Integer idFluxo, Integer idTarefa, String acaoFluxo, HashMap<String, String> params, Collection<CamposObjetoNegocioDTO> colCamposTodosPrincipal, Collection<CamposObjetoNegocioDTO> colCamposTodosVinc) throws Exception{
		HashMap<String, Object> map = new HashMap();
		trataCamposTarefa(params, colCamposTodosPrincipal, map, "S");
		trataCamposTarefa(params, colCamposTodosVinc, map, "N");
		Integer idSolicitacao = new Integer((String) map.get("IDREQUISICAOMUDANCA"));
		RequisicaoMudancaDTO requisicaoMudancaDto = new RequisicaoMudancaServiceEjb().restoreAll(idSolicitacao, tc);

		new ExecucaoMudanca(tc).executa(usuarioDto.getLogin(), requisicaoMudancaDto, idTarefa, acaoFluxo, map);
	}

	public void executa(RequisicaoMudancaDTO requisicaoMudancaDto, Integer idTarefa, String acaoFluxo, TransactionControler tc) throws Exception {
		String acao = Enumerados.ACAO_EXECUTAR;
		if (requisicaoMudancaDto.getAcaoFluxo() != null)
			acao = requisicaoMudancaDto.getAcaoFluxo();
		HashMap<String, Object> objetos = new HashMap();
		RequisicaoMudancaDTO mudancaAuxDto = new RequisicaoMudancaServiceEjb().restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca(), tc);
		new ExecucaoMudanca(tc).executa(requisicaoMudancaDto.getUsuarioDto().getLogin(), mudancaAuxDto, idTarefa, acaoFluxo, objetos);
	}

	public void direcionaAtendimento( RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception {
		if (requisicaoMudancaDto.getIdGrupoAtual() == null) 
			return;
			
		RequisicaoMudancaDTO mudancaAuxDto = new RequisicaoMudancaServiceEjb().restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca(), tc);
		
		if (mudancaAuxDto == null)
			throw new Exception(i18n_Message("requisicaoMudanca.problemaRecuperacao"));

		if (mudancaAuxDto.getNomeGrupoAtual() == null || mudancaAuxDto.getNomeGrupoAtual().length() == 0)
			throw new Exception(i18n_Message("requisicaoMudanca.grupoNaoEncontrado"));
		
		new ExecucaoMudanca(mudancaAuxDto,tc, usuario).direcionaAtendimento(requisicaoMudancaDto.getUsuarioDto().getLogin(), mudancaAuxDto, mudancaAuxDto.getNomeGrupoAtual());
	}
	
	public void direcionaAtendimentoAutomatico( RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception {
		if (requisicaoMudancaDto.getIdGrupoAtual() == null) 
			return;
			
		RequisicaoMudancaDTO mudancaAuxDto = new RequisicaoMudancaServiceEjb().restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca(), tc);
		
		if(mudancaAuxDto != null && mudancaAuxDto.getIdResponsavel() == null){
			mudancaAuxDto.setIdResponsavel(mudancaAuxDto.getIdProprietario());
		}
		
		if (mudancaAuxDto == null)
			throw new Exception(i18n_Message("requisicaoMudanca.problemaRecuperacao"));

		if (mudancaAuxDto.getNomeGrupoAtual() == null || mudancaAuxDto.getNomeGrupoAtual().length() == 0)
			throw new Exception(i18n_Message("requisicaoMudanca.grupoNaoEncontrado"));
		
		new ExecucaoMudanca(mudancaAuxDto,tc, usuario).direcionaAtendimento("admin", mudancaAuxDto, mudancaAuxDto.getNomeGrupoAtual());
	}
	
	public void executa(UsuarioDTO usuarioDto, Integer idTarefa, String acaoFluxo) throws Exception {
		TarefaFluxoDTO tarefaDto = recuperaTarefa(idTarefa);
		if (tarefaDto == null)
			return;

		ExecucaoMudancaDTO execucaoMudancaDto = (ExecucaoMudancaDTO) new ExecucaoMudancaDao().findByIdInstanciaFluxo(tarefaDto.getIdInstancia());
		if (execucaoMudancaDto == null)
			return;

		RequisicaoMudancaDTO requisicaoMudancaDto = new RequisicaoMudancaDTO();
		requisicaoMudancaDto.setIdRequisicaoMudanca(execucaoMudancaDto.getIdRequisicaoMudanca());
		requisicaoMudancaDto.setUsuarioDto(usuarioDto);
		TransactionControlerImpl tc = new TransactionControlerImpl(getDao().getAliasDB());
		executa(requisicaoMudancaDto, idTarefa, acaoFluxo, tc);
		try {
			if (tc != null) {
				tc.close();
				tc = null;
			}
		} catch (Exception e) {
		}
	}

	public void suspende(UsuarioDTO usuarioDto, RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception {
		if(usuario == null){
			usuario = new Usuario();
		}
		usuario.setLocale(usuarioDto.getLocale());
		ExecucaoMudanca execucaoMudanca = new ExecucaoMudanca(requisicaoMudancaDto, tc, usuario);
		execucaoMudanca.suspende(usuarioDto.getLogin());
	}
	
	public void encerra(UsuarioDTO usuarioDto, RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception {
		if(usuario == null){
			usuario = new Usuario();
		}
		usuario.setLocale(usuarioDto.getLocale());
		ExecucaoMudanca execucaoMudanca = new ExecucaoMudanca(requisicaoMudancaDto, tc, usuario);
		execucaoMudanca.encerra();
	}
	
	public void reativa(UsuarioDTO usuarioDto, RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception {
		if(usuario == null){
			usuario = new Usuario();
		}
		usuario.setLocale(usuarioDto.getLocale());
		ExecucaoMudanca execucaoMudanca = new ExecucaoMudanca(requisicaoMudancaDto, tc, usuario);
		execucaoMudanca.reativa(usuarioDto.getLogin());
	}
	
}
