package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.OcorrenciaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.OcorrenciaLiberacaoDao;
import br.com.centralit.citcorpore.integracao.OcorrenciaMudancaDao;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;

/**
 * @author breno.guimaraes
 *
 */
public class OcorrenciaLiberacaoServiceEjb extends CrudServicePojoImpl implements OcorrenciaLiberacaoService {

    private OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao;
    
    /**
     * 
     */
    private static final long serialVersionUID = 7276352361450284960L;

    @Override
    protected CrudDAO getDao() throws ServiceException {
	if(ocorrenciaLiberacaoDao == null){
		ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
	}
	return ocorrenciaLiberacaoDao;
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
    
    public Collection findByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws Exception {
	OcorrenciaLiberacaoDao ocorrenciaLiberacaoDao = new OcorrenciaLiberacaoDao();
	return ocorrenciaLiberacaoDao.findByIdRequisicaoLiberacao(idRequisicaoLiberacao);
    }

    
    
    
}
