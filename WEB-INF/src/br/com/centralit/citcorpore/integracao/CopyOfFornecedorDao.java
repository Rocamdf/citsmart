package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class CopyOfFornecedorDao extends CrudDaoDefaultImpl {
	public CopyOfFornecedorDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idFornecedor" ,"idFornecedor", true, true, false, false));
		listFields.add(new Field("razaoSocial" ,"razaoSocial", false, false, false, false));
		listFields.add(new Field("nomeFantasia" ,"nomeFantasia", false, false, false, false));
		listFields.add(new Field("cnpj" ,"cnpj", false, false, false, false));
		listFields.add(new Field("email" ,"email", false, false, false, false));
		listFields.add(new Field("observacao" ,"observacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Fornecedor";
	}
	public Collection list() throws Exception {
		return super.list("razaoSocial");
	}

	public Class getBean() {
		return FornecedorDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
}
