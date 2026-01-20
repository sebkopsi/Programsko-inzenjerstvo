import './individualRequest.css'
import id from './EuropeanIdCard2_A-1-4204201529.jpg'

export function IndividualRequest() {
  return (
    <div id="page">
      <h2 id="title">Request</h2>
      <hr/>
      <div id="request">
        <div id="image">
          <img src={id} alt="identification card"/>
          <span>Identifikacijski dokument</span>
        </div>
        <div id="text">
          <h3>Diploma</h3>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod 
            tempor incididunt ut labore et dolore magna aliqua.
          </p>
          <p>
            Ut enim ad minim veniam, 
            quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo 
            consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse 
            cillum dolore eu fugiat nulla pariatur. 
          </p>
          <p>
             Excepteur sint occaecat cupidatat 
            non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
          </p>
        </div>
      </div>
      <hr/>
      <div id="buttons">
        <button id="approve">Approve</button>
        <button id="deny">Deny</button>
      </div>
    </div>
  );
}
