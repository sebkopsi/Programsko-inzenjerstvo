import {
  isRouteErrorResponse,
  Links,
  Meta,
  Outlet,
  Scripts,
  ScrollRestoration,
  useNavigation,
} from "react-router";

import type { Route } from "./+types/root";
import SideBar from "./pages/components/sidebar";

import "./app.css"

export const links: Route.LinksFunction = () => [
  { rel: "preconnect", href: "https://fonts.googleapis.com" },
  {
    rel: "preconnect",
    href: "https://fonts.gstatic.com",
    crossOrigin: "anonymous",
  },
  {
    rel: "stylesheet",
    href: "https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap",
  },
];

export function Layout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Meta />
        <Links />
      </head>
      <body>
        {children}
        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}

import { GetJwtToken } from "~/util/cookie";

export async function loader({ request }: Route.LoaderArgs) {

  const jwt = GetJwtToken(request);

 if (!jwt) {
    // User is not logged in
    return { user: null };
  }
  const res = await fetch("http://localhost:8890/user/my", {
    headers: { "Authorization": "Bearer " + jwt },
  });

  if (!res.ok) throw new Error("Failed to fetch user info22");

  const user = await res.json();

  console.log("RAW /user/my response:");  
  console.log(JSON.stringify(user, null, 2));

  console.log("user.isAdmin:", user.isAdmin);


  return {user};
}

import { useLoaderData } from "react-router"; 

export default function App() {
  const navigation = useNavigation()
   const { user } = useLoaderData<typeof loader>();
  return (
    <>
      <main>
        <SideBar isAdmin={user?.isAdmin === true} />
        <Outlet />
      </main>
    </>
  );
}

export function ErrorBoundary({ error }: Route.ErrorBoundaryProps) {
  let message = "Oops!";
  let details = "An unexpected error occurred.";
  let stack: string | undefined;

  if (isRouteErrorResponse(error)) {
    message = error.status === 404 ? "404" : "Error";
    details =
      error.status === 404
        ? "The requested page could not be found."
        : error.statusText || details;
  } else if (import.meta.env.DEV && error && error instanceof Error) {
    details = error.message;
    stack = error.stack;
  }

  return (
    <main >
      {/* <SideBar isAdmin={user?.isAdmin === true} /> */}
      <section id="content">
        <section id="error">
          <h1>{message}</h1>
          <p>{details}</p>
        </section>
      </section>
    </main>
  );
}
