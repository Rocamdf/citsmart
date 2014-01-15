/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.SituacaoDemandaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SituacaoDemandaDao extends CrudDaoDefaultImpl {

    private static final long serialVersionUID = -2983316142102074344L;

    public SituacaoDemandaDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public Class getBean() {
	return SituacaoDemandaDTO.class;
    }

    public Collection getFields() {
	Collection listFields = new ArrayList();

	listFields.add(new Field("IDSITUACAODEMANDA", "idSituacaoDemanda", true, false, false, true));
	listFields.add(new Field("NOMESITUACAOSERVICO", "nomeSituacao", false, false, false, false));

	return listFields;
    }

    public String getTableName() {
	return "SITUACAODEMANDA";
    }

    public Collection find(IDto obj) throws Exception {
	return null;
    }

    public Collection list() throws Exception {
	List list = new ArrayList();
	list.add(new Order("nomeSituacao"));
	return super.list(list);
    }

}
