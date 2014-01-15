package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AvaliacaoFornecedorDTO;
import br.com.centralit.citcorpore.bean.AvaliacaoReferenciaFornecedorDTO;
import br.com.centralit.citcorpore.bean.CriterioAvaliacaoFornecedorDTO;
import br.com.centralit.citcorpore.integracao.AvaliacaoFornecedorDao;
import br.com.centralit.citcorpore.integracao.AvaliacaoReferenciaFornecedorDao;
import br.com.centralit.citcorpore.integracao.CriterioAvaliacaoFornecedorDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("serial")
public class AvaliacaoFornecedorServiceEjb extends CrudServicePojoImpl implements AvaliacaoFornecedorService {
	protected CrudDAO getDao() throws ServiceException {
		return new AvaliacaoFornecedorDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.citframework.service.CrudServicePojoImpl#create(br.com.citframework.dto.IDto)
	 */
	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {

		AvaliacaoFornecedorDao avaliacaoForncedorDao = new AvaliacaoFornecedorDao();

		AvaliacaoReferenciaFornecedorDao avaliacaoReferenciaFornecedorDao = new AvaliacaoReferenciaFornecedorDao();

		CriterioAvaliacaoFornecedorDao criterioAvaliacaoFornecedorDao = new CriterioAvaliacaoFornecedorDao();

		AvaliacaoFornecedorDTO avaliacaoFornecedorDto = (AvaliacaoFornecedorDTO) model;

		TransactionControler transactionControler = new TransactionControlerImpl(getDao().getAliasDB());

		try {

			avaliacaoForncedorDao.setTransactionControler(transactionControler);

			avaliacaoReferenciaFornecedorDao.setTransactionControler(transactionControler);

			criterioAvaliacaoFornecedorDao.setTransactionControler(transactionControler);

			transactionControler.start();

			avaliacaoFornecedorDto = (AvaliacaoFornecedorDTO) avaliacaoForncedorDao.create(avaliacaoFornecedorDto);

			if (avaliacaoFornecedorDto.getListAvaliacaoReferenciaFornecedor() != null) {
				for (AvaliacaoReferenciaFornecedorDTO avaliacaoReferenciaFornecedorDto : avaliacaoFornecedorDto.getListAvaliacaoReferenciaFornecedor()) {
					if (avaliacaoFornecedorDto.getIdAvaliacaoFornecedor() != null && avaliacaoFornecedorDto.getIdAvaliacaoFornecedor() != 0) {
						
						if (avaliacaoReferenciaFornecedorDto.getDecisao().equalsIgnoreCase("Sim")) {
							avaliacaoReferenciaFornecedorDto.setDecisao("S");
						} else {
							avaliacaoReferenciaFornecedorDto.setDecisao("N");
						}
						
						avaliacaoReferenciaFornecedorDto.setIdAvaliacaoFornecedor(avaliacaoFornecedorDto.getIdAvaliacaoFornecedor());
						avaliacaoReferenciaFornecedorDao.create(avaliacaoReferenciaFornecedorDto);
					}

				}
			}

			if (avaliacaoFornecedorDto.getListCriterioAvaliacaoFornecedor() != null) {
				for (CriterioAvaliacaoFornecedorDTO criterioAvaliacaoFornecedorDto : avaliacaoFornecedorDto.getListCriterioAvaliacaoFornecedor()) {
					if (avaliacaoFornecedorDto.getIdAvaliacaoFornecedor() != null && avaliacaoFornecedorDto.getIdAvaliacaoFornecedor() != 0) {
						if (criterioAvaliacaoFornecedorDto.getValor().equalsIgnoreCase("Sim")) {
							criterioAvaliacaoFornecedorDto.setValorInteger(1);
						} else {
							if (criterioAvaliacaoFornecedorDto.getValor().equalsIgnoreCase("Não")) {
								criterioAvaliacaoFornecedorDto.setValorInteger(0);
							} else {
								criterioAvaliacaoFornecedorDto.setValorInteger(2);
							}
						}
						criterioAvaliacaoFornecedorDto.setIdAvaliacaoFornecedor(avaliacaoFornecedorDto.getIdAvaliacaoFornecedor());
						criterioAvaliacaoFornecedorDao.create(criterioAvaliacaoFornecedorDto);
					}

				}
			}

			transactionControler.commit();
			transactionControler.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		}

		return avaliacaoFornecedorDto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.citframework.service.CrudServicePojoImpl#update(br.com.citframework.dto.IDto)
	 */
	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		AvaliacaoFornecedorDao avaliacaoForncedorDao = new AvaliacaoFornecedorDao();

		AvaliacaoReferenciaFornecedorDao avaliacaoReferenciaFornecedorDao = new AvaliacaoReferenciaFornecedorDao();

		CriterioAvaliacaoFornecedorDao criterioAvaliacaoFornecedorDao = new CriterioAvaliacaoFornecedorDao();

		AvaliacaoFornecedorDTO avaliacaoFornecedorDto = (AvaliacaoFornecedorDTO) model;

		TransactionControler transactionControler = new TransactionControlerImpl(getDao().getAliasDB());

		try {

			avaliacaoForncedorDao.setTransactionControler(transactionControler);

			avaliacaoReferenciaFornecedorDao.setTransactionControler(transactionControler);

			criterioAvaliacaoFornecedorDao.setTransactionControler(transactionControler);

			transactionControler.start();

			avaliacaoForncedorDao.update(avaliacaoFornecedorDto);

			if (avaliacaoFornecedorDto.getListAvaliacaoReferenciaFornecedor() != null) {
				if (avaliacaoFornecedorDto.getIdAvaliacaoFornecedor() != null && avaliacaoFornecedorDto.getIdAvaliacaoFornecedor() != 0) {
					avaliacaoReferenciaFornecedorDao.deleteByIdAvaliacaoFornecedor(avaliacaoFornecedorDto.getIdAvaliacaoFornecedor());
					for (AvaliacaoReferenciaFornecedorDTO avaliacaoReferenciaFornecedorDto : avaliacaoFornecedorDto.getListAvaliacaoReferenciaFornecedor()) {
						
						if (avaliacaoReferenciaFornecedorDto.getDecisao().equalsIgnoreCase("Sim")) {
							avaliacaoReferenciaFornecedorDto.setDecisao("S");
						} else {
							avaliacaoReferenciaFornecedorDto.setDecisao("N");
						}
						
						avaliacaoReferenciaFornecedorDto.setIdAvaliacaoFornecedor(avaliacaoFornecedorDto.getIdAvaliacaoFornecedor());
						avaliacaoReferenciaFornecedorDao.create(avaliacaoReferenciaFornecedorDto);

					}
				}

			}

			if (avaliacaoFornecedorDto.getListCriterioAvaliacaoFornecedor() != null) {
				if (avaliacaoFornecedorDto.getIdAvaliacaoFornecedor() != null && avaliacaoFornecedorDto.getIdAvaliacaoFornecedor() != 0) {
					criterioAvaliacaoFornecedorDao.deleteByIdAvaliacaoFornecedor(avaliacaoFornecedorDto.getIdAvaliacaoFornecedor());
					for (CriterioAvaliacaoFornecedorDTO criterioAvaliacaoFornecedorDto : avaliacaoFornecedorDto.getListCriterioAvaliacaoFornecedor()) {

						if (criterioAvaliacaoFornecedorDto.getValor().equalsIgnoreCase("Sim")) {
							criterioAvaliacaoFornecedorDto.setValorInteger(1);
						} else {
							if (criterioAvaliacaoFornecedorDto.getValor().equalsIgnoreCase("Não")) {
								criterioAvaliacaoFornecedorDto.setValorInteger(0);
							} else {
								criterioAvaliacaoFornecedorDto.setValorInteger(2);
							}
						}
						criterioAvaliacaoFornecedorDto.setIdAvaliacaoFornecedor(avaliacaoFornecedorDto.getIdAvaliacaoFornecedor());
						criterioAvaliacaoFornecedorDao.create(criterioAvaliacaoFornecedorDto);

					}
				}

			}

			transactionControler.commit();
			transactionControler.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		}

	}

	public Collection findByIdFornecedor(Integer parm) throws Exception {
	    return new AvaliacaoFornecedorDao().findByIdFornecedor(parm);
	}
	
	public boolean fornecedorQualificado(Integer idFornecedor) throws Exception {
	    boolean result = false;
	    Collection<AvaliacaoFornecedorDTO> colAvaliacoes = findByIdFornecedor(idFornecedor);
	    if (colAvaliacoes != null) {
	        for (AvaliacaoFornecedorDTO avaliacaoFornecedorDto : colAvaliacoes) {
                if (avaliacaoFornecedorDto.getDecisaoQualificacao().equals("Q")) {
                    result = true;
                    break;
                }
            }
	    }
	    return result;
	}
}
