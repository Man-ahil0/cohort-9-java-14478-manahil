function ContactDetailsModal({ contact }) {
  if (!contact) {
    return null;
  }

  return (
    <div
      className="modal fade"
      id="contactDetailsModal"
      tabIndex="-1"
      aria-labelledby="contactDetailsModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content shadow">

          <div className="modal-header bg-primary text-white">
            <h5 className="modal-title" id="contactDetailsModalLabel">
              Contact Details
            </h5>

            <button
              type="button"
              className="btn-close btn-close-white"
              data-bs-dismiss="modal"
            ></button>
          </div>

          <div className="modal-body">

            <div className="mb-3">
              <strong>First Name:</strong>
              <p className="mb-0">{contact.firstName}</p>
            </div>

            <div className="mb-3">
              <strong>Last Name:</strong>
              <p className="mb-0">{contact.lastName}</p>
            </div>

            <div className="mb-3">
              <strong>Email:</strong>
              <p className="mb-0">{contact.email}</p>
            </div>

            <div className="mb-3">
              <strong>Phone Number:</strong>
              <p className="mb-0">{contact.phoneNumber}</p>
            </div>

            <div className="mb-3">
              <strong>Company:</strong>
              <p className="mb-0">{contact.company}</p>
            </div>

            <div className="mb-3">
              <strong>Job Title:</strong>
              <p className="mb-0">{contact.jobTitle}</p>
            </div>

          </div>

          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
          </div>

        </div>
      </div>
    </div>
  );
}

export default ContactDetailsModal;