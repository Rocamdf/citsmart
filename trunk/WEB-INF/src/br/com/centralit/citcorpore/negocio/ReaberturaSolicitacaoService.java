package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface ReaberturaSolicitacaoService extends CrudServicePojo {
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception;
}
