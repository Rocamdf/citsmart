package br.com.centralit.citcorpore.negocio;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.integracao.ClienteDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.RequisitoSLADao;
import br.com.centralit.citcorpore.integracao.UnidadeDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilStrings;
public class RequisitoSLAServiceEjb extends CrudServicePojoImpl implements RequisitoSLAService {
	protected CrudDAO getDao() throws ServiceException {
		return new RequisitoSLADao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdEmpregado(Integer parm) throws Exception{
		RequisitoSLADao dao = new RequisitoSLADao();
		try{
			return dao.findByIdEmpregado(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdEmpregado(Integer parm) throws Exception{
		RequisitoSLADao dao = new RequisitoSLADao();
		try{
			dao.deleteByIdEmpregado(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public Collection findById(Integer idRequisitoSla) throws Exception {
		RequisitoSLADao dao = new RequisitoSLADao();
		try {
			return dao.findById(idRequisitoSla);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public String verificaIdSolicitante(HashMap mapFields) throws Exception {
		EmpregadoDao empregadoDao = new EmpregadoDao();
		List<EmpregadoDTO> listaEmpregado = null;
		String id = mapFields.get("IDEMPREGADO").toString().trim();
		//Integer idContrato = mapFields.get("IDEMPREGADO").toString().trim();
		if ((id==null)||(id.equals(""))){
			id="0";
		}
		if (UtilStrings.soContemNumeros(id)) {
			Integer idEmpregado;
			idEmpregado = (Integer) Integer.parseInt(id);
			listaEmpregado = empregadoDao.findByIdEmpregado(idEmpregado);
		} else {
			listaEmpregado = empregadoDao.findByNomeEmpregado(id);
		}
		if((listaEmpregado != null)&&(listaEmpregado.size()>0)){
			return String.valueOf(listaEmpregado.get(0).getIdEmpregado());
		}else{
			return "0";
		}
	}	
}