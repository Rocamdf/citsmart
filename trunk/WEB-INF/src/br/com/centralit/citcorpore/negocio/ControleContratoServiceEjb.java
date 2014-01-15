package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.CatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.ControleContratoDTO;
import br.com.centralit.citcorpore.bean.ControleContratoModuloSistemaDTO;
import br.com.centralit.citcorpore.bean.ControleContratoOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ControleContratoPagamentoDTO;
import br.com.centralit.citcorpore.bean.ControleContratoTreinamentoDTO;
import br.com.centralit.citcorpore.bean.ControleContratoVersaoDTO;
import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.ServContratoCatalogoServDTO;
import br.com.centralit.citcorpore.integracao.CatalogoServicoDao;
import br.com.centralit.citcorpore.integracao.ControleContratoDao;
import br.com.centralit.citcorpore.integracao.ControleContratoModuloSistemaDao;
import br.com.centralit.citcorpore.integracao.ControleContratoOcorrenciaDao;
import br.com.centralit.citcorpore.integracao.ControleContratoPagamentoDao;
import br.com.centralit.citcorpore.integracao.ControleContratoTreinamentoDao;
import br.com.centralit.citcorpore.integracao.ControleContratoVersaoDao;
import br.com.centralit.citcorpore.integracao.InfoCatalogoServicoDao;
import br.com.centralit.citcorpore.integracao.ServContratoCatalogoServDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author Pedro
 *
 */


public class ControleContratoServiceEjb extends CrudServicePojoImpl implements ControleContratoService {
	
	private static final long serialVersionUID = -2253183314661440900L;
    
	protected CrudDAO getDao() throws ServiceException {
		return new ControleContratoDao(); 
	}
	
	private ControleContratoDao controleContratoDao() throws ServiceException {
		return (ControleContratoDao) getDao();
	}
	
	protected void validaCreate(Object obj) throws Exception {
		
	}
	
	protected void validaDelete(Object obj) throws Exception {
		
	}
	
	protected void validaUpdate(Object obj) throws Exception {
		
	}
	
	protected void validaFind(Object obj) throws Exception {
		
	}
	
