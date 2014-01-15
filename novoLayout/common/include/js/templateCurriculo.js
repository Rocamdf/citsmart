// ------------- variaveis globais -------------
var pagina = "1";
var proximaPagina = "";
/*bruno.aquino: foi comentado o trecho de código abaixo e colocado em templateCurriculo.jsp pois não era possivel capturar o locale*/
/*jQuery(function($){
	$("#cep").mask("99.999-999");
	$("#cpf").mask("999.999.999-99");
	$("#telefone").mask("9999-9999");
	$("#dataNascimento").mask("99/99/9999");
	
 Desenvolvedor: Gilberto Nery - Data: 25/11/2013 - Horário: 15:00 - ID Citsmart: 0
 * 
 * Motivo/Comentário: Formato o campo Data de Nascimento para o padrão brasileiro (dd/mm/yyyy)
 * 
	$('#dataNascimento').datepicker({
		dateFormat: 'dd/mm/yy',
		language: 'pt-BR'
	});

});*/

$(function() {
				pagina = 1;
				/*document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
					
					$('#telefone').unmask();
					$('#telefone').mask('(99) 9999-9999').val($('#telefone').val() );
					
					$('#fax').unmask();
					$('#fax').mask('(99) 9999-9999').val($('#fax').val() );
					
					$('#cep').unmask();
					$('#cep').mask('99999-999').val($('#cep').val() );					
					
					$('#idNaturalidade option[value=' + $('#idPais').val() + ']').prop('selected', true);
					$('#enderecoIdUF option[value=' + $('#idUf').val() + ']').prop('selected', true);
					$('#enderecoNomeCidade option[value=' + $('#idCidade').val() + ']').prop('selected', true);
				};*/
				
				
				$('#idNaturalidade').change(function() {
		            $('#idPais').val($('#idNaturalidade option:selected').val() );
		            
		            if ($('#idPais').val() == '') {
		            	$('#hiddenIdUf').val('');
		            	$('#idCidade').val('');
		            	document.formPesquisaCurriculo.fireEvent('preencherComboCidades');
		            }		            
		            document.formPesquisaCurriculo.fireEvent('preencherComboUfs');
		        });
				
				/* CONFIGURA O CAMPO ESCONDIDO idUf DE ACORDO COM A OPÇÃO SELECIONADA NA CAIXA DE SELEÇÃO DE UFs*/
				$('#enderecoIdUF').change(function() {
					$('#hiddenIdUf').val($('#enderecoIdUF option:selected').val() );
					
					if ($('#hiddenIdUf').val() == '') {
						$('#idCidade').val('');
					}
					document.formPesquisaCurriculo.fireEvent('preencherComboCidades');
				});
				
				/* CONFIGURA O CAMPO ESCONDIDO idCidade DE ACORDO COM A OPÇÃO SELECIONADA NA CAIXA DE SELEÇÃO DE CIDADES*/
				$('#enderecoNomeCidade').change(function() {
					$('#idCidade').val($('#enderecoNomeCidade option:selected').val() );					
				});
				
				/* 1 - CONFIGURANDO A MÁSCARA APROPRIADA PARA CADA CAMPO DO FORMULÁRIO */
				
				/* 1.2 - PARA OS CAMPOS TELEFONE E FAX */
			/*	$('#telefone').mask('(99) 9999-9999');
				$('#fax').mask('(99) 9999-9999');
				
				 1.3 - CEP 				
				$('#cep').mask('99999-999');
				
				$('#email').focusout(function() {
					var email = $('#email').val();
					
					if (email != '') {
						// Avaliando a expressão regular para validação do e-mail.
						if (!/\b[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}\b/.test(email) ) {
							alert(i18n_message("citcorpore.validacao.emailInvalido"));
						}
					}
				});*/
			});

function gerarImgDel(row, obj){
    row.cells[3].innerHTML = '<span onclick="excluirTelefone()"class="btn btn-icon glyphicons circle_remove"><i></i>'+i18n_message("carrinho.excluir")+'</span>';
};

function gerarImgDelEmail(row, obj){
	row.cells[2].innerHTML = '<span  onclick="excluirEmail(this.parentNode.parentNode.rowIndex)" class="btn btn-icon glyphicons circle_remove"><i></i>'+i18n_message("carrinho.excluir")+'</span>';
};

function gerarImagemDelEndereco(row, obj){
	row.cells[8].innerHTML = '<span  onclick="excluirEndereco(this.parentNode.parentNode.rowIndex)" class="btn btn-icon glyphicons circle_remove"><i></i>'+i18n_message("carrinho.excluir")+'</span>';
};

function gerarImagemDelFormacao(row, obj){
	row.cells[4].innerHTML = '<span onclick="excluirFormacao(this.parentNode.parentNode.rowIndex)" class="btn btn-icon glyphicons circle_remove"><i></i>'+i18n_message("carrinho.excluir")+'</span>';
};

function gerarImagemDelExperiencia(row, obj){
	row.cells[4].innerHTML = '<span onclick="excluirExperiencia(this.parentNode.parentNode.rowIndex)" class="btn btn-icon glyphicons circle_remove"><i></i>'+i18n_message("carrinho.excluir")+'</span>';
};

function gerarImagemDelCompetencia(row, obj){
	row.cells[1].innerHTML = '<span onclick="excluirCompetencia(this.parentNode.parentNode.rowIndex);" class="btn btn-icon glyphicons circle_remove"><i></i>'+i18n_message("carrinho.excluir")+'</span>';
};

function gerarImagemDelCertificacao(row, obj){
	row.cells[3].innerHTML = '<span onclick="excluirCertificacao(this.parentNode.parentNode.rowIndex);" class="btn btn-icon glyphicons circle_remove"><i></i>'+i18n_message("carrinho.excluir")+'</span>';
};

function gerarImagemDelIdioma(row, obj){
	row.cells[4].innerHTML = '<span onclick="excluirIdioma(this.parentNode.parentNode.rowIndex);" class="btn btn-icon glyphicons circle_remove"><i></i>'+i18n_message("carrinho.excluir")+'</span>';
};

function manipulaDivDeficiencia(){
	
	if(document.formPesquisaCurriculo.portadorNecessidadeEspecial[0].checked){
		document.getElementById('divQualDeficiencia').style.display = 'block';
	}
	else{
		document.getElementById('divQualDeficiencia').style.display = 'none';
	}
}

function manipulaDivQtdFilhos(){
	
	if(document.formPesquisaCurriculo.filhos[0].checked){
		document.getElementById('divQtdFilhos').style.display = 'block';
	}
	else{
		document.getElementById('divQtdFilhos').style.display = 'none';
	}
}

