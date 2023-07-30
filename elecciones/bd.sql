--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.24
-- Dumped by pg_dump version 13.1

-- Started on 2023-06-07 18:23:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2243 (class 1262 OID 263495)
-- Name: eleccionesgenerales; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE eleccionesgenerales WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Argentina.1252';


ALTER DATABASE eleccionesgenerales OWNER TO postgres;

\connect eleccionesgenerales

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 6 (class 2615 OID 263555)
-- Name: accesos; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA accesos;


ALTER SCHEMA accesos OWNER TO postgres;

--
-- TOC entry 5 (class 2615 OID 263498)
-- Name: elecciones; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA elecciones;


ALTER SCHEMA elecciones OWNER TO postgres;

SET default_tablespace = '';

--
-- TOC entry 198 (class 1259 OID 263556)
-- Name: roles; Type: TABLE; Schema: accesos; Owner: postgres
--

CREATE TABLE accesos.roles (
    idrol bigint NOT NULL,
    rol character varying(255)
);


ALTER TABLE accesos.roles OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 263561)
-- Name: user_rol; Type: TABLE; Schema: accesos; Owner: postgres
--

CREATE TABLE accesos.user_rol (
    idusuario bigint NOT NULL,
    idrol bigint NOT NULL
);


ALTER TABLE accesos.user_rol OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 263566)
-- Name: usuario; Type: TABLE; Schema: accesos; Owner: postgres
--

CREATE TABLE accesos.usuario (
    idusuario bigint NOT NULL,
    apellido character varying(255),
    habilitado boolean NOT NULL,
    nombre character varying(255),
    clave character varying(255),
    usuario character varying(255)
);


ALTER TABLE accesos.usuario OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 263501)
-- Name: agrupacion_politica; Type: TABLE; Schema: elecciones; Owner: postgres
--

CREATE TABLE elecciones.agrupacion_politica (
    id_agrupacion_politica integer NOT NULL,
    descripcion character varying(255)
);


ALTER TABLE elecciones.agrupacion_politica OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 263499)
-- Name: agrupacion_politica_id_agrupacion_politica_seq; Type: SEQUENCE; Schema: elecciones; Owner: postgres
--

CREATE SEQUENCE elecciones.agrupacion_politica_id_agrupacion_politica_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE elecciones.agrupacion_politica_id_agrupacion_politica_seq OWNER TO postgres;

--
-- TOC entry 2244 (class 0 OID 0)
-- Dependencies: 188
-- Name: agrupacion_politica_id_agrupacion_politica_seq; Type: SEQUENCE OWNED BY; Schema: elecciones; Owner: postgres
--

ALTER SEQUENCE elecciones.agrupacion_politica_id_agrupacion_politica_seq OWNED BY elecciones.agrupacion_politica.id_agrupacion_politica;


--
-- TOC entry 191 (class 1259 OID 263509)
-- Name: circuitos; Type: TABLE; Schema: elecciones; Owner: postgres
--

CREATE TABLE elecciones.circuitos (
    id_circuito integer NOT NULL,
    nombre character varying(255),
    porcentaje_pondera_nacion double precision,
    porcentaje_pondera_provincia_muni double precision
);


ALTER TABLE elecciones.circuitos OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 263507)
-- Name: circuitos_id_circuito_seq; Type: SEQUENCE; Schema: elecciones; Owner: postgres
--

CREATE SEQUENCE elecciones.circuitos_id_circuito_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE elecciones.circuitos_id_circuito_seq OWNER TO postgres;

--
-- TOC entry 2245 (class 0 OID 0)
-- Dependencies: 190
-- Name: circuitos_id_circuito_seq; Type: SEQUENCE OWNED BY; Schema: elecciones; Owner: postgres
--

ALTER SEQUENCE elecciones.circuitos_id_circuito_seq OWNED BY elecciones.circuitos.id_circuito;


--
-- TOC entry 193 (class 1259 OID 263517)
-- Name: escrutinio; Type: TABLE; Schema: elecciones; Owner: postgres
--

CREATE TABLE elecciones.escrutinio (
    id_escrutinio integer NOT NULL,
    id_listainterna integer,
    total_votos_concejales integer,
    total_votos_diputados_nacionales integer,
    total_votos_legisladores_provinciales integer,
    mesa_id integer
);


