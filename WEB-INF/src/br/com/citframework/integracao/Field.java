package br.com.citframework.integracao;

import java.io.Serializable;

/**
 * Classe de campos da base de dados e com os atributos do DTO.
 * 
 * @author Administrador
 * 
 */
public class Field implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5945592714921506681L;

	private String fieldDB;
	private String fieldClass;
	private boolean pk = false;// caso seja chave primária
	private boolean sequence = false;// preenchido com sequence
	private boolean auto = false;// valor default ou auto incremento
	private boolean unique = false;// se pode ou não possuir valor duplicado
	private String msgReturn = ""; // mensagem de retorno para campo único

	/**
	 * Construtor Field sem o campo de descrição para unique
	 * 
	 * @param fieldDB
	 *            Nome do campo no banco de dados.
	 * @param fieldClass
	 *            Nome do atributo do DTO
	 * @param pk
	 *            Ccso seja chave primária
	 * @param sequence
	 *            Preenchido com sequence
	 * @param auto
	 *            Valor default ou auto incremento
	 * @param unique
	 *            Se pode ou não possuir valor duplicado
	 */
	public Field(String fieldDB, String fieldClass, boolean pk,
			boolean sequence, boolean auto, boolean unique) {
		super();
		this.fieldDB = fieldDB;
		this.fieldClass = fieldClass;
		this.pk = pk;
		this.sequence = sequence;
		this.auto = auto;
		this.unique = unique;
	}

	/**
	 * Construtor Field com o campo de descrição para unique
	 * 
	 * @param fieldDB
	 *            Nome do campo no banco de dados.
	 * @param fieldClass
	 *            Nome do atributo do DTO
	 * @param pk
	 *            Ccso seja chave primária
	 * @param sequence
	 *            Preenchido com sequence
	 * @param auto
	 *            Valor default ou auto incremento
	 * @param unique
	 *            Se pode ou não possuir valor duplicado
	 * @param msgReturn
	 *            de retorno caso aconteça duplicação de campos acionado pelo
	 *            parâmetro 'unique'
	 */
	public Field(String fieldDB, String fieldClass, boolean pk,
			boolean sequence, boolean auto, boolean unique, String msgReturn) {
		super();
		this.fieldDB = fieldDB;
		this.fieldClass = fieldClass;
		this.pk = pk;
		this.sequence = sequence;
		this.auto = auto;
		this.unique = unique;
		this.msgReturn = msgReturn;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public String getFieldClass() {
		return fieldClass;
	}

	public void setFieldClass(String campoClasse) {
		this.fieldClass = campoClasse;
	}

	public String getFieldDB() {
		return fieldDB;
	}

	public void setFieldDB(String campoDB) {
		this.fieldDB = campoDB;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public boolean isSequence() {
		return sequence;
	}

	public void setSequence(boolean sequence) {
		this.sequence = sequence;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public void setMsgReturn(String msgReturn) {
		this.msgReturn = msgReturn;
	}

	public String getMsgReturn() {
		return msgReturn;
	}

}
