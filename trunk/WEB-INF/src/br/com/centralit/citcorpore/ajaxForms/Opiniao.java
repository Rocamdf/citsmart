package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.OpiniaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.OpiniaoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

public class Opiniao extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	
	}
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}   
    	OpiniaoService opiniaoService = (OpiniaoService) ServiceLocator.getInstance().getService(OpiniaoService.class, null); 	
		Integer idUsuario = new Integer(usrDto.getIdUsuario());
		OpiniaoDTO opiniao = (OpiniaoDTO) document.getBean();
		opiniao.setIdUsuario(idUsuario);
		opiniao.setData(UtilDatas.getDataAtual());
		opiniao.setHora(UtilDatas.getDataHoraAtual());
		opiniaoService.create(opiniao);
		document.alert("Obrigado por dar sua opinião!");		
		document.executeScript("fecharPopup()");			
	}
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
	}
	@SuppressWarnings("rawtypes")
	public Class getBeanClass(){
		return OpiniaoDTO.class;
	}
}
