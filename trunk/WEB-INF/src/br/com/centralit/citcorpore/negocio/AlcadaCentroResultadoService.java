package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface AlcadaCentroResultadoService extends CrudServiceEjb2 {
	
	/**
	 * Retorna true ou falso
	 * 
	 * @param obj
	 * @return boolean
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarVinculoCentroResultado(Integer obj) throws Exception;
	public Collection<AlcadaCentroResultadoDTO> findByIdCentroResultado(Integer idCentroResultado) throws Exception;
}
