package br.com.centralit.bpm.servico;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ElementoFluxoInicioDTO;
import br.com.centralit.bpm.dto.ElementoFluxoRaiaDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.SequenciaFluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.ElementoFluxoEmailDao;
import br.com.centralit.bpm.integracao.ElementoFluxoEventoDao;
import br.com.centralit.bpm.integracao.ElementoFluxoFinalizacaoDao;
import br.com.centralit.bpm.integracao.ElementoFluxoInicioDao;
import br.com.centralit.bpm.integracao.ElementoFluxoPortaDao;
import br.com.centralit.bpm.integracao.ElementoFluxoScriptDao;
import br.com.centralit.bpm.integracao.ElementoFluxoTarefaDao;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.bpm.integracao.InstanciaFluxoDao;
import br.com.centralit.bpm.integracao.SequenciaFluxoDao;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;

public class FluxoServiceEjb extends CrudServicePojoImpl implements FluxoService {
	
	protected CrudDAO getDao() throws ServiceException {
		return new FluxoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		FluxoDTO fluxoDto = (FluxoDTO) arg0;
		if (fluxoDto.getIdTipoFluxo() == null) {
			throw new LogicException("Tipo de fluxo não definido.");	
		}
		if (fluxoDto.getDataInicio() == null) {
			throw new LogicException("Data início não definida.");	
		}
		//if (fluxoDto.getConteudoXml() == null || fluxoDto.getConteudoXml().trim().length() == 0) {
			//throw new LogicException("Conteúdo XML não definido.");	
		//}
		fluxoDto.setVersao("1.0");
	}
	
	protected void validaDelete(Object arg0) throws Exception {
		FluxoDTO fluxoDto = (FluxoDTO) arg0;
		FluxoDTO fluxoAuxDto = (FluxoDTO) new FluxoDao().restore(fluxoDto);
		if (fluxoAuxDto != null) {
			Collection colInstancias = new InstanciaFluxoDao().findByIdFluxo(fluxoAuxDto.getIdFluxo());
			if (colInstancias != null && !colInstancias.isEmpty())
				throw new LogicException("Fluxo '"+fluxoAuxDto.getDescricao()+"' não pode ser excluído. Já existem instâncias vinculadas.");	
		}
	}
	
	protected void validaFind(Object arg0) throws Exception {}

	protected void validaUpdate(Object arg0) throws Exception {
		FluxoDTO fluxoDto = (FluxoDTO) arg0;
		if (fluxoDto.getIdTipoFluxo() == null) {
			throw new LogicException("Tipo de fluxo não definido.");	
		}
		if (fluxoDto.getDataInicio() == null) {
			throw new LogicException("Data início não definida.");	
		}
		//if (fluxoDto.getConteudoXml() == null || fluxoDto.getConteudoXml().trim().length() == 0) {
		//	throw new LogicException("Conteúdo XML não definido.");	
		//}
	}
	
	@Override
	public Collection listAll() throws Exception {
		return new FluxoDao().listAll();
	}
	
	public FluxoDTO findByTipoFluxo(Integer idTipoFluxo) throws Exception {
		return new FluxoDao().findByTipoFluxo(idTipoFluxo);
	}
	
