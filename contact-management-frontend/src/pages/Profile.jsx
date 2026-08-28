import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axiosConfig";

function Profile() {

  const navigate = useNavigate();

  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const [isEditing, setIsEditing] = useState(false);

  const [profileData, setProfileData] = useState({
    firstName: "",
    lastName: "",
    phoneNumber: "",
    profilePicture: "",
  });

  const [showPasswordModal, setShowPasswordModal] = useState(false);

  const [passwordData, setPasswordData] = useState({
    currentPassword: "",
    newPassword: "",
  });

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");


  // =========================
  // Fetch Profile
  // =========================

  useEffect(() => {
    fetchProfile();
  }, []);


  const fetchProfile = async () => {

    try {

      const response = await API.get("/users/me");

      setUser(response.data);

      setProfileData({
        firstName: response.data.firstName || "",
        lastName: response.data.lastName || "",
        phoneNumber: response.data.phoneNumber || "",
        profilePicture: response.data.profilePicture || "",
      });

    } catch (error) {

      console.error(error);

      setError(
        error.response?.data?.message ||
        "Unable to load profile."
      );

    } finally {

      setLoading(false);

    }

  };


  // =========================
  // Profile Input
  // =========================

  const handleProfileChange = (e) => {

    setProfileData({
      ...profileData,
      [e.target.name]: e.target.value,
    });

  };


  // =========================
  // Profile Picture
  // =========================

  const handleProfilePicture = (e) => {

    const file = e.target.files[0];

    if (!file) {
      return;
    }

    // Convert image to Base64
    const reader = new FileReader();

    reader.onloadend = () => {

      setProfileData({
        ...profileData,
        profilePicture: reader.result,
      });

    };

    reader.readAsDataURL(file);

  };


  // =========================
  // Save Profile
  // =========================

  const saveProfile = async (e) => {

    e.preventDefault();

    setMessage("");
    setError("");

    try {

      const response = await API.put(
        "/users/me",
        profileData
      );

      setUser(response.data);

      setProfileData({
        firstName: response.data.firstName || "",
        lastName: response.data.lastName || "",
        phoneNumber: response.data.phoneNumber || "",
        profilePicture: response.data.profilePicture || "",
      });

      setIsEditing(false);

      setMessage(
        "Profile updated successfully!"
      );

      // Tell Navbar to refresh
      window.dispatchEvent(
        new Event("profileUpdated")
      );

    } catch (error) {

      console.error(error);

      setError(
        error.response?.data?.message ||
        error.response?.data ||
        "Failed to update profile."
      );

    }

  };


  // =========================
  // Cancel Editing
  // =========================

  const cancelEditing = () => {

    setProfileData({
      firstName: user?.firstName || "",
      lastName: user?.lastName || "",
      phoneNumber: user?.phoneNumber || "",
      profilePicture: user?.profilePicture || "",
    });

    setIsEditing(false);

    setError("");
    setMessage("");

  };


  // =========================
  // Password Input
  // =========================

  const handlePasswordChange = (e) => {

    setPasswordData({
      ...passwordData,
      [e.target.name]: e.target.value,
    });

  };


  // =========================
  // Change Password
  // =========================

  const changePassword = async (e) => {

    e.preventDefault();

    setMessage("");
    setError("");

    try {

      await API.put(
        "/users/change-password",
        passwordData
      );

      setMessage(
        "Password changed successfully!"
      );

      setPasswordData({
        currentPassword: "",
        newPassword: "",
      });

      setShowPasswordModal(false);

    } catch (error) {

      console.error(error);

      setError(
        error.response?.data?.message ||
        error.response?.data ||
        "Failed to change password."
      );

    }

  };


  // =========================
  // Logout
  // =========================

  const handleLogout = () => {

    const confirmed = window.confirm(
      "Are you sure you want to logout?"
    );

    if (!confirmed) {
      return;
    }

    localStorage.removeItem("token");

    window.dispatchEvent(
      new Event("authChange")
    );

    navigate("/login");

  };


  // =========================
  // Loading
  // =========================

  if (loading) {

    return (
      <div className="profile-loading">

        <div
          className="spinner-border"
          role="status"
        ></div>

        <p>
          Loading profile...
        </p>

      </div>
    );

  }


  // =========================
  // Initials
  // =========================

  const initials =
    `${user?.firstName?.charAt(0) || ""}${user?.lastName?.charAt(0) || ""}`
      .toUpperCase();


  // =========================
  // Profile
  // =========================

  return (

    <div className="profile-page">

      <div className="profile-card">


        {/* =========================
            HEADER
        ========================== */}

        <div className="profile-header">

          <div className="profile-avatar-wrapper">

            {isEditing ? (

              <label
                htmlFor="profilePicture"
                className="profile-avatar clickable-avatar"
              >

                {profileData.profilePicture ? (

                  <img
                    src={profileData.profilePicture}
                    alt="Profile"
                    className="profile-avatar-image"
                  />

                ) : (

                  <span>
                    {initials || "U"}
                  </span>

                )}

                <span className="avatar-edit-icon">
                  ✎
                </span>

              </label>

            ) : (

              <div className="profile-avatar">

                {user?.profilePicture ? (

                  <img
                    src={user.profilePicture}
                    alt="Profile"
                    className="profile-avatar-image"
                  />

                ) : (

                  <span>
                    {initials || "U"}
                  </span>

                )}

              </div>

            )}

            {isEditing && (

              <input
                id="profilePicture"
                type="file"
                accept="image/*"
                onChange={handleProfilePicture}
                className="d-none"
              />

            )}

          </div>


          <div>

            <h4>
              {user?.firstName} {user?.lastName}
            </h4>

            <p>
              {user?.email}
            </p>

          </div>

        </div>


        {/* =========================
            BODY
        ========================== */}

        <div className="profile-body">


          {error && (

            <div className="alert alert-danger">
              {error}
            </div>

          )}


          {message && (

            <div className="alert alert-success">
              {message}
            </div>

          )}


          {/* =========================
              PROFILE FORM
          ========================== */}

          {isEditing ? (

            <form onSubmit={saveProfile}>

              <div className="profile-info-grid">


                {/* First Name */}

                <div className="profile-info-item">

                  <label className="profile-label">
                    First Name
                  </label>

                  <input
                    type="text"
                    name="firstName"
                    className="form-control"
                    value={profileData.firstName}
                    onChange={handleProfileChange}
                    required
                  />

                </div>


                {/* Last Name */}

                <div className="profile-info-item">

                  <label className="profile-label">
                    Last Name
                  </label>

                  <input
                    type="text"
                    name="lastName"
                    className="form-control"
                    value={profileData.lastName}
                    onChange={handleProfileChange}
                    required
                  />

                </div>


                {/* Email */}

                <div className="profile-info-item">

                  <span className="profile-label">
                    Email
                  </span>

                  <p className="profile-value">
                    {user?.email || "—"}
                  </p>

                  <small className="profile-help">
                    Email cannot be changed.
                  </small>

                </div>


                {/* Phone */}

                <div className="profile-info-item">

                  <label className="profile-label">
                    Phone Number
                  </label>

                  <input
                    type="text"
                    name="phoneNumber"
                    className="form-control"
                    value={profileData.phoneNumber}
                    onChange={handleProfileChange}
                    required
                  />

                </div>


                {/* Role */}

                <div className="profile-info-item">

                  <span className="profile-label">
                    Role
                  </span>

                  <p className="profile-value">
                    {user?.role || "USER"}
                  </p>

                </div>

              </div>


              {/* Save / Cancel */}

              <div className="profile-actions">

                <button
                  type="submit"
                  className="btn btn-profile-primary"
                >
                  Save Changes
                </button>

                <button
                  type="button"
                  className="btn btn-app-secondary"
                  onClick={cancelEditing}
                >
                  Cancel
                </button>

              </div>

            </form>

          ) : (

            <>


              {/* =========================
                  VIEW PROFILE
              ========================== */}

              <div className="profile-info-grid">


                <div className="profile-info-item">

                  <span className="profile-label">
                    First Name
                  </span>

                  <p className="profile-value">
                    {user?.firstName || "—"}
                  </p>

                </div>


                <div className="profile-info-item">

                  <span className="profile-label">
                    Last Name
                  </span>

                  <p className="profile-value">
                    {user?.lastName || "—"}
                  </p>

                </div>


                <div className="profile-info-item">

                  <span className="profile-label">
                    Email
                  </span>

                  <p className="profile-value">
                    {user?.email || "—"}
                  </p>

                </div>


                <div className="profile-info-item">

                  <span className="profile-label">
                    Phone Number
                  </span>

                  <p className="profile-value">
                    {user?.phoneNumber || "—"}
                  </p>

                </div>


                <div className="profile-info-item">

                  <span className="profile-label">
                    Role
                  </span>

                  <p className="profile-value">
                    {user?.role || "USER"}
                  </p>

                </div>

              </div>


              {/* =========================
                  ACTIONS
              ========================== */}

              <div className="profile-actions">


                <button
                  type="button"
                  className="btn btn-profile-secondary"
                  onClick={() => {

                    setError("");
                    setMessage("");
                    setShowPasswordModal(true);

                  }}
                >
                  Change Password
                </button>


                <button
                  type="button"
                  className="btn btn-profile-danger"
                  onClick={handleLogout}
                >
                  Logout
                </button>

              </div>

            </>

          )}

        </div>

      </div>


      {/* =========================
          CHANGE PASSWORD MODAL
      ========================== */}

      {showPasswordModal && (

        <div
          className="profile-modal-backdrop"
          onClick={() =>
            setShowPasswordModal(false)
          }
        >

          <div
            className="profile-password-modal"
            onClick={(e) =>
              e.stopPropagation()
            }
          >

            <div className="modal-header">

              <div>

                <h5 className="modal-title">
                  Change Password
                </h5>

                <p className="password-modal-subtitle">
                  Update your account password securely.
                </p>

              </div>

              <button
                type="button"
                className="btn-close"
                onClick={() =>
                  setShowPasswordModal(false)
                }
              ></button>

            </div>


            <form onSubmit={changePassword}>

              <div className="modal-body">


                <div className="mb-3">

                  <label className="form-label">
                    Current Password
                  </label>

                  <input
                    type="password"
                    name="currentPassword"
                    className="form-control"
                    placeholder="Enter current password"
                    value={
                      passwordData.currentPassword
                    }
                    onChange={handlePasswordChange}
                    required
                  />

                </div>


                <div className="mb-3">

                  <label className="form-label">
                    New Password
                  </label>

                  <input
                    type="password"
                    name="newPassword"
                    className="form-control"
                    placeholder="Enter new password"
                    value={
                      passwordData.newPassword
                    }
                    onChange={handlePasswordChange}
                    required
                  />

                </div>

              </div>


              <div className="modal-footer">

                <button
                  type="button"
                  className="btn btn-app-secondary"
                  onClick={() =>
                    setShowPasswordModal(false)
                  }
                >
                  Cancel
                </button>

                <button
                  type="submit"
                  className="btn btn-profile-primary"
                >
                  Change Password
                </button>

              </div>

            </form>

          </div>

        </div>

      )}

    </div>

  );

}

export default Profile;