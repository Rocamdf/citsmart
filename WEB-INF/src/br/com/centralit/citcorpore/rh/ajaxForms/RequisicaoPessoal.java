package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.PaisDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.PaisServico;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.rh.bean.CargoAtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.CargoCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoCursoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoExperienciaAnteriorDTO;
import br.com.centralit.citcorpore.rh.bean.CargoExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.CargoFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.CargoHabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.CargoIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoAtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoConhecimentoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoCursoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoExperienciaAnteriorDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoHabilidadeDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.negocio.DescricaoCargoService;
import br.com.centralit.citcorpore.rh.negocio.JornadaEmpregadoService;
import br.com.centralit.citcorpore.rh.negocio.RequisicaoPessoalService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
 
 @SuppressWarnings({"rawtypes","unchecked"})
public class RequisicaoPessoal extends AjaxFormAction {
 
	  public String getAcao() {
		 return RequisicaoPessoalDTO.ACAO_CRIACAO; 
	  }
	  
      public Class getBeanClass() {
            return RequisicaoPessoalDTO.class;
      }
 
      public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          CargosService cargoService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, WebUtil.getUsuarioSistema(request));
          HTMLSelect idCargo = (HTMLSelect) document.getSelectById("idCargo");
          idCargo.removeAllOptions();
          idCargo.addOption("", "--- Selecione ---");
          Collection<CargosDTO> colCargo = cargoService.listarAtivos();
          if(colCargo != null && !colCargo.isEmpty()) {
        	  for (CargosDTO cargoDto : colCargo) {
        		  if (cargoDto.getIdDescricaoCargo() != null)
        			  idCargo.addOption(""+cargoDto.getIdCargo(), cargoDto.getNomeCargo());
				
        	  }
          }
          
          RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();

          HTMLSelect idProjeto = (HTMLSelect) document.getSelectById("idProjeto");
          idProjeto.removeAllOptions();
          idProjeto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
          if (requisicaoPessoalDto.getIdContrato() != null) {
              ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
              ContratoDTO contratoDto = new ContratoDTO();
              contratoDto.setIdContrato(requisicaoPessoalDto.getIdContrato());
              contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
              if (contratoDto != null) {
                  ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
                  Collection colProjetos = projetoService.listHierarquia(contratoDto.getIdCliente(), true);
                  if(colProjetos != null && !colProjetos.isEmpty()) 
                      idProjeto.addOptions(colProjetos, "idProjeto", "nomeHierarquizado", null);
              }
          }
          JornadaEmpregadoService jornadaDeTrabalhoService = (JornadaEmpregadoService) ServiceLocator.getInstance().getService(JornadaEmpregadoService.class, WebUtil.getUsuarioSistema(request));
          HTMLSelect idJornada = (HTMLSelect) document.getSelectById("idJornada");
          idJornada.removeAllOptions();
          idJornada.addOption("", "--- Selecione ---");
          Collection colJornada = jornadaDeTrabalhoService.list();
          if(colJornada != null && !colJornada.isEmpty())
              idJornada.addOptions(colJornada, "idJornada", "identificacao", null);
          
          UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, WebUtil.getUsuarioSistema(request));
          HTMLSelect idUnidade = (HTMLSelect) document.getSelectById("idUnidade");
          idUnidade.removeAllOptions();
          idUnidade.addOption("", "--- Selecione ---");
          Collection colUnidade = unidadeService.listHierarquia();
          if(colUnidade != null && !colUnidade.isEmpty())
              idUnidade.addOptions(colUnidade, "idUnidade", "nome", null);
          
          CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
          HTMLSelect idCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");
          idCentroCusto.removeAllOptions();
          idCentroCusto.addOption("", "--- Selecione ---");
          Collection colCentroCusto = centroResultadoService.listAtivos();
          if(colCentroCusto != null && !colCentroCusto.isEmpty())
              idCentroCusto.addOptions(colCentroCusto, "idCentroResultado", "nomeHierarquizado", null);
          HTMLSelect comboTipo = (HTMLSelect) document.getSelectById("tipoContratacao");
          
          comboTipo.removeAllOptions();
  		  comboTipo.addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
  		  comboTipo.addOption("C", UtilI18N.internacionaliza(request, "colaborador.contratoEmpresaPJ"));
  		  comboTipo.addOption("E", UtilI18N.internacionaliza(request, "colaborador.empregadoCLT"));
  		  comboTipo.addOption("T", UtilI18N.internacionaliza(request, "colaborador.estagio"));
  		  comboTipo.addOption("F", UtilI18N.internacionaliza(request, "colaborador.freeLancer"));
  		  comboTipo.addOption("O", UtilI18N.internacionaliza(request, "colaborador.outros"));
  		  comboTipo.addOption("X", UtilI18N.internacionaliza(request, "colaborador.socio"));
  		  comboTipo.addOption("S", UtilI18N.internacionaliza(request, "colaborador.solicitante"));

  		  preencherComboPais(document, request, response);
  		  
    	  restore(document,request,response); 
      }
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
          RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
          if (requisicaoPessoalDto.getIdSolicitacaoServico() != null) {
	            RequisicaoPessoalService requisicaoPessoalService = (RequisicaoPessoalService) ServiceLocator.getInstance().getService(RequisicaoPessoalService.class, null);
	            requisicaoPessoalDto = (RequisicaoPessoalDTO) requisicaoPessoalService.restore(requisicaoPessoalDto);

	            document.executeScript("inicializaContLinha()");
	            
	            HTMLTable tblFormacaoAcademica = document.getTableById("tblFormacaoAcademica");
	            tblFormacaoAcademica.deleteAllRows();
	            if (requisicaoPessoalDto.getColFormacaoAcademica() != null) {
	            	for (RequisicaoFormacaoAcademicaDTO requisicaoFormacaoAcademicaDto : requisicaoPessoalDto.getColFormacaoAcademica()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"FormacaoAcademica\","+requisicaoFormacaoAcademicaDto.getIdFormacaoAcademica()+",\""+requisicaoFormacaoAcademicaDto.getDescricao()+"\",\""+requisicaoFormacaoAcademicaDto.getObrigatorio()+"\",\""+requisicaoFormacaoAcademicaDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblCertificacao = document.getTableById("tblCertificacao");
	            tblCertificacao.deleteAllRows();
	            if (requisicaoPessoalDto.getColCertificacao() != null) {
	            	for (RequisicaoCertificacaoDTO requisicaoCertificacaoDto : requisicaoPessoalDto.getColCertificacao()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"Certificacao\","+requisicaoCertificacaoDto.getIdCertificacao()+",\""+requisicaoCertificacaoDto.getDescricao()+"\",\""+requisicaoCertificacaoDto.getObrigatorio()+"\",\""+requisicaoCertificacaoDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblCurso = document.getTableById("tblCurso");
	            tblCurso.deleteAllRows();
	            if (requisicaoPessoalDto.getColCurso() != null) {
	            	for (RequisicaoCursoDTO requisicaoCursoDto : requisicaoPessoalDto.getColCurso()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"Curso\","+requisicaoCursoDto.getIdCurso()+",\""+requisicaoCursoDto.getDescricao()+"\",\""+requisicaoCursoDto.getObrigatorio()+"\",\""+requisicaoCursoDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblExperienciaInformatica = document.getTableById("tblExperienciaInformatica");
	            tblExperienciaInformatica.deleteAllRows();
	            if (requisicaoPessoalDto.getColExperienciaInformatica() != null) {
	            	for (RequisicaoExperienciaInformaticaDTO requisicaoExperienciaInformaticaDto : requisicaoPessoalDto.getColExperienciaInformatica()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaInformatica\","+requisicaoExperienciaInformaticaDto.getIdExperienciaInformatica()+",\""+requisicaoExperienciaInformaticaDto.getDescricao()+"\",\""+requisicaoExperienciaInformaticaDto.getObrigatorio()+"\",\""+requisicaoExperienciaInformaticaDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblIdioma = document.getTableById("tblIdioma");
	            tblIdioma.deleteAllRows();
	            if (requisicaoPessoalDto.getColIdioma() != null) {
	            	for (RequisicaoIdiomaDTO requisicaoIdiomaDto : requisicaoPessoalDto.getColIdioma()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"Idioma\","+requisicaoIdiomaDto.getIdIdioma()+",\""+requisicaoIdiomaDto.getDescricao()+"\",\""+requisicaoIdiomaDto.getObrigatorio()+"\",\""+requisicaoIdiomaDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblExperienciaAnterior = document.getTableById("tblExperienciaAnterior");
	            tblExperienciaAnterior.deleteAllRows();
	            if (requisicaoPessoalDto.getColExperienciaAnterior() != null) {
	            	for (RequisicaoExperienciaAnteriorDTO requisicaoExperienciaAnteriorDto : requisicaoPessoalDto.getColExperienciaAnterior()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaAnterior\","+requisicaoExperienciaAnteriorDto.getIdConhecimento()+",\""+requisicaoExperienciaAnteriorDto.getDescricao()+"\",\""+requisicaoExperienciaAnteriorDto.getObrigatorio()+"\",\""+requisicaoExperienciaAnteriorDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblConhecimento = document.getTableById("tblConhecimento");
	            tblConhecimento.deleteAllRows();
	            if (requisicaoPessoalDto.getColConhecimento() != null) {
	            	for (RequisicaoConhecimentoDTO requisicaoConhecimentoDto : requisicaoPessoalDto.getColConhecimento()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"Conhecimento\","+requisicaoConhecimentoDto.getIdConhecimento()+",\""+requisicaoConhecimentoDto.getDescricao()+"\",\""+requisicaoConhecimentoDto.getObrigatorio()+"\",\""+requisicaoConhecimentoDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblHabilidade = document.getTableById("tblHabilidade");
	            tblHabilidade.deleteAllRows();
	            if (requisicaoPessoalDto.getColHabilidade() != null) {
	            	for (RequisicaoHabilidadeDTO requisicaoHabilidadeDto : requisicaoPessoalDto.getColHabilidade()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"Habilidade\","+requisicaoHabilidadeDto.getIdHabilidade()+",\""+requisicaoHabilidadeDto.getDescricao()+"\",\""+requisicaoHabilidadeDto.getObrigatorio()+"\",\""+requisicaoHabilidadeDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            HTMLTable tblAtitudeIndividual = document.getTableById("tblAtitudeIndividual");
	            tblAtitudeIndividual.deleteAllRows();
	            if (requisicaoPessoalDto.getColAtitudeIndividual() != null) {
	            	for (RequisicaoAtitudeIndividualDTO requisicaoAtitudeIndividualDto : requisicaoPessoalDto.getColAtitudeIndividual()) {
	            		document.executeScript("adicionarLinhaSelecionada(\"AtitudeIndividual\","+requisicaoAtitudeIndividualDto.getIdAtitudeIndividual()+",\""+requisicaoAtitudeIndividualDto.getDescricao()+"\",\""+requisicaoAtitudeIndividualDto.getObrigatorio()+"\",\""+requisicaoAtitudeIndividualDto.getDetalhe()+"\");");
	  				}
	            }
	            
	            preencherComboUfs(document,request,requisicaoPessoalDto);
	            preencherComboCidade(document,request,requisicaoPessoalDto); 
          }
          
          requisicaoPessoalDto.setAcao(getAcao());
          HTMLForm form = document.getForm("form");
          form.setValues(requisicaoPessoalDto);
          
      } 

      public void restoreCargo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
          RequisicaoPessoalDTO requisicaoPessoalDto = (RequisicaoPessoalDTO) document.getBean();
          if (requisicaoPessoalDto.getIdCargo() == null) {
        	  document.executeScript("configuraPerfilCargo(0)");
        	  return;
          }
          document.executeScript("configuraPerfilCargo(1)");
          CargosDTO cargosDto = new CargosDTO();
          cargosDto.setIdCargo(requisicaoPessoalDto.getIdCargo());
          
          CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
          cargosDto = (CargosDTO) cargosService.restore(cargosDto);
          
          DescricaoCargoDTO descricaoCargoDto = new DescricaoCargoDTO();
          descricaoCargoDto.setIdDescricaoCargo(cargosDto.getIdDescricaoCargo());
          
          if ( descricaoCargoDto.getIdDescricaoCargo() == null) {
        	  return;
          }
          
          DescricaoCargoService descricaoCargoService = (DescricaoCargoService) ServiceLocator.getInstance().getService(DescricaoCargoService.class, null);
          descricaoCargoDto = (DescricaoCargoDTO) descricaoCargoService.restore(descricaoCargoDto);
          
          HTMLForm form = document.getForm("form");
          form.setValues(descricaoCargoDto);
          
          document.executeScript("inicializaContLinha()");
          
          HTMLTable tblFormacaoAcademica = document.getTableById("tblFormacaoAcademica");
          tblFormacaoAcademica.deleteAllRows();
          if (descricaoCargoDto.getColFormacaoAcademica() != null) {
          	for (CargoFormacaoAcademicaDTO cargoFormacaoAcademicaDto : descricaoCargoDto.getColFormacaoAcademica()) {
          		document.executeScript("adicionarLinhaSelecionada(\"FormacaoAcademica\","+cargoFormacaoAcademicaDto.getIdFormacaoAcademica()+",\""+cargoFormacaoAcademicaDto.getDescricao()+"\",\""+cargoFormacaoAcademicaDto.getObrigatorio()+"\",\""+cargoFormacaoAcademicaDto.getDetalhe()+"\");");
			}
          }
          
          HTMLTable tblCertificacao = document.getTableById("tblCertificacao");
          tblCertificacao.deleteAllRows();
          if (descricaoCargoDto.getColCertificacao() != null) {
          	for (CargoCertificacaoDTO cargoCertificacaoDto : descricaoCargoDto.getColCertificacao()) {
          		document.executeScript("adicionarLinhaSelecionada(\"Certificacao\","+cargoCertificacaoDto.getIdCertificacao()+",\""+cargoCertificacaoDto.getDescricao()+"\",\""+cargoCertificacaoDto.getObrigatorio()+"\",\""+cargoCertificacaoDto.getDetalhe()+"\");");
			}
          }
          
          HTMLTable tblCurso = document.getTableById("tblCurso");
          tblCurso.deleteAllRows();
          if (descricaoCargoDto.getColCurso() != null) {
          	for (CargoCursoDTO cargoCursoDto : descricaoCargoDto.getColCurso()) {
          		document.executeScript("adicionarLinhaSelecionada(\"Curso\","+cargoCursoDto.getIdCurso()+",\""+cargoCursoDto.getDescricao()+"\",\""+cargoCursoDto.getObrigatorio()+"\",\""+cargoCursoDto.getDetalhe()+"\");");
			}
          }
          
          HTMLTable tblExperienciaInformatica = document.getTableById("tblExperienciaInformatica");
          tblExperienciaInformatica.deleteAllRows();
          if (descricaoCargoDto.getColExperienciaInformatica() != null) {
          	for (CargoExperienciaInformaticaDTO cargoExperienciaInformaticaDto : descricaoCargoDto.getColExperienciaInformatica()) {
          		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaInformatica\","+cargoExperienciaInformaticaDto.getIdExperienciaInformatica()+",\""+cargoExperienciaInformaticaDto.getDescricao()+"\",\""+cargoExperienciaInformaticaDto.getObrigatorio()+"\",\""+cargoExperienciaInformaticaDto.getDetalhe()+"\");");
			}
          }
          
          HTMLTable tblIdioma = document.getTableById("tblIdioma");
          tblIdioma.deleteAllRows();
          if (descricaoCargoDto.getColIdioma() != null) {
          	for (CargoIdiomaDTO cargoIdiomaDto : descricaoCargoDto.getColIdioma()) {
          		document.executeScript("adicionarLinhaSelecionada(\"Idioma\","+cargoIdiomaDto.getIdIdioma()+",\""+cargoIdiomaDto.getDescricao()+"\",\""+cargoIdiomaDto.getObrigatorio()+"\",\""+cargoIdiomaDto.getDetalhe()+"\");");
			}
          }
          
          HTMLTable tblExperienciaAnterior = document.getTableById("tblExperienciaAnterior");
          tblExperienciaAnterior.deleteAllRows();
          if (descricaoCargoDto.getColExperienciaAnterior() != null) {
          	for (CargoExperienciaAnteriorDTO cargoExperienciaAnteriorDto : descricaoCargoDto.getColExperienciaAnterior()) {
          		document.executeScript("adicionarLinhaSelecionada(\"ExperienciaAnterior\","+cargoExperienciaAnteriorDto.getIdConhecimento()+",\""+cargoExperienciaAnteriorDto.getDescricao()+"\",\""+cargoExperienciaAnteriorDto.getObrigatorio()+"\",\""+cargoExperienciaAnteriorDto.getDetalhe()+"\");");
			}
          }
          
          HTMLTable tblConhecimento = document.getTableById("tblConhecimento");
          tblConhecimento.deleteAllRows();
          if (descricaoCargoDto.getColConhecimento() != null) {
          	for (CargoConhecimentoDTO cargoConhecimentoDto : descricaoCargoDto.getColConhecimento()) {
          		document.executeScript("adicionarLinhaSelecionada(\"Conhecimento\","+cargoConhecimentoDto.getIdConhecimento()+",\""+cargoConhecimentoDto.getDescricao()+"\",\""+cargoConhecimentoDto.getObrigatorio()+"\",\""+cargoConhecimentoDto.getDetalhe()+"\");");
			}
          }
          
          HTMLTable tblHabilidade = document.getTableById("tblHabilidade");
          tblHabilidade.deleteAllRows();
          if (descricaoCargoDto.getColHabilidade() != null) {
          	for (CargoHabilidadeDTO cargoHabilidadeDto : descricaoCargoDto.getColHabilidade()) {
          		document.executeScript("adicionarLinhaSelecionada(\"Habilidade\","+cargoHabilidadeDto.getIdHabilidade()+",\""+cargoHabilidadeDto.getDescricao()+"\",\""+cargoHabilidadeDto.getObrigatorio()+"\",\""+cargoHabilidadeDto.getDetalhe()+"\");");
			}
          }
          
          HTMLTable tblAtitudeIndividual = document.getTableById("tblAtitudeIndividual");
          tblAtitudeIndividual.deleteAllRows();
          if (descricaoCargoDto.getColAtitudeIndividual() != null) {
          	for (CargoAtitudeIndividualDTO cargoAtitudeIndividualDto : descricaoCargoDto.getColAtitudeIndividual()) {
          		document.executeScript("adicionarLinhaSelecionada(\"AtitudeIndividual\","+cargoAtitudeIndividualDto.getIdAtitudeIndividual()+",\""+cargoAtitudeIndividualDto.getDescricao()+"\",\""+cargoAtitudeIndividualDto.getObrigatorio()+"\",\""+cargoAtitudeIndividualDto.getDetalhe()+"\");");
			}
          }
      } 
      
      private void preencherComboPais(DocumentHTML document, HttpServletRequest request, RequisicaoPessoalDTO requisicaoPessoalDTO) throws Exception {
    	  
    		PaisServico paisServico = (PaisServico) ServiceLocator.getInstance().getService(PaisServico.class, null);

    		HTMLSelect comboPais = (HTMLSelect) document.getSelectById("idPais");
    		ArrayList<PaisDTO> listPais = (ArrayList) paisServico.list();
    		comboPais.removeAllOptions();
    		comboPais.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

    		if (listPais != null) {
    			for (PaisDTO paisDto : listPais) {
    				comboPais.addOption(paisDto.getIdPais().toString(), paisDto.getNomePais());
    			}
    	  }
    	}
        
      protected void preencherComboUfs(DocumentHTML document, HttpServletRequest request, RequisicaoPessoalDTO requisicaoPessoalDTO) throws Exception {
    		UfDTO ufDto = new UfDTO();

    		UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

    		if (requisicaoPessoalDTO.getIdPais() != null) 
    			ufDto.setIdPais(requisicaoPessoalDTO.getIdPais());

    		HTMLSelect comboUfs = (HTMLSelect) document.getSelectById("idUf");

    		ArrayList<UfDTO> listUfs = (ArrayList) ufService.listByIdPais(ufDto);

    		comboUfs.removeAllOptions();
    		comboUfs.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

    		if (listUfs != null) {
    			for (UfDTO uf : listUfs) {
    				comboUfs.addOption(uf.getIdUf().toString(), uf.getNomeUf());
    			}
    		}
    	}
      protected void preencherComboCidade(DocumentHTML document, HttpServletRequest request, RequisicaoPessoalDTO requisicaoPessoalDTO) throws Exception {
    		CidadesDTO cidadeDto = new CidadesDTO();

    		CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

    		if (requisicaoPessoalDTO.getIdUf() != null) {
    			cidadeDto.setIdUf(requisicaoPessoalDTO.getIdUf());
    		}
    		HTMLSelect comboCidade = (HTMLSelect) document.getSelectById("idCidade");

    		ArrayList<CidadesDTO> listCidade = (ArrayList) cidadesService.listByIdCidades(cidadeDto);

    		comboCidade.removeAllOptions();
    		comboCidade.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
    		if (listCidade != null) {
    			for (CidadesDTO cidade : listCidade) {
    				comboCidade.addOption(cidade.getIdCidade().toString(), cidade.getNomeCidade());
    			}
    		}
    	}
    	
      public void preencherComboPais(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

  		RequisicaoPessoalDTO requisicaoPessoalDTO = (RequisicaoPessoalDTO) document.getBean();
  		preencherComboPais(document, request, requisicaoPessoalDTO);
  	}
      
  	public void preencherComboUfs(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

  		RequisicaoPessoalDTO requisicaoPessoalDTO = (RequisicaoPessoalDTO) document.getBean();  		
  		preencherComboUfs(document, request, requisicaoPessoalDTO);
  	}
  	public void preencherComboCidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

  		RequisicaoPessoalDTO requisicaoPessoalDTO = (RequisicaoPessoalDTO) document.getBean();  		
  		preencherComboCidade(document, request, requisicaoPessoalDTO);
  	}
}


