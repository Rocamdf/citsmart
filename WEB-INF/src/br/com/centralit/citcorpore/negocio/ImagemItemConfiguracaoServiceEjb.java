package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.ImagemItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.integracao.ImagemItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;

/**
 * 
 * @author breno.guimaraes
 * 
 */

public class ImagemItemConfiguracaoServiceEjb implements ImagemItemConfiguracaoService {

	ImagemItemConfiguracaoDao imgDao;

	public ImagemItemConfiguracaoServiceEjb() {
		this.imgDao = new ImagemItemConfiguracaoDao();
	}

	@Override
	public IDto create(IDto model) throws LogicException, RemoteException, ServiceException {
		ImagemItemConfiguracaoDTO imgDto = (ImagemItemConfiguracaoDTO) model;
		System.out.println("Item não existe. Create.");
		return imgDao.create(imgDto);
	}

	@Override
	public IDto restore(IDto model) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	@Override
	public void update(IDto model) throws LogicException, RemoteException, ServiceException {
		ImagemItemConfiguracaoDTO imgDto = (ImagemItemConfiguracaoDTO) model;
		System.out.println("Item já existe. Update.");
		imgDao.update(imgDto);
	}

	@Override
	public void delete(IDto model) throws LogicException, RemoteException, ServiceException {
		ImagemItemConfiguracaoDTO imagem = (ImagemItemConfiguracaoDTO) model;
		imgDao.excluiIdPaiDeItensFilho(imagem.getIdImagemItemConfiguracao());
		this.imgDao.delete(model);
	}

	@Override
	public Collection find(IDto obj) throws LogicException, RemoteException,
			ServiceException {
		return null;
	}

	public Collection findByIdServico(int idServico) throws LogicException,
			RemoteException, ServiceException {
		return imgDao.findByIdServico(idServico);
	}

	@Override
	public Collection list() throws LogicException, RemoteException,
			ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUsuario(Usuario usr) throws RemoteException,
			ServiceException {

	}

