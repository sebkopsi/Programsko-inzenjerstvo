import { Header } from '../Header'
import { Search } from './Search';
import { Courses } from './Courses';
import { Footer } from './Footer'

export function AllCoursesPage() {
  return(
    <>
      <Header />
      <div className="cambo-regular">
        <Search />
        <Courses />
        <Footer />
      </div>
    </>
  )
}
