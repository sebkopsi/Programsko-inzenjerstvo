-- Start
SET search_path TO public;

INSERT INTO public."user" ("userId", "firstname", "surname", "password_hash", "email", "isVerified", "createdAt")
OVERRIDING SYSTEM VALUE
VALUES (1, 'Deleted', 'User', '', 'deleted@system.local', false, NOW())
ON CONFLICT ("userId") DO NOTHING;

-- ========== ON DELETE CASES ==========

-- ========== TABLE: material ==========
-- typeId → materialType.typeId (RESTRICT)	

-- ========== TABLE: enrolleeProfile ==========
-- skillLevelId → difficultyLevel.difficultyId (RESTRICT)

-- ========== TABLE: user ==========
-- instructorId → instructorProfile.instructorId (SET NULL)
-- enrolleeId → enrolleeProfile.enrolleeId (RESTRICT)

-- ========== TABLE: course ==========
-- difficultyId → difficultyLevel.difficultyId (RESTRICT)
-- creatorId → user.userId (SET DEFAULT 1)

-- ========== TABLE: collaboration ==========
-- userId → user.userId (CASCADE)
-- courseId → course.courseId (CASCADE)

-- ========== TABLE: enrolledCourse ==========
-- certificateId → material.idmateriala (SET NULL)
-- userId → user.userId (CASCADE)
-- courseId → course.courseId (CASCADE)

-- ========== TABLE: module ==========
-- courseId → course.courseId (CASCADE)

-- ========== TABLE: lecture ==========
-- creatorId → user.userId (SET DEFAULT 1)
-- moduleId → module.moduleId (CASCADE)
-- difficultyId → difficultyLevel.difficultyId (RESTRICT)
-- videoId → material.idmateriala (RESTRICT)

-- ========== TABLE: lectureIngredient ==========
-- lectureId → lecture.lectureId (CASCADE)
-- ingredientId → ingredient.ingredientId (RESTRICT)

-- ========== TABLE: enrolledLecture ==========
-- lectureId → lecture.lectureId (CASCADE)
-- userId → user.userId (CASCADE)

-- ========== TABLE: comment ==========
-- lectureId → lecture.lectureId (CASCADE)
-- parentCommentId → comment.commentId (CASCADE)
-- userId → user.userId (SET DEFAULT 1)

-- ========== TABLE: qanda ==========
-- lectureId → lecture.lectureId (CASCADE)
-- parentqandaId → qanda.qandaId (CASCADE)
-- userId → user.userId (SET DEFAULT 1)

-- ========== TABLE: review ==========
-- courseId → course.courseId (CASCADE)
-- lectureId → lecture.lectureId (CASCADE)
-- commentPhotoId → material.idmateriala (SET NULL)
-- userId → user.userId (SET DEFAULT 1)


-- ========== =============== THE CODE =============== ==========

-- ========== TABLE: material ==========
-- typeId → materialType.typeId (RESTRICT)
ALTER TABLE public.material
    DROP CONSTRAINT materijali_idtipa_fkey,
    ADD CONSTRAINT materijali_idtipa_fkey
        FOREIGN KEY ("typeId")
        REFERENCES public."materialType"("typeId")
        ON DELETE RESTRICT;

-- ========== TABLE: enrolleeProfile ==========
-- skillLevelId → difficultyLevel.difficultyId (RESTRICT)
ALTER TABLE public."enrolleeProfile"
    DROP CONSTRAINT polaznikprofil_idrazinevjestine_fkey,
    ADD CONSTRAINT polaznikprofil_idrazinevjestine_fkey
        FOREIGN KEY ("skillLevelId")
        REFERENCES public."difficultyLevel"("difficultyId")
        ON DELETE RESTRICT;

-- ========== TABLE: user ==========
-- instructorId → instructorProfile.instructorId (SET NULL)
ALTER TABLE public."user"
    DROP CONSTRAINT korisnik_idinstr_fkey,
    ADD CONSTRAINT korisnik_idinstr_fkey
        FOREIGN KEY ("instructorId")
        REFERENCES public."instructorProfile"("instructorId")
        ON DELETE SET NULL;
-- enrolleeId → enrolleeProfile.enrolleeId (RESTRICT)
ALTER TABLE public."user"
    DROP CONSTRAINT korisnik_idpolaz_fkey,
    ADD CONSTRAINT korisnik_idpolaz_fkey
        FOREIGN KEY ("enrolleeId")
        REFERENCES public."enrolleeProfile"("enrolleeId")
        ON DELETE RESTRICT;

-- ========== TABLE: course ==========
-- difficultyId → difficultyLevel.difficultyId (RESTRICT)
ALTER TABLE public."course"
    DROP CONSTRAINT "course_difficultyId_fkey",
    ADD CONSTRAINT "course_difficultyId_fkey"
        FOREIGN KEY ("difficultyId")
        REFERENCES public."difficultyLevel"("difficultyId")
        ON DELETE RESTRICT;
