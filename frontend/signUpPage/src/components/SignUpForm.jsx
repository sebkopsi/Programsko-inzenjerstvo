import { useState } from "react";
import { useNavigate } from "react-router-dom"; 

export default function SignUpForm() {
        const [username, setUsername] = useState("")
        const [email, setEmail] = useState("")
        const [password, setPassword] = useState("")
        const [passwordConfirm, setPasswordConfirm] = useState("")
        const [message, setMessage] = useState("")

        const navigate = useNavigate()

        const resetUserData = () => {
                setUsername("")
                setEmail("")
                setPassword("")
                setPasswordConfirm("")
        }

        const handleUsername = (event) => {setUsername(event.target.value)}
        const handleEmail = (event) => {setEmail(event.target.value)}
        const handlePassword = (event) => {setPassword(event.target.value)}
        const handlePasswordConfirm = (event) => {setPasswordConfirm(event.target.value)}

        let pass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/

        const handleSubmit = (event) => {
                event.preventDefault()

                if(!username || !email || !password || !passwordConfirm) {
                        setMessage("Obavezno popunite sva polja!")
                        resetUserData()
                        return
                }
                if(!pass.test(password)) {
                        setMessage("Lozinka mora imati najmanje 8 znakova, od toga barem jedno malo slovo, jedno veliko slovo, jedan broj i jedan specijalan znak")
                        resetUserData()
                        return
                }
                if(password != passwordConfirm) {
                        setMessage("Lozinka nije točna!")
                        resetUserData()
                        return
                }

                navigate("/home")
        }

        return (
                <div className="signUp-container">
                        <form onSubmit={handleSubmit} className="signUp-form" method="post" action="/users/signup">

                                <label htmlFor="username" className="signUp-label">korisničko ime:</label><br />
                                <input className="input" id="username" type="text" value={username} onChange={handleUsername} />
                                <br />
                                <label htmlFor="email" className="signUp-label">e-pošta:</label><br />
                                <input className="input" id="email" type="email" value={email} onChange={handleEmail} />
                                <br/>
                                <label htmlFor="password" className="signUp-label">lozinka:</label><br />
                                <input className="input" id="password" type="password" value={password} onChange={handlePassword} />
                                <br/>
                                <label htmlFor="passwordConfirm" className="signUp-label">ponovi lozinku:</label><br />
                                <input className="input" id="passwordConfirm" type="password" value={passwordConfirm} onChange={handlePasswordConfirm} />
                                <br />

                                {message ? <><label className="message">{message}</label><br /></>  : <></>}
                                <button className="signUp-submit-btn" type="submit" onClick={handleSubmit}>Kreiraj i nastavi &rarr;</button>
                        </form>

                        <div className="goToLogIn">
                                <p>Već imaš račun?</p>
                                <button type="button" className="goToLogIn-btn" onClick={() => navigate("/login")}>
                                        Prijavi se &rarr; 
                                </button>
                        </div>
                </div>
        )
}