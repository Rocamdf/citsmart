package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.mail.Message;
import javax.mail.MessagingException;

import microsoft.exchange.webservices.data.Item;

import br.com.centralit.citcorpore.bean.ClienteEmailCentralServicoDTO;
import br.com.centralit.citcorpore.integracao.ClienteEmailCentralServicoDao;
import br.com.citframework.excecao.LogicException;

/**
 * @author breno.guimaraes
 * 
 */
public class ClienteEmailCentralServicoServiceEjb implements ClienteEmailCentralServicoService {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4101073129183404628L;
    private ClienteEmailCentralServicoDao dao;

    public ArrayList<Item> getMails() throws MessagingException, LogicException {
	// posteriormente validar dados
	return getDao().getMails();
    }

    public ClienteEmailCentralServicoDao getDao() {
	if (dao == null) {
	    this.dao = new ClienteEmailCentralServicoDao();
	}
	return dao;
    }
}
