CREATE TABLE public."courseTag" (
	"tagId" int4 NOT NULL,
	"courseId" int4 NOT NULL,
	CONSTRAINT coursetag_tag_fk FOREIGN KEY ("tagId") REFERENCES public.tag("tagId"),
	CONSTRAINT coursetag_course_fk FOREIGN KEY ("courseId") REFERENCES public.course("courseId")
);
