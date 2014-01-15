package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import br.com.centralit.citcorpore.bean.FornecedorProdutoDTO;
import br.com.centralit.citcorpore.bean.RelacionamentoProdutoDTO;
import br.com.centralit.citcorpore.bean.TipoProdutoDTO;
import br.com.centralit.citcorpore.integracao.FornecedorProdutoDao;
import br.com.centralit.citcorpore.integracao.RelacionamentoProdutoDao;
import br.com.centralit.citcorpore.integracao.TipoProdutoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("unchecked")
public class TipoProdutoServiceEjb extends CrudServicePojoImpl implements TipoProdutoService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8194811007054949900L;

	protected CrudDAO getDao() throws ServiceException {
		return new TipoProdutoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public List<TipoProdutoDTO> findByIdCategoria(Integer idCategoria) throws Exception {
	    return new TipoProdutoDao().findByIdCategoria(idCategoria);
	}

	@Override
	public boolean consultarTiposProdutos(TipoProdutoDTO tipoProdutoDTO) throws Exception {
		
		return new TipoProdutoDao().consultarTiposProdutos(tipoProdutoDTO);
	}

	
	@Override
	public IDto create(IDto model, HttpServletRequest request) throws Exception {

		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		
		try {
		
		TipoProdutoDTO tipoProdutoDTO = (TipoProdutoDTO) model;
		
		TipoProdutoDao tipoProdutoDao = new TipoProdutoDao();
		tipoProdutoDao.setTransactionControler(tc);
		
		RelacionamentoProdutoDao relacionamentoProdutoDao = new RelacionamentoProdutoDao();
		relacionamentoProdutoDao.setTransactionControler(tc);
		
		FornecedorProdutoDao fornecedorProdutoDao = new FornecedorProdutoDao();
		fornecedorProdutoDao.setTransactionControler(tc);
		
		tipoProdutoDTO = (TipoProdutoDTO) tipoProdutoDao.create(tipoProdutoDTO);
		
		ArrayList<RelacionamentoProdutoDTO> listaRelacionamentos = (ArrayList<RelacionamentoProdutoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
				RelacionamentoProdutoDTO.class, "relacionamentosSerializados", request);
		
		ArrayList<FornecedorProdutoDTO> listaFornecedores = (ArrayList<FornecedorProdutoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
				FornecedorProdutoDTO.class, "fornecedoresSerializados", request);
		
		
		if (listaRelacionamentos != null && !listaRelacionamentos.isEmpty()) {

			for (RelacionamentoProdutoDTO relacionamentoProdutoDTO : listaRelacionamentos) {
				relacionamentoProdutoDTO.setIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
				RelacionamentoProdutoDTO relacionamentoprodutoAux = (RelacionamentoProdutoDTO) relacionamentoProdutoDao.restore(relacionamentoProdutoDTO);
				if (relacionamentoprodutoAux == null){				
					relacionamentoProdutoDao.create(relacionamentoProdutoDTO);
				}
			}
		}
		
		if (listaFornecedores != null && !listaFornecedores.isEmpty()) {

			for (FornecedorProdutoDTO fornecedorProdutoDTO : listaFornecedores) {
				fornecedorProdutoDTO.setDataInicio(UtilDatas.getDataAtual());
				fornecedorProdutoDTO.setIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
				FornecedorProdutoDTO fornecedorProdutoDTOAux = fornecedorProdutoDao.findByIdTipoProdutoAndFornecedor(fornecedorProdutoDTO.getIdTipoProduto(), fornecedorProdutoDTO.getIdFornecedor());
				if (fornecedorProdutoDTOAux == null){				
					fornecedorProdutoDao.create(fornecedorProdutoDTO);
				}
			}
		}
		
		
		tc.commit();
		tc.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(tc, e);
		}
		return null;
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void update(IDto model, HttpServletRequest request) throws ServiceException, LogicException {
	TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());
		
		try {
			
			Collection<FornecedorProdutoDTO> colFornecedorProdutoDTO = new ArrayList();
			
		TipoProdutoDTO tipoProdutoDTO = (TipoProdutoDTO) model;
		
		TipoProdutoDao tipoProdutoDao = new TipoProdutoDao();
		tipoProdutoDao.setTransactionControler(tc);
		
		FornecedorProdutoDao fornecedorProdutoDao = new FornecedorProdutoDao();
		fornecedorProdutoDao.setTransactionControler(tc);
		
		RelacionamentoProdutoDao relacionamentoProdutoDao = new RelacionamentoProdutoDao();
		relacionamentoProdutoDao.setTransactionControler(tc);
		
		tipoProdutoDao.update(tipoProdutoDTO);
		
		relacionamentoProdutoDao.deleteByIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
		
		ArrayList<RelacionamentoProdutoDTO> listaRelacionamentos = (ArrayList<RelacionamentoProdutoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
				RelacionamentoProdutoDTO.class, "relacionamentosSerializados", request);
		
		ArrayList<FornecedorProdutoDTO> listaFornecedores = (ArrayList<FornecedorProdutoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
				FornecedorProdutoDTO.class, "fornecedoresSerializados", request);
		
		if (listaRelacionamentos != null && !listaRelacionamentos.isEmpty()) {

			for (RelacionamentoProdutoDTO relacionamentoProdutoDTO : listaRelacionamentos) {
				relacionamentoProdutoDTO.setIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
				RelacionamentoProdutoDTO relacionamentoprodutoAux = (RelacionamentoProdutoDTO) relacionamentoProdutoDao.restore(relacionamentoProdutoDTO);
				if (relacionamentoprodutoAux == null){				
					relacionamentoProdutoDao.create(relacionamentoProdutoDTO);
				}
			}
		}
		colFornecedorProdutoDTO = fornecedorProdutoDao.findByIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
		
		if (listaFornecedores != null && !listaFornecedores.isEmpty()) {

			for (FornecedorProdutoDTO fornecedorProdutoDTO : listaFornecedores) {
				
				if (colFornecedorProdutoDTO != null && !colFornecedorProdutoDTO.isEmpty()){
				
				for (FornecedorProdutoDTO fornecedor : colFornecedorProdutoDTO){
					if (fornecedorProdutoDTO.getIdFornecedor().equals(fornecedor.getIdFornecedor())){
						fornecedorProdutoDTO.setDataInicio(fornecedor.getDataInicio());
						fornecedorProdutoDTO.setIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
						fornecedorProdutoDTO.setIdFornecedorProduto(fornecedor.getIdFornecedorProduto());
						fornecedorProdutoDao.update(fornecedorProdutoDTO);
						break;
					}
				}
				}
				if (fornecedorProdutoDTO.getIdTipoProduto() == null){
					fornecedorProdutoDTO.setDataInicio(UtilDatas.getDataAtual());
					fornecedorProdutoDTO.setIdTipoProduto(tipoProdutoDTO.getIdTipoProduto());
					FornecedorProdutoDTO fornecedorProdutoDTOAux = fornecedorProdutoDao.findByIdTipoProdutoAndFornecedor(fornecedorProdutoDTO.getIdTipoProduto(), fornecedorProdutoDTO.getIdFornecedor());
					if (fornecedorProdutoDTOAux == null){				
						fornecedorProdutoDao.create(fornecedorProdutoDTO);
					}
				}
			}
		}
		
		
		
		tc.commit();
		tc.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(tc, e);
		}
		
	}
	
	
	
}
