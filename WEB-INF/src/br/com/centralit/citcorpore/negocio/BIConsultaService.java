package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.citframework.service.CrudServicePojo;
public interface BIConsultaService extends CrudServicePojo {
	public Collection findByIdCategoria(Integer parm) throws Exception;
	public BIConsultaDTO getByIdentificacao(String ident) throws Exception;
}
