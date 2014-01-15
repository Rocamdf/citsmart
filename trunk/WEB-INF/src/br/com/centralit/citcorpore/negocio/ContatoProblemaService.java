package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ContatoProblemaDTO;
import br.com.citframework.service.CrudServicePojo;
/**
 * 
 * @author geber.costa
 *
 */
public interface ContatoProblemaService extends CrudServicePojo {
	public ContatoProblemaDTO restoreContatosById(Integer idContatoProblema) throws Exception;
	public ContatoProblemaDTO restoreContatosById(ContatoProblemaDTO obj) throws Exception;
}
