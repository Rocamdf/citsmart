package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.mail.Message;
import javax.mail.MessagingException;

import microsoft.exchange.webservices.data.Item;

import br.com.centralit.citcorpore.bean.ClienteEmailCentralServicoDTO;
import br.com.citframework.excecao.LogicException;

/**
 * @author breno.guimaraes
 * 
 */
public interface ClienteEmailCentralServicoService {

    ArrayList<Item> getMails() throws MessagingException, LogicException;

}
