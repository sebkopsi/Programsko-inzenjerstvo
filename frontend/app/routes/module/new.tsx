import type { Route } from "../+types/module";
import { GetJwtToken } from "~/util/cookie";
import { redirect } from "react-router";
import { NewModulePage } from "~/pages/module/newModule";

export async function action({ request, params }: Route.ActionArgs) {
  const formData = await request.formData();

  const jwt = GetJwtToken(request);
  if (!jwt) {
    return redirect("/login");
  }

 
 
  try {
    const resp = await fetch(`http://localhost:8890/course/${params.courseId}/module`, {
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

    if (!resp.ok) {
      const text = await resp.text();
      throw new Error("failed to create new module");
    }

    return redirect(`/course/${params.courseId}`);

    
  } catch (error: any) {
    throw new Error(error)
  }
}

export default function NewModule() {
  return <NewModulePage />
}

