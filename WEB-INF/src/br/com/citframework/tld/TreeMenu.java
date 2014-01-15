package br.com.citframework.tld;


import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import br.com.citframework.util.Reflexao;

public class TreeMenu extends TagSupport{
	
	/** 
	 * 
	 */
	private static final long serialVersionUID = 5622076804636058198L;
	private String target;
	private String rootLabel;
	private String jsBaseDir;
	private String cssBaseDir;
	private String collection;
	private String idAttrib;
	private String idFatherAttrib;
	private String descAttrib;
	private String urlAttrib;
	private String hintAttrib;
	private boolean makeNull = true;
	
	
	
	public boolean isMakeNull() {
		return makeNull;
	}
	public void setMakeNull(boolean makeNull) {
		this.makeNull = makeNull;
	}
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public String getCssBaseDir() {
		return cssBaseDir;
	}
	public void setCssBaseDir(String cssBaseDir) {
		this.cssBaseDir = cssBaseDir;
	}
	public String getDescAttrib() {
		return descAttrib;
	}
	public void setDescAttrib(String descAttrib) {
		this.descAttrib = descAttrib;
	}
	public String getHintAttrib() {
		return hintAttrib;
	}
	public void setHintAttrib(String hintAttrib) {
		this.hintAttrib = hintAttrib;
	}
	public String getIdAttrib() {
		return idAttrib;
	}
	public void setIdAttrib(String idAttrib) {
		this.idAttrib = idAttrib;
	}
	public String getIdFatherAttrib() {
		return idFatherAttrib;
	}
	public void setIdFatherAttrib(String idFatherAttrib) {
		this.idFatherAttrib = idFatherAttrib;
	}
	public String getJsBaseDir() {
		return jsBaseDir;
	}
	public void setJsBaseDir(String jsBaseDir) {
		this.jsBaseDir = jsBaseDir;
	}
	public String getRootLabel() {
		return rootLabel;
	}
	public void setRootLabel(String rootLabel) {
		this.rootLabel = rootLabel;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getUrlAttrib() {
		return urlAttrib;
	}
	public void setUrlAttrib(String urlAttrib) {
		this.urlAttrib = urlAttrib;
	}
	
	public int doStartTag() throws JspException {
		Collection col = getCollectionImpl();
		if(col==null){
			return SKIP_BODY;
		}
		
		if(col.size()>0){
			try {
				pageContext.getOut().println("	<link rel='StyleSheet' href='"+getCssBaseDir()+"/dtree.css' type='text/css' />");
		        pageContext.getOut().println("	<script type='text/javascript' src='"+getJsBaseDir()+"/dtree.js'></script>");
		        pageContext.getOut().println("	<script>");
		        pageContext.getOut().println("	d = new dTree('d');");
		        pageContext.getOut().println("	d.config.useIcons = false;");
		        pageContext.getOut().println("	d.add(0,-1,'"+getRootLabel()+"');");
		        Iterator it = col.iterator();
		        while(it.hasNext()){
		        	Object obj = it.next();
		        	Object id = Reflexao.getPropertyValue(obj, getIdAttrib());
		        	if(id==null){
		        		throw new JspException("o valor do atributo "+getIdAttrib()+" não pode ser nulo ");
		        	}
		        	Object desc = Reflexao.getPropertyValue(obj, getDescAttrib());
		        	if(desc==null){
		        		throw new JspException("o valor do atributo "+getDescAttrib()+" não pode ser nulo ");
		        	}		        	
		        	
		        	Object idPai = Reflexao.getPropertyValue(obj, getIdFatherAttrib());
		        	if(idPai==null){
		        		idPai="0";
		        	}
		        	
		        	Object url = null;
		        	if(getUrlAttrib()!=null){
		        	   url=Reflexao.getPropertyValue(obj, getUrlAttrib());	
		        	}
		        	
		        	if(url==null){
		        		url="";
		        	}
		        	
		        	Object hint =null;
		        	if( getHintAttrib()!=null){
			        	hint = Reflexao.getPropertyValue(obj, getHintAttrib());		        		
		        	}
		        	if(hint==null){
		        		hint=desc;
		        	}
		        	
		        	if(getTarget()==null){
		        		setTarget("");
		        	}
		        	
			        pageContext.getOut().println("	d.add("+id+","+idPai+",'"+desc+"','"+url+"','"+hint+"','"+target+"','','');");

		        	
		        	
		        }
		        pageContext.getOut().println("	document.write(d);");
		        pageContext.getOut().println("	</script>");
		        
		        
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new JspException(e);
			}
			
			
		}
		
		return SKIP_BODY;
	}
	
	private Collection getCollectionImpl() throws JspException{
		
		Object obj = pageContext.getRequest().getAttribute(getCollection());
		if(obj!= null){
			if(obj instanceof Collection){
				return (Collection)obj;
			}else{
				throw new JspException("Objeto "+getCollection()+" deve ser do tipo java.util.Collection");
			}
		}else{
			obj = pageContext.getSession().getAttribute(getCollection());
			if(obj!= null){
				if(obj instanceof Collection){
					return (Collection)obj;
				}else{
					throw new JspException("Objeto "+getCollection()+" deve ser do tipo java.util.Collection");
				}
			}else{
				if(makeNull)
					throw new JspException("Objeto "+getCollection()+" não encontrado em nenhum escopo");
				else
					return null;
			}
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
