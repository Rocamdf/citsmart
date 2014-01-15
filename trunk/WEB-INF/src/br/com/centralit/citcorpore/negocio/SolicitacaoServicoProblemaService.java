package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoProblemaDTO;
import br.com.citframework.service.CrudServiceEjb2;
@SuppressWarnings("rawtypes")
public interface SolicitacaoServicoProblemaService  extends CrudServiceEjb2 {
	
	
	public Collection findByIdProblema(Integer parm) throws Exception;
	
	public SolicitacaoServicoProblemaDTO restoreByIdProblema(Integer idProblema) throws Exception;

}
