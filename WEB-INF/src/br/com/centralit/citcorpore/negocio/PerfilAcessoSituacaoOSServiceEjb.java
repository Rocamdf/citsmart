package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoSituacaoOSDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoSituacaoOSDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
public class PerfilAcessoSituacaoOSServiceEjb extends CrudServicePojoImpl implements PerfilAcessoSituacaoOSService {
	protected CrudDAO getDao() throws ServiceException {
		return new PerfilAcessoSituacaoOSDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection getSituacoesOSPermitidasByUsuario(UsuarioDTO usuario) throws Exception{
		MenuDao menuDao = new MenuDao();
		Integer idPerfilAcesso = menuDao.getPerfilAcesso(usuario);
		PerfilAcessoSituacaoOSDao perfilAcessoSituacaoOSDao = new PerfilAcessoSituacaoOSDao();
		if (idPerfilAcesso == null){
			return null;
		}
		Collection colSituacoesPerfil = perfilAcessoSituacaoOSDao.findByIdPerfil(idPerfilAcesso);
		if (colSituacoesPerfil == null){
			return null;
		}
		Collection colFinal = new ArrayList();
		for (Iterator it = colSituacoesPerfil.iterator(); it.hasNext();){
			PerfilAcessoSituacaoOSDTO perfilAcessoSituacaoOSDTO = (PerfilAcessoSituacaoOSDTO)it.next();
			if (perfilAcessoSituacaoOSDTO.getDataFim() == null || perfilAcessoSituacaoOSDTO.getDataFim().after(UtilDatas.getDataAtual())){
				colFinal.add(perfilAcessoSituacaoOSDTO.getSituacaoOs());
			}
		}
		return colFinal;
	}
	
	@Override
	public Collection getSituacoesOSPermitidasByGrupo(PerfilAcessoGrupoDTO perfilAcessoGrupoDTO) throws Exception {
		
		PerfilAcessoSituacaoOSDao perfilAcessoSituacaoOSDao = new PerfilAcessoSituacaoOSDao();
		
		Integer idPerfilAcesso = perfilAcessoGrupoDTO.getIdPerfilAcessoGrupo();
		if(idPerfilAcesso == null){
			return null;
		}
		Collection colSituacoesPerfil = perfilAcessoSituacaoOSDao.findByIdPerfil(idPerfilAcesso);
		if (colSituacoesPerfil == null){
			return null;
		}
		Collection colFinal = new ArrayList();
		for (Iterator it = colSituacoesPerfil.iterator(); it.hasNext();){
			PerfilAcessoSituacaoOSDTO perfilAcessoSituacaoOSDTO = (PerfilAcessoSituacaoOSDTO)it.next();
			if (perfilAcessoSituacaoOSDTO.getDataFim() == null || perfilAcessoSituacaoOSDTO.getDataFim().after(UtilDatas.getDataAtual())){
				colFinal.add(perfilAcessoSituacaoOSDTO.getSituacaoOs());
			}
		}
		return colFinal;
	}
	
	public Collection findByIdPerfil(Integer parm) throws Exception{
		PerfilAcessoSituacaoOSDao dao = new PerfilAcessoSituacaoOSDao();
		try{
			return dao.findByIdPerfil(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdPerfil(Integer parm) throws Exception{
		PerfilAcessoSituacaoOSDao dao = new PerfilAcessoSituacaoOSDao();
		try{
			dao.deleteByIdPerfil(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findBySituacaoOs(Integer parm) throws Exception{
		PerfilAcessoSituacaoOSDao dao = new PerfilAcessoSituacaoOSDao();
		try{
			return dao.findBySituacaoOs(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteBySituacaoOs(Integer parm) throws Exception{
		PerfilAcessoSituacaoOSDao dao = new PerfilAcessoSituacaoOSDao();
		try{
			dao.deleteBySituacaoOs(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
