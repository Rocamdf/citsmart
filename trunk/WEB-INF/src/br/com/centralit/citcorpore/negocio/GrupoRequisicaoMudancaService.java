package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;

public interface GrupoRequisicaoMudancaService extends CrudServicePojo{
	
	public void deleteByIdRequisicaoMudanca(Integer parm) throws Exception;
	
	public Collection listByIdHistoricoMudanca(Integer idHistoricoMudanca) throws Exception;
	
	public Collection findByIdMudancaEDataFim(Integer idRequisicaoMudanca) throws Exception;
	
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception;
	
	public void deleteByIdGrupo(Integer parm) throws Exception;
	
	public Collection findByIdGrupo(Integer parm) throws Exception;
	
	public void deleteByIdGrupoRequisicaoMudanca(Integer parm) throws Exception;
	
	public Collection findByIdGrupoRequisicaoMudanca(Integer parm) throws Exception;
}
