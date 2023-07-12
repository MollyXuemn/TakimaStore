-- create table book
CREATE TABLE public.book
(
  id          BIGINT PRIMARY KEY REFERENCES public.product,
  isbn        VARCHAR(17)      NOT NULL,
  author      VARCHAR          NULL,
  format      VARCHAR          NULL
);

-- create table video_game
CREATE TABLE public.video_game
(
  id          BIGINT PRIMARY     KEY REFERENCES public.product,
  developer   VARCHAR            NOT NULL,
  platform    VARCHAR            NOT NULL,
  pegi        INT                NULL
);

-- phone
CREATE TABLE public.phone
(
  id                                BIGINT PRIMARY KEY REFERENCES public.product,
  resolution                        VARCHAR          NULL,
  bands_csv                         VARCHAR          NULL,
  back_cam_mpix                     FLOAT(4)         NULL,
  front_cam_mpix                    FLOAT(4)         NULL
);
