package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.PesquisaRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CurriculoDao extends CrudDaoDefaultImpl {
	
	public CurriculoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}	

	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idCurriculo" ,"idCurriculo", true, true, false, false));
		listFields.add(new Field("dataNascimento" ,"dataNascimento", false, false, false, false));
		listFields.add(new Field("portadorNecessidadeEspecial" ,"portadorNecessidadeEspecial", false, false, false, false));
		listFields.add(new Field("idItemListaTipoDeficiencia" ,"idItemListaTipoDeficiencia", false, false, false, false));
		listFields.add(new Field("qtdeFilhos" ,"qtdeFilhos", false, false, false, false));
		listFields.add(new Field("filhos" ,"filhos", false, false, false, false));
		listFields.add(new Field("cidadeNatal" ,"cidadeNatal", false, false, false, false));
		listFields.add(new Field("idNaturalidade" ,"idNaturalidade", false, false, false, false));
		listFields.add(new Field("nome" ,"nome", false, false, false, false));
		listFields.add(new Field("sexo" ,"sexo", false, false, false, false));
		listFields.add(new Field("cpf" ,"cpf", false, false, false, false));
		listFields.add(new Field("estadoCivil" ,"estadoCivil", false, false, false, false));
		listFields.add(new Field("observacoesEntrevista" ,"observacoesEntrevista", false, false, false, false));
		listFields.add(new Field("listaNegra" ,"listaNegra", false, false, false, false));
		//listFields.add(new Field("indicacao" ,"indicacao", false, false, false, false));
		//listFields.add(new Field("idEmpregadoIndicacao" ,"idEmpregadoIndicacao", false, false, false, false));
		//listFields.add(new Field("observacoes" ,"observacoes", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "RH_Curriculo";
	}

	public Collection list() throws Exception {
        List list = new ArrayList();
        list.add(new Order("nome"));
        return super.list(list);
	}

	public Class getBean() {
		return CurriculoDTO.class;
	}
	
	/*public List<RequisicaoLiberacaoDTO> findIdByCpf(String cpf) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select idCurriculo ");
		sql.append("from rh_curriculo ");
		sql.append("where cpf = ? ");
		
		
		List parametro = new ArrayList();
		List<String> listRetorno = new ArrayList<String>();
		parametro.add(cpf);

		listRetorno.add("idCurriculo");


		List lista = this.execSQL(sql.toString(), parametro.toArray());
		return this.engine.listConvertion(getBean(), lista, listRetorno.get(0));
	}*/
	
	public CurriculoDTO findIdByCpf(String cpf) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("cpf", "=", cpf)); 
		ordenacao.add(new Order("idCurriculo"));
		List list = (List) super.findByCondition(condicao, ordenacao);
		CurriculoDTO curriculoDTO = (CurriculoDTO) list.get(0);
		return curriculoDTO;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<CurriculoDTO> listaCurriculosPorCriterios(RequisicaoPessoalDTO requisicaoPessoalDTO) throws Exception {

		List listRetorno = new ArrayList();

		List lista = new ArrayList();

		List parametros = new ArrayList();

		StringBuffer  var1 = new StringBuffer();
		var1.append("SELECT distinct c.idCurriculo, c.dataNascimento, c.cidadeNatal, c.nome, c.cpf ");
		var1.append("FROM   rh_curriculo c ");

		if (requisicaoPessoalDTO.getPesquisa_formacao() != null && !requisicaoPessoalDTO.getPesquisa_formacao().equals("")) {
			var1.append("       INNER JOIN rh_formacaocurriculo f ");
			var1.append("               ON f.idcurriculo = c.idcurriculo ");
			var1.append("                  AND descricao LIKE ? ");
			
			String formacao = "%"+requisicaoPessoalDTO.getPesquisa_formacao()+"%";
			parametros.add(formacao);
		}
		if (requisicaoPessoalDTO.getPesquisa_idiomas() != null && !requisicaoPessoalDTO.getPesquisa_idiomas().equals("")) {
			var1.append("       INNER JOIN rh_idiomacurriculo i ");
			var1.append("               ON i.idcurriculo = c.idcurriculo ");
			var1.append("       INNER JOIN rh_idioma id ");
			var1.append("               ON i.ididioma = id.ididioma ");
			var1.append("                  AND id.descricao LIKE ? ");
			String idioma = "%"+requisicaoPessoalDTO.getPesquisa_formacao()+"%";
			parametros.add(idioma);
		}
		if (requisicaoPessoalDTO.getPesquisa_certificacao() != null && !requisicaoPessoalDTO.getPesquisa_certificacao().equals("")) {
			var1.append("       INNER JOIN rh_certificacaocurriculo cer ");
			var1.append("               ON cer.idcurriculo = c.idcurriculo ");
			var1.append("                  AND cer.descricao LIKE ? ");
			String certificacao = "%"+requisicaoPessoalDTO.getPesquisa_certificacao()+"%";
			parametros.add(certificacao);
		}
		if (requisicaoPessoalDTO.getPesquisa_cidade() != null && !requisicaoPessoalDTO.getPesquisa_cidade().equals("")) {
			var1.append("       INNER JOIN rh_enderecocurriculo ende ");
			var1.append("               ON ende.idcurriculo = c.idcurriculo ");
			var1.append("                  AND ende.nomecidade LIKE ? ");
			String cidade = "%"+requisicaoPessoalDTO.getPesquisa_cidade()+"%";
			parametros.add(cidade);
		}
		

		lista = this.execSQL(var1.toString(), parametros.toArray());
		
		
		listRetorno.add("idCurriculo");
		listRetorno.add("dataNascimento");
		listRetorno.add("cidadeNatal");
		listRetorno.add("nome");
		listRetorno.add("cpf");
		
		
		List listaCurriculos = this.engine.listConvertion(getBean(), lista, listRetorno);

		return listaCurriculos;
	}
	
	public Collection<CurriculoDTO> listaCurriculosAprovados() throws Exception {
		return null;
		
	}
	
	
}
