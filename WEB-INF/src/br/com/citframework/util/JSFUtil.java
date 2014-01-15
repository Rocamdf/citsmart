/*
 * JSFUtil.java
 *
 * Created on 31 de Outubro de 2005, 21:11
 *
 */

package br.com.citframework.util;


/**
 *
 * @author hp
 */
public class JSFUtil {
    
/*    public static String getParameterValue(String name) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        Object value = ctx.getExternalContext().getRequestParameterMap().
                get(name);
        
        if ( value != null ) {
            return value.toString();
        } else {
            return null;
        }
    }
    

    public static void setParameterValue(String name,Object value) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        ((HttpServletRequest)ctx.getExternalContext().getRequest()).setAttribute(name, value);

    }

    public static HttpServletRequest getRequest(){
        FacesContext ctx = FacesContext.getCurrentInstance();
      
        return (HttpServletRequest)ctx.getExternalContext().getRequest();

    }
    
    public static Usuario getUsuarioSessao() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        Object value = ctx.getExternalContext().getSessionMap().
                get(Constantes.getValue("USUARIO_SESSAO"));
        
        if ( value != null ) {
            return (Usuario)value;
        } else {
        	Usuario usr = new Usuario();
        	usr.setNomeUsuario("Euclides");
            return usr;
        }
    }
    
    public static Usuario getUsuarioSessaoLdap() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        Object value = ctx.getExternalContext().getSessionMap().
                get(Constantes.getValue("USUARIO_SESSAO"));
        
        if ( value != null ) {
            return (Usuario)value;
        } else {
        	Usuario usr = new Usuario();
        	if(getRequest().getUserPrincipal()!=null){
        		usr.setNomeUsuario(getRequest().getUserPrincipal().getName());
        		
        	}else{
        		usr.setNomeUsuario("Euclides");
        	}
        	
        	
            return usr;
        }
    }
    
    
    
    public static void setUsuarioSessao(Usuario usr) {
        FacesContext ctx = FacesContext.getCurrentInstance();    
        ((HttpServletRequest)ctx.getExternalContext().getRequest()).getSession().setAttribute(Constantes.getValue("USUARIO_SESSAO"), usr);
    
    }
    
    
    
    public static void addGlobalMessage(String summary, Exception e) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(
                summary, 
                e.getMessage());
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        ctx.addMessage(null, message);    
    }
    
    public static void addGlobalMessage(String summary) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(
                summary);
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        ctx.addMessage(null, message);    
    }
    
    *//**
     * Construtor
     *//*
    private JSFUtil() {
    }*/
    
}
