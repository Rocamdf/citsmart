package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CriterioAvaliacaoFornecedorDTO;
import br.com.centralit.citcorpore.integracao.CriterioAvaliacaoFornecedorDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class CriterioAvaliacaoFornecedorServiceEjb extends CrudServicePojoImpl implements CriterioAvaliacaoFornecedorService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		return new CriterioAvaliacaoFornecedorDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdCriterio(Integer parm) throws Exception {
		CriterioAvaliacaoFornecedorDao dao = new CriterioAvaliacaoFornecedorDao();
		try {
			return dao.findByIdCriterio(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdCriterio(Integer parm) throws Exception {
		CriterioAvaliacaoFornecedorDao dao = new CriterioAvaliacaoFornecedorDao();
		try {
			dao.deleteByIdCriterio(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<CriterioAvaliacaoFornecedorDTO> listByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws Exception {
		CriterioAvaliacaoFornecedorDao dao = new CriterioAvaliacaoFornecedorDao();
		Collection<CriterioAvaliacaoFornecedorDTO> listCriterioAvaliacaoFornecedor =  dao.listByIdAvaliacaoFornecedor(idAvaliacaoFornecedor);
		try {
			if(listCriterioAvaliacaoFornecedor!=null){
				for(CriterioAvaliacaoFornecedorDTO criterioAvaliacaoFornecedor : listCriterioAvaliacaoFornecedor){
					if(criterioAvaliacaoFornecedor.getValorInteger() == 1){
						criterioAvaliacaoFornecedor.setValor("Sim");
					}else{
						if(criterioAvaliacaoFornecedor.getValorInteger()==0){
							criterioAvaliacaoFornecedor.setValor("Não");
						}else{
							criterioAvaliacaoFornecedor.setValor("N/A");
						}
					}
				}
			}
			return listCriterioAvaliacaoFornecedor;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws Exception {
		CriterioAvaliacaoFornecedorDao dao = new CriterioAvaliacaoFornecedorDao();
		try {
			dao.deleteByIdAvaliacaoFornecedor(idAvaliacaoFornecedor);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
}
