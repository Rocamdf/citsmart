/**
 * CentralIT - CITCorpore.
 * 
 * @author CentralIT
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.CaracteristicaDao;
import br.com.centralit.citcorpore.integracao.CaracteristicaTipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * EJB de Característica Service.
 * 
 * @author valdoilo.damasceno
 * 
 */
public class CaracteristicaServiceEjb extends CrudServicePojoImpl implements CaracteristicaService {

	private static final long serialVersionUID = 3495723561939373579L;

	@Override
	public void create(CaracteristicaDTO caracteristica, HttpServletRequest request) throws ServiceException, LogicException {
		/*
		 * Retirado da rotina para validação da pink elephant caracteristica.setSistema("N");
		 * Acrescentado novamente por apresentar erro ao gravar característica. No Oracle, campo não pode ser Null.
		 */
		caracteristica.setSistema("N");
		caracteristica.setNome(caracteristica.getNome().replaceAll("[<>]", ""));
		caracteristica.setTag(caracteristica.getTag().replaceAll("[<>]", ""));
		caracteristica.setDataInicio(UtilDatas.getDataAtual());
		caracteristica.setTipo("");
		caracteristica.setIdEmpresa(WebUtil.getIdEmpresa(request));
		super.create(caracteristica);
	}

	@Override
	public void excluirCaracteristica(CaracteristicaDTO caracteristica) throws ServiceException, Exception {
		if (this.getCaracteristicaTipoItemConfiguracaoDao().existeAssociacaoComCaracteristica(caracteristica.getIdCaracteristica(), null)) {
			throw new LogicException("Característica não pode ser excluída!");
		} else {
			caracteristica.setDataFim(UtilDatas.getDataAtual());
			super.update(caracteristica);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<CaracteristicaDTO> consultarCaracteristicasAtivas(Integer idTipoItemConfiguracao) throws ServiceException {
		try {
			return (List) this.getCaracteristicaDao().consultarCaracteristicasAtivas(idTipoItemConfiguracao);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<CaracteristicaDTO> consultarCaracteristicasAtivas(Integer idTipoItemConfiguracao, String [] arrCaracteristicas) throws ServiceException {
		try {
			return (List) this.getCaracteristicaDao().consultarCaracteristicasAtivas(idTipoItemConfiguracao, arrCaracteristicas);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.centralit.citcorpore.negocio.CaracteristicaService# consultarCaracteristicasComValores(java.lang.Integer, java.lang.Integer)
	 */
	public Collection<CaracteristicaDTO> consultarCaracteristicasComValores(Integer idTipoItemConfiguracao, Integer idBaseItemConfiguracao) throws LogicException, ServiceException, Exception {
		Collection<CaracteristicaDTO> caracteristicas = this.consultarCaracteristicasAtivas(idTipoItemConfiguracao);

		for (CaracteristicaDTO caracteristica : caracteristicas) {
			ValorDTO valor = this.getValorService().restore(idBaseItemConfiguracao, caracteristica.getIdCaracteristica());

			if (valor != null && valor.getValorStr() != null) {
				caracteristica.setValorString(valor.getValorStr());
			}
		}
		return caracteristicas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.centralit.citcorpore.negocio.CaracteristicaService# consultarCaracteristicasComValores(java.lang.Integer, java.lang.Integer)
	 */
	public Collection<CaracteristicaDTO> consultarCaracteristicasComValoresItemConfiguracao(Integer idTipoItemConfiguracao, Integer idItemConfiguracao) throws LogicException, ServiceException, Exception {
		Collection<CaracteristicaDTO> caracteristicas = this.consultarCaracteristicasAtivas(idTipoItemConfiguracao);
		if (caracteristicas != null) {
			for (CaracteristicaDTO caracteristica : caracteristicas) {
				ValorDTO valor = this.getValorService().restoreItemConfiguracao(idItemConfiguracao, caracteristica.getIdCaracteristica());

				if (valor != null && valor.getValorStr() != null) {
					caracteristica.setValorString(valor.getValorStr());
				}
			}
		}
		return caracteristicas;
	}
	
	public Collection<CaracteristicaDTO> consultarCaracteristicasComValoresItemConfiguracao(Integer idTipoItemConfiguracao, Integer idItemConfiguracao, String [] arr) throws LogicException, ServiceException, Exception {
		Collection<CaracteristicaDTO> caracteristicas = this.consultarCaracteristicasAtivas(idTipoItemConfiguracao,arr);
		if (caracteristicas != null) {
			for (CaracteristicaDTO caracteristica : caracteristicas) {
				ValorDTO valor = this.getValorService().restoreItemConfiguracao(idItemConfiguracao, caracteristica.getIdCaracteristica());

				if (valor != null && valor.getValorStr() != null) {
					caracteristica.setValorString(valor.getValorStr());
				}
			}
		}
		return caracteristicas;
	}

	/**
	 * Retorna Service de Valor.
	 * 
	 * @return ValorService
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public ValorService getValorService() throws ServiceException, Exception {
		return (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
	}

	/**
	 * Retorna DAO de CaracteristicaTipoItemConfiguracao.
	 * 
	 * @return CaracteristicaTipoItemConfiguracaoDAO
	 * @author VMD
	 */
	public CaracteristicaTipoItemConfiguracaoDAO getCaracteristicaTipoItemConfiguracaoDao() {
		return new CaracteristicaTipoItemConfiguracaoDAO();
	}

	/**
	 * Retorna DAO de Caracteristica.
	 * 
	 * @return CaracteristicaDao
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	public CaracteristicaDao getCaracteristicaDao() throws ServiceException {
		return (CaracteristicaDao) getDao();
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new CaracteristicaDao();
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
	public boolean verificarSeCaracteristicaExiste(CaracteristicaDTO caracteristica) throws PersistenceException {
		CaracteristicaDao dao = new CaracteristicaDao();
		return dao.verificarSeCaracteristicaExiste(caracteristica);
	}

}
