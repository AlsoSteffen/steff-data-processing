# create TABLE IF NOT EXISTS `tweet_mentions` (
#      `id` BIGINT NOT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
#      `tweet_id` BIGINT NOT NULL,
#      `mention_id` BIGINT NOT NULL,
#      INDEX `tweet_id` (`tweet_id`),
#      INDEX `mention_id` (`mention_id`),
#      FOREIGN KEY (`tweet_id`) REFERENCES tweets(`id`) ON delete CASCADE,
#      FOREIGN KEY (`mention_id`) REFERENCES mentions(`id`) ON delete CASCADE
# )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
