package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CatalogoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * 
 * @author pedro
 *
 */
public interface ControleContratoService extends CrudServiceEjb2 {
	

	
	public void update(IDto model) throws ServiceException, LogicException;
	
	 public IDto create(IDto model) throws ServiceException, LogicException;
	 

}