function limpar(){
	window.location = '<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/requisicaoPessoal/requisicaoPessoal.load';
} 

function desabilitarTela() {
   var f = document.formPesquisaCurriculo;
   for(i=0;i<f.length;i++){
       var el =  f.elements[i];
       if (el.type != 'hidden') { 
           if (el.disabled != null && el.disabled != undefined) {
               el.disabled = 'disabled';
            }
        }
    }  
}
  

addEvent(window, "load", load, false);
function load(){        
      document.formPesquisaCurriculo.afterLoad = function () {
      	  habilitarQtdeFilhos();    
      	  habilitarListaDeficiencias(); 
      } 
      document.formPesquisaCurriculo.afterRestore = function () {
      	habilitarQtdeFilhos();
      	habilitarListaDeficiencias();
      }   
}
function validar() {
   }
   
function getObjetoSerializado() {
       var obj = new CIT_RequisicaoPessoalDTO();
       HTMLUtils.setValuesObject(document.formPesquisaCurriculo, obj);
       return ObjectUtils.serializeObject(obj);
	} 
	
	


//--------------------------------------- TELEFONE ---------------------------------------
function marcaTelefone(row, obj){		
}

function LOOKUP_CURRICULOS_select(id,desc){
	document.formPesquisaCurriculo.restore({idCurriculo:id});
}

function excluirTelefone(){
	var tbl = document.getElementById('tblTelefones');
	var iRowFimAux = tbl.rows.length;
	var b = false;
	for(var i = 1; i < iRowFimAux; i++){
		try{
			if (tbl.rows[i].cells[0].innerHTML != '' && tbl.rows[i].cells[0].innerHTML != "&nbsp;"){
				HTMLUtils.deleteRow('tblTelefones', i);
				b = true;
			}
		}catch(ex){
		}
	}	
	if (!b){
		alert(i18n_message("rh.selecioneTelefoneExcluir"));
	}
};	


function addTelefone(){

	var obj = new CIT_TelefoneCurriculoDTO();
	
	//Valida as Informacoes digitadas do Telefone
	if (StringUtils.isBlank(document.getElementById('telefone#idTipoTelefone').value)){
		alert(i18n_message("rh.informeTipoTelefone"));
		document.getElementById('telefone#idTipoTelefone').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('telefone#ddd').value)){
		alert(i18n_message("rh.informeDDD"));
		document.getElementById('telefone#ddd').focus();
		return;
	}else{
		var dddTelefone =  document.getElementById('telefone#ddd').value;
		if (dddTelefone.length <= 1) {
			alert(i18n_message("rh.DDDInvalido"));
			document.getElementById('telefone#ddd').focus();
			return;
		}
	}
	if (StringUtils.isBlank(document.getElementById('telefone').value)){
		alert(i18n_message("rh.informeNumeroTelefone"));
		document.getElementById('telefone').focus();
		return;
	}		
	
	obj.descricaoTipoTelefone = document.getElementById('telefone#idTipoTelefone').options[document.getElementById('telefone#idTipoTelefone').selectedIndex].text;
	obj.numeroTelefone = document.getElementById('telefone').value;
	obj.ddd = document.getElementById('telefone#ddd').value;
	
	HTMLUtils.addRow('tblTelefones', document.formPesquisaCurriculo, 'telefone#idTipoTelefone', obj, ['descricaoTipoTelefone', 'ddd', 'numeroTelefone',''], null, '', [gerarImgDel], marcaTelefone, null, false);
	document.getElementById('telefone').value = '';
	document.getElementById('telefone#ddd').value = '';
	document.getElementById('telefone#idTipoTelefone').value = '';
	document.getElementById('telefone#idTipoTelefone').focus();
};




//--------------------------------------- EMAIL ---------------------------------------

function mostrarEmailPrincipal(row, obj){
	if (obj.principal == 'S'){
		row.cells[1].innerHTML = '<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/ok.png" border="0"/>';
	}else{
		row.cells[1].innerHTML = '&nbsp;';
	}
	validaPrincipal();
};

function excluirEmail(rowIndex){
var tbl = document.getElementById('tblEmail');
	var iRowFimAux = tbl.rows.length;
	var obj = new CIT_EmailCurriculoDTO();
	var b = false;
		try{
			HTMLUtils.deleteRow('tblEmail', rowIndex);
		}catch(ex){
		}
	if (obj.principal == 'S'){
		document.getElementById('email#principal').checked = true;
	}else{
		document.getElementById('email#principal').checked = false;
	}
	
	document.getElementById('email#descricaoEmail').value = '';
	validaPrincipal();
};	

function addEmail(){
	
	var obj = new CIT_EmailCurriculoDTO();
	
	//Valida as Informacoes digitadas do Telefone
	if (StringUtils.isBlank(document.getElementById('email#descricaoEmail').value)){
		alert(i18n_message("rh.informeEmail"));
		document.getElementById('email#descricaoEmail').focus();
		return;
	}else{
		var resultado = ValidacaoUtils.validaEmail(document.getElementById('email#descricaoEmail'),'');
		if (resultado == false) {
			return;
		}
	}
	
	if (document.getElementById('email#principal').checked){
		var tbl = document.getElementById('tblEmail');
		var iRowFimAux = tbl.rows.length;
		for(var i = 1; i < iRowFimAux; i++){
			HTMLUtils.setValueColumnTable('tblEmail', '&nbsp;', 1, null, 0);
		}
		obj.imagemEmailPrincipal = 'Sim';
		obj.principal = 'S';
	}else{
		obj.imagemEmailPrincipal = '';
		obj.principal = 'N';
	}
	
	obj.descricaoEmail = document.getElementById('email#descricaoEmail').value;
	HTMLUtils.addRow('tblEmail', document.formPesquisaCurriculo, 'email', obj, ['imagemEmailPrincipal', 'descricaoEmail',''], null, '', [gerarImgDelEmail], null, null, false);
	document.getElementById('email#descricaoEmail').value = '';
	document.getElementById('email#principal').parentNode.className='';

};
function validaPrincipal() {
	var objEmail = HTMLUtils.getObjectsByTableId('tblEmail');
	
		for(var i = 0; i < objEmail.length; i++){
			var obj = objEmail[i];
			if (obj.principal == 'S') {
				document.getElementById('divPrincipal').style.display = 'none';
				return;
			}
		}
		document.getElementById('divPrincipal').style.display = 'block';
		
}


//--------------------------------------- FORMAÇÃO ---------------------------------------
function marcaFormacao(row, obj){
}

function excluirFormacao(rowIndex){
	HTMLUtils.deleteRow('tblFormacao', rowIndex);
	limpaFormacao
};	

