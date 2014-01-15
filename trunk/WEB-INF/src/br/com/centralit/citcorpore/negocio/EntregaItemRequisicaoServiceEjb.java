package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.EntregaItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.InspecaoEntregaItemDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CotacaoItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.EntregaItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.InspecaoEntregaItemDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoEntregaItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
public class EntregaItemRequisicaoServiceEjb extends CrudServicePojoImpl implements EntregaItemRequisicaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new EntregaItemRequisicaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdPedido(Integer parm) throws Exception{
		EntregaItemRequisicaoDao dao = new EntregaItemRequisicaoDao();
		try{
			return dao.findByIdPedido(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdPedido(Integer parm) throws Exception{
		EntregaItemRequisicaoDao dao = new EntregaItemRequisicaoDao();
		try{
			dao.deleteByIdPedido(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdColetaPreco(Integer parm) throws Exception{
		EntregaItemRequisicaoDao dao = new EntregaItemRequisicaoDao();
		try{
			return dao.findByIdColetaPreco(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdColetaPreco(Integer parm) throws Exception{
		EntregaItemRequisicaoDao dao = new EntregaItemRequisicaoDao();
		try{
			dao.deleteByIdColetaPreco(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdItemRequisicaoProduto(Integer parm) throws Exception{
		EntregaItemRequisicaoDao dao = new EntregaItemRequisicaoDao();
		try{
			return dao.findByIdItemRequisicaoProduto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdItemRequisicaoProduto(Integer parm) throws Exception{
		EntregaItemRequisicaoDao dao = new EntregaItemRequisicaoDao();
		try{
			dao.deleteByIdItemRequisicaoProduto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

    @Override
    public Collection findByIdItemTrabalho(Integer parm) throws Exception {
        EntregaItemRequisicaoDao dao = new EntregaItemRequisicaoDao();
        try{
            return dao.findByIdItemTrabalho(parm);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    
    @Override
    public Collection<EntregaItemRequisicaoDTO> findNaoAprovadasByIdSolicitacaoServico(Integer idSolicitacaoServico) throws Exception {
        EntregaItemRequisicaoDao dao = new EntregaItemRequisicaoDao();
        try{
            return dao.findNaoAprovadasByIdSolicitacaoServico(idSolicitacaoServico);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    
    public void atualizaInspecao(EntregaItemRequisicaoDTO entregaItemRequisicaoDto) throws Exception {
        InspecaoEntregaItemDao inspecaoEntregaItemDao = new InspecaoEntregaItemDao();
        TransactionControler tc = new TransactionControlerImpl(inspecaoEntregaItemDao.getAliasDB());
        
        try{
            tc.start();
            inspecaoEntregaItemDao.setTransactionControler(tc);
            
            inspecaoEntregaItemDao.deleteByIdEntrega(entregaItemRequisicaoDto.getIdEntrega());
            if (entregaItemRequisicaoDto.getColInspecao() != null) {
                for (InspecaoEntregaItemDTO inspecaoDto : entregaItemRequisicaoDto.getColInspecao()) {
                    if (inspecaoDto.getAvaliacao() == null || inspecaoDto.getAvaliacao().trim().length() == 0)
                        throw new Exception("Avaliação não informada");
                    inspecaoDto.setIdResponsavel(entregaItemRequisicaoDto.getUsuarioDto().getIdEmpregado());
                    inspecaoDto.setDataHoraInspecao(UtilDatas.getDataHoraAtual());
                    inspecaoDto.setIdEntrega(entregaItemRequisicaoDto.getIdEntrega());
                    inspecaoEntregaItemDao.create(inspecaoDto);
                }
            }
            
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }        
    }

    public void atualizaSituacao(EntregaItemRequisicaoDTO entregaItemRequisicaoDto, UsuarioDTO usuarioDto, TransactionControler tc) throws Exception{
        ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        itemRequisicaoDao.setTransactionControler(tc);
        ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();

        ItemRequisicaoProdutoDTO itemRequisicaoDto = new ItemRequisicaoProdutoDTO();
        itemRequisicaoDto.setIdItemRequisicaoProduto(entregaItemRequisicaoDto.getIdItemRequisicaoProduto());
        itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoDto);

        EntregaItemRequisicaoDao entregaItemRequisicaoDao = new EntregaItemRequisicaoDao();
        entregaItemRequisicaoDao.setTransactionControler(tc);
        
        EntregaItemRequisicaoDTO entregaAuxDto = new EntregaItemRequisicaoDTO();
        entregaAuxDto.setIdEntrega(entregaItemRequisicaoDto.getIdEntrega());
        entregaAuxDto = (EntregaItemRequisicaoDTO) entregaItemRequisicaoDao.restore(entregaAuxDto);
        ParecerDTO parecerDto = new ParecerServiceEjb().createOrUpdate(tc, entregaAuxDto.getIdParecer(), usuarioDto, entregaItemRequisicaoDto.getIdJustificativa(), entregaItemRequisicaoDto.getComplementoJustificativa(), entregaItemRequisicaoDto.getAprovado());
        entregaAuxDto.setIdParecer(parecerDto.getIdParecer());
        if (parecerDto.getAprovado().equalsIgnoreCase("S")) {
            entregaAuxDto.setSituacao(SituacaoEntregaItemRequisicao.Aprovada.name());
            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.Finalizado.name());
        }else if (parecerDto.getAprovado().equalsIgnoreCase("N")){
            entregaAuxDto.setIdItemTrabalho(null);
            entregaAuxDto.setSituacao(SituacaoEntregaItemRequisicao.NaoAprovada.name());
            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.InspecaoRejeitada.name());
        }
        entregaItemRequisicaoDao.update(entregaAuxDto);
        itemRequisicaoDao.update(itemRequisicaoDto);
        itemRequisicaoService.geraHistorico(tc, usuarioDto, itemRequisicaoDto, AcaoItemRequisicaoProduto.Inspecao, null, SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
    }

    public void atualizaAcionamentoGarantia(EntregaItemRequisicaoDTO entregaItemRequisicaoDto, UsuarioDTO usuarioDto, TransactionControler tc) throws Exception {
        EntregaItemRequisicaoDao entregaItemRequisicaoDao = new EntregaItemRequisicaoDao();
        entregaItemRequisicaoDao.setTransactionControler(tc);
        
        EntregaItemRequisicaoDTO entregaAuxDto = new EntregaItemRequisicaoDTO();
        entregaAuxDto.setIdEntrega(entregaItemRequisicaoDto.getIdEntrega());
        entregaAuxDto = (EntregaItemRequisicaoDTO) entregaItemRequisicaoDao.restore(entregaAuxDto);
        entregaAuxDto.setSituacao(SituacaoEntregaItemRequisicao.Aguarda.name());
        entregaAuxDto.setIdItemTrabalho(null);
        entregaItemRequisicaoDao.update(entregaAuxDto);

        ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        itemRequisicaoDao.setTransactionControler(tc);
        ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();

        ItemRequisicaoProdutoDTO itemRequisicaoDto = new ItemRequisicaoProdutoDTO();
        itemRequisicaoDto.setIdItemRequisicaoProduto(entregaItemRequisicaoDto.getIdItemRequisicaoProduto());
        itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoDto);
        itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoInspecao.name());
        entregaItemRequisicaoDao.update(entregaAuxDto);
        itemRequisicaoService.geraHistorico(tc, usuarioDto, itemRequisicaoDto, AcaoItemRequisicaoProduto.Garantia, null, SituacaoItemRequisicaoProduto.AguardandoInspecaoGarantia);
    }

}
