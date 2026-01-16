DROP SCHEMA public CASCADE;
CREATE SCHEMA public;


-- public."difficultyLevel" definition

-- Drop table

-- DROP TABLE public."difficultyLevel";

CREATE TABLE public."difficultyLevel" (
	"difficultyId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" text NOT NULL,
	CONSTRAINT razinatezine_pkey PRIMARY KEY ("difficultyId"),
	CONSTRAINT unique_difficulty_name UNIQUE (name)
);


-- public.ingredient definition

-- Drop table

-- DROP TABLE public.ingredient;

CREATE TABLE public.ingredient (
	"ingredientId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" text NOT NULL,
	CONSTRAINT sastojci_pkey PRIMARY KEY ("ingredientId"),
	CONSTRAINT unique_ingredient_name UNIQUE (name)
);


-- public."instructorProfile" definition

-- Drop table

-- DROP TABLE public."instructorProfile";

CREATE TABLE public."instructorProfile" (
	"instructorId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	biography text NOT NULL,
	username text NOT NULL,
	specialization text NOT NULL,
	CONSTRAINT instruktorprofil_pkey PRIMARY KEY ("instructorId")
);


-- public."materialType" definition

-- Drop table

-- DROP TABLE public."materialType";

CREATE TABLE public."materialType" (
	"typeId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" text NOT NULL,
	CONSTRAINT tipmaterijala_pkey PRIMARY KEY ("typeId"),
	CONSTRAINT unique_type_name UNIQUE (name)
);


-- public.tag definition

-- Drop table

-- DROP TABLE public.tag;

CREATE TABLE public.tag (
	"tagId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" varchar NOT NULL,
	category varchar NOT NULL,
	CONSTRAINT tag_pk PRIMARY KEY ("tagId"),
	CONSTRAINT tag_unique UNIQUE (category, name)
);


-- public."enrolleeProfile" definition

-- Drop table

-- DROP TABLE public."enrolleeProfile";

CREATE TABLE public."enrolleeProfile" (
	"enrolleeId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	username text NOT NULL,
	"skillLevelId" int4 NOT NULL,
	CONSTRAINT polaznikprofil_pkey PRIMARY KEY ("enrolleeId"),
	CONSTRAINT polaznikprofil_idrazinevjestine_fkey FOREIGN KEY ("skillLevelId") REFERENCES public."difficultyLevel"("difficultyId") ON DELETE RESTRICT
);


-- public.material definition

-- Drop table

-- DROP TABLE public.material;

CREATE TABLE public.material (
	idmateriala int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	contents text NOT NULL,
	"typeId" int4 NOT NULL,
	CONSTRAINT materijali_pkey PRIMARY KEY (idmateriala),
	CONSTRAINT materijali_idtipa_fkey FOREIGN KEY ("typeId") REFERENCES public."materialType"("typeId") ON DELETE RESTRICT
);


-- public."user" definition

-- Drop table

-- DROP TABLE public."user";

CREATE TABLE public."user" (
	"userId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	firstname text NOT NULL,
	surname text NOT NULL,
	password_hash varchar(256) NOT NULL,
	email text NOT NULL,
	"createdAt" timestamptz DEFAULT now() NOT NULL,
	"enrolleeId" int4 NOT NULL,
	"instructorId" int4 NULL,
	"isVerified" bool DEFAULT false NOT NULL,
	CONSTRAINT korisnik_emailkoris_key UNIQUE (email),
	CONSTRAINT korisnik_pkey PRIMARY KEY ("userId"),
	CONSTRAINT korisnik_idinstr_fkey FOREIGN KEY ("instructorId") REFERENCES public."instructorProfile"("instructorId") ON DELETE SET NULL,
	CONSTRAINT korisnik_idpolaz_fkey FOREIGN KEY ("enrolleeId") REFERENCES public."enrolleeProfile"("enrolleeId") ON DELETE RESTRICT
);


-- public.usertag definition

-- Drop table

-- DROP TABLE public.usertag;

CREATE TABLE public.usertag (
	"userId" int4 NOT NULL,
	"tagId" int4 NOT NULL,
	preferred bool NOT NULL,
	CONSTRAINT usertag_pk PRIMARY KEY ("userId", "tagId"),
	CONSTRAINT usertag_tag_fk FOREIGN KEY ("tagId") REFERENCES public.tag("tagId") ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT usertag_user_fk FOREIGN KEY ("userId") REFERENCES public."user"("userId") ON DELETE CASCADE ON UPDATE CASCADE
);


-- public.course definition

-- Drop table

-- DROP TABLE public.course;

