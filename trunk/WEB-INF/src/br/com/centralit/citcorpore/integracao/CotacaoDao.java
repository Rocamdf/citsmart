package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class CotacaoDao extends CrudDaoDefaultImpl {
	public CotacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idCotacao" ,"idCotacao", true, true, false, false));
		listFields.add(new Field("idEmpresa" ,"idEmpresa", false, false, false, false));
        listFields.add(new Field("identificacao" ,"identificacao", false, false, false, false));
		listFields.add(new Field("dataHoraCadastro" ,"dataHoraCadastro", false, false, false, false));
        listFields.add(new Field("dataFinalPrevista" ,"dataFinalPrevista", false, false, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
        listFields.add(new Field("observacoes" ,"observacoes", false, false, false, false));
        listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Cotacao";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return CotacaoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	@Override
	public void updateNotNull(IDto obj) throws Exception {
	    super.updateNotNull(obj);
	}
    public void atualizaSituacao(CotacaoDTO cotacaoDto) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE " + getTableName() + " SET situacao = ? WHERE (idCotacao = ?)");
        Object[] params = {cotacaoDto.getSituacao(), cotacaoDto.getIdCotacao() };
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização cotação.");
            e.printStackTrace();
        }
    }  
}
