package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.List;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CargaCatalagoServicoDTO;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface CargaCatalagoServicoService extends CrudServiceEjb2 {
	
	public List<CargaCatalagoServicoDTO> gerarCarga(String[] carga) throws ServiceException, Exception;
	public List<CargaCatalagoServicoDTO> gerarCarga(File carga ,Integer idempresa) throws ServiceException, Exception;

}
