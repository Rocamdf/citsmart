package br.com.centralit.bpm.dto;

import java.sql.Date;
import java.util.List;

import br.com.citframework.dto.IDto;

public class FluxoDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8653101480124828990L;
	private Integer idFluxo;
	private String nomeFluxo;
	private String descricao;
    private String nomeClasseFluxo;
	
	private Integer idTipoFluxo;
	private String versao;
	private String variaveis;
	private String conteudoXml;
	private Date dataInicio;
	private Date dataFim;
	
	private Integer idTarefa;
	private String criar;
	private String executar;	
	private String delegar;	
	private String suspender;
	
	private String acaoFluxo;
	private String usuarioDestino;	
	
	private String identificador;
	
	private Integer idInstanciaFluxo;
	
	private ElementoFluxoDTO inicioFluxo;
	private String[] colVariaveis;

	private List<ElementoFluxoDTO> colElementos; 
	private List<ElementoFluxoRaiaDTO> colRaias; 
	private List<ElementoFluxoDTO> colTarefas; 
	private List<ElementoFluxoDTO> colPortas; 
	private List<ElementoFluxoDTO> colScripts; 
    private List<ElementoFluxoDTO> colEventos; 
	private List<ElementoFluxoDTO> colEmails; 
	private List<SequenciaFluxoDTO> colSequenciamentos; 
	private List<ElementoFluxoDTO> colFinalizacoes;
	
    private String elemento_serializado;
    private String sequencia;
    private String elementos_serializados;
    private String sequencias_serializadas;
    private String linhaAtual;
    
    private String acao;

	public Integer getIdFluxo() {
		return idFluxo;
	}
	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}
	public String getNomeFluxo() {
		return nomeFluxo;
	}
	public void setNomeFluxo(String nomeFluxo) {
		this.nomeFluxo = nomeFluxo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIdTipoFluxo() {
		return idTipoFluxo;
	}
	public void setIdTipoFluxo(Integer idTipoFluxo) {
		this.idTipoFluxo = idTipoFluxo;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public String getVariaveis() {
		return variaveis;
	}
	public void setVariaveis(String variaveis) {
		if (variaveis != null) 
			this.colVariaveis = variaveis.split(";");
		else
			this.colVariaveis = new String[]{};
		this.variaveis = variaveis;
	}
	public String getConteudoXml() {
		return conteudoXml;
	}
	public void setConteudoXml(String conteudoXml) {
		this.conteudoXml = conteudoXml;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Integer getIdTarefa() {
		return idTarefa;
	}
	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}
	public String getCriar() {
		return criar;
	}
	public void setCriar(String criar) {
		this.criar = criar;
	}
	public String getExecutar() {
		return executar;
	}
	public void setExecutar(String executar) {
		this.executar = executar;
	}
	public String getDelegar() {
		return delegar;
	}
	public void setDelegar(String delegar) {
		this.delegar = delegar;
	}
	public String getSuspender() {
		return suspender;
	}
	public void setSuspender(String suspender) {
		this.suspender = suspender;
	}
	public String getAcaoFluxo() {
		return acaoFluxo;
	}
	public void setAcaoFluxo(String acaoFluxo) {
		this.acaoFluxo = acaoFluxo;
	}
	public String getUsuarioDestino() {
		return usuarioDestino;
	}
	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Integer getIdInstanciaFluxo() {
		return idInstanciaFluxo;
	}
	public void setIdInstanciaFluxo(Integer idInstanciaFluxo) {
		this.idInstanciaFluxo = idInstanciaFluxo;
	}
	public ElementoFluxoDTO getInicioFluxo() {
		return inicioFluxo;
	}
	public void setInicioFluxo(ElementoFluxoDTO inicioFluxo) {
		this.inicioFluxo = inicioFluxo;
	}
	public String[] getColVariaveis() {
		return colVariaveis;
	}
	public void setColVariaveis(String[] colVariaveis) {
		this.variaveis = "";
		if (colVariaveis != null && colVariaveis.length > 0) {
			for (int i = 0; i < colVariaveis.length; i++) {
				if (i > 0)
					this.variaveis += ";";
				this.variaveis += colVariaveis[i];
			}
		}
		this.colVariaveis = colVariaveis;
	}
	public List<ElementoFluxoDTO> getColTarefas() {
		return colTarefas;
	}
	public void setColTarefas(List<ElementoFluxoDTO> colTarefas) {
		this.colTarefas = colTarefas;
	}
	public List<ElementoFluxoDTO> getColPortas() {
		return colPortas;
	}
	public void setColPortas(List<ElementoFluxoDTO> colPortas) {
		this.colPortas = colPortas;
	}
	public List<ElementoFluxoDTO> getColScripts() {
		return colScripts;
	}
	public void setColScripts(List<ElementoFluxoDTO> colScripts) {
		this.colScripts = colScripts;
	}
	public List<SequenciaFluxoDTO> getColSequenciamentos() {
		return colSequenciamentos;
	}
	public void setColSequenciamentos(
			List<SequenciaFluxoDTO> colSequenciamentos) {
		this.colSequenciamentos = colSequenciamentos;
	}
	public List<ElementoFluxoDTO> getColFinalizacoes() {
		return colFinalizacoes;
	}
	public void setColFinalizacoes(List<ElementoFluxoDTO> colFinalizacoes) {
		this.colFinalizacoes = colFinalizacoes;
	}
	public List<ElementoFluxoDTO> getColEmails() {
		return colEmails;
	}
	public void setColEmails(List<ElementoFluxoDTO> colEmails) {
		this.colEmails = colEmails;
	}
	public List<ElementoFluxoRaiaDTO> getColRaias() {
		return colRaias;
	}
	public void setColRaias(List<ElementoFluxoRaiaDTO> colRaias) {
		this.colRaias = colRaias;
	}
	public List<ElementoFluxoDTO> getColElementos() {
		return colElementos;
	}
	public void setColElementos(List<ElementoFluxoDTO> colElementos) {
		this.colElementos = colElementos;
	}
    public List<ElementoFluxoDTO> getColEventos() {
        return colEventos;
    }
    public void setColEventos(List<ElementoFluxoDTO> colEventos) {
        this.colEventos = colEventos;
    }
    public String getNomeClasseFluxo() {
        return nomeClasseFluxo;
    }
    public void setNomeClasseFluxo(String nomeClasseFluxo) {
        this.nomeClasseFluxo = nomeClasseFluxo;
    }
	public String getElemento_serializado() {
		return elemento_serializado;
	}
	public void setElemento_serializado(String elemento_serializado) {
		this.elemento_serializado = elemento_serializado;
	}
	public String getSequencia() {
		return sequencia;
	}
	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}
	public String getElementos_serializados() {
		return elementos_serializados;
	}
	public void setElementos_serializados(String elementos_serializados) {
		this.elementos_serializados = elementos_serializados;
	}
	public String getSequencias_serializadas() {
		return sequencias_serializadas;
	}
	public void setSequencias_serializadas(String sequencias_serializadas) {
		this.sequencias_serializadas = sequencias_serializadas;
	}
	public String getLinhaAtual() {
		return linhaAtual;
	}
	public void setLinhaAtual(String linhaAtual) {
		this.linhaAtual = linhaAtual;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	} 
	
}
