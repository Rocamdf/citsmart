package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.PaisDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.PaisServico;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CompetenciaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EmailCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EnderecoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.TelefoneCurriculoDTO;
import br.com.centralit.citcorpore.rh.negocio.CurriculoService;
import br.com.centralit.citcorpore.rh.negocio.IdiomaService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
 
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TemplateCurriculo extends AjaxFormAction {
 
	 public Class getBeanClass() {
         return CurriculoDTO.class;
   }
	 
	 
   public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
	   if(!WebUtil.validarSeUsuarioEstaNaSessao(request, document))
			return;

		CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean(); 
		
		IdiomaService idiomaService = (IdiomaService) ServiceLocator.getInstance().getService(IdiomaService.class, null);
		Collection colIdioma = idiomaService.list();
		
		HTMLSelect idIdioma = document.getSelectById("idioma#idIdioma");
		idIdioma.addOption(" ", "--- Selecione ---");
		idIdioma.addOptions(colIdioma, "idIdioma", "descricao", null);
		
		HTMLSelect combopais = (HTMLSelect) document.getSelectById("idNaturalidade");
		if (combopais != null) {	
			this.preencherComboPaises(combopais, document, request, response);
		}

		HTMLSelect comboUfs = (HTMLSelect) document.getSelectById("enderecoIdUF");

		if (comboUfs != null) {
			this.inicializarCombo(comboUfs, request);
		}

		HTMLSelect comboCidades = (HTMLSelect) document.getSelectById("enderecoNomeCidade");

		if (comboCidades != null) {
			this.inicializarCombo(comboCidades, request);
		}
		
		String idCurriculoPesquisa = request.getParameter("idCurriculoPesquisa");
		
		if(idCurriculoPesquisa != null){
			curriculoDto.setIdCurriculo(Integer.parseInt(idCurriculoPesquisa));
			restore(document, request, response);
		}
		
 	}
	
   public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
	   if(!WebUtil.validarSeUsuarioEstaNaSessao(request, document))
			return;
		
		CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean(); 
		CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
