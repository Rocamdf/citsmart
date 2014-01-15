package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.MidiaSoftwareDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.InvalidTransactionControler;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MidiaSoftwareDAO extends CrudDaoDefaultImpl {

	public MidiaSoftwareDAO(){

		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2569631881714593678L;

	@Override
	public Collection find(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getFields() {
		
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("IDMIDIASOFTWARE", "IdMidiaSoftware", true, true, false, false));
		listFields.add(new Field("NOME", "nome", false, false, false, false));
		listFields.add(new Field("ENDFISICO", "endFisico", false, false, false, false));
		listFields.add(new Field("ENDLOGICO", "endLogico", false, false, false, false));
		listFields.add(new Field("LICENCAS", "licencas", false, false, false, false));
		listFields.add(new Field("IDMIDIA", "idMidia", false, false, false, false));
		listFields.add(new Field("IDTIPOSOFTWARE", "idTipoSoftware", false, false, false, false));
		listFields.add(new Field("VERSAO", "versao", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "MIDIASOFTWARE";
	}

	@Override
	public Collection list() throws Exception{
		List list = new ArrayList();
		list.add(new Order("nome"));
		return super.list(list);
	    }


	@Override
	public Class getBean() {
		return MidiaSoftwareDTO.class;
	}
	
	public boolean consultarMidiasAtivas(MidiaSoftwareDTO obj) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idmidiasoftware From " + getTableName() + "  where  nome = ?   and dataFim is null ";
		
		if(obj.getIdMidiaSoftware() != null){
			sql+=" and idmidiasoftware <> "+ obj.getIdMidiaSoftware();
		}
		
		parametro.add(obj.getNome());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

}
