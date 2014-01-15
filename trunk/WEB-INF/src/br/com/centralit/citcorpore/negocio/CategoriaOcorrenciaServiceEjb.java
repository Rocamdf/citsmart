package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
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
public class CategoriaOcorrenciaServiceEjb extends CrudServicePojoImpl implements CategoriaOcorrenciaService {
	private static final long serialVersionUID = -3141376983649289489L;

	@Override
	public void deletarCategoriaOcorrencia(IDto model, DocumentHTML document) throws ServiceException, Exception {
		CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) model;
		CategoriaOcorrenciaDAO categoriaOcorrenciaDAO = new CategoriaOcorrenciaDAO();
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB() );

		try {
			validaUpdate(model);
			// Configura transações para cada entidade a ser registrada no banco de dados
			categoriaOcorrenciaDAO.setTransactionControler(tc);
			// inicia transação
			tc.start();
			categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.restore(categoriaOcorrenciaDTO);
			categoriaOcorrenciaDTO.setDataFim(UtilDatas.getDataAtual() );
			categoriaOcorrenciaDAO.update(categoriaOcorrenciaDTO);
			document.alert(i18n_Message("MSG07") );
			// confirma transação
			tc.commit();
			// encerra transação
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
	}

	@Override
	public boolean consultarCategoriaOcorrenciaAtiva(CategoriaOcorrenciaDTO obj) throws Exception {
		CategoriaOcorrenciaDAO dao = new CategoriaOcorrenciaDAO();
		return dao.consultarCategoriaOcorrenciaAtiva(obj);
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new CategoriaOcorrenciaDAO();
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
