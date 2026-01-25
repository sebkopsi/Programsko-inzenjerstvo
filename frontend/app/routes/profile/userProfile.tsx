import { redirect, useLoaderData } from "react-router";
import type { Route } from "../+types/home";
import UserProfile from "~/pages/profile/userProfile";
import { GetJwtToken } from "~/util/cookie";

export type Tag = {
  name: string;
  category: string;
  preferred: boolean;
};

export type EnrolledCourse = {
  courseId: number;
  userId: number;
  completionPercentage: number;
  certificateId: number | null;
  enrolledAt: string;
  status: string;
  endedAt: string | null;
};

export type UserData = {
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

  if (!jwt) {
    return redirect("/login");
  }

  const res = await fetch("http://localhost:8890/profile/my", {
    headers: {
      "Authorization": `Bearer ${jwt}`,
      "Content-Type": "application/json",
    },
  });

  if (!res.ok) {
    throw new Error("Failed to fetch profile");
  }

  const profile: ProfileResponse = await res.json();  
  return {
    user: profile.data,
    jwt,
  };
}

export default function ProfileRoute() {
  const data = useLoaderData<typeof loader>();
  return <UserProfile user={data.user} jwt={data.jwt} />;
}
