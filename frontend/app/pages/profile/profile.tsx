import { Form, useActionData, useFetcher, useLoaderData } from "react-router";

import "../styles/components.css"
import "../styles/forms.css"
import UserInfoPage from "./userInfo";
import UserPrefernces from "./userPrefrences";
import { GlobalError } from "../components/GlobalError";

export function ProfilePage() {
  const { errorObject } = useLoaderData();

  return (
    <GlobalError error={errorObject} />
      <UserPrefernces />
    </section>
  );

  return (
      <section id="content">{UserPrefernces()}</section>
  );
}
