package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface TarefaLinhaBaseProjetoService extends CrudServicePojo {
	public Collection findByIdLinhaBaseProjeto(Integer parm) throws Exception;
	public void deleteByIdLinhaBaseProjeto(Integer parm) throws Exception;
	public Collection findCarteiraByIdEmpregado(Integer idEmpregado) throws Exception;
	public Collection findByIdTarefaLinhaBaseProjetoMigr(Integer idTarefaLinhaBaseProjetoMigr) throws Exception;
	public Collection findByIdTarefaLinhaBaseProjetoPai(Integer idTarefaLinhaBaseProjetoPai) throws Exception;
}
