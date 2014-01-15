/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ComentariosDTO;
import br.com.centralit.citcorpore.integracao.ComentariosDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author valdoilo.damasceno
 * 
 */
public class ComentariosServiceEjb extends CrudServicePojoImpl implements ComentariosService {

    private static final long serialVersionUID = -1782545793169006912L;

    @Override
    public Collection<ComentariosDTO> consultarComentarios(BaseConhecimentoDTO baseConhecimentoBean) throws ServiceException, Exception {
    	return this.getComentariosDAO().consultarComentarios(baseConhecimentoBean);
    }

    @Override
    public void restaurarGridComentarios(DocumentHTML document, Collection<ComentariosDTO> comentarios) {
		document.executeScript("deleteAllRows()");
		if (comentarios != null && !comentarios.isEmpty()) {
		    int count = 0;
		    document.executeScript("countComentario = 0");
		    for (ComentariosDTO comentarioBean : comentarios) {
			count++;
	
			document.executeScript("restoreRow()");
			document.executeScript("seqSelecionada = " + count);
	
			String comentario = (comentarioBean.getComentario() != null ? comentarioBean.getComentario() : "");
			String nome = (comentarioBean.getNome() != null ? comentarioBean.getNome() : "");
			String email = (comentarioBean.getEmail() != null ? comentarioBean.getEmail() : "");
			String dataInicio = (comentarioBean.getDataInicio() != null ? comentarioBean.getDataInicio().toString() : "");
			String nota = (comentarioBean.getNota() != null ? comentarioBean.getNota().toString() : "");
	
			document.executeScript("setRestoreComentario('" + comentarioBean.getIdComentario() + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(comentario) + "'," + "'"
				+ br.com.citframework.util.WebUtil.codificaEnter(nome) + "'," + "'" 
				+ br.com.citframework.util.WebUtil.codificaEnter(email) + "'," + "'"
				+ br.com.citframework.util.WebUtil.codificaEnter(nota) + "'," + "'"
				+ br.com.citframework.util.WebUtil.codificaEnter(dataInicio) + "')");
		    }
		    document.executeScript("exibeGrid()");
		} else {
		    document.executeScript("ocultaGrid()");
		}
    }

    public ComentariosDAO getComentariosDAO() throws ServiceException {
    	return (ComentariosDAO) this.getDao();
    }
    
    public Double calcularNota(Integer idBaseConhecimento) throws Exception {
    	return this.getComentariosDAO().calcularNota(idBaseConhecimento);
    }
    @Override
    protected CrudDAO getDao() throws ServiceException {
    	return new ComentariosDAO();
    }

    @Override
    protected void validaCreate(Object arg0) throws Exception {

    }

    @Override
    protected void validaDelete(Object arg0) throws Exception {

    }

    @Override
    protected void validaFind(Object arg0) throws Exception {

    }

    @Override
    protected void validaUpdate(Object arg0) throws Exception {

    }

	@Override
	public Integer consultarComentariosPorPeriodo(BaseConhecimentoDTO baseConhecimentoDTO) throws ServiceException, Exception {
		List<ComentariosDTO> listaComentarios = new ArrayList();
		try {
			listaComentarios = (List<ComentariosDTO>) this.getComentariosDAO().consultarComentariosPorPeriodo(baseConhecimentoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaComentarios.size();
	}

   /* public ArrayList<ComentariosDTO> restoreByIdBaseConhecimentoByNota(Integer idBaseConhecimento, Integer nota) throws ServiceException, Exception{
    	ArrayList<Condition> condicoes = new ArrayList<Condition>();
    	
    	condicoes.add(new Condition("idBaseConhecimento", "=", idBaseConhecimento));
    	condicoes.add(new Condition("nota", ">=", nota));
    	
    	ArrayList<ComentariosDTO> resultado =  (ArrayList<ComentariosDTO>) getComentariosDAO().findByCondition(condicoes, null);
    	
    	if(resultado != null){
    		return resultado;
    	}
    	
    	return null;
    }
*/
}
