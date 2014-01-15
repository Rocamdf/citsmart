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
import br.com.centralit.citcorpore.bean.AnexoMudancaDTO;
import br.com.centralit.citcorpore.bean.BarraFerramentasIncidentesDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.integracao.AnexoIncidenteDAO;
import br.com.centralit.citcorpore.integracao.AnexoMudancaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

/**
 * @author breno.guimaraes
 */
public class AnexoMudancaServiceEjb extends CrudServicePojoImpl implements AnexoMudancaService{

    private static final long serialVersionUID = 1366543033831132918L;
    private RequisicaoMudancaDTO requisicaoMudancaDTO;
    private AnexoMudancaDAO anexoDao;
    private File diretorioAnexosMudancas;

    public void create(Collection<UploadDTO> arquivosUpados, Integer idMudanca) throws Exception {
    	copiaArquivosEPersisteReferencias(arquivosUpados, idMudanca);
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
    private void copiaArquivosEPersisteReferencias(Collection<UploadDTO> arquivosUpados, Integer idMudanca) throws IOException, Exception {
		if (arquivosUpados == null || arquivosUpados.isEmpty()) {
		    return;
		}
		for (UploadDTO arquivo : arquivosUpados) {
		    File file = new File(arquivo.getPath());
	
		    if (!file.getAbsolutePath().equalsIgnoreCase(this.getDiretorioAnexosMudancas().getAbsoluteFile() + File.separator + file.getName())) {
		    	this.copiarArquivo(file, this.getDiretorioAnexosMudancas() + File.separator + file.getName());
	
				if (file.getAbsolutePath().indexOf("tempUpload") > 1) {
				    file.delete();
				}
					this.criarAnexoBaseConhecimento(idMudanca, arquivo);
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
			anexoIncidente.setLink(this.getDiretorioAnexosMudancas().getAbsolutePath() + File.separator + arquivo.getNameFile());
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
	    anexoDao = new AnexoMudancaDAO();
	}
		return anexoDao;
    }

    private RequisicaoMudancaDTO getRequisicaoMudancaBean() {
	if(requisicaoMudancaDTO == null){
		requisicaoMudancaDTO = new RequisicaoMudancaDTO();
	}
		return requisicaoMudancaDTO;
    }

    private File getDiretorioAnexosMudancas() {
	if( diretorioAnexosMudancas == null ){
		diretorioAnexosMudancas = new File(Constantes.getValue("DIRETORIO_ANEXOS_MUDANCAS"));
	}
		return diretorioAnexosMudancas;
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
    public Collection<AnexoMudancaDTO> consultarAnexosMudanca(Integer idMudanca) throws Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idMudanca", "=", idMudanca));
		return getAnexoIncidenteDao().findByCondition(condicoes, null);
    }

}
