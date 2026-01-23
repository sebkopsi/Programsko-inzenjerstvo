import type { Route } from "../+types/module";
import { GetJwtToken } from "~/util/cookie";
import { redirect } from "react-router";
import { NewModulePage } from "~/pages/module/newModule";

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

    if(resp.status === 401) {
      return redirect("/login");
    }

    if(!resp.ok) {
      const text = await resp.text();
      return {
        errorObject: { message: "Failed to create module: " + text },
      };
    }

    const data = await resp.json();
    if (!data.success) {
      return {
        errorObject: { message: "Module creation failed" },
      };
    }

    return redirect(`/course/${data.data.courseId}`);

    
  } catch (error) {
    throw new Error("Module creation error: " + error);
  }
}

export default function NewModule() {
  return <NewModulePage />
}