function addFormacao(){
	var obj = new CIT_FormacaoCurriculoDTO();
	
	//Valida as Informacoes digitadas do Telefone
	if (StringUtils.isBlank(document.getElementById('formacao#idTipoFormacao').value)){
		alert(i18n_message("rh.informeFormacao"));
		document.getElementById('formacao#idTipoFormacao').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('formacao#idSituacao').value)){
		alert(i18n_message("rh.informeSituacao"));
		document.getElementById('formacao#idSituacao').focus();
		return;
	}
	
	if (StringUtils.isBlank(document.getElementById('formacao#instituicao').value)){
		alert(i18n_message("rh.informeInstituicao"));
		document.getElementById('formacao#instituicao').focus();
		return;
	}
	
	if (StringUtils.isBlank(document.getElementById('formacaoDescricao').value)){
		alert(i18n_message("rh.informeDescricao"));
		document.getElementById('formacaoDescricaoaddIdioma()').focus();
		return;
	}
	
	obj.descricaoTipoFormacao = document.getElementById('formacao#idTipoFormacao').options[document.getElementById('formacao#idTipoFormacao').selectedIndex].text;
	obj.descricaoSituacao = document.getElementById('formacao#idSituacao').options[document.getElementById('formacao#idSituacao').selectedIndex].text;
	obj.descricao = document.getElementById('formacaoDescricao').value;
	obj.instituicao = document.getElementById('formacao#instituicao').value;
	
	HTMLUtils.addRow('tblFormacao', document.formPesquisaCurriculo, 'formacao', obj, ['descricaoTipoFormacao' , 'instituicao' , 'descricaoSituacao' , 'descricao',''], null, '', [gerarImagemDelFormacao], marcaFormacao, null, false);
	HTMLUtils.applyStyleClassInAllCells('tblFormacao', 'celulaGrid');
	limpaFormacao();
};




//--------------------------------------- CERTIFICAÇÃO ---------------------------------------
function marcaCertificacao(row, obj){
}

function excluirCertificacao(){
	HTMLUtils.deleteRow('tblCertificacao', rowIndex);
	limpaCertificacao();
};	

function addCertificacao(){
	var obj = new CIT_CertificacaoCurriculoDTO();
	
	//Valida as Informacoes digitadas do Telefone
	if (StringUtils.isBlank(document.getElementById('certificacao#descricao').value)){
		alert(i18n_message("rh.informeDescricaoCertificacao"));
		document.getElementById('certificacao#descricao').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('certificacao#versao').value)){
		alert(i18n_message("rh.informeVersaoCertificacao"));
		document.getElementById('certificacao#versao').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('certificacao#validade').value)){
		alert(i18n_message("rh.informeValidadeCertificacao"));
		document.getElementById('certificacao#validade').focus();
		return;
	}else{
		var  validade = document.getElementById('certificacao#validade').value;
		if (validade.length <= 3) {
			alert(i18n_message("rh.validadeCertificacaoInvalida"));
			document.getElementById('certificacao#validade').focus();
			return;
		}
	}		
	
	obj.descricao = document.getElementById('certificacao#descricao').value;
	obj.versao = document.getElementById('certificacao#versao').value;
	obj.validade = document.getElementById('certificacao#validade').value;
	
	HTMLUtils.addRow('tblCertificacao', document.formPesquisaCurriculo, 'certificacao', obj, ['descricao' , 'versao' , 'validade', '' ], null, '', [gerarImagemDelCertificacao], marcaCertificacao, null, false);
	limpaCertificacao();
};




//--------------------------------------- IDIOMA ---------------------------------------
function addIdioma(){
	var obj = new CIT_IdiomaCurriculoDTO();
	
	//Valida as Informacoes digitadas do Idioma
	if (StringUtils.isBlank(document.getElementById('idioma#idIdioma').options[document.getElementById('idioma#idIdioma').selectedIndex].value)){
		alert('Informe o Idioma!');
		document.getElementById('idioma#idIdioma').focus();
		return;
	}
	
	if (StringUtils.isBlank(document.getElementById('idioma#idNivelEscrita').options[document.getElementById('idioma#idNivelEscrita').selectedIndex].value)){
		alert('Informe o Nível de escrita!');
		document.getElementById('idioma#idNivelEscrita').focus();
		return;
	}
	
	if (StringUtils.isBlank(document.getElementById('idioma#idNivelLeitura').options[document.getElementById('idioma#idNivelLeitura').selectedIndex].value)){
		alert('Informe o Nível de leitura!');
		document.getElementById('idioma#idNivelLeitura').focus();
		return;
	}	
	
	if (StringUtils.isBlank(document.getElementById('idioma#idNivelConversa').options[document.getElementById('idioma#idNivelConversa').selectedIndex].value)){
		alert('Informe o Nível de conversação!');
		document.getElementById('idioma#idNivelConversa').focus();
		return;
	}
	
	obj.descricaoIdioma = document.getElementById('idioma#idIdioma').options[document.getElementById('idioma#idIdioma').selectedIndex].text;
	obj.nivelEscrita = document.getElementById('idioma#idNivelEscrita').options[document.getElementById('idioma#idNivelEscrita').selectedIndex].text;
	obj.nivelLeitura = document.getElementById('idioma#idNivelLeitura').options[document.getElementById('idioma#idNivelLeitura').selectedIndex].text;
	obj.nivelConversa = document.getElementById('idioma#idNivelConversa').options[document.getElementById('idioma#idNivelConversa').selectedIndex].text;
	obj.descNivelEscrita = document.getElementById('idioma#idNivelEscrita').options[document.getElementById('idioma#idNivelEscrita').selectedIndex].text;
	obj.descNivelLeitura = document.getElementById('idioma#idNivelLeitura').options[document.getElementById('idioma#idNivelLeitura').selectedIndex].text;
	obj.descNivelConversa = document.getElementById('idioma#idNivelConversa').options[document.getElementById('idioma#idNivelConversa').selectedIndex].text;
	
	var strNivel = '<table width="100%"><tr><td class="celulaGrid"><b>'+i18n_message("rh.escrita")+': </b>' + obj.nivelEscrita + '</td></tr>';
	strNivel = strNivel + '<tr><td class="celulaGrid"><b>'+i18n_message("rh.leitura")+': </b>' + obj.nivelLeitura + '</td></tr>';
	strNivel = strNivel + '<tr><td class="celulaGrid"><b>'+i18n_message("rh.conversacao")+': </b>' + obj.nivelConversa + '</td></tr>';
	strNivel = strNivel + '</table>';
	
	obj.detalhamentoNivel = strNivel;
	
	HTMLUtils.addRow('tblIdioma', document.formPesquisaCurriculo, 'idioma', obj, ['descricaoIdioma', 'descNivelEscrita','descNivelLeitura', 'descNivelConversa',''], null, '', [gerarImagemDelIdioma], marcaIdioma, null, false);
	limpaIdioma();
};

