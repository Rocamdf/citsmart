package br.com.centralit.citcorpore.integracao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

@SuppressWarnings({"rawtypes","unchecked"})
public class RequisicaoViagemDAO extends CrudDaoDefaultImpl{

	public RequisicaoViagemDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Collection find(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idsolicitacaoservico" ,"idSolicitacaoServico", true, false, false, false));
		listFields.add(new Field("idprojeto" ,"idProjeto", false, false, false, false));
        listFields.add(new Field("idcentrocusto" ,"idCentroCusto", false, false, false, false));
		listFields.add(new Field("idcidadeorigem" ,"idCidadeOrigem", false, false, false, false));
		listFields.add(new Field("idcidadedestino" ,"idCidadeDestino", false, false, false, false));
		listFields.add(new Field("idmotivoviagem" ,"idMotivoViagem", false, false, false, false));
		listFields.add(new Field("idaprovacao" ,"idAprovacao", false, false, false, false));
		listFields.add(new Field("descricaomotivo" ,"descricaoMotivo", false, false, false, false));
		listFields.add(new Field("datainicio" ,"dataInicioViagem", false, false, false, false));
		listFields.add(new Field("datafim" ,"dataFimViagem", false, false, false, false));
		listFields.add(new Field("qtdedias" ,"qtdeDias", false, false, false, false));
		listFields.add(new Field("estado" ,"estado", false, false, false, false));
		listFields.add(new Field("tarefainiciada" ,"tarefaIniciada", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return this.getOwner() + "requisicaoviagem";
	}

	@Override
	public Collection list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getBean() {
		return RequisicaoViagemDTO.class;
	}

	/* (non-Javadoc)
	 * @see br.com.citframework.integracao.CrudDaoDefaultImpl#updateNotNull(br.com.citframework.dto.IDto)
	 */
	@Override
	public void updateNotNull(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		super.updateNotNull(obj);
	}
	
	
	public Collection<RequisicaoViagemDTO> recuperaRequisicoesViagem(RequisicaoViagemDTO requisicaoViagemDto, Integer pgAtual, Integer qtdPaginacao) throws Exception {
		
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuffer sql = new StringBuffer();
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
			sql.append(" ;WITH TabelaTemporaria AS ( ");
		}
		
        sql.append(" SELECT idsolicitacaoservico, datainicio, datafim, descricaomotivo ");
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
        	sql.append(" , ROW_NUMBER() OVER (ORDER BY idsolicitacaoservico) AS Row ");
    	}
        sql.append(" FROM requisicaoviagem ");
        sql.append(" WHERE estado <> 'Rejeitada Planejamento' AND estado <> 'Não Aprovada' AND estado <>'Finalizada'");
        
        if (requisicaoViagemDto.getIdSolicitacaoServico() != null) {
            sql.append(" AND idSolicitacaoServico = ? ");
            parametro.add(requisicaoViagemDto.getIdSolicitacaoServico());
        }
        
	    if (requisicaoViagemDto.getDataInicio() != null) {
            sql.append(" AND datainicio between ? ");
            parametro.add(requisicaoViagemDto.getDataInicio());
        }
        
	    if (requisicaoViagemDto.getDataFim() != null) {
            sql.append(" AND ? ");
            parametro.add(requisicaoViagemDto.getDataFim());
        }
        
        /*sql.append(" ORDER BY idsolicitacaoservico");*/
        	
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
        	sql.append(" ORDER BY idsolicitacaoservico");
        	Integer pgTotal = pgAtual * qtdPaginacao;
        	pgAtual = pgTotal - qtdPaginacao;
        	sql.append(" LIMIT " + qtdPaginacao + " OFFSET " +pgAtual);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)){
        	sql.append(" ORDER BY idsolicitacaoservico");
        	Integer pgTotal = pgAtual * qtdPaginacao;
        	pgAtual = pgTotal - qtdPaginacao;
        	sql.append(" LIMIT " +pgAtual+ ", "+qtdPaginacao);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)){
        	Integer quantidadePaginator2 = new Integer(0);
        	if (pgAtual > 0) {
        		quantidadePaginator2 = qtdPaginacao * pgAtual;
        		pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
        	}else{
        		quantidadePaginator2 = qtdPaginacao;
        		pgAtual = 0;
        	}
        	sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row> "+pgAtual+" and Row<"+(quantidadePaginator2+1)+" ");
        }

        String sqlOracle = "";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)){
        	Integer quantidadePaginator2 = new Integer(0);
        	if (pgAtual > 1) {
        		quantidadePaginator2 = qtdPaginacao * pgAtual;
        		pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
        		pgAtual = pgAtual + 1;
        	}else{
        		quantidadePaginator2 = qtdPaginacao;
        		pgAtual = 0;
        	}
        	int intInicio = pgAtual;
        	int intLimite = quantidadePaginator2;
        	sqlOracle = paginacaoOracle(sql.toString(), intInicio, intLimite);
        }
        
        List lista = new ArrayList();
        
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)){
        	lista = this.execSQL(sqlOracle, parametro.toArray());
    	}else{
    		lista = this.execSQL(sql.toString(), parametro.toArray());
    	}
        
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("dataInicio");
        listRetorno.add("dataFim");
        listRetorno.add("descricaoMotivo");
        
        return this.engine.listConvertion(getBean(), lista, listRetorno);
	}
	
	public Integer calculaTotalPaginas(Integer itensPorPagina, RequisicaoViagemDTO requisicaoViagemDto) throws Exception {
		List parametro = new ArrayList();
		StringBuffer sql = new StringBuffer();
		
        sql.append("SELECT COUNT(*) ");
        sql.append("FROM requisicaoviagem ");
        sql.append("WHERE estado <> 'Rejeitada Planejamento' AND estado <> 'Não Aprovada' AND estado <>'Finalizada'");
        
        if (requisicaoViagemDto.getIdSolicitacaoServico() != null) {
            sql.append(" AND idSolicitacaoServico = ? ");
            parametro.add(requisicaoViagemDto.getIdSolicitacaoServico());
        }
        
	    if (requisicaoViagemDto.getDataInicio() != null) {
            sql.append(" AND datainicio between ? ");
            parametro.add(requisicaoViagemDto.getDataInicio());
        }
        
	    if (requisicaoViagemDto.getDataFim() != null) {
            sql.append(" AND ? ");
            parametro.add(requisicaoViagemDto.getDataFim());
        }
        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        Long totalLinhaLong = 0l;
        Long totalPagina = 0l;
        Integer total = 0;
        BigDecimal totalLinhaBigDecimal;
        Integer totalLinhaInteger;
        int intLimite = itensPorPagina;
        if(lista != null){
        	Object[] totalLinha = (Object[]) lista.get(0);
        	if(totalLinha != null && totalLinha.length > 0){
        		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
        			totalLinhaLong = (Long) totalLinha[0];
        		}
        		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
        			totalLinhaBigDecimal = (BigDecimal) totalLinha[0];
        			totalLinhaLong = totalLinhaBigDecimal.longValue();
        		}
        		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
        			totalLinhaInteger = (Integer) totalLinha[0];
        			totalLinhaLong = Long.valueOf(totalLinhaInteger);
        		}
        	}
        }

        if (totalLinhaLong > 0) {
        	totalPagina = (totalLinhaLong / intLimite);
        	if(totalLinhaLong % intLimite != 0){
        		totalPagina = totalPagina + 1;
        	}
        }
        total = Integer.valueOf(totalPagina.toString());
        return total;
        
	}
	
	public String paginacaoOracle(String strSQL, int intInicio, int intLimite) {
		strSQL = strSQL + " order by idsolicitacaoservico ";
		return "SELECT * FROM (SELECT PAGING.*, ROWNUM PAGING_RN FROM" +
		" (" + strSQL + ") PAGING WHERE (ROWNUM <= " + intLimite + "))" +
		" WHERE (PAGING_RN >= " + intInicio + ") ";
	}
	

}
