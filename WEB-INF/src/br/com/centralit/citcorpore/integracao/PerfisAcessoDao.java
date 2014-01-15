package br.com.centralit.citcorpore.integracao;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemConfigEventoDTO;
import br.com.centralit.citcorpore.bean.PerfisAcessoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.util.Constantes;

public class PerfisAcessoDao  extends CrudDaoDefaultImpl{

  

    public PerfisAcessoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"),null);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Collection find(IDto arg0) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection getFields() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String getTableName() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection list() throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Class getBean() {
	return PerfisAcessoDTO.class;
    }

}
