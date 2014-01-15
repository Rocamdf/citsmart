package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;

@SuppressWarnings("rawtypes")
public interface ServicoContratoService extends CrudServicePojo {

	public Collection findByIdServico(Integer parm) throws Exception;

	public Collection findByIdContratoDistinct(Integer idContrato) throws Exception;

	public void deleteByIdServico(Integer parm) throws Exception;
	
	/**
	 * Instancia DAO das classe para realizar a exclus�o l�gica.
	 * @param model
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void deletarByIdServicoContrato(IDto model, DocumentHTML document) throws ServiceException, Exception;

	public Collection findByIdContrato(Integer parm) throws Exception;
	
	public Collection findByIdContratoPaginada(ServicoContratoDTO servicoContratoDTO, String paginacao, Integer pagAtual, Integer pagAtualAux, Integer totalPag, Integer quantidadePaginator, String campoPesquisa) throws Exception;
	
	public void deleteByIdContrato(Integer parm) throws Exception;

	public ServicoContratoDTO findByIdContratoAndIdServico(Integer idContrato, Integer idServico) throws Exception;

	public void setDataFim(HashMap map) throws Exception;
	
	public Collection listarServicosPorFornecedor(Integer idFornecedor) throws Exception;
	
	public boolean validaServicoContrato(HashMap map) throws Exception;
	
	public Collection findServicoContratoByIdContrato(Integer idContrato) throws Exception;

	public ServicoContratoDTO findByIdServicoContrato(Integer idServico, Integer idContrato) throws Exception;
	
	public boolean pesquisaServicosVinculados(DocumentHTML document, HashMap map, HttpServletRequest request) throws Exception;
	
}
