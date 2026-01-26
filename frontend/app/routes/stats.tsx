import { StatsPage } from "~/pages/stats/stats";
import { redirect } from "react-router";
import { GetJwtToken } from "~/util/cookie";
import type { Route } from "./+types/request";

export async function loader( { request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request);
  if (!jwt)
    return redirect("/login");

  const userRes = await fetch("http://localhost:8890/user/my", {
    headers: { "Authorization": "Bearer " + jwt },
  });
  if (!userRes.ok) throw new Error("Failed to fetch user info");
  const user = await userRes.json();
  if (!user.isAdmin) {
    return redirect("/");
  }

  const statsRes = await fetch(`http://localhost:8890/admin/statistics`, {
    headers: {
      "Authorization": "Bearer " + jwt,
    }
  });
  if (!statsRes.ok)
    throw new Error("Failed to fetch stats");
  const stats = await statsRes.json();
  if (!stats.success)
    throw new Error("No stats found");

  return { stats };
}

export default function Stats() {
  return <StatsPage />
}