package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.CategoriaSolucaoDTO;
import br.com.centralit.citcorpore.integracao.CategoriaSolucaoDao;
import br.com.centralit.citcorpore.integracao.CategoriaSolucaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class CategoriaSolucaoServiceEjb extends CrudServicePojoImpl implements CategoriaSolucaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new CategoriaSolucaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdCategoriaSolucaoPai(Integer parm) throws Exception{
		CategoriaSolucaoDao dao = new CategoriaSolucaoDao();
		try{
			return dao.findByIdCategoriaSolucaoPai(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCategoriaSolucaoPai(Integer parm) throws Exception{
		CategoriaSolucaoDao dao = new CategoriaSolucaoDao();
		try{
			dao.deleteByIdCategoriaSolucaoPai(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection listHierarquia() throws Exception{
		CategoriaSolucaoDao dao = new CategoriaSolucaoDao();
		Collection colFinal = new ArrayList();
		try{
			Collection col = dao.findSemPai();
			if (col != null){
			    for (Iterator it = col.iterator(); it.hasNext();){
				CategoriaSolucaoDTO categoriaSolucaoDTO = (CategoriaSolucaoDTO)it.next();
				categoriaSolucaoDTO.setNivel(0);
				colFinal.add(categoriaSolucaoDTO);
				Collection colAux = getCollectionHierarquia(categoriaSolucaoDTO.getIdCategoriaSolucao(), 0);
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
	public Collection getCollectionHierarquia(Integer idCateg, Integer nivel) throws Exception{
	    CategoriaSolucaoDao dao = new CategoriaSolucaoDao();
	    Collection col = dao.findByIdPai(idCateg);
	    Collection colFinal = new ArrayList();
	    if (col != null){
		for (Iterator it = col.iterator(); it.hasNext();){
		    CategoriaSolucaoDTO categoriaSolucaoDTO = (CategoriaSolucaoDTO)it.next();
		    categoriaSolucaoDTO.setNivel(nivel + 1);
		    colFinal.add(categoriaSolucaoDTO);
		    Collection colAux = getCollectionHierarquia(categoriaSolucaoDTO.getIdCategoriaSolucao(), categoriaSolucaoDTO.getNivel());
		    if (colAux != null && colAux.size() > 0){
			colFinal.addAll(colAux);
		    }
		}
	    }
	    return colFinal;
	}	
}
