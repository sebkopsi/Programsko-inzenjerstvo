import { GetJwtToken } from "~/util/cookie";
import type { Route } from "./+types/module";
import { ModulePage } from "~/pages/module/module";
import { redirect } from "react-router";

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
  
  if(!courseInfo.success){
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
  
  if(!moduleInfo.success){
    throw new Error("No module found :(")
  }

  const lecturesReq = await fetch(`http://localhost:8890/course/${params['courseId']}/module/${params['moduleId']}/lecture/search`, {
    method: "POST",
    headers: {
      "Authorization": "Bearer " + jwt,
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ term: "", moduleId: params['moduleId']})
  });

  if (!lecturesReq.ok) {
    throw new Error("Failed to fetch lectures");
  }

  const lecturesData = await lecturesReq.json();

  return {
    courseInfo,
    moduleInfo,
    lecturesData
  }
}

export default function Module() {
  return <ModulePage/>
}

