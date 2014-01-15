package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.DicionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DicionarioDao extends CrudDaoDefaultImpl {

	public DicionarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
		// TODO Auto-generated constructor stub
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

		listFields.add(new Field("iddicionario", "idDicionario", true, true, false, false));
		listFields.add(new Field("idlingua", "idLingua", false, false, false, false));
		listFields.add(new Field("nome", "nome", false, false, false, false));
		listFields.add(new Field("valor", "valor", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "DICIONARIO";
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		List listaRetorno = new ArrayList();
		listaRetorno.add("iddicionario");
		listaRetorno.add("idlingua");
		listaRetorno.add("nome");	
		listaRetorno.add("valor");	
		listaRetorno.add("sigla");	
		String sql = "SELECT d.iddicionario, d.idlingua, d.nome, d.valor, l.sigla FROM " + getTableName() + " d JOIN LINGUA l ON l.idlingua = d.idlingua ORDER BY d.idlingua";
		list = this.execSQL(sql.toString(), null);
		List<DicionarioDTO> listaDicionario = this.listConvertion(DicionarioDTO.class, list, listaRetorno);
		return listaDicionario;
	}

	@Override
	public Class getBean() {
		// TODO Auto-generated method stub
		return DicionarioDTO.class;
	}

	public DicionarioDTO verificarDicionarioAtivoByKey(DicionarioDTO obj) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();

		sql.append("select nome, valor from " + getTableName() + "  ");

		if (obj.getNome() != null) {
			sql.append("where  nome = ? ");
			parametro.add(obj.getNome());
		}

		if (obj.getIdLingua() != null) {
			sql.append("and idlingua = ?");
			parametro.add(obj.getIdLingua());
		}

		list = this.execSQL(sql.toString(), parametro.toArray());
		List listaRetorno = new ArrayList();
		listaRetorno.add("nome");
		listaRetorno.add("valor");
		List<DicionarioDTO> listaDicionario = this.listConvertion(DicionarioDTO.class, list, listaRetorno);

		if (listaDicionario != null && listaDicionario.size() > 0) {
			return listaDicionario.get(0);
		}

		return null;

	}

	/**
	 * 
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public Integer verificarDicionarioAtivo(DicionarioDTO obj) throws Exception {
		List parametro = new ArrayList();

		List list = new ArrayList();

		StringBuffer sql = new StringBuffer();

		sql.append("select iddicionario from " + getTableName() + "  where   nome = ?");

		if (obj.getIdDicionario() != null) {
			sql.append(" and iddicionario <> " + obj.getIdDicionario());
		}

		if (obj.getIdLingua() != null) {
			sql.append(" and idlingua = " + obj.getIdLingua());
		}

		List listaRetorno = new ArrayList();
		listaRetorno.add("idDicionario");

		parametro.add(obj.getNome());

		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			Collection<DicionarioDTO> listaDicionario = this.listConvertion(DicionarioDTO.class, list, listaRetorno);
			if (listaDicionario != null || listaDicionario.size() != 0) {
				for (DicionarioDTO dicionario : listaDicionario) {
					return dicionario.getIdDicionario();
				}
			}

		} else {
			return null;
		}
		return null;
	}

	public Collection<DicionarioDTO> listaDicionario(DicionarioDTO dicionarioDto) throws Exception {
		List list = new ArrayList();

		List parametro = new ArrayList();

		StringBuffer sql = new StringBuffer();

		sql.append("select * from " + getTableName() + " ");

		if (dicionarioDto.getIdLingua() != null) {
			sql.append("where idlingua = ? ");
			parametro.add(dicionarioDto.getIdLingua());
		}
		sql.append(" order by nome");

		list = this.execSQL(sql.toString(), parametro.toArray());

		List listaRetorno = new ArrayList();

		listaRetorno.add("idDicionario");
		listaRetorno.add("nome");
		listaRetorno.add("valor");
		listaRetorno.add("idLingua");

		if (list != null && !list.isEmpty()) {
			Collection<DicionarioDTO> listaDicionario = this.listConvertion(DicionarioDTO.class, list, listaRetorno);
			return listaDicionario;
		} else {
			return null;
		}
	}

}