ALTER TABLE elecciones.escrutinio OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 263515)
-- Name: escrutinio_id_escrutinio_seq; Type: SEQUENCE; Schema: elecciones; Owner: postgres
--

CREATE SEQUENCE elecciones.escrutinio_id_escrutinio_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE elecciones.escrutinio_id_escrutinio_seq OWNER TO postgres;

--
-- TOC entry 2246 (class 0 OID 0)
-- Dependencies: 192
-- Name: escrutinio_id_escrutinio_seq; Type: SEQUENCE OWNED BY; Schema: elecciones; Owner: postgres
--

ALTER SEQUENCE elecciones.escrutinio_id_escrutinio_seq OWNED BY elecciones.escrutinio.id_escrutinio;


--
-- TOC entry 195 (class 1259 OID 263525)
-- Name: lista_interna; Type: TABLE; Schema: elecciones; Owner: postgres
--

CREATE TABLE elecciones.lista_interna (
    id_lista_interna integer NOT NULL,
    permite_concejales boolean DEFAULT true,
    permite_diputados_nacionales boolean DEFAULT true,
    permite_legisladores_provinciales boolean DEFAULT true,
    color character varying(255),
    descripcion character varying(255),
    nro_lista character varying(255),
    texto_formateado character varying(255),
    agrupacion_politica_id integer
);


ALTER TABLE elecciones.lista_interna OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 263523)
-- Name: lista_interna_id_lista_interna_seq; Type: SEQUENCE; Schema: elecciones; Owner: postgres
--

CREATE SEQUENCE elecciones.lista_interna_id_lista_interna_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE elecciones.lista_interna_id_lista_interna_seq OWNER TO postgres;

--
-- TOC entry 2247 (class 0 OID 0)
-- Dependencies: 194
-- Name: lista_interna_id_lista_interna_seq; Type: SEQUENCE OWNED BY; Schema: elecciones; Owner: postgres
--

ALTER SEQUENCE elecciones.lista_interna_id_lista_interna_seq OWNED BY elecciones.lista_interna.id_lista_interna;


--
-- TOC entry 197 (class 1259 OID 263539)
-- Name: lugares_votacion; Type: TABLE; Schema: elecciones; Owner: postgres
--

CREATE TABLE elecciones.lugares_votacion (
    id_lugar_votacion integer NOT NULL,
    circuito character varying(50),
    direccion character varying(50),
    mesa_desde integer,
    mesa_hasta integer,
    nombre character varying(50),
    seccion character varying(50),
    id_circuito integer
);


ALTER TABLE elecciones.lugares_votacion OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 263537)
-- Name: lugares_votacion_id_lugar_votacion_seq; Type: SEQUENCE; Schema: elecciones; Owner: postgres
--

CREATE SEQUENCE elecciones.lugares_votacion_id_lugar_votacion_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE elecciones.lugares_votacion_id_lugar_votacion_seq OWNER TO postgres;

--
-- TOC entry 2248 (class 0 OID 0)
-- Dependencies: 196
-- Name: lugares_votacion_id_lugar_votacion_seq; Type: SEQUENCE OWNED BY; Schema: elecciones; Owner: postgres
--

ALTER SEQUENCE elecciones.lugares_votacion_id_lugar_votacion_seq OWNED BY elecciones.lugares_votacion.id_lugar_votacion;


--
-- TOC entry 202 (class 1259 OID 263586)
-- Name: mesa; Type: TABLE; Schema: elecciones; Owner: postgres
--

