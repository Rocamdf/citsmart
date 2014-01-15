package br.com.centralit.citsmart.rest.util;

import java.math.BigInteger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.operation.IRestOperation;
import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.centralit.citsmart.rest.util.RestEnum.ClassType;
import br.com.centralit.citsmart.rest.util.RestEnum.OperationType;
import br.com.citframework.util.UtilDatas;
         
public class RestOperationUtil { 
	
	public static RestExecutionDTO initialize(RestSessionDTO restSessionDto, RestOperationDTO restOperationDto, Object input) throws JAXBException {
		RestExecutionDTO restExecutionDto = null;
		try {
			restExecutionDto = RestUtil.getRestExecutionService(restSessionDto).start(restSessionDto, restOperationDto,input);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JAXBException(e.getMessage());
		}
		if (restExecutionDto != null) {
			restExecutionDto.setRestOperationDto(restOperationDto);
			restExecutionDto.setInput(input);
		}
		return restExecutionDto;
	}

	public static void finalize(RestExecutionDTO restExecutionDto, RestSessionDTO restSessionDto, CtMessageResp resp) {
		 resp.setOperationID(new BigInteger(""+restExecutionDto.getIdRestExecution()));
		 
		 try {
			resp.setDateTime(Util.asXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));
		 } catch (Exception e) {
			e.printStackTrace();
		 } 
		 
		 try {
			RestUtil.getRestExecutionService(restSessionDto).end(restExecutionDto, resp);
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setError(RestUtil.getRestLogService(restSessionDto).create(restExecutionDto, e));
		 }
	}		 
	
	public static boolean validAttributes(RestOperationDTO restOperationDto) {
		if (restOperationDto.getClassType().equalsIgnoreCase(ClassType.Java.name()))
			return restOperationDto.getJavaClass() != null && !restOperationDto.getJavaClass().trim().equals("");
		else
			return restOperationDto.getJavaScript() != null && !restOperationDto.getJavaScript().trim().equals("");
	}

	public static CtMessageResp execute(RestSessionDTO restSessionDto, RestExecutionDTO restExecutionDto, RestOperationDTO restOperationDto, CtMessage input) throws JAXBException {
		if (!validAttributes(restOperationDto)) 
			throw new JAXBException("Classe de execução não foi parametrizada");
		if (restOperationDto.getClassType().equalsIgnoreCase(ClassType.Java.name())) 
			return executeJavaClass(restSessionDto, restExecutionDto, restOperationDto, input);
		else
			return executeJavaScript(restSessionDto, restExecutionDto, restOperationDto, input);
	}

	public static CtMessageResp executeJavaScript(RestSessionDTO restSessionDto, RestExecutionDTO restExecutionDto, RestOperationDTO restOperationDto, CtMessage input) throws JAXBException {
		return null;
	}

	public static CtMessageResp executeJavaClass(RestSessionDTO restSessionDto, RestExecutionDTO restExecutionDto, RestOperationDTO restOperationDto, CtMessage input) throws JAXBException {
		IRestOperation restOperation;
		try {
			restOperation = (IRestOperation) Class.forName(restOperationDto.getJavaClass()).newInstance();
			return restOperation.execute(restSessionDto, restExecutionDto, restOperationDto, input);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JAXBException(e.getLocalizedMessage());
		}
	}
	
	public static CtMessageResp buildOperationError(String code, String descr, CtMessage input) {
		CtMessageResp resp;
		try {
			resp = (CtMessageResp) Class.forName(input.getClass().getName()+"Resp").newInstance();
		} catch (Exception e) {
			 resp = new CtMessageResp();
		}
		resp.setDateTime(Util.asXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));
		resp.setError(buildError(code,descr));
		return resp;
	}
	
	public static CtError buildError(String code, String descr) {
		 CtError error = new CtError();
		 error.setCode(code);
		 error.setDescription(descr);
		 return error;
	}
	
	public static CtError buildError(Exception e) {
		CtError error = new CtError();
		error.setCode(RestEnum.INTERNAL_ERROR);
		error.setDescription(RestUtil.stackToString(e));
		return error;
	}
	
	 public static Response execute(CtMessage input) {
		 RestSessionDTO restSessionDto = RestUtil.getRestSessionService(null).getSession(input.getSessionID());
		 if (!RestUtil.isValidSession(restSessionDto))
			 return Response.status(Status.PRECONDITION_FAILED).entity(RestOperationUtil.buildOperationError(RestEnum.SESSION_ERROR,"Sessão não existe ou está expirada", input)).build();

		 RestOperationDTO restOperationDto = RestUtil.getRestOperationService(restSessionDto).findByName(input.getMessageID());
		 if (restOperationDto == null)
			 return Response.status(Status.PRECONDITION_FAILED).entity(RestOperationUtil.buildOperationError(RestEnum.PARAM_ERROR,"Operação não cadastrada", input)).build();

		 if (!RestUtil.getRestPermissionService(restSessionDto).allowedAccess(restSessionDto, restOperationDto))
			 return Response.status(Status.PRECONDITION_FAILED).entity(RestOperationUtil.buildOperationError(RestEnum.PARAM_ERROR,"Usuário não tem acesso à operação", input)).build();

		 RestExecutionDTO restExecutionDto = null;
		 try{
			 restExecutionDto = RestOperationUtil.initialize(restSessionDto, restOperationDto, input);
		 } catch (Exception e) {
			 return Response.status(Status.PRECONDITION_FAILED).entity(RestOperationUtil.buildOperationError(RestEnum.INTERNAL_ERROR,RestUtil.stackToString(e), input)).build();
		 }
		 
		 if (restOperationDto.getOperationType().equalsIgnoreCase(OperationType.Sync.name())) {
			 CtMessageResp resp = null;
			 try{
				 resp = RestOperationUtil.execute(restSessionDto, restExecutionDto, restOperationDto, input);
			 } catch (Exception e) {
				 e.printStackTrace();
				 CtError error = RestUtil.getRestLogService(restSessionDto).create(restExecutionDto, e);
				 resp = new CtMessageResp();
				 resp.setError(error);
			 }
			 RestOperationUtil.finalize(restExecutionDto, restSessionDto, resp);
			 if (resp.getError() == null)
				 return Response.status(Status.OK).entity(resp).build();
			 else
				 return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
		 }else{
			 CtMessageResp resp = new CtMessageResp(); 
			 resp.setDateTime(Util.asXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));
			 resp.setOperationID(new BigInteger(""+restExecutionDto.getIdRestExecution()));
			 return Response.status(Status.OK).entity(resp).build();
		 }	
	 }
	 
}  

