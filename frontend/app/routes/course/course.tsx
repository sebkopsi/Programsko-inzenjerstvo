import { redirect } from "react-router";
import { GetJwtToken } from "~/util/cookie";
import type { Route } from "./+types/course";
import { CoursePage } from "~/pages/course/course";

export async function loader({ params, request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request)
  if (!jwt) {
    return redirect("/login");
  }

  try {
    const courseReq = await fetch(`http://localhost:8890/course/${params['courseId']}`, {
      method: "GET",
      headers: {
        "Authorization": "Bearer " + jwt,
      }
    });

    if (!courseReq.ok) {
      return {
        courseInfo: null,
        modulesData: [],
        errorObject: { message: "Failed to fetch courses" }
      };
    }

    const courseInfo = await courseReq.json();
  
    if(!courseInfo.success){
      return {
        courseInfo: null,
        modulesData: [],
        errorObject: { message: "No course found :(" }
      };
    }

    const modulesReq = await fetch(`http://localhost:8890/course/${params['courseId']}/module/search`, {
      method: "POST",
      headers: {
        "Authorization": "Bearer " + jwt,
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ term: "", scope: "" })
    });

    if (!modulesReq.ok) {
      return {
        courseInfo,
        modulesData: [],
        errorObject: { message: "Failed to fetch modules" }
      };
    }

    const modulesData = await modulesReq.json();

    if (!modulesData.success) {
      return {
        courseInfo,
        modulesData: [],
        errorObject: { message: "Failed to load modules" }
      };
    }

    return {
      courseInfo,
      modulesData,
      errorObject: null
    };

  } catch (error) {
    throw new Error("Fetch module error: " + error);
  }
  
}

export default function Course() {
  return <CoursePage/>
}

