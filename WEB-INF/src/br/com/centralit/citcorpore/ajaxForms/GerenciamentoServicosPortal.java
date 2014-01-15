package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.Enumerados.ItensPorPagina;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings("rawtypes")
public class GerenciamentoServicosPortal extends AjaxFormAction implements GerenciamentoProcessos {

	private GerenciamentoServicosDTO gerenciamentoServicosDTO = new GerenciamentoServicosDTO();

	public void iniciar(StringBuilder sb, HttpServletRequest request, Integer itensPorPagina, Integer paginaSelecionada, Integer tipoLista) throws Exception {
		/* Forçando o filtro pela solicitações abertas pelo usuário */

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		if (usuarioDto != null && usuarioDto.getIdEmpregado() != null) {
			this.getGerenciamentoServicosDTO().setIdSolicitante(usuarioDto.getIdEmpregado());
			criarScriptPaginacao(sb);
			Integer totalPaginasFinal = totalPaginas(request, itensPorPagina);
			carregarCabecalhoGerenciamento(totalPaginasFinal, sb, paginaSelecionada, request, tipoLista);
			renderizarLista(sb, request, itensPorPagina, paginaSelecionada, true, tipoLista);
			carregarRodapeGerenciamento(totalPaginasFinal, sb, paginaSelecionada, request);
		}
	}

