package br.com.centralit.citsmart.rest.resource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;

import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtAddServiceRequest;
import br.com.centralit.citsmart.rest.schema.CtAddServiceRequestResp;
import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.schema.CtLogin;
import br.com.centralit.citsmart.rest.schema.CtLoginResp;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.centralit.citsmart.rest.service.RestSessionService;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.centralit.citsmart.rest.util.RestEnum.OperationType;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
import br.com.centralit.citsmart.rest.util.RestUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
         
@Path("/services") 
public class RestOperationResources { 
	
	@Context 
	protected HttpServletRequest httpRequest;
	

	 @POST
	 @Path("/login")
	 public Response login(CtLogin login) throws JAXBException{
		 CtLoginResp resp = new CtLoginResp();
		 try {
			 RestSessionService restSessionService = (RestSessionService) ServiceLocator.getInstance().getService(RestSessionService.class, null);
			 RestSessionDTO sessionDto = restSessionService.newSession(httpRequest, login);
			 resp.setSessionID(sessionDto.getSessionID());
		 } catch (Exception e) {
			 CtError error = RestOperationUtil.buildError(RestEnum.SESSION_ERROR,e.getMessage());
			 resp.setError(error);
			 return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
		 }

		 return Response.status(Status.OK).entity(resp).build();
	 }
	 
	 @POST
	 @Path("/xml/login")
	 public Response login(String xml) throws JAXBException{
		 InputStream ioos = new ByteArrayInputStream(xml.getBytes());
		 CtLogin login = JAXB.unmarshal(ioos, CtLogin.class); 
		 return login(login);
	 }
	 
	 @POST
	 @Path("/xml/addServiceRequest")	 
	 public Response addServiceRequest(String xml) throws JAXBException{
		 InputStream ioos = new ByteArrayInputStream(xml.getBytes());
		 br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequest addServiceRequest = JAXB.unmarshal(ioos, br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequest.class); 
		 return addServiceRequest(addServiceRequest);
	 }
	 
	 
	 @POST
	 @Path("/addServiceRequest")	 
	 public Response addServiceRequest(br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequest addServiceRequest) throws JAXBException{
		 br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequestResp resp = new br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequestResp();
		 resp.setDateTime(Util.asXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));
		 
		 CtAddServiceRequest input = new CtAddServiceRequest();
		 
		 try {
			Reflexao.copyPropertyValues(addServiceRequest, input);
		 } catch (Exception e) {
			 e.printStackTrace();
			 CtError error = RestOperationUtil.buildError(RestEnum.INTERNAL_ERROR,e.getMessage());
			 resp.setError(error);
			 return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
		 }
		 
		 input.setMessageID("addServiceRequest");
		 input.setServiceRequestSource(addServiceRequest.getServiceRequestSource());
		 
		 RestSessionDTO restSessionDto = RestUtil.getRestSessionService(null).getSession(input.getSessionID());
		 if (!RestUtil.isValidSession(restSessionDto)){
			 CtError error = RestOperationUtil.buildError(RestEnum.SESSION_ERROR,"Sessão não existe ou está expirada");
			 resp.setError(error);
			 return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
		 }

		 RestOperationDTO restOperationDto = RestUtil.getRestOperationService(restSessionDto).findByName(input.getMessageID());
		 if (restOperationDto == null) {
			 CtError error = RestOperationUtil.buildError(RestEnum.PARAM_ERROR,"Operação não cadastrada");
			 resp.setError(error);
			 return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
		 }
		 
		 if (!RestUtil.getRestPermissionService(restSessionDto).allowedAccess(restSessionDto, restOperationDto)) {
			 CtError error = RestOperationUtil.buildError(RestEnum.PARAM_ERROR,"Usuário não tem acesso à operação");
			 resp.setError(error);
			 return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
		 }
		 
		 RestExecutionDTO restExecutionDto = null;
		 try{
			 restExecutionDto = RestOperationUtil.initialize(restSessionDto, restOperationDto, input);
		 } catch (Exception e) {
			 CtError error = RestOperationUtil.buildError(RestEnum.INTERNAL_ERROR,RestUtil.stackToString(e));
			 resp.setError(error);
			 return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
		 }
		 
		 try{
			 CtAddServiceRequestResp result = (CtAddServiceRequestResp) RestOperationUtil.execute(restSessionDto, restExecutionDto, restOperationDto, input);
			 resp.setDateTime(result.getDateTime());
			 resp.setError(result.getError());
			 resp.setOperationID(result.getOperationID());
			 resp.setServiceRequestDest(result.getServiceRequestDest());
			 RestOperationUtil.finalize(restExecutionDto, restSessionDto, result);
			 if (resp.getError() == null)
				 return Response.status(Status.OK).entity(resp).build();
			 else
				 return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();			 
		 } catch (Exception e) {
			 e.printStackTrace();
			 CtError error = RestUtil.getRestLogService(restSessionDto).create(restExecutionDto, e);
			 resp.setError(error);
			 return Response.status(Status.PRECONDITION_FAILED).entity(resp).build();
		 }
	 }		 

	 @POST
	 @Path("/execute")
	 public Response execute(CtMessage input) {
		 return RestOperationUtil.execute(input);
	 }

}  

