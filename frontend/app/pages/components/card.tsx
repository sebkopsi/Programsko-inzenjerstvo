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
            <img src="/images/banner_daska_za_rezanje.jpg" alt="" className="banner" />

        </NavLink>
    )
}