ALTER TABLE `citsmart_homologacao`.`cargos` ADD COLUMN `iddescricaocargo` INT NULL  AFTER `datafim` ,   ADD CONSTRAINT `fk_descricaocargo`  FOREIGN KEY (`iddescricaocargo` )  REFERENCES `citsmart_homologacao`.`rh_descricaocargo` (`iddescricaocargo` )  ON DELETE NO ACTION  ON UPDATE NO ACTION, ADD INDEX `fk_descricaocargo_idx` (`iddescricaocargo` ASC) ;