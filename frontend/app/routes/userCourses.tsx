import { UserCoursesPage } from "~/pages/course/userCourses";
import type { Route } from "./+types/course";
import { GetJwtToken } from "~/util/cookie";
import { redirect } from "react-router";

export async function loader({ request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request)
  if (!jwt) {
    return redirect("/login");
  }

  
  const allCoursesReq = await fetch("http://localhost:8890/course/search", {
    method: "POST",
    headers: {
      "Authorization": "Bearer " + jwt,
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ term: "test" })
  });


  // Check if responses are OK
  if (!allCoursesReq.ok) {
    throw new Error("Failed to fetch all courses");
  }

  const allCoursesData = await allCoursesReq.json();


  return {
    allCoursesData
  }
}
export default function userCourses() {
  return <UserCoursesPage />
}

