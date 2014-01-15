package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PrioridadeServicoUsuarioDTO;
import br.com.centralit.citcorpore.integracao.PrioridadeAcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.PrioridadeServicoUsuarioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.Order;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @since 14/06/2013
 * @author rodrigo.oliveira
 *
 */
public class PrioridadeServicoUsuarioServiceEjb extends CrudServicePojoImpl implements PrioridadeServicoUsuarioService {
	protected CrudDAO getDao() throws ServiceException {
		return new PrioridadeServicoUsuarioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	@Override
	public Collection findByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception {
		PrioridadeServicoUsuarioDao dao = new PrioridadeServicoUsuarioDao();
		try{
			return dao.findByIdAcordoNivelServico(idAcordoNivelServico);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public PrioridadeServicoUsuarioDTO findByIdAcordoNivelServicoAndIdUsuario(Integer idAcordoNivelServico, Integer idUsuario) throws Exception {
		PrioridadeServicoUsuarioDao dao = new PrioridadeServicoUsuarioDao();
		try{
			return dao.findByIdAcordoNivelServicoAndIdUsuario(idAcordoNivelServico, idUsuario);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Integer recuperaPrioridade(Integer idAcordoNivelServico, Integer idUsuario) throws Exception {
		List condicao = new ArrayList();
	    List ordenacao = new ArrayList();
	    
	    condicao.add(new Condition("idAcordoNivelServico", "=", idAcordoNivelServico));
	    condicao.add(new Condition("idUsuario", "=", idUsuario));
	    ordenacao.add(new Order("idUsuario"));
	    
	    PrioridadeServicoUsuarioDao dao = new PrioridadeServicoUsuarioDao();
		try{
			List<PrioridadeServicoUsuarioDTO> resp = (List<PrioridadeServicoUsuarioDTO>) dao.findByCondition(condicao, ordenacao);
			if(resp != null){
				PrioridadeServicoUsuarioDTO prioridadeServicoUsuarioDTO = resp.get(0);
				return prioridadeServicoUsuarioDTO.getIdPrioridade();
			}else{
				return 0;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public void deleteByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception{
		PrioridadeAcordoNivelServicoDao dao = new PrioridadeAcordoNivelServicoDao();
		try{
			dao.deleteByIdAcordoNivelServico(idAcordoNivelServico);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
