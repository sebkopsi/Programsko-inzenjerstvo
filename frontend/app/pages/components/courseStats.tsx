import "./../styles/stats.css"
export function courseStats(courseInfo: any, additional: any) {
    return (
        <section id="stats" className="section ">
            <section className="info">
                  <section className="tags">
                    {additional?.tags?.map((tag) =>
                        <div className="tag">{tag['name']}</div>
                    )}
                </section>
                <h3>Course Information</h3>
                <span className="desc">{courseInfo?.desc}</span>
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