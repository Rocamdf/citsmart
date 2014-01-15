package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.FaixaValoresRecursoDTO;
import br.com.centralit.citcorpore.bean.RecursoDTO;
import br.com.centralit.citcorpore.integracao.FaixaValoresRecursoDao;
import br.com.centralit.citcorpore.integracao.RecursoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

public class RecursoServiceEjb extends CrudServicePojoImpl implements
		RecursoService {
	protected CrudDAO getDao() throws ServiceException {
		return new RecursoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdGrupoRecurso(Integer parm) throws Exception {
		RecursoDao dao = new RecursoDao();
		try {
			return dao.findByIdGrupoRecurso(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdGrupoRecurso(Integer parm) throws Exception {
		RecursoDao dao = new RecursoDao();
		try {
			dao.deleteByIdGrupoRecurso(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public IDto create(IDto model) throws ServiceException, LogicException {
		// Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		FaixaValoresRecursoDao faixaDao = new FaixaValoresRecursoDao();
		TransactionControler tc = new TransactionControlerImpl(
				crudDao.getAliasDB());
		try {
			// Faz validacao, caso exista.
			validaCreate(model);

			// Instancia ou obtem os DAOs necessarios.

			// Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			faixaDao.setTransactionControler(tc);

			// Inicia transacao
			tc.start();

			// Executa operacoes pertinentes ao negocio.
			model = crudDao.create(model);
			RecursoDTO objDto = (RecursoDTO) model;
			if (objDto.getColFaixasValores() != null) {
				for (Iterator it = objDto.getColFaixasValores().iterator(); it
						.hasNext();) {
					FaixaValoresRecursoDTO faixaDto = (FaixaValoresRecursoDTO) it
							.next();
					faixaDto.setIdRecurso(objDto.getIdRecurso());

					faixaDao.create(faixaDto);
				}
			}

			// Faz commit e fecha a transacao.
			tc.commit();
			tc.close();

			return model;
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
		return model;
	}

	public void update(IDto model) throws ServiceException, LogicException {
		// Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		FaixaValoresRecursoDao faixaDao = new FaixaValoresRecursoDao();
		TransactionControler tc = new TransactionControlerImpl(
				crudDao.getAliasDB());
		try {
			// Faz validacao, caso exista.
			validaUpdate(model);

			// Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			faixaDao.setTransactionControler(tc);

			// Inicia transacao
			tc.start();

			RecursoDTO objDto = (RecursoDTO) model;

			// Executa operacoes pertinentes ao negocio.
			crudDao.update(model);
			faixaDao.deleteByIdRecurso(objDto.getIdRecurso());

			if (objDto.getColFaixasValores() != null) {
				for (Iterator it = objDto.getColFaixasValores().iterator(); it
						.hasNext();) {
					FaixaValoresRecursoDTO faixaDto = (FaixaValoresRecursoDTO) it
							.next();
					faixaDto.setIdRecurso(objDto.getIdRecurso());

					faixaDao.create(faixaDto);
				}
			}

			// Faz commit e fecha a transacao.
			tc.commit();
			tc.close();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
	}

	@Override
	public IDto restore(IDto model) throws ServiceException, LogicException {
		RecursoDTO recursoDTO = (RecursoDTO) super.restore(model);
		if (recursoDTO != null) {
			FaixaValoresRecursoDao faixaDao = new FaixaValoresRecursoDao();
			Collection col;
			try {
				col = faixaDao.findByIdRecurso(recursoDTO.getIdRecurso());
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
			recursoDTO.setColFaixasValores(col);
		}
		return (IDto) recursoDTO;
	}

	public Collection findByHostName(String hostName) throws Exception {
		RecursoDao dao = new RecursoDao();
		try {
			return dao.findByHostName(hostName);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
