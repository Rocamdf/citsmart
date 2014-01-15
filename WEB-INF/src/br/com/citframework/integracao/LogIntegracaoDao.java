package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.IntegracaoSistemasExternosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class LogIntegracaoDao extends CrudDaoDefaultImpl {
	public LogIntegracaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idLogIntegracao" ,"idLogIntegracao", true, true, false, false));
		listFields.add(new Field("idIntegracao" ,"idIntegracao", false, false, false, false));
		listFields.add(new Field("dataHora" ,"dataHora", false, false, false, false));
		listFields.add(new Field("resultado" ,"resultado", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "logintegracao";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return IntegracaoSistemasExternosDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
}
