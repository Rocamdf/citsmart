package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.citframework.service.CrudServicePojo;

public interface EntrevistaCandidatoService extends CrudServicePojo {
	public EntrevistaCandidatoDTO findByIdTriagemAndIdCurriculo(Integer idTriagem, Integer idCurriculo) throws Exception;
	public Collection listCurriculosAprovadosPorOrdemMaiorNota(Integer idTriagem) throws Exception;
	public Boolean seCandidatoAprovado(TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDTO) throws Exception;
}
