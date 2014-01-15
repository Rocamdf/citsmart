package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ComplexidadeDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.integracao.ClienteDao;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.integracao.FornecedorDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ContratoServiceEjb extends CrudServicePojoImpl implements ContratoService {

	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		return new ContratoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdCliente(Integer parm) throws Exception {
		ContratoDao dao = new ContratoDao();
		try {
			return dao.findByIdCliente(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdFornecedor(Integer parm) throws Exception {
		ContratoDao dao = new ContratoDao();
		try {
			return dao.findByIdFornecedor(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdCliente(Integer parm) throws Exception {
		ContratoDao dao = new ContratoDao();
		try {
			dao.deleteByIdCliente(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection findByIdContrato(Integer parm) throws Exception {
		ContratoDao dao = new ContratoDao();
		try {
			return dao.findByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<ComplexidadeDTO> listaComplexidadePorContrato(Integer idServicoContrato) throws Exception {
		ContratoDao dao = new ContratoDao();
		return dao.listaComplexidadePorContrato(idServicoContrato);
	}

	public Collection listByIdAcordoNivelServicoAndTipo(Integer idAcordoNivelServicoParm, String tipoParm) throws Exception {
		ContratoDao dao = new ContratoDao();
		return dao.listByIdAcordoNivelServicoAndTipo(idAcordoNivelServicoParm, tipoParm);
	}

	@Override
	public Collection<ContratoDTO> listAtivos() throws Exception {

		ContratoDao contratosDao = new ContratoDao();

		return contratosDao.listAtivos();
	}

	@Override
	public Collection findByIdGrupo(Integer idGrupo) throws Exception {
		ContratoDao contratoDao = new ContratoDao();
		return contratoDao.findByIdGrupo(idGrupo);
	}

	public Collection<ContratoDTO> findAtivosByGrupos(Collection<GrupoDTO> listGrupoDto) throws Exception {
		ContratoDao contratoDao = new ContratoDao();

		return contratoDao.findAtivosByGrupos(listGrupoDto);
	}

	@Override
	public Collection<ContratoDTO> findAtivosByIdEmpregado(Integer idEmpregado) throws ServiceException, Exception {

		ContratoDao contratoDao = new ContratoDao();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		try {

			Collection<GrupoDTO> listGrupoDto = (Collection<GrupoDTO>) grupoService.getGruposByPessoa(idEmpregado);

			return contratoDao.findAtivosByGrupos(listGrupoDto);

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	@Override
	public String verificaIdCliente(HashMap mapFields) throws Exception {
		ClienteDao clienteDao = new ClienteDao();
		List<ClienteDTO> listaCliente = null;
		String id = mapFields.get("IDCLIENTE").toString().trim();
		if ((id==null)||(id.equals(""))){
			id="0";
		}
		if (UtilStrings.soContemNumeros(id)) {
			Integer idCliente;
			idCliente = (Integer) Integer.parseInt(id);
			listaCliente = clienteDao.findByIdCliente(idCliente);
		} else {
			listaCliente = clienteDao.findByRazaoSocial(id);
		}
		if((listaCliente != null)&&(listaCliente.size()>0)){
			return String.valueOf(listaCliente.get(0).getIdCliente());
		}else{
			return "0";
		}
	}
	
	@Override
	public String verificaIdFornecedor(HashMap mapFields) throws Exception {
		FornecedorDao fornecedorDao = new FornecedorDao();
		List<FornecedorDTO> listaFornecedor = null;
		String id = mapFields.get("IDFORNECEDOR").toString().trim();
		if ((id==null)||(id.equals(""))){
			id="0";
		}
		if (UtilStrings.soContemNumeros(id)) {
			Integer idFornecedor = (Integer) Integer.parseInt(id);
			listaFornecedor = fornecedorDao.findByIdFornecedor(idFornecedor);
		} else {
			listaFornecedor = fornecedorDao.findByRazaoSocial(id);
		}
		if((listaFornecedor != null)&&(listaFornecedor.size()>0)){
			return String.valueOf(listaFornecedor.get(0).getIdFornecedor());
		}else{
			return "0";
		}
	}	
}