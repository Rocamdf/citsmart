package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;
public interface CamposObjetoNegocioService extends CrudServicePojo {
	public Collection findByIdObjetoNegocio(Integer idObjetoNegocioParm) throws Exception;
	public Collection findByIdObjetoNegocioAndNomeDB(Integer idObjetoNegocioParm, String nomeDBParm) throws Exception;
	public void updateComplexidade(Integer idCampoObjNegocio1, Integer idCampoObjNegocio2) throws Exception;
	public void updateFluxoServico(Integer idCampoObjNegocio1, Integer idCampoObjNegocio2, Integer idCampoObjNegocio3) throws Exception;
}
