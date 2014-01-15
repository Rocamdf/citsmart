package br.com.centralit.citcorpore.negocio;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.FormaPagamentoDTO;
import br.com.centralit.citcorpore.integracao.FormaPagamentoDAO;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

public class FormaPagamentoServiceEjb extends CrudServicePojoImpl implements FormaPagamentoService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6922992780716142503L;

	@Override
	public boolean consultarFormaPagamento(FormaPagamentoDTO obj)
			throws Exception {
		FormaPagamentoDAO dao = new FormaPagamentoDAO();
		return dao.consultarFormaPagamento(obj);
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new FormaPagamentoDAO();
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
	public void deletarFormaPagamento(IDto model, DocumentHTML document,
			HttpServletRequest request) throws ServiceException, Exception {
		FormaPagamentoDTO formaPagamentoDto = (FormaPagamentoDTO) model;
		FormaPagamentoDAO formaDao = new FormaPagamentoDAO();
		try{
			formaPagamentoDto.setSituacao("I");
			formaDao.update(model);
			document.alert(UtilI18N.internacionaliza((HttpServletRequest) request, "MSG07"));
		} catch (Exception e) {
			throw new ServiceException(e);
		}	
		
	}


}
