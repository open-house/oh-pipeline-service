ALTER TABLE `pipeline_service`.`phases` ADD COLUMN `auto` TINYINT UNSIGNED NOT NULL DEFAULT 1 AFTER `name`;
