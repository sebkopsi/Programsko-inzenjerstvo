import type { Route } from "./+types/home";
import { CoursePage } from "~/pages/course/CoursePage";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Course page" },
    { name: "description", content: "Welcome to the course page!" },
  ];
}

export default function Course() {
  return <CoursePage />
}
