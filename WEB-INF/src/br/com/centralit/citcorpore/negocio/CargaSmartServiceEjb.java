/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import br.com.centralit.citcorpore.bean.CargaSmartDTO;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.integracao.CargosDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.UnidadeDao;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author Cleon Xavier
 * 
 */
public class CargaSmartServiceEjb extends CrudServicePojoImpl implements CargaSmartService {

	private static final long serialVersionUID = -4847501733091357273L;
	
	



	@Override
	protected CrudDAO getDao() throws ServiceException {
		return null;
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		
	}
	public List<CargaSmartDTO> gerarCarga(String[] carga)
			throws ServiceException, Exception {
		return null;
	}

	@Override
	public List<CargaSmartDTO> gerarCarga(File carga ,Integer idEmpresa)throws ServiceException, Exception {
		EmpregadoDao empregadoDao = new EmpregadoDao();
		CargosDao cargosDao = new CargosDao();
		UnidadeDao unidadeDao = new UnidadeDao();
		

		TransactionControler tc = new TransactionControlerImpl(empregadoDao.getAliasDB());
		

		
		empregadoDao.setTransactionControler(tc);
		cargosDao.setTransactionControler(tc);
		unidadeDao.setTransactionControler(tc);
		
		tc.start();
		
		BufferedReader arq = new BufferedReader(new InputStreamReader(new FileInputStream(carga), "WINDOWS-1252"));
		
		boolean primeiraLinha = true;
		
		while (arq.ready()){
			EmpregadoDTO empregadoDTO = new EmpregadoDTO();
			CargosDTO cargosDTO = new CargosDTO();
			UnidadeDTO unidadeDTO = new UnidadeDTO();
			UnidadeDTO unidadeFilhaDTO = new UnidadeDTO();
			String linhaAux = arq.readLine();
			String linha = null;
			if(linhaAux != null){
				linha = new String(linhaAux);
				linha = linha.trim();
			}
			EmpregadoDTO beanEmpregadoDTO = null;

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
						UnidadeDTO  beanUnidadeDTO = null;
						CargosDTO beanCargosDTO = null;
						if (coluna.equals("")){
							j++;
						}
						else{
							if (j == 0){
								beanUnidadeDTO = new UnidadeDTO();
								beanUnidadeDTO = this.existeUnidadePorNome(coluna, unidadeDao);
								if(beanUnidadeDTO.getIdUnidade() == null){
									beanUnidadeDTO.setIdEmpresa(idEmpresa);
									beanUnidadeDTO.setIdUnidade(unidadeDTO.getIdUnidade());
									beanUnidadeDTO.setNome(coluna);
									beanUnidadeDTO.setDataInicio(Util.getSqlDataAtual());
									unidadeDTO = this.criaUnidadeDTO(beanUnidadeDTO, unidadeDao);
								}
								else{
									unidadeDTO = beanUnidadeDTO;
								}
								j++;
							}

							else if (j == 1){
								beanUnidadeDTO = new UnidadeDTO();
								beanUnidadeDTO = this.existeUnidadePorNome(coluna, unidadeDao);
								if(beanUnidadeDTO.getIdUnidade() == null){
									beanUnidadeDTO.setIdEmpresa(idEmpresa);
									beanUnidadeDTO.setNome(coluna);								
									if (unidadeDTO.getIdUnidade() != null)
									{
										beanUnidadeDTO.setIdUnidadePai(unidadeDTO.getIdUnidade());
									}
									beanUnidadeDTO.setDataInicio(Util.getSqlDataAtual());
									unidadeFilhaDTO = this.criaUnidadeDTO(beanUnidadeDTO, unidadeDao);

								} else{
									if (unidadeDTO.getIdUnidade() != null)
									{
									beanUnidadeDTO.setIdUnidadePai(unidadeDTO.getIdUnidade());
									}
									beanUnidadeDTO.setDataInicio(Util.getSqlDataAtual());
									unidadeFilhaDTO = beanUnidadeDTO;
									unidadeDao.update(unidadeFilhaDTO);
								}
								j++;
							}
							else if (j == 2){
								beanCargosDTO = new CargosDTO();
								beanCargosDTO = this.existeCargosPorNome(coluna, cargosDao);
								if(beanCargosDTO.getIdCargo() == null){
									beanCargosDTO.setNomeCargo(coluna);
									beanCargosDTO.setDataInicio(Util.getSqlDataAtual());
									cargosDTO = this.criaCargosDTO(beanCargosDTO, cargosDao);

								} else{
									cargosDTO = beanCargosDTO;
								}
								j++;
							}
							else if (j == 3){
								beanEmpregadoDTO = new EmpregadoDTO();
								beanEmpregadoDTO = this.existeEmpregadoPorNome(coluna, empregadoDao);							
									if(beanEmpregadoDTO.getIdEmpregado() == null){
										beanEmpregadoDTO.setNome(coluna);
										beanEmpregadoDTO.setNomeProcura(coluna);
										beanEmpregadoDTO.setIdCargo(cargosDTO.getIdCargo());
										beanEmpregadoDTO.setIdUnidade(unidadeFilhaDTO.getIdUnidade());
										beanEmpregadoDTO.setIdSituacaoFuncional(1);
										if (cargosDTO.getIdCargo() != null && unidadeFilhaDTO.getIdUnidade() != null){
											empregadoDTO = this.criaEmpregadoDTO(beanEmpregadoDTO, empregadoDao);
										}
									} else{
										beanEmpregadoDTO.setIdCargo(cargosDTO.getIdCargo());
										beanEmpregadoDTO.setIdUnidade(unidadeFilhaDTO.getIdUnidade());
										empregadoDTO = beanEmpregadoDTO;
									}
									j++;
								}
							
							else if (j== 4){
								if (coluna.equalsIgnoreCase("outros")){
									empregadoDTO.setTipo("O");
								}
								else if (coluna.equalsIgnoreCase("Contrato Empresa - PJ")){
									empregadoDTO.setTipo("C");
								}
								else if (coluna.equalsIgnoreCase("Empregado CLT")){
									empregadoDTO.setTipo("E");
								}
								else if (coluna.equalsIgnoreCase("Estágio")){
									empregadoDTO.setTipo("T");
								}
								else if (coluna.equalsIgnoreCase("FreeLancer")){
									empregadoDTO.setTipo("F");
								}
								else if (coluna.equalsIgnoreCase("Sócio")){
									empregadoDTO.setTipo("X");
								}
								else if (coluna.equalsIgnoreCase("Solicitante")){
									empregadoDTO.setTipo("S");
								}
								j++;

							}
								
							else if (j==5){
								if (coluna.equalsIgnoreCase("Ativo")){
									empregadoDTO.setIdSituacaoFuncional(1);
								}
								
								else if (coluna.equalsIgnoreCase("Inativo")){
									empregadoDTO.setIdSituacaoFuncional(2);
								}
								j++;
							}
								
							else if (j==6){
								empregadoDTO.setEmail(coluna);
								j++;
							}
								
							else if (j==7){
								empregadoDTO.setTelefone(coluna);
								if (empregadoDTO.getIdEmpregado() != null){
									if (empregadoDTO.getIdCargo() != null && empregadoDTO.getIdUnidade() != null){
										empregadoDao.update(empregadoDTO);										
									}
								}
								j++;
							}
						}
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


	
	

	private CargosDTO criaCargosDTO(CargosDTO cargosDTO, CargosDao dao) throws Exception {
		return (CargosDTO) dao.create(cargosDTO);
	}

	private EmpregadoDTO criaEmpregadoDTO(EmpregadoDTO empregadoDTO, EmpregadoDao dao) throws Exception {
		
		return (EmpregadoDTO) dao.create(empregadoDTO);
	}


	private EmpregadoDTO existeEmpregadoPorNome(String coluna, EmpregadoDao empregadoDao) throws Exception {
		EmpregadoDTO empregadoDTO = new EmpregadoDTO();
		empregadoDTO.setNome(coluna);
		List<EmpregadoDTO> listEmpregado = (List<EmpregadoDTO>) empregadoDao.findByNomeEmpregado(empregadoDTO);
		 if (listEmpregado != null){
		    	return listEmpregado.get(0);
		    }
		    
			return empregadoDTO;
	}


	private UnidadeDTO existeUnidadePorNome(String coluna, UnidadeDao unidadeDao) throws Exception {
		UnidadeDTO unidadeDTO = new UnidadeDTO();
		unidadeDTO.setNome(coluna);
	    List<UnidadeDTO> listUnidade = (List<UnidadeDTO>) unidadeDao.findByNomeUnidade(unidadeDTO);
	    
	    if (listUnidade != null){
	    	return listUnidade.get(0);
	    }
	    
		return unidadeDTO;
	}
	

	private UnidadeDTO criaUnidadeDTO(UnidadeDTO unidadeDTO, UnidadeDao dao) throws Exception{
		return (UnidadeDTO) dao.create(unidadeDTO);
		
	}


	private CargosDTO existeCargosPorNome(String coluna, CargosDao cargosDao) throws Exception {
		CargosDTO cargosDTO = new CargosDTO();
		cargosDTO.setNomeCargo(coluna);
		List<CargosDTO> listCargos = (List<CargosDTO>) cargosDao.findByNomeCargos(cargosDTO);
		if (listCargos != null){
			return listCargos.get(0);
		}
		return cargosDTO;
	}
}
	
