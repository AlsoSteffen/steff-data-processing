create TABLE IF NOT EXISTS `tweets` (
     `id` BIGINT NOT NULL UNIQUE PRIMARY KEY,
     `link` VARCHAR(255) NOT NULL,
     `content` VARCHAR(255) NOT NULL,
     `date` DATE NOT NULL,
     `retweets` INT NOT NULL,
     `favorites` INT NOT NULL,
     `mentions` VARCHAR(255) NOT NULL,
     `tags` VARCHAR(255) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
