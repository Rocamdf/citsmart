package br.com.centralit.citcorpore.negocio;

import java.sql.Date;

import br.com.citframework.service.CrudServicePojo;


public interface FeriadoService extends CrudServicePojo {
    
    public boolean isFeriado(Date data, Integer idCidade, Integer idUf) throws Exception;
    
}