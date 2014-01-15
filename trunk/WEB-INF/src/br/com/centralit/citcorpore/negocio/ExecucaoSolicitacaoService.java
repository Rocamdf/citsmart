package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.servico.ExecucaoFluxoService;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.citframework.integracao.TransactionControler;

public interface ExecucaoSolicitacaoService extends ExecucaoFluxoService {
	public Collection<GrupoVisaoCamposNegocioDTO> findCamposTarefa(Integer idTarefa) throws Exception;

	public void trataCamposTarefa(Map<String, String> params, Collection<CamposObjetoNegocioDTO> colCampos, Map<String, Object> map, String principal, TransactionControler tc) throws Exception;

	public void registraSolicitacao(SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception;

	public void executa(UsuarioDTO usuarioDto, TransactionControler tc, Integer idFluxo, Integer idTarefa, String acaoFluxo, HashMap<String, String> params,
			Collection<CamposObjetoNegocioDTO> colCamposTodosPrincipal, Collection<CamposObjetoNegocioDTO> colCamposTodosVinc) throws Exception;

	public void executa(SolicitacaoServicoDTO solicitacaoServicoDto, Integer idTarefa, String acaoFluxo, TransactionControler tc) throws Exception;

	public void executa(UsuarioDTO usuarioDto, Integer idTarefa, String acaoFluxo) throws Exception;

	public void cancelaTarefa(String loginUsuario, Integer idTarefa, String motivo, TransactionControler tc) throws Exception;

	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario) throws Exception;

	public List<TarefaFluxoDTO> recuperaTarefas(Integer pgAtual, Integer qtdAPaginacao, String login) throws Exception;

	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario, TipoSolicitacaoServico[] tiposSolicitacao, String somenteEmAprovacao) throws Exception;

	public TarefaFluxoDTO recuperaTarefa(Integer idTarefa) throws Exception;

	public Integer totalPaginas(Integer itensPorPagina, String login) throws Exception;

	/**
	 * Retorna o Total de Páginas de acordo com o Login do Usuário, os Filtros e a Lista de Contratos que o Usuário Logado está associado.
	 * 
	 * @param itensPorPagina
	 * @param loginUsuario
	 * @param gerenciamentoBean
	 * @param listContratoUsuarioLogado
	 * @return
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 05.11.2013
	 */
	public Integer obterTotalDePaginas(Integer itensPorPagina, String loginUsuario, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception;

	/**
	 * Consulta utilizada para a RENDERIZAÇÃO da LISTAGEM SOLICITACAO SERVIÇO. Retorna a Lista de TarefaDTO com SolicitacaoServidoDTO de acordo com o Login e a Lista de Contratos do Usuário Logado.
	 * 
	 * @param pgAtual
	 * @param qtdAPaginacao
	 * @param login
	 * @param gerenciamentoBean
	 * @param listContratoUsuarioLogado
	 * @return List<TarefaFluxoDTO - Com SolicitacaoServicoDTO recuperados.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 05.11.2013
	 */
	public List<TarefaFluxoDTO> recuperaTarefas(Integer pgAtual, Integer qtdAPaginacao, String login, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado)
			throws Exception;

	/**
	 * Utilizado para a RENDERIZAÇÃO do GRÁFICO, pois no Gráfico não é necessário a utilização de Paginação. Esta consulta considera o Login do Usuário Logado e os Contratos em que está inserido.
	 * 
	 * @param loginUsuario
	 * @param gerenciamentoBean
	 * @return List<TarefaFluxoDTO - Com SolicitacaoServicoDTO recuperados.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 05.11.2013
	 */
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception;
	
	
	/**
	 * @param login
	 * @throws Exception
	 * @author mario.haysaki
	 * Criado para centralizar variavel estatica
	 */
	public void atualizaListaTarefas (String login) throws Exception;
}