	 /* (non-Javadoc)
	 * @see br.com.citframework.service.CrudServicePojoImpl#create(br.com.citframework.dto.IDto)
	 * 
	 */
	public IDto create(IDto model) throws ServiceException, LogicException
	    {
		 	
		 ControleContratoDTO controleContratoDto = (ControleContratoDTO) model;
		 ControleContratoDao controleContratoDao = (ControleContratoDao)getDao();
	
		 ControleContratoVersaoDao controleContratoVersaoDao = new ControleContratoVersaoDao();
		 ControleContratoPagamentoDao controleContratoPagamentoDao = new ControleContratoPagamentoDao();
		 ControleContratoTreinamentoDao controleContratoTreinamentoDao = new ControleContratoTreinamentoDao();
		 ControleContratoOcorrenciaDao controleContratoOcorrenciaDao = new ControleContratoOcorrenciaDao();
		 ControleContratoModuloSistemaDao controleContratoModuloSistemaDao = new ControleContratoModuloSistemaDao();
		 
	        TransactionControler tc = new TransactionControlerImpl(controleContratoDao.getAliasDB());
	        
	        try{
	            validaCreate(model);
	            /** TC **/
	            controleContratoDao.setTransactionControler(tc);
	            controleContratoVersaoDao.setTransactionControler(tc);
	            controleContratoPagamentoDao.setTransactionControler(tc);
	            controleContratoTreinamentoDao.setTransactionControler(tc);
	            controleContratoOcorrenciaDao.setTransactionControler(tc);
	            controleContratoModuloSistemaDao.setTransactionControler(tc);
	            
	            tc.start();
	            /** MODEL **/
	            controleContratoDto = (ControleContratoDTO)controleContratoDao.create(model);
	            /** VERSAO **/
	            if(controleContratoDto.getLstVersao() != null && !controleContratoDto.getLstVersao().isEmpty()){
	            	ControleContratoVersaoDTO item = null;
	            	//varre a lista de serviços
	                for(int i = 0; i < controleContratoDto.getLstVersao().size(); i++){
	                	item = (ControleContratoVersaoDTO) controleContratoDto.getLstVersao().get(i);
	                	item.setIdControleContrato(controleContratoDto.getIdControleContrato());
	                	/*item.setIdCcVersao(controleContratoDto.getIdVersao());*/
	                	//item.setNomeCcVersao(controleContratoDto.getNomeCcVersao());
	                	// grava cada item da lista
	                	controleContratoVersaoDao.create(item);
	                }
	            }
	            /** PAGAMENTO **/
	            if(controleContratoDto.getLstPagamento() != null && !controleContratoDto.getLstPagamento().isEmpty()){
	            	ControleContratoPagamentoDTO itemPagamento = null;
	            	//varre a lista de serviços
	                for(int i = 0; i < controleContratoDto.getLstPagamento().size(); i++){
	                	itemPagamento = (ControleContratoPagamentoDTO) controleContratoDto.getLstPagamento().get(i);
	                	itemPagamento.setIdControleContrato(controleContratoDto.getIdControleContrato());
	           /*     	itemPagamento.setDataAtrasoCcPagamento(controleContratoDto.getDataAtrasoCcPagamento());
	                	itemPagamento.setDataCcPagamento(controleContratoDto.getDataCcPagamento());
	                	itemPagamento.setParcelaCcPagamento(controleContratoDto.getParcelaCcPagamento());*/
	                	// grava cada item da lista
	                	controleContratoPagamentoDao.create(itemPagamento);
	                }
	            }
	            /** TREINAMENTO **/
	            if(controleContratoDto.getLstTreinamento() != null && !controleContratoDto.getLstTreinamento().isEmpty()){
	            	ControleContratoTreinamentoDTO itemTreinamento = null;
	            	//varre a lista de serviços
	                for(int i = 0; i < controleContratoDto.getLstTreinamento().size(); i++){
	                	itemTreinamento = (ControleContratoTreinamentoDTO) controleContratoDto.getLstTreinamento().get(i);
	                	itemTreinamento.setIdControleContrato(controleContratoDto.getIdControleContrato());
	               /* 	itemTreinamento.setDataCcTreinamento(controleContratoDto.getDataCcTreinamento());
	                	itemTreinamento.setIdEmpregadoTreinamento(controleContratoDto.getIdEmpregadoTreinamento());*/
	                	
	                	// grava cada item da lista
	                	controleContratoTreinamentoDao.create(itemTreinamento);
	                }
	            }
	            /** OCORRENCIA **/
	            if(controleContratoDto.getLstOcorrencia() != null && !controleContratoDto.getLstOcorrencia().isEmpty()){
	            	ControleContratoOcorrenciaDTO itemOcorrencia = null;
	            	//varre a lista de serviços
	                for(int i = 0; i < controleContratoDto.getLstOcorrencia().size(); i++){
	                	itemOcorrencia = (ControleContratoOcorrenciaDTO) controleContratoDto.getLstOcorrencia().get(i);
	                	itemOcorrencia.setIdControleContrato(controleContratoDto.getIdControleContrato());
	                /*	itemOcorrencia.setAssuntoCcOcorrencia(controleContratoDto.getAssuntoCcOcorrencia());
	                	itemOcorrencia.setDataCcOcorrencia(controleContratoDto.getDataCcOcorrencia());
	                	itemOcorrencia.setIdEmpregadoOcorrencia(controleContratoDto.getIdUsuarioOcorrencia());*/
	                	
	                	// grava cada item da lista
	                	controleContratoOcorrenciaDao.create(itemOcorrencia);
	                }
	            }
	            
	            /** MODULOS ATIVOS **/
	            if(controleContratoDto.getLstModulosAtivos() != null && !controleContratoDto.getLstModulosAtivos().isEmpty()){
	            	ControleContratoModuloSistemaDTO item = new ControleContratoModuloSistemaDTO();
	            	//varre a lista de serviços
	                for(int i = 0; i < controleContratoDto.getLstModulosAtivos().size(); i++){
	                	item.setIdControleContrato(controleContratoDto.getIdControleContrato());
	                	item.setIdModuloSistema(Integer.parseInt((String) controleContratoDto.getLstModulosAtivos().get(i)));
	                	// grava cada item da lista
	                	controleContratoModuloSistemaDao.create(item);
	                }
	            }
	            
	            tc.commit();
	            tc.close();
	        }catch(Exception e){
	            e.printStackTrace();
	            this.rollbackTransaction(tc, e);
	        }
		 
		 
			return controleContratoDto;
		 
	    }
	 
 
	 /* (non-Javadoc)
	 * @see br.com.citframework.service.CrudServicePojoImpl#update(br.com.citframework.dto.IDto)
	 */
	public void update(IDto model) throws ServiceException, LogicException
	    {
		 ControleContratoDTO controleContratoDto = (ControleContratoDTO) model;
		 ControleContratoDao controleContratoDao = (ControleContratoDao)getDao();
	
		 ControleContratoVersaoDao controleContratoVersaoDao = new ControleContratoVersaoDao();
		 ControleContratoPagamentoDao controleContratoPagamentoDao = new ControleContratoPagamentoDao();
		 ControleContratoTreinamentoDao controleContratoTreinamentoDao = new ControleContratoTreinamentoDao();
		 ControleContratoOcorrenciaDao controleContratoOcorrenciaDao = new ControleContratoOcorrenciaDao();
		 ControleContratoModuloSistemaDao controleContratoModuloSistemaDao = new ControleContratoModuloSistemaDao();
		 
	        TransactionControler tc = new TransactionControlerImpl(controleContratoDao.getAliasDB());
	
	        
	        try{
	        	
	        	   validaCreate(model);
		            /** TC **/
		            controleContratoDao.setTransactionControler(tc);
		            controleContratoVersaoDao.setTransactionControler(tc);
		            controleContratoPagamentoDao.setTransactionControler(tc);
		            controleContratoTreinamentoDao.setTransactionControler(tc);
		            controleContratoOcorrenciaDao.setTransactionControler(tc);
		            controleContratoModuloSistemaDao.setTransactionControler(tc);
		            
		            tc.start();
		            /** MODEL **/
		           
		            controleContratoDao.update(controleContratoDto);
		            /** VERSAO **/
		            controleContratoVersaoDao.deleteByIdControleContrato(controleContratoDto); //delete
		            if(controleContratoDto.getLstVersao() != null && !controleContratoDto.getLstVersao().isEmpty()){
		            	ControleContratoVersaoDTO item = null;
		            	//varre a lista de serviços
		                for(int i = 0; i < controleContratoDto.getLstVersao().size(); i++){
		                	item = (ControleContratoVersaoDTO) controleContratoDto.getLstVersao().get(i);
		                	item.setIdControleContrato(controleContratoDto.getIdControleContrato());
		                	/*item.setIdCcVersao(controleContratoDto.getIdVersao());*/
		                	//item.setNomeCcVersao(controleContratoDto.getNomeCcVersao());
		                	// grava cada item da lista
		                	controleContratoVersaoDao.create(item);
		                }
		            }
		            /** PAGAMENTO **/
		            controleContratoPagamentoDao.deleteByIdControleContrato(controleContratoDto); //delete
		            if(controleContratoDto.getLstPagamento() != null && !controleContratoDto.getLstPagamento().isEmpty()){
		            	ControleContratoPagamentoDTO itemPagamento = null;
		            	//varre a lista de serviços
		                for(int i = 0; i < controleContratoDto.getLstPagamento().size(); i++){
		                	itemPagamento = (ControleContratoPagamentoDTO) controleContratoDto.getLstPagamento().get(i);
		                	itemPagamento.setIdControleContrato(controleContratoDto.getIdControleContrato());
		                	/*itemPagamento.setDataAtrasoCcPagamento(controleContratoDto.getDataAtrasoCcPagamento());
		                	itemPagamento.setDataCcPagamento(controleContratoDto.getDataCcPagamento());
		                	itemPagamento.setParcelaCcPagamento(controleContratoDto.getParcelaCcPagamento());
		                	// grava cada item da lista
*/		                	controleContratoPagamentoDao.create(itemPagamento);
		                }
		            }
		            /** TREINAMENTO **/
		            controleContratoTreinamentoDao.deleteByIdControleContrato(controleContratoDto); //delete
		            if(controleContratoDto.getLstTreinamento() != null && !controleContratoDto.getLstTreinamento().isEmpty()){
		            	ControleContratoTreinamentoDTO itemTreinamento = null;
		            	//varre a lista de serviços
		                for(int i = 0; i < controleContratoDto.getLstTreinamento().size(); i++){
		                	itemTreinamento = (ControleContratoTreinamentoDTO) controleContratoDto.getLstTreinamento().get(i);
		                	itemTreinamento.setIdControleContrato(controleContratoDto.getIdControleContrato());
		                /*	itemTreinamento.setDataCcTreinamento(controleContratoDto.getDataCcTreinamento());
		                	itemTreinamento.setIdEmpregadoTreinamento(controleContratoDto.getIdEmpregadoTreinamento());*/
		                	
		                	// grava cada item da lista
		                	controleContratoTreinamentoDao.create(itemTreinamento);
		                }
		            }
		            /** OCORRENCIA **/
		            controleContratoOcorrenciaDao.deleteByIdControleContrato(controleContratoDto); //delete
		            if(controleContratoDto.getLstOcorrencia() != null && !controleContratoDto.getLstOcorrencia().isEmpty()){
		            	ControleContratoOcorrenciaDTO itemOcorrencia = null;
		            	//varre a lista de serviços
		                for(int i = 0; i < controleContratoDto.getLstOcorrencia().size(); i++){
		                	itemOcorrencia = (ControleContratoOcorrenciaDTO) controleContratoDto.getLstOcorrencia().get(i);
		                	itemOcorrencia.setIdControleContrato(controleContratoDto.getIdControleContrato());
		                	/*itemOcorrencia.setAssuntoCcOcorrencia(controleContratoDto.getAssuntoCcOcorrencia());
		                	itemOcorrencia.setDataCcOcorrencia(controleContratoDto.getDataCcOcorrencia());
		                	itemOcorrencia.setIdEmpregadoOcorrencia(controleContratoDto.getIdUsuarioOcorrencia());*/
		                	
		                	// grava cada item da lista
		                	controleContratoOcorrenciaDao.create(itemOcorrencia);
		                }
		            }
		            
		            /** MODULOS ATIVOS **/
		            controleContratoModuloSistemaDao.deleteByIdControleContrato(controleContratoDto); //delete
		            if(controleContratoDto.getLstModulosAtivos() != null && !controleContratoDto.getLstModulosAtivos().isEmpty()){
		            	ControleContratoModuloSistemaDTO item = new ControleContratoModuloSistemaDTO();
		            	//varre a lista de serviços
		                for(int i = 0; i < controleContratoDto.getLstModulosAtivos().size(); i++){
		                	item.setIdControleContrato(controleContratoDto.getIdControleContrato());
		                	item.setIdModuloSistema(Integer.parseInt((String) controleContratoDto.getLstModulosAtivos().get(i)));
		                	// grava cada item da lista
		                	controleContratoModuloSistemaDao.create(item);
		                }
		            }
	            
	            tc.commit();
	            tc.close();
	        }catch(Exception e){
	            e.printStackTrace();
	            this.rollbackTransaction(tc, e);
	        }
		 
	    }
	 
