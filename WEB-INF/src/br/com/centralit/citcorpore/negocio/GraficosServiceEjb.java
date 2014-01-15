/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.GraficoPizzaDTO;
import br.com.centralit.citcorpore.integracao.GraficosDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author breno.guimaraes
 *
 */
public class GraficosServiceEjb extends CrudServicePojoImpl implements GraficosService {

    GraficosDao gDao;
    
    private GraficosDao getGraficosDao(){
	if(gDao == null){
	    gDao = new GraficosDao();
	}
	return gDao;
    }
    
    @Override
    protected CrudDAO getDao() throws ServiceException {
	return null;
    }
    
    @Override
    protected void validaCreate(Object obj) throws Exception {  }
    @Override
    protected void validaUpdate(Object obj) throws Exception {  }
    @Override
    protected void validaDelete(Object obj) throws Exception {  }
    @Override
    protected void validaFind(Object obj) throws Exception { }

    @Override
    public ArrayList<GraficoPizzaDTO> getRelatorioPorNomeCategoria() {
	return getGraficosDao().getRelatorioPorNomeCategoria();
    }
    
    public ArrayList<GraficoPizzaDTO> getRelatorioPorSituacao(){
	return getGraficosDao().getRelatorioPorSituacao();
    }
    
    public ArrayList<GraficoPizzaDTO> getRelatorioPorGrupo() {
	return getGraficosDao().getRelatorioPorGrupo();
    }
}
