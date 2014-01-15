package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;

public interface VisaoService extends CrudServicePojo {
	
	public Collection listAtivos() throws Exception;
	
	public VisaoDTO findByIdentificador(String identificador) throws Exception;
	
	public void deleteVisao(IDto model) throws Exception;
	
	public void importar(VisaoDTO visaoXML) throws Exception;
	
	public void atualizarVisao(VisaoDTO visaoAtual, VisaoDTO visaoXML) throws Exception;
	
	public VisaoDTO visaoExistente(String identificadorVisao) throws ServiceException, Exception;
	
}
