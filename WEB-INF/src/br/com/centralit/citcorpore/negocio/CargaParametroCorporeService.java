package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.List;

import br.com.centralit.citcorpore.bean.CargaParametroCorporeDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface CargaParametroCorporeService extends CrudServiceEjb2 {
	
	public List<CargaParametroCorporeDTO> gerarCarga(File carga ,Integer idEmpresa)throws ServiceException, Exception;

}
