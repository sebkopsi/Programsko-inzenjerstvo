import { ProfilePage } from "~/pages/profile/profile";
import type { Route } from "./+types/profile";
import { redirect } from "react-router";

export async function loader({ request }: Route.LoaderArgs) {
  const cookieHeader = request.headers.get("Cookie");
  const cookies: Record<string, string> = {};

  cookieHeader?.split(";").forEach((cookie) => {
    const [name, value] = cookie.trim().split("=");
    if (name && value) {
      cookies[name] = decodeURIComponent(value);
    }
  });

  // Check if JWT exists
  const jwt = cookies["jwt"];
  if (!jwt) {
    return redirect("/login");
  }

  try {
    const tagsPreferred = await fetch("http://localhost:8890/user/tag/preferred", {
      method: "GET",
      headers: {
        "Authorization": "Bearer " + jwt
      }
    });

    const tagsNotPreferred = await fetch("http://localhost:8890/user/tag/notpreferred", {
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
  } catch (error) {
    console.error("Loader error:", error);
    // You might want to handle this differently based on your needs
    return {
      preferred: [],
      notpreferred: []
    };
  }
}

export async function action({ request }: Route.ActionArgs) {
  const formData = await request.formData();
  const cookieHeader = request.headers.get("Cookie");
  const cookies: Record<string, string> = {};

  cookieHeader?.split(";").forEach((cookie) => {
    const [name, value] = cookie.trim().split("=");
    if (name && value) {
      cookies[name] = decodeURIComponent(value);
    }
  });

  const jwt = cookies["jwt"];
  if (!jwt) {
    return redirect("/login");
  }

  try {
    const resp = await fetch("http://localhost:8890/user/tag", {
      method: "POST",
      headers: {
        "content-type": "application/json",
        "Authorization": "Bearer " + jwt
      },
      body: JSON.stringify({
        name: formData.get("name"),
        category: "general", // Fixed typo: was "categroy"
        preferred: true
      })
    });

    if (!resp.ok) {
      throw new Error("Failed to add tag");
    }

    return redirect("/profile");
  } catch (error) {
    console.error("Action error:", error);
    // You might want to return an error message instead
    return redirect("/profile?error=failed_to_add_tag");
  }
}

// Make sure this is the default export, not named export
export default function ProfileRoute() {
  return <ProfilePage />;
}