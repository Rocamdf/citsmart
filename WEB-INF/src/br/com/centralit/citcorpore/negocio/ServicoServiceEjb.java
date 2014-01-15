package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.integracao.CategoriaServicoDao;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes","unchecked"})
public class ServicoServiceEjb extends CrudServicePojoImpl implements ServicoService {

	private static final long serialVersionUID = -8991891853124280580L;

	protected CrudDAO getDao() throws ServiceException {
		return new ServicoDao();
	}

	private ServicoDao getServicoDao() throws ServiceException {
		return (ServicoDao) getDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdCategoriaServico(Integer parm) throws Exception {
		ServicoDao dao = new ServicoDao();
		try {
			return dao.findByIdCategoriaServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdCategoriaServico(Integer parm) throws Exception {
		ServicoDao dao = new ServicoDao();
		try {
			dao.deleteByIdCategoriaServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdSituacaoServico(Integer parm) throws Exception {
		ServicoDao dao = new ServicoDao();
		try {
			return dao.findByIdSituacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdSituacaoServico(Integer parm) throws Exception {
		ServicoDao dao = new ServicoDao();
		try {
			dao.deleteByIdSituacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection findByIdTipoDemandaAndIdCategoria(Integer idTipoDemanda, Integer idCategoria) throws Exception {
		ServicoDao dao = new ServicoDao();
		try {
			return dao.findByIdTipoDemandaAndIdCategoria(idTipoDemanda, idCategoria);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdTipoDemandaAndIdContrato(Integer idTipoDemanda, Integer idContrato, Integer idCategoria) throws Exception {
		ServicoDao dao = new ServicoDao();
		try {
			return dao.findByIdTipoDemandaAndIdContrato(idTipoDemanda, idContrato, idCategoria);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public String retornarSiglaPorIdOs(Integer idOs) throws Exception {
		ServicoDao servicoDao = new ServicoDao();
		return servicoDao.retornaSiglaPorIdOs(idOs);
	}

	@Override
	public Collection findByIdServicoAndIdTipoDemandaAndIdCategoria(Integer idServico, Integer idTipoDemanda, Integer idCategoria) throws Exception {
		ServicoDao dao = new ServicoDao();
		try {
			return dao.findByIdServicoAndIdTipoDemandaAndIdCategoria(idServico, idTipoDemanda, idCategoria);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	
	public ServicoDTO restoreByIdServico(Integer idServico) {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idServico", "=", idServico));

		ArrayList<ServicoDTO> retorno;
		try {
			retorno = (ArrayList<ServicoDTO>) getServicoDao().findByCondition(condicoes, null);
			if (retorno != null) {
				return retorno.get(0);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Collection<ServicoDTO> findByServico(Integer idServico) throws Exception {
		ServicoDao servicoDao = new ServicoDao();
		return servicoDao.findByServico(idServico);
	}

	@Override
	public Collection<ServicoDTO> findByServico(Integer idServico, String nome) throws Exception {
		ServicoDao servicoDao = new ServicoDao();
		return servicoDao.findByServico(idServico, nome);
	}

	@Override
	public Collection<ServicoDTO> listaQuantidadeServicoAnalitico(ServicoDTO servicoDTO) throws Exception {
		ServicoDao dao = new ServicoDao();
		return dao.listaQuantidadeServicoAnalitico(servicoDTO);
	}

	@Override
	public ServicoDTO findByIdServico(Integer idServico) throws Exception {
		ServicoDao dao = new ServicoDao();
		return dao.findByIdServico(idServico);
	}

	@Override
	public Collection<ServicoDTO> listAtivos() throws Exception {
		ServicoDao dao = new ServicoDao();
		return dao.listAtivos();
	}
	
	@Override
	public void desvincularServicosRelacionadosTemplate(Integer idTemplate) throws Exception {
		ServicoDao servicoDao = new ServicoDao();
		TransactionControler transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());
		servicoDao.setTransactionControler(transactionControler);

		Collection<ServicoDTO> servicos = servicoDao.findByIdTemplate(idTemplate);

		if (servicos != null && !servicos.isEmpty()) {
			transactionControler.start();
			for (ServicoDTO servico : servicos) {
				if (servico.getIdTemplateAcompanhamento() != null && servico.getIdTemplateAcompanhamento().intValue() == idTemplate.intValue()) {
					servico.setIdTemplateAcompanhamento(null);
				}
				if (servico.getIdTemplateSolicitacao() != null && servico.getIdTemplateSolicitacao().intValue() == idTemplate.intValue()) {
					servico.setIdTemplateSolicitacao(null);
				}
				servicoDao.update(servico);
			}
			transactionControler.commit();
			transactionControler.close();
		}
	}
	
	@Override
	public Collection findByNomeAndContratoAndTipoDemandaAndCategoria(
			Integer idTipoDemanda, Integer idContrato, Integer idCategoria,
			String nome) throws Exception {
		ServicoDao dao = new ServicoDao();
		return dao.findByNomeAndContratoAndTipoDemandaAndCategoria(idTipoDemanda, idContrato, idCategoria, nome);
	}

	@Override
	public ServicoDTO findById(Integer idServico) throws Exception {
		ServicoDao dao = new ServicoDao();
		return dao.findById(idServico);
	}
	
	@Override
	public String verificaIdCategoriaServico(HashMap mapFields) throws Exception {
		CategoriaServicoDao categoriaServicoDao = new CategoriaServicoDao();
		List<CategoriaServicoDTO> listaCategoriaServicoDTO = null;
		String id = mapFields.get("IDCATEGORIASERVICO").toString().trim();
		if ((id==null)||(id.equals(""))){
			id="0";
		}		
		if (UtilStrings.soContemNumeros(id)) {
			Integer idCategoriaServico = (Integer) Integer.parseInt(id);
			listaCategoriaServicoDTO = categoriaServicoDao.findByIdCategoriaServico(idCategoriaServico);
		} else {
			listaCategoriaServicoDTO = categoriaServicoDao.findByNomeCategoria(id);
		}
		if((listaCategoriaServicoDTO != null)&&(listaCategoriaServicoDTO.size()>0)){
			return String.valueOf(listaCategoriaServicoDTO.get(0).getIdCategoriaServico());
		}else{
			return "0";
		}
	}	
}