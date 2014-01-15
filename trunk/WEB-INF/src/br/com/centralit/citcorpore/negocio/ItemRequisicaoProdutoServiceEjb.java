package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.HistoricoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.HistoricoItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.integracao.ProdutoDao;
import br.com.centralit.citcorpore.integracao.UnidadeMedidaDao;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
public class ItemRequisicaoProdutoServiceEjb extends CrudServicePojoImpl implements ItemRequisicaoProdutoService {
	protected CrudDAO getDao() throws ServiceException {
		return new ItemRequisicaoProdutoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

    public void alteraSituacao(UsuarioDTO usuarioDto, Integer idItemRequisicao, AcaoItemRequisicaoProduto acao, SituacaoItemRequisicaoProduto novaSituacao, String complemento, TransactionControler tc) throws Exception {
        ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        ItemRequisicaoProdutoDTO itemRequisicaoDto = new ItemRequisicaoProdutoDTO();
        itemRequisicaoDto.setIdItemRequisicaoProduto(idItemRequisicao);
        itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoDto);
        
        itemRequisicaoDao.setTransactionControler(tc);
        
        itemRequisicaoDto.setSituacao(novaSituacao.name());
        itemRequisicaoDao.update(itemRequisicaoDto);
        geraHistorico(tc, usuarioDto, itemRequisicaoDto, acao, complemento, novaSituacao);
    }
    
    public void geraHistorico(TransactionControler tc, UsuarioDTO usuarioDto, ItemRequisicaoProdutoDTO itemRequisicaoDto, AcaoItemRequisicaoProduto acao, String complemento, SituacaoItemRequisicaoProduto situacao) throws Exception {
        HistoricoItemRequisicaoDTO historicoItemRequisicaoDto = new HistoricoItemRequisicaoDTO();
        historicoItemRequisicaoDto.setIdItemRequisicao(itemRequisicaoDto.getIdItemRequisicaoProduto());
        if (usuarioDto != null && usuarioDto.getIdEmpregado() != null)
        	historicoItemRequisicaoDto.setIdResponsavel(usuarioDto.getIdEmpregado());
        historicoItemRequisicaoDto.setDataHora(UtilDatas.getDataHoraAtual());
        historicoItemRequisicaoDto.setComplemento(complemento);
        historicoItemRequisicaoDto.setAcao(acao.name());
        historicoItemRequisicaoDto.setSituacao(situacao.name());
        HistoricoItemRequisicaoDao historicoItemRequisicaoDao = new HistoricoItemRequisicaoDao();
        historicoItemRequisicaoDao.setTransactionControler(tc);
        historicoItemRequisicaoDao.create(historicoItemRequisicaoDto);
    }

