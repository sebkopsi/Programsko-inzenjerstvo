import Header from "./Header";
import Filters from "./Filters";
import Notes from "./Notes";
import PastCourses from "./PastCourses";
import UserData from "./UserData";
import Footer from "../signUp/Footer";
import "./user.css"

export function UserPage() {
        return (
                <>
                        <Header />
                        <UserData />
                        <Filters />
                        <PastCourses />
                        <Notes />
                        <Footer />
                </>
        )
}