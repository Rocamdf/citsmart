package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "serial", "rawtypes", "unused" })
public class EnderecoDao extends CrudDaoDefaultImpl {
	public EnderecoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idEndereco", "idEndereco", true, true, false, false));
		listFields.add(new Field("logradouro", "logradouro", false, false, false, false));
		listFields.add(new Field("numero", "numero", false, false, false, false));
		listFields.add(new Field("complemento", "complemento", false, false, false, false));
		listFields.add(new Field("bairro", "bairro", false, false, false, false));
		listFields.add(new Field("idCidade", "idCidade", false, false, false, false));
		listFields.add(new Field("idPais", "idPais", false, false, false, false));
		listFields.add(new Field("cep", "cep", false, false, false, false));
		listFields.add(new Field("idUf", "idUf", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "Endereco";
	}

	public Collection list() throws Exception {
		return super.list("logradouro");
	}

	public Class getBean() {
		return EnderecoDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	
	public EnderecoDTO recuperaEnderecoCompleto(EnderecoDTO endereco) throws Exception{
		List parametro = new ArrayList();
		StringBuffer sql = getSQLRestoreAll();
		sql.append(" WHERE e.idEndereco = ? ") ;
		parametro.add(endereco.getIdEndereco());

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());

		List<EnderecoDTO> list  = this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
	
		return list.get(0);
		
	}

	public EnderecoDTO recuperaEnderecoComUnidade(Integer idEndereco) throws Exception{
		List parametro = new ArrayList();
		StringBuffer sql = getSQLRestoreEnderecoUnidade();
		sql.append(" WHERE e.idEndereco = ? ") ;
		parametro.add(idEndereco);

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());

		List colunas = getColunasRestoreAll();
		colunas.add("identificacao");

		List<EnderecoDTO> list  = this.engine.listConvertion(getBean(), lista, colunas);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	private StringBuffer getSQLRestoreAll() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT e.idEndereco, e.logradouro, e.numero, e.complemento, e.bairro, e.idCidade, e.idPais, cep, ");
		sql.append("       c.nomeCidade, uf.siglaUf");
		sql.append("  FROM endereco e ");
		sql.append("       LEFT JOIN cidades c ON c.idCidade = e.idCidade ");
		sql.append("       LEFT JOIN ufs uf on uf.idUf = c.idUf ");
		return sql;
	}

	private StringBuffer getSQLRestoreEnderecoUnidade() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT e.idEndereco, e.logradouro, e.numero, e.complemento, e.bairro, e.idCidade, e.idPais, cep, ");
		sql.append("       c.nomeCidade, uf.siglaUf, unid.nome ");
		sql.append("  FROM endereco e ");
		sql.append("      INNER JOIN unidade unid ON unid.idEndereco = e.idEndereco ");
		sql.append("       LEFT JOIN cidades c ON c.idCidade = e.idCidade ");
		sql.append("       LEFT JOIN ufs uf on uf.idUf = c.idUf ");
		return sql;
	}

	private List getColunasRestoreAll() {
		List listRetorno = new ArrayList();
		listRetorno.add("idEndereco");
		listRetorno.add("logradouro");
		listRetorno.add("numero");
		listRetorno.add("complemento");
		listRetorno.add("bairro");
		listRetorno.add("idCidade");
		listRetorno.add("idPais");
		listRetorno.add("cep");
		listRetorno.add("nomeCidade");
		listRetorno.add("siglaUf");
		return listRetorno;
	}

	public Collection<EnderecoDTO> recuperaEnderecosEntregaProduto() throws Exception {
		List parametro = new ArrayList();

		StringBuffer sql = getSQLRestoreEnderecoUnidade();
		sql.append("  WHERE unid.aceitaEntregaProduto = 'S' ");

		sql.append("ORDER BY unid.nome");

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());

		List colunas = getColunasRestoreAll();
		colunas.add("identificacao");

		return this.engine.listConvertion(getBean(), lista, colunas);
	}
}
