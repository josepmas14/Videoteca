CREATE TABLE "peliculas" (
	"id"	INTEGER,
	"titulo"	TEXT NOT NULL,
	"id_director"	INTEGER NOT NULL,
	"anyo"	INTEGER NOT NULL,
	"url_caratula"	BLOB NOT NULL,
	"id_genero"	INTEGER NOT NULL,
	"es_animacion"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("id_director") REFERENCES "directores"("id"),
	FOREIGN KEY("id_genero") REFERENCES "generos"("id")
);

CREATE TABLE "artistas" (
	"id"	INTEGER,
	"nombre"	TEXT NOT NULL,
	"url_foto"	TEXT NOT NULL,
	"url_web"	TEXT NOT NULL,
	PRIMARY KEY("id")
);

CREATE TABLE "directores" (
	"id"	INTEGER,
	"nombre"	TEXT NOT NULL,
	"url_foto"	TEXT NOT NULL,
	"url_web"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "generos" (
	"id"	INTEGER,
	"descripcion"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);

CREATE TABLE "repartos" (
	"id_pelicula"	INTEGER,
	"id_artista"	INTEGER,
	PRIMARY KEY("id_pelicula","id_artista"),
	FOREIGN KEY("id_artista") REFERENCES "artistas"("id")
)