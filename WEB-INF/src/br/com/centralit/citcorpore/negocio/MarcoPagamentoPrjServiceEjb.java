package br.com.centralit.citcorpore.negocio;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.MarcoPagamentoPrjDTO;
import br.com.centralit.citcorpore.integracao.MarcoPagamentoPrjDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
public class MarcoPagamentoPrjServiceEjb extends CrudServicePojoImpl implements MarcoPagamentoPrjService {
	protected CrudDAO getDao() throws ServiceException {
		return new MarcoPagamentoPrjDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdProjeto(Integer parm) throws Exception{
		MarcoPagamentoPrjDao dao = new MarcoPagamentoPrjDao();
		try{
			return dao.findByIdProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdProjeto(Integer parm) throws Exception{
		MarcoPagamentoPrjDao dao = new MarcoPagamentoPrjDao();
		try{
			dao.deleteByIdProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void saveFromCollection(Collection colItens, Integer idProjetoParm) throws ServiceException, LogicException{
		//Instancia Objeto controlador de transacao
		MarcoPagamentoPrjDao crudDao = new MarcoPagamentoPrjDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			for (Iterator it = colItens.iterator(); it.hasNext();){
				MarcoPagamentoPrjDTO model = (MarcoPagamentoPrjDTO)it.next();
				
				model.setDataUltAlteracao(UtilDatas.getDataAtual());
				String hora = UtilDatas.getHoraHHMM(UtilDatas.getDataHoraAtual()).replaceAll(":", "");
				model.setHoraUltAlteracao(hora);
				model.setUsuarioUltAlteracao(usuario.getNomeUsuario());
				if (model.getIdMarcoPagamentoPrj() != null && model.getIdMarcoPagamentoPrj().intValue() > 0){
					//Faz validacao, caso exista.
					validaUpdate(model);
					
					//Executa operacoes pertinentes ao negocio.
					crudDao.updateNotNull(model);					
				}else{
					model.setSituacao("E");
					
					//Faz validacao, caso exista.
					validaCreate(model);
					
					//Executa operacoes pertinentes ao negocio.
					model = (MarcoPagamentoPrjDTO)crudDao.create(model);
				}
			}
			crudDao.deleteByIdProjetoNotIn(idProjetoParm, colItens);
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			tc = null;
		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}
}
