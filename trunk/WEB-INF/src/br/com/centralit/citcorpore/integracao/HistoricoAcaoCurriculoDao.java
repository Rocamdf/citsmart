package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoAcaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author ygor.magalhaes
 *
 */
@SuppressWarnings("serial")
public class HistoricoAcaoCurriculoDao extends CrudDaoDefaultImpl {

    public HistoricoAcaoCurriculoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @SuppressWarnings("rawtypes")
    public Class getBean() {
	return HistoricoAcaoCurriculoDTO.class;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection getFields() {
	Collection listFields = new ArrayList();

	listFields.add(new Field("idhistoricoacaocurriculo", "idHistoricoAcaoCurriculo", true, true, false, false));
	listFields.add(new Field("idcurriculo", "idCurriculo", false, false, false, false,""));
	listFields.add(new Field("idjustificativaacaocurriculo", "idJustificativaAcaoCurriculo", false, false, false, false, ""));
	listFields.add(new Field("idusuario", "idUsuario", false, false, false, false, ""));
	listFields.add(new Field("complementojustificativa", "complementoJustificativa", false, false, false, false,""));
	listFields.add(new Field("datahora", "dataHora", false, false, false, false,""));
	listFields.add(new Field("acao", "acao", false, false, false, false,""));

	return listFields;
    }

    public String getTableName() {
	return "rh_historicoacaocurriculo";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection find(IDto obj) throws Exception {
	List ordem = new ArrayList();
	//ordem.add(new Order("nome"));
	return super.find(obj, ordem);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection list() throws Exception {
	List list = new ArrayList();
	//list.add(new Order("nome"));
	return super.list(list);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection listByIdCurriculo(Integer idCurriculo) throws Exception {
	
	List ordenacao = new ArrayList(); 
	List condicao = new ArrayList();
	condicao.add(new Condition("idCurriculo", "=", idCurriculo)); 
	ordenacao.add(new Order("dataHora", Order.DESC));
	return super.findByCondition(condicao, ordenacao);
    }
    


}
