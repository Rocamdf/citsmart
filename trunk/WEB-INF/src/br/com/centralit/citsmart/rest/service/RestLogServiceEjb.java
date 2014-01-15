package br.com.centralit.citsmart.rest.service;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestLogDTO;
import br.com.centralit.citsmart.rest.dao.RestExecutionDao;
import br.com.centralit.citsmart.rest.dao.RestLogDao;
import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.util.RestEnum;
import br.com.centralit.citsmart.rest.util.RestEnum.ExecutionStatus;
import br.com.centralit.citsmart.rest.util.RestUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;
public class RestLogServiceEjb extends CrudServicePojoImpl implements RestLogService {
	protected CrudDAO getDao() throws ServiceException {
		return new RestLogDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdRestExecution(Integer parm) throws Exception{
		RestLogDao dao = new RestLogDao();
		try{
			return dao.findByIdRestExecution(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdRestExecution(Integer parm) throws Exception{
		RestLogDao dao = new RestLogDao();
		try{
			dao.deleteByIdRestExecution(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public CtError create(RestExecutionDTO restExecutionDto, String code, String description){
		CtError error = new CtError();
		error.setCode(code);
		error.setDescription(description);
		create(restExecutionDto, error, ExecutionStatus.Error);
		return error;
	}
	
	@Override
	public CtError create(RestExecutionDTO restExecutionDto, Exception e) {
		CtError error = new CtError();
		error.setCode(RestEnum.INTERNAL_ERROR);
		error.setDescription(RestUtil.stackToString(e));
		create(restExecutionDto, error, ExecutionStatus.Error);
		return error;
	}

	public RestLogDTO create(RestExecutionDTO restExecutionDto, Object result, ExecutionStatus status){
		RestLogDTO logDto = new RestLogDTO();
		logDto.setIdRestExecution(restExecutionDto.getIdRestExecution());
		logDto.setDateTime(UtilDatas.getDataHoraAtual());
		logDto.setStatus(status.name());
		logDto.setResultClass(result.getClass().getName());
		logDto.setResultData(new Gson().toJson(result));
		
		try {
			logDto = (RestLogDTO) new RestLogServiceEjb().create(logDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return logDto;
	}

	
}
