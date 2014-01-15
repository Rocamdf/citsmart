package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

@SuppressWarnings("rawtypes")
public interface UnidadeService extends CrudServiceEjb2 {

	public Collection findByIdUnidade() throws Exception;
	
	public Collection findById(Integer idUnidade) throws Exception;

	public Collection findByIdEmpregado() throws Exception;
	

	public Collection listHierarquia() throws Exception;
	
	public Collection listHierarquiaMultiContratos(Integer idContrato) throws Exception;

	public boolean jaExisteUnidadeComMesmoNome(UnidadeDTO unidade);

	/**
	 * Metodo pra fazer a exclusão lógica de Unidade
	 * 
	 * @param model
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void deletarUnidade(IDto model, DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception;
	
	/**
	 * Restaura GRID de Serviços.
	 * 
	 * @author rodrigo.oliveira
	 */
	public void restaurarGridServicos(DocumentHTML document, Collection<UnidadesAccServicosDTO> servicos);
	
}
