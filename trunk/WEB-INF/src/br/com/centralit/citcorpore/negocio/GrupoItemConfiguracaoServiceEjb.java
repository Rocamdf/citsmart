package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.GrupoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

public class GrupoItemConfiguracaoServiceEjb extends CrudServicePojoImpl implements GrupoItemConfiguracaoService {

	private static final long serialVersionUID = -9032778499282037808L;

	protected CrudDAO getDao() throws ServiceException {
		return new GrupoItemConfiguracaoDAO();
	}

	protected void validaCreate(Object obj) throws Exception {
	}

	protected void validaUpdate(Object obj) throws Exception {
	}

	protected void validaDelete(Object obj) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}

	@Override
	public boolean VerificaSeCadastrado(GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO) throws PersistenceException {
		GrupoItemConfiguracaoDAO dao = new GrupoItemConfiguracaoDAO();
		return dao.VerificaSeCadastrado(grupoItemConfiguracaoDTO);
	}

	@Override
	public Collection<GrupoItemConfiguracaoDTO> listByEvento(Integer idEvento)
			throws ServiceException, RemoteException {
		try {
			GrupoItemConfiguracaoDAO dao = (GrupoItemConfiguracaoDAO) getDao();
		    return dao.listByEvento(idEvento);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}
	
	public void updateNotNull(IDto dto) {
		try {
			validaUpdate(dto);
			((GrupoItemConfiguracaoDAO) getDao()).updateNotNull(dto);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean verificaICRelacionados(GrupoItemConfiguracaoDTO grupoItemConfiguracao)	throws Exception {
			try {
				GrupoItemConfiguracaoDAO dao = (GrupoItemConfiguracaoDAO) getDao();
			    return dao.verificaICRelacionados(grupoItemConfiguracao);
			} catch (Exception e) {
			    throw new ServiceException(e);
			}
	}
	@Override
	public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracaoPai(Integer idGrupoItemConfiguracaoPai) throws Exception{
		try {
			GrupoItemConfiguracaoDAO dao = (GrupoItemConfiguracaoDAO) getDao();
		    return dao.listByIdGrupoItemConfiguracaoPai(idGrupoItemConfiguracaoPai);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}
	
	@Override
	public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracaoDesenvolvimento(Integer idGrupoItemConfiguracaoPai) throws Exception{
		try {
			GrupoItemConfiguracaoDAO dao = (GrupoItemConfiguracaoDAO) getDao();
		    return dao.listByIdGrupoItemConfiguracaoDesenvolvimento(idGrupoItemConfiguracaoPai);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}
	
	@Override
	public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracao(Integer idGrupoItemConfiguracaoPai) throws Exception {
		try {
			GrupoItemConfiguracaoDAO dao = (GrupoItemConfiguracaoDAO) getDao();
		    return dao.listByIdGrupoItemConfiguracao(idGrupoItemConfiguracaoPai);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}
	
	public Collection listHierarquiaGruposByIdGrupo(Integer idGrupo, GrupoItemConfiguracaoDAO dao) throws Exception {
		try {
			if (dao == null){
				dao = (GrupoItemConfiguracaoDAO) getDao();
			}
			Collection col = dao.listByIdGrupoItemConfiguracaoPai(idGrupo);
			if (col != null && col.size() > 0){
				Iterator it = col.iterator();
				Collection colTratada = new ArrayList();
				for (; it.hasNext() ;){
					GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO)it.next();
					colTratada.add(grupoItemConfiguracaoDTO);
				}
				it = colTratada.iterator();
				for (; it.hasNext() ;){
					GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO)it.next();
					Collection col2 = this.listHierarquiaGruposByIdGrupo(grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao(), dao);
					if (col2 != null && col2.size() > 0){
						col.addAll(col2);
					}
				}
			}
		    return col;
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}
	public Collection listHierarquiaGrupoPaiNull() throws Exception {
		try {
			GrupoItemConfiguracaoDAO dao = (GrupoItemConfiguracaoDAO) getDao();
			Collection col = dao.listByIdGrupoItemConfiguracaoPaiNull();
			if (col != null && col.size() > 0){
				for (Iterator it = col.iterator(); it.hasNext();){
					GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO)it.next();
					Collection col2 = listHierarquiaGruposByIdGrupo(grupoItemConfiguracaoDTO.getIdGrupoItemConfiguracao(), dao);
					if (col2 != null && col2.size() > 0){
						col.addAll(col2);
					}
				}
			}
		    return col;
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}
	
	public void autenticaGrupoPadrao(int id, int idPai, String nome) throws Exception{
		
		GrupoItemConfiguracaoDTO grupoIC = new GrupoItemConfiguracaoDTO();
		GrupoItemConfiguracaoDAO dao = new GrupoItemConfiguracaoDAO();

		grupoIC.setIdGrupoItemConfiguracao(id);
		grupoIC.setNomeGrupoItemConfiguracao(nome);
		grupoIC.setIdGrupoItemConfiguracaoPai(idPai);
		grupoIC.setDataInicio(UtilDatas.getDataAtual());
		
		if(dao.VerificaSeCadastrado(grupoIC)){
		    grupoIC.setDataInicio(((GrupoItemConfiguracaoDTO)dao.restore(grupoIC)).getDataInicio());
		    dao.update(grupoIC);
		}else{
			this.create(grupoIC);
		}
	}
	
}