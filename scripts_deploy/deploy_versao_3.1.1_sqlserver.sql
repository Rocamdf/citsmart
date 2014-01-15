-- Início THIAGO MATIAS BARBOSA 27/11/2013

ALTER TABLE atividadeperiodica ALTER COLUMN criadopor VARCHAR(80);
ALTER TABLE atividadeperiodica ALTER COLUMN alteradopor VARCHAR(80);

-- Fim THIAGO MATIAS BARBOSA

-- INICIO - MURILO GABRIEL RODRIGUES - 28/11/2013

delete from menu where link like '/eventoItemConfig/eventoItemConfig.load';
delete from menu where link like '/baseItemConfiguracao/baseItemConfiguracao.load';

-- FIM - MURILO GABRIEL RODRIGUES - 28/11/2013
