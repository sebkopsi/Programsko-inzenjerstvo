ALTER TABLE public.lecture ALTER COLUMN "difficultyId" DROP NOT NULL;
ALTER TABLE public.lecture ALTER COLUMN "videoId" DROP NOT NULL;
ALTER TABLE public.lecture ALTER COLUMN "moduleId" SET NOT NULL;
ALTER TABLE public.lecture ADD quizjson json DEFAULT '{"questions":[]}' NOT NULL;
ALTER TABLE public."enrolledLecture" ADD quizanswers json DEFAULT '{}' NOT NULL;
ALTER TABLE public.lecture ADD minscore int DEFAULT 50 NULL;
