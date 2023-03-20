CREATE TABLE public.offer

(

    id          BIGSERIAL PRIMARY KEY,

    type        VARCHAR(16),

    code        VARCHAR(16) UNIQUE,

    description VARCHAR,

    start_date  TIMESTAMP  NOT NULL,

    expire_date TIMESTAMP  NOT NULL,

    min_qty     INT CONSTRAINT min_qty_check CHECK (min_qty >= 0),

    max_qty     INT CONSTRAINT max_qty_check CHECK (max_qty >= min_qty),

    min_price   DOUBLE PRECISION CONSTRAINT min_price_check CHECK (min_price >= 0),

    max_price   DOUBLE PRECISION CONSTRAINT max_price_check CHECK (min_price >= min_price),

    amount      DOUBLE PRECISION,

    currency    VARCHAR(8) NOT NULL,

    percent     DOUBLE PRECISION CHECK (percent >= 0)

);

CREATE TABLE public.article_offer

(

    offer_id   BIGINT REFERENCES offer (id),

    article_id BIGINT REFERENCES article (id),

    CONSTRAINT article_offer_pk PRIMARY KEY (article_id, offer_id)

);

CREATE TABLE public.cart_offer

(

    offer_id BIGINT REFERENCES offer (id),

    cart_id  BIGINT REFERENCES cart (id),

    CONSTRAINT cart_offer_pk PRIMARY KEY (cart_id, offer_id)

);

