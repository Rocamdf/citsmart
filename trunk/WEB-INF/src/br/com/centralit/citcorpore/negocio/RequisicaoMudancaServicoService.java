/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaServicoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface RequisicaoMudancaServicoService extends CrudServiceEjb2 {
	public ArrayList<RequisicaoMudancaServicoDTO> listByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception;
	public RequisicaoMudancaServicoDTO restoreByChaveComposta(RequisicaoMudancaServicoDTO dto) throws ServiceException, Exception;
	
}
