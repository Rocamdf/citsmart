package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * 
 * @author pedro
 *
 */
public interface InfoCatalogoServicoService extends CrudServiceEjb2 {
	
	public Collection<InfoCatalogoServicoDTO> findByCatalogoServico(Integer idCatalogoServico) throws ServiceException, Exception;
	public boolean findByContratoServico(Integer idContratoServico) throws ServiceException, Exception;
}
