package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoSituacaoFaturaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoSituacaoFaturaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
public class PerfilAcessoSituacaoFaturaServiceEjb extends CrudServicePojoImpl implements PerfilAcessoSituacaoFaturaService {
	protected CrudDAO getDao() throws ServiceException {
		return new PerfilAcessoSituacaoFaturaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection getSituacoesFaturaPermitidasByUsuario(UsuarioDTO usuario) throws Exception{
		MenuDao menuDao = new MenuDao();
		Integer idPerfilAcesso = menuDao.getPerfilAcesso(usuario);
		PerfilAcessoSituacaoFaturaDao perfilAcessoSituacaoFaturaDao = new PerfilAcessoSituacaoFaturaDao();
		if (idPerfilAcesso == null){
			return null;
		}
		Collection colSituacoesPerfil = perfilAcessoSituacaoFaturaDao.findByIdPerfil(idPerfilAcesso);
		if (colSituacoesPerfil == null){
			return null;
		}
		Collection colFinal = new ArrayList();
		for (Iterator it = colSituacoesPerfil.iterator(); it.hasNext();){
			PerfilAcessoSituacaoFaturaDTO perfilAcessoSituacaoFaturaDTO = (PerfilAcessoSituacaoFaturaDTO)it.next();
			if (perfilAcessoSituacaoFaturaDTO.getDataFim() == null || perfilAcessoSituacaoFaturaDTO.getDataFim().after(UtilDatas.getDataAtual())){
				colFinal.add(Integer.parseInt(perfilAcessoSituacaoFaturaDTO.getSituacaoFatura()));
			}
		}
		return colFinal;
	}
	
	@Override
	public Collection getSituacoesFaturaPermitidasByGrupo(PerfilAcessoGrupoDTO perfilAcessoGrupoDTO) throws Exception {
		
		PerfilAcessoSituacaoFaturaDao perfilAcessoSituacaoFaturaDao = new PerfilAcessoSituacaoFaturaDao();
		
		Integer idPerfilAcesso = perfilAcessoGrupoDTO.getIdPerfilAcessoGrupo();
		if(idPerfilAcesso == null){
			return null;
		}
		Collection colSituacoesPerfil = perfilAcessoSituacaoFaturaDao.findByIdPerfil(idPerfilAcesso);
		if (colSituacoesPerfil == null){
			return null;
		}
		Collection colFinal = new ArrayList();
		for (Iterator it = colSituacoesPerfil.iterator(); it.hasNext();){
			PerfilAcessoSituacaoFaturaDTO perfilAcessoSituacaoFaturaDTO = (PerfilAcessoSituacaoFaturaDTO)it.next();
			if (perfilAcessoSituacaoFaturaDTO.getDataFim() == null || perfilAcessoSituacaoFaturaDTO.getDataFim().after(UtilDatas.getDataAtual())){
				colFinal.add(Integer.parseInt(perfilAcessoSituacaoFaturaDTO.getSituacaoFatura()));
			}
		}
		return colFinal;
	}
	
	public Collection findByIdPerfil(Integer parm) throws Exception{
		PerfilAcessoSituacaoFaturaDao dao = new PerfilAcessoSituacaoFaturaDao();
		try{
			return dao.findByIdPerfil(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdPerfil(Integer parm) throws Exception{
		PerfilAcessoSituacaoFaturaDao dao = new PerfilAcessoSituacaoFaturaDao();
		try{
			dao.deleteByIdPerfil(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findBySituacaoFatura(String parm) throws Exception{
		PerfilAcessoSituacaoFaturaDao dao = new PerfilAcessoSituacaoFaturaDao();
		try{
			return dao.findBySituacaoFatura(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteBySituacaoFatura(String parm) throws Exception{
		PerfilAcessoSituacaoFaturaDao dao = new PerfilAcessoSituacaoFaturaDao();
		try{
			dao.deleteBySituacaoFatura(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