CREATE TABLE elecciones.mesa (
    id_mesa integer NOT NULL,
    circuito character varying(255),
    fecha_de_carga timestamp without time zone,
    mesa integer,
    total_votos_blanco_concejales integer DEFAULT 0,
    total_votos_blanco_diputados_nacionales integer DEFAULT 0,
    total_votos_blanco_legisladores_provinciales integer DEFAULT 0,
    total_votos_bolsin_concejales integer DEFAULT 0,
    total_votos_bolsin_diputados_nacionales integer DEFAULT 0,
    total_votos_bolsin_legisladores_provinciales integer DEFAULT 0,
    total_votos_concejales integer DEFAULT 0,
    total_votos_diputados_nacionales integer DEFAULT 0,
    total_votos_impugnado_concejales integer DEFAULT 0,
    total_votos_impugnado_diputados_nacionales integer DEFAULT 0,
    total_votos_impugnado_legisladores_provinciales integer DEFAULT 0,
    total_votos_legisladores_provinciales integer DEFAULT 0,
    total_votos_nulos_concejales integer DEFAULT 0,
    total_votos_nulos_diputados_nacionales integer DEFAULT 0,
    total_votos_nulos_legisladores_provinciales integer DEFAULT 0,
    total_votos_recurridos_concejales integer DEFAULT 0,
    total_votos_recurridos_diputados_nacionales integer DEFAULT 0,
    total_votos_recurridos_legisladores_provinciales integer DEFAULT 0,
    total_x_col_concejales integer DEFAULT 0,
    total_votos_x_individuos integer DEFAULT 0,
    total_x_col_diputados_nacionales integer DEFAULT 0,
    total_x_col_legisladores_provinciales integer DEFAULT 0,
    total_sobres integer DEFAULT 0,
    lugar_votacion_id integer
);


ALTER TABLE elecciones.mesa OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 263584)
-- Name: mesa_id_mesa_seq; Type: SEQUENCE; Schema: elecciones; Owner: postgres
--

CREATE SEQUENCE elecciones.mesa_id_mesa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE elecciones.mesa_id_mesa_seq OWNER TO postgres;

--
-- TOC entry 2249 (class 0 OID 0)
-- Dependencies: 201
-- Name: mesa_id_mesa_seq; Type: SEQUENCE OWNED BY; Schema: elecciones; Owner: postgres
--

ALTER SEQUENCE elecciones.mesa_id_mesa_seq OWNED BY elecciones.mesa.id_mesa;


--
-- TOC entry 187 (class 1259 OID 263496)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 2049 (class 2604 OID 263504)
-- Name: agrupacion_politica id_agrupacion_politica; Type: DEFAULT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.agrupacion_politica ALTER COLUMN id_agrupacion_politica SET DEFAULT nextval('elecciones.agrupacion_politica_id_agrupacion_politica_seq'::regclass);


--
-- TOC entry 2050 (class 2604 OID 263512)
-- Name: circuitos id_circuito; Type: DEFAULT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.circuitos ALTER COLUMN id_circuito SET DEFAULT nextval('elecciones.circuitos_id_circuito_seq'::regclass);


--
-- TOC entry 2051 (class 2604 OID 263520)
-- Name: escrutinio id_escrutinio; Type: DEFAULT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.escrutinio ALTER COLUMN id_escrutinio SET DEFAULT nextval('elecciones.escrutinio_id_escrutinio_seq'::regclass);


--
-- TOC entry 2052 (class 2604 OID 263528)
-- Name: lista_interna id_lista_interna; Type: DEFAULT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.lista_interna ALTER COLUMN id_lista_interna SET DEFAULT nextval('elecciones.lista_interna_id_lista_interna_seq'::regclass);


--
-- TOC entry 2056 (class 2604 OID 263542)
-- Name: lugares_votacion id_lugar_votacion; Type: DEFAULT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.lugares_votacion ALTER COLUMN id_lugar_votacion SET DEFAULT nextval('elecciones.lugares_votacion_id_lugar_votacion_seq'::regclass);


--
-- TOC entry 2057 (class 2604 OID 263589)
-- Name: mesa id_mesa; Type: DEFAULT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.mesa ALTER COLUMN id_mesa SET DEFAULT nextval('elecciones.mesa_id_mesa_seq'::regclass);


--
-- TOC entry 2233 (class 0 OID 263556)
-- Dependencies: 198
-- Data for Name: roles; Type: TABLE DATA; Schema: accesos; Owner: postgres
--



--
-- TOC entry 2234 (class 0 OID 263561)
-- Dependencies: 199
-- Data for Name: user_rol; Type: TABLE DATA; Schema: accesos; Owner: postgres
--



