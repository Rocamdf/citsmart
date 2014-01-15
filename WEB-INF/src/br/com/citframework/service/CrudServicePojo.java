package br.com.citframework.service;


import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;

public interface CrudServicePojo extends CrudService{
	
	
	/**
	 * @param model
	 * @throws DAOException
	 * Grava o IModel passado como parametro em meio persistente.
	 */
	public abstract IDto create( IDto model) throws  LogicException,ServiceException;

	/** 
	 * @param model
	 * @throws DAOException
	 * recebe um Object com seus atributos chave preenchidos, 
	 * recupera todos os atributos do meio persistente e 
	 * retorna o Object Preenchido.
	 */
	public abstract IDto restore(IDto model) throws  LogicException,ServiceException;

	/**
	 * @param model
	 * @throws DAOException
	 * Atualiza o Object passado como parametro em meio persistente.
	 */
	public abstract void update(IDto model) throws  LogicException,ServiceException;

	/**
	 * @param model
	 * @throws DAOException
	 * Exclui o Object passado como parametro do meio persistente.
	 */
	public abstract void delete(IDto model) throws  LogicException,ServiceException;
    
    public Collection find(IDto obj) throws  LogicException, ServiceException;
    
    public abstract Collection list() throws  LogicException, ServiceException;


}
