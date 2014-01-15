package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoResponsavelDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface LigacaoRequisicaoLiberacaoResponsavelService extends CrudServiceEjb2 {
	public Collection findByIdLiberacao(Integer parm) throws Exception;
}
