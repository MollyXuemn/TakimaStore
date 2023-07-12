-- populate products from articles
INSERT INTO product(
  id,
  ref,
  name,
  description,
  image,
  base_price,
  currency
)
  SELECT DISTINCT
    a.id,
    a.ref,
    a.name,
    a.description,
    a.image,
    a.price,
    a.currency
  FROM article a ORDER BY a.id;

-- create reference between article and their respective products
ALTER TABLE public.article ADD COLUMN product_id BIGINT NULL REFERENCES product(id);
UPDATE article a SET product_id = id;
ALTER TABLE public.article
  ALTER COLUMN product_id SET NOT NULL;
