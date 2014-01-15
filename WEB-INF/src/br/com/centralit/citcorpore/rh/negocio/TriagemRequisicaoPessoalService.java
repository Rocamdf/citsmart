package br.com.centralit.citcorpore.rh.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface TriagemRequisicaoPessoalService extends CrudServiceEjb2 {
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception;
	public void deleteByIdSolicitacaoServico(Integer parm) throws Exception;
	public Collection<CurriculoDTO> sugereCurriculos(RequisicaoPessoalDTO requisicaoPessoalDto,DescricaoCargoDTO descricaoCargoDto) throws Exception;
	public Collection<CurriculoDTO> triagemManual() throws Exception;
	public Collection<CurriculoDTO> triagemManualPorCriterios(RequisicaoPessoalDTO requisicaoPessoalDTO) throws Exception;
	public Collection findByIdSolicitacaoServicoAndIdTarefa(Integer idSolicitacaoServico, Integer idTarefa) throws Exception;
}
