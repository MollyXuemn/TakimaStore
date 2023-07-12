CREATE TABLE public.seller
(
  id      BIGSERIAL PRIMARY KEY,
  name    VARCHAR NOT NULL,
  street  VARCHAR NOT NULL,
  city    VARCHAR NOT NULL,
  zipcode VARCHAR NOT NULL,
  country VARCHAR NOT NULL,
  iban    VARCHAR NOT NULL
);

CREATE TABLE public.article
(
  id          BIGSERIAL PRIMARY KEY,
  seller_id   BIGINT           NOT NULL REFERENCES seller (id),
  ref         VARCHAR(16)      NOT NULL,
  name        VARCHAR          NOT NULL,
  description VARCHAR          NULL,
  image       VARCHAR          NOT NULL,
  qty         INT              NOT NULL CHECK (qty > -1) DEFAULT 0,
  price       DOUBLE PRECISION NOT NULL,
  currency    VARCHAR(8)       NOT NULL
);

ALTER SEQUENCE article_id_seq RESTART 100000 INCREMENT BY 50;
ALTER SEQUENCE seller_id_seq RESTART 100000 INCREMENT BY 50;
