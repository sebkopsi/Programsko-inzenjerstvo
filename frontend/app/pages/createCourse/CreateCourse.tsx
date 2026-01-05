import './createCourse.css'

export function CreateCourse() {
  return (
    <form>
      <label htmlFor="name">Course name</label><br/>
      <input type="text" id="name" name="name" /><br/><br/>

      <label htmlFor="desc">Course description</label><br/>
      <textarea id="desc" name="description"></textarea><br/>
      
      <p>Choose course difficulty:</p>
      <div>
        <input type="radio" id="easy" name="difficulty" value="easy"/>
        <label htmlFor="easy">easy</label><br/>
        <input type="radio" id="medium" name="difficulty" value="medium"/>
        <label htmlFor="medium">medium</label><br/>
        <input type="radio" id="hard" name="difficulty" value="hard"/>
        <label htmlFor="hard">hard</label><br/><br/>
      </div>

      <input className="submit-button" type="submit" value="Submit"/>
    </form>
  )
}