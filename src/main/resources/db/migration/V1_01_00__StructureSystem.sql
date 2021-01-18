CREATE TABLE `role` (
  `id` 				INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` 			VARCHAR(255) DEFAULT NULL
);

CREATE TABLE `profile` (
  `id` 				INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `first_name`		VARCHAR(255) NOT NULL,
  `last_name`		VARCHAR(255) NOT NULL,
  `date_of_birth`	DATETIME NOT NULL,
  `gender`			BIT NOT NULL,
  `phone_number`	VARCHAR(10) NOT NULL
);

CREATE TABLE `user` (
  `id` 				INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `email` 			VARCHAR(255) NOT NULL,
  `password` 		VARCHAR(255) NOT NULL,
  `status`			INT NOT NULL,
  `profile_id` 		INT NOT NULL,
  CONSTRAINT `FK_user_profile` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`)
);

CREATE TABLE `user_role` (
  `user_id` 		INT NOT NULL,
  `role_id` 		INT NOT NULL,
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

CREATE TABLE `department` (
  `id` 				INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name`			VARCHAR(255) NOT NULL,
  `short_name`		VARCHAR(6) NULL,
  `description`		LONGTEXT NULL,
  `object_status` 	BIT NOT NULL,
  `user_id`			INT NULL,
  `created_by` 		INT NULL DEFAULT 0,
  `created_date` 	DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` 	INT NULL DEFAULT 0,
  `modified_date` 	DATETIME NULL,
  CONSTRAINT `FK_department_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `team` (
  `id` 				INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `department_id`	INT NOT NULL,
  `name`			VARCHAR(255) NOT NULL,
  `short_name`		VARCHAR(6) NULL,
  `description`		LONGTEXT NULL,
  `object_status` 	BIT NOT NULL,
  `created_by` 		INT NULL DEFAULT 0,
  `created_date` 	DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_by` 	INT NULL DEFAULT 0,
  `modified_date` 	DATETIME NULL,
  CONSTRAINT `FK_team_department` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
);

CREATE TABLE `team_assign` (
  `team_id`			INT NOT NULL,
  `user_id`			INT NOT NULL,
  CONSTRAINT `FK_team_assign_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_team_assign_team` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`)
);