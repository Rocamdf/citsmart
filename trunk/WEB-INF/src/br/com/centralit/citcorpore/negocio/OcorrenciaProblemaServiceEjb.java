package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import com.google.gson.Gson;

import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.citcorpore.bean.JustificativaProblemaDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaProblemaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.OcorrenciaProblemaDAO;
import br.com.centralit.citcorpore.integracao.OcorrenciaSolicitacaoDao;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("rawtypes")
public class OcorrenciaProblemaServiceEjb extends CrudServicePojoImpl implements OcorrenciaProblemaService {
		
	private OcorrenciaProblemaDAO ocorrenciaProblemaDao;
	/**
	 * @author geber.costa
	 */
	private static final long serialVersionUID = -6218197888967378097L;
	

	protected CrudDAO getDao() throws ServiceException {
		if (ocorrenciaProblemaDao == null) {
			ocorrenciaProblemaDao = new OcorrenciaProblemaDAO();
		}
		return ocorrenciaProblemaDao;
	}

	@Override
	public IDto create(IDto model) throws ServiceException, LogicException {
		return super.create(model);

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

	public Collection findByIdProblema(Integer idProblema) throws Exception {
		OcorrenciaProblemaDAO ocorrenciaProblemaDAO = new OcorrenciaProblemaDAO();
		return ocorrenciaProblemaDAO.findByIdProblema(idProblema);
	}
	
	public static OcorrenciaProblemaDTO create(ProblemaDTO problemaDto, ItemTrabalhoFluxoDTO itemTrabalhoFluxoDto, String ocorrencia, 
            OrigemOcorrencia origem, CategoriaOcorrencia categoria, String informacoesContato, String descricao, String loginUsuario, int tempo, 
            JustificativaProblemaDTO justificativaDto, TransactionControler tc)  throws Exception {
        OcorrenciaProblemaDTO ocorrenciaProblemaDTO = new OcorrenciaProblemaDTO();
        ocorrenciaProblemaDTO.setIdProblema(problemaDto.getIdProblema());
        ocorrenciaProblemaDTO.setDataregistro(UtilDatas.getDataAtual());
        ocorrenciaProblemaDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
        ocorrenciaProblemaDTO.setTempoGasto(tempo);
        ocorrenciaProblemaDTO.setDescricao(descricao);
        ocorrenciaProblemaDTO.setDataInicio(UtilDatas.getDataAtual());
        ocorrenciaProblemaDTO.setDataFim(UtilDatas.getDataAtual());
        ocorrenciaProblemaDTO.setInformacoesContato(informacoesContato);
        ocorrenciaProblemaDTO.setRegistradopor(loginUsuario);
        try {
			ocorrenciaProblemaDTO.setDadosProblema(new Gson().toJson(problemaDto));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ocorrenciaProblemaDTO.setOcorrencia(ocorrencia);
        ocorrenciaProblemaDTO.setOrigem(origem.getSigla().toString());
        ocorrenciaProblemaDTO.setCategoria(categoria.getSigla());
        if (itemTrabalhoFluxoDto != null)
        	ocorrenciaProblemaDTO.setIdItemTrabalho(itemTrabalhoFluxoDto.getIdItemTrabalho());
        if (justificativaDto != null) {
        	ocorrenciaProblemaDTO.setIdJustificativa(justificativaDto.getIdJustificativaProblema());
        	ocorrenciaProblemaDTO.setComplementoJustificativa(justificativaDto.getDescricaoProblema());
        }

        OcorrenciaProblemaDAO ocorrenciaProblemaDao = new OcorrenciaProblemaDAO();
        if (tc != null)
        	ocorrenciaProblemaDao.setTransactionControler(tc);
        return (OcorrenciaProblemaDTO) ocorrenciaProblemaDao.create(ocorrenciaProblemaDTO);
    }

}
