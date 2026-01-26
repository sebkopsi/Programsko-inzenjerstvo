import { redirect } from "react-router";
import { GetJwtToken } from "~/util/cookie";
import type { Route } from "./+types/course";
import { CoursePage } from "~/pages/course/course";

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

type enrollResult = {
  success: boolean;
  message: string;
};

export async function action({ params, request }: Route.ActionArgs) {
  const jwt = GetJwtToken(request);
  if (!jwt) {
    return redirect("/login");
  }

  try {
    const enrollReq = await fetch(`http://localhost:8890/course/${params['courseId']}/enroll`, {
      method: "POST",
      headers: {
        "content-type": "application/json",
        "Authorization": "Bearer " + jwt,
      }
    });

    console.log("Status:", enrollReq.status);
    console.log("JWT Token:", jwt); 

    const data: enrollResult = await enrollReq.json();

    if (enrollReq.status === 403) {
      return {
        success: false,
        message: "Access denied. Please log in again."
      };
    }

    if (!data) {
      return {
        success: false,
        message: `Server returned status ${enrollReq.status} with no response`
      };
    }
    
    if (!data.success) {
      return {
        success: false,
        message: data.message || "Failed to enroll in course"
      };
    }
    
    return data;
    
  } catch (error) {
    console.error("Enrollment error:", error);
    return {
      success: false,
      message: "An error occurred while enrolling"
    };
  }
}

export default function Course() {
  return <CoursePage/>
}

