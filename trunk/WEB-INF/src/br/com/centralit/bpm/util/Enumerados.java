package br.com.centralit.bpm.util;

import java.io.Serializable;

public class Enumerados implements Serializable {
    
	public static final String ACAO_DELEGAR = "D";
	public static final String ACAO_INICIAR = "I";
	public static final String ACAO_EXECUTAR = "E";
	public static final String ACAO_VISUALIZAR = "V";
	
	public static final String INTERACAO_VISAO = "V";
	public static final String INTERACAO_URL = "U";
	
	public static final String TIPO_ASSOCIACAO_INSTANCIA = "I";
	public static final String TIPO_ASSOCIACAO_TAREFA = "T";
	
	public static final String INSTANCIA_ABERTA = SituacaoInstanciaFluxo.Aberta.name();
	public static final String INSTANCIA_ENCERRADA = SituacaoInstanciaFluxo.Encerrada.name();

	public enum SituacaoItemTrabalho {
        Criado("Criado"),
        Disponivel("Disponível"),
        EmAndamento("Em andamento"),
        Suspenso("Suspenso"),
        Cancelado("Cancelado"),
        Encerrado("Suspenso"),
        Executado("Executado");

        private String descricao;
        
        SituacaoItemTrabalho (String descricao){
            this.descricao  = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }    

    public enum SituacaoInstanciaFluxo {
        Aberta("Aberta"),
        Suspensa("Suspensa"),
        Cancelada("Cancelada"),
        Encerrada("Encerrada");

        private String descricao;
        
        SituacaoInstanciaFluxo (String descricao){
            this.descricao  = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }    

    public enum TipoAtribuicao {
        Automatica("Automática"),
        Acompanhamento("Acompanhamento"),
        Delegacao("Delegação");

        private String descricao;
        
        TipoAtribuicao (String descricao){
            this.descricao  = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }    

    public enum TipoElementoFluxo {
        Inicio("Início"),
        Tarefa("Tarefa"),
        Script("Script"),
        Email("Email"),
        Porta("Porta"),
        Evento("Evento"),
        Finalizacao("Finalização");

        private String descricao;
        
        TipoElementoFluxo (String descricao){
            this.descricao  = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }    

    public enum TipoPorta {
        Decisao("Decisão"),
        Paralela("Paralela"),
        Convergente("Convergente");

        private String descricao;
        
        TipoPorta (String descricao){
            this.descricao  = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }    

    public enum TipoFinalizacao {
        Encadeada("Encadeada");

        private String descricao;
        
        TipoFinalizacao (String descricao){
            this.descricao  = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }    
    
    public enum AcaoItemTrabalho {
        Iniciar("Iniciar"),
        Executar("Executar"),
        Delegar("Delegar"),
        Suspender("Suspender"),
        Cancelar("Cancelar");

        private String descricao;
        
        AcaoItemTrabalho (String descricao){
            this.descricao  = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }

}
