import { Card } from "../components/card";


const lecturePrepInfo = (data: any) => {
    console.debug(data)
  return {
    tags: [
      {name: `prep time: ${data.prepTime}`},
      {name: `cook time: ${data.cookTime}`},
      {name: `difficulty: ${data.difficulty}`}
    ]
}
}



export function allLectures(data: any) {
    return (
        <section id="all-lectures" class="cardTable section">
            <ul>
            {data?.map((lecture: any) =>
                <Card link={lecture?.id} desc={""} name={lecture.name} tags={lecturePrepInfo(lecture).tags}/>
            )}
            </ul>
        </section>
    )
}