	public void renderizarLista(StringBuilder sb, HttpServletRequest request, Integer itensPorPagina, Integer paginaSelecionada, boolean flag, Integer tipoLista) throws Exception {
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		Collection<SolicitacaoServicoDTO> colecao = solicitacaoServicoService.listaSolicitacoesPorIdEmpregado(paginaSelecionada, itensPorPagina, this.getGerenciamentoServicosDTO());
		int i = 0;
		if (flag)
			sb.append("<div  id='esquerda' class='innerTB'>");
		sb.append("<!-- Inicio do loop de solicitações abertas -->");
		if (colecao != null && !colecao.isEmpty()) {
			for (SolicitacaoServicoDTO sol : colecao) {
				/* Detalhado */
				sb.append("<div class='content-area " + (i % 2 == 0 ? "alternado" : "") + " listaDetalhada " + (tipoLista.equals(1) ? "ativo" : "") + "'>");
				sb.append("	<div class='row-fluid'>");
				sb.append("		<div class='span3'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(request, "gerenciaservico.numerosolicitacao") + "</div>");
				sb.append("		      <span class='verde-negrito'>[" + sol.getIdSolicitacaoServico() + "]</span>");
				sb.append("		    </label>");
				sb.append("		</div>");
				sb.append("		<div class='span3'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(request, "solicitacaoServico.tipo") + "</div>");
				sb.append("		      <span class='verde-negrito'>" + sol.getDemanda() + "</span>");
				sb.append("		    </label>");
				sb.append("		</div>");
				sb.append("		<div class='span3'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(request, "solicitacaoServico.prioridade") + "</div>");
				sb.append("			  <span class='prioridade Media'>" + sol.getPrioridade() + "</span>");
				sb.append("		    </label>");
				sb.append("	    </div>");
				sb.append("		<div class='span3'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(request, "solicitacaoServico.prazoLimite") + "</div>");
				if (!sol.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					/* Desenvolvedor: Thiago Matias - Data: 07/11/2013 - Horário: 17:50 - ID Citsmart: 123357 - 
					* Motivo/Comentário: quando a solicitação estava em fase de aprovação getDataHoraLimite vinha Null por isso será inserido o "--" enquando não for aprovada */
					if (sol.getDataHoraLimiteStr() != null){
						sb.append("		      <span class='verde-normal'>" + sol.getDataHoraLimiteStr() + "</span>");
					} else {
						sb.append("		      <span class='verde-normal'> -- </span>");
					}
				}
				sb.append("		    </label>");
				sb.append("	    </div>");
				sb.append(" </div>");
				sb.append(" <div class='row-fluid'>");
				sb.append("		<div class='span6'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(request, "citcorpore.comum.servico") + "</div>");
				sb.append("		      <span class='servico escuro-negrito'>" + sol.getServico() + "</span>");
				sb.append("		    </label>");
				sb.append("		</div>");
				sb.append("		<div class='span3'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(request, "citcorpore.comum.grupoExecutor") + "</div>");
				if (sol.getGrupoAtual() != null) {
					sb.append("		      <span>" + sol.getGrupoAtual() + "</span>");
				} else {
					sb.append("			  <span> - </span>");
				}
				sb.append("		    </label>");
				sb.append("		</div>");
				sb.append("		<div class='span2'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(request, "gerenciaservico.sla") + "</div>");
				if (sol.getSlaACombinar().equals("S") && !sol.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("		      <span class='verde-normal'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.acombinar") + "</span>");
				} else if (!sol.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("		      <span class='verde-normal'>" + sol.getPrazoHH() + " : " + sol.getPrazoMM() + "</span>");
				}
				sb.append("		    </label>");
				sb.append("		</div>");
				sb.append("	    <div class='span1'>");
				sb.append("		    <label class='content-row'>");
				if (sol.getAtrasoSLA() > 0 && !sol.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("		      <div>" + UtilI18N.internacionaliza(request, "tarefa.atraso") + "</div>");
					sb.append("		      <span>" + sol.getAtrasoSLAStr() + "</span>");
				}
				sb.append("		    </label>");
				sb.append("	    </div>");
				sb.append("	</div>");
				sb.append("	<div class='row-fluid'>");
				sb.append("		<div class='span6'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitante") + "</div>");
				sb.append("		      <span>" + sol.getSolicitante() + "</span>");
				sb.append("		    </label>");
				sb.append("	    </div>");
				sb.append("		<div class='span2'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</div>");
				if(sol.getSituacao()!= null && sol.getSituacao().equalsIgnoreCase("EmAndamento")){
					sb.append("		      <span>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento") + "</span>");
				} else if(sol.getSituacao()!= null && sol.getSituacao().equalsIgnoreCase("Suspensa")){
					sb.append("		      <span>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa") + "</span>");
				} else if(sol.getSituacao()!= null && sol.getSituacao().equalsIgnoreCase("Cancelada")){
					sb.append("		      <span>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada") + "</span>");
				} else if(sol.getSituacao()!= null && sol.getSituacao().equalsIgnoreCase("Fechada")){
					sb.append("		      <span>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Fechada") + "</span>");
				} else{
					sb.append("		      <span>" + sol.getSituacao() + "</span>");
				}
				sb.append("			</label>");
				sb.append("		</div>");
				sb.append("		<div class='span4 right'>");
				sb.append("		    <div class='content-row'>");
				sb.append("			    <button type='button' id='visualizar' onclick='visualizarSolicitacao(" + sol.getIdSolicitacaoServico() + ")' class='btn btn-default'>"
						+ UtilI18N.internacionaliza(request, "gerenciaservico.visualizar") + "</button>");
				if (sol.getSituacao().equals(SituacaoSolicitacaoServico.Fechada.toString())) {
					String idHashValidacao = CriptoUtils.generateHash("CODED" + sol.getIdSolicitacaoServico(), "MD5");
					sb.append("				<div id='acoes' class='btn-group btn-block w15 aLeft'>");
					sb.append("					<div class='leadcontainer'>");
					sb.append("						<button class='btn dropdown-lead btn-default'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.acao") + "</button>");
					sb.append("					</div>");
					sb.append("					<a class='btn btn-default dropdown-toggle' data-toggle='dropdown' href='#'><span class='caret'></span> </a>");
					sb.append("					<ul class='dropdown-menu pull-right'>");
					sb.append("						<li id='registraropinicao'  onclick='executaModalOpiniao(" + sol.getIdSolicitacaoServico() + ")'><a href='#'>"
							+ UtilI18N.internacionaliza(request, "portal.gerenciaservico.registraropiniao") + "</a></li>");
					sb.append("						<li id='pesquisasatisfacao' onclick='executaModalPesquisa(" + sol.getIdSolicitacaoServico() + " , \"" + idHashValidacao + "\")'><a href='#'>"
							+ UtilI18N.internacionaliza(request, "portal.gerenciaservico.pesquisasatisfacao") + "</a></li>");
					sb.append("					</ul>");
					sb.append("            </div>");
				}
				sb.append("	    	</div>");
				sb.append("		</div>");
				sb.append(" </div>");
				sb.append("</div>");

				/* Resumido */
				sb.append("<div class='content-area " + (i % 2 == 0 ? "alternado" : "") + " listaResumida " + (tipoLista.equals(2) ? "ativo" : "") + "'>");
				sb.append("	<div class='row-fluid'>");
				sb.append("		<div class='span2'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      	<div>" + UtilI18N.internacionaliza(request, "gerenciaservico.numerosolicitacao") + "</div>");
				sb.append("		      	<span class='verde-negrito'>[" + sol.getIdSolicitacaoServico() + "]</span>");
				sb.append("			</label>");
				sb.append("		</div>");
				sb.append("		<div class='span6'>");
				sb.append("			<label class='content-row'>");
				sb.append("		      	<div>" + UtilI18N.internacionaliza(request, "citcorpore.comum.servico") + "</div>");
				sb.append("		      	<span class='servico escuro-negrito'>" + sol.getServico() + "</span>");
				sb.append("			</label>");
				sb.append("		</div>");
				sb.append("		<div class='span1'>");
				sb.append("			<label class='content-row'>");
				sb.append("				<div>" + UtilI18N.internacionaliza(request, "solicitacaoServico.prioridade") + "</div>");
				sb.append("				<span class='prioridade Media'>" + sol.getPrioridade() + "</span>");
				sb.append("			</label>");
				sb.append("		</div>");
				sb.append("		<div class='span2'>");
				sb.append("			<label class='content-row'>");
				sb.append("				<div>" + UtilI18N.internacionaliza(request, "solicitacaoServico.prazoLimite") + "</div>");
				if (!sol.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("		     	<span class='verde-normal'>" + sol.getDataHoraLimiteStr() + "</span>");
				}
				sb.append("			</label>");
				sb.append("		</div>");
				sb.append("		<div class='span1'>");
				sb.append("			<label class='content-row'>");
				sb.append("   			<div>" + UtilI18N.internacionaliza(request, "gerenciaservico.sla") + "</div>");
				if (sol.getSlaACombinar().equals("S") && !sol.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("				<span class='verde-normal'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.acombinar") + "</span>");
				} else if (!sol.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.toString())) {
					sb.append("				<span class='verde-normal'>" + sol.getPrazoHH() + " : " + sol.getPrazoMM() + "</span>");
				}
				sb.append("			</label>");
				sb.append("		</div>");
				sb.append("	</div>");
				sb.append("	<div class='row-fluid'>");
				sb.append("		<div class='span6'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitante") + "</div>");
				sb.append("		      <span>" + sol.getSolicitante() + "</span>");
				sb.append("		    </label>");
				sb.append("	    </div>");
				sb.append("		<div class='span2'>");
				sb.append("		    <label class='content-row'>");
				sb.append("		      <div>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</div>");
				if(sol.getSituacao()!= null && sol.getSituacao().equalsIgnoreCase("EmAndamento")){
					sb.append("		      <span>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento") + "</span>");
				} else if(sol.getSituacao()!= null && sol.getSituacao().equalsIgnoreCase("Suspensa")){
					sb.append("		      <span>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa") + "</span>");
				} else if(sol.getSituacao()!= null && sol.getSituacao().equalsIgnoreCase("Cancelada")){
					sb.append("		      <span>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada") + "</span>");
				} else if(sol.getSituacao()!= null && sol.getSituacao().equalsIgnoreCase("Fechada")){
					sb.append("		      <span>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Fechada") + "</span>");
				} else{
					sb.append("		      <span>" + sol.getSituacao() + "</span>");
				}
				sb.append("			</label>");
				sb.append("		</div>");
				sb.append("		<div class='span4 right'>");
				sb.append("		    <div class='content-row'>");
				sb.append("			    <button type='button' id='visualizar' onclick='visualizarSolicitacao(" + sol.getIdSolicitacaoServico() + ")' class='btn btn-default'>"
						+ UtilI18N.internacionaliza(request, "gerenciaservico.visualizar") + "</button>");
				if (sol.getSituacao().equals(SituacaoSolicitacaoServico.Fechada.toString())) {
					sb.append("				<div id='acoes' class='btn-group btn-block w15 aLeft'>");
					sb.append("					<div class='leadcontainer'>");
					sb.append("						<button class='btn dropdown-lead btn-default'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.acao") + "</button>");
					sb.append("					</div>");
					sb.append("					<a class='btn btn-default dropdown-toggle' data-toggle='dropdown' href='#'><span class='caret'></span> </a>");
					sb.append("					<ul class='dropdown-menu pull-right'>");
					sb.append("						<li id='registraropinicao'  onclick='executaModalOpiniao(" + sol.getIdSolicitacaoServico() + ")'><a href='#'>"
							+ UtilI18N.internacionaliza(request, "portal.gerenciaservico.registraropiniao") + "</a></li>");
					sb.append("						<li id='pesquisasatisfacao' onclick='executaModalPesquisa(" + sol.getIdSolicitacaoServico() + ")'><a href='#'>"
							+ UtilI18N.internacionaliza(request, "portal.gerenciaservico.pesquisasatisfacao") + "</a></li>");
					sb.append("					</ul>");
					sb.append("            </div>");
				}
				sb.append("	    	</div>");
				sb.append("		</div>");
				sb.append(" </div>");
				sb.append("</div>");

				i++;
			}
		} else {
			sb.append("<div class='content-area'>");
			sb.append("	<div class='row-fluid'>");
			sb.append("		<div class='innerTB'>");
			sb.append("			<h4>" + UtilI18N.internacionaliza(request, "citcorpore.comum.resultado") + "</h4>");
			sb.append("		</div>");
			sb.append("	</div>");
			sb.append("</div>");
		}
		sb.append("<!-- Fim do loop de solicitações abertas -->");
		if (flag)
			sb.append("</div>");
	}

	public void carregarCabecalhoGerenciamento(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request, Integer tipoLista) throws Exception {
		/* Inicio Abertura da div maior */
		sb.append("<div class='row-fluid' >");
		sb.append("		<div class='span12'>");
		/* Inicio Abertura da div maior */

		sb.append("	<input type='hidden' id='paginaSelecionada' name='paginaSelecionada' />");
		sb.append("	<input type='hidden' id='quantidadeAtrasadas' name='quantidadeAtrasadas' />");
		sb.append("	<input type='hidden' id='quantidadeTotal' name='quantidadeTotal' />");
		sb.append("	<input type='hidden' id='tipoLista' name='tipoLista' value='" + (tipoLista == null ? 1 : tipoLista) + "' />");
		sb.append("<div id='titulo' >");
		sb.append("		<div class='row-fluid inicio'>");
		sb.append("			<div class='span6'>");
		sb.append("				<span class='btn btn-icon btn-primary' onclick='modalNovaSolicitacaoServico()'><i></i>" + UtilI18N.internacionaliza(request, "gerenciaservico.novasolicitacao") + "</span>");
		sb.append("				<button class='btn btn-default btn-primary' type='button' onclick='atualizarLista();'><i class='icon-white icon-refresh'></i></button>");
		sb.append("				<div class='uniformjs atualizar control-group'>");
		sb.append("      			<label class='checkbox'><div class='checker' id='uniform-undefined'><span class=''><input type='checkbox' name='chkAtualiza' id='chkAtualiza' value='X' style='opacity: 0;'></span></div> "
				+ UtilI18N.internacionaliza(request, "citcorpore.comum.atualizar") + "&nbsp;" + UtilI18N.internacionaliza(request, "citcorpore.comum.automaticamente") + " </label>");
		sb.append("				</div>");
		sb.append("			</div>");
		sb.append("			<div class='span6 paginacaoGerenciamento'>");

		paginacaoGerenciamento(totalPaginas, sb, paginaSelecionada, request);

		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("		<div class='row-fluid inicio'>");
		sb.append("			<div class='span8'>");
		sb.append("				<div class='lFloat rMargin'>");
		if(tipoLista != null){
			sb.append("					<a id='listaDetalhada' class='btn " + (tipoLista.equals(1) ? "btn-primary" : "") + "'>");
			sb.append("					<i class='icon-list lPadding " + (tipoLista.equals(1) ? "icon-white" : "") + "'></i>" + UtilI18N.internacionaliza(request, "citcorpore.comum.detalhado") + "</a>");
			sb.append("				</div>");
			sb.append("				<div class='lFloat rMargin'>");
			sb.append("					<a id='listaResumida' class='btn " + (tipoLista.equals(2) ? "btn-primary" : "") + "'>");
			sb.append("					<i class='icon-th-list lPadding " + (tipoLista.equals(2) ? "icon-white" : "") + "'></i>" + UtilI18N.internacionaliza(request, "citcorpore.comum.resumido") + "</a>");
		}
		sb.append("				</div>");
		sb.append("				<label>");
		sb.append("				<select id='itensPorPagina' name='itensPorPagina' onchange='atualizarLista();' class='span1 lFloat marginless' >");
		for (ItensPorPagina valor : ItensPorPagina.values()) {
			sb.append("	<option>" + valor.getValor() + "</option>");
		}
		sb.append("				</select>");
		sb.append(UtilI18N.internacionaliza(request, "citcorpore.comum.verporpagina"));
		sb.append("				</label>");
		sb.append("			</div>");
		sb.append("			<div class='span4 right paginacaoGerenciamentoQuantidade'>");
		carregarValoresPaginacao(totalPaginas, sb, paginaSelecionada, request);
		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("</div>");
	}

	public void carregarValoresPaginacao(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request) {
		sb.append(paginaSelecionada + " " + UtilI18N.internacionaliza(request, "citcorpore.comum.de") + " " + totalPaginas + " " + UtilI18N.internacionaliza(request, "citcorpore.comum.resultados"));
	}

	public void carregarRodapeGerenciamento(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request) throws Exception {
		sb.append("<div id='sub'>");
		sb.append("		<div class='row-fluid inicio'>");
		sb.append("			<div class='span12 right paginacaoGerenciamento'>");

		paginacaoGerenciamento(totalPaginas, sb, paginaSelecionada, request);

		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("		<div class='row-fluid'>");
		sb.append("			<div class='span12 right paginacaoGerenciamentoQuantidade'>");

		carregarValoresPaginacao(totalPaginas, sb, paginaSelecionada, request);

		sb.append("			</div>");
		sb.append("		</div>");
		sb.append("</div>");
		/* Inicio Fechamento da div maior */
		sb.append("		</div>");
		sb.append("</div>");
		/* Fim Fechamento da div maior */
	}

	/**
	 * Realiza a regra de paginação das solicitações Explicação: Se o número de páginas for maior do que cinco, já é possível criar os intervalos. Se a página atual for menor do que cinco (o adjacente
	 * esta configurado com 2) é feito um laço. No for, enquanto a variável 'i' for menor do que seis os números são mostrados fazendo uma verificação para saber qual é a página atual que exige uma
	 * estilização diferente. Mas se a página atual for maior do que quatro e menor do que a última menos três, é uma página intermediária. Primeiro são anexadas a primeira e última páginas. Depois é
	 * feito um laço para definir as adjacentes. A variável 'adjacentes' recebeu neste código o valor dois. Para enteder melhor este laço vamos supor que estamos na página seis. A variável 'i' vai
	 * receber quatro (atual - adjacentes), enquanto ela for menor do que oito (atual + adjacentes) os números links gerados com uma verificação para saber qual é a página atual. Por fim são anexadas
	 * a última e penúltima páginas. O último else é para quando a página atual esta perto do final da numeração. São anexadas a primeira e última páginas além dos três pontos. A variável 'i' recebe o
	 * resultado da última página menos oito (4+2*2) enquanto não for menor ou igual a este número, os links são gerados.
	 * 
	 * @param totalPaginas
	 * @param sb
	 * @param paginaSelecionada
	 * @param request
	 */
	public void paginacaoGerenciamento(Integer totalPaginas, StringBuilder sb, Integer paginaSelecionada, HttpServletRequest request) throws Exception {

		final Integer adjacentes = 2;
		if (paginaSelecionada == null)
			paginaSelecionada = 1;
		sb.append("	<div id='itenPaginacaoGerenciamento' class='pagination pagination-right margin-none'>");
		sb.append("		<ul>");
		sb.append("			<li " + (paginaSelecionada == 1 ? "class='disabled'" : "value='1' onclick='paginarItens(this.value);'") + " ><a href='#'>"
				+ UtilI18N.internacionaliza(request, "citcorpore.comum.primeiro") + "</a></li>");
		sb.append("			<li " + ((totalPaginas == 1 || paginaSelecionada == 1) ? "class='disabled'" : "value='" + (paginaSelecionada - 1) + "' onclick='paginarItens(this.value);'")
				+ "><a href='#'>&laquo;</a></li>");
		if (totalPaginas <= 5) {
			for (int i = 1; i <= totalPaginas; i++) {
				if (i == paginaSelecionada) {
					sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);' class='active'><a href='#'>" + i + "</a></li> ");
				} else {
					sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);'><a href='#'>" + i + "</a></li> ");
				}
			}
		} else {
			if (totalPaginas > 5) {
				if (paginaSelecionada < 1 + (2 * adjacentes)) {
					for (int i = 1; i < 2 + (2 * adjacentes); i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);' class='active'><a href='#'>" + i + "</a></li> ");
						} else {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);'><a href='#'>" + i + "</a></li> ");
						}
					}
				} else if (paginaSelecionada > (2 * adjacentes) && paginaSelecionada < totalPaginas - 3) {
					for (int i = paginaSelecionada - adjacentes; i <= paginaSelecionada + adjacentes; i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);' class='active'><a href='#'>" + i + "</a></li> ");
						} else {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);'><a href='#'>" + i + "</a></li> ");
						}
					}
				} else {
					for (int i = totalPaginas - (/* 4 + */(2 * adjacentes)); i <= totalPaginas; i++) {
						if (i == paginaSelecionada) {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);' class='active'><a href='#'>" + i + "</a></li> ");
						} else {
							sb.append("<li id='" + i + "' value='" + i + "' onclick='paginarItens(this.value);'><a href='#'>" + i + "</a></li> ");
						}
					}
				}
			}
		}
		sb.append("			<li " + ((totalPaginas == 1 || paginaSelecionada.equals(totalPaginas)) ? "class='disabled'" : "value='" + (paginaSelecionada + 1) + "' onclick='paginarItens(this.value);'")
				+ " ><a href='#'>&raquo;</a></li>");
		sb.append("			<li " + (paginaSelecionada.equals(totalPaginas) ? "class='disabled'" : "value='" + totalPaginas + "' onclick='paginarItens(this.value);'") + " ><a href='#'>"
				+ UtilI18N.internacionaliza(request, "citcorpore.comum.ultimo") + "</a></li> ");
		sb.append("		</ul>");
		sb.append("	</div>");
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	@Override
	public Class getBeanClass() {
		return GerenciamentoServicosDTO.class;
	}

	/**
	 * Cria os scipts que serão usados na tela de gerenciamento de serviços
	 * 
	 * @param sb
	 */
	public void criarScriptPaginacao(StringBuilder sb) {
		sb.append("<script type='text/javascript'>");
		sb.append("	function paginarItens(paginaSelecionada) {");
		sb.append("		janelaAguarde();");
		sb.append("		document.formGerenciamento.paginaSelecionada.value = paginaSelecionada;");
		sb.append("		document.formGerenciamento.fireEvent('paginarItens');");
		sb.append("	}");
		sb.append("	function atualizarLista() {");
		sb.append("		janelaAguarde();");
		sb.append("		$('#paginaSelecionada').val('1');");
		sb.append("		document.formGerenciamento.fireEvent('atualizarLista');");
		sb.append("	}");
		sb.append("	function atualizaPagina() {");
		sb.append("		if ( document.formGerenciamento.chkAtualiza.checked ) {");
		sb.append("		JANELA_AGUARDE_MENU.show();");
		sb.append("		atualizarLista();");
		sb.append("		}");
		sb.append("	}");
		sb.append("	window.setInterval(atualizaPagina, 30000);");
		sb.append("	function janelaAguarde() {");
		sb.append("		JANELA_AGUARDE_MENU.show();");
		sb.append("	}");
		sb.append("	function fechaJanelaAguarde() {");
		sb.append("		JANELA_AGUARDE_MENU.hide();");
		sb.append("	}");
		sb.append("function carregarValorClasse(valor, classe) {");
		sb.append("		divs = document.getElementsByClassName(classe);");
		sb.append("		[].slice.call( divs ).forEach(function ( div ) {");
		sb.append("		div.innerHTML = valor;");
		sb.append("	});");
		sb.append("}");
		sb.append("function visualizarSolicitacao(idSolicitacaoServico) {");
		sb.append("	document.getElementById('frameNovaSolicitacao').src = '../../pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?idSolicitacaoServico='+idSolicitacaoServico+'&escalar=N&alterarSituacao=N&editar=N';");
		sb.append("	$('#modal_novaSolicitacao').modal('show');");
		sb.append("}");
		sb.append("function fecharJanelaAguarde() {");
		sb.append("	JANELA_AGUARDE_MENU.hide();");
		sb.append("}");
		sb.append("function executaModalPesquisa(idSolicitacao,hash)	    {");
		sb.append("	document.getElementById('frameNovaSolicitacao').src = '../../pages/pesquisaSatisfacao/pesquisaSatisfacao.load?idSolicitacaoServico='+idSolicitacao+'&hash='+hash+'&frame=sim';");
		sb.append("	$('#modal_novaSolicitacao').modal();");
		sb.append("}");
		sb.append("function executaModalOpiniao(idSolicitacao){");
		sb.append("	document.getElementById('frameNovaSolicitacao').src = '../../pages/opiniao/opiniao.load?idSolicitacao='+idSolicitacao;");
		sb.append("	$('#modal_novaSolicitacao').modal();");
		sb.append("}");
		sb.append("function modalNovaSolicitacaoServico(){");
		// sb.append("	janelaAguarde();");
		sb.append("	document.getElementById('frameNovaSolicitacao').src = '../../pages/solicitacaoServicoMultiContratosPortal2/solicitacaoServicoMultiContratosPortal2.load?iframe=true';");
		sb.append("	$('#modal_novaSolicitacao').modal();");
		sb.append("}");
		sb.append("</script>");
	}

	/**
	 * Recarrega a lista de solicitações de serviços
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void recarregarLista(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.getGerenciamentoServicosDTO().setIdSolicitante(WebUtil.getUsuario(request).getIdEmpregado());
		StringBuilder sb = new StringBuilder();
		GerenciamentoServicosDTO gerenciamentoServicosDTO = (GerenciamentoServicosDTO) document.getBean();
		Integer itensPorPagina = (gerenciamentoServicosDTO.getItensPorPagina() == null ? Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "5"))
				: gerenciamentoServicosDTO.getItensPorPagina());

		Integer paginaSelecionada = gerenciamentoServicosDTO.getPaginaSelecionada();
		if (paginaSelecionada == null)
			paginaSelecionada = 1;
		Integer tipoLista = (gerenciamentoServicosDTO.getTipoLista() == null ? 1 : gerenciamentoServicosDTO.getTipoLista());
		renderizarLista(sb, request, itensPorPagina, paginaSelecionada, false, tipoLista);
		Integer totalPaginasFinal = totalPaginas(request, itensPorPagina);
		paginaSelecionada = (totalPaginasFinal == 1 ? 1 : paginaSelecionada);
		HTMLElement divPrincipal = document.getElementById("esquerda");
		divPrincipal.setInnerHTML(sb.toString());
		document.executeScript("fechaJanelaAguarde()");
		document.executeScript("inicializaPopover()");
		carregarItensPaginacao(document, request, totalPaginasFinal);

		HTMLForm form = document.getForm("formGerenciamento");
		form.setValues(gerenciamentoServicosDTO);
	}

	/**
	 * Realiza a paginação dos itens e recarrega a lista de solicitações
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void paginarItens(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setGerenciamentoServicosDTO((GerenciamentoServicosDTO) document.getBean());
		this.recarregarLista(document, request, response);
	}

	/**
	 * Atualiza e recarrega a lista de solicitações de serviços
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void atualizarLista(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setGerenciamentoServicosDTO((GerenciamentoServicosDTO) document.getBean());
		this.recarregarLista(document, request, response);
	}

	/**
	 * Carrega e atualiza os itens da paginação numerada e informa a quantidade de resultados Ex.: Primeiro « 1 2 3 4 5 » Último 1 De 7 Resultados
	 * 
	 * @param document
	 * @param request
	 * @param totalPaginasFinal
	 */
	public void carregarItensPaginacao(DocumentHTML document, HttpServletRequest request, Integer totalPaginasFinal) throws Exception {
		StringBuilder sb = new StringBuilder();
		GerenciamentoServicosDTO gerenciamentoServicosDTO = (GerenciamentoServicosDTO) document.getBean();
		Integer paginaSelecionada = gerenciamentoServicosDTO.getPaginaSelecionada();
		if (paginaSelecionada == null)
			paginaSelecionada = 1;
		paginacaoGerenciamento(totalPaginasFinal, sb, paginaSelecionada, request);
		document.executeScript("carregarValorClasse(\"" + sb.toString() + "\", \"paginacaoGerenciamento\")");

		StringBuilder valores = new StringBuilder();
		carregarValoresPaginacao(totalPaginasFinal, valores, paginaSelecionada, request);
		document.executeScript("carregarValorClasse(\"" + valores.toString() + "\",\"paginacaoGerenciamentoQuantidade\")");
	}

	/**
	 * Retorna o total de páginas de acordo com o parametro da quantidade de itens a serem listados
	 * 
	 * @param request
	 * @param itensPorPagina
	 */
	public Integer totalPaginas(HttpServletRequest request, Integer itensPorPagina) throws Exception {
		Integer totalPaginas = 0;
		/* Desenvolvedor: Thiago Matias - Data: 06/11/2013 - Horário: 17:00 - ID Citsmart: 122025 - 
		* Motivo/Comentário: era necessário enviar o usuario(usuario.getLogin()) e a lista de contrato(listContratoUsuarioLogado) como parâmetro no metodo*/
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario != null){
			ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
			Collection<ContratoDTO> listContratoUsuarioLogado = contratoService.findAtivosByIdEmpregado(usuario.getIdEmpregado());
			
			ExecucaoSolicitacaoService execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
			totalPaginas = execucaoSolicitacaoService.obterTotalDePaginas(itensPorPagina, usuario.getLogin(), gerenciamentoServicosDTO, listContratoUsuarioLogado);
		}
		return totalPaginas;
	}

	public GerenciamentoServicosDTO getGerenciamentoServicosDTO() {
		return gerenciamentoServicosDTO;
	}

	public void setGerenciamentoServicosDTO(GerenciamentoServicosDTO gerenciamentoServicosDTO) {
		this.gerenciamentoServicosDTO = gerenciamentoServicosDTO;
	}

}
