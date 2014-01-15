package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.AvaliacaoColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.centralit.citcorpore.integracao.AvaliacaoColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.ColetaPrecoDao;
import br.com.centralit.citcorpore.integracao.ItemCotacaoDao;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
public class ColetaPrecoServiceEjb extends CrudServicePojoImpl implements ColetaPrecoService {
	protected CrudDAO getDao() throws ServiceException {
		return new ColetaPrecoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	    validaAtualizacao((ColetaPrecoDTO) arg0);
	}
	
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {
	    validaAtualizacao((ColetaPrecoDTO) arg0);
	}

	private void validaAtualizacao(ColetaPrecoDTO coletaPrecoDto) throws Exception {
	    if (coletaPrecoDto.getDataValidade() != null && coletaPrecoDto.getDataValidade().compareTo(coletaPrecoDto.getDataColeta()) < 0)
            throw new LogicException("Data da validade não pode ser menor que a data da coleta");
	    if (coletaPrecoDto.getIdColetaPreco() == null) {
	        Collection<ColetaPrecoDTO> colColetas = findByIdItemCotacaoAndIdFornecedor(coletaPrecoDto.getIdFornecedor(),coletaPrecoDto.getIdItemCotacao());
	        if (colColetas != null && !colColetas.isEmpty())
	            throw new LogicException("Já existe uma coleta de preço para este fornecedor e este item");
	    }
        ItemCotacaoDTO itemCotacaoDto = new ItemCotacaoDTO();
        itemCotacaoDto.setIdItemCotacao(coletaPrecoDto.getIdItemCotacao());
        itemCotacaoDto = (ItemCotacaoDTO) new ItemCotacaoDao().restore(itemCotacaoDto);
        if (itemCotacaoDto.getExigeFornecedorQualificado() == null || !itemCotacaoDto.getExigeFornecedorQualificado().equalsIgnoreCase("S")) 
            return;
        if (!new AvaliacaoFornecedorServiceEjb().fornecedorQualificado(coletaPrecoDto.getIdFornecedor()))
            throw new LogicException("Este item de cotação exige que o fornecedor seja qualificado");
    }

