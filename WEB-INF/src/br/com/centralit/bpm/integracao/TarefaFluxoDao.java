package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TarefaFluxoDao extends ItemTrabalhoFluxoDao {

	private static final long serialVersionUID = 9015480787171060445L;

	public Class getBean() {
		return TarefaFluxoDTO.class;
	}

	public Collection<TarefaFluxoDTO> findByIdResponsavel(Integer idResponsavel) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idResponsavelAtual", "=", idResponsavel));
		condicao.add(new Condition("situacao", "<>", Enumerados.SituacaoItemTrabalho.Executado.name()));

		return super.findByCondition(condicao, null);
	}

	@Override
	public void updateNotNull(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		super.updateNotNull(obj);
	}

	/**
	 * @param idUsuario
	 * @param listGrupoBpmDTO
	 * @param idTarefa
	 * @return
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public List<TarefaFluxoDTO> findDisponiveisByIdUsuarioAndGruposBpm(Integer idUsuario, Collection<GrupoBpmDTO> listGrupoBpmDTO, Integer idTarefa) throws Exception {

		List<Object> parametros = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT " + this.getNamesFieldsStr("i") + ", " + "a.tipo" + ", " + "instancia.idfluxo");
		sql.append(" FROM   bpm_atribuicaofluxo a ");
		sql.append("       INNER JOIN bpm_itemtrabalhofluxo i ");
		sql.append("               ON a.iditemtrabalho = i.iditemtrabalho ");
		sql.append("       INNER JOIN bpm_instanciafluxo instancia ");
		sql.append("               ON i.idinstancia = instancia.idinstancia ");
		sql.append(" WHERE  i.situacao NOT IN ( ?, ? ) ");

		parametros.add(Enumerados.SituacaoItemTrabalho.Executado.name());
		parametros.add(Enumerados.SituacaoItemTrabalho.Cancelado.name());
		
		/* Incluido por Carlos Santos em 06.11.2013 */
		if (idTarefa != null) {
			sql.append(" AND i.idItemTrabalho = ? ");
			parametros.add(idTarefa);
		}

		sql.append("       AND ( a.idusuario = ? ");
		parametros.add(idUsuario);

		if (listGrupoBpmDTO != null && !listGrupoBpmDTO.isEmpty()) {
			sql.append("              OR a.idgrupo IN ( ");

			int i = 0;
			for (GrupoBpmDTO grupoBpmDto : listGrupoBpmDTO) {
				if (i > 0) {
					sql.append(",");
				}
				sql.append("?");
				parametros.add(grupoBpmDto.getIdGrupo());
				i++;
			}
			sql.append(" ) ");
		}

		sql.append("              OR i.idresponsavelatual = ? ) ");
		parametros.add(idUsuario);
		sql.append("       AND tipo = ? ");
		parametros.add(TipoAtribuicao.Automatica.name());
		sql.append(" UNION ALL ");
		sql.append(" SELECT DISTINCT " + this.getNamesFieldsStr("i") + ", " + "a.tipo " + ", " + "instancia.idfluxo");
		sql.append(" FROM   bpm_atribuicaofluxo a ");
		sql.append("       INNER JOIN bpm_itemtrabalhofluxo i ");
		sql.append("               ON a.iditemtrabalho = i.iditemtrabalho ");
		sql.append("       INNER JOIN bpm_instanciafluxo instancia ");
		sql.append("               ON i.idinstancia = instancia.idinstancia ");
		sql.append(" WHERE  i.situacao NOT IN ( ?, ? ) ");

		parametros.add(Enumerados.SituacaoItemTrabalho.Executado.name());
		parametros.add(Enumerados.SituacaoItemTrabalho.Cancelado.name());

		/* Incluido por Carlos Santos em 06.11.2013 */
		if (idTarefa != null) {
			sql.append(" AND i.idItemTrabalho = ? ");
			parametros.add(idTarefa);
		}

		sql.append("       AND ( a.idusuario = ? ");
		parametros.add(idUsuario);

		if (listGrupoBpmDTO != null && !listGrupoBpmDTO.isEmpty()) {
			sql.append("              OR a.idgrupo IN ( ");

			int i = 0;
			for (GrupoBpmDTO grupoBpmDto : listGrupoBpmDTO) {
				if (i > 0) {
					sql.append(",");
				}
				sql.append("?");
				parametros.add(grupoBpmDto.getIdGrupo());
				i++;
			}
			sql.append(" ) ");
		}

		sql.append("              OR i.idresponsavelatual = ? ) ");
		parametros.add(idUsuario);

		sql.append("       AND tipo = ? ");
		parametros.add(TipoAtribuicao.Acompanhamento.name());
		sql.append("       AND a.iditemtrabalho NOT IN (SELECT a.iditemtrabalho ");
		sql.append("                                    FROM   bpm_atribuicaofluxo a ");
		sql.append("                                           INNER JOIN bpm_itemtrabalhofluxo i ");
		sql.append("                                                   ON a.iditemtrabalho = i.iditemtrabalho ");
		sql.append("                                    WHERE  i.situacao NOT IN ( ");
		sql.append("                                           ?, ? ");

		parametros.add(Enumerados.SituacaoItemTrabalho.Executado.name());
		parametros.add(Enumerados.SituacaoItemTrabalho.Cancelado.name());

		sql.append("                                                             ) ");
		sql.append("                                           AND ( a.idusuario = ? ");
		parametros.add(idUsuario);

		if (listGrupoBpmDTO != null && !listGrupoBpmDTO.isEmpty()) {
			sql.append("              OR a.idgrupo IN ( ");

			int i = 0;
			for (GrupoBpmDTO grupoBpmDto : listGrupoBpmDTO) {
				if (i > 0) {
					sql.append(",");
				}
				sql.append("?");
				parametros.add(grupoBpmDto.getIdGrupo());
				i++;
			}
			sql.append(" ) ");
		}

		sql.append("              OR i.idresponsavelatual = ? ) ");
		parametros.add(idUsuario);

		sql.append("                                           AND tipo = ?)");
		parametros.add(TipoAtribuicao.Automatica.name());

		List lista = this.execSQL(sql.toString(), parametros.toArray());

		List listRetorno = new ArrayList(this.getListNamesFieldClass());

		listRetorno.add("tipoAtribuicao");
		listRetorno.add("idFluxo");

		List<TarefaFluxoDTO> listTarefaFluxoDto = this.engine.listConvertion(getBean(), lista, listRetorno);

		return listTarefaFluxoDto;
	}
}
