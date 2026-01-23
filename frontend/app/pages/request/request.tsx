import './request.css'
import { useEffect, useState } from "react";

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

export function Request() {
  const [data, setData] = useState([]); 
  const [error, setError] = useState<string|null>(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        const res = await fetch("http://localhost:8890/admin/request/2");
        if (!res.ok) throw new Error(String(res.status));
        setData(await res.json());
      } catch (e) {
        if (e instanceof Error) {
          setError(e.message);
        } else {
          setError("Unknown error");
        }
      }
    };
    loadData();
  }, []);

  if (error) return <p>Error: {error}</p>;

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
  const reqID = 1239;

  return (
    <section id="content">
      <div id="header">
        <div id="title">
          <a href="#"><h2>requests</h2></a>
          <h2> &gt; {reqID} ({type})</h2>
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
          <p><b>Sent by:</b> {userName} ({userID})</p>
          <p><b>Created at:</b> {dateTime}</p>
          {type == "report" && <p><b>Reported user:</b> {userName} ({userID})</p>}
          {type == "updateCourse" && <p><b>Target course:</b> {courseName} ({courseID})</p>}
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
