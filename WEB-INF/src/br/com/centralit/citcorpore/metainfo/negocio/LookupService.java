package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.bean.LookupDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface LookupService extends CrudServicePojo {
	public Collection findSimple(LookupDTO parm) throws Exception;
	public String findSimpleString(LookupDTO parm) throws Exception;
}
