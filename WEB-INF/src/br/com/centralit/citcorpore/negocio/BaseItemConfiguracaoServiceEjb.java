/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.BaseItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.BaseItemConfiguracaoDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * Service EJB de BaseItemConfiguracao.
 * 
 * @author valdoilo.damasceno
 * 
 */
public class BaseItemConfiguracaoServiceEjb extends CrudServicePojoImpl implements BaseItemConfiguracaoService {

    private static final long serialVersionUID = 8071416825521791780L;

    /** Bean de BaseItemConfiguracao. */
    //private BaseItemConfiguracaoDTO baseItemConfiguracaoBean;

    /** DAO de Valor. */
    //private ValorDao valorDao = new ValorDao();

    /** Controler de Transaction. */
    // private TransactionControler transactionControler;
    
    @Override
    public IDto[] create(BaseItemConfiguracaoDTO[] baseItemConfiguracao) throws ServiceException, LogicException {
    	BaseItemConfiguracaoDAO dao = new BaseItemConfiguracaoDAO();
    	
    	TransactionControler transactionControler = new TransactionControlerImpl(dao.getAliasDB());
	    try {
	    	
	    	dao.setTransactionControler(transactionControler);

		    transactionControler.start();
		    
		    baseItemConfiguracao[0].setDataInicio(UtilDatas.getDataAtual());
		    baseItemConfiguracao[0] = (BaseItemConfiguracaoDTO) dao.create(baseItemConfiguracao[0]);
		    
		    for (int i = 1; i < baseItemConfiguracao.length; i++) {
		    	if (baseItemConfiguracao[i] != null) {
			    	baseItemConfiguracao[i].setIdBaseItemConfiguracaoPai(baseItemConfiguracao[0].getId());
				    baseItemConfiguracao[i] = (BaseItemConfiguracaoDTO) dao.create(baseItemConfiguracao[i]);
		    	}
		    }

		    transactionControler.commit();
		    transactionControler.close();
		} catch (Exception e) {
			e.printStackTrace();
		    this.rollbackTransaction(transactionControler, e);
		}
	    
	    return baseItemConfiguracao;
    }

	@Override
	public void update(BaseItemConfiguracaoDTO[] vetorBaseItemConfiguracao)
			throws ServiceException, LogicException {
		
		BaseItemConfiguracaoDAO dao = new BaseItemConfiguracaoDAO();
    	
    	TransactionControler transactionControler = new TransactionControlerImpl(dao.getAliasDB());
	    try {
	    	
	    	dao.setTransactionControler(transactionControler);

		    transactionControler.start();
		    
		    List<IDto> filhos = this.restoreChildren(vetorBaseItemConfiguracao[0]);
		    
		    dao.update(vetorBaseItemConfiguracao[0]);
		    
		    for (int i = 1; i < vetorBaseItemConfiguracao.length; i++) {
		    	if (vetorBaseItemConfiguracao[i] != null) {
		    		vetorBaseItemConfiguracao[i].setIdBaseItemConfiguracaoPai(vetorBaseItemConfiguracao[0].getId());
		    		for (IDto iDto : filhos) {
		    			BaseItemConfiguracaoDTO dto = (BaseItemConfiguracaoDTO) iDto;
		    			if (dto.getTipoexecucao().equals(vetorBaseItemConfiguracao[i].getTipoexecucao())) {
		    				vetorBaseItemConfiguracao[i].setId(dto.getId());
		    				break;
		    			}
					}
		    		if (vetorBaseItemConfiguracao[i].getId() != null) {
		    			dao.update(vetorBaseItemConfiguracao[i]);
		    		} else {
		    			dao.create(vetorBaseItemConfiguracao[i]);
		    		}
		    	}
		    }

		    transactionControler.commit();
		    transactionControler.close();
		} catch (Exception e) {
			e.printStackTrace();
		    this.rollbackTransaction(transactionControler, e);
		}
	}

    /*@Override
    public IDto create(BaseItemConfiguracaoDTO baseItemConfiguracaoBean, HttpServletRequest request) throws ServiceException, LogicException {
	this.setBaseItemConfiguracaoBean(baseItemConfiguracaoBean);

	TransactionControler transactionControler = new TransactionControlerImpl(this.getBaseItemConfiguracaoDao().getAliasDB());

	try {
	    this.validaCreate(this.getBaseItemConfiguracaoBean());
	    this.getBaseItemConfiguracaoDao().setTransactionControler(transactionControler);
	    this.getValorDao().setTransactionControler(transactionControler);

	    transactionControler.start();

	    this.getBaseItemConfiguracaoBean().setDataInicio(UtilDatas.getDataAtual());
	    this.setBaseItemConfiguracaoBean((BaseItemConfiguracaoDTO) this.getBaseItemConfiguracaoDao().create(this.getBaseItemConfiguracaoBean()));

	    this.criarEAssociarValorDaCaracteristicaAoItemConfiguracao();

	    transactionControler.commit();
	    transactionControler.close();
	} catch (Exception e) {
	    e.printStackTrace();
	    this.rollbackTransaction(transactionControler, e);
	}
	return this.getBaseItemConfiguracaoBean();
    }*/
    
