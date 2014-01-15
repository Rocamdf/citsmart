package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import br.com.centralit.citcorpore.bean.DicionarioDTO;
import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.centralit.citcorpore.integracao.DicionarioDao;
import br.com.centralit.citcorpore.integracao.LinguaDao;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.Mensagens;

@SuppressWarnings({ "serial", "unchecked" })
public class DicionarioServiceEjb extends CrudServicePojoImpl implements DicionarioService {

	private static Properties props = null;
	private static InputStream inputStreamSettedInLoad = null;
	private static String fileName = "";

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new DicionarioDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {

	}

	@Override
	protected void validaFind(Object obj) throws Exception {

	}

	public void criarMensagensNovos(HttpServletRequest request, String locale, Integer idLingua) throws Exception {

		DicionarioDao dicionarioDao = new DicionarioDao();
		TransactionControler tc = new TransactionControlerImpl(dicionarioDao.getAliasDB());
		try {
			dicionarioDao.setTransactionControler(tc);
			tc.start();
			try {
				if (locale == null || locale.trim().equalsIgnoreCase("")) {
					// Codigo removido pois quando a sessão locale está em ingles o metodo faz a leitura do arquivo errado
					/*
					 * if (request != null && request.getSession().getAttribute("locale") != null && !request.getSession().getAttribute("locale").equals("")) { locale = (String)
					 * request.getSession().getAttribute("locale"); fileName = "Mensagens_" + locale.trim().toLowerCase() + ".properties"; } else {
					 */
					fileName = "Mensagens.properties";
					/* } */
				} else {
					fileName = "Mensagens_" + locale.trim().toLowerCase() + ".properties";
				}

				props = new Properties();
				ClassLoader load = Mensagens.class.getClassLoader();
				InputStream is = load.getResourceAsStream(fileName);
				if (is == null) {
					is = ClassLoader.getSystemResourceAsStream(fileName);
				}
				if (is == null) {
					is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
				}
				try {
					if (is == null) {
						is = inputStreamSettedInLoad;
					}
					if (is != null) {
						props.load(is);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (SecurityException e1) {
				e1.printStackTrace();
			}

			Set<String> keys = props.stringPropertyNames();
			int count = 0;
			for (String key : keys) {
				count++;
				String msg = "";

				if (props.getProperty(key.trim()) != null && !StringUtils.isEmpty(props.getProperty(key.trim()))) {

					if (StringUtils.contains(props.getProperty(key.trim()), "\\\"")) {

						msg = props.getProperty(key.trim());

					} else {

						if (StringUtils.contains(props.getProperty(key.trim()), "\"")) {

							msg = StringUtils.replace(props.getProperty(key.trim()), "\"", "\\\"");

						} else {

							msg = props.getProperty(key.trim());

						}
					}
//Mário Junior 18/11/2013 -  Comentado pois ele trata o aspas simples, e não é necessário, apenas aspas duplas, estava inserindo uma barra quando tinha aspas simples.				
//					if (StringUtils.contains(props.getProperty(key.trim()), "\\'")) {
//
//						msg = props.getProperty(key.trim());
//
//					} else {
//
//						if (StringUtils.contains(props.getProperty(key.trim()), "'")) {
//
//							msg = StringUtils.replace(props.getProperty(key.trim()), "'", "\\'");
//
//						} else {
//
//							msg = props.getProperty(key.trim());
//
//						}
//					}

				} else {

					msg = props.getProperty(key.trim());
				}

				LinguaDao linguaDao = new LinguaDao();

				DicionarioDTO dicionarioDto = new DicionarioDTO();

				List<LinguaDTO> listaLinguas = new ArrayList<LinguaDTO>();

				listaLinguas = (List<LinguaDTO>) linguaDao.list();

				if (listaLinguas != null && listaLinguas.size() != 0) {

					for (LinguaDTO objLing : listaLinguas) {

						dicionarioDto.setNome(key);
						dicionarioDto.setValor(msg);

						if (!objLing.getSigla().equalsIgnoreCase("PT")) {
							dicionarioDto.setIdLingua(objLing.getIdLingua());
							dicionarioDto = dicionarioDao.verificarDicionarioAtivoByKey(dicionarioDto);
							if (dicionarioDto == null) {
								dicionarioDto = new DicionarioDTO();
								dicionarioDto.setNome(key);
								dicionarioDto.setValor(msg);
							}
						}

						if (objLing.getIdLingua().intValue() == idLingua.intValue()) {
							dicionarioDto.setIdLingua(objLing.getIdLingua());
							dicionarioDto.setIdDicionario(null);
							Integer idDicionario = dicionarioDao.verificarDicionarioAtivo(dicionarioDto);

							try {
								if (idDicionario != null) {
									dicionarioDto.setIdDicionario(idDicionario);
									dicionarioDto.setValor(msg);
									dicionarioDao.update(dicionarioDto);
									continue;
								} else {
									dicionarioDao.create(dicionarioDto);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					}
				}
				if (count >= 100) {
					tc.commit();
					count = 0;
				}
			}
			tc.commit();
			tc.close();
			tc = null;

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			e.printStackTrace();

		} finally {
			UtilI18N.resetar();
		}

	}

	@Override
	public Collection<DicionarioDTO> listaDicionario(DicionarioDTO dicionarioDto) throws Exception {
		DicionarioDao dao = new DicionarioDao();
		return dao.listaDicionario(dicionarioDto);
	}

	@Override
	public void gerarCarga(File file) throws Exception {

		DicionarioDao dicionarioDao = new DicionarioDao();

		try {

			SAXBuilder sb = new SAXBuilder();

			Document doc = sb.build(file);

			Element elements = doc.getRootElement();

			List<Element> dicionarioSuperior = elements.getChild("dicionario").getChildren();

			for (Element dicionarioElement : dicionarioSuperior) {

				DicionarioDTO dicionarioDto = new DicionarioDTO();

				dicionarioDto.setIdLingua(Integer.parseInt(dicionarioElement.getChildText("idLingua")));
				dicionarioDto.setNome(dicionarioElement.getChildText("key"));
				dicionarioDto.setValor(dicionarioElement.getChildText("valor"));

				if (dicionarioDto.getIdDicionario() == null) {
					Integer idDicionario = dicionarioDao.verificarDicionarioAtivo(dicionarioDto);
					try {
						if (idDicionario == null) {
							dicionarioDao.create(dicionarioDto);
						} else {
							dicionarioDto.setIdDicionario(idDicionario);
							dicionarioDao.update(dicionarioDto);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@Override
	public DicionarioDTO verificarDicionarioAtivoByKey(DicionarioDTO obj) throws Exception {
		DicionarioDao dicionarioDao = new DicionarioDao();

		return dicionarioDao.verificarDicionarioAtivoByKey(obj);
	}

}
