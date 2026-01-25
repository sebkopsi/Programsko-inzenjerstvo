import { useLoaderData } from "react-router";
import 'any-date-parser';
import pfp from '../instructor/fame-media_no-prophile-picture-1-74336437.jpg';

import './request.css';

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
        <button className="deny">Deny</button>
      </div>
    </div>
  );
}

export function RequestPage() {
  const { requestInfo } = useLoaderData();
  const { reqId, title, content, type, sentByUserId, reportedUserId, targetCourseId, status, createdAt } = requestInfo.data;
  const dateTime = (new Date(createdAt)).toLocaleString();

  const userName = "Darko2303";
  const courseName = "Mediteranska kuhinja";

  return (
    <section id="content">
      <div id="header">
        <div id="title">
          <a href="/adminpanel/inbox"><h2>requests</h2></a>
          <h2> &gt; {reqId} ({type})</h2>
        </div>
        <div id="prevNext">
          <button>Previous</button>
          <button>Next</button>
        </div>
      </div>
      <hr/>
      <h3>{title}</h3>
      <div className="request">
        <p>{content}</p>
        <div>
          {type == "promoteInstructor" && 
            <div className="imgFrame">
              <img src={pfp} alt="pfp" />
            </div>}
          <p><b>Sent by:</b> {userName} ({sentByUserId})</p>
          <p><b>Created at:</b> {dateTime}</p>
          {type == "promoteInstructor" && <p><b>Identification document:</b> <a href="">driver's licence</a></p>}
          {type == "report" && <p><b>Reported user:</b> {userName} ({reportedUserId})</p>}
          {type == "updateCourse" && <p><b>Target course:</b> {courseName} ({targetCourseId})</p>}
        </div>
      </div>
      <hr/>
      <div className="request">
        <div>
          {type == "promoteInstructor" && <>
            <p><b>Certificates/diplomas:</b></p>
            <ul>
              <li><a href="#">diploma</a></li>
              <li><a href="#">certifikat 1</a></li>
              <li><a href="#">certifikat 2</a></li>
            </ul>
          </>}
        </div>
        <div>
          {type == "report" && <>
            <button className="deny">Suspend user</button>
            <button>Discard</button>
          </>}
          {type == "promoteInstructor" && <>
            <button id="approve">Approve</button>
            <button className="deny">Deny</button>
          </>}
          {type == "updateCourse" && <>
            <button>Edit course</button>
            <button>Discard</button>
          </>}
        </div>
      </div>
    </section>
  );
}
