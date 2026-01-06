import { useFetcher, useLoaderData } from "react-router";
import "../styles/preferences.css";

interface Tag {
  id: string;
  name: string;
  category: string;
}

export default function UserPrefernces() {
  const { preferred, notpreferred } = useLoaderData();
  const fetcher = useFetcher();
  return (
    <section>
      <section id="tags">
        <h2>Tags</h2>
        <section class="section">
          <section class="info">
            <h3>Preffered</h3>
            <span>
              Lectures, modules and courses containing these tags will be given
              priority in searches and recommendations.
            </span>
            <fetcher.Form className="login-form" method="POST" action="/profile?preferred">
              <div className="form-text-and-input">
                <input type="text" name="name" required />
              </div>
              <button className="submit-button" type="submit">
                Add
              </button>
            </fetcher.Form>
          </section>
          <section class="box">
            {preferred.map((tag) => (
              <div key={tag.id} class="tag">
                {tag.name}
              </div>
            ))}
          </section>
        </section>

        <section class="section">
          <section class="info">
            <h3>Don't show</h3>
            <span>
              Lectures, modules and courses containing these tags will be
              discarded in searches and recommendation.
            </span>
                   <fetcher.Form className="login-form" method="POST" action="/profile">
              <div className="form-text-and-input">
                <input type="text" name="name" required />
              </div>
              <button className="submit-button" type="submit">
                Add
              </button>
            </fetcher.Form>
          </section>
          <section class="box">
            {notpreferred.map((tag) => (
              <div key={tag.id} class="tag">
                {tag.name}
              </div>
            ))}
          </section>
        </section>
      </section>
    </section>
  );
}
