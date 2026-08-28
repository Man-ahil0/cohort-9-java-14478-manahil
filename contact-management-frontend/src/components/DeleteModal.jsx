function DeleteModal({ selectedContact, deleteContact }) {
  if (!selectedContact) {
    return null;
  }

  return (
    <div
      className="modal fade"
      id="deleteContactModal"
      tabIndex="-1"
      aria-labelledby="deleteContactModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content shadow">

          <div className="modal-header bg-danger text-white">
            <h5 className="modal-title" id="deleteContactModalLabel">
              Delete Contact
            </h5>

            <button
              type="button"
              className="btn-close btn-close-white"
              data-bs-dismiss="modal"
            ></button>
          </div>

          <div className="modal-body">
            <p className="mb-2">
              Are you sure you want to delete this contact?
            </p>

            <strong>
              {selectedContact.firstName} {selectedContact.lastName}
            </strong>
          </div>

          <div className="modal-footer">

            <button
              type="button"
              className="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Cancel
            </button>

            <button
              type="button"
              className="btn btn-danger"
              onClick={deleteContact}
              data-bs-dismiss="modal"
            >
              Delete
            </button>

          </div>

        </div>
      </div>
    </div>
  );
}

export default DeleteModal;