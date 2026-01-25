import { redirect } from "react-router";

export async function loader() {
  return redirect("/login", {
    headers: {
      "Set-Cookie": "jwt=; Max-Age=0; Path=/", // clear the cookie
    },
  });
}

export default function Logout() {
  return null; // nothing to render
}
