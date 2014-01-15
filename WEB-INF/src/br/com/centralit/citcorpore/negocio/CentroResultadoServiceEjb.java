package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.integracao.AlcadaCentroResultadoDAO;
import br.com.centralit.citcorpore.integracao.CentroResultadoDao;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;


@SuppressWarnings({"rawtypes", "unchecked"})
public class CentroResultadoServiceEjb extends CrudServicePojoImpl implements CentroResultadoService {
	
	private static final long serialVersionUID = -2888401978793306168L;

	protected CrudDAO getDao() throws ServiceException {
		return new CentroResultadoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	
	protected void validaDelete(Object arg0) throws Exception {}
	
	protected void validaFind(Object arg0) throws Exception {}
	
	protected void validaUpdate(Object arg0) throws Exception {}
	
	
	@Override
	public Collection list() throws ServiceException, LogicException {	
		CentroResultadoDao centroResultadoDAO = null;
		Collection lista = null;
		
		try {
			centroResultadoDAO = (CentroResultadoDao) getDao();
			lista = centroResultadoDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lista;
	}

	@Override
	public Collection findByIdPai(Integer idPai) throws Exception {
		CentroResultadoDao centroResultadoDAO = (CentroResultadoDao) getDao();
		
		return centroResultadoDAO.findByIdPai(idPai);
	}

	@Override
	public void recuperaImagem(CentroResultadoDTO centroResultadoDTO) throws Exception {
		
		centroResultadoDTO.setImagem(null);
		List<ControleGEDDTO> colGed = (List<ControleGEDDTO>) new ControleGEDDao().listByIdTabelaAndID(ControleGEDDTO.TABELA_CENTRORESULTADO, centroResultadoDTO.getIdCentroResultado() );
		
		if (colGed != null & !colGed.isEmpty() ) {
			centroResultadoDTO.setImagem(new ControleGEDServiceBean().getRelativePathFromGed(colGed.get(0) ) ); 
		}
	}

	@Override
	public Collection findSemPai() throws Exception {
		CentroResultadoDao centroResultadoDAO = (CentroResultadoDao) getDao();
		Collection centrosResultadoSemPai = centroResultadoDAO.findSemPai();		
		
		return centrosResultadoSemPai;
	}
	
	public boolean temFilhos(int idCentroResultado) throws Exception {
		CentroResultadoDao centroResultadoDAO = (CentroResultadoDao) getDao();
		
		return centroResultadoDAO.temFilhos(idCentroResultado);	
	}
	
	public Collection find(CentroResultadoDTO centroResultadoDTO) throws Exception {
		CentroResultadoDao centroResultadoDAO = (CentroResultadoDao) getDao();
		
		return centroResultadoDAO.find(centroResultadoDTO);
	}
	
    @Override
    public Collection listAtivos() throws Exception {
        return getHierarquia(false, false);
    }
    
    public Collection getHierarquia(boolean acrescentarInativos, boolean somenteRequisicaoProdutos) throws Exception{
        CentroResultadoDao centroResultadoDao = new CentroResultadoDao();
        Collection<CentroResultadoDTO> colSemPai = centroResultadoDao.findSemPai();
        if (colSemPai == null) return null;
        
        Collection colRetorno = new ArrayList();
        boolean bAcrescenta;
        for (CentroResultadoDTO centroResultadoDto : colSemPai) {
            bAcrescenta = true;
            /*if (!acrescentarInativos && !centroResultadoDto.getSituacao().equalsIgnoreCase("A"))
               bAcrescenta = false;
            if (bAcrescenta) {
                if (somenteRequisicaoProdutos && centroResultadoDto.getPermiteRequisicaoProduto() != null && !centroResultadoDto.getPermiteRequisicaoProduto().equalsIgnoreCase("S"))
                    bAcrescenta = false;                
            }*/
            if (bAcrescenta){
                centroResultadoDto.setNivel(new Integer(0));
                colRetorno.add(centroResultadoDto);
            
                Collection colFilhos = carregaFilhos(centroResultadoDto.getIdCentroResultado(), 0, acrescentarInativos, somenteRequisicaoProdutos);
                if (colFilhos != null)
                    colRetorno.addAll(colFilhos);
            }
        }
        return colRetorno;
    }
    
    private Collection carregaFilhos(Integer idPai, int nivel, boolean acrescentarInativos, boolean somenteRequisicaoProdutos) throws Exception{
        CentroResultadoDao centroResultadoDao = new CentroResultadoDao();
        Collection<CentroResultadoDTO> colFilhos = centroResultadoDao.findByIdPai(idPai);
        if (colFilhos == null) return null;
        
        Collection colRetorno = new ArrayList();
        
        boolean bAcrescenta;
        for (CentroResultadoDTO centroResultadoDto : colFilhos) {
            bAcrescenta = true;
            if (!acrescentarInativos && !centroResultadoDto.getSituacao().equalsIgnoreCase("A"))
                bAcrescenta = false; 
            if (bAcrescenta) {
                if (somenteRequisicaoProdutos && !centroResultadoDto.getPermiteRequisicaoProduto().equalsIgnoreCase("S"))
                    bAcrescenta = false;                
            }
            if (bAcrescenta){
                centroResultadoDto.setNivel(new Integer(nivel + 1));
                colRetorno.add(centroResultadoDto);
                
                Collection colFilhosFilhos = carregaFilhos(centroResultadoDto.getIdCentroResultado(), nivel + 1, acrescentarInativos, somenteRequisicaoProdutos);
                if (colFilhosFilhos != null)
                    colRetorno.addAll(colFilhosFilhos);
            }
        }       
        return colRetorno;
    }
    
    @Override
    public Collection listPermiteRequisicaoProduto() throws Exception {
        return getHierarquia(false, true);
    }

	@Override
	public Collection findByIdAlcada(Integer idAlcada) throws Exception {		
		return ( (CentroResultadoDao) getDao() ).findByIdAlcada(idAlcada);
	}
	
    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        CentroResultadoDao centroResultadoDao = new CentroResultadoDao();
        AlcadaCentroResultadoDAO alcadaCentroResultadoDao = new AlcadaCentroResultadoDAO();
        TransactionControler tc = new TransactionControlerImpl(centroResultadoDao.getAliasDB());
        
        try{
            validaCreate(model);
            
            centroResultadoDao.setTransactionControler(tc);
            alcadaCentroResultadoDao.setTransactionControler(tc);
            
            tc.start();
        
            CentroResultadoDTO centroResultadoDto = (CentroResultadoDTO) model;
            centroResultadoDto = (CentroResultadoDTO) centroResultadoDao.create(centroResultadoDto);
            
            atualizaAlcadas(centroResultadoDto, alcadaCentroResultadoDao);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
        return model;
    }
    
    private void atualizaAlcadas(CentroResultadoDTO centroResultadoDto, AlcadaCentroResultadoDAO alcadaCentroResultadoDao) throws Exception {
        alcadaCentroResultadoDao.deleteByIdCentroResultado(centroResultadoDto.getIdCentroResultado());
        if (centroResultadoDto.getColAlcadas() != null) {
            for (AlcadaCentroResultadoDTO alcadaDto : centroResultadoDto.getColAlcadas()) {
                if (alcadaDto.getIdAlcada() == null)
                    throw new Exception("Alçada não informada");
                if (alcadaDto.getIdEmpregado() == null)
                    throw new Exception("Empregado não informado");
                if (alcadaDto.getDataInicio() == null)
                    throw new Exception("Data de início informada");
                if (alcadaDto.getDataFim() != null && alcadaDto.getDataFim().compareTo(alcadaDto.getDataInicio()) < 0)
                    throw new Exception("Data de início não pode ser maior que a data fim");
                alcadaDto.setIdCentroResultado(centroResultadoDto.getIdCentroResultado());
                alcadaCentroResultadoDao.create(alcadaDto);
            }
        }    
    }

    @Override
    public void update(IDto model) throws ServiceException, LogicException {
        CentroResultadoDao centroResultadoDao = new CentroResultadoDao();
        AlcadaCentroResultadoDAO alcadaCentroResultadoDao = new AlcadaCentroResultadoDAO();
        TransactionControler tc = new TransactionControlerImpl(centroResultadoDao.getAliasDB());
        
        try{
            validaUpdate(model);

            centroResultadoDao.setTransactionControler(tc);
            alcadaCentroResultadoDao.setTransactionControler(tc);
            
            tc.start();
        
            CentroResultadoDTO centroResultadoDto = (CentroResultadoDTO) model;
            centroResultadoDao.update(centroResultadoDto);
            
            atualizaAlcadas(centroResultadoDto, alcadaCentroResultadoDao);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
    }
}