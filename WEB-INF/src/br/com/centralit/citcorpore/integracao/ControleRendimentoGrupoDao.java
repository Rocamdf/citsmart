package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleRendimentoGrupoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class ControleRendimentoGrupoDao extends CrudDaoDefaultImpl{

	public ControleRendimentoGrupoDao(String aliasDB, Usuario usuario) {
		super(aliasDB, usuario);
		// TODO Auto-generated constructor stub
	}

	public ControleRendimentoGrupoDao(){
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

		listFields.add(new Field("idControleRendimentoGrupo", "idControleRendimentoGrupo", true, true, false, true));
		listFields.add(new Field("idControleRendimento", "idControleRendimento", false, false, false, false));
		listFields.add(new Field("idGrupo", "idGrupo", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "controlerendimentogrupo";
	}

	@Override
	public Collection list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getBean() {
		return ControleRendimentoGrupoDTO.class;
	}

}
