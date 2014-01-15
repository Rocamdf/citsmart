package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.integracao.JustificativaSolicitacaoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class JustificativaSolicitacaoServiceEjb extends CrudServicePojoImpl implements JustificativaSolicitacaoService {

    private JustificativaSolicitacaoDao justificativaSolicitacaoDao;
    
    /**
     * 
     */
    private static final long serialVersionUID = 7276352361450284960L;

    @Override
    protected CrudDAO getDao() throws ServiceException {
	if(justificativaSolicitacaoDao == null){
		justificativaSolicitacaoDao = new JustificativaSolicitacaoDao();
	}
	return justificativaSolicitacaoDao;
    }
    
    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        return super.create(model);
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
	public Collection<JustificativaSolicitacaoDTO> listAtivasParaSuspensao() throws Exception {
		return new JustificativaSolicitacaoDao().listAtivasParaSuspensao();
	}
    
   
	@Override
    public Collection<JustificativaSolicitacaoDTO> listAtivasParaAprovacao() throws Exception {
        return new JustificativaSolicitacaoDao().listAtivasParaAprovacao();
    }

	@Override
	public Collection listAtivasParaViagem() throws Exception {
		return new JustificativaSolicitacaoDao().listAtivasParaViagem();
	}

}
