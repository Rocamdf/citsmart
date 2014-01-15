package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoMidiaDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface LigacaoRequisicaoLiberacaoMidiaService extends CrudServiceEjb2 {
	public Collection<LigacaoRequisicaoLiberacaoHistoricoMidiaDTO> findByIdLiberacao(Integer parm) throws Exception;
}
