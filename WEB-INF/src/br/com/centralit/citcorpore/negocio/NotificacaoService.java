package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceEjb2;

public interface NotificacaoService extends CrudServiceEjb2 {

	/**
	 * Retorna true caso titulo exista false caso titulo não exista ou esteje excluido.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean consultarNotificacaoAtivos(NotificacaoDTO obj) throws Exception;

	/**
	 * Realiza Update de Notificacao.
	 * 
	 * @param notificacaoDto
	 * @param transactionControler
	 * @throws Exception
	 * @author Thays
	 */
	public void update(NotificacaoDTO notificacaoDto, TransactionControler transactionControler) throws Exception;

	/**
	 * Cria uma nova notificacao.
	 * 
	 * @param notificacaoDto
	 * @param transactionControler
	 * @return NotificacaoDTO
	 * @throws Exception
	 */
	public NotificacaoDTO create(NotificacaoDTO notificacaoDto, TransactionControler transactionControler) throws Exception;
	
	public Collection<NotificacaoDTO> consultarNotificacaoAtivosOrigemServico(Integer idContrato) throws Exception;
	
	public Collection<NotificacaoDTO> listaIdContrato(Integer idContrato) throws Exception;
	
	public void updateNotNull(IDto obj) throws Exception;
	
}
