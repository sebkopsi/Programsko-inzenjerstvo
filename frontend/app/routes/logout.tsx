import { LogoutPage } from "~/pages/home/logout";
import type { Route } from "./+types/home";
import { redirect, useLoaderData } from "react-router";



export function meta({ }: Route.MetaArgs) {
  return [
    { title: "Logout1 - Cooking Flamingoz" },
    { name: "description", content: "loginout" },
  ];
}

export async function loader({ request }: Route.ActionArgs) {
  return redirect("/course", {
    headers: {
      "Set-Cookie": `jwt=; Path=/; HttpOnly; Secure; SameSite=Strict; expires=Thu, 01 Jan 1970 00:00:00 GMT`
    }})
}

export default function Login() {
  return <LogoutPage />
}
