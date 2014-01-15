package br.com.centralit.citcorpore.rh.negocio;
import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.rh.bean.CargoCertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CargoFormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.bean.CargoIdiomaDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.CertificacaoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.CurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.FormacaoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.IdiomaCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.TriagemRequisicaoPessoalDao;
import br.com.centralit.citcorpore.util.Enumerados.TipoEntrevista;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
public class TriagemRequisicaoPessoalServiceEjb extends CrudServicePojoImpl implements TriagemRequisicaoPessoalService {
	protected CrudDAO getDao() throws ServiceException {
		return new TriagemRequisicaoPessoalDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	
	public Collection<CurriculoDTO> sugereCurriculos(RequisicaoPessoalDTO requisicaoPessoalDto,DescricaoCargoDTO descricaoCargoDto) throws Exception {

		CurriculoService curriculoService = (CurriculoService) ServiceLocator.getInstance().getService(CurriculoService.class, null);
	//forcei para teste tirar depois.
		requisicaoPessoalDto.setChkFormacao("F");
		@SuppressWarnings("unchecked")
		Collection<CurriculoDTO> curriculos = new CurriculoDao().list();
		
		Collection<CurriculoDTO> curriculosTriados = new ArrayList<CurriculoDTO>();
		
		if(descricaoCargoDto.getColCertificacao()!=null) {
			if(requisicaoPessoalDto.getChkCertificacao().equalsIgnoreCase("C")) {
				CertificacaoCurriculoDao certificacaoCurriculoDao = new CertificacaoCurriculoDao();
				for (CurriculoDTO curriculoDTO : curriculos) {
					Collection<CertificacaoCurriculoDTO> colCertificacao = certificacaoCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());
					Collection<CargoCertificacaoDTO> colCargoCertificacao = descricaoCargoDto.getColCertificacao();
					for (CertificacaoCurriculoDTO certificacaoCurriculoDto: colCertificacao) {
						for(CargoCertificacaoDTO cargoCertificacaoDto: descricaoCargoDto.getColCertificacao()) {
							if (certificacaoCurriculoDto.getDescricao().contains(cargoCertificacaoDto.getDescricao())) {
								curriculosTriados.add(curriculoDTO);
							}
						}
					}
				}
			}
		}
		
		if(descricaoCargoDto.getColFormacaoAcademica()!=null) {
			if(requisicaoPessoalDto.getChkFormacao().equalsIgnoreCase("F")) {
				FormacaoCurriculoDao formacaoCurriculoDao = new FormacaoCurriculoDao();
				for (CurriculoDTO curriculoDTO : curriculos) {
					Collection<FormacaoCurriculoDTO> colFormacao = formacaoCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());
					Collection<CargoFormacaoAcademicaDTO> colCargoFormacaoAcademica = descricaoCargoDto.getColFormacaoAcademica();
					for (FormacaoCurriculoDTO formacaoCurriculoDTO: colFormacao) {
						for(CargoFormacaoAcademicaDTO cargoFormacaoAcademicaDTO: descricaoCargoDto.getColFormacaoAcademica()) {
							if (formacaoCurriculoDTO.getDescricao().contains(cargoFormacaoAcademicaDTO.getDescricao())) {
								if(validaInsercao(curriculosTriados, curriculoDTO.getIdCurriculo()))
								  curriculosTriados.add(curriculoDTO);
							}
						}
					}
				}
			}
		}
		
		if(descricaoCargoDto.getColIdioma()!=null) {
			if(requisicaoPessoalDto.getChkIdioma().equalsIgnoreCase("I")) {
				IdiomaCurriculoDao idiomaCurriculoDao = new IdiomaCurriculoDao();
				for (CurriculoDTO curriculoDTO : curriculos) {
					Collection<IdiomaCurriculoDTO> colIdioma = idiomaCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());
					Collection<CargoIdiomaDTO> colCargoIdioma = descricaoCargoDto.getColIdioma();
					for (IdiomaCurriculoDTO idiomaCurriculoDTO: colIdioma) {
						for(CargoIdiomaDTO cargoIdiomaDTO: descricaoCargoDto.getColIdioma()) {
							if (idiomaCurriculoDTO.getIdIdioma().intValue() == cargoIdiomaDTO.getIdIdioma().intValue()) {
								if(validaInsercao(curriculosTriados, curriculoDTO.getIdCurriculo()))
								  curriculosTriados.add(curriculoDTO);
							}
						}
					}
				}
			}
		}
		
		return curriculosTriados;
	}
	public Collection<CurriculoDTO> triagemManualPorCriterios(RequisicaoPessoalDTO requisicaoPessoalDTO) throws Exception {
		return new CurriculoDao().listaCurriculosPorCriterios(requisicaoPessoalDTO);
	}
	public boolean validaInsercao(Collection<CurriculoDTO> colecao, int id) throws Exception {
		for (CurriculoDTO curriculoDTO : colecao) {
			if(curriculoDTO.getIdCurriculo().intValue() == id)
				return false;
		}
		return true;
	}
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception{
		TriagemRequisicaoPessoalDao dao = new TriagemRequisicaoPessoalDao();
		try{
			return dao.findByIdSolicitacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdSolicitacaoServico(Integer parm) throws Exception{
		TriagemRequisicaoPessoalDao dao = new TriagemRequisicaoPessoalDao();
		try{
			dao.deleteByIdSolicitacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection findByIdSolicitacaoServicoAndIdTarefa(Integer idSolicitacaoServico, Integer idTarefa) throws Exception {
		Collection<TriagemRequisicaoPessoalDTO> result = new ArrayList();
		Collection<TriagemRequisicaoPessoalDTO> colTriagem = new TriagemRequisicaoPessoalDao().findByIdSolicitacaoServico(idSolicitacaoServico);
		if (colTriagem != null) {
			for (TriagemRequisicaoPessoalDTO triagemDto : colTriagem) {
				boolean bValido = false;
				bValido = triagemDto.getIdItemTrabalhoEntrevistaGestor() != null && triagemDto.getIdItemTrabalhoEntrevistaGestor().intValue() == idTarefa.intValue();
				if (bValido){
					triagemDto.setTipoEntrevista(TipoEntrevista.Gestor.name());
				}else{
					bValido = triagemDto.getIdItemTrabalhoEntrevistaRH() != null && triagemDto.getIdItemTrabalhoEntrevistaRH().intValue() == idTarefa.intValue();
					if (bValido)
						triagemDto.setTipoEntrevista(TipoEntrevista.RH.name());
				}
				if (bValido) 
					result.add(triagemDto);
			}
		}
		return result;
	}

	@Override
	public Collection<CurriculoDTO> triagemManual() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
