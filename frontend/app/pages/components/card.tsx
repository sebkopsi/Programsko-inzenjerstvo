import { NavLink } from "react-router";

export function Card({ link }) {
    return (
        <NavLink className="card" to={link} viewTransition>
            <section className="info">
                <h2 className="title">Name Lorem Ipsum</h2>
                <span className="desc">
                    {Array.from({ length: Math.floor(Math.random() * 20) + 1 }, (_, i) => (
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
                    ))}
                </span>
                <section className="tags">
                    {[...Array(10)].map((x, i) =>
                        <div className="tag">tag #{i}</div>
                    )}
                </section>

            </section>
            <img src="/images/banner_daska_za_rezanje.jpg" alt="" className="banner" />

        </NavLink>
    )
}