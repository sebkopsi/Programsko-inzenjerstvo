import type { Route } from "./+types/home";
import { HomePage } from "~/pages/home/home";
import { GetJwtToken } from "~/util/cookie";
import { useLoaderData } from "react-router";

export async function loader({ request }: Route.LoaderArgs) {
  const jwt = GetJwtToken(request);

  if (!jwt) {
    return { user: null };
  }

  const res = await fetch("http://localhost:8890/user/my", {
    headers: { Authorization: "Bearer " + jwt },
  });

  if (!res.ok) {
    return { user: null };
  }

  const user = await res.json();
  return { user };
}

export default function Home() {
  const { user } = useLoaderData<typeof loader>();

  return <HomePage user={user} />;
}
