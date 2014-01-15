package br.com.centralit.citajax.html;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citajax.framework.ParserRequest;
import br.com.centralit.citajax.util.JavaScriptUtil;


public class DocumentHTML {
	private HashMap hashElementos;
	private List comandsExecute;
	private Collection metodosTratamento;
	private int scriptCount;
	private Object bean;
	private HashMap forms;
	private HashMap valuesForm = null;
	private Object returnScriptSystem;
	private Object returnScriptUser;
	private boolean ignoreNextMethod;
	
	public DocumentHTML(){
		this.forms = null;
		
		hashElementos = new HashMap();
		comandsExecute = new ArrayList();
		metodosTratamento = new ArrayList();
	}
	public HTMLElement getElementById(String objId) throws Exception{
		return getElementById(objId, HTMLElement.class);
	}
	public void setElement(String objId, HTMLElement element){
		if (!hashElementos.containsKey(objId)){
			hashElementos.put(objId, element);
		}
	}
	private HTMLElement getElementById(String objId, Class classeElement) throws Exception{
		HTMLElement element;
		if (hashElementos.containsKey(objId)){
			element = (HTMLElement) hashElementos.get(objId);
		}else{
			Class[] parmClassConstrutor = { String.class, DocumentHTML.class };
			Constructor construtor = null;
			try {
				construtor = classeElement.getConstructor(parmClassConstrutor);
			} catch (SecurityException e) {
				throw new Exception("Problema com o Construtor do Objeto HTML: " + e.getMessage());
			} catch (NoSuchMethodException e) {
				throw new Exception("Problema com o Construtor do Objeto HTML: " + e.getMessage());
			}
			Object[] parmsInitObj = {objId, this};
			element = (HTMLElement) construtor.newInstance(parmsInitObj);
			this.setElement(objId, element);
		}
		return element;		
	}
	public HTMLSelect getSelectById(String objId) throws Exception{
		return (HTMLSelect) getElementById(objId, HTMLSelect.class);
	}
	public HTMLTextBox getTextBoxById(String objId) throws Exception{
		return (HTMLTextBox) getElementById(objId, HTMLTextBox.class);
	}
	public HTMLTextArea getTextAreaById(String objId) throws Exception{
		return (HTMLTextArea) getElementById(objId, HTMLTextArea.class);
	}
	public HTMLCheckbox getCheckboxById(String objId) throws Exception{
		return (HTMLCheckbox) getElementById(objId, HTMLCheckbox.class);
	}
	public HTMLRadio getRadioById(String objId) throws Exception{
		return (HTMLRadio) getElementById(objId, HTMLRadio.class);
	}
	public HTMLTable getTableById(String objId) throws Exception{
		return (HTMLTable) getElementById(objId, HTMLTable.class);
	}	
	public HTMLTreeView getTreeViewById(String objId) throws Exception{
		return (HTMLTreeView) getElementById(objId, HTMLTreeView.class);
	}	
	public HTMLJanelaPopup getJanelaPopupById(String objId) throws Exception{
		return (HTMLJanelaPopup) getElementById(objId, HTMLJanelaPopup.class);
	}	
	
	public void executeScript(String script){
		scriptCount++;
		comandsExecute.add(script);
	}
	
	public HTMLForm getForm(String nameForm){
		if (forms == null){
			forms = new HashMap();
		}
		HTMLForm form = (HTMLForm) forms.get(nameForm);
		if (form == null){
			form = new HTMLForm(nameForm, this);
			forms.put(nameForm, form);
		}
		return form;
	}
	
	public void focusInFirstActivateField(HTMLForm form){
		List lst = getComandsExecute();
		if (form == null){
			lst.add("HTMLUtils.focusInFirstActivateField()");
		}else{
			lst.add("HTMLUtils.focusInFirstActivateField(document." + form.getName() + ")");
		}
	}
	
	public void setTitle(String title){
		List lst = getComandsExecute();
		lst.add("document.getElementById('spanTitulo').innerHTML = '" + JavaScriptUtil.escapeJavaScript(title) + "'");
	}
	
	public void alert(String msg){
		List lst = getComandsExecute();
		lst.add("alert('" + JavaScriptUtil.escapeJavaScript(msg) + "')");
	}	
	
	public Collection getAllScripts(){
		Collection col = comandsExecute;
		Collection colRetorno = new ArrayList();
		ScriptExecute script;
		
		String mName;
		for(Iterator it = metodosTratamento.iterator(); it.hasNext();){
			mName = (String)it.next();
			script = new ScriptExecute();
			if (mName.indexOf("click") > -1){
				String nameObj[] = mName.split("_");
				if (nameObj!=null && nameObj.length>0){
					script.setScript("addEvent(document.getElementById('" + nameObj[0] + "'), 'click', DEFINEALLPAGES_trataEventos_Click, false)");
					colRetorno.add(script);
				}
			}else if (mName.indexOf("change") > -1){
				String nameObj[] = mName.split("_");
				if (nameObj!=null && nameObj.length>0){
					script.setScript("addEvent(document.getElementById('" + nameObj[0] + "'), 'change', DEFINEALLPAGES_trataEventos_Change, false)");
					colRetorno.add(script);
				}				
			}
		}
		
		for(Iterator it = col.iterator(); it.hasNext();){
			script = new ScriptExecute();
			script.setScript((String)it.next());
			colRetorno.add(script);
		}
		return colRetorno;
	}
	public List getComandsExecute() {
		return comandsExecute;
	}
	public void setComandsExecute(List comandsExecute) {
		this.comandsExecute = comandsExecute;
	}	
	public Collection getMetodosTratamento(){
		return metodosTratamento;
	}
	public void setMetodosTratamentoByMethods(Collection col){
		Method m;
		for(Iterator it = col.iterator(); it.hasNext();){
			m = (Method)it.next();
			metodosTratamento.add(m.getName());
		}
	}
	public Collection getInternalBeanCollection(String internalForm, Class classe) {
		ParserRequest parser = new ParserRequest();
		Collection col = null;
		try {
			col = parser.converteValoresRequestToInternalBean(valuesForm, classe, internalForm);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
		return col;
	}
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}
	public HashMap getForms() {
		return forms;
	}
	public void setForms(HashMap forms) {
		this.forms = forms;
	}
	public HashMap getValuesForm() {
		return valuesForm;
	}
	public void setValuesForm(HashMap valuesForm) {
		this.valuesForm = valuesForm;
	}
	public Object getReturnScriptSystem() {
		return returnScriptSystem;
	}
	public void setReturnScriptSystem(Object returnScriptSystem) {
		this.returnScriptSystem = returnScriptSystem;
	}
	public Object getReturnScriptUser() {
		return returnScriptUser;
	}
	public void setReturnScriptUser(Object returnScriptUser) {
		this.returnScriptUser = returnScriptUser;
	}
	public boolean isIgnoreNextMethod() {
		return ignoreNextMethod;
	}
	public void setIgnoreNextMethod(boolean ignoreNextMethod) {
		this.ignoreNextMethod = ignoreNextMethod;
	}
}
