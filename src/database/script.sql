--CREATE DATABASE mutants;
-- Table: public.stats

-- DROP TABLE public.stats;

CREATE TABLE stats
(
    dna varchar(4000) NOT NULL,
    is_mutant boolean,
    CONSTRAINT stats_pkey PRIMARY KEY (dna)
);


COMMENT ON TABLE public.stats
    IS 'Tabla que alberga la informacion de DNA consultados asi como su correspondiente verificacion';

COMMENT ON COLUMN public.stats.dna
    IS 'DNA de la persona, debe ser unica';

COMMENT ON COLUMN public.stats.is_mutant
    IS 'Validacion si el ADN es mutante=true o humano=false';



	CREATE TABLE public.stats_error
	(
		id Integer NOT NULL,
		dna varchar(4000) NOT NULL,
		is_mutant boolean,
		error_message varchar(1000) NOT NULL,
		date_error timestamp with time zone,
		CONSTRAINT stats_error_pkey PRIMARY KEY (id)
	);


CREATE SEQUENCE seq_stats_error
START 1
INCREMENT 1
MINVALUE 1
OWNED BY stats_error.id;
