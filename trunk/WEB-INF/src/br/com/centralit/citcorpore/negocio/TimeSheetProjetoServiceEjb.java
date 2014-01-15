package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.RecursoTarefaLinBaseProjDTO;
import br.com.centralit.citcorpore.bean.TarefaLinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.bean.TimeSheetProjetoDTO;
import br.com.centralit.citcorpore.integracao.RecursoTarefaLinBaseProjDao;
import br.com.centralit.citcorpore.integracao.TarefaLinhaBaseProjetoDao;
import br.com.centralit.citcorpore.integracao.TimeSheetProjetoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
public class TimeSheetProjetoServiceEjb extends CrudServicePojoImpl implements TimeSheetProjetoService {
	protected CrudDAO getDao() throws ServiceException {
		return new TimeSheetProjetoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdRecursoTarefaLinBaseProj(Integer idRecursoTarefaLinBaseProj, Integer idEmpregado) throws Exception{
		TimeSheetProjetoDao dao = new TimeSheetProjetoDao();
		TarefaLinhaBaseProjetoDao tarefaLinhaBaseProjetoDao = new TarefaLinhaBaseProjetoDao();
		RecursoTarefaLinBaseProjDao recursoTarefaLinBaseProjDao = new RecursoTarefaLinBaseProjDao();
		Collection col = null;
		try{
			col = dao.findByIdRecursoTarefaLinBaseProj(idRecursoTarefaLinBaseProj);
			RecursoTarefaLinBaseProjDTO recursoTarefaLinBaseProjDTO = new RecursoTarefaLinBaseProjDTO();
			recursoTarefaLinBaseProjDTO.setIdRecursoTarefaLinBaseProj(idRecursoTarefaLinBaseProj);
			recursoTarefaLinBaseProjDTO = (RecursoTarefaLinBaseProjDTO) recursoTarefaLinBaseProjDao.restore(recursoTarefaLinBaseProjDTO);
			if (recursoTarefaLinBaseProjDTO != null){
				TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = new TarefaLinhaBaseProjetoDTO();
				tarefaLinhaBaseProjetoAux.setIdTarefaLinhaBaseProjeto(recursoTarefaLinBaseProjDTO.getIdTarefaLinhaBaseProjeto());
				tarefaLinhaBaseProjetoAux = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.restore(tarefaLinhaBaseProjetoAux);
				if (tarefaLinhaBaseProjetoAux != null){
					Collection colRecursos = recursoTarefaLinBaseProjDao.findByIdTarefaLinhaBaseProjetoAndIdEmpregado(tarefaLinhaBaseProjetoAux.getIdTarefaLinhaBaseProjetoMigr(), idEmpregado);
					if (colRecursos != null){
						for (Iterator itRec = colRecursos.iterator(); itRec.hasNext();){
							RecursoTarefaLinBaseProjDTO recursoTarefaLinBaseProjAux = (RecursoTarefaLinBaseProjDTO)itRec.next();
							Collection colAux = findByIdRecursoTarefaLinBaseProj(recursoTarefaLinBaseProjAux.getIdRecursoTarefaLinBaseProj(), idEmpregado);
							if (colAux != null){
								if (col == null){
									col = new ArrayList();
								}
								col.addAll(colAux);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return col;
	}
	public void deleteByIdRecursoTarefaLinBaseProj(Integer parm) throws Exception{
		TimeSheetProjetoDao dao = new TimeSheetProjetoDao();
		try{
			dao.deleteByIdRecursoTarefaLinBaseProj(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		TimeSheetProjetoDao crudDao = new TimeSheetProjetoDao();
		RecursoTarefaLinBaseProjDao recursoTarefaLinBaseProjDao = new RecursoTarefaLinBaseProjDao();
		TarefaLinhaBaseProjetoDao tarefaLinhaBaseProjetoDao = new TarefaLinhaBaseProjetoDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		TarefaLinhaBaseProjetoServiceEjb tarefaLinhaBaseProjetoServiceEjb = new TarefaLinhaBaseProjetoServiceEjb();
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			
			//Instancia ou obtem os DAOs necessarios.
			TimeSheetProjetoDTO timeSheetProjetoDTO = (TimeSheetProjetoDTO)model;
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			recursoTarefaLinBaseProjDao.setTransactionControler(tc);
			tarefaLinhaBaseProjetoDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			
			//Executa operacoes pertinentes ao negocio.
			model = crudDao.create(model);
			
			RecursoTarefaLinBaseProjDTO recursoTarefaLinBaseProjDTO = new RecursoTarefaLinBaseProjDTO();
			recursoTarefaLinBaseProjDTO.setIdRecursoTarefaLinBaseProj(timeSheetProjetoDTO.getIdRecursoTarefaLinBaseProj());
			recursoTarefaLinBaseProjDTO = (RecursoTarefaLinBaseProjDTO) recursoTarefaLinBaseProjDao.restore(recursoTarefaLinBaseProjDTO);
			if (recursoTarefaLinBaseProjDTO != null){
				if (recursoTarefaLinBaseProjDTO.getPercentualAloc() == null){
					recursoTarefaLinBaseProjDTO.setPercentualAloc(new Double(100));
				}
				if (recursoTarefaLinBaseProjDTO.getPercentualAloc() >= 100){
					TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = new TarefaLinhaBaseProjetoDTO();
					tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjeto(recursoTarefaLinBaseProjDTO.getIdTarefaLinhaBaseProjeto());
					tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.restore(tarefaLinhaBaseProjetoDTO);
					if (tarefaLinhaBaseProjetoDTO != null){
						TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = new TarefaLinhaBaseProjetoDTO();
						tarefaLinhaBaseProjetoAux.setIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
						tarefaLinhaBaseProjetoAux.setProgresso(timeSheetProjetoDTO.getPercExecutado());
						if (timeSheetProjetoDTO.getPercExecutado() != null && timeSheetProjetoDTO.getPercExecutado().doubleValue() >= 100){
							tarefaLinhaBaseProjetoAux.setSituacao(TarefaLinhaBaseProjetoDTO.PRONTO);
						}
						tarefaLinhaBaseProjetoDao.updateNotNull(tarefaLinhaBaseProjetoAux);
					}
				}else{
					TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = new TarefaLinhaBaseProjetoDTO();
					tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjeto(recursoTarefaLinBaseProjDTO.getIdTarefaLinhaBaseProjeto());
					tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.restore(tarefaLinhaBaseProjetoDTO);
					if (tarefaLinhaBaseProjetoDTO != null){
						Collection colTarefasLnBase = recursoTarefaLinBaseProjDao.findByIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
						double total = 0;
						if (colTarefasLnBase != null){
							for (Iterator it = colTarefasLnBase.iterator(); it.hasNext();){
								RecursoTarefaLinBaseProjDTO recursoTarefaLinBaseProjAux = (RecursoTarefaLinBaseProjDTO)it.next();
								double x = 0;
								if (recursoTarefaLinBaseProjAux.getPercentualAloc() == null || recursoTarefaLinBaseProjAux.getPercentualAloc().doubleValue() == 0){
									recursoTarefaLinBaseProjAux.setPercentualAloc(new Double(100));
								}	
								if (recursoTarefaLinBaseProjAux.getIdRecursoTarefaLinBaseProj().intValue() == timeSheetProjetoDTO.getIdRecursoTarefaLinBaseProj().intValue()){
									recursoTarefaLinBaseProjAux.setPercentualExec(timeSheetProjetoDTO.getPercExecutado());
									
									RecursoTarefaLinBaseProjDTO recursoTarefaLinBaseProjAux2 = new RecursoTarefaLinBaseProjDTO();
									recursoTarefaLinBaseProjAux2.setIdRecursoTarefaLinBaseProj(recursoTarefaLinBaseProjAux.getIdRecursoTarefaLinBaseProj());
									recursoTarefaLinBaseProjAux2.setPercentualExec(recursoTarefaLinBaseProjAux.getPercentualExec());
									recursoTarefaLinBaseProjDao.updateNotNull(recursoTarefaLinBaseProjAux2);
								}else{
									if (recursoTarefaLinBaseProjAux.getPercentualExec() == null){
										recursoTarefaLinBaseProjAux.setPercentualExec(new Double(0));
									}
								}
								x = (recursoTarefaLinBaseProjAux.getPercentualExec() / 100) * recursoTarefaLinBaseProjAux.getPercentualAloc();
								total = total + x;
							}
						}
						
						TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = new TarefaLinhaBaseProjetoDTO();
						tarefaLinhaBaseProjetoAux.setIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjeto());
						tarefaLinhaBaseProjetoAux.setProgresso(total);
						if (total >= 100){
							tarefaLinhaBaseProjetoAux.setSituacao(TarefaLinhaBaseProjetoDTO.PRONTO);
						}						
						tarefaLinhaBaseProjetoDao.updateNotNull(tarefaLinhaBaseProjetoAux);
					}					
				}
				tarefaLinhaBaseProjetoServiceEjb.atualizaInfoProporcionais(tc, recursoTarefaLinBaseProjDTO.getIdTarefaLinhaBaseProjeto());
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
}