function marcaIdioma(row, obj){
}

function excluirIdioma(rowIndex){
	HTMLUtils.deleteRow('tblIdioma', rowIndex);
	limpaIdioma();
};	




//--------------------------------------- ENDEREÇO  ---------------------------------------
function addEndereco(){
	
	var obj = new CIT_EnderecoCurriculoDTO();
	
	//Valida as Informacoes digitadas do Endereco
	if (StringUtils.isBlank(document.getElementById('endereco#idTipoEndereco').options[document.getElementById('endereco#idTipoEndereco').selectedIndex].value)){
		alert(i18n_message("rh.informeTipoEndereco"));
		document.getElementById('endereco#idTipoEndereco').focus();
		return;
	}	
	if (StringUtils.isBlank(document.getElementById('cep').value)){
		alert(i18n_message("rh.informeCEP"));
		document.getElementById('cep').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('endereco#logradouro').value)){
		alert(i18n_message("rh.informeLogradouro"));
		document.getElementById('endereco#logradouro').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('enderecoIdUF').options[document.getElementById('enderecoIdUF').selectedIndex].value)){
		alert(i18n_message("rh.informeUF"));
		document.getElementById('enderecoIdUF').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('enderecoNomeCidade').value)){
		alert(i18n_message("rh.informeCidade"));
		document.getElementById('enderecoNomeCidade').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('endereco#complemento').value)){
		alert(i18n_message("rh.informeComplemento"));		
		document.getElementById('endereco#complemento').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('endereco#nomeBairro').value)){
		alert(i18n_message("rh.informeBairro"));
		document.getElementById('endereco#nomeBairro').focus();
		return;
	}						
			
	obj.descricaoTipoEndereco = document.getElementById('endereco#idTipoEndereco').options[document.getElementById('endereco#idTipoEndereco').selectedIndex].text;
	
	if (document.getElementById('endereco#correspondencia').checked){
		HTMLUtils.setValueColumnTable('tblEnderecos', '&nbsp;', 0, null, 0);
		var objEnds = HTMLUtils.getObjectsByTableId('tblEnderecos');
		if (objEnds != null){
			for(var i = 0; i < objEnds.length; i++){
				objEnds[i].imagemEnderecoCorrespondencia = '';
				objEnds[i].correspondencia = '';
			}
		}
		obj.imagemEnderecoCorrespondencia = 'Sim';
	}else{
		obj.imagemEnderecoCorrespondencia = 'Não';
	}
	
	var strDetalhamento = '<b>'+i18n_message("avaliacaoFonecedor.endereco")+': </b>' + document.getElementById('endereco#logradouro').value + ' ' + document.getElementById('endereco#complemento').value;
	strDetalhamento = strDetalhamento + ' ' +  '<b>'+i18n_message("localidade.bairro")+':</b> ' + document.getElementById('endereco#nomeBairro').value + ' '  + ' <b>'+i18n_message("lookup.cidade")+':</b> '+ document.getElementById('enderecoNomeCidade').options[document.getElementById('enderecoNomeCidade').selectedIndex].text + ' - ' + document.getElementById('enderecoIdUF').options[document.getElementById('enderecoIdUF').selectedIndex].text;
	strDetalhamento = strDetalhamento + ' ' +  '<b>CEP:</b> ' + document.getElementById('cep').value;
	
	obj.detalhamentoEndereco = strDetalhamento;
	obj.logradouro = document.getElementById('endereco#logradouro').value;
	obj.nomeCidade = document.getElementById('enderecoNomeCidade').options[document.getElementById('enderecoNomeCidade').selectedIndex].text;
	obj.complemento = document.getElementById('endereco#complemento').value;
	obj.cep = document.getElementById('cep').value;
	obj.nomeUF = document.getElementById('enderecoIdUF').options[document.getElementById('enderecoIdUF').selectedIndex].text;
	obj.nomeBairro = document.getElementById('endereco#nomeBairro').value;
	
	HTMLUtils.addRow('tblEnderecos', document.formPesquisaCurriculo, 'endereco', obj, ['imagemEnderecoCorrespondencia', 'descricaoTipoEndereco', 'logradouro', 'complemento', 'nomeBairro', 'nomeCidade', 'nomeUF','cep' ,''], null, '', [gerarImagemDelEndereco], marcaEndereco, null, false);
	
	limpaEndereco();
	document.getElementById('endereco#idTipoEndereco').focus();
	document.getElementById('endereco#correspondencia').parentNode.className='';
	document.formPesquisaCurriculo.fireEvent('preencherComboUfs');
};

function atuEndereco(){
};	

function novoEndereco(){
	limpaEndereco();
	document.getElementById('divAdd').style.display = 'block';
	document.getElementById('divAltera').style.display = 'none';
	HTMLUtils.setValueColumnTable('tblEnderecos', '&nbsp;', 1, null, 0);
	document.getElementById('endereco#idTipoEndereco').focus();
};

function limpaEndereco(){
	document.getElementById('endereco#logradouro').value='';
	document.getElementById('cep').value='';
	document.getElementById('endereco#complemento').value='';
	document.getElementById('endereco#idTipoEndereco').value='';
	document.getElementById('endereco#correspondencia').value='';
	document.getElementById('enderecoNomeCidade').value='';
	document.getElementById('endereco#nomeBairro').value='';
	document.getElementById('enderecoIdUF').value='';
	
//	HTMLUtils.removeAllOptions('enderecoIdUF');
	HTMLUtils.removeAllOptions('enderecoNomeCidade');
	
};

function limpaFormacao(){
	document.getElementById('formacao#idTipoFormacao').value='';
	document.getElementById('formacao#idSituacao').value='';
	document.getElementById('formacao#instituicao').value='';
	document.getElementById('formacaoDescricao').value='';
};

 function limpaCertificacao(){
	document.getElementById('certificacao#descricao').value = '';
	document.getElementById('certificacao#versao').value = '';
	document.getElementById('certificacao#validade').value = '';
	document.getElementById('certificacao#descricao').focus();
};

