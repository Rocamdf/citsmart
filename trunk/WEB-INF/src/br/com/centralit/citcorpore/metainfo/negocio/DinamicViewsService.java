package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.metainfo.bean.DinamicViewsDTO;
import br.com.citframework.service.CrudServicePojo;

public interface DinamicViewsService extends CrudServicePojo {
	public void save(UsuarioDTO usuarioDto, DinamicViewsDTO dinamicViewDto, HashMap map) throws Exception;
	public void saveMatriz(UsuarioDTO usuarioDto, DinamicViewsDTO dinamicViewDto, HashMap map) throws Exception;
	public Collection restoreVisao(Integer idVisao, Collection colFilter) throws Exception;
	public String internacionalizaScript(String script, Locale locale) throws Exception;
}
