package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ContatoRequisicaoLiberacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ContatoRequisicaoLiberacaoDao extends CrudDaoDefaultImpl{

    public ContatoRequisicaoLiberacaoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
	// TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Collection find(IDto arg0) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    
    @Override
    public Collection getFields() {
	Collection listFields = new ArrayList();

	listFields.add(new Field("idContatoRequisicaoLiberacao", "idContatoRequisicaoLiberacao", true, true, false, false));
	listFields.add(new Field("nomeContato", "nomeContato", false, false, false, false));
	listFields.add(new Field("telefoneContato", "telefoneContato", false, false, false, false));
	listFields.add(new Field("emailContato", "emailContato", false, false, false, false));
	listFields.add(new Field("observacao", "observacao", false, false, false, false));
	listFields.add(new Field("idLocalidade", "idLocalidade", false, false, false, false));
	listFields.add(new Field("ramal", "ramal", false, false, false, false));
	listFields.add(new Field("idUnidade", "idUnidade", false, false, false, false));

	return listFields;
    }

    @Override
    public String getTableName() {
	
	return "contatorequisicaoliberacao";
    }

    @Override
    public Collection list() throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Class getBean() {
	return ContatoRequisicaoLiberacaoDTO.class;
    }
    

}
