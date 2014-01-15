package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.DicionarioDTO;
import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.integracao.LinguaService;
import br.com.centralit.citcorpore.negocio.DicionarioService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoUsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Menu extends AjaxFormAction {

	private MenuDTO menuBean;

	@Override
	public Class getBeanClass() {
		return MenuDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.focusInFirstActivateField(null);
		preencherComboMenuPai(document, request, response);
		
		String mostraBotoes = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.MOSTRAR_BOTOES_IMPORTACAO_XML_CADASTRO_MENU, "S");
		
		if(mostraBotoes.trim().equals("S")){
			document.getElementById("btnGerar").setVisible(true);
			document.getElementById("btnAtualizar").setVisible(true);
		}else{
			document.getElementById("btnGerar").setVisible(false);
			document.getElementById("btnAtualizar").setVisible(false);
		}
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		LinguaDTO linguaDTO = new LinguaDTO();
			linguaDTO.setSigla((String) session.getAttribute("locale"));
		if(linguaDTO.getSigla() == null){
			linguaDTO.setSigla("pt");
		}
		else if (linguaDTO.getSigla().equals("")){
			linguaDTO.setSigla("pt");
		}
		LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
		linguaDTO = linguaService.getIdLingua(linguaDTO);
		document.executeScript("setaLingua("+linguaDTO.getIdLingua()+")");
	}

	/**
	 * Preenche a combo MenuPerfis Pai.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	private void preencherComboMenuPai(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
		HTMLSelect comboMenuPai = (HTMLSelect) document.getSelectById("idMenuPai");
		ArrayList<MenuDTO> menus = (ArrayList) menuService.list();

		inicializarCombo(comboMenuPai, request);

		for (MenuDTO menuDto : menus) {
			if (menuDto.getDataFim() == null && (menuDto.getLink()==null || menuDto.getLink().trim().equals(""))) {
				comboMenuPai.addOption(menuDto.getIdMenu().toString(), UtilI18N.internacionaliza(request, menuDto.getNome()));
			}
		}
	}

	/**
	 * Inclui Novo MenuPesfis.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setMenuBean((MenuDTO) document.getBean());
		
		DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
		
		DicionarioDTO dicionarioDTO = new DicionarioDTO();
		if (!this.getMenuBean().getNome().startsWith("$")){
			document.alert(UtilI18N.internacionaliza(request, "menu.nomeInvalidoChave"));
			return;
		}
		else{
			dicionarioDTO.setNome(StringUtils.remove(this.getMenuBean().getNome(), "$"));
		}
			
			
		
		/*Setando o menu Vertical como padrão do sistema*/
		if(this.getMenuBean().getMenuRapido() == null)
			this.getMenuBean().setHorizontal("N");
		else
			this.getMenuBean().setHorizontal("S");
		if(this.getMenuBean().getIdMenuPai()==null) {
			this.getMenuBean().setMenuRapido(null);
		}
		
		if (this.getMenuBean().getIdMenu() == null || this.getMenuBean().getIdMenu().intValue() == 0) {
			if (dicionarioService.verificarDicionarioAtivoByKey(dicionarioDTO) == null){
				document.alert(UtilI18N.internacionaliza(request, "menu.nomeChaveNaoEncontrado"));
				return;
			}
			if (this.getMenuService().verificaSeExisteMenu(this.getMenuBean())) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			this.getMenuBean().setDataInicio(UtilDatas.getDataAtual());
			// Setando valor default para novo menu
			//this.getMenuBean().setOrdem(0);
			MenuDTO menuDTO = new MenuDTO();
			/*Seta perfil de acesso ao administrador*/
			PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
			menuDTO = (MenuDTO) this.getMenuService().create(this.getMenuBean());
			PerfilAcessoMenuDTO perfilAcessoMenuDTO = new PerfilAcessoMenuDTO();
			perfilAcessoMenuDTO.setDataInicio(UtilDatas.getDataAtual());
			perfilAcessoMenuDTO.setDeleta("S");
			perfilAcessoMenuDTO.setGrava("S");
			perfilAcessoMenuDTO.setPesquisa("S");
			perfilAcessoMenuDTO.setIdMenu(menuDTO.getIdMenu());
			perfilAcessoMenuDTO.setIdPerfilAcesso(1);
			perfilAcessoMenuService.create(perfilAcessoMenuDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			if (dicionarioService.verificarDicionarioAtivoByKey(dicionarioDTO) == null){
				document.alert("Chave não encontrada, favor cadastrar a chave antes de criar o menu");
				return;
			}
			// Verifica se o menu alterado é o mesmo que o Menu Pai
			if (this.getMenuBean().getIdMenu().equals(this.getMenuBean().getIdMenuPai())) {
				document.alert(UtilI18N.internacionaliza(request, "menu.validacao.menuPaiMenu"));
				return;
			}
			if (this.getMenuService().verificaSeExisteMenu(this.getMenuBean())) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			this.getMenuService().update(this.getMenuBean());
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("ativaMenuPai()");
	}

	@SuppressWarnings("unused")
	public void saveNewPositions(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario usuario = WebUtil.getUsuario((HttpServletRequest) request);

		try {
			if (!usuario.getNomeUsuario().equalsIgnoreCase("administrador") && !usuario.getNomeUsuario().equalsIgnoreCase("admin.centralit") || !usuario.getNomeUsuario().equalsIgnoreCase("consultor")) {
				document.alert(UtilI18N.internacionaliza(request, "menu.validacao.editarMenu"));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		MenuDTO menuDTO = (MenuDTO) document.getBean();
		ArrayList<MenuDTO> listaItens = (ArrayList) WebUtil.deserializeCollectionFromRequest(MenuDTO.class, "listaOrdensMenusSerializada", request);
		getMenuService().updateNotNull(listaItens);
	}

	/**
	 * Recupera menu.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setMenuBean((MenuDTO) document.getBean());

		this.preencherComboMenuPai(document, request, response);

		this.setMenuBean(this.getMenuService().restore(this.getMenuBean()));

/*		if (this.getMenuBean().getHorizontal() == null || this.getMenuBean().getHorizontal().equalsIgnoreCase("S")) {
			document.executeScript("desativaMenuPai()");
		} else {
			document.executeScript("ativaMenuPai()");
		}*/
		
		if (this.getMenuBean().getIdMenuPai()==null) {
			document.executeScript("desativaMenuRapido()");
		} else {
			document.executeScript("ativaMenuRapido()");
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(this.getMenuBean());
	}

	/**
	 * Exclui Tipo Item Configuração e suas características.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void update(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.setMenuBean((MenuDTO) document.getBean());

		if (this.getMenuBean().getIdMenu() != null && this.getMenuBean().getIdMenu() != 0) {
			this.getMenuBean().setDataFim(UtilDatas.getDataAtual());
			this.getMenuService().update(this.getMenuBean());
			document.executeScript("ativaMenuPai()");
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}

	/**
	 * Retorna instância de MenuPerfisService.
	 * 
	 * @return EmpregadoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public MenuService getMenuService() throws ServiceException, Exception {
		return (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
	}

	public PerfilAcessoUsuarioService getPerfilAcessoUsuarioService() throws ServiceException, Exception {
		return (PerfilAcessoUsuarioService) ServiceLocator.getInstance().getService(PerfilAcessoUsuarioService.class, null);
	}

	/**
	 * Atribui valor de MenuPerfisBean.
	 * 
	 * @param empregado
	 * @author thays.araujo
	 */
	public void setMenuBean(IDto menuPerfis) {
		this.menuBean = (MenuDTO) menuPerfis;
	}

	/**
	 * Retorna bean de menuPerfis.
	 * 
	 * @return EmpregadoDTO
	 * @author thays.araujo
	 */
	public MenuDTO getMenuBean() {
		return this.menuBean;
	}

	/**
	 * Iniciliza combo.
	 * 
	 * @param componenteCombo
	 * @author thays.araujo
	 */
	private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	public void exportarMenuXml(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
		

		Element tagMenuSuperior = new Element("menus");

		Element tagMenu = new Element("menuSuperior");

		ArrayList<MenuDTO> menusPais = (ArrayList) menuService.listarMenusPais();

		if (menusPais != null && !menusPais.isEmpty()) {

			for (MenuDTO menuPai : menusPais) {
				int j = 0;
				
				Element tagMenuPai = new Element("menu");
				Element nomeMenuPai = new Element("nome");
				Element descricaoMenuPai = new Element("descricao");
				Element ordemMenuPai = new Element("ordem");
				Element menuRapidoPai = new Element("menuRapido");
				Element linkMenuPai = new Element("link");
				Element imagemMenuPai = new Element("imagem");
				Element horizontalMenuPai = new Element("horizontal");

				nomeMenuPai.setText(menuPai.getNome());
				descricaoMenuPai.setText(menuPai.getDescricao());
				ordemMenuPai.setText(String.valueOf(menuPai.getOrdem()));
				menuRapidoPai.setText(String.valueOf(menuPai.getMenuRapido()));
				linkMenuPai.setText(menuPai.getLink());
				imagemMenuPai.setText(menuPai.getImagem());
				horizontalMenuPai.setText(menuPai.getHorizontal());

				tagMenuPai.addContent(nomeMenuPai);
				tagMenuPai.addContent(descricaoMenuPai);
				tagMenuPai.addContent(ordemMenuPai);
				tagMenuPai.addContent(menuRapidoPai);
				tagMenuPai.addContent(linkMenuPai);
				tagMenuPai.addContent(imagemMenuPai);
				tagMenuPai.addContent(horizontalMenuPai);

				Element tagMenuInferior = new Element("subMenu" + j);				

				tagMenuInferior = this.gerarTagMenu(menuPai, tagMenuInferior, j);
				
				tagMenuPai.addContent(tagMenuInferior);
				
				tagMenu.addContent(tagMenuPai);

			}
		}

		tagMenuSuperior.addContent(tagMenu);

		Document doc = new Document();

		doc.setRootElement(tagMenuSuperior);

		try {
			String separator = System.getProperty("file.separator");
			String diretorioReceita = CITCorporeUtil.caminho_real_app + "XMLs" + separator;			
			
			File file = new File(diretorioReceita + "menu.xml");

			Writer out = new OutputStreamWriter(new FileOutputStream(file));

			XMLOutputter xout = new XMLOutputter();

			xout.setFormat(Format.getCompactFormat().setEncoding("ISO-8859-1"));

			xout.output(doc, out);

			document.alert(UtilI18N.internacionaliza(request, "menu.criarXml"));

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private Element gerarTagMenu(MenuDTO menuPai, Element tagMenuInferior, int j) throws Exception {

		MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);


		ArrayList<MenuDTO> menusFilhos = (ArrayList) menuService.listarMenusFilhos(menuPai.getIdMenu());
		
		if(menuPai.getIdMenuPai() != null){
			j++;
			Element MenusInferiores = new Element("subMenu" + j);
		

		
		for (MenuDTO menuFilho : menusFilhos){
			Element tagMenuFilho = new Element("menu");

			Element nomeMenuFilho = new Element("nome");
			Element descricaoMenuFilho = new Element("descricao");
			Element ordemMenuFilho = new Element("ordem");
			Element menuRapidoMenuFilho = new Element("menuRapido");
			Element linkMenuFilho = new Element("link");
			Element imagemMenuFilho = new Element("imagem");
			Element horizontalMenuFilho = new Element("horizontal");

			nomeMenuFilho.setText(menuFilho.getNome());
			descricaoMenuFilho.setText(menuFilho.getDescricao());
			ordemMenuFilho.setText(String.valueOf(menuFilho.getOrdem()));
			menuRapidoMenuFilho.setText(String.valueOf(menuFilho.getMenuRapido()));
			linkMenuFilho.setText(menuFilho.getLink());
			imagemMenuFilho.setText(menuFilho.getImagem());
			horizontalMenuFilho.setText(menuFilho.getHorizontal());

			tagMenuFilho.addContent(nomeMenuFilho);
			tagMenuFilho.addContent(descricaoMenuFilho);
			tagMenuFilho.addContent(ordemMenuFilho);
			tagMenuFilho.addContent(menuRapidoMenuFilho);
			tagMenuFilho.addContent(linkMenuFilho);
			tagMenuFilho.addContent(imagemMenuFilho);
			tagMenuFilho.addContent(horizontalMenuFilho);

			tagMenuFilho = this.gerarTagMenu(menuFilho, tagMenuFilho , j);
			
			MenusInferiores.addContent(tagMenuFilho);

			
		}
		tagMenuInferior.addContent(MenusInferiores);
		}
		else{
			for (MenuDTO menuFilho : menusFilhos){
				Element tagMenuFilho = new Element("menu");

				Element nomeMenuFilho = new Element("nome");
				Element descricaoMenuFilho = new Element("descricao");
				Element ordemMenuFilho = new Element("ordem");
				Element menuRapidoFilho = new Element("menuRapido");
				Element linkMenuFilho = new Element("link");
				Element imagemMenuFilho = new Element("imagem");
				Element horizontalMenuFilho = new Element("horizontal");

				nomeMenuFilho.setText(menuFilho.getNome());
				descricaoMenuFilho.setText(menuFilho.getDescricao());
				ordemMenuFilho.setText(String.valueOf(menuFilho.getOrdem()));
				menuRapidoFilho.setText(String.valueOf(menuFilho.getMenuRapido()));
				linkMenuFilho.setText(menuFilho.getLink());
				imagemMenuFilho.setText(menuFilho.getImagem());
				horizontalMenuFilho.setText(menuFilho.getHorizontal());

				tagMenuFilho.addContent(nomeMenuFilho);
				tagMenuFilho.addContent(descricaoMenuFilho);
				tagMenuFilho.addContent(ordemMenuFilho);
				tagMenuFilho.addContent(menuRapidoFilho);
				tagMenuFilho.addContent(linkMenuFilho);
				tagMenuFilho.addContent(imagemMenuFilho);
				tagMenuFilho.addContent(horizontalMenuFilho);

				tagMenuFilho = this.gerarTagMenu(menuFilho, tagMenuFilho , j);
	
				tagMenuInferior.addContent(tagMenuFilho);
			}			
		}
		return tagMenuInferior;

	}

	public void atualizarMenuXml(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
		String separator = System.getProperty("file.separator");
		String diretorioReceita = CITCorporeUtil.caminho_real_app + "XMLs"  + separator;
		File file = new File(diretorioReceita + "menu.xml");
		menuService.gerarCarga(file);
		document.alert(UtilI18N.internacionaliza(request, "menu.atualizarXml"));
	}
}
