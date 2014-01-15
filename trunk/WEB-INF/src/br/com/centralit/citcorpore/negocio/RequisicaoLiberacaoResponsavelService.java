package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface RequisicaoLiberacaoResponsavelService extends CrudServiceEjb2 {
	public Collection findByIdLiberacao(Integer parm) throws Exception;
	public Collection findByIdLiberacaoEDataFim(Integer parm) throws Exception;
	public void deleteByIdLiberacao(Integer parm) throws Exception;
}
