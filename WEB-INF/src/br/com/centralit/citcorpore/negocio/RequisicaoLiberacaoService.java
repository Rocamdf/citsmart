package br.com.centralit.citcorpore.negocio;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.PesquisaRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceEjb2;
public interface RequisicaoLiberacaoService extends CrudServiceEjb2 {
		@SuppressWarnings("rawtypes")
		public Collection findByIdSolicitante(Integer parm) throws Exception;
		public List<RequisicaoLiberacaoDTO> listLiberacoes() throws Exception;
		public RequisicaoLiberacaoDTO restoreAll(Integer idRequisicaoLiberacao) throws Exception;
		public void reativa(UsuarioDTO usuarioDto, RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception;
		public void gravaInformacoesGED(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO, TransactionControler tc) throws Exception;
		public List<RequisicaoLiberacaoDTO> findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws Exception;
		public void updateSimples(IDto model);
		
		/**
		 * suspende a requisição mudança
		 * @param usuarioDto
		 * @param solicitacaoServicoDto
		 * @throws Exception
		 * @author maycon.fernandes
		 */
		public void suspende(UsuarioDTO usuarioDto, RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception;
		public ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> listItensRelacionadosRequisicaoLiberacao(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws ServiceException, Exception;
		
		
		/**
		 * @param problemaDto
		 * @return Template Liberacao
		 * @throws Exception
		 */
		public String getUrlInformacoesComplementares(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception ;
		
		/**
		 * @param problemaDto
		 * @return Template Liberacao
		 * @throws Exception
		 */
		public String getUrlInformacoesQuestionario(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws Exception ;
		
		
		/**
		 * @param RequisicaoQuestionarioDTO
		 * @throws Exception
		 */
		public void atualizaInformacoesQuestionario(RequisicaoQuestionarioDTO requisicaoQuestionarioDTO) throws Exception;
		
		/**
		 * O metodo atualiza somente os campos setados os campos anteriores continuará intacto.
		 * 
		 * @param RequisicaoLiberacaoDTO
		 * @throws Exception
		 */
		public void updateLiberacaoAprovada(IDto obj) throws Exception ;
		
		Collection<RequisicaoLiberacaoDTO> listaRequisicaoLiberacaoPorCriterios(
				PesquisaRequisicaoLiberacaoDTO pesquisaRequisicaoLiberacaoDto)
				throws Exception;
		FluxoDTO recuperaFluxo(RequisicaoLiberacaoDTO requisicaoLiberacaoDto)
				throws Exception;
		void reabre(UsuarioDTO usuarioDto,
				RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception;
		
		
		/**
		 * Retorna uma lista de Liberaçoes que estejam relacionada a um determinado item de cofiguração.
		 * 
		 * @param Integer
		 * @return List<RequisicaoLiberacaoDTO>
		 * @throws Exception
		 */
		public List<RequisicaoLiberacaoDTO> listLiberacaoByItemConfiugracao(Integer idItemConfiguracao) throws Exception;

		
		public Timestamp MontardataHoraAgendamentoInicial(RequisicaoLiberacaoDTO requisicaoLiberacaoDto);
		public Timestamp MontardataHoraAgendamentoFinal(RequisicaoLiberacaoDTO requisicaoLiberacaoDto);
		public void calculaTempoAtraso(RequisicaoLiberacaoDTO requisicaoLiberacaoDto) throws Exception;
		public boolean seHoraInicialMenorQAtual(RequisicaoLiberacaoDTO requisicaoLiberacaoDto);
		public boolean seHoraFinalMenorQAtual(RequisicaoLiberacaoDTO requisicaoLiberacaoDto);
		public boolean seHoraFinalMenorQHoraInicial(RequisicaoLiberacaoDTO requisicaoLiberacaoDto);

		
		public boolean verificaPermissaoGrupoCancelar(Integer idTipoLiberacao, Integer idGrupo) throws ServiceException, Exception;
		

}
