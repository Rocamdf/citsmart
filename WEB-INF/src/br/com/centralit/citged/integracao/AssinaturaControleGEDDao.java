package br.com.centralit.citged.integracao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import br.com.centralit.citged.bean.AssinaturaControleGEDDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;


public class AssinaturaControleGEDDao extends CrudDaoDefaultImpl {
	private static final long serialVersionUID = 1L;
	
	private static final String SQL_NEXT_KEY = 
          "SELECT MAX(IDASSINATURA) + 1 FROM ASSINATURA_CONTROLEGED";
	
	private static final String SQL_INSERT = 
	      "INSERT INTO ASSINATURA_CONTROLEGED (IDASSINATURA, IDCONTROLEGED, "
	    + "CONTEUDOASSINATURA) VALUES (?, ?, ?)";
	
	private static final String SQL_RESTORE = 
          "SELECT IDASSINATURA, IDCONTROLEGED, CONTEUDOASSINATURA " 
        + "FROM ASSINATURA_CONTROLEGED WHERE IDASSINATURA = ?";
	
	public String getTableName() { return "ASSINATURA_CONTROLEGED"; }
	
    public Class getBean(){ return AssinaturaControleGEDDTO.class; }
    
	public AssinaturaControleGEDDao()
	{
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection getFields()
	{
		Collection fields = new ArrayList();
		fields.add(new Field("idAssinatura", "idAssinatura", true, true, false, 
		        false));
		fields.add(new Field("idControleGED", "idControleGED", false, false, 
		        false, false));
		fields.add(new Field("pathAssinatura", "pathAssinatura", false, false, 
		        false, false));
		return fields;
	}
	
	private Integer nextKey(final Connection conn) throws Exception
    {
        Integer key = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(SQL_NEXT_KEY);
            rs = ps.executeQuery();
            if(rs.next()) key = new Integer(rs.getInt(1));
        }finally{
            if(rs != null) try{ rs.close(); }catch(SQLException sqle){}
            if(ps != null) try{ ps.close(); }catch(SQLException sqle){}
        }
        return key != null ? key : new Integer(1);
    }
	
    @Override
    public IDto create(IDto obj) throws Exception
    {
        File f = null;
        Connection conn = null;
        PreparedStatement ps = null;
        AssinaturaControleGEDDTO ass = (AssinaturaControleGEDDTO)obj;
        try{
            conn = super.getDataSource().getConnection();
            ass.setIdAssinatura(nextKey(conn));
            ps = conn.prepareStatement(SQL_INSERT);
            ps.setInt(2, ass.getIdControleGED());
            f = new File(ass.getPathAssinatura());
            InputStream is = new FileInputStream(f);
            if(is.available() > 0)
                ps.setBinaryStream(3, is, is.available());
            else  ps.setNull(3, Types.BLOB);
            ps.setInt(1, ass.getIdAssinatura());
            ps.executeUpdate();
        }finally{
            if(ps != null) try{ ps.close(); ps = null; }catch(SQLException sqle){}
            if(conn != null) try{ conn.close(); conn = null; }catch(SQLException sqle){}
            if(f != null)  f.delete();
        }
        return ass;
    }
    
    @Override
    public IDto restore(IDto obj) throws Exception
    {
        File f = null;
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement ps = null;
        AssinaturaControleGEDDTO ret = null;
        AssinaturaControleGEDDTO ass = (AssinaturaControleGEDDTO)obj;
        try{
            conn = super.getDataSource().getConnection();
            ps = conn.prepareStatement(SQL_RESTORE);
            ps.setInt(1, ass.getIdControleGED());
            rs = ps.executeQuery();
            if(rs.next()){
                ret = new AssinaturaControleGEDDTO();
                ret.setIdAssinatura(rs.getInt(1));
                ret.setIdControleGED(rs.getInt(2));
                InputStream is = rs.getBinaryStream(3);
                int qtd = 0;  byte[] b = null;
                f = new File(Constantes.getValue("DIRETORIO_GED") + "/" 
                        + Constantes.getValue("ID_EMPRESA_PROC_BATCH") + "/"
                        + ret.getPastaControleGed());
                f.mkdirs();
                f = new File(f.getAbsolutePath() + "/" 
                        + ret.getIdControleGED() + ".ged");
                f.createNewFile();
                ret.setPathAssinatura(f.getAbsolutePath());
                OutputStream os = new FileOutputStream(f);
                do{
                    b = new byte[1024];
                    qtd = is.read(b);
                    if(qtd >= 0){
                        os.write(b, 0, qtd);
                        os.flush();
                    }
                }while(qtd >= 0);
                os.close();
            }
        }finally{
            if(rs != null) try{ rs.close(); rs = null; }catch(SQLException sqle){}
            if(ps != null) try{ ps.close(); ps = null; }catch(SQLException sqle){}
            if(conn != null) try{ conn.close(); conn = null;}catch(SQLException sqle){}
        }
        return ret;
    }
	
    @Deprecated
    public Collection find(IDto obj) throws Exception { return null; }
    
    @Deprecated
    public Collection list() throws Exception { return null; }
}