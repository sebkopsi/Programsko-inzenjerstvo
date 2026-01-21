import { redirect } from "react-router";

const parseJwt = (token: string) => {
  try {
    return JSON.parse(atob(token.split(".")[1]));
  } catch (e) {
    return null;
  }
};


export function GetJwtToken(request: any): string {
  const cookieHeader = request.headers.get("Cookie");
  const cookies: Record<string, string> = {};

  cookieHeader?.split(";").forEach((cookie: string) => {
    const [name, value] = cookie.trim().split("=");
    if (name && value) cookies[name] = decodeURIComponent(value);
  });

  return cookies["jwt"] || "";
}


export function GetUserFromJwt(request: any) {
  const token = GetJwtToken(request);
  if (!token) return null;

  const user = parseJwt(token);
  return user; 
}
