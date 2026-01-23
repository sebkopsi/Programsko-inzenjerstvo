import './request.css'
import id from './EuropeanIdCard2_A-1-4204201529.jpg'

const types = ["promoteInstructor", "report", "updateCourse"]

function Footer() {
  return (
    <div className="request">
      <div>
        <p>Attachments:</p>
        <a href="#">diploma</a><br/>
        <a href="#">osobna iskaznica</a><br/>
        <a href="#">vozacka dozvola</a>
      </div>
      <div id="buttons">
        <button id="approve">Approve</button>
        <button id="deny">Deny</button>
      </div>
    </div>
  );
}

export function Request() {
  const userID = 22345;
  const userName = "Darko2303";
  const title = "Request title here";
  const content = `
    Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
    Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
    Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.`;
  const type = types[2];
  const dateTime = "12.1.2022. 13:00";
  const courseName = "Mediteranska kuhinja";
  const courseID = 676767;

  return (
    <section id="content">
      <h2 id="title">requests &gt; {userName} (ID {userID}) </h2>
      <hr/>
      <h3>{title}</h3>
      <div className="request">
        <p>{content}</p>
        <div>
          <p><b>Sent by:</b> {userName} ({userID})</p>
          <p><b>Created at:</b> {dateTime}</p>
          {type == "report" && <p><b>Reported user:</b> {userName} ({userID})</p>}
          {type == "updateCourse" && <p><b>Target course:</b> {courseName} ({courseID})</p>}
        </div>
      </div>
      <hr/>
      {type == "promoteInstructor" && <Footer/>}
    </section>
  );
}
