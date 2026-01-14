import { Card } from "../components/card";

export function allLectures(lectures: any[]) {
  return (
    <section id="all-lectures" className="cardTable section">
      <ul>
        {lectures.map((lecture) => (
          <Card
            key={lecture.id}
            link={lecture.id}
            name={lecture.name}
            desc={lecture.desc} // optional, just description
            prepTime={lecture.prepTime}
            cookTime={lecture.cookTime}
            difficulty={lecture.difficulty}
            type="lecture"
          />
        ))}
      </ul>
    </section>
  );
}
