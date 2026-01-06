import { ProfilePage } from "~/pages/profile/profile";
import type { Route } from "./+types/profile";
import { redirect } from "react-router";
import { GetJwtToken } from "~/util/cookie";

export async function loader({ request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request)
  if (!jwt) {
    return redirect("/login");
  }

  try {
    const tagsPreferred = await fetch("http://localhost:8890/user/tag?preferred=true", {
      method: "GET",
      headers: {
        "Authorization": "Bearer " + jwt
      }
    });


    const tagsNotPreferred = await fetch("http://localhost:8890/user/tag?preferred=false", {
      method: "GET",
      headers: {
        "Authorization": "Bearer " + jwt
      }
    });

    // Check if responses are OK
    if (!tagsPreferred.ok || !tagsNotPreferred.ok) {
      throw new Error("Failed to fetch tags");
    }

    const preferred = await tagsPreferred.json();
    const notpreferred = await tagsNotPreferred.json();

    return {
      preferred,
      notpreferred
    };
  } catch (error: any) {
    throw new Error(error.message);
  }
}

export async function action({ request }: Route.ActionArgs) {
  const formData = await request.formData();

  const jwt = GetJwtToken(request);
  if (!jwt) {
    return redirect("/login");
  }

  const preferred = request.url?.includes("preferred")

  try {
    const resp = await fetch("http://localhost:8890/user/tag", {
      method: "POST",
      headers: {
        "content-type": "application/json",
        "Authorization": "Bearer " + jwt
      },
      body: JSON.stringify({
        name: formData.get("name"),
        category: "general",
        preferred: preferred
      })
    });

    if (!resp.ok) {
      throw new Error("Failed to add tag");
    }

    return redirect("/profile");
  } catch (error) {
    console.error("Action error:", error);

    return redirect("/profile?error=failed_to_add_tag");
  }
}

export default function ProfileRoute() {
  return <ProfilePage />;
}