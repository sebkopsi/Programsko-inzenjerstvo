import './stats.css';

export function StatsPage() {
  return (
    <section id="content">
      <div>
        <h1>App statistics</h1>
        <hr/>
        <h2>Number of users</h2>
        <div id="general">
          <div>
            <p>Active users</p>
            <span>567</span>
          </div>
          <div>
            <p>Total users</p>
            <span>888</span>
          </div>
          <div>
            <p>Verified users</p>
            <span>12</span>
          </div>
        </div>
        <h3>By difficulty</h3>
        <div className="strips">
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>Carnivoreverylongtag</span>
            <span>999</span>
          </div>
        </div>
        <h3>By tag</h3>
        <div className="strips">
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>small</span>
            <span>2</span>
          </div>
        </div>
        <hr/>
        <h2>Number of courses by tag</h2>
        <div className="strips">
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>Carnivore</span>
            <span>999</span>
          </div>
          <div>
            <span>small</span>
            <span>2</span>
          </div>
        </div>
      </div>
    </section>
  ) 
}