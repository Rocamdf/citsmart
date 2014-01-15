package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citquestionario.bean.ListagemDTO;
import br.com.centralit.citquestionario.bean.ListagemItemDTO;
import br.com.centralit.citquestionario.bean.ListagemLinhaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;

public class ListagemDao extends CrudDaoDefaultImpl {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5757813303517999071L;

	public ListagemDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection getFields() {        
		return null;
	}

	public String getTableName() {
		return null;
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ListagemDTO.class;
	}

	public IDto restore(IDto obj) throws Exception {
	    if (obj != null) {
	        Collection linhas = new ArrayList();
            List lista = this.execSQL(((ListagemDTO) obj).getSQL(),null);
            
            for(Integer l = 0; l <= lista.size()-1; l++){ 
                Object[] row = (Object[]) lista.get(l);
                
                ListagemLinhaDTO linha = new ListagemLinhaDTO();
                Collection dados = new ArrayList();
                String descricao = "";

                int c = 0;
                for(Iterator it = ((ListagemDTO) obj).getCampos().iterator(); it.hasNext();){
                    ListagemItemDTO campo = (ListagemItemDTO) it.next();
                    ListagemItemDTO item = new ListagemItemDTO();
                    item.setNome(campo.getNome());
                    item.setDescricao(campo.getDescricao());
                    item.setValor(row[c].toString());
                    dados.add(item);
                    if (c>0){
                        if (descricao != ""){
                            descricao += " - ";
                        }
                        descricao += row[c].toString();
                    }
                    c += 1;
                }
                linha.setId(row[0].toString());
                linha.setDescricao(descricao);
                linha.setDados(dados);
                linhas.add(linha);
            }
            ((ListagemDTO) obj).setLinhas(linhas);
	    }
	    return obj;
	}	
	
}
