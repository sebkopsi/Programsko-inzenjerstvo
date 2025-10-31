-- Start
SET search_path TO public;
CREATE SCHEMA IF NOT EXISTS public;

-- REVOKE ALL ON SCHEMA public FROM PUBLIC;
-- GRANT USAGE, CREATE ON SCHEMA public TO postgres;

-- NO DELETE POLICIES YET SET
-- setup "tombstone user"

--instructorProfile
--materialType
--material
--ingredient
--difficultyLevel
--enrolleeProfile
--user
--course
--collaboration
--enrolledCourse
--module
--lecture
--lectureIngredient
--enrolledLecture
--comment
--qanda
--review

BEGIN;

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



-- public.material definition

-- Drop table

-- DROP TABLE public.material;

CREATE TABLE public.material (
	idmateriala int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	contents text NOT NULL,
	"typeId" int4 NOT NULL,
	CONSTRAINT materijali_pkey PRIMARY KEY (idmateriala)
);

-- public.material foreign keys

ALTER TABLE public.material ADD CONSTRAINT materijali_idtipa_fkey FOREIGN KEY ("typeId") REFERENCES public."materialType"("typeId");



-- public.ingredient definition

-- Drop table

-- DROP TABLE public.ingredient;

CREATE TABLE public.ingredient (
	"ingredientId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" text NOT NULL,
	CONSTRAINT sastojci_pkey PRIMARY KEY ("ingredientId"),
	CONSTRAINT unique_ingredient_name UNIQUE (name)
);



-- public."difficultyLevel" definition

-- Drop table

-- DROP TABLE public."difficultyLevel";

CREATE TABLE public."difficultyLevel" (
	"difficultyId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" text NOT NULL,
	CONSTRAINT razinatezine_pkey PRIMARY KEY ("difficultyId"),
	CONSTRAINT unique_difficulty_name UNIQUE (name)
);



-- public."enrolleeProfile" definition

-- Drop table

-- DROP TABLE public."enrolleeProfile";

CREATE TABLE public."enrolleeProfile" (
	"enrolleeId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	username text NOT NULL,
	"skillLevelId" int4 NOT NULL,
	CONSTRAINT polaznikprofil_pkey PRIMARY KEY ("enrolleeId")
);

-- public."enrolleeProfile" foreign keys

ALTER TABLE public."enrolleeProfile" ADD CONSTRAINT polaznikprofil_idrazinevjestine_fkey FOREIGN KEY ("skillLevelId") REFERENCES public."difficultyLevel"("difficultyId");



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
	CONSTRAINT korisnik_pkey PRIMARY KEY ("userId")
);

-- public."user" foreign keys

ALTER TABLE public."user" ADD CONSTRAINT korisnik_idinstr_fkey FOREIGN KEY ("instructorId") REFERENCES public."instructorProfile"("instructorId");
ALTER TABLE public."user" ADD CONSTRAINT korisnik_idpolaz_fkey FOREIGN KEY ("enrolleeId") REFERENCES public."enrolleeProfile"("enrolleeId");



-- public.course definition

-- Drop table

-- DROP TABLE public.course;

CREATE TABLE public.course (
	"courseId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" text NOT NULL,
	"creatorId" int4 NOT NULL,
	description text NULL,
	"difficultyId" int4 NULL,
	"avgRating" numeric(3, 2) DEFAULT 0 NULL,
	CONSTRAINT tecaj_pkey PRIMARY KEY ("courseId")
);

-- public.course foreign keys

ALTER TABLE public.course ADD CONSTRAINT "course_difficultyId_fkey" FOREIGN KEY ("difficultyId") REFERENCES public."difficultyLevel"("difficultyId");
ALTER TABLE public.course ADD CONSTRAINT tecaj_idkreator_fkey FOREIGN KEY ("creatorId") REFERENCES public."user"("userId");



-- public.collaboration definition

-- Drop table

-- DROP TABLE public.collaboration;

