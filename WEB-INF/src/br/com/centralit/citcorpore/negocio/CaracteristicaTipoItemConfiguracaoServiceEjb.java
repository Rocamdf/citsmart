/**
 * CentralIT - CITSmart.
 * 
 * @author valdoilo.damasceno
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.integracao.CaracteristicaTipoItemConfiguracaoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;

/**
 * EJB de CaracteristicaTipoItemConfiguracao.
 * 
 * @author valdoilo.damasceno
 */
public class CaracteristicaTipoItemConfiguracaoServiceEjb extends CrudServicePojoImpl implements CaracteristicaTipoItemConfiguracaoService {

    private static final long serialVersionUID = -1281162394841330885L;

    /*
     * (non-Javadoc)
     * 
     * @see
     * br.com.centralit.citcorpore.negocio.CaracteristicaTipoItemConfiguracaoService
     * #excluirAssociacaoCaracteristicaTipoItemConfiguracao(java.lang.Integer,
     * java.lang.Integer)
     */
    @Override
    public void excluirAssociacaoCaracteristicaTipoItemConfiguracao(Integer idTipoItemConfiguracao, Integer idCaracteristica) throws Exception {
	if (idCaracteristica != null && idCaracteristica.intValue() != 0) {
	    this.getCaracteristicaTipoItemConfiguracaoDao().excluirAssociacaoCaracteristicaTipoItemConfiguracao(idTipoItemConfiguracao,
		    idCaracteristica);
	} else {
	    Collection<CaracteristicaDTO> caracteristicas = this.getCaracteristicaService().consultarCaracteristicasAtivas(idTipoItemConfiguracao);

	    if (caracteristicas != null && !caracteristicas.isEmpty()) {
		for (CaracteristicaDTO caracteristica : caracteristicas) {
		    this.getCaracteristicaTipoItemConfiguracaoDao().excluirAssociacaoCaracteristicaTipoItemConfiguracao(idTipoItemConfiguracao,
			    caracteristica.getIdCaracteristica());
		}
	    }
	}
    }

    /**
     * Retorna Service de Caracteristica.
     * 
     * @return CaracteristicaService
     * @throws ServiceException
     * @throws Exception
     */
    public CaracteristicaService getCaracteristicaService() throws ServiceException, Exception {
	return (CaracteristicaService) ServiceLocator.getInstance().getService(CaracteristicaService.class, null);
    }

    public CaracteristicaTipoItemConfiguracaoDAO getCaracteristicaTipoItemConfiguracaoDao() throws ServiceException {
	return (CaracteristicaTipoItemConfiguracaoDAO) this.getDao();
    }

    @Override
    protected CrudDAO getDao() throws ServiceException {
	return new CaracteristicaTipoItemConfiguracaoDAO();
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

}
