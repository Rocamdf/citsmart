package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
import br.com.citframework.service.CrudServicePojo;

/**
 * @author breno.guimaraes
 * 
 */ @SuppressWarnings("rawtypes")
public interface JustificativaParecerService extends CrudServicePojo {
   
	public Collection listAplicaveisRequisicao() throws Exception;
	public Collection listAplicaveisRequisicaoViagem() throws Exception;
    public Collection listAplicaveisCotacao() throws Exception;
    public Collection listAplicaveisInspecao() throws Exception;
    public boolean consultarJustificativaAtiva(JustificativaParecerDTO justificativaParecerDto) throws Exception;
}
