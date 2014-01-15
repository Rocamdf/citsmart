package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.SolucaoContornoDTO;
import br.com.centralit.citcorpore.integracao.SolucaoContornoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

public class SolucaoContornoServiceEjb extends CrudServicePojoImpl implements
		SolucaoContornoService {

	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new SolucaoContornoDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public SolucaoContornoDTO findSolucaoContorno(SolucaoContornoDTO solucaoContorno) throws Exception {
		SolucaoContornoDao solucaoContornoDao = new SolucaoContornoDao();
		return (SolucaoContornoDTO) solucaoContornoDao.restore(solucaoContorno);
	}
	
	public SolucaoContornoDTO findByIdProblema(SolucaoContornoDTO solucaoContorno) throws Exception {
		SolucaoContornoDao solucaoContornoDao = new SolucaoContornoDao();
		return (SolucaoContornoDTO) solucaoContornoDao.findByIdProblema(solucaoContorno);
	}

	public SolucaoContornoDTO create(SolucaoContornoDTO solucaoContornoDto, TransactionControler tc) throws Exception {
		solucaoContornoDto.setDataHoraCriacao(UtilDatas.getDataHoraAtual());
		SolucaoContornoDao solucaoContornoDao = new SolucaoContornoDao();

		if (tc == null) {
			tc = new TransactionControlerImpl(this.getDao().getAliasDB());
		}

		solucaoContornoDao.setTransactionControler(tc);
		
		return (SolucaoContornoDTO) solucaoContornoDao.create(solucaoContornoDto);

	}

}
