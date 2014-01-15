package br.com.centralit.citcorpore.util;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.integracao.ParametroCorporeDAO;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.citframework.integracao.TransactionControler;

public class ParametroUtil {

	private static HashMap<Integer, String> parametroCitSmart = new HashMap<Integer, String>();

	/**
	 * Atualiza HashMap stático que armazena os parâmetros do CITSMart.
	 * 
	 * @param parametroSistema
	 *            - Parâmetro do sistema.
	 * @param valor
	 *            - Valor do parâmetro.
	 * @author valdoilo.damasceno
	 */
	public static void atualizarHashMapParametroCitSmart(Integer id, String valor) {
		if (id != null) {
			parametroCitSmart.put(id.intValue(), valor);
		}
	}

	/**
	 * Realiza consulta do valor do Parâmetro no BD.
	 * 
	 * @param parametro
	 * @param valorDefault
	 * @return valorParametro
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public static String getValor(ParametroSistema parametro) throws Exception {

		ParametroCorporeDTO parametroDto = new ParametroCorporeDAO().getParamentroAtivo(parametro.id());

		if (parametroDto.getValor() == null || StringUtils.isBlank(parametroDto.getValor().trim())) {
			return null;
		} else {
			return parametroDto.getValor().trim();
		}
	}

	public static String getValor(ParametroSistema parametro, TransactionControler tc, String valorDefault) throws Exception {
		// ParametroCorporeDAO parametroCorporeDao = new ParametroCorporeDAO();
		//
		// if (tc != null)
		// parametroCorporeDao.setTransactionControler(tc);
		//
		// ParametroCorporeDTO parametroDto = parametroCorporeDao.getParamentroAtivo(parametro.id());
		//
		// if (parametroDto.getValor() == null || StringUtils.isBlank(parametroDto.getValor().trim())) {
		// return valorDefault;
		// } else {
		// return parametroDto.getValor().trim();
		// }

		return getValorParametroCitSmartHashMap(parametro, valorDefault);
	}

	/**
	 * Retorna valor do Parâmetro que está armazenado no HashMap stático. Caso não haja valor armazenado retorna valor default informado.
	 * 
	 * @param parametro
	 *            - ParametroSistema informado.
	 * @param valorDefault
	 *            - Valor padrão que deverá ser assumido caso não haja nenhuma armazenado.
	 * @return ValorParametroCitSmart
	 * @author valdoilo.damasceno
	 */
	public static String getValorParametroCitSmartHashMap(ParametroSistema parametro, String valorDefault) {

		String valorParametroCitSmart = parametroCitSmart.get(parametro.id());

		if (valorParametroCitSmart != null && StringUtils.isNotBlank(valorParametroCitSmart)) {
			return valorParametroCitSmart;
		} else {
			return valorDefault;
		}

	}
	// public static String getValue(ParametroSistema parametro, String valorDefault) throws Exception {
	// return getValor(parametro, valorDefault);
	// }

	// public static ParametroCorporeDTO getValueBean(int id) throws Exception {
	// ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
	// return parametroCorporeService.getParamentroAtivo(id);
	// }

	// public static String getValue(int id) throws Exception {
	// ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
	// ParametroCorporeDTO parametroCorporeDTO = parametroCorporeService.getParamentroAtivo(id);
	// if (parametroCorporeDTO == null) {
	// return null;
	// }
	// return parametroCorporeDTO.getValor();
	// }

	// public static String getValue(int id, String valorDefault) throws Exception {
	// ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
	// ParametroCorporeDTO parametroCorporeDTO = parametroCorporeService.getParamentroAtivo(id);
	// if (parametroCorporeDTO == null) {
	// return valorDefault;
	// }
	// return parametroCorporeDTO.getValor();
	// }

}
