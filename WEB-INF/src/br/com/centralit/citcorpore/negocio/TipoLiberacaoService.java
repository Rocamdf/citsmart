package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.TipoLiberacaoDTO;
import br.com.citframework.service.CrudServicePojo;

@SuppressWarnings("rawtypes")
public interface TipoLiberacaoService extends CrudServicePojo{
	
	public Collection findByIdTipoLiberacao(Integer parm) throws Exception;

	public void deleteByIdTipoLiberacao(Integer parm) throws Exception;
	
	//método baseado em categoriaLiberacao
	//public Collection findByIdTipoLiberacaoPai(Integer parm) throws Exception;

	//método baseado em categoriaLiberacao
	//public void deleteByIdTipoLiberacaoPai(Integer parm) throws Exception;

	public Collection findByNomeTipoLiberacao(Integer parm) throws Exception;

	public void deleteByNomeTipoLiberacao(Integer parm) throws Exception;
	
	//baseado em categoriaLiberacao
	//public Collection listHierarquia() throws Exception;
	
	public Collection<TipoLiberacaoDTO> tiposAtivosPorNome(String nome);
	
	public boolean verificarTipoLiberacaoAtivos(TipoLiberacaoDTO obj) throws Exception;
	
	public Collection encontrarPorNomeTipoLiberacao(TipoLiberacaoDTO obj) throws Exception;
	
	public Collection getAtivos()throws Exception;
}
