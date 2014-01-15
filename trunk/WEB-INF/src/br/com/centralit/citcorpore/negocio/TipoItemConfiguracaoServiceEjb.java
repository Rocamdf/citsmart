/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.CaracteristicaTipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.CaracteristicaTipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.TipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

/**
 * EJB de Tipo Item Configuração.
 * 
 * @author valdoilo.damasceno
 */
public class TipoItemConfiguracaoServiceEjb extends CrudServicePojoImpl implements TipoItemConfiguracaoService {

	private static final long serialVersionUID = -2979544729438061652L;

	/** DAO de CaracteristicaTipoItemConfiguracao. */
	private CaracteristicaTipoItemConfiguracaoDAO caracteristicaTipoItemConfiguracaoDao = new CaracteristicaTipoItemConfiguracaoDAO();

	/** Bean de TipoItemConfiguracao. */
	private TipoItemConfiguracaoDTO tipoItemConfiguracaoBean;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService#create (br.com.citframework.dto.IDto, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public IDto create(IDto tipoItemConfiguracao, HttpServletRequest request) throws ServiceException, LogicException {
		this.setTipoItemConfiguracaoBean(tipoItemConfiguracao);

		TransactionControler transactionControler = new TransactionControlerImpl(this.getTipoItemConfiguracaoDao().getAliasDB());
		try {
			this.validaCreate(this.getTipoItemConfiguracaoBean());
			this.getTipoItemConfiguracaoDao().setTransactionControler(transactionControler);
			this.getCaracteristicaTipoItemConfiguracaoDao().setTransactionControler(transactionControler);

			transactionControler.start();

			this.getTipoItemConfiguracaoBean().setDataInicio(UtilDatas.getDataAtual());
			this.getTipoItemConfiguracaoBean().setIdEmpresa(WebUtil.getIdEmpresa(request));
			this.setTipoItemConfiguracaoBean((TipoItemConfiguracaoDTO) this.getTipoItemConfiguracaoDao().create(tipoItemConfiguracao));

			this.criarEAssociarCaracteristicaAoTipoItemConfiguracao();
			transactionControler.commit();
			transactionControler.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		}
		return this.getTipoItemConfiguracaoBean();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.citframework.service.CrudServicePojoImpl#update(br.com.citframework .dto.IDto)
	 */
	@Override
	public void update(IDto tipoItemConfiguracao) throws ServiceException, LogicException {
		this.setTipoItemConfiguracaoBean(tipoItemConfiguracao);
		try {
			this.criarEAssociarCaracteristicaAoTipoItemConfiguracao();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.update(this.getTipoItemConfiguracaoBean());
	}

	/**
	 * Associa Característica ao Tipo Item Configuração.
	 * 
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	private void criarEAssociarCaracteristicaAoTipoItemConfiguracao() throws Exception {
		if (this.getTipoItemConfiguracaoBean().getCaracteristicas() != null && !this.getTipoItemConfiguracaoBean().getCaracteristicas().isEmpty()) {
			for (int i = 0; i < this.getTipoItemConfiguracaoBean().getCaracteristicas().size(); i++) {
				Integer idCaracteristica = ((CaracteristicaDTO) this.getTipoItemConfiguracaoBean().getCaracteristicas().get(i)).getIdCaracteristica();

				CaracteristicaTipoItemConfiguracaoDTO caracteristicaTipoItemConfiguracaoBean = new CaracteristicaTipoItemConfiguracaoDTO();

				if (!this.getCaracteristicaTipoItemConfiguracaoDao().existeAssociacaoComCaracteristica(idCaracteristica, this.getTipoItemConfiguracaoBean().getId())) {
					caracteristicaTipoItemConfiguracaoBean.setIdTipoItemConfiguracao(this.getTipoItemConfiguracaoBean().getId());
					caracteristicaTipoItemConfiguracaoBean.setIdCaracteristica(idCaracteristica);
					caracteristicaTipoItemConfiguracaoBean.setDataInicio(UtilDatas.getDataAtual());

					this.getCaracteristicaTipoItemConfiguracaoDao().create(caracteristicaTipoItemConfiguracaoBean);
				}
			}
		}
	}

	public void restaurarGridCaracteristicas(DocumentHTML document, Collection<CaracteristicaDTO> caracteristicas) {
		if (caracteristicas != null && !caracteristicas.isEmpty()) {
			int count = 0;
			document.executeScript("countCaracteristica = 0");
			for (CaracteristicaDTO caracteristicaBean : caracteristicas) {
				count++;

				document.executeScript("restoreRow()");
				document.executeScript("seqSelecionada = " + count);

				String caracteristica = (caracteristicaBean.getNome() != null ? caracteristicaBean.getNome() : "");
				String tag = (caracteristicaBean.getTag() != null ? caracteristicaBean.getTag() : "");
				String valor = (caracteristicaBean.getValorString() != null ? caracteristicaBean.getValorString() : "");
				String descricao = (caracteristicaBean.getDescricao() != null ? caracteristicaBean.getDescricao() : "");
				String idEmpresa = (caracteristicaBean.getIdEmpresa().toString() != null ? caracteristicaBean.getIdEmpresa().toString() : "");
				String dataInicio = (caracteristicaBean.getDataInicio().toString() != null ? caracteristicaBean.getDataInicio().toString() : "");
				String dataFim = (caracteristicaBean.getDataFim() != null ? caracteristicaBean.getDataFim().toString() : "");

				if (caracteristica != null) {
					caracteristica = caracteristica.replaceAll("'", "");
				}
				if (tag != null) {
					tag = tag.replaceAll("'", "");
				}
				if (valor != null) {
					valor = valor.replaceAll("'", "");
				}
				if (descricao != null) {
					descricao = descricao.replaceAll("'", "");
				}

				document.executeScript("setRestoreCaracteristica('" + caracteristicaBean.getIdCaracteristica() + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(caracteristica) + "',"
						+ "'" + br.com.citframework.util.WebUtil.codificaEnter(tag) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(valor) + "'," + "'"
						+ br.com.citframework.util.WebUtil.codificaEnter(descricao) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(idEmpresa) + "'," + "'"
						+ br.com.citframework.util.WebUtil.codificaEnter(dataInicio) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(dataFim) + "')");
			}
			document.executeScript("exibeGrid()");
		} else {
			document.executeScript("ocultaGrid()");
		}
	}

	public void restaurarGridCaracteristicasItemConfiguracaoFilho(DocumentHTML document, Collection<CaracteristicaDTO> caracteristicas) {
		if (caracteristicas != null && !caracteristicas.isEmpty()) {
			int count = 0;
			document.executeScript("countCaracteristica = 0");
			for (CaracteristicaDTO caracteristicaBean : caracteristicas) {
				count++;

				document.executeScript("restoreRowCaracteristicaItemFilho()");
				document.executeScript("seqSelecionada = " + count);

				String caracteristica = (caracteristicaBean.getNome() != null ? caracteristicaBean.getNome() : "");
				String tag = (caracteristicaBean.getTag() != null ? caracteristicaBean.getTag() : "");
				String valor = (caracteristicaBean.getValorString() != null ? caracteristicaBean.getValorString() : "");
				String descricao = (caracteristicaBean.getDescricao() != null ? caracteristicaBean.getDescricao() : "");
				String idEmpresa = (caracteristicaBean.getIdEmpresa().toString() != null ? caracteristicaBean.getIdEmpresa().toString() : "");
				String dataInicio = (caracteristicaBean.getDataInicio().toString() != null ? caracteristicaBean.getDataInicio().toString() : "");
				String dataFim = (caracteristicaBean.getDataFim() != null ? caracteristicaBean.getDataFim().toString() : "");

				if (caracteristica != null) {
					caracteristica = caracteristica.replaceAll("'", "");
				}
				if (tag != null) {
					tag = tag.replaceAll("'", "");
				}
				if (valor != null) {
					valor = valor.replaceAll("'", "");
				}
				if (descricao != null) {
					descricao = descricao.replaceAll("'", "");
				}

				document.executeScript("setRestoreCaracteristicaItemConfiguracaoFilho('" + caracteristicaBean.getIdCaracteristica() + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(caracteristica) + "',"
						+ "'" + br.com.citframework.util.WebUtil.codificaEnter(tag) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(valor) + "'," + "'"
						+ br.com.citframework.util.WebUtil.codificaEnter(descricao) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(idEmpresa) + "'," + "'"
						+ br.com.citframework.util.WebUtil.codificaEnter(dataInicio) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(dataFim) + "')");
			}
			document.executeScript("exibeGridPatrimonioItemFilho()");
		} else {
			document.executeScript("ocultaGridPatrimonioItemFilho()");
		}
	}

	/**
	 * @return valor do atributo caracteristicaTipoItemConfiguracaoDao.
	 * @author valdoilo.damasceno
	 */
	public CaracteristicaTipoItemConfiguracaoDAO getCaracteristicaTipoItemConfiguracaoDao() {
		return caracteristicaTipoItemConfiguracaoDao;
	}

	/**
	 * Retorna DAO de TipoItemConfiguracao.
	 * 
	 * @return TipoItemConfiguracaoDAO
	 * @throws ServiceException
	 * @author VMD
	 */
	public TipoItemConfiguracaoDAO getTipoItemConfiguracaoDao() throws ServiceException {
		return (TipoItemConfiguracaoDAO) getDao();
	}

	/**
	 * Retorna Tipo Item Configuração.
	 * 
	 * @return TipoItemConfiguracaoDTO
	 * @author VMD
	 */
	public TipoItemConfiguracaoDTO getTipoItemConfiguracaoBean() {
		return this.tipoItemConfiguracaoBean;
	}

	/**
	 * Configura Tipo Item Configuração.
	 * 
	 * @param tipoItemConfiguracao
	 *            IDto
	 * @author VMD
	 */
	public void setTipoItemConfiguracaoBean(IDto tipoItemConfiguracao) {
		this.tipoItemConfiguracaoBean = (TipoItemConfiguracaoDTO) tipoItemConfiguracao;
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new TipoItemConfiguracaoDAO();
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

	@Override
	public boolean verificarSeTipoItemConfiguracaoExiste(TipoItemConfiguracaoDTO tipoItemConfiguracao) throws PersistenceException {
		TipoItemConfiguracaoDAO tipoItemConfiguracaoDao = new TipoItemConfiguracaoDAO();
		return tipoItemConfiguracaoDao.verificarSeTipoItemConfiguracaoExiste(tipoItemConfiguracao);
	}

}
