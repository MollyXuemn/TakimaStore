-- create table product
CREATE TABLE public.product
(
    id          BIGSERIAL PRIMARY KEY,
    ref         VARCHAR(16)      NOT NULL,
    name        VARCHAR          NOT NULL,
    brand       VARCHAR          NULL,
    description VARCHAR          NOT NULL,
    image       VARCHAR          NOT NULL,
    base_price  DOUBLE PRECISION NOT NULL,
    currency    VARCHAR(8)       NOT NULL,
    tags_csv     VARCHAR         NULL
);

ALTER SEQUENCE product_id_seq RESTART 100000 INCREMENT BY 50;

