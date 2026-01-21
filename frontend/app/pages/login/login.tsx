import { Form, useActionData, useFetcher, useLoaderData } from "react-router";
import "./login.css";

export function LoginPage() {
  const actionData = useLoaderData();
  const fetcher = useFetcher();
  const app_url = import.meta.env.PROD ? "https://cooking.planine.hr" : "http://localhost:5173"
  let busy = fetcher.state !== "idle";
  return (
    <section id="content">
      <section id="form">
        {fetcher.data?.ok == false && (
          <div id="error">{fetcher.data.errors.message}</div>
        )}
        <div className="CF-login-form">
          <fetcher.Form className="login-form" method="POST" action="/login">
            <div className="form-text-and-input">
              <span>email address</span>
              <input type="email" name="email" required />
            </div>
            <div className="form-text-and-input">
              <span>password</span>
              <input type="password" name="password" required />
            </div>
            <button className="submit-button" type="submit">
              {busy ? "Logging in..." : "Log in"}
              <div className="arrow-icon">&#8594;</div>
            </button>
          </fetcher.Form>
        </div>

        <hr className="dashed-line" />

        <div className="oAuth-options">
          <button
            className="oAuth-button"
            onClick={() => {
              const rootUrl = `https://accounts.google.com/o/oauth2/v2/auth`;
              const options = {
                redirect_uri:  app_url + '/oauth',
                client_id: "816590313634-7ov4cqdk97laihjvmrmre4pjt2j6dhkc.apps.googleusercontent.com",
                access_type: "offline",
                response_type: "code",
                prompt: "consent",
                scope: [
                  "https://www.googleapis.com/auth/userinfo.profile",
                  "https://www.googleapis.com/auth/userinfo.email",
                ].join(" "),
              };

              const qs = new URLSearchParams(options);

              window.location.href = `${rootUrl}?${qs.toString()}`;
            }}
          >
            <img
              className="oAuth-icon"
              src="/images/google_icon.png"
              alt="Google"
            />
            <span>Sign in with Google</span>
          </button>
        </div>
      </section>
    </section>
  );
}

export default LoginPage;
