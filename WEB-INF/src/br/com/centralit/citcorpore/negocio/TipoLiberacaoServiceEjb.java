package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoLiberacaoDTO;
import br.com.centralit.citcorpore.integracao.TipoLiberacaoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings({"serial","rawtypes","unchecked"})

public class TipoLiberacaoServiceEjb extends CrudServicePojoImpl implements TipoLiberacaoService{

	@Override
	public Collection findByIdTipoLiberacao(Integer parm) throws Exception {
		TipoLiberacaoDAO dao = new TipoLiberacaoDAO();
		try {
			return dao.findByIdTipoLiberacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByIdTipoLiberacao(Integer parm) throws Exception {	
		TipoLiberacaoDAO dao = new TipoLiberacaoDAO();
		try{
			dao.deleteByIdTipoLiberacao(parm);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection findByNomeTipoLiberacao(Integer parm) throws Exception {
		TipoLiberacaoDAO dao = new TipoLiberacaoDAO();
		try{
			return dao.findByNomeTipoLiberacao(parm);
		}catch(ServiceException e){
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByNomeTipoLiberacao(Integer parm) throws Exception {
		TipoLiberacaoDAO dao = new TipoLiberacaoDAO();
		try{
			dao.findByNomeTipoLiberacao(parm);
		}catch(ServiceException e){
			throw new ServiceException(e);
		}
		
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return new TipoLiberacaoDAO();
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
	
	
	public Collection<TipoLiberacaoDTO> tiposAtivosPorNome(String nome){
		List condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("nomeTipoLiberacao", "=",nome));
		condicoes.add(new Condition("datafim", "!=", "null"));
		try {
			return ((TipoLiberacaoDAO)getDao()).findByCondition(condicoes, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<TipoLiberacaoDTO>();
	
	}

	@Override
	public boolean verificarTipoLiberacaoAtivos(TipoLiberacaoDTO obj)
			throws Exception {
		TipoLiberacaoDAO dao = new TipoLiberacaoDAO();
		return dao.verificarTipoLiberacaoAtivos(obj);
	}

	@Override
	public Collection encontrarPorNomeTipoLiberacao(TipoLiberacaoDTO obj)
			throws Exception {
		TipoLiberacaoDAO dao = new TipoLiberacaoDAO();
		
		return dao.encontrarPorNomeTipoLiberacao(obj);
	}

	@Override
	public Collection getAtivos() throws Exception {
		TipoLiberacaoDAO dao = new TipoLiberacaoDAO();	
		return dao.getAtivos();
	}


}

