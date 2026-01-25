import { redirect } from "react-router";
import { GetJwtToken } from "~/util/cookie";
import type { Route } from "./+types/request";
import { RequestPage } from "~/pages/request/request";

export async function loader({ params, request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request);
  if (!jwt)
    return redirect("/login");

  const requestReq = await fetch("http://localhost:8890/admin/request/1", {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + jwt,
    }
  });
  if (!requestReq.ok)
    throw new Error("Failed to fetch request");

  const requestInfo = await requestReq.json();
  if (!requestInfo.success)
    throw new Error("No request found");

  return { requestInfo };
}

export default function Request() {
  return <RequestPage/>
}