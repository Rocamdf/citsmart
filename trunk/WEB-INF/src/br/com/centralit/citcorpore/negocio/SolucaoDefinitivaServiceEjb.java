package br.com.centralit.citcorpore.negocio;


import br.com.centralit.citcorpore.bean.SolucaoDefinitivaDTO;
import br.com.centralit.citcorpore.integracao.SolucaoDefinitivaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

public class SolucaoDefinitivaServiceEjb extends CrudServicePojoImpl implements SolucaoDefinitivaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public SolucaoDefinitivaDTO findSolucaoDefinitiva(SolucaoDefinitivaDTO solucaoDefinitiva) throws Exception {
		return (SolucaoDefinitivaDTO) getDao().restore(solucaoDefinitiva);
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new SolucaoDefinitivaDAO();
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
	
	public SolucaoDefinitivaDTO create(SolucaoDefinitivaDTO solucaoDefinitivaDto, TransactionControler tc) throws Exception{
		solucaoDefinitivaDto.setDataHoraCriacao(UtilDatas.getDataHoraAtual());
		SolucaoDefinitivaDAO solucaoDefinitivaDao = new SolucaoDefinitivaDAO();
		
		if (tc == null) {
			tc = new TransactionControlerImpl(this.getDao().getAliasDB());
		}
		solucaoDefinitivaDao.setTransactionControler(tc);
		return (SolucaoDefinitivaDTO) solucaoDefinitivaDao.create(solucaoDefinitivaDto);
		
	}
	
	public SolucaoDefinitivaDTO findByIdProblema(SolucaoDefinitivaDTO solucaoDefinitiva) throws Exception {
		SolucaoDefinitivaDAO solucaoDefinitivaDAO = new SolucaoDefinitivaDAO();
		return (SolucaoDefinitivaDTO) solucaoDefinitivaDAO.findByIdProblema(solucaoDefinitiva);
	}
	
}
