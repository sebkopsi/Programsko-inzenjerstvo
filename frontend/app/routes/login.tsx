import type { Route } from "./+types/home";
import { LoginPage } from "~/pages/login/login";
import { redirect, useLoaderData } from "react-router";
import type assert from "assert";

function assertReqIsLoginReq(data: any): asserts data is loginReq{
  if(!data.email){
    throw new Error('Invalid email')
  }
  if(!data.password){
    throw new Error('Invalid password')
  }
}

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Login - Cooking Flamingoz" },
    { name: "description", content: "login form" },
  ];
}

type loginReq = {
  email: string,
  password: string,
};

export async function action({request}: Route.ActionArgs) {
  const formData = await request.formData();

  const resp = await fetch("http://localhost:8890/user/login", {
    method: "POST",
    headers: {
      "content-type": "application/json"
    },
    body: JSON.stringify({
      email: formData.get("email"),
      password: formData.get("password")
    })
  })

  if(resp.status === 401) {
      return redirect("/login");
  }
  
  if(!resp.ok){
    const loginInfo =  await resp.text()
    return {
      ok: false,
      errorObject: {
        message: "Login failed: " + loginInfo
      }
    }
  }

  const loginInfo =  await resp.json()
  
  if(!loginInfo?.token){
    return {
      ok: false,
      errorObject: {
        message: "Login failed to get JWT token"
      }
    }
  }

  return redirect("/course", {
    headers: {
      "Set-Cookie": `jwt=${loginInfo.token}; Path=/; HttpOnly; Secure; SameSite=Strict`
    }
  })
}

export default function Login() {
  return <LoginPage/>
}
