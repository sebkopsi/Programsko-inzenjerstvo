import type { Route } from "./+types/home";
import { CreateCourse } from "~/pages/createCourse/CreateCourse";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Create course form" },
    { name: "description", content: "Welcome to the create course form!" },
  ];
}

export default function CreateCoursePage() {
  return <CreateCourse />
}