package br.com.centralit.citsmart.rest.operation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtListTasks;
import br.com.centralit.citsmart.rest.schema.CtListTasksResp;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
import br.com.centralit.citsmart.rest.util.RestUtil;
import br.com.citframework.util.UtilStrings;


public class RestListTasks extends RestOperation {
	
	@Override
	public CtMessageResp execute(RestSessionDTO restSessionDto, RestExecutionDTO restExecutionDto, RestOperationDTO restOperationDto, CtMessage message) throws JAXBException {
		 CtListTasksResp resp = new CtListTasksResp();
		 //CtListObjects resp = new CtListObjects();
		 
		 if (restSessionDto.getUser() == null || restSessionDto.getUser().getLogin() == null || restSessionDto.getUser().getLogin().trim().equals("")) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Login do usuário não identificado"));
			 return resp;
		 }
		
		 CtListTasks input = (CtListTasks) message;
		 List<TipoSolicitacaoServico> tipos = new ArrayList();
		 if (UtilStrings.nullToVazio(input.getListarCompras()).equalsIgnoreCase("S"))
			tipos.add(TipoSolicitacaoServico.Compra);
		 if (UtilStrings.nullToVazio(input.getListarIncidentes()).equalsIgnoreCase("S"))
			tipos.add(TipoSolicitacaoServico.Incidente);
		 if (UtilStrings.nullToVazio(input.getListarRequisicoes()).equalsIgnoreCase("S"))
			tipos.add(TipoSolicitacaoServico.Requisicao);
		 if (UtilStrings.nullToVazio(input.getListarRH()).equalsIgnoreCase("S"))
			tipos.add(TipoSolicitacaoServico.RH);
		 if (UtilStrings.nullToVazio(input.getListarViagens()).equalsIgnoreCase("S"))
			tipos.add(TipoSolicitacaoServico.Viagem);
		 
		 if (tipos.size() == 0) {
			 resp.setError(RestOperationUtil.buildError(RestEnum.INPUT_ERROR, "Pelo menos um tipo de solicitação deve ser selecionado"));
			 return resp;
		 }

		 TipoSolicitacaoServico[] arrayTipos = new TipoSolicitacaoServico[tipos.size()];
		 int i = 0;
		 for (TipoSolicitacaoServico tipo : tipos) {
			arrayTipos[i] = tipo;
			i++;
		 }
		 
		 try {
			resp.setTarefas(RestUtil.getExecucaoSolicitacaoService(restSessionDto).recuperaTarefas(restSessionDto.getUser().getLogin(), arrayTipos, "N"));
			resp.setQtdeTarefas(resp.getTarefas().size());
			/*List<TarefaFluxoDTO> origem = RestUtil.getRestExecucaoSolicitacaoService(restSessionDto).recuperaTarefas(restSessionDto.getUser().getLogin());
			if (origem != null) {
				List<IDto> destino = resp.getObjetos();
				for (TarefaFluxoDTO tarefaDto : origem) {
					destino.add(tarefaDto);
				}
			}*/
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestOperationUtil.buildError(e));
		 } 

		 return resp;
	}

}
