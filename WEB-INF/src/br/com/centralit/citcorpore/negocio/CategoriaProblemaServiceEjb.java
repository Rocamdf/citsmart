package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.centralit.citcorpore.integracao.CategoriaProblemaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings({"rawtypes","unchecked"})
public class CategoriaProblemaServiceEjb extends CrudServicePojoImpl implements CategoriaProblemaService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		return new CategoriaProblemaDAO();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	
	public Collection findByIdCategoriaProblema(Integer parm) throws Exception {
		CategoriaProblemaDAO dao = new CategoriaProblemaDAO();
		try {
			return dao.findByIdCategoriaProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdCategoriaProblema(Integer parm) throws Exception {
		CategoriaProblemaDAO dao = new CategoriaProblemaDAO();
		try {
			dao.deleteByIdCategoriaProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByNomeCategoria(String parm) throws Exception {
		CategoriaProblemaDAO dao = new CategoriaProblemaDAO();
		try {
			return dao.findByNomeCategoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByNomeCategoria(String parm) throws Exception {
		CategoriaProblemaDAO dao = new CategoriaProblemaDAO();
		try {
			dao.deleteByNomeCategoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection listHierarquia() throws Exception {
		CategoriaProblemaDAO dao = new CategoriaProblemaDAO();
		Collection colFinal = new ArrayList();
		try {
			Collection col = dao.findCategoriaProblemaSemPai();
			if (col != null) {
				for (Iterator it = col.iterator(); it.hasNext();) {
					CategoriaProblemaDTO categoriaMudancaDto = (CategoriaProblemaDTO) it.next();
					categoriaMudancaDto.setNivel(0);
					colFinal.add(categoriaMudancaDto);
					Collection colAux = getCollectionHierarquia(categoriaMudancaDto, 0);
					if (colAux != null && colAux.size() > 0) {
						colFinal.addAll(colAux);
					}
				}
			}
			return colFinal;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private Collection getCollectionHierarquia(CategoriaProblemaDTO idCategoriaProblema, Integer nivel) throws Exception {
		CategoriaProblemaDAO dao = new CategoriaProblemaDAO();
		Collection col = dao.findByIdCategoriaProblemaPai(idCategoriaProblema.getIdCategoriaProblemaPai());
		Collection colFinal = new ArrayList();
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				CategoriaProblemaDTO categoriaMudancaDto = (CategoriaProblemaDTO) it.next();
				categoriaMudancaDto.setNivel(nivel + 1);
				colFinal.add(categoriaMudancaDto);
				Collection colAux = getCollectionHierarquia(categoriaMudancaDto, categoriaMudancaDto.getNivel());
				if (colAux != null && colAux.size() > 0) {
					colFinal.addAll(colAux);
				}
			}
		}
		return colFinal;
	}

	@Override
	public Collection findByNomeCategoriaProblema(
			CategoriaProblemaDTO categoriaProblemaDto) throws Exception {
		CategoriaProblemaDAO dao = new CategoriaProblemaDAO();
		try{
			return dao.findByNomeCategoriaProblema(categoriaProblemaDto);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Collection<CategoriaProblemaDTO> findByIdTemplate(Integer idTemplate) throws Exception {
		CategoriaProblemaDAO dao = new CategoriaProblemaDAO();
		return dao.findByIdTemplate(idTemplate);
	}
	
	@Override
	public void desvincularCategoriaProblemasRelacionadasTemplate(Integer idTemplate) throws Exception {
		CategoriaProblemaDAO categoriaProblemaDao = new CategoriaProblemaDAO();
		TransactionControler transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());
		categoriaProblemaDao.setTransactionControler(transactionControler);
		
		Collection<CategoriaProblemaDTO> listaCategoriaProblema = findByIdTemplate(idTemplate);
		
		if (listaCategoriaProblema != null && !listaCategoriaProblema.isEmpty()) {
			transactionControler.start();
			for (CategoriaProblemaDTO categoriaProblemaDTO : listaCategoriaProblema) {
				categoriaProblemaDTO.setIdTemplate(null);
				categoriaProblemaDao.update(categoriaProblemaDTO);
			}
			transactionControler.commit();
			transactionControler.close();
		}
	}

	@Override
	public Collection getAtivos() throws Exception {
		CategoriaProblemaDAO categoriaProblemaDao = new CategoriaProblemaDAO();
		return categoriaProblemaDao.getAtivos();
	}

	@Override
	public boolean consultarCategoriasAtivas(CategoriaProblemaDTO obj) throws Exception {
		CategoriaProblemaDAO categoriaProblemaDao = new CategoriaProblemaDAO();
		return categoriaProblemaDao.consultarCategoriasAtivas(obj);
	}
}
