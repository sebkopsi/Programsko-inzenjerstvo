import type { Route } from "./+types/home";
import { SignUpPage } from "~/pages/signup/signup";
import { redirect, useLoaderData } from "react-router";
import type assert from "assert";

function assertReqIsLoginReq(data: any): asserts data is signupReq{
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

type signupReq = {
  email: string,
  password: string,
  firstname: string,
  surname: string,
  username: string
};

export async function action({request}: Route.ActionArgs) {
  const formData = await request.formData();
  if(formData.get("password") != formData.get("password2")){
    return {
      ok: false,
      errors: {
        message: "Passwords do not match!"
      }
    }
  }

  const resp = await fetch("http://localhost:8080/user", {
    method: "POST",
    headers: {
      "content-type": "application/json"
    },
    body: JSON.stringify({
      email: formData.get("email"),
      password: formData.get("password"),
      surname: formData.get("surname"),
      firstname: formData.get("firstname"),
      username: formData.get("username") 
    })
  })
  
  if(resp.status != 200){
    return {
      ok: false,
      errors: {
        message: "Signup failed... response status: " + resp.status
      }
    }
  }


  return redirect("/login")
}

export default function Login() {
  return <SignUpPage/>
}
