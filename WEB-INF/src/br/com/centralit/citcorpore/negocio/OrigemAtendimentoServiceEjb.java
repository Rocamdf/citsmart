package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.integracao.OrigemAtendimentoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
public class OrigemAtendimentoServiceEjb extends CrudServicePojoImpl implements OrigemAtendimentoService {
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		return new OrigemAtendimentoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	public void deletarOrigemAtendimento(IDto model, DocumentHTML document) throws ServiceException, Exception {
		OrigemAtendimentoDTO origemAtendimentoDTO = (OrigemAtendimentoDTO) model;
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		OrigemAtendimentoDao origemAtendimentoDao = new OrigemAtendimentoDao();

		try {
			validaUpdate(origemAtendimentoDTO);
			origemAtendimentoDao.setTransactionControler(tc);
			tc.start();
			origemAtendimentoDTO.setDataFim(UtilDatas.getDataAtual());
			origemAtendimentoDao.update(origemAtendimentoDTO);
				document.alert(i18n_Message("MSG07"));
				tc.commit();
				tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}

	}
	
	@Override
	public boolean consultarOrigemAtendimentoAtivos(OrigemAtendimentoDTO obj) throws Exception {
		OrigemAtendimentoDao dao = new OrigemAtendimentoDao();
		return dao.consultarOrigemAtendimentoAtivos(obj);
	}
	
	/**
	 * Retorna todos os registros de Origens de Antedimento que possuim dataFim == null; 
	 * 
	 * @author riubbe.oliveira
	 * @return Collection<OrigemAtendimentoDTO>
	 * @throws Exception
	 */
	public Collection<OrigemAtendimentoDTO> recuperaAtivos() throws Exception{
		OrigemAtendimentoDao dao = new OrigemAtendimentoDao();
		return dao.listarTodosAtivos();
	}

}