CREATE TABLE public.course (
	"courseId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" text NOT NULL,
	"creatorId" int4 DEFAULT 1 NOT NULL,
	description text NULL,
	"difficultyId" int4 NULL,
	"avgRating" numeric(3, 2) DEFAULT 0 NULL,
	CONSTRAINT tecaj_pkey PRIMARY KEY ("courseId"),
	CONSTRAINT "course_difficultyId_fkey" FOREIGN KEY ("difficultyId") REFERENCES public."difficultyLevel"("difficultyId") ON DELETE RESTRICT,
	CONSTRAINT tecaj_idkreator_fkey FOREIGN KEY ("creatorId") REFERENCES public."user"("userId") ON DELETE SET DEFAULT
);


-- public."enrolledCourse" definition

-- Drop table

-- DROP TABLE public."enrolledCourse";

CREATE TABLE public."enrolledCourse" (
	"completionPercentage" int4 NOT NULL,
	"courseId" int4 NOT NULL,
	"certificateId" int4 NULL,
	"userId" int4 NOT NULL,
	CONSTRAINT completion_range CHECK ((("completionPercentage" >= 0) AND ("completionPercentage" <= 100))),
	CONSTRAINT upistecaj_pkey PRIMARY KEY ("courseId", "userId"),
	CONSTRAINT upistecaj_idcertifikata_fkey FOREIGN KEY ("certificateId") REFERENCES public.material(idmateriala) ON DELETE SET NULL,
	CONSTRAINT upistecaj_idpolaz_fkey FOREIGN KEY ("userId") REFERENCES public."user"("userId") ON DELETE CASCADE,
	CONSTRAINT upistecaj_idtecaj_fkey FOREIGN KEY ("courseId") REFERENCES public.course("courseId") ON DELETE CASCADE
);


-- public."module" definition

-- Drop table

-- DROP TABLE public."module";

CREATE TABLE public."module" (
	"moduleId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" text NOT NULL,
	"courseId" int4 NOT NULL,
	orderindex int2 DEFAULT 0 NOT NULL,
	CONSTRAINT model_pkey PRIMARY KEY ("moduleId"),
	CONSTRAINT model_idtecaj_fkey FOREIGN KEY ("courseId") REFERENCES public.course("courseId") ON DELETE CASCADE
);


-- public.collaboration definition

-- Drop table

-- DROP TABLE public.collaboration;

CREATE TABLE public.collaboration (
	"courseId" int4 NOT NULL,
	"userId" int4 NOT NULL,
	CONSTRAINT pravakolaboracije_pkey PRIMARY KEY ("courseId", "userId"),
	CONSTRAINT pravakolaboracije_idkoris_fkey FOREIGN KEY ("userId") REFERENCES public."user"("userId") ON DELETE CASCADE,
	CONSTRAINT pravakolaboracije_idtecaj_fkey FOREIGN KEY ("courseId") REFERENCES public.course("courseId") ON DELETE CASCADE
);


-- public.lecture definition

-- Drop table

-- DROP TABLE public.lecture;

CREATE TABLE public.lecture (
	"lectureId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"writtenSteps" text NOT NULL,
	"preparationTime" interval NOT NULL,
	"cookingTime" interval NOT NULL,
	"name" text NOT NULL,
	"moduleId" int4 NOT NULL,
	"videoId" int4 NOT NULL,
	"difficultyId" int4 NOT NULL,
	"creatorId" int4 DEFAULT 1 NOT NULL,
	CONSTRAINT lekcija_pkey PRIMARY KEY ("lectureId"),
	CONSTRAINT lekcija_idinstr_fkey FOREIGN KEY ("creatorId") REFERENCES public."user"("userId") ON DELETE SET DEFAULT,
	CONSTRAINT lekcija_idmodel_fkey FOREIGN KEY ("moduleId") REFERENCES public."module"("moduleId") ON DELETE CASCADE,
	CONSTRAINT lekcija_idrazinetezine_fkey FOREIGN KEY ("difficultyId") REFERENCES public."difficultyLevel"("difficultyId") ON DELETE RESTRICT,
	CONSTRAINT lekcija_idvideo_fkey FOREIGN KEY ("videoId") REFERENCES public.material(idmateriala) ON DELETE RESTRICT
);


-- public."lectureIngredient" definition

-- Drop table

-- DROP TABLE public."lectureIngredient";

