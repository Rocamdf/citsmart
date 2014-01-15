package br.com.centralit.citcorpore.negocio;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.integracao.CategoriaProdutoDao;
import br.com.centralit.citcorpore.integracao.CriterioCotacaoCategoriaDao;
import br.com.centralit.citcorpore.integracao.ProdutoDao;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

public class ProdutoServiceEjb extends CrudServicePojoImpl implements ProdutoService {
	protected CrudDAO getDao() throws ServiceException {
		return new ProdutoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdTipoProduto(Integer parm) throws Exception{
		ProdutoDao dao = new ProdutoDao();
		try{
			return dao.findByIdTipoProduto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdTipoProduto(Integer parm) throws Exception{
		ProdutoDao dao = new ProdutoDao();
		try{
			dao.deleteByIdTipoProduto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
    @Override
    public void recuperaImagem(ProdutoDTO especificacaoProdutoDto) throws Exception {
        especificacaoProdutoDto.setImagem(null);
        List<ControleGEDDTO> colGed = (List<ControleGEDDTO>) new ControleGEDDao().listByIdTabelaAndID(ControleGEDDTO.TABELA_PRODUTO, especificacaoProdutoDto.getIdProduto());
        if (colGed != null && !colGed.isEmpty()) 
            especificacaoProdutoDto.setImagem(new ControleGEDServiceBean().getRelativePathFromGed(colGed.get(0)));
    }

    @Override
    public Collection findByIdCategoria(Integer parm) throws Exception {
        ProdutoDao dao = new ProdutoDao();
        try{
            return dao.findByIdCategoria(parm);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdCategoriaAndAceitaRequisicao(Integer idCategoria, String aceitaRequisicao) throws Exception {
        ProdutoDao dao = new ProdutoDao();
        try{
            return dao.findByIdCategoriaAndAceitaRequisicao(idCategoria, aceitaRequisicao);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public Collection validaNovoProduto(ProdutoDTO produtoDto) throws Exception {
		ProdutoDao dao = new ProdutoDao();
		try{
			return dao.validaNovoProduto(produtoDto);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public IDto restore(IDto model) throws ServiceException, LogicException {
	    ProdutoDTO produtoDto = (ProdutoDTO) super.restore(model);
	    if (produtoDto != null)
	        produtoDto.montaIdentificacao();   // só pra setar a identificação
	    return produtoDto;
	}
	
	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
        ProdutoDao produtoDao = new ProdutoDao();
        TransactionControler tc = new TransactionControlerImpl(produtoDao.getAliasDB());
        
        try{
            validaCreate(model);
            
            produtoDao.setTransactionControler(tc);
            
            tc.start();
        
            ProdutoDTO produtoDto = (ProdutoDTO) model;
            produtoDto = (ProdutoDTO) produtoDao.create(produtoDto);
            
            if (produtoDto.getCodigoProduto() == null || produtoDto.getCodigoProduto().trim().length() == 0) {
                produtoDto.setCodigoProduto(""+produtoDto.getIdProduto());
                produtoDao.update(produtoDto);
            }
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
        return model;
	}
}
