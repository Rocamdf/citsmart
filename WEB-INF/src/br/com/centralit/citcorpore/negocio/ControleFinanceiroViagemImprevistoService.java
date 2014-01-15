package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;

public interface ControleFinanceiroViagemImprevistoService extends ComplemInfSolicitacaoServicoService{

	Collection<RequisicaoViagemDTO> recuperaRequisicoesViagem(ControleFinanceiroViagemDTO controleFinanceiroViagemDto, Integer paginaSelecionada, Integer itensPorPagina) throws Exception;

	Integer calculaTotalPaginas(Integer itensPorPagina, ControleFinanceiroViagemDTO controleFinanceiroViagemDto) throws Exception;
	public IDto createAll(IDto model) throws ServiceException, LogicException;
}