function limpaExperiencia(){
	document.getElementById('experiencia#empresa').value='';
	document.getElementById('experiencia#funcao').value='';
	document.getElementById('experiencia#localidade').value='';
	document.getElementById('experiencia#idMesInicio').value='';
	document.getElementById('experiencia#anoInicio').value='';
	document.getElementById('experiencia#anoInicio').value='';
	document.getElementById('experiencia#idMesFim').value='';
	document.getElementById('experiencia#anoFim').value='';
};

 function limpaIdioma(){
	document.getElementById('idioma#idNivelEscrita').value = '';
	document.getElementById('idioma#idNivelLeitura').value = '';
	document.getElementById('idioma#idNivelConversa').value = '';
	
	document.getElementById('idioma#idIdioma').value = ' ';
};

function limpaCompetencia(){
	document.getElementById('competencia#descricao').value='';
};

function marcaEndereco(row, obj){	
}	

function excluirEndereco(){
};

function mostrarEnderecoCorrespondencia(row, obj){
	if (obj.correspondencia == 'S'){
		row.cells[1].innerHTML = '<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/ok.png" border="0"/>';
	}else{
		row.cells[1].innerHTML = '&nbsp;';
	}
};

function marcaEndereco(row, obj){		
}
excluirEndereco = function(rowIndex){
	HTMLUtils.deleteRow('tblEnderecos', rowIndex);
	limpaEndereco();
	document.getElementById('divAdd').style.display = 'block';
	document.getElementById('divAltera').style.display = 'none';
};
	
	
function habilitaDivGraduacao() {
	document.getElementById('divPos').style.display = 'none';
	document.getElementById('divGraduacao').style.display = 'block';
}
function habilitaDivPos() {
	document.getElementById('divPos').style.display = 'block';
	document.getElementById('divGraduacao').style.display = 'none';
}
function desabilitaDiv() {
	document.getElementById('divPos').style.display = 'none';
	document.getElementById('divGraduacao').style.display = 'none';
}
function calculaIdade(){
	document.getElementById('spnIdade').innerHTML = '';
	if (!StringUtils.isBlank(document.getElementById('dataNascimento').value)){
		document.formPesquisaCurriculo.fireEvent('calculaIdade');
	}
};	


//--------------------------------------- Experiencias ---------------------------------------
function marcaExperiencia(row, obj){
}

function excluirExperiencia(rowIndex){
	HTMLUtils.deleteRow('tblExperiencias', rowIndex);
	limpaExperiencia();
};	

function addExperiencia(){
	var obj = new CIT_ExperienciaProfissionalCurriculoDTO();
	// experiencia não é obrigatório o candidato pode não ter experiencias!
	
	if (StringUtils.isBlank(document.getElementById('experiencia#empresa').value)){
		alert(i18n_message("rh.informeNomeEmpresa"));
		document.getElementById('experiencia#empresa').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('experiencia#funcao').value)){
		alert(i18n_message("rh.informeFuncao"));
		document.getElementById('experiencia#funcao').focus();
		return;
	}
	
	if (StringUtils.isBlank(document.getElementById('experiencia#idMesInicio').value)){
		alert(i18n_message("rh.informePeriodo"));
		document.getElementById('experiencia#idMesInicio').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('experiencia#anoInicio').value)){
		alert(i18n_message("rh.informeAnoInicio"));
		document.getElementById('experiencia#anoInicio').focus();
		return;
	}else{
		var anoInicio = document.getElementById('experiencia#anoInicio').value;
		if (anoInicio.length <= 3) {
			alert(i18n_message("rh.anoInicioInvalido"));
			document.getElementById('experiencia#anoInicio').focus();
			return;
		}
	}	
	if (StringUtils.isBlank(document.getElementById('experiencia#idMesFim').value)){
		alert(i18n_message("rh.informeMesFim"));
		document.getElementById('experiencia#idMesFim').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('experiencia#anoFim').value)){
		alert(i18n_message("rh.informeAnoFim"));
		document.getElementById('experiencia#anoFim').focus();
		return;
	}else{
		var anoFinal = document.getElementById('experiencia#anoFim').value;
		if (anoFinal.length <= 3) {
			alert(i18n_message("rh.anoFinalInvalido"));
			document.getElementById('experiencia#anoFim').focus();
			return;
		}
	}
	
	
	//Validação ano
	if (anoInicio > anoFinal) {
		alert(i18n_message("rh.anoInicialNaoPodeSerMaiorFinal"));
		document.getElementById('experiencia#anoInicio').focus();
		return;
	} else if (anoInicio == anoFinal) {
		
		var mesInicio = parseInt(document.getElementById('experiencia#idMesInicio').value);
		var mesFim = parseInt(document.getElementById('experiencia#idMesFim').value); 
		
		if(mesInicio > mesFim){
			alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
			document.getElementById('experiencia#anoInicio').focus();
			return;
		}
	}
	
	var strDetalhamento = document.getElementById('experiencia#idMesInicio').options[document.getElementById('experiencia#idMesInicio').selectedIndex].text + ' ' + document.getElementById('experiencia#anoInicio').value;
	strDetalhamento = strDetalhamento + ' - ' +  document.getElementById('experiencia#idMesFim').options[document.getElementById('experiencia#idMesFim').selectedIndex].text + ' '  + document.getElementById('experiencia#anoFim').value;
	
	obj.descricaoEmpresa = document.getElementById('experiencia#empresa').value;
	obj.funcao = document.getElementById('experiencia#funcao').value;
	obj.localidade = document.getElementById('experiencia#localidade').value;
	obj.periodo = strDetalhamento;
	
	HTMLUtils.addRow('tblExperiencias', document.formPesquisaCurriculo, 'experiencia', obj, ['descricaoEmpresa' , 'funcao' , 'localidade' , 'periodo',''], null, '', [gerarImagemDelExperiencia], marcaExperiencia, null, false);
	limpaExperiencia();
};

//--------------------------------------- Experiencias ---------------------------------------
function marcaCompetencia(row, obj){
}

function excluirCompetencia(rowIndex){
	HTMLUtils.deleteRow('tblCompetencia', rowIndex);
	limpaCompetencia();
};	

function addCompetencia(){
	var obj = new CIT_CompetenciaCurriculoDTO();
	
	if (StringUtils.isBlank(document.getElementById('competencia#descricao').value)){
		alert(i18n_message("rh.informeCompetencia"));
		document.getElementById('competencia#descricao').focus();
		return;
	}
	
	obj.descricaoCompetencia = document.getElementById('competencia#descricao').value;
	
	HTMLUtils.addRow('tblCompetencia', document.formPesquisaCurriculo, 'competencia', obj, ['descricaoCompetencia',''], null, '', [gerarImagemDelCompetencia], marcaExperiencia, null, false);
	limpaCompetencia();
};

