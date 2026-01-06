import { Card } from "../components/card";


export function allModules() {
    return (
        <section id="all-modules" class="cardTable section">
            <ul>
            {[...Array(50)].map((x, i) =>
                <Card link="moduleid"/>
            )}
            </ul>
        </section>
    )
}