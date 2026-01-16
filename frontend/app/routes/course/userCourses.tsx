import { UserCoursesPage } from "~/pages/course/userCourses";
import type { Route } from "./+types/course";
import { GetJwtToken } from "~/util/cookie";
import { redirect } from "react-router";

export async function loader({ request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request)
  if (!jwt) {
    return redirect("/login");
  }

  const userInfoReq = await fetch("http://localhost:8890/user/my", {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + jwt,
    },
  });


  if (!userInfoReq.ok) {
    throw new Error("Failed to fetch user information");
  }
  const userInfoData = await userInfoReq.json();



  const allCoursesReq = await fetch("http://localhost:8890/course/search", {
    method: "POST",
    headers: {
      "Authorization": "Bearer " + jwt,
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ term: "", scope: "" })
  });

  if (!allCoursesReq.ok) {
    throw new Error("Failed to fetch all courses");
  }

  const allCoursesData = await allCoursesReq.json();


  const myCoursesReq = await fetch("http://localhost:8890/course/search", {
    method: "POST",
    headers: {
      "Authorization": "Bearer " + jwt,
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ term: "", scope: "my" })
  });


  if (!myCoursesReq.ok) {
    throw new Error("Failed to fetch all courses");
  }
  const myCoursesData = await myCoursesReq.json();


  return {
    userInfoData,
    allCoursesData,
    myCoursesData
  }
}


export default function userCourses() {
  return <UserCoursesPage />
}

