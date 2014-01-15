package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoAcaoCurriculoDTO;
import br.com.centralit.citcorpore.integracao.HistoricoAcaoCurriculoDao;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.integracao.CertificacaoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.CompetenciaCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.CurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.EmailCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.EnderecoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.ExperienciaProfissionalCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.FormacaoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.IdiomaCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.TelefoneCurriculoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author ygor.magalhaes
 *
 */
@SuppressWarnings({"serial","rawtypes"})
public class HistoricoAcaoCurriculoServiceEjb extends CrudServicePojoImpl implements
	HistoricoAcaoCurriculoService {

    protected CrudDAO getDao() throws ServiceException {
	return new HistoricoAcaoCurriculoDao();
    }

    protected void validaCreate(Object arg0) throws Exception {
    }

    protected void validaDelete(Object arg0) throws Exception {
    }

    protected void validaUpdate(Object arg0) throws Exception {
    }

    protected void validaFind(Object obj) throws Exception {
    }

	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
	return null;
    }

    public Collection list(String ordenacao) throws LogicException, RemoteException,
	    ServiceException {
	return null;
    }
    
    public IDto create(IDto model) throws ServiceException, LogicException{
    	
    	CrudDAO crudDao = getDao();
    	
		CurriculoDao curriculoDao = new CurriculoDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		
		HistoricoAcaoCurriculoDTO acaoCurriculoDTO = (HistoricoAcaoCurriculoDTO) model;
		CurriculoDTO curriculoDTO = new CurriculoDTO();
    	
    	try {

			// Faz validacao, caso exista.

			validaCreate(model);

			// Seta o TransactionController para os DAOs

			crudDao.setTransactionControler(tc);
			curriculoDao.setTransactionControler(tc);
			
			curriculoDTO.setIdCurriculo(acaoCurriculoDTO.getIdCurriculo());
			curriculoDTO = (CurriculoDTO) curriculoDao.restore(curriculoDTO);
			
			curriculoDTO.setListaNegra(acaoCurriculoDTO.getAcao());
			curriculoDao.update(curriculoDTO);
			
			model = crudDao.create(acaoCurriculoDTO);
			
    	}catch(Exception ex){
    		this.rollbackTransaction(tc, ex);
    	}
    	
		return model;

	}

	@Override
	public Collection listByIdCurriculo(Integer idCurriculo) throws Exception {
		HistoricoAcaoCurriculoDao dao = new HistoricoAcaoCurriculoDao();
		return dao.listByIdCurriculo(idCurriculo);
	}
    

}
