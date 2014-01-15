package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.EmailSolicitacaoServicoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface EmailSolicitacaoServicoService extends CrudServiceEjb2 {

    public EmailSolicitacaoServicoDTO listSituacao(String messageid) throws Exception;
}

