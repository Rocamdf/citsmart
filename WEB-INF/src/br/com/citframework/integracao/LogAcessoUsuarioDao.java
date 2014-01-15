/**
 * 
 */
package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.LogAcessoUsuario;
import br.com.citframework.dto.Usuario;
import br.com.citframework.util.Constantes;

/**
 * @author karem.ricarte
 *
 */
public class LogAcessoUsuarioDao extends CrudDaoDefaultImpl{


	private static final long serialVersionUID = 1L;

	public LogAcessoUsuarioDao(Usuario usuario) {
		super(Constantes.getValue("CONEXAO_DEFAUT"), usuario);
		
	}

	public Collection find(IDto dto) throws Exception {
		
		return null;
	}

	public Collection getFields() {
		List lista = new ArrayList();
		
		lista.add(new Field("dtAcessoUsuario","dtAcessoUsuario", false, false, false, false));
		lista.add(new Field("HistAtualUsuario_idUsuario","HistAtualUsuario_idUsuario", false, false, false, false));
		lista.add(new Field("login", "login", false, false, false, false));
		
		return lista;
	}

	public String getTableName() {
		
		return "logacessousuario";
	}

	public Collection list() throws Exception {
		List ordenacao = new ArrayList();
		
		ordenacao.add(new Order("dtAcessoUsuario"));
		
		return super.list(ordenacao);
	}

	public Class getBean() {
		
		return LogAcessoUsuario.class;
	}

}
