/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaPostDTO;
import br.com.centralit.citcorpore.integracao.CategoriaPostDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class CategoriaPostServiceEjb extends CrudServicePojoImpl implements CategoriaPostService {

	private static final long serialVersionUID = -2253183314661440900L;

	protected CrudDAO getDao() throws ServiceException {
		return new CategoriaPostDao();
	}

	@Override
	public Collection listCategoriasAtivas() throws Exception {
		CategoriaPostDao categoriaPostDTO = (CategoriaPostDao) this.getDao();

		return categoriaPostDTO.listCategoriasAtivas();
	}

	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	public Collection list(String ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
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
	public boolean verificarSeCategoriaExiste(CategoriaPostDTO categoriaPostDTO) throws PersistenceException, ServiceException {
		CategoriaPostDao categoriaDao = (CategoriaPostDao) this.getDao();

		return categoriaDao.verificarSeCategoriaExiste(categoriaPostDTO);
	}
		
  public List<CategoriaPostDTO> listCategoriaHierarquia (CategoriaPostDTO categoriaServicoDTO,  List<CategoriaPostDTO> listCategoriaHierarquia ) throws Exception {
	  CategoriaPostDao  dao = new CategoriaPostDao();
	  CategoriaPostDTO bean = new CategoriaPostDTO();
    	listCategoriaHierarquia.add(categoriaServicoDTO);
		if(categoriaServicoDTO.getIdCategoriaPostPai() != null){
			   bean.setIdCategoriaPost(categoriaServicoDTO.getIdCategoriaPostPai());
			   bean = (CategoriaPostDTO) dao.restore(bean);
			
			  if(bean.getIdCategoriaPostPai() != null){
				   listCategoriaHierarquia(bean,  listCategoriaHierarquia);
			}else{
				listCategoriaHierarquia.add(bean);
			}
		}
		return  listCategoriaHierarquia;
	}

	@Override
	public boolean verificarSeCategoriaPossuiServicoOuSubCategoria(CategoriaPostDTO categoriaPostDTO) throws PersistenceException, ServiceException {
		CategoriaPostDao categoriaPostDao = (CategoriaPostDao) this.getDao();

		if (categoriaPostDao.verificarSeCategoriaPossuiFilho(categoriaPostDTO) || categoriaPostDao.verificarSeCategoriaPossuiPost(categoriaPostDTO)) {
			return true;
		} else {
			return false;
		}
	}


	

}
