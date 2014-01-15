package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.RelatorioValorServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ValoresServicoContratoDTO;
import br.com.centralit.citcorpore.integracao.ValoresServicoContratoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings({ "serial", "rawtypes" })
public class ValoresServicoContratoServiceEjb extends CrudServicePojoImpl implements ValoresServicoContratoService {

	protected CrudDAO getDao() throws ServiceException {
		return new ValoresServicoContratoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdServicoContrato(Integer parm) throws Exception {
		ValoresServicoContratoDao dao = new ValoresServicoContratoDao();
		try {
			return dao.findByIdServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdServicoContrato(Integer parm) throws Exception {
		ValoresServicoContratoDao dao = new ValoresServicoContratoDao();
		try {
			dao.deleteByIdServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection obterValoresAtivosPorIdServicoContrato(Integer idServicoContrato) throws ServiceException {
		ValoresServicoContratoDao dao = new ValoresServicoContratoDao();
		try {
			return dao.obterValoresAtivosPorIdServicoContrato(idServicoContrato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean existeAtivos(Integer idServicoContrato) throws Exception {
		// TODO Auto-generated method stub
		ValoresServicoContratoDao dao = new ValoresServicoContratoDao();
		try {
			return dao.existeAtivos(idServicoContrato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<RelatorioValorServicoContratoDTO> listaValoresServicoContrato(ValoresServicoContratoDTO parm) 	throws Exception {
		// TODO Auto-generated method stub
		ValoresServicoContratoDao dao = new ValoresServicoContratoDao();
		try {
			return dao.listaValoresServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	
}
