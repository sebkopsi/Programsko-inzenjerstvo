import { CourseTile } from './CourseTile'

export function Courses() {
  return(
    <div id="courses">
      <h2 className="cambo-regular">Prijedlozi za Vas:</h2>
      <div id="courseList">
        <CourseTile />
        <CourseTile />
        <CourseTile />
      </div>
    </div>
  )
}