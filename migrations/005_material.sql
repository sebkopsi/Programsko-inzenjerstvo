ALTER TABLE public.material DROP CONSTRAINT materijali_idtipa_fkey;
ALTER TABLE public.material RENAME COLUMN "typeId" TO "type";
ALTER TABLE public.material ALTER COLUMN "type" TYPE varchar USING "type"::varchar;
ALTER TABLE public.material ADD "name" varchar NOT NULL;

