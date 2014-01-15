package br.com.centralit.citcorpore.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.comm.server.Servidor;
import br.com.citframework.excecao.ServiceException;

public class DisparaInventario implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
	try {
	   Servidor.executarPesquisaIPGerarInvenario();
	} catch (ServiceException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
}
