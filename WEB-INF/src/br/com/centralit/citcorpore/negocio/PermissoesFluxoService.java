package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.service.CrudServicePojo;
public interface PermissoesFluxoService extends CrudServicePojo {
	public Collection findByIdTipoFluxo(Integer parm) throws Exception;
	public void deleteByIdTipoFluxo(Integer parm) throws Exception;
	public Collection findByIdGrupo(Integer parm) throws Exception;
	public void deleteByIdGrupo(Integer parm) throws Exception;
	public Collection<FluxoDTO> findFluxosByUsuario(UsuarioDTO usuarioDto) throws Exception;
	public PermissoesFluxoDTO findByUsuarioAndFluxo(UsuarioDTO usuarioDto, FluxoDTO fluxoDto) throws Exception;
	public boolean permissaoGrupoExecutor(Integer idTipoMudanca, Integer idGrupoExecutor) throws Exception;
	public boolean permissaoGrupoExecutorLiberacao(Integer idTipoMudanca, Integer idGrupoExecutor) throws Exception;
	public boolean permissaoGrupoExecutorProblema(Integer idCategoriaProblema, Integer idGrupoExecutor) throws Exception;
	public boolean permissaoGrupoExecutorLiberacaoServico(Integer idGrupoExecutor, Integer idTipoFluxo) throws Exception;
}
