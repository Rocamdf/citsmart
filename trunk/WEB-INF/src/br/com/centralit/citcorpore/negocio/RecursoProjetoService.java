package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface RecursoProjetoService extends CrudServicePojo {
	public Collection findByIdProjeto(Integer parm) throws Exception;
	public void deleteByIdProjeto(Integer parm) throws Exception;
	public Collection findByIdEmpregado(Integer parm) throws Exception;
	public void deleteByIdEmpregado(Integer parm) throws Exception;
}
