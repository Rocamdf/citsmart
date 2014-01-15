package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.centralit.citcorpore.integracao.TipoMudancaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings({"serial","rawtypes","unchecked"})

public class TipoMudancaServiceEjb extends CrudServicePojoImpl implements TipoMudancaService{

	@Override
	public Collection findByIdTipoMudanca(Integer parm) throws Exception {
		TipoMudancaDAO dao = new TipoMudancaDAO();
		try {
			return dao.findByIdTipoMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByIdTipoMudanca(Integer parm) throws Exception {	
		TipoMudancaDAO dao = new TipoMudancaDAO();
		try{
			dao.deleteByIdTipoMudanca(parm);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection findByNomeTipoMudanca(Integer parm) throws Exception {
		TipoMudancaDAO dao = new TipoMudancaDAO();
		try{
			return dao.findByNomeTipoMudanca(parm);
		}catch(ServiceException e){
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByNomeTipoMudanca(Integer parm) throws Exception {
		TipoMudancaDAO dao = new TipoMudancaDAO();
		try{
			dao.findByNomeTipoMudanca(parm);
		}catch(ServiceException e){
			throw new ServiceException(e);
		}
		
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return new TipoMudancaDAO();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	public Collection<TipoMudancaDTO> tiposAtivosPorNome(String nome){
		List condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("nomeTipoMudanca", "=",nome));
		condicoes.add(new Condition("datafim", "!=", "null"));
		try {
			return ((TipoMudancaDAO)getDao()).findByCondition(condicoes, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<TipoMudancaDTO>();
	
	}

	@Override
	public boolean verificarTipoMudancaAtivos(TipoMudancaDTO obj)
			throws Exception {
		TipoMudancaDAO dao = new TipoMudancaDAO();
		return dao.verificarTipoMudancaAtivos(obj);
	}

	@Override
	public Collection encontrarPorNomeTipoMudanca(TipoMudancaDTO obj)
			throws Exception {
		TipoMudancaDAO dao = new TipoMudancaDAO();
		
		return dao.encontrarPorNomeTipoMudanca(obj);
	}

	@Override
	public Collection getAtivos() throws Exception {
		TipoMudancaDAO dao = new TipoMudancaDAO();	
		return dao.getAtivos();
	}


}

