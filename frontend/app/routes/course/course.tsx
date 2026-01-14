import { redirect } from "react-router";
import { GetJwtToken } from "~/util/cookie";
import type { Route } from "./+types/course";
import { CoursePage } from "~/pages/course/course";

export async function loader({ params, request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request)
  if (!jwt) {
    return redirect("/login");
  }

  console.log(params)
  
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
  
  if(!courseInfo.success){
    throw new Error("No course found :(")
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
    throw new Error("Failed to fetch modules");
  }

  const modulesData = await modulesReq.json();

  return {
    courseInfo,
    modulesData
  }
}


export default function Course() {
  return <CoursePage/>
}

