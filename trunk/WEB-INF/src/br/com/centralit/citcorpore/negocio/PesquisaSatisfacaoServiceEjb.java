/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PesquisaSatisfacaoDTO;
import br.com.centralit.citcorpore.integracao.PesquisaSatisfacaoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author valdoilo
 * 
 */
public class PesquisaSatisfacaoServiceEjb extends CrudServicePojoImpl implements PesquisaSatisfacaoService {

    private static final long serialVersionUID = -3479958263124790072L;

    @Override
    protected CrudDAO getDao() throws ServiceException {
	return new PesquisaSatisfacaoDAO();
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

    public Collection<PesquisaSatisfacaoDTO> getPesquisaByIdSolicitacao(int idServico){
    	try {
			return ((PesquisaSatisfacaoDAO)getDao()).getPesquisaByIdSolicitacao(idServico);
		} catch (ServiceException e) {
			e.printStackTrace();
		}    	
    	return null;
    }

	@Override
	public Collection<PesquisaSatisfacaoDTO> relatorioPesquisaSatisfacao(
			PesquisaSatisfacaoDTO pesquisaSatisfacaoDTO) throws Exception {
		PesquisaSatisfacaoDAO pesquisaSatisfacaoDAO = (PesquisaSatisfacaoDAO) getDao();
		
		return pesquisaSatisfacaoDAO.relatorioPesquisaSatisfacao(pesquisaSatisfacaoDTO);
	}
}
