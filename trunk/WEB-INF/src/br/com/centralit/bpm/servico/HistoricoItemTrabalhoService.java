package br.com.centralit.bpm.servico;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;
public interface HistoricoItemTrabalhoService extends CrudServiceEjb2 {
	public Collection findByIdItemTrabalho(Integer parm) throws Exception;
	public Collection findByIdUsuario(Integer parm) throws Exception;
}
