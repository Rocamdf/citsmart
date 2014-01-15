package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.CausaIncidenteDTO;
import br.com.centralit.citcorpore.integracao.CausaIncidenteDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class CausaIncidenteServiceEjb extends CrudServicePojoImpl implements CausaIncidenteService {
	protected CrudDAO getDao() throws ServiceException {
		return new CausaIncidenteDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdCausaIncidentePai(Integer parm) throws Exception{
		CausaIncidenteDao dao = new CausaIncidenteDao();
		try{
			return dao.findByIdCausaIncidentePai(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCausaIncidentePai(Integer parm) throws Exception{
		CausaIncidenteDao dao = new CausaIncidenteDao();
		try{
			dao.deleteByIdCausaIncidentePai(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection listHierarquia() throws Exception{
		CausaIncidenteDao dao = new CausaIncidenteDao();
		Collection colFinal = new ArrayList();
		try{
			Collection col = dao.findSemPai();
			if (col != null){
			    for (Iterator it = col.iterator(); it.hasNext();){
				CausaIncidenteDTO causaIncidenteDTO = (CausaIncidenteDTO)it.next();
				causaIncidenteDTO.setNivel(0);
				colFinal.add(causaIncidenteDTO);
				Collection colAux = getCollectionHierarquia(causaIncidenteDTO.getIdCausaIncidente(), 0);
				if (colAux != null && colAux.size() > 0){
				    colFinal.addAll(colAux);
				}				
			    }
			}
			return colFinal;
		} catch (Exception e) {
			throw new ServiceException(e);
		}	    
	}
	public Collection getCollectionHierarquia(Integer idCausa, Integer nivel) throws Exception{
	    CausaIncidenteDao dao = new CausaIncidenteDao();
	    Collection col = dao.findByIdPai(idCausa);
	    Collection colFinal = new ArrayList();
	    if (col != null){
		for (Iterator it = col.iterator(); it.hasNext();){
		    CausaIncidenteDTO causaIncidenteDTO = (CausaIncidenteDTO)it.next();
		    causaIncidenteDTO.setNivel(nivel + 1);
		    colFinal.add(causaIncidenteDTO);
		    Collection colAux = getCollectionHierarquia(causaIncidenteDTO.getIdCausaIncidente(), causaIncidenteDTO.getNivel());
		    if (colAux != null && colAux.size() > 0){
			colFinal.addAll(colAux);
		    }
		}
	    }
	    return colFinal;
	}
}
