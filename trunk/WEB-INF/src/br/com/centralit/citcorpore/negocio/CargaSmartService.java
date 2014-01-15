/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.List;

import br.com.centralit.citcorpore.bean.CargaSmartDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author Vadoilo Damasceno
 * 
 */
public interface CargaSmartService extends CrudServiceEjb2 {

	List<CargaSmartDTO> gerarCarga(File arquivo, Integer idEmpresa) throws ServiceException, Exception;
	

}
