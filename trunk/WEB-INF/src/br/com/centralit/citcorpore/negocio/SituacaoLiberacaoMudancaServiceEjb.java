package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.SituacaoLiberacaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.LiberacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.SituacaoLiberacaoMudancaDAO;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class SituacaoLiberacaoMudancaServiceEjb extends CrudServicePojoImpl implements SituacaoLiberacaoMudancaService{

	/**
	 * @author geber.costa
	 */
	private static final long serialVersionUID = -1259060812490191736L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		
		return new SituacaoLiberacaoMudancaDAO();
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

	@Override
	public boolean consultaExistenciaSituacao(SituacaoLiberacaoMudancaDTO obj)
			throws Exception {
		
		SituacaoLiberacaoMudancaDAO  situacaoDao= new SituacaoLiberacaoMudancaDAO();
		return situacaoDao.consultarSituacoes(obj);    
	}

	@Override
	public void deletarSituacao(IDto model, DocumentHTML document,HttpServletRequest request)
			throws ServiceException, Exception {
		SituacaoLiberacaoMudancaDTO situacaoDto = (SituacaoLiberacaoMudancaDTO) model;
		SituacaoLiberacaoMudancaDAO situacaoDao = new SituacaoLiberacaoMudancaDAO();
		try {

			if (situacaoDao.consultarSituacoes(situacaoDto)) {
				document.alert(i18n_Message("citcorpore.comum.registroNaoPodeSerExcluido"));
				return;
			} else {
				situacaoDao.delete(situacaoDto);
				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			}
		} catch (Exception e) {
		}

		
	}

	@Override
	public Collection<SituacaoLiberacaoMudancaDTO> listAll() throws ServiceException,Exception{
	SituacaoLiberacaoMudancaDAO dao = new SituacaoLiberacaoMudancaDAO();
	try{
		return dao.listAll();
	} catch (Exception e) {
		throw new ServiceException(e);
	}
}

}
