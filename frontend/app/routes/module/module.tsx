import { GetJwtToken } from "~/util/cookie";
import type { Route } from "./+types/module";
import { ModulePage } from "~/pages/module/module";
import { redirect } from "react-router";

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

    if(courseReq.status === 401) {
      return redirect("/login");
    }
    
    if (!courseReq.ok) {
      return {
        courseInfo: null,
        moduleInfo: null,
        lecturesData: [],
        errorObject: { message: "Failed to fetch course" },
      };
    }

    const courseInfo = await courseReq.json();
    if(!courseInfo.success){
      return {
        courseInfo: null,
        moduleInfo: null,
        lecturesData: [],
        errorObject: { message: "Course not found" },
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
        lecturesData: [],
        errorObject: { message: "Failed to fetch module" },
      };
    }

    const moduleInfo = await moduleReq.json();
    if(!moduleInfo.success){
      return {
        courseInfo,
        moduleInfo: null,
        lecturesData: [],
        errorObject: { message: "Module not found" },
      };
    }

    const lecturesReq = await fetch(`http://localhost:8890/course/${params['courseId']}/module/${params['moduleId']}/lecture/search`, {
      method: "POST",
      headers: {
        "Authorization": "Bearer " + jwt,
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ term: "", moduleId: params['moduleId']})
    });

    if(lecturesReq.status === 401) {
      return redirect("/login");
    }
    
    if (!lecturesReq.ok) {
      return {
        courseInfo,
        moduleInfo,
        lecturesData: [],
        errorObject: { message: "Failed to fetch lectures" },
      };
    }

    const lecturesData = await lecturesReq.json();
    if (!lecturesData.success) {
      return {
        courseInfo,
        moduleInfo,
        lecturesData: [],
        errorObject: { message: "No lectures found" },
      };
    }

    return {
      courseInfo,
      moduleInfo,
      lecturesData,
      errorObject: null,
    }
  } catch (error) {
    throw new Error("Module loader crash: " + error);
  }
}

export default function Module() {
  return <ModulePage/>
}
