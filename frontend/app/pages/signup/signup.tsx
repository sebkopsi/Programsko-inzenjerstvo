import { Form, useActionData, useFetcher, useLoaderData } from "react-router";
import "./signup.css";

export function SignUpPage() {
  const actionData = useLoaderData();
  const fetcher = useFetcher();
  let busy = fetcher.state !== "idle";
  const app_url = import.meta.env.PROD ? "https://cooking.planine.hr" : "http://localhost:5173"
  return (
    <section id="content">

      <section>
        <GlobalError error={errorObject} />
        {fetcher.data?.ok === false && (
                <div className="error">{fetcher.data.errorObject.message}</div>
        )}
        <div className="CF-login-form">
          <fetcher.Form className="login-form" method="POST" action="/signup">
            <div className="form-text-and-input">
              <span>email address</span>
              <input type="email" name="email" required />
            </div>
            <div className="form-text-and-input">
              <span>name</span>
              <input type="text" name="firstname" required />
            </div>
            <div className="form-text-and-input">
              <span>surname</span>
              <input type="text" name="surname" required />
            </div>
            <div className="form-text-and-input">
              <span>username</span>
              <input type="text" name="username" required />
            </div>
            <div className="form-text-and-input">
              <span>password</span>
              <input type="password" name="password" required />
            </div>
            <div className="form-text-and-input">
              <span>repeat password</span>
              <input type="password" name="password2" required />
            </div>
            <button className="submit-button" type="submit">
              {busy ? "Loading..." : "Sign up"}
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
                redirect_uri: app_url +"/oauth",
                client_id:
                  "816590313634-7ov4cqdk97laihjvmrmre4pjt2j6dhkc.apps.googleusercontent.com",
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
            <span>Sign up with Google</span>
          </button>
        </div>
      </section>
    </section>
  );
}

export default SignUpPage;
