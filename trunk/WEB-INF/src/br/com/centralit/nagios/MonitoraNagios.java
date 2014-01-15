package br.com.centralit.nagios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitoramentoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RecursoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoEvtMonDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.EmpregadoServiceEjb;
import br.com.centralit.citcorpore.negocio.EventoMonitoramentoServiceEjb;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoServiceEjb;
import br.com.centralit.citcorpore.negocio.RecursoServiceEjb;
import br.com.centralit.citcorpore.negocio.ServicoContratoServiceEjb;
import br.com.centralit.citcorpore.negocio.ServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoEvtMonServiceEjb;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.UsuarioServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class MonitoraNagios extends Thread {
	public static Semaphore performanceDataSemaphore = new Semaphore(1);
	public static PerformanceData performanceData;
	public static JavNag javaNagios;
	long lastPerformanceDump = 0;
	public static HashMap mapaStatusHosts = new HashMap();
	public static HashMap mapaStatusHosts_RegIncident = new HashMap();
	
	@Override
	public void run() {
		String habilitaMonitoramentoNagios = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.HABILITA_MONITORAMENTO_NAGIOS, "N");
		if (habilitaMonitoramentoNagios != null && habilitaMonitoramentoNagios.trim().equalsIgnoreCase("S")) {
			performanceData = new PerformanceData();
			while(true){
				performanceDataSemaphore.acquireUninterruptibly();
				try{
					System.out.println("CITSMART - Carregando informacoes do Nagios...");
					javaNagios = new JavNag();
					String pathNagiosMon = "";
					try {
						pathNagiosMon = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PATH_NAGIOS_STATUS, "C:\\jboss\\jboss\\server\\default\\deploy\\status.dat.txt");
					} catch (Exception e1) {
						//e1.printStackTrace();
						pathNagiosMon = "C:\\jboss\\jboss\\server\\default\\deploy\\status.dat.txt";
					}
					try {
						javaNagios.loadNagiosData(pathNagiosMon, 3, "Nagios");
					} catch (Exception e) {
						//e.printStackTrace(); para evitar lixo no log
					}
					try {
						collectPerformanceData(javaNagios);
					} catch (Exception e) {
						//e.printStackTrace(); para evitar lixo no log
					}
				}
				finally{
					performanceDataSemaphore.release();
				}	
				super.run();
				//System.out.println("CITSMART - Carregando informacoes do Nagios... Terminado.");
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
				}			
			}
		}
	}
	public void generateHashLastState(JavNag javNag) throws Exception{
		for(Host currentHost : javNag.getListOfHosts())	{
			List<ParameterEntry> hostEntries = currentHost.getParameters();
			String current_state = currentHost.getParameter("current_state");
			if (current_state.equalsIgnoreCase("1")){
				current_state = "DOWN";
			}else if (current_state.equalsIgnoreCase("0")){
				current_state = "UP";
			}else{
				current_state = "PENDING";
			}
			String current_attempt_str = currentHost.getParameter("current_attempt");
			String max_attempts_str = currentHost.getParameter("max_attempts");
			String last_check_str = currentHost.getParameter("last_check");
			
			int current_attempt = 0;
			try{
				current_attempt = Integer.parseInt(current_attempt_str);
			}catch(Exception e){}
			int max_attempts = 0;
			try{
				max_attempts = Integer.parseInt(max_attempts_str);
			}catch(Exception e){}
			int last_check = 0;
			try{
				last_check = Integer.parseInt(last_check_str);
			}catch(Exception e){}	
			
			//if (current_attempt > 0 && current_attempt >= max_attempts && (current_state.equalsIgnoreCase("DOWN") || current_state.equalsIgnoreCase("CRITICAL") || current_state.equalsIgnoreCase("WARNING"))){
			if ((current_state.equalsIgnoreCase("DOWN") || current_state.equalsIgnoreCase("CRITICAL") || current_state.equalsIgnoreCase("WARNING"))){
				Integer lastRegIncidente = (Integer) mapaStatusHosts_RegIncident.get(currentHost.getHostName());
				if (lastRegIncidente == null || lastRegIncidente.intValue() != last_check){
					RecursoServiceEjb recursoService = new RecursoServiceEjb();
					Collection col = recursoService.findByHostName(currentHost.getHostName());
					if (col != null){
						for (Iterator it = col.iterator(); it.hasNext();){
							RecursoDTO recursoDTO = (RecursoDTO) it.next();
							if (recursoDTO.getServiceName() == null || recursoDTO.getServiceName().trim().equalsIgnoreCase("")){
								try{
									generateIncidente(recursoDTO, currentHost.getHostName(), current_state, last_check, hostEntries);
								}catch(Exception e){
									e.printStackTrace();
								}
							}
							recursoDTO = null;
						}
					}
				}
			}	
			current_state = current_state + "#" + last_check_str;
			mapaStatusHosts.put(currentHost.getHostName(), current_state);			
		}		
	}
	public void generateIncidente(RecursoDTO recursoDTO, String hostName, String current_state, int last_check, List<ParameterEntry> hostEntries) throws ServiceException, LogicException{
		if (recursoDTO == null){
			return;
		}
		if (recursoDTO.getStatusAberturaInc() == null || recursoDTO.getStatusAberturaInc().trim().equalsIgnoreCase("")){
			return;
		}
		if (recursoDTO.getStatusAberturaInc().indexOf(current_state) > -1){ //Se o status em questao estiver dentro da lista informada no cadastro. o padrao eh DOWN,CRITICAL,...
			SolicitacaoServicoEvtMonServiceEjb solicitacaoServicoEvtMonServiceEjb = new SolicitacaoServicoEvtMonServiceEjb();
			Collection colDados = null;
			try {
				colDados = solicitacaoServicoEvtMonServiceEjb.findByIdRecursoAndSolicitacaoAberta(recursoDTO.getIdRecurso());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (colDados == null || colDados.size() == 0){
				ServicoContratoServiceEjb servicoContratoService = new ServicoContratoServiceEjb();
				EmpregadoServiceEjb empregadoService = new EmpregadoServiceEjb();
				EventoMonitoramentoServiceEjb eventoMonitoramentoService = new EventoMonitoramentoServiceEjb();
				ServicoServiceEjb servicoService = new ServicoServiceEjb();
				UsuarioServiceEjb usuarioService = new UsuarioServiceEjb();
				ItemConfiguracaoServiceEjb itemConfiguracaoServiceEjb = new ItemConfiguracaoServiceEjb();
				SolicitacaoServicoServiceEjb solicitacaoServicoService = new SolicitacaoServicoServiceEjb();
				
				ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
				servicoContratoDTO.setIdServicoContrato(recursoDTO.getIdServicoContrato());
				try{
					servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);
				}catch(Exception e){
					e.printStackTrace();
					servicoContratoDTO = null;
				}
				
				String nameEventSystem = "";
				if (recursoDTO.getIdEventoMonitoramento() != null && recursoDTO.getIdEventoMonitoramento().intValue() > 0){
					EventoMonitoramentoDTO eventoMonitoramentoDTO = new EventoMonitoramentoDTO();
					eventoMonitoramentoDTO.setIdEventoMonitoramento(recursoDTO.getIdEventoMonitoramento());
					eventoMonitoramentoDTO = (EventoMonitoramentoDTO) eventoMonitoramentoService.restore(eventoMonitoramentoDTO);
					if (eventoMonitoramentoDTO != null){
						nameEventSystem = eventoMonitoramentoDTO.getNomeEvento();
					}
				}				
				String descr = recursoDTO.getDescricaoAbertInc();
				
				descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{dadosmonitoramento\\}", getInfoParameters(hostEntries));
				descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{hostname\\}", hostName);
				descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{eventname\\}", nameEventSystem);
				descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{currentstate\\}", current_state);
				descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{dataatual\\}", UtilDatas.dateToSTR(UtilDatas.getDataAtual()));
				descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{datahoraatual\\}", UtilDatas.dateToSTR(UtilDatas.getDataHoraAtual()) + " - " + UtilDatas.formatHoraFormatadaHHMMSSStr(UtilDatas.getDataHoraAtual()));
				
				SolicitacaoServicoDTO solicitacaoServicoDTO = new SolicitacaoServicoDTO();
				solicitacaoServicoDTO.setIdSolicitante(recursoDTO.getIdSolicitante());
				solicitacaoServicoDTO.setDescricao(descr);
				solicitacaoServicoDTO.setIdOrigem(recursoDTO.getIdOrigem());
				if(servicoContratoDTO != null){
					solicitacaoServicoDTO.setIdContrato(servicoContratoDTO.getIdContrato());
				}
				solicitacaoServicoDTO.setIdServicoContrato(recursoDTO.getIdServicoContrato());
				solicitacaoServicoDTO.setIdGrupoAtual(recursoDTO.getIdGrupo());
				solicitacaoServicoDTO.setSituacao(Enumerados.SituacaoSolicitacaoServico.EmAndamento.name());
				solicitacaoServicoDTO.setEmailcontato(recursoDTO.getEmailAberturaInc());
				solicitacaoServicoDTO.setUrgencia(recursoDTO.getUrgencia());
				solicitacaoServicoDTO.setImpacto(recursoDTO.getImpacto());
				
				EmpregadoDTO empregadoDto = new EmpregadoDTO();
				empregadoDto.setIdEmpregado(recursoDTO.getIdSolicitante());
				try{
					empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
				}catch(Exception e){
					empregadoDto = null;
				}
				UsuarioDTO usuario = new UsuarioDTO();
				if (empregadoDto != null){
					solicitacaoServicoDTO.setNomecontato(empregadoDto.getNome());
					solicitacaoServicoDTO.setTelefonecontato(empregadoDto.getTelefone());
					solicitacaoServicoDTO.setRamal(empregadoDto.getRamal());
					solicitacaoServicoDTO.setIdUnidade(empregadoDto.getIdUnidade());
					
					usuario.setIdEmpregado(empregadoDto.getIdEmpregado());
					try {
						usuario = usuarioService.restoreByIdEmpregado(empregadoDto.getIdEmpregado());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
				solicitacaoServicoDTO.setObservacao("Automatically generate by monitoring system.\n" + nameEventSystem + "\nState: " + current_state);
				
				ServicoDTO servicoDTO = new ServicoDTO();
				if(servicoContratoDTO != null){
					servicoDTO.setIdServico(servicoContratoDTO.getIdServico());
				}
				servicoDTO = (ServicoDTO) servicoService.restore(servicoDTO);
				
				if(servicoContratoDTO != null){
					solicitacaoServicoDTO.setIdServico(servicoContratoDTO.getIdServico());
					solicitacaoServicoDTO.setIdTipoDemandaServico(servicoDTO.getIdTipoDemandaServico());
				}
				
				solicitacaoServicoDTO.setUsuarioDto(usuario);
				solicitacaoServicoDTO.setRegistradoPor(usuario.getNomeUsuario());	
				
				solicitacaoServicoDTO.setEnviaEmailCriacao("S");
				solicitacaoServicoDTO.setEnviaEmailAcoes("S");
				solicitacaoServicoDTO.setEnviaEmailFinalizacao("S");
				
				if (recursoDTO.getIdItemConfiguracao() != null){
					List<ItemConfiguracaoDTO> colItensIC = new ArrayList<ItemConfiguracaoDTO>();
					ItemConfiguracaoDTO itemConfiguracaoDTO = null;
					try {
						itemConfiguracaoDTO = itemConfiguracaoServiceEjb.restoreByIdItemConfiguracao(recursoDTO.getIdItemConfiguracao());
					} catch (Exception e) {
						e.printStackTrace();
						itemConfiguracaoDTO = null;
					}
					if (itemConfiguracaoDTO != null){
						colItensIC.add(itemConfiguracaoDTO);
						solicitacaoServicoDTO.setColItensICSerialize(colItensIC);
					}
				}	
				
				SolicitacaoServicoEvtMonDTO solicitacaoServicoEvtMonDTO = new SolicitacaoServicoEvtMonDTO();
				solicitacaoServicoEvtMonDTO.setIdEventoMonitoramento(recursoDTO.getIdEventoMonitoramento());
				solicitacaoServicoEvtMonDTO.setIdRecurso(recursoDTO.getIdRecurso());
				solicitacaoServicoEvtMonDTO.setNomeHost(recursoDTO.getHostName());
				solicitacaoServicoEvtMonDTO.setNomeService(recursoDTO.getServiceName());
				solicitacaoServicoEvtMonDTO.setInfoAdd(getInfoParameters(hostEntries));
				List<SolicitacaoServicoEvtMonDTO> colSolicitacaoServicoEvtMon = new ArrayList<SolicitacaoServicoEvtMonDTO>();
				colSolicitacaoServicoEvtMon.add(solicitacaoServicoEvtMonDTO);
				
				solicitacaoServicoDTO.setColSolicitacaoServicoEvtMon(colSolicitacaoServicoEvtMon);
				
				solicitacaoServicoDTO.setRegistroexecucao(solicitacaoServicoDTO.getObservacao());
				
				solicitacaoServicoDTO = (SolicitacaoServicoDTO) solicitacaoServicoService.create(solicitacaoServicoDTO);
				mapaStatusHosts_RegIncident.put(hostName, last_check);		
				solicitacaoServicoDTO = null;
			}
		}
		if (recursoDTO.getStatusAlerta().indexOf(current_state) > -1){ //Se o status em questao estiver dentro da lista informada no cadastro. o padrao eh DOWN,CRITICAL,...
			EventoMonitoramentoServiceEjb eventoMonitoramentoService = new EventoMonitoramentoServiceEjb();
			String nameEventSystem = "";
			if (recursoDTO.getIdEventoMonitoramento() != null && recursoDTO.getIdEventoMonitoramento().intValue() > 0){
				EventoMonitoramentoDTO eventoMonitoramentoDTO = new EventoMonitoramentoDTO();
				eventoMonitoramentoDTO.setIdEventoMonitoramento(recursoDTO.getIdEventoMonitoramento());
				eventoMonitoramentoDTO = (EventoMonitoramentoDTO) eventoMonitoramentoService.restore(eventoMonitoramentoDTO);
				if (eventoMonitoramentoDTO != null){
					nameEventSystem = eventoMonitoramentoDTO.getNomeEvento();
				}
			}
			
			String descr = recursoDTO.getDescricaoAbertInc();
			descr = UtilStrings.nullToVazio(descr).replaceAll("\n", "<br>");
			descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{dadosmonitoramento\\}", getInfoParameters(hostEntries));
			descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{hostname\\}", hostName);
			descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{eventname\\}", nameEventSystem);
			descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{currentstate\\}", current_state);
			descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{dataatual\\}", UtilDatas.dateToSTR(UtilDatas.getDataAtual()));
			descr = UtilStrings.nullToVazio(descr).replaceAll("\\$\\{datahoraatual\\}", UtilDatas.dateToSTR(UtilDatas.getDataHoraAtual()) + " - " + UtilDatas.formatHoraFormatadaHHMMSSStr(UtilDatas.getDataHoraAtual()));
			
			String remetente = null;
			try {
				remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (remetente == null){
				remetente = "citsmart@citsmart.com.br";
			}
			String mailsAlerta = UtilStrings.nullToVazio(recursoDTO.getEmailsAlerta()).trim();
			String[] mails = mailsAlerta.split(";");
			if (mails != null){
				for (int i = 0; i < mails.length; i++){
					String mailTo = UtilStrings.nullToVazio(mails[i]).trim();
					MensagemEmail mensagemEmail = new MensagemEmail(mailTo, null, null, remetente, "CITSMART - Monitoring Alert: " + hostName + " - " + current_state + " -->> Event: " + nameEventSystem, descr);
					try {
						mensagemEmail.envia(mailTo, null, remetente);
						System.out.println("CITSMART - Monitoring Alert: " + current_state + " Event: " + nameEventSystem + " -->> Send mail: " + mailTo);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("CITSMART - Monitoring Alert: " + current_state + " Event: " + nameEventSystem + " -->> PROBLEM Send mail: " + mailTo);
					}
				}
			}
		}
		System.gc();
	}
	public String getInfoParameters(List<ParameterEntry> hostEntries){
		if (hostEntries == null){
			return "";
		}
		String ret = "";
		for (Iterator it = hostEntries.iterator(); it.hasNext();){
			ParameterEntry parameterEntry = (ParameterEntry)it.next();
			ret = ret + parameterEntry.getParameterName() + " : " + parameterEntry.getParameterValue() + "<br>\n";
		}
		return ret;
	}
	public void collectPerformanceData(JavNag javNag) throws Exception{
		for(Host currentHost : javNag.getListOfHosts())	{
			String hostPerformanceData = currentHost.getParameter("performance_data");
			String lastHostCheck = currentHost.getParameter("last_check");
			if (hostPerformanceData != null && hostPerformanceData.trim().equals("") == false)
				performanceData.add(currentHost.getHostName(), hostPerformanceData, lastHostCheck);

			for(Service currentService : currentHost.getServices())	{
				String servicePerformanceData = currentService.getParameter("performance_data");
				String lastServiceCheck = currentService.getParameter("last_check");
				if (servicePerformanceData != null && servicePerformanceData.trim().equals("") == false)
					performanceData.add(currentHost.getHostName() + " | " + currentService.getServiceName(), servicePerformanceData, lastServiceCheck);
			}
		}
		generateHashLastState(javNag);

		if ((System.currentTimeMillis() - lastPerformanceDump)  > 1800000){
			dumpPerformanceData(javNag);

			lastPerformanceDump = System.currentTimeMillis();
		}
	}
	
	public void dumpPerformanceData(JavNag javNag) throws Exception{
		processaDump(javNag);
	}
	public void processaDump(JavNag javNag){
		for(Host currentHost : javNag.getListOfHosts()){
			String hostPerformanceData = currentHost.getParameter("performance_data");
			String lastHostCheck = currentHost.getParameter("last_check");
			String current_state = currentHost.getParameter("current_state");
			current_state = UtilStrings.nullToVazio(current_state);
			if (current_state.equalsIgnoreCase("1")){
				current_state = "DOWN";
			}else if (current_state.equalsIgnoreCase("0")){
				current_state = "UP";
			}else{
				current_state = "PENDING";
			}
			//System.out.println("Host: " + currentHost.getHostName() + "   -> " + hostPerformanceData + " -> " + lastHostCheck + " -> " + current_state);

			for(Service currentService : currentHost.getServices()){
				String servicePerformanceData = currentService.getParameter("performance_data");
				String lastServiceCheck = currentService.getParameter("last_check");
				//System.out.println("		Serviço: " + currentService.getServiceName() + "   -> " + servicePerformanceData + " -> " + lastServiceCheck);
			}
		}		
	}
	public static void main(String[] args) {
		MonitoraNagios monitoraNagios = new MonitoraNagios();
		monitoraNagios.start();
	}
}
