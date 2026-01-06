import { Form, useActionData, useFetcher, useLoaderData } from "react-router";

import "../styles/components.css"
import UserInfoPage from "./userInfo";
import UserPrefernces from "./userPrefrences";

export function ProfilePage() {
  return (
      <section id="content">{UserPrefernces()}</section>
  );
}
