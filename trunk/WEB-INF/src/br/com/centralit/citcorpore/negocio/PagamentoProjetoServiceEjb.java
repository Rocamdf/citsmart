package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.PagamentoProjetoDTO;
import br.com.centralit.citcorpore.bean.TarefaLinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.integracao.PagamentoProjetoDao;
import br.com.centralit.citcorpore.integracao.TarefaLinhaBaseProjetoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
public class PagamentoProjetoServiceEjb extends CrudServicePojoImpl implements PagamentoProjetoService {
	protected CrudDAO getDao() throws ServiceException {
		return new PagamentoProjetoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdProjeto(Integer parm) throws Exception{
		PagamentoProjetoDao dao = new PagamentoProjetoDao();
		try{
			return dao.findByIdProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdProjeto(Integer parm) throws Exception{
		PagamentoProjetoDao dao = new PagamentoProjetoDao();
		try{
			dao.deleteByIdProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		//Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		TarefaLinhaBaseProjetoDao tarefaLinhaBaseProjetoDao = new TarefaLinhaBaseProjetoDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaCreate(model);
			
			//Instancia ou obtem os DAOs necessarios.
			
			
			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			tarefaLinhaBaseProjetoDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			double valorPagamento = 0;
			
			//Executa operacoes pertinentes ao negocio.
			PagamentoProjetoDTO pagamentoProjetoDTO = (PagamentoProjetoDTO)model;
			pagamentoProjetoDTO.setValorPagamento(new Double(0));
			model = crudDao.create(model);
			if (pagamentoProjetoDTO.getIdTarefasParaPagamento() != null){ 
				for (int i = 0; i < pagamentoProjetoDTO.getIdTarefasParaPagamento().length; i++){
					TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = new TarefaLinhaBaseProjetoDTO();
					tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjeto(pagamentoProjetoDTO.getIdTarefasParaPagamento()[i]);
					TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.restore(tarefaLinhaBaseProjetoDTO);
					if (tarefaLinhaBaseProjetoAux != null){
						if (tarefaLinhaBaseProjetoAux.getCustoPerfil() != null){
							valorPagamento = valorPagamento + tarefaLinhaBaseProjetoAux.getCustoPerfil().doubleValue();
						}
					}
					tarefaLinhaBaseProjetoDTO.setIdPagamentoProjeto(pagamentoProjetoDTO.getIdPagamentoProjeto());
					tarefaLinhaBaseProjetoDao.updateNotNull(tarefaLinhaBaseProjetoDTO);
				}
			}
			pagamentoProjetoDTO.setValorPagamento(valorPagamento);
			crudDao.update(pagamentoProjetoDTO);
			
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
		TarefaLinhaBaseProjetoDao tarefaLinhaBaseProjetoDao = new TarefaLinhaBaseProjetoDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try{
			//Faz validacao, caso exista.
			validaUpdate(model);

			//Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			tarefaLinhaBaseProjetoDao.setTransactionControler(tc);
			
			//Inicia transacao
			tc.start();
			double valorPagamento = 0;
			
			//Executa operacoes pertinentes ao negocio.
			PagamentoProjetoDTO pagamentoProjetoDTO = (PagamentoProjetoDTO)model;
			pagamentoProjetoDTO.setValorPagamento(new Double(0));			
			crudDao.update(model);
			if (pagamentoProjetoDTO.getIdTarefasParaPagamento() != null){ 
				for (int i = 0; i < pagamentoProjetoDTO.getIdTarefasParaPagamento().length; i++){
					TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = new TarefaLinhaBaseProjetoDTO();
					tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjeto(pagamentoProjetoDTO.getIdTarefasParaPagamento()[i]);
					TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.restore(tarefaLinhaBaseProjetoDTO);
					if (tarefaLinhaBaseProjetoAux != null){
						if (tarefaLinhaBaseProjetoAux.getCustoPerfil() != null){
							valorPagamento = valorPagamento + tarefaLinhaBaseProjetoAux.getCustoPerfil().doubleValue();
						}
					}					
					tarefaLinhaBaseProjetoDTO.setIdPagamentoProjeto(pagamentoProjetoDTO.getIdPagamentoProjeto());
					tarefaLinhaBaseProjetoDao.updateNotNull(tarefaLinhaBaseProjetoDTO);
				}
			}
			pagamentoProjetoDTO.setValorPagamento(valorPagamento);
			crudDao.update(pagamentoProjetoDTO);			
			
			//Faz commit e fecha a transacao.
			tc.commit();
			tc.close();
			tc = null;

		}catch(Exception e){
			this.rollbackTransaction(tc, e);
		}
	}
	
	public void updateSituacao(IDto model) throws ServiceException, LogicException {
		PagamentoProjetoDTO pagamentoProjetoDTO = new PagamentoProjetoDTO();
		PagamentoProjetoDTO pagamentoProjetoParm = (PagamentoProjetoDTO)model;
		pagamentoProjetoDTO.setIdPagamentoProjeto(pagamentoProjetoParm.getIdPagamentoProjeto());
		pagamentoProjetoDTO.setSituacao("P");
		pagamentoProjetoDTO.setDataUltAlteracao(pagamentoProjetoParm.getDataUltAlteracao());
		pagamentoProjetoDTO.setHoraUltAlteracao(pagamentoProjetoParm.getHoraUltAlteracao());
		pagamentoProjetoDTO.setUsuarioUltAlteracao(pagamentoProjetoParm.getUsuarioUltAlteracao());
		pagamentoProjetoDTO.setDataPagamento(pagamentoProjetoParm.getDataPagamentoAtu());
		PagamentoProjetoDao dao = new PagamentoProjetoDao();
		try{
			dao.updateNotNull(pagamentoProjetoDTO);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	public Collection getTotaisByIdProjeto(Integer idProjetoParm) throws Exception{
		PagamentoProjetoDao dao = new PagamentoProjetoDao();
		try{
			return dao.getTotaisByIdProjeto(idProjetoParm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}			
	}
}
