import { NavLink } from "react-router";

export function Card({ link, name, desc, tags}) {
    return (
        <NavLink className="card" to={"" + link} viewTransition>
            <section className="info">
                <h2 className="title">{"" + name}</h2>
                <span className="desc">
                    { desc}
                </span>
                <section className="tags">
                    {tags?.map((tag) =>
                        <div className="tag">{tag['name']}</div>
                    )}
                </section>

            </section>
          </>
        )}

       
        {type === "module" && (
          <>
            <span className="desc">{desc}</span>
            <span className="numOfLessons">Lessons in total: {lessons}</span>
          </>
        )}

  
     {type === "lecture" && (
  <>
    <span className="desc">{desc}</span>
    <section className="lecture-stats">
      {prepTime && <span>Prep: {prepTime}</span>}
      {cookTime && <span>Cook: {cookTime}</span>}
      {difficulty && <span>Difficulty: {difficulty}</span>}
    </section>
  </>
)}


      </section>

   
      <img src={defaultBanner} alt="" className="banner" />
    </NavLink>
  );
}