-- creatorId → user.userId (SET DEFAULT 1)
ALTER TABLE public.course
    ALTER COLUMN "creatorId" SET DEFAULT 1;

ALTER TABLE public."course"
    DROP CONSTRAINT tecaj_idkreator_fkey,
    ADD CONSTRAINT tecaj_idkreator_fkey
        FOREIGN KEY ("creatorId")
        REFERENCES public."user"("userId")
        ON DELETE SET DEFAULT;

-- ========== TABLE: collaboration ==========
-- userId → user.userId (CASCADE)
ALTER TABLE public.collaboration
    DROP CONSTRAINT pravakolaboracije_idkoris_fkey,
    ADD CONSTRAINT pravakolaboracije_idkoris_fkey
        FOREIGN KEY ("userId")
        REFERENCES public."user"("userId")
        ON DELETE CASCADE;
-- courseId → course.courseId (CASCADE)
ALTER TABLE public.collaboration
    DROP CONSTRAINT pravakolaboracije_idtecaj_fkey,
    ADD CONSTRAINT pravakolaboracije_idtecaj_fkey
        FOREIGN KEY ("courseId")
        REFERENCES public."course"("courseId")
        ON DELETE CASCADE;

-- ========== TABLE: enrolledCourse ==========
-- certificateId → material.idmateriala (SET NULL)
ALTER TABLE public."enrolledCourse"
    DROP CONSTRAINT upistecaj_idcertifikata_fkey,
    ADD CONSTRAINT upistecaj_idcertifikata_fkey
        FOREIGN KEY ("certificateId")
        REFERENCES public."material"("idmateriala")
        ON DELETE SET NULL;
-- userId → user.userId (CASCADE)
ALTER TABLE public."enrolledCourse"
    DROP CONSTRAINT upistecaj_idpolaz_fkey,
    ADD CONSTRAINT upistecaj_idpolaz_fkey
        FOREIGN KEY ("userId")
        REFERENCES public."user"("userId")
        ON DELETE CASCADE;
-- courseId → course.courseId (CASCADE)
ALTER TABLE public."enrolledCourse"
    DROP CONSTRAINT upistecaj_idtecaj_fkey,
    ADD CONSTRAINT upistecaj_idtecaj_fkey
        FOREIGN KEY ("courseId")
        REFERENCES public."course"("courseId")
        ON DELETE CASCADE;

-- ========== TABLE: module ==========
-- courseId → course.courseId (CASCADE)
ALTER TABLE public."module"
    DROP CONSTRAINT model_idtecaj_fkey,
    ADD CONSTRAINT model_idtecaj_fkey
        FOREIGN KEY ("courseId")
        REFERENCES public."course"("courseId")
        ON DELETE CASCADE;

-- ========== TABLE: lecture ==========
-- creatorId → user.userId (SET DEFAULT 1)
ALTER TABLE public.lecture
    ALTER COLUMN "creatorId" SET DEFAULT 1;

ALTER TABLE public."lecture"
    DROP CONSTRAINT lekcija_idinstr_fkey,
    ADD CONSTRAINT lekcija_idinstr_fkey
        FOREIGN KEY ("creatorId")
        REFERENCES public."user"("userId")
        ON DELETE SET DEFAULT;
-- moduleId → module.moduleId (CASCADE)
ALTER TABLE public."lecture"
    DROP CONSTRAINT lekcija_idmodel_fkey,
    ADD CONSTRAINT lekcija_idmodel_fkey
        FOREIGN KEY ("moduleId")
        REFERENCES public."module"("moduleId")
        ON DELETE CASCADE;
-- difficultyId → difficultyLevel.difficultyId (RESTRICT)
ALTER TABLE public."lecture"
    DROP CONSTRAINT lekcija_idrazinetezine_fkey,
    ADD CONSTRAINT lekcija_idrazinetezine_fkey
        FOREIGN KEY ("difficultyId")
        REFERENCES public."difficultyLevel"("difficultyId")
        ON DELETE RESTRICT;
-- videoId → material.idmateriala (RESTRICT)
ALTER TABLE public."lecture"
    DROP CONSTRAINT lekcija_idvideo_fkey,
    ADD CONSTRAINT lekcija_idvideo_fkey
        FOREIGN KEY ("videoId")
        REFERENCES public."material"("idmateriala")
        ON DELETE RESTRICT;

-- ========== TABLE: lectureIngredient ==========
-- lectureId → lecture.lectureId (CASCADE)
ALTER TABLE public."lectureIngredient"
    DROP CONSTRAINT sastojcilekcija_idlekc_fkey,
    ADD CONSTRAINT sastojcilekcija_idlekc_fkey
        FOREIGN KEY ("lectureId")
        REFERENCES public."lecture"("lectureId")
        ON DELETE CASCADE;
