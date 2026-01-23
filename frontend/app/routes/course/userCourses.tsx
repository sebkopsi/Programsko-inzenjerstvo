import { UserCoursesPage } from "~/pages/course/userCourses";
import type { Route } from "./+types/course";
import { GetJwtToken } from "~/util/cookie";
import { redirect } from "react-router";

export async function loader({ request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request)
  if (!jwt) {
    return redirect("/login");
  }

  try {
    const userInfoReq = await fetch("http://localhost:8890/user/my", {
      method: "GET",
      headers: {
        "Authorization": "Bearer " + jwt,
      },
    });


    if (!userInfoReq.ok) {
      return {
        userInfoData: [],
        allCoursesData: [],
        myCoursesData: [],
        errorObject: { message: "Failed to fetch user info"}
      };
    }

    const userInfoData = await userInfoReq.json();

    if (!userInfoData.success) {
      return {
        userInfoData: [],
        allCoursesData: [],
        myCoursesData: [],
        errorObject: { message: "Failed to fetch user info"}
      };
    }

    const allCoursesReq = await fetch("http://localhost:8890/course/search", {
      method: "POST",
      headers: {
        "Authorization": "Bearer " + jwt,
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ term: "", scope: "" })
    });

    if (!allCoursesReq.ok) {
      return {
        userInfoData,
        allCoursesData: [],
        myCoursesData: [],
        errorObject: { message: "Failed to fetch all courses"}
      };
    }

    const allCoursesData = await allCoursesReq.json();
    if (!allCoursesData.success) {
      return {
        userInfoData,
        allCoursesData: [],
        myCoursesData: [],
        errorObject: { message: "Failed to fetch all courses"}
      };
    }

    const myCoursesReq = await fetch("http://localhost:8890/course/search", {
      method: "POST",
      headers: {
        "Authorization": "Bearer " + jwt,
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ term: "", scope: "my" })
    });


    if (!myCoursesReq.ok) {
      return {
        userInfoData,
        allCoursesData,
        myCoursesData: [],
        errorObject: { message: "Failed to fetch my courses"}
      };
    }

    const myCoursesData = await myCoursesReq.json();
    if (!myCoursesData.success) {
      return {
        userInfoData,
        allCoursesData,
        myCoursesData: [],
        errorObject: { message: "Failed to fetch my courses"}
      };
    }

    return {
      userInfoData,
      allCoursesData,
      myCoursesData,
      errorObject: null
    };

  } catch (error) {
    throw new Error("Fetch courses error: " + error);
  }
}


export default function userCourses() {
  return <UserCoursesPage />
}
