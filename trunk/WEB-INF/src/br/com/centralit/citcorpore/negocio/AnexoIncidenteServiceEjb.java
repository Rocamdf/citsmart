/**

 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.AnexoIncidenteDTO;
import br.com.centralit.citcorpore.bean.BarraFerramentasIncidentesDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.integracao.AnexoIncidenteDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

/**
 * @author breno.guimaraes
 */
public class AnexoIncidenteServiceEjb extends CrudServicePojoImpl implements AnexoIncidenteService{

    private static final long serialVersionUID = 1366543033831132918L;
    private BarraFerramentasIncidentesDTO barraFerramentasIncidentesDTO;
    private AnexoIncidenteDAO anexoDao;
    private File diretorioAnexosIncidentes;

    public void create(Collection<UploadDTO> arquivosUpados, Integer idIncidente) throws Exception {
	copiaArquivosEPersisteReferencias(arquivosUpados, idIncidente);
    }

    /**
     * Copia da pasta temporária para a pasta definitiva definida nas constantes e persiste no banco
     * os relacionamentos entre anexos e serviços.
     * @author breno.guimaraes
     * @param arquivosUpados
     * @param idIncidente
     * @throws IOException
     * @throws Exception
     */
    private void copiaArquivosEPersisteReferencias(Collection<UploadDTO> arquivosUpados, Integer idIncidente) throws IOException, Exception {
	if (arquivosUpados == null || arquivosUpados.isEmpty()) {
	    return;
	}
	for (UploadDTO arquivo : arquivosUpados) {
	    File file = new File(arquivo.getPath());

	    if (!file.getAbsolutePath().equalsIgnoreCase(this.getDiretorioAnexosIncidentes().getAbsoluteFile() + File.separator + file.getName())) {
		this.copiarArquivo(file, this.getDiretorioAnexosIncidentes() + File.separator + file.getName());

		if (file.getAbsolutePath().indexOf("tempUpload") > 1) {
		    file.delete();
		}
		this.criarAnexoBaseConhecimento(idIncidente, arquivo);
	    }
	}
    }

    private void criarAnexoBaseConhecimento(Integer idIncidente, UploadDTO arquivo) throws Exception {
	AnexoIncidenteDTO anexoIncidente = new AnexoIncidenteDTO();
	anexoIncidente.setDataInicio(UtilDatas.getDataAtual());

	String extensao[] = arquivo.getNameFile().split("\\.");
	if(extensao.length > 1){
	    anexoIncidente.setExtensao(extensao[extensao.length-1]);
	}
	anexoIncidente.setNomeAnexo(extensao[0]);

	anexoIncidente.setIdIncidente(idIncidente);
	anexoIncidente.setLink(this.getDiretorioAnexosIncidentes().getAbsolutePath() + File.separator + arquivo.getNameFile());
	anexoIncidente.setDescricao(arquivo.getDescricao());
	
	this.getAnexoIncidenteDao().create(anexoIncidente);
    }


    public void copiarArquivo(File fonte, String destino) throws IOException {
	InputStream in = null;
	OutputStream out = null;
	try {
	    in = new FileInputStream(fonte);
	    out = new FileOutputStream(new File(destino));

	    byte[] buf = new byte[1024];
	    int len;
	    try {
		while ((len = in.read(buf)) > 0) {
		    out.write(buf, 0, len);
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    
	} catch (FileNotFoundException e) {
	    System.out.println("Arquivo não encontrado");
	    e.printStackTrace();
	} finally{
		if(in != null){
			in.close();
		}
		if(out != null){
			out.close();
		}
	}
    }   
    
    private AnexoIncidenteDAO getAnexoIncidenteDao(){
	try {
	    return (AnexoIncidenteDAO) getDao();
	} catch (ServiceException e) {
	    e.printStackTrace();
	}
	return null;
    }
    
    @Override
    protected CrudDAO getDao() throws ServiceException {
	if(anexoDao == null){
	    anexoDao = new AnexoIncidenteDAO();
	}
	return anexoDao;
    }

    private BarraFerramentasIncidentesDTO getBarraFerramentasIncidentesBean() {
	if(barraFerramentasIncidentesDTO == null){
	    barraFerramentasIncidentesDTO = new BarraFerramentasIncidentesDTO();
	}
	return barraFerramentasIncidentesDTO;
    }

    private File getDiretorioAnexosIncidentes() {
	if( diretorioAnexosIncidentes == null ){
	    diretorioAnexosIncidentes = new File(Constantes.getValue("DIRETORIO_ANEXOS_INCIDENTES"));
	}
	return diretorioAnexosIncidentes;
    }

    
    @Override
    protected void validaCreate(Object arg0) throws Exception {
	
    }
    
    @Override
    protected void validaDelete(Object arg0) throws Exception {
	
    }
    
    @Override
    protected void validaFind(Object arg0) throws Exception {
	
    }
    
    @Override
    protected void validaUpdate(Object arg0) throws Exception {
	
    }

    @Override
    public Collection<AnexoIncidenteDTO> consultarAnexosIncidentes(Integer idIncidente) throws Exception {
	ArrayList<Condition> condicoes = new ArrayList<Condition>();
	condicoes.add(new Condition("idIncidente", "=", idIncidente));
	return getAnexoIncidenteDao().findByCondition(condicoes, null);
    }

}
