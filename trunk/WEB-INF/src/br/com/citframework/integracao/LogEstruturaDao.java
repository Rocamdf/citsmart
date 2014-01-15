package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.LogEstrutura;
import br.com.citframework.dto.Usuario;
import br.com.citframework.util.Constantes;

public class LogEstruturaDao extends CrudDaoDefaultImpl{


	private static final long serialVersionUID = 1L;

	public LogEstruturaDao(Usuario usuario) {
		super(Constantes.getValue("CONEXAO_DEFAUT"), usuario);
	
	}

	public Collection find(IDto dto) throws Exception {
		
		return null;
	}

	public Collection getFields() {
		List lista = new ArrayList();
		
		lista.add(new Field("logTabela_idlog","logTabela_idlog", false, false, false, true));
		lista.add(new Field("estrutura","estrutura", false, false, false, false));
		
		return lista;
	}

	public String getTableName() {
		
		return "logestrutura";
	}

	public Collection list() throws Exception {
		
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("logTabela_idlog"));
		
		return list(ordenacao);
	}

	public Class getBean() {
		
		return LogEstrutura.class;
	}

}
