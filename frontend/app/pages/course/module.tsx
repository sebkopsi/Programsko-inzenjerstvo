import { Form, useActionData, useFetcher, useLoaderData } from "react-router";

import "../styles/courses.css"
import { courseStats } from "./courseStats";
import { allLectures } from "./allLectures";

const dummyLectures = [
  {
    id: "lecture1",
    name: "Making the Perfect Pizza Dough",
    desc: "Step-by-step guide to making soft, airy pizza dough.",
    prepTime: "15 min",
    cookTime: "10 min",
    difficulty: "Easy"
  },
  {
    id: "lecture2",
    name: "Classic French Omelette",
    desc: "Learn how to fold and cook a perfect omelette.",
    prepTime: "5 min",
    cookTime: "10 min",
    difficulty: "Medium"
  },
  {
    id: "lecture3",
    name: "Sushi Rice Preparation",
    desc: "Properly cook and season sushi rice for perfect sushi rolls.",
    prepTime: "20 min",
    cookTime: "0 min",
    difficulty: "Medium"
  },
  {
    id: "lecture4",
    name: "Vegetable Stir Fry",
    desc: "Quick and healthy stir fry techniques for vegetables.",
    prepTime: "10 min",
    cookTime: "8 min",
    difficulty: "Easy"
  },
  {
    id: "lecture5",
    name: "Beef Wellington",
    desc: "Advanced recipe for a classic Beef Wellington.",
    prepTime: "45 min",
    cookTime: "60 min",
    difficulty: "Hard"
  }
];

export function ModulePage() {
  return (
      
      <section id="content">
        <section id="path">
          <h4><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
            <path d="M11.7 2.805a.75.75 0 0 1 .6 0A60.65 60.65 0 0 1 22.83 8.72a.75.75 0 0 1-.231 1.337 49.948 49.948 0 0 0-9.902 3.912l-.003.002c-.114.06-.227.119-.34.18a.75.75 0 0 1-.707 0A50.88 50.88 0 0 0 7.5 12.173v-.224c0-.131.067-.248.172-.311a54.615 54.615 0 0 1 4.653-2.52.75.75 0 0 0-.65-1.352 56.123 56.123 0 0 0-4.78 2.589 1.858 1.858 0 0 0-.859 1.228 49.803 49.803 0 0 0-4.634-1.527.75.75 0 0 1-.231-1.337A60.653 60.653 0 0 1 11.7 2.805Z" />
            <path d="M13.06 15.473a48.45 48.45 0 0 1 7.666-3.282c.134 1.414.22 2.843.255 4.284a.75.75 0 0 1-.46.711 47.87 47.87 0 0 0-8.105 4.342.75.75 0 0 1-.832 0 47.87 47.87 0 0 0-8.104-4.342.75.75 0 0 1-.461-.71c.035-1.442.121-2.87.255-4.286.921.304 1.83.634 2.726.99v1.27a1.5 1.5 0 0 0-.14 2.508c-.09.38-.222.753-.397 1.11.452.213.901.434 1.346.66a6.727 6.727 0 0 0 .551-1.607 1.5 1.5 0 0 0 .14-2.67v-.645a48.549 48.549 0 0 1 3.44 1.667 2.25 2.25 0 0 0 2.12 0Z" />
            <path d="M4.462 19.462c.42-.419.753-.89 1-1.395.453.214.902.435 1.347.662a6.742 6.742 0 0 1-1.286 1.794.75.75 0 0 1-1.06-1.06Z" />
          </svg>
            All Courses</h4>
          <h4>Course Name</h4>
          <h2>Module Name</h2>
        </section>
        {courseStats(1)}
        {allLectures(dummyLectures)}
      </section>
  );
}
