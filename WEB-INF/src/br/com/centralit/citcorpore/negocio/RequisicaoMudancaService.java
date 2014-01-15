/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.HistoricoMudancaDTO;
import br.com.centralit.citcorpore.bean.PesquisaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceEjb2;

@SuppressWarnings("rawtypes")
public interface RequisicaoMudancaService extends CrudServiceEjb2 {
	/**
	 * Retorna Requisições de Mudança associados ao conhecimento informado.
	 * 
	 * @param baseConhecimentoDto
	 * @return Collection
	 * @throws ServiceException
	 * @throws LogicException
	 * @author Vadoilo Damasceno
	 */
	public Collection findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException;

	public ServicoContratoDTO findByIdContratoAndIdServico(Integer idContrato, Integer idServico) throws Exception;

	public Collection findBySolictacaoServico(RequisicaoMudancaDTO bean) throws ServiceException, LogicException;

	public void gravaInformacoesGED(Collection colArquivosUpload, int idEmpresa, RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception;

	public void gravaPlanoDeReversaoGED(RequisicaoMudancaDTO requisicaomudacaDTO, TransactionControler tc, HistoricoMudancaDTO historicoMudancaDTO) throws Exception;
	
	public Collection<RequisicaoMudancaDTO> listaMudancaPorBaseConhecimento(RequisicaoMudancaDTO mudanca) throws Exception;

	public Collection listaQuantidadeMudancaPorImpacto(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

	public Collection listaQuantidadeMudancaPorPeriodo(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

	public Collection listaQuantidadeMudancaPorProprietario(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

	public Collection listaQuantidadeMudancaPorSolicitante(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

	public Collection listaQuantidadeMudancaPorStatus(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

	public Collection listaQuantidadeMudancaPorUrgencia(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

	public Collection listaQuantidadeSemAprovacaoPorPeriodo(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

	public List<RequisicaoMudancaDTO> listMudancaByIdItemConfiguracao(Integer idItemConfiguracao) throws Exception;
	
	public String getUrlInformacoesComplementares(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception ;
	
	public void updateNotNull(IDto obj) throws Exception;
	/**
	 * Retorna uma lista de solicitacao servico associada a requisição mudanca
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public List<RequisicaoMudancaDTO> listMudancaByIdSolicitacao(RequisicaoMudancaDTO bean) throws Exception;

	/**
	 * Retorna uma lista de requisicao mudanca de acordo com os criterios passados.
	 * 
	 * @param requisicaoMudancaDto
	 * @return Collection<RequisicaoMudancaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios(PesquisaRequisicaoMudancaDTO requisicaoMudancaDto) throws Exception;

	public Collection<RequisicaoMudancaDTO> quantidadeMudancaPorBaseConhecimento(RequisicaoMudancaDTO mudanca) throws Exception;

	/**
	 * reativa requisição servico
	 * 
	 * @param usuarioDto
	 * @param solicitacaoServicoDto
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void reativa(UsuarioDTO usuarioDto, RequisicaoMudancaDTO RequisicaoMudancaDto) throws Exception;

	public RequisicaoMudancaDTO restoreAll(Integer idRequisicaoMudanca) throws Exception;

	public void rollbackTransaction(TransactionControler tc, Exception ex) throws ServiceException, LogicException;

	/**
	 * suspende a requisição mudança
	 * 
	 * @param usuarioDto
	 * @param solicitacaoServicoDto
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void suspende(UsuarioDTO usuarioDto, RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

	/**
	 * Retorna verdadeiro ou falso caso a requisição esteja aprovada
	 * 
	 * @param requisicaoMudancaDto
	 * @param tc
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean validacaoAvancaFluxo(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception;
	
	/**
	 * Retorna se a requisição mudança esta aprovada, reprovada ou aguardando votação 
	 * @param requisicaoMudancaDto
	 * @param tc
	 * @return
	 * @throws Exception
	 * @author thiago.oliveira
	 */
	public String verificaAprovacaoProposta(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception;
	public String verificaAprovacaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDto, TransactionControler tc) throws Exception;

	/**
	 * Retorna verdadeiro ou falso caso tipo mudanca esteja associado a requisição mudança
	 * 
	 * @param idTipoMudanca
	 * @return
	 * @throws Exception
	 * @author geber.costa
	 */
	public boolean verificarSeRequisicaoMudancaPossuiTipoMudanca(Integer idTipoMudanca) throws Exception;

	public Collection listaQuantidadeERelacionamentos(HttpServletRequest request, RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

	public Collection listaIdETituloMudancasPorPeriodo(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception;

	public void update(IDto requisicaoMudancaDto,
			HttpServletRequest request) throws ServiceException, LogicException;
	
	public boolean verificaPermissaoGrupoCancelar(Integer idTipoMudança, Integer idGrupo) throws ServiceException, Exception;
	
	
	public boolean verificarItensRelacionados(RequisicaoMudancaDTO requisicaoMudancaDto) throws ServiceException,  Exception;

	public boolean planoDeReversaoInformado(RequisicaoMudancaDTO requisicaoMudancaDto, HttpServletRequest request) throws Exception; 
}
