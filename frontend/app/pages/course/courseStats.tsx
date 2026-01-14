import "./../styles/stats.css"
export function courseStats(id: any) {
    return (
        <section id="stats" className="section ">
            <section className="info">
                <span className="desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</span>
            </section>
            <section className="options">

            </section>
            <section className="progress">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"
                    width="100%"
                    height="100%" >
                    <path className="grey" d="M40,90 A40,40 0 1,1 60,90" />
                    <path className="purple" d="M40,90 A40,40 0 1,1 60,90" />
                    <text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle">75%</text>
                </svg>
            </section>
        </section>
    )
}