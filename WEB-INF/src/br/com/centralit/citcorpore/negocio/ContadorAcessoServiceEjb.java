package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ContadorAcessoDTO;
import br.com.centralit.citcorpore.integracao.ContadorAcessoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("serial")
public class ContadorAcessoServiceEjb extends CrudServicePojoImpl implements ContadorAcessoService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ContadorAcessoDao();
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

	@Override
	public boolean verificarDataHoraDoContadorDeAcesso(ContadorAcessoDTO contadorDto) throws Exception {
		ContadorAcessoDao dao = new ContadorAcessoDao();
		return dao.verificarDataHoraDoContadorDeAcesso(contadorDto);
	}

	@Override
	public Integer quantidadesDeAcessoPorBaseConhecimnto(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		ContadorAcessoDao dao = new ContadorAcessoDao();
		return dao.quantidadesDeAcessoPorBaseConhecimnto(baseConhecimentoDTO);
	}

	@Override
	public Integer quantidadesDeAcessoPorPeriodo(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		ContadorAcessoDao dao = new ContadorAcessoDao();
		Integer quantidadeAcessos = 0;
		try{
			quantidadeAcessos = dao.quantidadesDeAcessoPorPeriodo(baseConhecimentoDTO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return quantidadeAcessos;
	}

}
