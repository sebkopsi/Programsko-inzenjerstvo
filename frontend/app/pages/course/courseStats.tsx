import "./../styles/stats.css"
export function courseStats(id: any) {
    return (
        <section id="stats" className="section ">
            <section className="info">
                <span className="desc">Lorem, ipsum dolor sit amet consectetur adipisicing elit. Quasi dignissimos, delectus provident debitis reprehenderit in praesentium consequatur saepe doloremque aliquid illo, nostrum autem tempora quisquam laboriosam labore itaque facere. Consequuntur!</span>
            </section>
            <section className="options">

            </section>
            <section className="progress">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"
                    width="100%"
                    height="100%" >
                    <path className="grey" d="M40,90 A40,40 0 1,1 60,90" />
                    <path className="purple" d="M40,90 A40,40 0 1,1 60,90" />
                    <text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle">75</text>
                </svg>
            </section>
        </section>
    )
}