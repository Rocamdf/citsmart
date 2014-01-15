-- CARLOS SANTOS - 10/05 - Altera��o nas tarefas do fluxo

alter table solicitacaoservico 
	add column idtarefaencerramento bigint(20) null default null  after prazocapturamm,   
	add constraint fk_tarefa_encerramento foreign key (idtarefaencerramento) references bpm_itemtrabalhofluxo (iditemtrabalho ), 
	add index fk_tarefa_encerramento_idx (idtarefaencerramento asc);


update solicitacaoservico sol set idtarefaencerramento = 
		(select max(o.iditemtrabalho) from ocorrenciasolicitacao o inner join bpm_itemtrabalhofluxo i on i.iditemtrabalho = o.iditemtrabalho
		where upper(i.situacao) = 'EXECUTADO' 
			and i.idresponsavelatual is not null 
			and upper(o.categoria) = 'EXECUCAO' 
			and (upper(dadossolicitacao) like '%RESOLVIDA%' 
			or upper(dadossolicitacao) like '%CANCELADA%')
			and o.idsolicitacaoservico = sol.idsolicitacaoservico)
where idtarefaencerramento is null;

