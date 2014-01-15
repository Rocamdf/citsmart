package br.com.centralit.citsmart.rest.operation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.ComplemInfSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TemplateSolicitacaoServicoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.centralit.citsmart.rest.schema.CtNotification;
import br.com.centralit.citsmart.rest.schema.CtNotificationDetail;
import br.com.centralit.citsmart.rest.schema.CtNotificationFeedback;
import br.com.centralit.citsmart.rest.schema.CtNotificationFeedbackResp;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetById;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetByIdResp;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUser;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUserResp;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetReasons;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetReasonsResp;
import br.com.centralit.citsmart.rest.schema.CtNotificationNew;
import br.com.centralit.citsmart.rest.schema.CtNotificationNewResp;
import br.com.centralit.citsmart.rest.schema.CtReason;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
import br.com.centralit.citsmart.rest.util.RestUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;


public class RestMobile extends RestOperation {
	
	private static final int NO_PRAZO = 0;
	private static final int NO_LIMITE = 1;
	private static final int ATRASADA = 2;
	private static final int SUSPENSA = 3;
		
	protected void determinaTimeFlag(SolicitacaoServicoDTO solicitacaoDto, CtNotification notification) {
		 if (solicitacaoDto.getAtrasada() && !solicitacaoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.name())) {
			 notification.setTimeFlag(ATRASADA);
		 }else{
			 if (solicitacaoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.name())) {
				 notification.setTimeFlag(SUSPENSA);
			 }else if (solicitacaoDto.getFalta1Hora()) {
				 notification.setTimeFlag(NO_LIMITE);
			 }else{
				 notification.setTimeFlag(NO_PRAZO);
			 }
		 }
	}
	
	protected SolicitacaoServicoDTO getSolicitacaoServicoByTarefa(RestSessionDTO restSessionDto, TarefaFluxoDTO tarefaDto) throws Exception {
		 SolicitacaoServicoDTO solicitacaoDto = null;
		 Collection<TarefaFluxoDTO> listTarefas = new ArrayList();
		 listTarefas.add(tarefaDto);
		 TipoSolicitacaoServico[] tipos = new TipoSolicitacaoServico[] {TipoSolicitacaoServico.Compra, TipoSolicitacaoServico.Incidente, TipoSolicitacaoServico.Requisicao, TipoSolicitacaoServico.RH, TipoSolicitacaoServico.Viagem};
		 SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, RestUtil.getUsuarioSistema(restSessionDto));
		 Collection<SolicitacaoServicoDTO> colSolicitacao = solicitacaoServicoService.listByTarefas(listTarefas, tipos);
		 if (colSolicitacao != null && !colSolicitacao.isEmpty())
			 solicitacaoDto = (SolicitacaoServicoDTO) ((List) colSolicitacao).get(0);
		 return solicitacaoDto;
	}
	
	protected boolean emAprovacao(RestSessionDTO restSessionDto, ItemTrabalhoFluxoDTO itemTrabalhoDto) throws Exception {
		 boolean result = false;
		 ElementoFluxoDTO elementoDto = itemTrabalhoDto.getElementoFluxoDto();
		 if (itemTrabalhoDto.getSituacao().equals(SituacaoItemTrabalho.Disponivel.name()) && elementoDto.getTemplate() != null && !elementoDto.getTemplate().trim().equals("")) {
			TemplateSolicitacaoServicoService templateSolicitacaoServicoService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance().getService(TemplateSolicitacaoServicoService.class, RestUtil.getUsuarioSistema(restSessionDto));
			TemplateSolicitacaoServicoDTO templateDto = templateSolicitacaoServicoService.findByIdentificacao(elementoDto.getTemplate().trim());
			if (templateDto != null && templateDto.getAprovacao().equals("S"))
				result = true;
		 }
		 return result;
	}
	
	protected int determinaTipoSolicitacao(SolicitacaoServicoDTO solicitacaoDto) {
		 if (solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.Compra))
			return 1;
		 if (solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.Viagem))
			return 2;
		 if (solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.RH))
			return 3;
		 if (solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.Incidente))
			return 4;
		 if (solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.Requisicao))
			return 5;
		 return 0;
	}
	
	protected void verificaImpactoUrgencia(RestSessionDTO restSessionDto, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0)
			return;

		if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdContrato() == null)
			return;

		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());

		if (servicoContratoDto != null) {
			AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
			AcordoNivelServicoDTO acordoNivelServicoDto = acordoNivelServicoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
			if (acordoNivelServicoDto == null) {
				//Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato, entao busca um acordo geral que esteja vinculado ao servicocontrato.
				AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
				AcordoServicoContratoDTO acordoServicoContratoDTO = acordoServicoContratoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
				if (acordoServicoContratoDTO == null)
					throw new LogicException("Acordo de nível de serviço não definido");
				//Apos achar a vinculacao do acordo com o servicocontrato, entao faz um restore do acordo de nivel de servico.
				acordoNivelServicoDto = new AcordoNivelServicoDTO();
				acordoNivelServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
				acordoNivelServicoDto = (AcordoNivelServicoDTO) new AcordoNivelServicoDao().restore(acordoNivelServicoDto);
				if (acordoNivelServicoDto == null)
					throw new LogicException("Acordo de nível de serviço não definido");
			}
			if (acordoNivelServicoDto.getImpacto() != null) {
				solicitacaoServicoDto.setImpacto(acordoNivelServicoDto.getImpacto());
			} else {
				solicitacaoServicoDto.setImpacto("B");
			}
			if (acordoNivelServicoDto.getUrgencia() != null) {
				solicitacaoServicoDto.setUrgencia(acordoNivelServicoDto.getUrgencia());
			} else {
				solicitacaoServicoDto.setUrgencia("B");
			}
		} else {
			solicitacaoServicoDto.setUrgencia("B");
			solicitacaoServicoDto.setImpacto("B");
		}
	}	
	