//		CurriculoDao curriculoDao = new CurriculoDao();
		//curriculoDto =  curriculoDao.findIdByCpf("014.462.941-02");

  		curriculoDto = (CurriculoDTO) curriculoService.restore(curriculoDto);
    	
  		// País
  		HTMLSelect combopais = document.getSelectById("idNaturalidade");
  		document.getElementById("idPais").setValue(curriculoDto.getIdNaturalidade().toString());
			this.preencherComboPaises( combopais ,document, request, response);

		// Uf
			this.preencherComboUfs(document, request, response);
		// Cidade
			this.preencherComboCidades(document, request, response);
		
		HTMLForm form = document.getForm("form");
        form.setValues(curriculoDto);
        
        if (curriculoDto.getDataNascimento()==null){
  			document.executeScript("$('spnIdade').innerHTML = ''");
  		}else{
  			String textoIdade = UtilDatas.calculaIdade(curriculoDto.getDataNascimento(), "LONG");
  			document.executeScript("$('spnIdade').innerHTML = '" + textoIdade + "'");
  		}
        
        
        HTMLTable tblTelefones = document.getTableById("tblTelefones");
  		tblTelefones.deleteAllRows();
  		Collection<TelefoneCurriculoDTO> colTelefones = curriculoDto.getColTelefones(); 
  		for (TelefoneCurriculoDTO telefone : colTelefones) {
  			if (telefone.getIdTipoTelefone() != null){
  				if (telefone.getIdTipoTelefone().intValue() == 1){
  					telefone.setDescricaoTipoTelefone("Residencial");
  				}else{
  					telefone.setDescricaoTipoTelefone("CELULAR");
  				}
  			}else{
  				telefone.setDescricaoTipoTelefone("");
  			}
		}
  		if (curriculoDto.getColTelefones()!=null){
  			tblTelefones.addRowsByCollection(curriculoDto.getColTelefones(), 
  					new String[] {"descricaoTipoTelefone", "ddd", "numeroTelefone",""}, 
  					null, 
  					"Já existe registrado esta demanda na tabela", 
  					new String[] { "gerarImgDel"}, 
  					"marcaTelefone", 
  					null);		
  		}
  		tblTelefones.applyStyleClassInAllCells("celulaGrid");
  		
  		HTMLTable tblCertificacao = document.getTableById("tblCertificacao");
  		tblCertificacao.deleteAllRows();
  		if (curriculoDto.getColCertificacao()!=null){
  			tblCertificacao.addRowsByCollection(curriculoDto.getColCertificacao(), 
  					new String[] {"descricao" , "versao" , "validade", ""}, 
  					null, 
  					"Já existe registrado esta demanda na tabela", 
  					new String[] { "gerarImagemDelCertificacao"},
  					"marcaCertificacao", 
  					null);		
  		}
  		tblCertificacao.applyStyleClassInAllCells("celulaGrid");
  		
  		HTMLTable tblIdioma = document.getTableById("tblIdioma");
  		tblIdioma.deleteAllRows();
  		for (Object idioma : curriculoDto.getColIdioma()) {
  			IdiomaCurriculoDTO idiomaCurriculoDTO = (IdiomaCurriculoDTO) idioma;
  			if (idiomaCurriculoDTO.getIdIdioma() != null){
  				if (idiomaCurriculoDTO.getIdNivelConversa().intValue() == 1){
  					idiomaCurriculoDTO.setDescIdNivelConversa("Não tem");
  				}if(idiomaCurriculoDTO.getIdNivelConversa().intValue() == 2){
  					idiomaCurriculoDTO.setDescIdNivelConversa("Intermediária");
  				}
	  			}if(idiomaCurriculoDTO.getIdNivelConversa().intValue() == 3){
	  				idiomaCurriculoDTO.setDescIdNivelConversa("Boa");
	  			}
	  			if(idiomaCurriculoDTO.getIdNivelConversa().intValue() == 4){
	  				idiomaCurriculoDTO.setDescIdNivelConversa("Avançada");
	  			}
	  			if (idiomaCurriculoDTO.getIdNivelEscrita().intValue() == 1){
	  				idiomaCurriculoDTO.setDescIdNivelEscrita("Não tem");
	  			}if(idiomaCurriculoDTO.getIdNivelEscrita().intValue() == 2){
	  				idiomaCurriculoDTO.setDescIdNivelEscrita("Intermediária");
	  			}
	  			if(idiomaCurriculoDTO.getIdNivelEscrita().intValue() == 3){
	  				idiomaCurriculoDTO.setDescIdNivelEscrita("Boa");
	  			}
	  			if(idiomaCurriculoDTO.getIdNivelEscrita().intValue() == 4){
	  				idiomaCurriculoDTO.setDescIdNivelEscrita("Avançada");
	  			}
	  			if (idiomaCurriculoDTO.getIdNivelLeitura().intValue() == 1){
	  				idiomaCurriculoDTO.setDescIdNivelLeitura("Não tem");
	  			}if(idiomaCurriculoDTO.getIdNivelLeitura().intValue() == 2){
	  				idiomaCurriculoDTO.setDescIdNivelLeitura("Intermediária");
	  			}
	  			if(idiomaCurriculoDTO.getIdNivelLeitura().intValue() == 3){
	  				idiomaCurriculoDTO.setDescIdNivelLeitura("Boa");
	  			}
	  			if(idiomaCurriculoDTO.getIdNivelLeitura().intValue() == 4){
	  				idiomaCurriculoDTO.setDescIdNivelLeitura("Avançada");
	  			}
  		}
  			
  		if (curriculoDto.getColIdioma()!=null){
  			tblIdioma.addRowsByCollection(curriculoDto.getColIdioma(), 
  					new String[] {"descricaoIdioma", "descIdNivelEscrita" , "descIdNivelLeitura", "descIdNivelConversa",""}, 
  					null, 
  					"Já existe registrado esta demanda na tabela", 
  					new String[] { "gerarImagemDelIdioma"}, 
  					"marcaIdioma", 
  					null);		
  		}
  		tblIdioma.applyStyleClassInAllCells("celulaGrid");
  		
  		HTMLTable tblEnderecos = document.getTableById("tblEnderecos");
  		tblEnderecos.deleteAllRows();	
  		if (curriculoDto.getColEnderecos()!=null){
  			tblEnderecos.addRowsByCollection(curriculoDto.getColEnderecos(), 
  					new String[] {"imagemEnderecoCorrespondencia", "descricaoTipoEndereco", "logradouro", "complemento", "nomeBairro", "nomeCidade", "nomeUF","cep" ,""}, 
  					null, 
  					"Já existe registrado esta informação na tabela", 
  					new String[] { "gerarImagemDelEndereco"},
  					"marcaEndereco", 
  					null);		
  		}
  		tblEnderecos.applyStyleClassInAllCells("celulaGrid");
  		
  		HTMLTable tblFormacao = document.getTableById("tblFormacao");
  		tblFormacao.deleteAllRows();	
  		if (curriculoDto.getColFormacao()!=null){
  			tblFormacao.addRowsByCollection(curriculoDto.getColFormacao(), 
  					new String[] {"descricaoTipoFormacao" , "instituicao" , "descricaoSituacao" , "descricao", ""}, 
  					null, 
  					"Já existe registrado esta informação na tabela", 
  					new String[] { "gerarImagemDelFormacao"}, 
  					"marcaEndereco", 
  					null);		
  		}
  		tblFormacao.applyStyleClassInAllCells("celulaGrid");
  		
  		HTMLTable tblEmail = document.getTableById("tblEmail");
  		tblEmail.deleteAllRows();	
  		if (curriculoDto.getColEmail()!=null){
  			
  			for (Object obj2 : curriculoDto.getColEmail()) {
  				EmailCurriculoDTO emailCurriculoDTO = (EmailCurriculoDTO) obj2;
  				if(emailCurriculoDTO.getPrincipal() != null && !emailCurriculoDTO.getPrincipal().equals("")){
  					if(emailCurriculoDTO.getPrincipal().equalsIgnoreCase("SIM")){
  						emailCurriculoDTO.setImagemEmailprincipal("Sim");
  					}
  				}
  			}
  			tblEmail.addRowsByCollection(curriculoDto.getColEmail(), 
  					new String[] {"imagemEmailPrincipal" , "descricaoEmail" , ""}, 
  					null, 
  					"Já existe registrado esta informação na tabela", 
  					new String[] { "gerarImgDelEmail"},  
  					null, 
  					null);		
  		}
  		
  		HTMLTable tblExperiencias = document.getTableById("tblExperiencias");
  		tblExperiencias.deleteAllRows();	
  		if (curriculoDto.getColExperienciaProfissional() !=null){
  			tblExperiencias.addRowsByCollection(curriculoDto.getColExperienciaProfissional(), 
  					new String[] {"descricaoEmpresa" , "funcao" , "localidade" , "periodo",""}, 
  					null, 
  					"Já existe registrado esta informação na tabela", 
  					new String[] { "gerarImagemDelExperiencia"},  
  					"marcaEndereco", 
  					null);		
  		}
  		
  		HTMLTable tblCompetencia = document.getTableById("tblCompetencia");
  		tblCompetencia.deleteAllRows();	
  		if (curriculoDto.getColCompetencias()!=null){
  			tblCompetencia.addRowsByCollection(curriculoDto.getColCompetencias(), 
  					new String[] {"descricaoCompetencia",""}, 
  					null, 
  					"Já existe registrado esta informação na tabela", 
  					new String[] { "gerarImagemDelCompetencia"},  
  					"marcaEndereco", 
  					null);		
  		}

		document.executeScript("uploadAnexos.refresh()");
  		restaurarAnexos(document, request, response, curriculoDto.getIdCurriculo());
	}
   
	protected void restaurarAnexos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Integer idCurriculo) throws ServiceException, Exception {
		
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_CURRICULO, idCurriculo);
		Collection colAnexosFotos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.FOTO_CURRICULO, idCurriculo);
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);
		colAnexosFotos = controleGedService.convertListControleGEDToUploadDTO(colAnexosFotos);

		request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);
		
		if (colAnexosFotos != null && colAnexosFotos.size() > 0){
			Iterator it = colAnexosFotos.iterator();
//			UploadFileDTO uploadItem = (UploadFileDTO)it.next();
			UploadDTO uploadItem = (UploadDTO)it.next();
			
			document.executeScript("document.getElementById('divImgFoto').innerHTML = '<img src=\"" + uploadItem.getCaminhoRelativo() + "\" border=0 width=\"167px\" />'");
		}
	}
	
    public void calculaIdade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
 		if(!WebUtil.validarSeUsuarioEstaNaSessao(request, document))
			return;
 		
 		CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean();
 		
 		if (curriculoDto.getDataNascimento()==null){			
 			document.executeScript("document.getElementById('spnIdade').innerHTML = ''");
 			document.alert("Informe uma data válida para a Data de Nascimento!");
 			return;
 		}
 		
 		String textoIdade = UtilDatas.calculaIdade(curriculoDto.getDataNascimento(), "LONG");
 		
 		document.executeScript("document.getElementById('spnIdade').innerHTML = '" + textoIdade + "'");
 	}
   
    
    
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		if(!WebUtil.validarSeUsuarioEstaNaSessao(request, document))
			return;
		
		try {
			
			CurriculoDTO curriculoDto = (CurriculoDTO) document.getBean();
	  		CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
	  		
	  		Collection colTelefones = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(TelefoneCurriculoDTO.class, "colTelefones_Serialize", request);
	  		Collection<EnderecoCurriculoDTO> colEnderecos = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EnderecoCurriculoDTO.class, "colEnderecos_Serialize", request);
	  		Collection colFormacao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(FormacaoCurriculoDTO.class, "colFormacao_Serialize", request);
	  		Collection colEmail = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EmailCurriculoDTO.class, "colEmail_Serialize", request);
	  		Collection colExperienciaProfissional = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ExperienciaProfissionalCurriculoDTO.class, "colExperienciaProfissional_Serialize", request);
	  		Collection colCertificacao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(CertificacaoCurriculoDTO.class, "colCertificacao_Serialize", request);
	  		Collection colIdioma = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(IdiomaCurriculoDTO.class, "colIdioma_Serialize", request);
	  		Collection<CompetenciaCurriculoDTO> colCompetencias = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(CompetenciaCurriculoDTO.class, "colCompetencias_Serialize", request);
	  		
	  		UfDTO obj = new UfDTO();
			obj.setIdPais(Integer.parseInt(request.getParameter("idPais")));
	  		UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);
	  		List<UfDTO> ufs = (List) ufService.listByIdPais(obj);
	  		
	  		//adicionar idufs a coleção
	  		if (ufs != null){
	  			if(colEnderecos != null && colEnderecos.size() > 0){
	  				for (EnderecoCurriculoDTO endereco : colEnderecos) {
	  					for (UfDTO uf : ufs) {
	  						if(uf.getNomeUf().equalsIgnoreCase(endereco.getNomeUF())){
	  							endereco.setEnderecoIdUF(uf.getIdUf());
	  						}
	  					}
	  				}
	  			}
	  		}
		  		
	  		curriculoDto.setColTelefones(colTelefones);
	  		curriculoDto.setColEnderecos(colEnderecos);
	  		curriculoDto.setColFormacao(colFormacao);
	  		curriculoDto.setColEmail(colEmail);
	  		curriculoDto.setColExperienciaProfissional(colExperienciaProfissional);
	  		curriculoDto.setColCertificacao(colCertificacao);
	  		curriculoDto.setColIdioma(colIdioma);
	  		curriculoDto.setColCompetencias(colCompetencias);
	  		
	  		//seta o email principal
	  		if(curriculoDto.getColEmail() != null && curriculoDto.getColEmail().size() >0){
	  			for (Object obj2 : curriculoDto.getColEmail()) {
	  				EmailCurriculoDTO emailCurriculoDTO = (EmailCurriculoDTO) obj2;
	  				if(emailCurriculoDTO.getImagemEmailprincipal() != null && !emailCurriculoDTO.getImagemEmailprincipal().equals("")){
	  					if(emailCurriculoDTO.getImagemEmailprincipal().equalsIgnoreCase("SIM")){
	  						emailCurriculoDTO.setPrincipal("S");
	  					}
	  				}
	  			}
	  		}
	  		
	  		// seta idTipoTelefone
	  		for (Object objTel : curriculoDto.getColTelefones()) {
	  			TelefoneCurriculoDTO telefone = (TelefoneCurriculoDTO) objTel;
	  			telefone.setIdTipoTelefone(0);
	  			if (telefone.getIdTipoTelefone() == null){
	  				if (telefone.getDescricaoTipoTelefone().trim().equalsIgnoreCase("RESIDENCIAL")){
	  					telefone.setIdTipoTelefone(1);
	  				}else{
	  					telefone.setIdTipoTelefone(2);
	  				}
	  			}
			}
	  		
	  		
	  		Collection<UploadDTO> anexos = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
	  		curriculoDto.setAnexos(anexos);
	  		Collection<UploadDTO> uploadDTOs = (Collection<UploadDTO>) request.getSession().getAttribute("ARQUIVOS_UPLOAD");
	  		
	  		if (uploadDTOs != null && uploadDTOs.size() > 0){
	  			
	  			for (UploadDTO uploadDTO : uploadDTOs) {
	  				curriculoDto.setFoto(uploadDTO);
				}
	  			
	  		}
	  		
	  		if (curriculoDto.getIdCurriculo()==null || curriculoDto.getIdCurriculo().intValue()==0){
	  			curriculoDto.setListaNegra("N");
	  			curriculoDto = (CurriculoDTO) curriculoService.create(curriculoDto);
	  		}else{
	  			curriculoService.update(curriculoDto);
	  		}
	  
	        document.alert("Registro gravado com sucesso!");
	        
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		clear(document, request, response);
		
	}
	
	public void setaFoto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Collection col = (Collection)request.getSession().getAttribute("ARQUIVOS_UPLOAD");
		
		if (col != null && col.size() > 0){
			Iterator it = col.iterator();
//			UploadFileDTO uploadItem = (UploadFileDTO)it.next();
			UploadDTO uploadItem = (UploadDTO)it.next();
			
			document.executeScript("document.getElementById('divImgFoto').innerHTML = '<img src=\"" + uploadItem.getCaminhoRelativo() + "\" border=0 width=\"167px\" />'");
			document.executeScript("$('#modal_foto').modal('hide')");
		}
		
	}
      
	public void clear(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		
		request.getSession(true).setAttribute("colUploadsGED", null);
		
		document.executeScript("uploadAnexos.refresh()");
		
  		HTMLTable tblEnderecos = document.getTableById("tblEnderecos");
  		HTMLTable tblTelefones = document.getTableById("tblTelefones");
  		HTMLTable tblFormacao = document.getTableById("tblFormacao");
  		HTMLTable tblEmail = document.getTableById("tblEmail");
  		HTMLTable tblIdioma = document.getTableById("tblIdioma");
  		HTMLTable tblCertificacao = document.getTableById("tblCertificacao");
  		
  		HTMLTable tblExperiencia = document.getTableById("tblExperiencias");
  		HTMLTable tblCompetencia = document.getTableById("tblCompetencia");
  		
  		tblEnderecos.deleteAllRows();
  		tblTelefones.deleteAllRows();  
  		tblFormacao.deleteAllRows();
  		tblEmail.deleteAllRows();
  		tblIdioma.deleteAllRows();
  		tblCertificacao.deleteAllRows();
  		
  		tblExperiencia.deleteAllRows();
  		tblCompetencia.deleteAllRows();

  		document.getForm("formPesquisaCurriculo").clear();
        document.getForm("formFoto").clear();
        document.executeScript("limparGrids()");
        document.executeScript("primeiro()");
        
        apagarFoto(document, request, response);

	}
	
	 public void preencherComboCidades(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
			HTMLSelect comboCidades = (HTMLSelect) document.getSelectById("enderecoNomeCidade");
			
			if(comboCidades != null){
				comboCidades.removeAllOptions();
			}
			
			if (comboCidades != null) {
				this.inicializarCombo(comboCidades, request);

				int ufId = 0;

				if (request.getParameter("hiddenIdUf") != null && !request.getParameter("hiddenIdUf").equals("")) {
					ufId = Integer.parseInt(request.getParameter("hiddenIdUf"));
				} else {
					HTMLElement hiddenIdUf = document.getElementById("hiddenIdUf");

					if (hiddenIdUf != null && hiddenIdUf.getValue() != null && !hiddenIdUf.getValue().equals("")) {
						ufId = Integer.parseInt(hiddenIdUf.getValue());
					}
				}

				if (ufId > 0) {
					CidadesService cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);

					CidadesDTO obj = new CidadesDTO();
					obj.setIdUf(ufId);

					if (cidadesService != null && comboCidades != null) {
						// O nome do método deveria ser listByhiddenIdUf e não listByIdCidades.
						List<CidadesDTO> cidades = (List) cidadesService.listByIdCidades(obj);

						if (cidades != null) {
							// Ordenando cidades alfabeticamente.
							Collections.sort(cidades, new Comparator<CidadesDTO>() {
								@Override
								public int compare(CidadesDTO o1, CidadesDTO o2) {
									if (o1 == null || o1.getNomeCidade().trim().equals("")) {
										return -999;
									}

									if (o1 == null || o1.getNomeCidade().trim().equals("")) {
										return -999;
									}

									return o1.getNomeCidade().compareTo(o2.getNomeCidade());
								}

							});
							for (CidadesDTO cidade : cidades) {
								comboCidades.addOption(cidade.getIdCidade().toString(), cidade.getNomeCidade());
							}
						}
					}
				}
			}
		}
	   
	   private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		   
			componenteCombo.removeAllOptions();
			componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione "));
			
		}
	   
	   public void preencherComboUfs(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		   
			HTMLSelect comboUfs = (HTMLSelect) document.getSelectById("enderecoIdUF");

			if (comboUfs != null) {
				this.inicializarCombo(comboUfs, request);

				int paisId = 0;

				if (request.getParameter("idNaturalidade") != null && !request.getParameter("idNaturalidade").equals("")) {
					paisId = Integer.parseInt(request.getParameter("idPais"));
				} else {
					HTMLElement idPais = document.getElementById("idPais");

					if (idPais != null && idPais.getValue() != null && !idPais.getValue().equals("")) {
						paisId = Integer.parseInt(idPais.getValue());
					}
				}

				if (paisId > 0) {
					UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

					UfDTO obj = new UfDTO();
					obj.setIdPais(paisId);

					if (ufService != null) {
						List<UfDTO> ufs = (List) ufService.listByIdPais(obj);

						// Ordenando ufs alfabeticamente.
						Collections.sort(ufs, new Comparator<UfDTO>() {
							@Override
							public int compare(UfDTO o1, UfDTO o2) {
								if (o1 == null || o1.getNomeUf().trim().equals("")) {
									return -999;
								}

								if (o1 == null || o1.getNomeUf().trim().equals("")) {
									return -999;
								}

								return o1.getNomeUf().compareTo(o2.getNomeUf());
							}

						});

						if (ufs != null) {
							for (UfDTO uf : ufs) {
								comboUfs.addOption(uf.getIdUf().toString(), uf.getNomeUf());
							}
						}
					}
				}
			}
		}
	   
		public void preencherComboPaises(HTMLSelect combopais, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

			if (combopais != null) {
				this.inicializarCombo(combopais, request);

				PaisServico paisServico = (PaisServico) ServiceLocator.getInstance().getService(PaisServico.class, null);

				if (paisServico != null) {
					List<PaisDTO> paises = (List) paisServico.list();

					// Ordenando paises alfabeticamente.
					Collections.sort(paises, new Comparator<PaisDTO>() {
						@Override
						public int compare(PaisDTO o1, PaisDTO o2) {
							if (o1 == null || o1.getNomePais().trim().equals("")) {
								return -999;
							}

							if (o1 == null || o1.getNomePais().trim().equals("")) {
								return -999;
							}

							return o1.getNomePais().compareTo(o2.getNomePais());
						}

					});

					if (paises != null) {
						for (PaisDTO pais : paises) {
							combopais.addOption(pais.getIdPais().toString(), pais.getNomePais());
						}
					}
				}
			}
		}

		
		/**
		 * Metodo para apagar a foto gravada na sessão
		 * 
		 * @param document
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		public void apagarFoto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			request.getSession().setAttribute("colUploadsGED", null);
	        document.getForm("formFoto").clear();
	        request.getSession().setAttribute("ARQUIVOS_UPLOAD", null);
	        
		}
		
}