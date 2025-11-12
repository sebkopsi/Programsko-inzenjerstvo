export function Search() {
  return (
    <div id="search">
      <input type="text" placeholder="Pretraži..." />
      <img id="search-icon" src="/images/search_icon.png" alt="search icon" />
      <div id="filters">
        <button className="londrina-solid-regular filter-button">FILTER</button>
      </div>
    </div>
  );
}
