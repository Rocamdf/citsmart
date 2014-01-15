package br.com.citframework.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import br.com.citframework.excecao.LogicException;

public class UtilDatas {
	private static final String ZERO_HORAS_E_MINUTOS_4DGT = "00:00h";
	private static String[] dias = { "31", "30", "31", "30", "31", "30", "31", "31", "30", "31", "30", "31" };
	public static String[] meses = { "citcorpore.texto.mes.janeiro", "citcorpore.texto.mes.fevereiro", "citcorpore.texto.mes.marco", "citcorpore.texto.mes.abril", "citcorpore.texto.mes.maio",
			"citcorpore.texto.mes.junho", "citcorpore.texto.mes.julho", "citcorpore.texto.mes.agosto", "citcorpore.texto.mes.setembro", "citcorpore.texto.mes.outubro",
			"citcorpore.texto.mes.novembro", "citcorpore.texto.mes.dezembro" };

	/**
	 * Retorna a data corrente
	 * 
	 * @return String
	 */
	public static String getDataCorrenteStr() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(new Date());
	}

	/**
	 * Retorna o dia da semana domingo = 1 segunda = 2 terca = 3 quarta = 4 quinta = 5 sexta = 6 sabado = 7
	 * 
	 * @param dataConsulta
	 *            Date
	 * @return int
	 * @throws LogicException
	 */
	public static int getDiaSemana(String dataConsulta) throws LogicException {
		GregorianCalendar data = new GregorianCalendar();
		SimpleDateFormat dt = null;
		try {
			if (dataConsulta.indexOf("-") > -1) {
				dt = new SimpleDateFormat("yyyy-MM-dddd");
			} else {
				dt = new SimpleDateFormat("dd/MM/yyyy");
			}
			data.setTime(dt.parse(dataConsulta));
		} catch (ParseException e) {
			throw new LogicException(Mensagens.getValue("MSG03"));
		}
		return data.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Retorna o dia da semana domingo = dom segunda = seg terca = ter quarta = qua quinta = qui sexta = sex sabado = sab
	 * 
	 * @param data
	 *            Date
	 * @return String
	 * @throws LogicException
	 */
	public static String getDiaSemana(Date data) throws LogicException {
		int dia = getDiaSemana(UtilDatas.dateToSTR(data));
		if (dia == 1) {
			return "dom";
		} else if (dia == 2) {
			return "seg";
		} else if (dia == 3) {
			return "ter";
		} else if (dia == 4) {
			return "qua";
		} else if (dia == 5) {
			return "qui";
		} else if (dia == 6) {
			return "sex";
		} else if (dia == 7) {
			return "sab";
		} else {
			return "";
		}
	}

	/**
	 * Retorna a hora formatada
	 * 
	 * @return String
	 */
	public static String formatHoraFormatadaStr(Date hora) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(hora);
	}

	public static String formatHoraFormatadaHHMMSSStr(Date hora) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(hora);
	}

	public static String formatTimestamp(Timestamp dataHora) {
		if (dataHora == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return format.format(dataHora);
	}

	public static String formatTimestampUS(Timestamp dataHora) {
		if (dataHora == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(dataHora);
	}

	/**
	 * Formata uma hora String passada como parametro. Exemplo: 1000 e retorna 10:00
	 * 
	 * @param hora
	 * @return
	 */
	public static String formatHoraStr(String hora) {
		return hora.substring(0, 2) + ":" + hora.substring(2, 4);
	}

	/**
	 * Formata uma hora String passada como parametro. Exemplo: 8:00 e retorna 0800
	 * 
	 * @param hora
	 * @return
	 */
	public static String formatHoraHHMM(String hora) {
		if (UtilStrings.isNotVazio(hora)) {
			String aux = hora.replaceFirst(":", "");
			if (UtilStrings.isNotVazio(aux)) {
				for (int i = aux.length(); i < 4; i++) {
					aux = "0" + aux;
				}
				return aux;
			}
		}
		return "";
	}

	public static final Date strTodate(String data) throws LogicException {
		if (data == null || data.length() == 0)
			return null;
		try {
			SimpleDateFormat date = null;
			if (data.indexOf("-") > -1) {
				if(data.length() == 10)
					date = new SimpleDateFormat("yyyy-MM-dddd");
				else
					date = new SimpleDateFormat("yyyy-MM-dddd HH:mm:ss");
			} else if (data.length() == 10)
				date = new SimpleDateFormat("dd/MM/yyyy");
			else
				date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			if (data.equals("") || data.length() == 0) {
				return null;
			} else {
				return date.parse(data);
			}
		} catch (Exception e) {
			throw new LogicException(Mensagens.getValue("MSG03"));
		}
	}

	/**
	 * Gera uma timestamp a partir de uma string passada como parametro
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final Timestamp strToTimestamp(String data) throws Exception {
		if (data == null || data.length() == 0) {
			return null;
		}
		Timestamp result = new Timestamp(strTodate(data).getTime());

		return result;
	}

	/**
	 * Gera um java.sql.Date a partir de uma Data String
	 * 
	 * @param data
	 * @return
	 * @throws LogicException
	 */
	public static final java.sql.Date strToSQLDate(String data) throws LogicException {
		if (data == null || data.length() == 0)
			return null;
		return new java.sql.Date(strTodate(data).getTime());
	}

	/**
	 * Retorna a 1.a data do mes/ano Exemplo: se passar a data 23/01/2007, retornará 01/01/2007.
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final Date getPrimeiraDataMes(Date data) throws Exception {
		SimpleDateFormat spd = new SimpleDateFormat("MM/yyyy");
		String mesAno = spd.format(data);
		mesAno = "01/" + mesAno;
		spd = new SimpleDateFormat("dd/MM/yyyy");
		return spd.parse(mesAno);
	}

	/**
	 * Retorna a Ultima data do mes/ano - de acordo com o mes (Considerando ano bisexto) Exemplo: se passar a data 23/01/2007, retornará 31/01/2007.
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final Date getUltimaDataMes(Date data) throws ParseException {
		SimpleDateFormat spd = new SimpleDateFormat("MM");
		String tmp = spd.format(data);
		int ind = Integer.parseInt(tmp);
		if (ind != 2)
			tmp = dias[ind - 1];
		else {
			spd = new SimpleDateFormat("yyyy");
			String sAno = spd.format(data);
			int iAno = Integer.parseInt(sAno);
			if ((iAno % 4 == 0) && (iAno % 100 != 0))
				tmp = "29";
			else
				tmp = "28";
		}
		spd = new SimpleDateFormat("MM/yyyy");
		String mesAno = spd.format(data);
		mesAno = tmp + "/" + mesAno;
		spd = new SimpleDateFormat("dd/MM/yyyy");
		return spd.parse(mesAno);
	}

	/**
	 * Acrescenta Meses em uma data, caso queira subtrair meses, basta passar o segundo parametro negativo. Exemplo: acrescentaSubtraiMesesData(new Date(), 3); acrescentaSubtraiMesesData(new Date(),
	 * -4);
	 * 
	 * @param data
	 * @param mes
	 *            (numero de meses a acrescentar ou retirar), pode ser negativo.
	 * @return retorna a data final calculada.
	 * @throws LogicException
	 */
	public static final Date acrescentaSubtraiMesesData(Date data, int mes) throws LogicException {
		if (data == null)
			return null;

		int mesCalculo = getDiaMesAno(data, 2); // Pega o mes
		int anoCalculo = getDiaMesAno(data, 3); // Pega o ano
		int dia = getDiaMesAno(data, 1); // Pega o dia
		int soma = mesCalculo + mes;
		if (soma < 0) {
			soma = soma + 1;
			soma = 12 + soma;
			anoCalculo = anoCalculo - 1;
		}

		if (soma == 0) {
			soma = 12;
			anoCalculo--;
		}

		while (soma > 12) {
			soma = soma - 12;
			anoCalculo++;
		}
		String sDia = new Integer(dia).toString();
		if (sDia.length() == 1)
			sDia = "0" + sDia;
		String sMes = new Integer(soma).toString();
		if (sMes.length() == 1)
			sMes = "0" + sMes;

		return strTodate(sDia + "/" + sMes + "/" + anoCalculo);
	}

	/**
	 * Extrai dia, mes ou ano de uma data )
	 * 
	 * @param data
	 * @param ind
	 *            (Sendo 1 - Dia; 2 - Mes; 3 - Ano)
	 * @return Retorna dia , mes ou ano da data passada como parametro, segundo o Indice Passado. Ex: 1 - Dia 2 - Mes 3 - Ano
	 */
	public final static int getDiaMesAno(Date data, int ind) {
		if (data == null) {
			return 0;
		}
		if (ind == 1)// dia
		{
			SimpleDateFormat spd = new SimpleDateFormat("dd");
			return new Integer(spd.format(data)).intValue();
		}
		if (ind == 2)// mes
		{
			SimpleDateFormat spd = new SimpleDateFormat("MM");
			return new Integer(spd.format(data)).intValue();
		}
		if (ind == 3)// ano
		{
			SimpleDateFormat spd = new SimpleDateFormat("yyyy");
			return new Integer(spd.format(data)).intValue();
		}
		return 0;
	}

	/**
	 * Obtem a diferenca em mses de 2 datas.
	 * 
	 * @param inic
	 * @param fim
	 * @return quantidade de meses
	 */
	public final static int getDifMeses(Date inic, Date fim) {
		int anoInic = getDiaMesAno(inic, 3);
		int anoFim = getDiaMesAno(fim, 3);
		int mesInic = getDiaMesAno(inic, 2);
		int mesFim = getDiaMesAno(fim, 2);
		int difAno = 0;
		int difMes = 0;
		if (anoInic < anoFim) {
			difAno = anoFim - anoInic;
			difAno = difAno * 12;
		}
		difMes = (mesFim + difAno) - mesInic;
		return difMes + 1;
	}

	/**
	 * Obtem a descricao do Mes passado como parametro Exemplo: getDescricaoMes(12), retorna: Dezembro
	 * 
	 * @param mes
	 * @return
	 */
	public static final String getDescricaoMes(Integer mes) {
		if (mes.intValue() > 0 && mes.intValue() < 13) {
			int iMes = mes.intValue() - 1;
			return meses[iMes];
		} else
			return "";
	}

	/**
	 * Altera uma data de acordo com os parametros
	 * 
	 * @param data
	 * @param quantidade
	 * @param unidade
	 * @return
	 */
	public static final Date alteraData(Date data, int quantidade, int unidade) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.add(unidade, quantidade);
		return calendar.getTime();
	}

	/**
	 * Obtem uma data que representa o proximo mes de uma data passada como parametro.
	 * 
	 * @param data
	 * @return
	 */
	public static final Date getProximoMes(Date data) {
		int mes = getDiaMesAno(data, 2);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.set(Calendar.MONTH, mes++);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * Obtem o ultimo dia do mes da data passada como parametro.
	 * 
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	public static final int getUltimoDiaMes(Date data) throws ParseException {
		Date datTmp = getUltimaDataMes(data);
		return getDiaMesAno(datTmp, 1);
	}

	/**
	 * Gera um java.sql.Date a partir de um objeto Date
	 * 
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	public static final java.sql.Date getSqlDate(Date data) throws ParseException {
		SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy 00:00:00");
		SimpleDateFormat spd1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		data = spd1.parse(spd.format(data));
		java.sql.Date datasql = new java.sql.Date(data.getTime());
		return datasql;
	}

	/**
	 * Formata um objeto java.sql.Date para String no formato dd/MM/yyyy
	 * 
	 * @param data
	 * @return
	 */
	public static final String dateToSTR(java.sql.Date data) {
		if (data == null)
			return null;
		return dateToSTR(new Date(data.getTime()));
	}

	/**
	 * Formata um objeto Date para String no formato dd/MM/yyyy
	 * 
	 * @param data
	 * @return
	 */
	public static final String dateToSTR(Date data) {
		if (data == null)
			return "";
		SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");
		return spd.format(data).trim();
	}

	/**
	 * Formata um objeto java.sql.Date para String no formato passado como parametro.
	 * 
	 * @param data
	 * @return
	 */
	public static final String dateToSTR(Date data, String formato) {
		if (data == null)
			return "";
		SimpleDateFormat spd = new SimpleDateFormat(formato);
		return spd.format(data).trim();
	}

	public static String dateToSTRExtenso(java.sql.Date data, boolean withDescDiaSemana) {
		String diaf = null;
		String mesf = null;
		String retorno = null;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		int semana = calendar.get(Calendar.DAY_OF_WEEK);
		int mes = calendar.get(Calendar.MONTH);
		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int ano = calendar.get(Calendar.YEAR);

		// semana
		switch (semana) {
		case 1:
			diaf = "Domingo";
			break;
		case 2:
			diaf = "Segunda";
			break;
		case 3:
			diaf = "Terça";
			break;
		case 4:
			diaf = "Quarta";
			break;
		case 5:
			diaf = "Quinta";
			break;
		case 6:
			diaf = "Sexta";
			break;
		case 7:
			diaf = "Sábado";
			break;
		}
		// mês
		switch (mes) {
		case 0:
			mesf = "Janeiro";
			break;
		case 1:
			mesf = "Fevereiro";
			break;
		case 2:
			mesf = "Março";
			break;
		case 3:
			mesf = "Abril";
			break;
		case 4:
			mesf = "Maio";
			break;
		case 5:
			mesf = "Junho";
			break;
		case 6:
			mesf = "Julho";
			break;
		case 7:
			mesf = "Agosto";
			break;
		case 8:
			mesf = "Setembro";
			break;
		case 9:
			mesf = "Outubro";
			break;
		case 10:
			mesf = "Novembro";
			break;
		case 11:
			mesf = "Dezembro";
			break;
		}

		if (withDescDiaSemana) {
			retorno = diaf + ", " + dia + " de " + mesf + " de " + ano;
		} else {
			retorno = "" + dia + " de " + mesf + " de " + ano;
		}
		return retorno;
	}

	/**
	 * Retorna uma string no formato MM/yyyy de uma data passada como parametro.
	 * 
	 * @param data
	 * @return
	 */
	public static final String getMesAno(Date data) {
		if (data == null)
			return null;
		SimpleDateFormat spd = new SimpleDateFormat("MM/yyyy");
		return spd.format(data).trim();
	}

	/**
	 * Verifica se uma data eh Util, ou seja, nao cai no sabado e no domingo (nao considera feriados).
	 * 
	 * @param data
	 * @return
	 */
	public static final boolean verificaDiaUtil(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		boolean result;
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			result = false;
		} else
			result = true;
		return result;
	}

	/**
	 * Faz o calculo da idade com base na data passada como parametro.
	 * 
	 * @param dDataNasc
	 * @param type
	 *            - pode ser "SHORT" ou "LONG" (para determinar se saira: "19a 13m 5d" ou "19 anos 13 meses 5 dias"
	 * @return
	 */
	public static String calculaIdade(Date dDataNasc, String type) {
		Calendar hoje = Calendar.getInstance();
		Date now = hoje.getTime();
		return calculaIdade(dDataNasc, now, type);
	}

	public static String calculaIdade(Date dDataNasc, Date ateData, String type) {
		if (dDataNasc == null) {
			return "";
		}

		int anoResult = 0;
		int mesResult = 0;
		int diaResult = 0;
		String retorno = "";
		String strAno;
		String strAnos;
		String strMes;
		String strMeses;
		String strDia;
		String strDias;

		int anoHoje = getYear(ateData);
		int anoDataParm = getYear(dDataNasc);
		int mesHoje = getMonth(ateData);
		int mesDataParm = getMonth(dDataNasc);
		int diaHoje = getDay(ateData);
		int diaDataParm = getDay(dDataNasc);

		if ("SHORT".equalsIgnoreCase(type)) {
			strAno = strAnos = "a";
			strMes = strMeses = "m";
			strDia = strDias = "d";
		} else {
			strAno = " ano";
			strAnos = " anos";
			strMes = " mes";
			strMeses = " meses";
			strDia = " dia";
			strDias = " dias";
		}

		anoResult = anoHoje - anoDataParm;
		if ((mesHoje < mesDataParm) || ((mesHoje == mesDataParm) && (diaHoje < diaDataParm))) {
			anoResult = anoResult - 1;
		}

		mesResult = mesHoje - mesDataParm;
		if ((mesResult < 0) || ((mesHoje == mesDataParm) && (diaHoje < diaDataParm))) {
			mesResult = mesResult + 12;
		}

		diaResult = (diaHoje - diaDataParm);
		if (diaResult < 0) {
			mesResult = mesResult - 1;
			int numDiasMes = 30;
			try {
				numDiasMes = UtilDatas.getUltimoDiaMes(dDataNasc);
			} catch (ParseException e) {
				numDiasMes = 30;
			}
			diaResult = numDiasMes + diaResult;
		}

		retorno = "";
		if (anoResult > 0) {
			if (anoResult == 1) {
				retorno = "1" + strAno + " ";
			} else {
				retorno = String.valueOf(anoResult) + strAnos + " ";
			}
		}

		if (mesResult > 0) {
			if (mesResult == 1) {
				retorno = retorno + "1" + strMes + " ";
			} else {
				retorno = retorno + String.valueOf(mesResult) + strMeses + " ";
			}
		}

		if (diaResult > 0) {
			if (diaResult == 1) {
				retorno = retorno + "1" + strDia + " ";
			} else {
				retorno = retorno + String.valueOf(diaResult) + strDias + " ";
			}
		}

		return retorno;
	}

	/**
	 * Faz o calculo da idade com base na data passada como parametro (retorna apenas mes e ano)
	 * 
	 * @param dDataNasc
	 * @param type
	 *            - pode ser "SHORT" ou "LONG" (para determinar se saira: "19a 13m" ou "19 anos 13 meses"
	 * @return Retorna apenas mes e ano
	 */
	public static String calculaIdadeMesAno(Date dDataNasc, String type) {
		if (dDataNasc == null) {
			return "";
		}

		int anoResult = 0;
		int mesResult = 0;
		int diaResult = 0;
		Calendar hoje = Calendar.getInstance();
		Date now = hoje.getTime();
		String retorno = "";
		String strAno;
		String strAnos;
		String strMes;
		String strMeses;

		int anoHoje = getYear(now);
		int anoDataParm = getYear(dDataNasc);
		int mesHoje = getMonth(now);
		int mesDataParm = getMonth(dDataNasc);
		int diaHoje = getDay(now);
		int diaDataParm = getDay(dDataNasc);

		if ("SHORT".equalsIgnoreCase(type)) {
			strAno = strAnos = "a";
			strMes = strMeses = "m";
		} else {
			strAno = " ano";
			strAnos = " anos";
			strMes = " mes";
			strMeses = " meses";
		}

		anoResult = anoHoje - anoDataParm;
		if ((mesHoje < mesDataParm) || ((mesHoje == mesDataParm) && (diaHoje < diaDataParm))) {
			anoResult = anoResult - 1;
		}

		mesResult = mesHoje - mesDataParm;
		if ((mesResult < 0) || ((mesHoje == mesDataParm) && (diaHoje < diaDataParm))) {
			mesResult = mesResult + 12;
		}

		diaResult = (diaHoje - diaDataParm);
		if (diaResult < 0) {
			mesResult = mesResult - 1;
			diaResult = 30 + diaResult;
		}

		retorno = "";
		if (anoResult > 0) {
			if (anoResult == 1) {
				retorno = "1" + strAno + " ";
			} else {
				retorno = String.valueOf(anoResult) + strAnos + " ";
			}
		}

		if (mesResult > 0) {
			if (mesResult == 1) {
				retorno = retorno + "1" + strMes + " ";
			} else {
				retorno = retorno + String.valueOf(mesResult) + strMeses + " ";
			}
		}

		return retorno;
	}

	/**
	 * Faz o calculo da idade com base na data passada como parametro e a data de referencia. retorna apenas os anos retorna um numero representando a quantidade de anos.
	 * 
	 * @param dDataNasc
	 *            - Data de Nascimento
	 * @param dataRef
	 *            - Data de referencia para calculo da idade.
	 * @return
	 */
	public static Integer calculaIdadeEmAnos(Date dDataNasc, Date dataRef) {
		if (dDataNasc == null) {
			return new Integer(0);
		}
		if (dataRef == null) {
			return new Integer(0);
		}

		int anoResult = 0;

		int anoHoje = getYear(dataRef);
		int anoDataParm = getYear(dDataNasc);
		int mesHoje = getMonth(dataRef);
		int mesDataParm = getMonth(dDataNasc);
		int diaHoje = getDay(dataRef);
		int diaDataParm = getDay(dDataNasc);

		anoResult = anoHoje - anoDataParm;
		if ((mesHoje < mesDataParm) || ((mesHoje == mesDataParm) && (diaHoje < diaDataParm))) {
			anoResult = anoResult - 1;
		}

		if (anoResult > 0) {
			if (anoResult == 1) {
				return new Integer(1);
			} else {
				return new Integer(anoResult);
			}
		}

		return new Integer(0);
	}

	/**
	 * Faz o calculo da idade com base na data passada como parametro. retorna apenas os anos retorna um numero representando a quantidade de anos.
	 * 
	 * @param dDataNasc
	 * @return
	 */
	public static Integer calculaIdadeEmAnos(Date dDataNasc) {
		if (dDataNasc == null) {
			return new Integer(0);
		}

		Calendar hoje = Calendar.getInstance();
		Date now = hoje.getTime();

		return calculaIdadeEmAnos(dDataNasc, now);
	}

	/**
	 * Faz o calculo da idade com base na data passada como parametro. retorna apenas os meses retorna um numero representando a quantidade de meses.
	 * 
	 * @param dDataNasc
	 * @return
	 */
	public static Integer calculaIdadeEmMeses(Date dDataNasc) {
		if (dDataNasc == null) {
			return new Integer(0);
		}

		int anoResult = 0;
		int mesResult = 0;
		Calendar hoje = Calendar.getInstance();
		Date now = hoje.getTime();

		int anoHoje = getYear(now);
		int anoDataParm = getYear(dDataNasc);
		int mesHoje = getMonth(now);
		int mesDataParm = getMonth(dDataNasc);
		int diaHoje = getDay(now);
		int diaDataParm = getDay(dDataNasc);

		anoResult = anoHoje - anoDataParm;
		if ((mesHoje < mesDataParm) || ((mesHoje == mesDataParm) && (diaHoje < diaDataParm))) {
			anoResult = anoResult - 1;
		}

		mesResult = mesHoje - mesDataParm;
		if ((mesResult < 0) || ((mesHoje == mesDataParm) && (diaHoje < diaDataParm))) {
			mesResult = mesResult + 12;
		}

		if (anoResult > 0) {
			if (anoResult == 1) {
				return new Integer((1 * 12) + mesResult);
			} else {
				return new Integer((anoResult * 12) + mesResult);
			}
		} else {
			return new Integer(mesResult);
		}
	}

	public static Integer calculaIdadeEmMeses(Date dDataNasc, Date dataRef) {
		if (dDataNasc == null) {
			return new Integer(0);
		}
		if (dataRef == null) {
			return new Integer(0);
		}

		int anoResult = 0;
		int mesResult = 0;
		Date dataReferencia = dataRef;

		int anoHoje = getYear(dataReferencia);
		int anoDataParm = getYear(dDataNasc);
		int mesHoje = getMonth(dataReferencia);
		int mesDataParm = getMonth(dDataNasc);
		int diaHoje = getDay(dataReferencia);
		int diaDataParm = getDay(dDataNasc);

		anoResult = anoHoje - anoDataParm;
		if ((mesHoje < mesDataParm) || ((mesHoje == mesDataParm) && (diaHoje < diaDataParm))) {
			anoResult = anoResult - 1;
		}

		mesResult = mesHoje - mesDataParm;
		if ((mesResult < 0) || ((mesHoje == mesDataParm) && (diaHoje < diaDataParm))) {
			mesResult = mesResult + 12;
		}

		if (anoResult > 0) {
			if (anoResult == 1) {
				return new Integer((1 * 12) + mesResult);
			} else {
				return new Integer((anoResult * 12) + mesResult);
			}
		} else {
			return new Integer(mesResult);
		}
	}

	/**
	 * Faz o calculo da idade com base na data passada como parametro. Retorna a quantidade de anos. retorna um int.
	 * 
	 * @param dDataNasc
	 * @return
	 */
	public static int calculaIdade(Date dDataNasc) {
		if (dDataNasc == null) {
			return 0;
		}
		int anoResult = 0;
		Calendar hoje = Calendar.getInstance();
		Date now = hoje.getTime();

		int anoHoje = getYear(now);
		int anoDataParm = getYear(dDataNasc);
		int mesHoje = getMonth(now);
		int mesDataParm = getMonth(dDataNasc);
		int diaHoje = getDay(now);
		int diaDataParm = getDay(dDataNasc);

		anoResult = anoHoje - anoDataParm;
		if ((mesHoje < mesDataParm) || ((mesHoje == mesDataParm) && (diaHoje < diaDataParm))) {
			anoResult = anoResult - 1;
		}

		return anoResult;
	}

	/**
	 * Incrementa uma quantidade de dias em uma data
	 * 
	 * @param data
	 * @param numDias
	 * @return
	 */
	public static Date incrementaDiasEmData(Date data, int numDias) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(data);
		c.add(Calendar.DATE, numDias);
		return c.getTime();
	}

	/**
	 * Pega o ano de uma data
	 * 
	 * @param data
	 * @return
	 */
	public static int getYear(Date data) {
		Calendar c = Calendar.getInstance();

		c.setTime(data);

		return c.get(Calendar.YEAR);
	}

	/**
	 * Pega o mes de uma data
	 * 
	 * @param data
	 * @return
	 */
	public static int getMonth(Date data) {
		Calendar c = Calendar.getInstance();

		c.setTime(data);

		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * Pega o dia de uma data
	 * 
	 * @param data
	 * @return
	 */
	public static int getDay(Date data) {
		Calendar c = Calendar.getInstance();

		c.setTime(data);

		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Pega a data Data Atual em java.sql.Date
	 * 
	 * @param data
	 * @return
	 */
	public static java.sql.Date getDataAtual() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return strToSQLDate(sdf.format(new Date()));
		} catch (LogicException e) {
			return new java.sql.Date(new Date().getTime());
		}
	}

	/**
	 * Pega a hora atual (java.sql.Time)
	 * 
	 * @return
	 */
	public static java.sql.Time getHoraAtual() {
		return new java.sql.Time(new Date().getTime());
	}

	/**
	 * Pega data e hora atual (timestamp)
	 * 
	 * @return
	 */
	public static Timestamp getDataHoraAtual() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * Compara 2 datas
	 * 
	 * @param datInicio
	 * @param datFim
	 * @param descDatas
	 * @param req
	 * @param foco
	 * @throws LogicException
	 */
	public static void comparaDatas(Date datInicio, Date datFim, String descDatas) throws LogicException {
		if (datFim != null && datInicio != null)
			if (datInicio.compareTo(datFim) > 0) {
				throw new LogicException("A Data inicial(" + descDatas + ") nao pode ser maior que a data final");
			}
	}

	/**
	 * Valida uma data em comparacao com a data atual.
	 * 
	 * @param datInicio
	 * @param nomeCampo
	 * @throws LogicException
	 */
	public static void validateDataMenorIgualAtual(Date datInicio, String nomeCampo) throws LogicException {
		if (datInicio != null)
			if (datInicio.compareTo(getDataAtual()) > 0) {
				throw new LogicException(nomeCampo + " nao pode ser maior que a data atual");
			}
	}

	/**
	 * Obtem um ano seguinte atraves de uma data passada como parametro. Retona a data calculada.
	 * 
	 * @param data
	 * @return
	 * @throws LogicException
	 */
	public static Date geraUmAnoSeguinte(Date data) throws LogicException {
		if (data == null)
			return null;
		String dataStr = dateToSTR(data);

		int ano = getDiaMesAno(data, 3);
		ano++;

		NumberFormat formatoAux = new DecimalFormat("0000");
		String anoStr = formatoAux.format(ano);

		String dataAux = dataStr.substring(0, 6) + anoStr;
		return strTodate(dataAux);
	}

	/**
	 * Formata hora em 4 digitos com h no final Exemplo: 1000 retorna 10:00h
	 * 
	 * @param hora4digitos
	 * @return
	 */
	public static String formatarHora4Digitos(String hora4digitos) {
		if (hora4digitos == null)
			return ZERO_HORAS_E_MINUTOS_4DGT;
		if (hora4digitos.length() > 4)
			throw new IllegalArgumentException("A String de hora não pode ser 'null' nem conter mais que 4 caracteres.");
		while (hora4digitos.length() < 4)
			hora4digitos = '0' + hora4digitos;
		hora4digitos = hora4digitos.substring(0, 2) + ':' + hora4digitos.substring(2) + 'h';
		return hora4digitos;
	}

	/**
	 * Acrescenta uma quantidade de anos em uma data
	 * 
	 * @param data
	 *            em formato DD/MM/YYYY (String)
	 * @param qtdAnos
	 * @return
	 */
	public static String acrecentaAnoEmData(String data, int qtdAnos) {
		// Retorna uma data com o ano alterado, dependendo do parametro passado,
		// por exemplo, se os parametros passados forem (15/06/2006, 2) , o
		// retorno será: 15/06/2008
		// note que dois anos foram adicionado a data
		try {
			String anoAntes = data.substring(6, 10);
			int ano = new Integer(anoAntes).shortValue();
			ano = ano + qtdAnos;
			data = data.replaceAll(anoAntes, "" + ano);

			return data;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Retorna quantidade de dias entre as duas datas. As datas devem ser passadas no seguinte formato: dd/mm/yyyy
	 * 
	 * @param String
	 *            data maior
	 * @param String
	 *            data menor
	 * @return int dias
	 */
	public static int getDiasEntreDatas(String dataMaior, String dataMenor) throws LogicException {

		if (dataMaior.length() != 10 || dataMenor.length() != 10) {
			return 0;
		}

		Date data1 = strTodate(dataMaior);
		Date data2 = strTodate(dataMenor);

		// Difference in milliseconds between the two times.
		long timeDifference = data1.getTime() - data2.getTime();

		// Convert milliseconds to days.
		long seconds = timeDifference / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;

		return (int) days;

	}

	/**
	 * Retorna quantidade de dias entre as duas datas
	 * 
	 * @param Date
	 *            data maior
	 * @param Date
	 *            data menor
	 * @return int dias
	 */
	public static int getDiasEntreDatas(Date data1, Date data2) throws LogicException {
		// Difference in milliseconds between the two times.
		long timeDifference = data1.getTime() - data2.getTime();

		// Convert milliseconds to days.
		long seconds = timeDifference / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;

		return (int) days;
	}

	/**
	 * Método para comparar as das e retornar o numero de dias de diferença entre elas
	 * 
	 * Compare two date and return the difference between them in days.
	 * 
	 * @param dataLow
	 *            The lowest date
	 * @param dataHigh
	 *            The highest date
	 * 
	 * @return int
	 */
	public static int dataDiff(Date dataInicio, Date dataFim) {

		GregorianCalendar startTime = new GregorianCalendar();
		GregorianCalendar endTime = new GregorianCalendar();

		GregorianCalendar curTime = new GregorianCalendar();
		GregorianCalendar baseTime = new GregorianCalendar();

		startTime.setTime(dataInicio);
		endTime.setTime(dataFim);

		int dif_multiplier = 1;

		// Verifica a ordem de inicio das datas
		if (dataInicio.compareTo(dataFim) < 0) {
			baseTime.setTime(dataFim);
			curTime.setTime(dataInicio);
			dif_multiplier = 1;
		} else {
			baseTime.setTime(dataInicio);
			curTime.setTime(dataFim);
			dif_multiplier = -1;
		}

		int result_years = 0;
		int result_months = 0;
		int result_days = 0;

		// Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import
		// acumulando
		// no total de dias. Ja leva em consideracao ano bissesto
		while (curTime.get(Calendar.YEAR) < baseTime.get(Calendar.YEAR) || curTime.get(Calendar.MONTH) < baseTime.get(Calendar.MONTH)) {

			int max_day = curTime.getActualMaximum(Calendar.DAY_OF_MONTH);
			result_months += max_day;
			curTime.add(Calendar.MONTH, 1);

		}

		// Marca que é um saldo negativo ou positivo
		result_months = result_months * dif_multiplier;

		// Retirna a diferenca de dias do total dos meses
		result_days += (endTime.get(Calendar.DAY_OF_MONTH) - startTime.get(Calendar.DAY_OF_MONTH));

		return result_years + result_months + result_days;
	}

	/**
	 * Compara 2 datas e retorna a maior, verificando os nulos.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static java.sql.Date getDataMaior(java.sql.Date data1, java.sql.Date data2) {
		if (data1 == null)
			return data2;
		if (data2 == null)
			return data1;
		if (data1.after(data2)) {
			return data1;
		} else {
			return data2;
		}
	}

	/**
	 * Verifica se uma data esta no intervalo (data inicio e fim)
	 * 
	 * @param dataComparar
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean dataEntreIntervalo(java.sql.Date dataComparar, java.sql.Date data1, java.sql.Date data2) {
		if (dataComparar == null)
			return false;
		if (data1 == null)
			return false;
		if (data2 == null)
			return false;
		if (dataComparar.compareTo(data1) >= 0 && dataComparar.compareTo(data2) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verifica se uma data esta no intervalo (data inicio e fim)
	 * 
	 * @param dataComparar
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean dataEntreIntervalo(Date dataComparar, Date data1, Date data2) {
		if (dataComparar == null)
			return false;
		if (data1 == null)
			return false;
		if (data2 == null)
			return false;
		if (dataComparar.compareTo(data1) >= 0 && dataComparar.compareTo(data2) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retorna o numero de minutos
	 * 
	 * @param horaFim
	 * @param horaIni
	 * @param formatoHora
	 *            - hh:mm:ss
	 * @return
	 */
	public static double subtraiHora(String horaFim, String horaIni, String formatoHora) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatoHora);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

		double min_1 = 0;
		double min_2 = 0;
		double result = 0;

		min_1 = getHoras(horaFim, formatter);
		min_2 = getHoras(horaIni, formatter);

		result = (min_1 - min_2) * 60;
		if (min_1 < min_2)
			result = (min_2 - min_1) * 60;

		if (result == 0) {
			return 0;
		}
		double x = ((result / 3600) / 1000);
		return x * 60;
	}

	private static long getHoras(String hora, SimpleDateFormat formatter) {
		String hor = hora.substring(0, 2);
		String min = hora.substring(3, 5);
		String sec = hora.substring(6);

		int intHor = Integer.parseInt(hor);
		int intMin = Integer.parseInt(min);
		int intSec = Integer.parseInt(sec);

		long ret = (intSec * 1000) + ((intMin * 60) * 1000) + (((intHor * 60) * 60) * 1000);
		long rhora = ret / 60;
		return rhora;
	}

	public static String getHoraHHMMSS(Timestamp dataHoraRef) {
		// cria um StringBuilder
		StringBuilder sb = new StringBuilder();
		// cria um GregorianCalendar que vai conter a hora atual
		GregorianCalendar d = new GregorianCalendar();
		d.setTime(dataHoraRef);

		String aux = "" + d.get(GregorianCalendar.HOUR_OF_DAY);
		if (aux.length() < 2)
			aux = "0" + aux;
		sb.append(aux);

		aux = "" + d.get(GregorianCalendar.MINUTE);
		if (aux.length() < 2)
			aux = "0" + aux;
		sb.append(":");
		sb.append(aux);

		aux = "" + d.get(GregorianCalendar.SECOND);
		if (aux.length() < 2)
			aux = "0" + aux;
		sb.append(":");
		sb.append(aux);

		return sb.toString();
	}

	public static String getHoraHHMM(Timestamp dataHoraRef) {
		// cria um StringBuilder
		StringBuilder sb = new StringBuilder();
		// cria um GregorianCalendar que vai conter a hora atual
		GregorianCalendar d = new GregorianCalendar();
		d.setTime(dataHoraRef);

		String aux = "" + d.get(GregorianCalendar.HOUR_OF_DAY);
		if (aux.length() < 2)
			aux = "0" + aux;
		sb.append(aux);

		aux = "" + d.get(GregorianCalendar.MINUTE);
		if (aux.length() < 2)
			aux = "0" + aux;
		sb.append(":");
		sb.append(aux);

		return sb.toString();
	}

	public static long calculaDiferencaTempoEmMilisegundos(Timestamp dataHoraFinal, Timestamp dataHoraInicial) throws Exception {
		if (dataHoraFinal.compareTo(dataHoraInicial) < 0)
			return 0;

		long difDias = dataDiff(new Date(dataHoraInicial.getTime()), new Date(dataHoraFinal.getTime()));

		GregorianCalendar dInicial = new GregorianCalendar();
		dInicial.setTime(dataHoraInicial);

		GregorianCalendar dFinal = new GregorianCalendar();
		dFinal.setTime(dataHoraFinal);

		int intHor = dInicial.get(GregorianCalendar.HOUR_OF_DAY);
		int intMin = dInicial.get(GregorianCalendar.MINUTE);
		int intSec = dInicial.get(GregorianCalendar.SECOND);

		long tempoInicial = (intSec * 1000) + (intMin * 60 * 1000) + (intHor * 60 * 60 * 1000);

		intHor = dFinal.get(GregorianCalendar.HOUR_OF_DAY);
		intMin = dFinal.get(GregorianCalendar.MINUTE);
		intSec = dFinal.get(GregorianCalendar.SECOND);
		long tempoFinal = (intSec * 1000) + (intMin * 60 * 1000) + (intHor * 60 * 60 * 1000);

		return (difDias * 24 * 60 * 60 * 1000) + tempoFinal - tempoInicial;

	}

	public static Timestamp somaSegundos(Timestamp ts, int segundos) throws Exception {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(ts);
		calendar.add(GregorianCalendar.SECOND, segundos);

		return new Timestamp(calendar.getTime().getTime());

	}

	/**
	 * Retorna um timeStamp no formato String
	 * 
	 * @author rodrigo.oliveira
	 */
	public String geraTimeStamp() throws Exception {

		Timestamp time = new Timestamp(System.currentTimeMillis());

		return time.toString();
	}

	/**
	 * Valida o formato de uma determinada Data
	 * 
	 * @param dateToValidate
	 * @param dateFromat
	 * @return
	 * @author flavio.santana
	 */
	public static boolean isThisDateValid(String dateToValidate, String dateFromat) {
		if (dateToValidate == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		try {
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);

		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static final String formataDataParaOracle(String data) {
		if (data == null)
			return "";
		String dataFormatada = "";
		
		String[] partes = data.split("/");
		dataFormatada += partes[2] + "/";
		dataFormatada += partes[1] + "/"; 
		dataFormatada += partes[0];
		
		return dataFormatada;
		
	}

}
