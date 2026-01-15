import { GetJwtToken } from "~/util/cookie";
import type { Route } from "./+types/lecture";
import { redirect } from "react-router";
import LecturePage from "~/pages/lecture/lecture";

export async function loader({ params, request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request)
  if (!jwt) {
    return redirect("/login");
  }

  const courseReq = await fetch(`http://localhost:8890/course/${params['courseId']}`, {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + jwt,
    }
  });

  if (!courseReq.ok) {
    throw new Error("Failed to fetch courses");
  }

  const courseInfo = await courseReq.json();

  if (!courseInfo.success) {
    throw new Error("No course found :(")
  }

  const moduleReq = await fetch(`http://localhost:8890/course/${params['courseId']}/module/${params['moduleId']}`, {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + jwt,
    }
  });

  if (!moduleReq.ok) {
    throw new Error("Failed to fetch modules");
  }

  const moduleInfo = await moduleReq.json();

  if (!moduleInfo.success) {
    throw new Error("No module found :(")
  }


  const lectureReq = await fetch(`http://localhost:8890/course/${params['courseId']}/module/${params['moduleId']}/lecture/${params['lectureId']}`, {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + jwt,
    }
  });

  if (!lectureReq.ok) {
    throw new Error("Failed to fetch lecture");
  }

  const lectureInfo = await lectureReq.json();

  if (!lectureInfo.success) {
    throw new Error("No module lecture :(")
  }


  return {
    courseInfo,
    moduleInfo,
    lectureInfo
  }
}

export async function action({params, request }: Route.ActionArgs) {
  const formData = await request.formData();

  const jwt = GetJwtToken(request);
  if (!jwt) {
    return redirect("/login");
  }


  console.debug(formData)

  try {
    const resp = await fetch(`http://localhost:8890/course/${params['courseId']}/module/${params.moduleId}/lecture/${params.lectureId}/submit`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
        "Authorization": "Bearer " + jwt
      },
      body: JSON.stringify({

      })
    });

    
  } catch (error: any) {
    throw new Error(error)
  }
}


export default function userCourses() {
  return <LecturePage />
}

