package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

@SuppressWarnings({"rawtypes", "unchecked"})
public class FornecedorDao extends CrudDaoDefaultImpl {
	
	private static final long serialVersionUID = 8392982446544184230L;
	
	public FornecedorDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idfornecedor", "idFornecedor", true, true, false, false) );
		listFields.add(new Field("razaosocial", "razaoSocial", false, false, false, false) );
		listFields.add(new Field("nomefantasia", "nomeFantasia", false, false, false, false) );
		listFields.add(new Field("cnpj", "cnpj", false, false, false, false) );
		listFields.add(new Field("email", "email", false, false, false, false) );
		listFields.add(new Field("observacao", "observacao", false, false, false, false) );
		listFields.add(new Field("deleted", "deleted", false, false, false, false) );
        listFields.add(new Field("telefone", "telefone", false, false, false, false) );
        listFields.add(new Field("fax", "fax", false, false, false, false) );
        listFields.add(new Field("idendereco", "idEndereco", false, false, false, false) );
        listFields.add(new Field("nomecontato", "nomeContato", false, false, false, false) );
        listFields.add(new Field("inscricaomunicipal", "inscricaoMunicipal", false, false, false, false) );
        listFields.add(new Field("inscricaoestadual", "inscricaoEstadual", false, false, false, false) );
        listFields.add(new Field("tipopessoa", "tipoPessoa", false, false, false, false) );        
        
		return listFields;
	}
	
	public String getTableName() {
		return this.getOwner() + "Fornecedor";
	}
	
	public Collection list() throws Exception {
		String sql = "SELECT " + this.getNamesFieldsStr() + " FROM " + this.getTableName() + " WHERE ";		
		 if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) 
			sql += "UPPER(deleted) IS NULL OR UPPER(deleted) = 'N' ";
		 else 
			sql += "deleted IS NULL OR deleted = 'N' ";
		sql += "ORDER BY razaoSocial ";
			
		List lst = this.execSQL(sql, null);
		
		return this.listConvertion(this.getBean(), lst, this.getListNamesFieldClass() );
	}

	public Class getBean() {
		return FornecedorDTO.class;
	}
	
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	
	/**
	 * Retorna uma lista de escopo de fornecimento de acordo com o fornecedor passado
	 * 
	 * @param fornecedorProdutoDto
	 * @return Collection<FornecedorProdutoDTO>
	 * @throws Exception
	 * @author Thays.araujo
	 * 
	 */
	public Collection<FornecedorDTO> listEscopoFornecimento(FornecedorDTO fornecedorDto) throws Exception {

		List parametro = new ArrayList();
		List listaRetornor = new ArrayList();
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();

		sql.append("select fornecedor.nomeFantasia, nomeProduto, nomeMarca ");
		sql.append("from fornecedorproduto ");
		sql.append("inner join tipoproduto tipo on tipo.idTipoProduto = fornecedorproduto.idTipoProduto ");
		sql.append("inner join fornecedor fornecedor on fornecedor.idFornecedor = fornecedorproduto.idFornecedor ");
		sql.append(" left join marca marca on marca.idMarca = fornecedorproduto.idMarca ");
		if (fornecedorDto.getIdFornecedor() != null) {
			sql.append("where fornecedor.idFornecedor = ? ");
			parametro.add(fornecedorDto.getIdFornecedor());
		}

		list = this.execSQL(sql.toString(), parametro.toArray());

		listaRetornor.add("nomeFantasia");
		listaRetornor.add("nomeProduto");
		listaRetornor.add("marca");

		if (list != null && !list.isEmpty()) {

			Collection<FornecedorDTO> listEscopoFornecimento = this.listConvertion(FornecedorDTO.class, list, listaRetornor);

			return listEscopoFornecimento;

		}

		return null;
	}
	
	public boolean consultarCargosAtivos(FornecedorDTO fornecedor) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idfornecedor From " + getTableName() + "  where  razaosocial = ?   and deleted is null ";
		
		if(fornecedor.getIdFornecedor() != null){
			sql+=" and idfornecedor <> "+ fornecedor.getIdFornecedor();
		}
		
		parametro.add(fornecedor.getRazaoSocial());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean excluirFornecedor(FornecedorDTO fornecedor) throws Exception {
		try {
			execUpdate("delete from fornecedor where idfornecedor = " + fornecedor.getIdFornecedor(), null);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Encontra o Fornecedor pelo ID
	 * @author euler.ramos
	 * @throws Exception 
	 */
	public List<FornecedorDTO> findByIdFornecedor(Integer id) throws Exception {
		List resp = new ArrayList();

		Collection fields = getFields();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		String campos = "";
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (!campos.trim().equalsIgnoreCase("")) {
				campos = campos + ",";
			}
			campos = campos + field.getFieldDB();
			listRetorno.add(field.getFieldClass());
		}

		String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE idfornecedor=? and ((deleted is NULL)OR(deleted<>'y')) ORDER BY idfornecedor";
		parametro.add(id);
		resp = this.execSQL(sql, parametro.toArray());
		
		List result = this.engine.listConvertion(getBean(), resp, listRetorno);
		return (result == null ? new ArrayList<FornecedorDTO>() : result);
	}

	/**
	 * Encontra o Fornecedor pela Razão Social
	 * @author euler.ramos
	 * @throws Exception 
	 */
	public List<FornecedorDTO> findByRazaoSocial(String razaoSocial) throws Exception {
		List resp = new ArrayList();

		Collection fields = getFields();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		String campos = "";
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (!campos.trim().equalsIgnoreCase("")) {
				campos = campos + ",";
			}
			campos = campos + field.getFieldDB();
			listRetorno.add(field.getFieldClass());
		}

		String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE razaosocial=? and ((deleted is NULL)OR(deleted<>'y')) ORDER BY razaosocial";
		parametro.add(razaoSocial);
		resp = this.execSQL(sql, parametro.toArray());
		
		List result = this.engine.listConvertion(getBean(), resp, listRetorno);
		return (result == null ? new ArrayList<FornecedorDTO>() : result);
	}
	
}
