package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.citframework.service.CrudServicePojo;
public interface ObjetoNegocioService extends CrudServicePojo {
	public Collection listAtivos() throws Exception;
	public Collection findByNomeTabelaDB(String nomeTabelaDBParm) throws Exception;
	public ObjetoNegocioDTO findByNomeObjetoNegocio(String nomeObjetoNegocio) throws Exception;
	public ObjetoNegocioDTO getByNomeTabelaDB(String nomeObjetoNegocio) throws Exception;
	public void updateDisable(ObjetoNegocioDTO objetoNegocioDTO) throws Exception;
}
