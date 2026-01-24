// pages/profile/userProfile.tsx
import "../styles/profile.css";
import type { UserData } from "~/routes/profile/userProfile"; // import the type

interface UserProfileProps {
  user: UserData;
}

export default function UserProfile({ user }: UserProfileProps) {
    console.log("User data in component:", user);
  return (
    <section id="content">
      {/* HEADER */}
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
            <span className="firstname">{user.firstname}</span>
            <span className="surname"> {user.surname}</span>
          </div>
          <span className="username">@{user.username}</span>
          <span className="email">{user.email}</span>
          <span className="date">
            Profile created: {new Date(user.createdAt).toLocaleDateString()}
          </span>
        </div>
      </section>

      {/* SKILL LEVEL */}
      <section className="profile-section">
        <label className="section-label">Skill level:</label>
        <select className="skill-select" value={user.skillLevel} disabled>
          <option value="beginner">Beginner</option>
          <option value="intermediate">Intermediate</option>
          <option value="advanced">Advanced</option>
        </select>
      </section>

      {/* COURSES HISTORY */}
      <section className="profile-section">
        <h2 className="section-title">Courses history:</h2>
        <div className="history-grid">
          {user.enrolledCoursesSet.length > 0 ? (
            user.enrolledCoursesSet.map((course) => (
              <div key={course.courseId} className="history-card">
                <img
                  src={`https://via.placeholder.com/180x120?text=Course+${course.courseId}`}
                  alt={`Course ${course.courseId}`}
                />
                <p>Course {course.courseId}</p>
                <p>Progress: {course.completionPercentage}%</p>
                {course.endedAt && (
                  <p>Ended at: {new Date(course.endedAt).toLocaleDateString()}</p>
                )}
              </div>
            ))
          ) : (
            <p>No courses enrolled yet.</p>
          )}
        </div>
      </section>

      {/* TAGS */}
      <section className="profile-section">
        <h2 className="section-title">Tags:</h2>
        <div className="chip-group">
          {user.tags.length > 0 ? (
            user.tags.map((tag, index) => (
              <button key={index} className="chip">
                {tag.name} {tag.preferred ? "(preferred)" : ""}
              </button>
            ))
          ) : (
            <p>No tags added yet.</p>
          )}
        </div>
      </section>

      {/* LOG OUT BUTTON */}
      <section className="log-out-section">
        <button className="log-out-button" onClick={() => {
          document.cookie = "jwt=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
          window.location.href = "/login";
        }}>
          Log Out
        </button>
      </section>
    </section>
  );
}
