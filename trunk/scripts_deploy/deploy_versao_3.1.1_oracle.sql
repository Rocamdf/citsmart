-- Início THIAGO MATIAS BARBOSA 27/11/2013

ALTER TABLE atividadeperiodica MODIFY (criadopor VARCHAR2(80));
ALTER TABLE atividadeperiodica MODIFY (alteradopor VARCHAR2(80));

-- Fim THIAGO MATIAS BARBOSA

-- INICIO - MURILO GABRIEL RODRIGUES - 28/11/2013

delete from menu where link like '/eventoItemConfig/eventoItemConfig.load';
delete from menu where link like '/baseItemConfiguracao/baseItemConfiguracao.load';

-- FIM - MURILO GABRIEL RODRIGUES - 28/11/2013
