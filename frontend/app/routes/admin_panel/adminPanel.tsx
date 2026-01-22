
import type { Route } from "../+types/home";
import AdminPanelContent from "~/pages/admin_panel/adminPanel";
import { GetJwtToken } from "~/util/cookie";
import { redirect } from "react-router";

export async function loader({ request }: Route.LoaderArgs) {

  const jwt = GetJwtToken(request);
  if (!jwt) return redirect("/login"); 

  const res = await fetch("http://localhost:8890/user/my", {
    headers: { "Authorization": "Bearer " + jwt },
  });

  if (!res.ok) throw new Error("Failed to fetch user info");

  const user = await res.json();

  if (user.isAdmin===false) return redirect("/"); 

  return user;
}

export default function AdminPanel() {
  return <AdminPanelContent />;
}
