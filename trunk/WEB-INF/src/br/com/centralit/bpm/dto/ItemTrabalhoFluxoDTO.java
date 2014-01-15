package br.com.centralit.bpm.dto;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateTimeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ItemTrabalhoFluxo")
public class ItemTrabalhoFluxoDTO implements IDto {
	@XmlElement(name = "IdItemTrabalho")
	private Integer idItemTrabalho;

	@XmlElement(name = "IdInstancia")
	private Integer idInstancia;

	@XmlElement(name = "IdElemento")
	private Integer idElemento;

	@XmlElement(name = "IdResponsavelAtual")
	private Integer idResponsavelAtual;

	@XmlElement(name = "DataHoraCriacao")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraCriacao;

	@XmlElement(name = "DataHoraInicio")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraInicio;

	@XmlElement(name = "DataHoraFinalizacao")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraFinalizacao;

	@XmlElement(name = "DataHoraExecucao")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	private Timestamp dataHoraExecucao;

	@XmlElement(name = "Situacao")
	private String situacao;
	@XmlElement(name = "DescrSituacao")
	private String descrSituacao;

	@XmlElement(name = "ResponsavelAtual")
	private String responsavelAtual;

	@XmlElement(name = "Compartilhamento")
	private String compartilhamento;

	@XmlElement(name = "ElementoFluxo")
	private ElementoFluxoDTO elementoFluxoDto;

	private Integer idFluxo;

	public Integer getIdItemTrabalho() {
		return idItemTrabalho;
	}

	public void setIdItemTrabalho(Integer idItemTrabalho) {
		this.idItemTrabalho = idItemTrabalho;
	}

	public Integer getIdInstancia() {
		return idInstancia;
	}

	public void setIdInstancia(Integer idInstancia) {
		this.idInstancia = idInstancia;
	}

	public Integer getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(Integer idElemento) {
		this.idElemento = idElemento;
	}

	public Integer getIdResponsavelAtual() {
		return idResponsavelAtual;
	}

	public void setIdResponsavelAtual(Integer idResponsavelAtual) {
		this.idResponsavelAtual = idResponsavelAtual;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
		try {
			if (this.situacao != null)
				this.descrSituacao = SituacaoItemTrabalho.valueOf(this.situacao.trim()).getDescricao();
		} catch (Exception e) {
		}

	}

	public ElementoFluxoDTO getElementoFluxoDto() {
		return elementoFluxoDto;
	}

	public void setElementoFluxoDto(ElementoFluxoDTO elementoFluxoDto) {
		this.elementoFluxoDto = elementoFluxoDto;
	}

	public Timestamp getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public void setDataHoraCriacao(Timestamp dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}

	public String getResponsavelAtual() {
		return responsavelAtual;
	}

	public void setResponsavelAtual(String responsavelAtual) {
		this.responsavelAtual = responsavelAtual;
	}

	public Timestamp getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Timestamp dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Timestamp getDataHoraFinalizacao() {
		return dataHoraFinalizacao;
	}

	public void setDataHoraFinalizacao(Timestamp dataHoraFinalizacao) {
		this.dataHoraFinalizacao = dataHoraFinalizacao;
	}

	public String getCompartilhamento() {
		return compartilhamento;
	}

	public void setCompartilhamento(String compartilhamento) {
		this.compartilhamento = compartilhamento;
	}

	public Timestamp getDataHoraExecucao() {
		return dataHoraExecucao;
	}

	public void setDataHoraExecucao(Timestamp dataHoraExecucao) {
		this.dataHoraExecucao = dataHoraExecucao;
	}

	public String getDescrSituacao() {
		return descrSituacao;
	}

	public void setDescrSituacao(String descrSituacao) {
		this.descrSituacao = descrSituacao;
	}

	public Integer getIdFluxo() {
		return idFluxo;
	}

	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}

}
