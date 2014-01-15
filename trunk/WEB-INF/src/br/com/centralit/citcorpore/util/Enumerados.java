
package br.com.centralit.citcorpore.util;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class Enumerados implements Serializable {

	private static final long serialVersionUID = -523719211080468112L;


	public enum TipoAgendamento { 
		D("Diariamente"), S("Semanalmente"), M("Mensalmente"), U("Uma vez");
		private String descricao;
		TipoAgendamento(String descricao) {
			this.descricao = descricao;
		}
		public String getDescricao() {
			return descricao;
		}
	}
	
	public enum OrigemBaseConhecimento{
		  
		CONHECIMENTO(1, "Conhecimento"), EVENTO(2, "Evento"),  MUDANCA(3, "Mudança"),
		INCIDENTE(4, "Incidente"), SERVICO(5, "Serviço"), PROBLEMA(6, "Problema");
		
		private Integer origem;
		private String descOrigem;
		
		public Integer getOrigem(){
			return origem;
		}
		public String getDescOrigem(){
			return descOrigem;
		}
		public static String getDescOrigemByOrigem(Integer origem){
			String descStrOrigem = "";
			if(origem.intValue() == 1){
				descStrOrigem = OrigemBaseConhecimento.CONHECIMENTO.getDescOrigem();
			}
			if(origem.intValue() == 2){
				descStrOrigem = OrigemBaseConhecimento.EVENTO.getDescOrigem();
			}
			if(origem.intValue() == 3){
				descStrOrigem = OrigemBaseConhecimento.MUDANCA.getDescOrigem();
			}
			if(origem.intValue() == 4){
				descStrOrigem = OrigemBaseConhecimento.INCIDENTE.getDescOrigem();
			}
			if(origem.intValue() == 5){
				descStrOrigem = OrigemBaseConhecimento.SERVICO.getDescOrigem();
			}
			if(origem.intValue() == 6){
				descStrOrigem = OrigemBaseConhecimento.PROBLEMA.getDescOrigem();
			}
			return descStrOrigem;
		}
		private OrigemBaseConhecimento(Integer origem, String descOrigem) {
			this.origem = origem;
			this.descOrigem = descOrigem;
		}
		
		
	}
	
	public enum SituacaoSolicitacaoServico {
		EmAndamento("Em andamento"), Suspensa("Suspensa"), Cancelada("Cancelada"), Resolvida("Resolvida"), Reaberta("Reaberta"), Fechada("Fechada"), ReClassificada("Reclassificada");

		private String descricao;

		SituacaoSolicitacaoServico(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	 static final String DATA = "Date";
	 static final String HORA = "Hora";
	 static final String CNPJ = "CNPJ";
	 static final String CPF =  "CPF";
	 static final String NUMERO = "Numero";
	 static final String MOEDA = "MOEDA";
	 static final String TEXTO = "Texto";
	 static final String EMAIL = "Email";
	 static final String CEP = "CEP";
	 static final String TELEFONE = "Telefone";
	 static final String SENHA = "Senha";
	 
	 
	
	public enum ParametroSistema {
		
		ORIGEM_SISTEMA(0, "parametro.0", TEXTO), 
		CaminhoArquivoNetMap(1, "parametro.1", TEXTO), 
		FaixaIp(2, "parametro.2", TEXTO),
		NoPesquisa(3, "parametro.3", TEXTO), 
		Atributo(4, "parametro.4", TEXTO), 
		CaminhoBaseItemCfg(5, "parametro.5", TEXTO), 
		DiretorioXmlAgente(6, "parametro.6", TEXTO), 
		DiasInventario(7, "parametro.7", NUMERO), 
		CaminhoNmap(8, "parametro.8", TEXTO), 
		ID_GRUPO_PADRAO_NIVEL1(9, "parametro.9", NUMERO), 
		RemetenteNotificacoesSolicitacao(10,"parametro.10", TEXTO), 
		EmailAutenticacao(11, "parametro.11", TEXTO), 
		EmailUsuario(12, "parametro.12", TEXTO), 
		EmailSenha(13,"parametro.13", SENHA), 
		EmailSMTP(14, "parametro.14", TEXTO), 
		GedInterno(15, "parametro.15", TEXTO), 
		GedInternoBD(16, "parametro.16", TEXTO), 
		GedExternoClasse(17,"parametro.17", TEXTO), 
		GedDiretorio(18, "parametro.18", TEXTO),
		EMPRESA_Nome(19, "parametro.19", TEXTO), 
		METODO_AUTENTICACAO_Pasta(22, "parametro.22", TEXTO), 
		SMTP_LEITURA_Servidor(23,"parametro.23", TEXTO), 
		SMTP_LEITURA_Caixa(24, "parametro.24", TEXTO),
		SMTP_LEITURA_Senha(25,"parametro.25", SENHA), 
		SMTP_LEITURA_Provider(26, "parametro.26", TEXTO), 
		SMTP_LEITURA_Porta(27, "parametro.27", TEXTO), 
		SMTP_LEITURA_Pasta(28, "parametro.28", TEXTO), 
		NomeFluxoPadraoServicos(29,"parametro.29", TEXTO), 
		IDFaseExecucaoServicos(30, "parametro.30", NUMERO), 
		EnviaEmailFluxo(31, "parametro.31", TEXTO), 
		DB_SCHEMA(32, "parametro.32", TEXTO), 
		URL_Sistema(33, "parametro.33", TEXTO), 
		LDAP_URL(34, "parametro.34", TEXTO), 
		DOMINIO_AD(35,"parametro.35", TEXTO), 
		DOMINIO_EMAIL(36, "parametro.36", TEXTO), 
		LOGIN_AD(37,"parametro.37", TEXTO), 
		SENHA_AD(38,"parametro.38", SENHA), 
		ID_PERFIL_ACESSO_DEFAULT(39,"parametro.39", TEXTO), 
		CONTROLE_ACC_UNIDADE_INC_SOLIC(40,"parametro.40", TEXTO), 
		COLABORADORES_VINC_CONTRATOS(41, "parametro.41", TEXTO), 
		PAGE_CADADTRO_SOLICITACAOSERVICO(42, "parametro.42", TEXTO), 
		LDAD_SUFIXO_DOMINIO(43, "parametro.43", TEXTO), 
		DISKFILEUPLOAD_REPOSITORYPATH(44,"parametro.44", TEXTO), 
		ID_GRUPO_PADRAO_LDAP(45, "parametro.45", TEXTO), 		
		LOGIN_PORTAL(46, "parametro.46", TEXTO), // valores S ou N
		FLUXO_PADRAO_MUDANCAS(47, "parametro.47", TEXTO), 
		VALIDAR_BOTOES(48, "parametro.48", TEXTO), // valores S ou N
		LDAP_SN_LAST_NAME(49, "parametro.49", TEXTO), 
		OS_VALOR_ZERO(50, "parametro.50", TEXTO), 
		FORMULA_CALCULO_GLOSA_OS(51,"parametro.51", NUMERO), 
		USE_LOG(52, "parametro.52", TEXTO), 
		TIPO_LOG(53, "parametro.53", TEXTO), 
		PATH_LOG(54,"parametro.54", TEXTO), 
		FILE_LOG(55, "parametro.55", TEXTO), 
		EXT_LOG(56, "parametro.56", TEXTO), 
		ID_MODELO_EMAIL_GRUPO_DESTINO(57,"parametro.57", NUMERO),
		NOTIFICAR_GRUPO_RECEPCAO_SOLICITACAO(58,"parametro.58", TEXTO), 
		PATRIMONIO_IDTIPOITEMCONFIGURACAO(59, "parametro.59", NUMERO),
		NOME_GRUPO_ITEM_CONFIG_NOVOS(60, "parametro.60", TEXTO),
		UNIDADE_VINC_CONTRATOS(61, "parametro.61", TEXTO),
		SERVICO_PADRAO_SOLICITACAO(62, "parametro.62", NUMERO),
		PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL(63, "parametro.63", TEXTO),
		LDAP_ATRIBUTO(64, "parametro.64", TEXTO),
		ORIGEM_PADRAO_SOLICITACAO(65, "parametro.65", NUMERO),
		IDIOMAPADRAO(66, "parametro.66", TEXTO),
		LDAP_FILTRO(67, "parametro.67", TEXTO),
		LDAP_MOSTRA_BOTAO(68, "parametro.68", TEXTO),
		CAMPOS_OBRIGATORIO_SOLICITACAOSERVICO(69,"parametro.69", TEXTO),
		MOSTRAR_BOTOES_IMPORTACAO_XML_CADASTRO_MENU(70, "parametro.70", TEXTO),
		LER_ARQUIVO_PADRAO_XML_MENUS(71, "parametro.71", TEXTO),
		SMTP_LEITURA_LIMITE_(72, "parametro.72", TEXTO),
		AVISAR_DATAEXPIRACAO_LICENCA(73, "parametro.73", NUMERO),
		ENVIAR_EMAIL_DATAEXPIRACAO(74, "parametro.74", NUMERO),
		ID_MODELO_EMAIL_EXPIRACAO_LICENCA(75, "parametro.75", NUMERO),
		DOMINIO_REDE(76, "parametro.76", TEXTO),
		AVISAR_DATAEXPIRACAO_BASECONHECIMENTO(78, "parametro.78", NUMERO),
		ID_MODELO_EMAIL_AVISAR_CRIACAO_PASTA(79, "parametro.79", NUMERO),
		ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_PASTA(80, "parametro.80", NUMERO),
		ID_MODELO_EMAIL_AVISAR_EXCLUSAO_PASTA(81, "parametro.81", NUMERO),
		ID_MODELO_EMAIL_AVISAR_CRIACAO_CONHECIMENTO(82, "parametro.82", NUMERO),
		ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_CONHECIMENTO(83, "parametro.83", NUMERO),
		ID_MODELO_EMAIL_AVISAR_EXCLUSAO_CONHECIMENTO(84, "parametro.84", NUMERO),
		ITEM_CONFIGURACAO_MUDANCA(85, "parametro.85", TEXTO),
		MOSTRAR_CATEGORIA_SERVICO_EM_INCIDENTE(86, "parametro.86", TEXTO),
		ID_MODELO_EMAIL_AVISAR_CRIACAO_IC(87, "parametro.87", NUMERO),
		ID_MODELO_EMAIL_AVISAR_ALTERACAO_IC(88, "parametro.88", NUMERO),
		ID_MODELO_EMAIL_AVISAR_ALTERACAO_IC_GRUPO(89, "parametro.89", NUMERO),
		ENVIO_PADRAO_EMAIL_IC(90, "parametro.90", NUMERO),
		SMTP_GMAIL(91,"parametro.91", TEXTO),
		CICLO_DE_VIDA_IC_DESENVOLVIMENTO(92, "parametro.92", TEXTO),
		CICLO_DE_VIDA_IC_PRODUCAO(93, "parametro.93", TEXTO),
		CICLO_DE_VIDA_IC_HOMOLOGACAO(94, "parametro.94", TEXTO),
		NOME_INVENTARIO(95,"parametro.95", TEXTO),
		ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO(96, "parametro.96", NUMERO),
		ID_GRUPO_PADRAO_IC_PRODUCAO(97, "parametro.97", NUMERO),
		ID_GRUPO_PADRAO_IC_HOMOLOGACA0(98, "parametro.98", NUMERO),
		ID_GRUPO_PADRAO_IC_INVENTARIO(99, "parametro.99", NUMERO),
		ID_GRUPO_PADRAO_REQ_PRODUTOS(100, "parametro.100", NUMERO),
		PERC_MAX_VAR_PRECO_COTACAO(101, "parametro.101", TEXTO),
		NUMERO_COLABORADORES_CONSULTA_AD(102, "parametro.102", NUMERO),
		ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO(103, "parametro.103", NUMERO),
		CALCULAR_PRIORIDADE_SOLICITACAO_DINAMICAMENTE(104, "parametro.104", TEXTO),
		ORIGEM_PADRAO(105, "parametro.105", NUMERO),
        DETERMINA_URGENCIA_IMPACTO_REQPROD(106, "parametro.106", TEXTO),
        COTACAO_PESO_PRECO(107, "parametro.107", NUMERO),
        COTACAO_PESO_PRAZO_ENTREGA(108, "parametro.108", NUMERO),
        COTACAO_PESO_PRAZO_PAGTO(109, "parametro.109", NUMERO),
        COTACAO_PESO_GARANTIA(110, "parametro.110", NUMERO),
        COTACAO_PESO_JUROS(111, "parametro.111", NUMERO),
		PATH_NAGIOS_STATUS(112, "parametro.112", TEXTO),
		ID_MODELO_EMAIL_ESCALACAO_AUTOMATICA(113, "parametro.113", NUMERO),
		PEMITE_REQUISICAO_EMPREGADO_COMPRAS(114,"parametro.114", TEXTO),
		TIRAR_VINCULO_LOCALIDADE_UNIDADE(115, "parametro.115", TEXTO),
		ID_MODELO_EMAIL_ALTERACAO_SENHA(116, "parametro.116", SENHA),
		INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS(117, "parametro.117", TEXTO),
		CONTRATO_PADRAO(118, "parametro.118", NUMERO),
		TIPO_CAPTURA_SOLICITACOES(119, "parametro.119", NUMERO),
		ID_MODELO_EMAIL_GRUPO_COMITE_REQUISICAOMUDANCA(120, "parametro.120", NUMERO),
		LDAP_OPEN_LDAP(121, "parametro.121", TEXTO),
		ID_MODELO_EMAIL_GRUPO_DESTINO_REQUISICAOMUDANCA(122, "parametro.122", NUMERO),
		NomeFluxoPadraoProblema(123, "parametro.123", TEXTO),
		ID_MODELO_EMAIL_CRIACAO_PROBLEMA(124, "parametro.124", NUMERO),
		ID_MODELO_EMAIL_ANDAMENTO_PROBLEMA(125, "parametro.125", NUMERO),
		ID_MODELO_EMAIL_FINALIZADO_PROBLEMA(126, "parametro.126", NUMERO),
		ID_MODELO_EMAIL_GRUPO_DESTINO_PROBLEMA(127, "parametro.127", NUMERO),		
		ID_MODELO_EMAIL_PRAZO_SOLUCAO_CONTORNO_PROBLEMA_EXPIRADO(128, "parametro.128", NUMERO),
		NOTIFICAR_RESPONSAVEL_GRUPO_PRAZO_SOLUCAO_CONTORNO_PROBLEMA_EXPIRADO(129,"parametro.129", TEXTO),		
		LIBERAR_ORDEM_SERVICO_DATA_ANTERIOR(130, "parametro.130", TEXTO),
		QUANT_RETORNO_PESQUISA(131, "parametro.131", NUMERO),
		QUANT_RETORNO_PESQUISA_ORDEM_SERVICO(132, "parametro.132", TEXTO),//Não esta sendo usado
		ID_MODELO_EMAIL_AVISAR_REUNIAO_MARCADA(133, "parametro.133", NUMERO), 
		ID_GRUPO_PADRAO_REQ_RH(134, "parametro.134", NUMERO),
		ID_PERFIL_ACESSO_ADMINISTRADOR(135, "parametro.135", NUMERO),
		URL_LOGO_PADRAO_RELATORIO(136, "parametro.136", TEXTO),
		ID_MODELO_EMAIL_AVISAR_PESQUISA_SATISFACAO_RUIM_OU_REGULAR(137, "parametro.137", NUMERO),
		ID_GRUPO_PADRAO_AVISAR_PESQUISA_SATISFACAO_RUIM_OU_REGULAR(138, "parametro.138", NUMERO),
		QTDE_DIAS_RESP_PESQ_SASTISFACAO(139,"parametro.139", NUMERO),
		ID_GRUPO_PADRAO_IC_DESENVOLVIMENTO_PADRAO(140, "parametro.140", NUMERO),
		ID_GRUPO_PADRAO_IC_HOMOLOGACAO_PADRAO(141, "parametro.141", NUMERO),
		ID_GRUPO_PADRAO_IC_PRODUCAO_PADRAO(142, "parametro.142", NUMERO),
		NOME_GRUPO_PADRAO_DESENVOLVIMENTO(143, "parametro.143", TEXTO),
		NOME_GRUPO_PADRAO_HOMOLOGACAO(144, "parametro.144", TEXTO),
		NOME_GRUPO_PADRAO_PRODUCAO(145, "parametro.145", TEXTO),
		TEMPLATE_QUESTIONARIO(146, "parametro.146", NUMERO),
		LUCENE_DIR_BASECONHECIMENTO(147, "parametro.147", TEXTO),
		LUCENE_DIR_PALAVRAGEMEA(148, "parametro.148", TEXTO),
		LUCENE_DIR_ANEXOBASECONHECIMENTO(149, "parametro.149", TEXTO),
		LUCENE_REFAZER_INDICES(150, "parametro.150", TEXTO),
		AVALIAÇÃO_AUTOMATICA(151, "parametro.151", TEXTO),
		NOTA_AVALIAÇÃO_AUTOMATICA(152, "parametro.152", TEXTO),
		PONTUACAO_PRODUTIVIDADE_BAIXA_DENTRO_DO_PRAZO(153, "parametro.153", NUMERO),
		PONTUACAO_PRODUTIVIDADE_BAIXA_RETRABALHO(154, "parametro.154", NUMERO),
		PONTUACAO_PRODUTIVIDADE_BAIXA_FORA_DO_PRAZO(155, "parametro.155", NUMERO),
		PONTUACAO_PRODUTIVIDADE_BAIXA_RETRABALHADO_PRAZO_ESTOURADO(156, "parametro.156", NUMERO),
		PONTUACAO_PRODUTIVIDADE_MEDIA_DENTRO_DO_PRAZO(157, "parametro.157", NUMERO),
		PONTUACAO_PRODUTIVIDADE_MEDIA_RETRABALHO(158, "parametro.158", NUMERO),
		PONTUACAO_PRODUTIVIDADE_MEDIA_FORA_DO_PRAZO(159, "parametro.159", NUMERO),
		PONTUACAO_PRODUTIVIDADE_MEDIA_RETRABALHADO_PRAZO_ESTOURADO(160, "parametro.160", NUMERO),
		PONTUACAO_PRODUTIVIDADE_ALTA_DENTRO_DO_PRAZO(161, "parametro.161", NUMERO),
		PONTUACAO_PRODUTIVIDADE_ALTA_RETRABALHO(162, "parametro.162", NUMERO),
		PONTUACAO_PRODUTIVIDADE_ALTA_FORA_DO_PRAZO(163, "parametro.163", NUMERO),
		PONTUACAO_PRODUTIVIDADE_ALTA_RETRABALHADO_PRAZO_ESTOURADO(164, "parametro.164", NUMERO),
		NIVEL_EXCELENCIA_EXIGIDO(165, "parametro.165", NUMERO),		
		ID_GRUPO_PADRAO_TESTE(166, "parametro.166", NUMERO),		
		ID_GRUPO_PADRAO_EXECUTOR(167, "parametro.167", NUMERO),
		FILTRO_FLUXO_NOME(168, "parametro.168", TEXTO),
		FILTRO_FLUXO_ENCERRAMENTO(169, "parametro.169", TEXTO),
		ID_GRUPO_PADRAO_REQ_VIAGEM_EXECUCAO(170, "parametro.170", NUMERO),
		DIAS_LIMITE_REABERTURA_INCIDENTE_REQUISICAO(171,"parametro.171", NUMERO),
		MOSTRAR_GERENCIA_RECURSOS_HUMANOS(172,"parametro.172", TEXTO),
		MOSTRAR_COMPRAS(173,"parametro.173", TEXTO),
		ID_GRUPO_PADRAO_RESPONSAVEL_COTACAO_VIAGEM(174,"parametro.174", NUMERO),
		ID_GRUPO_PADRAO_RESPONSAVEL_ADIANTAMENTO_VIAGEM(175,"parametro.175", NUMERO),
		ID_GRUPO_PADRAO_RESPONSAVEL_CONFERENCIA_VIAGEM(176,"parametro.176", NUMERO),
		SERVASTERISKATIVAR(177,"parametro.177", TEXTO),
		SERVASTERISKIP(178,"parametro.178", TEXTO),
		SERVASTERISKLOGIN(179,"parametro.179", TEXTO),
		SERVASTERISKSENHA(180,"parametro.180", SENHA),
		SERVASTERISKINTERVALO(181,"parametro.181", NUMERO),
		PASTA_SALVA_DESCRICAO_RESPOSTA_DE_SOLICITACAOSERVICO_EM_BASECONHECIMENTO(182,"parametro.182", TEXTO),
		HABILITA_MONITORAMENTO_NAGIOS(183,"parametro.183", TEXTO),
		IP_SERVIDOR_INVENTARIO(184,"parametro.184", TEXTO),
		INVENTARIO_PROCESSAMENTO_ATIVO(185,"parametro.185", TEXTO),
		INVENTARIO_SNMP_COMMUNITY(186,"parametro.186", TEXTO),
		FAIXA_DISCOVERY_IP(187,"parametro.187", TEXTO),
		ID_MODELO_EMAIL_SOFTWARE_LISTA_NEGRA(188,"parametro.188", NUMERO),
		ID_GRUPO_PADRAO_RESPONSAVEL_SOFTWARE_LISTA_NEGRA(189, "parametro.189", NUMERO),
		HABILITA_REGRA_ESCALONAMENTO(190, "parametro.190", TEXTO),
		ATIVA_NOVO_LAYOUT(191,"parametro.191",  TEXTO),
		MOSTRAR_GRAVAR_BASE_CONHECIMENTO(192, "parametro.192", TEXTO),
		HABILITA_ESCALONAMENTO_MUDANÇA(193, "parametro.193", TEXTO),
		HABILITA_ESCALONAMENTO_PROBLEMA(194, "parametro.194", TEXTO),
		ID_MODELO_EMAIL_PRAZO_VENCENDO(195, "parametro.195", NUMERO),
		ID_MODELO_EMAIL_PRESTACAO_CONTAS_NAO_APROVADA(196, "parametro.196", NUMERO),
		LOGIN_USUARIO_ENVIO_EMAIL_AUTOMATICO(197, "parametro.197", TEXTO),
		PATH_ARQ_BANCO_LOG(198, "parametro.198", TEXTO),
		EmailStartTLS(199,"parametro.199", TEXTO),
		HABILITA_ROTINA_DE_LEITURA_EMAIL(200,"parametro.200", TEXTO),
		HABILITA_BOTAO_ORDEMSERVICO(201,"parametro.201", TEXTO),
		DISCOVERY_QTDE_THREADS(202,"parametro.202", NUMERO);

		private final int id;
		
		private final String campo;
		
		private final String tipoCampo;
		

		

		
		public int id() {
			return this.id;
		}
		
		public String campo() {
			return this.campo;
		}
		
		public String getCampoParametroInternacionalizado(HttpServletRequest request){
			return UtilI18N.internacionaliza(request, this.campo);
		}
		
		public String tipoCampo() {
			return this.tipoCampo;
		}
		

		
		private ParametroSistema(int id, String campo, String tipoCampo) {
			this.id = id;
			this.campo = campo;
			this.tipoCampo = tipoCampo;

		}
	}

	/**
	 * ENUM Tipo Demanda Serviço.
	 * 
	 * @author valdoilo.damasceno
	 * 
	 */
	public enum TipoDemandaServico {
		REQUISICAO("R", "Requisição"), INCIDENTE("I", "Incidente"), OS("O", "Ordem de Serviço");

		private String classificacao;

		private String campo;

		TipoDemandaServico(String classificacao, String campo) {
			this.classificacao = classificacao;
			this.campo = campo;
		}

		/**
		 * @return valor do atributo classificacao.
		 */
		public String getClassificacao() {
			return classificacao;
		}

		/**
		 * @return valor do atributo campo.
		 */
		public String getCampo() {
			return campo;
		}

	}

	/**
	 * @author breno.guimaraes
	 * 
	 */
	public enum OrigemOcorrencia {
		EMAIL("Email", 'E'), FONE_FAX("Fone/Fax", 'F'), VOICE_MAIL("Voice Mail", 'V'), PESSOALMENTE("Pessoalmente", 'P'), OUTROS("Outros", 'O');

		private String descricao;
		private Character sigla;

		private OrigemOcorrencia(String descricao, Character sigla) {
			this.descricao = descricao;
			this.sigla = sigla;
		}

		public String getDescricao() {
			return descricao;
		}

		public Character getSigla() {
			return sigla;
		}
	}

	/**
	 * @author breno.guimaraes
	 * 
	 */
	public enum CategoriaOcorrencia {
		Criacao("Registro da Solicitação"), 
		Acompanhamento("Acompanhamento com o Cliente"), 
		Atualizacao("Atualização de Status"), 
		Diagnostico("Diagnóstico"), 
		Investigacao("Investigação"), 
		Memorando("Memorando"), 
		Informacao("Pedido de Informação"), 
		Retorno("Retorno do Cliente"), 
		Sintoma("Sintoma do Problema"), 
		Contorno("Solução de Contorno"), 
		Execucao("Registro de Execução"),
		MudancaSLA("Mudança de SLA"), 
		Reclassificacao("Reclassificação"), 
		Agendamento("Agendamento de Atividade"), 
		Suspensao("Suspensão da Solicitação"), 
		Reativacao("Reativação da Solicitação"), 
		Encerramento("Encerramento da Solicitação"), 
		Reabertura("Reabertura da Solicitação"),
		Direcionamento("Direcionamento da Solicitação"), 
		Compartilhamento("Compartilhamento de Tarefa"), 
		CancelamentoTarefa("Cancelamento de Tarefa"),
		InicioSLA("Inicio do SLA"), 
		SuspensaoSLA("Suspensão do SLA"),
		Aprovar("Liberação de Requisição Liberação"),
		ReativacaoSLA("Reativação do SLA");

		private String descricao;

		private CategoriaOcorrencia(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}

		public String getSigla() {
			return name();
		}
	}

	/**
	 * ENUM para listar as Notas da Pesquisa de Satisfação.
	 * 
	 * @author valdoilo
	 * 
	 */
	public enum Nota {
		OTIMO(4, "Ótimo","citcorpore.comum.otimo"), BOM(3, "Bom","citcorpore.comum.bom"), REGULAR(2, "Regular","citcorpore.comum.regular"), RUIM(1, "Ruim","citcorpore.comum.ruim");

		private Integer nota;

		private String descricao;
		
		private String chaveInternacionalizacao;

		private Nota(Integer nota, String descricao, String chaveInternacionalizacao) {
			this.nota = nota;
			this.descricao = descricao;
			this.chaveInternacionalizacao = chaveInternacionalizacao;
		}

		/**
		 * @return the nota
		 */
		public Integer getNota() {
			return nota;
		}

		/**
		 * @param nota
		 *            the nota to set
		 */
		public void setNota(Integer nota) {
			this.nota = nota;
		}

		/**
		 * @return the descricao
		 */
		public String getDescricao() {
			return descricao;
		}
		
		public void setChaveInternacionalizacao(String chaveInternacionalizacao){
			this.chaveInternacionalizacao = chaveInternacionalizacao;
		}
		public String getChaveInternacionalizacao(){
			return chaveInternacionalizacao;
		}
		/**
		 * @param descricao
		 *            the descricao to set
		 */
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

	}

	public enum FaseRequisicaoMudanca {
		Proposta("Proposta", SituacaoRequisicaoMudanca.Proposta),
		Registrada("Registrada", SituacaoRequisicaoMudanca.Registrada),
		Aprovacao("Aprovação", SituacaoRequisicaoMudanca.Aprovada),
		Planejamento("Planejamento", SituacaoRequisicaoMudanca.Planejada),
		Execucao("Execução", SituacaoRequisicaoMudanca.Executada),
		Avaliacao("Avaliação", SituacaoRequisicaoMudanca.Concluida);

		private String descricao;
		private SituacaoRequisicaoMudanca situacao;

		FaseRequisicaoMudanca(String descricao, SituacaoRequisicaoMudanca situacao) {
			this.descricao = descricao;
			this.situacao = situacao;
		}

		public SituacaoRequisicaoMudanca getSituacao() {
			return situacao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	

	public enum SituacaoRequisicaoMudanca {
		Registrada("Registrada"), 
		Proposta("Aguardando Aprovação"),
		Aprovada("Aprovada"), 
		Planejada("Planejada"), 
		EmExecucao("Em execução"), 
		Executada("Executada"), 
		Suspensa("Suspensa"), 
		Cancelada("Cancelada"), 
		Rejeitada("Rejeitada"), 
		Resolvida("Resolvida"), 
		Reaberta("Reaberta"), 
		Concluida("Concluída");

		private String descricao;

		SituacaoRequisicaoMudanca(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
	public enum FaseRequisicaoLiberacao {
		Liberada("Liberada", SituacaoRequisicaoLiberacao.Aprovada), 
		NaoResolvida("Não Resolvida", SituacaoRequisicaoLiberacao.NaoResolvida),
		Execucao("Execução", SituacaoRequisicaoLiberacao.Executada),
		Resolvida("Resolvida", SituacaoRequisicaoLiberacao.Resolvida),
		Finalizada("Fechada", SituacaoRequisicaoLiberacao.Fechada);

		private String descricao;
		private SituacaoRequisicaoLiberacao situacao;

		FaseRequisicaoLiberacao(String descricao, SituacaoRequisicaoLiberacao situacao) {
			this.descricao = descricao;
			this.situacao = situacao;
		}

		public SituacaoRequisicaoLiberacao getSituacao() {
			return situacao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
	public enum SituacaoRequisicaoLiberacao {
		Registrada("Registrada"),
		NaoResolvida("Não Resolvida"),
		Aprovada("Aprovada"), 
		Planejada("Planejada"), 
		EmExecucao("Execução"), 
		Executada("Executada"), 
		Suspensa("Suspensa"), 
		Cancelada("Cancelada"), 
		Rejeitada("Rejeitada"), 
		Resolvida("Resolvida"), 
		Reaberta("Reaberta"), 
		Fechada("Fechada"),
		Concluida("Concluída");

		private String descricao;

		SituacaoRequisicaoLiberacao(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
	public enum FaseRequisicaoProblema {
		Registrada("Registrada",SituacaoRequisicaoProblema.Registrada),Aprovacao("Aprovação", SituacaoRequisicaoProblema.Aprovada), Planejamento("Planejamento", SituacaoRequisicaoProblema.Planejada), Execucao("Execução", SituacaoRequisicaoProblema.Executada), 
		Avaliacao("Avaliação",SituacaoRequisicaoProblema.Concluida),EmInvestigacao("Em Investigação",SituacaoRequisicaoProblema.EmInvestigacao),SolucaoContorno("Solução de Contorno",SituacaoRequisicaoProblema.SolucaoContorno),
		Revisado("Revisado",SituacaoRequisicaoProblema.Revisado),Resolucao("Resolução",SituacaoRequisicaoProblema.Resolucao),Encerramento("Encerramento",SituacaoRequisicaoProblema.Encerramento),Revisar("Revisar",SituacaoRequisicaoProblema.Revisar),
		RegistroErroConhecido("Registro de Erro Conhecido",SituacaoRequisicaoProblema.RegistroErroConhecido);

		private String descricao;
		private SituacaoRequisicaoProblema situacao;

		FaseRequisicaoProblema(String descricao, SituacaoRequisicaoProblema situacao) {
			this.descricao = descricao;
			this.situacao = situacao;
		}

		public SituacaoRequisicaoProblema getSituacao() {
			return situacao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
	public enum SituacaoRequisicaoProblema {
		Registrada("Registrada"), Aprovada("Aprovada"), Planejada("Planejada"), EmExecucao("Em execução"), Executada("Executada"), Suspensa("Suspensa"), Cancelada("Cancelada"), Rejeitada("Rejeitada"), Resolvida(
				"Resolvida"), Reaberta("Reaberta"), Concluida("Concluída"),EmInvestigacao("Em Investigação"),SolucaoContorno("Solução de Contorno"),Revisado("Revisado"),Resolucao("Resolução"),Encerramento("Encerramento"),Revisar("Revisar"),
				RegistroErroConhecido("Registro de Erro Conhecido");

		private String descricao;

		SituacaoRequisicaoProblema(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}

	/**
	 * Enum armazena os Tipos de Carga do Sistema.
	 * 
	 * @author Vadoilo Damasceno
	 * 
	 */
	public enum TipoCargaSmart {
		Empregado("Colaboradores");

		private String descricao;

		TipoCargaSmart(String descricao) {

			this.descricao = descricao;

		}

		public String getDescricao() {
			return descricao;
		}

	}

	public enum Complexidade {

		BAIXA(1, "Baixa"), INTERMEDIARIA(2, "Intermediária"), MEDIANA(3, "Mediana"), ALTA(4, "Alta"), ESPECIALISTA(5, "Especialista");

		private int index;
		private String descricao;

		private Complexidade(int index, String descricao) {
			this.index = index;
			this.descricao = descricao;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

	}

	public enum PeriodoFormula {

		MENSAL(1, "Mensal"), SEMANAL(2, "Semanal"), DIARIO(3, "Diário"), DIAS_UTEIS(4, "Dias úteis");

		private int index;
		private String descricao;

		private PeriodoFormula(int index, String descricao) {
			this.index = index;
			this.descricao = descricao;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
    }
    
  public enum StatusIC {
    	
    	ATIVADO(1,"Ativado","baseItemConfiguracao.Ativado"), DESATIVADO(2,"Desativado","baseItemConfiguracao.Desativado"), EM_MANUTENCAO(3,"Em Manutenção","baseItemConfiguracao.Em_Manutenção"), 
    	IMPLANTACAO(4,"Implantação","baseItemConfiguracao.Implantação"), HOMOLOGACAO(5,"Homologação","baseItemConfiguracao.Homologação"), EM_DESENVOLVIMENTO(6,"Em Desenvolvimento","baseItemConfiguracao.Em_Desenvolvimento"), 
    	ARQUIVADO(7, "Arquivado","baseItemConfiguracao.Arquivado"),VALIDAR(8,"Validar Item","baseItemConfiguracao.Validar_Item");
    	
    	private Integer item;
    	private String descricao;
    	private String chaveInternacionalizacao;
    	
    	private StatusIC(Integer item, String descricao, String chaveInternacionalizacao){
    		this.item = item;
    		this.descricao = descricao;
    		this.chaveInternacionalizacao = chaveInternacionalizacao;
    	}
    	
    	public Integer getItem() {
			return item;
		}
		public void setItem(Integer item) {
			this.item = item;
		}    	
		public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		public String getChaveInternacionalizacao() {
			return chaveInternacionalizacao;
		}
		public void setChaveInternacionalizacao(String chaveInternacionalizacao) {
			this.chaveInternacionalizacao = chaveInternacionalizacao;
		}
		public static StatusIC getStatus(Integer id) {
		    if (id == null)
		        return null;
		    StatusIC result = null;
		    for (StatusIC status : StatusIC.values()) {
		        if (status.getItem().intValue() == id.intValue()) {
		            result = status;
		            break;
		        }
		    }
		    return result;
		}
    }
    
    public enum CriticidadeIC {
    	
    	CRITICA(1,"citcorpore.comum.critica"),ALTA(2,"citcorpore.comum.alta"),MEDIA(3,"citcorpore.comum.media"),BAIXA(4,"citcorpore.comum.baixa");
    	
    	private Integer item;
    	private String descricao;
    	
    	private CriticidadeIC(Integer item, String descricao) {
    		this.item = item;
    		this.descricao = descricao;
    				
    	}
    	public Integer getItem() {
    		return item;
    	}
    	public void setItem(Integer item) {
    		this.item = item;
    	}
    	public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
    }    
    
	/**
	 * Enum utilizado para listar as Permissões de Acesso ás Pasta de Base de Conhecimento.
	 * 
	 */
	public enum PermissaoAcessoPasta {

		SEMPERMISSAO("SEMPERMISSAO"), LEITURA("LEITURA"), LEITURAGRAVACAO("LEITURAGRAVACAO");

		private String permissao;

		private PermissaoAcessoPasta(String permissao) {
			this.permissao = permissao;
		}

		/**
		 * @return the permissao
		 */
		public String getPermissao() {
			return permissao;
		}

		/**
		 * @param permissao
		 *            the permissao to set
		 */
		public void setPermissao(String permissao) {
			this.permissao = permissao;
		}

	}
	
	/**
	 * Enum utilizado para listar as Permissões de Acesso ás Pasta de Base de Conhecimento.
	 * 
	 */
    public enum TipoNotificacao {

        ServTodasAlt("tipoNotificacao.ocorrerQualquerAlteracaoServico","T"),
        ServADICIONADOS("tipoNotificacao.novosServicosForemAdicionados","C"),
        ServALTERADOS("tipoNotificacao.servicosForemAlterados","A"), 
        ServEXCLUIDOS("tipoNotificacao.servicosForemExcluidos","E");

        private String descricao;
        private String tipoNotificacao;

        private TipoNotificacao(String descricao,String tipoNotificacao) {
              this.setDescricao(descricao);
              this.setTipoNotificacao(tipoNotificacao);
        }

        public String getTipoNotificacao() {
              return tipoNotificacao;
        }
        public void setTipoNotificacao(String tipoNotificacao) {
              this.tipoNotificacao = tipoNotificacao;
        }
        public String getDescricao() {
              return descricao;
        }
        public void setDescricao(String descricao) {
              this.descricao = descricao;
        }
  }


	/**
	 * Enum com os Graus de Importância do Conhecimento.
	 * 
	 * @author Vadoilo Damasceno
	 * 
	 */
	public enum EnumGrauImportanciaConhecimento {

		BAIXO("Baixo"), MEDIO("Médio"), ALTO("Alto");

		private String grauImportancia;

		private EnumGrauImportanciaConhecimento(String grauImportancia) {

			this.grauImportancia = grauImportancia;
		}

		/**
		 * @return the grauImportancia
		 */
		public String getGrauImportancia() {
			return grauImportancia;
		}

		/**
		 * @param grauImportancia
		 *            the grauImportancia to set
		 */
		public void setGrauImportancia(String grauImportancia) {
			this.grauImportancia = grauImportancia;
		}

	}    
    
    /*
     * Enumerador para situação de FAQ - Frequently Asked Questions
     * */
    public enum SituacaoFAQ {
		PUBLICADO("Publicado"), NAO_PUBLICADO("Não Publicado");
		
		private String descricao;
		
		SituacaoFAQ(String descricao) {
			this.descricao = descricao;
		}
		public String getDescricao() {
			return descricao;
		}
	}
    /*
     * Enumerador para situação de SIM e NAO
     * */
    public enum Situacao {
		SIM("Sim"), NAO("Não");
		
		private String descricao;
		
		Situacao(String descricao) {
			this.descricao = descricao;
		}
		public String getDescricao() {
			return descricao;
		}
	}
    
    public enum CategoriaTipoItemConfiguracao {
    	
    	HARDWARE(1,"Hardware"),SOFTWARE(2,"Software"),BIOS(3,"Bios");
    	
    	private Integer item;
    	private String descricao;
    	
    	private CategoriaTipoItemConfiguracao(Integer item, String descricao) {
    		this.item = item;
    		this.descricao = descricao;
    				
    	}
    	public Integer getItem() {
    		return item;
    	}
    	public void setItem(Integer item) {
    		this.item = item;
    	}
    	public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
    }    

    public enum SituacaoItemRequisicaoProduto {
        AguardandoValidacao("Aguardando validação",AcaoItemRequisicaoProduto.Criacao),
        RejeitadoCompras("Requisição rejeitada",AcaoItemRequisicaoProduto.Validacao),
        Inviabilizado("Inviabilizado",AcaoItemRequisicaoProduto.Validacao),
        AguardandoAutorizacaoCompra("Aguardando autorização de compra",AcaoItemRequisicaoProduto.Validacao),
        RejeitadoAutorizador("Requisição rejeitada",AcaoItemRequisicaoProduto.Autorizacao),
        CompraNaoAutorizada("Compra não autorizada",AcaoItemRequisicaoProduto.Autorizacao),
        AguardandoCotacao("Aguardando cotação",AcaoItemRequisicaoProduto.Autorizacao),
        AguardandoAprovacaoCotacao("Aguardando aprovação da cotação",AcaoItemRequisicaoProduto.Publicacao),
        CotacaoNaoAprovada("Cotação não aprovada",AcaoItemRequisicaoProduto.Aprovacao),
        AguardandoPedido("Aguardando pedido de compra",AcaoItemRequisicaoProduto.Aprovacao),
        AguardandoEntrega("Aguardando entrega",AcaoItemRequisicaoProduto.Pedido),
        AguardandoInspecao("Aguardando inspeção",AcaoItemRequisicaoProduto.Entrega),
        AguardandoInspecaoGarantia("Aguardando inspeção",AcaoItemRequisicaoProduto.Garantia),
        InspecaoRejeitada("Inspeção rejeitada",AcaoItemRequisicaoProduto.Inspecao),
        Cancelado("Cancelado",AcaoItemRequisicaoProduto.Cancelamento),
        Finalizado("Finalizado",AcaoItemRequisicaoProduto.Inspecao);
        
        private String descricao;
        private AcaoItemRequisicaoProduto acaoPadrao;
        
        SituacaoItemRequisicaoProduto(String descricao, AcaoItemRequisicaoProduto acaoPadrao) {
            this.descricao = descricao;
            this.acaoPadrao = acaoPadrao;
        }

		public String getDescricao() {
			return descricao;
		}

		public AcaoItemRequisicaoProduto getAcaoPadrao() {
			return acaoPadrao;
		}
    }
        
    public enum AcaoItemRequisicaoProduto {
        Criacao("Criação da requisição"),
        Alteracao("Alteração da requisição"),
        Validacao("Validação pela área de compras"),
        Autorizacao("Autorização da compra"),
        Publicacao("Publicação dos resultados"),
        Aprovacao("Aprovação da cotação"),
        Reabertura("Reabertura das coletas de preço"),        
        Pedido("Geração do pedido"),
        Entrega("Entrega do pedido"),
        Garantia("Retorno de garantia"),
        Inspecao("Inspeção do solicitante"),
        Cancelamento("Exclusão/Cancelamento do item"),
        ExclusaoPedido("Exclusão do pedido"),   
        ExclusaoItemCotacao("Exclusão do item de cotação"),   
        EncerramentoCotacao("Encerramento da cotação");        
        
        private String descricao;
        
        AcaoItemRequisicaoProduto(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
    
    public enum TipoAlcada {
        Compras("Autorização de compras"), 
        Pessoal("Requisição de pessoal"),
        Viagem("Requisicao de Viagem");

        private String descricao;

        TipoAlcada(String descricao) {

            this.descricao = descricao;

        }

        public String getDescricao() {
            return descricao;
        }

    }
 
    public enum SituacaoCotacao {
        EmAndamento("Em andamento"),
        Calculada("Resultado calculado"),
        Publicada("Resultado publicado"),
        Pedido("Pedido(s) gerado(s)"),
        Entrega("Pedido(s) entregue(s)"),
        Finalizada("Finalizada"),
        Cancelada("Cancelada");

        private String descricao;

        SituacaoCotacao(String descricao) {

            this.descricao = descricao;

        }

        public String getDescricao() {
            return descricao;
        }
    }    

    public enum SituacaoCotacaoItemRequisicao {
        AguardaAprovacao("Aguardando aprovação"),
        PreAprovado("Pré aprovado"),
        Aprovado("Aprovado"),
        NaoAprovado("Não aprovado");

        private String descricao;

        SituacaoCotacaoItemRequisicao(String descricao) {

            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
    
    public enum OrigemNotificacao {
        B("Notificação de base de conhecimento"),
        P("Notificação de Pasta"),
        S("Notificação de serviço de contrato");
        
        private String descricao;
        
        OrigemNotificacao (String descricao){
            this.descricao  = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    public enum ResultadoValidacao {   
        V("Validado"),   
        A("Aviso"),   
        E("Erro não impeditivo"),
        I("Erro impeditivo");  
        
        private String descricao;   
           
        ResultadoValidacao (String descricao){   
            this.descricao  = descricao;   
        }   
        
        public String getDescricao() {   
            return descricao;   
        }   
    } 

    public enum SituacaoPedidoCompra {   
        Efetivado("Efetivado"),   
        Analise("Aguardando análise de crédito"),   
        Aprovacao("Aguardando aprovação do pagamento"),
        Aprovado("Pagamento aprovado"),
        Transportadora("Entregue à transportadora"),
        Transporte("Em rota de entrega"),
        Entregue("Entregue");  
        
        private String descricao;   
           
        SituacaoPedidoCompra (String descricao){   
            this.descricao  = descricao;   
        }   
        
        public String getDescricao() {   
            return descricao;   
        }   
    }
    
    public enum SituacaoEntregaItemRequisicao {
        Aguarda("Aguardando inspeção"),
        Aprovada("Entrega aprovada"),
        NaoAprovada("Entrega nao aprovada");

        private String descricao;

        SituacaoEntregaItemRequisicao(String descricao) {

            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
    
    public enum SituacaoSLA {
        N("Não iniciado"),
        S("Suspenso"),
        A("Em andamento");

        private String descricao;

        SituacaoSLA(String descricao) {

            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
    
    public enum Moeda{
    	UST("UST"),
    	REAL("Real");
    	
    	private String descricao;
    	
    	Moeda(String descricao){
    		this.descricao = descricao;
    	}
    	
    	public String getDescricao(){
    		return descricao;
    	}
    }
    
    public enum SituacaoProblema {
		EmAndamento("Em andamento"), Suspensa("Suspensa"), Cancelada("Cancelada"), Resolvida("Resolvida"), Reaberta("Reaberta"), Fechada("Fechada"), ReClassificada("Reclassificada");

		private String descricao;

		SituacaoProblema(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
	/**
     * @author maycnn.fernandes
     *
     *
     *Identifica qual modulo que faz referencia.
     *
     */
    public enum TipoRequisicao {
    	LIBERCAO(1,"Liberação"),MUDANCA(2,"Mudança"),SOLICITACAOSERVICO(3,"Solicitação Serviço"),PROBLEMA(4,"Problema");
    	private Integer id;
    	private String descricao;
    	
    	private TipoRequisicao(Integer id, String descricao) {
    		this.id = id;
    		this.descricao = descricao;
    				
    	}
    	public Integer getId() {
    		return id;
    	}
    	public void setId(Integer item) {
    		this.id = id;
    	}
    	public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
    }
    
    
    /**
     * @author maycon.fernandes
     *
     *Enumerado para definir qual aba será vinculano na requisição.
     *
     *Ex: Módulo Liberação aba TesteLiberação. onde o iframe irá abri.
     *
     *Não criar outro enumerado adicionar novas abas.
     *
     *
     */
    public enum Aba {
    	LIBERCAOTESTE(1,"Teste Liberação"), LIBERACAOETAPAS(2,"Liberação Etapas"), LIBERACAOITEMCONFIGUACAO(3,"Liberação Item Configuração"),LIBERACAOSERVICO(4,"Liberação Serviço"), MUDANCAGENERICO(5,"Questionário Mudança");
    	private Integer id;
    	private String aba;
    	
    	private Aba(Integer id, String aba) {
    		this.id = id;
    		this.aba = aba;		
    	}
    	public Integer getId() {
    		return id;
    	}
    	public void setId(Integer item) {
    		this.id = id;
    	}
    	public String getAba() {
			return aba;
		}
		public void setAba(String descricao) {
			this.aba = aba;
		}
    }
    
    public enum TipoEntrevista {
		RH("Entrevista com RH"), 
		Gestor("Entrevista com Gestor");

		private String descricao;

		TipoEntrevista(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	} 
    
    public enum ResultadoEntrevista {
		N("Não avaliada"), 
		A("Entrevista com RH"), 
		R("Reprovado"),
		S("2ª Oportunidade"),
		D("Descarte");

		private String descricao;

		ResultadoEntrevista(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}    	
    }
    
    /**
	 * @author ronnie.lopes
	 * Enumerados do tipo de movimentação financeira do módulo de viagens.
	 */
	public enum TipoMovFinViagem {	
		D("requisicaoViagem.itemReferente",1),
		R("requisicaoViagem.ressarcimento",2);
		
		private String descricao;
		private Integer id;
		
		TipoMovFinViagem(String descricao, Integer id){   
	        this.descricao  = descricao; 
	        this.id = id;
	    }
		
		public String getDescricao() {
			return descricao;
		}
		public Integer getId() {
			return id;
		}
	
	}
	
	/**
	 * @author ronnie.lopes
	 * Enumerados da classificação de movimentação financeira do módulo de viagens.
	 */
	public enum ClassificacaoMovFinViagem {	
		Passagem("Passagem",1),
		Hospedagem("Hospedagem",2),
		LocacaoVeiculo("Locação Veículo",3),
		Diaria("Diária",4),
		DespesaExtra("Despesa Extra",5);
		
		private String descricao;
		private Integer id;
		
		ClassificacaoMovFinViagem(String descricao, Integer id){   
	        this.descricao  = descricao; 
	        this.id = id;
	    }
		
		public String getDescricao() {
			return descricao;
		}
		public Integer getId() {
			return id;
		}
	}

	public enum TipoSolicitacaoServico {	
		Incidente("Incidente"),
		Requisicao("Requisição"),
		Compra("Compra"),
		Viagem("Viagem"),
		RH("Requisição de pessoal");
		
		private String descricao;
		
		TipoSolicitacaoServico(String descricao){   
	        this.descricao  = descricao; 
	    }
		
		public String getDescricao() {
			return descricao;
		}
	}
	
	//Mário Júnior - 25/10/2013 19:40 Para Ordem em SolicitaçãoServiço
	public enum OrdemSolicitacaoServico {	
		
		PRAZOLIMITE("dataHoraLimite","solicitacaoServico.prazoLimite"),
		DATACRIADA("dataHoraInicio","visao.dataCriacao"),
		SOLICITANTE("solicitanteUnidade","solicitacaoServico.solicitante"),
		CRIADOPOR("responsavel","requisitosla.criador"),
		ATRASO("atrasoSLAStr","tarefa.atraso"),
		GRUPOEXECUTOR("grupoAtual","tipoLiberacao.nomeGrupoExecutor"),
		PRIORIDADE("prioridade","solicitacaoServico.prioridade"),
		SERVICO("nomeServico","servico.servico"),
		NUMEROSOLICITACAO("idSolicitacaoServico","solicitacaoServico.numerosolicitacao"),
		CONTRATO("idContrato","requisitosla.contrato"),
		SITUACAO("situacao", "requisitosla.situacao");
		
		private String ordem;
		private String valor;
		
		OrdemSolicitacaoServico(String ordem, String valor){   
	        this.ordem  = ordem; 
	        this.valor =  valor;
	    }
		
		public String getOrdem() {
			return ordem;
		}
		public String getValor() {
			return valor;
		}
		
	}
	
	public enum ItensPorPagina { 
    	CINCO(5), DEZ(10), QUIZE(15), VINTE(20);
    	
    	private Integer valor;
    	
    	private ItensPorPagina(Integer valor){
    		this.setValor(valor);
    	}
		public Integer getValor() {
			return valor;
		}
		public void setValor(Integer valor) {
			this.valor = valor;
		}

		
	}
	
}
