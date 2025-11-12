import { LectureTile } from "./LectureTile";

export function LectureDropdown() {
  return (
    <div className="lectures" id="dalmatinska">
      <h3>LEKCIJE</h3>
      <div className="lectureList">
        <LectureTile />
        <LectureTile />
        <LectureTile />
        <LectureTile />
        <LectureTile />
      </div>
    </div>
  );
}
