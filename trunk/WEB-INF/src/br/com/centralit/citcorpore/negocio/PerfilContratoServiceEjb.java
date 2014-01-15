package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.PerfilContratoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class PerfilContratoServiceEjb extends CrudServicePojoImpl implements PerfilContratoService {
	protected CrudDAO getDao() throws ServiceException {
		return new PerfilContratoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdContrato(Integer parm) throws Exception{
		PerfilContratoDao dao = new PerfilContratoDao();
		try{
			return dao.findByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdContrato(Integer parm) throws Exception{
		PerfilContratoDao dao = new PerfilContratoDao();
		try{
			dao.deleteByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection getTotaisByIdMarcoPagamentoPrj(Integer idMarcoPagamentoPrjParm, Integer idLinhaBaseProjetoParm) throws Exception{
		PerfilContratoDao dao = new PerfilContratoDao();
		try{
			return dao.getTotaisByIdMarcoPagamentoPrj(idMarcoPagamentoPrjParm, idLinhaBaseProjetoParm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	public Collection getTotaisByIdTarefaLinhaBaseProjeto(Integer idTarefaLinhaBaseProjetoParm) throws Exception{
		PerfilContratoDao dao = new PerfilContratoDao();
		try{
			return dao.getTotaisByIdTarefaLinhaBaseProjeto(idTarefaLinhaBaseProjetoParm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	public Collection getTotaisByIdLinhaBaseProjeto(Integer idLinhaBaseProjetoParm) throws Exception{
		PerfilContratoDao dao = new PerfilContratoDao();
		try{
			return dao.getTotaisByIdLinhaBaseProjeto(idLinhaBaseProjetoParm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}	
	public Collection getTotaisSEMIdMarcoPagamentoPrj(Integer idLinhaBaseProjetoParm) throws Exception{
		PerfilContratoDao dao = new PerfilContratoDao();
		try{
			return dao.getTotaisSEMIdMarcoPagamentoPrj(idLinhaBaseProjetoParm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
}
