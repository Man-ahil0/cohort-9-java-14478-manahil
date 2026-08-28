function SearchBar({
  searchType,
  setSearchType,
  searchText,
  setSearchText,
  searchContacts,
}) {
  return (
    <div className="row mb-3">

      <div className="col-md-3">
        <select
          className="form-select"
          value={searchType}
          onChange={(e) => setSearchType(e.target.value)}
        >
          <option value="firstname">First Name</option>
          <option value="lastname">Last Name</option>
          <option value="email">Email</option>
          <option value="company">Company</option>
          <option value="jobtitle">Job Title</option>
        </select>
      </div>

      <div className="col-md-7">
        <input
          className="form-control"
          placeholder="Search..."
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
        />
      </div>

      <div className="col-md-2">
        <button
          className="btn btn-primary w-100"
          onClick={searchContacts}
        >
          Search
        </button>
      </div>

    </div>
  );
}

export default SearchBar;