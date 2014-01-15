package br.com.centralit.citgerencial.generateservices.requisicaoMudanca;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RiscoDTO;
import br.com.centralit.citcorpore.integracao.GraficosDao;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citgerencial.bean.GerencialGenerateService;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

/**
 * @author rodrigo.oliveira
 * @since 14/08/2012
 */
public class GenerateServiceMudancaPorSLA extends GerencialGenerateService {

	private HashMap novoParametro = new HashMap();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List execute(HashMap parametersValues, Collection paramtersDefinition) throws ParseException {
		
	    Set set = parametersValues.entrySet();
	    Iterator i = set.iterator();

	    while(i.hasNext()){
	      Map.Entry entrada = (Map.Entry)i.next();
	      getNovoParametro().put(entrada.getKey(), entrada.getValue());
	    }
	    
		String datainicial = (String) getNovoParametro().get("PARAM.dataInicial");
		String datafinal = (String) getNovoParametro().get("PARAM.dataFinal");
		
		Date datafim = new Date();
		Date datainicio = new Date();
		SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
		try {
			datainicio = new SimpleDateFormat("dd/MM/yyyy").parse(datainicial);
			datafim = new SimpleDateFormat("dd/MM/yyyy").parse(datafinal); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(datafim);  
		calendar.add(GregorianCalendar.DATE, 1);  
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.MYSQL)){
		    getNovoParametro().put("PARAM.dataInicial", formatoBanco.format(datainicio));
		    getNovoParametro().put("PARAM.dataFinal", formatoBanco.format(calendar.getTime()));
		}else{
		    getNovoParametro().put("PARAM.dataInicial", new java.sql.Date(datainicio.getTime()));
    		    getNovoParametro().put("PARAM.dataFinal", new java.sql.Date(calendar.getTime().getTime()));			    
		}
		
		GraficosDao graficosDao = new GraficosDao();
		Object[] listaGenericaPrazo = new Object[2];
		Object[] listaGenericaAtrazo = new Object[2];
		List listaRetorno = new ArrayList();
		String descricaoPrazo = "";
		String descricaoAtrazo = "";
		int qtdePrazo = 0;
		int qtdeAtrazo = 0;
		long atrasoSLA = 0;
		
		try {
			ArrayList<RequisicaoMudancaDTO> colPorSLA = (ArrayList<RequisicaoMudancaDTO>) graficosDao.consultaMudancaPorSLAAtrazo(getNovoParametro(), "I");
			if (colPorSLA != null) {
				for (RequisicaoMudancaDTO requisicaoMudancaDTO: colPorSLA) {
					if (requisicaoMudancaDTO.getDataHoraTermino() != null) {
						Timestamp dataHoraLimite = requisicaoMudancaDTO.getDataHoraTermino();
						Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
						//if (!requisicaoMudancaDTO.getStatus().equals("Concluida")) {
							if (requisicaoMudancaDTO.getDataHoraConclusao() != null) {
								dataHoraComparacao = requisicaoMudancaDTO.getDataHoraConclusao();
							}
							if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
								atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;
								requisicaoMudancaDTO.setAtraso(atrasoSLA);
								String atrasoSLAStr = requisicaoMudancaDTO.getAtrasoStr();
								descricaoAtrazo += "\n" + requisicaoMudancaDTO.getTitulo() + " Atraso: " + atrasoSLAStr;
								qtdeAtrazo++;
							}else{
								if (requisicaoMudancaDTO.getPrazoHH() > 0 || requisicaoMudancaDTO.getPrazoMM() > 0) {
									descricaoPrazo += "\n" + "\n" + requisicaoMudancaDTO.getTitulo();
								    String prazoHH = " Prazo: ";
								    String PrazoMM = "";
									if (requisicaoMudancaDTO.getPrazoHH() < 10) {
										prazoHH += "0" + requisicaoMudancaDTO.getPrazoHH() + ":";
									}else{
										prazoHH += requisicaoMudancaDTO.getPrazoHH() + ":";
									}
									if (requisicaoMudancaDTO.getPrazoMM() < 10) {
										PrazoMM += "0" + requisicaoMudancaDTO.getPrazoMM();
									}else{
										PrazoMM += requisicaoMudancaDTO.getPrazoMM();
									}
									descricaoPrazo += prazoHH += PrazoMM;
									qtdePrazo++;
								}
							//}
						}
					}
				}
				if (descricaoAtrazo.trim().length() > 0) {
					listaGenericaAtrazo[0] = descricaoAtrazo +    "       ";
				}else{
					listaGenericaAtrazo[0] = "Atraso: ";
				}
				listaGenericaAtrazo[1] = qtdeAtrazo;
				if (descricaoPrazo.trim().length() > 0) {
					listaGenericaPrazo[0] = descricaoPrazo +    "     	";
				}else{
					listaGenericaPrazo[0] = "Prazo: ";
				}
				listaGenericaPrazo[1] = qtdePrazo;
				listaRetorno.add(listaGenericaAtrazo);
				listaRetorno.add(listaGenericaPrazo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//resetando parâmetro
		setNovoParametro(null);
		
		return listaRetorno;
	}
	
	@SuppressWarnings("rawtypes")
	public HashMap getNovoParametro() {
		return novoParametro;
	}

	public void setNovoParametro(HashMap novoParametro) {
		this.novoParametro = novoParametro;
	}

}
