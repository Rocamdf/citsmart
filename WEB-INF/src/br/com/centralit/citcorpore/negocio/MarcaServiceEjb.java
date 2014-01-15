package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.MarcaDTO;
import br.com.centralit.citcorpore.integracao.MarcaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings({"serial", "rawtypes"})
public class MarcaServiceEjb extends CrudServicePojoImpl implements MarcaService {
	protected CrudDAO getDao() throws ServiceException {
		return new MarcaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	
	public Collection findByIdFabricante(Integer parm) throws Exception{
		MarcaDao dao = new MarcaDao();
		try{
			return dao.findByIdFabricante(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdFabricante(Integer parm) throws Exception{
		MarcaDao dao = new MarcaDao();
		try{
			dao.deleteByIdFabricante(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean consultarMarcas(MarcaDTO marca) throws Exception {
		
		MarcaDao dao = new MarcaDao();
		
		return dao.consultarMarcas(marca);
	}
}
