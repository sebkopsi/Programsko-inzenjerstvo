import { redirect } from "react-router";
import { GetJwtToken } from "~/util/cookie";
import type { Route } from "./+types/request";
import { RequestPage } from "~/pages/request/request";

export async function loader({ params, request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request);
  if (!jwt)
    return redirect("/login");

  const res = await fetch("http://localhost:8890/user/my", {
    headers: { "Authorization": "Bearer " + jwt },
  });
  if (!res.ok) throw new Error("Failed to fetch user info");
  const user = await res.json();
  if (!user.isAdmin) {
    return redirect("/");
  }

  const requestReq = await fetch(`http://localhost:8890/admin/request/${params['reqId']}`, {
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

  const userReq = await fetch(`http://localhost:8890/user/${requestInfo.data.sentByUserId}`, {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + jwt,
    }
  });
  if (!userReq.ok)
    throw new Error("Failed to fetch user");
  const userInfo = await userReq.json();

  return { requestInfo, userInfo };
}

export default function Request() {
  return <RequestPage/>
}