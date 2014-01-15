package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.EventoGrupoDTO;
import br.com.centralit.citcorpore.bean.EventoItemConfigDTO;
import br.com.centralit.citcorpore.bean.EventoItemConfigRelDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfigEventoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.componenteMaquina.ThreadDisparaEvento;
import br.com.centralit.citcorpore.integracao.EventoGrupoDao;
import br.com.centralit.citcorpore.integracao.EventoItemConfigDao;
import br.com.centralit.citcorpore.integracao.EventoItemConfigRelDao;
import br.com.centralit.citcorpore.integracao.ItemConfigEventoDao;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

public class EventoItemConfigServiceEjb extends CrudServicePojoImpl implements EventoItemConfigService {

    private static final long serialVersionUID = 1L;

    protected CrudDAO getDao() throws ServiceException {
	return new EventoItemConfigDao();
    }

    protected void validaCreate(Object obj) throws Exception {
    }

    protected void validaDelete(Object obj) throws Exception {
    }

    protected void validaUpdate(Object obj) throws Exception {
    }

    protected void validaFind(Object obj) throws Exception {
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
	CrudDAO crudDao = getDao();
	ItemConfigEventoDao itemConfigEventoDao = new ItemConfigEventoDao();
	EventoGrupoDao eventoGrupoDao = new EventoGrupoDao();
	EventoItemConfigRelDao eventoItemConfigRelDao = new EventoItemConfigRelDao();

	TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
	try {
	    validaCreate(model);

	    crudDao.setTransactionControler(tc);
	    itemConfigEventoDao.setTransactionControler(tc);
    	eventoGrupoDao.setTransactionControler(tc);
    	eventoItemConfigRelDao.setTransactionControler(tc);

	    tc.start();

	    crudDao.create(model);

	    EventoItemConfigDTO eventoDto = (EventoItemConfigDTO) model;
	    
	    this.generateTableRelationship(eventoDto, eventoGrupoDao, eventoItemConfigRelDao);
	    
	    if (eventoDto.getLstItemConfigEvento() != null && eventoDto.getLstItemConfigEvento().size() > 0) {
		for (ItemConfigEventoDTO itemConfigEventoDto : eventoDto.getLstItemConfigEvento()) {
		    itemConfigEventoDto.setIdEvento(eventoDto.getIdEvento());
		    itemConfigEventoDao.create(itemConfigEventoDto);

		    // Dispara evento
		    shootEvent(itemConfigEventoDto, eventoDto);
		}
	    }

	    tc.commit();
	    tc.close();
	} catch (Exception e) {
	    this.rollbackTransaction(tc, e);
	}
	return model;
    }

    @Override
    public void update(IDto model) throws ServiceException, LogicException {
	CrudDAO crudDao = getDao();
	ItemConfigEventoDao itemConfigEventoDao = new ItemConfigEventoDao();
	EventoGrupoDao eventoGrupoDao = new EventoGrupoDao();
	EventoItemConfigRelDao eventoItemConfigRelDao = new EventoItemConfigRelDao();

	TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
	try {
	    validaUpdate(model);

	    crudDao.setTransactionControler(tc);
	    itemConfigEventoDao.setTransactionControler(tc);
    	eventoGrupoDao.setTransactionControler(tc);
    	eventoItemConfigRelDao.setTransactionControler(tc);

	    tc.start();
	    
	    crudDao.update(model);

	    EventoItemConfigDTO eventoDto = (EventoItemConfigDTO) model;

	    this.deleteObsoleteRelationship(eventoGrupoDao, eventoItemConfigRelDao, eventoDto.getIdEvento());
	    
	    this.generateTableRelationship(eventoDto, eventoGrupoDao, eventoItemConfigRelDao);
	    
	    if (eventoDto.getLstItemConfigEvento() != null && eventoDto.getLstItemConfigEvento().size() > 0) {
		itemConfigEventoDao.deleteByIdEvento(eventoDto.getIdEvento());
		for (ItemConfigEventoDTO itemConfigEventoDto : eventoDto.getLstItemConfigEvento()) {
		    itemConfigEventoDto.setIdEvento(eventoDto.getIdEvento());
		    itemConfigEventoDao.create(itemConfigEventoDto);

		    // Dispara evento
		    shootEvent(itemConfigEventoDto, eventoDto);
		}
	    }

	    tc.commit();
	    tc.close();
	} catch (Exception e) {
	    this.rollbackTransaction(tc, e);
	}
    }

