package br.com.centralit.citcorpore.rh.ajaxForms;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.rh.bean.AtitudeCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.AtitudeOrganizacionalDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.negocio.AtitudeCandidatoService;
import br.com.centralit.citcorpore.rh.negocio.AtitudeOrganizacionalService;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.EntrevistaCandidatoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalService;
import br.com.centralit.citcorpore.rh.negocio.TriagemRequisicaoPessoalService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoEntrevista;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({"rawtypes","unchecked"})
public class EntrevistaCandidato extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request,
					"citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '"
					+ Constantes.getValue("SERVER_ADDRESS")
					+ request.getContextPath() + "'");
			return;
		}
		
		restore(document,request,response);
	}

	 public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
	        UsuarioDTO usuario = WebUtil.getUsuario(request);
	        
	        if (usuario == null){
	              document.alert("Sessão expirada! Favor efetuar logon novamente!");
	              return;
	        }
	        
	        EntrevistaCandidatoDTO entrevistaAuxDto = null;
	        EntrevistaCandidatoDTO entrevistaCandidatoDto = (EntrevistaCandidatoDTO) document.getBean();
        	EntrevistaCandidatoService entrevistaCandidatoService = (EntrevistaCandidatoService) ServiceLocator.getInstance().getService(EntrevistaCandidatoService.class, null);
        	
	        if (entrevistaCandidatoDto.getIdEntrevista() != null) {
	        	
	        	entrevistaAuxDto = new EntrevistaCandidatoDTO();
	        	entrevistaAuxDto.setIdEntrevista(entrevistaCandidatoDto.getIdEntrevista());
	        	entrevistaAuxDto = (EntrevistaCandidatoDTO) entrevistaCandidatoService.restore(entrevistaAuxDto);
	        } else if (entrevistaCandidatoDto.getIdTriagem() != null && entrevistaCandidatoDto.getIdCurriculo() != null) {
	        	
	        	entrevistaAuxDto = entrevistaCandidatoService.findByIdTriagemAndIdCurriculo(entrevistaCandidatoDto.getIdTriagem(), 
	        																				entrevistaCandidatoDto.getIdCurriculo());	
	        }
	        	
        	if (entrevistaAuxDto != null)
        		Reflexao.copyPropertyValues(entrevistaAuxDto, entrevistaCandidatoDto);
        	
        	CurriculoDTO curriculoDto = new CurriculoDTO();
        	curriculoDto.setIdCurriculo(entrevistaCandidatoDto.getIdCurriculo());
        	
        	CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
        	curriculoDto = (CurriculoDTO) curriculoService.restore(curriculoDto);
        	
        	TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDto = new TriagemRequisicaoPessoalDTO();
        	triagemRequisicaoPessoalDto.setIdTriagem(entrevistaCandidatoDto.getIdTriagem());
        	
        	TriagemRequisicaoPessoalService triagemRequisicaoPessoaService = (TriagemRequisicaoPessoalService) ServiceLocator.getInstance().getService(TriagemRequisicaoPessoalService.class, null);
        	triagemRequisicaoPessoalDto = (TriagemRequisicaoPessoalDTO) triagemRequisicaoPessoaService.restore(triagemRequisicaoPessoalDto);
        	
        	RequisicaoPessoalDTO requisicaoPessoalDto = new RequisicaoPessoalDTO();
        	requisicaoPessoalDto.setIdSolicitacaoServico(triagemRequisicaoPessoalDto.getIdSolicitacaoServico());
        	
        	RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
        	requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);
        	
        	if (requisicaoPessoalDto != null && requisicaoPessoalDto.getQtdCandidatosAprovados() != null) {
        		document.getElementById("qtdCandidatosAprovados").setValue(requisicaoPessoalDto.getQtdCandidatosAprovados().toString());
        	}
        	
        	CargosDTO cargosDto = new CargosDTO();
        	if (requisicaoPessoalDto != null && requisicaoPessoalDto.getIdCargo() != null) {
        		cargosDto.setIdCargo(requisicaoPessoalDto.getIdCargo());
        	}
        	
        	CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
        	cargosDto = (CargosDTO) cargosService.restore(cargosDto);
        	
        	entrevistaCandidatoDto.setCandidato(curriculoDto.getNome());
        	entrevistaCandidatoDto.setCargoPretendido(cargosDto.getNomeCargo());
        	entrevistaCandidatoDto.setIdade(calculaIdade(curriculoDto.getDataNascimento()));
        	
        	restaurarAnexos(request, entrevistaCandidatoDto.getIdEntrevista());

        	HTMLForm form = document.getForm("form");
	        form.setValues(entrevistaCandidatoDto);
	        
	    	AtitudeOrganizacionalService atitudeOrganizacionalService = (AtitudeOrganizacionalService) ServiceLocator.getInstance().getService(AtitudeOrganizacionalService.class, null);
	        Collection<AtitudeCandidatoDTO> colAtitudesCandidato = null;
	        
	        if (entrevistaCandidatoDto.getIdEntrevista() != null) {

	        	AtitudeCandidatoService atitudeCandidatoService = (AtitudeCandidatoService) ServiceLocator.getInstance().getService(AtitudeCandidatoService.class, null);
	        	colAtitudesCandidato = atitudeCandidatoService.findByIdEntrevista(entrevistaCandidatoDto.getIdEntrevista());
	        }
	        
        	if (colAtitudesCandidato != null) {
        		
        		for (AtitudeCandidatoDTO atitudeCandidatoDto : colAtitudesCandidato) {
        			
        			AtitudeOrganizacionalDTO atitudeOrgDto = new AtitudeOrganizacionalDTO();
        			atitudeOrgDto.setIdAtitudeOrganizacional(atitudeCandidatoDto.getIdAtitudeOrganizacional());
        			atitudeOrgDto = (AtitudeOrganizacionalDTO) atitudeOrganizacionalService.restore(atitudeOrgDto);
        			
        			atitudeCandidatoDto.setDescricao(atitudeOrgDto.getDescricao());
				}
        	} else {
        		
				Collection<AtitudeOrganizacionalDTO> colAtitudesOrg = atitudeOrganizacionalService.list();
				
				if (colAtitudesOrg != null) {
					
					colAtitudesCandidato = new ArrayList();
					
					for (AtitudeOrganizacionalDTO atitudeOrganizacionalDto : colAtitudesOrg) {
						
						AtitudeCandidatoDTO atitudeCandidatoDto = new AtitudeCandidatoDTO();
						atitudeCandidatoDto.setIdAtitudeOrganizacional(atitudeOrganizacionalDto.getIdAtitudeOrganizacional());
	        			atitudeCandidatoDto.setDescricao(atitudeOrganizacionalDto.getDescricao());
	        			atitudeCandidatoDto.setAvaliacao("");
	        			
	        			colAtitudesCandidato.add(atitudeCandidatoDto);
					}
				}
	        }
	        
	        HTMLTable tblAtitudes = document.getTableById("tblAtitudes");
	        tblAtitudes.deleteAllRows();
	        
	        if (colAtitudesCandidato != null) {
	        	tblAtitudes.addRowsByCollection(colAtitudesCandidato, 
	                                                new String[] {"descricao",""}, 
	                                                null, 
	                                                "", 
	                                                new String[] {"gerarCampoAvaliacao"}, 
	                                                null, 
	                                                null);  
	        }
	 }
	 
	 public String calculaIdade(Date dataNascimento) throws Exception{
		 if (dataNascimento==null){	
			 return "Informe uma data válida para a Data de Nascimento!";
		 }
		
		 return UtilDatas.calculaIdadeMesAno(dataNascimento, "LONG");
	 }
	 
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			return;
		}
		
        EntrevistaCandidatoDTO entrevistaCandidatoDto = (EntrevistaCandidatoDTO) document.getBean();
        Collection<UploadDTO> anexos = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
        
        entrevistaCandidatoDto.setAnexos(anexos);
        entrevistaCandidatoDto.setColAtitudes(br.com.citframework.util.WebUtil.deserializeCollectionFromString(AtitudeCandidatoDTO.class, entrevistaCandidatoDto.getSerializeAtitudes()));

    	EntrevistaCandidatoService entrevistaCandidatoService = (EntrevistaCandidatoService) ServiceLocator.getInstance().getService(EntrevistaCandidatoService.class, null);
    	
		if (entrevistaCandidatoDto.getTipoEntrevista().equalsIgnoreCase(TipoEntrevista.RH.name())) {
			
			entrevistaCandidatoDto.setIdEntrevistadorRH(usuario.getIdEmpregado());
		} else {
			
			entrevistaCandidatoDto.setIdEntrevistadorGestor(usuario.getIdEmpregado());
		}
		
		if (entrevistaCandidatoDto.getIdEntrevista() == null) {
			
			entrevistaCandidatoDto.setDataHora(UtilDatas.getDataHoraAtual());
			entrevistaCandidatoDto = (EntrevistaCandidatoDTO) entrevistaCandidatoService.create(entrevistaCandidatoDto);
		} else {
			
			entrevistaCandidatoService.update(entrevistaCandidatoDto);
		}
        
        document.alert("Registro gravado com sucesso!"); 
        document.executeScript("parent.fechaPopupEntrevista();");
	}

	@Override
	public Class getBeanClass() {
		return EntrevistaCandidatoDTO.class;
	}
	
	protected void restaurarAnexos(HttpServletRequest request, Integer idEntrevistaCandidato) throws ServiceException, Exception {
	        ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
	        Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_COLETAPRECOS, idEntrevistaCandidato);
	        Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

	        request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);
	  }

}