	private void criaElementos(FluxoDTO fluxoDto, TransactionControler tc) throws Exception {
		if (fluxoDto.getInicioFluxo() == null) 
			throw new LogicException("Início do fluxo não definido");
		
		if (fluxoDto.getColFinalizacoes() == null || fluxoDto.getColFinalizacoes().isEmpty()) 
			throw new LogicException("Término do fluxo não definido");

		if (fluxoDto.getColSequenciamentos() == null || fluxoDto.getColSequenciamentos().isEmpty()) 
			throw new LogicException("O fluxo deve ter pelo menos uma ligação entre elementos");

		ElementoFluxoInicioDao fluxoInicioDao = new ElementoFluxoInicioDao();
		ElementoFluxoFinalizacaoDao fluxoFinalizacaoDao = new ElementoFluxoFinalizacaoDao();
		ElementoFluxoTarefaDao fluxoTarefaDao = new ElementoFluxoTarefaDao();
		ElementoFluxoPortaDao fluxoPortaDao = new ElementoFluxoPortaDao();
		ElementoFluxoScriptDao fluxoScriptDao = new ElementoFluxoScriptDao();
		ElementoFluxoEmailDao fluxoEmailDao = new ElementoFluxoEmailDao();
        ElementoFluxoEventoDao fluxoEventoDao = new ElementoFluxoEventoDao();
		SequenciaFluxoDao fluxoSequenciaDao = new SequenciaFluxoDao();

		fluxoInicioDao.setTransactionControler(tc);
		fluxoFinalizacaoDao.setTransactionControler(tc);
		fluxoTarefaDao.setTransactionControler(tc);
		fluxoPortaDao.setTransactionControler(tc);
		fluxoScriptDao.setTransactionControler(tc);
		fluxoEmailDao.setTransactionControler(tc);
	    fluxoEventoDao.setTransactionControler(tc);
		fluxoSequenciaDao.setTransactionControler(tc);
		
		HashMap<String, String> mapRaias = new HashMap();
		List<ElementoFluxoRaiaDTO> colRaias = fluxoDto.getColRaias();
		if (colRaias != null) {
			for (ElementoFluxoRaiaDTO raiaDto : colRaias) {
				if (raiaDto.getSiglaGrupo() != null && raiaDto.getSiglaGrupo().trim().length() > 0 && raiaDto.getIdElementosAgrupados() != null) {
					for (String elemento: raiaDto.getIdElementosAgrupados()) 
						mapRaias.put(elemento,raiaDto.getSiglaGrupo());
				}
			}
		}

		if (fluxoDto.getIdFluxo() != null) {
			fluxoSequenciaDao.deleteByIdFluxo(fluxoDto.getIdFluxo());
			fluxoInicioDao.deleteByIdFluxo(fluxoDto.getIdFluxo());
			fluxoTarefaDao.deleteByIdFluxo(fluxoDto.getIdFluxo());
			fluxoScriptDao.deleteByIdFluxo(fluxoDto.getIdFluxo());
			fluxoPortaDao.deleteByIdFluxo(fluxoDto.getIdFluxo());
			fluxoEmailDao.deleteByIdFluxo(fluxoDto.getIdFluxo());
            fluxoEventoDao.deleteByIdFluxo(fluxoDto.getIdFluxo());
			fluxoFinalizacaoDao.deleteByIdFluxo(fluxoDto.getIdFluxo());
		}
		
		Integer idFluxo = fluxoDto.getIdFluxo();
		
		HashMap<String, ElementoFluxoDTO> mapId = new HashMap();
		ElementoFluxoDTO inicioDto = fluxoDto.getInicioFluxo();
		if (inicioDto != null) {
			inicioDto.setIdFluxo(idFluxo);
			inicioDto = (ElementoFluxoDTO) fluxoInicioDao.create(inicioDto);
			mapId.put(inicioDto.getIdDesenho(), inicioDto);
		}
		
		List<ElementoFluxoDTO> colTarefas = fluxoDto.getColTarefas();
		if (colTarefas != null) {
			for (ElementoFluxoDTO tarefaDto : colTarefas) {
				if (tarefaDto.getGrupos() == null || tarefaDto.getGrupos().trim().length() == 0)
					tarefaDto.setGrupos(mapRaias.get(tarefaDto.getIdDesenho()));
				tarefaDto.setIdFluxo(idFluxo);
				tarefaDto = (ElementoFluxoDTO) fluxoTarefaDao.create(tarefaDto);
				mapId.put(tarefaDto.getIdDesenho(), tarefaDto);
			}
		}
		
		List<ElementoFluxoDTO> colScripts = fluxoDto.getColScripts();
		if (colScripts != null) {
			for (ElementoFluxoDTO scriptDto : colScripts) {
				scriptDto.setIdFluxo(idFluxo);
				scriptDto = (ElementoFluxoDTO) fluxoScriptDao.create(scriptDto);
				mapId.put(scriptDto.getIdDesenho(), scriptDto);
			}
		}

        List<ElementoFluxoDTO> colEventos = fluxoDto.getColEventos();
        if (colEventos != null) {
            for (ElementoFluxoDTO eventoDto : colEventos) {
                eventoDto.setIdFluxo(idFluxo);
                eventoDto = (ElementoFluxoDTO) fluxoEventoDao.create(eventoDto);
                mapId.put(eventoDto.getIdDesenho(), eventoDto);
            }
        }

        List<ElementoFluxoDTO> colEmails = fluxoDto.getColEmails();
		if (colEmails != null) {
			for (ElementoFluxoDTO emailDto : colEmails) {
				emailDto.setIdFluxo(idFluxo);
				emailDto = (ElementoFluxoDTO) fluxoEmailDao.create(emailDto);
				mapId.put(emailDto.getIdDesenho(), emailDto);
			}
		}

		List<ElementoFluxoDTO> colPortas = fluxoDto.getColPortas();
		if (colPortas != null) {
			for (ElementoFluxoDTO decisaoDto : colPortas) {
				decisaoDto.setIdFluxo(idFluxo);
				decisaoDto = (ElementoFluxoDTO) fluxoPortaDao.create(decisaoDto);
				mapId.put(decisaoDto.getIdDesenho(), decisaoDto);
			}
		}

		List<ElementoFluxoDTO> colFinalizacoes = fluxoDto.getColFinalizacoes();
		if (colFinalizacoes != null) {
			for (ElementoFluxoDTO finalizacaoDto : colFinalizacoes) {
				finalizacaoDto.setIdFluxo(idFluxo);
				finalizacaoDto = (ElementoFluxoDTO) fluxoFinalizacaoDao.create(finalizacaoDto);
				mapId.put(finalizacaoDto.getIdDesenho(), finalizacaoDto);
			}
		}

		List<SequenciaFluxoDTO> colSequenciamentos = fluxoDto.getColSequenciamentos();
		if (colSequenciamentos != null) {
			for (SequenciaFluxoDTO sequenciaDto : colSequenciamentos) {
				sequenciaDto.setIdFluxo(idFluxo);
				ElementoFluxoDTO elementoDto = mapId.get(sequenciaDto.getIdDesenhoOrigem());
				if (elementoDto != null) 
					sequenciaDto.setIdElementoOrigem(elementoDto.getIdElemento());
				elementoDto = mapId.get(sequenciaDto.getIdDesenhoDestino());
				if (elementoDto != null) 
					sequenciaDto.setIdElementoDestino(elementoDto.getIdElemento());
				sequenciaDto = (SequenciaFluxoDTO) fluxoSequenciaDao.create(sequenciaDto);
			}
		}
	}
	
	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		TransactionControler tc = new TransactionControlerImpl(new FluxoDao().getAliasDB());
		try {
			tc.start();

			FluxoDTO fluxoDto = (FluxoDTO) model;
			fluxoDto = create(fluxoDto, tc);
			
			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
		return model;
	}
	
