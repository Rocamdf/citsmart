/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;

/**
 * DAO de PerfilAcesso.
 * 
 * @author thays.araujo
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PerfilAcessoDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = -7104684492924299509L;

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("IDPERFIL", "idPerfilAcesso", true, true, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
		listFields.add(new Field("NOME", "nomePerfilAcesso", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "PERFILACESSO";
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nomePerfilAcesso"));
		return super.list(list);
	}

	public PerfilAcessoDTO listByName(PerfilAcessoDTO obj) throws Exception {
		List fields = new ArrayList();
		fields.add("idPerfilAcesso");
		fields.add("nomePerfilAcesso");
		String sql = "SELECT idPerfil, nome FROM " + getTableName() + " WHERE dataFim IS NULL AND idPerfil = ? ";
		List dados = this.execSQL(sql, new Object[] { obj.getIdPerfilAcesso() });
		List perfis = this.listConvertion(getBean(), dados, fields);

		if (perfis != null && !perfis.isEmpty()) {
			return (PerfilAcessoDTO) perfis.get(0);
		}

		return null;
	}

	/**
	 * Consulta Perfils de Acesso com DataFim = NULL.
	 * 
	 * @return perfisAtivos
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<PerfilAcessoDTO> consultarPerfisDeAcessoAtivos() throws Exception {
		List ordenacao = new ArrayList();
		List condicao = new ArrayList();
		ordenacao.add(new Order("nomePerfilAcesso"));
		condicao.add(new Condition("dataFim", "is", null));
		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * Consulta Perfis de Acesso da Pasta informada.
	 * 
	 * @param pastaBean
	 * @return perfisDeAcessoAtivos
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<PerfilAcessoDTO> consultarPerfisDeAcessoAtivos(PastaDTO pastaBean) throws Exception {
		StringBuffer sql = new StringBuffer();
		List parametro = new ArrayList();
		List retorno = new ArrayList();

		sql.append("SELECT perfilacesso.idperfil, perfilacesso.nome, perfilacessopasta.aprovaBaseConhecimento, perfilacessopasta.permiteleitura, perfilacessopasta.permiteleituragravacao ");
		sql.append("FROM perfilacessopasta ");
		sql.append("INNER JOIN perfilacesso ON perfilacessopasta.idperfil = perfilacesso.idperfil ");
		sql.append("WHERE perfilacessopasta.idpasta = ? ");

		parametro.add(pastaBean.getId());

		retorno.add("idPerfilAcesso");
		retorno.add("nomePerfilAcesso");
		retorno.add("aprovaBaseConhecimento");
		retorno.add("permiteLeitura");
		retorno.add("permiteLeituraGravacao");

		List list = this.execSQL(sql.toString(), parametro.toArray());
		return this.engine.listConvertion(this.getBean(), list, retorno);
	}

	@Override
	public Class getBean() {
		return PerfilAcessoDTO.class;
	}

	public PerfilAcessoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	@Override
	public IDto restore(IDto obj) throws Exception {
		PerfilAcessoDTO perfilAcessoDTO = (PerfilAcessoDTO) obj;
		List fields = new ArrayList();
		fields.add("idPerfilAcesso");
		fields.add("dataInicio");
		fields.add("dataFim");
		fields.add("nomePerfilAcesso");
		String sql = "SELECT idPerfil, dataInicio, dataFim, nome FROM " + getTableName() + " WHERE dataFim IS NULL AND idPerfil = ? ";
		List dados = this.execSQL(sql, new Object[] { perfilAcessoDTO.getIdPerfilAcesso() });
		return (IDto) this.listConvertion(getBean(), dados, fields).get(0);
	}

	public Integer listarIdAdministrador() throws Exception {
		String SGBD = CITCorporeUtil.SGBD_PRINCIPAL.trim();
		List parametro = new ArrayList();
		PerfilAcessoDTO perfilAcessoDTO = new PerfilAcessoDTO();
		StringBuffer sql = new StringBuffer();
		if (SGBD.equalsIgnoreCase("ORACLE")) {
			sql.append("SELECT idPerfil FROM " + getTableName() + " WHERE dataFim IS NULL AND UPPER( nome ) LIKE '%Administrador%' ");	
		}else{
			sql.append("SELECT idPerfil FROM " + getTableName() + " WHERE dataFim IS NULL AND  nome LIKE '%Administrador%' ");
		}		
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idPerfilAcesso");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result != null && result.size() > 0) {
			perfilAcessoDTO = (PerfilAcessoDTO) result.get(0);
		} else {
			perfilAcessoDTO = new PerfilAcessoDTO();
		}
		@SuppressWarnings("unused")
		Integer resultado = new Integer(1);
		if (perfilAcessoDTO != null) {
			resultado = perfilAcessoDTO.getIdPerfilAcesso();
			return resultado;
		}
		return null;
	}

	/**
	 * Verifica se PerfilAcessoInformado informada existe.
	 * 
	 * @param perfilAcesso
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSePerfilAcessoExiste(PerfilAcessoDTO perfilAcesso) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select idperfil from " + getTableName() + "  where  nome = ? and datafim is null ");
		parametro.add(perfilAcesso.getNomePerfilAcesso());

		if (perfilAcesso.getIdPerfilAcesso() != null) {
			sql.append("and idperfil <> ?");
			parametro.add(perfilAcesso.getIdPerfilAcesso());
		}

		list = this.execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
