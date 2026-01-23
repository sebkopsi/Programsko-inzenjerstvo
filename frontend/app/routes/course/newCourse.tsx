import { NewCoursePage } from "~/pages/course/forms/newCourse";
import type { Route } from "./+types/course";
import { GetJwtToken } from "~/util/cookie";
import { redirect } from "react-router";

export async function action({ request }: Route.ActionArgs) {
  const formData = await request.formData();

  const jwt = GetJwtToken(request);
  if (!jwt) {
    return redirect("/login");
  }


  try {
    const resp = await fetch("http://localhost:8890/course", {
      method: "POST",
      headers: {
        "content-type": "application/json",
        "Authorization": "Bearer " + jwt
      },
      body: JSON.stringify({
        "name": formData.get("name"),
        "desc": formData.get("desc"),
        "tags": formData.getAll("tags")
      })
    });

    if(!resp.ok) {
      const respText = await resp.text();

      return {
        ok: false,
        errorObject: { message: "Creating course failed: " + respText }
      };
    }

    return redirect("/course");
    
  } catch (error) {
    throw new Error("Create course error: " + error);
  }
}

export default function Newcourse() {
  return <NewCoursePage />
}
