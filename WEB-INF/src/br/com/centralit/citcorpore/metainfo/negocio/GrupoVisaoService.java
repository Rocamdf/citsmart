package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface GrupoVisaoService extends CrudServicePojo {
	public Collection findByIdVisao(Integer idVisao) throws Exception;
	public Collection findByIdVisaoAtivos(Integer idVisao) throws Exception;
}
