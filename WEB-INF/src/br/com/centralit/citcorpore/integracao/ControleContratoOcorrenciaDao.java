package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ControleContratoDTO;
import br.com.centralit.citcorpore.bean.ControleContratoOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ControleContratoTreinamentoDTO;
import br.com.centralit.citcorpore.bean.ModuloSistemaDTO;
import br.com.centralit.citcorpore.bean.SistemaOperacionalDTO;
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
public class ControleContratoOcorrenciaDao extends CrudDaoDefaultImpl {

    public ControleContratoOcorrenciaDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @SuppressWarnings("rawtypes")
    public Class getBean() {
	return ControleContratoOcorrenciaDTO.class;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection getFields() {
	Collection listFields = new ArrayList();

	listFields.add(new Field("idccocorrencia", "idCcOcorrencia", true, true, false, false));
	listFields.add(new Field("assuntoccocorrencia", "assuntoCcOcorrencia", false, false, false, false));
	listFields.add(new Field("idempregadoocorrencia", "idEmpregadoOcorrencia", false, false, false, false));
	listFields.add(new Field("idcontrolecontrato", "idControleContrato", false, false, false, false));
	listFields.add(new Field("dataccocorrencia", "dataCcOcorrencia", false, false, false, false));
	

	return listFields;
    }

    public String getTableName() {
	return "CONTROLECONTRATOOCORRENCIA";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection find(IDto obj) throws Exception {
	List ordem = new ArrayList();
	return super.find(obj, ordem);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection list() throws Exception {
	List list = new ArrayList();
	return super.list(list);
    }

    private static final String SQL_DELETE = 
	          "DELETE FROM CONTROLECONTRATOOCORRENCIA WHERE idcontrolecontrato = ? ";

	public void deleteByIdControleContrato(ControleContratoDTO controleContrato)
		    throws Exception{
		        super.execUpdate(SQL_DELETE, new Object[]{controleContrato.getIdControleContrato()});
		    }
	
	 private static final String SQL_FIND = 
		      "SELECT * FROM CONTROLECONTRATOOCORRENCIA WHERE idcontrolecontrato = ? ";

	public Collection findByIdControleContrato(ControleContratoOcorrenciaDTO dto) throws Exception {
     return super.listConvertion(getBean(), 
             super.execSQL(SQL_FIND, new Object[]{dto.getIdControleContrato()}), 
             new ArrayList(getFields()));
	}
    
}
