import type { Route } from "./+types/home";
import { LoginPage } from "~/pages/login/login";
import { useLoaderData } from "react-router";
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

  const req = await fetch("http://localhost:8080/user/login", {
    method: "POST",
    body: JSON.stringify({
      email: formData.get("email"),
      password: formData.get("password")
    })
  })
  
  if(req.status != 200){
    return {
      ok: false,
      errors: {
        message: await req.text()
      }
    }
  }

  return {
    ok: true,
    data: {
      token: "test"
    }
  }
}

export default function Login() {
  return <LoginPage/>
}
