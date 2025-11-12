import { LectureDropdown } from "./LectureDropdown";

export function Module() {
  return (
    <tr>
      <td className="lesson-name">
        <span>Dalmatinska kuhinja</span>
        <div className="dropbtn">
          <button>
            <img src="images/dropdown_icon.png" alt="dropdown icon" />
          </button>
        </div>
        <LectureDropdown />
      </td>
    </tr>
  );
}
