package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

/**
 * @author breno.guimaraes
 * 
 */
public class ClienteEmailCentralServicoDTO implements IDto {

    private static final long serialVersionUID = 4004251311921076618L;
    /*
     * private String host = "pop.gmail.com"; private String username = "breno.centralit@gmail.com"; private String password = "brenoc3ntr0lit";
     * private String provider = "pop3s";
     */
    private String host;
    private String username;
    private String password;
    private String provider;
    private Integer port;
    private Integer idContrato;

    public ClienteEmailCentralServicoDTO() {

    }

    /**
     * @param host
     *            Exemplo: pop.gmail.com
     * @param username
     *            Exemplo: breno.centralit@gmail.com
     * @param password
     * @param provider
     *            Exemplo(gmail): pop3s Link de auxílio: https://support.google.com/mail/bin/answer.py?hl=pt-BR&answer=78799
     */
    public ClienteEmailCentralServicoDTO(String host, String username, String password, String provider, Integer port) {
	// super();
	this.setHost(host);
	this.setUsername(username);
	this.setPassword(password);
	this.setProvider(provider);
	this.setPort(port);
    }

    /**
     * @return host
     */
    public String getHost() {
	return host;
    }

    /**
     * @param host
     *            Exemplo: pop.gmail.com
     */
    public void setHost(String host) {
	this.host = host;
    }

    public String getUsername() {
	return username;
    }

    /**
     * @param username
     *            Exemplo: breno.centralit@gmail.com
     */
    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getProvider() {
	return provider;
    }

    /**
     * @param provider
     *            Exemplo(gmail): pop3s
     */
    public void setProvider(String provider) {
	this.provider = provider;
    }

    public Integer getPort() {
	return port;
    }

    public void setPort(Integer port) {
	this.port = port;
    }

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

}
