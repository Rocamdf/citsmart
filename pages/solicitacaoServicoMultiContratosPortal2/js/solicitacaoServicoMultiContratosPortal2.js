function cancelar(){
	parent.fecharModalNovaSolicitacao();
}
function abrir(){
	parent.abrirModalNovaSolicitacao();
}
function mostraMensagemInsercao(param) {
	bootbox.alert(param, function(result) {
		parent.fecharModalNovaSolicitacao();
	});
}
function validarCampoDescricao() {
    if (/^\s*$/g.test($('#descricao').val())) {
        return false;
    }
    return true;
};
function salvar() {
	if(validarCampoDescricao()) {
		document.form.save();
	}else {
		alert(i18n_message("solicitacaoServico.informedescricao"));
		var editor = new wysihtml5.Editor("descricao");
		editor.focus(true);
	}
}