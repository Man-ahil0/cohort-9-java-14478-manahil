function PaginationControls({
  page,
  totalPages,
  setPage
}) {
  return (
    <div className="d-flex justify-content-center align-items-center mt-4 gap-3">

      <button
        className="btn btn-outline-primary"
        disabled={page === 0}
        onClick={() => setPage(page - 1)}
      >
        ← Previous
      </button>

      <span className="fw-bold">
        Page {page + 1} of {totalPages}
      </span>

      <button
        className="btn btn-outline-primary"
        disabled={page + 1 >= totalPages}
        onClick={() => setPage(page + 1)}
      >
        Next →
      </button>

    </div>
  );
}

export default PaginationControls;