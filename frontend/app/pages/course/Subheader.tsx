export function Subheader() {
  return (
    <div className="cambo-regular" id="subheader">
      <div id="autor">
        <span>Autor:</span>
        <div id="profilePic">
          <img src="/images/profile_icon.png" alt="profile picture" />
        </div>
        <span>Pero Perica</span>
      </div>
      {/* <span>Objavljeno 6.9.2025.</span> */}
      <span>Razina težine: srednji</span>
      <span>Prosječna ocjena: 3.2</span>
    </div>
  );
}