    public Collection findHabilitadasByIdCotacao(Integer parm) throws Exception{
        ColetaPrecoDao dao = new ColetaPrecoDao();
        try{
            return dao.findHabilitadasByIdCotacao(parm);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
	
	public Collection findByIdItemCotacao(Integer parm) throws Exception{
		ColetaPrecoDao dao = new ColetaPrecoDao();
		try{
			return dao.findByIdItemCotacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdItemCotacao(Integer parm) throws Exception{
		ColetaPrecoDao dao = new ColetaPrecoDao();
		try{
			dao.deleteByIdItemCotacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdPedido(Integer parm) throws Exception{
		ColetaPrecoDao dao = new ColetaPrecoDao();
		try{
			return dao.findByIdPedido(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdFornecedor(Integer parm) throws Exception{
		ColetaPrecoDao dao = new ColetaPrecoDao();
		try{
			return dao.findByIdFornecedor(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdFornecedor(Integer parm) throws Exception{
		ColetaPrecoDao dao = new ColetaPrecoDao();
		try{
			dao.deleteByIdFornecedor(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	private void atualizaCriterios(ColetaPrecoDTO coletaPrecoDto, AvaliacaoColetaPrecoDao avaliacaoColetaPrecoDao) throws Exception {
        if (coletaPrecoDto.getIdCriterioColeta() != null) {
            Integer[] idCriterioColeta = coletaPrecoDto.getIdCriterioColeta();
            Integer[] pesoCriterioColeta = coletaPrecoDto.getPesoCriterioColeta();
            for (int i = 0; i < idCriterioColeta.length; i++) {
                if (idCriterioColeta[i] != null) {
                    if (pesoCriterioColeta[i] == null)
                        throw new Exception("Avaliação não informada");
                    if (pesoCriterioColeta[i].intValue() > 10)
                        throw new Exception("A avaliação deve estar entre 0 e 10");    
                    
                    AvaliacaoColetaPrecoDTO avaliacaoCotacaoDto = new AvaliacaoColetaPrecoDTO();
                    avaliacaoCotacaoDto.setIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
                    avaliacaoCotacaoDto.setIdCriterio(idCriterioColeta[i]);
                    avaliacaoCotacaoDto.setAvaliacao(pesoCriterioColeta[i]);
                    avaliacaoColetaPrecoDao.create(avaliacaoCotacaoDto);
                }
            }
        }
	}
	
	private void atualizaAnexos(ColetaPrecoDTO coletaPrecoDto, TransactionControler tc) throws Exception {
        new ControleGEDServiceBean().atualizaAnexos(coletaPrecoDto.getAnexos(), ControleGEDDTO.TABELA_COLETAPRECOS, coletaPrecoDto.getIdColetaPreco(), tc);
	}
	
	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
        ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();
        AvaliacaoColetaPrecoDao avaliacaoColetaPrecoDao = new AvaliacaoColetaPrecoDao();
        TransactionControler tc = new TransactionControlerImpl(coletaPrecoDao.getAliasDB());
        
        try{
            validaCreate(model);
            
            coletaPrecoDao.setTransactionControler(tc);
            avaliacaoColetaPrecoDao.setTransactionControler(tc);
            
            tc.start();
        
            ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) model;
            coletaPrecoDto = (ColetaPrecoDTO) coletaPrecoDao.create(coletaPrecoDto);
            
            atualizaCriterios(coletaPrecoDto, avaliacaoColetaPrecoDao);
            atualizaAnexos(coletaPrecoDto, tc);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
        return model;
	}
	
	@Override
	public void update(IDto model) throws ServiceException, LogicException {
        ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();
        AvaliacaoColetaPrecoDao avaliacaoColetaPrecoDao = new AvaliacaoColetaPrecoDao();
        TransactionControler tc = new TransactionControlerImpl(coletaPrecoDao.getAliasDB());
        
        try{
            validaUpdate(model);
            
            coletaPrecoDao.setTransactionControler(tc);
            avaliacaoColetaPrecoDao.setTransactionControler(tc);
            
            tc.start();
        
            ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) model;
            coletaPrecoDao.update(coletaPrecoDto);
            
            avaliacaoColetaPrecoDao.deleteByIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
            atualizaCriterios(coletaPrecoDto, avaliacaoColetaPrecoDao);
            atualizaAnexos(coletaPrecoDto, tc);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
	}

    @Override
    public void deleteByIdItemCotacaoAndIdFornecedor(Integer idFornecedor, Integer idItemCotacao) throws Exception {
        new ColetaPrecoDao().deleteByIdItemCotacaoAndIdFornecedor(idFornecedor, idItemCotacao);
    }

    @Override
    public Collection<ColetaPrecoDTO> findByIdItemCotacaoAndIdFornecedor(Integer idFornecedor, Integer idItemCotacao) throws Exception {
        return new ColetaPrecoDao().findByIdItemCotacaoAndIdFornecedor(idFornecedor, idItemCotacao);
    }
	
    @Override
    public Collection<ColetaPrecoDTO> findByIdCotacao(Integer idCotacao) throws Exception {
        return new ColetaPrecoDao().findByIdCotacao(idCotacao);
    }
	
    @Override
    public Collection findResultadoByIdItemCotacao(Integer idItemCotacao) throws Exception {
        return new ColetaPrecoDao().findResultadoByIdItemCotacao(idItemCotacao);
    }

    @Override
    public void defineResultado(ColetaPrecoDTO coletaPrecoDto)throws Exception {
        if (coletaPrecoDto.getResultadoFinal() == null || coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_EMPATE))
            throw new LogicException("Resultado não definido");
        if (coletaPrecoDto.getQuantidadeCompra() == null)
            throw new LogicException("Quantidade para compra não definida");

        ColetaPrecoDTO coletaPrecoBean = new ColetaPrecoDTO();
        coletaPrecoBean = (ColetaPrecoDTO) restore(coletaPrecoDto);
        
        if (coletaPrecoDto.getQuantidadeCompra().doubleValue() > coletaPrecoBean.getQuantidadeCotada().doubleValue())
            throw new LogicException("Quantidade para compra é maior que a quantidade cotada");
        
        if (coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_MELHOR_COTACAO) && (coletaPrecoDto.getQuantidadeCompra() == null || coletaPrecoDto.getQuantidadeCompra().doubleValue() == 0))
            throw new LogicException("Quantidade para compra não definida");
        
        if (coletaPrecoDto.getIdJustifResultado() == null) {
            boolean bExibeJustificativa = !coletaPrecoDto.getResultadoCalculo().equalsIgnoreCase(coletaPrecoDto.getResultadoFinal());
            if (!bExibeJustificativa)
                bExibeJustificativa = coletaPrecoDto.getQuantidadeCompra().doubleValue() != coletaPrecoBean.getQuantidadeCalculo().doubleValue();
            
            if (bExibeJustificativa)
                throw new LogicException("Justificativa não informada");
        }
        
        ColetaPrecoDao coletaPrecoDao = new ColetaPrecoDao();
        TransactionControler tc = new TransactionControlerImpl(coletaPrecoDao.getAliasDB());
        try{
            coletaPrecoDao.setTransactionControler(tc);

            /*if (coletaPrecoBean.getResultadoCalculo().equals(ColetaPrecoDTO.RESULT_EMPATE)) {
                if (coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_EMPATE))
                    throw new LogicException("Deve ser definido o vencedor do empate");
                
                
                List<ColetaPrecoDTO> colEmpate = (List<ColetaPrecoDTO>) coletaPrecoDao.findByIdItemCotacaoAndPontuacao(coletaPrecoBean.getIdItemCotacao(), coletaPrecoBean.getPontuacao());
                if (colEmpate != null) {
                    ColetaPrecoDTO coletaEmpateDto = null;
                    for (ColetaPrecoDTO coletaAux : colEmpate) {
                        if (coletaAux.getIdColetaPreco().intValue() != coletaPrecoDto.getIdColetaPreco().intValue() && coletaAux.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_EMPATE)) {
                            coletaEmpateDto = coletaAux;
                            break;
                        }
                    }
                    if (coletaEmpateDto != null) {
                        coletaEmpateDto.setResultadoFinal(ColetaPrecoDTO.RESULT_DESCLASSIFICADA);
                        if (coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_DESCLASSIFICADA))
                            coletaEmpateDto.setResultadoFinal(ColetaPrecoDTO.RESULT_MELHOR_COTACAO);
                        coletaEmpateDto.setIdRespResultado(coletaPrecoDto.getIdRespResultado());
                        coletaEmpateDto.setIdJustifResultado(coletaPrecoDto.getIdJustifResultado());
                        coletaEmpateDto.setComplemJustifResultado(coletaPrecoDto.getComplemJustifResultado());
                        coletaPrecoDao.atualizaResultadoFinal(coletaEmpateDto);
                    }
                } 
            }*/
            
            tc.start();
            
            coletaPrecoDao.atualizaResultadoFinal(coletaPrecoDto);
            new ItemCotacaoServiceEjb().validaEAtualiza(tc, coletaPrecoDto.getIdItemCotacao());
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
    }
	
}
