import { useLoaderData } from "react-router";
import 'any-date-parser';

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
          <a href="#"><h2>requests</h2></a>
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
          <p><b>Sent by:</b> {userName} ({sentByUserId})</p>
          <p><b>Created at:</b> {dateTime}</p>
          {type == "report" && <p><b>Reported user:</b> {userName} ({reportedUserId})</p>}
          {type == "updateCourse" && <p><b>Target course:</b> {courseName} ({targetCourseId})</p>}
        </div>
      </div>
      <hr/>
      <div className="request">
        <div>
          {type == "promoteInstructor" && <>
            <p><b>Attachments:</b></p>
            <a href="#">diploma</a><br/>
            <a href="#">osobna iskaznica</a><br/>
            <a href="#">vozacka dozvola</a>
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
