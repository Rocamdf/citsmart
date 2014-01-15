package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface ContatoRequisicaoMudancaService extends CrudServicePojo {
	public ContatoRequisicaoMudancaDTO restoreContatosById(Integer idContatoRequisicaoMudanca);
}
