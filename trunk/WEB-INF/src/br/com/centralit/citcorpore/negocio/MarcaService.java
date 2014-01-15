package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MarcaDTO;
import br.com.citframework.service.CrudServiceEjb2;


@SuppressWarnings("rawtypes")
public interface MarcaService extends CrudServiceEjb2 {
	
	
	public Collection findByIdFabricante(Integer parm) throws Exception;
	
	public void deleteByIdFabricante(Integer parm) throws Exception;
	
	public boolean consultarMarcas(MarcaDTO marca) throws Exception;
}
