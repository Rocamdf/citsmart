package br.com.centralit.citcorpore.bpm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.UsuarioBpmDTO;
import br.com.centralit.bpm.negocio.IUsuarioGrupo;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmailDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.UsuarioDao;

public class BPMUsuarioGrupo implements IUsuarioGrupo {

	@Override
	public UsuarioBpmDTO recuperaUsuario(Integer idUsuario) throws Exception {
		UsuarioBpmDTO usuarioBpmDto = null;
		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setIdUsuario(idUsuario);
		usuarioDto = (UsuarioDTO) new UsuarioDao().restore(usuarioDto);
		if (usuarioDto != null) {
			usuarioBpmDto = new UsuarioBpmDTO();
			usuarioBpmDto.setIdUsuario(usuarioDto.getIdUsuario());
			usuarioBpmDto.setLogin(usuarioDto.getLogin());
			usuarioBpmDto.setNome(usuarioDto.getNomeUsuario());
			EmpregadoDTO empregadoDto = new EmpregadoDao().restoreByIdEmpregado(usuarioDto.getIdEmpregado());
			if (empregadoDto != null)
                usuarioBpmDto.setEmails(new String[] {empregadoDto.getEmail()});
		}
		return usuarioBpmDto;
	}

	@Override
	public UsuarioBpmDTO recuperaUsuario(String login) throws Exception {
	    if (login == null)
	        return null;
		UsuarioBpmDTO usuarioBpmDto = null;
		UsuarioDTO usuarioDto = new UsuarioDao().restoreByLogin(login.trim());
		if (usuarioDto != null) {
			usuarioBpmDto = new UsuarioBpmDTO();
			usuarioBpmDto.setIdUsuario(usuarioDto.getIdUsuario());
			usuarioBpmDto.setLogin(login);
			usuarioBpmDto.setNome(usuarioDto.getNomeUsuario());
            EmpregadoDTO empregadoDto = new EmpregadoDao().restoreByIdEmpregado(usuarioDto.getIdEmpregado());
            if (empregadoDto != null)
                usuarioBpmDto.setEmails(new String[] {empregadoDto.getEmail()});
		}
		return usuarioBpmDto;
	}

	@Override
	public GrupoBpmDTO recuperaGrupo(String siglaGrupo) throws Exception {
	    if (siglaGrupo == null)
	        return null;
		GrupoBpmDTO grupoBpmDto = null;
		GrupoDTO grupoDto = new GrupoDao().restoreBySigla(siglaGrupo.trim());
		if (grupoDto != null) {
			grupoBpmDto = new GrupoBpmDTO();
			grupoBpmDto.setIdGrupo(grupoDto.getIdGrupo());
			grupoBpmDto.setSigla(siglaGrupo);
            grupoBpmDto.setEmails(recuperaEmailsGrupo(grupoDto.getIdGrupo()));
		}
		return grupoBpmDto;
	}
	

	
	@Override
	public boolean existeUsuario(String login) throws Exception {
		return recuperaUsuario(login) != null;
	}

	@Override
	public boolean existeGrupo(String siglaGrupo) throws Exception {
		return recuperaGrupo(siglaGrupo) != null;
	}

	@Override
	public List<GrupoBpmDTO> getGruposDoUsuario(String login) throws Exception {
	    if (login == null)
	        return null;
		List<GrupoBpmDTO> result = null;	
		UsuarioDTO usuarioDto = new UsuarioDao().restoreByLogin(login.trim());
		if (usuarioDto != null) {
			try {
				Collection<GrupoEmpregadoDTO> colGrupos = new GrupoEmpregadoDao().findAtivosByIdEmpregado(usuarioDto.getIdEmpregado());
				if (colGrupos != null) {
					result = new ArrayList();
					for (GrupoEmpregadoDTO grupoDto : colGrupos) {
						GrupoBpmDTO grupoBpmDto = new GrupoBpmDTO();
						grupoBpmDto.setIdGrupo(grupoDto.getIdGrupo());
						grupoBpmDto.setSigla(grupoDto.getSigla());
				        grupoBpmDto.setEmails(recuperaEmailsGrupo(grupoDto.getIdGrupo()));
						result.add(grupoBpmDto);
					}
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	@Override
	public GrupoBpmDTO recuperaGrupo(Integer idGrupo) throws Exception {
		GrupoBpmDTO grupoBpmDto = null;
		GrupoDTO grupoDto = new GrupoDTO();
		grupoDto.setIdGrupo(idGrupo);
		grupoDto = (GrupoDTO) new GrupoDao().restore(grupoDto);
		if (grupoDto != null) {
			grupoBpmDto = new GrupoBpmDTO();
			grupoBpmDto.setIdGrupo(grupoDto.getIdGrupo());
			grupoBpmDto.setSigla(grupoDto.getSigla());
			grupoBpmDto.setEmails(recuperaEmailsGrupo(grupoDto.getIdGrupo()));
		}
		return grupoBpmDto;
	}

	@Override
	public boolean pertenceAoGrupo(String login, String siglaGrupo) throws Exception {
		boolean bResult = false;
		List<GrupoBpmDTO> grupos = getGruposDoUsuario(login);
		if (grupos != null) {
			for (GrupoBpmDTO grupoDto : grupos) {
				if (grupoDto.getSigla().equalsIgnoreCase(siglaGrupo)) {
					bResult = true;
					break;
				}
			}
		}
		return bResult;
	}
	
	private String[] recuperaEmailsGrupo(Integer idGrupo) throws Exception {
	    Collection<GrupoEmailDTO> colEmails = new GrupoEmailDao().findByIdGrupo(idGrupo);
	    if (colEmails != null) {
	        int i = 0;
	        String[] emails = new String[colEmails.size()];
	        for (GrupoEmailDTO grupoEmailDto : colEmails) {
	            emails[i] = grupoEmailDto.getEmail();
	            i++;
            }
	        return emails;
	    }else
	        return null;
	}

}
