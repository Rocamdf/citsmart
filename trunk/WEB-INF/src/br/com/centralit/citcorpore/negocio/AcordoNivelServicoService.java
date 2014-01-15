
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoHistoricoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface AcordoNivelServicoService extends CrudServiceEjb2 {
	public Collection findByIdServicoContrato(Integer parm) throws Exception;
	public Collection consultaPorIdServicoContrato(Integer parm) throws Exception;
	public void deleteByIdServicoContrato(Integer parm) throws Exception;
	public Collection findByIdPrioridadePadrao(Integer parm) throws Exception;
	public void deleteByIdPrioridadePadrao(Integer parm) throws Exception;
	public void copiarSLA(Integer idAcordoNivelServico, Integer idServicoContratoOrigem, Integer[] idServicoCopiarPara) throws Exception;
	public AcordoNivelServicoDTO findAtivoByIdServicoContrato(Integer idServicoContrato, String tipo) throws Exception;
	public List<ServicoContratoDTO> buscaServicosComContrato(String tituloSla) throws Exception;
	public boolean verificaSeNomeExiste(HashMap mapFields) throws Exception;
	public List<AcordoNivelServicoDTO> findAcordosSemVinculacaoDireta() throws Exception;
	/**
	 * Cria um novo acordo de nível de serviço
	 * 
	 * @param acordoNivelServicoDTO
	 * @param acordoNivelServicoHistoricoDTO
	 * @return AcordoNivelServicoDTO
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public AcordoNivelServicoDTO create(AcordoNivelServicoDTO acordoNivelServicoDTO, AcordoNivelServicoHistoricoDTO acordoNivelServicoHistoricoDTO) throws ServiceException, LogicException;
	
	/**
	 * Atualiza um novo acordo de nível de serviço
	 * 
	 * @param acordoNivelServicoDTO
	 * @param acordoNivelServicoHistoricoDTO
	 * @return AcordoNivelServicoDTO
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public AcordoNivelServicoDTO update(AcordoNivelServicoDTO acordoNivelServicoDTO, AcordoNivelServicoHistoricoDTO acordoNivelServicoHistoricoDTO) throws ServiceException, LogicException;
	
	/**
	 * Exclui Base de Conhecimento.
	 * 
	 * @param baseConhecimentoBean
	 * @param isAprovaBaseConhecimento
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public void excluir(AcordoNivelServicoDTO acordoNivelServicoDTO) throws Exception;
	public List<AcordoNivelServicoDTO> findIdEmailByIdSolicitacaoServico(Integer idSolicitacaoServico) throws Exception;
	public String verificaIdAcordoNivelServico(HashMap mapFields) throws Exception;

}
