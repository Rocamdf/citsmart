package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleRendimentoUsuarioDTO;
import br.com.centralit.citcorpore.integracao.ControleRendimentoUsuarioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ControleRendimentoUsuarioServiceEjb extends CrudServicePojoImpl implements ControleRendimentoUsuarioService{

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ControleRendimentoUsuarioDao();
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
	public Collection<ControleRendimentoUsuarioDTO> findByIdControleRendimentoUsuario(Integer idGrupo, String mes, String ano) throws ServiceException {
		ControleRendimentoUsuarioDao dao = new ControleRendimentoUsuarioDao();
		try {
			return dao.findByIdControleRendimentoUsuario(idGrupo,mes, ano);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Collection<ControleRendimentoUsuarioDTO> findByIdControleRendimentoMelhoresUsuario(Integer idGrupo, String mesInicio, String mesFim, String anoInicio, String anoFim, Boolean deUmAnoParaOOutro) throws ServiceException {
		ControleRendimentoUsuarioDao dao = new ControleRendimentoUsuarioDao();
		try {
			return dao.findByIdControleRendimentoMelhoresUsuario(idGrupo,mesInicio, mesFim, anoInicio, anoFim, deUmAnoParaOOutro);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Collection<ControleRendimentoUsuarioDTO> findIdsControleRendimentoUsuarioPorPeriodo(Integer idGrupo, String mesInicio, String mesFim, String anoInicio, String anoFim, Boolean deUmAnoParaOOutro) throws ServiceException {
		ControleRendimentoUsuarioDao dao = new ControleRendimentoUsuarioDao();
		try {
			return dao.findIdsControleRendimentoUsuarioPorPeriodo(idGrupo,mesInicio, mesFim, anoInicio, anoFim, deUmAnoParaOOutro);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