    public void recuperaRelacionamentos(Collection<ItemRequisicaoProdutoDTO> colItens) throws Exception{
        if (colItens != null) {
            UnidadeMedidaDao unidadeMedidaDao = new UnidadeMedidaDao();
            ProdutoDao produtoDao = new ProdutoDao();
            ParecerDao parecerDao = new ParecerDao();
            for (ItemRequisicaoProdutoDTO itemRequisicaoProdutoDto : colItens) {
                UnidadeMedidaDTO unidDto = new UnidadeMedidaDTO();
                if(itemRequisicaoProdutoDto.getIdUnidadeMedida() != null) {
	                unidDto.setIdUnidadeMedida(itemRequisicaoProdutoDto.getIdUnidadeMedida());
	                unidDto = (UnidadeMedidaDTO) unidadeMedidaDao.restore(unidDto);
	                itemRequisicaoProdutoDto.setSiglaUnidadeMedida(unidDto.getSiglaUnidadeMedida());
                }
                if (itemRequisicaoProdutoDto.getIdProduto() != null) {
                    ProdutoDTO produtoDto = new ProdutoDTO();
                    produtoDto.setIdProduto(itemRequisicaoProdutoDto.getIdProduto());
                    produtoDto = (ProdutoDTO) produtoDao.restore(produtoDto);
                    if (produtoDto != null) {
                        itemRequisicaoProdutoDto.setCodigoProduto(produtoDto.getCodigoProduto());
                        itemRequisicaoProdutoDto.setNomeProduto(produtoDto.getNomeProduto());
                    }
                }
                if (itemRequisicaoProdutoDto.getIdParecerValidacao() != null) {
                    ParecerDTO parecerDto = new ParecerDTO();
                    parecerDto.setIdParecer(itemRequisicaoProdutoDto.getIdParecerValidacao());
                    parecerDto = (ParecerDTO) parecerDao.restore(parecerDto);
                    if (parecerDto != null) {
                        itemRequisicaoProdutoDto.setIdJustificativaValidacao(parecerDto.getIdJustificativa());
                        itemRequisicaoProdutoDto.setComplemJustificativaValidacao(parecerDto.getComplementoJustificativa());
                        itemRequisicaoProdutoDto.setValidado(parecerDto.getAprovado());
                    }
                }
                if (itemRequisicaoProdutoDto.getIdParecerAutorizacao() != null) {
                    ParecerDTO parecerDto = new ParecerDTO();
                    parecerDto.setIdParecer(itemRequisicaoProdutoDto.getIdParecerAutorizacao());
                    parecerDto = (ParecerDTO) parecerDao.restore(parecerDto);
                    if (parecerDto != null) {
                        itemRequisicaoProdutoDto.setIdJustificativaAutorizacao(parecerDto.getIdJustificativa());
                        itemRequisicaoProdutoDto.setComplemJustificativaAutorizacao(parecerDto.getComplementoJustificativa());
                        itemRequisicaoProdutoDto.setAutorizado(parecerDto.getAprovado());
                    }
                }
            }               
        }
	}
	
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception{
		ItemRequisicaoProdutoDao dao = new ItemRequisicaoProdutoDao();
		try{
			Collection<ItemRequisicaoProdutoDTO> col = dao.findByIdSolicitacaoServico(parm);
			recuperaRelacionamentos(col);
			return col;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
    public Collection findByIdSolicitacaoServicoAndSituacao(Integer parm,  SituacaoItemRequisicaoProduto[] situacao) throws Exception{
        ItemRequisicaoProdutoDao dao = new ItemRequisicaoProdutoDao();
        try{
            Collection<ItemRequisicaoProdutoDTO> result = new ArrayList();
            if (situacao != null && situacao.length > 0) {
                for (int i = 0; i < situacao.length; i++) {
                    SituacaoItemRequisicaoProduto situacaoItem = situacao[i];
                    Collection<ItemRequisicaoProdutoDTO> col = dao.findByIdSolicitacaoServicoAndSituacao(parm, situacaoItem);    
                    if (col != null) {
                        recuperaRelacionamentos(col);
                        result.addAll(col);
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public Collection findByIdSolicitacaoAndSituacaoAndTipoAtendimento(Integer parm,  SituacaoItemRequisicaoProduto[] situacao, String tipoAtendimento) throws Exception{
        ItemRequisicaoProdutoDao dao = new ItemRequisicaoProdutoDao();
        try{
            Collection<ItemRequisicaoProdutoDTO> result = new ArrayList();
            if (situacao != null && situacao.length > 0) {
                for (int i = 0; i < situacao.length; i++) {
                    SituacaoItemRequisicaoProduto situacaoItem = situacao[i];
                    Collection<ItemRequisicaoProdutoDTO> col = dao.findByIdSolicitacaoAndSituacaoAndTipoAtendimento(parm, situacaoItem, tipoAtendimento);    
                    if (col != null) {
                        recuperaRelacionamentos(col);
                        result.addAll(col);
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void deleteByIdSolicitacaoServico(Integer parm) throws Exception{
		ItemRequisicaoProdutoDao dao = new ItemRequisicaoProdutoDao();
		try{
			dao.deleteByIdSolicitacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdProduto(Integer parm) throws Exception{
		ItemRequisicaoProdutoDao dao = new ItemRequisicaoProdutoDao();
		try{
			return dao.findByIdProduto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

    @Override
	public Collection findByIdItemCotacao(Integer parm) throws Exception {
        ItemRequisicaoProdutoDao dao = new ItemRequisicaoProdutoDao();
        try{
            return dao.findByIdItemCotacao(parm);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
	}
	
    @Override
    public Collection<ItemRequisicaoProdutoDTO> recuperaItensParaCotacao(Date dataInicio, Date dataFim, Integer idCentroCusto,
            Integer idProjeto, Integer idEnderecoEntrega, Integer idSolicitacaoServico) throws Exception {
        ItemRequisicaoProdutoDao dao = new ItemRequisicaoProdutoDao();
        try{
            return dao.recuperaItensParaCotacao(dataInicio, dataFim, idCentroCusto, idProjeto, idEnderecoEntrega, idSolicitacaoServico);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void atualizaIdItemCotacao(UsuarioDTO usuarioDto, ItemRequisicaoProdutoDTO itemRequisicaoDto, ItemCotacaoDTO itemCotacaoDto, TransactionControler tc) throws Exception {
        ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        itemRequisicaoDao.setTransactionControler(tc);
        
        ItemRequisicaoProdutoDTO itemRequisicaoAuxDto = new ItemRequisicaoProdutoDTO();
        itemRequisicaoAuxDto.setIdItemRequisicaoProduto(itemRequisicaoDto.getIdItemRequisicaoProduto());
        itemRequisicaoAuxDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoAuxDto);
        itemRequisicaoAuxDto.setIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
        itemRequisicaoAuxDto.setQtdeCotada(new Double(0.0));
        //itemRequisicaoAuxDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoPedido.name());
        itemRequisicaoDao.update(itemRequisicaoAuxDto);
        
        //geraHistorico(tc, usuarioDto, itemRequisicaoDto, "Cotação No. "+itemCotacaoDto.getIdCotacao(), SituacaoItemRequisicaoProduto.AguardandoPedido);
    }

    public void desassociaItemCotacao(UsuarioDTO usuarioDto, ItemCotacaoDTO itemCotacaoDto, AcaoItemRequisicaoProduto acao, TransactionControler tc) throws Exception {
        ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        itemRequisicaoDao.setTransactionControler(tc);
        Collection<ItemRequisicaoProdutoDTO> colItens = itemRequisicaoDao.findByIdItemCotacao(itemCotacaoDto.getIdItemCotacao());
        if (colItens != null) {
            for (ItemRequisicaoProdutoDTO itemRequisicaoDto : colItens) {
            	if (!itemRequisicaoDto.getSituacao().equals(SituacaoItemRequisicaoProduto.AguardandoCotacao.name())) {
                    itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoCotacao.name());
                    geraHistorico(tc, usuarioDto, itemRequisicaoDto, acao, null, SituacaoItemRequisicaoProduto.AguardandoCotacao);
            	}
                itemRequisicaoDto.setIdItemCotacao(null);
                itemRequisicaoDto.setQtdeCotada(new Double(0.0));
                itemRequisicaoDao.update(itemRequisicaoDto);
            }
        }
    }
}
