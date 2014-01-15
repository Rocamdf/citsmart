package br.com.centralit.citcorpore.negocio;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.ImportConfigCamposDTO;
import br.com.centralit.citcorpore.bean.ImportConfigDTO;
import br.com.centralit.citcorpore.integracao.ImportConfigCamposDao;
import br.com.centralit.citcorpore.integracao.ImportConfigDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
public class ImportConfigServiceEjb extends CrudServicePojoImpl implements ImportConfigService {
	protected CrudDAO getDao() throws ServiceException {
		return new ImportConfigDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	public IDto create( IDto model) throws ServiceException, LogicException{
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		ImportConfigDTO importConfigDTO = (ImportConfigDTO)model;
		ImportConfigCamposDao importConfigCamposDao = new ImportConfigCamposDao();
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			
			//Instancia ou obtem os DAOs necessarios.
			
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			importConfigCamposDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			model = crudDao.create(model);
			
			if (importConfigDTO.getColDadosCampos() != null){
				for (Iterator it = importConfigDTO.getColDadosCampos().iterator(); it.hasNext();){
					ImportConfigCamposDTO importConfigCamposDTO = (ImportConfigCamposDTO)it.next();
					importConfigCamposDTO.setIdImportConfig(importConfigDTO.getIdImportConfig());
					importConfigCamposDao.create(importConfigCamposDTO);
				}
			}
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			

			return model;
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
		return model;
	}	
	
	public void update( IDto model) throws ServiceException, LogicException{
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		ImportConfigDTO importConfigDTO = (ImportConfigDTO)model;
		ImportConfigCamposDao importConfigCamposDao = new ImportConfigCamposDao();		
		try{
			//Faz validacao, caso exista.
			validaUpdate(model);

			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			importConfigCamposDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			crudDao.update(model);
			
			importConfigCamposDao.deleteByIdImportConfig(importConfigDTO.getIdImportConfig());
			if (importConfigDTO.getColDadosCampos() != null){
				for (Iterator it = importConfigDTO.getColDadosCampos().iterator(); it.hasNext();){
					ImportConfigCamposDTO importConfigCamposDTO = (ImportConfigCamposDTO)it.next();
					importConfigCamposDTO.setIdImportConfig(importConfigDTO.getIdImportConfig());
					importConfigCamposDao.create(importConfigCamposDTO);
				}
			}
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			

		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}		
	}	

}
