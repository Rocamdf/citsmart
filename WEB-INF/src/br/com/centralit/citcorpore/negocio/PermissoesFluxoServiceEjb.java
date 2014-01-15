package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.PermissoesFluxoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.Reflexao;
public class PermissoesFluxoServiceEjb extends CrudServicePojoImpl implements PermissoesFluxoService {
	protected CrudDAO getDao() throws ServiceException {
		return new PermissoesFluxoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdTipoFluxo(Integer parm) throws Exception{
		PermissoesFluxoDao dao = new PermissoesFluxoDao();
		try{
			return dao.findByIdTipoFluxo(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdTipoFluxo(Integer parm) throws Exception{
		PermissoesFluxoDao dao = new PermissoesFluxoDao();
		try{
			dao.deleteByIdTipoFluxo(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdGrupo(Integer parm) throws Exception{
		PermissoesFluxoDao dao = new PermissoesFluxoDao();
		try{
			return dao.findByIdGrupo(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdGrupo(Integer parm) throws Exception{
		PermissoesFluxoDao dao = new PermissoesFluxoDao();
		try{
			dao.deleteByIdGrupo(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<FluxoDTO> findFluxosByUsuario(UsuarioDTO usuarioDto) throws Exception {
		Collection<FluxoDTO> result = new ArrayList();
		if (usuarioDto.getColGrupos() != null) {
			for (GrupoDTO grupoDto : usuarioDto.getColGrupos() ) {
				Collection<PermissoesFluxoDTO> colAux = findByIdGrupo(grupoDto.getIdGrupo());
				if (colAux != null) {
					FluxoDao fluxoDao = new FluxoDao();
					for (PermissoesFluxoDTO permissoesFluxoDto : colAux) {
						FluxoDTO fluxoDto = fluxoDao.findByTipoFluxo(permissoesFluxoDto.getIdTipoFluxo());
						if (fluxoDto != null) {
							Reflexao.copyPropertyValues(permissoesFluxoDto, fluxoDto);
							fluxoDto.setConteudoXml(null);
							result.add(fluxoDto);
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public PermissoesFluxoDTO findByUsuarioAndFluxo(UsuarioDTO usuarioDto, FluxoDTO fluxoDto) throws Exception {
		PermissoesFluxoDTO permissoesDto = null;
		if (usuarioDto.getColGrupos() != null && fluxoDto != null) {
			String criar = "N";
			String executar = "N";
			String delegar = "N";
			String suspender = "N";
			String reativar = "N";
			String alterarSLA = "N";
			String reabrir = "N";
			for (GrupoDTO grupoDto : usuarioDto.getColGrupos() ) {
				
				//TODO
				Collection<PermissoesFluxoDTO> colPermissoes = findByIdGrupo(grupoDto.getIdGrupo());
				
				if (colPermissoes != null) {
					
					FluxoDao fluxoDao = new FluxoDao();
					
					for (PermissoesFluxoDTO permissoesAuxDto : colPermissoes) {
						
						//TODO
						FluxoDTO fluxoAuxDto = fluxoDao.findByTipoFluxo(permissoesAuxDto.getIdTipoFluxo());
						
						if (fluxoAuxDto != null && fluxoAuxDto.getIdFluxo().intValue() == fluxoDto.getIdFluxo().intValue()) {
							if (permissoesDto == null)
								permissoesDto = permissoesAuxDto;

							if (permissoesAuxDto.getCriar() != null && permissoesAuxDto.getCriar().equalsIgnoreCase("S"))
								criar = "S";
							if (permissoesAuxDto.getExecutar() != null && permissoesAuxDto.getExecutar().equalsIgnoreCase("S"))
								executar = "S";
							if (permissoesAuxDto.getDelegar() != null && permissoesAuxDto.getDelegar().equalsIgnoreCase("S"))
								delegar = "S";
							if (permissoesAuxDto.getSuspender() != null && permissoesAuxDto.getSuspender().equalsIgnoreCase("S"))
								suspender = "S";
							if (permissoesAuxDto.getReativar() != null && permissoesAuxDto.getReativar().equalsIgnoreCase("S"))
								reativar = "S";
							if (permissoesAuxDto.getSuspender() != null && permissoesAuxDto.getSuspender().equalsIgnoreCase("S"))
								suspender = "S";
							if (permissoesAuxDto.getAlterarSLA() != null && permissoesAuxDto.getAlterarSLA().equalsIgnoreCase("S"))
								alterarSLA = "S";
							if (permissoesAuxDto.getReabrir() != null && permissoesAuxDto.getReabrir().equalsIgnoreCase("S"))
								reabrir = "S";
						}
					}
				}
			}
			if (permissoesDto != null) {					// Retorna as permissões para o fluxo, independentemente do grupo
				permissoesDto.setIdGrupo(null);
				permissoesDto.setCriar(criar);
				permissoesDto.setExecutar(executar);
				permissoesDto.setDelegar(delegar);
				permissoesDto.setSuspender(suspender);
				permissoesDto.setReativar(reativar);
				permissoesDto.setSuspender(suspender);
				permissoesDto.setAlterarSLA(alterarSLA);
				permissoesDto.setReabrir(reabrir);
			}
				
		}
		return permissoesDto;
	}

	@Override
	public boolean permissaoGrupoExecutor(Integer idTipoMudanca, Integer idGrupoExecutor) throws Exception {
		PermissoesFluxoDao permissoesFluxoDao = new PermissoesFluxoDao();
		return permissoesFluxoDao.permissaoGrupoExecutor(idTipoMudanca, idGrupoExecutor);
	}
	
	@Override
	public boolean permissaoGrupoExecutorLiberacao(Integer idTipoMudanca, Integer idGrupoExecutor) throws Exception {
		PermissoesFluxoDao permissoesFluxoDao = new PermissoesFluxoDao();
		return permissoesFluxoDao.permissaoGrupoExecutorLiberacao(idTipoMudanca, idGrupoExecutor);
	}
	
	@Override
	public boolean permissaoGrupoExecutorProblema(Integer idCategoriaProblema, Integer idGrupoExecutor) throws Exception {
		PermissoesFluxoDao permissoesFluxoDao = new PermissoesFluxoDao();
		return permissoesFluxoDao.permissaoGrupoExecutorProblema(idCategoriaProblema, idGrupoExecutor);
	}
	
	@Override
	public boolean permissaoGrupoExecutorLiberacaoServico(Integer idCategoriaProblema, Integer idGrupoExecutor) throws Exception {
		PermissoesFluxoDao permissoesFluxoDao = new PermissoesFluxoDao();
		return permissoesFluxoDao.permissaoGrupoExecutorLiberacaoServico(idCategoriaProblema, idGrupoExecutor);
	}
}
