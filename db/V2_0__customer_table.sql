CREATE TABLE public.customer
(
  id        BIGSERIAL PRIMARY KEY,
  gender    CHAR(1) NOT NULL CHECK (gender IN ('m', 'f') ),
  firstname VARCHAR NOT NULL,
  lastname  VARCHAR NOT NULL,
  email     VARCHAR NOT NULL UNIQUE,
  street    VARCHAR,
  city      VARCHAR,
  zipcode   VARCHAR,
  country   VARCHAR,
  iban      VARCHAR
);

ALTER SEQUENCE customer_id_seq RESTART 100000 INCREMENT BY 50;
