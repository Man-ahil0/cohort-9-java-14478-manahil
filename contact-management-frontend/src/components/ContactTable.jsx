function ContactTable({
  contacts,
  onEdit,
  onDelete,
  onView,
}) {

  return (
    <>

      <thead>
        <tr>
          <th>Contact</th>
          <th>Email</th>
          <th>Phone</th>
          <th>Company</th>
          <th>Job Title</th>
          <th className="text-end">Actions</th>
        </tr>
      </thead>

      <tbody>

        {contacts.length === 0 ? (

          <tr>
            <td
              colSpan="6"
              className="empty-contacts"
            >

              <div className="empty-icon">
                👤
              </div>

              <strong>
                No contacts found
              </strong>

              <span>
                Add a contact to get started.
              </span>

            </td>
          </tr>

        ) : (

          contacts.map((contact) => (

            <tr key={contact.id}>

              {/* Contact */}
              <td>

                <div className="contact-name">

                  <div className="contact-avatar">
                    {contact.firstName?.charAt(0)}
                    {contact.lastName?.charAt(0)}
                  </div>

                  <div className="contact-name-details">

                    <strong>
                      {contact.firstName} {contact.lastName}
                    </strong>

                  </div>

                </div>

              </td>

              {/* Email */}
              <td>
                {contact.email}
              </td>

              {/* Phone */}
              <td>
                {contact.phoneNumber}
              </td>

              {/* Company */}
              <td>
                {contact.company || "—"}
              </td>

              {/* Job Title */}
              <td>
                {contact.jobTitle || "—"}
              </td>

              {/* Actions */}
              <td>

                <div className="contact-actions">

                  <button
                    type="button"
                    className="action-btn btn-view"
                    onClick={() => onView(contact)}
                  >
                    View
                  </button>

                  <button
                    type="button"
                    className="action-btn btn-edit"
                    onClick={() => onEdit(contact)}
                  >
                    Edit
                  </button>

                  <button
                    type="button"
                    className="action-btn btn-delete"
                    onClick={() => onDelete(contact)}
                  >
                    Delete
                  </button>

                </div>

              </td>

            </tr>

          ))

        )}

      </tbody>

    </>
  );
}

export default ContactTable;