package br.com.centralit.citsmart.rest.operation;

import javax.xml.bind.JAXBException;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;


public interface IRestOperation {
	public CtMessageResp execute(RestSessionDTO restSessionDto, RestExecutionDTO restExecutionDto, RestOperationDTO restOperationDto, CtMessage message) throws JAXBException;
}
