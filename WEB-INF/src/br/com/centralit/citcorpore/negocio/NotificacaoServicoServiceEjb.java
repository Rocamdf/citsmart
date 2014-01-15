package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoServicoDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("serial")
public class NotificacaoServicoServiceEjb extends CrudServicePojoImpl implements NotificacaoServicoService {

	@Override
	protected CrudDAO getDao() throws ServiceException {

		return new NotificacaoServicoDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<NotificacaoServicoDTO> listaIdServico(Integer idServico) throws Exception {
		NotificacaoServicoDao dao = new NotificacaoServicoDao();
		return dao.listaIdServico(idServico);
	}

	@Override
	public Collection<NotificacaoServicoDTO> listaIdNotificacao(Integer idNotificacao) throws Exception {
		NotificacaoServicoDao dao = new NotificacaoServicoDao();
		return dao.listaIdNotificacao(idNotificacao);
	}

	@Override
	public boolean existeServico(Integer idNotificacao, Integer idservico) throws Exception {
		NotificacaoServicoDao dao = new NotificacaoServicoDao();
		return dao.existeServico(idNotificacao, idservico);
	}
	public void enviarEmailNotificacao(ServicoDTO servicoDto) throws Exception {
		try{
			EmpregadoDao empregadoDao = new EmpregadoDao();
			Collection<EmpregadoDTO> colEmpregados = new ArrayList();
			String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
			
			String ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO, "");
			if (ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO != null && !ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO.isEmpty()) {
	
				colEmpregados = empregadoDao.listarEmailsNotificacoesServico(servicoDto.getIdServico());
	
				if (colEmpregados != null) {
	
					for (EmpregadoDTO empregados : colEmpregados) {
	
						MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL_AVISAR_ALTERACAO_SERVICO.trim()), new IDto[] { servicoDto });
	
						if (empregados.getEmail() != null) {
							mensagem.envia(empregados.getEmail(), "", remetente);
						}
	
					}
				}
	
			}		
		}catch(Exception e){
		}
	}

}
