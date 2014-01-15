package br.com.centralit.citcorpore.quartz.job;

import java.util.ArrayList;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.citframework.service.ServiceLocator;

public class EventoPopulaDescricaoSolicitacao implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			SolicitacaoServicoService service = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			
			@SuppressWarnings("unchecked")
			ArrayList<SolicitacaoServicoDTO> lista = (ArrayList<SolicitacaoServicoDTO>) service.list();
			for (SolicitacaoServicoDTO solicitacaoServicoDTO : lista) {
				
				if (solicitacaoServicoDTO.getDescricao() != null && !StringUtils.isBlank(solicitacaoServicoDTO.getDescricao())) {
					Source source = new Source(solicitacaoServicoDTO.getDescricao());
					solicitacaoServicoDTO.setDescricaoSemFormatacao(source.getTextExtractor().toString());
				}
				if(solicitacaoServicoDTO.getDescricaoSemFormatacao() != null && !StringUtils.isBlank(solicitacaoServicoDTO.getDescricaoSemFormatacao()))
					service.updateNotNull(solicitacaoServicoDTO);
			}
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
