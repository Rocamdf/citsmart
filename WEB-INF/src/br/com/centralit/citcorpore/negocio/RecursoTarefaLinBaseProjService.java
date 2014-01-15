package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface RecursoTarefaLinBaseProjService extends CrudServicePojo {
	public Collection findByIdTarefaLinhaBaseProjeto(Integer parm) throws Exception;
	public void deleteByIdTarefaLinhaBaseProjeto(Integer parm) throws Exception;
	public Collection findByIdEmpregado(Integer parm) throws Exception;
	public void deleteByIdEmpregado(Integer parm) throws Exception;
}
