import { GetJwtToken } from "~/util/cookie";
import type { Route } from "./+types/lecture";
import { redirect } from "react-router";
import LecturePage from "~/pages/lecture/lecture";

export async function loader({ params, request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request)
  if (!jwt) {
    return redirect("/login");
  }

  try{
    const courseReq = await fetch(`http://localhost:8890/course/${params['courseId']}`, {
      method: "GET",
      headers: {
        "Authorization": "Bearer " + jwt,
      }
    });

    if(courseReq.status === 401) {
      return redirect("/login");
    }

    if (!courseReq.ok) {
      return {
        courseInfo: null,
        moduleInfo: null,
        lectureInfo: null,
        errorObject: { message: "Failed to fetch courses" }
      };
    }

    const courseInfo = await courseReq.json();

    if (!courseInfo.success) {
      return {
        courseInfo: null,
        moduleInfo: null,
        lectureInfo: null,
        errorObject: { message: "No course found :(" }
      };
    }

    const moduleReq = await fetch(`http://localhost:8890/course/${params['courseId']}/module/${params['moduleId']}`, {
      method: "GET",
      headers: {
        "Authorization": "Bearer " + jwt,
      }
    });

    if(moduleReq.status === 401) {
      return redirect("/login");
    }

    if (!moduleReq.ok) {
      return {
        courseInfo,
        moduleInfo: null,
        lectureInfo: null,
        errorObject: { message: "Failed to fetch modules" }
      };
    }

    const moduleInfo = await moduleReq.json();

    if (!moduleInfo.success) {
      return {
        courseInfo,
        moduleInfo: null,
        lectureInfo: null,
        errorObject: { message: "No module found :(" }
      };
    }


    const lectureReq = await fetch(`http://localhost:8890/course/${params['courseId']}/module/${params['moduleId']}/lecture/${params['lectureId']}`, {
      method: "GET",
      headers: {
        "Authorization": "Bearer " + jwt,
      }
    });

    if(lectureReq.status === 401) {
      return redirect("/login");
    }

    if (!lectureReq.ok) {
      return {
        courseInfo,
        moduleInfo,
        lectureInfo: null,
        errorObject: { message: "Failed to fetch lecture" }
      };
    }

    const lectureInfo = await lectureReq.json();

    if (!lectureInfo.success) {
      return {
        courseInfo,
        moduleInfo,
        lectureInfo: null,
        errorObject: { message: "No lecture found :(" }
      };
    }

    return {
      courseInfo,
      moduleInfo,
      lectureInfo,
      errorObject: null
    };
  } catch (error) {
    throw new Error("Lecture error: " + error);
  }
}

export async function action({params, request }: Route.ActionArgs) {
  const formData = await request.formData();

  const jwt = GetJwtToken(request);
  if (!jwt) {
    return redirect("/login");
  }


  console.debug(formData)

    const resp = await fetch(`http://localhost:8890/course/${params['courseId']}/module/${params.moduleId}/lecture/${params.lectureId}/submit`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
        "Authorization": "Bearer " + jwt
      },
      body: JSON.stringify({

      })
    });

    if(resp.status === 401) {
      return redirect("/login");
    }

    if(!resp.ok) {
      const respText = await resp.text();
      return {
        ok: false,
        errorObject: { message: "Lecture error: " + respText }
      };
    }
    
}


export default function userCourses() {
  return <LecturePage />
}