    public ValorDTO pegarCaminhoItemConfig(String nomeBaseItemConfig) throws ServiceException, RemoteException {
	try {
	    EventoItemConfigDao dao = (EventoItemConfigDao) getDao();
	    return dao.pegarCaminhoItemConfig(nomeBaseItemConfig);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

    /**
     * Método que gera o relacionamento dos grupos de item configuração com o evento e dos itens de configuração com o evento.
     * 
     * @param eventoDto EventoItemConfigDTO evento a ser relacionado
     * @param tc TransactionControler controlador de transição do DAO
     * @throws Exception
     */
    private void generateTableRelationship(EventoItemConfigDTO eventoDto, EventoGrupoDao eventoGrupoDao, EventoItemConfigRelDao eventoItemConfigRelDao) throws Exception {
    	 // Cria o relacionamento Evento com o Grupo
	    if (eventoDto.getLstGrupo() != null && eventoDto.getLstGrupo().size() > 0) {
	    	for (EventoGrupoDTO eventoGrupoDTO : eventoDto.getLstGrupo()) {
				eventoGrupoDTO.setIdEvento(eventoDto.getIdEvento());
				eventoGrupoDao.create(eventoGrupoDTO);
			}
	    }
	    
	    // Cria o relacionamento Evento com o Item de Configuração
	    if (eventoDto.getLstItemConfiguracao() != null && eventoDto.getLstItemConfiguracao().size() > 0) {
	    	for (EventoItemConfigRelDTO itemConfigRelDTO : eventoDto.getLstItemConfiguracao()) {
				itemConfigRelDTO.setIdEvento(eventoDto.getIdEvento());
				eventoItemConfigRelDao.create(itemConfigRelDTO);
			}
	    }
    }
    
    /**
     * Método que verifica se é para executar o evento agora e dispara a thread.
     * 
     * @param itemConfigEventoDto
     * @param eventoDto
     * @throws Exception 
     */
    private void shootEvent(ItemConfigEventoDTO itemConfigEventoDto, EventoItemConfigDTO eventoDto) throws Exception {
	    if (itemConfigEventoDto.getGerarQuando().equalsIgnoreCase("A")) {
		/*if (eventoDto.getLstEmpregado() != null && eventoDto.getLstEmpregado().size() > 0) {
		    for (EventoEmpregadoDTO eventoEmpDto : eventoDto.getLstEmpregado()) {
			new Thread(new ThreadDisparaEvento(eventoEmpDto.getIdEmpregado(), itemConfigEventoDto.getIdBaseItemConfiguracao(), itemConfigEventoDto.getIdEvento(),
				itemConfigEventoDto.getTipoExecucao(), itemConfigEventoDto.getLinhaComando())).start();
		    }
		}*/
	    	List<EventoItemConfigRelDTO> listItemConfiguracao = null;
	    	if(eventoDto != null){
	    		listItemConfiguracao = eventoDto.getLstItemConfiguracao();
	    	}
	    	if (listItemConfiguracao == null) {
	    		listItemConfiguracao = new ArrayList<EventoItemConfigRelDTO>();
	    	}
	    	//Busca Itens de Configuração relacionados ao grupo
	    	List<EventoGrupoDTO> lstGrupo = eventoDto.getLstGrupo();
	    	GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = new GrupoItemConfiguracaoDTO();
	    	for (EventoGrupoDTO eventoGrupoDTO : lstGrupo) {
				Integer idGrupo = eventoGrupoDTO.getIdGrupo();
				ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
				grupoItemConfiguracaoDTO.setIdGrupoItemConfiguracao(idGrupo);
				Collection<ItemConfiguracaoDTO> lstItemConfigGrupo = itemConfiguracaoDao.listByGrupo(grupoItemConfiguracaoDTO, null, null);
				for (ItemConfiguracaoDTO itemConfiguracaoDTO : lstItemConfigGrupo) {
					EventoItemConfigRelDTO configRelDTO = new EventoItemConfigRelDTO();
					configRelDTO.setIdItemConfiguracao(itemConfiguracaoDTO.getIdItemConfiguracao());
					// Verifica se o Item de Configuração consta na lista
					if (!listItemConfiguracao.contains(configRelDTO)) {
						listItemConfiguracao.add(configRelDTO);
					}
				}
			}
	    	
	    	if (listItemConfiguracao.size() > 0) {
			    for (EventoItemConfigRelDTO itemConfiguracaoRel : listItemConfiguracao) {
					new Thread(new ThreadDisparaEvento(itemConfiguracaoRel.getIdItemConfiguracao(), itemConfigEventoDto.getIdBaseItemConfiguracao(), itemConfigEventoDto.getIdEvento(),
						itemConfigEventoDto.getTipoExecucao(), itemConfigEventoDto.getLinhaComando(), itemConfigEventoDto.getLinhaComandoLinux())).start();
			    }
			}
	    }
    	
    }
    
    /**
     * Método que deleta os relacionamentos com Grupo de item configuração e Item configuração com o evento
     * 
     * @param eventoGrupoDao DAO de EventoGrupo
     * @param eventoItemConfigRelDao DAO de EventoItemConfigRel
     * @param idEvento
     * @throws Exception
     */
    private void deleteObsoleteRelationship(EventoGrupoDao eventoGrupoDao, EventoItemConfigRelDao eventoItemConfigRelDao, Integer idEvento) throws Exception {
    	eventoGrupoDao.deleteByIdEvento(idEvento);
    	eventoItemConfigRelDao.deleteByIdEvento(idEvento);
    }

	@Override
	public Collection<CaracteristicaDTO> pegarNetworksItemConfiguracao(
			Integer idItemConfiguracao) throws ServiceException, RemoteException {
		try {
		    EventoItemConfigDao dao = (EventoItemConfigDao) getDao();
		    return dao.pegarNetworksItemConfiguracao(idItemConfiguracao);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}

	@Override
	public String pegarSistemaOperacionalItemConfiguracao(
			Integer idItemConfiguracao) throws ServiceException,
			RemoteException {
		try {
		    EventoItemConfigDao dao = (EventoItemConfigDao) getDao();
		    return dao.pegarSistemaOperacionalItemConfiguracao(idItemConfiguracao);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}
	
}
