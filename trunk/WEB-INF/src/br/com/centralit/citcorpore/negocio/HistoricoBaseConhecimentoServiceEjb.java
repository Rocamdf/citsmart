/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.HistoricoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.integracao.HistoricoBaseConhecimentoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class HistoricoBaseConhecimentoServiceEjb extends CrudServicePojoImpl implements HistoricoBaseConhecimentoService {

	private static final long serialVersionUID = -7304755292267069607L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new HistoricoBaseConhecimentoDAO();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {

	}

	@Override
	protected void validaFind(Object obj) throws Exception {

	}

	@Override
	public Collection<HistoricoBaseConhecimentoDTO> obterHistoricoDeAlteracao(HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDto) throws Exception {
		HistoricoBaseConhecimentoDAO historicoBaseConhecimentoDao = new HistoricoBaseConhecimentoDAO();
		return historicoBaseConhecimentoDao.obterHistoricoDeAlteracao(historicoBaseConhecimentoDto);
	}

	@Override
	public Collection<HistoricoBaseConhecimentoDTO> obterHistoricoDeAlteracaoPorPeriodo(HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDTO) throws Exception {
		HistoricoBaseConhecimentoDAO historicoBaseConhecimentoDao = new HistoricoBaseConhecimentoDAO();
		Collection<HistoricoBaseConhecimentoDTO> listaHistoricoBaseConhecimentoDTOs = null;
		try{
			listaHistoricoBaseConhecimentoDTOs = historicoBaseConhecimentoDao.list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return listaHistoricoBaseConhecimentoDTOs;
	}
}
