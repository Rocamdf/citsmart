package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ModuloSistemaDTO;
import br.com.centralit.citcorpore.bean.SistemaOperacionalDTO;
import br.com.centralit.citcorpore.bean.TreinamentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author Pedro
 *
 */
@SuppressWarnings("serial")
public class TreinamentoDao extends CrudDaoDefaultImpl {

    public TreinamentoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @SuppressWarnings("rawtypes")
    public Class getBean() {
	return TreinamentoDTO.class;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection getFields() {
	Collection listFields = new ArrayList();

	listFields.add(new Field("idtreinamento", "idTreinamento", true, true, false, false));
	listFields.add(new Field("desctreinamento", "descTreinamento", false, false, false, false));
	listFields.add(new Field("datatreinamento", "dataTreinamento", false, false, false, false));
	listFields.add(new Field("idinstrutortreinamento", "idInstrutorTreinamento", false, false, false, false));

	return listFields;
    }

    public String getTableName() {
	return "TREINAMENTO";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection find(IDto obj) throws Exception {
	List ordem = new ArrayList();
	ordem.add(new Order("descTreinamento"));
	return super.find(obj, ordem);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection list() throws Exception {
	List list = new ArrayList();
	list.add(new Order("descTreinamento"));
	return super.list(list);
    }

}
