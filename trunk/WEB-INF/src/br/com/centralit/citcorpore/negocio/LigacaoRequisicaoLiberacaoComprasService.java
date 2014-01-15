package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoComprasDTO;
import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoMidiaDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface LigacaoRequisicaoLiberacaoComprasService extends CrudServiceEjb2 {
	public Collection<LigacaoRequisicaoLiberacaoHistoricoComprasDTO> findByIdLiberacao(Integer parm) throws Exception;
}
