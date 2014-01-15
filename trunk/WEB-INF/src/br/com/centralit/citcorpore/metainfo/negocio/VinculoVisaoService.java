package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface VinculoVisaoService extends CrudServicePojo {
	public Collection findByIdGrupoVisaoPai(Integer parm) throws Exception;
	public void deleteByIdGrupoVisaoPai(Integer parm) throws Exception;
	public Collection findByIdCamposObjetoNegocioPai(Integer parm) throws Exception;
	public void deleteByIdCamposObjetoNegocioPai(Integer parm) throws Exception;
	public Collection findByIdGrupoVisaoFilho(Integer parm) throws Exception;
	public void deleteByIdGrupoVisaoFilho(Integer parm) throws Exception;
	public Collection findByIdCamposObjetoNegocioFilho(Integer parm) throws Exception;
	public void deleteByIdCamposObjetoNegocioFilho(Integer parm) throws Exception;
	public Collection findByIdCamposObjetoNegocioPaiNN(Integer parm) throws Exception;
	public void deleteByIdCamposObjetoNegocioPaiNN(Integer parm) throws Exception;
	public Collection findByIdCamposObjetoNegocioFilhoNN(Integer parm) throws Exception;
	public void deleteByIdCamposObjetoNegocioFilhoNN(Integer parm) throws Exception;
	public Collection findByIdVisaoRelacionada(Integer parm) throws Exception;
}
