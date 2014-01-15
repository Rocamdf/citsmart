package br.com.centralit.citsmart.rest.service;
import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.dao.RestExecutionDao;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.centralit.citsmart.rest.util.RestEnum.ExecutionStatus;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;
public class RestExecutionServiceEjb extends CrudServicePojoImpl implements RestExecutionService {
	protected CrudDAO getDao() throws ServiceException {
		return new RestExecutionDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdRestOperation(Integer parm) throws Exception{
		RestExecutionDao dao = new RestExecutionDao();
		try{
			return dao.findByIdRestOperation(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdRestOperation(Integer parm) throws Exception{
		RestExecutionDao dao = new RestExecutionDao();
		try{
			dao.deleteByIdRestOperation(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public RestExecutionDTO start(RestSessionDTO restSessionDto, RestOperationDTO restOperationDto, Object input) throws Exception {
		RestExecutionDTO restExecutionDto = new RestExecutionDTO();
		restExecutionDto.setIdRestOperation(restOperationDto.getIdRestOperation());
		restExecutionDto.setInputClass(input.getClass().getName());
		restExecutionDto.setInputData(new Gson().toJson(input));
		restExecutionDto.setInputDateTime(UtilDatas.getDataHoraAtual());
		restExecutionDto.setStatus(ExecutionStatus.NotInitiated.name());
		restExecutionDto.setIdUser(restSessionDto.getUserId());
		
		restExecutionDto = (RestExecutionDTO) new RestExecutionServiceEjb().create(restExecutionDto);
		return restExecutionDto;
	}

	public RestExecutionDTO start(RestOperationDTO restOperationDto, String input) throws Exception {
		RestExecutionDTO restExecutionDto = new RestExecutionDTO();
		restExecutionDto.setIdRestOperation(restOperationDto.getIdRestOperation());
		restExecutionDto.setInputClass(String.class.getName());
		restExecutionDto.setInputData(input);
		restExecutionDto.setInputDateTime(UtilDatas.getDataHoraAtual());
		restExecutionDto.setStatus(ExecutionStatus.NotInitiated.name());
		
		restExecutionDto = (RestExecutionDTO) new RestExecutionServiceEjb().create(restExecutionDto);
		return restExecutionDto;
	}
	
	public void end(RestExecutionDTO restExecutionDto, CtMessageResp output) throws Exception {
		ExecutionStatus status = ExecutionStatus.Processed;
		if (output.getError() != null)
			status = ExecutionStatus.Error;
		restExecutionDto.setStatus(status.name());
		new RestExecutionServiceEjb().update(restExecutionDto);
		
		new RestLogServiceEjb().create(restExecutionDto, output, status);
	}

	
}
