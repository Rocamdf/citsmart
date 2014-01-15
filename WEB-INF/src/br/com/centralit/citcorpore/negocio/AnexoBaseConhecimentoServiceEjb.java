/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.integracao.AnexoBaseConhecimentoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * EJB de AnexoBaseConhecimento.
 * 
 * @author valdoilo.damasceno
 */
public class AnexoBaseConhecimentoServiceEjb extends CrudServicePojoImpl implements AnexoBaseConhecimentoService {

    private static final long serialVersionUID = -1769415742138459073L;

    @Override
    public Collection<AnexoBaseConhecimentoDTO> consultarAnexosDaBaseConhecimento(BaseConhecimentoDTO baseConhecimentoBean) throws ServiceException,
	    Exception {
	return this.getAnexoBaseConhecimentoDao().consultarAnexosDaBaseConhecimento(baseConhecimentoBean);
    }

    /**
     * Retorna DAO de Anexo Base Conhecimento.
     * 
     * @return AnexoBaseConhecimentoDAO
     * @throws ServiceException
     * @author valdoilo.damasceno
     */
    public AnexoBaseConhecimentoDAO getAnexoBaseConhecimentoDao() throws ServiceException {
	return (AnexoBaseConhecimentoDAO) this.getDao();
    }

    @Override
    protected CrudDAO getDao() throws ServiceException {
	return new AnexoBaseConhecimentoDAO();
    }

    @Override
    protected void validaCreate(Object arg0) throws Exception {

    }

    @Override
    protected void validaDelete(Object arg0) throws Exception {

    }

    @Override
    protected void validaFind(Object arg0) throws Exception {

    }

    @Override
    protected void validaUpdate(Object arg0) throws Exception {

    }

}