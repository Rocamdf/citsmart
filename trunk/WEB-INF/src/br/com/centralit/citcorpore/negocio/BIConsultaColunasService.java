package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface BIConsultaColunasService extends CrudServicePojo {
	public Collection findByIdConsulta(Integer parm) throws Exception;
	public void deleteByIdConsulta(Integer parm) throws Exception;
	public Collection findByOrdem(Integer parm) throws Exception;
	public void deleteByOrdem(Integer parm) throws Exception;
}
