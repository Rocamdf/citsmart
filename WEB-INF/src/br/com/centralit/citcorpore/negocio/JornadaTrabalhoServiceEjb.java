package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.integracao.CalendarioDao;
import br.com.centralit.citcorpore.integracao.JornadaTrabalhoDao;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
@SuppressWarnings({ "rawtypes", "serial" })
public class JornadaTrabalhoServiceEjb extends CrudServicePojoImpl implements JornadaTrabalhoService {
    protected CrudDAO getDao() throws ServiceException {
    	return new JornadaTrabalhoDao();
    }

    protected void validaCreate(Object arg0) throws Exception {
    	calculaCargaHoraria((JornadaTrabalhoDTO) arg0);
    }

    protected void validaDelete(Object arg0) throws Exception {
    }

    protected void validaFind(Object arg0) throws Exception {
    }

    protected void validaUpdate(Object arg0) throws Exception {
    	calculaCargaHoraria((JornadaTrabalhoDTO) arg0);
    }

    protected void calculaCargaHoraria(JornadaTrabalhoDTO jornadaTrabalhoDto) throws Exception {
	double cargaHoraria = 0.0;
	if (jornadaTrabalhoDto.getInicio1() != null && jornadaTrabalhoDto.getInicio1().trim().length() > 0) {
	    if (jornadaTrabalhoDto.getTermino1() == null)
		throw new Exception(i18n_Message("jornadaTrabalho.horaTerminoIntervaloUm"));
	    
	    String jornadaInicio = tratarDoisPontos(jornadaTrabalhoDto.getInicio1());
	    String jornadaTermino = tratarDoisPontos(jornadaTrabalhoDto.getTermino1());
	    
	    cargaHoraria += Util.calculaDuracao(jornadaInicio, jornadaTermino);
	    
	    
	}
	if (jornadaTrabalhoDto.getInicio2() != null && jornadaTrabalhoDto.getInicio2().trim().length() > 0) {
	    if (jornadaTrabalhoDto.getTermino2() == null)
		throw new Exception(i18n_Message("jornadaTrabalho.horaTerminoIntervaloDois"));

	    String jornadaInicio = tratarDoisPontos(jornadaTrabalhoDto.getInicio2());
	    String jornadaTermino = tratarDoisPontos(jornadaTrabalhoDto.getTermino2());
	    
	    cargaHoraria += Util.calculaDuracao(jornadaInicio, jornadaTermino);
	    
	}
	if (jornadaTrabalhoDto.getInicio3() != null && jornadaTrabalhoDto.getInicio3().trim().length() > 0) {
	    if (jornadaTrabalhoDto.getTermino3() == null)
		throw new Exception(i18n_Message("jornadaTrabalho.horaTerminoIntervaloTres"));
	    
	    String jornadaInicio = tratarDoisPontos(jornadaTrabalhoDto.getInicio3());
	    String jornadaTermino = tratarDoisPontos(jornadaTrabalhoDto.getTermino3());
	    
	    cargaHoraria += Util.calculaDuracao(jornadaInicio, jornadaTermino);
	    
	}
	if (jornadaTrabalhoDto.getInicio4() != null && jornadaTrabalhoDto.getInicio4().trim().length() > 0) {
	    if (jornadaTrabalhoDto.getTermino4() == null)
		throw new Exception(i18n_Message("jornadaTrabalho.horaTerminoIntervaloQuatro"));
	  
	    String jornadaInicio = tratarDoisPontos(jornadaTrabalhoDto.getInicio4());
	    String jornadaTermino = tratarDoisPontos(jornadaTrabalhoDto.getTermino4());
	    
	    cargaHoraria += Util.calculaDuracao(jornadaInicio, jornadaTermino);
	}
	if (jornadaTrabalhoDto.getInicio5() != null && jornadaTrabalhoDto.getInicio5().trim().length() > 0) {
	    if (jornadaTrabalhoDto.getTermino5() == null)
		throw new Exception(i18n_Message("jornadaTrabalho.horaTerminoIntervaloCinco"));
	 
	    String jornadaInicio = tratarDoisPontos(jornadaTrabalhoDto.getInicio5());
	    String jornadaTermino = tratarDoisPontos(jornadaTrabalhoDto.getTermino5());
	    
	    cargaHoraria += Util.calculaDuracao(jornadaInicio, jornadaTermino);
	}
	jornadaTrabalhoDto.setCargaHoraria(Util.getHoraFmtStr(cargaHoraria));
    }
    
    public String tratarDoisPontos(String hora) throws Exception {
	String [] arrayHora;
	    
	arrayHora = hora.split(":");
	hora = arrayHora[0] + arrayHora[1];
	
	return hora;
    }

	@Override
	public String deletarJornada(IDto model) throws ServiceException, Exception {
		
		String resp = "";
		
		JornadaTrabalhoDTO jornadaTrabalhoDTO = (JornadaTrabalhoDTO) model;
		CalendarioDao calendarioDao = new CalendarioDao();
		JornadaTrabalhoDao jornadaTrabalhoDao = new JornadaTrabalhoDao();
		
		try {
			if (calendarioDao.verificaJornada(jornadaTrabalhoDTO.getIdJornada())) {
				resp = i18n_Message("citcorpore.comum.registroNaoPodeSerExcluido");
			} else {
				jornadaTrabalhoDTO.setDataFim(UtilDatas.getDataAtual());
				jornadaTrabalhoDao.updateNotNull(jornadaTrabalhoDTO);
				resp = i18n_Message("MSG07");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
	}
	
	
	@Override
	public Collection listarJornadasAtivas() throws Exception {
		JornadaTrabalhoDao jornadaTrabalhoDao = new JornadaTrabalhoDao();
		return jornadaTrabalhoDao.listarJornadasAtivas();
	}

	@Override
	public boolean verificaJornadaExistente(JornadaTrabalhoDTO jornadaTrabalho) throws Exception {
		JornadaTrabalhoDao jornadaTrabalhoDao = new JornadaTrabalhoDao();
		return jornadaTrabalhoDao.verificaJornadaExistente(jornadaTrabalho);
	}
    
}
