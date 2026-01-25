import { useFetcher, useLoaderData } from "react-router";
import "../styles/preferences.css";
import "../styles/components.css"
import "../styles/forms.css"

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
        <h2 className="preferences-big-text">Tags</h2>
        <section className="section">
          <section className="info">
            <h3>Preffered</h3>
            <span>
              Here you can specifiy your dietary preferences, favorite cuisines, etc.
              </span>
            <span className="tag-explanation">
              Lectures, modules and courses containing these tags will be given
              priority in searches and recommendations.
            </span>
            <fetcher.Form className="form" method="POST" action="/profile?preferred">
              <div className="form-text-and-input">
                <input type="text" name="name" required />
              </div>
              <button className="submit-button" type="submit">
                Add
              </button>
            </fetcher.Form>
          </section>
          <section className="box">
            {preferred.map((tag) => (
              <div key={tag.id} className="tag">
                {tag.name}
              </div>
            ))}
          </section>
        </section>

        <section className="section">
          <section className="info">
            <h3>Don't show</h3>
            <span>
                Here you can specifiy your allergies or keywords you would like to avoid in general.
                </span>
              <span className="tag-explanation">
              Lectures, modules and courses containing these tags will be
              discarded in searches and recommendation.
            </span>
                   <fetcher.Form className="form" method="POST" action="/profile">
              <div className="form-text-and-input">
                <input type="text" name="name" required />
              </div>
              <button className="submit-button" type="submit">
                Add
              </button>
            </fetcher.Form>
          </section>
          <section className="box">
            {notpreferred.map((tag) => (
              <div key={tag.id} className="tag">
                {tag.name}
              </div>
            ))}
          </section>
        </section>
      </section>
      <section id="submitter">
        <button className="submitAllButton">Save changes</button>
      </section>
    </section>
  );
}
