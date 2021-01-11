create TABLE IF NOT EXISTS `tesla_stock` (
    `date` DATE NOT NULL UNIQUE PRIMARY KEY,
    `open` DOUBLE NOT NULL,
    `high` DOUBLE NOT NULL,
    `low` DOUBLE NOT NULL,
    `close` DOUBLE NOT NULL,
    `volume` BIGINT NOT NULL,
    `adj_close` DOUBLE NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
