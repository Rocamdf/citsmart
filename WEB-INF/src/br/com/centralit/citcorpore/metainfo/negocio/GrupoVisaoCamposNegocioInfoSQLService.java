package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface GrupoVisaoCamposNegocioInfoSQLService extends CrudServicePojo {
	public Collection findByIdGrupoVisao(Integer parm) throws Exception;
	public void deleteByIdGrupoVisao(Integer parm) throws Exception;
	public Collection findByIdCamposObjetoNegocio(Integer parm) throws Exception;
	public void deleteByIdCamposObjetoNegocio(Integer parm) throws Exception;
}
