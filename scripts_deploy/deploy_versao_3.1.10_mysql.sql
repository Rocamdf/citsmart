set sql_safe_updates = 0;

-- INÍCIO - MARIO HAYASAKI JUNIOR 30/12/2013

ALTER TABLE planomelhoria CHANGE COLUMN criadopor criadopor VARCHAR(255);

ALTER TABLE planomelhoria CHANGE COLUMN modificadopor modificadopor VARCHAR(255);

-- FIM - MARIO HAYASAKI JUNIOR

set sql_safe_updates = 1;
