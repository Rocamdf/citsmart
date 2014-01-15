package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.FornecedorCotacaoDTO;
import br.com.centralit.citcorpore.integracao.ColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.CotacaoDao;
import br.com.centralit.citcorpore.integracao.FornecedorCotacaoDao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
public class FornecedorCotacaoServiceEjb extends CrudServicePojoImpl implements FornecedorCotacaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new FornecedorCotacaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	
    protected void validaDelete(Object arg0) throws Exception {
        FornecedorCotacaoDTO fornecedorCotacaoDto = (FornecedorCotacaoDTO) restore((FornecedorCotacaoDTO) arg0);
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        cotacaoDto.setIdCotacao(fornecedorCotacaoDto.getIdCotacao());
        cotacaoDto = (CotacaoDTO) new CotacaoDao().restore(cotacaoDto);
        if (!cotacaoDto.getSituacao().equals(SituacaoCotacao.EmAndamento.name()))
            throw new LogicException("A situação da cotação não permite a exclusão do fornecedor."); 
        
        Collection colColetas = new ColetaPrecoDao().findByIdFornecedor(fornecedorCotacaoDto.getIdFornecedor());
        if (colColetas != null && !colColetas.isEmpty())
            throw new LogicException("Exclusão não permitida. Existe pelo menos uma coleta de preços associada ao fornecedor."); 
    }
    
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdCotacao(Integer parm) throws Exception{
		FornecedorCotacaoDao dao = new FornecedorCotacaoDao();
		try{
			return dao.findByIdCotacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCotacao(Integer parm) throws Exception{
		FornecedorCotacaoDao dao = new FornecedorCotacaoDao();
		try{
			dao.deleteByIdCotacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdFornecedor(Integer parm) throws Exception{
		FornecedorCotacaoDao dao = new FornecedorCotacaoDao();
		try{
			return dao.findByIdFornecedor(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
    @Override
    public void delete(IDto model) throws ServiceException, LogicException {
        FornecedorCotacaoDTO fornecedorCotacaoDto = (FornecedorCotacaoDTO) model;
        fornecedorCotacaoDto = (FornecedorCotacaoDTO) restore(fornecedorCotacaoDto);
        FornecedorCotacaoDao fornecedorCotacaoDao = new FornecedorCotacaoDao();
        TransactionControler tc = new TransactionControlerImpl(fornecedorCotacaoDao.getAliasDB());
        
        try{
            validaDelete(model);
            
            fornecedorCotacaoDao.setTransactionControler(tc);
            
            tc.start();
        
            fornecedorCotacaoDao.delete(fornecedorCotacaoDto);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
    }

}
