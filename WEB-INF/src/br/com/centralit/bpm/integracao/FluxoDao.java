package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FluxoDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = -2983316142102074344L;

	private static final String TABLE_NAME = "bpm_fluxo";

	private static final String SQL_RESTORE = "SELECT f.idFluxo, t.nomeFluxo, t.descricao, t.idTipoFluxo, t.nomeClasseFluxo, f.variaveis, f.versao, f.conteudoXml, f.dataInicio, f.dataFim "
			+ "  FROM Bpm_Fluxo f INNER JOIN Bpm_TipoFluxo t ON t.idTipoFluxo = f.idTipoFluxo ";

	public FluxoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return FluxoDTO.class;
	}

	private List getListaDeCampos() {
		List listRetorno = new ArrayList();
		listRetorno.add("idFluxo");
		listRetorno.add("nomeFluxo");
		listRetorno.add("descricao");
		listRetorno.add("idTipoFluxo");
		listRetorno.add("nomeClasseFluxo");
		listRetorno.add("variaveis");
		listRetorno.add("versao");
		listRetorno.add("conteudoXml");
		listRetorno.add("dataInicio");
		listRetorno.add("dataFim");

		return listRetorno;
	}

	private List<FluxoDTO> recuperaEstrutura(List<FluxoDTO> list) throws Exception {
		if (list != null) {
			ElementoFluxoDao elementoDao = new ElementoFluxoDao();
			ElementoFluxoInicioDao fluxoInicioDao = new ElementoFluxoInicioDao();
			ElementoFluxoFinalizacaoDao fluxoFinalizacaoDao = new ElementoFluxoFinalizacaoDao();
			ElementoFluxoTarefaDao fluxoTarefaDao = new ElementoFluxoTarefaDao();
			ElementoFluxoPortaDao fluxoPortaDao = new ElementoFluxoPortaDao();
			ElementoFluxoScriptDao fluxoScriptDao = new ElementoFluxoScriptDao();
			ElementoFluxoEventoDao fluxoEventoDao = new ElementoFluxoEventoDao();
			SequenciaFluxoDao fluxoSequenciaDao = new SequenciaFluxoDao();

			TransactionControler tc = this.getTransactionControler();
			fluxoInicioDao.setTransactionControler(tc);
			fluxoFinalizacaoDao.setTransactionControler(tc);
			fluxoTarefaDao.setTransactionControler(tc);
			fluxoPortaDao.setTransactionControler(tc);
			fluxoScriptDao.setTransactionControler(tc);
			fluxoSequenciaDao.setTransactionControler(tc);
			fluxoEventoDao.setTransactionControler(tc);
			elementoDao.setTransactionControler(tc);

			for (FluxoDTO fluxoDto : list) {
				String id = fluxoDto.getNomeFluxo().trim();
				if (fluxoDto.getVersao() != null)
					id += "_v" + fluxoDto.getVersao();
				fluxoDto.setIdentificador(id);

				Integer idFluxo = fluxoDto.getIdFluxo();
				fluxoDto.setInicioFluxo(fluxoInicioDao.restoreByIdFluxo(idFluxo));
				fluxoDto.setColTarefas(fluxoTarefaDao.findByIdFluxo(idFluxo));
				fluxoDto.setColScripts(fluxoScriptDao.findByIdFluxo(idFluxo));
				fluxoDto.setColPortas(fluxoPortaDao.findByIdFluxo(idFluxo));
				fluxoDto.setColEventos(fluxoEventoDao.findByIdFluxo(idFluxo));
				fluxoDto.setColFinalizacoes(fluxoFinalizacaoDao.findByIdFluxo(idFluxo));
				fluxoDto.setColSequenciamentos(fluxoSequenciaDao.findByIdFluxo(idFluxo));
				fluxoDto.setColElementos(elementoDao.findAllByIdFluxo(idFluxo));
			}
		}
		return list;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("idFluxo", "idFluxo", true, true, false, false));
		listFields.add(new Field("idTipoFluxo", "idTipoFluxo", false, false, false, false));
		listFields.add(new Field("versao", "versao", false, false, false, false));
		listFields.add(new Field("variaveis", "variaveis", false, false, false, false));
		listFields.add(new Field("conteudoXml", "conteudoXml", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection list() throws Exception {
		String sql = SQL_RESTORE + " WHERE f.dataFim IS NULL " + " ORDER BY t.nomeFluxo, f.idFluxo";

		List lista = this.execSQL(sql, null);

		List result = recuperaEstrutura(this.engine.listConvertion(getBean(), lista, getListaDeCampos()));
		return result;
	}

	public Collection listAll() throws Exception {
		String sql = SQL_RESTORE + " ORDER BY t.nomeFluxo, f.idFluxo";

		List lista = this.execSQL(sql, null);

		List result = recuperaEstrutura(this.engine.listConvertion(getBean(), lista, getListaDeCampos()));
		return result;
	}

	@Override
	public IDto restore(IDto obj) throws Exception {
		FluxoDTO fluxoDto = (FluxoDTO) obj;
		if (fluxoDto == null || fluxoDto.getIdFluxo() == null)
			return null;

		String sql = SQL_RESTORE + " WHERE f.idFluxo = ? ";

		List lista = this.execSQL(sql, new Object[] { fluxoDto.getIdFluxo() });

		List result = recuperaEstrutura(this.engine.listConvertion(getBean(), lista, getListaDeCampos()));
		if (result != null && !result.isEmpty())
			return (FluxoDTO) result.get(0);
		else
			return null;
	}

	public FluxoDTO findByTipoFluxo(Integer idTipoFluxo) throws Exception {
		String sql = SQL_RESTORE + " WHERE f.dataFim IS NULL " + "   AND f.idTipoFluxo = ? ";

		List lista = this.execSQL(sql, new Object[] { idTipoFluxo });

		List result = recuperaEstrutura(this.engine.listConvertion(getBean(), lista, getListaDeCampos()));
		if (result != null && !result.isEmpty())
			return (FluxoDTO) result.get(0);
		else
			return null;
	}
	
	public Collection findTodosByTipoFluxo(Integer idTipoFluxo) throws Exception {
		String sql = SQL_RESTORE + " WHERE f.idTipoFluxo = ? ";

		List lista = this.execSQL(sql, new Object[] { idTipoFluxo });

		return recuperaEstrutura(this.engine.listConvertion(getBean(), lista, getListaDeCampos()));
	}

	public FluxoDTO findByVersao(FluxoDTO fluxoDto) throws Exception {
		String sql = SQL_RESTORE + " WHERE t.nomeFluxo = ? " + "   AND f.versao    = ? ";

		List lista = this.execSQL(sql, new Object[] { fluxoDto.getNomeFluxo(), fluxoDto.getVersao() });

		List result = recuperaEstrutura(this.engine.listConvertion(getBean(), lista, getListaDeCampos()));
		if (result != null && !result.isEmpty())
			return (FluxoDTO) result.get(0);
		else
			return null;
	}

	public FluxoDTO findByNome(String nomeFluxo) throws Exception {
		String sql = SQL_RESTORE + " WHERE t.nomeFluxo = ? " + "   AND f.dataFim IS NULL ";

		List lista = this.execSQL(sql, new Object[] { nomeFluxo });

		List result = recuperaEstrutura(this.engine.listConvertion(getBean(), lista, getListaDeCampos()));
		if (result != null && !result.isEmpty())
			return (FluxoDTO) result.get(0);
		else
			return null;
	}

	@Override
	public void updateNotNull(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		super.updateNotNull(obj);
	}
}
