package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.CamposObjetoNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
public class ObjetoNegocioServiceEjb extends CrudServicePojoImpl implements ObjetoNegocioService {
	protected CrudDAO getDao() throws ServiceException {
		return new ObjetoNegocioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	public Collection listAtivos() throws Exception {
		ObjetoNegocioDao dao = new ObjetoNegocioDao();
		return dao.listAtivos();		
	}
	
	public Collection findByNomeTabelaDB(String nomeTabelaDBParm) throws Exception {
		ObjetoNegocioDao dao = new ObjetoNegocioDao();
		return dao.findByNomeTabelaDB(nomeTabelaDBParm);
	}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			
			//Instancia ou obtem os DAOs necessarios.
			
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			camposObjetoNegocioDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			model = crudDao.create(model);
			ObjetoNegocioDTO objNegocioDto = (ObjetoNegocioDTO)model;
			if (objNegocioDto.getColCampos() != null){
				for(Iterator it = objNegocioDto.getColCampos().iterator(); it.hasNext();){
					CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
					camposObjetoNegocioDTO.setIdObjetoNegocio(objNegocioDto.getIdObjetoNegocio());
					camposObjetoNegocioDao.create(camposObjetoNegocioDTO);
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

	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			
			//Instancia ou obtem os DAOs necessarios.
			
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			camposObjetoNegocioDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			crudDao.update(model);
			String strCamposDB = "";
			ObjetoNegocioDTO objNegocioDto = (ObjetoNegocioDTO)model;
			if (objNegocioDto.getColCampos() != null){
				for(Iterator it = objNegocioDto.getColCampos().iterator(); it.hasNext();){
					CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO)it.next();
					camposObjetoNegocioDTO.setIdObjetoNegocio(objNegocioDto.getIdObjetoNegocio());
					
					Collection colCampos = camposObjetoNegocioDao.findByIdObjetoNegocioAndNomeDB(camposObjetoNegocioDTO.getIdObjetoNegocio(), camposObjetoNegocioDTO.getNomeDB());
					if (colCampos == null || colCampos.size() == 0){
						camposObjetoNegocioDao.create(camposObjetoNegocioDTO);
					}else{
						CamposObjetoNegocioDTO camposObjetoNegocioAux = (CamposObjetoNegocioDTO)((List)colCampos).get(0);
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(camposObjetoNegocioAux.getIdCamposObjetoNegocio());
						camposObjetoNegocioDao.update(camposObjetoNegocioDTO);
					}
					if (strCamposDB != null && !strCamposDB.trim().equalsIgnoreCase("")){
						strCamposDB = strCamposDB + ",";
					}
					strCamposDB = strCamposDB + "'" + camposObjetoNegocioDTO.getNomeDB() + "'";
				}
			}
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			try{
				camposObjetoNegocioDao.deleteFromNOTRelNomeDB(strCamposDB, objNegocioDto.getIdObjetoNegocio());
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}

	public ObjetoNegocioDTO findByNomeObjetoNegocio(String nomeObjetoNegocio) throws Exception {
		ObjetoNegocioDao dao = new ObjetoNegocioDao();
		return dao.findByNomeObjetoNegocio(nomeObjetoNegocio);
	}	
	public ObjetoNegocioDTO getByNomeTabelaDB(String nomeObjetoNegocio) throws Exception {
		ObjetoNegocioDao dao = new ObjetoNegocioDao();
		return dao.getByNomeTabelaDB(nomeObjetoNegocio);		
	}

	@Override
	public void updateDisable(ObjetoNegocioDTO objetoNegocioDTO) throws Exception {
		super.update(objetoNegocioDTO);		
	}
	
	
}
