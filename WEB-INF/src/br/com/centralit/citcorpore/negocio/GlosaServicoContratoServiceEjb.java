/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GlosaServicoContratoDTO;
import br.com.centralit.citcorpore.integracao.GlosaServicoContratoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author rodrigo.oliveira
 *
 */
public class GlosaServicoContratoServiceEjb extends CrudServicePojoImpl implements GlosaServicoContratoService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3616007081918319588L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new GlosaServicoContratoDAO();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
	}
	
	@Override
	public Collection findByIdServicoContrato(Integer parm) throws Exception {
		GlosaServicoContratoDAO dao = new GlosaServicoContratoDAO();
		try {
			return dao.findByIdServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Integer quantidadeGlosaServico(Integer idServicoContrato) throws Exception {
		GlosaServicoContratoDAO dao = new GlosaServicoContratoDAO();
		Integer quantidade = 0;
		try {
			List<GlosaServicoContratoDTO> listResult = (List<GlosaServicoContratoDTO>) dao.quantidadeGlosaServico(idServicoContrato);
			if(listResult != null && listResult.size()>0){
				quantidade = listResult.get(0).getQuantidadeGlosa();
			}
			return quantidade;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void atualizaQuantidadeGlosa(Integer novaQuantidade, Integer idServicoContrato) throws Exception {
		GlosaServicoContratoDAO dao = new GlosaServicoContratoDAO();
		try {
			dao.atualizaQuantidadeGlosa(novaQuantidade, idServicoContrato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
