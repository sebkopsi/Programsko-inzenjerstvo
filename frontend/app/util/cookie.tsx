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

    return cookies["jwt"];
}