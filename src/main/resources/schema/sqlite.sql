-- Trade SQLITE Schema
CREATE TABLE IF NOT EXISTS `Shops`
    `id`           INTEGER PRIMARY KEY NOT NULL,
    `uuid`         VARCHAR(36)         NOT NULL,
    `world`        VARCHAR(64)         NOT NULL,
    `x`            BIGINT              NOT NULL,
    `y`            BIGINT              NOT NULL,
    `z`            BIGINT              NOT NULL,
    `shop`         BIGINT              NOT NULL
);

CREATE TABLE IF NOT EXISTS `Items`
    `id`           INTEGER PRIMARY KEY NOT NULL,
    `shop`         BIGINT              NOT NULL,
    `z`
);