CREATE TABLE public."lectureIngredient" (
	amount numeric(10, 2) NOT NULL,
	"lectureId" int4 NOT NULL,
	"ingredientId" int4 NOT NULL,
	unit text NOT NULL,
	CONSTRAINT amount_range CHECK ((amount > (0)::numeric)),
	CONSTRAINT "lectureIngredient_unit_check" CHECK ((unit <> ''::text)),
	CONSTRAINT sastojcilekcija_pkey PRIMARY KEY ("lectureId", "ingredientId"),
	CONSTRAINT sastojcilekcija_idlekc_fkey FOREIGN KEY ("lectureId") REFERENCES public.lecture("lectureId") ON DELETE CASCADE,
	CONSTRAINT sastojcilekcija_idsast_fkey FOREIGN KEY ("ingredientId") REFERENCES public.ingredient("ingredientId") ON DELETE RESTRICT
);


-- public.qanda definition

-- Drop table

-- DROP TABLE public.qanda;

CREATE TABLE public.qanda (
	"qandaId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	contents text NOT NULL,
	"parentqandaId" int4 NULL,
	"lectureId" int4 NOT NULL,
	"userId" int4 DEFAULT 1 NOT NULL,
	CONSTRAINT qanda_pkey PRIMARY KEY ("qandaId"),
	CONSTRAINT qanda_idlekc_fkey FOREIGN KEY ("lectureId") REFERENCES public.lecture("lectureId") ON DELETE CASCADE,
	CONSTRAINT qanda_idparentqanda_fkey FOREIGN KEY ("parentqandaId") REFERENCES public.qanda("qandaId") ON DELETE CASCADE,
	CONSTRAINT qanda_korisnik_id_fkey FOREIGN KEY ("userId") REFERENCES public."user"("userId") ON DELETE SET DEFAULT
);


-- public.review definition

-- Drop table

-- DROP TABLE public.review;

CREATE TABLE public.review (
	"reviewId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	score int2 NOT NULL,
	"commentPhotoId" int4 NULL,
	"lectureId" int4 NULL,
	"courseId" int4 NULL,
	contents text NOT NULL,
	"userId" int4 DEFAULT 1 NOT NULL,
	CONSTRAINT recenzije_pkey PRIMARY KEY ("reviewId"),
	CONSTRAINT review_target_xor CHECK ((("courseId" IS NOT NULL) <> ("lectureId" IS NOT NULL))),
	CONSTRAINT score_range CHECK ((((score)::numeric >= (1)::numeric) AND ((score)::numeric <= (5)::numeric))),
	CONSTRAINT course_fk FOREIGN KEY ("courseId") REFERENCES public.course("courseId") ON DELETE CASCADE,
	CONSTRAINT lecture_fk FOREIGN KEY ("lectureId") REFERENCES public.lecture("lectureId") ON DELETE CASCADE,
	CONSTRAINT recenzije_idslikekoment_fkey FOREIGN KEY ("commentPhotoId") REFERENCES public.material(idmateriala) ON DELETE SET NULL,
	CONSTRAINT user_fk FOREIGN KEY ("userId") REFERENCES public."user"("userId") ON DELETE SET DEFAULT
);


-- public."comment" definition

-- Drop table

-- DROP TABLE public."comment";

CREATE TABLE public."comment" (
	"commentId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	contents text NOT NULL,
	"parentCommentId" int4 NULL,
	"userId" int4 DEFAULT 1 NOT NULL,
	"lectureId" int4 NULL,
	CONSTRAINT komentari_pkey PRIMARY KEY ("commentId"),
	CONSTRAINT komentari_idlekc_fkey FOREIGN KEY ("lectureId") REFERENCES public.lecture("lectureId") ON DELETE CASCADE,
	CONSTRAINT komentari_idparentkoment_fkey FOREIGN KEY ("parentCommentId") REFERENCES public."comment"("commentId") ON DELETE CASCADE,
	CONSTRAINT komentari_korisnik_id_fkey FOREIGN KEY ("userId") REFERENCES public."user"("userId") ON DELETE SET DEFAULT
);


-- public."enrolledLecture" definition

-- Drop table

-- DROP TABLE public."enrolledLecture";

CREATE TABLE public."enrolledLecture" (
	"completionPercentage" int4 NOT NULL,
	"lectureId" int4 NOT NULL,
	"userId" int4 NOT NULL,
	CONSTRAINT upislekcija_pkey PRIMARY KEY ("lectureId", "userId"),
	CONSTRAINT upislekcija_idlekc_fkey FOREIGN KEY ("lectureId") REFERENCES public.lecture("lectureId") ON DELETE CASCADE,
	CONSTRAINT upislekcija_idpolaz_fkey FOREIGN KEY ("userId") REFERENCES public."user"("userId") ON DELETE CASCADE
);
