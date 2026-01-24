import { redirect, useLoaderData } from "react-router";
import UserProfile from "~/pages/profile/userProfile";
import { GetJwtToken } from "~/util/cookie";

interface ProfileResponse {
  success: boolean;
  message: string;
  data: UserData;
}

export interface Tag {
  name: string;
  category: string;
  preferred: boolean;
}

export interface EnrolledCourse {
  courseId: number;
  userId: number;
  completionPercentage: number;
  certificateId: number | null;
  enrolledAt: string;
  status: string;
  endedAt: string | null;
}

export interface UserData {
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
}

export async function profileLoader({ request }: { request: Request }) {
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

  const profile = await res.json();
  console.log("Profile response from backend:", profile);

  return profile.data;
}

export default function ProfileRoute() {
  const user = useLoaderData<UserData>();
  return <UserProfile user={user} />;
}
