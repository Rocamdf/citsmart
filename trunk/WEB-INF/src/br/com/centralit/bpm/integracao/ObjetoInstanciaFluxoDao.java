package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ObjetoInstanciaFluxoDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 3358165502883317230L;

	private static final String TABLE_NAME = "bpm_objetoinstanciafluxo";

	public ObjetoInstanciaFluxoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idObjetoInstancia", "idObjetoInstancia", true, true, false, false));
		listFields.add(new Field("idInstancia", "idInstancia", false, false, false, false));
		listFields.add(new Field("idItemTrabalho", "idItemTrabalho", false, false, false, false));
		listFields.add(new Field("idObjetoNegocio", "idObjetoNegocio", false, false, false, false));
		listFields.add(new Field("nomeObjeto", "nomeObjeto", false, false, false, false));
		listFields.add(new Field("nomeClasse", "nomeClasse", false, false, false, false));
		listFields.add(new Field("tipoAssociacao", "tipoAssociacao", false, false, false, false));
		listFields.add(new Field("campoChave", "campoChave", false, false, false, false));
		listFields.add(new Field("objetoPrincipal", "objetoPrincipal", false, false, false, false));
		listFields.add(new Field("nomeTabelaBD", "nomeTabelaBD", false, false, false, false));
		listFields.add(new Field("nomeCampoBD", "nomeCampoBD", false, false, false, false));
		listFields.add(new Field("tipoCampoBD", "tipoCampoBD", false, false, false, false));
		listFields.add(new Field("valor", "valor", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ObjetoInstanciaFluxoDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection findByIdInstancia(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idInstancia", "=", parm));
		condicao.add(new Condition("tipoAssociacao", "=", "I"));
		ordenacao.add(new Order("nomeObjeto"));
		return super.findByCondition(condicao, ordenacao);
	}

	public ObjetoInstanciaFluxoDTO findByIdInstanciaAndNomeObjeto(Integer idInstancia, String nomeObjeto) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idInstancia", "=", idInstancia));
		condicao.add(new Condition("tipoAssociacao", "=", "I"));
		condicao.add(new Condition("nomeObjeto", "=", nomeObjeto));
		ordenacao.add(new Order("nomeObjeto"));
		Collection col = super.findByCondition(condicao, ordenacao);
		if (col != null && !col.isEmpty())
			return (ObjetoInstanciaFluxoDTO) ((List) col).get(0);
		else
			return null;
	}

	public Collection findByIdInstanciaAndNome(Integer idInstancia, String nomeCampo) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idInstancia", "=", idInstancia));
		condicao.add(new Condition("tipoAssociacao", "=", "I"));
		condicao.add(new Condition("nomeObjeto", "=", nomeCampo));
		ordenacao.add(new Order("nomeCampo"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdInstancia(Integer idInstancia) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idInstancia", "=", idInstancia));
		condicao.add(new Condition("tipoAssociacao", "=", "I"));
		super.deleteByCondition(condicao);
	}

	public void deleteByIdInstanciaAndNome(Integer idInstanciaFluxo, String nomeCampo) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idInstancia", "=", idInstanciaFluxo));
		condicao.add(new Condition("tipoAssociacao", "=", "I"));
		condicao.add(new Condition("nomeObjeto", "=", nomeCampo));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdTarefa(Integer idTarefa) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemTrabalho", "=", idTarefa));
		condicao.add(new Condition("tipoAssociacao", "=", "T"));
		ordenacao.add(new Order("nomeObjeto"));
		return super.findByCondition(condicao, ordenacao);
	}
}
