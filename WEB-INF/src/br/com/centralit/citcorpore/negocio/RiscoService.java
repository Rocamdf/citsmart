package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.RiscoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface RiscoService extends CrudServiceEjb2{
	boolean jaExisteRegistroComMesmoNome(RiscoDTO risco);
	public boolean existeNoBanco(RiscoDTO risco);
	ArrayList<RiscoDTO> riscoAtivo();

}
