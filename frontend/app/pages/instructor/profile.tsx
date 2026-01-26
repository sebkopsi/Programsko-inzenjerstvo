import './new.css';
import pfp from './fame-media_no-prophile-picture-1-74336437.jpg';

export function InstructorProfilePage() {
  return (
    <section id="content">
      <h2>Instructor profile</h2>
      <hr/>
      <div>
        <h3>Profile picture</h3>
        <div className="imgFrame">
          <img 
            src={pfp} 
            alt="pfp" 
          />
        </div>
      </div>
      <hr/>

      <div>
        <h3>Identification document</h3>
        <a href="#">osobna_iskaznica.png</a>
      </div>
      <hr/>

      <div>
        <h3>Diplomas/certificates</h3>
        <a href="#">cert1 </a>
        <a href="#">cert2 </a>
        <a href="#">cert3 </a>
      </div>
    </section>
  );
}