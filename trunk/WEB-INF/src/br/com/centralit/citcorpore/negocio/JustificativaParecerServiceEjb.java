package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
import br.com.centralit.citcorpore.integracao.JustificativaParecerDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings({"unused", "rawtypes"})
public class JustificativaParecerServiceEjb extends CrudServicePojoImpl implements JustificativaParecerService {


	private JustificativaParecerDao justificativaParecerDao;
    
    /**
     * 
     */
    private static final long serialVersionUID = 7276352361450284960L;

    @Override
    protected CrudDAO getDao() throws ServiceException {
        return new JustificativaParecerDao();
    }

    @Override
    protected void validaCreate(Object arg0) throws Exception {

    }

    @Override
    protected void validaDelete(Object arg0) throws Exception {

    }

    @Override
    protected void validaFind(Object arg0) throws Exception {

    }

    @Override
    protected void validaUpdate(Object arg0) throws Exception {

    }

    
	@Override
    public Collection listAplicaveisCotacao() throws Exception {
        return new JustificativaParecerDao().listAplicaveisCotacao();
    }

    @Override
    public Collection listAplicaveisRequisicao() throws Exception {
        return new JustificativaParecerDao().listAplicaveisRequisicao();
    }
    
    @Override
    public Collection listAplicaveisInspecao() throws Exception {
        return new JustificativaParecerDao().listAplicaveisInspecao();
    }

	@Override
	public boolean consultarJustificativaAtiva(JustificativaParecerDTO justificativaParecerDto) throws Exception {
		
		return  new JustificativaParecerDao().consultarJustificativaAtiva(justificativaParecerDto);
	}

	@Override
	public Collection listAplicaveisRequisicaoViagem() throws Exception {
		return new JustificativaParecerDao().listAplicaveisRequisicaoViagem();
	}
}
