set sql_safe_updates = 0;

-- Início THIAGO MATIAS BARBOSA 27/11/2013

ALTER TABLE atividadeperiodica CHANGE criadopor criadopor varchar (80);
ALTER TABLE atividadeperiodica CHANGE alteradopor alteradopor varchar (80);

-- Fim THIAGO MATIAS BARBOSA

-- INICIO - MURILO GABRIEL RODRIGUES - 28/11/2013

delete from menu where link like '/eventoItemConfig/eventoItemConfig.load';
delete from menu where link like '/baseItemConfiguracao/baseItemConfiguracao.load';

-- FIM - MURILO GABRIEL RODRIGUES - 28/11/2013

set sql_safe_updates = 1;
