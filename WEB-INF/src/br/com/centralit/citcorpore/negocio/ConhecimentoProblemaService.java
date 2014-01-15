package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ConhecimentoProblemaDTO;
import br.com.citframework.service.CrudServiceEjb2;
@SuppressWarnings("rawtypes")
public interface ConhecimentoProblemaService extends CrudServiceEjb2 {
	
	
	/**
	 * Retorna uma lista de idBaseConhecimento de acordo com o idproblema passado
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public Collection findByIdProblema(Integer parm) throws Exception;
	
	/**
	 * Retorna uma lista de base de conhecimento de acordo com o idProblema passado
	 * @param parm
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<ConhecimentoProblemaDTO> listaBaseConhecimentoByIdProblema(Integer parm) throws Exception;

}
