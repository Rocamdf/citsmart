package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.integracao.CargosDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

public class CargosServiceEjb extends CrudServicePojoImpl implements CargosService {

	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new CargosDao();
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

	public void deletarCargo(IDto model, DocumentHTML document) throws ServiceException, Exception {
		CargosDTO cargosDto = (CargosDTO) model;
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		EmpregadoDao empregadoDao = new EmpregadoDao();
		CargosDao cargosDao = new CargosDao();

		try {
			validaUpdate(model);
			empregadoDao.setTransactionControler(tc);
			cargosDao.setTransactionControler(tc);
			tc.start();
			if (empregadoDao.verificarSeCargoPossuiEmpregado(cargosDto.getIdCargo())) {
				document.alert(i18n_Message("citcorpore.comum.registroNaoPodeSerExcluido"));
				return;
			} else {
				cargosDto.setDataFim(UtilDatas.getDataAtual());
				cargosDao.update(model);
				document.alert(i18n_Message("MSG07"));
				tc.commit();
				tc.close();
			}
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}

	}

	@Override
	public boolean consultarCargosAtivos(CargosDTO obj) throws Exception {
		CargosDao dao = new CargosDao();
		return dao.consultarCargosAtivos(obj);
	}

	@Override
	public Collection<CargosDTO> seCargoJaCadastrado(CargosDTO cargosDTO) throws Exception {
		CargosDao dao = new CargosDao();
		return dao.seCargoJaCadastrado(cargosDTO);
	}

	@Override
	public Collection<CargosDTO> listarAtivos() throws Exception {
		return new CargosDao().listarAtivos();
	}

}
