import { redirect, useLoaderData } from "react-router";
import type { Route } from "../+types/home";
import UserProfile from "~/pages/profile/userProfile";
import { GetJwtToken } from "~/util/cookie";

type Tag = {
  name: string;
  category: string;
  preferred: boolean;
};

type EnrolledCourse = {
  courseId: number;
  userId: number;
  completionPercentage: number;
  certificateId: number | null;
  enrolledAt: string;
  status: string;
  endedAt: string | null;
};

type UserData = {
  firstname: string;
  surname: string;
  email: string;
  createdAt: string;
  isAdmin: boolean;
  isModerator: boolean;
  isVerified: boolean;
  username: string;
  skillLevel: string;
  tags: Tag[];
  enrolledCoursesSet: EnrolledCourse[];
};

type ProfileResponse = {
  success: boolean;
  message: string;
  data: UserData | null;
};

export async function loader({ request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request);
  console.log("JWT token from cookie:", jwt);

  if (!jwt) {
    console.log("No JWT found — redirecting to /login");
    return redirect("/login");
  }

  const res = await fetch("http://localhost:8890/profile/my", {
    headers: {
      "Authorization": `Bearer ${jwt}`,
      "Content-Type": "application/json",
    },
  });

  console.log("Fetch response status:", res.status);

  if (!res.ok) {
    const text = await res.text();
    console.log("Fetch failed, response text:", text);
    throw new Error("Failed to fetch profile");
  }

  const profile: ProfileResponse = await res.json();
  console.log("Profile response from backend ssuccess?:", profile.success);
  console.log(JSON.stringify(profile.data, null, 2));

  return {
    requests: profile.data ?? null
  };
}

export default function ProfileRoute() {
  const user = useLoaderData<typeof loader>();
  return <UserProfile user={user.requests} />;
}