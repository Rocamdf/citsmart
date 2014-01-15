
package br.com.citframework.service;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.LogEstrutura;
import br.com.citframework.dto.LogTabela;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.LogEstruturaDao;
import br.com.citframework.integracao.LogTabelaDao;
import br.com.citframework.integracao.MetaDataDao;
import br.com.citframework.integracao.TransactionControler;

/**
 * @author karem.ricarte
 *
 */
public class LogTabelaServiceBean extends CrudServiceEjb2Impl implements LogTabelaService {


	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new LogTabelaDao(usuario);
	}

	protected void validaCreate(Object arg0) throws Exception {
		
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
		
	}

	public LogTabela getLogByTabela(String nomeTabela) throws RemoteException, ServiceException {
			LogTabelaDao tBdao = (LogTabelaDao)getDao();
			try {
				LogTabela lt = tBdao.getLogByTabela(nomeTabela);
				if(lt!=null){
					return lt;
				}else{
					lt = new LogTabela();
					lt.setNomeTabela(nomeTabela);
					lt = (LogTabela)create(lt);
				    return lt;	
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}	
	}

	public IDto create(IDto model) throws ServiceException, RemoteException, LogicException {
		LogTabelaDao tBdao = (LogTabelaDao)getDao();
		LogEstruturaDao eSDao = new LogEstruturaDao(usuario);
		MetaDataDao  mdDao = new MetaDataDao();
		
		TransactionControler tc = null;
		try {
			tc = tBdao.getTransactionControler();
			mdDao.setTransactionControler(tc);
			eSDao.setTransactionControler(tc);
			tc.start();
			model  =tBdao.create(model);
			Collection col = mdDao.getCamposByTabela(((LogTabela)model).getNomeTabela());
			String str = "";
			if(col!=null){
				Iterator it = col.iterator();
				while(it.hasNext()){
					if(str.trim().length()>0){
						str+=";";
					}
					str+=it.next();
				}
			}
			LogEstrutura le = new LogEstrutura();
			le.setLogTabela_idlog(((LogTabela)model).getIdLog());
			le.setEstrutura(str);
			eSDao.create(le);
			
			
			
			tc.commit();
			tc.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			rollbackTransaction(tc, e);
		}
		return model;
		
		
		
	}
	
	
	
	

}
