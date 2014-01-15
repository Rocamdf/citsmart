package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoGrupoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoMenuDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoUsuarioDAO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
public class MenuServiceEjb extends CrudServicePojoImpl implements MenuService {

	@Override
	protected CrudDAO getDao() throws ServiceException {

		return new MenuDao();
	}

	@Override
	protected void validaCreate(Object arg0) throws Exception {

	}

	@Override
	protected void validaDelete(Object arg0) throws Exception {

	}

	@Override
	protected void validaFind(Object arg0) throws Exception {

	}

	@Override
	protected void validaUpdate(Object arg0) throws Exception {

	}

	public void updateNotNull(Collection<MenuDTO> menus) {
		getMenuDao().updateNotNull(menus);
	}

	@Override
	public Collection listarMenus() throws Exception {
		try {
			return this.getMenuDao().listarMenus();
		} catch (LogicException e) {
			throw e;
		}
	}

	public MenuDao getMenuDao() {
		try {
			return (MenuDao) getDao();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<MenuDTO> listarSubMenus(MenuDTO submenu) throws Exception {
		try {
			return this.getMenuDao().listarSubMenus(submenu);
		} catch (LogicException e) {
			throw e;
		}
	}

	@Override
	public Collection<MenuDTO> listarMenusPorPerfil(UsuarioDTO usuario, Integer idMenuPai) throws Exception {
		return getMenuDao().listarMenusPorPerfil(usuario, idMenuPai, false);
	}

	@Override
	public Collection<MenuDTO> listarMenusPorPerfil(UsuarioDTO usuario, Integer idMenuPai, boolean menuRapido) throws Exception {
		return getMenuDao().listarMenusPorPerfil(usuario, idMenuPai, menuRapido);
	}

	public void criaMenus(Integer idUsuario) throws Exception {
		MenuDao menuDao = new MenuDao();
		PerfilAcessoDTO perfilAcessoDTO = new PerfilAcessoDTO();
		PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
		PerfilAcessoUsuarioDTO perfilAcessoUsuarioDto = new PerfilAcessoUsuarioDTO();

		Collection listaMenus = menuDao.list();
		if (listaMenus == null || listaMenus.size() == 0) {

			String[] paiNome = { "Gerência Conhecimento", "Gerência Configuração", "Gerência Serviços", "Gerência Contratos", "Gerência de Pessoal", "Relatório", "Cadastros", "Visões e Meta Dados", "Sistema",
					"Justificação de Falhas", "Inventário", "Eventos", "Incidentes/Serviços" };

			String[] paiDescricao = { "Gerenciamento de Conhecimento", "Gerenciamento de Configuração", "Gerenciamento de Serviços", "Gerenciamento de Contratos", "Gerenciamento de Pessoal", "Relatórios", "Cadastros",
					"Visões e Meta Dados", "Sistema", "Justificação de Falhas", "Levantamento de Inventário", "Execução de Eventos", "Abertura de Incidentes e Serviços" };

			String[] paiLink = { "", "", "", "", "", "", "", "", "", "/justificacaoFalhas/justificacaoFalhas.load", "/inventario/inventario.load", "/eventoItemConfig/eventoItemConfig.load",
					"/gerenciamentoServicos/gerenciamentoServicos.load" };

			String[] paiOrdem = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

			String[] paiImagem = { "user_comment.png", "books_2.png", "cog_3.png", "documents.png", "users.png", "graph.png", "list_w_images.png", "strategy.png", "alert_2.png", "alert_2.png", "hard_disk.png",
					"month_calendar.png", "strategy.png" };

			String[] paiHorizontal = { "N", "N", "N", "N", "N", "N", "N", "N", "N", "S", "S", "S", "S" };

			// FILHOS GERÊNCIA CONHECIMENTO
			String[] filhoNomeGerenciaConhecimento = { "Base de Conhecimento", "Pasta" };

			String[] filhoDescricaoGerenciaConhecimento = { "Base de Conhecimento", "Pasta" };

			String[] filhoLinkGerenciaConhecimento = { "/baseConhecimento/baseConhecimento.load", "/pasta/pasta.load" };

			String[] filhoOrdemGerenciaConhecimento = { "0", "1" };

			String[] filhoImagemGerenciaConhecimento = { "user_comment.png", "documents.png" };

			String[] filhoHorizontalGerenciaConhecimento = { "N", "N" };

			// FILHOS GERÊNCIA CONFIGURAÇÃO
			String[] filhoNomeGerenciaConfiguracao = { "Pesquisa Item Config.", "Tipo Item Configuração", "Característica", "Softwares Inst/Des.", "Item de Configuração" };

			String[] filhoDescricaoGerenciaConfiguracao = { "Pesquisa de Item de Configuração", "Tipo de Item Configuração", "Característica dos Itens de Configuração", "Softwares para Instalação/Desinstalação",
					"Item de Configuração" };

			String[] filhoLinkGerenciaConfiguracao = { "/pesquisaItemConfiguracao/pesquisaItemConfiguracao.load", "/tipoItemConfiguracao/tipoItemConfiguracao.load", "/caracteristica/caracteristica.load",
					"/baseItemConfiguracao/baseItemConfiguracao.load", "/itemConfiguracao/itemConfiguracao.load" };

			String[] filhoOrdemGerenciaConfiguracao = { "0", "1", "2", "3", "4" };

			String[] filhoImagemGerenciaConfiguracao = { "books_2.png", "books_2.png", "books_2.png", "books_2.png", "books_2.png" };

			String[] filhoHorizontalGerenciaConfiguracao = { "N", "N", "N", "N", "N" };

			// FILHOS GERÊNCIA SERVIÇOS
			String[] filhoNomeGerenciaServico = { "Minhas Requisições", "Mapa Desenho Serviço", "Modelo de Email", "Serviços", "Situação de Serviço", "Prioridade", "Condição de Operação", "Importância Negócio",
					"Categoria Serviço", "Tipo Serviço", "Pesquisa Sol. Serviço" };

			String[] filhoDescricaoGerenciaServico = { "Minhas Requisições", "Mapa Desenho Serviço", "Modelo de Email", "Serviços", "Situação de Serviço", "Prioridade", "Condição de Operação", "Importância Negócio",
					"Categoria Serviço", "Tipo Serviço", "Pesquisa Sol. Serviço" };

			String[] filhoLinkGerenciaServico = { "/resumoSolicitacoesServicos/resumoSolicitacoesServicos.load", "/mapaDesenhoServico/mapaDesenhoServico.load", "/modeloEmail/modeloEmail.load",
					"/dinamicViews/dinamicViews.load?idVisao=17", "/situacaoServico/situacaoServico.load", "/prioridade/prioridade.load", "/condicaoOperacao/condicaoOperacao.load",
					"/importanciaNegocio/importanciaNegocio.load", "/categoriaServico/categoriaServico.load", "/tipoServico/tipoServico.load", "/pesquisaSolicitacoesServicos/pesquisaSolicitacoesServicos.load" };

			String[] filhoOrdemGerenciaServico = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

			String[] filhoImagemGerenciaServico = { "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png", "cog_3.png" };

			String[] filhoHorizontalGerenciaServico = { "N", "N", "N", "N", "N", "N", "N", "N", "N", "N", "N" };

			// FILHOS GERÊNCIA CONTRATOS
			String[] filhoNomeGerenciaContrato = { "Administração de Contratos", "Agenda Ativ. Periódicas", "Cadastro de Contratos", "Atividade Periódica" };

			String[] filhoDescricaoGerenciaContrato = { "Administração de Contratos", "Agenda Atividades Periódicas", "Cadastro de Contratos", "Atividade Periódica" };

			String[] filhoLinkGerenciaContrato = { "/informacoesContrato/informacoesContrato.load", "/agendaAtvPeriodicas/agendaAtvPeriodicas.load", "/dinamicViews/dinamicViews.load?idVisao=20",
					"/atividadePeriodica/atividadePeriodica.load" };

			String[] filhoOrdemGerenciaContrato = { "0", "1", "2", "3" };

			String[] filhoImagemGerenciaContrato = { "documents.png", "documents.png", "documents.png", "documents.png" };

			String[] filhoHorizontalGerenciaContrato = { "N", "N", "N", "N" };

			// FILHOS GERÊNCIA DE PESSOAL
			String[] filhoNomeGerenciaPessoal = { "Calendário", "Jornada de Trabalho", "Colaborador", "Grupo", "Usuário", "Unidade", "Tipo de Unidade", "Perfil Acesso", "Cargos" };

			String[] filhoDescricaoGerenciaPessoal = { "Calendário", "Jornada de Trabalho", "Colaborador", "Grupo", "Usuário", "Unidade", "Tipo de Unidade", "Perfil Acesso", "Cargos" };

			String[] filhoLinkGerenciaPessoal = { "/calendario/calendario.load", "/jornadaTrabalho/jornadaTrabalho.load", "/empregado/empregado.load", "/grupo/grupo.load", "/usuario/usuario.load",
					"/unidade/unidade.load", "/tipoUnidade/tipoUnidade.load", "/perfilAcesso/perfilAcesso.load", "/cargos/cargos.load" };

			String[] filhoOrdemGerenciaPessoal = { "0", "1", "2", "3", "4", "5", "6", "7", "8" };

			String[] filhoImagemGerenciaPessoal = { "users.png", "users.png", "users.png", "users.png", "users.png", "users.png", "users.png", "users.png", "users.png" };

			String[] filhoHorizontalGerenciaPessoal = { "N", "N", "N", "N", "N", "N", "N", "N", "N" };

			// FILHOS RELATÓRIO
			String[] filhoNomeGerenciaRelatorio = { "Gantt", "Gráficos", "Gráfico Tempo Real", "Quantitativo de Incidentes / Solicitações", "Relatório Base de Conhecimento", "Utilização das USTs" };

			String[] filhoDescricaoGerenciaRelatorio = { "Gantt", "Gráficos", "Gráfico Tempo Real", "Relatório Quantitativo de Incidentes / Solicitações", "Relatório Base de Conhecimento",
					"Relatório de Utilização das USTs" };

			String[] filhoLinkGerenciaRelatorio = { "/ganttSolicitacaoServico/ganttSolicitacaoServico.load", "/painel/painel.load", "/graficos/graficos.load", "/relatorioQuantitativo/relatorioQuantitativo.load",
					"/relatorioBaseConhecimento/relatorioBaseConhecimento.load", "/relatorioOrdemServicoUst/relatorioOrdemServicoUst.load" };

			String[] filhoOrdemGerenciaRelatorio = { "0", "1", "2", "3", "4", "5" };

			String[] filhoImagemGerenciaRelatorio = { "graph.png", "graph.png", "graph.png", "documents.png", "documents.png", "documents.png" };

			String[] filhoHorizontalGerenciaRelatorio = { "N", "N", "N", "N", "N", "N" };

			// FILHOS CADASTROS
			String[] filhoNomeGerenciaCadastro = { "Administração de Contratos", "Agenda Ativ. Periódicas", "Cadastro de Contratos", "Atividade Periódica" };

			String[] filhoDescricaoGerenciaCadastro = { "Administração de Contratos", "Agenda Atividades Periódicas", "Cadastro de Contratos", "Atividade Periódica" };

			String[] filhoLinkGerenciaCadastro = { "/informacoesContrato/informacoesContrato.load", "/agendaAtvPeriodicas/agendaAtvPeriodicas.load", "/dinamicViews/dinamicViews.load?idVisao=20",
					"/atividadePeriodica/atividadePeriodica.load" };

			String[] filhoOrdemGerenciaCadastro = { "0", "1", "2", "3" };

			String[] filhoImagemGerenciaCadastro = { "documents.png", "documents.png", "documents.png", "documents.png" };

			String[] filhoHorizontalGerenciaCadastro = { "N", "N", "N", "N" };

			// FILHOS VISÕES E META DADOS
			String[] filhoNomeGerenciaVisoesMetaDados = { "Questionário", "Carrega Meta Dados", "Manutenção de Visões" };

			String[] filhoDescricaoGerenciaVisoesMetaDados = { "Questionário", "Carrega Meta Dados", "Manutenção de Visões" };

			String[] filhoLinkGerenciaVisoesMetaDados = { "/questionario/questionario.load", "/dataBaseMetaDados/dataBaseMetaDados.load", "/visaoAdm/visaoAdm.load" };

			String[] filhoOrdemGerenciaVisoesMetaDados = { "0", "1", "2" };

			String[] filhoImagemGerenciaVisoesMetaDados = { "strategy.png", "strategy.png", "strategy.png" };

			String[] filhoHorizontalGerenciaVisoesMetaDados = { "N", "N", "N" };

			// FILHOS SISTEMA
			String[] filhoNomeGerenciaSistema = { "Parâmetros CITSmart" };

			String[] filhoDescricaoGerenciaSistema = { "Parâmetros CITSmart" };

			String[] filhoLinkGerenciaSistema = { "/parametroCorpore/parametroCorpore.load" };

			String[] filhoOrdemGerenciaSistema = { "0" };

			String[] filhoImagemGerenciaSistema = { "alert_2.png" };

			String[] filhoHorizontalGerenciaSistema = { "N" };

			perfilAcessoDTO.setIdPerfilAcesso(1);
			perfilAcessoUsuarioDto.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
			perfilAcessoUsuarioDto.setIdUsuario(idUsuario);
			perfilAcessoUsuarioDto.setDataInicio(UtilDatas.getDataAtual());

			// CRIAÇÃO DOS MENUS
			int i = 0;
			for (String pai : paiNome) {
				MenuDTO dto = new MenuDTO();
				PerfilAcessoMenuDTO perfilAcessoMenuDTOPai = new PerfilAcessoMenuDTO();

				dto.setDataInicio(UtilDatas.getDataAtual());
				dto.setNome(pai);
				dto.setDescricao(paiDescricao[i]);
				dto.setImagem(paiImagem[i]);
				dto.setOrdem(new Integer(paiOrdem[i]));
				dto.setLink(paiLink[i]);
				dto.setHorizontal(paiHorizontal[i]);
				i++;
				dto = (MenuDTO) menuDao.create(dto);

				perfilAcessoMenuDTOPai.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
				perfilAcessoMenuDTOPai.setIdMenu(dto.getIdMenu());
				perfilAcessoMenuDTOPai.setPesquisa("S");
				perfilAcessoMenuDTOPai.setGrava("S");
				// perfilAcessoMenuDTOPai.setAltera("S");
				perfilAcessoMenuDTOPai.setDeleta("S");
				// perfilAcessoMenuDTOPai.setInclui("S");
				perfilAcessoMenuDTOPai.setDataInicio(UtilDatas.getDataAtual());
				perfilAcessoMenuDao.create(perfilAcessoMenuDTOPai);

				// FILHOS GERÊNCIA CONHECIMENTO
				if (pai.equals("Gerência Conhecimento")) {
					int y = 0;
					for (String filho : filhoNomeGerenciaConhecimento) {
						PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
						MenuDTO dtoFilho = new MenuDTO();
						dtoFilho.setDataInicio(UtilDatas.getDataAtual());
						dtoFilho.setNome(filho);
						dtoFilho.setDescricao(filhoDescricaoGerenciaConhecimento[y]);
						dtoFilho.setImagem(filhoImagemGerenciaConhecimento[y]);
						dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaConhecimento[y]));
						dtoFilho.setLink(filhoLinkGerenciaConhecimento[y]);
						dtoFilho.setHorizontal(filhoHorizontalGerenciaConhecimento[y]);
						dtoFilho.setIdMenuPai(dto.getIdMenu());
						y++;
						dtoFilho = (MenuDTO) menuDao.create(dtoFilho);

						perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
						perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
						perfilAcessoMenuDTOFilho.setPesquisa("S");
						perfilAcessoMenuDTOFilho.setGrava("S");
						// perfilAcessoMenuDTOFilho.setAltera("S");
						perfilAcessoMenuDTOFilho.setDeleta("S");
						// perfilAcessoMenuDTOFilho.setInclui("S");
						perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

						perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
					}
				}

				// FILHOS GERÊNCIA CONFIGURAÇÃO
				if (pai.equals("Gerência Configuração")) {
					int y = 0;
					for (String filho : filhoNomeGerenciaConfiguracao) {
						PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
						MenuDTO dtoFilho = new MenuDTO();
						dtoFilho.setDataInicio(UtilDatas.getDataAtual());
						dtoFilho.setNome(filho);
						dtoFilho.setDescricao(filhoDescricaoGerenciaConfiguracao[y]);
						dtoFilho.setImagem(filhoImagemGerenciaConfiguracao[y]);
						dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaConfiguracao[y]));
						dtoFilho.setLink(filhoLinkGerenciaConfiguracao[y]);
						dtoFilho.setHorizontal(filhoHorizontalGerenciaConfiguracao[y]);
						dtoFilho.setIdMenuPai(dto.getIdMenu());
						y++;
						dtoFilho = (MenuDTO) menuDao.create(dtoFilho);

						perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
						perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
						perfilAcessoMenuDTOFilho.setPesquisa("S");
						perfilAcessoMenuDTOFilho.setGrava("S");
						perfilAcessoMenuDTOFilho.setDeleta("S");
						perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

						perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
					}
				}

				// FILHOS GERÊNCIA SERVIÇO
				if (pai.equals("Gerência Serviços")) {
					int y = 0;
					for (String filho : filhoNomeGerenciaServico) {
						PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
						MenuDTO dtoFilho = new MenuDTO();
						dtoFilho.setDataInicio(UtilDatas.getDataAtual());
						dtoFilho.setNome(filho);
						dtoFilho.setDescricao(filhoDescricaoGerenciaServico[y]);
						dtoFilho.setImagem(filhoImagemGerenciaServico[y]);
						dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaServico[y]));
						dtoFilho.setLink(filhoLinkGerenciaServico[y]);
						dtoFilho.setHorizontal(filhoHorizontalGerenciaServico[y]);
						dtoFilho.setIdMenuPai(dto.getIdMenu());
						y++;
						dtoFilho = (MenuDTO) menuDao.create(dtoFilho);

						perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
						perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
						perfilAcessoMenuDTOFilho.setPesquisa("S");
						perfilAcessoMenuDTOFilho.setGrava("S");
						perfilAcessoMenuDTOFilho.setDeleta("S");
						perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

						perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
					}
				}

				// FILHOS GERÊNCIA CONTRATOS
				if (pai.equals("Gerência Contratos")) {
					int y = 0;
					for (String filho : filhoNomeGerenciaContrato) {
						PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
						MenuDTO dtoFilho = new MenuDTO();
						dtoFilho.setDataInicio(UtilDatas.getDataAtual());
						dtoFilho.setNome(filho);
						dtoFilho.setDescricao(filhoDescricaoGerenciaContrato[y]);
						dtoFilho.setImagem(filhoImagemGerenciaContrato[y]);
						dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaContrato[y]));
						dtoFilho.setLink(filhoLinkGerenciaContrato[y]);
						dtoFilho.setHorizontal(filhoHorizontalGerenciaContrato[y]);
						dtoFilho.setIdMenuPai(dto.getIdMenu());
						y++;
						dtoFilho = (MenuDTO) menuDao.create(dtoFilho);

						perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
						perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
						perfilAcessoMenuDTOFilho.setPesquisa("S");
						perfilAcessoMenuDTOFilho.setGrava("S");
						perfilAcessoMenuDTOFilho.setDeleta("S");
						perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

						perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
					}
				}

				// FILHOS GERÊNCIA PESSOAL
				if (pai.equals("Gerência de Pessoal")) {
					int y = 0;
					for (String filho : filhoNomeGerenciaPessoal) {
						PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
						MenuDTO dtoFilho = new MenuDTO();
						dtoFilho.setDataInicio(UtilDatas.getDataAtual());
						dtoFilho.setNome(filho);
						dtoFilho.setDescricao(filhoDescricaoGerenciaPessoal[y]);
						dtoFilho.setImagem(filhoImagemGerenciaPessoal[y]);
						dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaPessoal[y]));
						dtoFilho.setLink(filhoLinkGerenciaPessoal[y]);
						dtoFilho.setHorizontal(filhoHorizontalGerenciaPessoal[y]);
						dtoFilho.setIdMenuPai(dto.getIdMenu());
						y++;
						dtoFilho = (MenuDTO) menuDao.create(dtoFilho);

						perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
						perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
						perfilAcessoMenuDTOFilho.setPesquisa("S");
						perfilAcessoMenuDTOFilho.setGrava("S");
						perfilAcessoMenuDTOFilho.setDeleta("S");
						perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

						perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
					}
				}

				// RELATÓRIO
				if (pai.equals("Relatório")) {
					int y = 0;
					for (String filho : filhoNomeGerenciaRelatorio) {
						PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
						MenuDTO dtoFilho = new MenuDTO();
						dtoFilho.setDataInicio(UtilDatas.getDataAtual());
						dtoFilho.setNome(filho);
						dtoFilho.setDescricao(filhoDescricaoGerenciaRelatorio[y]);
						dtoFilho.setImagem(filhoImagemGerenciaRelatorio[y]);
						dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaRelatorio[y]));
						dtoFilho.setLink(filhoLinkGerenciaRelatorio[y]);
						dtoFilho.setHorizontal(filhoHorizontalGerenciaRelatorio[y]);
						dtoFilho.setIdMenuPai(dto.getIdMenu());
						y++;
						dtoFilho = (MenuDTO) menuDao.create(dtoFilho);

						perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
						perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
						perfilAcessoMenuDTOFilho.setPesquisa("S");
						perfilAcessoMenuDTOFilho.setGrava("S");
						perfilAcessoMenuDTOFilho.setDeleta("S");
						perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

						perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
					}
				}

				// CADASTROS
				if (pai.equals("Cadastros")) {
					int y = 0;
					for (String filho : filhoNomeGerenciaCadastro) {
						PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
						MenuDTO dtoFilho = new MenuDTO();
						dtoFilho.setDataInicio(UtilDatas.getDataAtual());
						dtoFilho.setNome(filho);
						dtoFilho.setDescricao(filhoDescricaoGerenciaCadastro[y]);
						dtoFilho.setImagem(filhoImagemGerenciaCadastro[y]);
						dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaCadastro[y]));
						dtoFilho.setLink(filhoLinkGerenciaCadastro[y]);
						dtoFilho.setHorizontal(filhoHorizontalGerenciaCadastro[y]);
						dtoFilho.setIdMenuPai(dto.getIdMenu());
						y++;
						dtoFilho = (MenuDTO) menuDao.create(dtoFilho);

						perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
						perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
						perfilAcessoMenuDTOFilho.setPesquisa("S");
						perfilAcessoMenuDTOFilho.setGrava("S");
						perfilAcessoMenuDTOFilho.setDeleta("S");
						perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

						perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
					}
				}

				// VISÕES E META DADOS
				if (pai.equals("Visões e Meta Dados")) {
					int y = 0;
					for (String filho : filhoNomeGerenciaVisoesMetaDados) {
						PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
						MenuDTO dtoFilho = new MenuDTO();
						dtoFilho.setDataInicio(UtilDatas.getDataAtual());
						dtoFilho.setNome(filho);
						dtoFilho.setDescricao(filhoDescricaoGerenciaVisoesMetaDados[y]);
						dtoFilho.setImagem(filhoImagemGerenciaVisoesMetaDados[y]);
						dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaVisoesMetaDados[y]));
						dtoFilho.setLink(filhoLinkGerenciaVisoesMetaDados[y]);
						dtoFilho.setHorizontal(filhoHorizontalGerenciaVisoesMetaDados[y]);
						dtoFilho.setIdMenuPai(dto.getIdMenu());
						y++;
						dtoFilho = (MenuDTO) menuDao.create(dtoFilho);

						perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
						perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
						perfilAcessoMenuDTOFilho.setPesquisa("S");
						perfilAcessoMenuDTOFilho.setGrava("S");
						perfilAcessoMenuDTOFilho.setDeleta("S");
						perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

						perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
					}
				}

				// SISTEMA
				if (pai.equals("Sistema")) {
					int y = 0;
					for (String filho : filhoNomeGerenciaSistema) {
						PerfilAcessoMenuDTO perfilAcessoMenuDTOFilho = new PerfilAcessoMenuDTO();
						MenuDTO dtoFilho = new MenuDTO();
						dtoFilho.setDataInicio(UtilDatas.getDataAtual());
						dtoFilho.setNome(filho);
						dtoFilho.setDescricao(filhoDescricaoGerenciaSistema[y]);
						dtoFilho.setImagem(filhoImagemGerenciaSistema[y]);
						dtoFilho.setOrdem(new Integer(filhoOrdemGerenciaSistema[y]));
						dtoFilho.setLink(filhoLinkGerenciaSistema[y]);
						dtoFilho.setHorizontal(filhoHorizontalGerenciaSistema[y]);
						dtoFilho.setIdMenuPai(dto.getIdMenu());
						y++;
						dtoFilho = (MenuDTO) menuDao.create(dtoFilho);

						perfilAcessoMenuDTOFilho.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
						perfilAcessoMenuDTOFilho.setIdMenu(dtoFilho.getIdMenu());
						perfilAcessoMenuDTOFilho.setPesquisa("S");
						perfilAcessoMenuDTOFilho.setGrava("S");
						perfilAcessoMenuDTOFilho.setDeleta("S");
						perfilAcessoMenuDTOFilho.setDataInicio(UtilDatas.getDataAtual());

						perfilAcessoMenuDao.create(perfilAcessoMenuDTOFilho);
					}
				}
			}
		}
	}

	public Collection<MenuDTO> listaMenuByUsr(UsuarioDTO usuario) throws Exception {
		return getMenuDao().listaMenuByUsr(usuario);
	}

	@Override
	public boolean verificaSeExisteMenu(MenuDTO menuDTO) throws Exception {
		return getMenuDao().verificaSeExisteMenu(menuDTO);
	}

	@Override
	public Integer buscarIdMenu(String link) throws Exception {
		return getMenuDao().buscarIdMenu(link);
	}

	@Override
	public Collection<MenuDTO> listarMenusPais() throws Exception {
		return getMenuDao().listarMenusPais();
	}

	@Override
	public Collection<MenuDTO> listarMenusFilhos(Integer idMenuPai) throws Exception {
		return getMenuDao().listarMenusFilhos(idMenuPai);
	}

	@Override
	public void gerarCarga(File file) throws Exception {
		MenuDao menuDao = new MenuDao();
		PerfilAcessoDao perfilAcessoDAO = new PerfilAcessoDao();
		PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
		PerfilAcessoDao perfilAcessoDAOAux = new PerfilAcessoDao();
		TransactionControler tc = new TransactionControlerImpl(menuDao.getAliasDB());
		menuDao.setTransactionControler(tc);
		perfilAcessoMenuDao.setTransactionControler(tc);
		perfilAcessoDAO.setTransactionControler(tc);
		perfilAcessoDAOAux.setTransactionControler(tc);
		Integer idPerfilAcesso = perfilAcessoDAO.listarIdAdministrador();
		if (idPerfilAcesso != null) {
			try {
				tc.start();
				ArrayList<MenuDTO> menusPais = (ArrayList) menuDao.listarMenusPais();
				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(file);
				Element elements = doc.getRootElement();
				List<Element> menuSuperior = elements.getChild("menuSuperior").getChildren();
				for (Element menuCarregadoXMLElement : menuSuperior) {
					int j = 0;
					MenuDTO menuCarregadoXmlDTO = new MenuDTO();
					MenuDTO menuDtoAux = new MenuDTO();
					menuCarregadoXmlDTO.setNome(menuCarregadoXMLElement.getChildText("nome").trim());
					menuCarregadoXmlDTO.setDescricao(menuCarregadoXMLElement.getChildText("descricao"));
					menuCarregadoXmlDTO.setOrdem(Integer.parseInt(menuCarregadoXMLElement.getChildText("ordem")));
					menuCarregadoXmlDTO.setLink(menuCarregadoXMLElement.getChildText("link").trim());
					menuCarregadoXmlDTO.setImagem(menuCarregadoXMLElement.getChildText("imagem"));
					menuCarregadoXmlDTO.setHorizontal(menuCarregadoXMLElement.getChildText("horizontal"));
					menuCarregadoXmlDTO.setMenuRapido(menuCarregadoXMLElement.getChildText("menuRapido"));
					menuCarregadoXmlDTO.setDataInicio(UtilDatas.getDataAtual());
					menuCarregadoXmlDTO.setMostrar(menuCarregadoXMLElement.getChildText("mostrar"));
										
					for (MenuDTO menuDoBancoDTO : menusPais) {
						if (menuCarregadoXmlDTO.getNome() != null && menuDoBancoDTO.getNome() != null) {
							if (menuDoBancoDTO.getNome().trim().replaceAll(" ", "").equalsIgnoreCase(menuCarregadoXmlDTO.getNome().trim().replaceAll(" ", "")) && menuDoBancoDTO.getLink().trim().equalsIgnoreCase(menuCarregadoXmlDTO.getLink().trim())) {
								menuCarregadoXmlDTO.setIdMenu(menuDoBancoDTO.getIdMenu());
								menuCarregadoXmlDTO.setDataFim(null);
								menuDoBancoDTO.setDescricao(menuCarregadoXmlDTO.getDescricao());
								menuDoBancoDTO.setMostrar(menuCarregadoXmlDTO.getMostrar());
								menuDao.update(menuDoBancoDTO);
								menuDtoAux = menuDoBancoDTO;
								break;
							}
						}
						
					}
					if (menuCarregadoXmlDTO.getIdMenu() == null) {
						if (!menuDao.verificaSeExisteMenuPorLink(menuCarregadoXmlDTO)) {
							menuDtoAux = (MenuDTO) menuDao.create(menuCarregadoXmlDTO);
						}
					}
					
					/* Cria Acesso ao administrador */
					PerfilAcessoMenuDTO perfilAcessoMenuDTO = new PerfilAcessoMenuDTO();
					perfilAcessoMenuDTO.setDataInicio(UtilDatas.getDataAtual());
					perfilAcessoMenuDTO.setDeleta("S");
					perfilAcessoMenuDTO.setGrava("S");
					perfilAcessoMenuDTO.setPesquisa("S");
					perfilAcessoMenuDTO.setIdMenu(menuDtoAux.getIdMenu());
					perfilAcessoMenuDTO.setIdPerfilAcesso(idPerfilAcesso);
					if (perfilAcessoMenuDao.restoreMenusAcesso(perfilAcessoMenuDTO).isEmpty()) {
						perfilAcessoMenuDao.create(perfilAcessoMenuDTO);
					}
					if (!menuCarregadoXMLElement.getChild("subMenu" + j).getChildren().isEmpty()) {
						this.importarFilhos(menuCarregadoXMLElement, j, menuDtoAux, menuDao, idPerfilAcesso, perfilAcessoMenuDao);
					}
				}
				
				
				tc.commit();
				tc.close();
				tc = null;
			} catch (ServiceException e) {
				this.rollbackTransaction(tc, e);
				e.printStackTrace();
			}
		}
	}

	
	public void deletaMenusSemReferencia() throws Exception {
		MenuDao menuDao = new MenuDao();
		TransactionControler tc = new TransactionControlerImpl(menuDao.getAliasDB());
		menuDao.setTransactionControler(tc);
		
		
		try {
			ArrayList<MenuDTO> menusPaisAtualizados = (ArrayList) menuDao.listarMenusPais();
			for (MenuDTO menuDoBancoAtualizadoDTO : menusPaisAtualizados) {
				ArrayList<MenuDTO> menuFilho = (ArrayList) menuDao.listarMenusFilhoByIdMenuPai(menuDoBancoAtualizadoDTO.getIdMenu());
				if((menuDoBancoAtualizadoDTO.getLink() == null || menuDoBancoAtualizadoDTO.getLink().equalsIgnoreCase("")) &&(menuFilho == null || menuFilho.isEmpty())){
					menuDao.deleteMenu(menuDoBancoAtualizadoDTO.getIdMenu());
				}
			}
			
			tc.commit();
			tc.close();
			tc = null;
		} catch (ServiceException e) {
			this.rollbackTransaction(tc, e);
			e.printStackTrace();
		}
	}
	
	private void importarFilhos(Element menus, int j, MenuDTO menuDtoAux, MenuDao menuDao, Integer idPerfilAcesso, PerfilAcessoMenuDao perfilAcessoMenuDao) throws Exception {
		List<Element> subMenu = menus.getChild("subMenu" + j).getChildren();
		ArrayList<MenuDTO> menusFilhos = (ArrayList) menuDao.listarMenusFilhos(menuDtoAux.getIdMenu());
		/* menus filhos */
		for (Element subMenus : subMenu) {
			MenuDTO menuDtoAux1 = new MenuDTO();
			MenuDTO menuDTO = new MenuDTO();
			menuDTO.setIdMenuPai(menuDtoAux.getIdMenu());
			menuDTO.setNome(subMenus.getChildText("nome").trim());
			menuDTO.setDescricao(subMenus.getChildText("descricao"));
			menuDTO.setOrdem(Integer.parseInt(subMenus.getChildText("ordem")));
			menuDTO.setLink(subMenus.getChildText("link").trim());
			menuDTO.setImagem(subMenus.getChildText("imagem"));
			menuDTO.setHorizontal(subMenus.getChildText("horizontal"));
			menuDTO.setMenuRapido(subMenus.getChildText("menuRapido"));
			menuDTO.setDataInicio(UtilDatas.getDataAtual());
			for (MenuDTO menusDTO : menusFilhos) {
				if (menuDTO.getNome() != null && menusDTO.getNome() != null) {
					if (menusDTO.getNome().trim().replaceAll(" ", "").equalsIgnoreCase(menuDTO.getNome().trim().replaceAll(" ", "")) && menusDTO.getLink().trim().equalsIgnoreCase(menuDTO.getLink().trim())) {
						menuDTO.setIdMenu(menusDTO.getIdMenu());
						menuDTO.setDataFim(null);
						menusDTO.setDescricao(menuDTO.getDescricao());
						menuDao.update(menusDTO);
						menuDtoAux1 = menusDTO;
						break;
					}
				}
			}
			if (menuDTO.getIdMenu() == null) {
				if (menuDao.verificaSeExisteMenuPorLink(menuDTO) == false) {
					menuDtoAux1 = (MenuDTO) menuDao.create(menuDTO);
				}
				else{
					menuDao.alterarMenuPorNome(menuDTO);
				}
			}
			PerfilAcessoMenuDTO perfilAcessoMenuDTO = new PerfilAcessoMenuDTO();
			perfilAcessoMenuDTO.setDataInicio(UtilDatas.getDataAtual());
			perfilAcessoMenuDTO.setDeleta("S");
			perfilAcessoMenuDTO.setGrava("S");
			perfilAcessoMenuDTO.setPesquisa("S");
			perfilAcessoMenuDTO.setIdMenu(menuDtoAux1.getIdMenu());
			perfilAcessoMenuDTO.setIdPerfilAcesso(idPerfilAcesso);
			if (perfilAcessoMenuDao.restoreMenusAcesso(perfilAcessoMenuDTO).isEmpty()) {
				perfilAcessoMenuDao.create(perfilAcessoMenuDTO);
			}
			if (subMenus.getChild("subMenu" + (j + 1)) != null) {
				this.importarFilhos(subMenus, (j + 1), menuDtoAux1, menuDao, idPerfilAcesso, perfilAcessoMenuDao);
			}
		}
		j++;
	}
}
