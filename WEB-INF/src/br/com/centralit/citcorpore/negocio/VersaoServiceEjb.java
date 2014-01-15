package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.VersaoDTO;
import br.com.centralit.citcorpore.integracao.VersaoDao;
import br.com.centralit.citcorpore.util.FiltroSegurancaCITSmart;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class VersaoServiceEjb extends CrudServicePojoImpl implements VersaoService {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void validaVersoes(UsuarioDTO usuario) throws Exception {
		VersaoDao versaoDao = (VersaoDao) getDao();
		Collection<VersaoDTO> versoes = versaoDao.list();
		for (VersaoDTO versao : versoes) {
			if (versao.getIdUsuario() == null || versao.getIdUsuario().longValue() == 0) {
				versao.setIdUsuario(usuario.getIdUsuario());
				update(versao);
			}
		}
		FiltroSegurancaCITSmart.setHaVersoesSemValidacao(haVersoesSemValidacao());
	}

	@Override
	public VersaoDTO versaoASerValidada() throws Exception {
		VersaoDao versaoDao = (VersaoDao) getDao();
		return versaoDao.versaoASerValidada();
	}

	@Override
	public Collection<VersaoDTO> versoesComErrosScripts() throws Exception {
		VersaoDao versaoDao = (VersaoDao) getDao();
		return versaoDao.versoesComErrosScripts();
	}

	@Override
	public boolean haVersoesSemValidacao() throws Exception {
		VersaoDao versaoDao = (VersaoDao) getDao();
		return versaoDao.haVersoesSemValidacao();
	}

	@Override
	public VersaoDTO buscaVersaoPorNome(String nome) throws Exception {
		VersaoDao versaoDao = (VersaoDao) getDao();
		return versaoDao.buscaVersaoPorNome(nome);
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new VersaoDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
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
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

}
