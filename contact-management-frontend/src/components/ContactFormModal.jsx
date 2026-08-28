function ContactFormModal({
  newContact,
  setNewContact,
  saveContact,
  isEditing,
}) {
  return (
    <div
      className="modal fade"
      id="addContactModal"
      tabIndex="-1"
      aria-labelledby="addContactModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content shadow">

          <div className="modal-header bg-primary text-white">
            <h5 className="modal-title">
  {isEditing ? "Edit Contact" : "Add Contact"}
</h5>

            <button
              type="button"
              className="btn-close btn-close-white"
              data-bs-dismiss="modal"
            ></button>
          </div>

          <div className="modal-body">

            <div className="row">

              <div className="col-md-6 mb-3">
                <label>First Name</label>
                <input
                  className="form-control"
                  value={newContact.firstName}
                  onChange={(e) =>
                    setNewContact({
                      ...newContact,
                      firstName: e.target.value
                    })
                  }
                />
              </div>

              <div className="col-md-6 mb-3">
                <label>Last Name</label>
                <input
                  className="form-control"
                  value={newContact.lastName}
                  onChange={(e) =>
                    setNewContact({
                      ...newContact,
                      lastName: e.target.value
                    })
                  }
                />
              </div>

              <div className="col-md-6 mb-3">
                <label>Email</label>
                <input
                  type="email"
                  className="form-control"
                  value={newContact.email}
                  onChange={(e) =>
                    setNewContact({
                      ...newContact,
                      email: e.target.value
                    })
                  }
                />
              </div>

              <div className="col-md-6 mb-3">
                <label>Phone Number</label>
                <input
                  className="form-control"
                  value={newContact.phoneNumber}
                  onChange={(e) =>
                    setNewContact({
                      ...newContact,
                      phoneNumber: e.target.value
                    })
                  }
                />
              </div>

              <div className="col-md-6 mb-3">
                <label>Company</label>
                <input
                  className="form-control"
                  value={newContact.company}
                  onChange={(e) =>
                    setNewContact({
                      ...newContact,
                      company: e.target.value
                    })
                  }
                />
              </div>

              <div className="col-md-6 mb-3">
                <label>Job Title</label>
                <input
                  className="form-control"
                  value={newContact.jobTitle}
                  onChange={(e) =>
                    setNewContact({
                      ...newContact,
                      jobTitle: e.target.value
                    })
                  }
                />
              </div>

            </div>

          </div>

          <div className="modal-footer">

            <button
              className="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Cancel
            </button>

            <button
  className="btn btn-primary"
  onClick={saveContact}
  data-bs-dismiss="modal"
>
  {isEditing ? "Update Contact" : "Save Contact"}
</button>

          </div>

        </div>
      </div>
    </div>
  );
}

export default ContactFormModal;