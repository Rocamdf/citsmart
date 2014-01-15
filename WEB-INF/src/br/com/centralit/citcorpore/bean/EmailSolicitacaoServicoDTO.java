package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

@SuppressWarnings("serial")
public class EmailSolicitacaoServicoDTO implements IDto{
    
    private Integer idEmailSolicitacaoServico;
    private String messageId;
    private String situacao;
    
    
    
    /**
     * @return the idEmailSolicitacaoServico
     */
    public Integer getIdEmailSolicitacaoServico() {
        return idEmailSolicitacaoServico;
    }
    /**
     * @param idEmailSolicitacaoServico the idEmailSolicitacaoServico to set
     */
    public void setIdEmailSolicitacaoServico(Integer idEmailSolicitacaoServico) {
        this.idEmailSolicitacaoServico = idEmailSolicitacaoServico;
    }
    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }
    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    /**
     * @return the situacao
     */
    public String getSituacao() {
        return situacao;
    }
    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

}