// -------------- fotos ---------------------------------------
function submitFormFoto(){
	addEvent(document.getElementById("frameUploadFoto"),"load", carregouIFrame);
	
    document.formFoto.setAttribute("target","frameUploadFoto");
    document.formFoto.setAttribute("method","post"); 
    document.formFoto.setAttribute("enctype","multipart/form-data"); 
    document.formFoto.setAttribute("encoding","multipart/form-data"); 
    
    //submetendo 
    document.formFoto.submit();
}

function carregouIFrame() {    
	HTMLUtils.removeEvent(document.getElementById("frameUploadFoto"),"load", carregouIFrame);
	
	document.formPesquisaCurriculo.fireEvent('setaFoto');
	
	$("#POPUP_FICHA").dialog("close");
}

// ------------- autocomplete formaçao ------------------

$(document).ready(function() {
	
	$('#formacaoDescricao').autocomplete({ 
		serviceUrl:'pages/autoCompleteFormacaoCurriculo/autoCompleteFormacaoCurriculo.load',
		noCache: true,
		onSelect: function(value, data){
			//document.form.clear();
			$('#idFormacaoAcademica').val(data);
			$('#formacaoDescricao').val(value);

		}
	});
});

function habilitaDivGraduacao() {
	document.getElementById('divGraduacao').style.display = 'block';
}

function desabilitaDiv() {
	document.getElementById('divGraduacao').style.display = 'none';
}

//--------------------------- funções dos botões de navegação ----------------


function paginacao1(){
	pagina = 1;
	proximaPagina = 2;
	abrirAbaPagina1();
	document.getElementById('numeroPagina').value = pagina;
	$('#liPaginacao4').css("display" ,"block");
	$('#liPaginacao5').css("display" ,"none");
	$("#liPaginacao1").removeClass("disabled");
	$("#liPaginacao1").addClass("disabled");
	$("#liPaginacao2").addClass("disabled");
	
}

function paginacao2(){
	var todosPreenchidos = validarCamposPagina1();
	if (todosPreenchidos == true) {
		pagina = 2;
		proximaPagina = 3;
		abrirAbaPagina2();
		document.getElementById('numeroPagina').value = pagina;
		$('#liPaginacao4').css("display" ,"block");
		$('#liPaginacao5').css("display" ,"none");
		$("#liPaginacao1").removeClass("disabled");
		$("#liPaginacao2").removeClass("disabled");
		return true;
	}else{
		return false;
	}
}

function paginacao3(){
	var todosPreenchidos1 = validarCamposPagina1();
	if (todosPreenchidos1 == true) {
		var todosPreenchidos2 = validarCamposPagina2();
		if (todosPreenchidos2 == true) {
			pagina = 3;
			proximaPagina = 4;
			abrirAbaPagina3();
			document.getElementById('numeroPagina').value = pagina;
			$('#liPaginacao4').css("display" ,"block");
			$('#liPaginacao5').css("display" ,"none");
			$("#liPaginacao1").removeClass("disabled");
			$("#liPaginacao2").removeClass("disabled");
			return true;
		}else{
			$('#ulWizard li:eq(1) a').tab('show');
		}
	}else{
		$('#ulWizard a:first').tab('show');
	}
	return false;
}


function paginacao4(){
	var todosPreenchidos1 = validarCamposPagina1();
	if (todosPreenchidos1 == true) {
		var todosPreenchidos2 = validarCamposPagina2();
		if (todosPreenchidos2 == true) {
			var todosPreenchidos3 = validarCamposPagina3();
			if (todosPreenchidos3 == true) {
				pagina = 4;
				proximaPagina = 5;
				abrirAbaPagina4();
				document.getElementById('numeroPagina').value = pagina;
				$('#liPaginacao4').css("display" ,"block");
				$('#liPaginacao5').css("display" ,"none");
				$("#liPaginacao1").removeClass("disabled");
				$("#liPaginacao2").removeClass("disabled");
				return true;
			}else{
				$('#ulWizard li:eq(2) a').tab('show');
			}
		}else{
			$('#ulWizard li:eq(1) a').tab('show');
		}
	}else{
		$('#ulWizard a:first').tab('show');
	}
	return false;
}

function paginacao5(){
	
	var todosPreenchidos1 = validarCamposPagina1();
	
	if (todosPreenchidos1 == true) {
		
		var todosPreenchidos2 = validarCamposPagina2();
		if (todosPreenchidos2 == true) {
			
			var todosPreenchidos3 = validarCamposPagina3();
			if (todosPreenchidos3 == true) {
				
				var todosPreenchidos4 = validarCamposPagina4();
				if (todosPreenchidos4 == true) {
					
					pagina = 5;
					proximaPagina = 0;
					abrirAbaPagina5();
					document.getElementById('numeroPagina').value = pagina;
					$('#liPaginacao4').css("display" ,"none");
					$('#liPaginacao5').css("display" ,"block");
					$("#liPaginacao1").removeClass("disabled");
					$("#liPaginacao2").removeClass("disabled");
					return true;
					
				}else{
					pagina = 4;
					$('#ulWizard li:eq(3) a').tab('show');
					desabilitarBT();
				}
			}else{
				pagina = 3;
				$('#ulWizard li:eq(2) a').tab('show');
				desabilitarBT();
			}
		}else{
			pagina = 2;
			$('#ulWizard li:eq(1) a').tab('show');
			desabilitarBT();
		}
	}else{
		pagina = 1;
		$('#ulWizard a:first').tab('show');
	}
	
	return false;
	
}

function avancar(){

	if(pagina == 1){
		
		var todosPreenchidos2 = paginacao2();
		if (todosPreenchidos2 == true) {
			$("#wizardCurriculo").find("#li1").each(function() {
				$("#li1").removeClass("active");
				$("#li2").addClass("active");
				$("#tab1").removeClass("active");
				$('#tab2').addClass("active");
			});
			return;
		}
	}
	
	if(pagina == 2){
		
		var todosPreenchidos3 = paginacao3();
		
		if (todosPreenchidos3 == true) {
			$("#wizardCurriculo").find("#li2").each(function() {
				$("#li2").removeClass("active");
				$("#li3").addClass("active");
				$("#tab2").removeClass("active");
				$('#tab3').addClass("active");
			});
			return;
		}
	}
	
	if(pagina == 3){
		
	    var todosPreenchidos4 = paginacao4();
	    
	    if (todosPreenchidos4 == true) {
			$("#wizardCurriculo").find("#li2").each(function() {
				$("#li3").removeClass("active");
				$("#li4").addClass("active");
				$("#tab3").removeClass("active");
				$('#tab4').addClass("active");
			});
			return;
	    }
	}
	
	if(pagina == 4){
		
		var todosPreenchidos5 = paginacao5();
		
		if (todosPreenchidos5 == true) {
			
			$("#wizardCurriculo").find("#li2").each(function() {
				
				$("#li4").removeClass("active");
				$("#li5").addClass("active");
				$("#tab4").removeClass("active");
				$('#tab5').addClass("active");
				$('#liPaginacao4').css("display" ,"none");
				$('#liPaginacao5').css("display" ,"block");
				$('#liPaginacao3').addClass("disabled");
				$('#liPaginacao5').removeClass("disabled");
			});
			
			return;
		}
	}
	
	if (pagina == 5) {
		
		$('#liPaginacao3').css("display" ,"none");
		$("#liPaginacao3").removeClass("disabled");
		
		$('#liPaginacao5').css("display" ,"block");
		$("#liPaginacao5").addClass("enabled");
		
		pagina = 1;
	}else{
		$('#liPaginacao3').css("display" ,"block");
		$('#liPaginacao5').css("display" ,"none");
		$("#liPaginacao5").addClass("disabled");
		$("#liPaginacao3").removeClass("disabled");
	}
	
}


