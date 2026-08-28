function Filter({
  companyFilter,
  setCompanyFilter,
  jobFilter,
  setJobFilter,
  applyFilters,
  clearFilters,
}) {
  return (
    <div className="row mb-4">

      <div className="col-md-4">
        <input
          className="form-control"
          placeholder="Filter by Company"
          value={companyFilter}
          onChange={(e) => setCompanyFilter(e.target.value)}
        />
      </div>

      <div className="col-md-4">
        <input
          className="form-control"
          placeholder="Filter by Job Title"
          value={jobFilter}
          onChange={(e) => setJobFilter(e.target.value)}
        />
      </div>

      <div className="col-md-2">
        <button
          className="btn btn-dark w-100"
          onClick={applyFilters}
        >
          Filter
        </button>
      </div>

      <div className="col-md-2">
        <button
          className="btn btn-secondary w-100"
          onClick={clearFilters}
        >
          Clear
        </button>
      </div>

    </div>
  );
}

export default Filter;