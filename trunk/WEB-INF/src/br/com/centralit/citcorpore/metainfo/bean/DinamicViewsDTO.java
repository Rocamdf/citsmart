package br.com.centralit.citcorpore.metainfo.bean;

import java.util.Collection;
import java.util.HashMap;

import br.com.citframework.dto.IDto;

public class DinamicViewsDTO implements IDto {
	private Integer idVisao;
	private Integer idVisaoEdit;
	private Integer idVisaoPesquisa;
	private Integer dinamicViewsIdVisao;
	private Integer dinamicViewsIdVisaoPesquisaSelecionada;
	private String dinamicViewsAcaoPesquisaSelecionada;
	private VisaoDTO visaoDto;
	private Collection colGrupos;
	
	private String dinamicViewsJson_data;
	private String jsonMatriz;
	private String jsonDataEdit;
	private String dinamicViewsDadosAdicionais;
	private String dinamicViewsJson_tempData;
	private String keyControl;
	
	private HashMap<String, Object> dinamicViewsMapDadosAdicional;
	
	private String modoExibicao;
	private Integer idFluxo;
	private Integer idTarefa;
	private String acaoFluxo;
	
	private String identificacao;
	
	private String id;
	private String saveInfo;
	
	private String msgRetorno = "";
	
	private boolean abortFuncaoPrincipal = false;
	
	public Collection getColGrupos() {
		return colGrupos;
	}

	public void setColGrupos(Collection colGrupos) {
		this.colGrupos = colGrupos;
	}

	public Integer getIdVisao() {
		return idVisao;
	}

	public void setIdVisao(Integer idVisao) {
		this.idVisao = idVisao;
	}

	public VisaoDTO getVisaoDto() {
		return visaoDto;
	}

	public void setVisaoDto(VisaoDTO visaoDto) {
		this.visaoDto = visaoDto;
	}

	public Integer getDinamicViewsIdVisao() {
		return dinamicViewsIdVisao;
	}

	public void setDinamicViewsIdVisao(Integer dinamicViewsIdVisao) {
		this.dinamicViewsIdVisao = dinamicViewsIdVisao;
	}

	public Integer getIdVisaoPesquisa() {
		return idVisaoPesquisa;
	}

	public void setIdVisaoPesquisa(Integer idVisaoPesquisa) {
		this.idVisaoPesquisa = idVisaoPesquisa;
	}

	public String getDinamicViewsJson_data() {
		return dinamicViewsJson_data;
	}

	public void setDinamicViewsJson_data(String dinamicViewsJson_data) {
		this.dinamicViewsJson_data = dinamicViewsJson_data;
	}

	public Integer getDinamicViewsIdVisaoPesquisaSelecionada() {
		return dinamicViewsIdVisaoPesquisaSelecionada;
	}

	public void setDinamicViewsIdVisaoPesquisaSelecionada(
			Integer dinamicViewsIdVisaoPesquisaSelecionada) {
		this.dinamicViewsIdVisaoPesquisaSelecionada = dinamicViewsIdVisaoPesquisaSelecionada;
	}

	public String getDinamicViewsAcaoPesquisaSelecionada() {
		return dinamicViewsAcaoPesquisaSelecionada;
	}

	public void setDinamicViewsAcaoPesquisaSelecionada(
			String dinamicViewsAcaoPesquisaSelecionada) {
		this.dinamicViewsAcaoPesquisaSelecionada = dinamicViewsAcaoPesquisaSelecionada;
	}

	public String getDinamicViewsDadosAdicionais() {
		return dinamicViewsDadosAdicionais;
	}

	public void setDinamicViewsDadosAdicionais(String dinamicViewsDadosAdicionais) {
		this.dinamicViewsDadosAdicionais = dinamicViewsDadosAdicionais;
	}

	public HashMap<String, Object> getDinamicViewsMapDadosAdicional() {
		return dinamicViewsMapDadosAdicional;
	}

	public void setDinamicViewsMapDadosAdicional(
			HashMap<String, Object> dinamicViewsMapDadosAdicional) {
		this.dinamicViewsMapDadosAdicional = dinamicViewsMapDadosAdicional;
	}

	public Integer getIdVisaoEdit() {
		return idVisaoEdit;
	}

	public void setIdVisaoEdit(Integer idVisaoEdit) {
		this.idVisaoEdit = idVisaoEdit;
	}

	public String getJsonDataEdit() {
		return jsonDataEdit;
	}

	public void setJsonDataEdit(String jsonDataEdit) {
		this.jsonDataEdit = jsonDataEdit;
	}

	public Integer getIdFluxo() {
		return idFluxo;
	}

	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}

	public Integer getIdTarefa() {
		return idTarefa;
	}

	public String getModoExibicao() {
		return modoExibicao;
	}

	public void setModoExibicao(String modoExibicao) {
		this.modoExibicao = modoExibicao;
	}

	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}

	public String getAcaoFluxo() {
		return acaoFluxo;
	}

	public void setAcaoFluxo(String acaoFluxo) {
		this.acaoFluxo = acaoFluxo;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSaveInfo() {
		return saveInfo;
	}

	public void setSaveInfo(String saveInfo) {
		this.saveInfo = saveInfo;
	}

	public boolean isAbortFuncaoPrincipal() {
	    return abortFuncaoPrincipal;
	}

	public void setAbortFuncaoPrincipal(boolean abortFuncaoPrincipal) {
	    this.abortFuncaoPrincipal = abortFuncaoPrincipal;
	}

	public String getMsgRetorno() {
	    return msgRetorno;
	}

	public void setMsgRetorno(String msgRetorno) {
	    this.msgRetorno = msgRetorno;
	}

	public String getJsonMatriz() {
		return jsonMatriz;
	}

	public void setJsonMatriz(String jsonMatriz) {
		this.jsonMatriz = jsonMatriz;
	}

	public String getDinamicViewsJson_tempData() {
		return dinamicViewsJson_tempData;
	}

	public void setDinamicViewsJson_tempData(String dinamicViewsJson_tempData) {
		this.dinamicViewsJson_tempData = dinamicViewsJson_tempData;
	}

	public String getKeyControl() {
		return keyControl;
	}

	public void setKeyControl(String keyControl) {
		this.keyControl = keyControl;
	}

}
