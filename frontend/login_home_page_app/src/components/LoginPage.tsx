import React from "react";
import "../styles/LoginPage.css";

// LoginPage has no props, so we type it as React.FC
const LoginPage: React.FC = () => {
  return (
    <div>
      <main>
        <div className="CF-login-form">
          <form className="login-form" method="POST">
            <div className="form-text-and-input">
              <span>email adresa</span>
              <input type="email" name="email" required />
            </div>
            <div className="form-text-and-input">
              <span>lozinka</span>
              <input type="password" name="password" required />
            </div>
            <button className="submit-button" type="submit">
              Prijava
              <div className="arrow-icon">&#8594;</div>
            </button>
          </form>
        </div>

        <hr className="dashed-line" />

        <div className="oAuth-options">
          <button className="oAuth-button">
            <img className="oAuth-icon" src="/images/google_icon.png" alt="Google" />
            <span>Prijava putem Google računa</span>
          </button>
          <button className="oAuth-button">
            <img className="oAuth-icon" src="/images/microsoft_icon.png" alt="Microsoft" />
            <span>Prijava putem Microsoft računa</span>
          </button>
          <button className="oAuth-button">
            <img className="oAuth-icon" src="/images/apple_icon.png" alt="Apple" />
            <span>Prijava putem Apple računa</span>
          </button>
        </div>
      </main>

      <div id="login-footer">
        <img className="footer-logo" src="/images/cf_logo_green.png" alt="Footer Logo" />
      </div>
    </div>
  );
};

export default LoginPage;
