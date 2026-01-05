
CREATE TABLE public.tag (
	tagId int4 GENERATED ALWAYS AS IDENTITY NOT NULL,
	"name" varchar NOT NULL,
	category varchar NOT NULL,
	CONSTRAINT tag_pk PRIMARY KEY (tagId),
	CONSTRAINT tag_unique UNIQUE (category, name)
);

-- CREATE TABLE public.usertag (
-- 	userId int4 NOT NULL,
-- 	tagId int4 NOT NULL,
-- 	preferred boolean NOT NULL,
-- 	CONSTRAINT usertag_pk PRIMARY KEY (userId,tagid),
-- 	CONSTRAINT usertag_user_fk FOREIGN KEY (userId) REFERENCES public."user"("userId") ON DELETE CASCADE ON UPDATE CASCADE,
-- 	CONSTRAINT usertag_tag_fk FOREIGN KEY (tagId) REFERENCES public.tag(tagId) ON DELETE CASCADE ON UPDATE CASCADE
-- );