function retroceder(){
	$("#liPaginacao3").removeClass("disabled");
	if(pagina == 5){
		$("#wizardCurriculo").find("#li5").each(function() {
			$("#li4").addClass("active");
			$("#li5").removeClass("active");
			$("#tab4").addClass("active");
			$('#tab5').removeClass("active");
		});
		paginacao4();
		return;
	}
	if(pagina == 4){
		$("#wizardCurriculo").find("#li4").each(function() {
			$("#li4").removeClass("active");
			$("#li3").addClass("active");
			$("#tab4").removeClass("active");
			$('#tab3').addClass("active");
		});
		paginacao3();
		return;
	}
	if(pagina == 3){
		$("#wizardCurriculo").find("#li2").each(function() {
			$("#li3").removeClass("active");
			$("#li2").addClass("active");
			$("#tab3").removeClass("active");
			$('#tab2').addClass("active");
		});
		paginacao2();
		return;
	}
	if(pagina == 2){
		$("#wizardCurriculo").find("#li1").each(function() {
			$("#li2").removeClass("active");
			$("#li1").addClass("active");
			$("#tab2").removeClass("active");
			$('#tab1').addClass("active");
			$('#liPaginacao4').css("display" ,"none");
			$('#liPaginacao5').css("display" ,"block");
			
		});
		paginacao1();
		return;
	}
}
	function avancarUltimo(){
		var todosPreenchidos5 = paginacao5();
		if (todosPreenchidos5 == true) {
			$("#li" + pagina).removeClass("active");
			$("#li5").addClass("active");
			$("#tab" + pagina).removeClass("active");
			$('#tab5').addClass("active");
			$('#liPaginacao4').css("display" ,"none");
			$('#liPaginacao5').css("display" ,"block");
			$("#liPaginacao1").removeClass("disabled");
			$("#liPaginacao3").addClass("disabled");
			$('#liPaginacao5').removeClass("disabled");
		}
	
	}
	
	function desabilitarBT() {
		$('#liPaginacao1').removeClass("disabled");
		$("#liPaginacao2").removeClass("disabled");
	}
	
	function primeiro(){
		$('#ulWizard a:first').tab('show');
		$('#liPaginacao1').addClass("disabled");
		$("#liPaginacao2").addClass("disabled");
		$("#liPaginacao3").removeClass("disabled");
		$("#liPaginacao4").removeClass("disabled");
		$("#liPaginacao5").addClass("disabled");
		$('#liPaginacao4').css("display" ,"block");
		$('#liPaginacao5').css("display" ,"none");
	}
	
	// ---------------------- gravar -------------------
	
	function gravar(){
		
        var itens = HTMLUtils.getObjectsByTableId('tblTelefones');
        document.formPesquisaCurriculo.colTelefones_Serialize.value = ObjectUtils.serializeObjects(itens);
        
        itens = HTMLUtils.getObjectsByTableId('tblEmail');
        document.formPesquisaCurriculo.colEmail_Serialize.value = ObjectUtils.serializeObjects(itens);
        
        itens = HTMLUtils.getObjectsByTableId('tblFormacao');
        document.formPesquisaCurriculo.colFormacao_Serialize.value = ObjectUtils.serializeObjects(itens);
        
        itens = HTMLUtils.getObjectsByTableId('tblCertificacao');
        document.formPesquisaCurriculo.colCertificacao_Serialize.value = ObjectUtils.serializeObjects(itens);
        
        itens = HTMLUtils.getObjectsByTableId('tblIdioma');
        document.formPesquisaCurriculo.colIdioma_Serialize.value = ObjectUtils.serializeObjects(itens);
        
        itens = HTMLUtils.getObjectsByTableId('tblEnderecos');
        document.formPesquisaCurriculo.colEnderecos_Serialize.value = ObjectUtils.serializeObjects(itens);
        
        itens = HTMLUtils.getObjectsByTableId('tblExperiencias');
        document.formPesquisaCurriculo.colExperienciaProfissional_Serialize.value = ObjectUtils.serializeObjects(itens);
        
        itens = HTMLUtils.getObjectsByTableId('tblCompetencia');
        document.formPesquisaCurriculo.colCompetencias_Serialize.value = ObjectUtils.serializeObjects(itens);
		
		document.formPesquisaCurriculo.save();
	}
	
function restaurar(){
	document.formPesquisaCurriculo.fireEvent('restore');
}

function setarImgFoto(src){
	$("#divImgFoto").attr("src",src);
}


function delImgFoto(){
	
	document.getElementById("divImgFoto").innerHTML="";
	HTMLUtils.clearForm(document.formFoto);
	
	document.formPesquisaCurriculo.fireEvent('apagarFoto');	
	
}


