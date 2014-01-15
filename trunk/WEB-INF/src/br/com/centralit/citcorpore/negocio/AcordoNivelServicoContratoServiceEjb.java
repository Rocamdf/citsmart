package br.com.centralit.citcorpore.negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ResultadosEsperadosDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoContratoDao;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.ResultadosEsperadosDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;
@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class AcordoNivelServicoContratoServiceEjb extends CrudServicePojoImpl implements AcordoNivelServicoContratoService {
	protected CrudDAO getDao() throws ServiceException {
		return new AcordoNivelServicoContratoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdContrato(Integer parm) throws Exception{
		AcordoNivelServicoContratoDao dao = new AcordoNivelServicoContratoDao();
		try{
			return dao.findByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdContrato(Integer parm) throws Exception{
		AcordoNivelServicoContratoDao dao = new AcordoNivelServicoContratoDao();
		try{
			dao.deleteByIdContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean verificaDataContrato(HashMap mapFields) throws Exception {
		
		String dtInicioSLA = (String) mapFields.get("DATAINICIO");
		Integer idContrato = (Integer) Integer.parseInt((String) mapFields.get("SESSION.NUMERO_CONTRATO_EDICAO"));
		
		Date dataInicioSLA = null;
		
		try {
			dataInicioSLA = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(dtInicioSLA);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		ContratoDTO contratoDto = new ContratoDTO();
		ContratoServiceEjb contratoService = new ContratoServiceEjb();
		Date dataInicioContrato = null;
		Date dataFimContrato = null;
		
		if(idContrato != null){
			contratoDto.setIdContrato(idContrato);
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			dataInicioContrato = contratoDto.getDataContrato();
			dataFimContrato = contratoDto.getDataFimContrato();
		}else{
			return false;
		}
		
		if(dataFimContrato != null && dataInicioSLA != null && dataInicioContrato != null){
			boolean resp = UtilDatas.dataEntreIntervalo(dataInicioSLA, dataInicioContrato, dataFimContrato);
			if(resp){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}

	
	@Override
	public Collection consultaResultadosEsperados(ResultadosEsperadosDTO resultadosEsperadosDTO) throws Exception {
		ResultadosEsperadosDAO dao = new ResultadosEsperadosDAO();
		Collection colRetorno = new ArrayList();
		try {
			Collection col = dao.findByIdServicoContrato(resultadosEsperadosDTO.getIdServicoContrato());
			if (col != null && col.size() > 0){
			    for (Iterator it = col.iterator(); it.hasNext();){
					ResultadosEsperadosDTO resultados = (ResultadosEsperadosDTO)it.next();
					if(resultados.getDeleted()!=null){
						resultados.setDeleted(resultados.getDeleted().trim());
					}
					if (resultados.getDeleted() == null || resultados.getDeleted().equalsIgnoreCase("N") || resultados.getDeleted().trim().equals("")){
					    if(!consultaAcordoNivelServicoAtivo(resultados)){
					    	colRetorno.add(resultados);
					    }
					}
			    }
			}
			return colRetorno;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	private boolean consultaAcordoNivelServicoAtivo(ResultadosEsperadosDTO resultadosEsperadosDTO) throws ServiceException{
		AcordoNivelServicoDao dao = new AcordoNivelServicoDao();
		AcordoNivelServicoDTO acordoNivelServicoContratoDTO = new AcordoNivelServicoDTO();
		acordoNivelServicoContratoDTO.setIdAcordoNivelServico(resultadosEsperadosDTO.getIdAcordoNivelServico());
		try{
			acordoNivelServicoContratoDTO = (AcordoNivelServicoDTO) dao.restore(acordoNivelServicoContratoDTO);
			String situacao = acordoNivelServicoContratoDTO.getSituacao();
			if(!situacao.equalsIgnoreCase("A")){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	
}
