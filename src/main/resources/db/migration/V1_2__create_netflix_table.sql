create TABLE IF NOT EXISTS `netflix` (
    `id` BIGINT NOT NULL UNIQUE PRIMARY KEY,
    `title_type` VARCHAR(255) NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `director` VARCHAR(255) NOT NULL,
    `cast` VARCHAR(255) NOT NULL,
    `country` VARCHAR(255) NOT NULL,
    `date_added` VARCHAR(255) NOT NULL,
    `release_year` INT NOT NULL,
    `rating` VARCHAR(255) NOT NULL,
    `duration` VARCHAR(255) NOT NULL,
    `category` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL
    )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
