package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AvaliacaoReferenciaFornecedorDTO;
import br.com.centralit.citcorpore.integracao.AvaliacaoReferenciaFornecedorDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class AvaliacaoReferenciaFornecedorServiceEjb extends CrudServicePojoImpl implements AvaliacaoReferenciaFornecedorService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		return new AvaliacaoReferenciaFornecedorDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	@Override
	public Collection<AvaliacaoReferenciaFornecedorDTO> listByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws Exception {
		AvaliacaoReferenciaFornecedorDao dao = new AvaliacaoReferenciaFornecedorDao();

		Collection<AvaliacaoReferenciaFornecedorDTO> listAvaliacaoReferenciaFornecedor = dao.listByIdAvaliacaoFornecedor(idAvaliacaoFornecedor);

		if (listAvaliacaoReferenciaFornecedor != null) {
			for (AvaliacaoReferenciaFornecedorDTO avaliacaoReferenciaFornecedor : listAvaliacaoReferenciaFornecedor) {
				if (avaliacaoReferenciaFornecedor.getDecisao().equalsIgnoreCase("S")) {
					avaliacaoReferenciaFornecedor.setDecisao("Sim");
				} else {
					avaliacaoReferenciaFornecedor.setDecisao("Não");
				}
			}
		}

		return listAvaliacaoReferenciaFornecedor;
	}

	public void deleteByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws Exception {
		AvaliacaoReferenciaFornecedorDao dao = new AvaliacaoReferenciaFornecedorDao();
		dao.deleteByIdAvaliacaoFornecedor(idAvaliacaoFornecedor);
	}

}
