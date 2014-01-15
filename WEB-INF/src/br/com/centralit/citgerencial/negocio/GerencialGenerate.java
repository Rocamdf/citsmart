package br.com.centralit.citgerencial.negocio;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citgerencial.bean.GerencialInfoGenerateDTO;
import br.com.centralit.citgerencial.bean.GerencialItemInformationDTO;
import br.com.centralit.citgerencial.bean.GerencialItemPainelDTO;
import br.com.centralit.citgerencial.bean.GerencialOptionsDTO;
import br.com.centralit.citgerencial.bean.GerencialPainelDTO;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;

public interface GerencialGenerate extends CrudServicePojo {
	public Object generate(GerencialItemInformationDTO gerencialItemDto, Usuario usuario, GerencialInfoGenerateDTO infoGenerate, GerencialItemPainelDTO gerencialItemPainelAuxDto, GerencialPainelDTO gerencialPainelDto, HttpServletRequest request) throws ServiceException;
	public Collection executaSQLOptions(GerencialOptionsDTO options, GerencialPainelDTO gerencialPainelDto, HashMap hashParametros, Usuario user) throws ServiceException;
	public Collection executaSQLOptions(GerencialOptionsDTO options, Collection listParameters, HashMap hashParametros, Usuario user) throws ServiceException;
	public Object geraTabelaVazia(GerencialInfoGenerateDTO infoGenerate,  HttpServletRequest request);
}
