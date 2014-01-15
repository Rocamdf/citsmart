package br.com.centralit.citsmart.rest.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXB;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citsmart.rest.schema.CtLogin;
import br.com.centralit.citsmart.rest.schema.CtLoginResp;
import br.com.centralit.citsmart.rest.schema.CtService;
import br.com.centralit.citsmart.rest.schema.CtServiceRequest;
import br.com.centralit.citsmart.rest.schema.StServiceRequestPriority;
import br.com.centralit.citsmart.rest.schema.StServiceRequestType;
import br.com.centralit.citsmart.rest.schema.CtAddServiceRequest;
import br.com.centralit.citsmart.rest.schema.CtAddServiceRequestResp;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;

public class RestTest {
	
	public static String autenticacaoComObjeto() throws Exception {
	   CtLogin login = new CtLogin();
	   login.setUserName("carlos.alberto");
	   login.setPassword("123");
	   
	   ClientRequest request = new ClientRequest("http://localhost:8080/citsmart/services/login");

	   request.body(MediaType.APPLICATION_XML, login);

	   ClientResponse<CtLoginResp> response = request.post(CtLoginResp.class);
 
		if (response.getStatus() != 200) {
			throw new RuntimeException("Chamada falhou: HTTP error code : "
				+ response.getStatus() + response.getEntity());
		}
 
		CtLoginResp resp = response.getEntity();
		System.out.println("#### CHAMADA COM OBJETO - > SessionID: "+resp.getSessionID());
		
		return resp.getSessionID();
	}

	public static String autenticacaoComXML() throws Exception {
	   String xml = 
	    "<Login>"
	   +"<UserName>carlos.alberto</UserName>"
	   +"<Password>123</Password>"
	   +"</Login>";
	   
	   ClientRequest request = new ClientRequest("http://localhost:8080/citsmart/services/xml/login");

	   request.body(MediaType.APPLICATION_XML,  xml);
	   request.accept(MediaType.APPLICATION_XML);

	   ClientResponse<String> response = request.post(String.class);
		
	   if (response.getStatus() != 200) {
			throw new RuntimeException("Chamada falhou: HTTP error code : "
				+ response.getStatus() + response.getEntity());
		}
 
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));
	 
		String output;
		System.out.println("#### CHAMADA COM XML - > \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
		
		 InputStream ioos = new ByteArrayInputStream(response.getEntity().getBytes());
		 CtLoginResp resp = JAXB.unmarshal(ioos, CtLoginResp.class); 
		 
		 return resp.getSessionID();
	}

	public static String autenticacaoComJSON() throws Exception {
	   String input = "{\"userName\":\"carlos.alberto\",\"password\":\"123\"}";
	   
	   ClientRequest request = new ClientRequest("http://localhost:8080/citsmart/services/login");

	   request.accept(MediaType.APPLICATION_JSON);
	   request.body(MediaType.APPLICATION_JSON, input);

	    ClientResponse<String> response = request.post(String.class);
		 
		if (response.getStatus() != 200) {
			throw new RuntimeException("Chamada falhou: HTTP error code : "
				+ response.getStatus() + response.getEntity());
		}
 
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));
	 
		String output;
		System.out.println("#### CHAMADA COM JSON - > \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
 
		CtLoginResp resp = new Gson().fromJson(response.getEntity(), CtLoginResp.class);
		return resp.getSessionID();
	}

	public static br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequestResp incluiSolicitacaoCITSmart() throws Exception {
		br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequest addServiceRequest = new br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequest();
		CtServiceRequest serviceRequest = new CtServiceRequest();		
		CtService service = new CtService();	
		
		// -- Atribui a sessão
		addServiceRequest.setSessionID(autenticacaoComObjeto());
		
		// -- Atributos obrigatórios
		service.setCode("292");
		service.setDescription("ACESSOS.ALTERAR.REDE.CONFLITO DE IP.");
		serviceRequest.setService(service);

		serviceRequest.setNumber("8765436"); 
		serviceRequest.setStartDateTime(Util.asXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));
		serviceRequest.setDescription("Teste de inclusao de incidente no CITSmart");
		serviceRequest.setType(StServiceRequestType.I);
		serviceRequest.setUserID("carlos.alberto");
		serviceRequest.setImpact(StServiceRequestPriority.H);
		serviceRequest.setUrgency(StServiceRequestPriority.M);
		
		addServiceRequest.setServiceRequestSource(serviceRequest);
		
		ClientRequest request = new ClientRequest("http://localhost:8080/citsmart/services/addServiceRequest");

		request.body(MediaType.APPLICATION_XML, addServiceRequest);
		request.accept(MediaType.APPLICATION_JSON);

		ClientResponse<br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequestResp> response = request.post(br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequestResp.class);
 
		if (response.getStatus() != 200) {
			throw new RuntimeException("Chamada falhou: HTTP error code : "
				+ response.getStatus() + response.getEntity());
		}
 
		br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequestResp resp = response.getEntity();
		JAXB.marshal(resp, "D:\\temp\\resp.xml");
		System.out.println("#### RESP: "+new Gson().toJson(resp));
		
		return resp;
	}

	public static br.com.centralit.citsmart.rest.schema.CtAddServiceRequestResp incluiSolicitacaoCITSmart2() throws Exception {
		CtAddServiceRequest addServiceRequest = new CtAddServiceRequest();
		CtServiceRequest serviceRequest = new CtServiceRequest();		
		CtService service = new CtService();	
		
		// -- Atribui a sessão
		String sessionId = autenticacaoComObjeto();
		addServiceRequest.setSessionID(sessionId);
		addServiceRequest.setMessageID("addServiceRequest");
		
		// -- Atributos obrigatórios
		service.setCode("292");
		service.setDescription("ACESSOS.ALTERAR.REDE.CONFLITO DE IP.");
		serviceRequest.setService(service);

		serviceRequest.setNumber("123456786"); 
		serviceRequest.setStartDateTime(Util.asXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));
		serviceRequest.setDescription("Teste de inclusao de incidente no CITSmart");
		serviceRequest.setType(br.com.centralit.citsmart.rest.schema.StServiceRequestType.I);
		serviceRequest.setUserID("carlos.alberto");
		serviceRequest.setImpact(br.com.centralit.citsmart.rest.schema.StServiceRequestPriority.H);
		serviceRequest.setUrgency(br.com.centralit.citsmart.rest.schema.StServiceRequestPriority.M);
		
		addServiceRequest.setServiceRequestSource(serviceRequest);
		
		ClientRequest request = new ClientRequest("http://localhost:8080/citsmart/services/execute");

		request.body(MediaType.APPLICATION_XML, addServiceRequest);
		request.accept(MediaType.APPLICATION_JSON);

		ClientResponse<CtAddServiceRequestResp> response = request.post(CtAddServiceRequestResp.class);
 
		if (response.getStatus() != 200) {
			throw new RuntimeException("Chamada falhou: HTTP error code : "
				+ response.getStatus() + response.getEntity());
		}
 
		CtAddServiceRequestResp resp = response.getEntity();
		JAXB.marshal(resp, "D:\\temp\\resp.xml");
		System.out.println("#### RESP: "+new Gson().toJson(resp));
		
		return resp;
	}
	
	public static void main(String[] args) throws Exception {
		autenticacaoComObjeto();
		//autenticacaoComJSON();
		//autenticacaoComXML();	
		//incluiSolicitacaoCITSmart2();
		//incluiSolicitacaoCITSmart2();		
	}

}
