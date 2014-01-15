package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.centralit.citquestionario.integracao.QuestaoQuestionarioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;


public class QuestaoQuestionarioServiceBean extends CrudServicePojoImpl implements QuestaoQuestionarioService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new QuestaoQuestionarioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
  	public Collection listByIdGrupoQuestionario(Integer idGrupoQuestionario) throws Exception {
		QuestaoQuestionarioDao dao = new QuestaoQuestionarioDao();
		return dao.listByIdGrupoQuestionario(idGrupoQuestionario);
	}
  	public Collection listByIdGrupoQuestionarioComAgrupadoras(Integer idGrupoQuestionario) throws Exception {
  		QuestaoQuestionarioDao dao = new QuestaoQuestionarioDao();
  		return dao.listByIdGrupoQuestionarioComAgrupadoras(idGrupoQuestionario);
  	}
    public Collection listByIdQuestaoAgrupadora(Integer idQuestaoAgrupadora) throws Exception {
        QuestaoQuestionarioDao dao = new QuestaoQuestionarioDao();
        return dao.listByIdQuestaoAgrupadora(idQuestaoAgrupadora);
    }
    public Collection listCabecalhosLinha(Integer idQuestaoAgrupadora) throws Exception {
        QuestaoQuestionarioDao dao = new QuestaoQuestionarioDao();
        return dao.listCabecalhosLinha(idQuestaoAgrupadora);
    }    
    public Collection listCabecalhosColuna(Integer idQuestaoAgrupadora) throws Exception {
        QuestaoQuestionarioDao dao = new QuestaoQuestionarioDao();
        return dao.listCabecalhosColuna(idQuestaoAgrupadora); 
    }
    public QuestaoQuestionarioDTO findBySiglaAndIdQuestionario(String sigla, Integer idQuestionario) throws Exception {
        QuestaoQuestionarioDao dao = new QuestaoQuestionarioDao();
        return dao.findBySiglaAndIdQuestionario(sigla, idQuestionario); 
    }
    public Collection listByTipoQuestaoAndIdQuestionario(String tipoQuestao, Integer idQuestionario) throws Exception {
        QuestaoQuestionarioDao dao = new QuestaoQuestionarioDao();
        return dao.listByTipoQuestaoAndIdQuestionario(tipoQuestao, idQuestionario); 
    }
    public Collection listByTipoAndIdQuestionario(String tipo, Integer idQuestionario) throws Exception {
        QuestaoQuestionarioDao dao = new QuestaoQuestionarioDao();
        return dao.listByTipoAndIdQuestionario(tipo, idQuestionario); 
    }
    public Collection listByIdQuestaoAndContrato(Integer idQuestao, Integer idContrato) throws Exception {
        QuestaoQuestionarioDao dao = new QuestaoQuestionarioDao();
        return dao.listByIdQuestaoAndContrato(idQuestao, idContrato);
    }    
    public Collection listByIdQuestaoAndContratoOrderDataASC(Integer idQuestao, Integer idContrato) throws Exception {
    	QuestaoQuestionarioDao dao = new QuestaoQuestionarioDao();
    	return dao.listByIdQuestaoAndContratoOrderDataASC(idQuestao, idContrato);
    }    
}
