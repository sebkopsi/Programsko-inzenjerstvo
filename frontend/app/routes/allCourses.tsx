import type { Route } from "./+types/home";
import { AllCoursesPage } from "~/pages/allCourses/AllCoursesPage";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "All courses page" },
    { name: "description", content: "Welcome to the All courses page!" },
  ];
}

export default function AllCourses() {
  return <AllCoursesPage/>
}
