import { redirect } from "react-router";
import type { Route } from "../+types/home";
import AdminPanelContent from "~/pages/admin_panel/adminInbox";
import { GetJwtToken } from "~/util/cookie";


export function meta({}: Route.MetaArgs) {
  return [
    { title: "Admin Inbox - Cooking Flamingoz" },
    { name: "description", content: "admin dashboard" },
  ];
}

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
  
  
   if (!user.isAdmin) {
      return redirect("/");
    }
  
  
  
    return user;
  }


export default function AdminInbox() {
  return <AdminInboxContent />;
}
