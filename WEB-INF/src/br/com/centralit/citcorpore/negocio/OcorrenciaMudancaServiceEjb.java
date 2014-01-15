package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.JustificativaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaMudancaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.OcorrenciaMudancaDao;
import br.com.centralit.citcorpore.integracao.OcorrenciaSolicitacaoDao;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * @author breno.guimaraes
 *
 */
public class OcorrenciaMudancaServiceEjb extends CrudServicePojoImpl implements OcorrenciaMudancaService {

    private OcorrenciaMudancaDao ocorrenciaMudancaDao;
    
    /**
     * 
     */
    private static final long serialVersionUID = 7276352361450284960L;

    @Override
    protected CrudDAO getDao() throws ServiceException {
	if(ocorrenciaMudancaDao == null){
	    ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
	}
	return ocorrenciaMudancaDao;
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
    
    public Collection findByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws Exception {
	OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
	return ocorrenciaMudancaDao.findByIdRequisicaoMudanca(idRequisicaoMudanca);
    }
    
    public static OcorrenciaMudancaDTO create(RequisicaoMudancaDTO requisicaoMudancaDto, ItemTrabalhoFluxoDTO itemTrabalhoFluxoDto, String ocorrencia, 
            OrigemOcorrencia origem, CategoriaOcorrencia categoria, String informacoesContato, String descricao,UsuarioDTO usuarioDTO, int tempo, 
            JustificativaRequisicaoMudancaDTO justificativaDto, TransactionControler tc)  throws Exception {
        OcorrenciaMudancaDTO ocorrenciaMudancaDTO = new OcorrenciaMudancaDTO();
        ocorrenciaMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
        ocorrenciaMudancaDTO.setDataregistro(UtilDatas.getDataAtual());
        ocorrenciaMudancaDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
        ocorrenciaMudancaDTO.setTempoGasto(tempo);
        ocorrenciaMudancaDTO.setDescricao(descricao);
        ocorrenciaMudancaDTO.setDataInicio(UtilDatas.getDataAtual());
        ocorrenciaMudancaDTO.setDataFim(UtilDatas.getDataAtual());
        ocorrenciaMudancaDTO.setInformacoesContato(informacoesContato);
        ocorrenciaMudancaDTO.setRegistradopor(usuarioDTO.getLogin());
        try {
			ocorrenciaMudancaDTO.setDadosMudanca(new Gson().toJson(requisicaoMudancaDto));
		} catch (Exception e) {
			System.out.println("Problema na gravação dos dados da ocorrência da mudança - Objeto GSON");
			//e.printStackTrace();
		}
        ocorrenciaMudancaDTO.setOcorrencia(ocorrencia);
        ocorrenciaMudancaDTO.setOrigem(origem.getSigla().toString());
        ocorrenciaMudancaDTO.setCategoria(categoria.getSigla());
        if (itemTrabalhoFluxoDto != null)
            ocorrenciaMudancaDTO.setIdItemTrabalho(itemTrabalhoFluxoDto.getIdItemTrabalho());
        if (justificativaDto != null) {
            ocorrenciaMudancaDTO.setIdJustificativa(justificativaDto.getIdJustificativaMudanca());
            ocorrenciaMudancaDTO.setComplementoJustificativa(justificativaDto.getDescricaoJustificativa());
        }

        OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();
        if (tc != null)
            ocorrenciaMudancaDao.setTransactionControler(tc);
        return (OcorrenciaMudancaDTO) ocorrenciaMudancaDao.create(ocorrenciaMudancaDTO);
    }
    
}