// ============================================================== Serviços ============================================================== 	
	protected CtMessageResp getByUser(RestSessionDTO restSessionDto, RestExecutionDTO restExecutionDto, RestOperationDTO restOperationDto, CtMessage message) throws JAXBException {
		 CtNotificationGetByUserResp resp = new CtNotificationGetByUserResp();
		 
		 if (restSessionDto.getUser() == null || restSessionDto.getUser().getLogin() == null || restSessionDto.getUser().getLogin().trim().equals("")) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Login do usuário não identificado"));
			 return resp;
		 }
		
		 CtNotificationGetByUser input = (CtNotificationGetByUser) message;
		 int tipo = input.getNotificationType();
		 
		 List<TipoSolicitacaoServico> tipos = new ArrayList();
		 if (tipo == 0 || tipo == 1)
			tipos.add(TipoSolicitacaoServico.Compra);
		 if (tipo == 0 || tipo == 2)
			tipos.add(TipoSolicitacaoServico.Viagem);
		 if (tipo == 0 || tipo == 3)
			tipos.add(TipoSolicitacaoServico.RH);
		 if (tipo == 0 || tipo == 4)
			tipos.add(TipoSolicitacaoServico.Incidente);
		 if (tipo == 0 || tipo == 5)
			tipos.add(TipoSolicitacaoServico.Requisicao);
		 
		 if (tipos.size() == 0) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Pelo menos um tipo de solicitação deve ser selecionado"));
			 return resp;
		 }
		 
		 String aprovacao = "N";
		 if (input.getOnlyApproval() == 1)
			 aprovacao = "S";

		 TipoSolicitacaoServico[] arrayTipos = new TipoSolicitacaoServico[tipos.size()];
		 int i = 0;
		 for (TipoSolicitacaoServico tipoSolicitacao : tipos) {
			arrayTipos[i] = tipoSolicitacao;
			i++;
		 }
		 
		 try {
			List<TarefaFluxoDTO> tarefas = (RestUtil.getExecucaoSolicitacaoService(restSessionDto).recuperaTarefas(restSessionDto.getUser().getLogin(), arrayTipos, aprovacao));
			if (tarefas != null) {
				int qtde = 0;
				for (TarefaFluxoDTO tarefaDto : tarefas) {
					 if (!tarefaDto.getExecutar().equalsIgnoreCase("S"))
						continue;
					 CtNotification notification = new CtNotification();
					 SolicitacaoServicoDTO solicitacaoDto = ((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());
					 Integer id = solicitacaoDto.getIdSolicitacaoServico();
					 notification.setNumber(new BigInteger(""+id));
					 notification.setDate(UtilDatas.dateToSTR(solicitacaoDto.getDataHoraInicio()));
					 notification.setTaskId(new BigInteger(""+tarefaDto.getIdItemTrabalho()));
					 notification.setType(determinaTipoSolicitacao(solicitacaoDto));
					 notification.setTask(tarefaDto.getElementoFluxoDto().getDocumentacao());
					 notification.setEndSLA(solicitacaoDto.getDataHoraLimiteStr());
					 notification.setService(solicitacaoDto.getServico());
					 determinaTimeFlag(solicitacaoDto, notification);  	
					 resp.getNotifications().add(notification);
					 
					 notification.setWaiting(0);
					 try {
						 if (emAprovacao(restSessionDto, tarefaDto))
						 	notification.setWaiting(1);
					 } catch (Exception e) {
						e.printStackTrace();
						resp.setError(RestOperationUtil.buildError(e));	
						return resp;
					 }
					 
					 qtde ++;
				}
				resp.setAmount(qtde);
			}else
				resp.setAmount(0);

		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
		 } 

		 return resp;		
	}
	
	protected CtMessageResp getById(RestSessionDTO restSessionDto, RestExecutionDTO restExecutionDto, RestOperationDTO restOperationDto, CtMessage message) throws JAXBException {
		 CtNotificationGetByIdResp resp = new CtNotificationGetByIdResp();
		 
		 CtNotificationGetById input = (CtNotificationGetById) message;
		 if (input.getTaskId() == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Id da tarefa não informado"));
			 return resp;
		 }	
		 
		 TarefaFluxoDTO tarefaDto = null;
		 ElementoFluxoDTO elementoDto = null;
		 ItemTrabalho itemTrabalho = null;
		 try {
			itemTrabalho = ItemTrabalho.getItemTrabalho(new Integer(""+input.getTaskId()));
			if (itemTrabalho != null) {
				tarefaDto = new TarefaFluxoDTO();
				Reflexao.copyPropertyValues(itemTrabalho.getItemTrabalhoDto(), tarefaDto);
				elementoDto = itemTrabalho.getElementoFluxoDto();
				tarefaDto.setElementoFluxoDto(elementoDto);
			}
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
			 return resp;
		 }
		 
		 if (tarefaDto == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Tarefa não encontrada"));
			 return resp;
		 }	
		 
		 SolicitacaoServicoDTO solicitacaoDto = null;
		 try {
			 solicitacaoDto = getSolicitacaoServicoByTarefa(restSessionDto,tarefaDto);
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
			 return resp;				
		 }
		 
		 if (solicitacaoDto == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Solicitacação de serviço não encontrada"));
			 return resp;
		 }
		 
		 CtNotificationDetail notification = new CtNotificationDetail();
		 
		 notification.setWaiting(0);
		 try {
			 if (emAprovacao(restSessionDto, tarefaDto))
			 	notification.setWaiting(1);
		 } catch (Exception e) {
			e.printStackTrace();
			resp.setError(RestOperationUtil.buildError(e));	
			return resp;
		 }
		 
		 Integer id = solicitacaoDto.getIdSolicitacaoServico();
		 notification.setNumber(new BigInteger(""+id));
		 notification.setDate(UtilDatas.dateToSTR(solicitacaoDto.getDataHoraInicio()));
		 notification.setTaskId(new BigInteger(""+tarefaDto.getIdItemTrabalho()));
		 notification.setType(determinaTipoSolicitacao(solicitacaoDto));
		 
		 String descricao = null;
		 try {
			 SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, RestUtil.getUsuarioSistema(restSessionDto));
			 ComplemInfSolicitacaoServicoService complemInfSolicitacaoServicoService = solicitacaoServicoService.getInformacoesComplementaresService(itemTrabalho);
			 if (complemInfSolicitacaoServicoService != null)
				 descricao = complemInfSolicitacaoServicoService.getInformacoesComplementaresFmtTexto(solicitacaoDto, itemTrabalho);
		 } catch (Exception e) {
		 }

		 if (descricao == null || descricao.trim().equals(""))
			 descricao = solicitacaoDto.getDescricaoSemFormatacao();
		 
		 notification.setDescription(descricao);
		 notification.setStatus(solicitacaoDto.getDescrSituacao());
		 notification.setTask(elementoDto.getDocumentacao());
		 notification.setTaskStatus(tarefaDto.getDescrSituacao());
		 notification.setEndSLA(solicitacaoDto.getDataHoraLimiteStr());
		 notification.setService(solicitacaoDto.getServico());
		 determinaTimeFlag(solicitacaoDto, notification);  	
		 
		 resp.setNotification(notification);
		 String url = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.URL_Sistema, "").trim()+Constantes.getValue("CONTEXTO_APLICACAO");
		 resp.setSite(url.replaceAll("//", "/"));
		 return resp;
	}
	
	protected CtMessageResp feedback(RestSessionDTO restSessionDto, RestExecutionDTO restExecutionDto, RestOperationDTO restOperationDto, CtMessage message) throws JAXBException {
		 CtNotificationFeedbackResp resp = new CtNotificationFeedbackResp();
		 
		 CtNotificationFeedback input = (CtNotificationFeedback) message;
		 if (input.getTaskId() == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Id da tarefa não informado"));
			 return resp;
		 }	
		 
		 if (input.getFeedback() == 0 && input.getReasonId() == 0) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Justificativa não informada"));
			 return resp;
		 }
		 
		 TarefaFluxoDTO tarefaDto = null;
		 ElementoFluxoDTO elementoDto = null;
		 ItemTrabalho itemTrabalho = null;
		 try {
			itemTrabalho = ItemTrabalho.getItemTrabalho(new Integer(""+input.getTaskId()));
			if (itemTrabalho != null) {
				tarefaDto = new TarefaFluxoDTO();
				Reflexao.copyPropertyValues(itemTrabalho.getItemTrabalhoDto(), tarefaDto);
				elementoDto = itemTrabalho.getElementoFluxoDto();
				tarefaDto.setElementoFluxoDto(elementoDto);
			}
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
			 return resp;
		 }
		 
		 if (tarefaDto == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Tarefa não encontrada"));
			 return resp;
		 }	
		 
		 try {
			 if (!emAprovacao(restSessionDto, tarefaDto)) {
				 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "A tarefa já foi aprovada ou não está em aprovação"));
				 return resp;
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
			 return resp;				
		 }
		 
		 SolicitacaoServicoDTO solicitacaoDto = null;
		 try {
			 solicitacaoDto = getSolicitacaoServicoByTarefa(restSessionDto,tarefaDto);
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
			 return resp;				
		 }
		 
		 if (solicitacaoDto == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Solicitacação de serviço não encontrada"));
			 return resp;
		 }
		 
		 try{
			 String aprovacao = "N";
			 if (input.getFeedback() == 1)
				 aprovacao = "S";
			 
			 SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, RestUtil.getUsuarioSistema(restSessionDto));
			 ComplemInfSolicitacaoServicoService complemInfSolicitacaoServicoService = solicitacaoServicoService.getInformacoesComplementaresService(itemTrabalho);
			 if (complemInfSolicitacaoServicoService != null)
				 complemInfSolicitacaoServicoService.preparaSolicitacaoParaAprovacao(solicitacaoDto, itemTrabalho, aprovacao, input.getReasonId(), input.getComments());
			 
			 if (solicitacaoDto.getAcaoFluxo() != null) {
				 solicitacaoDto.setUsuarioDto(restSessionDto.getUser());
				 solicitacaoServicoService.updateInfo(solicitacaoDto);
			 }else{
				 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Não foi possível determinar a ação. Entre em contato com o suporte"));
				 return resp;
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
			 return resp;				
		 }
		 	
		 return resp;
	}
	
	protected CtMessageResp add(RestSessionDTO restSessionDto, RestExecutionDTO restExecutionDto, RestOperationDTO restOperationDto, CtMessage message) throws JAXBException {
		 CtNotificationNewResp resp = new CtNotificationNewResp();
		 
		 CtNotificationNew input = (CtNotificationNew) message;
		 if (input.getDescription() == null || input.getDescription().trim().equals("")) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Descrição da solicitação de serviço não informada"));
			 return resp;
		 }
		 if (restSessionDto.getUser() == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Solicitante da solicitação de serviço não informado"));
			 return resp;
		 }	

		 EmpregadoDTO empregadoDto = RestUtil.getEmpregadoByLogin(restSessionDto.getUser().getLogin());
		 if (empregadoDto == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "Dados do usuário não cadastrados"));
			 return resp;
		 }

		 String idContratoStr = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTRATO_PADRAO, "").trim();		
		 if (idContratoStr == null || idContratoStr.trim().equals("")) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "ID do contrato padrão não definido"));
			 return resp;
		 }
		 
		 String idServicoStr = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SERVICO_PADRAO_SOLICITACAO, "").trim();		
		 if (idServicoStr == null || idServicoStr.trim().equals("")) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "ID do serviço padrão não definido"));
			 return resp;
		 }
		 
		 ServicoDTO servicoDto = null;
		 try {
			 ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, RestUtil.getUsuarioSistema(restSessionDto));
			 servicoDto = new ServicoDTO();
			 servicoDto.setIdServico(new Integer(idServicoStr));
			 servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
		 } catch (Exception e1) {
			e1.printStackTrace();
		 }
		 if (servicoDto == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "Serviço não parametrizado"));
			 return resp;
		 } 

		 ServicoContratoDTO servicoContratoDto = null;
		 try {
			 ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, RestUtil.getUsuarioSistema(restSessionDto));
			 servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(new Integer(idContratoStr), new Integer(new Integer(idServicoStr)));
		 } catch (Exception e1) {
			e1.printStackTrace();
		 }
		 if (servicoContratoDto == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "Serviço não associado ao contrato"));
			 return resp;
		 } 
			

		 String idOrigemStr = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "");
		 if (idOrigemStr == null || idOrigemStr.trim().equals("")) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "ID da origem de atendimento não parametrizado"));
			 return resp;
		 }
		 
		 Integer idUnidade = restSessionDto.getDptoId();
		 if (idUnidade == null) {
			 HashMap<String, RestDomainDTO> mapParam = RestUtil.getRestParameterService(restSessionDto).findParameters(restSessionDto, restOperationDto);
			 if (mapParam == null || mapParam.isEmpty()) {
				 resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "Parâmetros da operação não cadastrados"));
				 return resp;
			 }
			 
			 String idUnidadeStr = RestUtil.getRestParameterService(restSessionDto).getParamValue(mapParam, RestEnum.PARAM_DEFAULT_DEPTO_ID);
			 if (idUnidadeStr == null)
				 resp.setError(RestOperationUtil.buildError(RestEnum.PARAM_ERROR, "ID da unidade não definido"));
			 idUnidade = new Integer(idUnidadeStr);
		 }
		 
		 SolicitacaoServicoDTO solicitacaoDto = new SolicitacaoServicoDTO();
		 solicitacaoDto.setUsuarioDto(restSessionDto.getUser());
		 solicitacaoDto.setIdContrato(new Integer(idContratoStr));
		 solicitacaoDto.setIdOrigem(new Integer(idOrigemStr));
		 solicitacaoDto.setIdServico(new Integer(idServicoStr));
		 solicitacaoDto.setIdSolicitante(empregadoDto.getIdEmpregado());
		 solicitacaoDto.setIdTipoDemandaServico(servicoDto.getIdTipoDemandaServico());
		 solicitacaoDto.setIdUnidade(idUnidade);
		 solicitacaoDto.setIdGrupoNivel1(servicoContratoDto.getIdGrupoNivel1());
		 solicitacaoDto.setIdGrupoAtual(servicoContratoDto.getIdGrupoExecutor());
		 solicitacaoDto.setSituacao(SituacaoSolicitacaoServico.EmAndamento.name());
		 try {
			 verificaImpactoUrgencia(restSessionDto, solicitacaoDto);
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
			 return resp;			
		 }		 
		 
		 solicitacaoDto.setNomecontato(empregadoDto.getNome());
		 solicitacaoDto.setEmailcontato(empregadoDto.getEmail());
		 solicitacaoDto.setTelefonecontato(empregadoDto.getTelefone());
		 solicitacaoDto.setRamal(empregadoDto.getRamal());		 
		 solicitacaoDto.setEnviaEmailCriacao("S");
		 solicitacaoDto.setEnviaEmailAcoes("N");
		 solicitacaoDto.setEnviaEmailFinalizacao("S");
		 
		 solicitacaoDto.setDescricao(input.getDescription());
		 solicitacaoDto.setRegistroexecucao("");
		 
		 try {
			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, RestUtil.getUsuarioSistema(restSessionDto));
			solicitacaoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.create(solicitacaoDto);
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
			 return resp;			
		 }
		 
		 resp.setNumber(new BigInteger(""+solicitacaoDto.getIdSolicitacaoServico()));
		 return resp;	
	}

	protected CtMessageResp getReasons(RestSessionDTO restSessionDto, RestExecutionDTO restExecutionDto, RestOperationDTO restOperationDto, CtMessage message) throws JAXBException {
		 CtNotificationGetReasonsResp resp = new CtNotificationGetReasonsResp();
		 
		 CtNotificationGetReasons input = (CtNotificationGetReasons) message;
		 if (input.getTaskId() == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Id da tarefa não informado"));
			 return resp;
		 }	
		 
		 TarefaFluxoDTO tarefaDto = null;
		 ElementoFluxoDTO elementoDto = null;
		 try {
			ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(new Integer(""+input.getTaskId()));
			if (itemTrabalho != null) {
				tarefaDto = new TarefaFluxoDTO();
				Reflexao.copyPropertyValues(itemTrabalho.getItemTrabalhoDto(), tarefaDto);
				elementoDto = itemTrabalho.getElementoFluxoDto();
				tarefaDto.setElementoFluxoDto(elementoDto);
			}
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
			 return resp;
		 }
		 
		 if (tarefaDto == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Tarefa não encontrada"));
			 return resp;
		 }	
		 
		 SolicitacaoServicoDTO solicitacaoDto = null;
		 try {
			 solicitacaoDto = getSolicitacaoServicoByTarefa(restSessionDto,tarefaDto);
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
			 return resp;				
		 }
		 
		 if (solicitacaoDto == null) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Solicitacação de serviço não encontrada"));
			 return resp;
		 }
		 
		 try{
			 if (!solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.Incidente) && !solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.Requisicao) && !solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.Viagem)) {
				 Collection<JustificativaParecerDTO> colJustificativas = null;
				 JustificativaParecerService justificativaParecerService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, RestUtil.getUsuarioSistema(restSessionDto));
				 if (solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.Compra)) {
					 if (UtilStrings.nullToVazio(elementoDto.getTemplate()).toUpperCase().indexOf("APROVACAO") >= 0)
						 colJustificativas = justificativaParecerService.listAplicaveisCotacao();
					 else if (UtilStrings.nullToVazio(elementoDto.getTemplate()).toUpperCase().indexOf("AUTORIZACAO") >= 0)
						 colJustificativas = justificativaParecerService.listAplicaveisRequisicao();
				 }
				 if (solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.Viagem)) {
					 colJustificativas = justificativaParecerService.list();
				 }
				 if (solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.RH)) {
					 colJustificativas = justificativaParecerService.list();
				 }
				 if (colJustificativas != null) {
					for (JustificativaParecerDTO justificativaDto : colJustificativas) {
						CtReason justificativa = new CtReason();
						justificativa.setId(justificativaDto.getIdJustificativa());
						justificativa.setDesc(justificativaDto.getDescricaoJustificativa());
						resp.getReasons().add(justificativa);
					}
				 }
			 }else{
				 Collection<JustificativaSolicitacaoDTO> colJustificativas = null;
				 JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, RestUtil.getUsuarioSistema(restSessionDto));
				 if (solicitacaoDto.getTipoSolicitacao().equals(TipoSolicitacaoServico.Viagem)) {
					 colJustificativas = justificativaSolicitacaoService.listAtivasParaViagem();
				 }else{
					 colJustificativas = justificativaSolicitacaoService.listAtivasParaAprovacao();
				 }
				 if (colJustificativas != null) {
					for (JustificativaSolicitacaoDTO justificativaDto : colJustificativas) {
						CtReason justificativa = new CtReason();
						justificativa.setId(justificativaDto.getIdJustificativa());
						justificativa.setDesc(justificativaDto.getDescricaoJustificativa());
						resp.getReasons().add(justificativa);
					}
				 }
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
			 return resp;				
		 }
		 	
		 resp.setAmount(resp.getReasons().size());
		 return resp;
	}
	
	@Override
	public CtMessageResp execute(RestSessionDTO restSessionDto, RestExecutionDTO restExecutionDto, RestOperationDTO restOperationDto, CtMessage message) throws JAXBException {
		if (message.getMessageID().endsWith("getByUser")) 
			return getByUser(restSessionDto, restExecutionDto,restOperationDto,message);
		else if (message.getMessageID().endsWith("getById")) 
			return getById(restSessionDto, restExecutionDto,restOperationDto,message);			
		else if (message.getMessageID().endsWith("feedback")) 
			return feedback(restSessionDto, restExecutionDto,restOperationDto,message);		
		else if (message.getMessageID().endsWith("new")) 
			return add(restSessionDto, restExecutionDto,restOperationDto,message);		
		else if (message.getMessageID().endsWith("getReasons")) 
			return getReasons(restSessionDto, restExecutionDto,restOperationDto,message);		
		else 
			return null;
	}

}
