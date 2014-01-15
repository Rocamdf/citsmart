package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import br.com.centralit.citcorpore.bean.MarcoPagamentoPrjDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;;

public class MarcoPagamentoPrjDao extends CrudDaoDefaultImpl {
	public MarcoPagamentoPrjDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idMarcoPagamentoPrj" ,"idMarcoPagamentoPrj", true, true, false, false));
		listFields.add(new Field("idProjeto" ,"idProjeto", false, false, false, false));
		listFields.add(new Field("nomeMarcoPag" ,"nomeMarcoPag", false, false, false, false));
		listFields.add(new Field("dataPrevisaoPag" ,"dataPrevisaoPag", false, false, false, false));
		listFields.add(new Field("valorPagamento" ,"valorPagamento", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("dataUltAlteracao" ,"dataUltAlteracao", false, false, false, false));
		listFields.add(new Field("horaUltAlteracao" ,"horaUltAlteracao", false, false, false, false));
		listFields.add(new Field("usuarioUltAlteracao" ,"usuarioUltAlteracao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "MarcoPagamentoPrj";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return MarcoPagamentoPrjDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdProjeto(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProjeto", "=", parm)); 
		ordenacao.add(new Order("dataPrevisaoPag"));
		ordenacao.add(new Order("nomeMarcoPag"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdProjeto(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProjeto", "=", parm));
		super.deleteByCondition(condicao);
	}
	public void deleteByIdProjetoNotIn(Integer idProjetoParm, Collection col) throws Exception {
		String sql = "DELETE FROM " + this.getTableName() + " WHERE IDPROJETO = ? AND idMarcoPagamentoPrj NOT IN (";
		String ids = "";
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				MarcoPagamentoPrjDTO marcoPagamentoPrjDTO = (MarcoPagamentoPrjDTO)it.next();
				if (!ids.trim().equalsIgnoreCase("")){
					ids += ",";
				}
				ids += "" + marcoPagamentoPrjDTO.getIdMarcoPagamentoPrj();
			}
		}
		sql += ids + ")";
		super.execUpdate(sql, new Object[] {idProjetoParm});
	}
	@Override
	public void updateNotNull(IDto obj) throws Exception {
		super.updateNotNull(obj);
	}
	
}
