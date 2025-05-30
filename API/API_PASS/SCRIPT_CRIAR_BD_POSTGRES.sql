PGDMP  $    9                }           gestor_passwords    16.9    17.4 .               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            	           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            
           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false                       1262    16398    gestor_passwords    DATABASE     �   CREATE DATABASE gestor_passwords WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
     DROP DATABASE gestor_passwords;
                     postgres    false                        3079    16459    pgcrypto 	   EXTENSION     <   CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;
    DROP EXTENSION pgcrypto;
                        false                       0    0    EXTENSION pgcrypto    COMMENT     <   COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';
                             false    2            �            1259    16423    apps    TABLE     �   CREATE TABLE public.apps (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    user_id integer,
    uuid uuid DEFAULT gen_random_uuid()
);
    DROP TABLE public.apps;
       public         heap r       postgres    false            �            1259    16422    apps_id_seq    SEQUENCE     �   CREATE SEQUENCE public.apps_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.apps_id_seq;
       public               postgres    false    221                       0    0    apps_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.apps_id_seq OWNED BY public.apps.id;
          public               postgres    false    220            �            1259    16449    logs    TABLE       CREATE TABLE public.logs (
    id integer NOT NULL,
    user_id integer,
    username character varying(100),
    acao character varying(100) NOT NULL,
    descricao text,
    sucesso boolean DEFAULT true,
    data timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.logs;
       public         heap r       postgres    false            �            1259    16448    logs_id_seq    SEQUENCE     �   CREATE SEQUENCE public.logs_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.logs_id_seq;
       public               postgres    false    225                       0    0    logs_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.logs_id_seq OWNED BY public.logs.id;
          public               postgres    false    224            �            1259    16435 	   passwords    TABLE     �   CREATE TABLE public.passwords (
    id integer NOT NULL,
    site character varying(100) NOT NULL,
    password text NOT NULL,
    app_id integer,
    uuid uuid DEFAULT gen_random_uuid()
);
    DROP TABLE public.passwords;
       public         heap r       postgres    false            �            1259    16434    passwords_id_seq    SEQUENCE     �   CREATE SEQUENCE public.passwords_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.passwords_id_seq;
       public               postgres    false    223                       0    0    passwords_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.passwords_id_seq OWNED BY public.passwords.id;
          public               postgres    false    222            �            1259    16400    roles    TABLE     `   CREATE TABLE public.roles (
    id integer NOT NULL,
    nome character varying(50) NOT NULL
);
    DROP TABLE public.roles;
       public         heap r       postgres    false            �            1259    16399    roles_id_seq    SEQUENCE     �   CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.roles_id_seq;
       public               postgres    false    217                       0    0    roles_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;
          public               postgres    false    216            �            1259    16409    users    TABLE     �   CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    role_id integer
);
    DROP TABLE public.users;
       public         heap r       postgres    false            �            1259    16408    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public               postgres    false    219                       0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public               postgres    false    218            U           2604    16426    apps id    DEFAULT     b   ALTER TABLE ONLY public.apps ALTER COLUMN id SET DEFAULT nextval('public.apps_id_seq'::regclass);
 6   ALTER TABLE public.apps ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    221    220    221            Y           2604    16452    logs id    DEFAULT     b   ALTER TABLE ONLY public.logs ALTER COLUMN id SET DEFAULT nextval('public.logs_id_seq'::regclass);
 6   ALTER TABLE public.logs ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    224    225    225            W           2604    16438    passwords id    DEFAULT     l   ALTER TABLE ONLY public.passwords ALTER COLUMN id SET DEFAULT nextval('public.passwords_id_seq'::regclass);
 ;   ALTER TABLE public.passwords ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    222    223    223            S           2604    16403    roles id    DEFAULT     d   ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);
 7   ALTER TABLE public.roles ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    216    217    217            T           2604    16412    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    218    219    219                      0    16423    apps 
   TABLE DATA           7   COPY public.apps (id, nome, user_id, uuid) FROM stdin;
    public               postgres    false    221   �0                 0    16449    logs 
   TABLE DATA           U   COPY public.logs (id, user_id, username, acao, descricao, sucesso, data) FROM stdin;
    public               postgres    false    225   p1                 0    16435 	   passwords 
   TABLE DATA           E   COPY public.passwords (id, site, password, app_id, uuid) FROM stdin;
    public               postgres    false    223   �2       �          0    16400    roles 
   TABLE DATA           )   COPY public.roles (id, nome) FROM stdin;
    public               postgres    false    217   �3       �          0    16409    users 
   TABLE DATA           @   COPY public.users (id, username, password, role_id) FROM stdin;
    public               postgres    false    219    4                  0    0    apps_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.apps_id_seq', 3, true);
          public               postgres    false    220                       0    0    logs_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.logs_id_seq', 5, true);
          public               postgres    false    224                       0    0    passwords_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.passwords_id_seq', 5, true);
          public               postgres    false    222                       0    0    roles_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.roles_id_seq', 1, false);
          public               postgres    false    216                       0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 3, true);
          public               postgres    false    218            e           2606    16428    apps apps_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.apps
    ADD CONSTRAINT apps_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.apps DROP CONSTRAINT apps_pkey;
       public                 postgres    false    221            i           2606    16458    logs logs_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.logs
    ADD CONSTRAINT logs_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.logs DROP CONSTRAINT logs_pkey;
       public                 postgres    false    225            g           2606    16442    passwords passwords_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.passwords
    ADD CONSTRAINT passwords_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.passwords DROP CONSTRAINT passwords_pkey;
       public                 postgres    false    223            ]           2606    16407    roles roles_nome_key 
   CONSTRAINT     O   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_nome_key UNIQUE (nome);
 >   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_nome_key;
       public                 postgres    false    217            _           2606    16405    roles roles_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_pkey;
       public                 postgres    false    217            a           2606    16414    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public                 postgres    false    219            c           2606    16416    users users_username_key 
   CONSTRAINT     W   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);
 B   ALTER TABLE ONLY public.users DROP CONSTRAINT users_username_key;
       public                 postgres    false    219            k           2606    16429    apps apps_user_id_fkey    FK CONSTRAINT     u   ALTER TABLE ONLY public.apps
    ADD CONSTRAINT apps_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);
 @   ALTER TABLE ONLY public.apps DROP CONSTRAINT apps_user_id_fkey;
       public               postgres    false    219    4705    221            l           2606    16443    passwords passwords_app_id_fkey    FK CONSTRAINT     |   ALTER TABLE ONLY public.passwords
    ADD CONSTRAINT passwords_app_id_fkey FOREIGN KEY (app_id) REFERENCES public.apps(id);
 I   ALTER TABLE ONLY public.passwords DROP CONSTRAINT passwords_app_id_fkey;
       public               postgres    false    221    223    4709            j           2606    16417    users users_role_id_fkey    FK CONSTRAINT     w   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);
 B   ALTER TABLE ONLY public.users DROP CONSTRAINT users_role_id_fkey;
       public               postgres    false    217    219    4703               �   x�5�1�0 �9yE>`��Nڎ| ��;���
�����.��]/�sJɡS�Er�\$A���dm]��ǿADG�*QlM�g��h5��D�B9���	:�հ�#|�6�k���_E�e`�n 2G"�D������H/�         a  x�mQ�N�0�����C�����,,L�V)�I\�Hm�I�7���a�GU�����%�;��s�Aw�wdr��6�>��2A�p��0��o�ن��ɼ�9c@Y-E-U�%��ad7�@I��'��������c��`v�=$J��]���[fd:��kZֲ*X9W�����8����(�	nЍE��nr��&�Km'��B����B�M:����ᏻ��֔j����w��م����m#�O��e|F3i��o���Q�l���ju{��W��e��m��|��U%�Q.�y6���W%2yV�iD���e�[8LSӛT�������Ŝ�UٺȲ�&��         �   x�]��j�1D��wYٿ$_ޥP�ݍ�V�����Ђsf�PZ�b^���]?�-.�A��[|R��̖ Ȑ��`7��E�!S����?���J0V�TAq-���@*��4�,i�:�� 'N�M-D���낮ڠ3�MĦkdM���~��:<+fQ����#�S��hB��+���g~J���<}Cn�s{��e��]�,I�$s�h�tе�Boͱ�f="��r�ߌ�c�      �      x�3�,-N-�2�LL�������� :�      �   0   x�3�LL��̃��FƜF\F��ũE���؄Ӑ��1B����� ��     