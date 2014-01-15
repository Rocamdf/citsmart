package br.com.centralit.citajax.html;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.CitAjaxWebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilStrings;

public class HTMLForm {
	private DocumentHTML document;
	
	private String name;
	private String action;
	private String target;
	private String encoding;
	
	public HTMLForm(){
	}
	public HTMLForm(String nameForm, DocumentHTML documentParm){
		setName(nameForm);
		setDocument(documentParm);
	}	
	protected void setCommandExecute(String comand){
		this.document.getComandsExecute().add(comand);
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
		setCommandExecute("document." + this.getName() + ".action='" + action + "'");
	}
	public DocumentHTML getDocument() {
		return document;
	}
	public void setDocument(DocumentHTML document) {
		this.document = document;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
		setCommandExecute("document." + this.getName() + ".target='" + target + "'");
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
		setCommandExecute("document." + this.getName() + ".encoding='" + encoding + "'");
	}
	public void lockForm(){
		setCommandExecute("HTMLUtils.lockForm(document." + this.getName() + ")");
	}
	public void unLockForm(){
		setCommandExecute("HTMLUtils.unLockForm(document." + this.getName() + ")");
	}
	public void clear(){
		setCommandExecute("HTMLUtils.clearForm(document." + this.getName() + ")");
	}
	public void lockButtonsForm(String[] excep){
		String str = "";
		if (excep != null){
			for(int i = 0; i < excep.length;i++){
				if (!str.equalsIgnoreCase("")){
					str += ",";
				}
				str += "'" + excep[i] + "'";
			}
		}
		if (!str.equalsIgnoreCase("")){
			str = "[" + str + "]";
		}else{
			str = "null";
		}
		setCommandExecute("HTMLUtils.lockButtonsForm(document." + this.getName() + ", " + str + ")");
	}
	public void unLockButtonsForm(String[] excep){
		String str = "";
		if (excep != null){
			for(int i = 0; i < excep.length;i++){
				if (!str.equalsIgnoreCase("")){
					str += ",";
				}
				str += "'" + excep[i] + "'";
			}
		}
		if (!str.equalsIgnoreCase("")){
			str = "[" + str + "]";
		}else{
			str = "null";
		}		
		setCommandExecute("HTMLUtils.unlockButtonsForm(document." + this.getName() + ", " + str + ")");
	}	
	public void setValues(IDto bean){
		List listSets = CitAjaxReflexao.findSets(bean);
		setCommandExecute("document.fAux_HTMLForm_temp = HTMLUtils.getForm()");
		setCommandExecute("HTMLUtils.setForm(document." + this.getName() + ")");
		for(int i = 0; i < listSets.size(); i++){
			String property = (String) listSets.get(i);
			Object valor = null;
			try {
				valor = CitAjaxReflexao.getPropertyValue(bean, property);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Erro ao obter valor da propriedade: " + bean.getClass().getName() + " - " + property);
			}
			property = UtilStrings.convertePrimeiraLetra(property, "L");
			
			String valorTransf = null;
			if ((valor instanceof BigInteger)){
				valorTransf = UtilFormatacao.formatInt(((BigInteger)valor).intValue(), "################");
			}
			
			if ((valor instanceof Long)){
				valorTransf = UtilFormatacao.formatInt(((Long)valor).intValue(), "################");
			}
			if ((valor instanceof Integer)) {
				valorTransf = UtilFormatacao.formatInt(((Integer)valor).intValue(), "################");
			}
			if ((valor instanceof Double)) {
				//valorTransf = UtilFormatacao.formatDecimal(((Double)valor).doubleValue(), "###############0,00");
				valorTransf = UtilFormatacao.formatBigDecimal(new BigDecimal(((Double)valor).doubleValue()), 2);
			}
			if ((valor instanceof BigDecimal)) {
				valorTransf = UtilFormatacao.formatBigDecimal(((BigDecimal)valor), 2);
			}
			if ((valor instanceof String)) {
				valorTransf = (String)valor;
			}
			if ((valor instanceof Date)){
				valorTransf = UtilDatas.dateToSTR((Date)valor);
			}
			
			if (valorTransf != null){
				setCommandExecute("HTMLUtils.setValue('" + property + "', ObjectUtils.decodificaEnter(ObjectUtils.decodificaAspasApostrofe('" + CitAjaxWebUtil.codificaAspasApostrofe(CitAjaxWebUtil.codificaEnter(StringEscapeUtils.escapeJavaScript(valorTransf))) + "')))");
				/*
				String[] strSplit = valorTransf.split("\r\n");
				if (strSplit == null || strSplit.length == 0 || strSplit.length == 1){
					valorTransf = "'" + valorTransf + "'";
					setCommandExecute("HTMLUtils.setValue('" + property + "', " + valorTransf + ")");
				}else{
					setCommandExecute("var var" + property + " = ''");
					for(int x = 0; x < strSplit.length; x++){
						setCommandExecute("var" + property + " = " + "var" + property + " + '" + CitAjaxWebUtil.codificaAspasApostrofe(strSplit[x]) + "'");
						//setCommandExecute("var" + property + " = " + "var" + property + " + '" + strSplit[x] + "{N_newline}{R_newline}'");
						//setCommandExecute("var" + property + " = " + "var" + property + ".replace('{N_newline}', '')"); //'\\\n')");
						//setCommandExecute("var" + property + " = " + "var" + property + ".replace('{R_newline}', '')"); //'\\\r')");
					}
					setCommandExecute("HTMLUtils.setValue('" + property + "', ObjectUtils.decodificaAspasApostrofe(var" + property + "))");
				}
				*/
			}
		}
		setCommandExecute("HTMLUtils.setForm(document.fAux_HTMLForm_temp)");		
	}
	
	public void setValueText(String fieldName, Integer index, String value){
		if (index == null){
			setCommandExecute("document." + this.getName() + "." + fieldName + ".value = ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('" + CitAjaxWebUtil.codificaAspasApostrofe(CitAjaxWebUtil.codificaEnter(value)) + "'))");
		}else{
			setCommandExecute("document." + this.getName() + "." + fieldName + "[" + index.intValue() + "].value = ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('" + CitAjaxWebUtil.codificaAspasApostrofe(CitAjaxWebUtil.codificaEnter(value)) + "'))");
		}
	}
}
