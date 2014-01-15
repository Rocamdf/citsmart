package br.com.centralit.citcorpore.negocio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.centralit.citcorpore.bean.CriterioCotacaoCategoriaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.integracao.CategoriaProdutoDao;
import br.com.centralit.citcorpore.integracao.CriterioCotacaoCategoriaDao;
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
import br.com.citframework.util.Constantes;
public class CategoriaProdutoServiceEjb extends CrudServicePojoImpl implements CategoriaProdutoService {
	protected CrudDAO getDao() throws ServiceException {
		return new CategoriaProdutoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	    validaPesos(arg0);
	}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {
	    validaPesos(arg0);
	}

	
    private void validaPesos(Object arg0) throws Exception {
        CategoriaProdutoDTO categoriaProdutoDto = (CategoriaProdutoDTO) arg0;
        
        if (categoriaProdutoDto.getPesoCotacaoPreco() == null)
            throw new Exception("Peso para o critério preço não foi informado");
        if (categoriaProdutoDto.getPesoCotacaoPrazoEntrega() == null)
            throw new Exception("Peso para o critério prazo de entrega não foi informado");
        if (categoriaProdutoDto.getPesoCotacaoPrazoGarantia() == null)
            throw new Exception("Peso para o critério prazo de garantia não foi informado");
        if (categoriaProdutoDto.getPesoCotacaoPrazoPagto() == null)
            throw new Exception("Peso para o critério prazo de pagamento não foi informado");
        if (categoriaProdutoDto.getPesoCotacaoTaxaJuros() == null)
            throw new Exception("Peso para o critério taxa de juros não foi informado");

        if (categoriaProdutoDto.getPesoCotacaoPreco().intValue() > 10)
            throw new Exception("Peso para o critério preço deve estar entre 0 e 10");
        if (categoriaProdutoDto.getPesoCotacaoPrazoEntrega().intValue() > 10)
            throw new Exception("Peso para o critério prazo deve estar entre 0 e 10");
        if (categoriaProdutoDto.getPesoCotacaoPrazoGarantia().intValue() > 10)
            throw new Exception("Peso para o critério prazo deve estar entre 0 e 10");
        if (categoriaProdutoDto.getPesoCotacaoPrazoPagto().intValue() > 10)
            throw new Exception("Peso para o critério prazo deve estar entre 0 e 10");
        if (categoriaProdutoDto.getPesoCotacaoTaxaJuros().intValue() > 10)
            throw new Exception("Peso para o critério taxa deve estar entre 0 e 10");
        
    }

    public Collection listAtivas() throws Exception {
        return getHierarquia(false, false);
    }
    
    @Override
    public Collection list() throws ServiceException, LogicException {
        try {
            return getHierarquia(true, false);
        } catch (Exception e) {
        }
        return null;
    }
    
    public Collection getHierarquia(boolean acrescentarInativos, boolean somenteRequisicaoProdutos) throws Exception{
        CategoriaProdutoDao categoriaProdutoDao = new CategoriaProdutoDao();
        Collection<CategoriaProdutoDTO> colSemPai = categoriaProdutoDao.findSemPai();
        if (colSemPai == null) return null;
        
        Collection colRetorno = new ArrayList();
        boolean bAcrescenta;
        for (CategoriaProdutoDTO categoriaProdutoDto : colSemPai) {
            bAcrescenta = true;
            if (!acrescentarInativos && !categoriaProdutoDto.getSituacao().equalsIgnoreCase("A"))
               bAcrescenta = false;
            if (bAcrescenta){
                categoriaProdutoDto.setNivel(new Integer(0));
                colRetorno.add(categoriaProdutoDto);
            
                Collection colFilhos = carregaFilhos(categoriaProdutoDto.getIdCategoria(), 0, acrescentarInativos);
                if (colFilhos != null)
                    colRetorno.addAll(colFilhos);
            }
        }
        return colRetorno;
    }
    
