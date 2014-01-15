package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CalculoJornadaDTO;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.ExcecaoCalendarioDTO;
import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.bean.RecursoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.TipoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.centralit.citcorpore.integracao.CalendarioDao;
import br.com.centralit.citcorpore.integracao.ExcecaoCalendarioDao;
import br.com.centralit.citcorpore.integracao.FeriadoDao;
import br.com.centralit.citcorpore.integracao.JornadaTrabalhoDao;
import br.com.centralit.citcorpore.integracao.RecursoDao;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.TipoLiberacaoDAO;
import br.com.centralit.citcorpore.integracao.TipoMudancaDAO;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

public class CalendarioServiceEjb extends CrudServicePojoImpl implements CalendarioService {

	private static final long serialVersionUID = -3066792018768546643L;

	public static final String HORA_INICIO_CALCULADA = "IC";
	public static final String HORA_TERMINO_CALCULADA = "TC";
	public static final String HORA_INICIO_JORNADA = "IJ";
	public static final String HORA_TERMINO_JORNADA = "TJ";

	public static final String FOLGA = "F";
	public static final String TRABALHO = "T";

	protected CrudDAO getDao() throws ServiceException {
		return new CalendarioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	/**
	 * Determina a Data Hora final, ou seja, o Prazo Limite.
	 * 
	 * @param calculoDto
	 * @param bCalculaHoraInicio
	 * @return CalculoJornadaDTO
	 * @throws Exception
	 */
	public CalculoJornadaDTO calculaDataHoraFinal(CalculoJornadaDTO calculoDto, boolean bCalculaHoraInicio) throws Exception {
		if (calculoDto.getIdCalendario() == null)
			throw new Exception("ID do calendário não informado para cálculo da data e hora");
		if (calculoDto.getDataHoraInicial() == null)
			throw new Exception("Data e hora inicial não informadas para cálculo da data e hora");
		if (calculoDto.getPrazoHH() == null)
			throw new Exception("Prazo em horas não informado para cálculo da data e hora");
		if (calculoDto.getPrazoMM() == null)
			throw new Exception("Prazo em minutos não informado para cálculo da data e hora");

		CalendarioDTO calendarioDto = recuperaCalendario(calculoDto.getIdCalendario());

		if (calendarioDto == null)
			throw new Exception("Serviço Contrato sem calendário. Por favor selecione Calendário em Serviço Contrato!");

		double slaDefinido = calculoDto.getPrazoHH() + new Double(calculoDto.getPrazoMM()).doubleValue() / 60;

		Timestamp dataHoraInicioSla = calculaDataHoraJornada(calendarioDto, calculoDto.getDataHoraInicial(), HORA_INICIO_CALCULADA, true);

		double dataHoraInicioSlaDbl = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraInicioSla));

		double prazoDisponivel = calculaCargaHoraria(calendarioDto, dataHoraInicioSla);

		double cargaHoraria = prazoDisponivel;

		int difDias = 0;

		Timestamp dataHoraTermino = calculaDataHoraJornada(calendarioDto, dataHoraInicioSla, HORA_TERMINO_JORNADA, false);

		while (prazoDisponivel < slaDefinido) {
			dataHoraTermino = incrementaDias(dataHoraTermino, 1);
			dataHoraTermino = calculaDataHoraJornada(calendarioDto, dataHoraTermino, HORA_TERMINO_JORNADA, false);
			cargaHoraria = calculaCargaHorariaTotal(calendarioDto, dataHoraTermino);
			prazoDisponivel += cargaHoraria;
			difDias++;
		}

