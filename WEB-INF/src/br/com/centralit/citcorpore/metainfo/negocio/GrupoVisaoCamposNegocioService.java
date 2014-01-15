package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface GrupoVisaoCamposNegocioService extends CrudServicePojo {
	public Collection findByIdGrupoVisao(Integer idGrupoVisao) throws Exception;
	public Collection findByIdGrupoVisaoAtivos(Integer idGrupoVisao) throws Exception;
}
