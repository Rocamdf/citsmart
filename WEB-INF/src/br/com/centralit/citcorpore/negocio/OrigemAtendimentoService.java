package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;
public interface OrigemAtendimentoService extends CrudServiceEjb2 {
	
	public void deletarOrigemAtendimento(IDto model, DocumentHTML document) throws ServiceException, Exception;
	public boolean consultarOrigemAtendimentoAtivos(OrigemAtendimentoDTO obj) throws Exception;
	public Collection<OrigemAtendimentoDTO> recuperaAtivos() throws Exception;
}
