import { Form, useFetcher } from "react-router";
import '../../styles/courses.css';
import "../../styles/forms.css";
import { useState } from "react";

export function NewCoursePage() {
  const fetcher = useFetcher();
  const [tags, setTags] = useState<string[]>([]);

  const addTag = () => {
    const input = document.getElementById("tag-name") as HTMLInputElement;
    const newTag = input?.value.trim();
    if (!newTag) return;

    if (!tags.includes(newTag)) {
      setTags([...tags, newTag]);
    }

    input.value = "";
  };

  return (
    <section id="content">
      <section id="path">
        <h4>
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
            <path
              fillRule="evenodd"
              d="M2.25 2.25a.75.75 0 0 0 0 1.5H3v10.5a3 3 0 0 0 3 3h1.21l-1.172 3.513a.75.75 0 0 0 1.424.474l.329-.987h8.418l.33.987a.75.75 0 0 0 1.422-.474l-1.17-3.513H18a3 3 0 0 0 3-3V3.75h.75a.75.75 0 0 0 0-1.5H2.25Zm6.54 15h6.42l.5 1.5H8.29l.5-1.5Zm8.085-8.995a.75.75 0 1 0-.75-1.299 12.81 12.81 0 0 0-3.558 3.05L11.03 8.47a.75.75 0 0 0-1.06 0l-3 3a.75.75 0 1 0 1.06 1.06l2.47-2.47 1.617 1.618a.75.75 0 0 0 1.146-.102 11.312 11.312 0 0 1 3.612-3.321Z"
              clipRule="evenodd"
            />
          </svg>
          My Courses
        </h4>
        <h2>New Course</h2>
      </section>

      <fetcher.Form className="course-form" method="POST" action="/course/new">

        <h2>Info</h2>

        <section className="section">
          <section className="info">
            <h3>Name</h3>
            <span>Name of the new course, should be simple and straight to the point!</span>
          </section>
          <input type="text" name="name" className="box" />
        </section>

        <section className="section">
          <section className="info">
            <h3>Description</h3>
            <span>Description that will be shown to users browsing the course.</span>
          </section>
          <textarea name="desc" className="box" />
        </section>

        <section className="section">
          <section className="info">
            <h3>Tags</h3>
            <span>Tags that will make finding the course easy for users!</span>
            <div className="form">
              <input type="text" name="tag-name" id="tag-name" />
              <button type="button" className="submit-button" onClick={addTag}>Add</button>
            </div>
          </section>

          <section className="box">
            {tags.map((tag, index) => (
              <div className="tag" key={index}>
                {tag}
                <input type="hidden" name="tags" value={tag} />
              </div>
            ))}
          </section>
        </section>

        <section className="section">
          <section className="info">
            <h3>Banner</h3>
            <span>Banner image that will be displayed alongside information about the course.</span>
            <div className="form">
              <input type="file" name="banner" />
            </div>
          </section>
          <section className="box"></section>
        </section>

        <section className="create-section">
          <button className="create-button" type="submit">Create</button>
        </section>
      </fetcher.Form>
    </section>
  );
}
