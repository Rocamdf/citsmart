package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.citframework.service.CrudServicePojo;

public interface FornecedorService extends CrudServicePojo {

	/**
	 * Retorna uma lista de escopo de fornecimento de acordo com o fornecedor passado
	 * 
	 * @param fornecedorProdutoDto
	 * @return Collection<FornecedorProdutoDTO>
	 * @throws Exception
	 * @author Thays.araujo
	 * 
	 */
	public Collection<FornecedorDTO> listEscopoFornecimento(FornecedorDTO fornecedorDtoF) throws Exception;
	
	public boolean consultarCargosAtivos(FornecedorDTO fornecedor) throws Exception;
	
	public boolean excluirFornecedor(FornecedorDTO fornecedor) throws Exception;
	
}
