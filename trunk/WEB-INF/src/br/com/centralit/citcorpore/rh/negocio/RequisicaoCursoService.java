package br.com.centralit.citcorpore.rh.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface RequisicaoCursoService extends CrudServiceEjb2 {
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception;
	public void deleteByIdSolicitacaoServico(Integer parm) throws Exception;
}
