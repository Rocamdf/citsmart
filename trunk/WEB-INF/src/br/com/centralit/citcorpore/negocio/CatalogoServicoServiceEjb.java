package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.CatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.ServContratoCatalogoServDTO;
import br.com.centralit.citcorpore.integracao.CatalogoServicoDao;
import br.com.centralit.citcorpore.integracao.InfoCatalogoServicoDao;
import br.com.centralit.citcorpore.integracao.ServContratoCatalogoServDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

public class CatalogoServicoServiceEjb extends CrudServicePojoImpl implements CatalogoServicoService {
	
	private static final long serialVersionUID = -2253183314661440900L;
    
	protected CrudDAO getDao() throws ServiceException {
		return new CatalogoServicoDao();
	}
	
	private CatalogoServicoDao catalogoServicoDao() throws ServiceException {
		return (CatalogoServicoDao) getDao();
	}
	
	protected void validaCreate(Object obj) throws Exception {
		
	}
	
	protected void validaDelete(Object obj) throws Exception {
		
	}
	
	protected void validaUpdate(Object obj) throws Exception {
		
	}
	
	protected void validaFind(Object obj) throws Exception {
		
	}
	
	 public IDto create(IDto model) throws ServiceException, LogicException
	    {
		 	
		 CatalogoServicoDTO catalogoServicoDto = (CatalogoServicoDTO) model;
		 CatalogoServicoDao catalogoServicodao = (CatalogoServicoDao)getDao();
		 
		 ServContratoCatalogoServDao servContratoCatalogoServDao = new ServContratoCatalogoServDao();
		 InfoCatalogoServicoDao infoCatalogoServicoDao = new InfoCatalogoServicoDao();
		 
	        TransactionControler tc = new TransactionControlerImpl(catalogoServicodao.getAliasDB());
	        
	        try{
	            validaCreate(model);
	            catalogoServicodao.setTransactionControler(tc);
	            infoCatalogoServicoDao.setTransactionControler(tc);
	            servContratoCatalogoServDao.setTransactionControler(tc);
	            tc.start();
	            catalogoServicoDto = (CatalogoServicoDTO)catalogoServicodao.create(model);
	            
	            if(catalogoServicoDto.getColServicoContrato() != null && !catalogoServicoDto.getColServicoContrato().isEmpty()){
	            	ServContratoCatalogoServDTO item = null;
	            	//varre a lista de serviços
	                for(int i = 0; i < catalogoServicoDto.getColServicoContrato().size(); i++){
	                	item = (ServContratoCatalogoServDTO) catalogoServicoDto.getColServicoContrato().get(i);
	                	item.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());
	                	
	                	// grava cada item da lista
	                	servContratoCatalogoServDao.create(item);
	                }
	            }
	            
	            if(catalogoServicoDto.getColInfoCatalogoServico() != null && !catalogoServicoDto.getColInfoCatalogoServico().isEmpty()){
	            	InfoCatalogoServicoDTO item = null;
	            	//varre a lista de serviços
	                for(int i = 0; i < catalogoServicoDto.getColInfoCatalogoServico().size(); i++){
	                	item = (InfoCatalogoServicoDTO) catalogoServicoDto.getColInfoCatalogoServico().get(i);
	                		if(catalogoServicoDto.getIdCatalogoServico() != null){
	                			item.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());
	                		}
		                	if(catalogoServicoDto.getNomeInfoCatalogoServico() != null){
		                		item.setNomeInfoCatalogoServico(catalogoServicoDto.getNomeInfoCatalogoServico());
		                	}
		                	if(catalogoServicoDto.getDescInfoCatalogoServico() != null){
		                		item.setDescInfoCatalogoServico(catalogoServicoDto.getDescInfoCatalogoServico());
		                	}
	                	// grava cada item da lista
		                	if(item.getIdInfoCatalogoServico() == null){
		                		infoCatalogoServicoDao.create(item);
		                	}else{
		                		infoCatalogoServicoDao.update(item);
		                	}
	                }
	            }
	            
	            
	            tc.commit();
	            tc.close();
	        }catch(Exception e){
	            e.printStackTrace();
	            this.rollbackTransaction(tc, e);
	        }
		 
		 
			return catalogoServicoDto;
		 
	    }
	 
	 public void update(IDto model) throws ServiceException, LogicException
	    {
		 	
		 CatalogoServicoDTO catalogoServicoDto = (CatalogoServicoDTO) model;
		 CatalogoServicoDao catalogoServicodao = (CatalogoServicoDao)getDao();
		 
		 ServContratoCatalogoServDao servContratoCatalogoServDao = new ServContratoCatalogoServDao();
		 InfoCatalogoServicoDao infoCatalogoServicoDao = new InfoCatalogoServicoDao();
		 
	        TransactionControler tc = new TransactionControlerImpl(catalogoServicodao.getAliasDB());
	        
	        try{
	            validaCreate(model);
	            catalogoServicodao.setTransactionControler(tc);
	            infoCatalogoServicoDao.setTransactionControler(tc);
	            servContratoCatalogoServDao.setTransactionControler(tc);
	            tc.start();
	            catalogoServicodao.update(catalogoServicoDto); 
	            servContratoCatalogoServDao.deleteByIdServContratoCatalogo(catalogoServicoDto);
	            if(catalogoServicoDto.getColServicoContrato() != null && !catalogoServicoDto.getColServicoContrato().isEmpty()){
	            	ServContratoCatalogoServDTO item = null;
	            	//varre a lista de serviços
	                for(int i = 0; i < catalogoServicoDto.getColServicoContrato().size(); i++){
	                	item = (ServContratoCatalogoServDTO) catalogoServicoDto.getColServicoContrato().get(i);
	                	item.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());
	                	// grava cada item da lista
	                	servContratoCatalogoServDao.create(item);
	                }
	            }
	            
	            infoCatalogoServicoDao.deleteByIdInfoCatalogo(catalogoServicoDto);
	            if(catalogoServicoDto.getColInfoCatalogoServico() != null && !catalogoServicoDto.getColInfoCatalogoServico().isEmpty()){
	            	 
	            	InfoCatalogoServicoDTO item = null;
	            	//varre a lista de serviços
	                for(int i = 0; i < catalogoServicoDto.getColInfoCatalogoServico().size(); i++){
	                	item = (InfoCatalogoServicoDTO) catalogoServicoDto.getColInfoCatalogoServico().get(i);
	                	item.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());
	                	// grava cada item da lista
	                	infoCatalogoServicoDao.create(item);
	                }
	            }
	            
	            
	            tc.commit();
	            tc.close();
	        }catch(Exception e){
	            e.printStackTrace();
	            this.rollbackTransaction(tc, e);
	        }
		 
	    }
	 
	 public IDto restore(IDto model) throws ServiceException, LogicException  {
         CatalogoServicoDTO catalogoServicoDto = null;
         CatalogoServicoDao dao = (CatalogoServicoDao)getDao();
         try{
        	 catalogoServicoDto = (CatalogoServicoDTO)dao.restore(model);
             ServContratoCatalogoServDTO servContratoCatalogoServDTO = new ServContratoCatalogoServDTO();
             //SERVCONTRATOCATALOGOSERVICO
             servContratoCatalogoServDTO.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());
             catalogoServicoDto.setColServicoContrato(new ArrayList( new ServContratoCatalogoServDao().findByIdCatalogo(servContratoCatalogoServDTO)));
             
             InfoCatalogoServicoDTO infoCatalogoServicoDTO = new InfoCatalogoServicoDTO();
             infoCatalogoServicoDTO.setIdCatalogoServico(catalogoServicoDto.getIdCatalogoServico());
             catalogoServicoDto.setColInfoCatalogoServico(new ArrayList( new InfoCatalogoServicoDao().findByIdInfoCatalogo(infoCatalogoServicoDTO)));
         }catch(Exception e){
             e.printStackTrace();
             throw new ServiceException(e);
         }
         return catalogoServicoDto;
     }

	@Override
	public Collection<CatalogoServicoDTO> listAllCatalogos() throws ServiceException, Exception {
		return this.catalogoServicoDao().listAllCatalogos();
	}

	@Override
	public boolean verificaSeCatalogoExiste(CatalogoServicoDTO catalogoServicoDTO) throws PersistenceException, ServiceException {
		return this.catalogoServicoDao().verificaSeCatalogoExiste(catalogoServicoDTO);
	}
	
	@Override
	public Collection<CatalogoServicoDTO> listByIdContrato(Integer idContrato) throws ServiceException, Exception {
		return this.catalogoServicoDao().listByIdContrato(idContrato);
	}

}