	 /* (non-Javadoc)
	 * @see br.com.citframework.service.CrudServicePojoImpl#restore(br.com.citframework.dto.IDto)
	 */
	public IDto restore(IDto model) throws ServiceException, LogicException  {
		 ControleContratoDTO controleContratoDto = (ControleContratoDTO) model;
		 ControleContratoDao controleContratoDao = (ControleContratoDao)getDao();
	
		 ControleContratoVersaoDao controleContratoVersaoDao = new ControleContratoVersaoDao();
		 ControleContratoPagamentoDao controleContratoPagamentoDao = new ControleContratoPagamentoDao();
		 ControleContratoTreinamentoDao controleContratoTreinamentoDao = new ControleContratoTreinamentoDao();
		 ControleContratoOcorrenciaDao controleContratoOcorrenciaDao = new ControleContratoOcorrenciaDao();
		 ControleContratoModuloSistemaDao controleContratoModuloSistemaDao = new ControleContratoModuloSistemaDao();
         try{
        	 controleContratoDto = (ControleContratoDTO) controleContratoDao.restore(model);
        	 ControleContratoVersaoDTO controleContratoVersaoDTO = new ControleContratoVersaoDTO();
        	 controleContratoVersaoDTO.setIdControleContrato(controleContratoDto.getIdControleContrato());
        	 controleContratoDto.setLstVersao(new ArrayList( new ControleContratoVersaoDao().findByIdControleContrato(controleContratoVersaoDTO)));
        	 
        	 ControleContratoPagamentoDTO controleContratoPagamentoDTO = new ControleContratoPagamentoDTO();
        	 controleContratoPagamentoDTO.setIdControleContrato(controleContratoDto.getIdControleContrato());
        	 controleContratoDto.setLstPagamento(new ArrayList( new ControleContratoPagamentoDao().findByIdControleContrato(controleContratoPagamentoDTO)));
        	 
        	 ControleContratoTreinamentoDTO controleContratoTreinamentoDTO = new ControleContratoTreinamentoDTO();
        	 controleContratoTreinamentoDTO.setIdControleContrato(controleContratoDto.getIdControleContrato());
        	 controleContratoDto.setLstTreinamento(new ArrayList( new ControleContratoTreinamentoDao().findByIdControleContrato(controleContratoTreinamentoDTO)));
        	 
        	 ControleContratoOcorrenciaDTO controleContratoOcorrenciaDTO = new ControleContratoOcorrenciaDTO();
        	 controleContratoOcorrenciaDTO.setIdControleContrato(controleContratoDto.getIdControleContrato());
        	 controleContratoDto.setLstOcorrencia(new ArrayList( new ControleContratoOcorrenciaDao().findByIdControleContrato(controleContratoOcorrenciaDTO)));
        	 
        	 ControleContratoModuloSistemaDTO controleContratoModuloSistemaDTO = new ControleContratoModuloSistemaDTO();
        	 controleContratoModuloSistemaDTO.setIdControleContrato(controleContratoDto.getIdControleContrato());
        	 controleContratoDto.setLstModulosAtivos(new ArrayList( new ControleContratoModuloSistemaDao().findByIdControleContrato(controleContratoModuloSistemaDTO)));

         }catch(Exception e){
             e.printStackTrace();
             throw new ServiceException(e);
         }
         return controleContratoDto;
     }

	/*@Override
	public Collection<CatalogoServicoDTO> listAllCatalogos() throws ServiceException, Exception {
		return this.controleContratoDao().list();
	}
*/
	/*@Override
	public boolean verificaSeCatalogoExiste(CatalogoServicoDTO catalogoServicoDTO) throws PersistenceException, ServiceException {
		return this.catalogoServicoDao().verificaSeCatalogoExiste(catalogoServicoDTO);
	}*/

}
