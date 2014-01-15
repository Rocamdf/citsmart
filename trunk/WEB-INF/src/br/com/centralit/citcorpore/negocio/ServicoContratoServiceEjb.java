package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.AtividadesServicoContratoDao;
import br.com.centralit.citcorpore.integracao.FluxoServicoDao;
import br.com.centralit.citcorpore.integracao.ResultadosEsperadosDAO;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.ValorAjusteGlosaDAO;
import br.com.centralit.citcorpore.integracao.ValoresServicoContratoDao;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("rawtypes")
public class ServicoContratoServiceEjb extends CrudServicePojoImpl implements ServicoContratoService {

	private static final long serialVersionUID = 4803335429437181213L;

	protected CrudDAO getDao() throws ServiceException {
		return new ServicoContratoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) model;
		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
		FluxoServicoDao fluxoServicoDao = new FluxoServicoDao(); 
		TransactionControler tc = new TransactionControlerImpl(servicoContratoDao.getAliasDB());
		try {
			fluxoServicoDao.setTransactionControler(tc);
			tc.start();
			List<FluxoServicoDTO> listaFluxo =  servicoContratoDTO.getListaFluxo();
			servicoContratoDTO = (ServicoContratoDTO) servicoContratoDao.create(servicoContratoDTO);
			if (listaFluxo != null) {
				fluxoServicoDao.deleteByIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
				for (FluxoServicoDTO fluxoServicoDTO : listaFluxo) {
					fluxoServicoDTO.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
					fluxoServicoDTO.setDeleted(null);
					fluxoServicoDao.create(fluxoServicoDTO);
					/*if (fluxoServicoDao.validarFluxoServico(fluxoServicoDTO)) {
						fluxoServicoDao.create(fluxoServicoDTO);
					}*/
				}
			}			 
			 tc.commit();
	         tc.close();
	         tc = null;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return servicoContratoDTO;
	}

	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) model;
		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
		FluxoServicoDao fluxoServicoDao = new FluxoServicoDao(); 
		TransactionControler tc = new TransactionControlerImpl(servicoContratoDao.getAliasDB());
		try {
			fluxoServicoDao.setTransactionControler(tc);
			tc.start();
			List<FluxoServicoDTO> listaFluxo =  servicoContratoDTO.getListaFluxo();
			servicoContratoDao.update(servicoContratoDTO);
			if (listaFluxo != null) {
				fluxoServicoDao.deleteByIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
				for (FluxoServicoDTO fluxoServicoDTO : listaFluxo) {
					fluxoServicoDTO.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
					fluxoServicoDTO.setDeleted(null);
					fluxoServicoDao.create(fluxoServicoDTO);
					/*if (fluxoServicoDao.validarFluxoServico(fluxoServicoDTO)) {
						fluxoServicoDao.create(fluxoServicoDTO);
					}*/
				}
			}			 
			 tc.commit();
	         tc.close();
	         tc = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Collection findByIdServico(Integer parm) throws Exception {
		ServicoContratoDao dao = new ServicoContratoDao();
		try {
			return dao.findByIdServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdServico(Integer parm) throws Exception {
		ServicoContratoDao dao = new ServicoContratoDao();
		try {
			dao.deleteByIdServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deletarByIdServicoContrato(IDto model, DocumentHTML document) throws ServiceException, Exception {
		ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) model;
		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
		AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
		AtividadesServicoContratoDao atividadesServicoContratoDao = new AtividadesServicoContratoDao();
		ValoresServicoContratoDao valoresServicoContratoDao = new ValoresServicoContratoDao();
		ResultadosEsperadosDAO resultadosEsperadosDAO = new ResultadosEsperadosDAO();
		ValorAjusteGlosaDAO valorAjusteGlosaDAO = new ValorAjusteGlosaDAO();
		try {
			validaUpdate(model);
			servicoContratoDao.setTransactionControler(tc);
			acordoNivelServicoDao.setTransactionControler(tc);
			atividadesServicoContratoDao.setTransactionControler(tc);
			valoresServicoContratoDao.setTransactionControler(tc);
			resultadosEsperadosDAO.setTransactionControler(tc);
			valorAjusteGlosaDAO.setTransactionControler(tc);
			tc.start();
			servicoContratoDTO.setDataFim(UtilDatas.getDataAtual());
			valorAjusteGlosaDAO.updateValorAjusteGlosa(servicoContratoDTO.getIdServicoContrato());
			resultadosEsperadosDAO.updateResultadosEsperados(servicoContratoDTO.getIdServicoContrato());
			valoresServicoContratoDao.updateValoresServicoContrato(servicoContratoDTO.getIdServicoContrato(), servicoContratoDTO.getDataFim());
			acordoNivelServicoDao.updateAcordoNivelServico(servicoContratoDTO.getIdServicoContrato(), servicoContratoDTO.getDataFim());
			atividadesServicoContratoDao.updateAtividadesServicoContrato(servicoContratoDTO.getIdServicoContrato());
			servicoContratoDao.updateServicoContrato(servicoContratoDTO.getIdServicoContrato(), servicoContratoDTO.getDataFim());
			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
	}
	
	public Collection findByIdContrato(Integer parm) throws Exception {
		ServicoContratoDao dao = new ServicoContratoDao();
		try {
			return dao.findByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public Collection findByIdContratoPaginada(ServicoContratoDTO servicoContratoDTO, String paginacao, Integer pagAtual, Integer pagAtualAux, Integer totalPag, Integer quantidadePaginator, String campoPesquisa) throws Exception {
		ServicoContratoDao dao = new ServicoContratoDao();
		try {
			return dao.findByIdContratoPaginada(servicoContratoDTO, paginacao, pagAtual, pagAtualAux, totalPag, quantidadePaginator, campoPesquisa);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdContrato(Integer parm) throws Exception {
		ServicoContratoDao dao = new ServicoContratoDao();
		try {
			dao.deleteByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public ServicoContratoDTO findByIdContratoAndIdServico(Integer idContrato, Integer idServico) throws Exception {
		ServicoContratoDao dao = new ServicoContratoDao();
		try {
			return dao.findByIdContratoAndIdServico(idContrato, idServico);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection findByIdContratoDistinct(Integer idContrato) throws Exception {
		ServicoContratoDao dao = new ServicoContratoDao();
		try {
			return dao.findByIdContratoDistinct(idContrato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void setDataFim(HashMap map) throws Exception {

		String idServicoCon = (String) map.get("IDSERVICOCONTRATO");
		Integer idServicoContrato = new Integer(0);
		if (idServicoCon != null && !StringUtils.trim(idServicoCon).isEmpty()) {
			idServicoContrato = Integer.parseInt(idServicoCon);
		}

		ServicoContratoDao dao = new ServicoContratoDao();
		try {
			if (idServicoContrato != null && idServicoContrato != 0) {
				dao.setDataFim(idServicoContrato);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public Collection listarServicosPorFornecedor(Integer idFornecedor) throws Exception {
		ServicoContratoDao dao = new ServicoContratoDao();
		try {
			return dao.findByIdFornecedor(idFornecedor);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public boolean validaServicoContrato(HashMap mapFields) throws Exception {
		
		//Pegando valores do parâmetro recebido
		String idContratoTxt = (String) mapFields.get("IDCONTRATO");
		String idServicoTxt = (String) mapFields.get("IDSERVICO");
		
		Integer idContrato = null; 
		Integer idServico = null; 
		
		if(idContratoTxt != null && !idContratoTxt.equals("")){
			idContrato = Integer.parseInt(idContratoTxt);
		}
		
		if(idServicoTxt != null && !idServicoTxt.equals("")){
			idServico = Integer.parseInt(idServicoTxt);
		}
		
		if(idContrato != null && idServico != null){
			ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
			return servicoContratoDao.validaServicoContrato(idContrato, idServico);
		}
		return false;
	}
	
	@Override
	public Collection findServicoContratoByIdContrato(Integer idContrato) throws Exception {
		ServicoContratoDao dao = new ServicoContratoDao();
		try {
			return dao.findServicoContratoByIdContrato(idContrato);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public ServicoContratoDTO findByIdServicoContrato(Integer idServico, Integer idContrato) throws Exception {
		ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
		return servicoContratoDao.findByIdServicoContrato(idServico, idContrato);
	}
	
	@Override
	public boolean pesquisaServicosVinculados(DocumentHTML document, HashMap map, HttpServletRequest request) throws Exception{
		
		String idContratoStr = (String) map.get("IDCONTRATO");
		
		/* Desenvolvedor: Rodrigo Pecci - Data: 03/11/2013 - Horário: 16h20min
		 * Motivo/Comentário: Foi adicionada a validação para garantir que o id do contrato existe 
		 */
		if (idContratoStr == null || idContratoStr.equals("")) {
			document.alert(UtilI18N.internacionaliza(request, "dinamicview.nenhumRegistroSelecionado"));
			return false;
		}
		
		Integer idContrato = Integer.parseInt(idContratoStr);
	
		ServicoContratoDao dao = new ServicoContratoDao();
		Collection<ServicoContratoDTO> colecaoServicosVinculados = dao.findByIdContrato(idContrato);
		
		if(colecaoServicosVinculados == null || colecaoServicosVinculados.isEmpty()){
			return true;
		} else{
			document.alert(UtilI18N.internacionaliza(request, "dinamicview.existem") +" "+ colecaoServicosVinculados.size() + " "+  UtilI18N.internacionaliza(request, "dinamicview.servicosvinculados"));
			return false;
		}

	}
}
