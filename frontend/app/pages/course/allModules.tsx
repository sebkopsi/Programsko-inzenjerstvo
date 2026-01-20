import { Card } from "../components/card";

const info = () => {
    return (
        <section className="cardInfo">
            <span className="">Number of Lectures: </span>
        </section>
    )
}



export function allModules(modules: any) {
    return (
        <section id="all-modules" className="cardTable section">
            <ul>
                {modules?.map((module) =>
                    <Card link={module.id} name={module.name} desc={info()} />
                )}
            </ul>
        </section>
    )
}

