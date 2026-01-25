import "../styles/profile.css";
import "../styles/components.css";
import type { UserData } from "~/routes/profile/userProfile";
import { Card } from "../components/card";
import { useState } from "react";

type UserProfileProps = {
  user: UserData;
  jwt: string;
}

const SKILL_LEVEL_MAP: Record<string, number> = {
  beginner: 1,
  intermediate: 2,
  advanced: 3,
};

export default function UserProfile({ user, jwt }: UserProfileProps) {
  const [editing, setEditing] = useState(false);
  const [saving, setSaving] = useState(false);

  const [firstname, setFirstname] = useState(user.firstname);
  const [surname, setSurname] = useState(user.surname);
  const [email, setEmail] = useState(user.email);
  const [username, setUsername] = useState(user.username);
  const [skillLevel, setSkillLevel] = useState(user.skillLevel);

  const saveProfile = async () => {
    setSaving(true);

    try {
      const requests: Promise<Response>[] = [];

      if (firstname !== user.firstname || surname !== user.surname) {
        requests.push(
          fetch("http://localhost:8890/user/my/name", {
            method: "PATCH",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify({ firstname, surname }),
          })
        );
      }

      if (email !== user.email) {
        requests.push(
          fetch("http://localhost:8890/user/my/email", {
            method: "PATCH",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify({ email }),
          })
        );
      }

      if (username !== user.username) {
        requests.push(
          fetch("http://localhost:8890/user/my/username", {
            method: "PATCH",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify({ username }),
          })
        );
      }

      if (skillLevel !== user.skillLevel) {
        requests.push(
          fetch("http://localhost:8890/user/my/skill-level", {
            method: "PATCH",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${jwt}`,
            },
            body: JSON.stringify(SKILL_LEVEL_MAP[skillLevel]),
          })
        );
      }

      if (requests.length === 0) {
        setEditing(false);
        return;
      }

      const results = await Promise.all(
      requests.map(async (req) => {
      const r = await req;
        if (!r.ok) {
         const text = await r.text();
          console.error("PATCH failed response:", text);
      }
        if(r.ok){
          console.log("PATCH succeeded response");
        }

     return r;
    })
  );
      const failed = results.filter((r) => !r.ok);

      if (failed.length > 0) {
        console.error("Failed PATCH responses:", failed);
        alert("Some updates failed. Check console.");
        return;
      }

      alert("Profile updated successfully!");
      setEditing(false);
    } catch (err) {
      console.error("SAVE PROFILE ERROR:", err);
      alert("Failed to save profile");
    } finally {
      setSaving(false);
    }
  };

  const cancelEdit = () => {
-  setFirstname(user.firstname);
  setSurname(user.surname);
  setEmail(user.email);
  setUsername(user.username);
  setSkillLevel(user.skillLevel);

  setEditing(false); 
};


  return (
    <section id="content">
      <section className="profile-header">
        <div className="avatar-wrapper">
          <div className="avatar">
            <img
              className="avatar-image"
              src="/images/profile_icon.png"
              alt="Avatar"
            />
          </div>
        </div>

        <div className="profile-info">
          <div className="fullname">
            {editing ? (
              <>
                <input
                  className="firstname"
                  value={firstname}
                  onChange={(e) => setFirstname(e.target.value)}
                />
                <input
                  className="surname"
                  value={surname}
                  onChange={(e) => setSurname(e.target.value)}
                />
              </>
            ) : (
              <>
                <span className="firstname">{firstname}</span>
                <span className="surname">{surname}</span>
              </>
            )}
          </div>

          {editing ? (
            <input
              className="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          ) : (
            <span className="username">@{username}</span>
          )}

          {editing ? (
            <input
              className="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          ) : (
            <span className="email">{email}</span>
          )}

          <span className="date">
            Profile created:{" "}
            {new Date(user.createdAt).toLocaleDateString()}
          </span>
        </div>
      </section>

      <section className="profile-section">
        <label className="section-label">Skill level:</label>
        <select
          className="skill-select"
          value={skillLevel}
          disabled={!editing}
          onChange={(e) => setSkillLevel(e.target.value)}
        >
          <option value="beginner">Beginner</option>
          <option value="intermediate">Intermediate</option>
          <option value="advanced">Advanced</option>
        </select>
      </section>

   <section className="profile-section">
  <h2 className="section-title">Courses history:</h2>
  <div className="history-grid">
    {user.enrolledCoursesSet.length > 0 ? (
      user.enrolledCoursesSet.map((course) => {
        const enrolledAt = new Date(course.enrolledAt).toLocaleDateString();
        const endedAt = course.endedAt
          ? new Date(course.endedAt).toLocaleDateString()
          : "-";

        const descElements = [
          <div key="status">Status: {course.status}</div>,
          <div key="progress">Progress: {course.completionPercentage}%</div>,
          <div key="enrolledAt">Enrolled At: {enrolledAt}</div>,
          <div key="endedAt">Ended At: {endedAt}</div>,
        ];

        if (course.certificateId !== null) {
          descElements.push(
            <div key="certificate">Certificate ID: {course.certificateId}</div>
          );
        }

        return (
          <Card
            key={course.courseId}
            link={`/course/${course.courseId}`}
            name={`Course ${course.courseId}`}
            desc={descElements} 
            tags={[]}
            type="course"
          />
        );
      })
    ) : (
      <p>No courses enrolled yet.</p>
    )}
  </div>
</section>

<section className="profile-section">
  <h2 className="section-title">Tags:</h2>
  <div className="chip-group">
    {user.tags.length > 0 ? (
      user.tags.map((tag, index) => (
        <button
          key={index}
          className={`chip ${tag.preferred ? "preferred" : "not-preferred"}`}
        >
          {tag.name}
        </button>
      ))
    ) : (
      <p>No tags added yet.</p>
    )}
  </div>
</section>

      <section className="edit-section">
  {editing ? (
    <>
      <button
        className="save-button"
        disabled={saving}
        onClick={saveProfile}
      >
        Save
      </button>
      <button
        className="cancel-button"
        disabled={saving}
        onClick={cancelEdit}
      >
        Cancel
      </button>
    </>
  ) : (
    <button
      className="edit-button"
      onClick={() => setEditing(true)}
    >
      Edit Profile
    </button>
  )}
</section>


     {!editing && (
  <section className="logout-section">
    <a href="/logout">
      <button className="logout-button">LOG OUT</button>
    </a>
  </section>
)}

        </section>

  );
  
}
