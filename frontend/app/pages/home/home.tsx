import { Link } from "react-router";
import './home.css'

export function HomePage() {
 return (
    <main>
      <header>
        <div className="left-section">
          <Link to="/">
            <img className="logo" src="/images/cf_logo_pink.png" alt="Cooking Flamingoz Logo" />
          </Link>
        </div>
        <div className="right-section">
          <div className="menu">
            <Link id="kvacica-allCourses-link" to="/allCourses">
              <img className="kvacica-icon" src="/images/kvacica_green.png" alt="Checkmark icon" />
            </Link>
            <Link id="allCourses-link" to="/allCourses">TECAJEVI</Link>
          </div>
          <div className="menu">
            <Link id="about-us-link" to="/about_us">O NAMA</Link>
          </div>
          <div className="menu">
            <Link className="profile-link" to="/login">
              <img className="profile-icon" src="/images/profile_icon.png" alt="Profile icon" />
            </Link>
          </div>
        </div>
      </header>

      <div className="banner">
        <div className="banner-image-container">
          <img
            className="banner-image"
            src="/images/banner_daska_za_rezanje.jpg"
            alt="Banner"
          />
        </div>
        <div className="banner-text">Hrana za misli</div>
      </div>

      <div className="demo-courses">
        <div id="demo-courses-text">Isprobajte sada!</div>
        <div className="demo-courses-cards">
          <div className="course-card">
            <div className="course-card-thumbnail">
              <a className="course-card-link" href="course1.html">
                <img
                  className="course-card-tn-image"
                  src="/images/thumbnail_mediteranska_kuhinja.jpg"
                  alt="Mediteranska kuhinja"
                />
              </a>
            </div>
            <div className="course-card-text">Mediteranska kuhinja</div>
          </div>

          <div className="course-card">
            <div className="course-card-thumbnail">
              <a className="course-card-link" href="course2.html">
                <img
                  className="course-card-tn-image"
                  src="/images/thumbnail_kineska_kuhinja.jpg"
                  alt="Kineska kuhinja"
                />
              </a>
            </div>
            <div className="course-card-text">Kineska kuhinja</div>
          </div>

          <div className="course-card">
            <div className="course-card-thumbnail">
              <a className="course-card-link" href="course3.html">
                <img
                  className="course-card-tn-image"
                  src="/images/thumbnail_indijska_kuhinja.jpg"
                  alt="Indijska kuhinja"
                />
              </a>
            </div>
            <div className="course-card-text">Indijska kuhinja</div>
          </div>
        </div>
      </div>

      <div id="home-footer">
        <span>Copyright © Cooking Flamingoz 2025</span>
      </div>
    </main>
  );
}
