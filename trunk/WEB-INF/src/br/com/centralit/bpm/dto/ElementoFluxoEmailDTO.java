package br.com.centralit.bpm.dto;

public class ElementoFluxoEmailDTO extends ElementoFluxoDTO {
    
    private String[] colGrupos;
    private String[] colUsuarios;
    private String[] colDestinatarios;
    
    @Override
    public void setGrupos(String grupos) {
        if (grupos != null)   
            this.colGrupos = grupos.split(";");
        else
            this.colGrupos = new String[]{};
        this.grupos = grupos;
    }
    @Override
    public void setUsuarios(String usuarios) {
        if (usuarios != null) 
            this.colUsuarios = usuarios.split(";");
        else
            this.colUsuarios = new String[]{};      
        this.usuarios = usuarios;
    }
    public void setColGrupos(String[] colGrupos) {
        this.grupos = "";
        if (colGrupos != null && colGrupos.length > 0) {
            for (int i = 0; i < colGrupos.length; i++) {
                if (i > 0)
                    this.grupos += ";";
                this.grupos += colGrupos[i];
            }
        }       
        this.colGrupos = colGrupos;
    }
    public void setColUsuarios(String[] colUsuarios) {
        this.usuarios = "";
        if (colUsuarios != null && colUsuarios.length > 0) {
            for (int i = 0; i < colUsuarios.length; i++) {
                if (i > 0)
                    this.usuarios += ";";
                this.usuarios += colUsuarios[i];
            }
        }       
        this.colUsuarios = colUsuarios;
    }
    public String[] getColGrupos() {
        return colGrupos;
    }
    public String[] getColUsuarios() {
        return colUsuarios;
    }
    
    @Override
    public void setDestinatariosEmail(String destinatariosEmail) {
        if (destinatariosEmail != null)   
            this.colDestinatarios = destinatariosEmail.split(";");
        else
            this.colDestinatarios = new String[]{};
        this.destinatariosEmail = destinatariosEmail;
    }
    public String[] getColDestinatarios() {
        return colDestinatarios;
    }
    public void setColDestinatarios(String[] colDestinatarios) {
        this.destinatariosEmail = "";
        if (colDestinatarios != null && colDestinatarios.length > 0) {
            for (int i = 0; i < colDestinatarios.length; i++) {
                if (i > 0)
                    this.destinatariosEmail += ";";
                this.destinatariosEmail += colDestinatarios[i];
            }
        }       
        this.colDestinatarios = colDestinatarios;
    }
	
}