	@Override
	public void update(IDto model) throws ServiceException, LogicException {
		FluxoDao fluxoDao = new FluxoDao();
		TipoFluxoDao tipoFluxoDao = new TipoFluxoDao();
		TransactionControler tc = new TransactionControlerImpl(fluxoDao.getAliasDB());
		try {
			validaUpdate(model);

			tc.start();

			fluxoDao.setTransactionControler(tc);
			tipoFluxoDao.setTransactionControler(tc);

			FluxoDTO fluxoDto = (FluxoDTO) model;

			fluxoDao.update(fluxoDto);
			TipoFluxoDTO tipoFluxoDto = tipoFluxoDao.findByNome(fluxoDto.getNomeFluxo());
			if (tipoFluxoDto != null && tipoFluxoDto.getIdTipoFluxo().intValue() != fluxoDto.getIdTipoFluxo().intValue())
				throw new LogicException("Já existe um fluxo com esse nome");
			
			tipoFluxoDto = new TipoFluxoDTO();
			Reflexao.copyPropertyValues(fluxoDto, tipoFluxoDto);
			tipoFluxoDao.update(tipoFluxoDto);
			
			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
	}

	@Override
	public FluxoDTO criaEstrutura(FluxoDTO fluxoDto) throws Exception {
		FluxoDao fluxoDao = new FluxoDao();
		TransactionControler tc = new TransactionControlerImpl(new FluxoDao().getAliasDB());
		try {
			fluxoDao.setTransactionControler(tc);
			
			tc.start();
			
			FluxoDTO fluxoAuxDto = (FluxoDTO) fluxoDao.restore(fluxoDto);
			criaEstrutura(fluxoDto, tc);

			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
		return fluxoDto;
	}
	
	@Override
	public FluxoDTO criaFluxoEEstrutura(FluxoDTO fluxoDto) throws Exception {
		TipoFluxoDao tipoFluxoDao = new TipoFluxoDao();
		TipoFluxoDTO tipoFluxoDto = tipoFluxoDao.findByNome(fluxoDto.getNomeFluxo());
		if (tipoFluxoDto != null)
			throw new LogicException("Já existe um fluxo com esse nome");

		TransactionControler tc = new TransactionControlerImpl(new FluxoDao().getAliasDB());
		FluxoDao fluxoDao = new FluxoDao();
		try {
			fluxoDao.setTransactionControler(tc);
			tipoFluxoDao.setTransactionControler(tc);

			tc.start();

			tipoFluxoDto = new TipoFluxoDTO();
			tipoFluxoDto.setNomeFluxo(fluxoDto.getNomeFluxo());
			tipoFluxoDto.setDescricao(fluxoDto.getDescricao());
            tipoFluxoDto.setNomeClasseFluxo(fluxoDto.getNomeClasseFluxo());
			tipoFluxoDto = (TipoFluxoDTO) tipoFluxoDao.create(tipoFluxoDto);
			
			fluxoDto.setDataInicio(UtilDatas.getDataAtual());
			fluxoDto.setVersao("1.0");
			fluxoDto.setIdTipoFluxo(tipoFluxoDto.getIdTipoFluxo());
			fluxoDto = (FluxoDTO) fluxoDao.create(fluxoDto);
			criaEstrutura(fluxoDto, tc);

			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
		return fluxoDto;
	}
	
	public FluxoDTO create(FluxoDTO fluxoDto, TransactionControler tc) throws Exception {
		FluxoDao fluxoDao = new FluxoDao();
		TipoFluxoDao tipoFluxoDao = new TipoFluxoDao();

		fluxoDao.setTransactionControler(tc);
		tipoFluxoDao.setTransactionControler(tc);

		// Executa operacoes pertinentes ao negocio.
		fluxoDto.setDataInicio(UtilDatas.getDataAtual());
		fluxoDto.setVersao("1.0");
		TipoFluxoDTO tipoFluxoDto = tipoFluxoDao.findByNome(fluxoDto.getNomeFluxo());
		if (fluxoDto.getIdTipoFluxo() == null) {
			if (tipoFluxoDto != null)
				throw new LogicException("Já existe um fluxo com esse nome");
			tipoFluxoDto = new TipoFluxoDTO();
			tipoFluxoDto.setNomeFluxo(fluxoDto.getNomeFluxo());
			tipoFluxoDto.setDescricao(fluxoDto.getDescricao());
            tipoFluxoDto.setNomeClasseFluxo(fluxoDto.getNomeClasseFluxo());
			tipoFluxoDto = (TipoFluxoDTO) tipoFluxoDao.create(tipoFluxoDto);
			fluxoDto.setIdTipoFluxo(tipoFluxoDto.getIdTipoFluxo());
		}else{
			if (tipoFluxoDto != null && tipoFluxoDto.getIdTipoFluxo().intValue() != fluxoDto.getIdTipoFluxo().intValue())
				throw new LogicException("Já existe um fluxo com esse nome");
			tipoFluxoDto = new TipoFluxoDTO();
			tipoFluxoDto.setNomeFluxo(fluxoDto.getNomeFluxo());
			tipoFluxoDto.setIdTipoFluxo(fluxoDto.getIdTipoFluxo());
			tipoFluxoDto.setDescricao(fluxoDto.getDescricao());
            tipoFluxoDto.setNomeClasseFluxo(fluxoDto.getNomeClasseFluxo());
			tipoFluxoDao.update(tipoFluxoDto);
		}
		fluxoDto = (FluxoDTO) fluxoDao.create(fluxoDto);

		return fluxoDto;
	}

	private FluxoDTO criaEstrutura(FluxoDTO fluxoDto, TransactionControler tc) throws Exception {
		FluxoDao fluxoDao = new FluxoDao();
			
		Collection<ElementoFluxoDTO> colElementos = fluxoDto.getColElementos();
		Collection<SequenciaFluxoDTO> colSeq = fluxoDto.getColSequenciamentos();
		fluxoDao.setTransactionControler(tc);
		
		FluxoDTO fluxoAuxDto = new FluxoDTO();
		fluxoAuxDto.setIdFluxo(fluxoDto.getIdFluxo());
		fluxoAuxDto = (FluxoDTO) fluxoDao.restore(fluxoAuxDto);
		Collection colInstancias = new InstanciaFluxoDao().findByIdFluxo(fluxoAuxDto.getIdFluxo());
		if (colInstancias != null && !colInstancias.isEmpty()) {
			fluxoAuxDto.setDataFim(UtilDatas.getDataAtual());
			fluxoDao.update(fluxoAuxDto);
			fluxoDto.setIdFluxo(null);
			Double versao = new Double(fluxoDto.getVersao()) + 1;
			fluxoDto.setVersao(versao.toString());
			fluxoDto.setDataInicio(UtilDatas.getDataAtual());
			fluxoDto.setDataFim(null);
			fluxoDto = (FluxoDTO) fluxoDao.create(fluxoDto);
		}
		
		ElementoFluxoInicioDTO elementoInicioDto = null;
		List<ElementoFluxoDTO> colTarefas = new ArrayList();
		List<ElementoFluxoDTO> colScripts = new ArrayList();
        List<ElementoFluxoDTO> colEventos = new ArrayList();
		List<ElementoFluxoDTO> colEmails = new ArrayList();
		List<ElementoFluxoDTO> colPortas = new ArrayList();
		List<ElementoFluxoDTO> colFinalizacoes = new ArrayList();
		List<SequenciaFluxoDTO> colSequencias = new ArrayList();
		
		if (colElementos != null) {
			for (ElementoFluxoDTO elementoDto : colElementos) {
				elementoDto.setIdDesenho(""+elementoDto.getIdElemento());
				elementoDto.setIdElemento(null);
				if (elementoDto.getTipoElemento().equalsIgnoreCase(TipoElementoFluxo.Tarefa.name())) {
					if (elementoDto.getTipoInteracao() != null) {
						if (elementoDto.getTipoInteracao().equalsIgnoreCase("U"))
							elementoDto.setUrl(elementoDto.getInteracao());
						else
							elementoDto.setVisao(elementoDto.getInteracao());
					}
					colTarefas.add(elementoDto);
				}else if (elementoDto.getTipoElemento().equalsIgnoreCase(TipoElementoFluxo.Script.name())) {
					colScripts.add(elementoDto);
				}else if (elementoDto.getTipoElemento().equalsIgnoreCase(TipoElementoFluxo.Email.name())) {
					colEmails.add(elementoDto);
                }else if (elementoDto.getTipoElemento().equalsIgnoreCase(TipoElementoFluxo.Evento.name())) {
                    colEventos.add(elementoDto);
				}else if (elementoDto.getTipoElemento().equalsIgnoreCase(TipoElementoFluxo.Porta.name())) {
					colPortas.add(elementoDto);
				}else if (elementoDto.getTipoElemento().equalsIgnoreCase(TipoElementoFluxo.Finalizacao.name())) {
					colFinalizacoes.add(elementoDto);
				}else if (elementoDto.getTipoElemento().equalsIgnoreCase(TipoElementoFluxo.Inicio.name())) {
					elementoInicioDto = new ElementoFluxoInicioDTO();
					Reflexao.copyPropertyValues(elementoDto, elementoInicioDto);
				}
			}
		}
		
		if (colSeq != null) {
			for (SequenciaFluxoDTO sequenciaDto : colSeq) {
				sequenciaDto.setIdDesenhoOrigem(""+sequenciaDto.getIdElementoOrigem());
				sequenciaDto.setIdDesenhoDestino(""+sequenciaDto.getIdElementoDestino());
				if (sequenciaDto.getPosicaoAlterada() == null)
					sequenciaDto.setPosicaoAlterada("N");
				colSequencias.add(sequenciaDto);
			}
		}
		fluxoDto.setInicioFluxo(elementoInicioDto);
		fluxoDto.setColTarefas(colTarefas);
		fluxoDto.setColScripts(colScripts);
        fluxoDto.setColEventos(colEventos);
		fluxoDto.setColEmails(colEmails);
		fluxoDto.setColPortas(colPortas);
		fluxoDto.setColFinalizacoes(colFinalizacoes);
		fluxoDto.setColSequenciamentos(colSequencias);
		
		criaElementos(fluxoDto, tc);

		return fluxoDto;
	}

	@Override
	public void delete(IDto model) throws ServiceException, LogicException {
		FluxoDao fluxoDao = new FluxoDao();
		ElementoFluxoDao elementoFluxoDao = new ElementoFluxoDao();
		SequenciaFluxoDao sequenciaFluxoDao = new SequenciaFluxoDao();
		TipoFluxoDao tipoFluxoDao = new TipoFluxoDao();

		TransactionControler tc = new TransactionControlerImpl(fluxoDao.getAliasDB());
		try {
			validaDelete(model);

			tc.start();

			fluxoDao.setTransactionControler(tc);
			tipoFluxoDao.setTransactionControler(tc);
			elementoFluxoDao.setTransactionControler(tc);
			sequenciaFluxoDao.setTransactionControler(tc);

			FluxoDTO fluxoDto = (FluxoDTO) model;
			sequenciaFluxoDao.deleteByIdFluxo(fluxoDto.getIdFluxo());
			elementoFluxoDao.deleteAllByIdFluxo(fluxoDto.getIdFluxo());
			
			fluxoDao.delete(fluxoDto);

			TipoFluxoDTO tipoFluxoDto = new TipoFluxoDTO();
			tipoFluxoDto.setIdTipoFluxo(fluxoDto.getIdTipoFluxo());
			tipoFluxoDto = (TipoFluxoDTO) tipoFluxoDao.restore(tipoFluxoDto);
			tipoFluxoDto.setNomeFluxo(tipoFluxoDto.getNomeFluxo()+"_EXCL_FLUXO_"+fluxoDto.getIdFluxo());
			tipoFluxoDao.updateNotNull(tipoFluxoDto);
			
			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
			throw new ServiceException(e);
		}
	}

}
