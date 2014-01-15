package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CentroResultadoDTO;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

@SuppressWarnings("rawtypes")
public interface CentroResultadoService extends CrudServiceEjb2 {
	
	public Collection list() throws ServiceException, LogicException;
	public Collection listAtivos() throws Exception;
	public Collection listPermiteRequisicaoProduto() throws Exception;
	public Collection findByIdPai(Integer idPai) throws Exception;
	public Collection findSemPai() throws Exception;
	public Collection find(CentroResultadoDTO centroResultadoDTO) throws Exception;
	
	public boolean temFilhos(int idCentroResultado) throws Exception;

	public void recuperaImagem(CentroResultadoDTO centroResultadoDTO) throws Exception;
	
	public Collection findByIdAlcada(Integer idAlcada) throws Exception;
}
