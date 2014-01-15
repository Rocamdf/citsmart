package br.com.centralit.bpm.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.PropriedadeElementoDTO;
import br.com.citframework.util.Reflexao;




public class Design {
	
	private List<PropriedadeElementoDTO> propriedades;
	private List<ElementoFluxoDTO> elementos;

	public List<PropriedadeElementoDTO> getPropriedades() {
		return propriedades;
	}

	public void setPropriedades(List<PropriedadeElementoDTO> propriedades) {
		this.propriedades = propriedades;
	}

	public PropriedadeElementoDTO getPropriedade(String id) {
		if (this.propriedades == null)
			return null;
		
		PropriedadeElementoDTO result = null;
		for (PropriedadeElementoDTO propriedadeDto : this.propriedades) {
			if (propriedadeDto.getId().equalsIgnoreCase(id)) {
				result = propriedadeDto;
				break;
			}
		}
		return result;
	}
	
	public ElementoFluxoDTO getElemento(String tipo) {
		if (this.elementos == null)
			return null;
		
		ElementoFluxoDTO result = null;
		for (ElementoFluxoDTO elementoDto : this.elementos) {
			if (elementoDto.getTipoElemento().equalsIgnoreCase(tipo)) {
				result = elementoDto;
				break;
			}
		}
		return result;
	}

	public List<ElementoFluxoDTO> getElementos() {
		return elementos;
	}

	public void setElementos(List<ElementoFluxoDTO> elementos) {
		this.elementos = elementos;
	}

	public void configuraElementos() {
		if (this.propriedades == null || this.elementos == null)
			return;
		
		HashMap<String, PropriedadeElementoDTO> mapProp = new HashMap();
		for (PropriedadeElementoDTO propriedade : this.propriedades) {
			if (propriedade != null) {
			    if (propriedade.getValorDefault() == null)
			        propriedade.setValorDefault("");
				mapProp.put(propriedade.getId(), propriedade);
			}
		}
		
		for (ElementoFluxoDTO elemento : this.elementos) {
			if (elemento != null && elemento.getLstPropriedades() != null) {
				List<PropriedadeElementoDTO> lst = new ArrayList();
				for (int i = 0; i < elemento.getLstPropriedades().length; i++) {
					if (mapProp.get(elemento.getLstPropriedades()[i]) != null) {
					    PropriedadeElementoDTO propriedade = mapProp.get(elemento.getLstPropriedades()[i]);
						lst.add(propriedade);
						try {
							Reflexao.setPropertyValue(elemento, elemento.getLstPropriedades()[i], propriedade.getValorDefault());
						} catch (Exception e) {
						}
					}
				}
				elemento.setPropriedades(lst);
			}
		}
	}
	
}
