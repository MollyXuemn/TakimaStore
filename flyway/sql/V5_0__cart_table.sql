CREATE TABLE public.cart
(
  id          BIGSERIAL PRIMARY KEY,
  customer_id BIGINT REFERENCES customer (id),
  date        TIMESTAMP
);

CREATE TABLE public.cart_article
(
  cart_id    BIGINT NOT NULL REFERENCES cart (id),
  article_id BIGINT NOT NULL REFERENCES article (id),
  qty        int NOT NULL CHECK (qty > 0),
  _order     int CHECK (_order >= 0),
  CONSTRAINT cart_article_pk PRIMARY KEY (article_id, cart_id)
);

ALTER SEQUENCE cart_id_seq RESTART 100000 INCREMENT BY 50;
