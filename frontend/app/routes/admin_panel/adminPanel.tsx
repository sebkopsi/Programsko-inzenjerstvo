
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

  console.log("RAW /user/my response:");
console.log(JSON.stringify(user, null, 2));

  console.log("user.isAdmin:", user.isAdmin);
console.log("user.data?.isAdmin:", user.data?.isAdmin);
console.log("user.user?.isAdmin:", user.user?.isAdmin);

 if (user.isAdmin === undefined) {
    return redirect("/course");
  }



  return user;
}

export default function AdminPanel() {
   
  return <AdminPanelContent />;

}

