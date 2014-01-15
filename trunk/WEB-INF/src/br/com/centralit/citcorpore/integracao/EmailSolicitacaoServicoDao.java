package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.EmailSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class EmailSolicitacaoServicoDao  extends CrudDaoDefaultImpl{

    @Override
    public Collection getFields() {
	Collection listFields = new ArrayList();
	listFields.add(new Field("idemailsolicitacaoservico", "idEmailSolicitacaoServico", true, true, false, false));
	listFields.add(new Field("messageid", "messageId", false, false, false, true));
	listFields.add(new Field("situacao", "situacao", false, false, false, false));
	

	return listFields;
    }

    @Override
    public String getTableName() {
	return "emailsolicitacaoservico";
    }
    
   
    @Override
    public Collection list() throws Exception {
	List ordenacao = new ArrayList();
	ordenacao.add(new Order("messageId"));
	return super.list(ordenacao);
    }
    
    public EmailSolicitacaoServicoDTO listSituacao(String messageid) throws Exception {
   	List parametro = new ArrayList();
   	StringBuffer sb = new StringBuffer();
   	sb.append("select idEmailSolicitacaoServico,situacao from emailsolicitacaoservico where messageid = ? ");
   	parametro.add(messageid);
   	List lista = this.execSQL(sb.toString(), parametro.toArray());
   	List<String> listRetorno = new ArrayList<String>();
   	listRetorno.add("idEmailSolicitacaoServico");
   	listRetorno.add("situacao");
   	List result = this.engine.listConvertion(EmailSolicitacaoServicoDTO.class, lista, listRetorno);
   	return (EmailSolicitacaoServicoDTO) result.get(0);
       }
    
    @Override
    public Class getBean() {
	return EmailSolicitacaoServicoDTO.class;
    }

    public EmailSolicitacaoServicoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    } 
    
    @Override
    public Collection find(IDto arg0) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

}
