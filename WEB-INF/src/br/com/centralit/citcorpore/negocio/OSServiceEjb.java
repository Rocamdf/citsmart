package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.AtividadesOSDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GlosaOSDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.RelatorioAcompanhamentoDTO;
import br.com.centralit.citcorpore.bean.RelatorioOrdemServicoUstDTO;
import br.com.centralit.citcorpore.integracao.AtividadesOSDao;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.integracao.GlosaOSDao;
import br.com.centralit.citcorpore.integracao.OSDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class OSServiceEjb extends CrudServicePojoImpl implements OSService {

	private static final long serialVersionUID = -2740323580011252045L;
	
	protected CrudDAO getDao() throws ServiceException {
		return new OSDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdContratoAndSituacao(Integer parm, Integer sit) throws Exception {
		OSDao dao = new OSDao();
		try {
			return dao.findByIdContratoAndSituacao(parm, sit);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdContrato(Integer parm) throws Exception {
		OSDao dao = new OSDao();
		try {
			return dao.findByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdContrato(Integer parm) throws Exception {
		OSDao dao = new OSDao();
		try {
			dao.deleteByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdClassificacaoOS(Integer parm) throws Exception {
		OSDao dao = new OSDao();
		try {
			return dao.findByIdClassificacaoOS(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdClassificacaoOS(Integer parm) throws Exception {
		OSDao dao = new OSDao();
		try {
			dao.deleteByIdClassificacaoOS(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByAno(Integer parm) throws Exception {
		OSDao dao = new OSDao();
		try {
			return dao.findByAno(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByAno(Integer parm) throws Exception {
		OSDao dao = new OSDao();
		try {
			dao.deleteByAno(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public IDto create(IDto model) throws ServiceException, LogicException {
		CrudDAO crudDao = getDao();
		AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());

		try {
			// Faz validacao, caso exista.
			validaCreate(model);
			OSDTO osDTO = (OSDTO) model;
			// Instancia ou obtem os DAOs necessarios.

			// Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			atividadesOSDao.setTransactionControler(tc);

			// Inicia transacao
			tc.start();

			// Executa operacoes pertinentes ao negocio.
			model = crudDao.create(model);
			if (osDTO.getColItens() != null) {
				int i = 0;
				for (Iterator it = osDTO.getColItens().iterator(); it.hasNext();) {
					i++;
					AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO) it.next();
					atividadesOSDTO.setIdOS(osDTO.getIdOS());
					atividadesOSDTO.setSequencia(i);
					atividadesOSDTO.setDeleted("N");
					atividadesOSDao.create(atividadesOSDTO);
				}
			}

			// Faz commit e fecha a transacao.
			tc.commit();
			tc.close();

			return model;
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
		return model;
	}
	
	public boolean retornaRegistroOsPai(OSDTO osDTO) throws Exception {
		OSDao dao = new OSDao();
		Collection col = null;
		try {
			col = dao.retornaSeExisteOSFilha(osDTO.getIdOS());
			if(col != null && col.size() == 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void retornaAtividadeCadastradaByPai(OSDTO osDTO) throws Exception {
		AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
		Collection colAtividadeOs = atividadesOSDao.findByIdOS(osDTO.getIdOSPai());

		if (colAtividadeOs != null && colAtividadeOs.size() > 0) {
			List<AtividadesOSDTO> listAtividadeOs = (List<AtividadesOSDTO>) colAtividadeOs;
			Collection listFinal = new ArrayList();
			for (AtividadesOSDTO atividadesOSDTO : listAtividadeOs) {
				if (osDTO.getQuantidade() != null) {
					atividadesOSDTO.setCustoAtividade(atividadesOSDTO.getCustoAtividade() * osDTO.getQuantidade());
				}

				listFinal.add(atividadesOSDTO);
			}
			osDTO.setColItens(listFinal);
		}
	}

	@Override
	public void duplicarOS(Integer idOS) throws Exception {
		OSDao osDao = new OSDao();
		AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
		GlosaOSDao glosaOSDao = new GlosaOSDao();

		OSDTO osDto = new OSDTO();

		osDto.setIdOS(idOS);

		osDto = (OSDTO) osDao.restore(osDto);

		osDto.setDataInicio(null);
		osDto.setDataFim(null);
		osDto.setSituacaoOS(null);
		osDto.setNumero(null);

		Collection<GlosaOSDTO> glosasOS = glosaOSDao.findByIdOs(idOS);

		if (glosasOS != null && !glosasOS.isEmpty()) {

			if (osDto.getQuantidadeGlosasAnterior() != null) {
				osDto.setQuantidadeGlosasAnterior(glosasOS.size() + osDto.getQuantidadeGlosasAnterior());
			} else {
				osDto.setQuantidadeGlosasAnterior(glosasOS.size());
			}
		}

		Collection<AtividadesOSDTO> atividadesOS = atividadesOSDao.findByIdOS(idOS);

		TransactionControler transaction = new TransactionControlerImpl(osDao.getAliasDB());

		try {
			osDao.setTransactionControler(transaction);
			atividadesOSDao.setTransactionControler(transaction);

			transaction.start();

			osDto.setIdOS(null);
			osDto = (OSDTO) osDao.create(osDto);

			if (atividadesOS != null && !atividadesOS.isEmpty()) {

				for (AtividadesOSDTO atividade : atividadesOS) {

					atividade.setIdAtividadesOS(null);

					atividade.setGlosaAtividade(null);

					atividade.setIdOS(osDto.getIdOS());

					atividadesOSDao.create(atividade);
				}
			}

			transaction.commit();
			transaction.close();

		} catch (Exception e) {
			this.rollbackTransaction(transaction, e);
		}

	}

	public void update(IDto model) throws ServiceException, LogicException {
		// Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try {
			// Faz validacao, caso exista.
			validaUpdate(model);
			OSDTO osDTO = (OSDTO) model;

			OSDTO osDTOAux = (OSDTO) crudDao.restore(osDTO);

			if (!osDTOAux.getIdServicoContrato().equals(osDTO.getIdServicoContrato())) {
				osDTO.setQuantidadeGlosasAnterior(null);
			} else {
				osDTO.setQuantidadeGlosasAnterior(osDTOAux.getQuantidadeGlosasAnterior());
			}

			// Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			atividadesOSDao.setTransactionControler(tc);

			// Inicia transacao
			tc.start();

			// Executa operacoes pertinentes ao negocio.
			crudDao.update(osDTO);
			atividadesOSDao.deleteByIdOS(osDTO.getIdOS());
			if (osDTO.getColItens() != null) {
				int i = 0;
				for (Iterator it = osDTO.getColItens().iterator(); it.hasNext();) {
					i++;
					AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO) it.next();
					atividadesOSDTO.setIdOS(osDTO.getIdOS());
					atividadesOSDTO.setSequencia(i);
					atividadesOSDTO.setDeleted("N");
					atividadesOSDao.create(atividadesOSDTO);
				}
			}

			// Faz commit e fecha a transacao.
			tc.commit();
			tc.close();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
	}

	public void updateSituacao(OSDTO os, Collection colGlosas, Collection colItens) throws Exception {
		OSDao dao = new OSDao();
		GlosaOSDao glosaOSDao = new GlosaOSDao();
		AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
		TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());
		@SuppressWarnings("unused")
		Collection colIdsNaoApagar = new ArrayList();
		try {
			dao.setTransactionControler(tc);
			glosaOSDao.setTransactionControler(tc);
			atividadesOSDao.setTransactionControler(tc);
			
			tc.start();
			
			if(os.getSituacaoOS() != null){
				dao.updateSituacao(os.getIdOS(), os.getSituacaoOS());
			}
			
			if(os.getObsFinalizacao() != null){
				dao.updateObsFinal(os.getIdOS(), os.getObsFinalizacao());
			}
			
			if(os.getQuantidade() != null){
				dao.updateQuantidade(os.getIdOS(), os.getQuantidade());
			}
			
			if (colItens != null) {
				for (Iterator it = colItens.iterator(); it.hasNext();) {
					AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO) it.next();
					AtividadesOSDTO atividadesOSAux = new AtividadesOSDTO();
					atividadesOSAux.setIdAtividadesOS(atividadesOSDTO.getIdAtividadesOS());
					atividadesOSAux.setGlosaAtividade(atividadesOSDTO.getGlosaAtividade());
					atividadesOSAux.setQtdeExecutada(atividadesOSDTO.getQtdeExecutada());
					atividadesOSAux.setCustoAtividade(atividadesOSDTO.getCustoAtividade());
					if (atividadesOSDTO.getGlosaAtividade() != null || atividadesOSDTO.getQtdeExecutada() != null) {
						atividadesOSDao.updateNotNull(atividadesOSAux);
					} else if (atividadesOSDTO.getCustoAtividade() != null && os.getIdOSPai() != null) {
						atividadesOSAux = null;
						atividadesOSAux = new AtividadesOSDTO();
						atividadesOSAux.setIdAtividadesOS(atividadesOSDTO.getIdAtividadesOS());
						atividadesOSAux.setCustoAtividade(atividadesOSDTO.getCustoAtividade());
						atividadesOSDao.updateNotNull(atividadesOSAux);
					}
				}
			}
			
			if (colGlosas != null) {
				for (Iterator it = colGlosas.iterator(); it.hasNext();) {
					GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it.next();
					glosaOSDTO.setIdOs(os.getIdOS());
					glosaOSDTO.setDataUltModificacao(UtilDatas.getDataAtual());
					if (glosaOSDTO.getIdGlosaOS() == null || glosaOSDTO.getIdGlosaOS().intValue() == 0) {
						glosaOSDTO.setDataCriacao(UtilDatas.getDataAtual());
						glosaOSDTO = (GlosaOSDTO) glosaOSDao.create(glosaOSDTO);
					} else {
						GlosaOSDTO glosaOSAux = new GlosaOSDTO();
						glosaOSAux.setIdGlosaOS(glosaOSDTO.getIdGlosaOS());
						glosaOSAux = (GlosaOSDTO) glosaOSDao.restore(glosaOSAux);
						glosaOSDTO.setDataCriacao(glosaOSAux.getDataCriacao());
						glosaOSDao.update(glosaOSDTO);
					}
					//colIdsNaoApagar.add(glosaOSDTO.getIdGlosaOS());
				}/*
				String notIn = "";
				for (Iterator it = colIdsNaoApagar.iterator(); it.hasNext();) {
					Integer id = (Integer) it.next();
					if (!notIn.equalsIgnoreCase("")) {
						notIn += ",";
					}
					notIn += "" + id.intValue();
				}
				if (!notIn.equalsIgnoreCase("")) {
					glosaOSDao.deleteByOsNotIn(os.getIdOS(), notIn);
				}*/
			}

			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}

	}

	public void updateSituacao(Integer idOs, Integer situacao, Collection colGlosasOS, Collection colItens, String obsFinalizacao) throws Exception {
		OSDao dao = new OSDao();
		GlosaOSDao glosaOSDao = new GlosaOSDao();
		AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
		TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());
		@SuppressWarnings("unused")
		Collection colIdsNaoApagar = new ArrayList();
		try {
			dao.setTransactionControler(tc);
			glosaOSDao.setTransactionControler(tc);
			atividadesOSDao.setTransactionControler(tc);

			tc.start();

			dao.updateSituacao(idOs, situacao);
			dao.updateObsFinal(idOs, obsFinalizacao);

			if (colItens != null) {
				for (Iterator it = colItens.iterator(); it.hasNext();) {
					AtividadesOSDTO atividadesOSDTO = (AtividadesOSDTO) it.next();
					AtividadesOSDTO atividadesOSAux = new AtividadesOSDTO();
					atividadesOSAux.setIdAtividadesOS(atividadesOSDTO.getIdAtividadesOS());
					atividadesOSAux.setGlosaAtividade(atividadesOSDTO.getGlosaAtividade());
					atividadesOSAux.setQtdeExecutada(atividadesOSDTO.getQtdeExecutada());
					if (atividadesOSDTO.getGlosaAtividade() != null || atividadesOSDTO.getQtdeExecutada() != null) {
						atividadesOSDao.updateNotNull(atividadesOSAux);
					}
				}
			}

			if (colGlosasOS != null) {
				for (Iterator it = colGlosasOS.iterator(); it.hasNext();) {
					GlosaOSDTO glosaOSDTO = (GlosaOSDTO) it.next();
					glosaOSDTO.setIdOs(idOs);
					glosaOSDTO.setDataUltModificacao(UtilDatas.getDataAtual());
					if (glosaOSDTO.getIdGlosaOS() == null || glosaOSDTO.getIdGlosaOS().intValue() == 0) {
						glosaOSDTO.setDataCriacao(UtilDatas.getDataAtual());
						glosaOSDTO = (GlosaOSDTO) glosaOSDao.create(glosaOSDTO);
					} else {
						GlosaOSDTO glosaOSAux = new GlosaOSDTO();
						glosaOSAux.setIdGlosaOS(glosaOSDTO.getIdGlosaOS());
						glosaOSAux = (GlosaOSDTO) glosaOSDao.restore(glosaOSAux);
						glosaOSDTO.setDataCriacao(glosaOSAux.getDataCriacao());
						glosaOSDao.update(glosaOSDTO);
					}
					//colIdsNaoApagar.add(glosaOSDTO.getIdGlosaOS());
				}/*
				String notIn = "";
				for (Iterator it = colIdsNaoApagar.iterator(); it.hasNext();) {
					Integer id = (Integer) it.next();
					if (!notIn.equalsIgnoreCase("")) {
						notIn += ",";
					}
					notIn += "" + id.intValue();
				}
				if (!notIn.equalsIgnoreCase("")) {
					glosaOSDao.deleteByOsNotIn(idOs, notIn);
				}
				*/
			}

			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
	}

	public Collection findByIdContratoAndPeriodoAndSituacao(Integer idContrato, Date dataInicio, Date dataFim, Integer[] situacao, Integer idospai) throws Exception {
		OSDao dao = new OSDao();
		return dao.findByIdContratoAndPeriodoAndSituacao(idContrato, dataInicio, dataFim, situacao, idospai);
	}
	
	public Collection findByIdContratoAndPeriodoAndSituacao(Integer idContrato, Date dataInicio, Date dataFim, Integer[] situacao) throws Exception {
		OSDao dao = new OSDao();
		return dao.findByIdContratoAndPeriodoAndSituacao(idContrato, dataInicio, dataFim, situacao);
	}
	
	public Collection listOSHomologadasENaoAssociadasFatura(Integer idContrato) throws Exception {
		OSDao dao = new OSDao();
		return dao.listOSHomologadasENaoAssociadasFatura(idContrato);
	}

	public Collection listOSByIds(Integer idContrato, Integer[] idOSs) throws Exception {
		OSDao dao = new OSDao();
		return dao.listOSByIds(idContrato, idOSs);
	}

	public Collection listOSAssociadasFatura(Integer idFatura) throws Exception {
		OSDao dao = new OSDao();
		return dao.listOSAssociadasFatura(idFatura);
	}

	public Collection listOSByIdAtividadePeriodica(Integer idatividade) throws Exception {
		OSDao dao = new OSDao();
		return dao.listOSByIdAtividadePeriodica(idatividade);
	}

	public Collection listAtividadePeridodicaByIdos(Integer idos) throws Exception {
		OSDao dao = new OSDao();
		return dao.listAtividadePeridodicaByIdos(idos);
	}

	@Override
	public Collection<RelatorioOrdemServicoUstDTO> listaCustoAtividadeOrdemServicoPorPeriodo(RelatorioOrdemServicoUstDTO relatorio) throws Exception {
		OSDao dao = new OSDao();
		Collection<RelatorioOrdemServicoUstDTO> listaCustoAtividadePorPerido = null;
		try {
			listaCustoAtividadePorPerido = dao.listaCustoAtividadeOrdemServicoPorPeriodo(relatorio);
			if (listaCustoAtividadePorPerido != null) {
				for (RelatorioOrdemServicoUstDTO ust : listaCustoAtividadePorPerido) {
					if (ust.getPeriodo().equals(1)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.janeiro"));
					}
					if (ust.getPeriodo().equals(2)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.fevereiro"));
					}
					if (ust.getPeriodo().equals(3)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.marco"));
					}
					if (ust.getPeriodo().equals(4)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.abril"));
					}
					if (ust.getPeriodo().equals(5)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.maio"));
					}
					if (ust.getPeriodo().equals(6)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.junho"));
					}
					if (ust.getPeriodo().equals(7)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.julho"));
					}
					if (ust.getPeriodo().equals(8)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.agosto"));
					}
					if (ust.getPeriodo().equals(9)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.setembro"));
					}
					if (ust.getPeriodo().equals(10)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.outubro"));
					}
					if (ust.getPeriodo().equals(11)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.novembro"));
					}
					if (ust.getPeriodo().equals(12)) {
						ust.setMes(i18n_Message("citcorpore.texto.mes.dezembro"));
					}

					String valor1 = UtilFormatacao.formatDoubleSemPontos(ust.getCustoAtividade(), 2);
					ust.setCustoAtividadeFormatada(valor1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaCustoAtividadePorPerido;
	}

	@Override
	public Collection<RelatorioOrdemServicoUstDTO> listaAnos() throws Exception {
		OSDao osDao = new OSDao();
		return osDao.listaAnos();
	}

	@Override
	public Collection<RelatorioAcompanhamentoDTO> listaAcompanhamentoPorPeriodoDoContrato(RelatorioAcompanhamentoDTO relatorio) throws Exception {

		ContratoDTO contratoDto = new ContratoDTO();

		OSDao osDao = new OSDao();

		ContratoDao contratoDao = new ContratoDao();

		contratoDto.setIdContrato(relatorio.getIdContrato());

		contratoDto = (ContratoDTO) contratoDao.restore(contratoDto);

		List<RelatorioAcompanhamentoDTO> listaDeMesesAcompanhamentoPorPeriodoDoContrato = new ArrayList<RelatorioAcompanhamentoDTO>();

		List<RelatorioAcompanhamentoDTO> listRelatorioAcompanhamentoFinal = new ArrayList<RelatorioAcompanhamentoDTO>();

		Calendar dataInicioContrato = Calendar.getInstance();

		dataInicioContrato.setTime(contratoDto.getDataContrato());

		Integer anoInicial = dataInicioContrato.get(Calendar.YEAR);

		Integer mesInicial = dataInicioContrato.get(Calendar.MONTH) + 1;

		relatorio.setDataInicioContrato(contratoDto.getDataContrato());

		relatorio.setDataFimContrato(contratoDto.getDataFimContrato());

		Integer quantidadeMesesPeriodoContratro = this.verificarPeriodoDeVigenciaContrato(relatorio);
		Integer cont = 0;

		try {

			listaDeMesesAcompanhamentoPorPeriodoDoContrato = (List<RelatorioAcompanhamentoDTO>) osDao.listaAcompanhamentoPorPeriodoDoContrato(relatorio);

			if (quantidadeMesesPeriodoContratro != null && quantidadeMesesPeriodoContratro > 0) {

				int mes = 0;
				Integer ano = anoInicial;
				for (int numeroMes = mesInicial; numeroMes < (quantidadeMesesPeriodoContratro + mesInicial) ; numeroMes++) {

					mes = numeroMes;

					if (numeroMes > 12) {

						Integer diferenca = 0;
						diferenca = numeroMes / 12;

						mes = numeroMes - (diferenca * 12);

						if (mes == 0) {
							mes = 12;
							diferenca = diferenca - 1;
						}
						ano = anoInicial + diferenca;
					}

					RelatorioAcompanhamentoDTO relatorioAcompanhamentoDto = new RelatorioAcompanhamentoDTO();

					relatorioAcompanhamentoDto.setMes(i18n_Message(UtilDatas.getDescricaoMes(mes)) + " / " + ano);

					/*relatorioAcompanhamentoDto.setNumeroMes(mes);*/
					
					relatorioAcompanhamentoDto.setNumeroMesDouble((double) mes);

					relatorioAcompanhamentoDto.setAnoDouble((double) ano);

					if (listaDeMesesAcompanhamentoPorPeriodoDoContrato != null && !listaDeMesesAcompanhamentoPorPeriodoDoContrato.isEmpty()) {

						for (RelatorioAcompanhamentoDTO mesComAtividadeRealizada : listaDeMesesAcompanhamentoPorPeriodoDoContrato) {

							if (mesComAtividadeRealizada.getNumeroMes().equals(relatorioAcompanhamentoDto.getNumeroMes())
									&& mesComAtividadeRealizada.getAno().equals(relatorioAcompanhamentoDto.getAno())) {
								cont = cont + 1;
								relatorioAcompanhamentoDto.setCustoAtividade(mesComAtividadeRealizada.getCustoAtividade());

							}
							relatorioAcompanhamentoDto.setValorEstimadoContrato(mesComAtividadeRealizada.getValorEstimadoContrato());
							relatorioAcompanhamentoDto.setQuantidadePeriodoRealizado(cont);
							if (this.verificarPeriodoDeVigenciaContrato(mesComAtividadeRealizada) != null) {
								relatorioAcompanhamentoDto.setPeridoVigenciaContrato(this.verificarPeriodoDeVigenciaContrato(mesComAtividadeRealizada));
							}

						}
					}

					listRelatorioAcompanhamentoFinal.add(relatorioAcompanhamentoDto);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listRelatorioAcompanhamentoFinal;
	}

	public Integer verificarPeriodoDeVigenciaContrato(RelatorioAcompanhamentoDTO relatorio) {
		Integer quantidade = 0;
		quantidade = UtilDatas.getDifMeses(relatorio.getDataInicioContrato(), relatorio.getDataFimContrato());
		if (quantidade != null) {
			return quantidade;
		} else {
			return null;
		}
	}

	@Override
	public void cancelaOsFilhas(OSDTO os) throws Exception {
		OSDao dao = new OSDao();
		try {
			if(os.getSituacaoOS() != null){
				dao.cancelaOSFilhas(os);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection retornaSeExisteOSFilha(OSDTO os) throws Exception {
		OSDao dao = new OSDao();
		try {
			return dao.retornaSeExisteOSFilha(os.getIdOS());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public void delete(IDto model) throws ServiceException, LogicException {
		// Instancia Objeto controlador de transacao
		CrudDAO crudDao = getDao();
		AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
		TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
		try {
			// Faz validacao, caso exista.
			validaUpdate(model);
			OSDTO osDTO = (OSDTO) model;

			// Seta o TransactionController para os DAOs
			crudDao.setTransactionControler(tc);
			atividadesOSDao.setTransactionControler(tc);

			// Inicia transacao
			tc.start();

			// Executa operacoes pertinentes ao negocio.
			atividadesOSDao.deleteByIdOS(osDTO.getIdOS());
			crudDao.delete(osDTO);

			// Faz commit e fecha a transacao.
			tc.commit();
			tc.close();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
	}
	
}
