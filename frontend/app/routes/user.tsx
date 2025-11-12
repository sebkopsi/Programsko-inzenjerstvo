import { UserPage } from "~/pages/user/UserPage";

export function meta({}: { title: string; description: string }) {
  return [
    { title: "User Profile" },
    { name: "description", content: "Profil korisnika" },
  ];
}

export default function User() {
  return <UserPage />;
}