CREATE TABLE public.collaboration (
	"courseId" int4 NOT NULL,
	"userId" int4 NOT NULL,
	CONSTRAINT pravakolaboracije_pkey PRIMARY KEY ("courseId", "userId")
);

-- public.collaboration foreign keys

ALTER TABLE public.collaboration ADD CONSTRAINT pravakolaboracije_idkoris_fkey FOREIGN KEY ("userId") REFERENCES public."user"("userId");
ALTER TABLE public.collaboration ADD CONSTRAINT pravakolaboracije_idtecaj_fkey FOREIGN KEY ("courseId") REFERENCES public.course("courseId");



-- public."enrolledCourse" definition

-- Drop table

-- DROP TABLE public."enrolledCourse";

CREATE TABLE public."enrolledCourse" (
	"completionPercentage" int4 NOT NULL,
	"courseId" int4 NOT NULL,
	"certificateId" int4 NULL,
	"userId" int4 NOT NULL,
	CONSTRAINT completion_range CHECK ((("completionPercentage" >= 0) AND ("completionPercentage" <= 100))),
	CONSTRAINT upistecaj_pkey PRIMARY KEY ("courseId", "userId")
);

-- public."enrolledCourse" foreign keys

ALTER TABLE public."enrolledCourse" ADD CONSTRAINT upistecaj_idcertifikata_fkey FOREIGN KEY ("certificateId") REFERENCES public.material(idmateriala);
ALTER TABLE public."enrolledCourse" ADD CONSTRAINT upistecaj_idpolaz_fkey FOREIGN KEY ("userId") REFERENCES public."user"("userId");
ALTER TABLE public."enrolledCourse" ADD CONSTRAINT upistecaj_idtecaj_fkey FOREIGN KEY ("courseId") REFERENCES public.course("courseId");



-- public."module" definition

-- Drop table

-- DROP TABLE public."module";

CREATE TABLE public."module" (
	"moduleId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" text NOT NULL,
	"courseId" int4 NOT NULL,
	orderindex int2 DEFAULT 0 NOT NULL,
	CONSTRAINT model_pkey PRIMARY KEY ("moduleId")
);

-- public."module" foreign keys

ALTER TABLE public."module" ADD CONSTRAINT model_idtecaj_fkey FOREIGN KEY ("courseId") REFERENCES public.course("courseId");



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
	"creatorId" int4 NOT NULL,
	CONSTRAINT lekcija_pkey PRIMARY KEY ("lectureId")
);

-- public.lecture foreign keys

ALTER TABLE public.lecture ADD CONSTRAINT lekcija_idinstr_fkey FOREIGN KEY ("creatorId") REFERENCES public."user"("userId");
ALTER TABLE public.lecture ADD CONSTRAINT lekcija_idmodel_fkey FOREIGN KEY ("moduleId") REFERENCES public."module"("moduleId");
ALTER TABLE public.lecture ADD CONSTRAINT lekcija_idrazinetezine_fkey FOREIGN KEY ("difficultyId") REFERENCES public."difficultyLevel"("difficultyId");
ALTER TABLE public.lecture ADD CONSTRAINT lekcija_idvideo_fkey FOREIGN KEY ("videoId") REFERENCES public.material(idmateriala);



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
	CONSTRAINT sastojcilekcija_pkey PRIMARY KEY ("lectureId", "ingredientId")
);

-- public."lectureIngredient" foreign keys

ALTER TABLE public."lectureIngredient" ADD CONSTRAINT sastojcilekcija_idlekc_fkey FOREIGN KEY ("lectureId") REFERENCES public.lecture("lectureId");
ALTER TABLE public."lectureIngredient" ADD CONSTRAINT sastojcilekcija_idsast_fkey FOREIGN KEY ("ingredientId") REFERENCES public.ingredient("ingredientId");



-- public."enrolledLecture" definition

-- Drop table

-- DROP TABLE public."enrolledLecture";

