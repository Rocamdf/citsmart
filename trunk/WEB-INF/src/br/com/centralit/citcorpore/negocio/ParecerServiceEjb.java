package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.integracao.ParecerDao;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.exception.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("serial")
public class ParecerServiceEjb extends CrudServicePojoImpl implements ParecerService {
	protected CrudDAO getDao() throws ServiceException {
		return new ParecerDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public ParecerDTO create(TransactionControler tc, UsuarioDTO usuario, Integer idJustificativa, String complementoJustificativa, String aprovado) throws Exception {
		if (aprovado == null)
			aprovado = "N";
		if (aprovado.equalsIgnoreCase("N") && idJustificativa == null)
			throw new LogicException("Justificativa não informada");

		ParecerDao parecerDao = new ParecerDao();
		parecerDao.setTransactionControler(tc);
		ParecerDTO parecerDto = new ParecerDTO();
		parecerDto.setIdResponsavel(usuario.getIdEmpregado());
		parecerDto.setIdJustificativa(idJustificativa);
		parecerDto.setComplementoJustificativa(complementoJustificativa);
		parecerDto.setAprovado(aprovado);
		parecerDto.setDataHoraParecer(UtilDatas.getDataHoraAtual());
		return (ParecerDTO) parecerDao.create(parecerDto);
	}

	public ParecerDTO createOrUpdate(TransactionControler tc, Integer idParecer, UsuarioDTO usuario, Integer idJustificativa, String complementoJustificativa, String aprovado) throws Exception {
		ParecerDao parecerDao = new ParecerDao();
		parecerDao.setTransactionControler(tc);
		ParecerDTO parecerDto = new ParecerDTO();
		if (idParecer != null && idParecer.intValue() > 0) {
			parecerDto.setIdParecer(idParecer);
			parecerDto = (ParecerDTO) parecerDao.restore(parecerDto);
			if (parecerDto.getIdResponsavel().intValue() != usuario.getIdEmpregado().intValue())
				parecerDto = new ParecerDTO();
		}
		if (aprovado == null)
			aprovado = "N";
		if (aprovado.equalsIgnoreCase("N") && idJustificativa == null)
			throw new LogicException("Justificativa não informada");

		parecerDto.setIdResponsavel(usuario.getIdEmpregado());
		parecerDto.setIdJustificativa(idJustificativa);
		parecerDto.setComplementoJustificativa(complementoJustificativa);
		parecerDto.setAprovado(aprovado);
		parecerDto.setDataHoraParecer(UtilDatas.getDataHoraAtual());
		if (parecerDto.getIdParecer() != null)
			parecerDao.update(parecerDto);
		else
			parecerDto = (ParecerDTO) parecerDao.create(parecerDto);
		return parecerDto;
	}

	@Override
	public boolean verificarSeExisteJustificativaParecer(ParecerDTO obj) throws Exception {
		ParecerDao parecerDao = new ParecerDao();
		return parecerDao.verificarSeExisteJustificativaParecer(obj);
	}
}
