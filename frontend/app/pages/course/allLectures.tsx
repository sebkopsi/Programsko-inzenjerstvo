import { Card } from "../components/card";


export function allLectures() {
    return (
        <section id="all-lectures" class="cardTable section">
            <ul>
            {[...Array(50)].map((x, i) =>
                <Card link="lectureid"/>
            )}
            </ul>
        </section>
    )
}