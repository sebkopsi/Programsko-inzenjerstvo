import { Header } from '../Header'
import { CourseDescription } from './CourseDescription'
import { Subheader } from './Subheader'
import { Modules } from './Modules'

export function CoursePage() {
  return(
    <>
      <Header />
      <div className="cambo-regular" id="site">
        <Subheader />
        <h1 className="londrina-solid-regular" id="courseTitle">Mediteranska kuhinja</h1>
        <CourseDescription />
        <Modules />
      </div>  
    </>
  )
}