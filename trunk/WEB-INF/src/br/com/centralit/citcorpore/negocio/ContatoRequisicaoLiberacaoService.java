package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ContatoRequisicaoLiberacaoDTO;
import br.com.citframework.service.CrudServicePojo;

public interface ContatoRequisicaoLiberacaoService extends CrudServicePojo {
	public ContatoRequisicaoLiberacaoDTO restoreContatosById(Integer idContatoRequisicaoLiberacao);
}

