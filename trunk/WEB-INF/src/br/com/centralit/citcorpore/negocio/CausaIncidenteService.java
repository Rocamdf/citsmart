package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface CausaIncidenteService extends CrudServicePojo {
	public Collection findByIdCausaIncidentePai(Integer parm) throws Exception;
	public void deleteByIdCausaIncidentePai(Integer parm) throws Exception;
	public Collection listHierarquia() throws Exception;
	public Collection getCollectionHierarquia(Integer idCausa, Integer nivel) throws Exception;
}
