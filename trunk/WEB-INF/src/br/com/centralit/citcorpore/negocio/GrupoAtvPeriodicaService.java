package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface GrupoAtvPeriodicaService extends CrudServicePojo {
	public Collection findByDescGrupoAtvPeriodica(String parm) throws Exception;
	public void deleteByDescGrupoAtvPeriodica(String parm) throws Exception;
	public Collection listGrupoAtividadePeriodicaAtiva() throws Exception;
	
}
