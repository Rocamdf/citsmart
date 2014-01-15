package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.config.Config;
import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.ajaxForms.ListaOSContrato;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ElementoFluxoDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 8742320627971853729L;

	private static final String TABLE_NAME = "bpm_elementofluxo";

	public ElementoFluxoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idElemento", "idElemento", true, true, false, false));
		listFields.add(new Field("idFluxo", "idFluxo", false, false, false, false));
		listFields.add(new Field("tipoElemento", "tipoElemento", false, false, false, false));
		listFields.add(new Field("subTipo", "subTipo", false, false, false, false));
		listFields.add(new Field("nome", "nome", false, false, false, false));
		listFields.add(new Field("documentacao", "documentacao", false, false, false, false));
		listFields.add(new Field("acaoEntrada", "acaoEntrada", false, false, false, false));
		listFields.add(new Field("acaoSaida", "acaoSaida", false, false, false, false));
		listFields.add(new Field("tipoInteracao", "tipoInteracao", false, false, false, false));
		listFields.add(new Field("visao", "visao", false, false, false, false));
		listFields.add(new Field("url", "url", false, false, false, false));
		listFields.add(new Field("grupos", "grupos", false, false, false, false));
		listFields.add(new Field("usuarios", "usuarios", false, false, false, false));
		listFields.add(new Field("script", "script", false, false, false, false));
		listFields.add(new Field("textoEmail", "textoEmail", false, false, false, false));
		listFields.add(new Field("nomeFluxoEncadeado", "nomeFluxoEncadeado", false, false, false, false));
		listFields.add(new Field("modeloEmail", "modeloEmail", false, false, false, false));
		listFields.add(new Field("intervalo", "intervalo", false, false, false, false));
		listFields.add(new Field("condicaoDisparo", "condicaoDisparo", false, false, false, false));
		listFields.add(new Field("multiplasInstancias", "multiplasInstancias", false, false, false, false));
		listFields.add(new Field("contabilizaSLA", "contabilizaSLA", false, false, false, false));
		listFields.add(new Field("percExecucao", "percExecucao", false, false, false, false));
		listFields.add(new Field("destinatariosEmail", "destinatariosEmail", false, false, false, false));

		listFields.add(new Field("posX", "posX", false, false, false, false));
		listFields.add(new Field("posY", "posY", false, false, false, false));
		listFields.add(new Field("largura", "largura", false, false, false, false));
		listFields.add(new Field("altura", "altura", false, false, false, false));
		listFields.add(new Field("template", "template", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ElementoFluxoDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public List<ElementoFluxoDTO> findAllByIdFluxo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idFluxo", "=", parm));
		ordenacao.add(new Order("idElemento"));
		return (List<ElementoFluxoDTO>) super.findByCondition(condicao, ordenacao);
	}

	public List<ElementoFluxoDTO> findByIdFluxo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idFluxo", "=", parm));
		condicao.add(new Condition("tipoElemento", "=", getTipoElemento().name()));
		ordenacao.add(new Order("idElemento"));
		return (List<ElementoFluxoDTO>) super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdFluxo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idFluxo", "=", parm));
		condicao.add(new Condition("tipoElemento", "=", getTipoElemento().name()));
		super.deleteByCondition(condicao);
	}
	
	public void deleteAllByIdFluxo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idFluxo", "=", parm));
		super.deleteByCondition(condicao);
	}

	@Override
	public IDto create(IDto obj) throws Exception {
		((ElementoFluxoDTO) obj).setTipoElemento(getTipoElemento().name());
		return super.create(obj);
	}

	@Override
	public IDto restore(IDto obj) throws Exception {
		obj = super.restore(obj);
		if (obj == null)
			return null;
		String nomeClasse = Config.getClasseDtoElemento(((ElementoFluxoDTO) obj).getTipoElemento());
		ElementoFluxoDTO elementoDto = (ElementoFluxoDTO) Class.forName(nomeClasse).newInstance();
		Reflexao.copyPropertyValues(obj, elementoDto);
		return elementoDto;
	}

	public ElementoFluxoDTO restore(Integer idElementoFluxo) throws Exception {
		ElementoFluxoDTO elementoDto = new ElementoFluxoDTO();
		elementoDto.setIdElemento(idElementoFluxo);
		return (ElementoFluxoDTO) restore(elementoDto);
	}

	protected Enumerados.TipoElementoFluxo getTipoElemento() {
		return null;
	}
	
	public List<ElementoFluxoDTO> listaElementoFluxo(String documentacao) throws Exception {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List lista = new ArrayList();
		
		String sql = "";
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER))
			sql = "select documentacao from bpm_elementofluxo where trim(documentacao) ";
		else
			sql = "select distinct(documentacao) from bpm_elementofluxo where trim(documentacao) ";
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL) )
			sql+=" ilike '%"+documentacao+"%'";
		else 
			sql+=" like '%"+documentacao+"%'";
		
		sql+=" ORDER BY documentacao ";
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
			String sqlOracleSqlServer = "";
			if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) { 
				sqlOracleSqlServer = "select distinct cast(documentacao as varchar2(4000)) documentacao  from bpm_elementofluxo where UPPER(documentacao) ";
			}
			if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) { 
				sqlOracleSqlServer = "select distinct cast(documentacao as varchar(4000)) documentacao  from bpm_elementofluxo where documentacao ";
			}
			sqlOracleSqlServer+=" like UPPER('%"+documentacao+"%')";
			sqlOracleSqlServer += " and documentacao is not null ";
			sqlOracleSqlServer += " order by documentacao ";
			
			listRetorno.add("documentacao");
			
			lista = this.execSQL(sqlOracleSqlServer.toString(), parametro.toArray());
			
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		}
		
		
		if (!CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			lista = this.execSQL(sql, parametro.toArray());
		}

		listRetorno.add("documentacao");

		return this.engine.listConvertion(getBean(), lista, listRetorno);
	}
 
}
