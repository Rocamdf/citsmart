package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.CotacaoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.CotacaoItemRequisicaoDao;
import br.com.centralit.citcorpore.integracao.ItemRequisicaoProdutoDao;
import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacaoItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;
public class CotacaoItemRequisicaoServiceEjb extends CrudServicePojoImpl implements CotacaoItemRequisicaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new CotacaoItemRequisicaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findPendentesByIdCotacao(Integer idCotacao) throws Exception {
        CotacaoItemRequisicaoDao dao = new CotacaoItemRequisicaoDao();
        try{
            return dao.findPendentesByIdCotacao(idCotacao);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
	}
	
    public Collection findByIdRequisicaoProduto(Integer parm) throws Exception{
        CotacaoItemRequisicaoDao dao = new CotacaoItemRequisicaoDao();
        try{
            return dao.findByIdRequisicaoProduto(parm);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
    public Collection findByIdCotacao(Integer parm) throws Exception{
        CotacaoItemRequisicaoDao dao = new CotacaoItemRequisicaoDao();
        try{
            return dao.findByIdCotacao(parm);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }    
    public Collection findByIdColetaPreco(Integer parm) throws Exception{
		CotacaoItemRequisicaoDao dao = new CotacaoItemRequisicaoDao();
		try{
			return dao.findByIdColetaPreco(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdColetaPreco(Integer parm) throws Exception{
		CotacaoItemRequisicaoDao dao = new CotacaoItemRequisicaoDao();
		try{
			dao.deleteByIdColetaPreco(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdItemRequisicaoProduto(Integer parm) throws Exception{
		CotacaoItemRequisicaoDao dao = new CotacaoItemRequisicaoDao();
		try{
			return dao.findByIdItemRequisicaoProduto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdItemTrabalho(Integer parm) throws Exception{
        CotacaoItemRequisicaoDao dao = new CotacaoItemRequisicaoDao();
        try{
            return dao.findByIdItemTrabalho(parm);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
	public void deleteByIdItemRequisicaoProduto(Integer parm) throws Exception{
		CotacaoItemRequisicaoDao dao = new CotacaoItemRequisicaoDao();
		try{
			dao.deleteByIdItemRequisicaoProduto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

    public void atualizaAprovacaoCotacao(CotacaoItemRequisicaoDTO cotacaoItemRequisicaoDto, UsuarioDTO usuarioDto, TransactionControler tc) throws Exception{
        ItemRequisicaoProdutoDao itemRequisicaoDao = new ItemRequisicaoProdutoDao();
        CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
        cotacaoItemRequisicaoDao.setTransactionControler(tc);
        itemRequisicaoDao.setTransactionControler(tc);
        
        CotacaoItemRequisicaoDTO itemAuxDto = new CotacaoItemRequisicaoDTO();
        itemAuxDto.setIdItemRequisicaoProduto(cotacaoItemRequisicaoDto.getIdItemRequisicaoProduto());
        itemAuxDto.setIdColetaPreco(cotacaoItemRequisicaoDto.getIdColetaPreco());
        itemAuxDto = (CotacaoItemRequisicaoDTO) cotacaoItemRequisicaoDao.restore(itemAuxDto);
        
        ItemRequisicaoProdutoDTO itemRequisicaoDto = new ItemRequisicaoProdutoDTO();
        itemRequisicaoDto.setIdItemRequisicaoProduto(cotacaoItemRequisicaoDto.getIdItemRequisicaoProduto());
        itemRequisicaoDto = (ItemRequisicaoProdutoDTO) itemRequisicaoDao.restore(itemRequisicaoDto);
        itemRequisicaoDto.setPercVariacaoPreco(cotacaoItemRequisicaoDto.getPercVariacaoPreco());
        
        ParecerDTO parecerDto = new ParecerServiceEjb().createOrUpdate(tc, itemAuxDto.getIdParecer(), usuarioDto, cotacaoItemRequisicaoDto.getIdJustificativa(), cotacaoItemRequisicaoDto.getComplementoJustificativa(), cotacaoItemRequisicaoDto.getAprovado());
        itemAuxDto.setIdParecer(parecerDto.getIdParecer());
        if (parecerDto.getAprovado().equalsIgnoreCase("S")) {
            ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();
            coletaPrecoDao.setTransactionControler(tc);
            ColetaPrecoDTO coletaPrecoDto = new ColetaPrecoDTO();
            coletaPrecoDto.setIdColetaPreco(cotacaoItemRequisicaoDto.getIdColetaPreco());
            coletaPrecoDto = (ColetaPrecoDTO) coletaPrecoDao.restore(coletaPrecoDto);
            double valor = (coletaPrecoDto.getPreco() - coletaPrecoDto.getValorDesconto() + coletaPrecoDto.getValorAcrescimo()) / coletaPrecoDto.getQuantidadeCotada();
            
            itemAuxDto.setSituacao(SituacaoCotacaoItemRequisicao.Aprovado.name());
            itemRequisicaoDto.setValorAprovado(valor);
            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.AguardandoPedido.name());
        }else if (parecerDto.getAprovado().equalsIgnoreCase("N")){
            itemAuxDto.setSituacao(SituacaoCotacaoItemRequisicao.NaoAprovado.name());
            itemRequisicaoDto.setValorAprovado(new Double(0));
            itemRequisicaoDto.setSituacao(SituacaoItemRequisicaoProduto.CotacaoNaoAprovada.name());
        }
        
        itemRequisicaoDao.update(itemRequisicaoDto);
        cotacaoItemRequisicaoDao.update(itemAuxDto);
        
        ItemRequisicaoProdutoServiceEjb itemRequisicaoService = new ItemRequisicaoProdutoServiceEjb();
        itemRequisicaoService.geraHistorico(tc, usuarioDto, itemRequisicaoDto, AcaoItemRequisicaoProduto.Aprovacao, "Cotação No. "+cotacaoItemRequisicaoDto.getIdCotacao()+", Coleta No. "+cotacaoItemRequisicaoDto.getIdColetaPreco(), SituacaoItemRequisicaoProduto.valueOf(itemRequisicaoDto.getSituacao()));
        
        if (parecerDto.getAprovado().equalsIgnoreCase("S")) {
            ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();
            coletaPrecoDao.setTransactionControler(tc);
            ColetaPrecoDTO coletaPrecoDto = new ColetaPrecoDTO();
            coletaPrecoDto.setIdColetaPreco(cotacaoItemRequisicaoDto.getIdColetaPreco());
            coletaPrecoDto = (ColetaPrecoDTO) coletaPrecoDao.restore(coletaPrecoDto);
            double qtdeAprovada = coletaPrecoDto.getQuantidadeAprovada();
            qtdeAprovada += itemAuxDto.getQuantidade();
            coletaPrecoDto.setQuantidadeAprovada(qtdeAprovada);
            coletaPrecoDao.update(coletaPrecoDto);
        }
    }
}
