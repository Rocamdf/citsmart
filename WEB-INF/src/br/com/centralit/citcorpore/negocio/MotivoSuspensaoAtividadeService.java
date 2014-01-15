package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.MotivoSuspensaoAtividadeDTO;
import br.com.citframework.service.CrudServicePojo;
public interface MotivoSuspensaoAtividadeService extends CrudServicePojo {
	public boolean jaExisteRegistroComMesmoNome(MotivoSuspensaoAtividadeDTO motivoSuspensaoAtividadeDTO);
	public Collection listarMotivosSuspensaoAtividadeAtivos() throws Exception;
}
