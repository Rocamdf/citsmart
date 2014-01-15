package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ControleContratoDTO;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author Pedro
 *
 */
public interface ControleContratoOcorrenciaService extends CrudServiceEjb2 {
	public void deleteByIdControleContrato(ControleContratoDTO controleContrato) throws Exception;

}