    private Collection carregaFilhos(Integer idPai, int nivel, boolean acrescentarInativos) throws Exception{
        CategoriaProdutoDao categoriaProdutoDao = new CategoriaProdutoDao();
        Collection<CategoriaProdutoDTO> colFilhos = categoriaProdutoDao.findByIdPai(idPai);
        if (colFilhos == null) return null;
        
        Collection colRetorno = new ArrayList();
        
        boolean bAcrescenta;
        for (CategoriaProdutoDTO categoriaProdutoDto : colFilhos) {
            bAcrescenta = true;
            if (!acrescentarInativos && !categoriaProdutoDto.getSituacao().equalsIgnoreCase("A"))
                bAcrescenta = false; 
            if (bAcrescenta){
                categoriaProdutoDto.setNivel(new Integer(nivel + 1));
                colRetorno.add(categoriaProdutoDto);
                
                Collection colFilhosFilhos = carregaFilhos(categoriaProdutoDto.getIdCategoria(), nivel + 1, acrescentarInativos);
                if (colFilhosFilhos != null)
                    colRetorno.addAll(colFilhosFilhos);
            }
        }       
        return colRetorno;
    }
    
    public Collection listPermiteRequisicaoProduto() throws Exception {
        return getHierarquia(false, true);
    }

    @Override
    public void recuperaImagem(CategoriaProdutoDTO categoriaProdutoDto) throws Exception {
        categoriaProdutoDto.setImagem(null);
        if((categoriaProdutoDto.getIdCategoria()!=null)&&(categoriaProdutoDto.getIdCategoria()>0)){
            List<ControleGEDDTO> colGed = (List<ControleGEDDTO>) new ControleGEDDao().listByIdTabelaAndID(ControleGEDDTO.TABELA_CATEGORIAPRODUTO, categoriaProdutoDto.getIdCategoria());
            if (colGed != null && !colGed.isEmpty()) { 
                try{
                    categoriaProdutoDto.setImagem(new ControleGEDServiceBean().getRelativePathFromGed(colGed.get(0)));
                }catch (Exception e) {
                }
            }
        }
    }

    public Collection findByIdPai(Integer idPai) throws Exception{
    	CategoriaProdutoDao categoriaProdutoDao = new CategoriaProdutoDao();
    	return categoriaProdutoDao.findByIdPai(idPai);
    }
	
    public boolean existeIgual(CategoriaProdutoDTO categoriaProduto) throws Exception {
		 return new CategoriaProdutoDao().existeIgual(categoriaProduto);
	}
    public String getHierarquiaHTML(String acao) throws Exception {
        String result = "";
        Collection<CategoriaProdutoDTO> colCategorias = listAtivas();
        if (colCategorias != null) {
            int maiorNivel = 0;
            for (CategoriaProdutoDTO categoriaProdutoDto : colCategorias) {
                if (categoriaProdutoDto.getNivel().intValue() > maiorNivel)
                    maiorNivel = categoriaProdutoDto.getNivel().intValue();
            }
            result = "<table>";
            for (CategoriaProdutoDTO categoriaProdutoDto : colCategorias) {
                recuperaImagem(categoriaProdutoDto);
                String path = categoriaProdutoDto.getImagem();
                if (path == null || path.equals(""))
                    path = Constantes.getValue("CONTEXTO_APLICACAO")+"/imagens/folder.png";
                if (acao != null && !acao.trim().equals("")) 
                    result += "<tr onclick='"+acao+"("+categoriaProdutoDto.getIdCategoria()+")' id='trCateg_"+categoriaProdutoDto.getIdCategoria()+"' style=\"cursor: pointer;height: 18px !important; padding: 0px 0px 0px 0px !important\" onMouseOver='TrowOn(this,\"#eee\");' onMouseOut='TrowOff(this)'>";
                else
                    result += "<tr id='trCateg_"+categoriaProdutoDto.getIdCategoria()+"' style=\"cursor: pointer;height: 18px !important; padding: 0px 0px 0px 0px !important\" >";
                result += "<td><table><tr>";
                for (int i = 0; i <= categoriaProdutoDto.getNivel().intValue(); i++) {
                    if (i < categoriaProdutoDto.getNivel().intValue())
                        result += "<td width='16px'>&nbsp;</td>";
                    else
                        result += "<td><img style='width:16px;height:16px' src=\""+path+"\" />"+categoriaProdutoDto.getNomeCategoria()+"</td>";
                }
                result += "</tr></table></td>";
                result += "</tr>";
            }
            result += "</table>";
        } 
        return result;
    }
    
