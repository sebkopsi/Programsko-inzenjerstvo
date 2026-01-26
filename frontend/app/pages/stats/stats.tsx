import './stats.css';
import { useLoaderData } from "react-router";

export function StatsPage() {
  const { stats } = useLoaderData();

  return (
    <section id="content">
      <div>
        <h1>App statistics</h1>
        <hr/>
        <h2>Number of users</h2>
        <div id="general">
          <div>
            <p>Active users</p>
            <span>{stats.numActiveUsers}</span>
          </div>
          <div>
            <p>Total users</p>
            <span>{stats.numTotalUsers}</span>
          </div>
          <div>
            <p>Verified users</p>
            <span>{stats.numVerifiedUsers}</span>
          </div>
        </div>
        <h3>By difficulty</h3>
        <div className="strips">
          {Object.entries(stats.numUsersByDifficulty).map(([diff, count]) => (
            <div>
              <span>{diff}</span>
              <span>{count as number}</span>
            </div>
          ))}
        </div>
        <h3>By tag</h3>
        <div className="strips">
          {Object.entries(stats.numUsersByTag).map(([tag, count]) => (
            <div>
              <span>{tag}</span>
              <span>{count as number}</span>
            </div>
          ))}
        </div>
        <hr/>
        <h2>Number of courses by tag</h2>
        <div className="strips">
          {Object.entries(stats.numCoursesByTag).map(([tag, count]) => (
            <div>
              <span>{tag}</span>
              <span>{count as number}</span>
            </div>
          ))}
        </div>
      </div>
    </section>
  ) 
}