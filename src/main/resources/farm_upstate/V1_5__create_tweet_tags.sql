# create TABLE IF NOT EXISTS `tweet_tags` (
#     `id` BIGINT NULL UNIQUE PRIMARY KEY AUTO_INCREMENT,
#     `tweet_id` BIGINT NOT NULL,
#     `tag_id` BIGINT NOT NULL,
#      INDEX `tweet_id` (`tweet_id`),
#      INDEX `tag_id` (`tag_id`),
#      FOREIGN KEY (`tweet_id`) REFERENCES tweets(`id`) ON delete CASCADE,
#      FOREIGN KEY (`tag_id`) REFERENCES tags(`id`) ON delete CASCADE
# )ENGINE=InnoDB DEFAULT CHARSET=UTF8;
