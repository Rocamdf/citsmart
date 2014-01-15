package br.com.centralit.citcorpore.quartz.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.EventoGrupoDTO;
import br.com.centralit.citcorpore.bean.EventoItemConfigRelDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfigEventoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.componenteMaquina.ThreadDisparaEvento;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.centralit.citcorpore.negocio.EventoGrupoService;
import br.com.centralit.citcorpore.negocio.EventoItemConfigRelService;
import br.com.centralit.citcorpore.negocio.ItemConfigEventoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

public class DisparaEvento implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			EventoGrupoService eventoGrupoService = (EventoGrupoService) ServiceLocator.getInstance().getService(EventoGrupoService.class, null);
			EventoItemConfigRelService eventoItemConfigRelService = (EventoItemConfigRelService) ServiceLocator.getInstance().getService(EventoItemConfigRelService.class, null);
			ItemConfigEventoService itemConfigEventoService = (ItemConfigEventoService) ServiceLocator.getInstance().getService(ItemConfigEventoService.class, null);
			Collection<ItemConfigEventoDTO> colEventos = itemConfigEventoService.verificarDataHoraEvento();
			for (ItemConfigEventoDTO itemCfgEventoDto : colEventos) {
				
				Collection<EventoGrupoDTO> lstGrupo = eventoGrupoService.listByEvento(itemCfgEventoDto.getIdEvento());
				itemCfgEventoDto.getIdEvento();
				List<EventoItemConfigRelDTO> listItemConfiguracao = (List<EventoItemConfigRelDTO>) eventoItemConfigRelService.listByEvento(itemCfgEventoDto.getIdEvento());
				
				if (listItemConfiguracao == null) {
					listItemConfiguracao =  new ArrayList<EventoItemConfigRelDTO>();
				}
		    	//Busca Itens de Configuração relacionados ao grupo
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
				
				for (EventoItemConfigRelDTO eventoItemConfigRel : listItemConfiguracao) {
				    new Thread(new ThreadDisparaEvento(eventoItemConfigRel.getIdItemConfiguracao(), itemCfgEventoDto.getIdBaseItemConfiguracao(), itemCfgEventoDto.getIdEvento(),
					    itemCfgEventoDto.getTipoExecucao(), itemCfgEventoDto.getLinhaComando(), itemCfgEventoDto.getLinhaComandoLinux())).start();
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
