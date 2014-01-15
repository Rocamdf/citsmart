package br.com.centralit.citcorpore.ajaxForms;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.SchedulerException;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.batch.JobProcessamentoBatchExecuteSQL;
import br.com.centralit.citcorpore.batch.JobService;
import br.com.centralit.citcorpore.bean.ExecucaoBatchDTO;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.integracao.ExecucaoBatchDao;
import br.com.centralit.citcorpore.negocio.ProcessamentoBatchService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unused" })
public class ProcessamentoBatch extends AjaxFormAction {

	public void agendaJob(ProcessamentoBatchDTO procDto, DocumentHTML document, HttpServletRequest request) {
		org.quartz.SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
		// org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		org.quartz.Scheduler scheduler = null;
		try {
			scheduler = schedulerFactory.getScheduler("CitSmartMonitor");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		boolean bAgendouFase1 = false;
		boolean bAgendouFase2 = false;
		boolean bEntrou = false;
		if (scheduler != null) {
			if (procDto.getSituacao().equalsIgnoreCase("A")) {
				try {
					org.quartz.JobDetail jobDetailSQLs = new org.quartz.JobDetail("Processamento_CITSMART_" + procDto.getIdProcessamentoBatch(), "grupoBatch_CITSMART",
							JobProcessamentoBatchExecuteSQL.class);
					org.quartz.CronTrigger cronTrigger = new org.quartz.CronTrigger("ProcessamentoBatchCITSMART_" + procDto.getIdProcessamentoBatch(), "CITSMART_PROC_BATCH",
							procDto.getExpressaoCRON());
					String str[] = scheduler.getJobNames("grupoBatch_CITSMART");
					boolean existeJobAdicionado = false;
					if (str != null) {
						for (int i = 0; i < str.length; i++) {
							if (str[i].equalsIgnoreCase("Processamento_CITSMART_" + procDto.getIdProcessamentoBatch())) {
								existeJobAdicionado = true;
							}
						}
					}
					if (existeJobAdicionado) {
						try {
							boolean retDelJob = scheduler.deleteJob("Processamento_CITSMART_" + procDto.getIdProcessamentoBatch(), "grupoBatch_CITSMART");
							System.out.println("Exclusao do Job - " + "Processamento_CITSMART_" + procDto.getIdProcessamentoBatch() + ": " + retDelJob);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					scheduler.scheduleJob(jobDetailSQLs, cronTrigger);
					bEntrou = true;
					bAgendouFase1 = true;
					System.out.println("JOB INICIADO COM SUCESSO!!! " + procDto.getIdProcessamentoBatch() + " --> " + procDto.getExpressaoCRON());
				} catch (SchedulerException e) {
					System.out.println("PROBLEMAS AO AGENDAR JOB: " + "Processamento batch CITSMART - SQL: " + procDto.getIdProcessamentoBatch());
					e.printStackTrace();
				} catch (ParseException e) {
					System.out.println("PROBLEMAS AO AGENDAR JOB: " + "Processamento batch CITSMART - SQL: " + procDto.getIdProcessamentoBatch());
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("SCHEDULER NAO ENCONTRADO - Problemas no start de Processamentos Batch!!!");
		}
		if (bEntrou) {
			try {
				scheduler.start();
				bAgendouFase2 = true;
			} catch (SchedulerException e) {
				System.out.println("PROBLEMAS AO START OS JOBS BATCH SQLs!!!");
				e.printStackTrace();
			}
		}

		if (bAgendouFase1 && bAgendouFase2) {
			document.alert(UtilI18N.internacionaliza(request, "processamentoBatch.processamentoAgendadoComSucesso"));
		}
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse arg2) throws Exception {
		ProcessamentoBatchDTO motivoBean = (ProcessamentoBatchDTO) document.getBean();
		ProcessamentoBatchService motivoService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);

		if (motivoBean.getIdProcessamentoBatch() == null || motivoBean.getIdProcessamentoBatch().intValue() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.necessarioSelecionarRegistro"));
			return;
		} else {
			motivoBean.setSituacao("I");
			motivoService.update(motivoBean);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("document.location.reload()");
	}

	public void executaJobService(DocumentHTML document, HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		ProcessamentoBatchDTO motivoBean = (ProcessamentoBatchDTO) document.getBean();
		if (motivoBean.getNomeClasseJobService() == null || motivoBean.getNomeClasseJobService().trim().length() == 0)
			return;

		JobService jobService = (JobService) Class.forName(motivoBean.getNomeClasseJobService()).newInstance();
		jobService.execute(null);

	}

	public Class getBeanClass() {
		return ProcessamentoBatchDTO.class;
	}

	public void listaProcessamentosBatch(DocumentHTML document, HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		org.quartz.SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
		// org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		org.quartz.Scheduler scheduler = null;
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcessamentoBatchDTO motivoBean = (ProcessamentoBatchDTO) document.getBean();

		populaSelects(document, request, response);

		System.out.println(((HttpServletRequest) request).getHeader("user-agent"));

		String str = "";
		if (motivoBean.getIdProcessamentoBatch() != null) {
			ExecucaoBatchDao execDao = new ExecucaoBatchDao();
			List lst = (List) execDao.findByIdProcBatch(motivoBean.getIdProcessamentoBatch());
			if (lst != null) {
				int i = 0;
				for (Iterator it = lst.iterator(); it.hasNext();) {
					if (i > 10) {
						break;
					}
					ExecucaoBatchDTO execucaoBatchDTO = (ExecucaoBatchDTO) it.next();
					if (!str.equalsIgnoreCase("")) {
						if (((HttpServletRequest) request).getHeader("user-agent").contains("Chrome"))
							str += "----------------------------------------------------------\n";
						else
							str += "----------------------------------------------------------------\n";
					}

					str += UtilI18N.internacionaliza(request, "processamentoBatch.execucaoRealizada") + UtilDatas.formatTimestamp(execucaoBatchDTO.getDataHora());
					str += "\n";
					String aux = execucaoBatchDTO.getConteudo();
					/* Verifica se IE */
					if (((HttpServletRequest) request).getHeader("user-agent").contains("MSIE")) {
						str = str.replaceAll("\n", "<br/>");
					}
					str += aux;
					i++;
				}
			}

			byte[] buffer = str.getBytes();

			ServletOutputStream outputStream = response.getOutputStream();
			response.setContentLength(buffer.length);
			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();
		}
	}

	public void montaExpressaoCron(ProcessamentoBatchDTO processamentoBatchDTO) {
		String expressaoCron = new String();
		if (processamentoBatchDTO.getSegundos() != null) {
			expressaoCron += processamentoBatchDTO.getSegundos() + " ";
		}
		if (processamentoBatchDTO.getMinutos() != null) {
			expressaoCron += processamentoBatchDTO.getMinutos() + " ";
		}
		if (processamentoBatchDTO.getHoras() != null) {
			expressaoCron += processamentoBatchDTO.getHoras() + " ";
		}
		if (processamentoBatchDTO.getDiaDoMes() != null) {
			expressaoCron += processamentoBatchDTO.getDiaDoMes() + " ";
		}
		if (processamentoBatchDTO.getMes() != null) {
			expressaoCron += processamentoBatchDTO.getMes() + " ";
		}
		if (processamentoBatchDTO.getDiaDaSemana() != null) {
			expressaoCron += processamentoBatchDTO.getDiaDaSemana() + " ";
		}
		if (processamentoBatchDTO.getAno() != null) {
			expressaoCron += processamentoBatchDTO.getAno();
		}
		processamentoBatchDTO.setExpressaoCRON(expressaoCron);
	}

	public void populaSelects(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboAno = (HTMLSelect) document.getSelectById("ano");
		comboAno.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		for (Integer ano = 2013; ano <= 2020; ano++) {
			comboAno.addOption(ano.toString(), ano.toString());
		}

		HTMLSelect comboDiaDaSemana = (HTMLSelect) document.getSelectById("diaDaSemana");
		comboDiaDaSemana.addOption("?", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboDiaDaSemana.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		comboDiaDaSemana.addOption("1", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.segundaFeira"));
		comboDiaDaSemana.addOption("2", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.tercaFeira"));
		comboDiaDaSemana.addOption("3", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.quartaFeira"));
		comboDiaDaSemana.addOption("4", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.quintaFeira"));
		comboDiaDaSemana.addOption("5", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.sextaFeira"));
		comboDiaDaSemana.addOption("6", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.sabado"));
		comboDiaDaSemana.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.domingo"));

		HTMLSelect comboDiaDoMes = (HTMLSelect) document.getSelectById("diaDoMes");
		comboDiaDoMes.addOption("?", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboDiaDoMes.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		for (Integer dia = 1; dia <= 31; dia++) {
			comboDiaDoMes.addOption(dia.toString(), dia.toString());
		}

		HTMLSelect comboHoras = (HTMLSelect) document.getSelectById("horas");
		comboHoras.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboHoras.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todas"));
		for (Integer hora = 0; hora <= 23; hora++) {
			comboHoras.addOption(hora.toString(), hora.toString());
		}

		HTMLSelect comboMes = (HTMLSelect) document.getSelectById("mes");
		comboMes.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboMes.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		comboMes.addOption("1", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.janeiro"));
		comboMes.addOption("2", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.fevereiro"));
		comboMes.addOption("3", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.marco"));
		comboMes.addOption("4", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.abril"));
		comboMes.addOption("5", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.maio"));
		comboMes.addOption("6", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.junho"));
		comboMes.addOption("7", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.julho"));
		comboMes.addOption("8", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.agosto"));
		comboMes.addOption("9", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.setembro"));
		comboMes.addOption("10", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.outubro"));
		comboMes.addOption("11", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.novembro"));
		comboMes.addOption("12", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.dezembro"));

		HTMLSelect comboMinutos = (HTMLSelect) document.getSelectById("minutos");
		comboMinutos.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboMinutos.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		for (Integer minuto = 0; minuto <= 59; minuto++) {
			comboMinutos.addOption(minuto.toString(), minuto.toString());
		}

		HTMLSelect comboSegundos = (HTMLSelect) document.getSelectById("segundos");
		comboSegundos.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboSegundos.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		for (Integer segundo = 0; segundo <= 59; segundo++) {
			comboSegundos.addOption(segundo.toString(), segundo.toString());
		}
	}

	public void restore(DocumentHTML document, HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		ProcessamentoBatchDTO processamentoBatchDTO = (ProcessamentoBatchDTO) document.getBean();
		ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
		processamentoBatchDTO = (ProcessamentoBatchDTO) processamentoBatchService.restore(processamentoBatchDTO);

		setaPropriedadesExpressaoCron(processamentoBatchDTO);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(processamentoBatchDTO);

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse arg2) throws Exception {
		ProcessamentoBatchDTO processamentoBatchDTO = (ProcessamentoBatchDTO) document.getBean();
		ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);

		processamentoBatchDTO.setSituacao("A");

		montaExpressaoCron(processamentoBatchDTO);

		if (processamentoBatchDTO.getIdProcessamentoBatch() != null && processamentoBatchDTO.getIdProcessamentoBatch().intValue() > 0) {
			processamentoBatchService.update(processamentoBatchDTO);
			agendaJob(processamentoBatchDTO, document, request);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		} else {
			boolean existeDuplicidade = processamentoBatchService.existeDuplicidade(processamentoBatchDTO);

			if (existeDuplicidade) {
				document.alert(UtilI18N.internacionaliza(request, "MSE01"));
				return;
			} else {
				processamentoBatchDTO = (ProcessamentoBatchDTO) processamentoBatchService.create(processamentoBatchDTO);
				agendaJob(processamentoBatchDTO, document, request);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			}
		}
		HTMLForm form = document.getForm("form");
		form.clear();
	}

	public void setaPropriedadesExpressaoCron(ProcessamentoBatchDTO processamentoBatchDTO) {
		if (processamentoBatchDTO.getExpressaoCRON() != null && !processamentoBatchDTO.getExpressaoCRON().isEmpty()) {
			String expressaoCron = processamentoBatchDTO.getExpressaoCRON().trim();
			expressaoCron = expressaoCron.replaceAll("  ", " ");
			String partes[] = expressaoCron.split(" ");

			if (partes.length > 5) {
				processamentoBatchDTO.setSegundos(partes[0]);
				processamentoBatchDTO.setMinutos(partes[1]);
				processamentoBatchDTO.setHoras(partes[2]);
				processamentoBatchDTO.setDiaDoMes(partes[3]);
				processamentoBatchDTO.setMes(partes[4]);
				processamentoBatchDTO.setDiaDaSemana(partes[5]);
			}
			if (partes.length > 6) {
				processamentoBatchDTO.setAno(partes[6]);
			}
		}
	}
}
