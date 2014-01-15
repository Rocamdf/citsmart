package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CategoriaGaleriaImagemDTO;
import br.com.centralit.citcorpore.integracao.CategoriaGaleriaImagemDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

public class CategoriaGaleriaImagemServiceEjb extends CrudServicePojoImpl implements CategoriaGaleriaImagemService{
	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new CategoriaGaleriaImagemDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {}

	@Override
	protected void validaUpdate(Object obj) throws Exception {}

	@Override
	protected void validaDelete(Object obj) throws Exception {}

	@Override
	protected void validaFind(Object obj) throws Exception {}

	/**
	 * Deleta Categoria Imagem Ativos
	 * 
	 * @param model
	 * @param document
	 * @return
	 * @throws Exception
	 * @author cledson.junior
	 */
	public void deletarCategoriaImagem(IDto model, DocumentHTML document) throws ServiceException, Exception {
		CategoriaGaleriaImagemDTO categoriaGaleriaImagemDto = (CategoriaGaleriaImagemDTO) model;
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		CategoriaGaleriaImagemDao categoriaGaleriaImagemDao = new CategoriaGaleriaImagemDao();

		try {
			validaUpdate(model);
			categoriaGaleriaImagemDao.setTransactionControler(tc);
			tc.start();
			categoriaGaleriaImagemDto.setDataFim(UtilDatas.getDataAtual());
			categoriaGaleriaImagemDao.update(model);
				document.alert(i18n_Message("MSG07"));
				tc.commit();
				tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}

	}

	/**
	 * Consultar Categoria Imagem Ativos
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author cledson.junior
	 */
	@Override
	public boolean consultarCategoriaImagemAtivos(CategoriaGaleriaImagemDTO obj) throws Exception {
		CategoriaGaleriaImagemDao dao = new CategoriaGaleriaImagemDao();
		return dao.consultarCategoriaImagemAtivos(obj);
	}
}