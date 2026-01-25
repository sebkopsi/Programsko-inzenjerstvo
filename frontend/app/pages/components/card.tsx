import { NavLink } from "react-router";

export function Card({ link, name, desc, tags, type, lessons, prepTime, cookTime, difficulty }: any) {
  const defaultBanner = "/images/banner_daska_za_rezanje.jpg";

  return (
    <NavLink className={`card ${type}`} to={"" + link} viewTransition>
      <section className="info">
        <h2 className="title">{name}</h2>

      
        {type === "course" && (
          <>
            <div className="desc">{desc}</div>
            <section className="tags">
              {tags?.map((tag: any, index: number) => (
                <div className="tag" key={index}>{tag.name}</div>
              ))}
            </section>
          </>
        )}

       
        {type === "module" && (
          <>
            <div className="desc">{desc}</div>
            <span className="numOfLessons">Lessons in total: {lessons}</span>
          </>
        )}

  
     {type === "lecture" && (
  <>
    <div className="desc">{desc}</div>
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
