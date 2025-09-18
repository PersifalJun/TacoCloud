CREATE KEYSPACE IF NOT EXISTS tacocloud
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}
     AND durable_writes = true;

USE tacocloud;

DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS tacos;
DROP TYPE IF EXISTS taco;
DROP TYPE IF EXISTS ingredient;

CREATE TYPE ingredient (
                           name text,
                           type text
                       );

CREATE TYPE taco (
                     name text,
                     ingredients list<frozen<ingredient>>
                 );

CREATE TABLE IF NOT EXISTS ingredients (
                                           id   text PRIMARY KEY,
                                           name text,
                                           type text
);

CREATE TABLE IF NOT EXISTS tacos (
                                     id timeuuid PRIMARY KEY,
                                     name text,
                                     createdat timestamp,
                                     ingredients list<frozen<ingredient>>
);

CREATE TABLE IF NOT EXISTS orders (
                                      id timeuuid PRIMARY KEY,
                                      placed_at timestamp,
                                      delivery_name   text,
                                      delivery_street text,
                                      delivery_city   text,
                                      delivery_state  text,
                                      delivery_zip    text,
                                      cc_number       text,
                                      cc_expiration   text,
                                      cc_cvv          text,
                                      tacos list<frozen<taco>>
);
