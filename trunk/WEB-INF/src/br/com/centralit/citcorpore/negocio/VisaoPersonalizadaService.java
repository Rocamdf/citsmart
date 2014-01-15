package br.com.centralit.citcorpore.negocio;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface VisaoPersonalizadaService extends CrudServicePojo {
    public void deleteAll() throws Exception;
}
