import { NewInstructorPage } from "~/pages/instructor/new";
import type { Route } from "./+types/new";
import { redirect } from "react-router";
import { GetJwtToken } from "~/util/cookie";

export async function action({ request }: Route.ActionArgs) {
  const formData = await request.formData();

  const jwt = GetJwtToken(request);
  if (!jwt)
    return redirect("/login");

  try {
    var data = {
      title: formData.get('title'),
      description: formData.get('desc')
    }

    const multipartData = new FormData();
    multipartData.append("info", new Blob([JSON.stringify(data)], { type: "application/json" }));
    multipartData.append("pfp", formData.get("pfp") as File);
    multipartData.append("id", formData.get("id") as File);

    const certFiles = formData.getAll("cert") as File[];
    certFiles.forEach(file => {
      multipartData.append("cert", file);
    });

    const req = await fetch("http://localhost:8890/instructor/promotionRequest", {
      method: "POST",
      headers: {
        "Authorization": "Bearer " + jwt
      },
      body: multipartData
    });

    if (!req.ok) {
      throw new Error("Failed to send request");
    }

  } catch (error: any) {
    throw new Error(error);
  }

  return redirect("/");
}

export async function loader({ request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request);
  if (!jwt)
    return redirect("/login");

  const res = await fetch("http://localhost:8890/instructor/promotionRequest", {
    headers: {
      "Authorization": "Bearer " + jwt,
    }
  });
  if (!res.ok)
    throw new Error("Failed to fetch request");
  const reqList = await res.json();
  if (!reqList.success)
    throw new Error("No requests found for user");

  for (const req of reqList.data) {
    if (req.type == "promotionRequest") {
      if (req.status == "pending")
        return redirect("/instructor/pending");
      if (req.status == "approved")
        return redirect("/instructor/profile");
    }
  }

  return;
}

export default function NewInstructor() {
  return <NewInstructorPage />
}