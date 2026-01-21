import { redirect } from "react-router";
import type { Route } from "../+types/home";
import AdminPanelContent from "~/pages/admin_panel/adminInbox";
import AdminInboxContent from "~/pages/admin_panel/adminInbox";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Admin Inbox - Cooking Flamingoz" },
    { name: "description", content: "admin dashboard" },
  ];
}

// export async function loader({ request }: Route.LoaderArgs) {
//   try {
//     const res = await fetch("http://localhost:8890/user/me", {
//       headers: {
//         cookie: request.headers.get("cookie") || "",
//       },
//     });

//     if (!res.ok) {
//       return redirect("/");
//     }

//     const user = await res.json();

//     if (!user?.isAdmin) {
//       return redirect("/"); 
//     }
// }

export default function AdminInbox() {
  return <AdminInboxContent />;
}
