import { NavLink } from "react-router";

export function Card({ link, name, desc, tags, type, lessons, prepTime, cookTime, difficulty }: any) {
  const defaultBanner = "/images/banner_daska_za_rezanje.jpg";

  return (
    <NavLink className={`card ${type}`} to={"" + link} viewTransition>
      <section className="info">
        <h2 className="title">{name}</h2>

      
        {type === "course" && (
          <>
            <span className="desc">{desc}</span>
            <section className="tags">
              {tags?.map((tag: any, index: number) => (
                <div className="tag" key={index}>{tag.name}</div>
              ))}
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
