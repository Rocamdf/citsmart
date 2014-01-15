/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ImportanciaConhecimentoGrupoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class ImportanciaConhecimentoGrupoServiceEjb extends CrudServicePojoImpl implements ImportanciaConhecimentoGrupoService {

	private static final long serialVersionUID = -6347631300758676649L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ImportanciaConhecimentoGrupoDAO();
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
	public void deleteByIdConhecimento(Integer idBaseConhecimento, TransactionControler transactionControler) throws Exception {

		ImportanciaConhecimentoGrupoDAO importanciaConhecimentoGrupoDao = new ImportanciaConhecimentoGrupoDAO();

		importanciaConhecimentoGrupoDao.setTransactionControler(transactionControler);

		importanciaConhecimentoGrupoDao.deleteByIdConhecimento(idBaseConhecimento);

	}

	@Override
	public void create(ImportanciaConhecimentoGrupoDTO importanciaConhecimentoGrupo, TransactionControler transactionControler) throws Exception {

		ImportanciaConhecimentoGrupoDAO importanciaConhecimentoGrupoDao = new ImportanciaConhecimentoGrupoDAO();

		importanciaConhecimentoGrupoDao.setTransactionControler(transactionControler);

		importanciaConhecimentoGrupoDao.create(importanciaConhecimentoGrupo);

	}

	@Override
	public Collection<ImportanciaConhecimentoGrupoDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws Exception {
		ImportanciaConhecimentoGrupoDAO importanciaConhecimentoGrupoDao = new ImportanciaConhecimentoGrupoDAO();

		return importanciaConhecimentoGrupoDao.listByIdBaseConhecimento(idBaseConhecimento);
	}

	@Override
	public ImportanciaConhecimentoGrupoDTO obterGrauDeImportancia(BaseConhecimentoDTO baseConhecimentoDto, Collection<GrupoEmpregadoDTO> listGrupoEmpregado, UsuarioDTO usuarioDto) throws Exception {
		ImportanciaConhecimentoGrupoDAO importanciaConhecimentoGrupoDao = new ImportanciaConhecimentoGrupoDAO();
		return importanciaConhecimentoGrupoDao.obterGrauDeImportancia(baseConhecimentoDto, listGrupoEmpregado, usuarioDto);
	}

}
