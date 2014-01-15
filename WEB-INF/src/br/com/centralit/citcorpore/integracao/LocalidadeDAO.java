package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class LocalidadeDAO extends CrudDaoDefaultImpl {

	public LocalidadeDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Collection find(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("idlocalidade", "idLocalidade", true, true, false, false));
		listFields.add(new Field("nomelocalidade", "nomeLocalidade", false, false, false, false));
		listFields.add(new Field("datainicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("datafim", "dataFim", false, false, false, false));

		return listFields;
	}
	
	/**
	 * Retorna lista de status de usuário.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean verificarLocalidadeAtiva(LocalidadeDTO obj) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idlocalidade From " + getTableName() + "  where  nomelocalidade = ?   and dataFim is null ";
		
		if(obj.getIdLocalidade() != null){
			sql+=" and idlocalidade <> "+ obj.getIdLocalidade();
		}
		
		parametro.add(obj.getNomeLocalidade());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "localidade";
	}

	@Override
	public Collection list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getBean() {
		// TODO Auto-generated method stub
		return LocalidadeDTO.class;
	}
	
	 /**
	* Retorna lista de localidades
	*
	* @param
	* @return Collection
	* @throws Exception
	* @author thays.araujo
	*/
	public Collection<LocalidadeDTO> listLocalidade() throws Exception {
	List listRetorno = new ArrayList();
	List list = new ArrayList();
	String sql = "select idlocalidade From " + getTableName() + " where dataFim is null ";

	list = this.execSQL(sql, null);

	listRetorno.add("idLocalidade");

	if (list != null && !list.isEmpty()) {

	Collection<LocalidadeDTO> listLocalidade = this.listConvertion(LocalidadeDTO.class, list, listRetorno);
	return listLocalidade;

	} else {
	return null;
	}
	}


}
