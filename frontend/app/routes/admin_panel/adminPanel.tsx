// routes/adminPanel.tsx
import type { Route } from "../+types/home";
import AdminPanelContent from "~/pages/admin_panel/adminPanel"

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Admin Panel - Cooking Flamingoz" },
    { name: "description", content: "admin dashboard" },
  ];
}

export default function AdminPanel() {
  return <AdminPanelContent />;
}
