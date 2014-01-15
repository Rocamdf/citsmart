package br.com.centralit.citcorpore.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import br.com.centralit.citcorpore.bean.CargaParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.integracao.ParametroCorporeDAO;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

public class CargaParametroCorporeServiceEjb extends CrudServicePojoImpl implements CargaParametroCorporeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3434868475015504897L;



	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public List<CargaParametroCorporeDTO> gerarCarga(File carga ,Integer idEmpresa)throws ServiceException, Exception {
		ParametroCorporeDAO parametroCorporeDAO = new ParametroCorporeDAO();
		
		TransactionControler tc = new TransactionControlerImpl(parametroCorporeDAO.getAliasDB());
		
		parametroCorporeDAO.setTransactionControler(tc);
		
		tc.start();
		
		BufferedReader arq = new BufferedReader(new InputStreamReader(new FileInputStream(carga), "WINDOWS-1252"));

		boolean primeiraLinha = true;
		
		while (arq.ready()){
			ParametroCorporeDTO parametroCorporeDTO = new ParametroCorporeDTO();
			String linhaAux = arq.readLine();
			String linha = null;
			if(linhaAux != null){
				linha = new String(linhaAux);
				linha = linha.trim();
			}
			ParametroCorporeDTO beanParametroCorporeDTO = null;

			String[] linhaQuebrada = null;
			if(linha != null){
				linhaQuebrada = linha.split("\n");
			}
			if(linhaQuebrada!= null && linhaQuebrada.length > 0){
				for (String string : linhaQuebrada) {
					String[] colunasArray = string.split(";");
					if(colunasArray.length > 0){
						if(primeiraLinha){
							primeiraLinha = false;
							break;
						}
						int j = 0;
						for (String coluna : colunasArray){
								if ((j == 0) && (coluna.equalsIgnoreCase(""))){
								j++;
							}
							else if (j == 0){
								beanParametroCorporeDTO = new ParametroCorporeDTO();
								beanParametroCorporeDTO = this.existeParametroPorId(Integer.parseInt(coluna) , parametroCorporeDAO);
								if (beanParametroCorporeDTO.getId() == null){
/*									beanParametroCorporeDTO.setId(Integer.parseInt(coluna));
									beanParametroCorporeDTO.setIdEmpresa(idEmpresa);
									beanParametroCorporeDTO.setDataInicio(Util.getSqlDataAtual());*/
									parametroCorporeDTO = beanParametroCorporeDTO;
								}
								else {
									beanParametroCorporeDTO.setId(Integer.parseInt(coluna));
									beanParametroCorporeDTO.setIdEmpresa(idEmpresa);
									beanParametroCorporeDTO.setDataInicio(Util.getSqlDataAtual());
									parametroCorporeDTO = beanParametroCorporeDTO;
								}
								j++;
							}
							else if (j == 1){
								parametroCorporeDTO.setNome(coluna);
								j++;
							}
							else if (j == 2){
								parametroCorporeDTO.setValor(coluna);
								j++;
							}
					
						}

						if (parametroCorporeDTO.getId() != null){
							if (parametroCorporeDTO.getValor() == null){
								parametroCorporeDTO.setValor("");
							}
							parametroCorporeDAO.update(parametroCorporeDTO);
						}
					}
				}
			}
		}

		arq.close();
		arq = null;
		
		tc.commit();
		tc.close();
		return null;
		}
		
		private ParametroCorporeDTO existeParametroPorId(Integer coluna, ParametroCorporeDAO parametroCorporeDAO) throws Exception{
			ParametroCorporeDTO parametroCorporeDTO = new ParametroCorporeDTO();
			parametroCorporeDTO.setId(coluna);
			
			if (parametroCorporeDAO.findByID(parametroCorporeDTO) == null){
				parametroCorporeDTO.setId(null);
				return parametroCorporeDTO;	
			}
			
			return parametroCorporeDTO;
		}
	}


