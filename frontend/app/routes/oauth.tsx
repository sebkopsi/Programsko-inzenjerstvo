import type { Route } from "./+types/home";
import 'dotenv/config'
import {
  redirect,
  useLoaderData,
  useNavigate,
  useSearchParams,
} from "react-router";
import type assert from "assert";
import { useEffect } from "react";
import { url } from "inspector";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Login - Cooking Flamingoz" },
    { name: "description", content: "login form" },
  ];
}

export async function loader({ request }: Route.LoaderArgs) {
  const urlParams = new URLSearchParams(request.url.split('?').at(1));
  const code =  urlParams?.get("code");
  const error = urlParams?.get("error");

  if (error) {
   return  redirect("/login");
  }
  if(code === null){
    return redirect("/login")
  }

  const client = process.env.GOOGLE_CLIENT
  const secret = process.env.GOOGLE_SECRET
  if(client == null || secret == null){
    return redirect("/login")
  }
  const formData = new URLSearchParams();
  const app_url = import.meta.env.PROD ? "https://cooking.planine.hr" : "http://localhost:5173"

  formData.append("code" ,code)
  formData.append("client_id", client);
  formData.append("client_secret", secret);
  formData.append("redirect_uri", app_url + "/oauth");
  formData.append("grant_type", "authorization_code");
  
  const resp = await fetch("https://oauth2.googleapis.com/token", {
    method: "POST",
    headers: {
      "content-type": "application/x-www-form-urlencoded",
    },
    body: formData
  });

  if(resp.status != 200) {
   return  redirect("/login")
  }

  const body = await resp.json()

  const loginCredResp = await fetch("http://localhost:8890/user/oauth", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      "tokenType": "google",
      "token": body?.access_token
    })
  })

  if(loginCredResp.status != 200) {
    return redirect("/login")
  }

  const loginInfo = await loginCredResp.json();
  if(loginInfo == null || !loginInfo.token) {
    return redirect("/login")
  }

  return redirect("/", {
    headers: {
      "Set-Cookie": `jwt=${loginInfo.token}; Path=/; HttpOnly; Secure; SameSite=Strict`
    }
  })
}

export default async function oauth() {
  return <div>Processing...</div>;
}
