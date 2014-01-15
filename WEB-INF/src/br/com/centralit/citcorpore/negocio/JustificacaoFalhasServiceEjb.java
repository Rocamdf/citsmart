package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.JustificacaoEventoHistoricoDTO;
import br.com.centralit.citcorpore.bean.JustificacaoFalhasDTO;
import br.com.centralit.citcorpore.integracao.JustificacaoFalhasDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("serial")
public class JustificacaoFalhasServiceEjb extends CrudServicePojoImpl implements JustificacaoFalhasService{

    JustificacaoFalhasDao justificativaDao;
    public JustificacaoFalhasServiceEjb(){
	this.justificativaDao = new JustificacaoFalhasDao();
    }
    
    @Override
    public IDto create(IDto dto) {
	JustificacaoFalhasDTO justificacaoDto = (JustificacaoFalhasDTO) dto;	
	justificacaoDto.setData( UtilDatas.getDataAtual());
	justificacaoDto.setHora( UtilDatas.getHoraAtual().toString().substring(0, 4));
	IDto retorno = null;
	try {
	    retorno = super.create(justificacaoDto);
	} catch (ServiceException e) {
	    e.printStackTrace();
	} catch (LogicException e) {
	    e.printStackTrace();
	}
	return retorno;
    };

    public void teste(IDto dto){
	justificativaDao.findEventosComFalha(dto);
    }
    @Override
    protected CrudDAO getDao() throws ServiceException {
	return new JustificacaoFalhasDao();
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

    

    @Override
    public ArrayList<JustificacaoEventoHistoricoDTO> findEventosComFalha(JustificacaoFalhasDTO dto) {
	return justificativaDao.findEventosComFalha(dto);
    }

   

}
