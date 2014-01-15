<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<script type="text/javascript">
function buscaHistoricoPorVersao(){
	document.form.fireEvent("buscaHistoricoPorVersao");
}
</script>
<form  name='form'  action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index'>
<div class="flat_area grid_16" style='opacity: 1; position: relative; width: 98% !important; height: 100% !important; letter-spacing: normal;'>
		<h2><i18n:message key="citcorpore.comum.bemvindo"/></h2>
		<h3><i18n:message key="release.historicoAtualizacoes"/></h3>
		<div style="width: 30%" >
		<label style='font-weight:bold;'><i18n:message key="release.versao"/></label>
			<select   name="versao" id="versao" onchange="buscaHistoricoPorVersao()"> </select>
		</div>
		<div  id="divRelease" style="width: 30%;   overflow: auto ; height: 542px ">
		</div>
</div>
</form>
