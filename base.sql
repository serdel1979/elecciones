PGDMP     /                    {            eleccionesgenerales    15.1    15.1 D    x           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            y           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            z           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            {           1262    90500    eleccionesgenerales    DATABASE     �   CREATE DATABASE eleccionesgenerales WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Argentina.1252';
 #   DROP DATABASE eleccionesgenerales;
                postgres    false                        2615    90501    accesos    SCHEMA        CREATE SCHEMA accesos;
    DROP SCHEMA accesos;
                postgres    false                        2615    90502 
   elecciones    SCHEMA        CREATE SCHEMA elecciones;
    DROP SCHEMA elecciones;
                postgres    false            �            1255    90631    ponderadoresnacional()    FUNCTION     "  CREATE FUNCTION elecciones.ponderadoresnacional(OUT id_circuito integer, OUT ponderador double precision) RETURNS SETOF record
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
	select a1.id_circuito, case when a2.total_mesas is null then 0 else a1.porcentaje_pondera_nacion/((cast(a2.total_mesas as double precision)/a1.total_mesas) * 100) end ponderador
    from
    (select c.id_circuito,c.porcentaje_pondera_nacion, count(*) total_mesas from elecciones.mesa as m inner join elecciones.lugares_votacion as l on m.lugar_votacion_id = l.id_lugar_votacion
    inner join elecciones.circuitos as c on l.id_circuito = c.id_circuito group by c.id_circuito,c.porcentaje_pondera_nacion) a1
    left join 
    (select c.id_circuito, count(*) total_mesas 
    from elecciones.circuitos c 
    inner join elecciones.lugares_votacion as l on c.id_circuito = l.id_circuito 
    inner join elecciones.mesa as m on l.id_lugar_votacion = m.lugar_votacion_id and m.fecha_de_carga is not null
    group by c.id_circuito) a2 on a1.id_circuito = a2.id_circuito;
END;
$$;
 i   DROP FUNCTION elecciones.ponderadoresnacional(OUT id_circuito integer, OUT ponderador double precision);
    
   elecciones          postgres    false    7            �            1255    90632    ponderadoresprovinciaymuni()    FUNCTION     @  CREATE FUNCTION elecciones.ponderadoresprovinciaymuni(OUT id_circuito integer, OUT ponderador double precision) RETURNS SETOF record
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
	select a1.id_circuito, case when a2.total_mesas is null then 0 else a1.porcentaje_pondera_provincia_muni/((cast(a2.total_mesas as double precision)/a1.total_mesas) * 100) end ponderador
    from
    (select c.id_circuito,c.porcentaje_pondera_provincia_muni, count(*) total_mesas from elecciones.mesa as m inner join elecciones.lugares_votacion as l on m.lugar_votacion_id = l.id_lugar_votacion
    inner join elecciones.circuitos as c on l.id_circuito = c.id_circuito group by c.id_circuito,c.porcentaje_pondera_provincia_muni) a1
    left join 
    (select c.id_circuito, count(*) total_mesas 
    from elecciones.circuitos c 
    inner join elecciones.lugares_votacion as l on c.id_circuito = l.id_circuito 
    inner join elecciones.mesa as m on l.id_lugar_votacion = m.lugar_votacion_id and m.fecha_de_carga is not null
    group by c.id_circuito) a2 on a1.id_circuito = a2.id_circuito;
END;
$$;
 o   DROP FUNCTION elecciones.ponderadoresprovinciaymuni(OUT id_circuito integer, OUT ponderador double precision);
    
   elecciones          postgres    false    7            �            1259    90503    roles    TABLE     Z   CREATE TABLE accesos.roles (
    idrol bigint NOT NULL,
    rol character varying(255)
);
    DROP TABLE accesos.roles;
       accesos         heap    postgres    false    6            �            1259    90506    user_rol    TABLE     \   CREATE TABLE accesos.user_rol (
    idusuario bigint NOT NULL,
    idrol bigint NOT NULL
);
    DROP TABLE accesos.user_rol;
       accesos         heap    postgres    false    6            �            1259    90509    usuario    TABLE     �   CREATE TABLE accesos.usuario (
    idusuario bigint NOT NULL,
    apellido character varying(255),
    habilitado boolean NOT NULL,
    nombre character varying(255),
    clave character varying(255),
    usuario character varying(255)
);
    DROP TABLE accesos.usuario;
       accesos         heap    postgres    false    6            �            1259    90514    agrupacion_politica    TABLE     �   CREATE TABLE elecciones.agrupacion_politica (
    id_agrupacion_politica integer NOT NULL,
    descripcion character varying(255)
);
 +   DROP TABLE elecciones.agrupacion_politica;
    
   elecciones         heap    postgres    false    7            �            1259    90517 .   agrupacion_politica_id_agrupacion_politica_seq    SEQUENCE     �   CREATE SEQUENCE elecciones.agrupacion_politica_id_agrupacion_politica_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 I   DROP SEQUENCE elecciones.agrupacion_politica_id_agrupacion_politica_seq;
    
   elecciones          postgres    false    7    219            |           0    0 .   agrupacion_politica_id_agrupacion_politica_seq    SEQUENCE OWNED BY     �   ALTER SEQUENCE elecciones.agrupacion_politica_id_agrupacion_politica_seq OWNED BY elecciones.agrupacion_politica.id_agrupacion_politica;
       
   elecciones          postgres    false    220            �            1259    90518 	   circuitos    TABLE     �   CREATE TABLE elecciones.circuitos (
    id_circuito integer NOT NULL,
    nombre character varying(255),
    porcentaje_pondera_nacion double precision,
    porcentaje_pondera_provincia_muni double precision,
    porcentaje double precision
);
 !   DROP TABLE elecciones.circuitos;
    
   elecciones         heap    postgres    false    7            �            1259    90521    circuitos_id_circuito_seq    SEQUENCE     �   CREATE SEQUENCE elecciones.circuitos_id_circuito_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 4   DROP SEQUENCE elecciones.circuitos_id_circuito_seq;
    
   elecciones          postgres    false    221    7            }           0    0    circuitos_id_circuito_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE elecciones.circuitos_id_circuito_seq OWNED BY elecciones.circuitos.id_circuito;
       
   elecciones          postgres    false    222            �            1259    90522 
   escrutinio    TABLE     �  CREATE TABLE elecciones.escrutinio (
    id_escrutinio integer NOT NULL,
    id_listainterna integer,
    total_votos_concejales integer,
    total_votos_diputados_nacionales integer,
    total_votos_legisladores_provinciales integer,
    mesa_id integer,
    total_votos_senadores integer,
    total_votos_intendente integer,
    total_votos_senadores_nacionales integer,
    total_votos_gobernador integer,
    total_votos_parlamentarios_mercosur integer,
    total_votos_presidente integer
);
 "   DROP TABLE elecciones.escrutinio;
    
   elecciones         heap    postgres    false    7            �            1259    90525    escrutinio_id_escrutinio_seq    SEQUENCE     �   CREATE SEQUENCE elecciones.escrutinio_id_escrutinio_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 7   DROP SEQUENCE elecciones.escrutinio_id_escrutinio_seq;
    
   elecciones          postgres    false    223    7            ~           0    0    escrutinio_id_escrutinio_seq    SEQUENCE OWNED BY     e   ALTER SEQUENCE elecciones.escrutinio_id_escrutinio_seq OWNED BY elecciones.escrutinio.id_escrutinio;
       
   elecciones          postgres    false    224            �            1259    90526    lista_interna    TABLE     v  CREATE TABLE elecciones.lista_interna (
    id_lista_interna integer NOT NULL,
    permite_concejales boolean DEFAULT true,
    permite_diputados_nacionales boolean DEFAULT true,
    permite_legisladores_provinciales boolean DEFAULT true,
    color character varying(255),
    descripcion character varying(255),
    nro_lista character varying(255),
    texto_formateado character varying(255),
    agrupacion_politica_id integer,
    permite_senadores_nacionales boolean DEFAULT true,
    permite_intendente boolean,
    permite_parlamentarios_mercosur boolean,
    permite_gobernador boolean,
    permite_presidente boolean
);
 %   DROP TABLE elecciones.lista_interna;
    
   elecciones         heap    postgres    false    7            �            1259    90534 "   lista_interna_id_lista_interna_seq    SEQUENCE     �   CREATE SEQUENCE elecciones.lista_interna_id_lista_interna_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 =   DROP SEQUENCE elecciones.lista_interna_id_lista_interna_seq;
    
   elecciones          postgres    false    225    7                       0    0 "   lista_interna_id_lista_interna_seq    SEQUENCE OWNED BY     q   ALTER SEQUENCE elecciones.lista_interna_id_lista_interna_seq OWNED BY elecciones.lista_interna.id_lista_interna;
       
   elecciones          postgres    false    226            �            1259    90535    lugares_votacion    TABLE     ,  CREATE TABLE elecciones.lugares_votacion (
    id_lugar_votacion integer NOT NULL,
    circuito character varying(50),
    direccion character varying(50),
    mesa_desde integer,
    mesa_hasta integer,
    nombre character varying(50),
    seccion character varying(50),
    id_circuito integer
);
 (   DROP TABLE elecciones.lugares_votacion;
    
   elecciones         heap    postgres    false    7            �            1259    90538 &   lugares_votacion_id_lugar_votacion_seq    SEQUENCE     �   CREATE SEQUENCE elecciones.lugares_votacion_id_lugar_votacion_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 A   DROP SEQUENCE elecciones.lugares_votacion_id_lugar_votacion_seq;
    
   elecciones          postgres    false    227    7            �           0    0 &   lugares_votacion_id_lugar_votacion_seq    SEQUENCE OWNED BY     y   ALTER SEQUENCE elecciones.lugares_votacion_id_lugar_votacion_seq OWNED BY elecciones.lugares_votacion.id_lugar_votacion;
       
   elecciones          postgres    false    228            �            1259    90539    mesa    TABLE       CREATE TABLE elecciones.mesa (
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
    lugar_votacion_id integer,
    total_votos_blanco_senadores integer DEFAULT 0,
    total_votos_bolsin_senadores integer DEFAULT 0,
    total_votos_impugnado_senadores integer DEFAULT 0,
    total_votos_nulos_senadores integer DEFAULT 0,
    total_votos_recurridos_senadores integer DEFAULT 0,
    total_votos_senadores integer DEFAULT 0,
    total_x_col_senadores integer DEFAULT 0,
    total_votos_blanco_gobernador integer,
    total_votos_blanco_parlamentarios_mercosur integer,
    total_votos_bolsin_gobernador integer,
    total_votos_bolsin_parlamentarios_mercosur integer,
    total_votos_bolsin_intendente integer,
    total_votos_intendente integer,
    total_votos_gobernador integer,
    total_votos_parlamentarios_mercosur integer,
    total_impugnado_senadores integer,
    total_votos_impugnado_gobernador integer,
    total_votos_impugnado_parlamentarios_mercosur integer,
    total_votos_impugnado_intendente integer,
    total_x_col_gobernador integer,
    total_x_col_parlamentarios_mercosur integer,
    total_x_col_intendente integer,
    total_x_col_presidente integer,
    total_votos_blanco_presidente integer,
    total_bolsin_presidente integer,
    total_impugnado_presidente integer,
    total_votos_presidente integer,
    total_votos_blanco_intendente integer,
    total_votos_recurridos_intendente integer,
    total_votos_recurridos_parlamentarios_mercosur integer,
    total_votos_recurridos_presidente integer,
    total_votos_recurridos_gobernador integer,
    total_votos_bolsin_presidente integer DEFAULT 0,
    total_votos_impugnado_presidente integer DEFAULT 0,
    total_votos_nulos_gobernador integer DEFAULT 0,
    total_votos_nulos_intendente integer DEFAULT 0,
    total_votos_nulos_parlamentarios_mercosur integer DEFAULT 0,
    total_votos_nulos_presidente integer DEFAULT 0,
    total_votos_recurridos_parlamentario_mercosur integer DEFAULT 0,
    total_votos_individuos integer DEFAULT 0
);
    DROP TABLE elecciones.mesa;
    
   elecciones         heap    postgres    false    7            �            1259    90565    mesa_id_mesa_seq    SEQUENCE     }   CREATE SEQUENCE elecciones.mesa_id_mesa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE elecciones.mesa_id_mesa_seq;
    
   elecciones          postgres    false    229    7            �           0    0    mesa_id_mesa_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE elecciones.mesa_id_mesa_seq OWNED BY elecciones.mesa.id_mesa;
       
   elecciones          postgres    false    230            �            1259    90566    hibernate_sequence    SEQUENCE     {   CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hibernate_sequence;
       public          postgres    false            �           2604    90567 *   agrupacion_politica id_agrupacion_politica    DEFAULT     �   ALTER TABLE ONLY elecciones.agrupacion_politica ALTER COLUMN id_agrupacion_politica SET DEFAULT nextval('elecciones.agrupacion_politica_id_agrupacion_politica_seq'::regclass);
 ]   ALTER TABLE elecciones.agrupacion_politica ALTER COLUMN id_agrupacion_politica DROP DEFAULT;
    
   elecciones          postgres    false    220    219            �           2604    90568    circuitos id_circuito    DEFAULT     �   ALTER TABLE ONLY elecciones.circuitos ALTER COLUMN id_circuito SET DEFAULT nextval('elecciones.circuitos_id_circuito_seq'::regclass);
 H   ALTER TABLE elecciones.circuitos ALTER COLUMN id_circuito DROP DEFAULT;
    
   elecciones          postgres    false    222    221            �           2604    90623    escrutinio id_escrutinio    DEFAULT     �   ALTER TABLE ONLY elecciones.escrutinio ALTER COLUMN id_escrutinio SET DEFAULT nextval('elecciones.escrutinio_id_escrutinio_seq'::regclass);
 K   ALTER TABLE elecciones.escrutinio ALTER COLUMN id_escrutinio DROP DEFAULT;
    
   elecciones          postgres    false    224    223            �           2604    90570    lista_interna id_lista_interna    DEFAULT     �   ALTER TABLE ONLY elecciones.lista_interna ALTER COLUMN id_lista_interna SET DEFAULT nextval('elecciones.lista_interna_id_lista_interna_seq'::regclass);
 Q   ALTER TABLE elecciones.lista_interna ALTER COLUMN id_lista_interna DROP DEFAULT;
    
   elecciones          postgres    false    226    225            �           2604    90624 "   lugares_votacion id_lugar_votacion    DEFAULT     �   ALTER TABLE ONLY elecciones.lugares_votacion ALTER COLUMN id_lugar_votacion SET DEFAULT nextval('elecciones.lugares_votacion_id_lugar_votacion_seq'::regclass);
 U   ALTER TABLE elecciones.lugares_votacion ALTER COLUMN id_lugar_votacion DROP DEFAULT;
    
   elecciones          postgres    false    228    227            �           2604    90572    mesa id_mesa    DEFAULT     t   ALTER TABLE ONLY elecciones.mesa ALTER COLUMN id_mesa SET DEFAULT nextval('elecciones.mesa_id_mesa_seq'::regclass);
 ?   ALTER TABLE elecciones.mesa ALTER COLUMN id_mesa DROP DEFAULT;
    
   elecciones          postgres    false    230    229            f          0    90503    roles 
   TABLE DATA           ,   COPY accesos.roles (idrol, rol) FROM stdin;
    accesos          postgres    false    216   Yv       g          0    90506    user_rol 
   TABLE DATA           5   COPY accesos.user_rol (idusuario, idrol) FROM stdin;
    accesos          postgres    false    217   �v       h          0    90509    usuario 
   TABLE DATA           [   COPY accesos.usuario (idusuario, apellido, habilitado, nombre, clave, usuario) FROM stdin;
    accesos          postgres    false    218   �v       i          0    90514    agrupacion_politica 
   TABLE DATA           V   COPY elecciones.agrupacion_politica (id_agrupacion_politica, descripcion) FROM stdin;
 
   elecciones          postgres    false    219   dw       k          0    90518 	   circuitos 
   TABLE DATA           �   COPY elecciones.circuitos (id_circuito, nombre, porcentaje_pondera_nacion, porcentaje_pondera_provincia_muni, porcentaje) FROM stdin;
 
   elecciones          postgres    false    221   y       m          0    90522 
   escrutinio 
   TABLE DATA           X  COPY elecciones.escrutinio (id_escrutinio, id_listainterna, total_votos_concejales, total_votos_diputados_nacionales, total_votos_legisladores_provinciales, mesa_id, total_votos_senadores, total_votos_intendente, total_votos_senadores_nacionales, total_votos_gobernador, total_votos_parlamentarios_mercosur, total_votos_presidente) FROM stdin;
 
   elecciones          postgres    false    223   �y       o          0    90526    lista_interna 
   TABLE DATA           V  COPY elecciones.lista_interna (id_lista_interna, permite_concejales, permite_diputados_nacionales, permite_legisladores_provinciales, color, descripcion, nro_lista, texto_formateado, agrupacion_politica_id, permite_senadores_nacionales, permite_intendente, permite_parlamentarios_mercosur, permite_gobernador, permite_presidente) FROM stdin;
 
   elecciones          postgres    false    225   �{       q          0    90535    lugares_votacion 
   TABLE DATA           �   COPY elecciones.lugares_votacion (id_lugar_votacion, circuito, direccion, mesa_desde, mesa_hasta, nombre, seccion, id_circuito) FROM stdin;
 
   elecciones          postgres    false    227   �~       s          0    90539    mesa 
   TABLE DATA           �  COPY elecciones.mesa (id_mesa, circuito, fecha_de_carga, mesa, total_votos_blanco_concejales, total_votos_blanco_diputados_nacionales, total_votos_blanco_legisladores_provinciales, total_votos_bolsin_concejales, total_votos_bolsin_diputados_nacionales, total_votos_bolsin_legisladores_provinciales, total_votos_concejales, total_votos_diputados_nacionales, total_votos_impugnado_concejales, total_votos_impugnado_diputados_nacionales, total_votos_impugnado_legisladores_provinciales, total_votos_legisladores_provinciales, total_votos_nulos_concejales, total_votos_nulos_diputados_nacionales, total_votos_nulos_legisladores_provinciales, total_votos_recurridos_concejales, total_votos_recurridos_diputados_nacionales, total_votos_recurridos_legisladores_provinciales, total_x_col_concejales, total_votos_x_individuos, total_x_col_diputados_nacionales, total_x_col_legisladores_provinciales, total_sobres, lugar_votacion_id, total_votos_blanco_senadores, total_votos_bolsin_senadores, total_votos_impugnado_senadores, total_votos_nulos_senadores, total_votos_recurridos_senadores, total_votos_senadores, total_x_col_senadores, total_votos_blanco_gobernador, total_votos_blanco_parlamentarios_mercosur, total_votos_bolsin_gobernador, total_votos_bolsin_parlamentarios_mercosur, total_votos_bolsin_intendente, total_votos_intendente, total_votos_gobernador, total_votos_parlamentarios_mercosur, total_impugnado_senadores, total_votos_impugnado_gobernador, total_votos_impugnado_parlamentarios_mercosur, total_votos_impugnado_intendente, total_x_col_gobernador, total_x_col_parlamentarios_mercosur, total_x_col_intendente, total_x_col_presidente, total_votos_blanco_presidente, total_bolsin_presidente, total_impugnado_presidente, total_votos_presidente, total_votos_blanco_intendente, total_votos_recurridos_intendente, total_votos_recurridos_parlamentarios_mercosur, total_votos_recurridos_presidente, total_votos_recurridos_gobernador, total_votos_bolsin_presidente, total_votos_impugnado_presidente, total_votos_nulos_gobernador, total_votos_nulos_intendente, total_votos_nulos_parlamentarios_mercosur, total_votos_nulos_presidente, total_votos_recurridos_parlamentario_mercosur, total_votos_individuos) FROM stdin;
 
   elecciones          postgres    false    229   �       �           0    0 .   agrupacion_politica_id_agrupacion_politica_seq    SEQUENCE SET     a   SELECT pg_catalog.setval('elecciones.agrupacion_politica_id_agrupacion_politica_seq', 31, true);
       
   elecciones          postgres    false    220            �           0    0    circuitos_id_circuito_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('elecciones.circuitos_id_circuito_seq', 8, true);
       
   elecciones          postgres    false    222            �           0    0    escrutinio_id_escrutinio_seq    SEQUENCE SET     P   SELECT pg_catalog.setval('elecciones.escrutinio_id_escrutinio_seq', 684, true);
       
   elecciones          postgres    false    224            �           0    0 "   lista_interna_id_lista_interna_seq    SEQUENCE SET     U   SELECT pg_catalog.setval('elecciones.lista_interna_id_lista_interna_seq', 37, true);
       
   elecciones          postgres    false    226            �           0    0 &   lugares_votacion_id_lugar_votacion_seq    SEQUENCE SET     Z   SELECT pg_catalog.setval('elecciones.lugares_votacion_id_lugar_votacion_seq', 280, true);
       
   elecciones          postgres    false    228            �           0    0    mesa_id_mesa_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('elecciones.mesa_id_mesa_seq', 1, false);
       
   elecciones          postgres    false    230            �           0    0    hibernate_sequence    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);
          public          postgres    false    231            �           2606    90574    roles roles_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY accesos.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (idrol);
 ;   ALTER TABLE ONLY accesos.roles DROP CONSTRAINT roles_pkey;
       accesos            postgres    false    216            �           2606    90576    user_rol user_rol_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY accesos.user_rol
    ADD CONSTRAINT user_rol_pkey PRIMARY KEY (idusuario, idrol);
 A   ALTER TABLE ONLY accesos.user_rol DROP CONSTRAINT user_rol_pkey;
       accesos            postgres    false    217    217            �           2606    90578    usuario usuario_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY accesos.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (idusuario);
 ?   ALTER TABLE ONLY accesos.usuario DROP CONSTRAINT usuario_pkey;
       accesos            postgres    false    218            �           2606    90580 ,   agrupacion_politica agrupacion_politica_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY elecciones.agrupacion_politica
    ADD CONSTRAINT agrupacion_politica_pkey PRIMARY KEY (id_agrupacion_politica);
 Z   ALTER TABLE ONLY elecciones.agrupacion_politica DROP CONSTRAINT agrupacion_politica_pkey;
    
   elecciones            postgres    false    219            �           2606    90582    circuitos circuitos_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY elecciones.circuitos
    ADD CONSTRAINT circuitos_pkey PRIMARY KEY (id_circuito);
 F   ALTER TABLE ONLY elecciones.circuitos DROP CONSTRAINT circuitos_pkey;
    
   elecciones            postgres    false    221            �           2606    90584    escrutinio escrutinio_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY elecciones.escrutinio
    ADD CONSTRAINT escrutinio_pkey PRIMARY KEY (id_escrutinio);
 H   ALTER TABLE ONLY elecciones.escrutinio DROP CONSTRAINT escrutinio_pkey;
    
   elecciones            postgres    false    223            �           2606    90586     lista_interna lista_interna_pkey 
   CONSTRAINT     p   ALTER TABLE ONLY elecciones.lista_interna
    ADD CONSTRAINT lista_interna_pkey PRIMARY KEY (id_lista_interna);
 N   ALTER TABLE ONLY elecciones.lista_interna DROP CONSTRAINT lista_interna_pkey;
    
   elecciones            postgres    false    225            �           2606    90588 &   lugares_votacion lugares_votacion_pkey 
   CONSTRAINT     w   ALTER TABLE ONLY elecciones.lugares_votacion
    ADD CONSTRAINT lugares_votacion_pkey PRIMARY KEY (id_lugar_votacion);
 T   ALTER TABLE ONLY elecciones.lugares_votacion DROP CONSTRAINT lugares_votacion_pkey;
    
   elecciones            postgres    false    227            �           2606    90590    mesa mesa_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY elecciones.mesa
    ADD CONSTRAINT mesa_pkey PRIMARY KEY (id_mesa);
 <   ALTER TABLE ONLY elecciones.mesa DROP CONSTRAINT mesa_pkey;
    
   elecciones            postgres    false    229            �           2606    90591 $   user_rol fke49edys5r6kxgly4m8m7wuwtf    FK CONSTRAINT     �   ALTER TABLE ONLY accesos.user_rol
    ADD CONSTRAINT fke49edys5r6kxgly4m8m7wuwtf FOREIGN KEY (idusuario) REFERENCES accesos.usuario(idusuario);
 O   ALTER TABLE ONLY accesos.user_rol DROP CONSTRAINT fke49edys5r6kxgly4m8m7wuwtf;
       accesos          postgres    false    3268    218    217            �           2606    90596 $   user_rol fkgejp0s9quouh6www2qbpofprk    FK CONSTRAINT     �   ALTER TABLE ONLY accesos.user_rol
    ADD CONSTRAINT fkgejp0s9quouh6www2qbpofprk FOREIGN KEY (idrol) REFERENCES accesos.roles(idrol);
 O   ALTER TABLE ONLY accesos.user_rol DROP CONSTRAINT fkgejp0s9quouh6www2qbpofprk;
       accesos          postgres    false    3264    216    217            �           2606    90626    mesa fk3315e67c5f0e13    FK CONSTRAINT     �   ALTER TABLE ONLY elecciones.mesa
    ADD CONSTRAINT fk3315e67c5f0e13 FOREIGN KEY (lugar_votacion_id) REFERENCES elecciones.lugares_votacion(id_lugar_votacion);
 C   ALTER TABLE ONLY elecciones.mesa DROP CONSTRAINT fk3315e67c5f0e13;
    
   elecciones          postgres    false    227    3278    229            �           2606    90601 &   escrutinio fk4fywk54a9eelxbqhh9f45k1mi    FK CONSTRAINT     �   ALTER TABLE ONLY elecciones.escrutinio
    ADD CONSTRAINT fk4fywk54a9eelxbqhh9f45k1mi FOREIGN KEY (mesa_id) REFERENCES elecciones.mesa(id_mesa);
 T   ALTER TABLE ONLY elecciones.escrutinio DROP CONSTRAINT fk4fywk54a9eelxbqhh9f45k1mi;
    
   elecciones          postgres    false    229    223    3280            �           2606    90606 ,   lugares_votacion fk6dfqbvp04msd2yihm14bkj63b    FK CONSTRAINT     �   ALTER TABLE ONLY elecciones.lugares_votacion
    ADD CONSTRAINT fk6dfqbvp04msd2yihm14bkj63b FOREIGN KEY (id_circuito) REFERENCES elecciones.circuitos(id_circuito);
 Z   ALTER TABLE ONLY elecciones.lugares_votacion DROP CONSTRAINT fk6dfqbvp04msd2yihm14bkj63b;
    
   elecciones          postgres    false    3272    227    221            �           2606    90611     mesa fko8i50qy8m75dco20qgbx7hgp1    FK CONSTRAINT     �   ALTER TABLE ONLY elecciones.mesa
    ADD CONSTRAINT fko8i50qy8m75dco20qgbx7hgp1 FOREIGN KEY (lugar_votacion_id) REFERENCES elecciones.lugares_votacion(id_lugar_votacion);
 N   ALTER TABLE ONLY elecciones.mesa DROP CONSTRAINT fko8i50qy8m75dco20qgbx7hgp1;
    
   elecciones          postgres    false    229    227    3278            �           2606    90616 (   lista_interna fkss4bptdel9jw3yhue1almplg    FK CONSTRAINT     �   ALTER TABLE ONLY elecciones.lista_interna
    ADD CONSTRAINT fkss4bptdel9jw3yhue1almplg FOREIGN KEY (agrupacion_politica_id) REFERENCES elecciones.agrupacion_politica(id_agrupacion_politica);
 V   ALTER TABLE ONLY elecciones.lista_interna DROP CONSTRAINT fkss4bptdel9jw3yhue1almplg;
    
   elecciones          postgres    false    3270    219    225            f   !   x�3���q�v�2��]|=��b���� t��      g      x�3�4�2�4�2�1z\\\ 
      h   �   x�5̹�0  й�f"(c-�
R1(q!r�$|�1��MO>�����$<{�~�NY�Ww7n�P�jI���q�x��2u�'���ݸ�@������צ}�4���UN�V�,�Ù�Q�0b��lO#���,��\f���]o^��(0h�|��/�67y      i   �  x�U��r� ���)���S�i.e�d�`Dx&�}��l'v�ď��s�J��;2gt�t�U�yGl��u�F���12L��C�y���>����MO���>���m���
9ʲg�W��Ϙ��:�t@������=fL�~��3[+X�07��.�i��h��S|�j�mG5zn;8��1`�	X�:�v��'�Y��h�����YX�=�k�R� 4�_�!rQ�o�9��x�0�T�|R2ɥ��Lh�D��*�\7����*�C#�7Ĕ�;�=ɵ�u��ж�D^�����q�=r�ۃ:l�iD����uN��un��|:���N��s-��K���j��v�*���I��'�|	�x%�������K
"��|f��30�����i`X�I����Z���;      k   �   x�E�K�!�5}�\`P�~���X4�uay)���&����|e ?P1�'���F5�߬^��u�?�oT!��XBU��jԋ��4�b���gN�B�<p�o�+� 1�C��YV�g1�o�x��CBef�q_01�4�T(�'8��a'��	�����ĔNT��D�%��*'�3�A�L������,��(��_��t'����5WC�      m   �  x�}U[��0�&��2��q�=���#pzvjK���H+"�}��6�q������}�N1V�m� h�Qzwq����^�-�9�;��ѕ�K,��D	q�l�o&���l�x�(�q>��滁߲>4?��g��ߺX{��Q�B�ğ���6�w@�뛢=�� ���?��߇�7��!6���ڢ���i���?r�U���i���%ܧ����=m9�-�aQ�b�l#��܂w�7��o������]�;?��Vo,�T�Hl��5Ǝ���գi����1����?4VQ�N�hH���>k������c]1�i&�<��4�C!�L7)��ɥRFL}���Ҙ=��w���<�|����p(��-�p��+����&eu֙n�f쥂��e*��N�X&�򞳒b��t��s��\�	��Y�_4�c�����K��?�u}�Y�      o   �  x��V�n�0=;_!`�a�)G�sTlmS�H�dh�s�`��Ѯ�R���94��G��#ٿ��]��^�Q2�Z�+?*��N�4�̚�#X1���~b���:�$Jˆ��;^����MF�q��ȉ�a�Y�t�9e�U:{���I+ס�_�+I�<�#Nk���`v:�e��̢er��rM�|��@�TK���5W��\o[-���"} R�DZ��!�G�RW�Ifw���l�nN׀����l��U/�/���I�Nc��?8m&۠�Ճ�����Sq�����m�:�\�T�@.�~�"Oo/�պ%P]U	mYP?��d���#���n26�X�����%^�)� 9*���~j�i�0E!��g�		���L��Z%��6R�#�	�����S2�[P�لʅA1��(ش�Q9�,��0�d��"�{s�JNi�)K��u�go*rJz,f�o����"��] z-�]q�`�x����힤8��!���Zm����i�{V�����(���a�'���%��*3�S�y�6��O�zNB�5[���D��������CL�c�c5�ÅC�]�O:��B�9[��H�+�pLQ��`n��z�<�D-q|t��;1JLPJ�p�t#v���I�o�s��(ak��*9�*���x�+3�Z��{&O֍Q���f�x������h{B�������w����{��mY�Vj)r����L��*���o����(��w���6��      q      x��Z[��r�f����M��O�B���C�GMW��x	��*���	��D����4=�� ����ŕ�d�3�_|�nM�0-�aYYe���T/�u��X�����/?�?���2�o\����q�_�c�؊eU���)^��?��B�5?{vq��b�k� .���M��M����y��3���45	C��o/����(���M�~��y��?�*�z`��cS��a�2��4ߎ)�-AT徊���W\�H�6�����O��ig7;�H�$ $�ݳ���ӝVc}ô?F�Z7��ׂ�!�����f0���;D &�%CXK�m�8�D�u)\oѸ&�%�֠*I���긟��h��|�WF����6>�]�ѢHᑋ]��ǳkkE��[u���{��xs�O���ج��2h�BɐOˍ�w�s3u�a�a��IY;$q��B*n�����y��a�\���4`����fl��)��#��O���̔
x%6���~�0�=r3��
���"U��t:lj$�ts�y���0)5��T�c��~���Ğ�HKdla���(8>���vەT:��J��OyGKF�RSN�	<i=��Ҡ�D��d0��횚y��0Yj�Z`��S{�/��?/u��L���IhL��mq=S%I�X�8rZm�Lg"+��~v=NX��>OɯRTd���Bf�y�/�YsʝD�ձT�B#�n��N�
����ۖ6Δ@�\	�}��z1�	�����l���"��}��1YZ X��3�!���w�����@�ߕRw�]{��EH"�%���R�yv&����.��� 3����'m7�ڞ��M��&c%KC�Ig��ȅ�we�7�b�<l��md����Z"� n���Z�*4��!�H�+��}�rI>�C�bW ���<���}�[�T�V�����g+��Ԗ�C�� �"���k9��}�����6���/����M�z���]W������T�zL���FVv�75P�T؀��4���y�*t��-��F"�<�uFZ�QH����T�ڴ��l۴�֬R4��Mʹ2�	�� ��s\!9~�����d]瀌��������ѓ�8���hf�ҡ�;aE�v.܃�ж���j��O��T��I����ắ�Z�-�	E�SB!%�et�г�A���$��5=�AD)GPe	�<R^�U�"�@x AkzJZ0@P@���
$!�
�U�[��N��5t(4��D�;�������eI��I?���L�2D���x����'��Q���k�U(��g�7�6gRT������ .�
\[�tH8YSjL���LD�6�G��G���K�i�ˉMC(���P)[�C�PnT�AI�XB�5��~ !�������*싐sV7K|V�âlOE��3�$��T
N�%�s���T7D������G�E�	�'_���b�/��_���}��&�\p+��|���S��� ���u�K��c��W�2@�~��'�}�RڀD�ѯ͉^1H���B��=S� �?iSsÆ�O,�M��S.Cۤ���̨�o2�U䁹֡�n�&"e����@*o��M�j$��`fLa�5�]����@�EY�R���E� B�
�|ƺ�w߭�bd�肞�/�QP�^K��F�u�8�.�h�^A�+D��f�}~7�����S�WP@��X��5ߝ��L�^a��Am����'����ğK����_�t^�Ӱ�E�~*���_���&WA$0��ݠ�1J�`�����z_h�ԩh�V�S�P��� P�Pf����x$� ��X'l�7QJɃ�C
#+i�0OeT
�!�s�ma�R}K��vo M�yjiѤY=`���z��2��g�vעlG�82�4������8���S����4>4� ��8�����6�X+--���<��?|�������Mp��� KEM��[�߸�:�|���w� ���d�ӓ�B�P��:�؆d�F�A�O��8�9A!"l�j�"�4��X�=_\B�$���K�+x��g�)~<�Hwd@Kr����b�C$R��fY*�e� �\FO3�� ��P0�b.!C(:a���=�ݹ�i�6Y��s���&�҇vl���ca��V����"�DJ��>��1S��5�s�-*������	�A2lq7��iR��O?\��tg±���-rt�;?L�㝣]��>�ȀR��7�a�?�MV���>YEN���I���Y���,ɋ�,L�{������7�ۄ�L�/A��',�Jy�s֚���e�@֖�����Bz��/,S\t0ASo�ǼЖ�tXu�[��оsѺ��(� �ts��'MU"Fڑ����(V웝�'�,����Z��C��u����,X��,����)�)�?g5FCk)�Ǒ,���:Ґ���� B��������gHf������%��: `3 
[�Rd��mlз.���vAPt�ٻ�v�-O��#�T���w'��.B	�l������x�q	)��M���S�lA��cV�8�'���A١fE���k��%�Xy�6��Xc���8E��S�V%������N��;6sw:C�.�t��)�u�i����Ll�ɍ|�ݳ ��O'�E
Nݼ�4?(�Eġ�>���-s�t�V��)Ի�/��͓б'�_csA��1$��T�_�w�EW��Z|�:�@�Z���(�K��� �eO���}�ۍ����0�L�=�q��"�����gi�=S��k4O�����1��)�*��,�����	A�t%
ӳ�=��?-9�)�DV�C;`ā�
��)k<�I]3A؍T�K������4
#+� � ��#&�F\r.�쯤�A�T�2E��G��,�����.U� ���Ǳɵ���	#��"a!��;-5�<q�*�'p7�:����uND�+[�ztt���e��g�T�CN%���8B�aFԣQ�n���۳05v��A�˲��D�ՎR��*�t�P̈́�ز��C����ˇ� �lH�H(К�/�+�L���;��5�x(�g����\	�G���VrN�u�,L�c����C��k}Y�էp������)#Ńˮ1��7��q:?�`��ZK��LH�:z8��EخKH�5�
'��a{�:��.�P�����B4��c@jҤ�G�÷7(<��@�	��D	bR�o3����xO%%�����JR��7YS�K�D@ej;��`�1�����͏ڎ�wg]��	�3G Z!�A��:R��
�;��	��N��ޡF$��&������G��=�f+}��!:��D
�Bo�$�}y�B�o���9G�����-��BA?��)��P�1K�������Q�n���,��@�`��g��	|]xի�����*��� S,�`P�,9l�
��sR���)���Æ`���O�.��VO0:�C��_�$M�+W���qL�f��/��C�+��Ҫ����v�"����&��:��+ �i��M��q�P���������I c�}[>(����0ԴÍ$W���_���^��b����`�=0��˟�o�F�*��W]�/��^P4����U���߶��i�?q����0�P���<��z[�]6#	{������n3&����J��x�|=^��Mw޳�MJ�8����,/�0�^n���� ���Oy��9/��[D2ps��������t�xq�l�����K�y=��ץ��s��R����Q���Ʀf�^"=�k��0'ۉ%�RJ���H��JsJ��\7
��X��e8���~���'�Ϥ��Kb��/i[���5O�@�"�b�:��lȘ`�^~,�_�$4XT�y-�|����Ӱ���s(�#
�z͉�]�e�0�L���'����rZ�G�.�e�LI�_��'A�#Q.[����^�BY������o2��S!�7��W6���pbFI�FL��sz󲅘�m����/xʦ�S?�02��P��y�_X��V��(�W���}��{ 0   �S�x���<����Qt�pj3?F���v�����8v���{����O�      s      x�͝]��ƙ��Ux2.��O�m60+���1��j1c#'���A��Cu�"���9��߶��o���߮��/����ķ�����?}�����D�߄�u��&D�M�k�%!:n��oBt�D�߄躉��	����!zk��/���}>�OG��D����"Zo"��h������M���7�wvCt�D���yyg7D�M�=�.���~{g'D��-���7aZ`�ގ�V���#�&�����wĴ���10y�GL'L�������O�$�&��i��#
����=1���{<b�����h�Lx|������=1���{<b���xĄ���	�/����ӊ�WG2���{<b���xĄ�W��	����_��#&<�z�GLx|������=1���{|���1mx|sD!߼�#&<�y�GLx|�}+&����=1���{<b���xĄ�7��	�o��߼����2&<>�[�eLx|x���F��I&<><��eLx|x��˘���I��1����1�����1���7��1����ïb����B&<�k��+&����=^1��]{�b���xń�w��	����ߵ�+&<�k�WLx|���<~8����e'����=1���{<b���xĄ���	���?�Ǔ������xĄ���	���Ӊ�OG2���{<b���xĄ�O��	����?�Ǜy�����?��#&<~z�GLx��of�.<~9��	�_���i͊	������a�	���fń�}f�b��>�Y1�q�۬��OnO�&���nFL�7�ެ���oVLx�'8+&<�3��)Ί	��gń�}��b��>�Y1�q����O2�q��l�V��sVLL��ɼ�J�s�yΊi��{<bz��=1�0y�GLL���	��x�t��=1�a�OfCV��sVLx��9+&<���yΊ	��<gń�}��b��>�9�H�d��>�Y1�q�笘��sFL�9W�笘��s'\&���sVLx��9+&<���yΊ	��<gń�}��b��>�Y1�q�猘�s�>�9���d��>�Y1�q�笘��sVLx��9+&<���yΊ	��<gń�}�s8�;Ʉ�}�3b"Ϲ�<gń�}��b��>�Y1�q�笘��sVLx��9+&<�����I&<���yΊ	��<g�D�s�yΊ	��<gń�}��b��>�Y1�q���t&���sVLx��9+&<���4�$�yΊ	��<�0a5�D�s�yΊ	��<gń�}��b��>�Y1�q�笘��sS��Lx��9+&<���y�a�r�	��<g�D�s�yΊ	��<gń�}��b��>�Y1�q���	�d��>�Y1�q�笘��sVLx��9+&<��C��1��\}��b��>�Y1�q�笘��sW�I&<���yΊ	��<�p՞d��>�Y1�q�笘��sFL�9W�笘��sVLx��9+&<���yΊ	��<��b�	��<gń�}�s��3Ʉ�}��b��>��0m�97��>N2-0y�GL+L�����x���{<9��sn>�Y10y�'g�y���9+�&�����xr�����|��b��>�Y1}����I{|xb5ɴ��=^1�`���v���+�&���I{�b�`����0i�GL�<�&�笘���+&<���y����$�yΊ	��<gń�}��b��>�Y1�q�笘��sFL�yΊ	��<��r�	��<gń�}��b��>�Y1�q�笘��sVLx��9+&<�����I&<�����<gń�}��b��>�Y1}�����{<�+�>yο���#�&����{�a���|^j8	4�����R�	�_�{�O^�/&��ELo��z�0}�R?�|^�bZ`��]Ĵ��׻����祆x�L�w>/U1�q������KULx��*&<��R�y�����>/U1�q��N�N2�q������KULx�祆S˓Lx��*&<��R�y��	���Tń�}^j8?�t�q������KULx��*&<��R��$�y��	���Tń�}^�b��>/U1�q������KS:sLo<��R�y��	���Tń�}^�b��>/U1�q������KULx�祆'���KULx��������KULL���
��xĴ��=1�`���v���#�&���>��u��=1]0y�GLo�������Tń�}^�b��>/5�N2�q������KULx��*&<��R�y��	���Tń�}^*bZ��KULx�祆O��Lx��*&<��R�y��	������a�	���Tń�}^�b��>/1m|N>�0<�d�s�y����$��#DL/�;�G��X�|�bb��y������*&�;�G��X�|�bb��y��	���K����I&<��/�1�q�~��i����R��ʘ�~�TƄ����2&<��/�1�q��Z�7Y�Lx\�2&<��R�y��	���:���c"/��y��	��TƄ�u^j��N2�q��ʘ��KeLx\�2&<��R�y��	��TƄ�u^jNI�1��z�TƄ�u^*c��:/��I&���KeLx\�2&<��R�y�u8	4Ʉ�u^*c��:/���&���KUL�^:/�'�&���KeLx\����$�y��	���:�,�d��:/�1�q��ʘ��K��	�I����0i�'�&��0i�GL���&��ʘ���+�&��i�I{�bz��=1�0y�GLL���	��xĄ�u^jN-O2�q���������KeLL���
��x´��|����:�X���z���.�?���Z����>������i�a=��z鱣�h����QD�����QD��Dz�h8B9Gt�Dz�(":o"=4]7����7�m�>�ӟ�u��g�����ǃh�}��"�&�x10�ǃ�Jj�:a���<���0�ǃ�i��@�p3&t7c��@�p3&<�c��1܌	���:�7&���fLx\�p3&<�c��1܊i��:��1�q�͘��fLx\�p#�������|hr��9��M�����D�s�r�Ӹ�Bl���i\����ȟ�E�I���i\C��D�4�y��q�Ӹ�載�i\Ct��� ���<���4����x���]�1�4��2&�t�C��Ӏޗ��g�I&��Τb�x�{�����;ɘx�������Cɘ�ޣdLx\�R2&<��)�;��i��S|����sD��җ��?Gt�/}%^Dt�	�B���>�ux�uq��>�ex��]�Ux�}&����}c_���2o����W��:��b_�˯2&����*cb_�˯2&����*cb_�˯2&����*cb_�˯2&�����b����b���j���c��/����/����/����/����/����/����/����/����/�>yM2�q_~1|�|YQ���ΗUL|�|YQ���ΗUL|�|YQ���Η�<�N�O���bb��ˊ*&�O���bb��ˊ*&<�ˊ��h�LxܗULxܗULxܗULxܗ5L�=w�_5����,�>i�_1�t����������'5L�vW:�����p1��ZP������^�+��J���~�[�\�w�݊�䪘ح�J���݊���&N2�[�\�_�5���db��+�*&���k8�9�D����e����z����TLL~������^�b�`�kK��ɯ-�i�A��{ *�&��DL'Lzmi��z �P1�a����8|��Yg�	����	���&�w�p���	���&�v�p���	����	����	���&ow�p������� TLx�� 4�ă��� TLx�� TLx�'8+&<��M�A���	Ί	��gń�}�3j�#�y�g�D���������w�	Ί	��gń�}��b��>�Y1�q����Lx�'�"&S�OLULx�'�*���?L��u�۞d�`���^0y�GL;L�����x��I��`��c���}M���ο��b�{��߻���}M���ο��b:a����
9�t���O� W  &���>�����TLL���
��xĴ��=1�`���v��Ǜ����{<b:a���.���#�7L��Ӊ��|Ań��|Ań��|Ań��|Ań��|���d�	�����	�����	�����	�����	��������~��b��~��b��~��b��~�`��4Ʉ��|Ań��|Ań��|Ań��|��[k�	�����	��������|Ań��|Ań��|Ań��|Ań��|���n�	�����	�����	�����	�����	���������~��b��~��b�㾑�b�㾑uع9Ʉ�}#kń�}#kń�}#kń�}#�/u�	��F֊	��Fֈi�㾑�b�㾑�b�㾑�b�㾑�b�㾑�b�㾑�b�㾑�b�㾑uh�I&<�Y+&<�Y#���(d���xĄ���	�����������$������ӆ�}�oń�}�oń�}�oń�}�oń�}�oń�}�oń�}���)u�	���߈����37ç�I&~w>sS1��}�k��;��X�}����a�ig~;���5�g�*&�`�᪘X�}��a��6>?����(���D�M�Q��&�-�Q2�&����h������O�N��ŧ��ÓL�P|:�bb��Ӊ;�N��ء�t��l��t��Ӊ��ߡDL+L~��ܖ�N�|:�bz��=1�0y�GLL���	��x�t��=1}Zz��ǎ�ˠ�@�x��_P����:�����A�=��"���!��P^	���{��ʏd&�����EP�����z�Oe5P��
<ɳ4oJ�_ɧ�z��O}����?y�9O�f��<v��xΟ<vu�x�?y��4o��>��Hw^DD���S����Տ=����{J?:���z�)�lh��S���
걧�ӡ�cO��C��V�P�=����{J? �@}N�~\��8��:o"�GD�MdMP�o"�-h�`?�M�ԣ��}5�+��j\�VD�ո*���q=RX�O�~�>ZKޏ=����{^?E_A=��~���z�y�}����A��f������c��G�+�Ǟ���WP�=��o������4}�0����F����vo~uc�Ҽ��uo~u_IEto~u[IEto~�yEto~��xDt}=�*>*2ܜNB=�*>+RA=�*�߫DP�����*�c�r9��W��^%���ˏ��核o��7P����F����/(��J�D�O��O(����^(��DP��/3�����R�	嗙����t�����LrJv}�T�P�}�=	��Qa�J����sP�:��P��WA-@��
j}@����PzA��^(� WP�J/����ru>��\A](o��@j}ݗ�GP�����z|�|}u������
�������I��e�ߠ���湏�Q��|5s����>)��a��<���"�(�b=<����P~?A�P~?A�(o��	���oP������F�����Ts�@G�7(�j�g�*���}v���~���_B9����ڞT~���^O*��DT���/5���kMDu>��bQ]O*��DT���Uo
税�'��FT�ˬ�k���:�X�,ǟ����s��u���OG�}��1����v_7<U�dZ`��*�&�'�b�`Ҏ��^0i�WL;L�����{������a%���/��Ōi�I�UL+LznX]6ɴ����*�L:R1�0� }�t��3|����+�������+�+�7L���a�^�uȦ���aq�$���`VLo��1����3��i�I���
�^�+�&�WL/���0-w�>ë*Q��!}~�w�sDw�>�����!}n���>AP���B` �^��+|~`h�I&^Y���,�|c\��+._���b�˷55Lw��9V�w/�΁UDw/��-�Y�9���E��"������uGS��+�;����7+��j8-?��͊/����Y��U7+��%b�Q�a򯿨n/��n�aZ��X�7sD�S��,���9��U�*��!U�"��y��ߌ7����c�'5���{���4+�{��g4+�{��O��w/sD�ן{GD�ןz7D����g�ѿ���������     