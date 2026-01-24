import { Link } from "react-router";
import "./home.css";

type HomePageProps = {
  user: any | null;
};

export function HomePage({ user }: HomePageProps) {
  return (
    <section id="content">
      <header>
        <div className="option-section">
          <div className="menu">
            {!user && (
              <>
                <Link className="login-link" to="/login">
                  LOG IN
                </Link>
                <Link className="signup-link" to="/signup">
                  SIGN UP
                </Link>
              </>
            )}
              <Link className="profile-link" to="/profile">
                <img
                  className="profile-icon"
                  src="/images/profile_icon.png"
                  alt="Profile icon"
                />
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
            loading="eager"
          />
        </div>
        <div className="banner-text">Food for thought</div>
      </div>
      <div className="about-us-text">Here at Cooking Flamingoz, we bring the world of culinary arts straight to your kitchen. Our platform hosts thorough and easy-to-follow courses from professional chefs, sharing their expertise in cuisines from around the globe. Whether you’re a beginner or a seasoned cook, our interactive courses make learning fun, delicious, and inspiring. Explore, cook, and savor the flavors with us! </div>
      <div className="demo-courses">

        <div className="demo-courses-cards">
               <div id="demo-courses-text">Try it out now!</div>
          <div className="cards-row">
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
            <div className="course-card-text">Mediterranean cuisine</div>
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
            <div className="course-card-text">Chinese cuisine</div>
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
            <div className="course-card-text">Indian cuisine</div>
          </div>
        </div>
        </div>
      </div>

      <div id="home-footer">
        <span>Copyright © Cooking Flamingoz 2025</span>
      </div>
    </section>
  );
}
