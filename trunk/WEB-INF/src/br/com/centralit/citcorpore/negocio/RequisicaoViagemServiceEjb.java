package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.exception.LogicException;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.IntegranteViagemDao;
import br.com.centralit.citcorpore.integracao.ItemControleFinanceiroViagemDAO;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.ProjetoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoViagemDAO;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({ "unchecked", "serial","rawtypes" })
public class RequisicaoViagemServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements RequisicaoViagemService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoViagemDAO();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
	}

	@Override
	public IDto deserializaObjeto(String serialize) throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = null;

		if (serialize != null) {
			requisicaoViagemDto = (RequisicaoViagemDTO) WebUtil.deserializeObject(RequisicaoViagemDTO.class, serialize);
			if (requisicaoViagemDto != null && requisicaoViagemDto.getIntegranteViagemSerialize() != null) {
				requisicaoViagemDto.setIntegranteViagem(WebUtil.deserializeCollectionFromString(IntegranteViagemDTO.class, requisicaoViagemDto.getIntegranteViagemSerialize()));
			}
		}

		return requisicaoViagemDto;
	}

	@Override
	public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		this.validaAtualizacao(solicitacaoServicoDto, model);

	}

	@Override
	public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		this.validaAtualizacao(solicitacaoServicoDto, model);

	}

	public void validaAtualizacao(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) model;
		this.validaCentroResultado(requisicaoViagemDto);
		this.validaProjeto(requisicaoViagemDto);
		this.validaObrigatoriedade(requisicaoViagemDto);

	}
	


	public void validaObrigatoriedade(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		if (requisicaoViagemDto.getIdCidadeOrigem() == null) {
			throw new LogicException(i18n_Message("requisicaoViagem.cidadeOrigemCampoObrigatorio"));
		}
		if (requisicaoViagemDto.getIdCidadeDestino() == null) {
			throw new LogicException(i18n_Message("requisicaoViagem.cidadeDestinoCampoObrigatorio"));
		}
		if (requisicaoViagemDto.getDataInicioViagem() == null) {
			throw new LogicException(i18n_Message("requisicaoViagem.dataInicoCampoObrigatorio"));
		}
		if (requisicaoViagemDto.getDataFimViagem() == null) {
			throw new LogicException(i18n_Message("requisicaoViagem.dataFimCampoObrigatorio"));
		}
		if (requisicaoViagemDto.getIdMotivoViagem() == null) {
			throw new LogicException(i18n_Message("requisicaoViagem.justificativaCampoObrigatorio"));
		}
		if (requisicaoViagemDto.getDescricaoMotivo().equalsIgnoreCase("")) {
			throw new LogicException(i18n_Message("requisicaoViagem.descricaoMotivoCampoObrigatorio"));
		}
		if (requisicaoViagemDto.getDataInicioViagem().compareTo(UtilDatas.getDataAtual()) < 0){
			throw new LogicException(i18n_Message("solicitacaoliberacao.validacao.datainiciomenoratual"));
		}
		if (UtilStrings.nullToVazio(requisicaoViagemDto.getEstado()).equalsIgnoreCase(RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO)) {
			if (requisicaoViagemDto.getIntegranteViagem() == null) {
				throw new LogicException(i18n_Message("requisicaoViagem.integranteViagemCampoObrigatorio"));
			}

		}
	}

	private void validaProjeto(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		ProjetoDTO projetoDto = null;
		if (requisicaoViagemDto.getIdProjeto() != null) {
			projetoDto = new ProjetoDTO();
			projetoDto.setIdProjeto(requisicaoViagemDto.getIdProjeto());
			projetoDto = (ProjetoDTO) new ProjetoDao().restore(projetoDto);
		}
		if (projetoDto == null)
			throw new LogicException(i18n_Message("requisicaoViagem.projetoCampoObrigatorio"));
		if (projetoDto.getIdProjetoPai() == null)
			throw new LogicException(i18n_Message("requisicaoViagem.mensagemProjeto"));
	}

	private void validaCentroResultado(RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		CentroResultadoDTO centroCustoDto = null;
		if (requisicaoViagemDto.getIdCentroCusto() != null) {
			centroCustoDto = new CentroResultadoDTO();
			centroCustoDto.setIdCentroResultado(requisicaoViagemDto.getIdCentroCusto());
			centroCustoDto = (CentroResultadoDTO) new CentroResultadoDao().restore(centroCustoDto);
		}
		if (centroCustoDto == null)
			throw new LogicException(i18n_Message("requisicaoViagem.centroResultatoCampoObrigatorio"));
		if (centroCustoDto.getIdCentroResultadoPai() == null || (centroCustoDto.getPermiteRequisicaoProduto() == null || !centroCustoDto.getPermiteRequisicaoProduto().equalsIgnoreCase("S")))
			throw new LogicException(i18n_Message("requisicaoViagem.mensagemCentroResultado"));
	}
	
	

	@Override
	public IDto create(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) model;
		requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);

		SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
		RequisicaoViagemDAO requisicaoViagemDao = new RequisicaoViagemDAO();
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();

		validaCreate(solicitacaoServicoDto, model);

		requisicaoViagemDao.setTransactionControler(tc);
		integranteViagemDao.setTransactionControler(tc);
		solicitacaoServicoDao.setTransactionControler(tc);
		if (solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
			requisicaoViagemDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			requisicaoViagemDto = (RequisicaoViagemDTO) requisicaoViagemDao.create(requisicaoViagemDto);
		}

		if (requisicaoViagemDto.getIntegranteViagemSerialize() != null) {
			for (IntegranteViagemDTO integranteViagemDto : requisicaoViagemDto.getIntegranteViagem()) {
				integranteViagemDto.setIdSolicitacaoServico(requisicaoViagemDto.getIdSolicitacaoServico());
				integranteViagemDto.setIdEmpregado(integranteViagemDto.getIdEmpregado());
				integranteViagemDao.create(integranteViagemDto);
			}
		}
		return requisicaoViagemDto;
	}

	@Override
	public void update(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		RequisicaoViagemDTO requisicaoViagemDto = (RequisicaoViagemDTO) model;
		ParecerDTO parecerDto = new ParecerDTO();
		
		

		ParecerDao parecerDao = new ParecerDao();
		RequisicaoViagemDAO requisicaoViagemDao = new RequisicaoViagemDAO();
		IntegranteViagemDao integranteViagemDao = new IntegranteViagemDao();
		validaUpdate(solicitacaoServicoDto, model);
		
		
		parecerDao.setTransactionControler(tc);
		requisicaoViagemDao.setTransactionControler(tc);
		integranteViagemDao.setTransactionControler(tc);
		
		requisicaoViagemDto.setEstado(RequisicaoViagemDTO.AGUARDANDO_PLANEJAMENTO);
		
		parecerDto.setIdJustificativa(requisicaoViagemDto.getIdJustificativaAutorizacao());
		parecerDto.setIdResponsavel(solicitacaoServicoDto.getUsuarioDto().getIdEmpregado());
		parecerDto.setObservacoes(requisicaoViagemDto.getObservacoes());
		parecerDto.setComplementoJustificativa(requisicaoViagemDto.getComplemJustificativaAutorizacao());
		parecerDto.setAprovado(requisicaoViagemDto.getAutorizado());
		parecerDto.setDataHoraParecer(UtilDatas.getDataHoraAtual());
		
		parecerDto = (ParecerDTO) parecerDao.create(parecerDto);
		
		if(parecerDto !=null){
			requisicaoViagemDto.setIdAprovacao(parecerDto.getIdParecer());
			requisicaoViagemDao.updateNotNull(requisicaoViagemDto);
		}
		
		if (requisicaoViagemDto.getIntegranteViagemSerialize() != null) {
			for (IntegranteViagemDTO integranteViagemDto : requisicaoViagemDto.getIntegranteViagem()) {
				
				integranteViagemDto.setIdSolicitacaoServico(requisicaoViagemDto.getIdSolicitacaoServico());
				integranteViagemDto.setIdEmpregado(integranteViagemDto.getIdEmpregado());
				if(!integranteViagemDao.verificarSeIntegranteViagemExiste(integranteViagemDto.getIdSolicitacaoServico(), integranteViagemDto.getIdEmpregado())){
					integranteViagemDao.create(integranteViagemDto);
				}
				
			}
		}
		
	}

	@Override
	public void delete(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
		// TODO Auto-generated method stub

	}

	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemBySolicitacao(Integer idSolicitacao) throws Exception {
		Collection<IntegranteViagemDTO> colIntegrantesViagem = new ArrayList<IntegranteViagemDTO>();

		IntegranteViagemDao integranteDao = new IntegranteViagemDao();
		EmpregadoDao empregadoDao = new EmpregadoDao();
		EmpregadoDTO empregado;

		Collection<IntegranteViagemDTO> colIntegrantAux = integranteDao.findAllByIdSolicitacao(idSolicitacao);
		if (colIntegrantAux != null) {
			for (IntegranteViagemDTO integrante : colIntegrantAux) {
				empregado = empregadoDao.restoreByIdEmpregado(integrante.getIdEmpregado());
				integrante.setNome(empregado.getNome());
				integrante.setEmail(empregado.getEmail());
				colIntegrantesViagem.add(integrante);
			}
		}
		return colIntegrantesViagem;
	}

	public void enviaEmailIntegranteViagem(Integer idModeloEmail, RequisicaoViagemDTO requisicaoDto) throws Exception {

		Set emailsIntegrantes = new HashSet();
		
		Collection<IntegranteViagemDTO> colIntegrantes = this.recuperaIntegrantesViagemBySolicitacao(requisicaoDto.getIdSolicitacaoServico());

		if (colIntegrantes != null) {
			for (IntegranteViagemDTO integranteViagemDto : colIntegrantes) {
				emailsIntegrantes.add(integranteViagemDto.getEmail());
			}

		}
		
		MensagemEmail mensagemEmail = new MensagemEmail(56, new IDto[] { requisicaoDto });

		mensagemEmail.envia(StringUtils.remove(StringUtils.remove(emailsIntegrantes.toString(), "["),"]"), StringUtils.remove(StringUtils.remove(emailsIntegrantes.toString(), "["),"]"), ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, "10") );

	}

	public Double calculaValorTotalViagem(Integer idSolicitacao) throws Exception{
		Double valorTotal = 0.0;
		ItemControleFinanceiroViagemDAO itemDao = new ItemControleFinanceiroViagemDAO();
		Collection<ItemControleFinanceiroViagemDTO> colItens = itemDao.findAllBySolicitacao(idSolicitacao);
		if(colItens != null){
			for(ItemControleFinanceiroViagemDTO itemDto : colItens){
				valorTotal += itemDto.getValorAdiantamento();
			}
		}
		return valorTotal;
	}
	
}
