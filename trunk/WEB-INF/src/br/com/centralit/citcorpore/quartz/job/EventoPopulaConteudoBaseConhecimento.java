package br.com.centralit.citcorpore.quartz.job;

import java.util.ArrayList;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.citframework.service.ServiceLocator;
@SuppressWarnings("unchecked")
public class EventoPopulaConteudoBaseConhecimento  implements Job{

	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService)ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
			ArrayList<BaseConhecimentoDTO> lista = (ArrayList<BaseConhecimentoDTO>) baseConhecimentoService.list();
			if(lista!=null){
				for(BaseConhecimentoDTO baseConhecimentoDto : lista){
					if(baseConhecimentoDto.getConteudo()!=null && !StringUtils.isBlank(baseConhecimentoDto.getConteudo())){
						Source source = new Source(baseConhecimentoDto.getConteudo());
						baseConhecimentoDto.setConteudoSemFormatacao(source.getTextExtractor().toString());
					}
					if(baseConhecimentoDto.getConteudoSemFormatacao() != null && !StringUtils.isBlank(baseConhecimentoDto.getConteudoSemFormatacao())){
						baseConhecimentoService.updateNotNull(baseConhecimentoDto);
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
