import { redirect } from "react-router";
import type { Route } from "../+types/home";
import { GetJwtToken } from "~/util/cookie";
import { useLoaderData } from "react-router";
import AdminInboxContent from "~/pages/admin_panel/adminInbox";

type RequestSummary = {
  userEmail: string;
  type: string;
  status: string;
  title: string;
  createdAt: string;
  sentByUserId: string;
  reqId: number;
};

type InboxResult = {
  success: boolean;
  message: string;
  data: RequestSummary[] | null;
};

export async function loader({ request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request);
  if (!jwt) return redirect("/login");

  const res = await fetch("http://localhost:8890/admin/inbox", {
    headers: {
      "Authorization": "Bearer " + jwt
    },
  });

  if (!res.ok) {
    if (res.status === 401 || res.status === 403) {
      return redirect("/");
    }
    throw new Error("Failed to load admin inbox");
  }

  const data: InboxResult = await res.json();

  if (!data.success) {
    throw new Error(data.message);
  }

  return {
    requests: data.data ?? []
  };
}

export default function AdminInboxPage() {
  const data = useLoaderData<typeof loader>();
  return <AdminInboxContent />;
}