--
-- TOC entry 2235 (class 0 OID 263566)
-- Dependencies: 200
-- Data for Name: usuario; Type: TABLE DATA; Schema: accesos; Owner: postgres
--



--
-- TOC entry 2224 (class 0 OID 263501)
-- Dependencies: 189
-- Data for Name: agrupacion_politica; Type: TABLE DATA; Schema: elecciones; Owner: postgres
--



--
-- TOC entry 2226 (class 0 OID 263509)
-- Dependencies: 191
-- Data for Name: circuitos; Type: TABLE DATA; Schema: elecciones; Owner: postgres
--



--
-- TOC entry 2228 (class 0 OID 263517)
-- Dependencies: 193
-- Data for Name: escrutinio; Type: TABLE DATA; Schema: elecciones; Owner: postgres
--



--
-- TOC entry 2230 (class 0 OID 263525)
-- Dependencies: 195
-- Data for Name: lista_interna; Type: TABLE DATA; Schema: elecciones; Owner: postgres
--



--
-- TOC entry 2232 (class 0 OID 263539)
-- Dependencies: 197
-- Data for Name: lugares_votacion; Type: TABLE DATA; Schema: elecciones; Owner: postgres
--



--
-- TOC entry 2237 (class 0 OID 263586)
-- Dependencies: 202
-- Data for Name: mesa; Type: TABLE DATA; Schema: elecciones; Owner: postgres
--



--
-- TOC entry 2250 (class 0 OID 0)
-- Dependencies: 188
-- Name: agrupacion_politica_id_agrupacion_politica_seq; Type: SEQUENCE SET; Schema: elecciones; Owner: postgres
--

SELECT pg_catalog.setval('elecciones.agrupacion_politica_id_agrupacion_politica_seq', 1, false);


--
-- TOC entry 2251 (class 0 OID 0)
-- Dependencies: 190
-- Name: circuitos_id_circuito_seq; Type: SEQUENCE SET; Schema: elecciones; Owner: postgres
--

SELECT pg_catalog.setval('elecciones.circuitos_id_circuito_seq', 1, false);


--
-- TOC entry 2252 (class 0 OID 0)
-- Dependencies: 192
-- Name: escrutinio_id_escrutinio_seq; Type: SEQUENCE SET; Schema: elecciones; Owner: postgres
--

SELECT pg_catalog.setval('elecciones.escrutinio_id_escrutinio_seq', 1, false);


--
-- TOC entry 2253 (class 0 OID 0)
-- Dependencies: 194
-- Name: lista_interna_id_lista_interna_seq; Type: SEQUENCE SET; Schema: elecciones; Owner: postgres
--

SELECT pg_catalog.setval('elecciones.lista_interna_id_lista_interna_seq', 1, false);


--
-- TOC entry 2254 (class 0 OID 0)
-- Dependencies: 196
-- Name: lugares_votacion_id_lugar_votacion_seq; Type: SEQUENCE SET; Schema: elecciones; Owner: postgres
--

SELECT pg_catalog.setval('elecciones.lugares_votacion_id_lugar_votacion_seq', 1, false);


--
-- TOC entry 2255 (class 0 OID 0)
-- Dependencies: 201
-- Name: mesa_id_mesa_seq; Type: SEQUENCE SET; Schema: elecciones; Owner: postgres
--

SELECT pg_catalog.setval('elecciones.mesa_id_mesa_seq', 1, false);


--
-- TOC entry 2256 (class 0 OID 0)
-- Dependencies: 187
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- TOC entry 2092 (class 2606 OID 263560)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: accesos; Owner: postgres
--

ALTER TABLE ONLY accesos.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (idrol);


--
-- TOC entry 2094 (class 2606 OID 263565)
-- Name: user_rol user_rol_pkey; Type: CONSTRAINT; Schema: accesos; Owner: postgres
--

ALTER TABLE ONLY accesos.user_rol
    ADD CONSTRAINT user_rol_pkey PRIMARY KEY (idusuario, idrol);


--
-- TOC entry 2096 (class 2606 OID 263573)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: accesos; Owner: postgres
--

ALTER TABLE ONLY accesos.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (idusuario);


