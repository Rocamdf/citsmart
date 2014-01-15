package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.integracao.AlcadaDao;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.LimiteAlcadaDao;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.util.Enumerados.TipoAlcada;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class AlcadaServiceEjb extends CrudServicePojoImpl implements AlcadaService {
	protected CrudDAO getDao() throws ServiceException {
		return new AlcadaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
    public AlcadaDTO determinaAlcada(IDto objetoNegocioDto, CentroResultadoDTO centroCustoDto) throws Exception {
        return null;
    }
    
    public void determinaResponsaveis(AlcadaDTO alcadaDto, IDto objetoNegocioDto, EmpregadoDTO solicitante, GrupoDTO grupoDto, String abrangenciaCentroCusto, CentroResultadoDTO centroCustoDto) throws Exception {
        Collection<GrupoEmpregadoDTO> colGrupoEmpregado = new GrupoEmpregadoDao().findByIdGrupo(grupoDto.getIdGrupo());
        if (colGrupoEmpregado == null || colGrupoEmpregado.isEmpty()) 
            return; 
        Collection<EmpregadoDTO> colResponsaveis = alcadaDto.getColResponsaveis();
        if (colResponsaveis == null) {
            colResponsaveis = new ArrayList();
            alcadaDto.setColResponsaveis(colResponsaveis);
        }
        HashMap<String, EmpregadoDTO> mapResponsaveis = new HashMap();
        for (EmpregadoDTO empregadoDto : colResponsaveis) 
            mapResponsaveis.put(""+empregadoDto.getIdEmpregado(), empregadoDto);
        for (GrupoEmpregadoDTO grupoEmpregadoDto : colGrupoEmpregado) {
            if (solicitante != null && grupoEmpregadoDto.getIdEmpregado().intValue() == solicitante.getIdEmpregado().intValue())
                continue;
            if (mapResponsaveis.get(""+grupoEmpregadoDto.getIdEmpregado()) != null)
               continue;
            if (abrangenciaCentroCusto.equalsIgnoreCase("R")) {
                if (centroCustoDto.getColAlcadas() != null) {
                    for (AlcadaCentroResultadoDTO alcadaCentroResultadoDto : centroCustoDto.getColAlcadas()) {
                        if (alcadaCentroResultadoDto.getIdEmpregado() != null && alcadaCentroResultadoDto.getIdEmpregado().intValue() == grupoEmpregadoDto.getIdEmpregado().intValue()) {
                            EmpregadoDTO empregadoDto = recuperaEmpregado(grupoEmpregadoDto.getIdEmpregado());
                            if (empregadoDto != null) {
                                mapResponsaveis.put(""+empregadoDto.getIdEmpregado(), empregadoDto);
                                colResponsaveis.add(empregadoDto);
                            }
                        }
                    }
                }
            }else if (abrangenciaCentroCusto.equalsIgnoreCase("T")) {
                EmpregadoDTO empregadoDto = recuperaEmpregado(grupoEmpregadoDto.getIdEmpregado());
                if (empregadoDto != null) {
                    mapResponsaveis.put(""+empregadoDto.getIdEmpregado(), empregadoDto);
                    colResponsaveis.add(empregadoDto);
                }                
            }
        }
        if (colResponsaveis.size() == 0 && centroCustoDto.getIdCentroResultadoPai() != null) {
            CentroResultadoDTO ccustoPaiDto = new CentroResultadoDTO();
            ccustoPaiDto.setIdCentroResultado(centroCustoDto.getIdCentroResultadoPai());
            ccustoPaiDto = (CentroResultadoDTO) new CentroResultadoDao().restore(ccustoPaiDto);
            if (ccustoPaiDto != null)
                determinaResponsaveis(alcadaDto, objetoNegocioDto, solicitante, grupoDto, abrangenciaCentroCusto, ccustoPaiDto);
        }
    }

    private EmpregadoDTO recuperaEmpregado(Integer idEmpregado) throws Exception {
        EmpregadoDTO empregadoDto = new EmpregadoDTO();
        empregadoDto.setIdEmpregado(idEmpregado);
        return (EmpregadoDTO) new EmpregadoDao().restore(empregadoDto);
    }
    
	@Override
	public void deletarAlcada(IDto model, DocumentHTML document) throws ServiceException, Exception {
		AlcadaDTO alcadaDto = (AlcadaDTO) model;
		AlcadaDao alcadaDao = new AlcadaDao();
		LimiteAlcadaDao limiteAlcadaDao = new LimiteAlcadaDao();
		try {
			validaUpdate(model);
			if (limiteAlcadaDao.verificarSeAlcadaPossuiLimite(alcadaDto.getIdAlcada())) {
				document.alert(i18n_Message("citcorpore.comum.registroNaoPodeSerExcluido"));
				return;
			} else {
				alcadaDto.setSituacao("I");
				alcadaDao.update(model);
				document.alert(i18n_Message("MSG07"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean existeIgual(AlcadaDTO alcada) throws Exception {
	    AlcadaDao alcadaDao = new AlcadaDao();
		boolean result = alcadaDao.existeIgual(alcada);
		if (!result && alcada.getIdAlcada() == null && alcada.getTipoAlcada() != null) {
		    result = alcadaDao.findByTipo(TipoAlcada.valueOf(alcada.getTipoAlcada())) != null;
		}
		return result;
	}
}