		if (prazoDisponivel > slaDefinido) {
			double hora = 0;

			if (difDias > 0) {
				double diferenca = cargaHoraria - (prazoDisponivel - slaDefinido);
				JornadaTrabalhoDTO jornadaDto = recuperaJornada(calendarioDto, new Date(dataHoraTermino.getTime()), -1);
				double inicio[] = jornadaDto.getInicio();
				double termino[] = jornadaDto.getTermino();
				int i = 1;
				while (i <= 5 && inicio[i] != 99 && diferenca > 0) {
					double ch = termino[i] - inicio[i];
					if (diferenca > ch) {
						diferenca = diferenca - (termino[i] - inicio[i]);
						if (diferenca < 0)
							diferenca = 0;
						hora = inicio[i] + diferenca;
					} else {
						hora = inicio[i] + diferenca;
						diferenca = 0;
					}
					i++;
				}
			} else {
				double diferenca = cargaHoraria - (prazoDisponivel - slaDefinido);
				JornadaTrabalhoDTO jornadaDto = recuperaJornada(calendarioDto, new Date(dataHoraTermino.getTime()), -1);
				double inicio[] = jornadaDto.getInicio();
				double termino[] = jornadaDto.getTermino();
				int i = 1;
				while (i <= 5 && inicio[i] != 99 && diferenca > 0) {
					if (dataHoraInicioSlaDbl >= inicio[i] && dataHoraInicioSlaDbl <= termino[i]) {

						hora = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraInicioSla)) + diferenca;

						for (int j = i; j <= 5; j++) {
							if (termino[j] != 0.0 && inicio[j + 1] != 99.9 && hora > termino[j]) {
								hora += (inicio[j + 1] - termino[j]);
								diferenca = 0;
							}
						}
					}
					i++;
				}
			}

			Date dataRef = new Date(dataHoraTermino.getTime());

			dataHoraTermino = Timestamp.valueOf(UtilDatas.dateToSTR(dataRef, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(hora) + ":00");
		}

		calculoDto.setDataHoraFinal(dataHoraTermino);
		return calculoDto;
	}

	/**
	 * Realiza o cálculo de Tempo Decorrido de acordo com a data hora atual informada e o calendário.
	 * 
	 * @param calculoDto
	 *            - Instancia de CalculoJornadaDTO que deverá ser instanciada utilizando o construtor sobrecarregado que recebe o ID do Calendário e a Data Hora inicial.
	 * @param dataHoraAtual
	 *            - Timestamp da data hora atual.
	 * @return CalculoJornadaDTO - DTO de CalculoJornada com os atributos TempoDecorridoHH e TempoDecorridoMM
	 * @throws Exception
	 * @author carlos.santos
	 * @version 1.0 de 03.07.2013 por valdoilo.damasceno
	 */
	public CalculoJornadaDTO calculaPrazoDecorrido(CalculoJornadaDTO calculoDto, Timestamp dataHoraAtual) throws Exception {
		if (calculoDto.getIdCalendario() == null)
			throw new Exception("ID do calendário não informado para cálculo da data e hora");
		if (calculoDto.getDataHoraInicial() == null)
			throw new Exception("Data e hora inicial não informadas para cálculo da data e hora");

		CalendarioDTO calendarioDto = recuperaCalendario(calculoDto.getIdCalendario());
		if (calculoDto.getDataHoraInicial().compareTo(dataHoraAtual) > 0)
			throw new Exception("Data e Hora Inicial maior que Data e Hora Final");

		Timestamp dataHoraInicial = calculaDataHoraJornada(calendarioDto, calculoDto.getDataHoraInicial(), HORA_INICIO_CALCULADA, true);

		double prazoDecorrido = 0.00;
		int difDias = calculaDiferencaDias(dataHoraAtual, dataHoraInicial);
		boolean bMaisDeUmDia = difDias > 0;
		if (difDias > 0) {
			prazoDecorrido += calculaCargaHoraria(calendarioDto, dataHoraInicial);
			while (difDias > 0) {
				dataHoraInicial = incrementaDias(dataHoraInicial, 1);
				dataHoraInicial = calculaDataHoraJornada(calendarioDto, dataHoraInicial, HORA_INICIO_JORNADA, false);
				difDias = calculaDiferencaDias(dataHoraAtual, dataHoraInicial);
				if (difDias > 0)
					prazoDecorrido += calculaCargaHorariaTotal(calendarioDto, dataHoraInicial);
			}
		}
		if (difDias == 0) {
			JornadaTrabalhoDTO jornadaDto = recuperaJornada(calendarioDto, new Date(dataHoraInicial.getTime()), -1);
			double horaAtual = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraAtual));
			double inicio[] = jornadaDto.getInicio();
			if (inicio[1] < horaAtual) {
				boolean bCalculou = false;
				double termino[] = jornadaDto.getTermino();
				for (int i = 1; i <= 5; i++) {
					if (inicio[i] == 99)
						break;
					if (inicio[i] <= horaAtual && termino[i] > horaAtual) {
						int p = i;

						if (bMaisDeUmDia) {
							while (p > 1) {
								prazoDecorrido += termino[p] - inicio[p]; // Acumula turnos da jornada - OBS VALDOILO - ISSO SÓ FUNCIONA QUANDO O NÚMERO DE DIAS É MAIOR QUE 1
								p = p - 1;
							}
						}

						if (horaAtual < termino[i]) {
							double hrInicio = inicio[i];

							if (bMaisDeUmDia) {
								prazoDecorrido += horaAtual - hrInicio; // Acumula o tempo decorrido dentro do turno - OBS. VALDOÍLO - ISSO SÓ FUNCIONA QUANDO O NÚMERO DE DIAS É MAIOR QUE 1
							} else {
								hrInicio = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraInicial));
								prazoDecorrido += horaAtual - hrInicio;

								double intervalo = 0;
								for (int j = 1; j <= 5; j++) {
									if (jornadaDto.getTermino(j) != null && jornadaDto.getInicio(j + 1) != null) {
										double hrTermino = Util.getHoraDbl(jornadaDto.getTermino(j));
										if (horaAtual > hrTermino && hrTermino > hrInicio)
											intervalo += Util.calculaDuracao(jornadaDto.getTermino(j), jornadaDto.getInicio(j + 1));
									}
								}

								if (intervalo < prazoDecorrido) {
									prazoDecorrido -= intervalo;
								}
							}

						} else {
							prazoDecorrido += termino[i] - inicio[i]; // Acumula turno integral da jornada
						}
						bCalculou = true;
					}
				}
				if (!bCalculou) { // Ocorre quando a hora do tsRef está acima do horário de término da jornada
					double ch = calculaCargaHorariaTotal(calendarioDto, dataHoraInicial);
					if (bMaisDeUmDia) { // Quando é mais de um dia, acumula a jornada total
						prazoDecorrido += calculaCargaHorariaTotal(calendarioDto, dataHoraInicial);
					} else {
						double horaInicioSlaDbl = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraInicial));
						if (verificarSeHoraEstaForaDaJornadaDeTrabalho(jornadaDto, horaAtual)) { // Verifica se a hora do tsRef está num intervalo da jornada
							prazoDecorrido = retornaProximaHoraTerminoJornada(jornadaDto, horaInicioSlaDbl) - horaInicioSlaDbl;
						} else {
							dataHoraInicial = Timestamp.valueOf(UtilDatas.dateToSTR(new Date(dataHoraInicial.getTime()), "yyyy-MM-dd") + " " + Util.getHoraFmtStr(horaAtual) + ":00");
							prazoDecorrido = ch - calculaCargaHoraria(calendarioDto, dataHoraInicial);
						}
					}
				}
			}
		}

		calculoDto.setTempoDecorridoHH(new Integer(Util.getHora(prazoDecorrido)));
		calculoDto.setTempoDecorridoMM(new Integer(Util.getMinuto(prazoDecorrido)));
		return calculoDto;
	}

	private int calculaDiferencaDias(Timestamp tsFinal, Timestamp tsInicial) throws Exception {
		if (tsFinal.compareTo(tsInicial) < 0)
			return -1;
		int i = 0;
		SimpleDateFormat spd = new SimpleDateFormat("yyyyMMdd");
		Date dataRef = new Date(tsInicial.getTime());
		String dataRefInv = spd.format(dataRef).trim();
		String dataFinalInv = spd.format(new Date(tsFinal.getTime())).trim();
		while (dataRefInv.compareTo(dataFinalInv) < 0) {
			dataRef = new Date(UtilDatas.incrementaDiasEmData(dataRef, 1).getTime());
			dataRefInv = spd.format(dataRef).trim();
			i++;
		}
		return i;
	}

	public Timestamp incrementaDias(Timestamp ts, int qtdeDias) throws Exception {
		Date dataRef = new Date(ts.getTime());
		dataRef = new Date(UtilDatas.incrementaDiasEmData(dataRef, 1).getTime());
		return Timestamp.valueOf(UtilDatas.dateToSTR(dataRef, "yyyy-MM-dd") + " " + "00:00:00");
	}

	public Timestamp calculaDataHoraJornada(CalendarioDTO calendarioDto, Timestamp dataHoraRef, String tipoCalculoHora, boolean bUtilizaHorarioTimestamp) throws Exception {
		JornadaTrabalhoDTO jornadaDto = verificaDiaUtil(calendarioDto, dataHoraRef, bUtilizaHorarioTimestamp);
		if (jornadaDto == null)
			throw new Exception("Não existem jornadas configuradas para este calendário nos próximos 30 dias");

		double hora = Util.getHoraDbl(UtilDatas.getHoraHHMM(jornadaDto.getDataHoraInicial()));
		double horaUtil = hora;
		if (tipoCalculoHora.equalsIgnoreCase(HORA_INICIO_CALCULADA))
			horaUtil = calculaHoraUtilInicial(jornadaDto, hora);
		else if (tipoCalculoHora.equalsIgnoreCase(HORA_INICIO_JORNADA))
			horaUtil = retornaHoraInicioJornada(jornadaDto);
		else if (tipoCalculoHora.equalsIgnoreCase(HORA_TERMINO_JORNADA))
			horaUtil = retornaHoraTerminoJornada(jornadaDto);
		else
			horaUtil = calculaHoraUtilFinal(jornadaDto, hora);

		Date dataRef = new Date(jornadaDto.getDataHoraInicial().getTime());
		return Timestamp.valueOf(UtilDatas.dateToSTR(dataRef, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(horaUtil) + ":00");
	}

	public JornadaTrabalhoDTO verificaDiaUtil(CalendarioDTO calendarioDto, Timestamp dataHoraRef, boolean bUtilizaHorarioTimestamp) throws Exception {
		double horaRef = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraRef));
		Date dataRef = new Date(dataHoraRef.getTime());
		double horaCalculo = -1;
		if (bUtilizaHorarioTimestamp)
			horaCalculo = horaRef;
		JornadaTrabalhoDTO jornadaDto = recuperaJornada(calendarioDto, dataRef, horaCalculo);
		int i = 0;
		while (jornadaDto == null && i < 30) {
			dataRef = new Date(UtilDatas.incrementaDiasEmData(dataRef, 1).getTime());
			jornadaDto = recuperaJornada(calendarioDto, dataRef, -1);
			if (jornadaDto != null)
				horaRef = retornaHoraInicioJornada(jornadaDto);
			i++;
		}

		if (jornadaDto != null) {
			double horaInicio = retornaHoraInicioJornada(jornadaDto);
			if (horaRef > horaInicio)
				jornadaDto.setDataHoraInicial(Timestamp.valueOf(UtilDatas.dateToSTR(dataRef, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(horaRef) + ":00"));
			else
				jornadaDto.setDataHoraInicial(Timestamp.valueOf(UtilDatas.dateToSTR(dataRef, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(horaInicio) + ":00"));
		}

		return jornadaDto;
	}

	public JornadaTrabalhoDTO recuperaJornada(CalendarioDTO calendarioDto, Date dataRef, double horaRef) throws Exception {
		JornadaTrabalhoDTO jornadaDto = recuperaJornada(calendarioDto, dataRef);
		if (jornadaDto == null)
			return null;

		if (horaRef < 0)
			return jornadaDto;

		double horaFinal = retornaHoraTerminoJornada(jornadaDto);
		if (horaFinal < horaRef)
			return null;

		// double horaInicial = retornaHoraInicioJornada(jornadaDto);
		// if (horaInicial > horaRef)
		// return null;

		return jornadaDto;
	}

	public CalendarioDTO recuperaCalendario(Integer idCalendario) throws Exception {
		CalendarioDTO calendarioDto = new CalendarioDTO();
		calendarioDto.setIdCalendario(idCalendario);
		return (CalendarioDTO) new CalendarioDao().restore(calendarioDto);
	}

	public JornadaTrabalhoDTO recuperaJornada(CalendarioDTO calendarioDto, Date dataRef) throws Exception {
		Integer idJornada = calendarioDto.getIdJornada(dataRef);

		ExcecaoCalendarioDTO excecaoDto = new ExcecaoCalendarioDao().findByIdCalendarioAndData(calendarioDto.getIdCalendario(), dataRef);
		if (excecaoDto != null && excecaoDto.getTipo().equalsIgnoreCase(TRABALHO))
			idJornada = excecaoDto.getIdJornada();
		if (excecaoDto != null && excecaoDto.getTipo().equalsIgnoreCase(FOLGA))
			return null;

		if (idJornada == null)
			return null;

		boolean bFeriado = false;
		if (calendarioDto.getConsideraFeriados() != null && calendarioDto.getConsideraFeriados().equalsIgnoreCase("S"))
			bFeriado = new FeriadoDao().isFeriado(dataRef, null, null);
		if (bFeriado)
			return null;

		JornadaTrabalhoDTO jornadaDto = new JornadaTrabalhoDTO();
		jornadaDto.setIdJornada(idJornada);
		jornadaDto = (JornadaTrabalhoDTO) new JornadaTrabalhoDao().restore(jornadaDto);
		return jornadaDto;
	}

	public double calculaHoraUtilInicial(JornadaTrabalhoDTO jornadaDto, double horaRef) throws Exception {
		if (jornadaDto == null)
			return 0.0;

		double[] hrInicio = new double[] { 99, 99, 99, 99, 99, 99 };
		double[] hrTermino = new double[] { 0, 0, 0, 0, 0, 0 };

		for (int i = 1; i <= 5; i++) {
			if (jornadaDto.getInicio(i) != null) {
				hrInicio[i] = Util.getHoraDbl(jornadaDto.getInicio(i));
				hrTermino[i] = Util.getHoraDbl(jornadaDto.getTermino(i));
			}
		}

		double horaUtilDbl = 0.0;
		int i = 1;
		while (horaUtilDbl == 0.0 && i <= 5) {
			if (hrInicio[i] != 99 && (hrInicio[i] <= horaRef || i > 1) && hrTermino[i] >= horaRef)
				horaUtilDbl = hrInicio[i];
			i++;
		}

		if (horaUtilDbl > horaRef)
			return horaUtilDbl;
		else
			return horaRef;
	}

	public double calculaHoraUtilFinal(JornadaTrabalhoDTO jornadaDto, double horaRef) throws Exception {
		if (jornadaDto == null)
			return 0.0;

		double[] hrInicio = new double[] { 99, 99, 99, 99, 99, 99 };
		double[] hrTermino = new double[] { 0, 0, 0, 0, 0, 0 };

		for (int i = 1; i <= 5; i++) {
			if (jornadaDto.getInicio(i) != null) {
				hrInicio[i] = Util.getHoraDbl(jornadaDto.getInicio(i));
				hrTermino[i] = Util.getHoraDbl(jornadaDto.getTermino(i));
			}
		}

		double horaUtilDbl = 99;
		int i = 1;
		while (horaUtilDbl == 99 && i <= 5) {
			if (hrTermino[i] != 99 && hrTermino[i] >= horaRef)
				horaUtilDbl = hrTermino[i];
			i++;
		}

		if (horaUtilDbl > horaRef)
			return horaUtilDbl;
		else
			return horaRef;
	}

	public double retornaHoraTerminoJornada(JornadaTrabalhoDTO jornadaDto) throws Exception {
		double horaFinalDbl = -1;
		if (jornadaDto.getTermino5() != null && jornadaDto.getTermino5().trim().length() > 0)
			horaFinalDbl = Util.getHoraDbl(jornadaDto.getTermino5());
		else if (jornadaDto.getTermino4() != null && jornadaDto.getTermino4().trim().length() > 0)
			horaFinalDbl = Util.getHoraDbl(jornadaDto.getTermino4());
		else if (jornadaDto.getTermino3() != null && jornadaDto.getTermino3().trim().length() > 0)
			horaFinalDbl = Util.getHoraDbl(jornadaDto.getTermino3());
		else if (jornadaDto.getTermino2() != null && jornadaDto.getTermino2().trim().length() > 0)
			horaFinalDbl = Util.getHoraDbl(jornadaDto.getTermino2());
		else if (jornadaDto.getTermino1() != null && jornadaDto.getTermino1().trim().length() > 0)
			horaFinalDbl = Util.getHoraDbl(jornadaDto.getTermino1());

		return horaFinalDbl;
	}

	public double retornaHoraInicioJornada(JornadaTrabalhoDTO jornadaDto) throws Exception {
		double horaInicialDbl = -1;
		if (jornadaDto.getInicio1() != null && jornadaDto.getInicio1().trim().length() > 0)
			horaInicialDbl = Util.getHoraDbl(jornadaDto.getInicio1());
		else if (jornadaDto.getInicio2() != null && jornadaDto.getInicio2().trim().length() > 0)
			horaInicialDbl = Util.getHoraDbl(jornadaDto.getInicio2());
		else if (jornadaDto.getInicio3() != null && jornadaDto.getInicio3().trim().length() > 0)
			horaInicialDbl = Util.getHoraDbl(jornadaDto.getInicio3());
		else if (jornadaDto.getInicio4() != null && jornadaDto.getInicio4().trim().length() > 0)
			horaInicialDbl = Util.getHoraDbl(jornadaDto.getInicio4());
		else if (jornadaDto.getInicio5() != null && jornadaDto.getInicio5().trim().length() > 0)
			horaInicialDbl = Util.getHoraDbl(jornadaDto.getInicio5());

		return horaInicialDbl;
	}

	public double calculaIntervalos(JornadaTrabalhoDTO jornadaDto, double horaInicial) throws Exception {
		double intervalo = 0;
		for (int i = 1; i <= 5; i++) {
			if (jornadaDto.getTermino(i) != null && jornadaDto.getInicio(i + 1) != null) {
				double hr = Util.getHoraDbl(jornadaDto.getTermino(i));
				if (hr >= horaInicial)
					intervalo += Util.calculaDuracao(jornadaDto.getTermino(i), jornadaDto.getInicio(i + 1));
			}
		}
		return intervalo;
	}

	/**
	 * Verifica se a hora informada está fora da Jornada de trabalho. Ou seja, fora de um intervalo válido na Jornada.
	 * 
	 * @param jornadaDto
	 * @param hora
	 * @return true - Está fora da Jornada de Trabalho.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public boolean verificarSeHoraEstaForaDaJornadaDeTrabalho(JornadaTrabalhoDTO jornadaDto, double hora) throws Exception {
		boolean result = true;
		double inicio[] = jornadaDto.getInicio();
		double termino[] = jornadaDto.getTermino();
		for (int i = 1; i <= 5; i++) {
			if (hora >= inicio[i] && hora <= termino[i]) {
				result = false;
				break;
			}
		}

		return result;
	}

	public double calculaCargaHoraria(CalendarioDTO calendarioDto, Timestamp ts) throws Exception {
		Date dataRef = new Date(ts.getTime());
		JornadaTrabalhoDTO jornadaDto = recuperaJornada(calendarioDto, dataRef);
		if (jornadaDto == null)
			return 0.0;

		double horaInicial = Util.getHoraDbl(UtilDatas.getHoraHHMM(ts));
		double horaFinal = retornaHoraTerminoJornada(jornadaDto);

		double result = horaFinal - horaInicial - calculaIntervalos(jornadaDto, horaInicial);
		if (result > 0)
			return result;
		else
			return 0;
	}

	public double calculaCargaHorariaTotal(CalendarioDTO calendarioDto, Timestamp ts) throws Exception {
		Date dataRef = new Date(ts.getTime());
		JornadaTrabalhoDTO jornadaDto = recuperaJornada(calendarioDto, dataRef);
		if (jornadaDto == null)
			return 0.0;

		double horaInicial = retornaHoraInicioJornada(jornadaDto);
		double horaFinal = retornaHoraTerminoJornada(jornadaDto);

		double result = horaFinal - horaInicial - calculaIntervalos(jornadaDto, horaInicial);
		if (result > 0)
			return result;
		else
			return 0;
	}

	@Override
	public boolean jornadaDeTrabalhoEmUso(JornadaTrabalhoDTO jornadaTrabalhoDTO) throws Exception {

		boolean resp = false;

		Integer idJornada = jornadaTrabalhoDTO.getIdJornada();
		CalendarioDao calendarioDao = new CalendarioDao();

		resp = calendarioDao.verificaJornada(idJornada);

		return resp;
	}

	@Override
	public boolean verificaSeExisteCalendario(CalendarioDTO calendarioDTO) throws Exception {
		CalendarioDao calendarioDao = new CalendarioDao();
		return calendarioDao.verificaSeExisteCalendario(calendarioDTO);
	}

	/**
	 * retorna a difença entre o fim do intervalo e um hora que esteja dentro este intervalo
	 * 
	 * @param jornadaDto
	 * @param horaDentroIntervalo
	 * @return
	 */
	public double calcularTempoDentroIntervalo(JornadaTrabalhoDTO jornadaDto, double horaDentroIntervalo) {
		double inicio[] = jornadaDto.getInicio();
		for (int i = 1; i <= 5; i++) {
			if (inicio[i] == 99)
				break;
			if (inicio[i] != 99 && inicio[i] >= horaDentroIntervalo) {
				return inicio[i] - horaDentroIntervalo;
			}
		}
		return 0;
	}

	/**
	 * retorna o inicio do intervalo antes de uma determinada hora
	 * 
	 * @param jornadaDto
	 * @param hora
	 * @return
	 */
	public double retornaInicioIntervalo(JornadaTrabalhoDTO jornadaDto, double hora) {
		double termino[] = jornadaDto.getTermino();
		double horaInicio = 0;
		int i = 1;
		while (termino[i] != 0 && termino[i] <= hora) {
			horaInicio = termino[i];
			i++;
		}
		return horaInicio;

	}

	/**
	 * retorna o fim de um jornada proxima a hora passada
	 * 
	 * @param jornadaDto
	 * @param hora
	 * @return
	 */
	public double retornaProximaHoraTerminoJornada(JornadaTrabalhoDTO jornadaDto, double hora) {
		double termino[] = jornadaDto.getTermino();
		for (int i = 1; i <= 5; i++) {
			if (termino[i] == 0)
				break;
			if (termino[i] != 0 && termino[i] >= hora) {
				return termino[i];
			}
		}
		return 0;
	}

	/**
	 * @author euler.ramos
	 * @param idCalendario
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object verificaSePermiteExcluir(DocumentHTML document, HttpServletRequest request, CalendarioDTO calendario) throws Exception {
		String resultado = "excluir";
/*		Tabelas que se relacionam com calendario:
			RECURSO
			SERVICOCONTRATO
			TIPOLIBERACAO
			TIPOMUDANCA
*/
		RecursoDao recursoDao = new RecursoDao();
		ArrayList<RecursoDTO> listaRecursos = recursoDao.findByIdCalendario(calendario.getIdCalendario());
		if ((listaRecursos!=null)&&(listaRecursos.size()>0)){
			resultado = UtilI18N.internacionaliza(request,"calendario.naoExDevidoRecursos");
		} else {
			ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
			ArrayList<ServicoContratoDTO> listaservicoContrato = servicoContratoDao.findByIdCalendario(calendario.getIdCalendario());
			if ((listaservicoContrato!=null)&&(listaservicoContrato.size()>0)){
				resultado = UtilI18N.internacionaliza(request,"calendario.naoExDevidoServicoContrato");
			} else {
				TipoLiberacaoDAO tipoLiberacaoDAO = new TipoLiberacaoDAO();
				ArrayList<TipoLiberacaoDTO> listaTipoLiberacao = tipoLiberacaoDAO.findByIdCalendario(calendario.getIdCalendario());
				if ((listaTipoLiberacao!=null)&&(listaTipoLiberacao.size()>0)){
					resultado = UtilI18N.internacionaliza(request,"calendario.naoExDevidoTipoLiberacao");
				} else {
					TipoMudancaDAO tipoMudancaDAO = new TipoMudancaDAO();
					ArrayList<TipoMudancaDTO> listaTipoMudanca = tipoMudancaDAO.findByIdCalendario(calendario.getIdCalendario());
					if ((listaTipoMudanca!=null)&&(listaTipoMudanca.size()>0)){
						resultado = UtilI18N.internacionaliza(request,"calendario.naoExDevidoTipoMudanca");
					}
				}
			}
		}
		return resultado;
	}

}