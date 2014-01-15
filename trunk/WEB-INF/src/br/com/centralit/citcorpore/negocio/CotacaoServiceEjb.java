package br.com.centralit.citcorpore.negocio;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.AvaliacaoColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.CotacaoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.CriterioItemCotacaoDTO;
import br.com.centralit.citcorpore.bean.FornecedorCotacaoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.FornecedorProdutoDTO;
import br.com.centralit.citcorpore.bean.HistoricoSituacaoCotacaoDTO;
import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.centralit.citcorpore.bean.ItemPedidoCompraDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.PedidoCompraDTO;
import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AvaliacaoColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.ColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.CotacaoDao;
import br.com.centralit.citcorpore.integracao.CotacaoItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.CriterioItemCotacaoDao;
import br.com.centralit.citcorpore.integracao.FornecedorCotacaoDao;
import br.com.centralit.citcorpore.integracao.FornecedorDao;
import br.com.centralit.citcorpore.integracao.FornecedorProdutoDao;
import br.com.centralit.citcorpore.integracao.HistoricoSituacaoCotacaoDao;
import br.com.centralit.citcorpore.integracao.ItemCotacaoDao;
import br.com.centralit.citcorpore.integracao.ItemPedidoCompraDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.integracao.PedidoCompraDao;
import br.com.centralit.citcorpore.integracao.ProdutoDao;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.ResultadoValidacao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacaoItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoPedidoCompra;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilNumbersAndDecimals;
public class CotacaoServiceEjb extends CrudServicePojoImpl implements CotacaoService {
    
	protected CrudDAO getDao() throws ServiceException {
		return new CotacaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	    validaAtualizacao((CotacaoDTO) arg0);
	}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {
	    validaAtualizacao((CotacaoDTO) arg0);
	}

	private void validaAtualizacao(CotacaoDTO cotacaoDto) throws Exception {
        if (cotacaoDto.getDataFinalPrevista() != null && cotacaoDto.getDataFinalPrevista().compareTo(UtilDatas.getDataAtual()) < 0)
            throw new LogicException("Data final previsao não pode ser menor que a data atual");
	}

	public void alteraSituacao(UsuarioDTO usuarioDto, Integer idCotacao, SituacaoCotacao novaSituacao, TransactionControler tc) throws Exception {
        CotacaoDao cotacaoDao = new CotacaoDao();
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        cotacaoDto.setIdCotacao(idCotacao);
        cotacaoDto = (CotacaoDTO) cotacaoDao.restore(cotacaoDto);
        
        cotacaoDao.setTransactionControler(tc);
        
        cotacaoDto.setSituacao(novaSituacao.name());
        cotacaoDao.update(cotacaoDto);
        cotacaoDto.setUsuarioDto(usuarioDto);
        geraHistoricoSituacao(tc, cotacaoDto, novaSituacao);
	}
	
	private void geraHistoricoSituacao(TransactionControler tc, CotacaoDTO cotacaoDto, SituacaoCotacao situacao) throws Exception {
	    HistoricoSituacaoCotacaoDTO historicoSituacaoCotacaoDto = new HistoricoSituacaoCotacaoDTO();
	    historicoSituacaoCotacaoDto.setIdCotacao(cotacaoDto.getIdCotacao());
	    historicoSituacaoCotacaoDto.setIdResponsavel(cotacaoDto.getUsuarioDto().getIdEmpregado());
	    historicoSituacaoCotacaoDto.setDataHora(UtilDatas.getDataHoraAtual());
	    historicoSituacaoCotacaoDto.setSituacao(situacao.name());
	    HistoricoSituacaoCotacaoDao historicoSituacaoCotacaoDao = new HistoricoSituacaoCotacaoDao();
	    historicoSituacaoCotacaoDao.setTransactionControler(tc);
	    historicoSituacaoCotacaoDao.create(historicoSituacaoCotacaoDto);
	}
	
	
	public Collection findItensPendentesAprovacao(CotacaoDTO cotacaoDto) throws Exception {
	    return new CotacaoItemRequisicaoDao().findPendentesByIdCotacao(cotacaoDto.getIdCotacao());
	}
	
	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
        CotacaoDao cotacaoDao = new CotacaoDao();
        TransactionControler tc = new TransactionControlerImpl(cotacaoDao.getAliasDB());
        
