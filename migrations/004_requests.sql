-- public.request definition

-- Drop table

-- DROP TABLE public.request;

CREATE TABLE public.request (
	"reqId" int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"title" text NOT NULL,
	"content" text NULL,
	"type" text NOT NULL,
	"sentByUserId" int4 NOT NULL,
	"reportedUserId" int4 NULL,
	"targetCourseId" int4 NULL,
	"status" text NOT NULL DEFAULT 'pending',
	"createdAt" timestamp NOT NULL DEFAULT now(),
	CONSTRAINT request_pkey PRIMARY KEY ("reqId"),
	CONSTRAINT "request_SendingUserId_fkey" FOREIGN KEY ("sentByUserId") REFERENCES public."user"("userId") ON DELETE CASCADE,
	CONSTRAINT "request_ReportedUserId_fkey" FOREIGN KEY ("reportedUserId") REFERENCES public."user"("userId") ON DELETE CASCADE,
	CONSTRAINT "request_courseId_fkey" FOREIGN KEY ("targetCourseId") REFERENCES public."course"("courseId") ON DELETE CASCADE,
	CONSTRAINT request_type_check CHECK ("type" IN ('updateCourse','promoteInstructor','report')),
	CONSTRAINT request_status_check CHECK ("status" IN ('pending','approved','rejected')),
	CONSTRAINT request_logic CHECK (
	  ("type" = 'report' AND "reportedUserId" IS NOT NULL AND "targetCourseId" IS NULL)
	  OR
	  ("type" = 'updateCourse' AND "targetCourseId" IS NOT NULL AND "reportedUserId" IS NULL)
	  OR
	  ("type" = 'promoteInstructor' AND "reportedUserId" IS NULL AND "targetCourseId" IS NULL)
	)
);

-- public.requestDocuments definition

-- Drop table

-- DROP TABLE public.requestDocuments;

CREATE TABLE public."requestDocuments" (
	"reqId" int4 NOT NULL,
	"materialId" int4 NOT NULL,
	CONSTRAINT requestDocuments_pkey PRIMARY KEY ("reqId","materialId"),
	CONSTRAINT "requestDocuments_reqId_fkey" FOREIGN KEY ("reqId") REFERENCES public."request"("reqId") ON DELETE CASCADE,
	CONSTRAINT "requestDocuments_materialId_fkey" FOREIGN KEY ("materialId") REFERENCES public."material"("materialId") ON DELETE CASCADE
);