package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.citframework.service.CrudServicePojo;

@SuppressWarnings("rawtypes")
public interface TipoMudancaService extends CrudServicePojo{
	
	public Collection findByIdTipoMudanca(Integer parm) throws Exception;

	public void deleteByIdTipoMudanca(Integer parm) throws Exception;
	
	//método baseado em categoriaMudanca
	//public Collection findByIdTipoMudancaPai(Integer parm) throws Exception;

	//método baseado em categoriaMudanca
	//public void deleteByIdTipoMudancaPai(Integer parm) throws Exception;

	public Collection findByNomeTipoMudanca(Integer parm) throws Exception;

	public void deleteByNomeTipoMudanca(Integer parm) throws Exception;
	
	//baseado em categoriaMudanca
	//public Collection listHierarquia() throws Exception;
	
	public Collection<TipoMudancaDTO> tiposAtivosPorNome(String nome);
	
	public boolean verificarTipoMudancaAtivos(TipoMudancaDTO obj) throws Exception;
	
	public Collection encontrarPorNomeTipoMudanca(TipoMudancaDTO obj) throws Exception;
	
	public Collection getAtivos()throws Exception;
}
