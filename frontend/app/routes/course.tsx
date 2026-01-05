import type { Route } from "./+types/home";
import { Course } from "~/pages/course/Course";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "Course page" },
    { name: "description", content: "Welcome to the course page!" },
  ];
}

export default function CoursePage() {
  return <Course />
}