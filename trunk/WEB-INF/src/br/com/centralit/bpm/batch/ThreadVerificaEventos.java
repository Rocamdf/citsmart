package br.com.centralit.bpm.batch;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.integracao.EventoFluxoDao;
import br.com.citframework.util.UtilDatas;

public class ThreadVerificaEventos extends Thread {

	private EventoFluxoDao eventoFluxoDao;
	private Collection<EventoFluxoDTO> eventosDisponiveis;
	// ExecutorService pollDedicadaThreads = Executors.newCachedThreadPool();

	@Override
	public void run() {
		while (true) {
			eventoFluxoDao = new EventoFluxoDao();
			try {
				sleep(5000);
				carregaEventosDisponiveis();
				if (eventosDisponiveis != null) {
					for (EventoFluxoDTO eventoFluxoDto : eventosDisponiveis) {
						boolean bExecuta = true;
						if (eventoFluxoDto.getDataHoraExecucao() != null && eventoFluxoDto.getIntervalo() != null) {
							Timestamp ts = UtilDatas.somaSegundos(eventoFluxoDto.getDataHoraExecucao(), eventoFluxoDto.getIntervalo());
							bExecuta = ts.compareTo(UtilDatas.getDataHoraAtual()) <= 0;
						}
						if (bExecuta) {
							sleep(5000);
							//Forma Antiga, sem usar executor para controlar as threads
							ThreadExecutaEvento thread = new ThreadExecutaEvento(eventoFluxoDto);				
							thread.start();

							//Utilizando poll dedicado, caso a thread não esteja ativa por 60 sec, desabilita ela e retira do cache
							//pollDedicadaThreads.execute(new ThreadExecutaEvento(eventoFluxoDto));			
						}
					}
				}
			} catch (Exception e) {
				System.out.println("#########################################");
				System.out.println("Problemas na execução dos eventos bpm");
				System.out.println("#########################################");
				e.printStackTrace();
			} finally {
				eventoFluxoDao = null;
			}
		}
	}

	private synchronized void carregaEventosDisponiveis() {
		try {
			eventosDisponiveis = eventoFluxoDao.findDisponiveis();
		} catch (Exception e) {
			eventosDisponiveis = null;
			System.out.println("#########################################");
			System.out.println("Problemas na carga dos eventos bpm       ");
			System.out.println("#########################################");
			e.printStackTrace();
		}
	}

}