    private void atualizaAnexos(CategoriaProdutoDTO categoriaProdutoDto, TransactionControler tc) throws Exception {
        new ControleGEDServiceBean().atualizaAnexos(categoriaProdutoDto.getFotos(), ControleGEDDTO.TABELA_CATEGORIAPRODUTO, categoriaProdutoDto.getIdCategoria(), tc);
    }
    
    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        CategoriaProdutoDao categoriaProdutoDao = new CategoriaProdutoDao();
        CriterioCotacaoCategoriaDao criterioCotacaoCategoriaDao = new CriterioCotacaoCategoriaDao();
        TransactionControler tc = new TransactionControlerImpl(categoriaProdutoDao.getAliasDB());
        
        try{
            validaCreate(model);
            
            categoriaProdutoDao.setTransactionControler(tc);
            criterioCotacaoCategoriaDao.setTransactionControler(tc);
            
            tc.start();
        
            CategoriaProdutoDTO categoriaProdutoDto = (CategoriaProdutoDTO) model;
            categoriaProdutoDto = (CategoriaProdutoDTO) categoriaProdutoDao.create(categoriaProdutoDto);
            
            atualizaCriterios(categoriaProdutoDto, criterioCotacaoCategoriaDao);
            atualizaAnexos(categoriaProdutoDto, tc);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
        return model;
    }
    
    private void atualizaCriterios(CategoriaProdutoDTO categoriaProdutoDto, CriterioCotacaoCategoriaDao criterioCotacaoCategoriaDao) throws Exception {
        criterioCotacaoCategoriaDao.deleteByIdCategoria(categoriaProdutoDto.getIdCategoria());
        if (categoriaProdutoDto.getColCriterios() != null) {
            for (CriterioCotacaoCategoriaDTO criterioDto : categoriaProdutoDto.getColCriterios()) {
                if (criterioDto.getPesoCotacao() == null)
                    throw new Exception("Peso não informado");
                if (criterioDto.getPesoCotacao().intValue() > 10)
                    throw new Exception("O peso deve estar entre 0 e 10");                    
                criterioDto.setIdCategoria(categoriaProdutoDto.getIdCategoria());
                criterioCotacaoCategoriaDao.create(criterioDto);
            }
        }    
    }

    @Override
    public void update(IDto model) throws ServiceException, LogicException {
        CategoriaProdutoDao categoriaProdutoDao = new CategoriaProdutoDao();
        CriterioCotacaoCategoriaDao criterioCotacaoCategoriaDao = new CriterioCotacaoCategoriaDao();
        TransactionControler tc = new TransactionControlerImpl(categoriaProdutoDao.getAliasDB());
        
        try{
            validaUpdate(model);

            categoriaProdutoDao.setTransactionControler(tc);
            criterioCotacaoCategoriaDao.setTransactionControler(tc);
            
            tc.start();
        
            CategoriaProdutoDTO categoriaProdutoDto = (CategoriaProdutoDTO) model;
            categoriaProdutoDao.update(categoriaProdutoDto);
            
            atualizaCriterios(categoriaProdutoDto, criterioCotacaoCategoriaDao);
            atualizaAnexos(categoriaProdutoDto, tc);
            
            tc.commit();
            tc.close();
        }catch(Exception e){
            this.rollbackTransaction(tc, e);
        }
    }
}