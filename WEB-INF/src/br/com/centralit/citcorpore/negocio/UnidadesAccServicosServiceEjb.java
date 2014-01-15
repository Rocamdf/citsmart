package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.centralit.citcorpore.integracao.UnidadesAccServicosDao;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({"serial","rawtypes","unchecked"})
public class UnidadesAccServicosServiceEjb extends CrudServicePojoImpl implements UnidadesAccServicosService {
	protected CrudDAO getDao() throws ServiceException {
		return new UnidadesAccServicosDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdUnidade(Integer parm) throws Exception{
		UnidadesAccServicosDao dao = new UnidadesAccServicosDao();
		try{
			return dao.findByIdUnidade(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdUnidade(Integer parm) throws Exception{
		UnidadesAccServicosDao dao = new UnidadesAccServicosDao();
		try{
			dao.deleteByIdUnidade(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdServico(Integer parm) throws Exception{
		UnidadesAccServicosDao dao = new UnidadesAccServicosDao();
		try{
			return dao.findByIdServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdServico(Integer parm) throws Exception{
		UnidadesAccServicosDao dao = new UnidadesAccServicosDao();
		try{
			dao.deleteByIdServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public List deserealizaObjetosDoRequest(HttpServletRequest request) throws Exception{
		return (List) WebUtil.deserializeCollectionFromRequest(UnidadesAccServicosDTO.class, "servicosSerializados", request);
	}
	
	public Collection<UnidadesAccServicosDTO> consultarServicosAtivosPorUnidade(Integer idUnidade) throws Exception{
		UnidadesAccServicosDao dao = new UnidadesAccServicosDao();
		try{
			return (List) dao.consultaServicosPorUnidade(idUnidade);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void excluirAssociacaoServicosUnidade(Integer idUnidade,	Integer idServico) throws PersistenceException, ServiceException, Exception {
		UnidadesAccServicosDao dao = new UnidadesAccServicosDao();
		try{
			dao.deleteByIdServicoUnidade(idUnidade, idServico);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
