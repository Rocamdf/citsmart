package br.com.centralit.bpm.dto;

public class EventoFluxoDTO extends ItemTrabalhoFluxoDTO {
    
    private Integer intervalo;
    private Integer idTipoFluxo;
    private Integer idFluxo;
    private String nomeClasseFluxo;

    public Integer getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(Integer intervalo) {
        this.intervalo = intervalo;
    }

    public Integer getIdTipoFluxo() {
        return idTipoFluxo;
    }

    public void setIdTipoFluxo(Integer idTipoFluxo) {
        this.idTipoFluxo = idTipoFluxo;
    }

    public Integer getIdFluxo() {
        return idFluxo;
    }

    public void setIdFluxo(Integer idFluxo) {
        this.idFluxo = idFluxo;
    }

    public String getNomeClasseFluxo() {
        return nomeClasseFluxo;
    }

    public void setNomeClasseFluxo(String nomeClasseFluxo) {
        this.nomeClasseFluxo = nomeClasseFluxo;
    }
    
}
