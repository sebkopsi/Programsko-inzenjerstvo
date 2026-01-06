import { redirect } from "react-router";

const parseJwt = (token: string) => {
    try {
        return JSON.parse(atob(token.split(".")[1]));
    } catch (e) {
        return null;
    }
};

export function GetJwtToken(request: any): String {
    const cookieHeader = request.headers.get("Cookie");
    const cookies: Record<string, string> = {};

    cookieHeader?.split(";").forEach((cookie: any) => {
        const [name, value] = cookie.trim().split("=");
        if (name && value) {
            cookies[name] = decodeURIComponent(value);
        }
    });

    if(cookies["jwt"] === "") return "";

    const parsed = parseJwt(cookies["jwt"])
    console.debug(parsed, Math.floor(Date.now()/1000))
    if(parsed.exp < Math.floor(Date.now()/1000)) return "";
    return cookies["jwt"];
}
