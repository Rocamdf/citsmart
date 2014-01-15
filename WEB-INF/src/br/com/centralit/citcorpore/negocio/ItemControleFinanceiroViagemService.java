package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface ItemControleFinanceiroViagemService extends CrudServiceEjb2{
	
	public boolean consultarItemAtivo(ItemControleFinanceiroViagemDTO obj) throws Exception;
	public void deletarItem(IDto model, DocumentHTML document) throws ServiceException, Exception;
	@SuppressWarnings("rawtypes")
	public Collection recuperaItensControleFinanceiro(ItemControleFinanceiroViagemDTO itemControle) throws Exception;
	public boolean verificaExisteItens(Integer idSolicitacao) throws Exception;
	public Collection<ItemControleFinanceiroViagemDTO> listaItensAdiantamento(Integer idSolicitacaoServico, Integer idEmpregado) throws Exception;
	public boolean verificaItensAdiantamento(Integer idSolicitacaoServico) throws Exception;
	public Collection<ItemControleFinanceiroViagemDTO> listaItensCompra(Integer idSolicitacaoServico, Integer idEmpregado) throws Exception;
}
