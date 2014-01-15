package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface VisaoRelacionadaService extends CrudServicePojo {
	public Collection findByIdVisaoPaiAtivos(Integer idVisaoPai) throws Exception;
}
