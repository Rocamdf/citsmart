/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.PesquisaSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.RelatorioKpiProdutividadeDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoRetornoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO;
import br.com.centralit.citcorpore.bean.RelatorioSolicitacaoPorExecutanteDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceEjb2;

@SuppressWarnings("rawtypes")
public interface SolicitacaoServicoService extends CrudServiceEjb2 {
	public void deserializaInformacoesComplementares(SolicitacaoServicoDTO solicitacaoServicoDto, SolicitacaoServicoQuestionarioDTO solQuestionarioDto) throws Exception;

	public void encerra(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

	/**
	 * Retorna Solicitações de Serviço associados ao conhecimento informado.
	 * 
	 * @param baseConhecimentoDto
	 * @return Collection
	 * @throws ServiceException
	 * @throws LogicException
	 * @author Vadoilo Damasceno
	 */

	public Collection findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException;

	public Collection findByIdSolictacaoServico(Integer parm) throws ServiceException, LogicException;

	public Collection<SolicitacaoServicoDTO> findByServico(Integer idServico) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByServico(Integer idServico, String nome) throws Exception;

	public ArrayList<SolicitacaoServicoDTO> findSolicitacoesServicosUsuario(Integer idUsuario, Integer idItemConfiguracao);

	public boolean hasSolicitacoesServicosUsuario(Integer idUsuario, String status) throws Exception;

	public ArrayList<SolicitacaoServicoDTO> findSolicitacoesServicosUsuario(Integer idUsuario, String status, String campoBusca) throws Exception;

	public Collection<SolicitacaoServicoDTO> getHistoricoByIdSolicitacao(Integer idSolicitacao) throws Exception;

	public ItemTrabalho getItemTrabalho(Integer idItemTrabalho) throws Exception;

	public Integer getQuantidadeByIdServico(int idServico) throws Exception;

	public Integer getQuantidadeByIdServicoContrato(int idServicoContrato) throws Exception;

	public String getUrlInformacoesComplementares(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

	public void gravaInformacoesGED(Collection colArquivosUpload, int idEmpresa, SolicitacaoServicoDTO solicitacaoServicoDto, TransactionControler tc) throws Exception;

	public Collection<SolicitacaoServicoDTO> listAll() throws Exception;

	public Collection<SolicitacaoServicoDTO> listAllIncidentes(Integer idUsuario) throws Exception;

	public Collection<SolicitacaoServicoDTO> listAllServicos() throws Exception;

	public Collection<SolicitacaoServicoDTO> listAllServicosLikeNomeServico(String nome) throws Exception;

	/**
	 * Metodo retornar uma lista com a quantidade de solicitação por fase
	 * 
	 * @param solicitacaoDto
	 * @return
	 * @throws Exception
	 */
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorFase(SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	/**
	 * Metodo retornar uma lista com a quantidade de solicitação por grupoSolucionador.
	 * 
	 * @return
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorGrupo(HttpServletRequest request, SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorHoraAbertura(SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	/**
	 * Metodo retornar uma lista com a quantidade de solicitação por Item Configuração.
	 * 
	 * @return
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorItemConfiguracao(SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	/**
	 * Metodo retornar uma lista com a quantidade de solicitação por Origem
	 * 
	 * @param solicitacaoDto
	 * @return
	 * @throws Exception
	 */
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorOrigem(SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorPesquisaSatisfacao(HttpServletRequest request, SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	/**
	 * Metodo retornar uma lista com a quantidade de solicitação por prioridade
	 * 
	 * @param solicitacaoDto
	 * @return
	 * @throws Exception
	 */
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorPrioridade(SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorResponsavel(SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	/**
	 * Metodo retornar uma lista com a quantidade de solicitação por serviço
	 * 
	 * @param solicitacaoDto
	 * @return
	 * @throws Exception
	 */
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorServico(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

	/**
	 * Metodo retornar os serviços aprovados e abertos
	 * 
	 * @param solicitacaoDto
	 * @return
	 * @throws Exception
	 */
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaServicosAbertosAprovados(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

	/**
	 * Metodo retornar uma lista com a quantidade de solicitação por situação
	 * 
	 * @param solicitacaoDto
	 * @return
	 * @throws Exception
	 */
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSituacao(SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSituacaoSLA(HttpServletRequest request, SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	/**
	 * Metodo retornar uma lista com a quantidade de solicitação por solicitante.
	 * 
	 * @return
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSolicitante(SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	/**
	 * Metodo retornar uma lista com a quantidade de solicitação por tipo
	 * 
	 * @param solicitacaoDto
	 * @return
	 * @throws Exception
	 */
	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorTipo(SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorTipoServico(SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	public String listaServico(Integer idSolicitacaoservico) throws PersistenceException, Exception;

	/**
	 * Retorna uma lista de idSolicitacaoServico
	 * 
	 * @param solicitacao
	 * @return
	 * @throws Exception
	 */
	public Collection<SolicitacaoServicoDTO> listaSolicitacaoPorBaseConhecimento(SolicitacaoServicoDTO solicitacao) throws Exception;

	/**
	 * Retorna Solicitações serviço de acordo com os criterios passados
	 * 
	 * @param pesquisaSolicitacaoServicoDto
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto) throws Exception;

	public Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriteriosPaginado(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto, String paginacao, Integer pagAtual,
			Integer pagAtualAux, Integer totalPag, Integer quantidadePaginator, String campoPesquisa) throws Exception;

	public Collection listaSolicitacoesSemPesquisaSatisfacao() throws Exception;

	public SolicitacaoServicoDTO listIdentificacao(Integer idItemConfiguracao) throws Exception;

	public Collection<SolicitacaoServicoDTO> listIncidentesNaoFinalizados() throws Exception;

	public SolicitacaoServicoDTO listInformacaoContato(String nomeContato) throws Exception;;

	/**
	 * Retorna Solicitaï¿½ï¿½es de Serviços de acordo com o Tipo de Demanda e Usuï¿½rio.
	 * 
	 * @param tipoDemandaServico
	 * @param grupoSeguranca
	 * @param usuario
	 * @return
	 * @throws Exception
	 */
	public Collection<SolicitacaoServicoDTO> listSolicitacaoServico(String tipoDemandaServico, GrupoDTO grupoSeguranca, UsuarioDTO usuario, Date dataInicio, Date dataFim, String situacao)
			throws Exception;

	public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoByCriterios(Collection colCriterios) throws Exception;

	public List<SolicitacaoServicoDTO> listSolicitacaoServicoByItemConfiguracao(Integer idItemConfiguracao) throws Exception;

	public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoEmAndamento(Integer idSolicitacaoServico);

	public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoNaoFinalizadas() throws Exception;

	Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada(int idSolicitacaoPai);

	public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionadaPai(int idSolicitacaoPai);

	public Collection<SolicitacaoServicoDTO> listSolicitacoesFilhas() throws Exception;

	public SolicitacaoServicoDTO obterQuantidadeSolicitacoesServico(Integer idServicoContrato, java.util.Date dataInicio, java.util.Date dataFim) throws Exception;

	/**
	 * Retorna quantidade
	 * 
	 * @param solicitacao
	 * @return Integer
	 * @throws Exception
	 * @author Thays
	 */
	public Collection<SolicitacaoServicoDTO> quantidadeSolicitacaoPorBaseConhecimento(SolicitacaoServicoDTO solicitacao) throws Exception;

	public void reabre(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

	public void reativa(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

	public FluxoDTO recuperaFluxo(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

	public Collection<SolicitacaoServicoDTO> relatorioControleSla(SolicitacaoServicoDTO solicitacao) throws Exception;

	public SolicitacaoServicoDTO restoreAll(Integer idSolicitacaoServico) throws Exception;

	/**
	 * Retorna SolicitacaoServico com Item de Configuraï¿½ï¿½o do Solicitante.
	 * 
	 * @param login
	 * @return SolicitacaoServicoDTO
	 * @throws Exception
	 */
	public SolicitacaoServicoDTO retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(String login) throws Exception;

	public void suspende(UsuarioDTO usuarioDto, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;

	public boolean temSolicitacaoServicoAbertaDoEmpregado(Integer idEmpregado);

	public IDto updateInfo(IDto model) throws ServiceException, LogicException;

	public IDto updateInfoCollection(IDto model) throws ServiceException, LogicException;

	public void updateNotNull(IDto obj) throws Exception;

	public void updateSLA(IDto model) throws ServiceException, LogicException;

	void updateSolicitacaoPai(int idSolicitacaoPai, int idSolicitacao);

	/**
	 * Retornar verdadeiro ou falso
	 * 
	 * @param idUnidade
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarExistenciaDeUnidade(Integer idUnidade) throws Exception;

	/**
	 * Verifica se solicitação serviço possui Solicitação Filho.
	 * 
	 * @param idSolicitacaoServico
	 * @return true = possui; false = não possui.
	 * @throws Exception
	 */
	public boolean verificarExistenciaSolicitacaoFilho(Integer idSolicitacaoServico) throws Exception;

	public SolicitacaoServicoDTO verificaSituacaoSLA(SolicitacaoServicoDTO solicitacaoDto) throws Exception;

	public Collection incidentesPorContrato(Integer idContrato) throws Exception;

	public Collection<SolicitacaoServicoDTO> listarSLA() throws Exception;

	public Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorServicoContrato(Integer idServicoContratoContabil) throws Exception;

	public String calculaSLA(SolicitacaoServicoDTO solicitacaoServicoDto, HttpServletRequest request) throws Exception;

	public SolicitacaoServicoDTO findByIdSolicitacaoServico(Integer idSolicitacaoServico) throws Exception;

	/**
	 * Retorna uma lista de solicitacao serviço de acordo com os parametro passados com o principal objetivo de trazer somente solicitações fechadas ou canceladas.
	 * 
	 * @param relatorioSolicitacaoPorSolucionarDto
	 * @return Collection
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioSolicitacaoPorExecutanteDTO> listaSolicitacaoPorExecutante(RelatorioSolicitacaoPorExecutanteDTO relatorioSolicitacaoPorExecutanteDto) throws Exception;

	/**
	 * Retorna uma lista de Serviços que estejam associada a uma solicitação serviço.
	 * 
	 * @param relatorioAnaliseServicoDto
	 * @return Collection<RelatorioAnaliseServicoDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO> listaServicoPorSolicitacaoServico(RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO relatorioAnaliseServicoDto)
			throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdGrupo(Integer idGrupo) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataBaixa(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataMedia(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAlta(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasBaixa(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasMedia(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasAlta(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataSuspensasTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdPessoaEDataAtendidas(Integer idGrupo, String login, String nome, Date dataInicio, Date dataFim) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdPessoaEData(Integer idGrupo, String login, String nome, Date dataInicio, Date dataFim) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdPessoaEDataNaoAtendidas(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception;

	public Collection<RelatorioQuantitativoRetornoDTO> listaServicosRetorno(SolicitacaoServicoDTO solicitacaoServicoDTO, String grupoRetorno) throws Exception;

	public Collection<RelatorioQuantitativoRetornoDTO> listaServicosRetornoNomeResponsavel(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception;

	public SolicitacaoServicoDTO listaIdItemTrabalho(Integer idInstancia) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtrasadasTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception;

	public IDto create(IDto model, TransactionControler tc, boolean determinaPrioridadePrazo, boolean determinaHoraInicio, boolean determinaDataHoraSolicitacao) throws ServiceException,
			LogicException;

	public Collection<SolicitacaoServicoDTO> listaSolicitacoesPorIdEmpregado(Integer pgAtual, Integer qtdPaginacao, GerenciamentoServicosDTO gerenciamentoBean) throws Exception;

	public Collection<TipoDemandaServicoDTO> resumoTipoDemandaServico(List<TarefaFluxoDTO> listTarefas) throws Exception;

	public RelatorioQuantitativoRetornoDTO servicoRetorno(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception;

	public boolean validaQuantidadeRetorno(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception;

	public RelatorioQuantitativoRetornoDTO retornarIdEncerramento(String encerramento, RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception;

	/**
	 * @param solicitacaoServicoDTO
	 * @return Retorna Solicitacao com porcentagem de sla no prazo fora do prazo com referencia a prioridade
	 * @throws Exception
	 * @author maycon.fernandes
	 */
	public SolicitacaoServicoDTO relatorioControlePercentualQuantitativoSla(SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception;

	public boolean confirmaEncerramento(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO, Integer idElemento) throws Exception;

	public Collection<SolicitacaoServicoDTO> findByCodigoExterno(String codigoExterno) throws Exception;

	public SolicitacaoServicoDTO restoreByIdInstanciaFluxo(Integer idInstanciaFluxo) throws Exception;

	public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefas, TipoSolicitacaoServico[] tiposSolicitacao) throws Exception;

	public Collection<PrioridadeDTO> resumoPrioridade(List<TarefaFluxoDTO> listTarefas) throws Exception;

	public HashMap resumoPorPrazoLimite(Collection<TarefaFluxoDTO> listTarefas) throws Exception;

	public ComplemInfSolicitacaoServicoService getInformacoesComplementaresService(ItemTrabalho itemTrabalho) throws Exception;

	public boolean existeSolicitacaoServico(SolicitacaoServicoDTO solicitacaoservico) throws Exception;

	public boolean permissaoGrupoExecutorServico(int idGrupoExecutor, int idTipoFluxoSolicitacaoServico) throws Exception;
	
	public Collection<SolicitacaoServicoDTO> listarSolicitacoesAbertasEmAndamentoPorGrupo(int idGrupoAtual, String situacaoSla) throws Exception;
	
	public Collection<SolicitacaoServicoDTO> listarSolicitacoesMultadasSuspensasPorGrupo(int idGrupoAtual, String situacaoSla) throws Exception;
	
	public Collection<SolicitacaoServicoDTO> listaServicosPorResponsavelNoPeriodo(RelatorioKpiProdutividadeDTO dto) throws Exception;
		
}