-- ingredientId → ingredient.ingredientId (RESTRICT)
ALTER TABLE public."lectureIngredient"
    DROP CONSTRAINT sastojcilekcija_idsast_fkey,
    ADD CONSTRAINT sastojcilekcija_idsast_fkey
        FOREIGN KEY ("ingredientId")
        REFERENCES public."ingredient"("ingredientId")
        ON DELETE RESTRICT;

-- ========== TABLE: enrolledLecture ==========
-- lectureId → lecture.lectureId (CASCADE)
ALTER TABLE public."enrolledLecture"
    DROP CONSTRAINT upislekcija_idlekc_fkey,
    ADD CONSTRAINT upislekcija_idlekc_fkey
        FOREIGN KEY ("lectureId")
        REFERENCES public."lecture"("lectureId")
        ON DELETE CASCADE;
-- userId → user.userId (CASCADE)
ALTER TABLE public."enrolledLecture"
    DROP CONSTRAINT upislekcija_idpolaz_fkey,
    ADD CONSTRAINT upislekcija_idpolaz_fkey
        FOREIGN KEY ("userId")
        REFERENCES public."user"("userId")
        ON DELETE CASCADE;

-- ========== TABLE: comment ==========
-- lectureId → lecture.lectureId (CASCADE)
ALTER TABLE public."comment"
    DROP CONSTRAINT komentari_idlekc_fkey,
    ADD CONSTRAINT komentari_idlekc_fkey
        FOREIGN KEY ("lectureId")
        REFERENCES public."lecture"("lectureId")
        ON DELETE CASCADE;
-- parentCommentId → comment.commentId (CASCADE)
ALTER TABLE public.comment
    DROP CONSTRAINT komentari_idparentkoment_fkey,
    ADD CONSTRAINT komentari_idparentkoment_fkey
        FOREIGN KEY ("parentCommentId")
        REFERENCES public.comment("commentId")
        ON DELETE CASCADE;
-- userId → user.userId (SET DEFAULT 1)
ALTER TABLE public.comment
    ALTER COLUMN "userId" SET DEFAULT 1;

ALTER TABLE public."comment"
    DROP CONSTRAINT komentari_korisnik_id_fkey,
    ADD CONSTRAINT komentari_korisnik_id_fkey
        FOREIGN KEY ("userId")
        REFERENCES public."user"("userId")
        ON DELETE SET DEFAULT;

-- ========== TABLE: qanda ==========
-- lectureId → lecture.lectureId (CASCADE)
ALTER TABLE public."qanda"
    DROP CONSTRAINT qanda_idlekc_fkey,
    ADD CONSTRAINT qanda_idlekc_fkey
        FOREIGN KEY ("lectureId")
        REFERENCES public."lecture"("lectureId")
        ON DELETE CASCADE;
-- parentqandaId → qanda.qandaId (CASCADE)
ALTER TABLE public."qanda"
    DROP CONSTRAINT qanda_idparentqanda_fkey,
    ADD CONSTRAINT qanda_idparentqanda_fkey
        FOREIGN KEY ("parentqandaId")
        REFERENCES public."qanda"("qandaId")
        ON DELETE CASCADE;
-- userId → user.userId (SET DEFAULT 1)
ALTER TABLE public.qanda
    ALTER COLUMN "userId" SET DEFAULT 1;

ALTER TABLE public."qanda"
    DROP CONSTRAINT qanda_korisnik_id_fkey,
    ADD CONSTRAINT qanda_korisnik_id_fkey
        FOREIGN KEY ("userId")
        REFERENCES public."user"("userId")
        ON DELETE SET DEFAULT;

-- ========== TABLE: review ==========
-- courseId → course.courseId (CASCADE)
ALTER TABLE public."review"
    DROP CONSTRAINT course_fk,
    ADD CONSTRAINT course_fk
        FOREIGN KEY ("courseId")
        REFERENCES public."course"("courseId")
        ON DELETE CASCADE;
-- lectureId → lecture.lectureId (CASCADE)
ALTER TABLE public."review"
    DROP CONSTRAINT lecture_fk,
    ADD CONSTRAINT lecture_fk
        FOREIGN KEY ("lectureId")
        REFERENCES public."lecture"("lectureId")
        ON DELETE CASCADE;
-- commentPhotoId → material.idmateriala (SET NULL)
ALTER TABLE public."review"
    DROP CONSTRAINT recenzije_idslikekoment_fkey,
    ADD CONSTRAINT recenzije_idslikekoment_fkey
        FOREIGN KEY ("commentPhotoId")
        REFERENCES public."material"("idmateriala")
        ON DELETE SET NULL;
-- userId → user.userId (SET DEFAULT 1)
ALTER TABLE public.review
    ALTER COLUMN "userId" SET DEFAULT 1;

ALTER TABLE public."review"
    DROP CONSTRAINT user_fk,
    ADD CONSTRAINT user_fk
        FOREIGN KEY ("userId")
        REFERENCES public."user"("userId")
        ON DELETE SET DEFAULT;


-- END OF SQL
