ALTER TABLE `pipeline_service`.`phases` 
ADD COLUMN `username` VARCHAR(45) NULL AFTER `timestamp`,
ADD COLUMN `password` VARCHAR(255) NULL AFTER `username`;