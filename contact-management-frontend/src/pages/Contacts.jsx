import { useEffect, useState } from "react";
import API from "../api/axiosConfig";

import ContactTable from "../components/ContactTable";
import ContactFormModal from "../components/ContactFormModal";
import DeleteModal from "../components/DeleteModal";
import ContactDetailsModal from "../components/ContactDetailsModal";
import PaginationControls from "../components/PaginationControls";

import * as bootstrap from "bootstrap";

function Contacts() {
  const [contacts, setContacts] = useState([]);

  const [newContact, setNewContact] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    company: "",
    jobTitle: "",
  });

  const [isEditing, setIsEditing] = useState(false);
  const [selectedContactId, setSelectedContactId] = useState(null);

  const [selectedContact, setSelectedContact] = useState(null);

  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const [search, setSearch] = useState("");
  const [filter, setFilter] = useState("all");

  // =========================
  // Fetch Contacts
  // =========================

  const fetchContacts = async () => {
    try {
      // No search/filter → normal paginated contacts
      if (!search.trim() && filter === "all") {
        const response = await API.get(
          `/contacts?page=${page}&size=5`
        );

        setContacts(response.data.content);
        setTotalPages(response.data.totalPages);

        return;
      }

      let response;

      // =========================
      // Search
      // =========================

      if (search.trim()) {
        const searchValue = encodeURIComponent(search.trim());

        if (filter === "firstName") {
          response = await API.get(
            `/contacts/search/firstname?firstName=${searchValue}`
          );
        } else if (filter === "lastName") {
          response = await API.get(
            `/contacts/search/lastname?lastName=${searchValue}`
          );
        } else if (filter === "email") {
          response = await API.get(
            `/contacts/search/email?email=${searchValue}`
          );
        } else if (filter === "company") {
          response = await API.get(
            `/contacts/search/company?company=${searchValue}`
          );
        } else if (filter === "jobTitle") {
          response = await API.get(
            `/contacts/search/jobtitle?jobTitle=${searchValue}`
          );
        } else {
          // Search across fields
          const [
            firstNameResponse,
            lastNameResponse,
            emailResponse,
            companyResponse,
            jobTitleResponse,
          ] = await Promise.all([
            API.get(
              `/contacts/search/firstname?firstName=${searchValue}`
            ),
            API.get(
              `/contacts/search/lastname?lastName=${searchValue}`
            ),
            API.get(
              `/contacts/search/email?email=${searchValue}`
            ),
            API.get(
              `/contacts/search/company?company=${searchValue}`
            ),
            API.get(
              `/contacts/search/jobtitle?jobTitle=${searchValue}`
            ),
          ]);

          const allResults = [
            ...firstNameResponse.data,
            ...lastNameResponse.data,
            ...emailResponse.data,
            ...companyResponse.data,
            ...jobTitleResponse.data,
          ];

          // Remove duplicate contacts
          const uniqueResults = Array.from(
            new Map(
              allResults.map((contact) => [
                contact.id,
                contact,
              ])
            ).values()
          );

          setContacts(uniqueResults);
          setTotalPages(1);

          return;
        }

        setContacts(response.data);
        setTotalPages(1);

        return;
      }

      // =========================
      // Filter
      // =========================

      if (filter === "company") {
        response = await API.get(
          `/contacts/filter/company?company=`
        );
      } else if (filter === "jobTitle") {
        response = await API.get(
          `/contacts/filter/jobtitle?jobTitle=`
        );
      }

      if (response) {
        setContacts(response.data);
        setTotalPages(1);
      }
    } catch (error) {
      console.error("Error fetching contacts:", error);
      setContacts([]);
      setTotalPages(0);
    }
  };

  useEffect(() => {
    fetchContacts();
  }, [page, search, filter]);

  // =========================
  // Add / Update Contact
  // =========================

  const saveContact = async () => {
    try {
      if (isEditing) {
        await API.put(
          `/contacts/${selectedContactId}`,
          newContact
        );

        alert("Contact updated successfully!");
      } else {
        await API.post("/contacts", newContact);

        alert("Contact added successfully!");
      }

      fetchContacts();

      setNewContact({
        firstName: "",
        lastName: "",
        email: "",
        phoneNumber: "",
        company: "",
        jobTitle: "",
      });

      setIsEditing(false);
      setSelectedContactId(null);
    } catch (error) {
      console.error(error);

      alert(
        error.response?.data?.message ||
          error.response?.data ||
          error.message
      );
    }
  };

  // =========================
  // Edit Contact
  // =========================

  const editContact = (contact) => {
    setIsEditing(true);
    setSelectedContactId(contact.id);

    setNewContact({
      firstName: contact.firstName,
      lastName: contact.lastName,
      email: contact.email,
      phoneNumber: contact.phoneNumber,
      company: contact.company,
      jobTitle: contact.jobTitle,
    });

    const modalElement =
      document.getElementById("addContactModal");

    const modal =
      bootstrap.Modal.getOrCreateInstance(modalElement);

    modal.show();
  };

  // =========================
  // Add Contact
  // =========================

  const openAddContact = () => {
    setIsEditing(false);
    setSelectedContactId(null);

    setNewContact({
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
      company: "",
      jobTitle: "",
    });
  };

  // =========================
  // Delete - Open Modal
  // =========================

  const openDeleteModal = (contact) => {
    setSelectedContact(contact);

    const modalElement =
      document.getElementById("deleteContactModal");

    const modal =
      bootstrap.Modal.getOrCreateInstance(modalElement);

    modal.show();
  };

  // =========================
  // Delete Contact
  // =========================

  const deleteContact = async () => {
    if (!selectedContact) {
      return;
    }

    try {
      await API.delete(
        `/contacts/${selectedContact.id}`
      );

      alert("Contact deleted successfully!");

      setSelectedContact(null);

      fetchContacts();
    } catch (error) {
      console.error(error);

      alert(
        error.response?.data?.message ||
          error.response?.data ||
          error.message
      );
    }
  };

  // =========================
  // View Contact
  // =========================

  const viewContact = (contact) => {
    setSelectedContact(contact);

    const modalElement =
      document.getElementById("contactDetailsModal");

    const modal =
      bootstrap.Modal.getOrCreateInstance(modalElement);

    modal.show();
  };

  // =========================
  // Search / Filter Change
  // =========================

  const handleSearchChange = (e) => {
    setSearch(e.target.value);
    setPage(0);
  };

  const handleFilterChange = (e) => {
    setFilter(e.target.value);
    setSearch("");
    setPage(0);
  };

  return (
    <div className="contacts-page">
      <div className="container page-container">

        {/* Page Header */}

        <div className="contacts-header">
          <div>
            <p className="contacts-eyebrow">
              CONTACT MANAGEMENT
            </p>

            <h1 className="contacts-title">
              Your Contacts
            </h1>

            <p className="contacts-subtitle">
              Manage, organize and keep track of your contacts.
            </p>
          </div>

          <button
            className="btn btn-contact-primary"
            data-bs-toggle="modal"
            data-bs-target="#addContactModal"
            onClick={openAddContact}
          >
            <span className="add-icon">+</span>
            Add Contact
          </button>
        </div>

        {/* Contacts Table */}

        <div className="contacts-table-card">

          <div className="contacts-table-header">

            <div>
              <h5>All Contacts</h5>

              <p>
                View and manage your saved contacts
              </p>
            </div>

          </div>

          {/* Search & Filter */}

          <div className="contacts-tools">

  {/* Search */}
  <div className="contact-search-box">
    <label>Search Contacts</label>

    <div className="search-input-wrapper">
      <span className="search-icon">⌕</span>

      <input
        type="text"
        className="form-control"
        placeholder="Search by name, email, company..."
        value={search}
        onChange={handleSearchChange}
      />

      {search && (
        <button
          type="button"
          className="clear-search"
          onClick={() => {
            setSearch("");
            setPage(0);
          }}
        >
          ×
        </button>
      )}
    </div>
  </div>


  {/* Filter */}
  <div className="contact-filter-box">
    <label>Filter By</label>

    <select
      className="form-select"
      value={filter}
      onChange={handleFilterChange}
    >
      <option value="all">All Contacts</option>
      <option value="firstName">First Name</option>
      <option value="lastName">Last Name</option>
      <option value="email">Email</option>
      <option value="jobTitle">Job Title</option>
    </select>
  </div>

</div>

          {/* Table */}

          <div className="table-responsive">

            <table className="table contacts-table">

              <ContactTable
                contacts={contacts}
                onEdit={editContact}
                onDelete={openDeleteModal}
                onView={viewContact}
              />

            </table>

          </div>

        </div>

        {/* Pagination */}

        {!search.trim() && filter === "all" && (
          <PaginationControls
            page={page}
            totalPages={totalPages}
            setPage={setPage}
          />
        )}

        {/* Modals */}

        <ContactFormModal
          newContact={newContact}
          setNewContact={setNewContact}
          saveContact={saveContact}
          isEditing={isEditing}
        />

        <DeleteModal
          selectedContact={selectedContact}
          deleteContact={deleteContact}
        />

        <ContactDetailsModal
          contact={selectedContact}
        />

      </div>
    </div>
  );
}

export default Contacts;