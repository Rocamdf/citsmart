package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface HistoricoSituacaoCotacaoService extends CrudServiceEjb2 {
	public Collection findByIdCotacao(Integer parm) throws Exception;
	public void deleteByIdCotacao(Integer parm) throws Exception;
	public Collection findByIdResponsavel(Integer parm) throws Exception;
	public void deleteByIdResponsavel(Integer parm) throws Exception;
}
