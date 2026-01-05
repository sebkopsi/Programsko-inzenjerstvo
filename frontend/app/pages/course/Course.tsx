import './styles/allCourses.css'
import './styles/course.css'

import { Header } from './Header'
import { CourseDescription } from './CourseDescription'
import { Subheader } from './Subheader'
import { Modules } from './Modules'
import { Sidebar } from './Sidebar'

export function Course() {
  return(
    <>
      <Sidebar />
      {/* <Header /> */}
      <div className="cambo-regular main">
        <Subheader />
        <h1 className="londrina-solid-regular" id="courseTitle">Mediteranska kuhinja</h1>
        <CourseDescription />
        <Modules />
      </div>  
    </>
  )
}