        try{
            validaCreate(model);
            
            cotacaoDao.setTransactionControler(tc);
            
            tc.start();
            
            CotacaoDTO cotacaoDto = (CotacaoDTO) model;

            cotacaoDto.setSituacao(SituacaoCotacao.EmAndamento.name());
            cotacaoDto = (CotacaoDTO) cotacaoDao.create(cotacaoDto);
            geraHistoricoSituacao(tc, cotacaoDto, SituacaoCotacao.EmAndamento);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }	 
        return model;
	}
	
    @Override
    public void encerra(CotacaoDTO cotacaoDto) throws Exception {
        UsuarioDTO usuarioDto = cotacaoDto.getUsuarioDto();
        
        cotacaoDto = (CotacaoDTO) new CotacaoDao().restore(cotacaoDto);
        if (cotacaoDto == null)
            throw new LogicException("Cotação não encontrada");
        
        if (cotacaoDto.getSituacao().equals(SituacaoCotacao.Finalizada.name())) 
            throw new LogicException("Cotação já está encerrada");
        
        if (cotacaoDto.getSituacao().equals(SituacaoCotacao.Cancelada.name())) 
            throw new LogicException("Cotação está cancelada");
        
        if (cotacaoDto.getSituacao().equals(SituacaoCotacao.Pedido.name())) {
            Collection<ColetaPrecoDTO> colColetas = new ColetaPrecoDao().findHabilitadasByIdCotacao(cotacaoDto.getIdCotacao());
            if (colColetas != null) {
                ItemPedidoCompraDao itemPedidoDao = new ItemPedidoCompraDao();
                for (ColetaPrecoDTO coletaPrecoDto : colColetas) {
                    Collection<ItemPedidoCompraDTO> colItensPedido = itemPedidoDao.findByIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
                    if (colItensPedido == null || colItensPedido.size() == 0)
                        throw new LogicException("Existe pelo menos um pedido de compra ainda não gerado");
                }
            }
        }else if (cotacaoDto.getSituacao().equals(SituacaoCotacao.Entrega.name())) {
            Collection<PedidoCompraDTO> colPedidos = new PedidoCompraDao().findByIdCotacao(cotacaoDto.getIdCotacao());
            if (colPedidos != null) {
                for (PedidoCompraDTO pedidoDto : colPedidos) {
                    if (!pedidoDto.getSituacao().equals(SituacaoPedidoCompra.Entregue.name()))
                        throw new LogicException("Existe pelo menos um pedido de compra ainda não entregue");
                }
            }
        }

        CotacaoDao cotacaoDao = new CotacaoDao();
        TransactionControler tc = new TransactionControlerImpl(cotacaoDao.getAliasDB());
        try{
            cotacaoDao.setTransactionControler(tc);
            
            tc.start();

            cotacaoDto.setUsuarioDto(usuarioDto);
            
            if (cotacaoDto.getSituacao().equals(SituacaoCotacao.Publicada.name())) {
                reabreColetaPrecos(cotacaoDto, tc);
            }

            if (cotacaoDto.getSituacao().equals(SituacaoCotacao.EmAndamento.name())) {
                Collection<ItemCotacaoDTO> colItens = new ItemCotacaoDao().findByIdCotacao(cotacaoDto.getIdCotacao());
                if (colItens != null) {
                    ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
                    for (ItemCotacaoDTO itemCotacaoDto : colItens) {
                        itemRequisicaoService.desassociaItemCotacao(usuarioDto, itemCotacaoDto, AcaoItemRequisicaoProduto.EncerramentoCotacao, tc);
                    }
                }
            }
            
            cotacaoDto.setSituacao(SituacaoCotacao.Finalizada.name());
            cotacaoDao.update(cotacaoDto);
            geraHistoricoSituacao(tc, cotacaoDto, SituacaoCotacao.EmAndamento);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }    
    }

    @Override
    public ItemCotacaoDTO verificaInclusaoItensRequisicao(String tipoCriacaoItem, Collection<ItemRequisicaoProdutoDTO> colItensRequisicao) throws Exception {
        if (colItensRequisicao == null || colItensRequisicao.isEmpty())
            return null;
        
        ItemCotacaoDTO itemCotacaoRef = null;
        if (tipoCriacaoItem != null && tipoCriacaoItem.equals("2") && colItensRequisicao.size() > 1) {
            Integer idProduto = null;
            for (ItemRequisicaoProdutoDTO itemRequisicaoDto : colItensRequisicao) {
                if (itemRequisicaoDto.getIdProduto() == null)
                    continue;
                if (itemCotacaoRef == null)
                    itemCotacaoRef = new ItemCotacaoDTO();
                Reflexao.copyPropertyValues(itemRequisicaoDto, itemCotacaoRef);
                if (idProduto == null) {
                    idProduto = itemRequisicaoDto.getIdProduto();
                }else if (itemRequisicaoDto.getIdProduto().intValue() != idProduto.intValue()) {
                    throw new LogicException("Todos os itens de requisição deve estar associados ao mesmo produto");
                }
            }
            if (itemCotacaoRef == null) {
                itemCotacaoRef = new ItemCotacaoDTO();
                ItemRequisicaoProdutoDTO item = (ItemRequisicaoProdutoDTO) ((List) colItensRequisicao).get(0);
                Reflexao.copyPropertyValues(item, itemCotacaoRef);
            }
            if (idProduto == null && colItensRequisicao.size() > 1) {
                itemCotacaoRef.setTipoIdentificacao("D");
            }else{
                itemCotacaoRef.setTipoIdentificacao("S");
            }
        }
        return itemCotacaoRef;
    }
    
    @Override
    public void incluiItensRequisicao(UsuarioDTO usuarioDto, Integer idCotacao, ItemCotacaoDTO itemCotacaoRefDto, String tipoCriacaoItem, Collection<ItemRequisicaoProdutoDTO> colItensRequisicao) throws Exception {
        if (colItensRequisicao == null || colItensRequisicao.isEmpty())
            return;
        
        ItemCotacaoServiceEjb itemCotacaoService = new ItemCotacaoServiceEjb();
        TransactionControler tc = new TransactionControlerImpl(new CotacaoDao().getAliasDB());
        
        try{
            
            tc.start();
        
            if (tipoCriacaoItem.equals("1") || colItensRequisicao.size() == 1) {
                for (ItemRequisicaoProdutoDTO itemRequisicaoProdutoDto : colItensRequisicao) {
                    List<ItemRequisicaoProdutoDTO> itens = new ArrayList();
                    itens.add(itemRequisicaoProdutoDto);
                    ItemCotacaoDTO itemCotacaoDto = new ItemCotacaoDTO();
                    Reflexao.copyPropertyValues(itemRequisicaoProdutoDto, itemCotacaoDto);
                    itemCotacaoDto.setIdCotacao(idCotacao);
                    itemCotacaoDto.setQuantidade(itemRequisicaoProdutoDto.getQtdeAprovada());
                    itemCotacaoDto = (ItemCotacaoDTO) itemCotacaoService.create(usuarioDto, itemCotacaoDto, itens, tc);
                }
            }else{
                List<ItemRequisicaoProdutoDTO> itens = new ArrayList();
                ItemCotacaoDTO itemCotacaoDto = new ItemCotacaoDTO();
                Reflexao.copyPropertyValues(itemCotacaoRefDto, itemCotacaoDto);
                itemCotacaoDto.setIdCotacao(idCotacao);
                double qtde = 0.0;
                Timestamp tsLimite = null;
                for (ItemRequisicaoProdutoDTO itemRequisicaoProdutoDto : colItensRequisicao) { 
                    itens.add(itemRequisicaoProdutoDto);
                    qtde += itemRequisicaoProdutoDto.getQtdeAprovada().doubleValue();
                    if (tsLimite == null)
                        tsLimite = itemRequisicaoProdutoDto.getDataHoraLimite();
                    else if (itemRequisicaoProdutoDto.getDataHoraLimite() != null && itemRequisicaoProdutoDto.getDataHoraLimite().compareTo(tsLimite) < 0)
                        tsLimite = itemRequisicaoProdutoDto.getDataHoraLimite();
                }
                
                itemCotacaoDto.setQuantidade(qtde);
                itemCotacaoDto.setDataHoraLimite(tsLimite);
                itemCotacaoDto = (ItemCotacaoDTO) itemCotacaoService.create(usuarioDto, itemCotacaoDto, itens, tc);
            }
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
    }
    
    @Override
    public void incluiFornecedores(CotacaoDTO cotacaoDto, Collection<FornecedorDTO> colFornecedores) throws Exception {
        if (colFornecedores == null || colFornecedores.isEmpty())
            return;
        
        HashMap<String, Object> mapFornecedores = new HashMap();
        Collection<FornecedorCotacaoDTO> colFornecedorCotacao = new FornecedorCotacaoDao().findByIdCotacao(cotacaoDto.getIdCotacao());
        if (colFornecedorCotacao != null) {
            for (FornecedorCotacaoDTO fornecedorCotacaoDto : colFornecedorCotacao) {
                FornecedorDTO fornecedorDto = new FornecedorDTO();
                fornecedorDto.setIdFornecedor(fornecedorCotacaoDto.getIdFornecedor());
                mapFornecedores.put(""+fornecedorCotacaoDto.getIdFornecedor(), fornecedorCotacaoDto);
            }
        }
        
        FornecedorCotacaoDao fornecedorCotacaoDao = new FornecedorCotacaoDao();
        TransactionControler tc = new TransactionControlerImpl(fornecedorCotacaoDao.getAliasDB());
        
        try{
            fornecedorCotacaoDao.setTransactionControler(tc);
            
            tc.start();
        
            for (FornecedorDTO fornecedorDto : colFornecedores) {
                if (mapFornecedores.get(""+fornecedorDto.getIdFornecedor()) != null)
                    continue;
                FornecedorCotacaoDTO fornecedorCotacaoDto = new FornecedorCotacaoDTO();
                fornecedorCotacaoDto.setIdCotacao(cotacaoDto.getIdCotacao());
                fornecedorCotacaoDto.setIdFornecedor(fornecedorDto.getIdFornecedor());
                fornecedorCotacaoDao.create(fornecedorCotacaoDto);
            }
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public Collection<FornecedorDTO> sugereFornecedores(CotacaoDTO cotacaoDto) throws Exception {
        Collection<ItemCotacaoDTO> colItens = new ItemCotacaoDao().findByIdCotacao(cotacaoDto.getIdCotacao());
        if (colItens == null || colItens.isEmpty()) 
            return null;
        
        HashMap<String, Object> mapFornecedores = new HashMap();
        Collection<FornecedorCotacaoDTO> colFornecedorCotacao = new FornecedorCotacaoDao().findByIdCotacao(cotacaoDto.getIdCotacao());
        if (colFornecedorCotacao != null) {
            for (FornecedorCotacaoDTO fornecedorCotacaoDto : colFornecedorCotacao) {
                FornecedorDTO fornecedorDto = new FornecedorDTO();
                fornecedorDto.setIdFornecedor(fornecedorCotacaoDto.getIdFornecedor());
                mapFornecedores.put(""+fornecedorCotacaoDto.getIdFornecedor(), fornecedorCotacaoDto);
            }
        }
        
        AvaliacaoFornecedorServiceEjb avaliacaoFornecedorService = new AvaliacaoFornecedorServiceEjb();
        Collection<FornecedorDTO> result = new ArrayList();
        ProdutoDao produtoDao = new ProdutoDao();
        FornecedorDao fornecedorDao = new FornecedorDao();
        FornecedorProdutoDao fornecedorProdutoDao = new FornecedorProdutoDao();
        for (ItemCotacaoDTO itemCotacaoDto : colItens) {
            ProdutoDTO produtoDto = new ProdutoDTO();
            produtoDto.setIdProduto(itemCotacaoDto.getIdProduto());
            produtoDto = (ProdutoDTO) produtoDao.restore(produtoDto);
            if (produtoDto == null)
                continue;
            
            Collection<FornecedorProdutoDTO> colFornecedores = fornecedorProdutoDao.findByIdTipoProduto(produtoDto.getIdTipoProduto());
            if (colFornecedores == null || colFornecedores.isEmpty())
                continue;
            
            for (FornecedorProdutoDTO fornecedorProdutoDto : colFornecedores) {
                if (fornecedorProdutoDto.getDataFim() != null && fornecedorProdutoDto.getDataFim().compareTo(UtilDatas.getDataAtual()) < 0)
                    continue;
                if (itemCotacaoDto.getExigeFornecedorQualificado() != null && itemCotacaoDto.getExigeFornecedorQualificado().equalsIgnoreCase("S")) {
                    if (!avaliacaoFornecedorService.fornecedorQualificado(fornecedorProdutoDto.getIdFornecedor()))
                        continue;
                }
                if (mapFornecedores.get(""+fornecedorProdutoDto.getIdFornecedor()) != null)
                    continue;
                FornecedorDTO fornecedorDto = new FornecedorDTO();
                fornecedorDto.setIdFornecedor(fornecedorProdutoDto.getIdFornecedor());
                fornecedorDto = (FornecedorDTO) fornecedorDao.restore(fornecedorDto);
                if (fornecedorDto != null) {
                    result.add(fornecedorDto);
                    mapFornecedores.put(""+fornecedorProdutoDto.getIdFornecedor(), fornecedorDto);
                }
            }
        }
        return result;
    }
    
    private void classificaMenor(List<ColetaPrecoDTO> colColetas) throws Exception {
        Collections.sort(colColetas, new Comparator<ColetaPrecoDTO>() {
            public int compare(ColetaPrecoDTO coleta1, ColetaPrecoDTO coleta2) {
                int r = coleta1.getResultCriterios().compareTo(coleta2.getResultCriterios());
                if (r == 0)
                    r = coleta2.getQuantidadeCotada().compareTo(coleta1.getQuantidadeCotada());
                return r;
            }
        });
    }
    
    private void classificaResultado(List<ColetaPrecoDTO> colColetas) throws Exception {
        Collections.sort(colColetas, new Comparator<ColetaPrecoDTO>() {
            public int compare(ColetaPrecoDTO coleta1, ColetaPrecoDTO coleta2) {
                int r = coleta2.getResultCriterios().compareTo(coleta1.getResultCriterios());
                if (r == 0)
                    r = coleta2.getQuantidadeCotada().compareTo(coleta1.getQuantidadeCotada());
                return r;
            }
        });
    }
        
    public void calculaResultado(CotacaoDTO cotacaoDto) throws Exception {
        Collection<ItemCotacaoDTO> colItens = new ItemCotacaoDao().findByIdCotacao(cotacaoDto.getIdCotacao());
        if (colItens == null)
            return;
        
        CotacaoDao cotacaoDao = new CotacaoDao();
        CotacaoDTO cotacaoAuxDto = (CotacaoDTO) cotacaoDao.restore(cotacaoDto);
        
        ItemCotacaoDao itemCotacaoDao = new ItemCotacaoDao();
        ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();
        AvaliacaoColetaPrecoDao avaliacaoColetaDao = new AvaliacaoColetaPrecoDao();
        CriterioItemCotacaoDao criterioItemCotacaoDao = new CriterioItemCotacaoDao();

        ItemCotacaoServiceEjb itemCotacaoService = new ItemCotacaoServiceEjb();
        TransactionControler tc = new TransactionControlerImpl(coletaPrecoDao.getAliasDB());
        try{ 
            tc.start();
            
            itemCotacaoDao.setTransactionControler(tc);
            coletaPrecoDao.setTransactionControler(tc);
            avaliacaoColetaDao.setTransactionControler(tc);
            cotacaoDao.setTransactionControler(tc);
            criterioItemCotacaoDao.setTransactionControler(tc);

            for (ItemCotacaoDTO itemCotacaoDto : colItens) {
                List<ColetaPrecoDTO> colColetas = (List<ColetaPrecoDTO>) coletaPrecoDao.findByIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
                if (colColetas != null) {
                    double total = 0;
                    for (ColetaPrecoDTO coletaPrecoDto : colColetas) {
                        double r = coletaPrecoDto.getPreco()+coletaPrecoDto.getValorAcrescimo()-coletaPrecoDto.getValorDesconto()+coletaPrecoDto.getValorFrete();
                        r = r * (1 + coletaPrecoDto.getTaxaJuros() / 100);
                        coletaPrecoDto.setResultPreco(r / coletaPrecoDto.getQuantidadeCotada() * itemCotacaoDto.getPesoPreco());
                        coletaPrecoDto.setResultJuros(coletaPrecoDto.getTaxaJuros() * itemCotacaoDto.getPesoTaxaJuros());
                        coletaPrecoDto.setResultPrazoEntrega(coletaPrecoDto.getPrazoEntrega().doubleValue() * itemCotacaoDto.getPesoPrazoEntrega());
                        double resultCriterios = coletaPrecoDto.getResultPreco().doubleValue()
                                               + coletaPrecoDto.getResultJuros().doubleValue()
                                               + coletaPrecoDto.getResultPrazoEntrega().doubleValue();
                        coletaPrecoDto.setResultCriterios(resultCriterios);
                        total += resultCriterios;
                    }
                    classificaMenor(colColetas);
                    
                    for (ColetaPrecoDTO coletaPrecoDto : colColetas) {
                        coletaPrecoDto.setResultCriterios(10 - coletaPrecoDto.getResultCriterios() / total * 10);
                        coletaPrecoDto.setResultPrazoPagto(coletaPrecoDto.getPrazoMedioPagto() * itemCotacaoDto.getPesoPrazoPagto());
                        coletaPrecoDto.setResultGarantia(coletaPrecoDto.getPrazoGarantia().doubleValue() * itemCotacaoDto.getPesoPrazoGarantia());
                        double resultCriterios = coletaPrecoDto.getResultCriterios()
                                               + coletaPrecoDto.getResultPrazoPagto().doubleValue()
                                               + coletaPrecoDto.getResultGarantia().doubleValue();
                        
                        Collection<AvaliacaoColetaPrecoDTO> colAvaliacoes = avaliacaoColetaDao.findByIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
                        if (colAvaliacoes != null) {
                            for (AvaliacaoColetaPrecoDTO avaliacaoColetaPrecoDto : colAvaliacoes) {
                                CriterioItemCotacaoDTO criterioItemCotacaoDto = new CriterioItemCotacaoDTO();
                                criterioItemCotacaoDto.setIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
                                criterioItemCotacaoDto.setIdCriterio(avaliacaoColetaPrecoDto.getIdCriterio());
                                criterioItemCotacaoDto = (CriterioItemCotacaoDTO) criterioItemCotacaoDao.restore(criterioItemCotacaoDto);
                                if (criterioItemCotacaoDto != null)
                                    resultCriterios += avaliacaoColetaPrecoDto.getAvaliacao() * criterioItemCotacaoDto.getPeso();
                            }
                        }
                        coletaPrecoDto.setResultCriterios(resultCriterios);
                    }
                    classificaResultado(colColetas);

                    ColetaPrecoDTO coletaAnteriorDto = null;
                    double qtde = itemCotacaoDto.getQuantidade().doubleValue();
                    double pontuacao = 10;
                    double primeiroResultado = 0;
                    for (ColetaPrecoDTO coletaPrecoDto : colColetas) {
                        coletaPrecoDto.setIdJustifResultado(null);
                        coletaPrecoDto.setComplemJustifResultado(null);
                        coletaPrecoDto.setIdRespResultado(null);
                        coletaPrecoDto.setQuantidadeCalculo(0.0);
                        if (coletaAnteriorDto != null) {
                            double dif = coletaPrecoDto.getResultCriterios().doubleValue() / primeiroResultado;
                            pontuacao = 10 * dif;
                        }else
                            primeiroResultado = coletaPrecoDto.getResultCriterios().doubleValue();
                        pontuacao = UtilNumbersAndDecimals.setRound(pontuacao, 4);
                        
                        coletaPrecoDto.setResultadoCalculo(ColetaPrecoDTO.RESULT_DESCLASSIFICADA);
                        if (qtde > 0) {
                            if (coletaAnteriorDto != null && pontuacao == coletaAnteriorDto.getPontuacao().doubleValue()) { // empate
                                double qtdeAux = qtde + coletaAnteriorDto.getQuantidadeCalculo().doubleValue();
                                if (qtdeAux > coletaPrecoDto.getQuantidadeCotada()) {
                                    coletaPrecoDto.setQuantidadeCalculo(coletaPrecoDto.getQuantidadeCotada());
                                }else{
                                    coletaPrecoDto.setQuantidadeCalculo(qtdeAux);
                                }
                                coletaAnteriorDto.setResultadoCalculo(ColetaPrecoDTO.RESULT_EMPATE);
                                coletaPrecoDao.atualizaResultadoCalculo(coletaAnteriorDto);
                                coletaAnteriorDto.setResultadoFinal(ColetaPrecoDTO.RESULT_EMPATE);
                                coletaPrecoDao.atualizaResultadoFinal(coletaAnteriorDto);
                                coletaPrecoDto.setResultadoCalculo(ColetaPrecoDTO.RESULT_EMPATE);
                            }else{
                                if (qtde > coletaPrecoDto.getQuantidadeCotada()) {
                                    qtde = qtde - coletaPrecoDto.getQuantidadeCotada();
                                    coletaPrecoDto.setQuantidadeCalculo(coletaPrecoDto.getQuantidadeCotada());
                                }else{
                                    coletaPrecoDto.setQuantidadeCalculo(qtde);
                                    qtde = 0;
                                }
                                coletaPrecoDto.setResultadoCalculo(ColetaPrecoDTO.RESULT_MELHOR_COTACAO);
                            }
                        }
                        
                        coletaAnteriorDto = coletaPrecoDto;
                        coletaPrecoDto.setPontuacao(pontuacao);
                        coletaPrecoDao.atualizaResultadoCalculo(coletaPrecoDto);
                        
                        coletaPrecoDto.setResultadoFinal(coletaPrecoDto.getResultadoCalculo());
                        coletaPrecoDto.setQuantidadeCompra(coletaPrecoDto.getQuantidadeCalculo());
                        coletaPrecoDao.atualizaResultadoFinal(coletaPrecoDto);
                    }
    
                }
                itemCotacaoService.valida(tc, itemCotacaoDto);
                itemCotacaoDao.update(itemCotacaoDto);
            }
            cotacaoDto.setSituacao(SituacaoCotacao.Calculada.name());
            cotacaoDao.atualizaSituacao(cotacaoDto);
            geraHistoricoSituacao(tc, cotacaoDto, SituacaoCotacao.Calculada);
            
            tc.commit();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }        
        
    }

    public boolean todosItensValidos(CotacaoDTO cotacaoDto) throws Exception {
        boolean bResult = true;
        Collection<ItemCotacaoDTO> colItens = new ItemCotacaoDao().findByIdCotacao(cotacaoDto.getIdCotacao());
        if (colItens != null) {
            for (ItemCotacaoDTO itemCotacaoDto : colItens) {
                if (itemCotacaoDto.getResultadoValidacao() != null && itemCotacaoDto.getResultadoValidacao().equals(ResultadoValidacao.E.name())) {
                    bResult = false;
                    break;
                }
            }
        }
        return bResult;
    }
    
    @Override
    public void publicaResultado(CotacaoDTO cotacaoDto) throws Exception {
        boolean bValido = todosItensValidos(cotacaoDto);
        if (!bValido)
            throw new LogicException("Resultado não pode ser publicado. Existe pelo menos um item de cotação com problemas de validação");
        
        CotacaoDao cotacaoDao = new CotacaoDao();
        CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
        TransactionControler tc = new TransactionControlerImpl(cotacaoDao.getAliasDB());
        
        cotacaoDao.setTransactionControler(tc);
        cotacaoItemRequisicaoDao.setTransactionControler(tc);
        try{ 
            tc.start();
            
            Collection<ItemCotacaoDTO> colItensCotacao = new ItemCotacaoDao().findByIdCotacao(cotacaoDto.getIdCotacao());
            if (colItensCotacao != null) {
                cotacaoItemRequisicaoDao.deleteByIdCotacao(cotacaoDto.getIdCotacao());

                ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();
                coletaPrecoDao.setTransactionControler(tc);
                ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
                itemRequisicaoDao.setTransactionControler(tc);
                for (ItemCotacaoDTO itemCotacaoDto : colItensCotacao) {
                    Collection<ItemRequisicaoProdutoDTO> colItensRequisicao = itemRequisicaoDao.findByIdItemCotacaoOrderQtde(itemCotacaoDto.getIdItemCotacao());
                    if (colItensRequisicao != null) {
                        ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
                        for (ItemRequisicaoProdutoDTO itemRequisicaoDto : colItensRequisicao) {
                            itemRequisicaoDto.setQtdeCotada(new Double(0.0));
                            itemRequisicaoDao.update(itemRequisicaoDto);    
                        }
                        Collection<ColetaPrecoDTO> colColetas = coletaPrecoDao.findHabilitadasByIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
                        if (colColetas != null) {
                            for (ColetaPrecoDTO coletaPrecoDto : colColetas) {
                                double qtdeAprovada = 0;
                                double qtdeCompra = coletaPrecoDto.getQuantidadeCompra();
                                if (qtdeCompra == 0)
                                    continue;
                                
                                for (ItemRequisicaoProdutoDTO itemRequisicaoDto : colItensRequisicao) {
                                    double qtdeItem = qtdeCompra;
                                    if (qtdeItem > (itemRequisicaoDto.getQtdeAprovada().doubleValue() - itemRequisicaoDto.getQtdeCotada().doubleValue()))
                                        qtdeItem = (itemRequisicaoDto.getQtdeAprovada().doubleValue() - itemRequisicaoDto.getQtdeCotada().doubleValue());
                                    if (qtdeItem <= 0)
                                        continue;
                                    
                                    CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto = new CotacaoItemRequisicaoDTO();
                                    cotacaoItemRequisicaoDto.setIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
                                    cotacaoItemRequisicaoDto.setIdCotacao(cotacaoDto.getIdCotacao());
                                    cotacaoItemRequisicaoDto.setIdItemRequisicaoProduto(itemRequisicaoDto.getIdItemRequisicaoProduto());
                                    cotacaoItemRequisicaoDto.setIdSolicitacaoServico(itemRequisicaoDto.getIdSolicitacaoServico());
                                    cotacaoItemRequisicaoDto.setQuantidade(qtdeItem);
                                    cotacaoItemRequisicaoDto.setQuantidadeEntregue(0.0);
                                    
                                    boolean bAprovado = itemRequisicaoDto.getAprovaCotacao() != null && itemRequisicaoDto.getAprovaCotacao().equals("N");
                                    if (!bAprovado && itemRequisicaoDto.getValorAprovado() != null && itemRequisicaoDto.getValorAprovado().doubleValue() > 0) {
                                        double valor = (coletaPrecoDto.getPreco() - coletaPrecoDto.getValorDesconto() + coletaPrecoDto.getValorAcrescimo()) / coletaPrecoDto.getQuantidadeCotada();
                                        bAprovado = itemRequisicaoDto.getValorAprovado().doubleValue() >= valor;
                                        if (!bAprovado && itemRequisicaoDto.getPercVariacaoPreco() != null && itemRequisicaoDto.getPercVariacaoPreco().doubleValue() > 0) {
                                            double perc = ((valor / itemRequisicaoDto.getValorAprovado().doubleValue()) - 1) * 100;
                                            bAprovado = perc <= itemRequisicaoDto.getPercVariacaoPreco().doubleValue();
                                        }
                                    }
                                    
                                    if (bAprovado) {
                                        cotacaoItemRequisicaoDto.setSituacao(SituacaoCotacaoItemRequisicao.PreAprovado.name());
                                        itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoPedido.name());
                                        qtdeAprovada += qtdeItem;
                                    }else{
                                        cotacaoItemRequisicaoDto.setSituacao(SituacaoCotacaoItemRequisicao.AguardaAprovacao.name());
                                        itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoAprovacaoCotacao.name());
                                    }
                                    cotacaoItemRequisicaoDao.create(cotacaoItemRequisicaoDto);
                                    
                                    itemRequisicaoDto.setQtdeCotada(itemRequisicaoDto.getQtdeCotada().doubleValue() + qtdeItem);
                                    itemRequisicaoDao.update(itemRequisicaoDto); 
                                    itemRequisicaoService.geraHistorico(tc, cotacaoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Publicacao, "Publicação dos resultados da cotacao No. "+cotacaoDto.getIdCotacao(), SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
                                    
                                    coletaPrecoDto.setQuantidadeAprovada(qtdeAprovada);
                                    coletaPrecoDao.update(coletaPrecoDto);
                                    
                                    qtdeCompra = qtdeCompra - qtdeItem;
                                    if (qtdeCompra == 0)
                                        break;
                                }
                            }
                        }
                    }
                }
            }
            
            cotacaoDto.setSituacao(SituacaoCotacao.Publicada.name());
            cotacaoDao.atualizaSituacao(cotacaoDto);
            geraHistoricoSituacao(tc, cotacaoDto, SituacaoCotacao.Publicada);
            
            tc.commit();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }            
    }

    @Override
    public void reabreColetaPrecos(CotacaoDTO cotacaoDto) throws Exception {
        TransactionControler tc = new TransactionControlerImpl(new CotacaoDao().getAliasDB());
        reabreColetaPrecos(cotacaoDto, tc);
    }

    public void reabreColetaPrecos(CotacaoDTO cotacaoDto, TransactionControler tc) throws Exception {
        CotacaoDao cotacaoDao = new CotacaoDao();
        ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();
        CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
        ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        ItemCotacaoDao itemCotacaoDao = new ItemCotacaoDao();
        
        cotacaoDao.setTransactionControler(tc);
        coletaPrecoDao.setTransactionControler(tc);
        cotacaoItemRequisicaoDao.setTransactionControler(tc);
        itemRequisicaoDao.setTransactionControler(tc);
        itemCotacaoDao.setTransactionControler(tc);
        try{ 
            tc.start();
            
            Collection<ItemCotacaoDTO> colItens = itemCotacaoDao.findByIdCotacao(cotacaoDto.getIdCotacao());
            if (colItens != null) {
                for (ItemCotacaoDTO itemCotacaoDto : colItens) {
                    ExecucaoSolicitacaoService execucaoSolicitacaoService = new ExecucaoSolicitacaoServiceEjb();
                    String loginUsuario = cotacaoDto.getUsuarioDto().getLogin();
                    String motivo = "Reabertura das coletas de preços da cotação";
                    List<ColetaPrecoDTO> colColetas = (List<ColetaPrecoDTO>) coletaPrecoDao.findByIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
                    if (colColetas != null) {
                        ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
                        for (ColetaPrecoDTO coletaPrecoDto : colColetas) {
                            coletaPrecoDto.setIdJustifResultado(null);
                            coletaPrecoDto.setComplemJustifResultado(null);
                            coletaPrecoDto.setIdRespResultado(null);
                            coletaPrecoDto.setQuantidadeCalculo(null);
                            coletaPrecoDto.setQuantidadeCompra(null);
                            coletaPrecoDto.setQuantidadeAprovada(null);
                            coletaPrecoDto.setQuantidadePedido(null);
                            coletaPrecoDto.setResultadoCalculo(null);
                            coletaPrecoDto.setResultadoFinal(null);
                            coletaPrecoDto.setPontuacao(null);
                            coletaPrecoDao.update(coletaPrecoDto);
                            
                            Collection<CotacaoItemRequisicaoDTO> colItensCotacao = cotacaoItemRequisicaoDao.findByIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
                            if (colItensCotacao != null) {
                                for (CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto : colItensCotacao) {
                                    if (cotacaoItemRequisicaoDto.getIdItemTrabalho() != null)
                                        execucaoSolicitacaoService.cancelaTarefa(loginUsuario, cotacaoItemRequisicaoDto.getIdItemTrabalho(), motivo, tc);
                                    //itemRequisicaoService.alteraSituacao(cotacaoDto.getUsuarioDto(), cotacaoItemRequisicaoDto.getIdItemRequisicaoProduto(), SituacaoItemRequisicaoProduto.AguardandoPedido, "Cotação No. "+cotacaoDto.getIdCotacao(), tc);
                                    cotacaoItemRequisicaoDao.delete(cotacaoItemRequisicaoDto);
                                }
                            }
                        }
                    }
                    Collection<ItemRequisicaoProdutoDTO> colItensRequisicao = itemRequisicaoDao.findByIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
                    if (colItensRequisicao != null) {
                        ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
                        for (ItemRequisicaoProdutoDTO itemRequisicaoDto : colItensRequisicao) {
                            itemRequisicaoDto.setQtdeCotada(new Double(0.0));
                            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoCotacao.name());
                            itemRequisicaoDao.update(itemRequisicaoDto);
                            itemRequisicaoService.geraHistorico(tc, cotacaoDto.getUsuarioDto(), itemRequisicaoDto, AcaoItemRequisicaoProduto.Reabertura, "Reabertura das coletas de preço da cotacao No. "+cotacaoDto.getIdCotacao(), SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
                        }
                    }
                    itemCotacaoDao.limpaMensagensValidacao(itemCotacaoDto);
                }
            }
            cotacaoDto.setSituacao(SituacaoCotacao.EmAndamento.name());
            cotacaoDao.atualizaSituacao(cotacaoDto);
            geraHistoricoSituacao(tc, cotacaoDto, SituacaoCotacao.EmAndamento);
            
            tc.commit();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }     
    }

}
