package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.util.Constantes;
import br.com.citframework.integracao.TransactionControlerImpl;

@SuppressWarnings("rawtypes")
public class ConfiguracaoAmbiente extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.focusInFirstActivateField(null);

		TransactionControlerImpl tc = new TransactionControlerImpl(Constantes.getValue("DATABASE_ALIAS"));
		Connection connection = (Connection) tc.getTransactionObject();
		DatabaseMetaData meta = connection.getMetaData();

		request.setAttribute("versao_driver_jdbc", meta.getDriverVersion());

		connection.close();
		tc.close();
		connection = null;
		tc = null;
	}

	@Override
	public Class getBeanClass() {
		return Object.class;
	}

}