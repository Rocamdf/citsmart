package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.OrigemOcorrenciaDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

/**
 * @author thiago.monteiro
 */
public class OrigemOcorrenciaServiceEjb extends CrudServicePojoImpl implements OrigemOcorrenciaService {
	private static final long serialVersionUID = -343772284992542923L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new OrigemOcorrenciaDAO();
	}
	
	@Override
	public void deletarOrigemOcorrencia(IDto model, DocumentHTML document) throws ServiceException, Exception {
		OrigemOcorrenciaDTO origemOcorrenciaDTO = (OrigemOcorrenciaDTO) model;
		OrigemOcorrenciaDAO origemOcorrenciaDAO = new OrigemOcorrenciaDAO();
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB() );

		try {
			validaUpdate(model);
			origemOcorrenciaDAO.setTransactionControler(tc); 
			tc.start();
			origemOcorrenciaDTO = (OrigemOcorrenciaDTO) origemOcorrenciaDAO.restore(origemOcorrenciaDTO);
			origemOcorrenciaDTO.setDataFim(UtilDatas.getDataAtual() );
			origemOcorrenciaDAO.update(origemOcorrenciaDTO);
			document.alert(i18n_Message("MSG07") );
			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
	}
	
	@Override
	public boolean consultarOrigemOcorrenciaAtiva(OrigemOcorrenciaDTO origemOcorrenciaDTO) throws Exception {
		OrigemOcorrenciaDAO origemOcorrenciaDAO = new OrigemOcorrenciaDAO();
		return origemOcorrenciaDAO.consultarOrigemOcorrenciaAtiva(origemOcorrenciaDTO);
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

	
}
