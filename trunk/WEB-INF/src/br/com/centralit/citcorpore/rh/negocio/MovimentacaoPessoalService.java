package br.com.centralit.citcorpore.rh.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface MovimentacaoPessoalService extends CrudServiceEjb2 {
	public Collection findByIdEmpregado(Integer parm) throws Exception;
	public void deleteByIdEmpregado(Integer parm) throws Exception;
}
