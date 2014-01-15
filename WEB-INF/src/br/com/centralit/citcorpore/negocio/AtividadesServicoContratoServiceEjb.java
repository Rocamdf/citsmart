package br.com.centralit.citcorpore.negocio;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ComplexidadeDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.integracao.AtividadesOSDao;
import br.com.centralit.citcorpore.integracao.AtividadesServicoContratoDao;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.integracao.OSDao;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.WebUtil;

@SuppressWarnings({ "serial", "rawtypes" })
public class AtividadesServicoContratoServiceEjb extends CrudServicePojoImpl implements AtividadesServicoContratoService {

	protected CrudDAO getDao() throws ServiceException {
		return new AtividadesServicoContratoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdServicoContrato(Integer parm) throws Exception {
		AtividadesServicoContratoDao dao = new AtividadesServicoContratoDao();
		try {
			return dao.findByIdServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdServicoContrato(Integer parm) throws Exception {
		AtividadesServicoContratoDao dao = new AtividadesServicoContratoDao();
		try {
			dao.deleteByIdServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection obterAtividadesAtivasPorIdServicoContrato(Integer idServicoContrato) throws ServiceException {
		AtividadesServicoContratoDao dao = new AtividadesServicoContratoDao();
		Collection colRetorno = new ArrayList();
		try {
			Collection col = dao.obterAtividadesAtivasPorIdServicoContrato(idServicoContrato);
			if (col != null && col.size() > 0){
			    for (Iterator it = col.iterator(); it.hasNext();){
					AtividadesServicoContratoDTO atividadesServicoContratoDTO = (AtividadesServicoContratoDTO)it.next();
					if (atividadesServicoContratoDTO.getDeleted() == null || atividadesServicoContratoDTO.getDeleted().equalsIgnoreCase("N")){
					    colRetorno.add(atividadesServicoContratoDTO);
					}
			    }
			}
			return colRetorno;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * Método para atualizar a observacao de os que estão em criação
	 * 
	 * @param mapFields
	 * @throws Exception
	 */
	public boolean atualizaObservacao(HashMap mapFields) throws Exception{
		String observacao = (String) mapFields.get("OBSATIVIDADE");
		String idatividadeServico = (String) mapFields.get("IDATIVIDADESERVICOCONTRATO");
		OSDao daoOs = new OSDao();
		AtividadesOSDao daoAtividade = new AtividadesOSDao();
		try{
			List<OSDTO> respOs = daoOs.buscaOsEmCriacao();
			return daoAtividade.atualizaObservacao(Integer.parseInt(idatividadeServico), observacao, respOs);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public String calculaFormula(HashMap mapFields) throws Exception {
		
		Double result = null;
		
		//Pegando valores do parâmetro recebido
		String idContratoTxt = (String) mapFields.get("IDSERVICOCONTRATO");
		String horaTxt = (String) mapFields.get("HORA");
		String complexidade = (String) mapFields.get("COMPLEXIDADE");
		String quantidadeTxt = (String) mapFields.get("QUANTIDADE");
		String periodoTxt = (String) mapFields.get("PERIODO");
		
		//Fazendo parse dos valores
		Double hora = new Double(0);
		Integer quantidade = new Integer(0);
		DecimalFormat df = new DecimalFormat("0.00");
		
		if(horaTxt != null && !horaTxt.equals("")){
			horaTxt = horaTxt.replace(",", ".");
			hora = Double.valueOf(horaTxt);
			df.setMaximumFractionDigits(2);
			df.format(hora);
		}
		
		if(quantidadeTxt != null && !quantidadeTxt.equals("")){
			quantidade = Integer.parseInt(quantidadeTxt);
		}
		
		if(periodoTxt != null && !periodoTxt.equals("")){
			Integer periodo = Integer.parseInt(periodoTxt);
		}
		
		//Consultar valor da complexidade
		if(idContratoTxt != null && !idContratoTxt.equals("") && complexidade != null && !complexidade.equals("")){
			Integer idContrato = Integer.parseInt(idContratoTxt);
			ContratoDao contratoDao = new ContratoDao();
			Double valorComplex = contratoDao.consultaComplexidade(idContrato, complexidade);
			//Calcula custo
			result = hora*valorComplex*quantidade;
		}else{
			result = 0.0;
		}
		
		return UtilFormatacao.formatDouble(result, 2);
	}
	
	@Override
	public Double calculaFormula(AtividadesServicoContratoDTO atividadesServicoContrato) throws Exception {
		
		Double result = null;
		
		//Pegando valores do parâmetro recebido
		Integer idContratoTxt = atividadesServicoContrato.getIdServicoContrato();
		Double hora = atividadesServicoContrato.getHora();
		String complexidade = atividadesServicoContrato.getComplexidade();
		Integer quantidade = atividadesServicoContrato.getQuantidade();
//		String periodoTxt = atividadesServicoContrato.getPeriodo();

		if(idContratoTxt != null && !idContratoTxt.equals("") && complexidade != null && !complexidade.equals("")){
			Integer idContrato = idContratoTxt;
			ContratoDao contratoDao = new ContratoDao();
			Double valorComplex = contratoDao.consultaComplexidade(idContrato, complexidade);
			result = hora*valorComplex*quantidade;
		}else{
			result = 0.0;
		}
		
		return result;
	}
	@Override
	public boolean verificaComplexidade(HashMap mapFields) throws Exception {
		
		String idContratoTxt = (String) mapFields.get("IDSERVICOCONTRATO");
		
		if(idContratoTxt != null){
			Integer idContrato = Integer.parseInt(idContratoTxt);
			ContratoDao contratoDao = new ContratoDao();
			Collection listaComplexidades = contratoDao.listaComplexidadePorContrato(idContrato);
			if (listaComplexidades != null && listaComplexidades.size() > 0){
			    for (Iterator it = listaComplexidades.iterator(); it.hasNext();){
					ComplexidadeDTO complexidadeDTO = (ComplexidadeDTO) it.next();
					if (complexidadeDTO.getValorComplexidade() != null && !complexidadeDTO.getValorComplexidade().equals("")){
					    return true;
					}else{
						return false;
					}
			    }
			}else{
				return false;
			}
		}
		return false;
	}

	@Override
	public Collection preencheComboServicoContrato(HashMap mapFields) throws Exception {
		
		String idContratoTxt = (String) mapFields.get("IDCONTRATO");;
		Collection colServicoContrato = new ArrayList();
		
		if(idContratoTxt != null){
			colServicoContrato = obtemServicosContratoAtivos(Integer.parseInt(idContratoTxt));
			mapFields.put("SERVICOCONTRATOSERIALIZADO", WebUtil.serializeObjects(colServicoContrato));
			return colServicoContrato;
		}
		return colServicoContrato;
		
	}
	
	@SuppressWarnings("unchecked")
	private Collection obtemServicosContratoAtivos(Integer idServicoContrato) throws ServiceException, Exception{
		
		ServicoContratoService serviceContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		
		Collection colServicosContrato = serviceContratoService.findByIdContrato(idServicoContrato);
		
		List<ServicoContratoDTO> colFinal = new ArrayList();
		
		if (colServicosContrato != null){
			for(Iterator it = colServicosContrato.iterator(); it.hasNext();){
				ServicoContratoDTO servicoContratoAux = (ServicoContratoDTO)it.next();
				
				ServicoContratoDTO servicoContratoFinal = new ServicoContratoDTO();
				
				if (servicoContratoAux.getDeleted() != null && !servicoContratoAux.getDeleted().equalsIgnoreCase("N")){
				    continue;
				}
				if (servicoContratoAux.getDataFim() != null && !servicoContratoAux.getDataFim().after(UtilDatas.getDataAtual())) {
					continue;
				}
				
				if (servicoContratoAux.getIdServico() != null){
					ServicoDTO servicoDto = new ServicoDTO();
					servicoDto.setIdServico(servicoContratoAux.getIdServico());
					servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
					if (servicoDto != null){
						if (servicoDto.getDeleted() != null && !servicoDto.getDeleted().equalsIgnoreCase("N")){
						    continue;
						}		
						servicoContratoFinal.setIdServicoContrato(servicoContratoAux.getIdServicoContrato());
						servicoContratoFinal.setNomeServico(servicoDto.getNomeServico());
						
						colFinal.add(servicoContratoFinal);
					}
				}
			}
		}
		Collections.sort(colFinal, new ObjectSimpleComparator("getNomeServico", ObjectSimpleComparator.ASC));
		
		return colFinal;
	}
		
}
