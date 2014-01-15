/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.integracao.ParametroCorporeDAO;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

/**
 * @author valdoilo.damasceno
 * 
 */
@SuppressWarnings("unchecked")
public class ParametroCorporeServiceEjb extends CrudServicePojoImpl implements ParametroCorporeService {

	private static final long serialVersionUID = 682163869846846784L;

	public List<ParametroCorporeDTO> pesquisarParamentro(Integer id, String nomeParametro) throws ServiceException, LogicException, Exception {
		try {
			ParametroCorporeDAO parametroCorporeDAO = new ParametroCorporeDAO();
			return (List<ParametroCorporeDTO>) parametroCorporeDAO.pesquisarParamentro(id, nomeParametro);
		} catch (Exception e) {
			return null;
		}
	}

	public ParametroCorporeDTO getParamentroAtivo(Integer id) throws Exception {
		try {
			ParametroCorporeDAO parametroCorporeDAO = new ParametroCorporeDAO();
			return parametroCorporeDAO.getParamentroAtivo(id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void create(ParametroCorporeDTO parametroBean, HttpServletRequest request) throws ServiceException, LogicException {
		parametroBean.setIdEmpresa(WebUtil.getIdEmpresa(request));
		parametroBean.setDataInicio(UtilDatas.getDataAtual());

		try {
			super.create(parametroBean);

			if (parametroBean.getId() != null) {
				ParametroUtil.atualizarHashMapParametroCitSmart(parametroBean.getId(), parametroBean.getValor());
			}
		} catch (LogicException e) {
			e.printStackTrace();
		}
	}

	// @Override
	// public void criarParametros() throws Exception {
	// ParametroCorporeDAO parametroDAO = new ParametroCorporeDAO();
	//
	// Collection<ParametroCorporeDTO> parametros = parametroDAO.list();
	//
	// if (parametros == null || parametros.isEmpty()) {
	// for (ParametroSistema parametroSmart : ParametroSistema.values()) {
	// ParametroCorporeDTO parametroDto = new ParametroCorporeDTO();
	//
	// parametroDto.setId(parametroSmart.id());
	// parametroDto.setNome(parametroSmart.campo());
	// parametroDto.setDataInicio(UtilDatas.getDataAtual());
	// parametroDto.setIdEmpresa(1);
	// parametroDto.setValor("");
	//
	// parametroDAO.create(parametroDto);
	// }
	// }
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.centralit.citcorpore.negocio.ParametroCorporeService#
	 * criarParametrosNovos()
	 */
	public void criarParametrosNovos() throws Exception {
		ParametroCorporeDAO parametroDAO = new ParametroCorporeDAO();
		TransactionControler tc = new TransactionControlerImpl(parametroDAO.getAliasDB());
		parametroDAO.setTransactionControler(tc);
		tc.start();
		int count = 0;
		for (ParametroSistema parametroCitSmart : ParametroSistema.values()) {
			ParametroCorporeDTO parametroDto = new ParametroCorporeDTO();
			parametroDto = parametroDAO.getParamentroAtivo(parametroCitSmart.id());
			if (parametroDto != null) {
				parametroDto.setNome(parametroCitSmart.campo());
				parametroDto.setTipoDado(parametroCitSmart.tipoCampo());
				
				try {
					parametroDAO.updateNotNull(parametroDto);
					ParametroUtil.atualizarHashMapParametroCitSmart(parametroCitSmart.id(), parametroDto.getValor());
					System.out.println("Parâmetro " + parametroCitSmart + " ATUALIZADO.");
				} catch (Exception e) {
					System.out.println("ERRO AO ATUALIZAR PARÂMETRO");
					e.printStackTrace();
				}
			} else {
				parametroDto = new ParametroCorporeDTO();
				parametroDto.setNome(parametroCitSmart.campo());
				try {
					parametroDto.setId(parametroCitSmart.id());
					parametroDto.setDataInicio(UtilDatas.getDataAtual());
					parametroDto.setIdEmpresa(1);
					parametroDto.setTipoDado(parametroCitSmart.tipoCampo());
					parametroDto.setValor(" ");
					parametroDAO.create(parametroDto);
					ParametroUtil.atualizarHashMapParametroCitSmart(parametroCitSmart.id(), parametroDto.getValor());
					System.out.println("Parâmetro " + parametroCitSmart + " CRIADO.");
				} catch (Exception e) {
					System.out.println("Parâmetro " + parametroCitSmart);
					e.printStackTrace();
				}

			}
			count++;
			if (count == 50) {
				tc.commit();
				if (!tc.isStarted())
					tc.start();
				count = 0;
			}
		}
		tc.commit();
		if (!tc.isStarted())
			tc.start();
		tc.close();
		tc = null;
	}

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ParametroCorporeDAO();
	}

	@Override
	protected void validaCreate(Object unidadeBean) throws Exception {

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

	public void atualizarParametros(ParametroCorporeDTO parametroDto) throws Exception {
		ParametroCorporeDAO parametroCorporeDao = new ParametroCorporeDAO();

		try {
			parametroCorporeDao.updateNotNull(parametroDto);

			if (parametroDto.getId() != null) {

				ParametroUtil.atualizarHashMapParametroCitSmart(parametroDto.getId(), parametroDto.getValor());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void updateNotNull(IDto dto) {
		try {
			validaUpdate(dto);
			((ParametroCorporeDAO) getDao()).updateNotNull(dto);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
