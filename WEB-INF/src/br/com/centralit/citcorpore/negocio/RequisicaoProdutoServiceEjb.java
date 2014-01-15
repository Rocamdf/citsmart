package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.CotacaoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.centralit.citcorpore.bean.EntregaItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.InspecaoEntregaItemDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoRequisicaoProduto;
import br.com.centralit.citcorpore.exception.LogicException;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.CotacaoItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.EnderecoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.InspecaoEntregaItemDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.integracao.ProdutoDao;
import br.com.centralit.citcorpore.integracao.ProjetoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoProdutoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacaoItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoEntregaItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.WebUtil;
public class RequisicaoProdutoServiceEjb extends ComplemInfSolicitacaoServicoServiceEjb implements RequisicaoProdutoService {
	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoProdutoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

    @Override
	public IDto deserializaObjeto(String serialize) throws Exception {
	    RequisicaoProdutoDTO requisicaoProdutoDto = null;
	    
        if (serialize != null) {
            requisicaoProdutoDto = (RequisicaoProdutoDTO) WebUtil.deserializeObject(RequisicaoProdutoDTO.class, serialize);
            if (requisicaoProdutoDto != null && requisicaoProdutoDto.getItensRequisicao_serialize() != null)
                requisicaoProdutoDto.setItensRequisicao(WebUtil.deserializeCollectionFromString(ItemRequisicaoProdutoDTO.class, requisicaoProdutoDto.getItensRequisicao_serialize()));
            if (requisicaoProdutoDto != null && requisicaoProdutoDto.getItensCotacao_serialize() != null)
                requisicaoProdutoDto.setItensCotacao(WebUtil.deserializeCollectionFromString(CotacaoItemRequisicaoDTO.class, requisicaoProdutoDto.getItensCotacao_serialize()));
            if (requisicaoProdutoDto != null && requisicaoProdutoDto.getItensEntrega_serialize() != null)
                requisicaoProdutoDto.setItensEntrega(WebUtil.deserializeCollectionFromString(EntregaItemRequisicaoDTO.class, requisicaoProdutoDto.getItensEntrega_serialize()));
        }
        
        return requisicaoProdutoDto;
	}

