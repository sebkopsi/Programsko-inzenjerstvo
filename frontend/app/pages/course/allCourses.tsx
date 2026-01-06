import { Card } from "../components/card";


export function allCourses() {
    return (
        <section id="all-courses" class="cardList section">
            <ul>
            {[...Array(50)].map((x, i) =>
                <Card link="id"/>
            )}
            </ul>
        </section>
    )
}