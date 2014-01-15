package br.com.citframework.service;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.dto.LookupDTO;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.LookupDao;
import br.com.citframework.integracao.LookupProcessDefaultDao;
import br.com.citframework.util.LookupFieldUtil;

public class LookupProcessServiceEjb extends EjbSessionBean implements LookupProcessService {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4614784844380756777L;
	protected  Usuario usuario;
	
	protected LookupProcessDefaultDao getDao(LookupDTO lookup) throws ServiceException {
    	LookupFieldUtil lookupUtil = new LookupFieldUtil();
    	String daoProcess = lookupUtil.getDaoProcessor(lookup.getNomeLookup());
    	if (daoProcess.equalsIgnoreCase("DEFAULT")){
    		return new LookupProcessDefaultDao();
    	}else{
    		try {
				Class classe = Class.forName(daoProcess);
				return (LookupProcessDefaultDao) classe.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
    	}
    }
	@Override
	public Collection process(Object obj, HttpServletRequest request) throws RemoteException, LogicException, ServiceException {
		LookupDTO lookup = (LookupDTO)obj;
		
		LookupDao dao = (LookupDao)getDao(lookup);
		if (dao == null){
			throw new ServiceException("Nao encontrado DAO adequado para processamento deste Lookup!");
		}
		try {
			return dao.processLookup(lookup, request);
		} catch (LogicException e) {
			throw new LogicException(e);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	protected LookupProcessDefaultDao getDao() throws ServiceException {
		return new LookupProcessDefaultDao();
	}
	protected void validaCreate(Object obj) throws Exception {
	}
	protected void validaUpdate(Object obj) throws Exception {
	}
	protected void validaDelete(Object obj) throws Exception {
	}
	public void setUsuario(Usuario usr) {
		this.usuario = usr;
		
	}
}
