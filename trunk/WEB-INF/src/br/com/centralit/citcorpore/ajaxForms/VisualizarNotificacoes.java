package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.NotificacaoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoNotificacao;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citgerencial.generateservices.incidentes.ControleGenerateSLAPorServicoContrato;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.service.ServiceLocator;

public class VisualizarNotificacoes extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return NotificacaoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NotificacaoDTO notificacaoDTO = (NotificacaoDTO)document.getBean();
		NotificacaoService serviceNotificacao = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);

		Collection colNotificacaoServico = serviceNotificacao.consultarNotificacaoAtivosOrigemServico(notificacaoDTO.getIdContratoNotificacao());
		List colFinal = new ArrayList();
		
		if (colNotificacaoServico != null){
			for(Iterator it = colNotificacaoServico.iterator(); it.hasNext();){
				NotificacaoDTO notificacaoAux = (NotificacaoDTO)it.next();
				if(!notificacaoAux.getTipoNotificacao().isEmpty()){
					if(notificacaoAux.getTipoNotificacao().equalsIgnoreCase("T")){
						notificacaoAux.setNomeTipoNotificacao(UtilI18N.internacionaliza(request, TipoNotificacao.ServTodasAlt.getDescricao()));
					}else if(notificacaoAux.getTipoNotificacao().equalsIgnoreCase("C")){
						notificacaoAux.setNomeTipoNotificacao(UtilI18N.internacionaliza(request, TipoNotificacao.ServADICIONADOS.getDescricao()));
					}else if(notificacaoAux.getTipoNotificacao().equalsIgnoreCase("A")){
						notificacaoAux.setNomeTipoNotificacao(UtilI18N.internacionaliza(request, TipoNotificacao.ServALTERADOS.getDescricao()));
					}else{
						notificacaoAux.setNomeTipoNotificacao(UtilI18N.internacionaliza(request, TipoNotificacao.ServEXCLUIDOS.getDescricao()));
					}
				}
					colFinal.add(notificacaoAux);
				
			}
		}
	//	Collections.sort(colFinal, new ObjectSimpleComparator("getNomeServico", ObjectSimpleComparator.ASC));
		request.setAttribute("listaNotificacoes", colFinal);
	}

}