--
-- TOC entry 2082 (class 2606 OID 263506)
-- Name: agrupacion_politica agrupacion_politica_pkey; Type: CONSTRAINT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.agrupacion_politica
    ADD CONSTRAINT agrupacion_politica_pkey PRIMARY KEY (id_agrupacion_politica);


--
-- TOC entry 2084 (class 2606 OID 263514)
-- Name: circuitos circuitos_pkey; Type: CONSTRAINT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.circuitos
    ADD CONSTRAINT circuitos_pkey PRIMARY KEY (id_circuito);


--
-- TOC entry 2086 (class 2606 OID 263522)
-- Name: escrutinio escrutinio_pkey; Type: CONSTRAINT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.escrutinio
    ADD CONSTRAINT escrutinio_pkey PRIMARY KEY (id_escrutinio);


--
-- TOC entry 2088 (class 2606 OID 263536)
-- Name: lista_interna lista_interna_pkey; Type: CONSTRAINT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.lista_interna
    ADD CONSTRAINT lista_interna_pkey PRIMARY KEY (id_lista_interna);


--
-- TOC entry 2090 (class 2606 OID 263544)
-- Name: lugares_votacion lugares_votacion_pkey; Type: CONSTRAINT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.lugares_votacion
    ADD CONSTRAINT lugares_votacion_pkey PRIMARY KEY (id_lugar_votacion);


--
-- TOC entry 2098 (class 2606 OID 263614)
-- Name: mesa mesa_pkey; Type: CONSTRAINT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.mesa
    ADD CONSTRAINT mesa_pkey PRIMARY KEY (id_mesa);


--
-- TOC entry 2103 (class 2606 OID 263579)
-- Name: user_rol fke49edys5r6kxgly4m8m7wuwtf; Type: FK CONSTRAINT; Schema: accesos; Owner: postgres
--

ALTER TABLE ONLY accesos.user_rol
    ADD CONSTRAINT fke49edys5r6kxgly4m8m7wuwtf FOREIGN KEY (idusuario) REFERENCES accesos.usuario(idusuario);


--
-- TOC entry 2102 (class 2606 OID 263574)
-- Name: user_rol fkgejp0s9quouh6www2qbpofprk; Type: FK CONSTRAINT; Schema: accesos; Owner: postgres
--

ALTER TABLE ONLY accesos.user_rol
    ADD CONSTRAINT fkgejp0s9quouh6www2qbpofprk FOREIGN KEY (idrol) REFERENCES accesos.roles(idrol);


--
-- TOC entry 2099 (class 2606 OID 263616)
-- Name: escrutinio fk4fywk54a9eelxbqhh9f45k1mi; Type: FK CONSTRAINT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.escrutinio
    ADD CONSTRAINT fk4fywk54a9eelxbqhh9f45k1mi FOREIGN KEY (mesa_id) REFERENCES elecciones.mesa(id_mesa);


--
-- TOC entry 2101 (class 2606 OID 263550)
-- Name: lugares_votacion fk6dfqbvp04msd2yihm14bkj63b; Type: FK CONSTRAINT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.lugares_votacion
    ADD CONSTRAINT fk6dfqbvp04msd2yihm14bkj63b FOREIGN KEY (id_circuito) REFERENCES elecciones.circuitos(id_circuito);


--
-- TOC entry 2104 (class 2606 OID 263621)
-- Name: mesa fko8i50qy8m75dco20qgbx7hgp1; Type: FK CONSTRAINT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.mesa
    ADD CONSTRAINT fko8i50qy8m75dco20qgbx7hgp1 FOREIGN KEY (lugar_votacion_id) REFERENCES elecciones.lugares_votacion(id_lugar_votacion);


--
-- TOC entry 2100 (class 2606 OID 263545)
-- Name: lista_interna fkss4bptdel9jw3yhue1almplg; Type: FK CONSTRAINT; Schema: elecciones; Owner: postgres
--

ALTER TABLE ONLY elecciones.lista_interna
    ADD CONSTRAINT fkss4bptdel9jw3yhue1almplg FOREIGN KEY (agrupacion_politica_id) REFERENCES elecciones.agrupacion_politica(id_agrupacion_politica);


-- Completed on 2023-06-07 18:23:09

--
-- PostgreSQL database dump complete
--

