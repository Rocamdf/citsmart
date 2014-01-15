package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.CategoriaMudancaDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.integracao.CategoriaMudancaDao;
import br.com.centralit.citcorpore.integracao.PastaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("serial")
public class CategoriaMudancaServiceEjb extends CrudServicePojoImpl implements CategoriaMudancaService {
	protected CrudDAO getDao() throws ServiceException {
		return new CategoriaMudancaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	@SuppressWarnings("rawtypes")
	public Collection findByIdCategoriaMudanca(Integer parm) throws Exception {
		CategoriaMudancaDao dao = new CategoriaMudancaDao();
		try {
			return dao.findByIdCategoriaMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdCategoriaMudanca(Integer parm) throws Exception {
		CategoriaMudancaDao dao = new CategoriaMudancaDao();
		try {
			dao.deleteByIdCategoriaMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public Collection findByIdCategoriaMudancaPai(Integer parm) throws Exception {
		CategoriaMudancaDao dao = new CategoriaMudancaDao();
		try {
			return dao.findByIdCategoriaMudancaPai(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdCategoriaMudancaPai(Integer parm) throws Exception {
		CategoriaMudancaDao dao = new CategoriaMudancaDao();
		try {
			dao.deleteByIdCategoriaMudancaPai(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public Collection findByNomeCategoria(Integer parm) throws Exception {
		CategoriaMudancaDao dao = new CategoriaMudancaDao();
		try {
			return dao.findByNomeCategoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByNomeCategoria(Integer parm) throws Exception {
		CategoriaMudancaDao dao = new CategoriaMudancaDao();
		try {
			dao.deleteByNomeCategoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	//////
	@SuppressWarnings("rawtypes")
	public Collection listHierarquia() throws Exception {
		CategoriaMudancaDao dao = new CategoriaMudancaDao();
		Collection colFinal = new ArrayList();
		try {
			Collection col = dao.findCategoriaMudancaSemPai();
			if (col != null) {
				for (Iterator it = col.iterator(); it.hasNext();) {
					CategoriaMudancaDTO categoriaMudancaDto = (CategoriaMudancaDTO) it.next();
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

	private Collection getCollectionHierarquia(CategoriaMudancaDTO idCategoriaMudanca, Integer nivel) throws Exception {
		CategoriaMudancaDao dao = new CategoriaMudancaDao();
		Collection col = dao.findByIdCategoriaMudancaPai(idCategoriaMudanca.getIdCategoriaMudancaPai());
		Collection colFinal = new ArrayList();
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				CategoriaMudancaDTO categoriaMudancaDto = (CategoriaMudancaDTO) it.next();
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
	public Collection findCategoriaAtivos() {
		// TODO Auto-generated method stub
		return null;
	}

}
