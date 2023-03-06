-- remove columns that belongs to product

ALTER TABLE article

DROP COLUMN ref,

  DROP COLUMN name,

  DROP COLUMN description,

  DROP COLUMN image;