	@Override
	public Collection findByIdImagemItemConfiguracaoPai(int id)
			throws LogicException, RemoteException, ServiceException {

		return this.imgDao.findByIdImagemItemConfiguracaoPai(id);

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection findItensRelacionadosHierarquia(Integer idItemCfg) throws Exception{
		ImagemItemConfiguracaoDao imagemItemConfiguracaoDao = new ImagemItemConfiguracaoDao();
		ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
		Collection<ItemConfiguracaoDTO> colRetorno = new ArrayList<ItemConfiguracaoDTO>();
		
		/**/
		Collection<ImagemItemConfiguracaoDTO> col = imagemItemConfiguracaoDao.findByIdItemConfiguracao(idItemCfg);
		
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				ImagemItemConfiguracaoDTO imagemItemConfiguracaoDTO = (ImagemItemConfiguracaoDTO)it.next();
				ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
				itemConfiguracaoDTO.setIdItemConfiguracao(imagemItemConfiguracaoDTO.getIdItemConfiguracao());
				itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoDao.restore(itemConfiguracaoDTO);
				if (itemConfiguracaoDTO != null){
					itemConfiguracaoDTO.setTipoVinculo("FILHO");
					colRetorno.add(itemConfiguracaoDTO);
				}
				Collection colRel = imagemItemConfiguracaoDao.findByIdImagemItemConfiguracaoPai(imagemItemConfiguracaoDTO.getIdImagemItemConfiguracao());
				if (colRel != null) {
					for (Iterator it2 = colRel.iterator(); it2.hasNext();){
						ImagemItemConfiguracaoDTO imagemItemConfiguracaoAux = (ImagemItemConfiguracaoDTO)it2.next();
						ItemConfiguracaoDTO itemConfiguracaoAux = new ItemConfiguracaoDTO();
						itemConfiguracaoAux.setIdItemConfiguracao(imagemItemConfiguracaoAux.getIdItemConfiguracao());
						itemConfiguracaoAux = (ItemConfiguracaoDTO) itemConfiguracaoDao.restore(itemConfiguracaoAux);
						if (itemConfiguracaoAux != null){
							itemConfiguracaoAux.setTipoVinculo("VINC");
							colRetorno.add(itemConfiguracaoAux);
						}	
/*						Collection colItensVinc = findItensRelacionadosHierarquia(imagemItemConfiguracaoAux.getIdItemConfiguracao());
						if (colItensVinc != null){
							colRetorno.addAll(colItensVinc);
						}*/
					}
				}
			}
		}
		col = itemConfiguracaoDao.findByIdItemConfiguracaoPai(idItemCfg);
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();){
				ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO)it.next();
				if (itemConfiguracaoDTO != null){
					itemConfiguracaoDTO.setTipoVinculo("FILHO");
					colRetorno.add(itemConfiguracaoDTO);
				}
				Collection colItensVinc = null;
				if(itemConfiguracaoDTO != null){
					colItensVinc = findItensRelacionadosHierarquia(itemConfiguracaoDTO.getIdItemConfiguracao());
				}
				if (colItensVinc != null){
					colRetorno.addAll(colItensVinc);
				}
			}
		}		
		return colRetorno;
	}
	public Collection findServicosRelacionadosHierarquia(Integer idItemCfg) throws Exception{
		ImagemItemConfiguracaoDao imagemItemConfiguracaoDao = new ImagemItemConfiguracaoDao();
		ItemConfiguracaoDao itemConfiguracaoDao = new ItemConfiguracaoDao();
		ServicoDao servicoDao = new ServicoDao();
		Collection<ImagemItemConfiguracaoDTO> col = imagemItemConfiguracaoDao.findByIdItemConfiguracao(idItemCfg);
		Collection<ServicoDTO> colRetorno = new ArrayList<ServicoDTO>();
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				ImagemItemConfiguracaoDTO imagemItemConfiguracaoDTO = (ImagemItemConfiguracaoDTO)it.next();
				ServicoDTO servicoDTO = new ServicoDTO();
				servicoDTO.setIdServico(imagemItemConfiguracaoDTO.getIdServico());
				servicoDTO = (ServicoDTO) servicoDao.restore(servicoDTO);
				if (servicoDTO != null){
					colRetorno.add(servicoDTO);
				}
				Collection colRel = imagemItemConfiguracaoDao.findByIdImagemItemConfiguracaoPai(imagemItemConfiguracaoDTO.getIdImagemItemConfiguracao());
				if (colRel != null){
					for (Iterator it2 = colRel.iterator(); it2.hasNext();) {
						ImagemItemConfiguracaoDTO imagemItemConfiguracaoAux = (ImagemItemConfiguracaoDTO)it2.next();
						servicoDTO = new ServicoDTO();
						servicoDTO.setIdServico(imagemItemConfiguracaoAux.getIdServico());
						servicoDTO = (ServicoDTO) servicoDao.restore(servicoDTO);
						if (servicoDTO != null){
							colRetorno.add(servicoDTO);
						}	
						/*Collection colItensVinc = findServicosRelacionadosHierarquia(imagemItemConfiguracaoAux.getIdItemConfiguracao());
						if (colItensVinc != null){
							colRetorno.addAll(colItensVinc);
						}*/
					}
				}
			}
		}
		col = itemConfiguracaoDao.findByIdItemConfiguracaoPai(idItemCfg);
		if (col != null){
			for (Iterator it = col.iterator(); it.hasNext();){
				ItemConfiguracaoDTO itemConfiguracaoDTO = (ItemConfiguracaoDTO)it.next();
				Collection colItensVinc = findServicosRelacionadosHierarquia(itemConfiguracaoDTO.getIdItemConfiguracao());
				if (colItensVinc != null){
					colRetorno.addAll(colItensVinc);
				}
			}
		}		
		return colRetorno;
	}
}
