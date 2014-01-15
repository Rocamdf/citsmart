package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface NotificacaoGrupoService  extends CrudServiceEjb2 {
	
	public Collection<NotificacaoGrupoDTO> listaIdGrupo(Integer idNotificacao) throws Exception;

}
