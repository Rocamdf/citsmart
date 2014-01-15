package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.EventoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.JustificacaoEventoHistoricoDTO;
import br.com.centralit.citcorpore.bean.JustificacaoFalhasDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudServiceEjb2;

public interface JustificacaoFalhasService extends CrudServiceEjb2{
    void teste(IDto dto);
    ArrayList<JustificacaoEventoHistoricoDTO> findEventosComFalha(JustificacaoFalhasDTO dto);
    
    
}