    @Override
    public List<IDto> restoreChildren(BaseItemConfiguracaoDTO baseItemConfiguracaoBean) throws Exception {
    	return this.getBaseItemConfiguracaoDao().restoreFilhos(baseItemConfiguracaoBean);
    }

    /*@Override
    public void update(IDto baseItemConfiguracao) throws ServiceException, LogicException {
	this.setBaseItemConfiguracaoBean(baseItemConfiguracao);

	try {
		this.getBaseItemConfiguracaoBean().setTipoexecucao("I");
	    this.criarEAssociarValorDaCaracteristicaAoItemConfiguracao();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	super.update(baseItemConfiguracao);
    }*/

    /**
     * Cria ou Atualiza associação do Valor da Característica ao Ítem de Configuração.
     * 
     * @throws Exception
     * @author valdoilo.damasceno
     */
    /*private void criarEAssociarValorDaCaracteristicaAoItemConfiguracao() throws Exception {
    	if(this.getBaseItemConfiguracaoBean().getTipoItemConfiguracao() != null){
			if (this.getBaseItemConfiguracaoBean().getTipoItemConfiguracao().getCaracteristicas() != null
				&& !this.getBaseItemConfiguracaoBean().getTipoItemConfiguracao().getCaracteristicas().isEmpty()) {
			    for (int i = 0; i < this.getBaseItemConfiguracaoBean().getTipoItemConfiguracao().getCaracteristicas().size(); i++) {
				ValorDTO valor = this.getValorService().restore(this.getBaseItemConfiguracaoBean().getId(),
					this.getCaracteristicaBean(i).getIdCaracteristica());
		
				if (valor != null) {
				    valor.setValorStr(this.getCaracteristicaBean(i).getValorString());
				    valor.setValorLongo(this.getCaracteristicaBean(i).getValorString());
		
				    this.getValorService().update(valor);
				} else {
				    valor = new ValorDTO();
				    valor.setIdBaseItemConfiguracao(this.getBaseItemConfiguracaoBean().getId());
				    valor.setIdCaracteristica(this.getCaracteristicaBean(i).getIdCaracteristica());
				    valor.setValorStr(this.getCaracteristicaBean(i).getValorString());
				    valor.setValorLongo(this.getCaracteristicaBean(i).getValorString());
		
				    this.getValorDao().create(valor);
				}
			    }
			}
    	}
    }*/


    /**
     * Cria nova instância de TransactionController.
     * 
     * @author valdoilo.damasceno
     * @throws ServiceException
     * @author valdoilo.damasceno
     */
    // public void setTransactionControler() throws ServiceException {
    // this.transactionControler = new TransactionControlerImpl(this.getBaseItemConfiguracaoDao().getAliasDB());
    // }
    //
    // /**
    // * Retorna TransactionControler.
    // *
    // * @return TransactionControler
    // * @author valdoilo.damasceno
    // */
    // public TransactionControler getTransactionControler() {
    // return this.transactionControler;
    // }


    /**
     * Retorna Bean de BaseItemConfiguracao.
     * 
     * @return valor do atributo baseItemConfiguracaoBean.
     * @author valdoilo.damasceno
     */
    /*public BaseItemConfiguracaoDTO getBaseItemConfiguracaoBean() {
	return baseItemConfiguracaoBean;
    }*/

    /**
     * Retorna DAO de Valor.
     * 
     * @return valor do atributo valorDao.
     * @author valdoilo.damasceno
     */
    /*public ValorDao getValorDao() {
	return valorDao;
    }*/

    /**
     * Retorna DAO de BaseItemConfiguracao.
     * 
     * @return BaseItemConfiguracaoDAO
     * @throws ServiceException
     * @author valdoilo.damasceno
     */
    public BaseItemConfiguracaoDAO getBaseItemConfiguracaoDao() throws ServiceException {
    	return (BaseItemConfiguracaoDAO) this.getDao();
    }

    /**
     * Retorna Service de Valor.
     * 
     * @return ValorService
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public ValorService getValorService() throws ServiceException, Exception {
	return (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
    }

    @Override
    protected CrudDAO getDao() throws ServiceException {
	return new BaseItemConfiguracaoDAO();
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
	public boolean existBaseItemConfiguracao(BaseItemConfiguracaoDTO dto) throws Exception {
		return ((BaseItemConfiguracaoDAO) getDao()).existBaseItemConfiguracao(dto);
	}
    
    
    }


