package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.util.Enumerados.SituacaoInstanciaFluxo;
import br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;

public class EventoFluxoDao extends ItemTrabalhoFluxoDao {

	public Class getBean() {
		return EventoFluxoDTO.class;
	}

    private StringBuffer getSQLRestoreAll() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT item.idItemTrabalho, item.idInstancia, item.idElemento, item.dataHoraCriacao, " );
        sql.append("       item.dataHoraFinalizacao, item.dataHoraExecucao, item.situacao, elem.intervalo, ");
        sql.append("       inst.idFluxo, tipo.idTipoFluxo, tipo.nomeClasseFluxo ");
        sql.append("  FROM bpm_itemtrabalhofluxo item inner join bpm_elementofluxo elem on elem.idElemento = item.idElemento ");
        sql.append("                                  inner join bpm_instanciafluxo inst on inst.idInstancia = item.idInstancia ");
        sql.append("                                  inner join bpm_fluxo fluxo on fluxo.idFluxo = inst.idFluxo ");
        sql.append("                                  inner join bpm_tipofluxo tipo on tipo.idTipoFluxo = fluxo.idTipoFluxo ");
        sql.append(" WHERE elem.tipoElemento = '" + TipoElementoFluxo.Evento.name() + "'");
        return sql;
    }
    
    private List getColunasRestoreAll() {
    
        List listRetorno = new ArrayList();

        listRetorno.add("idItemTrabalho");
        listRetorno.add("idInstancia");
        listRetorno.add("idElemento");
        listRetorno.add("dataHoraCriacao");
        listRetorno.add("dataHoraFinalizacao");
        listRetorno.add("dataHoraExecucao");
        listRetorno.add("situacao");
        listRetorno.add("intervalo");
        listRetorno.add("idFluxo");
        listRetorno.add("idTipoFluxo");
        listRetorno.add("nomeClasseFluxo");
        return listRetorno;
    }   
    
    public Collection<EventoFluxoDTO> findDisponiveis() throws Exception {
        List parametro = new ArrayList();

        StringBuffer sql = getSQLRestoreAll();
        sql.append("    AND item.situacao <> ? ");
        sql.append("    AND item.situacao <> ? ");
        sql.append("    AND item.situacao <> ? ");
        sql.append("    AND inst.situacao = ? ");        
        
        parametro.add(SituacaoItemTrabalho.Cancelado.name());
        parametro.add(SituacaoItemTrabalho.Executado.name());
        parametro.add(SituacaoItemTrabalho.Suspenso.name());
        parametro.add(SituacaoInstanciaFluxo.Aberta.name());

        sql.append("ORDER BY item.dataHoraCriacao");
        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        
        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }		
	
    public Collection<EventoFluxoDTO> findDisponiveis(Integer idInstancia) throws Exception {
        List parametro = new ArrayList();

        StringBuffer sql = getSQLRestoreAll();
        sql.append("    AND item.idInstancia = ? ");
        sql.append("    AND item.situacao <> ? ");
        sql.append("    AND item.situacao <> ? ");
        sql.append("    AND item.situacao <> ? ");
        
        parametro.add(idInstancia);
        parametro.add(SituacaoItemTrabalho.Cancelado.name());
        parametro.add(SituacaoItemTrabalho.Executado.name());
        parametro.add(SituacaoItemTrabalho.Suspenso.name());
        sql.append("ORDER BY item.dataHoraCriacao");
        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        
        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }
}
