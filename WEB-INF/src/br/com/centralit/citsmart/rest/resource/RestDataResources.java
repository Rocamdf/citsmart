package br.com.centralit.citsmart.rest.resource;

import java.util.Collection;
import java.util.HashMap;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.centralit.citcorpore.ajaxForms.DataManager;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.citframework.service.ServiceLocator;

@Path("/services") 
public class RestDataResources {  
	    
	 protected static ObjetoNegocioDTO restoreByName(String name) throws Exception {
		ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
		return objetoNegocioService.findByNomeObjetoNegocio(name);
	 }
	 
	 protected static String exportDB(HashMap<String, String> map, ObjetoNegocioDTO objetoNegocioDto, String cond, String order, boolean links) throws Exception {
		map.put("excluirAoExportar","N");
		StringBuffer result = null;
		if (links) {
			map.put("exportarVinculos","S");
			result = new DataManager().geraRecursiveExportObjetoNegocio(map, objetoNegocioDto.getIdObjetoNegocio(), "", "", cond, order);
		} else {
			map.put("exportarVinculos","N");
			result = new DataManager().geraExportObjetoNegocio(map, objetoNegocioDto.getIdObjetoNegocio(), "", "", cond, order);
		}
		return "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='0'>\n" + result.toString() + "\n</tables>";
	 }
	 
	 protected static Collection<CamposObjetoNegocioDTO> restoreDataFields(ObjetoNegocioDTO objetoNegocioDto) throws Exception {
		CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
		return camposObjetoNegocioService.findByIdObjetoNegocio(objetoNegocioDto.getIdObjetoNegocio());
	 }

	 public CtError buildError(String code, String description) {
		 CtError error = new CtError();
		 error.setCode(code);
		 error.setDescription(description);
		 return error;
	 }
	 
	 @GET
	 @Path("/data/{name}/{id}")
	 @Produces( { MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 public Response getBusinessObjectById(@PathParam("name") String name, 
			 						   	   @PathParam("id") String id, 
			 						   	   @QueryParam("format") @DefaultValue(RestEnum.FORMAT_DB) String format,
			 						   	   @QueryParam("links") @DefaultValue("N") String links,
			 						   	   @QueryParam("cond") @DefaultValue("") String cond,
			 						   	   @QueryParam("order") @DefaultValue("") String order) {
		 if (format.equalsIgnoreCase(RestEnum.FORMAT_DB)) 
			 return getDataBaseObjectById(name, id, links, cond, order);
		 else {
			 CtError error = buildError(RestEnum.PARAM_ERROR, "### Erro nos parâmetros da consulta -> Formato '"+format+"' não suportado");
			 return Response.status(Status.OK).entity(error).build();
		 }
	 }
  
	 @GET 
	 @Path("/data/{name}")
	 @Produces( { MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 public Response getBusinessObjectByCondition(@PathParam("name") String name, 
			 						   	   @QueryParam("format") @DefaultValue(RestEnum.FORMAT_DB) String format,
			 						   	   @QueryParam("links") @DefaultValue("N") String links,
			 						   	   @QueryParam("cond") @DefaultValue("") String cond,
			 						   	   @QueryParam("order") @DefaultValue("") String order) {
		 if (cond == null || cond.equals("")) {
			 CtError error = buildError(RestEnum.PARAM_ERROR, "### Erro nos parâmetros da consulta -> Condição não informada");
			 return Response.status(Status.OK).entity(error).build();
		 }

		 if (format.equalsIgnoreCase(RestEnum.FORMAT_DB)) 
			 return getDataBaseObjectByCondition(name, links, cond, order);
		 else {
			 CtError error = buildError(RestEnum.PARAM_ERROR, "### Erro nos parâmetros da consulta -> Formato '"+format+"' não suportado");
			 return Response.status(Status.OK).entity(error).build();
		 }
	 }

	 @GET
	 @Path("/data/{name}/{id}")
	 @Produces( { MediaType.TEXT_PLAIN })
	 public Response getDataBaseObjectById(@PathParam("name") String name, 
			 						   @PathParam("id") String id, 
			 						   @QueryParam("links") @DefaultValue("N") String links,
			 						   @QueryParam("cond") @DefaultValue("") String cond,
		   							   @QueryParam("order") @DefaultValue("") String order) {
		try {
			ObjetoNegocioDTO objetoNegocioDto = RestDataResources.restoreByName(name);
			if (objetoNegocioDto == null) {
				 CtError error = buildError(RestEnum.PARAM_ERROR, "### Erro nos parâmetros da consulta -> Objeto '"+name+"' não existe");
				 return Response.status(Status.OK).entity(error).build();
			}

			Collection<CamposObjetoNegocioDTO> fields = RestDataResources.restoreDataFields(objetoNegocioDto);
			if (fields == null) {
				 CtError error = buildError(RestEnum.PARAM_ERROR, "Erro na recuperação dos atributos do objeto '"+name+"' não existe");
				 return Response.status(Status.OK).entity(error).build();
			} 
			
			HashMap<String, String> map = new HashMap();
			for (CamposObjetoNegocioDTO campoDto : fields) {
				if (campoDto.getPk() != null && campoDto.getPk().equalsIgnoreCase("S")) {
					map.put("COND_"+campoDto.getIdCamposObjetoNegocio(), "=");
					map.put("VALOR_"+campoDto.getIdCamposObjetoNegocio(), id);
					break;
				}
			}	
			if (map.size() == 0) {
				 CtError error = buildError(RestEnum.PARAM_ERROR, "Erro na recuperação dos atributos do objeto '"+name+"' não existe");
				 return Response.status(Status.OK).entity(error).build();
			}  
			
			boolean bLinks = links.equalsIgnoreCase("S") || links.equalsIgnoreCase("Y");
			return Response.status(Status.OK).entity(RestDataResources.exportDB(map,objetoNegocioDto,cond,order,bLinks)).build();
		} catch (Exception e) {
			e.printStackTrace();
			CtError error = buildError(RestEnum.INTERNAL_ERROR, e.getMessage());
			return Response.status(Status.OK).entity(error).build();
		}
	 }
   
	 @GET
	 @Path("/data/{name}")
	 @Produces( { MediaType.TEXT_PLAIN })
	 public Response getDataBaseObjectByCondition(@PathParam("name") String name, 
			 						   @QueryParam("links") @DefaultValue("N") String links,
			 						   @QueryParam("cond") @DefaultValue("") String cond,
		   							   @QueryParam("order") @DefaultValue("") String order) {
		try {
			ObjetoNegocioDTO objetoNegocioDto = RestDataResources.restoreByName(name);
			if (objetoNegocioDto == null) {
				 CtError error = buildError(RestEnum.PARAM_ERROR, "### Erro nos parâmetros da consulta -> Objeto '"+name+"' não existe");
				 return Response.status(Status.OK).entity(error).build();
			}
			
			HashMap<String, String> map = new HashMap();
			boolean bLinks = links.equalsIgnoreCase("S") || links.equalsIgnoreCase("Y");
			return Response.status(Status.OK).entity(RestDataResources.exportDB(map,objetoNegocioDto,cond,order,bLinks)).build();
		} catch (Exception e) {
			e.printStackTrace();
			CtError error = buildError(RestEnum.INTERNAL_ERROR, e.getMessage());
			return Response.status(Status.OK).entity(error).build();
		}
	 }	 
}  

