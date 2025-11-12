import profileIcon from "../../../public/images/profile_icon.png"
import email from "../../../public/images/mail_icon.png"

export default function UserData() {
        return (
                <>
                        <div className="user-data">
                                <div className="border">
                                        <img src={profileIcon} alt="profile-icon" />
                                </div>
                                <span>
                                        <h2>Ivan Horvat</h2>
                                        <div className="email">
                                                <img src={email} alt="mail-icon" />
                                                <a href="mailto:ivan.horvat@gmail.com">ivan.horvat@gmail.com</a>
                                        </div>
                                </span>
                        </div>
                </>
        )
}