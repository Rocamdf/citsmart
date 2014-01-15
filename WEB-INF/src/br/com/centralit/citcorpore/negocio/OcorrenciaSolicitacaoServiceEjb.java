package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Date;

import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
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

import com.google.gson.Gson;

/**
 * @author breno.guimaraes
 *
 */
public class OcorrenciaSolicitacaoServiceEjb extends CrudServicePojoImpl implements OcorrenciaSolicitacaoService {

    private OcorrenciaSolicitacaoDao ocorrenciaSolicitacaoDao;
    
    /**
     * 
     */
    private static final long serialVersionUID = 7276352361450284960L;

    @Override
    protected CrudDAO getDao() throws ServiceException {
	if(ocorrenciaSolicitacaoDao == null){
	    ocorrenciaSolicitacaoDao = new OcorrenciaSolicitacaoDao();
	}
	return ocorrenciaSolicitacaoDao;
    }
    
    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        return super.create(model);
    }

    @Override
    protected void validaCreate(Object arg0) throws Exception {

    }

    @Override
    protected void validaDelete(Object arg0) throws Exception {

    }

    @Override
    protected void validaFind(Object arg0) throws Exception {

    }

    @Override
    protected void validaUpdate(Object arg0) throws Exception {

    }
    
    public Collection findByIdSolicitacaoServico(Integer idSolicitacaoServicoParm) throws Exception {
	OcorrenciaSolicitacaoDao ocorrenciaSolicitacaoDao = new OcorrenciaSolicitacaoDao();
	return ocorrenciaSolicitacaoDao.findByIdSolicitacaoServico(idSolicitacaoServicoParm);
    }
    
    public OcorrenciaSolicitacaoDTO findUltimoByIdSolicitacaoServico(Integer idSolicitacaoServicoParm) throws Exception {
    	OcorrenciaSolicitacaoDao ocorrenciaSolicitacaoDao = new OcorrenciaSolicitacaoDao();
    	return ocorrenciaSolicitacaoDao.findUltimoByIdSolicitacaoServico(idSolicitacaoServicoParm);
        }
    
    public Collection findOcorrenciasDoTesteIdSolicitacaoServico(Integer idSolicitacaoServicoParm) throws Exception {
    	OcorrenciaSolicitacaoDao ocorrenciaSolicitacaoDao = new OcorrenciaSolicitacaoDao();
    	return ocorrenciaSolicitacaoDao.findOcorrenciaDoTesteByIdSolicitacaoServico(idSolicitacaoServicoParm);
      }
    
    public static OcorrenciaSolicitacaoDTO create(SolicitacaoServicoDTO solicitacaoServicoDto, ItemTrabalhoFluxoDTO itemTrabalhoFluxoDto, String ocorrencia, 
            OrigemOcorrencia origem, CategoriaOcorrencia categoria, String informacoesContato, String descricao,UsuarioDTO usuarioDTO, int tempo, 
            JustificativaSolicitacaoDTO justificativaDto, TransactionControler tc)  throws Exception {
        OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO = new OcorrenciaSolicitacaoDTO();
        ocorrenciaSolicitacaoDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
        ocorrenciaSolicitacaoDTO.setDataregistro(UtilDatas.getDataAtual());
        ocorrenciaSolicitacaoDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
        ocorrenciaSolicitacaoDTO.setTempoGasto(tempo);
        ocorrenciaSolicitacaoDTO.setDescricao(descricao);
        ocorrenciaSolicitacaoDTO.setDataInicio(UtilDatas.getDataAtual());
        ocorrenciaSolicitacaoDTO.setDataFim(UtilDatas.getDataAtual());
        ocorrenciaSolicitacaoDTO.setInformacoesContato(informacoesContato);
        ocorrenciaSolicitacaoDTO.setRegistradopor(usuarioDTO.getLogin());
        try {
			ocorrenciaSolicitacaoDTO.setDadosSolicitacao(new Gson().toJson(solicitacaoServicoDto));
		} catch (Exception e) {
			System.out.println("Problema na gravação dos dados da solicitação de serviço de id " + solicitacaoServicoDto.getIdSolicitacaoServico());
			e.printStackTrace();
		}
        ocorrenciaSolicitacaoDTO.setOcorrencia(ocorrencia);
        ocorrenciaSolicitacaoDTO.setOrigem(origem.getSigla().toString());
        ocorrenciaSolicitacaoDTO.setCategoria(categoria.getSigla());
        if (itemTrabalhoFluxoDto != null)
            ocorrenciaSolicitacaoDTO.setIdItemTrabalho(itemTrabalhoFluxoDto.getIdItemTrabalho());
        if (justificativaDto != null) {
            ocorrenciaSolicitacaoDTO.setIdJustificativa(justificativaDto.getIdJustificativa());
            ocorrenciaSolicitacaoDTO.setComplementoJustificativa(justificativaDto.getComplementoJustificativa());
        }

        OcorrenciaSolicitacaoDao ocorrenciaSolicitacaoDao = new OcorrenciaSolicitacaoDao();
        if (tc != null)
            ocorrenciaSolicitacaoDao.setTransactionControler(tc);
        return (OcorrenciaSolicitacaoDTO) ocorrenciaSolicitacaoDao.create(ocorrenciaSolicitacaoDTO);
    }
    
    @Override
	public Collection<OcorrenciaSolicitacaoDTO> findByIdPessoaEDataAtendidasGrupoTeste(Integer idPessoa, Date dataInicio, Date dataFim) throws Exception {
    	OcorrenciaSolicitacaoDao dao = new OcorrenciaSolicitacaoDao();
		try {
			return dao.findByIdPessoaEDataAtendidasGrupoTeste(idPessoa, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
    
    @Override
	public Collection<OcorrenciaSolicitacaoDTO> findByIdPessoaGrupoTeste(Integer idPessoa, Date dataInicio, Date dataFim) throws Exception {
    	OcorrenciaSolicitacaoDao dao = new OcorrenciaSolicitacaoDao();
		try {
			return dao.findByIdPessoaGrupoTeste(idPessoa, dataInicio, dataFim);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public OcorrenciaSolicitacaoDTO findByIdOcorrencia(Integer idOcorrencia) throws Exception {
	OcorrenciaSolicitacaoDao dao = new OcorrenciaSolicitacaoDao();
	try {
		return dao.findByIdOcorrencia(idOcorrencia);
	} catch (Exception e) {
		throw new ServiceException(e);
	}
	}

    
}