function validarCamposPagina1() {
	if (document.formPesquisaCurriculo.nome.value == '') {
		alert(i18n_message("rh.informeNome"));
		document.formPesquisaCurriculo.nome.focus();
		return false;
	}
	if (document.formPesquisaCurriculo.sexo.value == '') {
		alert(i18n_message("rh.informeSexo"));
		document.formPesquisaCurriculo.sexo.focus();
		return false;
	}
	if (document.formPesquisaCurriculo.estadoCivil.value == '0') {
		alert(i18n_message("rh.informeEstadoCivil"));
		document.formPesquisaCurriculo.estadoCivil.focus();
		return false;
	}
	if (document.formPesquisaCurriculo.cpf.value == '') {
		alert(i18n_message("rh.informeCPF"));
		document.formPesquisaCurriculo.cpf.focus();
		return false;
	}else{
		var resultado = validaCPFCurriculo(document.getElementById('cpf'),'');
		if (resultado == false) {
			document.formPesquisaCurriculo.cpf.focus();
			return;
		}
	}
	if (document.formPesquisaCurriculo.dataNascimento.value == '') {
		alert(i18n_message("rh.informeDataNascimento"));
		document.formPesquisaCurriculo.dataNascimento.focus();
		return false;
	}else{
		var resultado = ValidacaoUtils.validaData(document.getElementById('dataNascimento'),'');
		if (resultado == false) {
			return;
		}
	}
	if (document.formPesquisaCurriculo.filhos.value == '') {
		alert(i18n_message("rh.informeFilho"));
		document.formPesquisaCurriculo.filhos.focus();
		return false;
	}
	if (document.formPesquisaCurriculo.idNaturalidade.value == '') {
		alert(i18n_message("rh.informeNacionalidade"));
		document.formPesquisaCurriculo.idNaturalidade.focus();
		return false;
	}
	if (document.formPesquisaCurriculo.cidadeNatal.value == '') {
		alert(i18n_message("rh.informeCidadeNatal"));
		document.formPesquisaCurriculo.cidadeNatal.focus();
		return false;
	}
	var qtdLinhasTabelaTelefone = $("#tblTelefones tr").length;
	if (qtdLinhasTabelaTelefone <= 1) {
		alert(i18n_message("rh.informeTelefone"));
		//document.formPesquisaCurriculo.telefone#ddd.focus();
		return false;
	}
	var qtdLinhasTabelaEmail = $("#tblEmail tr").length;
	if (qtdLinhasTabelaEmail <= 1) {
		alert(i18n_message("rh.informeEmail"));
		//document.formPesquisaCurriculo.email#descricaoEmail.focus();
		return false;
	}
	var qtdLinhasTabelaEndereco = $("#tblEnderecos tr").length;
	if (qtdLinhasTabelaEndereco <= 1) {
		alert(i18n_message("rh.informeEndereco"));
		//document.formPesquisaCurriculo.endereco#idTipoEndereco.focus();
		return false;
	}
	return true;
}

function validarCamposPagina2() {
	var qtdLinhasTabelaFormcao = $("#tblFormacao tr").length;
	if (qtdLinhasTabelaFormcao <= 1) {
		alert(i18n_message("rh.informeFormacao"));
		return false;
	}
	return true;
}

function validarCamposPagina3() {
	var qtdLinhasTabelaExperiencia = $("#tblExperiencias tr").length;
	if (qtdLinhasTabelaExperiencia <= 1) {
		alert(i18n_message("rh.informeExperiencia"));
		//document.formPesquisaCurriculo.email#descricaoEmail.focus();
		return false;
	}
	return true;
}

function validarCamposPagina4() {
	var qtdLinhasTabelaCompetencia = $("#tblCompetencia tr").length;
	if (qtdLinhasTabelaCompetencia <= 1) {
		alert(i18n_message("rh.informeCompetencia"));
		//document.formPesquisaCurriculo.email#descricaoEmail.focus();
		return false;
	}
	var qtdLinhasTabelaCertificacoes = $("#tblCertificacao tr").length;
	if (qtdLinhasTabelaCertificacoes <= 1) {
		alert(i18n_message("rh.informeCertificacoes"));
		//document.formPesquisaCurriculo.email#descricaoEmail.focus();
		return false;
	}
	return true;
}

function abrirAbaPagina1() {
	$('#ulWizard a:first').tab('show');
}

function abrirAbaPagina2() {
	$("#li1").addClass("active");
	$("#li2").removeClass("active");
	
	$('#ulWizard li:eq(1) a').tab('show');
	
}

function abrirAbaPagina3() {
	$("#li2").addClass("active");
	$("#li3").removeClass("active");
	
	$('#ulWizard li:eq(2) a').tab('show');
	
}

function abrirAbaPagina4() {
	$("#li3").addClass("active");
	$("#li4").removeClass("active");
	
	$('#ulWizard li:eq(3) a').tab('show');
}

function abrirAbaPagina5() {
	$("#li4").addClass("active");
	$("#li5").removeClass("active");
	
	$('#ulWizard li:eq(4) a').tab('show');
}

function validaCPFCurriculo(field, label) {
	var cpf = field.value;
	
	if(StringUtils.isBlank(cpf)){
	     return true;
	}
	     
	cpf = cpf.replace(".","");
	cpf = cpf.replace(".","");
	cpf = cpf.replace("-","");                 
	var erro = new String;
	if (cpf.length < 11) erro += label+i18n_message("rh.saoNecessariosDigitosVerificacaoCPF")+"\n\n"; 
	var nonNumbers = /\D/;
	if (nonNumbers.test(cpf)) erro += label+i18n_message("rh.verificacaoCPFSuportaApenasNumeros")+" \n\n"; 
	if (cpf == "00000000000" || cpf == "11111111111" || cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" || cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf == "99999999999"){
		erro += label+i18n_message('citcorpore.validacao.numeroCPFInvalido');
	}
	var a = [];
	var b = new Number;
	var c = 11;
	for (i=0; i<11; i++){
		a[i] = cpf.charAt(i);
	    if (i < 9) b += (a[i] * --c);
	}
	if ((x = b % 11) < 2) { a[9] = 0 } else { a[9] = 11-x }
	b = 0;
	c = 11;
	for (y=0; y<10; y++) b += (a[y] * c--); 
	if ((x = b % 11) < 2) { a[10] = 0; } else { a[10] = 11-x; }
	if ((cpf.charAt(9) != a[9]) || (cpf.charAt(10) != a[10])){
		erro +=label+i18n_message("citcorpore.validacao.numeroCPFInvalido");
	}
	if (erro.length > 0){
		alert(erro);
		field.focus();
		return false;
	}
	return true;
};

function somenteNumero(e){
	 var tecla=(window.event)?event.keyCode:e.which;
	 if((tecla>47 && tecla<58)) 
		 return true;
	 else{
		 if (tecla==8 || tecla==0) return true;
		 	else  
		 		return false;
	 }
}

function limparGrids(){
	
	HTMLUtils.deleteAllRows("tblEmail");
	HTMLUtils.deleteAllRows("tblEnderecos");
	HTMLUtils.deleteAllRows("tblTelefones");
	HTMLUtils.deleteAllRows("tblFormacao");
	HTMLUtils.deleteAllRows("tblIdioma");
	HTMLUtils.deleteAllRows("tblExperiencias");
	HTMLUtils.deleteAllRows("tblCompetencia");
	HTMLUtils.deleteAllRows("tblCertificacao");

	delImgFoto();
	
}