    @Override
    public void validaCreate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
        validaAtualizacao(solicitacaoServicoDto, model);
    }

    @Override
    public void validaDelete(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void validaUpdate(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
        validaAtualizacao(solicitacaoServicoDto, model);
    }

    public void validaSolicitanteAutorizado(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
        String permiteRequisicao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PEMITE_REQUISICAO_EMPREGADO_COMPRAS, "N");
        if (permiteRequisicao == null || permiteRequisicao.equals(""))
            permiteRequisicao = "N";
        if (permiteRequisicao.equals("S"))
            return;
        
        String idGrupo = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_REQ_PRODUTOS, null);
        if (idGrupo == null || idGrupo.trim().equals(""))
            throw new Exception("Grupo padrão de requisição de produtos não parametrizado");

        Collection<GrupoEmpregadoDTO> colGrupos = new GrupoEmpregadoDao().findByIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
        if (colGrupos != null) {
            int id = new Integer(idGrupo).intValue();
            for (GrupoEmpregadoDTO grupoEmpregadoDto : colGrupos) {
                if (grupoEmpregadoDto.getIdGrupo().intValue() == id)
                    throw new Exception("Não é permitido que um empregado do grupo de Compras faça uma requisição de produtos");
            }
        }
    }
    
    public void validaAtualizacao(SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
        validaSolicitanteAutorizado(solicitacaoServicoDto, model);
        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) model;
        validaCentroResultado(requisicaoProdutoDto);
        validaProjeto(requisicaoProdutoDto);
        
        if (requisicaoProdutoDto.getRejeitada() == null || requisicaoProdutoDto.getRejeitada().trim().length() == 0)
            requisicaoProdutoDto.setRejeitada("N");
        if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_APROVACAO)) {
            if (requisicaoProdutoDto.getItensCotacao() != null) {
                Collection<CotacaoItemRequisicaoDTO> colItens = requisicaoProdutoDto.getItensCotacao();
                for (CotacaoItemRequisicaoDTO itemDto : colItens) {
                    if (itemDto.getAprovado() == null || itemDto.getAprovado().trim().length() == 0)
                        throw new LogicException("Parecer não informado para o item '"+itemDto.getDescricaoItem()+"'");
                    if (itemDto.getAprovado().equalsIgnoreCase("N") && itemDto.getIdJustificativa() == null)
                        throw new LogicException("Justificativa não informada para o item '"+itemDto.getDescricaoItem()+"'");
                    if (itemDto.getAprovado().equalsIgnoreCase("S") && itemDto.getPercVariacaoPreco() == null)
                        throw new LogicException("Percentual variação de preço a maior não informado para o item '"+itemDto.getDescricaoItem()+"'");                    
                }                
            }  
        }else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_INSPECAO)) {
            Collection<EntregaItemRequisicaoDTO> colEntregas = requisicaoProdutoDto.getItensEntrega();
            if (colEntregas != null) {
                InspecaoEntregaItemDao inspecaoEntregaDao = new InspecaoEntregaItemDao();
                for (EntregaItemRequisicaoDTO entregaDto : colEntregas) {
                    if (entregaDto.getAprovado() == null || entregaDto.getAprovado().trim().length() == 0)
                        throw new LogicException("Parecer não informado para a entrega '"+entregaDto.getDescricaoItem()+"'");
                    if (entregaDto.getAprovado().equalsIgnoreCase("N") && entregaDto.getIdJustificativa() == null)
                        throw new LogicException("Justificativa não informada para a entrega '"+entregaDto.getDescricaoItem()+"'");
                    if (entregaDto.getAprovado().equalsIgnoreCase("N"))
                        continue;
                    Collection<InspecaoEntregaItemDTO> colInspecoes = inspecaoEntregaDao.findByIdEntrega(entregaDto.getIdEntrega());
                    if (colInspecoes == null || colInspecoes.size() == 0)
                        throw new LogicException("Existe pelo menos uma entrega não avaliada");
                }
            }
        }else {
            ProdutoDao produtoDao = new ProdutoDao();
            if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_CRIACAO)) {
                if (requisicaoProdutoDto.getItensRequisicao() == null)
                    throw new LogicException("Não foi informado nenhum item da requisição");
                String percVariacaoPrecoStr = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PERC_MAX_VAR_PRECO_COTACAO, "0,0");
                if (percVariacaoPrecoStr == null || percVariacaoPrecoStr.equals(""))
                    percVariacaoPrecoStr = "0,0";
                for (ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
                    //if (itemRequisicaoDto.getPrecoAproximado() == null || itemRequisicaoDto.getPrecoAproximado().doubleValue() == 0)
                    //    throw new LogicException("Preço aproximado não informado");
                    if (itemRequisicaoDto.getIdProduto() != null && itemRequisicaoDto.getIdProduto().intValue() < 0)
                        itemRequisicaoDto.setIdProduto(null);
                    if (percVariacaoPrecoStr != null) 
                        itemRequisicaoDto.setPercVariacaoPreco(new Double(percVariacaoPrecoStr.replaceAll(",", ".")));
                    itemRequisicaoDto.setQtdeAprovada(itemRequisicaoDto.getQuantidade());
                    itemRequisicaoDto.setTipoIdentificacao("D");
                    if (itemRequisicaoDto.getIdProduto() != null) {
                        ProdutoDTO produtoDto = new ProdutoDTO();
                        produtoDto.setIdProduto(itemRequisicaoDto.getIdProduto());
                        produtoDto = (ProdutoDTO) produtoDao.restore(produtoDto);
                        if (produtoDto != null) {
                            itemRequisicaoDto.setIdCategoriaProduto(produtoDto.getIdCategoria());
                            itemRequisicaoDto.setDescricaoItem(produtoDto.getIdentificacao());
                            itemRequisicaoDto.setIdUnidadeMedida(produtoDto.getIdUnidadeMedida());
                            itemRequisicaoDto.setEspecificacoes(produtoDto.getDetalhes());
                            itemRequisicaoDto.setPrecoAproximado(produtoDto.getPrecoMercado());
                            itemRequisicaoDto.setMarcaPreferencial(produtoDto.getNomeMarca());
                            itemRequisicaoDto.setTipoIdentificacao("S");
                            itemRequisicaoDto.setValorAprovado(new Double(0));
                        }
                    }
                    if (itemRequisicaoDto.getPrecoAproximado() == null)
                    	itemRequisicaoDto.setPrecoAproximado(new Double(0));
                }
            }
            if (requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_ACOMPANHAMENTO)) {
                    Collection<ItemRequisicaoProdutoDTO> colItens = new ItemRequisicaoProdutoDao().findByIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico());
                    requisicaoProdutoDto.setItensRequisicao(colItens);
                    for (ItemRequisicaoProdutoDTO itemDto : colItens) {
                    	if (itemDto.cotacaoIniciada())
                            throw new LogicException("O item '"+itemDto.getDescricaoItem().trim()+"' está em processo de cotação.\nA requisição não pode ser rejeitada.");
                    }
                }
                return;
            }
            if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_VALIDACAO)) {
                Collection<ItemRequisicaoProdutoDTO> colItens = requisicaoProdutoDto.getItensRequisicao();
                for (ItemRequisicaoProdutoDTO itemDto : colItens) {
                    if (itemDto.getValidado() == null || itemDto.getValidado().trim().length() == 0)
                        throw new LogicException("Parecer não informado para o item '"+itemDto.getDescricaoItem()+"'");
                    if (itemDto.getValidado().equalsIgnoreCase("S") && itemDto.getIdProduto() == null && itemDto.getIdCategoriaProduto() == null)
                        throw new LogicException("Categoria do produto não informada para o item '"+itemDto.getDescricaoItem()+"'");
                    if (itemDto.getValidado().equalsIgnoreCase("N") && itemDto.getIdJustificativaValidacao() == null)
                        throw new LogicException("Justificativa não informada para o item '"+itemDto.getDescricaoItem()+"'");
                    if (itemDto.getValidado().equalsIgnoreCase("S") && itemDto.getTipoAtendimento() == null)
                        throw new LogicException("Tipo de atendimento não informado para o item '"+itemDto.getDescricaoItem()+"'");
                    if (itemDto.getIdProduto() != null && itemDto.getIdCategoriaProduto() == null) {
                        ProdutoDTO produtoDto = new ProdutoDTO();
                        produtoDto.setIdProduto(itemDto.getIdProduto());
                        produtoDto = (ProdutoDTO) produtoDao.restore(produtoDto);
                        itemDto.setIdCategoriaProduto(produtoDto.getIdCategoria());
                    }
                }
            }
            if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_AUTORIZACAO)) {
                Collection<ItemRequisicaoProdutoDTO> colItens = requisicaoProdutoDto.getItensRequisicao();
                for (ItemRequisicaoProdutoDTO itemDto : colItens) {
                    if (itemDto.getAutorizado() == null || itemDto.getAutorizado().trim().length() == 0)
                        throw new LogicException("Parecer não informado para o item '"+itemDto.getDescricaoItem()+"'");
                    if (itemDto.getAutorizado().equalsIgnoreCase("N") && itemDto.getIdJustificativaAutorizacao() == null)
                        throw new LogicException("Justificativa não informada para o item '"+itemDto.getDescricaoItem()+"'");
                    if (itemDto.getAutorizado().equalsIgnoreCase("S")) {
                        if (itemDto.getQtdeAprovada() == null)
                            throw new LogicException("Quantidade aprovada não informada para o item '"+itemDto.getDescricaoItem()+"'");
                    }
                }
            }            
        }
    }

    private void validaProjeto(RequisicaoProdutoDTO requisicaoProdutoDto) throws Exception {
        ProjetoDTO projetoDto = null;
        if (requisicaoProdutoDto.getIdProjeto() != null) {
            projetoDto = new ProjetoDTO();
            projetoDto.setIdProjeto(requisicaoProdutoDto.getIdProjeto());
            projetoDto = (ProjetoDTO) new ProjetoDao().restore(projetoDto);    
        }
        if (projetoDto == null)
            throw new LogicException("Projeto não encontrado");
        if (projetoDto.getIdProjetoPai() == null) 
            throw new LogicException("Você não pode escolher um projeto raiz. Selecione um projeto de nível analítico.");
    }

    private void validaCentroResultado(RequisicaoProdutoDTO requisicaoProdutoDto) throws Exception {
        CentroResultadoDTO centroCustoDto = null;
        if (requisicaoProdutoDto.getIdCentroCusto() != null) {
            centroCustoDto = new CentroResultadoDTO();
            centroCustoDto.setIdCentroResultado(requisicaoProdutoDto.getIdCentroCusto());
            centroCustoDto = (CentroResultadoDTO) new CentroResultadoDao().restore(centroCustoDto);    
        }
        if (centroCustoDto == null)
            throw new LogicException("Centro de custo não encontrado");
        if (centroCustoDto.getIdCentroResultadoPai() == null || (centroCustoDto.getPermiteRequisicaoProduto() == null || !centroCustoDto.getPermiteRequisicaoProduto().equalsIgnoreCase("S"))) 
            throw new LogicException("Centro de custo não permite requisição de produtos e serviços");
    }

    @Override
    public IDto create(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) model;
        requisicaoProdutoDto.setAcao(RequisicaoProdutoDTO.ACAO_CRIACAO);
        
        SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();
        RequisicaoProdutoDao requisicaoProdutoDao = new RequisicaoProdutoDao();
        ItemRequisicaoProdutoDao itemRequisicaoProdutoDao = new ItemRequisicaoProdutoDao();

        validaCreate(solicitacaoServicoDto, model);

        requisicaoProdutoDao.setTransactionControler(tc); 
        itemRequisicaoProdutoDao.setTransactionControler(tc);
        solicitacaoServicoDao.setTransactionControler(tc);
        
        requisicaoProdutoDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
        requisicaoProdutoDto = (RequisicaoProdutoDTO) requisicaoProdutoDao.create(requisicaoProdutoDto);
        
        String determinaUrgenciaImpacto = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.DETERMINA_URGENCIA_IMPACTO_REQPROD, null);
        if (determinaUrgenciaImpacto == null || determinaUrgenciaImpacto.trim().equals(""))
            determinaUrgenciaImpacto = "N";
        
        if (determinaUrgenciaImpacto.equals("S")) {
            if (requisicaoProdutoDto.getFinalidade().equalsIgnoreCase("C")) {
                solicitacaoServicoDto.setUrgencia("A");
                solicitacaoServicoDto.setImpacto("A");
                solicitacaoServicoDto.setIdPrioridade(new Integer(1));
            }else{
                solicitacaoServicoDto.setUrgencia("M");
                solicitacaoServicoDto.setImpacto("M");
                solicitacaoServicoDto.setIdPrioridade(new Integer(2));
            }
            solicitacaoServicoDao.atualizaUrgenciaImpacto(solicitacaoServicoDto);
        }
        
        if (requisicaoProdutoDto.getItensRequisicao() != null) {
            ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
            for (ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
                itemRequisicaoDto.setIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico());
                itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoValidacao.name());
                itemRequisicaoProdutoDao.create(itemRequisicaoDto);
                itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Criacao, null, SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
            }
        }

        return requisicaoProdutoDto;
    }

    @Override
    public void delete(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
        // TODO Auto-generated method stub
        
    }

    public void cancelaSolicitacao(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
        SolicitacaoServicoDao solicitacaoDao = new SolicitacaoServicoDao();
        solicitacaoDao.setTransactionControler(tc);
        solicitacaoServicoDto.setSituacao(SituacaoSolicitacaoServico.Cancelada.name());
        solicitacaoDao.atualizaSituacao(solicitacaoServicoDto);
    }
    
    @Override
    public void update(TransactionControler tc, SolicitacaoServicoDTO solicitacaoServicoDto, IDto model) throws Exception {
        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) model;
        requisicaoProdutoDto.setIdSolicitante(solicitacaoServicoDto.getIdSolicitante());

        RequisicaoProdutoDao requisicaoProdutoDao = new RequisicaoProdutoDao();
        ItemRequisicaoProdutoDao itemRequisicaoProdutoDao = new ItemRequisicaoProdutoDao();
        CotacaoItemRequisicaoServiceEjb cotacaoItemRequisicaoService = new CotacaoItemRequisicaoServiceEjb();
        EntregaItemRequisicaoServiceEjb entregaItemRequisicaoService = new EntregaItemRequisicaoServiceEjb();
        
        validaUpdate(solicitacaoServicoDto, model);

        ParecerServiceEjb parecerService = new ParecerServiceEjb();
        requisicaoProdutoDao.setTransactionControler(tc); 
        itemRequisicaoProdutoDao.setTransactionControler(tc);
        
        if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_APROVACAO)) {
            Collection<CotacaoItemRequisicaoDTO> colItens = requisicaoProdutoDto.getItensCotacao();
            for (CotacaoItemRequisicaoDTO itemDto : colItens) {
                cotacaoItemRequisicaoService.atualizaAprovacaoCotacao(itemDto, solicitacaoServicoDto.getUsuarioDto(), tc);
            }
        }else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_INSPECAO)) {
            Collection<EntregaItemRequisicaoDTO> colEntregas = requisicaoProdutoDto.getItensEntrega();
            for (EntregaItemRequisicaoDTO entregaDto : colEntregas) {
                entregaItemRequisicaoService.atualizaSituacao(entregaDto, solicitacaoServicoDto.getUsuarioDto(), tc);
            }
        }else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_GARANTIA)) {
            Collection<EntregaItemRequisicaoDTO> colEntregas = requisicaoProdutoDto.getItensEntrega();
            for (EntregaItemRequisicaoDTO entregaDto : colEntregas) {
                entregaItemRequisicaoService.atualizaAcionamentoGarantia(entregaDto, solicitacaoServicoDto.getUsuarioDto(), tc);
            }            
        }else if (requisicaoProdutoDto.getItensRequisicao() != null) {
            ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
            if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_VALIDACAO)) {
                boolean bExigeAutorizacao = new ExecucaoRequisicaoProduto(requisicaoProdutoDto, tc).exigeAutorizacao(requisicaoProdutoDto);
                int naoValidos = 0;
                Collection<ItemRequisicaoProdutoDTO> colItens = requisicaoProdutoDto.getItensRequisicao();
                Collection<ItemRequisicaoProdutoDTO> colItensAtuais = new ArrayList();
                for (ItemRequisicaoProdutoDTO itemDto : colItens) {
                    ItemRequisicaoProdutoDTO itemAuxDto = new ItemRequisicaoProdutoDTO();
                    itemAuxDto.setIdItemRequisicaoProduto(itemDto.getIdItemRequisicaoProduto());
                    itemAuxDto = (ItemRequisicaoProdutoDTO) itemRequisicaoProdutoDao.restore(itemAuxDto);
                    itemAuxDto.setIdProduto(itemDto.getIdProduto());
                    itemAuxDto.setTipoAtendimento(itemDto.getTipoAtendimento());
                    itemAuxDto.setIdCategoriaProduto(itemDto.getIdCategoriaProduto());
                    if (requisicaoProdutoDto.getRejeitada() != null && requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                        itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.RejeitadoCompras.name());
                    }else {
	                    ParecerDTO parecerDto = parecerService.createOrUpdate(tc, itemAuxDto.getIdParecerValidacao(), solicitacaoServicoDto.getUsuarioDto(), itemDto.getIdJustificativaValidacao(), itemDto.getComplemJustificativaValidacao(), itemDto.getValidado());
	                    itemAuxDto.setIdParecerValidacao(parecerDto.getIdParecer());
	                    if (parecerDto.getAprovado().equalsIgnoreCase("S")){
	                        if (bExigeAutorizacao) {
	                            itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoAutorizacaoCompra.name());
	                            itemAuxDto.setAprovaCotacao("S");
	                        }else{
	                            itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoCotacao.name());
	                            itemAuxDto.setAprovaCotacao("N");
	                        }
	
	                    }else if (parecerDto.getAprovado().equalsIgnoreCase("N")){
	                        itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.Inviabilizado.name());
	                        naoValidos ++;
	                    }
                    }
                    itemRequisicaoProdutoDao.update(itemAuxDto);
                    colItensAtuais.add(itemAuxDto);
                }
                if (naoValidos == colItens.size() && requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("N")) 
                    requisicaoProdutoDto.setRejeitada("S");
                if (requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                    for (ItemRequisicaoProdutoDTO itemDto : colItens) {
                        itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemDto, AcaoItemRequisicaoProduto.Validacao, "", SituacaoItemRequisicaoProduto.RejeitadoCompras);
                    }
                }else{
                    for (ItemRequisicaoProdutoDTO itemDto : colItensAtuais) {
                        itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemDto, AcaoItemRequisicaoProduto.Validacao, null, SituacaoItemRequisicaoProduto.valueOf(itemDto.getSituacao()));
                    }
                }
            }else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_AUTORIZACAO)) {
                int naoAutorizados = 0;
                Collection<ItemRequisicaoProdutoDTO> colItens = requisicaoProdutoDto.getItensRequisicao();
                Collection<ItemRequisicaoProdutoDTO> colItensAtuais = new ArrayList();
                for (ItemRequisicaoProdutoDTO itemDto : colItens) {
                    ItemRequisicaoProdutoDTO itemAuxDto = new ItemRequisicaoProdutoDTO();
                    itemAuxDto.setIdItemRequisicaoProduto(itemDto.getIdItemRequisicaoProduto());
                    itemAuxDto = (ItemRequisicaoProdutoDTO) itemRequisicaoProdutoDao.restore(itemAuxDto);
                    itemAuxDto.setIdProduto(itemDto.getIdProduto());
                    if (requisicaoProdutoDto.getRejeitada() != null && requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                        itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.RejeitadoAutorizador.name());
                    }else {
	                    ParecerDTO parecerDto = parecerService.createOrUpdate(tc, itemAuxDto.getIdParecerAutorizacao(), solicitacaoServicoDto.getUsuarioDto(), itemDto.getIdJustificativaAutorizacao(), itemDto.getComplemJustificativaAutorizacao(), itemDto.getAutorizado());
	                    itemAuxDto.setIdParecerAutorizacao(parecerDto.getIdParecer());
	                    if (parecerDto.getAprovado().equalsIgnoreCase("S")) {
	                        itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoCotacao.name());
	                    }else if (parecerDto.getAprovado().equalsIgnoreCase("N")){
	                        itemAuxDto.setSituacao(SituacaoItemRequisicaoProduto.CompraNaoAutorizada.name());
	                        naoAutorizados++;
	                    }
	                    itemAuxDto.setQtdeAprovada(itemDto.getQtdeAprovada());
	                    itemAuxDto.setAprovaCotacao(itemDto.getAprovaCotacao());
                    }
                    itemRequisicaoProdutoDao.update(itemAuxDto);
                    colItensAtuais.add(itemAuxDto);
                }
                if (naoAutorizados == colItens.size() && requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("N")) 
                    requisicaoProdutoDto.setRejeitada("S");
                if (requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
                    for (ItemRequisicaoProdutoDTO itemDto : colItens) {
                        itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemDto, AcaoItemRequisicaoProduto.Autorizacao, "", SituacaoItemRequisicaoProduto.RejeitadoAutorizador);
                    }
                }else{
                    for (ItemRequisicaoProdutoDTO itemDto : colItensAtuais) {
                        itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemDto, AcaoItemRequisicaoProduto.Autorizacao, null, SituacaoItemRequisicaoProduto.valueOf(itemDto.getSituacao()));
                    }
                }
             }else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_CRIACAO)) {
                 HashMap<String, ItemRequisicaoProdutoDTO> mapItens = new HashMap();
                 for (ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
                     if (itemRequisicaoDto.getIdItemRequisicaoProduto() == null)
                         continue;
                     mapItens.put(""+itemRequisicaoDto.getIdItemRequisicaoProduto(), itemRequisicaoDto);
                }
                Collection<ItemRequisicaoProdutoDTO> colItensExistentes = itemRequisicaoProdutoDao.findByIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
                if (colItensExistentes != null) {
                    for (ItemRequisicaoProdutoDTO itemRequisicaoDto : colItensExistentes) {
                        if (mapItens.get(""+itemRequisicaoDto.getIdItemRequisicaoProduto()) == null) {
                            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.Cancelado.name());
                            itemRequisicaoProdutoDao.update(itemRequisicaoDto);
                            itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Cancelamento, null, SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
                        }
                    }
                }
                for (ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
                    itemRequisicaoDto.setIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico());
                    itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoValidacao.name());
                    if (mapItens.get(""+itemRequisicaoDto.getIdItemRequisicaoProduto()) == null) {
                        itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoProdutoDao.create(itemRequisicaoDto);
                        itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Criacao, null, SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
                    }else{
                        itemRequisicaoProdutoDao.update(itemRequisicaoDto);
                        itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Alteracao, null, SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
                    }
                }
            }else if (UtilStrings.nullToVazio(requisicaoProdutoDto.getAcao()).equalsIgnoreCase(RequisicaoProdutoDTO.ACAO_ACOMPANHAMENTO)
        		  && requisicaoProdutoDto.getRejeitada() != null && requisicaoProdutoDto.getRejeitada().equalsIgnoreCase("S")) {
	           for (ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
	        	   if (itemRequisicaoDto.getSituacao().equals(SituacaoItemRequisicaoProduto.Cancelado.name()) || itemRequisicaoDto.getSituacao().equals(SituacaoItemRequisicaoProduto.Finalizado.name()))
	        		   continue;
	               itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.RejeitadoCompras.name());
	               itemRequisicaoProdutoDao.updateNotNull(itemRequisicaoDto);
	               itemRequisicaoService.geraHistorico(tc, solicitacaoServicoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Alteracao, null, SituacaoItemRequisicaoProduto.RejeitadoCompras);
	           }
            }
        }

        requisicaoProdutoDao.update(requisicaoProdutoDto);
    }
    
    public Collection<ItemRequisicaoProdutoDTO> recuperaItensValidos(RequisicaoProdutoDTO requisicaoProdutoDto, SituacaoItemRequisicaoProduto[] situacoes, TransactionControler tc) throws Exception {
        ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        if (tc != null)
            itemRequisicaoDao.setTransactionControler(tc);
        Collection<ItemRequisicaoProdutoDTO> result = new ArrayList();
        for (SituacaoItemRequisicaoProduto situacao : situacoes) {
            Collection<ItemRequisicaoProdutoDTO> col = itemRequisicaoDao.findByIdSolicitacaoServicoAndSituacao(requisicaoProdutoDto.getIdSolicitacaoServico(), situacao);  
            if (col != null)
                result.addAll(col);
        }
        return result;
    }

    public Double calculaValorAprovado(RequisicaoProdutoDTO requisicaoProdutoDto, TransactionControler tc) throws Exception {
        double valor = 0;
        Collection<ItemRequisicaoProdutoDTO> itens = recuperaItensValidos(requisicaoProdutoDto, new SituacaoItemRequisicaoProduto[]{SituacaoItemRequisicaoProduto.AguardandoAutorizacaoCompra,SituacaoItemRequisicaoProduto.AguardandoCotacao,SituacaoItemRequisicaoProduto.AguardandoAprovacaoCotacao}, tc);
        if (itens != null) {
            CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
            if (tc != null)
                cotacaoItemRequisicaoDao.setTransactionControler(tc);
            for (ItemRequisicaoProdutoDTO itemRequisicaoDto: itens) {
                if (!itemRequisicaoDto.getSituacao().equalsIgnoreCase(SituacaoItemRequisicaoProduto.AguardandoAprovacaoCotacao.name())) {
                    if (itemRequisicaoDto.getPrecoAproximado() != null)
                        valor += itemRequisicaoDto.getPrecoAproximado().doubleValue() * itemRequisicaoDto.getQuantidade().intValue();
                }else {
                    Collection<CotacaoItemRequisicaoDTO> colItens = cotacaoItemRequisicaoDao.findByIdItemRequisicaoProduto(itemRequisicaoDto.getIdItemRequisicaoProduto());
                    if (colItens != null) {
                        for (CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto : colItens) {
                            if (cotacaoItemRequisicaoDto.getSituacao().equalsIgnoreCase(SituacaoCotacaoItemRequisicao.Aprovado.name()) || cotacaoItemRequisicaoDto.getSituacao().equalsIgnoreCase(SituacaoCotacaoItemRequisicao.PreAprovado.name())  || cotacaoItemRequisicaoDto.getSituacao().equalsIgnoreCase(SituacaoCotacaoItemRequisicao.AguardaAprovacao.name())) {
                                if (cotacaoItemRequisicaoDto.getValorTotal() != null)
                                    valor += cotacaoItemRequisicaoDto.getValorTotal().doubleValue();
                            }
                        }
                    }
                }
            }
        }
        return valor;
    }
    
    public Double calculaValorAprovadoMensal(CentroResultadoDTO centroCustoDto, int mesRef, int anoRef, TransactionControler tc) throws Exception {
        double valor = 0;
        RequisicaoProdutoDao requisicaoDao = new RequisicaoProdutoDao();
        if (tc != null)
            requisicaoDao.setTransactionControler(tc);
        Collection<RequisicaoProdutoDTO> colRequisicoes = requisicaoDao.findByIdCentroCusto(centroCustoDto.getIdCentroResultado());
        if (colRequisicoes != null) {
            for (RequisicaoProdutoDTO requisicaoProdutoMesDto : colRequisicoes) {
                Date dataAux = new Date(requisicaoProdutoMesDto.getDataHoraSolicitacao().getTime());
                int mes = UtilDatas.getMonth(dataAux);
                int ano = UtilDatas.getYear(dataAux);
                if (mes != mesRef || ano != anoRef)
                    continue;
                valor += calculaValorAprovado(requisicaoProdutoMesDto, tc);
            }
        }
        return valor;
    }

    @Override
    public StringBuffer recuperaLoginAutorizadores(Integer idSolicitacaoServico) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoServiceEjb().restoreAll(idSolicitacaoServico, null);
        RequisicaoProdutoDao requisicaoProdutoDao = new RequisicaoProdutoDao();
        TransactionControler tc = new TransactionControlerImpl(requisicaoProdutoDao.getAliasDB());
        RequisicaoProdutoDTO requisicaoProdutoDto = new RequisicaoProdutoDTO();
        requisicaoProdutoDto.setIdSolicitacaoServico(idSolicitacaoServico);
        requisicaoProdutoDto = (RequisicaoProdutoDTO) requisicaoProdutoDao.restore(requisicaoProdutoDto);
        Reflexao.copyPropertyValues(solicitacaoServicoDto,requisicaoProdutoDto);
        ExecucaoRequisicaoProduto execucaoRequisicaoProduto = new ExecucaoRequisicaoProduto(requisicaoProdutoDto, tc);
        return execucaoRequisicaoProduto.recuperaLoginAutorizadores(requisicaoProdutoDto);
    }
    
	@Override
	public void preparaSolicitacaoParaAprovacao(SolicitacaoServicoDTO solicitacaoDto, ItemTrabalho itemTrabalho, String aprovacao, Integer idJustificativa, String observacoes) throws Exception {
		 RequisicaoProdutoDTO requisicaoProdutoDto = new RequisicaoProdutoDTO();
		 requisicaoProdutoDto.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
		 requisicaoProdutoDto = (RequisicaoProdutoDTO) restore(requisicaoProdutoDto);
		 
		 ElementoFluxoDTO elementoDto = itemTrabalho.getElementoFluxoDto();
		 if (UtilStrings.nullToVazio(elementoDto.getTemplate()).toUpperCase().indexOf("APROVACAO") >= 0) {
			requisicaoProdutoDto.setAcao(RequisicaoProdutoDTO.ACAO_APROVACAO);
	        CotacaoItemRequisicaoService cotacaoItemRequisicaoService = (CotacaoItemRequisicaoService) ServiceLocator.getInstance().getService(CotacaoItemRequisicaoService.class, this.usuario);
	        Collection<CotacaoItemRequisicaoDTO> itensCotacao = cotacaoItemRequisicaoService.findByIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
	        if (itensCotacao != null) {
	        	requisicaoProdutoDto.setItensCotacao(itensCotacao);
	        	for (CotacaoItemRequisicaoDTO cotacaoItemDto : itensCotacao) {
	        		cotacaoItemDto.setAprovado(aprovacao);
	        		if (aprovacao.equalsIgnoreCase("N")) {
	        			cotacaoItemDto.setIdJustificativa(idJustificativa);
	        			cotacaoItemDto.setComplementoJustificativa(observacoes);
	        		}
				}
	        }
		 }else if (UtilStrings.nullToVazio(elementoDto.getTemplate()).toUpperCase().indexOf("AUTORIZACAO") >= 0){
			 requisicaoProdutoDto.setAcao(RequisicaoProdutoDTO.ACAO_AUTORIZACAO);
     		 if (aprovacao.equalsIgnoreCase("N")) 
				requisicaoProdutoDto.setRejeitada("S");
    		 ItemRequisicaoProdutoService itemRequisicaoProdutoService = (ItemRequisicaoProdutoService) ServiceLocator.getInstance().getService(ItemRequisicaoProdutoService.class, this.usuario);
    		 requisicaoProdutoDto.setItensRequisicao(itemRequisicaoProdutoService.findByIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico()));
			 for (ItemRequisicaoProdutoDTO itemRequisicaoDto : requisicaoProdutoDto.getItensRequisicao()) {
				itemRequisicaoDto.setAutorizado(aprovacao);
        		if (aprovacao.equalsIgnoreCase("N")) {
					itemRequisicaoDto.setIdJustificativaAutorizacao(idJustificativa);
					itemRequisicaoDto.setComplemJustificativaAutorizacao(observacoes);
        		}
			 }					 
		 }
		 if (requisicaoProdutoDto.getAcao() != null) {
			solicitacaoDto.setInformacoesComplementares(requisicaoProdutoDto);
			solicitacaoDto.setAcaoFluxo(br.com.centralit.bpm.util.Enumerados.ACAO_EXECUTAR);
			solicitacaoDto.setIdTarefa(itemTrabalho.getIdItemTrabalho());
		 }
	}
	
	@Override
	public String getInformacoesComplementaresFmtTexto(SolicitacaoServicoDTO solicitacaoDto, ItemTrabalho itemTrabalho) throws Exception {
        RequisicaoProdutoDao requisicaoDao = new RequisicaoProdutoDao();
        RequisicaoProdutoDTO requisicaoDto = new RequisicaoProdutoDTO();
        requisicaoDto.setIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
        requisicaoDto = (RequisicaoProdutoDTO) requisicaoDao.restore(requisicaoDto);
        Reflexao.copyPropertyValues(solicitacaoDto, requisicaoDto);
        
        EnderecoDTO enderecoDto = new EnderecoDao().recuperaEnderecoComUnidade(requisicaoDto.getIdEnderecoEntrega());
        
		String descricao = "";
		if (requisicaoDto.getSolicitante() != null && !requisicaoDto.getSolicitante().trim().equals(""))
			descricao += "Solicitante: "+requisicaoDto.getSolicitante()+"\n";
		descricao += "Centro de Custo: "+requisicaoDto.getCentroCusto()+"\n";
		descricao += "Projeto: "+requisicaoDto.getProjeto()+"\n";
		if (enderecoDto != null && enderecoDto.getIdentificacao() != null)
			descricao += "Unidade destino: "+enderecoDto.getIdentificacao()+"\n";
		if (requisicaoDto.getFinalidade().equals("I"))
			descricao += "Finalidade: Uso interno\n";
		else	
			descricao += "Finalidade: Atendimento ao cliente\n";
		
		String descr = solicitacaoDto.getDescricaoSemFormatacao();
		if (descr != null && !descr.trim().isEmpty()) {
			descricao += "\n--------------------- DESCRIÇÃO -------------------\n";
			descricao += descr+"\n";
		}
		
		ElementoFluxoDTO elementoDto = itemTrabalho.getElementoFluxoDto();
		if (elementoDto == null) {
	        TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
	        tarefaFluxoDto.setIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
	        tarefaFluxoDto = (TarefaFluxoDTO) new TarefaFluxoDao().restore(tarefaFluxoDto);
	        elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
		}
		if (elementoDto != null && !elementoDto.getTemplate().trim().equals("")) {
			if (UtilStrings.nullToVazio(elementoDto.getTemplate()).toUpperCase().indexOf("APROVACAO") >= 0) {
		        CotacaoItemRequisicaoService cotacaoItemRequisicaoService = (CotacaoItemRequisicaoService) ServiceLocator.getInstance().getService(CotacaoItemRequisicaoService.class, this.usuario);
		        Collection<CotacaoItemRequisicaoDTO> itensCotacao = cotacaoItemRequisicaoService.findByIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
		        if (itensCotacao != null) {
		        	descricao += "\n---------------- ITENS DA APROVAÇÃO --------------\n";
		        	for (CotacaoItemRequisicaoDTO cotacaoItemDto : itensCotacao) {
		        		if (!cotacaoItemDto.getSituacao().equals(SituacaoCotacaoItemRequisicao.AguardaAprovacao.name()))
		        			continue;
		        		descricao += cotacaoItemDto.getDescricaoItem().toUpperCase()+"\n";
		        		descricao += "Fornecedor: "+cotacaoItemDto.getNomeFornecedor()+"\n";
		        		descricao += "Qtde: "+UtilFormatacao.formatDouble(cotacaoItemDto.getQuantidade(),2)+"\n";
		        		descricao += "Preço: "+UtilFormatacao.formatDouble(cotacaoItemDto.getPreco(),2)+"\n\n";
					}
		        }
			}else if (UtilStrings.nullToVazio(elementoDto.getTemplate()).toUpperCase().indexOf("INSPECAO") >= 0) {
		        EntregaItemRequisicaoService entregaItemRequisicaoService = (EntregaItemRequisicaoService) ServiceLocator.getInstance().getService(EntregaItemRequisicaoService.class, this.usuario);
		        Collection<EntregaItemRequisicaoDTO> itensInspecao = entregaItemRequisicaoService.findByIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
				if (itensInspecao != null) {
		        	descricao += "\n----------------- ITENS DA INSPEÇÃO --------------\n";
					for (EntregaItemRequisicaoDTO entregaItemDto : itensInspecao) {
						if (!entregaItemDto.getSituacao().equals(SituacaoEntregaItemRequisicao.Aguarda.name()))
							continue;
		        		descricao += entregaItemDto.getDescricaoItem().toUpperCase()+"\n";
		        		descricao += "Fornecedor: "+entregaItemDto.getNomeFornecedor()+"\n";
		        		descricao += "Qtde: "+UtilFormatacao.formatDouble(entregaItemDto.getQuantidadeEntregue(),2)+"\n\n";
					}
				}
			}else{
				 ItemRequisicaoProdutoService itemRequisicaoProdutoService = (ItemRequisicaoProdutoService) ServiceLocator.getInstance().getService(ItemRequisicaoProdutoService.class, this.usuario);
				 Collection<ItemRequisicaoProdutoDTO> colItens = itemRequisicaoProdutoService.findByIdSolicitacaoServico(solicitacaoDto.getIdSolicitacaoServico());
		         descricao += "\n---------------- ITENS DA REQUISIÇÃO -------------\n";
				 for (ItemRequisicaoProdutoDTO itemRequisicaoDto : colItens) {
		        	descricao += itemRequisicaoDto.getDescricaoItem().toUpperCase()+"\n";
	        		descricao += "Qtde: "+UtilFormatacao.formatDouble(itemRequisicaoDto.getQtdeAprovada(),2)+"\n";
	        		if (itemRequisicaoDto.getPrecoAproximado() != null && itemRequisicaoDto.getPrecoAproximado().doubleValue() > 0)
	        			descricao += "Valor aproximado: "+UtilFormatacao.formatDouble(itemRequisicaoDto.getPrecoAproximado(),2)+"\n";
	        		descricao += "Situação: "+itemRequisicaoDto.getDescrSituacao()+"\n\n";
				 }					 
			}
		}
		
		if (descricao == null) 
			descricao = solicitacaoDto.getDescricaoSemFormatacao();
		return descricao;
	}	
}
