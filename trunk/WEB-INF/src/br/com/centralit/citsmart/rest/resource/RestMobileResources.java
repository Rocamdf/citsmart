package br.com.centralit.citsmart.rest.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.centralit.citsmart.rest.schema.CtNotificationFeedback;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetById;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUser;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetReasons;
import br.com.centralit.citsmart.rest.schema.CtNotificationNew;
import br.com.centralit.citsmart.rest.util.RestOperationUtil;
         
@Path("/mobile") 
public class RestMobileResources { 
 
	 @POST
	 @Path("/notification/getByUser")
	 public Response getnotificationByUser(CtNotificationGetByUser input) {
		 input.setMessageID("notification_getByUser");
		 return RestOperationUtil.execute(input);
	 }
	 
	 @POST
	 @Path("/notification/getById")
	 public Response getNotificationById(CtNotificationGetById input) {
		 input.setMessageID("notification_getById");
		 return RestOperationUtil.execute(input);
	 }	 
	 
	 @POST
	 @Path("/notification/feedback")
	 public Response feedback(CtNotificationFeedback input) {
		 input.setMessageID("notification_feedback");
		 return RestOperationUtil.execute(input);
	 }	
	 
	 @POST
	 @Path("/notification/new")
	 public Response newNotification(CtNotificationNew input) {
		 input.setMessageID("notification_new");
		 return RestOperationUtil.execute(input);
	 }
	 
	 @POST
	 @Path("/notification/getReasons")
	 public Response getReasons(CtNotificationGetReasons input) {
		 input.setMessageID("notification_getReasons");
		 return RestOperationUtil.execute(input);
	 }	 

}  

