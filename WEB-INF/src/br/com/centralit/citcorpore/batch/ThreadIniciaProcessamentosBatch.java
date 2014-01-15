package br.com.centralit.citcorpore.batch;

import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;

import org.quartz.SchedulerException;

import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.negocio.ProcessamentoBatchService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

public class ThreadIniciaProcessamentosBatch extends Thread {
	public void run() {
		try {
			sleep(60000);
			sleep(60000);
			sleep(60000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		System.out.println("INICIANDO O START DOS PROCESSAMENTOS !!!");
		
		ProcessamentoBatchService procBatchService = null;
		try {
			procBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Collection col = null;
		if (procBatchService != null){
			try {
				col = procBatchService.list();
			} catch (LogicException e) {
				e.printStackTrace();
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		if (col != null && col.size() > 0){
			org.quartz.SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
			//org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			org.quartz.Scheduler scheduler = null;
			try {
				scheduler = schedulerFactory.getScheduler("CitSmartMonitor");
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
			
			boolean bEntrou = false;
			if (scheduler != null){
				try {
					for(Iterator it = col.iterator(); it.hasNext();){
						ProcessamentoBatchDTO procDto = (ProcessamentoBatchDTO)it.next();
						if (procDto.getSituacao().equalsIgnoreCase("A") && !procDto.getExpressaoCRON().isEmpty()){
							try {
								org.quartz.JobDetail jobDetailSQLs = new org.quartz.JobDetail("Processamento_CITSMART_" + procDto.getIdProcessamentoBatch(),"grupoBatch_CITSMART",JobProcessamentoBatchExecuteSQL.class);
								org.quartz.CronTrigger cronTrigger = new org.quartz.CronTrigger("ProcessamentoBatchCITSMART_" + procDto.getIdProcessamentoBatch(),"CITSMART_PROC_BATCH",procDto.getExpressaoCRON());
								scheduler.scheduleJob(jobDetailSQLs,cronTrigger);
								bEntrou = true;
								System.out.println("JOB INICIADO COM SUCESSO!!! " + procDto.getIdProcessamentoBatch() + " --> " + procDto.getExpressaoCRON() + " (" + procDto.getDescricao() + ")");
							} catch (SchedulerException e) {
								System.out.println("PROBLEMAS AO AGENDAR JOB: " + "Processamento batch CITSMART - SQL: " + procDto.getIdProcessamentoBatch());
								e.printStackTrace();
							} catch (ParseException e) {
								System.out.println("PROBLEMAS AO AGENDAR JOB: " + "Processamento batch CITSMART - SQL: " + procDto.getIdProcessamentoBatch());
								e.printStackTrace();
							}
						}
						procDto = null;
					}	
				} catch (Exception e) {
					System.out.println("PROBLEMAS AO AGENDAR JOB: " + "Processamento batch CITSMART - SQL: ");
				}				
			}else{
				System.out.println("SCHEDULER NAO ENCONTRADO - Problemas no start de Processamentos Batch!!!");
			}
			if (bEntrou){
				try {
					scheduler.start();
				} catch (SchedulerException e) {
					System.out.println("PROBLEMAS AO START OS JOBS BATCH SQLs!!!");
					e.printStackTrace();
					System.gc();
				}
			}
		}		
	}
}
