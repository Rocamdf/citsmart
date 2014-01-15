package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoServicoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO;
import br.com.centralit.citcorpore.integracao.NotificacaoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoGrupoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoServicoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoUsuarioDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("serial")
public class NotificacaoServiceEjb extends CrudServicePojoImpl implements NotificacaoService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new NotificacaoDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {

	}

	@Override
	protected void validaFind(Object obj) throws Exception {

	}

	public void deletarNotificacao(IDto model) throws ServiceException, Exception {
		NotificacaoDTO notificacaoDto = (NotificacaoDTO) model;
		NotificacaoDao notificacaoDao = new NotificacaoDao();
		TransactionControler transactionControler = new TransactionControlerImpl(getDao().getAliasDB());

		try {
			validaUpdate(model);
			transactionControler.start();
			notificacaoDto.setDataFim(UtilDatas.getDataAtual());
			notificacaoDao.update(model);
			transactionControler.commit();
			transactionControler.close();
		} catch (Exception e) {
			this.rollbackTransaction(transactionControler, e);
		}

	}

	@Override
	public boolean consultarNotificacaoAtivos(NotificacaoDTO obj) throws Exception {
		NotificacaoDao dao = new NotificacaoDao();
		return dao.consultarNotificacaoAtivos(obj);
	}

	@Override
	public void update(NotificacaoDTO notificacaoDto, TransactionControler transactionControler) throws Exception {
		NotificacaoDao notificacaoDao = new NotificacaoDao();
		NotificacaoGrupoDao notificacaoGrupoDao = new NotificacaoGrupoDao();
		NotificacaoUsuarioDao notificacaoUsuarioDao = new NotificacaoUsuarioDao();
		NotificacaoServicoDao notificacaoServicoDao = new NotificacaoServicoDao();
		
		if(transactionControler == null){
			transactionControler = new TransactionControlerImpl(notificacaoDao.getAliasDB());
		}
		NotificacaoGrupoDTO notificacaoGrupoDto = new NotificacaoGrupoDTO();
		NotificacaoUsuarioDTO notificacaoUsuarioDto = new NotificacaoUsuarioDTO();
		NotificacaoServicoDTO notificacaoServicoDto = new NotificacaoServicoDTO();
		
		notificacaoDao.setTransactionControler(transactionControler);
		notificacaoGrupoDao.setTransactionControler(transactionControler);
		notificacaoUsuarioDao.setTransactionControler(transactionControler);

		notificacaoDao.update(notificacaoDto);
		/*deletando as notificações para usuario*/
		notificacaoUsuarioDao.deleteByIdNotificacaoUsuario(notificacaoDto.getIdNotificacao());
		if (notificacaoDto.getListaDeUsuario() != null) {
			if (notificacaoDto.getIdNotificacao() != null && notificacaoDto.getIdNotificacao() != 0) {
				notificacaoUsuarioDao.deleteByIdNotificacaoUsuario(notificacaoDto.getIdNotificacao());
				for (NotificacaoUsuarioDTO notificacaoUsuario : notificacaoDto.getListaDeUsuario()) {
					notificacaoUsuarioDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
					notificacaoUsuarioDto.setIdUsuario(notificacaoUsuario.getIdUsuario());
					notificacaoUsuarioDao.create(notificacaoUsuarioDto);
				}
			}

		}
		/*deletando as notificações para grupo*/
		notificacaoGrupoDao.deleteByIdNotificacaoGrupo(notificacaoDto.getIdNotificacao());		
		if (notificacaoDto.getListaDeGrupo() != null) {
			if (notificacaoDto.getIdNotificacao() != null && notificacaoDto.getIdNotificacao() != 0) {
				notificacaoGrupoDao.deleteByIdNotificacaoGrupo(notificacaoDto.getIdNotificacao());
				for (NotificacaoGrupoDTO notificacaoGrupo : notificacaoDto.getListaDeGrupo()) {
					notificacaoGrupoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
					notificacaoGrupoDto.setIdGrupo(notificacaoGrupo.getIdGrupo());
					notificacaoGrupoDao.create(notificacaoGrupoDto);
				}
			}

		}
		/*deletando as notificações para serviço*/
		notificacaoServicoDao.deleteByIdNotificacaoServico(notificacaoDto.getIdNotificacao());
		if (notificacaoDto.getListaDeServico() != null) {
			for (NotificacaoServicoDTO notificacaoServico: notificacaoDto.getListaDeServico()) {
				if (notificacaoServico.getIdServico() != null && notificacaoServico.getIdServico() != 0) {
					notificacaoServicoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
					notificacaoServicoDto.setIdServico(notificacaoServico.getIdServico());
					notificacaoServicoDao.create(notificacaoServicoDto);
				}

			}
		}

	}

	@Override
	public NotificacaoDTO create(NotificacaoDTO notificacaoDto, TransactionControler transactionControler) throws Exception {
		NotificacaoDao notificacaoDao = new NotificacaoDao();
		NotificacaoGrupoDao notificacaoGrupoDao = new NotificacaoGrupoDao();
		NotificacaoUsuarioDao notificacaoUsuarioDao = new NotificacaoUsuarioDao();
		NotificacaoServicoDao notificacaoServicoDao = new NotificacaoServicoDao();

		NotificacaoGrupoDTO notificacaoGrupoDto = new NotificacaoGrupoDTO();
		NotificacaoUsuarioDTO notificacaoUsuarioDto = new NotificacaoUsuarioDTO();
		NotificacaoServicoDTO notificacaoServicoDto = new NotificacaoServicoDTO();

		notificacaoDao.setTransactionControler(transactionControler);
		notificacaoGrupoDao.setTransactionControler(transactionControler);
		notificacaoUsuarioDao.setTransactionControler(transactionControler);
		notificacaoServicoDao.setTransactionControler(transactionControler);

		notificacaoDto.setDataInicio(UtilDatas.getDataAtual());

		notificacaoDto = (NotificacaoDTO) notificacaoDao.create(notificacaoDto);

		if (notificacaoDto.getListaDeUsuario() != null) {
			for (NotificacaoUsuarioDTO notificacaoUsuario : notificacaoDto.getListaDeUsuario()) {
				if (notificacaoUsuario.getIdUsuario() != null && notificacaoUsuario.getIdUsuario() != 0) {
					notificacaoUsuarioDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
					notificacaoUsuarioDto.setIdUsuario(notificacaoUsuario.getIdUsuario());
					notificacaoUsuarioDao.create(notificacaoUsuarioDto);
				}
			}
		}

		if (notificacaoDto.getListaDeGrupo() != null) {
			for (NotificacaoGrupoDTO notificacaoGrupo : notificacaoDto.getListaDeGrupo()) {
				if (notificacaoGrupo.getIdGrupo() != null && notificacaoGrupo.getIdGrupo() != 0) {
					notificacaoGrupoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
					notificacaoGrupoDto.setIdGrupo(notificacaoGrupo.getIdGrupo());
					notificacaoGrupoDao.create(notificacaoGrupoDto);
				}
			}
		}
		
		if (notificacaoDto.getListaDeServico() != null) {
			for (NotificacaoServicoDTO notificacaoServico: notificacaoDto.getListaDeServico()) {
				if (notificacaoServico.getIdServico() != null && notificacaoServico.getIdServico() != 0) {
					notificacaoServicoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
					notificacaoServicoDto.setIdServico(notificacaoServico.getIdServico());
					notificacaoServicoDao.create(notificacaoServicoDto);
				}
			}
		}

		return notificacaoDto;
	}

	@Override
	public Collection<NotificacaoDTO> consultarNotificacaoAtivosOrigemServico(Integer idContrato)
			throws Exception {
		NotificacaoDao dao = new NotificacaoDao();
		return dao.consultarNotificacaoAtivosOrigemServico(idContrato);
	}
	
	@Override
	public IDto create(IDto model) throws ServiceException, br.com.citframework.excecao.LogicException {
		
		NotificacaoDao notificacaoDao = new NotificacaoDao();
		NotificacaoGrupoDao notificacaoGrupoDao = new NotificacaoGrupoDao();
		NotificacaoUsuarioDao notificacaoUsuarioDao = new NotificacaoUsuarioDao();
		NotificacaoServicoDao notificacaoServicoDao = new NotificacaoServicoDao();
		
		NotificacaoDTO notificacaoDto = (NotificacaoDTO) model;
		NotificacaoGrupoDTO notificacaoGrupoDto = new NotificacaoGrupoDTO();
		NotificacaoUsuarioDTO notificacaoUsuarioDto = new NotificacaoUsuarioDTO();
		NotificacaoServicoDTO notificacaoServicoDTO = new NotificacaoServicoDTO();
		
		TransactionControler transactionControler = new TransactionControlerImpl(getDao().getAliasDB());
		
		try {
			notificacaoDao.setTransactionControler(transactionControler);
			notificacaoGrupoDao.setTransactionControler(transactionControler);
			notificacaoUsuarioDao.setTransactionControler(transactionControler);
			notificacaoServicoDao.setTransactionControler(transactionControler);
			
			transactionControler.start();
			notificacaoDto.setDataInicio(UtilDatas.getDataAtual());
			notificacaoDto = (NotificacaoDTO) notificacaoDao.create(notificacaoDto);

			if (notificacaoDto.getListaDeUsuario() != null) {
				for (NotificacaoUsuarioDTO notificacaoUsuario : notificacaoDto.getListaDeUsuario()) {
					if (notificacaoUsuario.getIdUsuario() != null && notificacaoUsuario.getIdUsuario() != 0) {
						notificacaoUsuarioDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
						notificacaoUsuarioDto.setIdUsuario(notificacaoUsuario.getIdUsuario());
						notificacaoUsuarioDao.create(notificacaoUsuarioDto);
					}
				}
			}

			if (notificacaoDto.getListaDeGrupo() != null) {
				for (NotificacaoGrupoDTO notificacaoGrupo : notificacaoDto.getListaDeGrupo()) {
					if (notificacaoGrupo.getIdGrupo() != null && notificacaoGrupo.getIdGrupo() != 0) {
						notificacaoGrupoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());
						notificacaoGrupoDto.setIdGrupo(notificacaoGrupo.getIdGrupo());
						notificacaoGrupoDao.create(notificacaoGrupoDto);
					}
				}
			}
			
			if (notificacaoDto.getListaDeServico() != null) {
				for (NotificacaoServicoDTO notificacaoServico: notificacaoDto.getListaDeServico()) {
					if (notificacaoServico.getIdServico() != null && notificacaoServico.getIdServico() != 0) {
						notificacaoServicoDTO.setIdNotificacao(notificacaoDto.getIdNotificacao());
						notificacaoServicoDTO.setIdServico(notificacaoServico.getIdServico());
						notificacaoServicoDao.create(notificacaoServicoDTO);
					}
				}
			}
			
			transactionControler.commit();
			transactionControler.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		}

		return notificacaoDto;
	}
	
	public Collection<NotificacaoDTO> listaIdContrato(Integer idContrato) throws Exception {
		NotificacaoDao dao = new NotificacaoDao();
		return dao.listaIdContrato(idContrato);
	}

	@Override
	public void updateNotNull(IDto obj) throws Exception {
		NotificacaoDao dao = new NotificacaoDao();
		dao.updateNotNull(obj);
		
	}

}

	