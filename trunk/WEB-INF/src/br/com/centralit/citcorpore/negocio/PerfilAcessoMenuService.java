package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudService;

public interface PerfilAcessoMenuService extends CrudService{
    public Collection<PerfilAcessoMenuDTO> restoreMenusAcesso(IDto obj) throws Exception ;
    public void atualizaPerfis() throws Exception;
}
