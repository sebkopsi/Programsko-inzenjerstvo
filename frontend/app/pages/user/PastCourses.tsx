import mediteranskaKuhinja from "../../../public/images/mediteranska_kuhinja.jpg"
import kineskaKuhinja from "../../../public/images/kineska_kuhinja.jpg"

export default function PastCourses() {
        return (
                <div className="past-courses">
                        <h2>povijest tečajeva:</h2>
                        <div className="mediteranian">
                                <a href="#">
                                        <img src={mediteranskaKuhinja} alt="mediteranska kuhinja" />
                                        <p>Mediteranska kuhinja</p>
                                </a>
                        </div>
                        <div className="chinese">
                                <a href="#">
                                        <img src={kineskaKuhinja} alt="kineska kuhinja" />
                                        <p>Kineska kuhinja</p>
                                </a>
                        </div>
                </div>
        )
}