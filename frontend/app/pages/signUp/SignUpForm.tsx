import { useState } from "react";
import { useNavigate } from "react-router";

export function SignUpForm() {
        const [username, setUsername] = useState("");
        const [email, setEmail] = useState("");
        const [password, setPassword] = useState("");
        const [passwordConfirm, setPasswordConfirm] = useState("");
        const [message, setMessage] = useState("");

        const navigate = useNavigate();

        const resetUserData = () => {
                setUsername("");
                setEmail("");
                setPassword("");
                setPasswordConfirm("");
        };

        const handleSubmit = (e: React.FormEvent) => {
                e.preventDefault();
                const passRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

                if (!username || !email || !password || !passwordConfirm) {
                        setMessage("Obavezno popunite sva polja!");
                        resetUserData();
                        return;
                }

                if (!passRegex.test(password)) {
                        setMessage("Lozinka mora imati najmanje 8 znakova, od toga barem jedno malo slovo, jedno veliko slovo, jedan broj i jedan specijalan znak");
                        resetUserData();
                        return;
                }
                if (password !== passwordConfirm) {
                        setMessage("Lozinka nije točna!");
                        resetUserData();
                        return;
                }

                navigate("/user");
        };

        return (
                <div className="signUp-container">
                        <form onSubmit={handleSubmit} className="signUp-form">
                                <label htmlFor="username" className="signUp-label">korisničko ime:</label><br />
                                <input className="input" id="username" value={username} onChange={(e) => setUsername(e.target.value)} /><br />

                                <label htmlFor="email" className="signUp-label">e-pošta:</label><br />
                                <input className="input" id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} /><br />

                                <label htmlFor="password" className="signUp-label">lozinka:</label><br />
                                <input className="input" id="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} /><br />

                                <label htmlFor="passwordConfirm" className="signUp-label">ponovi lozinku:</label><br />
                                <input className="input" id="passwordConfirm" type="password" value={passwordConfirm} onChange={(e) => setPasswordConfirm(e.target.value)} /><br />

                                {message && <label className="message">{message}</label>}
                                <br />

                                <button className="signUp-submit-btn" type="submit">
                                        Kreiraj i nastavi &rarr;
                                </button>
                        </form>

                        <div className="goToLogIn">
                                <p>Već imaš račun?</p>
                                <button type="button" className="goToLogIn-btn" onClick={() => navigate("/login")}>
                                        Prijavi se &rarr;
                                </button>
                        </div>
                </div>
        );
}
