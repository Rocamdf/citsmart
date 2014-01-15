package br.com.centralit.citcorpore.negocio;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.BIConsultaColunasDTO;
import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.centralit.citcorpore.integracao.BIConsultaColunasDao;
import br.com.centralit.citcorpore.integracao.BIConsultaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
public class BIConsultaServiceEjb extends CrudServicePojoImpl implements BIConsultaService {
	protected CrudDAO getDao() throws ServiceException {
		return new BIConsultaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		BIConsultaColunasDao biConsultaColunasDao = new BIConsultaColunasDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			
			//Instancia ou obtem os DAOs necessarios.
			
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			biConsultaColunasDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			model = crudDao.create(model);
			BIConsultaDTO biConsultaDTO = (BIConsultaDTO)model;
			if (biConsultaDTO.getColColunas() != null){
				for (Iterator it = biConsultaDTO.getColColunas().iterator(); it.hasNext();){
					BIConsultaColunasDTO biConsultaColunasDTO = (BIConsultaColunasDTO)it.next();
					biConsultaColunasDTO.setIdConsulta(biConsultaDTO.getIdConsulta());
					biConsultaColunasDao.create(biConsultaColunasDTO);
				}
			}
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			tc = null;

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
		BIConsultaColunasDao biConsultaColunasDao = new BIConsultaColunasDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaUpdate(model);

			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			biConsultaColunasDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			crudDao.update(model);
			BIConsultaDTO biConsultaDTO = (BIConsultaDTO)model;
			biConsultaColunasDao.deleteByIdConsulta(biConsultaDTO.getIdConsulta());
			if (biConsultaDTO.getColColunas() != null){
				for (Iterator it = biConsultaDTO.getColColunas().iterator(); it.hasNext();){
					BIConsultaColunasDTO biConsultaColunasDTO = (BIConsultaColunasDTO)it.next();
					biConsultaColunasDTO.setIdConsulta(biConsultaDTO.getIdConsulta());
					biConsultaColunasDao.create(biConsultaColunasDTO);
				}
			}
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			tc = null;

		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}
	
	public Collection findByIdCategoria(Integer parm) throws Exception{
		BIConsultaDao biConsultaDao = new BIConsultaDao();
		return biConsultaDao.findByIdCategoria(parm);
	}
	
	public BIConsultaDTO getByIdentificacao(String ident) throws Exception {
		BIConsultaDao biConsultaDao = new BIConsultaDao();
		return biConsultaDao.getByIdentificacao(ident);		
	}

}
