package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ContatoRequisicaoMudancaDao extends CrudDaoDefaultImpl{

    public ContatoRequisicaoMudancaDao() {
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

	listFields.add(new Field("idcontatorequisicaomudanca", "idcontatorequisicaomudanca", true, true, false, false));
	listFields.add(new Field("nomecontato", "nomecontato", false, false, false, false));
	listFields.add(new Field("telefonecontato", "telefonecontato", false, false, false, false));
	listFields.add(new Field("emailcontato", "emailcontato", false, false, false, false));
	listFields.add(new Field("observacao", "observacao", false, false, false, false));
	listFields.add(new Field("idlocalidade", "idLocalidade", false, false, false, false));
	listFields.add(new Field("ramal", "ramal", false, false, false, false));

	return listFields;
    }

    @Override
    public String getTableName() {
	
	return "contatorequisicaomudanca";
    }

    @Override
    public Collection list() throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Class getBean() {
	return ContatoRequisicaoMudancaDTO.class;
    }
    

}
