import { NewInstructorPage } from "~/pages/instructor/newInstructor";
import type { Route } from "./+types/newInstructor";
import { redirect } from "react-router";

export async function action({ request }: Route.ActionArgs) {
  const formData = await request.formData();

  try {
    const res = await fetch("http://localhost:8890/instructor/promotionRequest", {
      method: "POST",
      body: formData
    });
  } catch (error: any) {
    throw new Error(error);
  }

  return redirect("/");
}

export default function NewInstructor() {
  return <NewInstructorPage />
}