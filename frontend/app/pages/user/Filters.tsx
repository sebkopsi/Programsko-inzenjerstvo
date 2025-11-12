export default function Filters() {
        return (
                <>
                        <form className="filters-skill">
                                <label htmlFor="skill-level">razina vještine:</label><br />
                                <select id="skill-level" name="skill-level">
                                        <option disabled selected></option>
                                        <option value="beginner">početnik</option>
                                        <option value="intermediate">srednji</option>
                                        <option value="advanced">napredni</option>
                                </select>
                        </form>
                        <form className="filters-form">
                                <h2 className="preference">prehrambene preferencije:</h2><br />
                                <label className="filters">
                                        <input type="checkbox" id="pref1" value="vegansko" />
                                        vegansko <span className="green-plus">&#128933;</span>
                                </label>
                                <label className="filters">
                                        <input type="checkbox" id="pref2" value="vegeterijansko" />
                                        vegeterijansko <span className="green-plus">&#128933;</span>
                                </label>
                                <label className="filters">
                                        <input type="checkbox" id="pref3" value="peskaterijansko" />
                                        peskaterijansko <span className="green-plus">&#128933;</span>
                                </label>
                        </form>
                        <form className="filters-form">
                                <h2 className="allergies">alergeni/intolerancije:</h2><br />
                                <label className="filters">
                                        <input type="checkbox" id="alerg1" value="gluten" />
                                        gluten <span className="green-plus">&#128933;</span>
                                </label>
                                <label className="filters">
                                        <input type="checkbox" id="alerg2" value="laktoza" />
                                        laktoza <span className="green-plus">&#128933;</span>
                                </label>
                                <label className="filters">
                                        <input type="checkbox" id="alerg3" value="orasasti-plodovi" />
                                        orasasti plodovi <span className="green-plus">&#128933;</span>
                                </label>
                        </form>
                        <form className="filters-form">
                                <h2 className="fav-cuisines">omiljene kuhinje:</h2><br />
                                <label className="filters">
                                        <input type="checkbox" id="cuisine1" value="kontinentalna" />
                                        kontinentalna <span className="green-plus">&#128933;</span>
                                </label>
                                <label className="filters">
                                        <input type="checkbox" id="cuisine2" value="talijanska" />
                                        talijanska <span className="green-plus">&#128933;</span>
                                </label>
                        </form>
                </>
        )
}