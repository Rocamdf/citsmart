package br.com.centralit.citsmart.rest.service;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestParameterDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.dao.RestParameterDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
public class RestParameterServiceEjb extends CrudServicePojoImpl implements RestParameterService {
	protected CrudDAO getDao() throws ServiceException {
		return new RestParameterDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public RestParameterDTO findByIdentifier(Integer parm) throws Exception{
		RestParameterDao dao = new RestParameterDao();
		try{
			return dao.findByIdentifier(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	public HashMap<String, RestDomainDTO> findParameters(RestSessionDTO restSessionDto, RestOperationDTO restOperationDto) {
		HashMap<String, RestDomainDTO> mapParam = new HashMap<String, RestDomainDTO>();
		 try {
			RestDomainService restDomainService = (RestDomainService) ServiceLocator.getInstance().getService(RestDomainService.class, null);
			List<RestDomainDTO> params = (List<RestDomainDTO>) restDomainService.findByIdRestOperation(restOperationDto.getIdRestOperation());
			if (params != null) {
				RestParameterService restParameterService = (RestParameterService) ServiceLocator.getInstance().getService(RestParameterService.class, null);
				for (RestDomainDTO domainDto : params) {
					RestParameterDTO paramDto = new RestParameterDTO();
					paramDto.setIdRestParameter(domainDto.getIdRestParameter());
					paramDto = (RestParameterDTO) restParameterService.restore(paramDto);
					if (paramDto != null)
						mapParam.put(paramDto.getIdentifier(), domainDto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return mapParam;
	 }
	
	public String getParamValue(HashMap<String, RestDomainDTO> mapParam, String key) {
		 RestDomainDTO restDomainDto = mapParam.get(key);
		 if (restDomainDto == null || restDomainDto.getValue() == null)
			 return null;
		 else
			 return restDomainDto.getValue().trim();
	 }

	
}
