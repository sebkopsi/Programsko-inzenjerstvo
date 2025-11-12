import { Module } from "./Module";

export function Modules() {
  return (
    <table className="cambo-regular" id="modules">
      <tr>
        <th id="lessons">MODULI</th>
      </tr>
      <Module />
      <Module />
      <Module />
      <Module />
    </table>
  );
}
