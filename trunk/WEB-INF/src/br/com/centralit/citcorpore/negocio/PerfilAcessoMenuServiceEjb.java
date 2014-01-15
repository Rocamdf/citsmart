package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoMenuDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.citframework.util.UtilDatas;

public class PerfilAcessoMenuServiceEjb  extends CrudServicePojoImpl implements PerfilAcessoMenuService{

    @Override
    public Collection<PerfilAcessoMenuDTO> restoreMenusAcesso(IDto obj) throws Exception {
	// TODO Auto-generated method stub
	return ((PerfilAcessoMenuDao)getDao()).restoreMenusAcesso(obj);
    }

    @Override
    protected CrudDAO getDao() throws ServiceException {
	// TODO Auto-generated method stub
	return new PerfilAcessoMenuDao();
    }

    @Override
    protected void validaCreate(Object arg0) throws Exception {
	// TODO Auto-generated method stub
	
    }

    @Override
    protected void validaDelete(Object arg0) throws Exception {
	// TODO Auto-generated method stub
	
    }

    @Override
    protected void validaFind(Object arg0) throws Exception {
	// TODO Auto-generated method stub
	
    }

    @Override
    protected void validaUpdate(Object arg0) throws Exception {
	// TODO Auto-generated method stub
	
    }

    @Override
	public void atualizaPerfis() throws Exception {
		PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
		MenuDao menuDao = new MenuDao();
		
		TransactionControler tc = new TransactionControlerImpl(perfilAcessoMenuDao.getAliasDB());
		
		perfilAcessoMenuDao.setTransactionControler(tc);
		menuDao.setTransactionControler(tc);
		
		try {
			ArrayList<PerfilAcessoMenuDTO> colecaoPerfisAcessoMenu = (ArrayList) perfilAcessoMenuDao.list();
			for (PerfilAcessoMenuDTO perfilAcessoMenu : colecaoPerfisAcessoMenu) {
				if (perfilAcessoMenu.getIdPerfilAcesso().intValue() == 2) {
					System.out.println();
				}
				ArrayList<MenuDTO> menuPai = (ArrayList<MenuDTO>) menuDao.listarMenuPai(perfilAcessoMenu.getIdMenu());
				
				if(menuPai != null && !menuPai.isEmpty() && (menuPai.get(0)).getIdMenuPai() != null && (menuPai.get(0)).getIdMenuPai() != 0){
					ArrayList<PerfilAcessoMenuDTO> perfilAcessoComMenuPai = (ArrayList) perfilAcessoMenuDao.pesquisaSeJaExisteAcessoMenuPai(perfilAcessoMenu.getIdPerfilAcesso(), (menuPai.get(0)).getIdMenuPai());
					if(perfilAcessoComMenuPai == null || perfilAcessoComMenuPai.isEmpty()){
						//criar acesso para o menu pai
						PerfilAcessoMenuDTO perfilAcessoMenuPaiDto = new PerfilAcessoMenuDTO();
						perfilAcessoMenuPaiDto.setDataInicio(UtilDatas.getDataAtual());
						perfilAcessoMenuPaiDto.setDeleta("S");
						perfilAcessoMenuPaiDto.setGrava("S");
						perfilAcessoMenuPaiDto.setPesquisa("S");
						perfilAcessoMenuPaiDto.setIdMenu(menuPai.get(0).getIdMenuPai());
						perfilAcessoMenuPaiDto.setIdPerfilAcesso(perfilAcessoMenu.getIdPerfilAcesso());
						perfilAcessoMenuDao.create(perfilAcessoMenuPaiDto);
					}
				}
			}
			
			tc.commit();
			tc.close();
			tc = null;
		} catch (ServiceException e) {
			this.rollbackTransaction(tc, e);
			e.printStackTrace();
		}
	}
    
}
