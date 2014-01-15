package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.bpm.integracao.HistoricoItemTrabalhoDao;
import br.com.centralit.citcorpore.bean.BaseItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.CaracteristicaTipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.BaseItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.CaracteristicaDao;
import br.com.centralit.citcorpore.integracao.CaracteristicaTipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.HistoricoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.HistoricoLiberacaoDao;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.TipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.ValorDao;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;


@SuppressWarnings({"unused" })
public class HistoricoLiberacaoServiceEjb extends CrudServicePojoImpl implements HistoricoLiberacaoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6547172240946338384L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new HistoricoLiberacaoDao();
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
	
	public List<HistoricoLiberacaoDTO> listHistoricoLiberacaoByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws Exception{
		HistoricoLiberacaoDao historicoLiberacaoDAO = new HistoricoLiberacaoDao();
		return historicoLiberacaoDAO.listHistoricoLiberacaoByIdRequisicaoLiberacao(idRequisicaoLiberacao);
	}
	
	/*public List<HistoricoItemConfiguracaoDTO> listHistoricoItemCfValorByIdHistoricoIC(Integer idHistoricoIC) throws Exception{
		HistoricoItemConfiguracaoDAO historicoItemdao = new HistoricoItemConfiguracaoDAO();
		return historicoItemdao.listHistoricoItemCfValorByIdHistoricoIC(idHistoricoIC);
	}*/

	@Override
	public void updateNotNull(IDto obj) throws Exception {
		HistoricoLiberacaoDao historicoItemdao = new HistoricoLiberacaoDao();
		historicoItemdao.updateNotNull(obj);
	}

	public HistoricoLiberacaoDTO maxIdHistorico(RequisicaoLiberacaoDTO i) throws Exception {
		HistoricoLiberacaoDao historicoLiberacaoDao = new HistoricoLiberacaoDao();
		return historicoLiberacaoDao.maxIdHistorico(i);
	}

}
