/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CategoriaServicoDTO;
import br.com.centralit.citcorpore.integracao.BaseConhecimentoDAO;
import br.com.centralit.citcorpore.integracao.CategoriaServicoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes","unchecked"})
public class CategoriaServicoServiceEjb extends CrudServicePojoImpl implements CategoriaServicoService {

	private static final long serialVersionUID = -2253183314661440900L;

	protected CrudDAO getDao() throws ServiceException {
		return new CategoriaServicoDao();
	}

	@Override
	public Collection listCategoriasAtivas() throws Exception {
		CategoriaServicoDao categoriaServicoDao = (CategoriaServicoDao) this.getDao();

		return categoriaServicoDao.listCategoriasAtivas();
	}

	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	public Collection list(String ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	@Override
	public boolean verificarSeCategoriaPossuiServicoOuSubCategoria(CategoriaServicoDTO categoriaServico) throws PersistenceException, ServiceException {
		CategoriaServicoDao categoriaServicoDao = (CategoriaServicoDao) this.getDao();

		if (categoriaServicoDao.verificarSeCategoriaPossuiFilho(categoriaServico) || categoriaServicoDao.verificarSeCategoriaPossuiServico(categoriaServico)) {
			return true;
		} else {
			return false;
		}
	}

	protected void validaCreate(Object obj) throws Exception {
	}

	protected void validaDelete(Object obj) throws Exception {
	}

	protected void validaUpdate(Object obj) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}

	@Override
	public boolean verificarSeCategoriaExiste(CategoriaServicoDTO categoriaServicoDTO) throws PersistenceException, ServiceException {
		CategoriaServicoDao categoriaDao = (CategoriaServicoDao) this.getDao();

		return categoriaDao.verificarSeCategoriaExiste(categoriaServicoDTO);
	}
	public Collection listHierarquia() throws Exception {
		CategoriaServicoDao dao = new CategoriaServicoDao();
		Collection colFinal = new ArrayList();
		try {
			Collection col = dao.findSemPai();
			if (col != null) {
				for (Iterator it = col.iterator(); it.hasNext();) {
				    	CategoriaServicoDTO dto = (CategoriaServicoDTO) it.next();
				    	dto.setNivel(0);
					colFinal.add(dto);
					Collection colAux = getCollectionHierarquia(dto.getIdCategoriaServico(), 0);
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

	
	public Collection getCollectionHierarquia(Integer idUnidade, Integer nivel) throws Exception {
		CategoriaServicoDao dao = new CategoriaServicoDao();
		Collection col = dao.findByIdPai(idUnidade);
		Collection colFinal = new ArrayList();
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
			    	CategoriaServicoDTO dto = (CategoriaServicoDTO) it.next();
			    	dto.setNivel(nivel + 1);
				colFinal.add(dto);
				Collection colAux = getCollectionHierarquia(dto.getIdCategoriaServico(), dto.getNivel());
				if (colAux != null && colAux.size() > 0) {
					colFinal.addAll(colAux);
				}
			}
		}
		return colFinal;
	}
	
	
	  public List<CategoriaServicoDTO> listCategoriaHierarquia (CategoriaServicoDTO categoriaServicoDTO,  List<CategoriaServicoDTO> listCategoriaHierarquia ) throws Exception {
		  CategoriaServicoDao  dao = new CategoriaServicoDao();
		  CategoriaServicoDTO bean = new CategoriaServicoDTO();
	    	listCategoriaHierarquia.add(categoriaServicoDTO);
			if(categoriaServicoDTO.getIdCategoriaServicoPai() != null){
				   bean.setIdCategoriaServico(categoriaServicoDTO.getIdCategoriaServicoPai());
				   bean = (CategoriaServicoDTO) dao.restore(bean);
				
				  if(bean.getIdCategoriaServicoPai() != null){
					   listCategoriaHierarquia(bean,  listCategoriaHierarquia);
				}else{
					listCategoriaHierarquia.add(bean);
				}
			}
			return  listCategoriaHierarquia;
		}
	
	    @Override
		public String verificaIdCategoriaServico(HashMap mapFields) throws Exception {
			CategoriaServicoDao dao = new CategoriaServicoDao();
			List<CategoriaServicoDTO> listaCategoriaServico = null;
			
			String id = mapFields.get("IDCATEGORIASERVICO").toString().trim();
			if ((id==null)||(id.equals(""))){
				return "0";
			}
			if (UtilStrings.soContemNumeros(id)) {
				Integer idCategoriaServico = (Integer) Integer.parseInt(id);
				listaCategoriaServico = dao.findByIdCategoriaServico(idCategoriaServico);
			} else {
				listaCategoriaServico = dao.findByNomeCategoria(id);
			}
			if((listaCategoriaServico != null)&&(listaCategoriaServico.size()>0)){
				return String.valueOf(listaCategoriaServico.get(0).getIdCategoriaServico());
			}else{
				return "0";
			}
		}
}