CREATE TABLE public."enrolledLecture" (
	"completionPercentage" int4 NOT NULL,
	"lectureId" int4 NOT NULL,
	"userId" int4 NOT NULL,
	CONSTRAINT upislekcija_pkey PRIMARY KEY ("lectureId", "userId")
);

-- public."enrolledLecture" foreign keys

ALTER TABLE public."enrolledLecture" ADD CONSTRAINT upislekcija_idlekc_fkey FOREIGN KEY ("lectureId") REFERENCES public.lecture("lectureId");
ALTER TABLE public."enrolledLecture" ADD CONSTRAINT upislekcija_idpolaz_fkey FOREIGN KEY ("userId") REFERENCES public."user"("userId");



-- public."comment" definition

-- Drop table

-- DROP TABLE public."comment";

CREATE TABLE public."comment" (
	"commentId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	contents text NOT NULL,
	"parentCommentId" int4 NULL,
	"userId" int4 NOT NULL,
	"lectureId" int4 NULL,
	CONSTRAINT komentari_pkey PRIMARY KEY ("commentId")
);

-- public."comment" foreign keys

ALTER TABLE public."comment" ADD CONSTRAINT komentari_idlekc_fkey FOREIGN KEY ("lectureId") REFERENCES public.lecture("lectureId");
ALTER TABLE public."comment" ADD CONSTRAINT komentari_idparentkoment_fkey FOREIGN KEY ("parentCommentId") REFERENCES public."comment"("commentId");
ALTER TABLE public."comment" ADD CONSTRAINT komentari_korisnik_id_fkey FOREIGN KEY ("userId") REFERENCES public."user"("userId");



-- public.qanda definition

-- Drop table

-- DROP TABLE public.qanda;

CREATE TABLE public.qanda (
	"qandaId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	contents text NOT NULL,
	"parentqandaId" int4 NULL,
	"lectureId" int4 NOT NULL,
	"userId" int4 NOT NULL,
	CONSTRAINT qanda_pkey PRIMARY KEY ("qandaId")
);

-- public.qanda foreign keys

ALTER TABLE public.qanda ADD CONSTRAINT qanda_idlekc_fkey FOREIGN KEY ("lectureId") REFERENCES public.lecture("lectureId");
ALTER TABLE public.qanda ADD CONSTRAINT qanda_idparentqanda_fkey FOREIGN KEY ("parentqandaId") REFERENCES public.qanda("qandaId");
ALTER TABLE public.qanda ADD CONSTRAINT qanda_korisnik_id_fkey FOREIGN KEY ("userId") REFERENCES public."user"("userId");



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
	"userId" int4 NOT NULL,
	CONSTRAINT recenzije_pkey PRIMARY KEY ("reviewId"),
	CONSTRAINT score_range CHECK ((((score)::numeric >= (1)::numeric) AND ((score)::numeric <= (5)::numeric)))
    CONSTRAINT review_target_xor CHECK (("courseId" IS NOT NULL) <> ("lectureId" IS NOT NULL)
);
);

-- public.review foreign keys

ALTER TABLE public.review ADD CONSTRAINT course_fk FOREIGN KEY ("courseId") REFERENCES public.course("courseId");
ALTER TABLE public.review ADD CONSTRAINT lecture_fk FOREIGN KEY ("lectureId") REFERENCES public.lecture("lectureId");
ALTER TABLE public.review ADD CONSTRAINT recenzije_idslikekoment_fkey FOREIGN KEY ("commentPhotoId") REFERENCES public.material(idmateriala);
ALTER TABLE public.review ADD CONSTRAINT user_fk FOREIGN KEY ("userId") REFERENCES public."user"("userId");



COMMIT;

--instructorProfile
--materialType
--material
--ingredient
--difficultyLevel
--enrolleeProfile
--user
--course
--collaboration
--enrolledCourse
--module
--lecture
--lectureIngredient
--enrolledLecture
--comment
--qanda
--review

