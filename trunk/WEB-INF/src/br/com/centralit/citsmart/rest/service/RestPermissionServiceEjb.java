package br.com.centralit.citsmart.rest.service;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestPermissionDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.dao.RestPermissionDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RestPermissionServiceEjb extends CrudServicePojoImpl implements RestPermissionService {
	protected CrudDAO getDao() throws ServiceException {
		return new RestPermissionDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdOperation(Integer parm) throws Exception{
		RestPermissionDao dao = new RestPermissionDao();
		try{
			return dao.findByIdOperation(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	public void deleteByIdOperation(Integer parm) throws Exception{
		RestPermissionDao dao = new RestPermissionDao();
		try{
			dao.deleteByIdOperation(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}			
	}
	public Collection findByIdGroup(Integer parm) throws Exception{
		RestPermissionDao dao = new RestPermissionDao();
		try{
			return dao.findByIdGroup(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}			
	}
	public void deleteByIdGroup(Integer parm) throws Exception{
		RestPermissionDao dao = new RestPermissionDao();
		try{
			dao.deleteByIdGroup(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	public boolean allowedAccess(RestSessionDTO restSessionDto, RestOperationDTO restOperationDto) {
		UsuarioDTO usuarioDto = restSessionDto.getUser();
		if (usuarioDto == null || usuarioDto.getColGrupos() == null)
			return false;
		boolean result = false;
		for (GrupoDTO grupoDto : usuarioDto.getColGrupos()) {
			RestPermissionDTO restPermissionDto = new RestPermissionDTO();
			restPermissionDto.setIdRestOperation(restOperationDto.getIdRestOperation());
			restPermissionDto.setIdGroup(grupoDto.getIdGrupo());
			List<RestPermissionDTO> list = null;
			try {
				list = (List<RestPermissionDTO>) find(restPermissionDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (list != null && !list.isEmpty() && list.get(0).getStatus().trim().equals("A")) {
				result = true;
				break;
			}
		}
		return result;
	}
}
